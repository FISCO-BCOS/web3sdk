pragma solidity ^0.4.24;

contract ContractLifeCyclePrecompiled {
    function freeze(address addr) public returns(int);
    function unfreeze(address addr) public returns(int);
    function grantManager(address contractAddr, address userAddr) public returns(int);
    function getStatus(address addr) public constant returns(uint,string);
    function queryManager(address addr) public constant returns(uint,address[]);
}
