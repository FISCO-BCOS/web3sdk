pragma solidity ^0.4.24;

contract ChainGovernance {
    function grantCommitteeMember(address user) public returns (int256) {}
    function revokeCommitteeMember(address user) public returns (int256) {}
    function listCommitteeMembers() public view returns (string) {}
    function queryCommitteeMemberWeight(address user) public view returns (int256) {}
    function updateCommitteeMemberWeight(address user, int256 weight) public returns (int256) {}
    // threshold range [0,100]
    function updateThreshold(int256 threshold) public returns (int256) {}
    function queryThreshold() public view returns (int256) {}
    function grantOperator(address user) public returns (int256) {}
    function revokeOperator(address user) public returns (int256) {}
    function listOperators() public view returns (string) {}
}

