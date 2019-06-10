package org.fisco.bcos.web3j.tx.txdecode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.protocol.exceptions.TransactionException;

@Data
public class TransactionDecoder {

    private String abi;
    private String bin;
    private Map<String, AbiDefinition> methodIDMap;

    public TransactionDecoder(String abi, String bin) {
        this.abi = abi;
        this.bin = bin;
        methodIDMap = new HashMap<>();
        List<AbiDefinition> funcAbiDefinitionList = ContractAbiUtil.getFuncAbiDefinition(abi);
        for (AbiDefinition abiDefinition : funcAbiDefinitionList) {
            List<String> inputTypes = ContractAbiUtil.getFuncInputType(abiDefinition);
            StringBuilder result = new StringBuilder();
            result.append(abiDefinition.getName());
            result.append("(");
            String params =
                    inputTypes.stream().map(String::toString).collect(Collectors.joining(","));
            result.append(params);
            result.append(")");
            String methodID = FunctionEncoder.buildMethodId(result.toString());
            methodIDMap.put(methodID, abiDefinition);
        }
    }

    public String decodeConstructorReturnJson(String input) throws JsonProcessingException {
        AbiDefinition abiConstructor = ContractAbiUtil.getAbiDefinitionForConstructor(abi);
        List<String> constructorTypes = ContractAbiUtil.getFuncInputType(abiConstructor);
        String result = ObjectMapperFactory.getObjectMapper().writeValueAsString(constructorTypes);
        return result;
    }

    public String decodeInputReturnJson(String input)
            throws JsonProcessingException, TransactionException, BaseException {
        // get function abi
        String methodID = input.substring(0, 10);
        AbiDefinition abiFunc = methodIDMap.get(methodID);
        if (abiFunc == null) {
            throw new TransactionException("The method is not included in the contract abi.");
        }
        // decode input
        //        String inputStr = input.substring(10);
        List<String> intputString = ContractAbiUtil.getFuncInputType(abiFunc);
        List<Type> inputType = ContractAbiUtil.inputFormat(intputString, null);
        List<Object> resultObj = ContractAbiUtil.callResultParse(intputString, inputType);
        System.out.println(resultObj);
        return "";
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
        // get function abi
        String methodID = input.substring(0, 10);
        AbiDefinition abiFunc = methodIDMap.get(methodID);
        if (abiFunc == null) {
            throw new TransactionException("The method is not included in the contract abi.");
        }
        // decode output
        List<String> outTypes = ContractAbiUtil.getFuncOutputType(abiFunc);
        List<TypeReference<?>> finalOutputs = ContractAbiUtil.outputFormat(outTypes);
        Function function = new Function(abiFunc.getName(), null, finalOutputs);
        List<Type> typeList = FunctionReturnDecoder.decode(output, function.getOutputParameters());
        List<Object> resultObj = ContractAbiUtil.callResultParse(outTypes, typeList);

        // format output to json
        List<NamedType> outputs = abiFunc.getOutputs();
        List<OutPutEntity> resultList = new ArrayList<>();
        for (int i = 0; i < outTypes.size(); i++) {
            resultList.add(
                    new OutPutEntity(
                            outputs.get(i).getName(), outputs.get(i).getType(), resultObj.get(i)));
        }
        String result =
                ObjectMapperFactory.getObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .writeValueAsString(resultList);
        return result;
    }

    /**
     * @param log
     * @return
     * @throws BaseException
     * @throws IOException
     */
    public String decodeEventReturnJson(String log) throws BaseException, IOException {
        // log json trans to list log
        ObjectMapper mapper = ObjectMapperFactory.getObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        CollectionType listType =
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, Log.class);
        List<Log> logList = (List<Log>) mapper.readValue(log, listType);

        // decode event
        List<AbiDefinition> eventAbiDefinitions = ContractAbiUtil.getEventAbiDefinitions(abi);
        Map<String, List<Object>> resultObjectMap =
                ContractAbiUtil.decodeEvents(logList, eventAbiDefinitions);

        // format event to json
        Map<String, List<EventEntity>> resultEventEntityMap = new HashMap<>();

        for (AbiDefinition abiDefinition : eventAbiDefinitions) {
            if (resultObjectMap.containsKey(abiDefinition.getName())) {
                List<EventEntity> eventEntityList = new ArrayList<>();
                List<NamedType> inputs = abiDefinition.getInputs();
                for (int i = 0; i < inputs.size(); i++) {
                    EventEntity eventEntity =
                            new EventEntity(
                                    inputs.get(i).getName(),
                                    inputs.get(i).getType(),
                                    resultObjectMap.get(abiDefinition.getName()).get(i));
                    eventEntityList.add(eventEntity);
                }
                resultEventEntityMap.put(abiDefinition.getName(), eventEntityList);
            }
        }
        String result = mapper.writeValueAsString(resultEventEntityMap);
        return result;
    }

    public Object decodeInputReturnObject(TransactionReceipt receipt) {
        // TODO
        return null;
    }

    public Object decodeOutPutReturnObject(TransactionReceipt receipt) {
        // TODO
        return null;
    }

    public Object decodeEventReturnObject(TransactionReceipt receipt) {
        // TODO
        return null;
    }
}
