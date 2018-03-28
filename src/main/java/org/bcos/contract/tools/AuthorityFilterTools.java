package org.bcos.contract.tools;

import org.bcos.contract.source.AuthorityFilter;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Utf8String;

/**
 * Created by mingzhenliu on 2018/3/13.
 */
public class AuthorityFilterTools {
    static void processAuthorityFilter(AuthorityFilter authorityFilter, String[]args)
    {
        try {
            if(args.length<4){
                System.out.println("account、contractAddress、functionName");
                return;
            }
            System.out.println("origin :"+args[1]);
            System.out.println("to :"+args[2]);
            System.out.println("func :"+args[3]);

            System.out.println("权限校验结果:"+authorityFilter.process(new Address(args[2])
                    ,new Address("")
                    ,new Address(args[3]),
                    new Utf8String(args[4]),
                    new Utf8String("")).get());
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
