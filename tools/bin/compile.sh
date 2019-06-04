#!/bin/bash
# arg1: java packege name


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

    eval ${command}
    local ret=$?
    if [ $ret -ne 0 ];then
        LOG_ERROR "execute command ${command} FAILED"
        exit 1
    fi
}
chmod a+x web3sdk

dirpath="$(cd "$(dirname "$0")" && pwd)"

cd $dirpath"/../contracts"

WEB3J=$dirpath"/../bin/web3sdk"
SOLC=`which fisco-solc`
Output=$dirpath"/../output"
Contracts=$dirpath"/../contracts"

if [ $# -lt 1 ]; then
    LOG_ERROR "usage: \n\targ1: java packege name"
    LOG_ERROR "\targ2: enable guomi or not (0: disable guomi; 1: enable guomi), default disable guomi"
    LOG_ERROR "\targ3: compiler path of guomi, default is ${SOLC}-guomi"
    exit 1
fi

LOG_INFO "... TO COMPILE SOLIDITY CODES AND GENERATE JAVA FILES...\n"

function compile()
{
    #path of solc(default: which fisco-bcos)
    local solc_bin="${1}" 
    local output_dir="${2}"
    LOG_INFO "#### SOLC_BIN: ${solc_bin}, OUTPUT_DIR: ${output_dir} \n" 
    local Files=`ls *.sol`
    for itemfile in ${Files}
    do
	item=`basename $itemfile ".sol"`
    	LOG_INFO "compiling ${itemfile} ..."
    	execute_cmd "${solc_bin} --abi --bin -o ${output_dir} ${itemfile} --overwrite"
    done
    LOG_INFO "... ... COMPILE SUCCESS ... ... "
}

function generate_java_code()
{
   local package="${1}"
   local output_dir="${2}"
   local guomi_output="" 
   if [ $# -ge 3 ];then
       guomi_output="${3}"
   fi
   local files=`ls *.sol`
   for itemfile in ${files}
   do
       local item=`basename ${itemfile} ".sol"`
	   LOG_INFO "... generate java code according to ${item}.sol abi, bin file..."
       if [ "${guomi_output}" != "" -a -d "${guomi_output}" ];then
           execute_cmd "${WEB3J} solidity generate ${output_dir}/${item}.bin ${output_dir}/${item}.abi -g ${guomi_output}/${item}.bin -o ${output_dir} -p ${package}"
       else
           execute_cmd "${WEB3J} solidity generate ${output_dir}/${item}.bin ${output_dir}/${item}.abi -o ${output_dir} -p ${package}"
       fi
   done
}

function compile_default()
{
    LOG_INFO "#### COMPILE with DEFAULT COMPILER, SOLC: ${SOLC}, OUTPUT: ${Output} \n"
    if [ -f "${SOLC}" ];then
    	compile "${SOLC}" "${Output}"
    else
	LOG_ERROR "DEFAULT COMPILER NOT FOUND, EXIT NOW..."
	exit 1
    fi
}

function compile_guomi()
{
    #default compiler of guomi
    solc_bin="${1}"
    LOG_INFO "#### COMPILE ABI AND BIN OF GUOMI VERSION\n"
    if [ -f "${solc_bin}" ];then
    	compile "${solc_bin}" "${Output}/guomi"
	return 0
    else
	LOG_ERROR "#### COMPILER OF GUOMI NOT FOUND, COMPILE WITH DEFAULT COMPILER\n"
	return 1
    fi
}

function compile_and_generate()
{
    local package="${1}"
    local enable_guomi="0"
    if [ $# -ge 2 ];then
        enable_guomi=${2}
    fi
    local guomi_solc_path="${SOLC}-guomi"
    if [ $# -ge 3 ];then
        guomi_solc_path="${3}"
    fi
    local guomi_ret=0
    LOG_INFO "... default guomi_solc_path: ${guomi_solc_path}\n" 
    #=========compile to generate bin and abi files==========
    #compile bin and abi with default compiler
    compile_default
    if [ "${enable_guomi}" == "1" ];then
        #compile bin and abi with guomi compiler
        LOG_INFO "#### ENABLE GUOMI COMPILING\n"
	compile_guomi "${guomi_solc_path}"
	guomi_ret=$?
    fi
    #========generate java code=============
    if [ "${enable_guomi}" == "1" -a ${guomi_ret} -eq 0 ];then
	LOG_INFO "... GENERATE GUOMI VERSION JAVA CODE ...\n"
        generate_java_code "${package}" "${Output}" "${Output}/guomi"
    else
	LOG_INFO "... GENERATE DEFAULT VERSION JAVA CODE...\n"
        generate_java_code "${package}" "${Output}"
    fi
}
rm -rf ${Output}
compile $@


echo "ending ."
cd ../bin
