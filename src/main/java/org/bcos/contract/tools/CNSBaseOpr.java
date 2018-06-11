package org.bcos.contract.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bcos.contract.source.ContractAbiMgr;
import org.bcos.contract.source.ContractAbiMgr.AddAbiEventResponse;
import org.bcos.contract.source.ContractAbiMgr.UpdateAbiEventResponse;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CNSBaseOpr {
	
	static Logger logger = LoggerFactory.getLogger(CNSBaseOpr.class);
	
	/**
	 * @param s
	 * @return
	 */
	public static boolean StringEmpty(String s) {
		return (s == null) || (s.equals(""));
	}
	/**
	 * @param contract
	 * @param version
	 * @return 
	 */
	public static String toCNSName(String contract, String version) {
		
		if(StringEmpty(contract)) {
			throw new IllegalArgumentException("toCNSName , invalid contract parameter.");
		}
		
		if(StringEmpty(version)) {
			return contract;
		}
		
		return contract + "/" + version;
	}
	
	/**
	 * @param contract
	 * @param version
	 * @param address
	 * @param abi
	 * @exception IllegalArgumentException
	 */
	public static void cnsParamCheck(String contract, String version, String address, String abi) {
		if(StringEmpty(contract)) {
			throw new IllegalArgumentException("invalid contract parameter.");
		}
		
		if(StringEmpty(address)) {
			throw new IllegalArgumentException("invalid address parameter.");
		}
		
		if(StringEmpty(abi)) {
			throw new IllegalArgumentException("invalid abi parameter.");
		}
	}
	
	/**
	 * @param abiMgr
	 * @param contractName
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public static boolean cnsIsExist(ContractAbiMgr abiMgr, String contractName, String version) throws Exception {
		CNSEle e = get(abiMgr, contractName, version);
		String abi = e.getAbi();
		String time = e.getTime();
		return !(StringEmpty(abi) &&  (0 == Integer.parseInt(time)));
	}
	
	/**
	 * @param abiMgr
	 * @param contractName
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public static CNSEle get(ContractAbiMgr abiMgr, String contractName, String version) throws Exception {

		try {
			List<Type> lr = abiMgr.getAll(new Utf8String(toCNSName(contractName, version))).get(10, TimeUnit.SECONDS);
			if (lr == null) {
				throw new RuntimeException("cns get null, contract = " + contractName + " ,version = " + version);
			}
			
			CNSEle e = new CNSEle(lr.get(0).toString(), lr.get(1).toString(), lr.get(2).toString(),
					lr.get(3).toString(), lr.get(4).getValue().toString(), lr.get(5).getValue().toString());
			
			logger.debug("cns get operation , cns = " + toCNSName(contractName, version) + " ,detail = " + e.toString());
			
			return e;

		} catch (InterruptedException | ExecutionException e) {
			throw e;
		}
	}

	/**
	 * @param abiMgr
	 * @throws Exception 
	 */
	public static List<CNSEle> list(ContractAbiMgr abiMgr) throws Exception {

		try {
			Uint256 r = abiMgr.getAbiCount().get(10, TimeUnit.SECONDS);
			if (r == null) {
				throw new RuntimeException("cns get abi count null.");
			}
			
			List<CNSEle> allEle = new ArrayList<CNSEle>();
			
			for (int i = 0; i < r.getValue().intValue(); i++) {
				List<Type> lr = abiMgr.getAllByIndex(new Uint256(i)).get();
				if (lr == null) {
					throw new RuntimeException("cns getByIndex null, total count = " + r.getValue().intValue() + " index = " + i);
				}
				
				CNSEle e = new CNSEle(lr.get(0).toString(), lr.get(1).toString(), lr.get(2).toString(),
						lr.get(3).toString(), lr.get(4).getValue().toString(), lr.get(5).getValue().toString());
				
				logger.debug("cns list operation , index = " + i + " ,detail = " + e.toString());
				
				allEle.add(e);
			}
			
			return allEle;

		} catch (InterruptedException | ExecutionException e) {
			throw e;
		}
	}
	
	/**
	 * @param abiMgr
	 * @param contract
	 * @param version
	 * @param address
	 * @param abi
	 * @throws Exception 
	 */
	public static void addBase(ContractAbiMgr abiMgr, String contract, String version, String address, String abi)
			throws Exception {
		abi = abi.trim();
		address = address.trim();
		cnsParamCheck(contract, version, address, abi); // parameter check
		if (cnsIsExist(abiMgr, contract, version)) {
			throw new CNSException("cns already register, contract = " + contract + " ,version = " + version);
		}
		
		TransactionReceipt receipt = abiMgr.addAbi(new Utf8String(toCNSName(contract, version)),
				new Utf8String(contract), new Utf8String(version), new Utf8String(abi), new Address(address))
				.get(10, TimeUnit.SECONDS);
		
		List<AddAbiEventResponse> resp = ContractAbiMgr.getAddAbiEvents(receipt);
		if(resp == null) {
			throw new RuntimeException("cns addBase event resp null, cns = " + toCNSName(contract, version));
		}
		
		if(resp.isEmpty()) {
			throw new RuntimeException("cns addBase event resp empty, cns = " + toCNSName(contract, version));
		}
		
		logger.debug("cns addBase event log, contract = " + resp.get(0).contractname + " ,version = " + resp.get(0).version + " ,addr = " + resp.get(0).addr
				+ " ,bk = " + resp.get(0).blocknumber + " ,tt = " + resp.get(0).timestamp + " ,abi = " + resp.get(0).abi);
		
		return;
	}
	
	/**
	 * @param abiMgr
	 * @param contract
	 * @param version
	 * @param address
	 * @param abi
	 * @throws Exception
	 */
	public static void updateBase(ContractAbiMgr abiMgr, String contract, String version, String address, String abi) throws Exception {
	    abi = abi.trim();
		address = address.trim();
		cnsParamCheck(contract, version, address, abi); // parameter check
		
		/*
		if(!cnsIsExist(abiMgr, contract, version)) {
			throw new CNSException("cns update failed, => contract = " + contract + " ,version = " + version + " not exist, If you need please add it first.");
		}
		
		System.out.println(" ====> Are you sure update the cns of the contract ?(Y/N) ");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
		String s = br.readLine().trim(); 
		br.close();
		if (!s.equals("Y")) {
			System.out.println("nothing to be done, if you want update operation , you must input 'Y'!");
			return ;
		}
		*/
		
		TransactionReceipt receipt = abiMgr.updateAbi(new Utf8String(toCNSName(contract, version)), new Utf8String(contract), new Utf8String(version),  new Utf8String(abi), new Address(address)).get(10, TimeUnit.SECONDS);
		List<UpdateAbiEventResponse> resp = ContractAbiMgr.getUpdateAbiEvents(receipt);
		if(resp == null ) {
			throw new RuntimeException("cns update resp null, cnsName = " + toCNSName(contract, version));
		}
		
		if(resp.isEmpty()) {
			throw new RuntimeException("cns udpate resp empty, cnsName = " + toCNSName(contract, version));
		}
		
		logger.debug("cns updateBase event log, contract = " + resp.get(0).contractname + " ,version = " + resp.get(0).version + " ,addr = " + resp.get(0).addr
				+ " ,bk = " + resp.get(0).blocknumber + " ,tt = " + resp.get(0).timestamp + " ,abi = " + resp.get(0).abi);
	}
	
	/**
	 * @param abiMgr
	 * @param contract
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public static List<CNSEle> cnsHistoryList(ContractAbiMgr abiMgr, String contract, String version) throws Exception {
		 
			try {
				
				List<CNSEle> allEle = new ArrayList<CNSEle>();
				
				Uint256 r = abiMgr.getHistoryAbiC(new Utf8String(toCNSName(contract,version))).get(10, TimeUnit.SECONDS);
				if (r == null) {
					throw new CNSException("cns get history count null, cnsName = " + toCNSName(contract, version));
				}
				
				for(int i=0;i<r.getValue().intValue();++i) {
					List<Type> lr = abiMgr.getHistoryAllByIndex(new Utf8String(toCNSName(contract,version)), new Uint256(i)).get(10, TimeUnit.SECONDS);
					if (lr == null) {
						throw new RuntimeException("cns getHistoryAllByIndex null, cnsName = " + toCNSName(contract,version) + " index = " + i);
					}
					
					CNSEle e = new CNSEle(lr.get(0).toString(), lr.get(1).toString(), lr.get(2).toString(),
							lr.get(3).toString(), lr.get(4).getValue().toString(), lr.get(5).getValue().toString());
					
					logger.debug("cns history list operation , index = " + i + " ,detail = " + e.toString());
					
					allEle.add(e);
				}
				
				return allEle;
				
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				throw e;
			}
	}
	
	/**
	 * @param abiMgr
	 * @param contract
	 * @param version
	 * @param index
	 * @throws Exception
	 */
	public static void cnsReset(ContractAbiMgr abiMgr, String contract, String version, int index) throws Exception {
		Uint256 r = abiMgr.getHistoryAbiC(new Utf8String(toCNSName(contract,version))).get(10, TimeUnit.SECONDS);
		if(r == null) {
			throw new RuntimeException("cns get history count null, cnsName = " + toCNSName(contract, version));
		}
		
		if(index >= r.getValue().intValue()) {
			throw new CNSException("index out of range , index = " + index + " ,total history count = " + r.getValue().intValue());
		}
		
		List<Type> lr = abiMgr.getHistoryAllByIndex(new Utf8String(toCNSName(contract,version)), new Uint256(index)).get(10, TimeUnit.SECONDS);
		if (lr == null) {
			throw new RuntimeException("cns getHistoryAllByIndex null, cnsName = " + toCNSName(contract,version) + " index = " + index);
		}
		
		CNSEle e = new CNSEle(lr.get(0).toString(), lr.get(1).toString(), lr.get(2).toString(),
				lr.get(3).toString(), lr.get(4).getValue().toString(), lr.get(5).getValue().toString());
		
		logger.debug("cns reset get history index , index = " + index + " ,detail = " + e.toString());
		
		updateBase(abiMgr, e.getContract(), e.getVersion(), e.getAddress(), e.getAbi());
	}
}
