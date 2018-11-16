package org.bcos.channel.test.contract;

import org.bcos.channel.client.Service;
import org.bcos.channel.test.TestBase;
import org.bcos.web3j.crypto.Credentials;
import org.bcos.web3j.protocol.Web3j;
import org.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.bouncycastle.util.test.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.ArrayList;

import static java.lang.System.exit;

public class PerfOk extends TestBase {

	public  void pressureTest() throws InterruptedException {
            int threadNum = 500;

				System.out.println("####create credential succ, begin deploy contract");
				java.math.BigInteger gasPrice = new BigInteger("300000000");
				java.math.BigInteger gasLimit = new BigInteger("300000000");
				java.math.BigInteger initialWeiValue = new BigInteger("0");
				final Ok okDemo = Ok.deploy(web3j, credentials, gasPrice, gasLimit, initialWeiValue).sendAsync().get();
				if (okDemo != null) {
                        ArrayList<Thread> threadArray = new ArrayList<Thread>();
                        for(int threadIndex = 0; threadIndex < threadNum; threadIndex++ ) {
                        threadArray.add(new Thread("" + threadIndex) {
                            public void run() {

                                    try {
							            //System.out.println("####contract address is: " + okDemo.getContractAddress());
    							        TransactionReceipt receipt = okDemo.trans(new BigInteger("4")).sendAsync().get();
    	    						    System.out.println("###callback trans success");
    	    	    					BigInteger toBalance = okDemo.get().sendAsync().get();
	    	    	    				System.out.println("============to balance:" + toBalance.intValue());
		    				            Thread.sleep(1000); //sleep 1s
                                    } catch(Exception e) {
                                        System.out.println("callback trans failed, error message:" + e.getMessage());
                                    }
                                }
                            });
                        threadArray.get(threadIndex).start();
                     }
                     //join all theads
                     for(int threadIndex = 0; threadIndex < threadNum; threadIndex++)
                         threadArray.get(threadIndex).join();
				} else {
					System.out.println("deploy Ok contract failed");
					exit(1);
				}
			}

}
