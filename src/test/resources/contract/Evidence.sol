pragma solidity ^0.4.4;
contract Evidence{
    string evidence;
    string evidenceInfo;
    string evidenceId;
    event newEvidenceEvent(string evi, string info, string id);
    function Evidence(string evi, string info, string id) public 
    {
        evidence = evi;
        evidenceInfo = info;
        evidenceId = id;
        newEvidenceEvent(evi,info,id);
    }

    function getEvidenceInfo() public constant returns(string)
    {
        return evidenceInfo;
    }

    function getEvidence() public constant returns(string,string,string){
        return(evidence,evidenceInfo,evidenceId);
    }
}
