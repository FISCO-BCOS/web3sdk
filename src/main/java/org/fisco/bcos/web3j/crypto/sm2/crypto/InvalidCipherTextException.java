package org.fisco.bcos.web3j.crypto.sm2.crypto;

/**
 * this exception is thrown whenever we find something we don't expect in a
 * message.
 */
public class InvalidCipherTextException extends CryptoException {

	private static final long serialVersionUID = 5927402763633918686L;

	/**
	 * base constructor.
	 */
	public InvalidCipherTextException() {
	}

	/**
	 * create a InvalidCipherTextException with the given message.
	 *
	 * @param message
	 *            the message to be carried with the exception.
	 */
	public InvalidCipherTextException(String message) {
		super(message);
	}

	/**
	 * create a InvalidCipherTextException with the given message.
	 *
	 * @param message
	 *            the message to be carried with the exception.
	 * @param cause
	 *            the root cause of the exception.
	 */
	public InvalidCipherTextException(String message, Throwable cause) {
		super(message, cause);
	}
}
