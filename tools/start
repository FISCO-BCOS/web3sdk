#!/usr/bin/env bash

LANG=zh_CN.UTF-8
##############################################################################
##
##  Console start up script for UN*X
##
##############################################################################

# @function: output log with red color (error log)
# @param: content: error message
function LOG_ERROR()
{
    local content=${1}
    echo -e "\033[31m"${content}"\033[0m"
}

# @function: output information log
# @param: content: information message
function LOG_INFO()
{
    local content=${1}
    echo -e "\033[32m"${content}"\033[0m"
}

function Usage() {
    LOG_INFO "# Console TOOLS"
    LOG_INFO "--Start console: \t./start [groupID] [privateKey]\n"
}

if [ "${1}" == "-h" ] || [ "${1}" == "--help" ] || [ "${1}" == "help" ];then
    Usage
    exit 0
else
	java -cp "apps/*:conf/:lib/*:classes/" org.fisco.bcos.web3j.console.ConsoleClient $1 $2    
fi


