package org.fisco.bcos.channel.test.parallel.precompile;

import java.math.BigInteger;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.sm2.SM2Sign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** create and sign tx pressure test */
public class transferSign {
    private static Logger logger = LoggerFactory.getLogger(transferSign.class);

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println(
                " \t java -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.parallel.parallelok.transferSign txCount ThreadCount GMOrNot CacheOrNot.");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            Usage();
        }

        try {
            BigInteger count = new BigInteger(args[0]);
            BigInteger threadCount = new BigInteger(args[1]);
            if (args.length > 2 && args[2].equals("gm")) {
                EncryptType encryptType = new EncryptType(1);
            } else {
                EncryptType encryptType = new EncryptType(0);
            }

            if (args.length > 3 && args[3].equals("cache")) {
                SM2Sign.setOpenSM2SignerCache(true);
            }

            System.out.println(
                    (EncryptType.encryptType == EncryptType.ECDSA_TYPE)
                            ? " ===>> normal transfer sign test "
                            : " ===>> sm transfer sign test ");

            System.out.println(
                    SM2Sign.isOpenSM2SignerCache()
                            ? " ===>> Cache SM2Signer"
                            : " ===>> Not Cache SM2Signer");

            logger.info(" transfer tx sign test, txCount: {}, threadCount: {}", count, threadCount);

            DagUserMgr d = new DagUserMgr();
            d.createUser(1000);

            PerformanceDTTest performanceDTTest = new PerformanceDTTest();
            performanceDTTest.setDagUserMgr(d);

            performanceDTTest.userTransferSignTxPerfTest(count, threadCount.intValue());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
