#!/bin/bash
function LOG_ERROR()
{
    local content=${1}
    echo -e "\033[31m"${content}"\033[0m"
}

function LOG_INFO()
{
    local content=${1}
    echo -e "\033[32m"${content}"\033[0m"
}

function execute_cmd()
{
    local command="${1}"
    eval ${command}
    local ret=$?
    if [ $ret -ne 0 ];then
        LOG_ERROR "execute command ${command} FAILED"
        exit 1
    else
        LOG_INFO "execute command ${command} SUCCESS"
    fi
}


CUR_DIR=`pwd`

function install_centos_pkg()
{
    local pkg_name="${1}"
    rpm -qa | grep "${pkg_name}"
    if [ $? -eq 0 ];then
        LOG_INFO "${pkg_name} has already been installed"
        return 1
    else
        execute_cmd "sudo yum -y install ${pkg_name}"
    fi
    return 0
}

function install_deps_centos()
{
    install_centos_pkg "git"
    install_centos_pkg "lsof"
    install_centos_pkg "unzip"
}

function install_ubuntu_pkg()
{
    local pkg_name="${1}"
    dpkg -l | grep -i "${pkg_name}"
    if [ $? -eq 0 ];then
        LOG_INFO "${pkg_name} has already been installed"
        return 1
    else
        execute_cmd "sudo apt-get install -y ${pkg_name}"
    fi
    return 0
}

function install_deps_ubuntu()
{
    install_ubuntu_pkg "git"
    install_ubuntu_pkg "lsof"
    install_ubuntu_pkg "tofrodos"
    install_ubuntu_pkg "unzip"
}



function compile_sdk_client()
{
    chmod +x ./gradlew
    execute_cmd "./gradlew build"
}

#centos
if grep -Eqi "Ubuntu" /etc/issue || grep -Eq "Ubuntu" /etc/*-release; then
    install_deps_ubuntu
else
    install_deps_centos
fi

compile_sdk_client