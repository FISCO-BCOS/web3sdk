package org.fisco.bcos.web3j.tx.txdecode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputEntity {

    private String name;
    private String type;
    private Object data;
}
