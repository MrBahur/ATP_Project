package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;
    private Map<Integer, ArrayList<Byte>> dictionary;
    private int numOfBytesPerIndex;
    private int currentIndex;
    private int previousIndex;
    private int extraByte;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        dictionary = null;
        numOfBytesPerIndex = 0;
        currentIndex = 0;
        previousIndex = 0;
        extraByte = 0;

    }
    @Override
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Sets a byte[] using the information read from the input Stream
     *
     * @param b The byte array to be set
     * @return **daniel please write what it's returns**
     * @throws IOException because of unchecked call to in.read
     */
    @Override
    public int read(byte[] b) throws IOException {
        //initializing fields (every time we call this function)
        dictionary = new HashMap<>();
        ArrayList<Byte> newPattern = null;
        currentIndex = 1;
        numOfBytesPerIndex = 0;
        previousIndex = 0;
        extraByte = 0;

        while (in.available() != 0) {
            if (currentIndex == 1) { //The first byte from the inputStream indicates the size of each index
                numOfBytesPerIndex = in.read();
            }
            previousIndex = previousIndex(numOfBytesPerIndex);
            extraByte = in.read();
            newPattern = new ArrayList<>();
            if (previousIndex != 0) {

                ArrayList<Byte> previousPattern = dictionary.get(previousIndex);
                newPattern.addAll(previousPattern);
            }
            newPattern.add((byte) extraByte);
            dictionary.put(currentIndex, newPattern);
            //Add the pattern and the index that indicates its turn to be added to the array

            currentIndex++;
        }
        setBytesArray(dictionary, b, currentIndex);
        return 0;
    }

    /**
     * Calculates the value of the index using the information read from the input stream
     *
     * @param numOfBytesPerIndex Indication of how many bytes are needed in order to get the value of the index
     * @return the value of the index
     */
    private Integer previousIndex(Integer numOfBytesPerIndex) {
        Integer previousIndex = null;
        try {
            if (numOfBytesPerIndex == 1) {
                previousIndex = in.read();
            } else if (numOfBytesPerIndex == 2) {
                int mul = in.read();
                int remainder = in.read();
                previousIndex = (255 * mul + remainder);
            } else { //numOfBytesPerIndex==3
                int firstMultiplication = in.read();
                int secondMultiplication = in.read();
                int remainder = in.read();
                previousIndex = ((65025 * firstMultiplication) + (255 * secondMultiplication) + remainder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return previousIndex;
    }

    /**
     * Sets the bytes Array
     *
     * @param dictionary   Where all the patterns are kept (the key indicates their turn to be added to the final byte[])
     * @param b            The Array to be set
     * @param currentIndex The number of patterns that are kept in the dictionary
     *                     (which is equal to the number of bytes in b)
     */
    private void setBytesArray(Map<Integer, ArrayList<Byte>> dictionary, byte[] b, int currentIndex) {
        int i = 0;
        for (int j = 1; j < currentIndex; j++) {
            for (int k = 0; k < dictionary.get(j).size(); k++) {
                if (i < b.length) {
                    b[i] = dictionary.get(j).get(k);
                    i++;
                }
            }
        }
    }
}
