import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

public class JasyptTests {
    // properties 암호화 - 실제 암호키나 암호화할 문자열을 입력하고 commit 하지마세요!!!
    private final static String ENC_KEY = "RlVdb0UHFlTVTi2VUnHMLnfAfoEB7dDbztnFuphpafjg1z4SMxeBEsF4fKmt7zY0";

    private StandardPBEStringEncryptor getEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(ENC_KEY);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Test
    void getEncString() {
        // 암호화할 평문
        String plainText = "test message";

        String encryptedText = getEncryptor().encrypt(plainText);
        System.out.println("==> ENC Result: ENC(" + encryptedText + ")");
    }

    @Test
    void getPlainString() {
        // 복호화할 암호문
        String encryptedText = "rj9nXjfGAox3gU9VgFntk/H3Rf/CEml2";

        String plainText = getEncryptor().decrypt(encryptedText);
        System.out.println("==> PLAIN Result: " + plainText);
    }

    @Test
    void convertToEncString() {
        String[] plainTextArray = new String[] {
                "test messages 1",
                "test messages 2",
                "test messages 3"
        };

        System.out.println("=== PlainText to EncText ===");
        for (String plainText: plainTextArray) {
            System.out.printf("%s\n=> ENC(%s)\n", plainText, getEncryptor().encrypt(plainText));
        }
    }

    @Test
    void convertToPlainString() {
        String[] encTextArray = new String[] {
                "OzynrpQI9BnrQsmwR8fN2G/gNAx5+yi2",
                "NciIp8tF7C4kyYe4O4Kn2Yusb8lizglU",
                "6piJv94PUdwEahr0vHNnMNU5ELBCQEu7"
        };

        System.out.println("=== EncText to PlainText ===");
        for (String encText: encTextArray) {
            System.out.printf("%s\n=> %s\n", encText, getEncryptor().decrypt(encText));
        }
    }
}
