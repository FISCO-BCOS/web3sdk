package org.fisco.bcos.web3j.protocol.channel;

public class StatusCode {

    public static final String Success = "0x0";
    public static final String Unknown = "0x1";
    public static final String BadRLP = "0x2";
    public static final String InvalidFormat = "0x3";
    public static final String OutOfGasIntrinsic = "0x4";
    public static final String InvalidSignature = "0x5";
    public static final String InvalidNonce = "0x6";
    public static final String NotEnoughCash = "0x7";
    public static final String OutOfGasBase = "0x8";
    public static final String BlockGasLimitReached = "0x9";
    public static final String BadInstruction = "0xa";
    public static final String BadJumpDestination = "0xb";
    public static final String OutOfGas = "0xc";
    public static final String OutOfStack = "0xd";
    public static final String StackUnderflow = "0xe";
    public static final String NonceCheckFail = "0xf";
    public static final String BlockLimitCheckFail = "0x10";
    public static final String FilterCheckFail = "0x11";
    public static final String NoDeployPermission = "0x12";
    public static final String NoCallPermission = "0x13";
    public static final String NoTxPermission = "0x14";
    public static final String PrecompiledError = "0x15";
    public static final String RevertInstruction = "0x16";
    public static final String InvalidZeroSignatureFormat = "0x17";
    public static final String AddressAlreadyUsed = "0x18";
    public static final String PermissionDenied = "0x19";
    public static final String CallAddressError = "0x1a";
    public static final String GasOverflow = "0x1b";
    public static final String TxPoolIsFull = "0x1c";
    public static final String TransactionRefused = "0x1d";

    // extension
    public static final String ExceptionCatched = "0x30";
    public static final String ErrorInRPC = "0x31";

    /// txPool related errors
    public static final String AlreadyKnown = "0x2710";
    public static final String AlreadyInChain = "0x2711";
    public static final String InvalidTxChainId = "0x2712";
    public static final String InvalidTxGroupId = "0x2713";
    public static final String RequestNotBelongToTheGroup = "0x2714";
    public static final String MalformedTx = "0x2715";

    public static String getStatusMessage(String status) {
        return getStatusMessage(status, " Error code: " + status);
    }

    public static String getStatusMessage(String status, String errorMessage) {
        String message = "";
        switch (status) {
            case Success:
                message = "success";
                break;
            case Unknown:
                message = "unknown";
                break;
            case BadRLP:
                message = "bad RLP";
                break;
            case InvalidFormat:
                message = "invalid format";
                break;
            case OutOfGasIntrinsic:
                message = "out of gas";
                break;
            case InvalidSignature:
                message = "invalid signature";
                break;
            case InvalidNonce:
                message = "invalid nonce";
                break;
            case NotEnoughCash:
                message = "not enough cash";
                break;
            case OutOfGasBase:
                message = "out of gas base";
                break;
            case BlockGasLimitReached:
                message = "block gas limit reached";
                break;
            case BadInstruction:
                message = "bad instruction";
                break;
            case BadJumpDestination:
                message = "bad jump destination";
                break;
            case OutOfGas:
                message = "out of gas";
                break;
            case OutOfStack:
                message = "out of stack";
                break;
            case StackUnderflow:
                message = "stack underflow";
                break;
            case NonceCheckFail:
                message = "nonce check fail";
                break;
            case BlockLimitCheckFail:
                message = "block limit check fail";
                break;
            case FilterCheckFail:
                message = "filter check fail";
                break;
            case NoDeployPermission:
                message = "no deploy permission";
                break;
            case NoCallPermission:
                message = "no call permission";
                break;
            case NoTxPermission:
                message = "no tx permission";
                break;
            case PrecompiledError:
                message = "precompiled error";
                break;
            case RevertInstruction:
                message = "revert instruction";
                break;
            case InvalidZeroSignatureFormat:
                message = "invalid zero signature format";
                break;
            case AddressAlreadyUsed:
                message = "address already used";
                break;
            case PermissionDenied:
                message = "permission denied";
                break;
            case CallAddressError:
                message = "call address error";
                break;
            case GasOverflow:
                message = "gas over flow";
                break;
            case TxPoolIsFull:
                message = "transaction pool is full";
                break;
            case TransactionRefused:
                message = "transaction refuesd";
                break;
            case ErrorInRPC:
                message = "error in RPC";
                break;
            case AlreadyKnown:
                message = "transaction already known";
                break;
            case AlreadyInChain:
                message = "transaction already in chain";
                break;
            case InvalidTxChainId:
                message = "invalid chain id";
                break;
            case InvalidTxGroupId:
                message = "invalid group id";
                break;
            case RequestNotBelongToTheGroup:
                message = "request not belong to the group";
                break;
            case MalformedTx:
                message = "malformed transaction";
                break;
            default:
                message = errorMessage;
                break;
        }

        return message;
    }
}
