package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    private HashMap<String, Integer> dictionary;
    private ArrayList<String> patterns;
    private String currentString;
    private int sizeOfKey;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        dictionary = new HashMap<>();
        patterns = new ArrayList<>();
        currentString = "";
        dictionary.put("", 0);
        patterns.add("");
        sizeOfKey = 0;
    }

    public int read(byte[] byteArray) {
        int currentIndex = 0;
        try {
            sizeOfKey = read();
            while (currentIndex < byteArray.length) {
                int[] keyParts = new int[sizeOfKey];
                for (int i = 0; i < sizeOfKey; i++) {
                    keyParts[i] = read();
                }
                int key = calculateKey(sizeOfKey, keyParts);
                int myByte = read();
                String s = patterns.get(key);
                if (!dictionary.containsKey(s + (char) myByte)) {
                    patterns.add(s + (char) myByte);
                    dictionary.put(s + (char) myByte, patterns.size() - 1);
                }
                for (char c : s.toCharArray()) {
                    byteArray[currentIndex] = (byte) c;
                    currentIndex++;
                }
                byteArray[currentIndex] = (byte) myByte;
                currentIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int calculateKey(int size, int[] keyParts) {
        int key = 0;
        for (int i = 0; i <size ; i++) {
            key *= 256;
            key += keyParts[i];
        }
        return key;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }
}
