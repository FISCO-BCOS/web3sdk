contract AuthorityTable {
    function insert(string table_name, string addr) public returns(string);
    function remove(string table_name, string addr) public returns(string);
    function queryByName(string table_name) public constant returns(string);
}