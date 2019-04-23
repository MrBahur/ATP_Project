package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;
    private HashMap<String, Integer> dictionary;
    private ArrayList<String> patterns;
    private String currentString;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        dictionary = new HashMap<>();
        patterns = new ArrayList<>();
        dictionary.put("", 0);
        patterns.add("");
        currentString = "";
    }

    //TODO implement and document
    @Override
    public void write(int b) throws IOException {
        if (dictionary.containsKey(currentString + (char) b)) {
            currentString += (char) b;
        } else {
            patterns.add(currentString + (char) b);
            dictionary.put(currentString + (char) b, patterns.size()-1);
            int x = dictionary.get(currentString);
            int[] indexes = splitToBytes(x);
            for (int i = 0; i < indexes.length; i++) {
                out.write(indexes[i]);
            }
            out.write(b);//byte
            currentString = "";
        }
    }

    private int[] splitToBytes(int x) {
        int size = (int) Math.ceil(Math.log(x) / Math.log(256));
        int maxIndexSize = 2;// 3 for huge mazes (bigger then 1400 X 1400, 2 for smaller)
        size = Math.max(size, maxIndexSize);
        if (size == maxIndexSize+1) {
            System.out.println("error");
        }
        int[] toReturn = new int[size];
        for (int i = size - 1; i >= 0; i--) {

            int [] bitArray = new int[8];
            for (int j = 7; j >=0 ; j--) {
                bitArray[j] = x%2;
                x/=2;
            }
            int y = 0;
            for (int j = 0; j < 8; j++) {
                y*=2;
                y+=bitArray[j];
            }
            toReturn[i] = y;
        }
        return toReturn;
    }

    //TODO implement and document
    @Override
    public void write(byte[] byteArray) {
        for (byte b : byteArray) {
            try {
                write(b & 0xFF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (currentString.length() > 0) {
            try {
                char x = currentString.charAt(currentString.length() - 1);
                currentString = currentString.substring(0, currentString.length() - 1);
                int[] indexes = splitToBytes(dictionary.get(currentString));
                out.write(indexes[0]);
                out.write(indexes[1]);
                out.write((int) x);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
