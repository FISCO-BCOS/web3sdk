package org.bcos.contract.tools;

import org.bcos.contract.source.ConfigAction;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;

import java.util.List;

/**
 * Created by mingzhenliu on 2018/3/13.
 */
public class ConfigActionTools {
    static void processConfigAction(ConfigAction configAction, String[]args)
    {
        try {
            switch (args[1]) {
                case "get":
                    if( args.length< 3 ){
                        System.out.println("Please input: key");
                        break;
                    }
                    System.out.println("key="+args[2]);
                    List<Type> value = configAction.get(new Utf8String(args[2])).get();
                    System.out.println(args[2]+"="+value.get(0)+","+value.get(1));
                case "all":
                    if( args.length< 4 ){
                        System.out.println("Please input: key value");
                        break;
                    }
                    configAction.set(new Utf8String(args[2]),new Utf8String(args[3])).get();
                    break;
                default:
                    break;
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
