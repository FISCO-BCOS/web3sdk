pragma solidity ^0.4.4;

contract Miner
{
    function addNodeToMiner(string nodeID) public returns(uint256);
    function addNodeToObserver(string nodeID) public returns(uint256);
    function removeNode(string nodeID) public returns(uint256);
}