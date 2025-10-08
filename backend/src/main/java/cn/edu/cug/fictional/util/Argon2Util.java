package cn.edu.cug.fictional.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Argon2 密码加密工具类
 */
public class Argon2Util {
    
    private static final Argon2 argon2 = Argon2Factory.create(
        Argon2Factory.Argon2Types.ARGON2id,
        32,
        64
    );
    
    private static final int ITERATIONS = 3;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 4;

    /**
     * 加密密码
     */
    public static String hash(String password) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
    }

    /**
     * 验证密码
     */
    public static boolean verify(String hash, String password) {
        try {
            return argon2.verify(hash, password.toCharArray());
        } catch (Exception e) {
            return false;
        }
    }
}

