package com.hengtiansoft.ecommerce.library.base.util;

import android.annotation.SuppressLint;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：RSA加密工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class RSAForAndroid {
    public static final String KEY_ALGORITHM = "RSA";

    /** 公钥 **/
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmhaYT3syy5knybU5JYA6J" +
            "/KDoKZ35f7rGH3ypkSTcPIeS38sUoLWFicMYRaDnwshJrq0XujMQ25dOPBqdLtqnbKihArhr9T8K7bc" +
            "+ySKhwaeVqnIbi3OzcJoRENNnWdA46+zKRndqtdD/uCmv6ndfT/PsFL/5uAts+TTBn2j0IQIDAQAB";

    /**
     * 自定义Base64工具解密
     *
     * @param key 密文
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key);
    }

//    /**
//     * RSA加密 用私钥加密
//     *
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static byte[] encryptByPrivateKey(byte[] data) throws Exception {
//        String key = privateKey;
//        // 对密钥解密
//        byte[] keyBytes = decryptBASE64(key);
//
//        // 取得私钥
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//
//        // 对数据加密
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//
//        return cipher.doFinal(data);
//    }

    /**
     * RSA解密 用私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
        String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKaFphPezLLmSfJt"
                + "TklgDon8oOgpnfl/usYffKmRJNw8h5LfyxSgtYWJwxhFoOfCyEmurRe6MxDbl048"
                + "Gp0u2qdsqKECuGv1Pwrttz7JIqHBp5WqchuLc7NwmhEQ02dZ0Djr7MpGd2q10P+4"
                + "Ka/qd19P8+wUv/m4C2z5NMGfaPQhAgMBAAECgYBjmFnNVps9gKjHmwKQtifb9cHT"
                + "OVjnqbJu3tQzosDWDEnV2Y3fx4Lp3IxYDwT+uKwMDbIvF8mFEqE7RI1yZ1yUgSQx"
                + "km+sQk55YPrkB5O1X488gnB7XJuKzWuSbiBftyjFPaWNCRW92XZ4mbZGTQ2GiZGt"
                + "+bzCjaXCMWCS1D8KcQJBANcYI2sFxUT+/RKAYcsfnr1tEuOxrBwqzVcokzZEFpdS"
                + "oxjU31AFv+8ETSZfT0aaSvBS+z5LrpvY3X5Nex2g0BUCQQDGMMXB2sYTvzXc6R+U"
                + "lOx2fIOJPeLdcGp44vUlf8QTes6SGN+Z1ZSaI0RbFInHBY45UNVHvPaTRvii+tde"
                + "QordAkEAilxHpDdhDyHrTvRwc8YSSY2ZQghvSuspjXBXSegAlDSfH4EHaSVZsnmU"
                + "2yMiDqt/mxn17bunRS76Q525nXRthQJBAMEZ2DSu4ftvfB2HjluIVnSDkO1xPM+g"
                + "rjymdVBaEkMgjpBOZ95M+QLNhdZwRmWBhjdWCNhixqRsq+iwGpMepokCQQCgkwir"
                + "X7ZZf8HVjBJfMThS2OMGi6ShCcTQFn93smGSkc5c1vGnERdzx+vxFfKCy1a0KBL1" + "jvD9bDqxYgcSocDK";
        // 对密钥解密
        byte[] keyBytes = android.util.Base64.decode(key, android.util.Base64.DEFAULT);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * RSA加密 用公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    @SuppressLint("TrulyRandom")
    public static byte[] encryptByPublicKey(byte[] data) throws Exception {
        String key = publicKey;
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] decryptByPublicKey(byte[] data) throws Exception {
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe8Krn6VCzUfcUaIFMSeHSbDxFkbLUJ5C5OOAy"
                + "dB2Ec9fqXqedSelYmR64US7iCgctDAB7HlAd02lfgIX6uKeMl1TKpJBPyMv1jU/KQF98thvusNk9"
                + "fxpu8Q3dJLgGLcFaP7JmOY+72XPUMuQQ1vCm01YVd1MBZCZvLREcwvRmhwIDAQAB";
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * Base64加密
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String encode(String string) throws Exception {
        byte[] data = string.getBytes();
        byte[] encodedData = encryptByPublicKey(data);
        return android.util.Base64.encodeToString(encodedData, android.util.Base64.DEFAULT);
    }
}
