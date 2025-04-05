import java.util.Scanner;

public class BigVegenere {
    int[] key;
    char[][] alphabet;

    public BigVegenere() {
        this.key = new int[0];
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la clave: ");
        String numericKey = sc.nextLine();
        generateAlphabet();
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            key[i] = charToInteger(numericKey.charAt(i));
        }
    }

    public BigVegenere(String numericKey) {
        generateAlphabet();
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            key[i] = charToInteger(numericKey.charAt(i));
        }
    }

    void generateAlphabet() {
        alphabet = new char[64][64];
        char[] base = new char[64];
        base[14] = 'ñ';
        base[41] = 'Ñ';
        int i = 0;

        for (char c = 'a'; c <= 'z'; c++) {
            if (i == 14) i++;
            base[i++] = c;
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (i == 41) i++;
            base[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            base[i++] = c;
        }
        for (int j = 0; j < base.length; j++) {
            for (int k = 0; k < alphabet.length; k++) { //a-z, A-Z, 0-9
                alphabet[j][k] = base[(j + k) % alphabet.length];
            }
        }
    }

    int charToInteger(char c) {
        for (int i = 0; i < 64; i++) {
            if (alphabet[0][i] == c) {
                return i;
            }
        }
        return 0;
    }

    public String encrypt(String message) {
        String messageEncrypted = "";
        int[] numericKey = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            numericKey[i] = key[i % key.length];
        }

        for (int i = 0; i < message.length(); i++) {
            char encryptedChar = alphabet[charToInteger(message.charAt(i))][numericKey[i]];
            messageEncrypted += encryptedChar;
        }

        return messageEncrypted;
    }

    public String decrypt(String encryptedMessage) {
        String messageDecrypted = "";
        int[] numericKey = new int[encryptedMessage.length()];
        for (int i = 0; i < encryptedMessage.length(); i++) {
            numericKey[i] = key[i % key.length];
        }

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char encryptChar = encryptedMessage.charAt(i);
            int keyIndex = numericKey[i];

            for (char[] chars : alphabet) {
                if (chars[keyIndex] == encryptChar) {
                    messageDecrypted += chars[0];
                    break;
                }
            }
        }

        return messageDecrypted;
    }

    public void reEncrypt() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el mensaje encriptado: ");
        String encrypt = sc.nextLine();
        String decrypt = decrypt(encrypt);
        System.out.print("Ingrese la nueva Clave: ");
        String newKey = sc.nextLine();
        key = new int[newKey.length()];
        for (int i = 0; i < newKey.length(); i++) {
            key[i] = charToInteger(newKey.charAt(i));
        }
        String newEncrypt = encrypt(decrypt);
        System.out.println("Nueva encriptacion:" + newEncrypt);
    }

    public char search(int position) {
        int index = 0;
        for (int j = 0; j < alphabet.length; j++) {
            for (int k = 0; k < alphabet.length; k++) {
                if (index++ == position) return alphabet[j][k];
            }
        }
        return 'a';
    }

    public char optimalSearch(int position) {
        int j = position / 64;
        int k = position % 64;
        return alphabet[j][k];
    }

    public static void main(String[] args) {
        BigVegenere bigVegenere = new BigVegenere("ADñ901EJ");
        String message = "Hola";
        String encrypted = bigVegenere.encrypt(message);
        System.out.println("Mensaje original: " + message);
        System.out.println("Mensaje cifrado: " + encrypted);
        String decrypted = bigVegenere.decrypt(encrypted);
        System.out.println("Mensaje descifrado: " + decrypted);
        //bigVegenere.reEncrypt();
        System.out.println(bigVegenere.search(127));

        System.out.println(bigVegenere.optimalSearch(127));
    }
}