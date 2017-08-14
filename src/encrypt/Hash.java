/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author desarrollo6
 */
public class Hash {
    
    public static String strToSHA256(String str) throws NoSuchAlgorithmException {
        if (str == null) {
            throw new IllegalArgumentException("str is null");
        }
        return bytesToSHA256(str.getBytes());
    }
    
    public static String bytesToSHA256(byte[] bytes) throws NoSuchAlgorithmException {
        return bytesToEncrypt(bytes, "SHA-256");
    }
    
    public static String strToMD5(String str) throws NoSuchAlgorithmException {
        if (str == null) {
            throw new IllegalArgumentException("str is null");
        }
        return bytesToMD5(str.getBytes());
    }
    
    public static String bytesToMD5(byte[] bytes) throws NoSuchAlgorithmException {
        return bytesToEncrypt(bytes, "MD5");
    }
    
    public static String bytesToEncrypt(byte[] bytes, String typeEncrypt) throws NoSuchAlgorithmException {
        if (bytes == null || bytes.length == 0) {
            throw new IllegalArgumentException("Param bytes is null or empty");
        }
        String outEncoded;
        try {
            MessageDigest md = MessageDigest.getInstance(typeEncrypt);
            md.update(bytes);
            byte[] result = md.digest();
            outEncoded = bytesToHex(result);
            //outEncoded = new String(Hex.encodeHex(bytes));
            return outEncoded;
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        }
    }
    
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static void main(String[] args) {
        
        try {
            System.out.println(Hash.strToSHA256("documento"));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Hash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
