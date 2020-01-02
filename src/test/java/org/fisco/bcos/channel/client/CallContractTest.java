package org.fisco.bcos.channel.client;

import static org.fisco.bcos.web3j.abi.Utils.typeMap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.Utils;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int16;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint16;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CallContractTest {
    static Logger logger = LoggerFactory.getLogger(CallContractTest.class);
    public static Web3j web3j;

    public static ECKeyPair keyPair;
    public static Credentials credentials;
    public static BigInteger gasPrice = new BigInteger("3000000000");
    public static BigInteger gasLimit = new BigInteger("3000000000");

    public static void main(String[] args) throws Exception {

        try {
            // init the Service
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

            Service service = context.getBean(Service.class);
            service.setGroupId(Integer.parseInt(args[0]));
            service.run(); // run the daemon service
            // init the client keys
            credentials = GenCredential.create();

            logger.info("-----> start test in CallContractTest!");
            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            channelEthereumService.setTimeout(10 * 1000);
            channelEthereumService.setChannelService(service);
            web3j = Web3j.build(channelEthereumService, Integer.parseInt(args[0]));

            if (args.length == 2) {
                String address = args[1];
                CallContract callContract = new CallContract(credentials, web3j);
                System.out.println(
                        "************************ Test call & sendTrandation ************************");
                testSyncCallContract(callContract, address);
                testAsyncCallContract(callContract, address);

                System.out.println("************************ Test decode ************************");
                testDecode(callContract, address);
                System.out.println("Test CallContract successfully.");
            } else {
                System.out.println("Please input group id and contract address.");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.exit(1);
        }

        System.exit(0);
    }

    private static void testSyncCallContract(CallContract callContract, String address) {
        CallResult contractResult;

        contractResult =
                callContract.call(
                        address,
                        "getStringOld",
                        new Utf8String("hello world"),
                        new Int256(10086),
                        new Bool(true));

        List<TypeReference<?>> referencesList =
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {});
        List<Type> returnList1 =
                FunctionReturnDecoder.decode(
                        contractResult.getOutput(), Utils.convert(referencesList));
        System.out.println("call getStringOld: " + (String) returnList1.get(0).getValue());

        TransactionReceipt receipt;
        receipt =
                callContract.sendTransaction(
                        gasPrice,
                        gasLimit,
                        address,
                        "setAndget",
                        new Utf8String("hello world"),
                        new Int256(10086));
        referencesList =
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {});
        List<Type> returnList2 =
                FunctionReturnDecoder.decode(receipt.getOutput(), Utils.convert(referencesList));
        System.out.println(
                "call setAndget: "
                        + (String) returnList2.get(0).getValue()
                        + ", "
                        + (BigInteger) returnList2.get(1).getValue());

        receipt =
                callContract.sendTransaction(
                        address, "setAndget", new Utf8String("hello world"), new Int256(10086));
        referencesList =
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {});
        List<Type> returnList3 =
                FunctionReturnDecoder.decode(receipt.getOutput(), Utils.convert(referencesList));
        System.out.println(
                "default call setAndget: "
                        + (String) returnList3.get(0).getValue()
                        + ", "
                        + (BigInteger) returnList3.get(1).getValue());

        contractResult =
                callContract.call(
                        address,
                        "getArray",
                        new StaticArray2(
                                typeMap(
                                        Arrays.asList(
                                                BigInteger.valueOf(-1), BigInteger.valueOf(2)),
                                        Int16.class)),
                        new DynamicArray(
                                typeMap(
                                        Arrays.asList(BigInteger.valueOf(2), BigInteger.valueOf(2)),
                                        Uint16.class)));
        List<Type> returnList4 =
                callContract.decode(
                        contractResult.getOutput(),
                        new TypeReference<StaticArray2<Int16>>() {},
                        new TypeReference<DynamicArray<Int16>>() {});
        System.out.println(
                "call getArray: "
                        + callContract.convertList((List<Type>) returnList4.get(0).getValue())
                        + ", "
                        + callContract.convertList((List<Type>) returnList4.get(1).getValue()));

        List<List<BigInteger>> dyadicArray = new ArrayList<List<BigInteger>>();
        dyadicArray.add(Arrays.asList(BigInteger.valueOf(-1), BigInteger.valueOf(2)));
        dyadicArray.add(Arrays.asList(BigInteger.valueOf(-1), BigInteger.valueOf(992)));
        byte[] bytes = new byte[] {'a', 'b'};
        contractResult =
                callContract.call(
                        address,
                        "newTest",
                        new StaticArray2(typeMap(dyadicArray, StaticArray2.class, Int256.class)),
                        new DynamicBytes(bytes));
        List<Type> returnList5 =
                callContract.decode(
                        contractResult.getOutput(),
                        new TypeReference<StaticArray2<StaticArray2<Int256>>>() {},
                        new TypeReference<DynamicBytes>() {});
        System.out.println(
                "call newTest: "
                        + callContract.convertListList(
                                (List<StaticArray<Int256>>) returnList5.get(0).getValue())
                        + ", "
                        + new String((byte[]) returnList5.get(1).getValue())
                        + ", "
                        + dyadicArray);
    }

    static class TransactionCallback extends TransactionSucCallback {
        TransactionCallback() {
            try {
                semaphore.acquire(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public void onResponse(TransactionReceipt receipt) {
            this.receipt = receipt;
            semaphore.release();
        }

        public TransactionReceipt receipt;
        public Semaphore semaphore = new Semaphore(1, true);
    };

    private static void testAsyncCallContract(CallContract callContract, String address) {
        TransactionCallback callback = new TransactionCallback();
        TransactionReceipt receipt;

        callContract.asyncSendTransaction(
                callback,
                gasPrice,
                gasLimit,
                address,
                "setAndget",
                new Utf8String("hello world"),
                new Int256(10086));
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getLocalizedMessage());
        }
        receipt = callback.receipt;
        List<TypeReference<?>> referencesList =
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {});
        List<Type> returnList1 =
                FunctionReturnDecoder.decode(receipt.getOutput(), Utils.convert(referencesList));
        System.out.println(
                "async call setAndget: "
                        + (String) returnList1.get(0).getValue()
                        + ", "
                        + (BigInteger) returnList1.get(1).getValue());

        callContract.asyncSendTransaction(
                callback, address, "setAndget", new Utf8String("hello world"), new Int256(10086));
        try {
            callback.semaphore.acquire(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(e.getLocalizedMessage());
        }
        receipt = callback.receipt;
        referencesList =
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {});
        List<Type> returnList2 =
                FunctionReturnDecoder.decode(receipt.getOutput(), Utils.convert(referencesList));
        System.out.println(
                "default async call setAndget: "
                        + (String) returnList2.get(0).getValue()
                        + ", "
                        + (BigInteger) returnList2.get(1).getValue());
    }

    private static void testDecode(CallContract callContract, String address) throws Exception {
        CallResult contractResult;

        contractResult = callContract.call(address, "getInt", new Int256(10086));
        System.out.println("Decode Int: " + callContract.decode(contractResult.getOutput(), "Int"));

        contractResult = callContract.call(address, "getString", new Utf8String("hello world"));
        System.out.println(
                "Decode String: " + callContract.decode(contractResult.getOutput(), "String"));

        contractResult =
                callContract.call(
                        address,
                        "getIntArray",
                        new DynamicArray(
                                typeMap(
                                        Arrays.asList(
                                                BigInteger.valueOf(110), BigInteger.valueOf(120)),
                                        Int256.class)));
        System.out.println(
                "Decode IntArray: " + callContract.decode(contractResult.getOutput(), "IntArray"));

        contractResult =
                callContract.call(
                        address,
                        "getStringArray",
                        new DynamicArray(typeMap(Arrays.asList("hehe", "xixi"), Utf8String.class)));
        System.out.println(
                "Decode StringArray: "
                        + callContract.decode(contractResult.getOutput(), "StringArray"));

        contractResult =
                callContract.call(
                        address,
                        "getAll",
                        new Int256(10086),
                        new DynamicArray(
                                typeMap(
                                        Arrays.asList(
                                                BigInteger.valueOf(110), BigInteger.valueOf(120)),
                                        Int256.class)),
                        new Utf8String("hello world"),
                        new DynamicArray(typeMap(Arrays.asList("hehe", "xixi"), Utf8String.class)));
        System.out.println(
                "Decode All: "
                        + callContract.decode(
                                contractResult.getOutput(), "Int,IntArray,String,StringArray"));
    }
}
