package org.fisco.bcos.channel.test.rpc;

import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GenerateGroup {

    private static Logger logger = LoggerFactory.getLogger(GenerateGroup.class);

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GenerateGroup groupID timestamp node0 node1...nodeN");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            Usage();
        }

        int groupID = Integer.valueOf(args[0]);
        int timestamp = Integer.valueOf(args[1]);
        List<String> nodes = new ArrayList<>();

        for (int i = 2; i < args.length; ++i) {
            nodes.add(args[i]);
        }

        System.out.println(
                " Generate Group operation, groupID: "
                        + groupID
                        + " ,timestamp: "
                        + timestamp
                        + " ,nodes: "
                        + nodes);

        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.setGroupId(1);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3j web3j = Web3j.build(channelEthereumService, 1);

        org.fisco.bcos.web3j.protocol.core.methods.response.GenerateGroup generateGroup =
                web3j.generateGroup(groupID, timestamp, nodes).send();
        logger.info("  generateGroup result: {}", generateGroup);

        System.out.println(" generateGroup result: " + generateGroup.getStatus());

        System.exit(0);
    }
}
