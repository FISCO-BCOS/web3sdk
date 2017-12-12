#!/bin/bash
# arg1: java packege name


if [ $# -lt 1 ]; then
    echo "usage: arg1: java packege name"
    exit 1
fi

dirpath="$(cd "$(dirname "$0")" && pwd)"

cd $dirpath"/../contracts"

echo "to compile and generate files ..."
WEB3J=$dirpath"/../bin/web3sdk"
SOLC=`which fisco-solc`
Output=$dirpath"/../output"
Package=$1
Contracts=$dirpath"/../contracts"

compile_all()
{
        Files=`ls *.sol`
        echo "files: $Files \n"

        for itemfile in ${Files}
        do
            item=`basename $itemfile ".sol"`
            echo "compiling ${itemfile} ..."
            echo "${SOLC} --abi --bin -o ${Output} ${Contracts}/${itemfile}"
            ${SOLC} --abi --bin -o ${Output} ${itemfile}
            echo "generate ${item}"".sol abi, bin file..."
            echo "${WEB3J} solidity generate ${Output}/${item}"".bin ${Output}/${item}"".abi -o ${Output} -p ${Package}"
            ${WEB3J} solidity generate ${Output}"/"${item}".bin" ${Output}"/"${item}".abi" -o ${Output} -p ${Package}
        done

        echo "output directory: ${Output}"

        echo "compilation Done "
}

compile_all

echo "ending ."
cd ../bin
