package org.fisco.bcos.channel.test.precompile;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.authority.AuthorityService;
import org.fisco.bcos.web3j.precompile.config.SystemConfigSerivce;
import org.fisco.bcos.web3j.precompile.consensus.ConsensusService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.lang.System.exit;

class PrecompileManager {

    //TODO: load from configuration
    public static Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
    protected static Web3j web3j;

    public static void main(String[] args) throws Exception {
        /// init application context from xml
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        /// init service
        String groupId = args[3];
        Service service = context.getBean(Service.class);
        service.setGroupId(Integer.parseInt(groupId));
        service.run();
        System.out.println("==== start ... ");
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        /// init web3j of specified group according to configuration file
        web3j = Web3j.build(channelEthereumService, service.getGroupId());
        if (args[0].equals("pbft")) {
        	ConsensusService consensusService = new ConsensusService();
        	consensusService.call(args, web3j, credentials, service.getGroupId());
        } 
        else if(args[0].equals("authority"))
        {
        	AuthorityService authorityService = new AuthorityService();
        	authorityService.call(args, web3j, credentials, service.getGroupId());
        }
        else if (args[0].equals("setSystemConfig")) {
            SystemConfigSerivce systemConfigSerivce = new SystemConfigSerivce();
            systemConfigSerivce.call(args, web3j, credentials, service.getGroupId());
        } else {
            System.out.println("Invalid Param, please provide pbft, authority or setSystemConfig.");
        }
        exit(0);
    }
}
