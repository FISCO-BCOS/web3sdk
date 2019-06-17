pragma solidity ^0.4.24;

import "./Table.sol";

contract TableTest {
    event CreateResult(int count);
    event InsertResult(int count, string name, bytes8 item_name, bytes32[] item_id);
    event UpdateResult(int count, string name);
    event RemoveResult(int count);
    
    //create table
    function create() public returns(int){
        TableFactory tf = TableFactory(0x1001); //The fixed address is 0x1001 for TableFactory
        
        int count = tf.createTable("t_test", "name", "item_id,item_name");
        emit CreateResult(count);
        return count;
    }

    //select records
    function select(string name) public constant returns(bytes32[], int[], bytes32[]){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test");
        
        Condition condition = table.newCondition();
        
        Entries entries = table.select(name, condition);
        bytes32[] memory user_name_bytes_list = new bytes32[](uint256(entries.size()));
        int[] memory item_id_list = new int[](uint256(entries.size()));
        bytes32[] memory item_name_bytes_list = new bytes32[](uint256(entries.size()));
        
        for(int i=0; i<entries.size(); ++i) {
            Entry entry = entries.get(i);
            
            user_name_bytes_list[uint256(i)] = entry.getBytes32("name");
            item_id_list[uint256(i)] = entry.getInt("item_id");
            item_name_bytes_list[uint256(i)] = entry.getBytes32("item_name");
        }
 
        return (user_name_bytes_list, item_id_list, item_name_bytes_list);
    }
    //insert records
    function insert(string name, int item_id, string item_name) public returns(int result, string result_name, bytes32, address addr, bytes32[]) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test");
        
        Entry entry = table.newEntry();
        entry.set("name", name);
        entry.set("item_id", item_id);
        entry.set("item_name", item_name);
        
        int count = table.insert(name, entry);
        
        bytes32[] memory bytes_list = new bytes32[](uint256(2));
        bytes_list[0] = "fisco";
        bytes_list[1] = "bcos";

        emit InsertResult(count, "fruit", "apple", bytes_list);
        emit UpdateResult(count, "fruit");

        return (count, "hello", "world", 0x12, bytes_list);
    }
    //update records
    function update(string name, int item_id, string item_name) public returns(int) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test");
        
        Entry entry = table.newEntry();
        entry.set("item_name", item_name);
        
        Condition condition = table.newCondition();
        condition.EQ("name", name);
        condition.EQ("item_id", item_id);
        
        int count = table.update(name, entry, condition);
         emit UpdateResult(count, "fruit");
        
        return count;
    }
    //remove records
    function remove(string name, int item_id) public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_test");
        
        Condition condition = table.newCondition();
        condition.EQ("name", name);
        condition.EQ("item_id", item_id);
        
        int count = table.remove(name, condition);
        emit RemoveResult(count);
        
        return count;
    }
}
