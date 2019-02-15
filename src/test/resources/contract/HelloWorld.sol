pragma solidity ^0.5.2;

contract HelloWorld{
   string  name;

     constructor() public {
       name = "Hello, World!";
    }

    function get() public view  returns(string memory ){
        return name;
    }

    function set(string memory n) public {
    	name = n;
    }
}