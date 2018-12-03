package org.fisco.bcos.web3j.console;

import org.fisco.bcos.web3j.tx.Contract;

public class ContractClassFactory {
	
	public static Class<?>  getContractClass(String contractName) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		return (Class<?>) Class.forName(contractName); 
	}
}
