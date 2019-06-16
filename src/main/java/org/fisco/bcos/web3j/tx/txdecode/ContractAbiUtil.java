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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.web3j.abi.EventValues;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition;
import org.fisco.bcos.web3j.protocol.core.methods.response.AbiDefinition.NamedType;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.tx.Contract;

/** ContractAbiUtil. */
public class ContractAbiUtil {

    public static final String TYPE_CONSTRUCTOR = "constructor";
    public static final String TYPE_FUNCTION = "function";
    public static final String TYPE_EVENT = "event";

    /**
     * @param contractAbi
     * @return
     */
    public static AbiDefinition getConstructorAbiDefinition(String contractAbi) {
        JSONArray abiArr = JSONArray.parseArray(contractAbi);
        AbiDefinition result = null;
        for (Object object : abiArr) {
            AbiDefinition abiDefinition = JSON.parseObject(object.toString(), AbiDefinition.class);
            if (TYPE_CONSTRUCTOR.equals(abiDefinition.getType())) {
                result = abiDefinition;
                break;
            }
        }
        return result;
    }

    /**
     * @param contractAbi
     * @return
     */
    public static List<AbiDefinition> getFuncAbiDefinition(String contractAbi) {
        JSONArray abiArr = JSONArray.parseArray(contractAbi);
        List<AbiDefinition> result = new ArrayList<>();
        for (Object object : abiArr) {
            AbiDefinition abiDefinition = JSON.parseObject(object.toString(), AbiDefinition.class);
            if (TYPE_FUNCTION.equals(abiDefinition.getType())
                    || TYPE_CONSTRUCTOR.equals(abiDefinition.getType())) {
                result.add(abiDefinition);
            }
        }
        return result;
    }

    /**
     * @param contractAbi
     * @return
     */
    public static List<AbiDefinition> getEventAbiDefinitions(String contractAbi) {
        JSONArray abiArr = JSONArray.parseArray(contractAbi);
        List<AbiDefinition> result = new ArrayList<>();
        for (Object object : abiArr) {
            AbiDefinition abiDefinition = JSON.parseObject(object.toString(), AbiDefinition.class);
            if (TYPE_EVENT.equals(abiDefinition.getType())) {
                result.add(abiDefinition);
            }
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
    public static List<TypeReference<?>> paramFormat(List<String> paramTypes) throws BaseException {
        List<TypeReference<?>> finalOutputs = new ArrayList<>();

        for (int i = 0; i < paramTypes.size(); i++) {

            AbiDefinition.NamedType.Type type = new AbiDefinition.NamedType.Type(paramTypes.get(i));
            // nested array , not support now.
            if (type.getDepth() > 1) {
                throw new BaseException(
                        201202,
                        String.format("type:%s unsupported array decoding", type.getName()));
            }

            TypeReference<?> typeReference = null;
            if (type.dynamicArray()) {
                typeReference = DynamicArrayReference.create(type.getBaseName());
            } else if (type.staticArray()) {
                typeReference =
                        StaticArrayReference.create(type.getBaseName(), type.getDimensions());
            } else {
                typeReference = TypeReference.create(ContractTypeUtil.getType(paramTypes.get(i)));
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
    public static List<Type> decodeEvent(Log log, AbiDefinition abiDefinition)
            throws BaseException {

        List<Type> result = null;
        List<TypeReference<?>> finalOutputs = paramFormat(getFuncInputType(abiDefinition));
        Event event = new Event(abiDefinition.getName(), finalOutputs);

        EventValues eventValues = Contract.staticExtractEventParameters(event, log);
        if (null != eventValues) {
            result = eventValues.getNonIndexedValues();
        }
        return result;
    }
}
