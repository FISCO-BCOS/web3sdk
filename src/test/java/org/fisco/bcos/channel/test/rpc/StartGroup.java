package org.fisco.bcos.channel.test.rpc;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartGroup {

    private static Logger logger = LoggerFactory.getLogger(GenerateGroup.class);

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.StartGroup groupID");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            Usage();
        }

        int groupID = Integer.valueOf(args[0]);

        System.out.println(" Start Group operation, groupID: " + groupID);

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.setGroupId(1);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3j web3j = Web3j.build(channelEthereumService, 1);

        org.fisco.bcos.web3j.protocol.core.methods.response.StartGroup startGroup =
                web3j.startGroup(groupID).send();
        logger.info("  StartGroup result: {}", startGroup);

        System.out.println(" StartGroup result: " + startGroup.getStatus());

        System.exit(0);
    }
}
