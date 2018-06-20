package org.bcos.contract.tools;

import org.bcos.contract.source.*;
import org.bcos.web3j.abi.datatypes.Address;
import org.bcos.web3j.abi.datatypes.DynamicArray;
import org.bcos.web3j.abi.datatypes.Type;
import org.bcos.web3j.abi.datatypes.Utf8String;
import org.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.bcos.web3j.abi.datatypes.generated.Uint256;
import org.bcos.web3j.abi.datatypes.generated.Uint8;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;

import java.util.List;

/**
 * Created by kimjin on 2018/4/17.
 */
public class ConsensusControlTools {

    static void processConsensusControl(ConsensusControlMgr mgr, Address systemAddr, String[] args, Object[] deployParams) {
        try {
            Web3j web3 = (Web3j)deployParams[0];
            Credentials credentials = (Credentials)deployParams[1];
            java.math.BigInteger gasPrice = (java.math.BigInteger)deployParams[2];
            java.math.BigInteger gasLimit = (java.math.BigInteger)deployParams[3];
            java.math.BigInteger initialWeiValue = (java.math.BigInteger)deployParams[4];
            switch(args[1]) {
                case "deploy":
                    ConsensusControl c = ConsensusControl.deploy(web3,credentials,gasPrice,gasLimit,initialWeiValue,
                            systemAddr).get();
                    mgr.setAddr(new Address(c.getContractAddress())).get();
                    System.out.println("deploy new ContractControl");
                    break;
                case "list":
                    Address addr = mgr.getAddr().get();
                    if (addr == null || addr.equals(new Address("0x0"))) {
                        System.err.println("[WARNNING] the 'ContractControl' contract haven't been depolyed, please [deploy] the contract before exec [list] command");
                        return;
                    }

                    ConsensusControl control = ConsensusControl.load(addr.toString(), web3, credentials, gasPrice, gasLimit);
                    DynamicArray<Bytes32> tmp = control.lookupAgencyList().get();
                    List<Bytes32> agencyList = tmp.getValue();
                    System.out.println("Current contract agency list and agency node count:");
                    for (Bytes32 agency : agencyList) {
                        Uint256 count = control.lookupAgencyCount(agency).get();
                        System.out.println("   agency:" + hexToString(agency) + "| count:" + count.getValue());
                    }
                    break;
                default:
                    help();
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }

    static String hexToString(Bytes32 hex) {
        byte[] t = hex.getValue();
        if (t == null)
            return "[ERROR!]";
        String str = new String(t);
        return str;
    }

    static void help() {
        System.out.println("Usage: ./web3sdk ConsensusControl deploy|turnoff|list");
    }
}