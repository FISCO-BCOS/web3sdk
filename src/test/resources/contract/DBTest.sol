
contract DBFactory {
    function openDB(string) public constant returns (DB);
    function createTable(string,string,string) public constant returns(int);
}

//查询条件
contract Condition {
    function EQ(string, int);
    function EQ(string, string);
    
    function NE(string, int);
    function NE(string, string);

    function GT(string, int);
    function GE(string, int);
    
    function LT(string, int);
    function LE(string, int);
    
    function limit(int);
    function limit(int, int);
}

//单条数据记录
contract Entry {
    function getInt(string) public constant returns(int);
    function getAddress(string) public constant returns(address);
    function getBytes64(string) public constant returns(byte[64]);
    function getBytes32(string) public constant returns(bytes32);

    function set(string, int) public;
    function set(string, string) public;
}

//数据记录集
contract Entries {
    function get(int) public constant returns(Entry);
    function size() public constant returns(int);
}

//DB主类
contract DB {
    function select(string, Condition) public constant returns(Entries);
    
    function insert(string, Entry) public returns(int);
    
    function update(string, Entry, Condition) public returns(int);

    function remove(string, Condition) public returns(int);
    
    function newEntry() public constant returns(Entry);
    function newCondition() public constant returns(Condition);
}

contract DBTest {
	
	event createResult(int count);
    event insertResult(int count);
    event updateResult(int count);
    event removeResult(int count);
    event readResult(bytes32 name, int item_id, bytes32 item_name);
    
    function create() public {
        DBFactory df = DBFactory(0x1001);
        int count = df.createTable("t_test", "name", "item_id,item_name");
        createResult(count);
    }

    function read(string name) public constant returns(bytes32[], int[], bytes32[]){
        DBFactory df = DBFactory(0x1001);
        DB db = df.openDB("t_test");
        
        Condition condition = db.newCondition();
        //condition.EQ("name", name);
        
        Entries entries = db.select(name, condition);
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
    
    function insert(string name, int item_id, string item_name) public returns(int) {
        DBFactory df = DBFactory(0x1001);
        DB db = df.openDB("t_test");
        
        Entry entry = db.newEntry();
        entry.set("name", name);
        entry.set("item_id", item_id);
        entry.set("item_name", item_name);
        
        int count = db.insert(name, entry);
        insertResult(count);
        
        return count;
    }
    
    function update(string name, int item_id, string item_name) public returns(int) {
        DBFactory df = DBFactory(0x1001);
        DB db = df.openDB("t_test");
        
        Entry entry = db.newEntry();
        entry.set("item_name", item_name);
        
        Condition condition = db.newCondition();
        condition.EQ("name", name);
        condition.EQ("item_id", item_id);
        
        int count = db.update(name, entry, condition);
        updateResult(count);
        
        return count;
    }

    function remove(string name, int item_id) public returns(int){
        DBFactory df = DBFactory(0x1001);
        DB db = df.openDB("t_test");
        
        Condition condition = db.newCondition();
        condition.EQ("name", name);
        condition.EQ("item_id", item_id);
        
        int count = db.remove(name, condition);
        removeResult(count);
        
        return count;
    }
}