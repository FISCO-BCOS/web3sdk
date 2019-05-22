package test;


import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;

import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.Utils;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.BytesType;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Uint;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray5;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.protocol.core.methods.request.Transaction;
import org.fisco.bcos.web3j.protocol.core.methods.response.Call;
import org.fisco.bcos.web3j.utils.Numeric;

public class HelloWorld {

	public static void test() throws NoSuchFieldException, SecurityException {
		
		 Function function = new Function("get", 
	                Arrays.<Type>asList(), 
	                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<DynamicArray<Utf8String>>>() {} ,new TypeReference<DynamicArray<Int256>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}));
		 
	    //String encodedFunction = FunctionEncoder.encode(function);

	    String value = new String("0x0000000000000000000000000000000000000000000000000000000000000060000000000000000000000000000000000000000000000000000000000000014000000000000000000000000000000000000000000000000000000000000001a000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000056672756974000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000566727569740000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000a0000000000000000000000000000000000000000000000000000000000000003231323334353637383930313233343536373839303132333435363738393031323334353637383930313233343536373839300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000a3132333131313131313100000000000000000000000000000000000000000000");
	    
	    FunctionReturnDecoder.decode(value, function.getOutputParameters());
	}
	
	  @SuppressWarnings("unchecked")
	  static <T extends Type> void getParameterizedTypeFromArray(TypeReference typeReference, int round)
	      throws ClassNotFoundException {

	    java.lang.reflect.Type type = typeReference.getType();
	    
	    System.out.println("type1 = " + type.getTypeName());
	    
	    java.lang.reflect.Type[] types = ((ParameterizedType) type).getActualTypeArguments();

	    String parameterizedTypeName = types[0].getTypeName();
	    
	    System.out.println("type2 => " + parameterizedTypeName);
	    
	    types = ((ParameterizedType) types[0]).getActualTypeArguments();

	    parameterizedTypeName = types[0].getTypeName();
	    
	    System.out.println("type3 => " + parameterizedTypeName);
	  
	  }
	  
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, ClassNotFoundException {
		// getParameterizedTypeFromArray(new TypeReference<DynamicArray<DynamicArray<Utf8String>>>() {}, 0);
		
		//System.out.print(dynamicType(new TypeReference<StaticArray<StaticArray<Uint256>>>() {}));
		//test0();
		
		final Function function = new Function("", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<StaticArray2<Utf8String>>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<DynamicArray<Int256>>() {}));
		System.out.println(Utils.getOffset(new TypeReference<Utf8String>() {}.getType()));
		System.out.println(Utils.getOffset(new TypeReference<StaticArray5<Uint256>>() {}.getType()));
		System.out.println(Utils.getOffset(new TypeReference<DynamicArray<Uint256>>() {}.getType()));
	}
}
