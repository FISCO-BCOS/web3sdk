package org.bcos.web3j.crypto.sm2.util;

public class ByteUtils {
	
	private ByteUtils(){};


	/**
	 * 将两个byte数组拼接起来返回拼接后的byte数组
	 * @param bytes1
	 * @param bytes2
	 * @return
	 * @date 2015-2-10
	 * @author
	 */
	public static byte[] addByteArray(byte[] bytes1,byte[] bytes2){
		byte[] temp;
		if(bytes1 ==null){
			temp = new byte[bytes2.length];
			System.arraycopy(bytes2,0,temp,0,bytes2.length);
		}else if(bytes2 ==null){
			temp = new byte[bytes1.length];
			System.arraycopy(bytes1,0,temp,0,bytes1.length);
		}else{
			temp = new byte[bytes1.length+bytes2.length];
			System.arraycopy(bytes1,0,temp,0,bytes1.length);
			System.arraycopy(bytes2,0,temp,bytes1.length,bytes2.length);
		}
		return temp;
	}

	/**
	 * 复制byte数组，返回复制的byte数组
	 * @param bytes
	 * @return
	 * @date 2015年12月3日
	 * @author
	 */
	public static byte[] copyBytes(byte[] bytes){
		byte[] temp;
		temp = new byte[bytes.length];
		System.arraycopy(bytes,0,temp,0,bytes.length);
		return temp;
	}
	
	/**
	 * 截取byte数组
	 * @param bytes
	 * @param pos
	 * @param length
	 * @return
	 * @author fisco-bcos
	 */
	public static byte[] subByteArray(byte[] bytes,int pos,int length){
		byte[] retbs=new byte[length];
		System.arraycopy(bytes, pos, retbs, 0, length);
		return retbs;
	}
	
	/**
	 * 翻转byte数组
	 * @param bytes
	 * @return
	 * @date 2015年11月4日
	 * @author
	 */
	public static byte[] reverse(byte[] bytes){
		byte[] ret=new byte[bytes.length];
		
		int index=ret.length-1;
		for (int i = 0; i < bytes.length; i++) {
			ret[index]=bytes[i];
			index--;
		}
		
		return ret;
	}
	
	/**
	 * 将小端字节顺序转为大端字节顺序
	 * @param bytes
	 * @return
	 * @date 2015年11月4日
	 * @author
	 */
	public static byte[] littleToBigEndian(byte[] bytes){
		return reverse(bytes);
	}
}
