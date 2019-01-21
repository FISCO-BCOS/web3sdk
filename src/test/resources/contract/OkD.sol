import "./DB.sol";

contract OkD{
    event insertResult(int count);

    struct Account{
        address account;
        int balance;
    }
    
    Account from;
    Account to;

    // struct  Translog {
    //     string time;
    //     address from;
    //     address to;
    //     uint amount;
    // }
    
    
    // Translog[] log;

    function OkD(){
        
        from.account=0x1;
        from.balance=10000000000;
        to.account=0x2;
        to.balance=0;

        DBFactory df = DBFactory(0x1001);
        df.createTable("t_ok", "from_accout", "from_balance,to_accout,to_balance");
        df = DBFactory(0x1001);
        DB db = df.openTable("t_ok");
        Entry entry = db.newEntry();
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

        DBFactory df = DBFactory(0x1001);
        DB db = df.openTable("t_ok");
        Entry entry = db.newEntry();
        entry.set("from_accout", from_accout);
        entry.set("from_balance", from.balance);
        entry.set("to_accout", "0x2");
        entry.set("to_balance", to.balance);
        int count = db.insert(from_accout, entry);
        insertResult(count);
        
    	// log.push(Translog("20170413",from.account,to.account,num));

    }

}
