package org.bcos.contract.tools;

import org.bcos.contract.source.CAAction;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.EncryptType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by mingzhenliu on 2018/3/13.
 */
public class CAActionTools {
	static Resource template=null;
	static InputStream ksInputStream=null;
	static ObjectMapper mapper=null;
	static CaInfo caInfo; 
    static  void processCAAction(ApplicationContext ctx, CAAction caActions, String[]args)
    {
        try {
            switch (args[1]) {
                case "add":
                	if( args.length< 3 ){
                		if(EncryptType.encryptType == 1)
                    		System.out.println("Please input: gmnode.ca");
                    	else
                    		System.out.println("Please input: node.ca");
                        break;
                    }
                	if(EncryptType.encryptType == 1)
                		System.out.println("gmnode.ca="+args[2]);
                	else
                		System.out.println("node.ca="+args[2]);
                	
                	template = ctx.getResource(args[2]);
                    ksInputStream =  template.getInputStream();
                    mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    caInfo = mapper.readValue(ksInputStream, CaInfo.class);
                    caActions.add(new Utf8String(caInfo.getSerial()), new Utf8String(caInfo.getPubkey()), new Utf8String(caInfo.getName())).get();
                	break;
                case "remove":
                    if( args.length< 3 ){
                    	if(EncryptType.encryptType == 1)
                    		System.out.println("Please input: gmnode.ca");
                    	else
                    		System.out.println("Please input: node.ca");
                        break;
                    }
                    if(EncryptType.encryptType == 1)
                		System.out.println("gmnode.ca="+args[2]);
                	else
                		System.out.println("node.ca="+args[2]);
                	
                    template = ctx.getResource(args[2]);
                    ksInputStream =  template.getInputStream();
                    mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    caInfo = mapper.readValue(ksInputStream, CaInfo.class);
                    caActions.remove(new Utf8String(caInfo.getSerial())).get();
                    break;
                case "all":
                    Uint256 len = caActions.getHashsLength().get();
                    System.out.println("HashsLength= "+len.getValue().intValue());
                    for( int i=0;i<len.getValue().intValue();i++){
                        System.out.println("----------CA "+i+"---------");
                        Utf8String hash = caActions.getHash(new Uint256(i)).get();
                        System.out.println("hash="+hash);
                        List<Type> ca = caActions.get(hash).get();
                        System.out.println("serial="+ca.get(0));
                        System.out.println("pubkey="+ca.get(1));
                        System.out.println("name="+ca.get(2));
                        System.out.println("blocknumber="+((BigInteger)(ca.get(3).getValue())).intValue());
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
