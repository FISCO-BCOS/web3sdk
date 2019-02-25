#!/bin/bash
cd ..
echo "Please ensure  gradle version is 4.10.1 and above!";
echo "Please ensure  input java package name!";
gradle run -Dexec.mainClass=org.fisco.bcos.web3j.solidity.CompileSolToJava --args="$1"
