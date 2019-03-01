package org.fisco.bcos.channel.test.contract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import org.fisco.bcos.channel.test.TestBase;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.console.ContractClassFactory;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.tx.Contract;
import org.junit.Test;

public class OkTest extends TestBase {

  public static Class<?> contractClass;
  public String contractName;
  public RemoteCall<?> remoteCall;
  public String contractAddress;
  public java.math.BigInteger gasPrice = new BigInteger("300000000");
  public java.math.BigInteger gasLimit = new BigInteger("300000000");

  @Test
  public void testOkContract() throws Exception {

    HelloWorld helloWorld = HelloWorld.deploy(web3j, credentials, gasPrice, gasLimit).send();

    if (helloWorld != null) {
      System.out.println("####contract address is: " + helloWorld.getContractAddress());
      web3j.getCode(helloWorld.getContractAddress(), DefaultBlockParameterName.LATEST);
      // TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
    }
  }

  @Test
  public void testLoadClass() throws Exception {

    contractName = "org.fisco.bcos.channel.test.contract." + "Ok";
    contractClass = ContractClassFactory.getContractClass(contractName);
    Method deploy =
        contractClass.getMethod(
            "deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
    Method deploy1 =
        contractClass.getDeclaredMethod(
            "deploy", Web3j.class, Credentials.class, BigInteger.class, BigInteger.class);
    //    Object obj = contractClass.newInstance();
    // Method m =  contractClass.getMethod("get",)
    //     m.invoke()
    remoteCall = (RemoteCall<?>) deploy.invoke(null, web3j, credentials, gasPrice, gasLimit);
    Contract contract = (Contract) remoteCall.send();
    contractAddress = contract.getContractAddress();
    System.out.println(contractAddress);

    Method load =
        contractClass.getMethod(
            "load",
            String.class,
            Web3j.class,
            Credentials.class,
            BigInteger.class,
            BigInteger.class);
    Object contractObject =
        load.invoke(null, contractAddress, web3j, credentials, gasPrice, gasLimit);
  }

  public static Class[] getParameterType(Class clazz, String methodName)
      throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
          InvocationTargetException, InstantiationException {
    Method[] methods = clazz.getDeclaredMethods();
    Class[] type = null;
    for (Method method : methods) {
      if (methodName.equals(method.getName())) {
        Parameter[] params = method.getParameters();
        type = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
          System.out.println(params[i].getParameterizedType().getTypeName());
          type[i] = Class.forName(params[i].getParameterizedType().getTypeName());
          Class.forName(params[i].getParameterizedType().getTypeName())
              .getDeclaredConstructor(String.class)
              .newInstance("124");
        }
      }
    }

    return type;
  }
}
