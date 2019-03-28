package org.fisco.bcos.precompile;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.precompile.crud.CRUDSerivce;
import org.fisco.bcos.web3j.precompile.crud.Condition;
import org.junit.Test;

public class CRUDServiceTest extends TestBase {


	CRUDSerivce crudSerivce = new CRUDSerivce(web3j, credentials);

	@SuppressWarnings("unchecked")
	@Test
  public void curdTest() throws Exception {
    
  	String tableName = "t_test" + new Random().nextInt(100000);
  	String key = "name";
  	
  	// create table
  	String valueField = "item_id, item_name";
    String resultCreate = crudSerivce.createTable(tableName, key, valueField);
    assertEquals(resultCreate, Common.SUSSCESS);
  	
    // insert records
    int insertResult = 0;
    int num = 5;
    for(int i = 1; i <= num; i++)
    {
	    Map<String, String> insertEntry = new HashMap<>();
	    insertEntry.put(key, "fruit");
	    insertEntry.put("item_id", "1");
    	insertEntry.put("item_name", "apple"+i);
    	insertResult += crudSerivce.insert(tableName, key, insertEntry, "");
    }
    assertEquals(insertResult, num);
		
    // select records
		Condition condition1 = new Condition();
		condition1.EQ("item_id", "1");
		condition1.Limit(2, 3);
		
		List<Map<String, String>> resultSelect1 = crudSerivce.select(tableName, key, condition1, "");
		assertEquals(resultSelect1.get(0).get("name"), "fruit");
		assertEquals(resultSelect1.get(0).get("item_id"), "1");
		assertEquals(resultSelect1.get(0).get("item_name"), "apple3");
  	
	  // update records
	  Map<String, String> updateEntry = new HashMap<>();
	  updateEntry.put(key, "fruit");
	  updateEntry.put("item_id", "1");
	  updateEntry.put("item_name", "orange");
		Condition updateCondition = new Condition();
		updateCondition.EQ("item_id", "1");
		int updateResult = crudSerivce.update(tableName, key, updateEntry, updateCondition, "");
		assertEquals(updateResult, num);
		
	  // select records
		Condition condition2 = new Condition();
		condition2.EQ("item_id", "1");
		condition2.Limit(1);;
		List<Map<String, String>> resultSelect2 = crudSerivce.select(tableName, key, condition2, "");
		assertEquals(resultSelect2.get(0).get("name"), "fruit");
		assertEquals(resultSelect2.get(0).get("item_id"), "1");
		assertEquals(resultSelect2.get(0).get("item_name"), "orange");
		
	  // remove records
		Condition removeCondition = new Condition();
		removeCondition.EQ("item_id", "1");
		int removeResult = crudSerivce.remove(tableName, key, removeCondition, "");
		assertEquals(removeResult, num);
		
	  // select records
		Condition condition3 = new Condition();
		condition3.EQ("item_id", "1");
		condition3.EQ("item_name", "orange");
		List<Map<String, String>> selectResult3 = crudSerivce.select(tableName, key, condition3, "");
		assertEquals(selectResult3.size(), 0);
		
  }
  
}
