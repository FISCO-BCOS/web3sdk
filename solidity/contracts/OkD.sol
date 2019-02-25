import "./Table.sol";

contract OkD{
    event insertResult(int count);

    struct Account{
        address account;
        int balance;
    }
    
    Account from;
    Account to;

    function OkD(){
        
        from.account=0x1;
        from.balance=10000000000;
        to.account=0x2;
        to.balance=0;

        TableFactory tf = TableFactory(0x1001);
        tf.createTable("t_ok", "from_accout", "from_balance,to_accout,to_balance");
        tf = TableFactory(0x1001);
        Table table = tf.openTable("t_ok");
        Entry entry = table.newEntry();
        entry.set("from_accout", "0x1");
        entry.set("from_balance", 10000000000);
        entry.set("to_accout", "0x2");
        entry.set("to_balance", 0);

    }
    function get()constant returns(int){
        return to.balance;
    }
    function trans(string from_accout, int num){

    	from.balance = from.balance - num;
    	to.balance += num;

        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_ok");
        Entry entry = table.newEntry();
        entry.set("from_accout", from_accout);
        entry.set("from_balance", from.balance);
        entry.set("to_accout", "0x2");
        entry.set("to_balance", to.balance);
        int count = table.insert(from_accout, entry);
        insertResult(count);
        
    	// log.push(Translog("20170413",from.account,to.account,num));

    }

}
