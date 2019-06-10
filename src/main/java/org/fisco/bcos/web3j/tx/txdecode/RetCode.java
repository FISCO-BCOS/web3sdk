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

import lombok.Data;

@Data
public class RetCode {
    private Integer code;
    private String msg;

    public RetCode() {}

    public RetCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RetCode mark(int code, String msg) {
        return new RetCode(code, msg);
    }

    public static RetCode mark(Integer code) {
        return new RetCode(code, null);
    }

    @Override
    public String toString() {
        return "RetCode [code=" + code + ", msg=" + msg + "]";
    }
}
