#!/bin/bash

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

# @function: execute command
# @params: 1. command: command to be executed
function execute_cmd()
{
    local command="${1}"
    local ignored=0
    if [ $# -gt 1 ];then
        ignored=${2}
    fi

    eval "${command}"
    local ret=$?
    if [ $ret -ne 0 ] && [ $ret -ne ${ignored} ];then
        LOG_ERROR "execute command ${command} FAILED"
        exit 1
    fi
}


function Usage()
{
    LOG_INFO "Usage":
    LOG_INFO "       ./compile_demo ${pacakge_name}"
}

#get demo
execute_cmd "wget https://raw.githubusercontent.com/FISCO-BCOS/FISCO-BCOS-DOC/master/docs/web3sdk/codes/Counter.sol -O Counter.sol"
#move Counter.sol to contract compiliation direcotry
execute_cmd "mv Counter.sol ../contracts"

if [ $# -lt 1 ];then
    Usage
    exit 1
fi
execute_cmd "chmod a+x compile.sh && ./compile.sh $@"

