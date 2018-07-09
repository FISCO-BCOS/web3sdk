package org.bcos.contract.tools;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;

import org.bcos.channel.client.Service;
import org.bcos.web3j.crypto.Hash;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.core.DefaultBlockParameterName;
import org.bcos.web3j.protocol.core.methods.request.Transaction;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bcos.web3j.protocol.exceptions.TransactionTimeoutException;
import org.bcos.web3j.tx.TransactionManager;
import org.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.bcos.web3j.tx.TransactionConstant.callType;

public class UTXOTool  {
	public static BigInteger gasPrice = new BigInteger("30000000");
	public static BigInteger gasLimited = new BigInteger("30000");
	public static BigInteger initialValue = new BigInteger("0");
	
	private Web3j web3j;
	private ToolConf toolConf;
	private TransactionManager manager;
	private static Logger logger = LoggerFactory.getLogger(Service.class);
	
	public UTXOTool(Web3j _web3j, ToolConf _toolConf, TransactionManager _manager)
	{
		web3j = _web3j;
		toolConf = _toolConf;
		manager = _manager;
	}
	
	public void eth_sendTransaction(String str) throws InterruptedException, IOException, TransactionTimeoutException {
		System.out.println("Eth_sendTransaction data:\n"+formatJson(str));
		byte[] byteArray = str.getBytes();
		String data = Numeric.toHexString(byteArray);
		TransactionReceipt receipt = manager.executeTransaction(gasPrice, gasLimited, "", data, initialValue, callType, false);
		String result = receipt.getTransactionHash();
		System.out.println("Result:\n" + result);
	}
	
	public String sha3(String str){
		byte[] input = str.getBytes();
		byte[] hash = Hash.sha3(input);
		return Numeric.toHexString(hash);
	}
	
	public String registerAccount(String account) {
		logger.info("Eth_Call data:RegisterAccount("+account+")");
		String data = "{\"utxotype\":3,\"queryparams\":[{\"account\":\""+account+"\"}]}";
		String result = "";
		try {
			result = formatJson(eth_Call(data));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		logger.info("Result:\n"+result);
		return result;
	}
	
	public String getToken(String tokenKey) {
		logger.info("Eth_Call data:GetToken("+tokenKey+")");
		String data = "{\"utxotype\":4,\"queryparams\":[{\"tokenkey\":\""+tokenKey+"\"}]}";
		String result = "";
		try {
			result = formatJson(eth_Call(data));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		logger.info("Result:\n"+result);
		return result;
	}
	
	public String getTx(String txKey) {
		logger.info("Eth_Call data:GetTx("+txKey+")");
		String data = "{\"utxotype\":5,\"queryparams\":[{\"txkey\":\""+txKey+"\"}]}";
		String result = "";
		try {
			result = formatJson(eth_Call(data));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		logger.info("Result:\n"+result);
		return result;
	}
	
	public String getVault(String account, String tokenType) {
		logger.info("Eth_Call data:GetVault("+account+","+tokenType+")");
		String result = QueryPaged("GetVault", account, tokenType);
		logger.info("Result:\n"+formatJson(result));
		return formatJson(result);
	}
	
	public String selectTokens(String account, String value) {
		logger.info("Eth_Call data:SelectTokens("+account+","+value+")");
		String result = QueryPaged("SelectTokens", account, value);
		logger.info("Result:\n"+formatJson(result));
		return formatJson(result);
	}
	
	public String tokenTracking(String tokenkey) {
		logger.info("Eth_Call data:TokenTracking("+tokenkey+")");
		String result = QueryPaged("TokenTracking", tokenkey, null);
		logger.info("Result:\n"+formatJson(result));
		return formatJson(result);
	}
	
	public String getBalance(String account) {
		logger.info("Eth_Call data:GetBalance("+account+")");
		String data = "{\"utxotype\":9,\"queryparams\":[{\"account\":\""+account+"\"}]}";
		String result = "";
		try {
			result = eth_Call(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		logger.info("Result:\n"+result);
		return result;
	}
	
	private String eth_Call(String data) throws InterruptedException, ExecutionException {
		String account = toolConf.getAccount();
		org.bcos.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
				Transaction.createEthCallTransaction(account, "", data, callType, false), 
				DefaultBlockParameterName.LATEST).sendAsync().get();
		return ethCall.getValue();
	}
	
	private String formatJson(String str) {
		if (str == null || str == "") {
			return "";
		}
        
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indentationTime = 0;
        
		for (int i = 0; i < str.length(); i++) {
			last = current;
			current = str.charAt(i);

			switch (current) {
				case '{':
				case '[':
				{
					sb.append(current);
					sb.append('\n');
					addBlankSpace(sb, ++indentationTime);
					break;
				}
				case '}':
				case ']':
				{
					sb.append('\n');
					addBlankSpace(sb, --indentationTime);
					sb.append(current);
					break;
				}
				case ',':
				{
					sb.append(current);
					// 非转移"\,"
					if (last != '\\') {
						sb.append('\n');
						addBlankSpace(sb, indentationTime);
					}
					break;
				}
				default:
				{
					sb.append(current);
				}
			}
		}
 
		return StringEscapeUtils.unescapeJava(sb.toString());
	}
 
	private void addBlankSpace(StringBuilder sb, int indent) {
		for (int i = 0; i < indent * 2; i++) {
			sb.append(' ');
		}
	}
    
	private String QueryPaged(String utxotype, String inparam1, String inparam2)
	{
		int code = 0;
		String msg = "Success.";
    	
		int begin = 0;
		int end = -1;
		int total = 0;
		int totalTokenValue = 0;
		String param = "";
		JSONArray array = new JSONArray();
    	
		while (end < total - 1 || end == -1) {
			begin = end + 1;
			if (utxotype == "GetVault") {
				param = "{\"utxotype\":6,\"queryparams\":[{\"account\":\""+inparam1+"\",\"value\":\""+inparam2+"\",\"begin\":\""+begin+"\"}]}";
			}
			else if (utxotype == "SelectTokens") {
				param = "{\"utxotype\":7,\"queryparams\":[{\"account\":\""+inparam1+"\",\"value\":\""+inparam2+"\",\"begin\":\""+begin+"\"}]}";
			}
			else if (utxotype == "TokenTracking") {
				param = "{\"utxotype\":8,\"queryparams\":[{\"tokenkey\":\""+inparam1+"\",\"begin\":\""+begin+"\"}]}";
			}
    		
			String result = "";
			try {
				result = StringEscapeUtils.unescapeJava(eth_Call(param));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} 
			if (result == "") {
				break;
			}
    		
			try {
				if (utxotype == "TokenTracking")
				{
					// Specific format adjustment
					result = result.replaceAll("\"\\{", "\\{");
					result = result.replaceAll("\\}\"", "\\}");
				}
				JSONObject jsStr = JSONObject.parseObject(result);
				code = jsStr.getIntValue("code");
				if (code != 0) {
					msg = jsStr.getString("msg");
					break;
				}
				end = jsStr.getIntValue("end");
        		JSONArray content = jsStr.getJSONArray("data");
        		if (content != null && 
        			content.size() > 0) {  
                	for (int i = 0; i < content.size(); i++) {  
                    	array.add(content.getString(i));
                    }  
                }  
        		if (total == 0) {
        			total = jsStr.getIntValue("total");
        		}
        		if (jsStr.getIntValue("totalTokenValue") > 0)
        		{
        			totalTokenValue = jsStr.getIntValue("totalTokenValue");
        		}
			} catch (NumberFormatException e) {
    			e.printStackTrace();
    		}
		}
    	
		JSONObject ret = new JSONObject();
		ret.put("code", code);
		ret.put("msg", msg);
		ret.put("data", array);
        if (totalTokenValue > 0) {
        	ret.put("totalTokenValue", totalTokenValue);
        }
        
        return ret.toString();
	}
}
