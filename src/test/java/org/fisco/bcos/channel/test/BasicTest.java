package org.fisco.bcos.channel.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.channel.test.contract.Ok;
import org.fisco.bcos.web3j.abi.datatypes.Array;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.console.ContractClassFactory;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.DefaultBlockParameter;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.*;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Ignore;
import org.junit.Test;

public class BasicTest extends TestBase {
  private static BigInteger gasPrice = new BigInteger("300000000");
  private static BigInteger gasLimit = new BigInteger("300000000");

  @Ignore
  @Test
  public void pbftViewTest() throws Exception {
    int i = web3j.getPbftView().send().getPbftView().intValue();
    System.out.println(i);
    assertNotNull(i > 0);
  }

  @Test
  public void consensusStatusTest() throws Exception {
    System.out.println(web3j.getConsensusStatus().sendForReturnString());
    assertNotNull(web3j.getConsensusStatus().sendForReturnString());
  }

  @Test
  public void syncTest() throws Exception {
    System.out.println(web3j.getSyncStatus().send().isSyncing());
    assertNotNull(web3j.getSyncStatus().send().isSyncing());
  }

  @Test
  public void versionTest() throws Exception {
    String web3ClientVersion = web3j.getClientVersion().sendForReturnString();
    System.out.println(web3ClientVersion);
    assertNotNull(web3ClientVersion);
  }

  // getPeers
  @Ignore
  @Test
  public void peersTest() throws Exception {
    Peers ethPeers = web3j.getPeers().send();
    System.out.println(ethPeers.getValue().get(0).getNodeID());
    assertNotNull(ethPeers);
  }

  @Test
  public void groupPeersTest() throws Exception {
    GroupPeers groupPeers = web3j.getGroupPeers().send();
    groupPeers.getGroupPeers().stream().forEach(System.out::println);
    assertNotNull(groupPeers.getResult());
  }

  @Test
  public void groupListTest() throws Exception {
    GroupList groupList = web3j.getGroupList().send();
    groupList.getGroupList().stream().forEach(System.out::println);
    assertTrue((groupList.getGroupList().size() > 0));
  }

  @Ignore
  @Test
  public void getTransactionByBlockNumberAndIndexTest() throws IOException {
    Transaction transaction =
        web3j
            .getTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(new BigInteger("1")), new BigInteger("0"))
            .send()
            .getTransaction()
            .get();
    assertTrue(transaction.getBlockNumber().intValue() == 1);
  }

  @Test
  public void basicTest() throws Exception {
    try {
      testDeployContract(web3j, credentials);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Execute basic test failed");
    }
  }

  private void testDeployContract(Web3j web3j, Credentials credentials) throws Exception {
    Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit).send();
    if (okDemo != null) {
      System.out.println(
          "####get nonce from Block: "
              + web3j
                  .getBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("0")), true)
                  .send()
                  .getBlock()
                  .getNonce());
      System.out.println(
          "####get block number by index from Block: "
              + web3j
                  .getBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger("1")), true)
                  .send()
                  .getBlock()
                  .getNumber());

      System.out.println("####contract address is: " + okDemo.getContractAddress());
      // TransactionReceipt receipt = okDemo.trans(new
      // BigInteger("4")).sendAsync().get(60000, TimeUnit.MILLISECONDS);
      TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).send();
      List<Ok.TransEventEventResponse> events = okDemo.getTransEventEvents(receipt);
      events.stream().forEach(System.out::println);

      System.out.println("###callback trans success");

      System.out.println(
          "####get block number from TransactionReceipt: " + receipt.getBlockNumber());
      System.out.println(
          "####get transaction index from TransactionReceipt: " + receipt.getTransactionIndex());
      System.out.println("####get gas used from TransactionReceipt: " + receipt.getGasUsed());
      // System.out.println("####get cumulative gas used from TransactionReceipt: " +
      // receipt.getCumulativeGasUsed());

      BigInteger toBalance = okDemo.get().send();
      System.out.println("============to balance:" + toBalance.intValue());
    }
  }



  @Test
  public void test() throws Exception {

    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    Class contractClass = ContractClassFactory.getContractClass("org.fisco.bcos.temp.EvidenceSignersData");
    Method[] methods = contractClass.getDeclaredMethods();
    Method method = null;
    for (Method m : methods) {
      if ("deploy".equals(m.getName())) {
        String s = m.toGenericString();
        if (s.contains("org.fisco.bcos.web3j.protocol.Web3j") && s.contains("org.fisco.bcos.web3j.crypto.Credentials") && s.contains("org.fisco.bcos.web3j.tx.gas.ContractGasProvider")) {
          System.out.println(m.toGenericString());
          method = m;
          break;
        }
      }
    }
  // Class c =  Class.forName("java.util.ArrayList<String>");
    List<String> ilist = new ArrayList<>();
    String[] ilist1 = {"[\"123\"]"};
    ilist.add("0x111111111");
    RemoteCall remoteCall = (RemoteCall<?>) method.invoke(null, web3j, credentials, contractGasProvider, ilist);
    Contract contract = (Contract) remoteCall.send();
    System.out.println(contract.getContractAddress());

    Class[] type = null;
    List<String> typeNames = new ArrayList<>();
    for (Method m : methods) {
      if ("deploy".equals(m.getName())) {

        Parameter[] params = m.getParameters();
        type = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
       //   ParameterizedType t = (ParameterizedType)params[i].getParameterizedType();
       //   Class<?> genericClazz = (Class<?>)t.getActualTypeArguments()[0];
          String typeName = params[i].getParameterizedType().getTypeName();
          typeNames.add(typeName);
          if ("byte[]".equals(typeName)) type[i] = byte[].class;
          else if(typeName.contains("java.util.List")) type[i] =  new ArrayList<String>().getClass();
          else type[i] = Class.forName(typeName);

          //    Class.forName(params[i].getParameterizedType().getTypeName())
//            .getDeclaredConstructor(String.class)
//            .newInstance("124");
        }
      }
    }

    getPrametersObject("EvidenceSignersData", type, typeNames, ilist1 );
  }


    public static Object[] getPrametersObject (String funcName, Class[]type, List<String> typeNames, String[]inputParams){
      Object[] obj = new Object[type.length ];
      for (int i = 4; i < type.length; i++) {
        if (type[i] == String.class) {
          if (inputParams[i ].startsWith("\"") && inputParams[i ].endsWith("\"")) {
            try {
              obj[i] = inputParams[i].substring(1, inputParams[i ].length() - 1);
            } catch (Exception e) {
              System.out.println(
                      "Please provide double quote for String that cannot contain any blank spaces.");
              System.out.println();
              return null;
            }
          } else {
            System.out.println(
                    "Please provide double quote for String that cannot contain any blank spaces.");
            System.out.println();
            return null;
          }
        }
        else if (type[i] == Boolean.class) {
          try {
          //  obj[i] = Boolean.parseBoolean(inputParams[i ]);
            obj[i]= Boolean.class.getDeclaredConstructor(String.class).newInstance(inputParams[i]);
          } catch (Exception e) {
            System.out.println(
                    "The " + (i + 1) + "th parameter of " + funcName + " needs boolean value.");
            System.out.println();
            return null;
          }
        } else if (type[i] == BigInteger.class) {
          try {
            obj[i] = new BigInteger(inputParams[i ]);
          } catch (Exception e) {
            System.out.println(
                    "The " + (i + 1) + "th parameter of " + funcName + " needs integer value.");
            System.out.println();
            return null;
          }
        }
        else if (type[i] == byte[].class) {
          if (inputParams[i].startsWith("\"") && inputParams[i].endsWith("\"")) {
            byte[] bytes1 = new byte[Type.MAX_BYTE_LENGTH];
            byte[] bytes2 = inputParams[i].substring(1, inputParams[i].length() - 1).getBytes();
            for (int j = 0; j < bytes2.length; j++) {
              bytes1[j] = bytes2[j];
            }
            obj[i] = bytes1;
          }
        }
          else if (type[i] == ArrayList.class) {
            if (inputParams[i].startsWith("[") && inputParams[i].endsWith("]")) {
              if(typeNames.get(i).contains("BigInteger")){
                List ilist = new ArrayList<BigInteger>();
                ilist.add(inputParams[i].substring(1, inputParams[i ].length() - 1));
              }
              else if(typeNames.get(i).contains("String")) {
                List ilist = new ArrayList<String>();
                inputParams[i].substring(1, inputParams[i ].length() - 1);
              }
              else if(typeNames.get(i).contains("byte")) {
                List ilist = new ArrayList<byte[]>();
                inputParams[i].substring(1, inputParams[i ].length() - 1);
              }
              else{

              }

            }
          } else {
            System.out.println("Please provide double quote for byte String.");
            System.out.println();
            return null;
          }
        }

      return obj;
    }


@Test
  public void test11() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
     Boolean  b = Boolean.class.getDeclaredConstructor(String.class).newInstance("true");
     System.out.println(b);
     ArrayList ilist = new ArrayList();
     ilist.add("1");
     ilist.add("2");
      ArrayList a = ArrayList.class.getDeclaredConstructor().newInstance(ilist);
  System.out.println(a);
  }

}
