package org.fisco.bcos.precompile;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.fisco.bcos.TestBase;
import org.fisco.bcos.web3j.precompile.crud.CRUDSerivce;
import org.fisco.bcos.web3j.precompile.crud.Condition;
import org.fisco.bcos.web3j.precompile.crud.Entry;
import org.fisco.bcos.web3j.precompile.crud.Table;
import org.junit.Test;

public class CRUDServiceTest extends TestBase {


	CRUDSerivce crudSerivce = new CRUDSerivce(web3j, credentials);

	@SuppressWarnings("unchecked")
	@Test
  public void curdTest() throws Exception {
    
  	String tableName = "t_test" + new Random().nextInt(100000);
  	String key = "name";
  	String valueFields  = "item_id, item_name";
  	Table table = new Table(tableName, key, valueFields);

  	// create table
    int resultCreate = crudSerivce.createTable(table);
    assertEquals(resultCreate, 0);
  	
    // insert records
    int insertResult = 0;
    int num = 5;
    for(int i = 1; i <= num; i++)
    {
	    Entry insertEntry = table.getEntry();
	    insertEntry.put(key, "fruit");
	    insertEntry.put("item_id", "1");
    	insertEntry.put("item_name", "apple"+i);
    	insertResult += crudSerivce.insert(table, insertEntry);
    }
    assertEquals(insertResult, num);
		
    // select records
		Condition condition1 = table.getCondition();
		condition1.EQ("item_id", "1");
		condition1.Limit(1);
		
		List<Map<String, String>> resultSelect1 = crudSerivce.select(table, condition1);
		assertEquals(resultSelect1.get(0).get("name"), "fruit");
		assertEquals(resultSelect1.get(0).get("item_id"), "1");
		assertEquals(resultSelect1.get(0).get("item_name"), "apple1");
  	
	  // update records
	  Entry updateEntry = table.getEntry();
	  updateEntry.put(key, "fruit");
	  updateEntry.put("item_id", "1");
	  updateEntry.put("item_name", "orange");
		Condition updateCondition = table.getCondition();
		updateCondition.EQ("item_id", "1");
		int updateResult = crudSerivce.update(table, updateEntry, updateCondition);
		assertEquals(updateResult, num);
		
	  // select records
		Condition condition2 = table.getCondition();
		condition2.EQ("item_id", "1");
		condition2.Limit(1);;
		List<Map<String, String>> resultSelect2 = crudSerivce.select(table, condition2);
		assertEquals(resultSelect2.get(0).get("name"), "fruit");
		assertEquals(resultSelect2.get(0).get("item_id"), "1");
		assertEquals(resultSelect2.get(0).get("item_name"), "orange");
		
	  // remove records
		Condition removeCondition = table.getCondition();
		removeCondition.EQ("item_id", "1");
		int removeResult = crudSerivce.remove(table, removeCondition);
		assertEquals(removeResult, num);
  }
  
}
