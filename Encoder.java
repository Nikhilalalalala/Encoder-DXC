import java.util.ArrayList;
import java.util.List;

class Encoder {

    final static char[] referenceTable = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '(', ')', '*', '+', ',', '-', '.', '/' };
    public static void main(String[] args) {
        Encoder e = new Encoder();
        String test1 = "HELLO WORLD"; // given testcase
        String test2 = "HELLO #$% WORLD"; // characters not in the reference table
        String test3 = "ABcd/.123"; // characters near the start and end of reference table and mix the types of char
        String result1 = e.encode(test1);
        String result2 = e.encode(test2);
        String result3 = e.encode(test3);

        System.out.println("\nTest 1: " + result1);
        System.out.println("Decoded 1: " + e.decode(result1));

        System.out.println("\nTest 2: " + result2);
        System.out.println("Decoded 2: " + e.decode(result2));

        System.out.println("\nTest 3: " + result3);
        System.out.println("Decoded 3: " + e.decode(result3));

    }

    /**
     * Encodes the given plaintext using a offset character that is determined randomly
     * @param plaintext the plaintext to encode
     * @return the encoded plaintext
     */
    public String encode(String plaintext) {
        int offsetIndex = (int) (Math.random() * referenceTable.length);
        char offsetChar = referenceTable[offsetIndex];
        char[] encodedChar = new char[plaintext.length() + 1];
        encodedChar[0] = offsetChar;

        for (int i = 1; i < encodedChar.length; i++) {
            char currChar = plaintext.charAt(i - 1);
            encodedChar[i] = getEncodedCharacter(offsetIndex, currChar);
        }

        return new String(encodedChar);
    }

    /**
     * Decodes the encoded text by using the first character as the offset character
     * @param encodedText the text to decode
     * @return the decoded text
     */
    public String decode(String encodedText) {
        char offsetChar = encodedText.charAt(0);
        int offsetIndex = findIndexOf(offsetChar);
        if (offsetIndex == -1) {
            // In the corner case if the decoded text starts with a character that is not in reference table
            // The decoded text is not valid, return empty string
            return "";
        } 

        char[] decodedChar = new char[encodedText.length() - 1];
        for (int i = 1; i < encodedText.length(); i++) {
            char currChar = encodedText.charAt(i);
            decodedChar[i - 1] = getDecodedCharacter(offsetIndex, currChar);
        }

        return new String(decodedChar);
    }

    /**
     * Finds the index of the given character in the reference table
     * @param c the character to find index of
     * @return the index of the character in the reference table, -1 if it does not exist in the reference table
     */
    public static int findIndexOf(char c) {
        for (int i = 0; i < referenceTable.length; i++) {
            if (referenceTable[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Encodes the given plaintext character according to the reference table and offset
     * @param offset The index of the offset character on the reference table
     * @param plaintext The character to encode
     * @return the encoded character
     */
    public char getEncodedCharacter(int offsetIndex, char plaintext) {
        int indexOfPlainText = findIndexOf(plaintext);
        if (indexOfPlainText == -1) {
            return plaintext;
        } else {
            int indexOfEncodedChar = indexOfPlainText + offsetIndex;
            if (indexOfEncodedChar >= referenceTable.length) {
                indexOfEncodedChar = indexOfEncodedChar - referenceTable.length;
            }
            return referenceTable[indexOfEncodedChar];
        }
    }
    
    /**
     * Decodes the given encoded character according to the reference table and offset
     * @param offsetIndex the index of the offset character
     * @param encodedChar the character to decode
     * @return the decoded character
     */
    public char getDecodedCharacter(int offsetIndex, char encodedChar) {
        int indexOfEncodedChar = findIndexOf(encodedChar);
        if (indexOfEncodedChar == -1) {
            // character not in reference table and is not encoded
            return encodedChar;
        } else {
            int indexOfDecodedChar = indexOfEncodedChar - offsetIndex;
            if (indexOfDecodedChar < 0) {
                indexOfDecodedChar = referenceTable.length + indexOfDecodedChar;
            }
            return referenceTable[indexOfDecodedChar];
        }
    }

}