package com.petdiary.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static class SHA256 {
        public static String encrypt(String plainText) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainText.getBytes());
            return bytesToHex(md.digest());
        }

        public static boolean compare(String plainText, String hashedText) throws NoSuchAlgorithmException {
            return hashedText.equals(encrypt(plainText));
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
