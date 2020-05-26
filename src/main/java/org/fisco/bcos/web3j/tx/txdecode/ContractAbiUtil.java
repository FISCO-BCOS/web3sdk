/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.fisco.bcos.web3j.tx.txdecode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.web3j.abi.EventValues;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** ContractAbiUtil. */
public class ContractAbiUtil {

    private static final Logger logger = LoggerFactory.getLogger(ContractAbiUtil.class);

    public static final String TYPE_CONSTRUCTOR = "constructor";
    public static final String TYPE_FUNCTION = "function";
    public static final String TYPE_EVENT = "event";

    /**
     * @param contractAbi
     * @return
     */
    public static AbiDefinition getConstructorAbiDefinition(String contractAbi) {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            AbiDefinition[] abiDefinitions =
                    objectMapper.readValue(contractAbi, AbiDefinition[].class);

            for (AbiDefinition abiDefinition : abiDefinitions) {
                if (TYPE_CONSTRUCTOR.equals(abiDefinition.getType())) {
                    return abiDefinition;
                }
            }
        } catch (JsonProcessingException e) {
            logger.warn(" invalid  json, abi: {}, e: {} ", contractAbi, e);
        }
        return null;
    }

    /**
     * @param contractAbi
     * @return
     */
    public static List<AbiDefinition> getFuncAbiDefinition(String contractAbi) {
        List<AbiDefinition> result = new ArrayList<>();
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            AbiDefinition[] abiDefinitions =
                    objectMapper.readValue(contractAbi, AbiDefinition[].class);

            for (AbiDefinition abiDefinition : abiDefinitions) {
                if (TYPE_FUNCTION.equals(abiDefinition.getType())
                        || TYPE_CONSTRUCTOR.equals(abiDefinition.getType())) {
                    result.add(abiDefinition);
                }
            }
        } catch (JsonProcessingException e) {
            logger.warn(" invalid json, abi: {}, e: {} ", contractAbi, e);
        }
        return result;
    }

    /**
     * @param contractAbi
     * @return
     */
    public static List<AbiDefinition> getEventAbiDefinitions(String contractAbi) {

        List<AbiDefinition> result = new ArrayList<>();
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            AbiDefinition[] abiDefinitions =
                    objectMapper.readValue(contractAbi, AbiDefinition[].class);

            for (AbiDefinition abiDefinition : abiDefinitions) {
                if (TYPE_EVENT.equals(abiDefinition.getType())) {
                    result.add(abiDefinition);
                }
            }
        } catch (JsonProcessingException e) {
            logger.warn(" invalid json, abi: {}, e: {} ", contractAbi, e);
        }
        return result;
    }

    /**
     * @param abiDefinition
     * @return
     */
    public static List<String> getFuncInputType(AbiDefinition abiDefinition) {
        List<String> inputList = new ArrayList<>();
        if (abiDefinition != null) {
            List<NamedType> inputs = abiDefinition.getInputs();
            for (NamedType input : inputs) {
                inputList.add(input.getType());
            }
        }
        return inputList;
    }

    /**
     * @param abiDefinition
     * @return
     */
    public static List<String> getFuncOutputType(AbiDefinition abiDefinition) {
        List<String> outputList = new ArrayList<>();
        List<NamedType> outputs = abiDefinition.getOutputs();
        for (NamedType output : outputs) {
            outputList.add(output.getType());
        }
        return outputList;
    }

    /**
     * @param paramTypes
     * @return
     * @throws BaseException
     */
    public static List<TypeReference<?>> paramFormat(List<NamedType> paramTypes)
            throws BaseException {
        List<TypeReference<?>> finalOutputs = new ArrayList<>();

        for (int i = 0; i < paramTypes.size(); i++) {

            AbiDefinition.NamedType.Type type =
                    new AbiDefinition.NamedType.Type(paramTypes.get(i).getType());
            // nested array , not support now.
            if (type.getDepth() > 1) {
                throw new BaseException(
                        201202,
                        String.format("type:%s unsupported array decoding", type.getName()));
            }

            TypeReference<?> typeReference = null;
            if (type.dynamicArray()) {
                typeReference =
                        DynamicArrayReference.create(
                                type.getBaseName(), paramTypes.get(i).isIndexed());
            } else if (type.staticArray()) {
                typeReference =
                        StaticArrayReference.create(
                                type.getBaseName(),
                                type.getDimensions(),
                                paramTypes.get(i).isIndexed());
            } else {
                typeReference =
                        TypeReference.create(
                                ContractTypeUtil.getType(paramTypes.get(i).getType()),
                                paramTypes.get(i).isIndexed());
            }

            finalOutputs.add(typeReference);
        }
        return finalOutputs;
    }

    /**
     * @param log
     * @param abiDefinition
     * @return
     * @throws BaseException
     */
    public static EventValues decodeEvent(Log log, AbiDefinition abiDefinition)
            throws BaseException {

        List<TypeReference<?>> finalOutputs = paramFormat(abiDefinition.getInputs());
        Event event = new Event(abiDefinition.getName(), finalOutputs);

        EventValues eventValues = Contract.staticExtractEventParameters(event, log);
        return eventValues;
    }
}
