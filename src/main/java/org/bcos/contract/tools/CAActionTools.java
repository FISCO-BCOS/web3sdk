package org.bcos.contract.tools;

import org.bcos.contract.source.CAAction;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.abi.datatypes.generated.Uint8;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

/**
 * Created by mingzhenliu on 2018/3/13.
 */
public class CAActionTools {
    static  void processCAAction(ApplicationContext ctx, CAAction caActions, String[]args)
    {
        try {
            switch (args[1]) {
                case "update":
                case "updateStatus":
                    if( args.length< 3 ){
                        System.out.println("Please input: ca.json");
                        break;
                    }
                    System.out.println("ca.json="+args[2]);
                    Resource template = ctx.getResource(args[2]);
                    InputStream ksInputStream =  template.getInputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    CaInfo caInfo = mapper.readValue(ksInputStream, CaInfo.class);
                    if ( args[1].equals("update")) {
                        caActions.update(new Utf8String(caInfo.getHash())
                                ,new Utf8String(caInfo.getPubkey()),new Utf8String(caInfo.getOrgname())
                                ,new Uint256(caInfo.getNotbefore()), new Uint256(caInfo.getNotafter())
                                ,new Uint8(caInfo.getStatus()),new Utf8String(caInfo.getWhitelist())
                                ,new Utf8String(caInfo.getBlacklist())).get();
                    }
                    else{
                        caActions.updateStatus(new Utf8String(caInfo.getHash()),
                                new Uint8(caInfo.getStatus())).get();
                    }
                case "all":
                    Uint256 len = caActions.getHashsLength().get();
                    System.out.println("HashsLength= "+len);
                    for( int i=0;i<len.getValue().intValue();i++){
                        System.out.println("----------CA "+i+"---------");
                        Utf8String hash = caActions.getHash(new Uint256(i)).get();
                        System.out.println("hash="+hash);
                        List<Type> ca = caActions.get(hash).get();

                        System.out.println("pubkey="+ca.get(1));
                        System.out.println("orgname="+ca.get(2));
                        System.out.println("notbefore="+ca.get(3));
                        System.out.println("notafter="+ca.get(4));
                        System.out.println("status="+ca.get(5));
                        System.out.println("blocknumber="+ca.get(6));

                        List<Type> iplist=caActions.getIp(hash).get();
                        System.out.println("whitelist="+iplist.get(0));
                        System.out.println("blacklist="+iplist.get(1));
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
