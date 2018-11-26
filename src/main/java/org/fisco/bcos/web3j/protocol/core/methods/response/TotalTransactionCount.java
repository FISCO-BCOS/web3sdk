package org.fisco.bcos.web3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.fisco.bcos.web3j.protocol.core.Response;
import org.fisco.bcos.web3j.utils.Numeric;

public class TotalTransactionCount extends Response<TotalTransactionCount.TransactionCount>{
	public TransactionCount getTotalTransactionCount() {
        return getResult();
    }
	
	public class TransactionCount {
		private String count;
		private String number;
		public TransactionCount() {
			
		}
		public TransactionCount(String count, String number) {
			super();
			this.count = count;
			this.number = number;
		}
		public String getCountRaw() {
			return count;
		}
		public BigInteger getCount() {
			return Numeric.decodeQuantity(count);
		}
		public void setCount(String count) {
			this.count = count;
		}
		public String getNumberRaw() {
			return number;
		}
		public BigInteger getNumber() {
			return Numeric.decodeQuantity(number);
		}
		public void setNumber(String number) {
			this.number = number;
		}
		
	}
}
