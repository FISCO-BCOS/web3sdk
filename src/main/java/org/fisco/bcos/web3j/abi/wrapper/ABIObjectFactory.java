package org.fisco.bcos.web3j.abi.wrapper;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ABIObjectFactory {

    private static final Logger logger = LoggerFactory.getLogger(ABIObjectFactory.class);

    public static ABIObject createInputObject(ABIDefinition abiDefinition) {
        return createObject(
                abiDefinition.getName(), abiDefinition.getType(), abiDefinition.getInputs());
    }

    public static ABIObject createOutputObject(ABIDefinition abiDefinition) {
        return createObject(
                abiDefinition.getName(), abiDefinition.getType(), abiDefinition.getOutputs());
    }

    private static ABIObject createObject(
            String name, String type, List<ABIDefinition.NamedType> namedTypes) {
        try {
            ABIObject abiObject = new ABIObject(ABIObject.ObjectType.STRUCT);

            for (ABIDefinition.NamedType namedType : namedTypes) {
                abiObject.getStructFields().add(buildTypeObject(namedType));
            }

            if (logger.isTraceEnabled()) {
                logger.trace(" name: {}", name);
            }

            return abiObject;

        } catch (Exception e) {
            logger.error("namedTypes: {},  e: ", namedTypes, e);
        }

        return null;
    }

    /**
     * build ABIObject by raw type name
     *
     * @param rawType
     * @return
     */
    public static ABIObject buildRawTypeObject(String rawType) {

        ABIObject abiObject = null;

        if (rawType.startsWith("uint")) {
            abiObject = new ABIObject(ABIObject.ValueType.UINT);
        } else if (rawType.startsWith("int")) {
            abiObject = new ABIObject(ABIObject.ValueType.INT);
        } else if (rawType.startsWith("bool")) {
            abiObject = new ABIObject(ABIObject.ValueType.BOOL);
        } else if (rawType.startsWith("string")) {
            abiObject = new ABIObject(ABIObject.ValueType.STRING);
        } else if (rawType.equals("bytes")) {
            abiObject = new ABIObject(ABIObject.ValueType.DBYTES);
        } else if (rawType.startsWith("bytes")) {
            abiObject = new ABIObject(ABIObject.ValueType.BYTES);
        } else if (rawType.startsWith("address")) {
            abiObject = new ABIObject(ABIObject.ValueType.ADDRESS);
        } else if (rawType.startsWith("fixed") || rawType.startsWith("ufixed")) {
            throw new UnsupportedOperationException("Unsupported type:" + rawType);
        } else {
            throw new UnsupportedOperationException("Unrecognized type:" + rawType);
        }

        return abiObject;
    }

    private static ABIObject buildTupleObject(ABIDefinition.NamedType namedType) {
        return createObject(namedType.getName(), namedType.getType(), namedType.getComponents());
    }

    private static ABIObject buildListObject(
            ABIDefinition.Type typeObj, ABIDefinition.NamedType namedType) {

        ABIObject abiObject = null;
        if (typeObj.isList()) {
            ABIObject listObject = new ABIObject(ABIObject.ObjectType.LIST);
            listObject.setListType(
                    typeObj.isFixedList() ? ABIObject.ListType.FIXED : ABIObject.ListType.DYNAMIC);
            if (typeObj.isFixedList()) {
                listObject.setListLength(typeObj.getLastDimension());
            }

            listObject.setListValueType(
                    buildListObject(typeObj.reduceDimensionAndGetType(), namedType));
            abiObject = listObject;
        } else if (typeObj.getRawType().startsWith("tuple")) {
            abiObject = buildTupleObject(namedType);
        } else {
            abiObject = buildRawTypeObject(typeObj.getRawType());
        }

        return abiObject;
    }

    public static ABIObject buildTypeObject(ABIDefinition.NamedType namedType) {
        try {
            String type = namedType.getType();
            // String name = namedType.getName();
            // boolean indexed = namedType.isIndexed();

            ABIDefinition.Type typeObj = new ABIDefinition.Type(type);
            String rawType = typeObj.getRawType();

            ABIObject abiObject = null;
            if (typeObj.isList()) {
                abiObject = buildListObject(typeObj, namedType);
            } else if (rawType.startsWith("tuple")) {
                abiObject = buildTupleObject(namedType);
            } else {
                abiObject = buildRawTypeObject(rawType);
            }

            abiObject.setName(namedType.getName());

            return abiObject;
        } catch (Exception e) {
            logger.error(" e: ", e);
        }

        return null;
    }
}
