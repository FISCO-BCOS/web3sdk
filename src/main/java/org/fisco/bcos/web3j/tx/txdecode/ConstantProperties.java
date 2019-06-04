/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.fisco.bcos.web3j.tx.txdecode;

import java.math.BigInteger;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ConstantProperties {
    public static final BigInteger GAS_PRICE = new BigInteger("100000000");
    public static final BigInteger GAS_LIMIT = new BigInteger("100000000");
    public static final BigInteger INITIAL_WEI_VALUE = new BigInteger("0");

    public static final String TYPE_CONSTRUCTOR = "constructor";
    public static final String TYPE_FUNCTION = "function";
    public static final String TYPE_EVENT = "event";

    // monitor business log format
    public static final String MONITOR_BUSINESS_FORMAT =
            "[{\"CODE\":\"%s\",\"COST_TIME\":\"{}\",\"RES_CODE\":\"0\"}][{}]";
    public static final String CODE_BUSINESS_10001 =
            String.format(MONITOR_BUSINESS_FORMAT, "10001");
    public static final String MSG_BUSINESS_10001 = "deploy_transaction";
    public static final String CODE_BUSINESS_10002 =
            String.format(MONITOR_BUSINESS_FORMAT, "10002");
    public static final String MSG_BUSINESS_10002 = "stateless_transaction";
    public static final String CODE_BUSINESS_10003 =
            String.format(MONITOR_BUSINESS_FORMAT, "10003");
    public static final String MSG_BUSINESS_10003 = "repetitive_deploy_uuid";
    public static final String CODE_BUSINESS_10004 =
            String.format(MONITOR_BUSINESS_FORMAT, "10004");
    public static final String MSG_BUSINESS_10004 = "repetitive_stateless_uuid";

    // monitor abnormal log format
    public static final String MONITOR_ABNORMAL_FORMAT =
            "[{\"CODE\":\"%s\",\"RES_CODE\":\"1\"}][{}]";
    public static final String CODE_ABNORMAL_S0001 =
            String.format(MONITOR_ABNORMAL_FORMAT, "S2001");
    public static final String MSG_ABNORMAL_S0001 = "deploy_to_chain_exception";
    public static final String CODE_ABNORMAL_S0002 =
            String.format(MONITOR_ABNORMAL_FORMAT, "S2002");
    public static final String MSG_ABNORMAL_S0002 = "stateless_to_chain_exception";
    public static final String CODE_ABNORMAL_S0003 =
            String.format(MONITOR_ABNORMAL_FORMAT, "S2003");
    public static final String MSG_ABNORMAL_S0003 = "deploy_request_arrive_limit";
    public static final String CODE_ABNORMAL_S0004 =
            String.format(MONITOR_ABNORMAL_FORMAT, "S2004");
    public static final String MSG_ABNORMAL_S0004 = "stateless_request_arrive_limit";
    public static final String CODE_ABNORMAL_S0005 =
            String.format(MONITOR_ABNORMAL_FORMAT, "S2005");
    public static final String MSG_ABNORMAL_S0005 = "request_sign_server_exception";

    // constant configuration from file
    public static final String CONSTANT_PREFIX = "constant";
    private String signServiceUrl = "http://10.0.0.1:8081/sign-service/sign";
    private String privateKey = "edf02a4a69b14ee6b1650a95de71d5f50496ef62ae4213026bd8d6651d030995";
    private String cronTrans = "0/1 * * * * ?";
    private int requestCountMax = 6;
    private int selectCount = 10;
    private int intervalTime = 600;
    private int sleepTime = 30;
    private int transMaxWait = 20;
    private boolean ifDistributedTask = false;
}
