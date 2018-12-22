package org.fisco.bcos.channel.test.precompile;

import org.fisco.bcos.web3j.cns.Contracts;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class AuthorityTableService {
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static String AuthorityPrecompileAddress = "0x000000000000000000000000000000000001005";

    public void call(String[] args, Web3j web3j, Credentials credentials, int groupId) throws Exception {
        /// get functions
        if (args.length < 1)
            Usage(args);
        String operation = args[1];
        String tableName;
        if (operation.equals("query")) {
        	if (args.length < 3)
                Usage(args);
        	tableName = args[2];
        	List<Authority> result = query(tableName, web3j, credentials);
            System.out.println(result.toString());
            System.exit(0);
        }
        if (args.length < 4)
            Usage(args);
        tableName = args[2];
        String addr = args[3];
        if (operation.equals("add")) {
            add(tableName, addr, web3j, credentials);
            System.out.println("==== add " + "tableName:" +tableName + " address:"+addr+ "of " + groupId + " END ====");
            System.exit(0);
        }
        if (operation.equals("remove")) {
            remove(tableName, addr, web3j, credentials);
            System.out.println("==== remove " + "tableName:" +tableName + " address:"+addr+ " END ====");
            System.exit(0);
        }
    }

    private void Usage(String[] args) {
        System.out.println("Usage:");
        System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager authority add ${tableName} ${address}");
		System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager authority remove ${tableName} ${address}");
		System.out.println("java -cp 'conf/:apps/*:lib/*' org.fisco.bcos.channel.test.PrecompileManager authority query ${tableName}");
        System.exit(0);
    }

    public int add(String tableName, String addr, Web3j web3j, Credentials credentials) throws Exception {
        return add(AuthorityPrecompileAddress, web3j, credentials, tableName, addr);
    }

    public int remove(String tableName, String addr, Web3j web3j, Credentials credentials) throws Exception {
        return remove(AuthorityPrecompileAddress, web3j, credentials, tableName, addr);
    }
    
    public List<Authority> query(String tableName, Web3j web3j, Credentials credentials) throws Exception {
    	return query(AuthorityPrecompileAddress, web3j, credentials, tableName);
    }
	
    private int add(String address, Web3j web3j, Credentials credentials, String tableName, String addr) throws Exception {
    	@SuppressWarnings("deprecation")
		AuthorityTable authority = AuthorityTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = authority.insert(tableName, addr).send();
        int result = 0;
        if("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".equals(receipt.getOutput()))
        {
        	result = -1;
        }
        else
        {
        	result = Integer.valueOf(receipt.getOutput().substring(2), 16).intValue();
        }
		return result;
    }

	private int remove(String address, Web3j web3j, Credentials credentials, String tableName, String addr) throws Exception {
		@SuppressWarnings("deprecation")
		AuthorityTable authority = AuthorityTable.load(address, web3j, credentials, gasPrice, gasLimit);
        TransactionReceipt receipt = authority.remove(tableName, addr).send();
        int result = 0;
        if("0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".equals(receipt.getOutput()))
        {
        	result = -1;
        }
        else
        {
        	result = Integer.valueOf(receipt.getOutput().substring(2), 16).intValue();
        }
		return result;	
	}
	
	private List<Authority> query(String address, Web3j web3j, Credentials credentials, String tableName) throws Exception {
		@SuppressWarnings("deprecation")
		AuthorityTable authority = AuthorityTable.load(address, web3j, credentials, gasPrice, gasLimit);
		String authorityInfo = authority.queryByName(tableName).send();
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
	    return objectMapper.readValue(authorityInfo, objectMapper.getTypeFactory().constructCollectionType(List.class, Authority.class));
    }
}