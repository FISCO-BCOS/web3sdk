package org.bcos.contract.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bcos.contract.source.ContractAbiMgr;
import org.bcos.contract.source.ContractBase;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CNSCmdTool {
	
	static Logger logger = LoggerFactory.getLogger(CNSCmdTool.class);
	
	/**
	 * 
	 */
	public static void usage() {
		System.out.println(" CnsAction Usage: ");
		System.out.println("\t CNSAction get    contract version");
		System.out.println("\t CNSAction add    contract contract.abi contract.address");
		System.out.println("\t CNSAction update contract contract.abi contract.address");
		System.out.println("\t CNSAction list [simple]");
		System.out.println("\t CNSAction historylist contract [version] [simple]");
		System.out.println("\t CNSAction reset contract [version] index");
	}
	
	/**
	 * @param abiPath
	 * @return
	 * @throws IOException 
	 */
	public static String getAbi(String abiPath) throws IOException {
		if(!abiPath.endsWith(".abi")) {
			abiPath += ".abi";
		}
		File f = null;
		FileInputStream in = null;
		try {
			f = new File(abiPath);
			Long length = f.length();
			byte[] bs = new byte[length.intValue()];
			in = new FileInputStream(f);
			int rdsize = in.read(bs);
			
			logger.debug("read size => " + rdsize);
			
			String s = new String(bs);
			logger.debug("read abi file content ,path = " + abiPath  + " ,abi = " + s);
			return s.trim();
			
		} catch (IOException e) {
			logger.error("read abi file content failed, abi path = " + abiPath);
			throw e;
		} finally {
			if(in != null) {
				in.close();
			}
		}
	}
	
	/**
	 * @param addrPath
	 * @return
	 * @throws IOException 
	 */
	public static String getAddr(String addrPath) throws IOException {
		if(!addrPath.endsWith(".address")) {
			addrPath += ".address";
		}
		
		File f = null;
		FileInputStream in = null;
		try {
			f = new File(addrPath);
			Long length = f.length();
			byte[] bs = new byte[length.intValue()];
			in = new FileInputStream(f);
			int rdsize = in.read(bs);
			
			logger.debug("read size => " + rdsize);
			
			String s = new String(bs);
			logger.debug("read address file content ,path = " + addrPath  + " ,address = " + s);
			return s.trim();
			
		} catch (IOException e) {
			logger.error("read address file content failed, addr path = " + addrPath);
			throw e;
		} finally {
			if(in != null) {
				in.close();
			}
		}
	}
	
	/**
	 * @param abi
	 * @return
	 */
	public static boolean isContractHasVersion(String abi) throws Exception {
		try {
			JSONArray a = JSON.parseArray(abi);
			for(int i=0;i<a.size();++i) {
				JSONObject o = a.getJSONObject(i);
				//if abi has function member getVersion , then maybe the contract has version
				if(o.containsKey("name") && o.getString("name").equals("getVersion")) {
					logger.trace("abi contains getVersion function");
					return true;
				}
			}
			
			return false;
		} catch (Exception e) {
			logger.error("invalid abi format, json parser failed abi = " + abi);
			throw e;
		}
	}
	
	/**
	 * @param address
	 * @param abi
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static String getCNSVersion(String address, String abi, Object[] param) throws Exception {
		String version = "";
		if(isContractHasVersion(abi)) {
			ContractBase base = ContractBase.load(address, (Web3j)param[0], (Credentials)param[1], (BigInteger)param[2], (BigInteger)param[3]);
			//get contract version
			version = base.getVersion().get(10, TimeUnit.SECONDS).getValue();
			logger.debug("ContractBase load ,get version address = " + address + " ,version = " + version);
		}
		return version;
	}
	
	/**
	 * @param abiMgr
	 * @param args
	 */
	public static void processCns(ContractAbiMgr abiMgr, String[] args, Object[] param) {
		if (args.length < 2) {
			System.out.println("cns invalid args length.");
			usage();
			return;
		}
		String opr = args[1];

		switch (opr) {
		case "add": {
			if(args.length < 5) {
				System.out.println("cns add operation, invalid args length.");
				usage();
				return;
			}
			
			try {
				String contract = args[2];
				String abi = getAbi(args[3]);
				String address = getAddr(args[4]);
				
				String version = getCNSVersion(address, abi, param);
				
				logger.debug("cns add operation , contract = " + contract + " ,address = " + address + " ,abi = " + abi);
				
				CNSBaseOpr.addBase(abiMgr, contract, version, address, abi);
				
				System.out.println("cns add operation success.");
				
			} catch (CNSException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("cns add operation failed, msg = " + e.getMessage());
			}
			
			break;
		}
		case "update": {
			if(args.length < 5) {
				System.out.println("cns update operation, invalid args length.");
				usage();
				return;
			}
			
			try {
				String contract = args[2];
				String abi = getAbi(args[3]);
				String address = getAddr(args[4]);
				
				String version = getCNSVersion(address, abi, param);
				
				if(!CNSBaseOpr.cnsIsExist(abiMgr, contract, version)) {
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
				
				logger.debug("cns update operation , contract = " + contract + " ,address = " + address + " ,abi = " + abi);
				
				CNSBaseOpr.updateBase(abiMgr, contract, version, address, abi);
				
				System.out.println("cns update operation success.");
				
			} catch (CNSException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("cns update operation failed, msg = " + e.getMessage());
			}
			
			break;
		}
		case "get": {
			if(args.length < 3) {
				System.out.println("cns get operation, invalid args length.");
				usage();
				return;
			}
			try {
				String contract = "";
				String version = "";
				
				contract = args[2];
				if(args.length > 3) {
					version = args[3];
				}
				logger.debug("cns get operation , contract = " + contract  + " ,version = " + version);
				
				CNSEle e = CNSBaseOpr.get(abiMgr, contract, version);
				
				if(e.getAbi().equals("") && (0 == Integer.parseInt(e.getTime()))) {
					System.out.println(" ====> contract => " + contract + " ,version => " + version + " not exist.");
				} else {
					System.out.println(" ====> contract => " + contract + " ,version => " + version);
					System.out.println("\t contract    = " + e.getContract());
					System.out.println("\t version     = " + e.getVersion());
					System.out.println("\t address     = " + e.getAddress());
					System.out.println("\t blocknumber = " + e.getBk());
					System.out.println("\t timestamp   = " + e.getTime());
					System.out.println("\t abi         = " + e.getAbi());
					
					System.out.println("cns get operation success.");
				}
				
			} catch (CNSException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("cns get operation failed, msg = " + e.getMessage());
			}
			break;
		}
		case "list": {
			boolean simple = false;
			if((args.length > 2) && args[2].equals("simple")) {
				simple = true;
			}
			
			try {
				List<CNSEle> l = CNSBaseOpr.list(abiMgr);
				System.out.println(" cns total count => " + l.size());
				for (int i = 0; i < l.size(); i++) {
					CNSEle e = l.get(i);
					if(simple) {
						System.out.println("\t" + i + ". contract = " + e.getContract() + " ,version = " + e.getVersion());
					} else {
						System.out.println(" ====> cns list index = " + i + " <==== ");
						System.out.println("\t contract    = " + e.getContract());
						System.out.println("\t version     = " + e.getVersion());
						System.out.println("\t address     = " + e.getAddress());
						System.out.println("\t blocknumber = " + e.getBk());
						System.out.println("\t timestamp   = " + e.getTime());
						System.out.println("\t abi         = " + e.getAbi());
					}
				}
				
				System.out.println("cns list operation success.");
				
			} catch (CNSException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("cns list operation failed, msg = " + e.getMessage());
			}
			
			break;
		}
		case "reset": {
			if(args.length < 3) {
				System.out.println("cns reset operation, invalid args length.");
				usage();
				return;
			}
			
			String contract = args[2];
			String version = "";
			int index = 0;
			if(args.length > 4) {
				version = args[3];
				index = Integer.parseInt(args[4]);
			} else {
				index = Integer.parseInt(args[3]);
			}
			
			try {
				CNSBaseOpr.cnsReset(abiMgr, contract, version, index);
				
				System.out.println("cns reset operation success.");
				
			}  catch (CNSException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("cns reset operation failed, msg = " + e.getMessage());
			}
			
			break;
		}
		case "historylist": {
			
			if(args.length < 2) {
				System.out.println("cns historylist operation, invalid args length.");
				usage();
				return;
			}
			
			boolean simple = false;
			String contract = args[2];
			String version = "";
			if(args.length > 4) {
				if(args[4].equals("simple")) {
					simple = true;
				}
				version = args[3];
			} else if (args.length > 3) {
				if(args[3].equals("simple")) {
					simple = true;
				} else {
					version = args[3];
				}
			}
			
			logger.debug(" cns history list operation , contract = " + contract + " ,version = " + version + " ,simple = " + simple);
			
			try {
				List<CNSEle> l = CNSBaseOpr.cnsHistoryList(abiMgr, contract, version);
				System.out.println(" cns history total count => " + l.size());
				for (int i = 0; i < l.size(); i++) {
					CNSEle e = l.get(i);
					if(simple) {
						System.out.println("\t" + i + ". contract = " + e.getContract() + " ,version = " + e.getAddress());
					} else {
						System.out.println(" ====> cns list index = " + i + " <==== ");
						System.out.println("\t contract    = " + e.getContract());
						System.out.println("\t version     = " + e.getVersion());
						System.out.println("\t address     = " + e.getAddress());
						System.out.println("\t blocknumber = " + e.getBk());
						System.out.println("\t timestamp   = " + e.getTime());
						System.out.println("\t abi         = " + e.getAbi());
					}
				}
				
				System.out.println("cns historylist operation success.");
				
			} catch (CNSException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("cns historylist operation failed, msg = " + e.getMessage());
			}
			
			break;
		}
		default: {
			System.out.println("CNSAction unkown operation.");
			usage();
			return;
		}
		}
	}
}
