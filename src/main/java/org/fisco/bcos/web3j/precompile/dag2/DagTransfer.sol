pragma solidity ^0.4.25;
contract DagTransfer {
    function userAdd(string user, uint256 balance) public returns(bool)
    {
        return true;   
    }
    function userSave(string user, uint256 balance) public returns(bool)
    {
        return true;
    }
    function userDraw(string user, uint256 balance) public returns(bool)
    {
        return true;
    }
    function userBalance(string user) public constant returns(bool,uint256)
    {
        return (true, 0);
    }
    function userTransfer(string user_a, string user_b, uint256 amount) public returns(bool)
    {
        return true;
    }
}