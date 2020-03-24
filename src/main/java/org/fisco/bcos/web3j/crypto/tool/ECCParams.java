package org.fisco.bcos.web3j.crypto.tool;

import java.math.BigInteger;
import java.security.spec.ECFieldFp;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.IESParameterSpec;

public final class ECCParams {

    // ECDSA secp256k1 algorithm constants
    public static final BigInteger POINTG_PRE =
            new BigInteger("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798", 16);
    public static final BigInteger POINTG_POST =
            new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8", 16);
    public static final BigInteger FACTOR_N =
            new BigInteger("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141", 16);
    public static final BigInteger FIELD_P =
            new BigInteger("fffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f", 16);
    public static final EllipticCurve ellipticCurve =
            new EllipticCurve(new ECFieldFp(FIELD_P), new BigInteger("0"), new BigInteger("7"));
    public static final ECPoint pointG = new ECPoint(POINTG_PRE, POINTG_POST);
    public static final ECNamedCurveSpec ecNamedCurveSpec =
            new ECNamedCurveSpec("secp256k1", ellipticCurve, pointG, FACTOR_N);

    public static final IESParameterSpec IES_PARAMS = new IESParameterSpec(null, null, 64);
}
