package org.fisco.bcos.web3j.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.ObjectMapperFactory;

public class AccountUtils {

    public static void main(String[] args) throws Exception {
        String destDir = "accounts";
        File destDirFile = createDir(destDir);
        Account account = null;
        if (args.length >= 1) {
            if ("-g".equals(args[0])) {
                account = newAccount(true);
            } else {
                System.out.println("ERROR: Please set -g option.");
                return;
            }
        } else {
            account = newAccount(false);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss'--'");
        LocalDateTime localDateTime =
                ZonedDateTime.now(ZoneOffset.systemDefault()).toLocalDateTime();
        String now = localDateTime.format(formatter);
        String fileName = now + account.getAddress() + "--" + account.getEncryptType() + ".json";
        File accoutFile = new File(destDirFile, fileName);
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        objectMapper.writeValue(accoutFile, account);
        System.out.println("address:" + account.getAddress());
        System.out.println("privateKey:" + account.getPrivateKey());
        System.out.println("publicKey:" + account.getPublicKey());
        System.out.println("encryptType:" + account.getEncryptType());
        System.out.println();
        System.out.println(
                "Account file " + fileName + " successfully created in the directory: " + destDir);
    }

    private static File createDir(String destinationDir) {
        File destination = new File(destinationDir);
        if (!destination.exists()) {
            if (!destination.mkdirs()) {
                System.out.println(
                        "Unable to create destination directory ["
                                + destinationDir
                                + "], exiting...");
            }
        }
        return destination;
    }

    public static Account newAccount(boolean flag)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
                    NoSuchProviderException {
        // guomi
        Account account = new Account();
        if (flag) {
            EncryptType.encryptType = 1;
            account.setEncryptType("guomi");
        } else {
            EncryptType.encryptType = 0;
            account.setEncryptType("standard");
        }
        Credentials credentials = GenCredential.create();

        String address = credentials.getAddress();
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);

        account.setAddress(address);
        account.setPrivateKey(privateKey);
        account.setPublicKey(publicKey);
        return account;
    }
}
