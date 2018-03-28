package org.bcos.contract.tools;

import org.bcos.contract.source.NodeAction;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.abi.datatypes.generated.Uint8;
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
    static void processNodeAction(ApplicationContext ctx, NodeAction nodeAction, String[] args)
    {
        try {
            switch (args[1]) {
                case "cancelNode":
                case "registerNode":
                    if( args.length< 3 ){
                        System.out.println("Please input: node.json");
                        break;
                    }
                    System.out.println("node.json="+args[2]);
                    Resource template = ctx.getResource(args[2]);
                    InputStream ksInputStream =  template.getInputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    NodeInfo nodeInfo = mapper.readValue(ksInputStream, NodeInfo.class);
                    if ( args[1].equals("cancelNode"))
                    {
                        nodeAction.cancelNode(new Utf8String(nodeInfo.getId())).get();
                    }
                    else
                    {
                        nodeAction.registerNode(new Utf8String(nodeInfo.getId())
                                ,new Utf8String(nodeInfo.getIp()),new Uint256(nodeInfo.getPort()),new Uint8(nodeInfo.getCategory())
                                ,new Utf8String(nodeInfo.getDesc()),new Utf8String(nodeInfo.getCAhash())
                                ,new Utf8String(nodeInfo.getAgencyinfo()),new Uint256(nodeInfo.getIdx())).get();
                    }
                case "all":
                    Uint256 len = nodeAction.getNodeIdsLength().get();
                    System.out.println("NodeIdsLength= "+len.getValue().intValue());
                    for( int i=0;i<len.getValue().intValue();i++){
                        System.out.println("----------node "+i+"---------");
                        Utf8String id = nodeAction.getNodeId(new Uint256(i)).get();
                        System.out.println("id="+id);
                        List<Type> node=nodeAction.getNode(id).get();
                        System.out.println("ip="+node.get(0));
                        System.out.println("port="+((BigInteger)(node.get(1).getValue())).intValue());
                        System.out.println("category="+((BigInteger)(node.get(2).getValue())).intValue());
                        System.out.println("desc="+node.get(3));
                        System.out.println("CAhash="+node.get(4));
                        System.out.println("agencyinfo="+node.get(5));
                        System.out.println("blocknumber="+((BigInteger)(node.get(2).getValue())).intValue());
                        System.out.println("Idx="+((BigInteger)(nodeAction.getNodeIdx(id).get()).getValue()).intValue());
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
