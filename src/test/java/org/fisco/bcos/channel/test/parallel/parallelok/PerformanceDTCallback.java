package org.fisco.bcos.channel.test.parallel.parallelok;

import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceDTCallback extends TransactionSucCallback {

    private Long startTime;

    private PerformanceDTCollector collector = null;
    private DagUserMgr dagUserMgr = null;

    private DagTransferUser user = null;
    private DagTransferUser fromUser = null;
    private DagTransferUser toUser = null;
    private BigInteger amount = null;

    private String callBackType = "transfer";

    public String getCallBackType() {
        return callBackType;
    }

    public void setCallBackType(String callBackType) {
        this.callBackType = callBackType;
    }

    public void recordStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public DagUserMgr getDagUserMgr() {
        return dagUserMgr;
    }

    public void setDagUserMgr(DagUserMgr dagUserMgr) {
        this.dagUserMgr = dagUserMgr;
    }

    public DagTransferUser getDagTransferUser() {
        return user;
    }

    public void setDagTransferUser(DagTransferUser dagTransferUser) {
        this.user = dagTransferUser;
    }

    public PerformanceDTCollector getCollector() {
        return collector;
    }

    public void setCollector(PerformanceDTCollector collector) {
        this.collector = collector;
    }

    static Logger logger = LoggerFactory.getLogger(PerformanceDTCallback.class);

    public PerformanceDTCallback() {}

    @Override
    public void onResponse(TransactionReceipt receipt) {
        Long cost = System.currentTimeMillis() - startTime;

        try {
            if (receipt.isStatusOK()) {

                if (callBackType.compareTo("set") == 0) { // add test
                    dagUserMgr.addUser(user);
                } else if (callBackType.compareTo("transfer") == 0) { // transfer test
                    fromUser.decrease(amount);
                    toUser.increase(amount);
                } else if (callBackType.compareTo("transferRevert") == 0) { // tranfer revert test
                    fromUser.decrease(amount);
                    toUser.increase(amount);
                }
            }

            if (callBackType.compareTo("transferRevert") == 0) {
                String info =
                        "[RevertTest-TxSent]"
                                + "\t[TxHash]="
                                + receipt.getTransactionHash()
                                + "\t[From]="
                                + fromUser.getUser()
                                + "\t[FromBalance]="
                                + fromUser.getAmount()
                                + "\t[To]="
                                + toUser.getUser()
                                + "\t[ToBalance]="
                                + toUser.getAmount()
                                + "\t[Status]="
                                + receipt.getStatus();
                System.out.println(info);
            }

            collector.onMessage(receipt, cost);
        } catch (Exception e) {
            logger.error("onMessage error: ", e);
        }
    }

    public DagTransferUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(DagTransferUser fromUser) {
        this.fromUser = fromUser;
    }

    public DagTransferUser getToUser() {
        return toUser;
    }

    public void setToUser(DagTransferUser toUser) {
        this.toUser = toUser;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }
}
