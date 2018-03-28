package org.bcos.contract.tools;

import org.bcos.contract.source.SystemProxy;
import org.bcos.contract.source.TransactionFilterBase;
import org.bcos.contract.source.TransactionFilterChain;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by mingzhenliu on 2018/3/13.
 */
public class SystemProxyTools {
    static void processSystemProxy(SystemProxy systemProxy, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        try {
            System.out.println("-----------------系统路由表----------------------");
            Uint256 routelength = systemProxy.getRouteSize().get();
            for( int i=0;i<routelength.getValue().intValue();i++){
                Utf8String key = systemProxy.getRouteNameByIndex(new Uint256(i)).get();
                List<Type> route = systemProxy.getRoute(key).get();
                System.out.println(" " + i + ")" + key + "=>"+(route.get(0))
                        +"," + route.get(1).getValue()
                        + "," + ((BigInteger)(route.get(2).getValue())).intValue());

                if( key.getValue().equals("TransactionFilterChain") ){
                    TransactionFilterChain transactionFilterChain1 = TransactionFilterChain.load(route.get(0).toString(), web3j, credentials, gasPrice, gasLimit);
                    Uint256 filterlength = transactionFilterChain1.getFiltersLength().get();
                    for( int j=0; j < filterlength.getValue().intValue(); j++){
                        Address filter = transactionFilterChain1.getFilter(new Uint256(j)).get();
                        TransactionFilterBase transactionFilterBase = TransactionFilterBase.load(filter.toString(),web3j,credentials,gasPrice,gasLimit);

                        Utf8String name = transactionFilterBase._name().get();
                        Utf8String version = transactionFilterBase._version().get();
                        System.out.println("       "+name+"=>"+version+","+filter);
                    }
                }
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
