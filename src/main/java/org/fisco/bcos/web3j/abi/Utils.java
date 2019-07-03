package org.fisco.bcos.web3j.abi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Fixed;
import org.fisco.bcos.web3j.abi.datatypes.Int;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Ufixed;
import org.fisco.bcos.web3j.abi.datatypes.Uint;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;

/** Utility functions. */
public class Utils {
    private Utils() {}

    public static <T extends Type> String getTypeName(TypeReference<T> typeReference) {
        return getTypeName(typeReference.getType());
    }

    public static <T extends Type> String getTypeName(java.lang.reflect.Type type) {
        try {

            Class<?> cls = Utils.getClassType(type);
            if (type instanceof ParameterizedType) { // array
                return getParameterizedTypeName(type);
            } else { // simple type
                return getSimpleTypeName(cls);
            }

        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Invalid class reference provided", e);
        }
    }

    private static <T extends Type, U extends Type> String getParameterizedTypeName(
            java.lang.reflect.Type type) {

        try {
            Class<?> cls = Utils.getClassType(type);

            if (DynamicArray.class.isAssignableFrom(cls)) {
                return getTypeName(((ParameterizedType) type).getActualTypeArguments()[0]) + "[]";
            } else if (StaticArray.class.isAssignableFrom(cls)) {

                int length =
                        Integer.parseInt(
                                cls.getSimpleName()
                                        .substring(StaticArray.class.getSimpleName().length()));

                return getTypeName(((ParameterizedType) type).getActualTypeArguments()[0])
                        + "["
                        + length
                        + "]";

            } else {
                throw new UnsupportedOperationException("Invalid type provided " + cls.getName());
            }
        } catch (ClassNotFoundException e) {
            throw new UnsupportedOperationException("Invalid class reference provided", e);
        }
    }

    static String getSimpleTypeName(Class<?> type) {
        String simpleName = type.getSimpleName().toLowerCase();

        if (type.equals(Uint.class)
                || type.equals(Int.class)
                || type.equals(Ufixed.class)
                || type.equals(Fixed.class)) {
            return simpleName + "256";
        } else if (type.equals(Utf8String.class)) {
            return "string";
        } else if (type.equals(DynamicBytes.class)) {
            return "bytes";
        } else {
            return simpleName;
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T extends Type> boolean dynamicType(java.lang.reflect.Type type)
            throws ClassNotFoundException {

        Class<T> cls = Utils.getClassType(type);
        // dynamic type
        if (Utf8String.class.isAssignableFrom(cls)
                || DynamicBytes.class.isAssignableFrom(cls)
                || DynamicArray.class.isAssignableFrom(cls)) {
            return true;
        }

        // not static type
        if (!StaticArray.class.isAssignableFrom(cls)) {
            return false;
        }

        // unpack static array for checking if dynamic type
        java.lang.reflect.Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        return dynamicType(types[0]);
    }

    public static int getLength(List<Type> parameters) {
        int count = 0;
        for (Type type : parameters) {
            count += type.offset();
        }
        return count;
    }

    public static <T extends Type> int getOffset(java.lang.reflect.Type type)
            throws ClassNotFoundException {

        if (Utils.dynamicType(type)) {
            return 1;
        }

        Class<T> cls = Utils.getClassType(type);
        if (StaticArray.class.isAssignableFrom(cls)) {
            int length =
                    Integer.parseInt(
                            cls.getSimpleName()
                                    .substring(StaticArray.class.getSimpleName().length()));
            java.lang.reflect.Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            return getOffset(types[0]) * length;
        } else {
            return 1;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends Type> Class<T> getClassType(java.lang.reflect.Type type)
            throws ClassNotFoundException {
        if (type instanceof ParameterizedType) {
            return (Class<T>) ((ParameterizedType) type).getRawType();
        } else {
            return (Class<T>) Class.forName(type.getTypeName());
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Type> Class<T> getParameterizedTypeFromArray(
            java.lang.reflect.Type type) throws ClassNotFoundException {

        java.lang.reflect.Type[] types = ((ParameterizedType) type).getActualTypeArguments();

        return Utils.getClassType(types[0]);
    }

    @SuppressWarnings("unchecked")
    public static List<TypeReference<Type>> convert(List<TypeReference<?>> input) {
        List<TypeReference<Type>> result = new ArrayList<>(input.size());
        result.addAll(
                input.stream()
                        .map(typeReference -> (TypeReference<Type>) typeReference)
                        .collect(Collectors.toList()));
        return result;
    }

    public static <T, R extends Type<T>, E extends Type<T>> List<E> typeMap(
            List<List<T>> input, Class<E> outerDestType, Class<R> innerType) {
        List<E> result = new ArrayList<>();
        try {
            Constructor<E> constructor = outerDestType.getDeclaredConstructor(List.class);
            for (List<T> ts : input) {
                E e = constructor.newInstance(typeMap(ts, innerType));
                result.add(e);
            }
        } catch (NoSuchMethodException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException e) {
            throw new TypeMappingException(e);
        }
        return result;
    }

    public static <T, R extends Type<T>> List<R> typeMap(List<T> input, Class<R> destType)
            throws TypeMappingException {

        List<R> result = new ArrayList<R>(input.size());

        if (!input.isEmpty()) {
            try {
                Constructor<R> constructor =
                        destType.getDeclaredConstructor(input.get(0).getClass());
                for (T value : input) {
                    result.add(constructor.newInstance(value));
                }
            } catch (NoSuchMethodException
                    | IllegalAccessException
                    | InvocationTargetException
                    | InstantiationException e) {
                throw new TypeMappingException(e);
            }
        }
        return result;
    }
}
