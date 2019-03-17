pragma solidity ^0.4.2;

contract Consensus {
    function addSealer(string nodeID) public returns(int);
    function addObserver(string nodeID) public returns(int);
    function remove(string nodeID) public returns(int);
}