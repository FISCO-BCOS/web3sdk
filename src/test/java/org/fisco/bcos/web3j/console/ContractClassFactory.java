package org.fisco.bcos.web3j.console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Arrays;

import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.Tuple;
import org.fisco.bcos.web3j.tuples.generated.*;

public class ContractClassFactory {
	
	public static Class<?>  getContractClass(String contractName) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		return (Class<?>) Class.forName(contractName); 
	}
	
    @SuppressWarnings("rawtypes")
	public static Class[] getParameterType(Class clazz, String methodName, int paramsLen) throws ClassNotFoundException {
        Method[] methods = clazz.getDeclaredMethods();
        Class[] type = null;
        for (Method method : methods) {
            if (methodName.equals(method.getName()) && (method.getParameters()).length == paramsLen) {
                Parameter[] params = method.getParameters();
                type =  new Class[params.length];
                for(int i=0; i< params.length;i++) {
                	String typeName = params[i].getParameterizedType().getTypeName();
					if("byte[]".equals(typeName))
						type[i] = byte[].class;
					else
						type[i] = Class.forName(typeName);
                }
                break;
            }
        }

        return type;
    }
    
	@SuppressWarnings("rawtypes")
	public static Object[] getPrametersObject(String funcName, Class[] type, String[] params) {
		Object[] obj = new Object[params.length - 4];
    	for (int i = 0; i < obj.length; i++) 
    	{	
			if(type[i] == String.class)
			{
				if(params[i+4].startsWith("\"") && params[i+4].endsWith("\""))
				{
					try {
						obj[i] = params[i+4].substring(1, params[i+4].length()-1);
					} catch (Exception e) {
						System.out.println("Please provide double quote for String that cannot contain any blank spaces");
						System.out.println();
						return null;
					}
				}
				else
				{
					System.out.println("Please provide double quote for String that cannot contain any blank spaces");
					System.out.println();
					return null;
				}
			}
			else if(type[i] == Boolean.class)
			{
				try {
					obj[i] = Boolean.parseBoolean(params[i+4]);
				} catch (Exception e) {
					System.out.println("The " + (i+1) + "th parameter of " + funcName +" needs boolean value");
					System.out.println();
					return null;
				}
			}
			else if(type[i] == BigInteger.class)
			{
				try {
					obj[i] = new BigInteger(params[i+4]);
				} catch (Exception e) {
					System.out.println("The " + (i+1) + "th parameter of " + funcName +" needs integer value");
					System.out.println();
					return null;
				}
			}
			else if(type[i] == byte[].class)
			{	
				if(params[i+4].startsWith("\"") && params[i+4].endsWith("\""))
				{
					byte[] bytes1 = new byte[Type.MAX_BYTE_LENGTH];
					byte[] bytes2 = params[i+4].substring(1, params[i+4].length()-1).getBytes();
					for(int j = 0; j < bytes2.length; j++)
					{
						bytes1[j] = bytes2[j];
					}
					obj[i] = bytes1;
				}
				else
				{
					System.out.println("Please provide double quote for byte String");
					System.out.println();
					return null;
				}
			}
		}
		return obj;
	}
	
    @SuppressWarnings("rawtypes")
	public static String getReturnType(Class clazz, String methodName, Class[] parameterType) throws ClassNotFoundException {
        Method[] methods = clazz.getDeclaredMethods();
        String returnType = null;
        for (Method method : methods) 
        {	
            if (methodName.equals(method.getName()) && (method.getParameterTypes()).length == parameterType.length) 
            {	
            	java.lang.reflect.Type genericReturnType = method.getGenericReturnType();
         		if(genericReturnType instanceof ParameterizedType){
         			java.lang.reflect.Type[] actualTypeArguments = ((ParameterizedType)genericReturnType).getActualTypeArguments();
        			for (java.lang.reflect.Type type : actualTypeArguments) {
        				String str = type.getTypeName();
        				int i = str.indexOf("Tuple");
        				if(i == -1)
        				{
        					String[] split = str.split("\\.");
        					returnType = split[split.length-1];
        					break;
        				}
        				else
        				{
        					returnType = str.substring(i, str.length());
        					break;
        				}
        			}
         		}
         		break;
         	}
        }
       return returnType;
    }

	@SuppressWarnings("unchecked")
	public static String getReturnObject(String returnType, Object result) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String resultStr = null;
		if(returnType.startsWith("Tuple") && "byte[]".equals(returnType.substring(7, 13)))
		{	
			int n = Integer.parseInt(returnType.substring(5, 6));
			Tuple resultObj = null;
			switch (n) {
			case 1:
				resultObj = (Tuple1<byte[]>)result;
				break;
			case 2:
				resultObj = (Tuple2<byte[],byte[]>)result;
				break;
			case 3:
				resultObj = (Tuple3<byte[],byte[],byte[]>)result;
				break;
			case 4:
				resultObj = (Tuple4<byte[],byte[],byte[],byte[]>)result;
				break;
			case 5:
				resultObj = (Tuple5<byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 6:
				resultObj = (Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 7:
				resultObj = (Tuple7<byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 8:
				resultObj = (Tuple8<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 9:
				resultObj = (Tuple9<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 10:
				resultObj = (Tuple10<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 11:
				resultObj = (Tuple11<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 12:
				resultObj = (Tuple12<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 13:
				resultObj = (Tuple13<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 14:
				resultObj = (Tuple14<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 15:
				resultObj = (Tuple15<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 16:
				resultObj = (Tuple16<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 17:
				resultObj = (Tuple17<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 18:
				resultObj = (Tuple18<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 19:
				resultObj = (Tuple19<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			case 20:
				resultObj = (Tuple20<byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[],byte[]>)result;
				break;
			}
			Class<? extends Tuple> classResult = resultObj.getClass();
			String[] str = new String[resultObj.getSize()];
			for(int i = 1; i <= resultObj.getSize(); i++)
			{
				Method get = classResult.getMethod("getValue"+i);
				str[i-1] = new String((byte[])get.invoke(result)).trim();
			}
			resultStr = Arrays.toString(str);

		}
		else if("BigInteger".equals(returnType))
		{
			BigInteger resultB = (BigInteger)result;
			resultStr = resultB.toString();
		}
		else if("TransactionReceipt".equals(returnType))
		{
			TransactionReceipt resultTx = (TransactionReceipt)result;
			resultStr = resultTx.getTransactionHash();
		}
		else
		{
			resultStr = result.toString();
		}
		
		return resultStr;
	}
}
