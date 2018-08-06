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
    install_centos_pkg "dos2unix"    
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
    if [ $? -eq 0 ];then
        execute_cmd "sudo ln -s /usr/bin/todos /usr/bin/unxi2dos"
        execute_cmd "sudo ln -s /usr/bin/fromdos /usr/bin/dos2unix"
    fi
}


function install_gradle()
{
    if [ ! -d "${HOME}/opt/gradle-4.6" ];then
        execute_cmd "wget https://services.gradle.org/distributions/gradle-4.6-bin.zip -O gradle-4.6-bin.zip"
        execute_cmd "mkdir -p ~/opt && unzip gradle-4.6-bin.zip && mv gradle-4.6 ~/opt/"
        execute_cmd "rm -rf gradle-4.6-bin.zip"
    fi

    if ! grep -Eq "gradle" ~/.bashrc;then
        LOG_INFO "export grandle"
        execute_cmd 'echo "export PATH=\$PATH:\$HOME/opt/gradle-4.6/bin" >> ~/.bashrc'
    fi
    source ~/.bashrc
}

function compile_sdk_client()
{
    execute_cmd "gradle build"
}

#centos
if grep -Eqi "Ubuntu" /etc/issue || grep -Eq "Ubuntu" /etc/*-release; then
    install_deps_ubuntu
else
    install_deps_centos
fi
install_gradle
compile_sdk_client
