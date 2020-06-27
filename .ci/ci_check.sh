#!/bin/bash

set -e

java -version

# code format check
bash gradlew verifyGoogleJavaFormat
# build
bash gradlew build
# UT test
bash gradlew test

curl -LO https://raw.githubusercontent.com/FISCO-BCOS/FISCO-BCOS/master/tools/build_chain.sh && chmod u+x build_chain.sh
curl -s https://raw.githubusercontent.com/FISCO-BCOS/FISCO-BCOS/master/tools/ci/download_bin.sh | bash -s -- -m -b master
echo "127.0.0.1:4 agency1 1,2,3" > ipconf

# bash build_chain.sh -h
# Non SM node test
echo " NonSM ============>>>> "
echo " NonSM ============>>>> "
echo " NonSM ============>>>> "

bash build_chain.sh -e bin/fisco-bcos -f ipconf -p 30300,20200,8545
bash nodes/127.0.0.1/start_all.sh
./nodes/127.0.0.1/fisco-bcos -v

mkdir -p src/integration-test/resources/
cp nodes/127.0.0.1/sdk/* src/integration-test/resources/
cp src/test/resources/applicationContext-sample.xml src/integration-test/resources/applicationContext.xml
cp src/test/resources/log4j.properties src/integration-test/resources/
bash gradlew integrationTest

# clean
bash nodes/127.0.0.1/stop_all.sh
bash nodes/127.0.0.1/stop_all.sh
bash nodes/127.0.0.1/stop_all.sh
rm -rf nodes

# SM node test
echo " SM NonSM SSL connection test ============>>>> "
echo " SM NonSM SSL connection test ============>>>> "
echo " SM NonSM SSL connection test ============>>>> "
bash build_chain.sh -e bin/fisco-bcos -g -f ipconf -p 30300,20200,8545
bash nodes/127.0.0.1/start_all.sh
cp -r nodes/127.0.0.1/sdk/* src/integration-test/resources/
cp src/test/resources/applicationContext-sample.xml src/integration-test/resources/applicationContext.xml
sed -i.bak 's/"0"/"1"/g' src/integration-test/resources/applicationContext.xml
cp src/test/resources/log4j.properties src/integration-test/resources/
bash gradlew integrationTest