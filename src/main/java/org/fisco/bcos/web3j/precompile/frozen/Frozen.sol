pragma solidity ^0.4.2;

contract Frozen {
    function kill(address addr) public returns(int);
    function freeze(address addr) public returns(int);
    function unfreeze(address addr) public returns(int);
    function queryStatus(address addr) public constant returns(uint,string);
    function queryAuthority(address addr) public constant returns(uint,address[]);
}