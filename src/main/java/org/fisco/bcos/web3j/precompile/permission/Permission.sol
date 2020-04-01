pragma solidity ^0.4.2;

contract Permission {
    function insert(string table_name, string addr) public returns(int) {}
    function remove(string table_name, string addr) public returns(int) {}
    function queryByName(string table_name) public constant returns(string) {}
    function grantWrite(address contractAddr, address user) public returns (int256) {}
    function revokeWrite(address contractAddr, address user) public returns (int256) {}
    function queryPermission(address contractAddr) public constant returns (string) {}
}