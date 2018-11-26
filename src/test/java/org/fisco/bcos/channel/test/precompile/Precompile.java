package org.fisco.bcos.channel.test;

import org.fisco.bcos.channel.test.UpdatePBFTNode;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;

import java.io.IOException;
import java.math.BigInteger;

class PrecompileManager {

    //TODO: load from configuration
    public static Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
    protected static Web3j web3j;

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private static BigInteger initialWeiValue = new BigInteger("0");

    public static void main(String[] args) throws Exception {

        /// init application context from xml
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        /// init service
        Service service = context.getBean(Service.class);
        service.run();
        System.out.println("==== start ... ");
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        /// init web3j of specified group according to configuration file
        web3j = Web3j.build(channelEthereumService, Integer.parseInt(args[3]));
        if (args[0].equals("pbft")) {
            UpdatePBFTNode pbft = new UpdatePBFTNode();
            pbft.call(args, web3j, credentials, service.getGroupId());
        } else {
            System.out.println("Invalid Param, only support updatePBFTNode precompile now!");
        }
    }
}
