package org.fisco.bcos.web3j.crypto;

import java.math.BigInteger;
import org.bouncycastle.math.ec.ECPoint;

/** An ECDSA Signature. */
public class ECDSASignature {
    public BigInteger r;
    public BigInteger s;
    /**
     * The value of p is used to assist in calculating the value of recvID and it's value is
     * generated during the signature process
     */
    public ECPoint p;

    public ECDSASignature(BigInteger r, BigInteger s, ECPoint p) {
        this.r = r;
        this.s = s;
        this.p = p;
    }

    public ECDSASignature(BigInteger r, BigInteger s) {
        this(r, s, null);
    }

    /**
     * @return true if the S component is "low", that means it is below {@link
     *     Sign#HALF_CURVE_ORDER}. See <a
     *     href="https://github.com/bitcoin/bips/blob/master/bip-0062.mediawiki#Low_S_values_in_signatures">
     *     BIP62</a>.
     */
    public boolean isCanonical() {
        return s.compareTo(Sign.HALF_CURVE_ORDER) <= 0;
    }

    /**
     * Will automatically adjust the S component to be less than or equal to half the curve order,
     * if necessary. This is required because for every signature (r,s) the signature (r, -s (mod
     * N)) is a valid signature of the same message. However, we dislike the ability to modify the
     * bits of a Bitcoin transaction after it's been signed, as that violates various assumed
     * invariants. Thus in future only one of those forms will be considered legal and the other
     * will be banned.
     *
     * @return the signature in a canonicalised form.
     */
    public ECDSASignature toCanonicalised() {
        if (!isCanonical()) {
            // The order of the curve is the number of valid points that exist on that curve.
            // If S is in the upper half of the number of valid points, then bring it back to
            // the lower half. Otherwise, imagine that
            //    N = 10
            //    s = 8, so (-8 % 10 == 2) thus both (r, 8) and (r, 2) are valid solutions.
            //    10 - 8 == 2, giving us always the latter solution, which is canonical.
            return new ECDSASignature(r, Sign.CURVE.getN().subtract(s), p);
        } else {
            return this;
        }
    }
}
