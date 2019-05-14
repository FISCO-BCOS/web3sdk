package org.fisco.bcos.web3j.protocol.channel;

public class StatusCode {

    public static String Success = "0x0";
    public static String Unknown = "0x1";
    public static String BadRLP = "0x2";
    public static String InvalidFormat = "0x3";
    public static String OutOfGasIntrinsic = "0x4";
    public static String InvalidSignature = "0x5";
    public static String InvalidNonce = "0x6";
    public static String NotEnoughCash = "0x7";
    public static String OutOfGasBase = "0x8";
    public static String BlockGasLimitReached = "0x9";
    public static String BadInstruction = "0xa";
    public static String BadJumpDestination = "0xb";
    public static String OutOfGas = "0xc";
    public static String OutOfStack = "0xd";
    public static String StackUnderflow = "0xe";
    public static String NonceCheckFail = "0xf";
    public static String BlockLimitCheckFail = "0x10";
    public static String FilterCheckFail = "0x11";
    public static String NoDeployPermission = "0x12";
    public static String NoCallPermission = "0x13";
    public static String NoTxPermission = "0x14";
    public static String PrecompiledError = "0x15";
    public static String RevertInstruction = "0x16";
    public static String InvalidZeroSignatureFormat = "0x17";
    public static String AddressAlreadyUsed = "0x18";
    public static String PermissionDenied = "0x19";
    public static String CallAddressError = "0x1a";
}
