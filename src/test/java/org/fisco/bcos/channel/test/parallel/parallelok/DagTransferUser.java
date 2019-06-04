package org.fisco.bcos.channel.test.parallel.parallelok;

import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DagTransferUser {
    private static Logger logger = LoggerFactory.getLogger(DagTransferUser.class);
    private String user;
    private BigInteger amount;

    @Override
    public String toString() {
        return "DagTransferUser [user=" + user + ", amount=" + amount + "]";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public synchronized BigInteger getAmount() {
        return amount;
    }

    public synchronized void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public synchronized void increase(BigInteger amount) {
        logger.debug("increase before amount is " + this.amount);
        this.amount = this.amount.add(amount);
        logger.debug("increase after amount is " + this.amount);
    }

    public synchronized void decrease(BigInteger amount) {
        logger.debug("decrease before amount is " + this.amount);
        this.amount = this.amount.subtract(amount);
        logger.debug("decrease after amount is " + this.amount);
    }
}
