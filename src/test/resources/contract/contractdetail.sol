pragma solidity ^0.4.11;


contract contractdetail   {

  struct contractStruct {
    string contractName;
    string contractAbi;
    string contractBin;
    string contractSource;
    string deployTime;
    string networkId;
    uint index;
  }

  mapping(address => contractStruct) private contractStructs;
  address[] private contractIndex;

   event LogNewContract (address indexed contractAddress, uint index,    string contractName,     string contractAbi,string contractBin ,  string networkId, string deployTime, string contractSource);

   event LogDeleteContract(address indexed contractAddress, uint index);

  function isContractExist(address contractAddress)
    public constant
    returns(bool isIndeed)
  {
    if(contractIndex.length == 0) return false;

    return ( contractIndex[contractStructs[contractAddress].index] == contractAddress);
  }


  function insertContract( address contractAddress,  string contractName , string contractAbi,string contractBin , string networkId, string deployTime, string contractSource )  public returns (uint index)
  {
    if(isContractExist(contractAddress)) throw;
    contractStructs[contractAddress].contractName = contractName;
    contractStructs[contractAddress].contractAbi = contractAbi;
    contractStructs[contractAddress].contractBin = contractBin;
    contractStructs[contractAddress].networkId = networkId;
    contractStructs[contractAddress].deployTime = deployTime;
    contractStructs[contractAddress].contractSource = contractSource;
    contractStructs[contractAddress].index  = contractIndex.push(contractAddress)-1;
    LogNewContract( contractAddress, contractStructs[contractAddress].index, contractName,  contractAbi,contractBin, networkId, deployTime, contractSource);
    return contractIndex.length-1;
  }

  function getContract(address contractAddress)  public  constant returns( string contractName , string contractAbi,string contractBin, string networkId, string deployTime, string contractSource, uint index)
  {
    if(!isContractExist(contractAddress)) throw;
    return(
      contractStructs[contractAddress].contractName,
      contractStructs[contractAddress].contractAbi,
      contractStructs[contractAddress].contractBin,
     contractStructs[contractAddress].networkId,
     contractStructs[contractAddress].deployTime,
     contractStructs[contractAddress].contractSource,
     contractStructs[contractAddress].index);
  }



   function deleteContract(address contractAddress)  public returns(uint index)
  {
    require(isContractExist(contractAddress));
    uint rowToDelete = contractStructs[contractAddress].index;
    address keyToMove = contractIndex[contractIndex.length-1];
    contractIndex[rowToDelete] = keyToMove;
    contractStructs[keyToMove].index = rowToDelete;
    contractIndex.length--;
    LogDeleteContract(contractAddress,rowToDelete);

    return rowToDelete;
  }

  function getContractCount()
    public constant returns(uint count)
  {
    return contractIndex.length;
  }

  function getContractAtIndex(uint index) public  returns ( string contractName, string contractAbi, string contractBin , string networkId, string deployTime, string contractSource, uint index1)
  {
   return getContract(contractIndex[index]);

  }

}