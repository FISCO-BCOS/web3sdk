package org.bcos.contract.tools;

import org.bcos.contract.source.NodeAction;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.abi.datatypes.generated.Uint8;
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
public class NodeActionTools {
	static Resource template;
	static InputStream ksInputStream=null;
	static ObjectMapper mapper=null;
	static NodeInfo nodeInfo=null;
	
    static void processNodeAction(ApplicationContext ctx, NodeAction nodeAction, String[] args)
    {
        try {
            switch (args[1]) {
                case "cancelNode":
                	 if( args.length< 3 ){
                		 if(EncryptType.encryptType == 1)
                     		System.out.println("Please input: gmnode.json");
                     	else
                     		System.out.println("Please input: node.json");
                         break;
                     }
                	 if(EncryptType.encryptType == 1)
                 		System.out.println("gmnode.json="+args[2]);
                 	 else
                 		System.out.println("node.json="+args[2]);
                 	
                	 template = ctx.getResource(args[2]);
                	 ksInputStream =  template.getInputStream();
                	 mapper = new ObjectMapper();
                	 mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                	 nodeInfo = mapper.readValue(ksInputStream, NodeInfo.class);
                	 nodeAction.cancelNode(new Utf8String(nodeInfo.getId())).get();
                	 break;
                case "registerNode":
                    if( args.length< 3 ){
                    	if(EncryptType.encryptType == 1)
                    		System.out.println("Please input: gmnode.json");
                    	else
                    		System.out.println("Please input: node.json");
                        break;
                    }
                    if(EncryptType.encryptType == 1)
                 		System.out.println("gmnode.json="+args[2]);
                 	 else
                 		System.out.println("node.json="+args[2]);
                 	
                    template = ctx.getResource(args[2]);
                    ksInputStream =  template.getInputStream();
                    mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    nodeInfo = mapper.readValue(ksInputStream, NodeInfo.class);
                    nodeAction.registerNode(new Utf8String(nodeInfo.getId()), new Utf8String(nodeInfo.getName()), new Utf8String(nodeInfo.getAgency()), new Utf8String(nodeInfo.getCaHash())).get();
                    break;
                case "all":
                    Uint256 len = nodeAction.getNodeIdsLength().get();
                    System.out.println("NodeIdsLength= "+len.getValue().intValue());
                    for( int i=0;i<len.getValue().intValue();i++){
                        System.out.println("----------node "+i+"---------");
                        Utf8String id = nodeAction.getNodeId(new Uint256(i)).get(); 
                        System.out.println("id="+id);
                        List<Type> node=nodeAction.getNode(id).get();
                        System.out.println("name="+node.get(0));
                        System.out.println("agency="+node.get(1));
                        System.out.println("caHash="+node.get(2));
                        System.out.println("Idx="+((BigInteger)(node.get(3).getValue())).intValue());
                        System.out.println("blocknumber="+((BigInteger)(node.get(4).getValue())).intValue());
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
