package org.fisco.bcos.channel.test.rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GroupManager {

    private static final Logger logger = LoggerFactory.getLogger(GroupManager.class);

    public GroupManager() throws Exception {
        this.web3j = init();
    }

    private Web3j web3j;

    public Web3j getWeb3j() {
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    public Web3j init() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
        return web3j;
    }

    public void generateGroup(
            int groupId, long timestamp, boolean enableFreeStorage, List<String> nodeList)
            throws IOException {
        org.fisco.bcos.web3j.protocol.core.methods.response.GenerateGroup generateGroup =
                web3j.generateGroup(groupId, timestamp, enableFreeStorage, nodeList).send();
        logger.info("  generateGroup result: {}", generateGroup.getResult());

        System.out.println(" generateGroup result: " + generateGroup.getResult());
    }

    public void startGroup(int groupId) throws IOException {
        org.fisco.bcos.web3j.protocol.core.methods.response.StartGroup startGroup =
                web3j.startGroup(groupId).send();
        logger.info("  startGroup result: {}", startGroup.getResult());

        System.out.println(" startGroup result: " + startGroup.getResult());
    }

    public void stopGroup(int groupId) throws IOException {
        org.fisco.bcos.web3j.protocol.core.methods.response.StopGroup stopGroup =
                web3j.stopGroup(groupId).send();
        logger.info("  stopGroup result: {}", stopGroup.getResult());

        System.out.println(" stopGroup result: " + stopGroup.getResult());
    }

    public void removeGroup(int groupId) throws IOException {
        org.fisco.bcos.web3j.protocol.core.methods.response.RemoveGroup removeGroup =
                web3j.removeGroup(groupId).send();
        logger.info("  removeGroup result: {}", removeGroup.getResult());

        System.out.println(" removeGroup result: " + removeGroup.getResult());
    }

    public void recoverGroup(int groupId) throws IOException {
        org.fisco.bcos.web3j.protocol.core.methods.response.RecoverGroup recoverGroup =
                web3j.recoverGroup(groupId).send();
        logger.info("  recoverGroup result: {}", recoverGroup.getResult());

        System.out.println(" recoverGroup result: " + recoverGroup.getResult());
    }

    public void queryGroupStatus(int groupId) throws IOException {
        org.fisco.bcos.web3j.protocol.core.methods.response.QueryGroupStatus queryGroupStatus =
                web3j.queryGroupStatus(groupId).send();
        logger.info("  queryGroupStatus result: {}", queryGroupStatus.getResult());

        System.out.println(" queryGroupStatus result: " + queryGroupStatus.getResult());
    }

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GroupManager generateGroup groupID timestamp enableFreeStorage node0 node1...nodeN");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GroupManager startGroup groupID");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GroupManager stopGroup groupID");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GroupManager removeGroup groupID");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GroupManager recoverGroup groupID");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.rpc.GroupManager queryGroupStatus groupID");
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            Usage();
        }

        GroupManager groupManager = null;
        try {
            groupManager = new GroupManager();
            String method = args[0];
            int groupID = Integer.valueOf(args[1]);

            switch (method) {
                case "generateGroup":
                    if (args.length < 5) {
                        Usage();
                    }
                    long timestamp = Long.valueOf(args[2]);

                    if (args[3].equals("true") || args[3].equals("false")) {
                        boolean enableFreeStorage = Boolean.valueOf(args[3]);
                        List<String> nodes = new ArrayList<>();
                        for (int i = 4; i < args.length; ++i) {
                            nodes.add(args[i]);
                        }

                        groupManager.generateGroup(groupID, timestamp, enableFreeStorage, nodes);
                    } else {
                        boolean enableFreeStorage = false;
                        List<String> nodes = new ArrayList<>();
                        for (int i = 3; i < args.length; ++i) {
                            nodes.add(args[i]);
                        }

                        groupManager.generateGroup(groupID, timestamp, enableFreeStorage, nodes);
                    }

                    break;
                case "startGroup":
                    groupManager.startGroup(groupID);
                    break;
                case "stopGroup":
                    groupManager.stopGroup(groupID);
                    break;
                case "removeGroup":
                    groupManager.removeGroup(groupID);
                    break;
                case "recoverGroup":
                    groupManager.recoverGroup(groupID);
                    break;
                case "queryGroupStatus":
                    groupManager.queryGroupStatus(groupID);
                    break;
                default:
                    System.out.println(" unrecognized methods. ");
            }
        } catch (Exception e) {
            System.out.println(" Failed => " + e.getMessage());
            logger.error("e: {}", e);
        } finally {
            System.exit(0);
        }
    }
}
