package org.fisco.bcos.web3j.console;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;

public class ContractClassFactory {
	
	public static Class<?>  getContractClass(String contractName) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		return (Class<?>) Class.forName(contractName); 
	}
	
    public static Class[] getParameterType(Class clazz, String methodName) throws ClassNotFoundException {
        Method[] methods = clazz.getDeclaredMethods();
        Class[] type = null;
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                Parameter[] params = method.getParameters();
                type =  new Class[params.length];
                for(int i=0; i< params.length;i++) {
                    type[i] = Class.forName(params[i].getParameterizedType().getTypeName());
                }

            }
        }

        return type;
    }
    
	public static Object[] getPrametersObject(String[] params) {
		Object[] argobj = new Object[params.length - 2];
    	for (int i = 0; i < argobj.length; i++) 
    	{	
    		if("true".equals(params[i+2].trim()))
    		{
    			argobj[i] = true;
    		}
    		else if( "false".equals(params[i+2].trim()))
    		{
    			argobj[i] = false;
    		}
    		else if(params[i+2].matches("^[0-9]*$"))
    		{
    			argobj[i] = new BigInteger(params[i+2]);
    		}
       		else
    		{
       			argobj[i] = params[i+2];
    		}
		}
		return argobj;
	}
}
