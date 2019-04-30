package IO;

import javafx.util.Pair;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    private HashMap<ArrayList<Byte>, Pair<Integer, Integer>> dictionary;
    /*pattern, pair<index,previous index>
             HashMap of:
             * Patterns - every new pattern is being added to the dictionary
             * Pairs:
             - the index of the new pattern in the dictionary
             - the index of the longest pattern which is contained in the new pattern. - if there is no
            such pattern the index equals to 0. */

    private ArrayList<Pair<Integer, Byte>> resultPairs;
    /*The final result  - pairs of the previous pattern index and the additional byte which
    together create a new pattern. */
    private int numOfBytesForIndex;
    private int previousPatternIndex;
    private int currentPatternIndex;


    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
        dictionary = null;
        resultPairs = null;
        numOfBytesForIndex = 0;
        previousPatternIndex = 0;
        currentPatternIndex = 0;

    }


    @Override
    public void write(int b) throws IOException {
        out.write((byte) b);
    }

    /**
     * Sets an Integer to be written to the output stream
     *
     * @param b                  The original value
     * @param numOfBytesPerIndex the number of bytes needed in order to write a representation
     *                           of the value
     * @throws IOException because of unchecked call to write
     */
    private void write(int b, int numOfBytesPerIndex) throws IOException {

        if (numOfBytesPerIndex == 1) {
            write(b);
        } else if (numOfBytesPerIndex == 2) {
            write(b / 255); //multiply by
            write(b % 255); //remainder
        } else if (numOfBytesPerIndex == 3) {
            write(b / 65025); //65025 multiply by
            write((b % 65025) / 255); //first remainder multiply by
            write((b % 65025) % 255); //second remainder

        }
    }

    /**
     * Compresses the given array and writes the compressed array into this output stream
     *
     * @param b the given byte array
     */

    @Override
    public void write(byte[] b) {
        //initializing fields (every time we call this function)
        dictionary = new HashMap<>();
        resultPairs = new ArrayList<>();
        currentPatternIndex = 1;
        numOfBytesForIndex = 1;
        ArrayList<Byte> newPattern = null;

        if (b == null) {
            throw new NullPointerException();
        } else {
            dictionary.put(null, new Pair<Integer, Integer>(0, 0)); //The first pattern (default)
            int i = 0;
            while (i < b.length) {
                previousPatternIndex = 0;
                newPattern = new ArrayList<>();
                newPattern.add(b[i]);
                while (indexOf(dictionary, newPattern) != -1) { //The pattern is in the list
                    previousPatternIndex = indexOf(dictionary, newPattern);
                    if (i == b.length - 1) {
                        break;
                    }
                    i++;
                    newPattern.add(b[i]);
                }
                dictionary.put(newPattern, new Pair<>(currentPatternIndex, previousPatternIndex));
                currentPatternIndex++;
                if (currentPatternIndex > 255) {//For mazes of about 100*100 or bigger
                    numOfBytesForIndex = 2;
                }
                if (currentPatternIndex > 65025) {//For mazes of about 1300*1300 or bigger
                    numOfBytesForIndex = 3;
                }
                Pair<Integer, Byte> newResultPair = new Pair<>(previousPatternIndex, b[i]);
                resultPairs.add(newResultPair);
                i++;
            }

            writeToOutput(resultPairs, numOfBytesForIndex);

        }
    }

    /**
     * checks if the given pattern exist in the given dictionary
     *
     * @param dictionary where the pattern is being searched
     * @param pattern    the checked pattern
     * @return if the pattern exists in the dictionary - returns its index in the dictionary.
     * else, returns -1
     */
    private int indexOf(HashMap<ArrayList<Byte>, Pair<Integer, Integer>> dictionary, ArrayList<Byte> pattern) {


        if (dictionary.get(pattern) == null) return -1;

        return dictionary.get(pattern).getKey();
    }

    private void writeToOutput(ArrayList<Pair<Integer, Byte>> resultPairs, int numOfBytesPerIndex) {
        try {
            write(((Integer) numOfBytesPerIndex).byteValue());
            for (int i = 0; i < resultPairs.size(); i++) {
                Pair<Integer, Byte> res = resultPairs.get(i);
                write(res.getKey(), numOfBytesPerIndex);
                write(res.getValue() & 0xFF);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}