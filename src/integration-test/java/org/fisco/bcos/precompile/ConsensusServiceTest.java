package org.fisco.bcos.precompile;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.precompile.Common;
import org.fisco.bcos.web3j.precompile.consensus.ConsensusService;
import org.junit.Test;

public class ConsensusServiceTest extends TestBase {


	private ConsensusService consensusService = new ConsensusService(web3j, credentials);

  @Test
  public void addSealerAndObserverTest() throws Exception {

  	List<String> sealerList1 = web3j.getSealerList().send().getSealerList();
  	String nodeId = sealerList1.get(sealerList1.size() -1);
  	List<String> observerList1 = web3j.getObserverList().send().getObserverList();
  	
		String addObserverResult = consensusService.addObserver(nodeId);
		assertEquals(addObserverResult, Common.susscess);
		List<String> sealerList2 = web3j.getSealerList().send().getSealerList();
		assertEquals(sealerList1.size(), sealerList2.size() + 1);
		List<String> observerList2 = web3j.getObserverList().send().getObserverList();
		assertEquals(observerList1.size() + 1, observerList2.size());
		
		String removeNodeResult = consensusService.removeNode(nodeId);
		assertEquals(removeNodeResult, Common.susscess);
  	List<String> observerList3 = web3j.getObserverList().send().getObserverList();
  	assertEquals(observerList1.size(), observerList3.size());
  	
  	String addSealerResult = consensusService.addSealer(nodeId);
  	assertEquals(addSealerResult, Common.susscess);
  	List<String> sealerList3 = web3j.getSealerList().send().getSealerList();
  	assertEquals(sealerList1.size(), sealerList3.size());
  	
  }
  
}
