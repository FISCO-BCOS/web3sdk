#!/bin/bash
echo "Please ensure  gradle version is 4.10.1 and above!";
gradle run -Dexec.mainClass=org.fisco.bcos.web3j.solidity.CompileSolToJava --args="$1"
