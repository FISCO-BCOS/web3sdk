#!/bin/bash

LANG=zh_CN.UTF-8
##############################################################################
##
##  Create a new account script for UN*X
##
##############################################################################

function Usage() {
    echo "Usage: "
    echo "-g <Generate guomi accounts>           Default no"
}

if [ "${1}" == "-h" ] || [ "${1}" == "--help" ] || [ "${1}" == "help" ] || [ $# -gt 1 ];then
    Usage
    exit 0
else
	java -cp "apps/*:lib/*" org.fisco.bcos.web3j.utils.AccountUtils $1    
fi


