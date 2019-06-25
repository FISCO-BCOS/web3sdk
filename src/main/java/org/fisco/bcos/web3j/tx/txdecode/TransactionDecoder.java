package org.fisco.bcos.web3j.tx.txdecode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.EventValues;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;

public class TransactionDecoder {

    private String abi = "";
    private String bin = "";
    private Map<String, AbiDefinition> methodIDMap;

    public TransactionDecoder(String abi) {
    	this(abi, "");
    }

    public TransactionDecoder(String abi, String bin) {
        this.abi = abi;
        this.bin = bin;
        methodIDMap = new HashMap<String, AbiDefinition>();
        List<AbiDefinition> funcAbiDefinitionList = ContractAbiUtil.getFuncAbiDefinition(abi);
        for (AbiDefinition abiDefinition : funcAbiDefinitionList) {
            String methodSign = decodeMethodSign(abiDefinition);
            String methodID = FunctionEncoder.buildMethodId(methodSign);
            methodIDMap.put(methodID, abiDefinition);
        }
    }
    
    private String addHexPrefixToString(String s) {
    	if(!s.startsWith("0x")) {
    		return "0x" + s;
    	}
    	
    	return s;
    }

    /**
     * @param input
     * @return
     * @throws JsonProcessingException
     * @throws TransactionException
     * @throws BaseException
     */
    public String decodeInputReturnJson(String input)
            throws JsonProcessingException, TransactionException, BaseException {
    	
    	input = addHexPrefixToString(input);
    	
        // select abi
        AbiDefinition abiFunc = selectAbiDefinition(input);

        // decode input
        List<ResultEntity> resultList = decodeInputReturnObject(input);

        // format result to json
        Map<String, Object> resultMap = new HashMap<>();
        String methodSign = decodeMethodSign(abiFunc);
        resultMap.put("function", methodSign);
        resultMap.put("methodID", FunctionEncoder.buildMethodId(methodSign));
        resultMap.put("data", resultList);

        String result = ObjectMapperFactory.getObjectMapper().writeValueAsString(resultMap);

        return result;
    }

    /**
     * @param input
     * @return
     * @throws BaseException
     * @throws TransactionException
     */
    public List<ResultEntity> decodeInputReturnObject(String input)
            throws BaseException, TransactionException {
    	
    	input = addHexPrefixToString(input);
    	
        // select abi
        AbiDefinition abiDefinition = selectAbiDefinition(input);

        // decode input
        List<NamedType> inputTypes = abiDefinition.getInputs();
        List<TypeReference<?>> inputTypeReferences = ContractAbiUtil.paramFormat(inputTypes);
        Function function = new Function(abiDefinition.getName(), null, inputTypeReferences);
        List<Type> resultType =
                FunctionReturnDecoder.decode(input.substring(10), function.getOutputParameters());

        // set result to java bean
        List<ResultEntity> resultList = new ArrayList<ResultEntity>();
        for (int i = 0; i < inputTypes.size(); i++) {
            resultList.add(
                    new ResultEntity(
                            inputTypes.get(i).getName(),
                            inputTypes.get(i).getType(),
                            resultType.get(i)));
        }
        return resultList;
    }

    /**
     * @param input
     * @param output
     * @return
     * @throws JsonProcessingException
     * @throws BaseException
     * @throws TransactionException
     */
    public String decodeOutputReturnJson(String input, String output)
            throws JsonProcessingException, BaseException, TransactionException {

        List<ResultEntity> resultList = decodeOutputReturnObject(input, output);
        String result = ObjectMapperFactory.getObjectMapper().writeValueAsString(resultList);
        return result;
    }

    /**
     * @param input
     * @param output
     * @return
     * @throws TransactionException
     * @throws BaseException
     */
    public List<ResultEntity> decodeOutputReturnObject(String input, String output)
            throws TransactionException, BaseException {
    	
    	output = addHexPrefixToString(output);

        // select abi
        AbiDefinition abiDefinition = selectAbiDefinition(input);
        // decode output
        List<NamedType> outputTypes = abiDefinition.getOutputs();
        List<TypeReference<?>> outputTypeReference = ContractAbiUtil.paramFormat(outputTypes);
        Function function = new Function(abiDefinition.getName(), null, outputTypeReference);
        List<Type> resultType =
                FunctionReturnDecoder.decode(output, function.getOutputParameters());

        // set result to java bean
        List<ResultEntity> resultList = new ArrayList<>();
        for (int i = 0; i < outputTypes.size(); i++) {
            resultList.add(
                    new ResultEntity(
                            outputTypes.get(i).getName(),
                            outputTypes.get(i).getType(),
                            resultType.get(i)));
        }
        return resultList;
    }

    /**
     * @param logs
     * @return
     * @throws BaseException
     * @throws IOException
     */
    public String decodeEventReturnJson(String logs) throws BaseException, IOException {
        // log json trans to list log
        ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
        CollectionType listType =
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, Log.class);
        @SuppressWarnings("unchecked")
        List<Log> logList = (List<Log>) mapper.readValue(logs, listType);

        // decode event
        Map<String, List<List<EventResultEntity>>> resultEntityMap =
                decodeEventReturnObject(logList);
        String result = mapper.writeValueAsString(resultEntityMap);

        return result;
    }

    /**
     * @param logList
     * @return
     * @throws BaseException
     * @throws IOException
     */
    public String decodeEventReturnJson(List<Log> logList) throws BaseException, IOException {
        // log json trans to list log
        ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
        // decode event
        Map<String, List<List<EventResultEntity>>> resultEntityMap =
                decodeEventReturnObject(logList);
        String result = mapper.writeValueAsString(resultEntityMap);

        return result;
    }

    /**
     * @param logList
     * @return
     * @throws BaseException
     * @throws IOException
     */
    public Map<String, List<List<EventResultEntity>>> decodeEventReturnObject(List<Log> logList)
            throws BaseException, IOException {

        // set result to java bean
        Map<String, List<List<EventResultEntity>>> resultEntityMap = new HashMap<>();

        for (Log log : logList) {
            Tuple2<AbiDefinition, List<EventResultEntity>> resultTuple2 =
                    decodeEventReturnObject(log);
            if (null == resultTuple2) {
                continue;
            }

            AbiDefinition abiDefinition = resultTuple2.getValue1();
            String eventName = decodeMethodSign(abiDefinition);
            if (resultEntityMap.containsKey(eventName)) {
                resultEntityMap.get(eventName).add(resultTuple2.getValue2());
            } else {
                List<List<EventResultEntity>> eventEntityList =
                        new ArrayList<List<EventResultEntity>>();
                eventEntityList.add(resultTuple2.getValue2());
                resultEntityMap.put(eventName, eventEntityList);
            }
        }

        return resultEntityMap;
    }

    public Tuple2<AbiDefinition, List<EventResultEntity>> decodeEventReturnObject(Log log)
            throws BaseException, IOException {

        Tuple2<AbiDefinition, List<EventResultEntity>> result = null;

        // decode log
        List<AbiDefinition> abiDefinitions = ContractAbiUtil.getEventAbiDefinitions(abi);

        for (AbiDefinition abiDefinition : abiDefinitions) {

            String eventSignature =
                    EventEncoder.buildEventSignature(decodeMethodSign(abiDefinition));

            List<String> topics = log.getTopics();
            if ((null == topics) || topics.isEmpty() || !topics.get(0).equals(eventSignature)) {
                continue;
            }

            EventValues eventValued = ContractAbiUtil.decodeEvent(log, abiDefinition);
            if (null != eventValued) {
                List<EventResultEntity> resultEntityList = new ArrayList<EventResultEntity>();
                List<NamedType> inputs = abiDefinition.getInputs();
                List<NamedType> indexedInputs =
                        inputs.stream().filter(NamedType::isIndexed).collect(Collectors.toList());
                List<NamedType> nonIndexedInputs =
                        inputs.stream().filter(p -> !p.isIndexed()).collect(Collectors.toList());

                for (int i = 0; i < indexedInputs.size(); i++) {
                    EventResultEntity eventEntity =
                            new EventResultEntity(
                                    indexedInputs.get(i).getName(),
                                    indexedInputs.get(i).getType(),
                                    true,
                                    eventValued.getIndexedValues().get(i));
                    
                    resultEntityList.add(eventEntity);
                }

                for (int i = 0; i < nonIndexedInputs.size(); i++) {
                    EventResultEntity eventEntity =
                            new EventResultEntity(
                                    nonIndexedInputs.get(i).getName(),
                                    nonIndexedInputs.get(i).getType(),
                                    false,
                                    eventValued.getNonIndexedValues().get(i));
                    
                    resultEntityList.add(eventEntity);
                }

                result =
                        new Tuple2<AbiDefinition, List<EventResultEntity>>(
                                abiDefinition, resultEntityList);
                break;
            }
        }

        return result;
    }

    /**
     * @param input
     * @return
     * @throws TransactionException
     */
    private AbiDefinition selectAbiDefinition(String input) throws TransactionException {
        if (input == null || input.length() < 10) {
            throw new TransactionException("The input is invalid.");
        }
        String methodID = input.substring(0, 10);
        AbiDefinition abiDefinition = methodIDMap.get(methodID);
        if (abiDefinition == null) {
            throw new TransactionException("The method is not included in the contract abi.");
        }
        return abiDefinition;
    }

    /**
     * @param abiDefinition
     * @return
     */
    private String decodeMethodSign(AbiDefinition abiDefinition) {
        List<NamedType> inputTypes = abiDefinition.getInputs();
        StringBuilder methodSign = new StringBuilder();
        methodSign.append(abiDefinition.getName());
        methodSign.append("(");
        String params =
                inputTypes.stream().map(NamedType::getType).collect(Collectors.joining(","));
        methodSign.append(params);
        methodSign.append(")");
        return methodSign.toString();
    }
}
