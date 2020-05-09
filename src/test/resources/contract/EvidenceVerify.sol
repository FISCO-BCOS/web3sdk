pragma solidity ^0.4.4;
import "./Evidence.sol";

contract EvidenceVerify
{
    event newEvidenceEvent(address addr);
    function insertEvidence(string evi, string info, string id, address signAddr, bytes32 message, uint8 v, bytes32 r, bytes32 s) public returns(address)
    {
        address evidence = new Evidence(evi, info, id);
        newEvidenceEvent(evidence);
        require(recoverSigner(message,v, r, s) == signAddr);
    }


   function recoverSigner(bytes32 message, uint8 v, bytes32 r, bytes32 s)
        internal
        pure
        returns (address)
    {
        return ecrecover(message, v, r, s);
    }
    
}
