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

public interface ConstantCode {
    // return success
    RetCode RET_SUCCEED = RetCode.mark(0, "success");

    // paramaters check
    String GROUP_ID_IS_EMPTY = "{\"code\":203001,\"msg\":\"group id cannot be empty\"}";
    String UUID_DEPLOY_IS_EMPTY = "{\"code\":203002,\"msg\":\"deploy uuid cannot be empty\"}";
    String UUID_TRANS_IS_EMPTY = "{\"code\":203002,\"msg\":\"trans uuid cannot be empty\"}";
    String SIGN_TYPE_IS_EMPTY = "{\"code\":203003,\"msg\":\"sign type cannot be empty\"}";
    String CONTRACT_BIN_IS_EMPTY = "{\"code\":203004,\"msg\":\"contract bin cannot be empty\"}";
    String CONTRACT_ABI_IS_EMPTY = "{\"code\":203005,\"msg\":\"contract abi cannot be empty\"}";
    String CONTRACT_ADDRESS_IS_EMPTY =
            "{\"code\":203006,\"msg\":\"contract address cannot be empty\"}";
    String FUNCTION_NAME_IS_EMPTY = "{\"code\":203007,\"msg\":\"function name cannot be empty\"}";

    // general error
    RetCode UUID_DEPLOY_IS_EXISTS = RetCode.mark(303001, "deploy uuid is already exists");
    RetCode UUID_IS_EXISTS = RetCode.mark(303001, "uuid is already exists");
    RetCode GET_SIGN_DATA_ERROR = RetCode.mark(303002, "get sign data from sign service error");
    RetCode IN_FUNCPARAM_ERROR = RetCode.mark(303003, "contract funcParam is error");
    RetCode SIGN_TYPE_ERROR = RetCode.mark(303004, "sign type is not exists");
    RetCode CONTRACT_ABI_EMPTY = RetCode.mark(303005, "contract abi is empty");
    RetCode FUNCTION_NOT_CONSTANT = RetCode.mark(303006, "request function can not be constant");
    RetCode FUNCTION_MUST_CONSTANT = RetCode.mark(303007, "query function must be constant");
    RetCode TRANSACTION_QUERY_FAILED = RetCode.mark(303008, "query data from chain failed");
    RetCode FILE_IS_EMPTY = RetCode.mark(303009, "file cannot be empty");
    RetCode NOT_A_ZIP_FILE = RetCode.mark(303010, "it is not a zip file");
    RetCode CONTRACT_NOT_DEPLOED = RetCode.mark(303011, "contract has not been deployed");
    RetCode CONTRACT_COMPILE_ERROR = RetCode.mark(303012, "contract compile error");
    RetCode NODE_REQUEST_FAILED = RetCode.mark(303013, "node request failed");
    RetCode EVENT_NOT_EXISTS = RetCode.mark(303014, "there is not event");
    RetCode TRANS_NOT_SENT = RetCode.mark(303015, "trans has not been sent to the chain");
    RetCode ADDRESS_ABI_EMPTY =
            RetCode.mark(
                    303016,
                    "if deploy uuid is empty, contract address and contract abi cannot be empty");
    RetCode TRANS_OUTPUT_EMPTY = RetCode.mark(303017, "trans output is empty");
    RetCode TRANS_NOT_EXIST = RetCode.mark(303018, "trans is not exist");
    RetCode GROUPID_NOT_CONFIGURED =
            RetCode.mark(303019, "request group id has not been configured");

    // system error
    RetCode SYSTEM_ERROR = RetCode.mark(103001, "system error");
    RetCode PARAM_VAILD_FAIL = RetCode.mark(103002, "param valid fail");
}
