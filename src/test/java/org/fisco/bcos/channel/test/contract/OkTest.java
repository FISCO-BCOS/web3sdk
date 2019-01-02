package org.fisco.bcos.channel.test.contract;

import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.web3j.console.ContractClassFactory;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import static org.fisco.bcos.web3j.console.ConsoleClient.*;
import static org.junit.Assert.assertTrue;


public class OkTest extends TestBase {

    public static Class<?> contractClass;
    public String  contractName;
    public RemoteCall<?>  remoteCall;
    public String contractAddress;
    public java.math.BigInteger gasPrice = new BigInteger("300000000");
    public java.math.BigInteger gasLimit = new BigInteger("300000000");
    public java.math.BigInteger initialWeiValue = new BigInteger("0");

    @Test
    public void testOkContract() throws Exception {


        Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).send();

        if (okDemo != null) {
            System.out.println("####contract address is: " + okDemo.getContractAddress());
            TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
            assertTrue(receipt.getBlockNumber().intValue() > 0);
            assertTrue(receipt.getTransactionIndex().intValue() >= 0);
            assertTrue(receipt.getGasUsed().intValue() > 0);
            BigInteger oldBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
            okDemo.trans(new BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
            BigInteger newBalance = okDemo.get().sendAsync().get(60000, TimeUnit.MILLISECONDS);
           assertTrue(newBalance.intValue() == oldBalance.intValue() + 4);
        }
    }


    @Test
    public void testLoadClass() throws Exception {

        contractName = "org.fisco.bcos.channel.test.contract." + "Ok";
            contractClass = ContractClassFactory.getContractClass(contractName);
            Method deploy = contractClass.getMethod("deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class, BigInteger.class);
            Method deploy1 = contractClass.getDeclaredMethod("deploy",Web3j.class, Credentials.class, BigInteger.class, BigInteger.class, BigInteger.class);
        //    Object obj = contractClass.newInstance();
            // Method m =  contractClass.getMethod("get",)
            //     m.invoke()
            remoteCall = (RemoteCall<?>) deploy.invoke(null, web3j, credentials, gasPrice, gasLimit, initialWeiValue);
            Contract contract = (Contract) remoteCall.send();
            contractAddress = contract.getContractAddress();
            System.out.println(contractAddress);

        Method load = contractClass.getMethod("load", String.class, Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
        Object contractObject = load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);


        Method func = contractClass.getMethod("get",  getParameterType(contractClass,"get"));
        RemoteCall<BigInteger> rm = (RemoteCall)func.invoke(contractObject);
        System.out.println( rm.send().intValue());

        Method func1 = contractClass.getMethod("trans",  getParameterType(contractClass,"trans"));
//       Class[] type =  getParameterType(contractClass,"trans");
//        type[0].newInstance();
        RemoteCall<TransactionReceipt> rm1 = (RemoteCall) func1.invoke(contractObject, new BigInteger("6"));
        System.out.println(rm1.send().getTransactionHash());
    }


    public static Class[] getParameterType(Class clazz, String methodName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method[] methods = clazz.getDeclaredMethods();
        Class[] type =null;
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                Parameter[] params = method.getParameters();
                type =  new Class[params.length];
                for(int i=0; i< params.length;i++) {
                    System.out.println(params[i].getParameterizedType().getTypeName());
                    type[i] = Class.forName(params[i].getParameterizedType().getTypeName());
                    Class.forName(params[i].getParameterizedType().getTypeName()).getDeclaredConstructor(String.class).newInstance("124");
                }

            }
        }

        return type;
    }
}





