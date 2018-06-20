pragma solidity ^0.4.0;
import "ContractBase.sol";

contract CNSTest is ContractBase("v-0.0.1") {
    event SetMsg(string msg);
    event SetU(uint256 u);
    event Set(string msg,uint256 u);
    string msg;
    uint256 u;
    function CNSTest(){
       msg ="Hi,Welcome!";
       u = 1111111111;
    }
    
    function getMsg() public constant returns(string) {
        return msg;
    }
    
    function getInt() public constant returns(uint256) {
        return u;
    }
    
    function setMsg(string m) public {
        SetMsg(m);
        msg = m;
    }
    
    function setU(uint256 ui) public {
        SetU(ui);
        u = ui;
    }
    
    function get() public constant returns(string, uint256) {
        return (msg,u);
    }
    
    function set(string m, uint256 ui) public {
        Set(m,ui);
        msg = m;
        u = ui;
    }
}
