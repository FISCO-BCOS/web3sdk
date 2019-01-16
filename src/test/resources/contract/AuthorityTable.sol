contract AuthorityTable {
    function insert(string table_name, string addr) public returns(int);
    function remove(string table_name, string addr) public returns(int);
    function queryByName(string table_name) public returns(string);
}