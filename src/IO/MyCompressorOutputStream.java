package IO;

import javafx.util.Pair;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }


    @Override
    public void write(int b) throws IOException {

        out.write(((Integer) b).byteValue());
    }

    public void write(int b, int numOfBytesPerIndex) throws IOException {

        if (numOfBytesPerIndex == 1) {
            write(b);
        }
        else if (numOfBytesPerIndex == 2) {
            write(b / 255); //multiply by
            write(b % 255); //remainder
        }
        else if (numOfBytesPerIndex == 3) {
            write(b / 65025); //65025 multiply by
            write((b % 65025) / 255); //first remainder multiply by
            write((b % 65025) % 255); //second remainder

        }
    }

//    @Override
//    public void write(int b) throws IOException {
//
//        int rest = b;
//        Integer char1 = 0, char2 = 0;
//        int[] binaryRepresentation = new int[16];
//        byte[] result = new byte[2];
//
//        for (int i = 15; rest > 0; i--) {
//            if ((Math.pow(2, i)) <= rest) {
//                binaryRepresentation[i] = 1;
//                rest -= (Math.pow(2, i));
//            }
//        }
//        int j = 0;
//        for (; j < 8; j++) {
//            if (binaryRepresentation[j] == 1) {
//                char1 = +(int) (Math.pow(2, j));
//            }
//        }
//        for (; j < 16; j++) {
//            if (binaryRepresentation[j] == 1) {
//                char2 = +(int) (Math.pow(2, j - 8));
//            }
//        }
//        byte c1 = char1.byteValue();
//        result[0] = c1;
//        byte c2 = char2.byteValue();
//        result[1] = c2;
//
//        out.write(result[0]);
//        out.write(result[1]);
//    }

    @Override
/**
 * Compresses the given array and writes the compressed array into this output stream
 */
    public void write(byte[] b) throws IOException {

        if (b == null) {
            throw new NullPointerException();
        } else {

            HashMap<ArrayList<Byte>, Pair<Integer, Integer>> dictionary = new HashMap();
            /*pattern, pair<index,previous index>
             HashMap of:
             * Patterns - every new pattern is being added to the dictionary
             * Pairs:
             - the index of the new pattern in the dictionary
             - the index of the longest pattern which is contained in the new pattern. - if there is no
            such pattern the index equals to 0. */
            ArrayList<Pair<Integer, Byte>> resultPairs = new ArrayList<>();
            /*The final result  - pairs of the previous pattern index and the additional byte which
            together create a new pattern. */
            dictionary.put(null, new Pair(0, 0)); //The first pattern (default)
            int i = 0;
            ArrayList<Byte> newPattern;
            int previousPatternIndex;
            int currentPatternIndex = 1;
            int numOfBytesForIndex = 1;

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
                if (currentPatternIndex > 255) {
                    numOfBytesForIndex = 2;
                }
                if (currentPatternIndex > 65025) {
                    numOfBytesForIndex = 3;
                }
                Pair newResultPair = new Pair(previousPatternIndex, b[i]);


                resultPairs.add(newResultPair);
                i++;
            }

            writeToOutput(resultPairs, numOfBytesForIndex);
        }
        return;
    }

    /**
     * checks if the given pattern exist in the given dictionary
     *
     * @param dictionary where the pattern is being searched
     * @param pattern    the checked pattern
     * @return if the pattern exists in the dictionary - returns its index in the dictionary.
     * else, returns -1
     */
    public int indexOf(HashMap<ArrayList<Byte>, Pair<Integer, Integer>> dictionary, ArrayList<Byte> pattern) {


        if (dictionary.get(pattern) == null) return -1;

        return dictionary.get(pattern).getKey();
    }

    public void writeToOutput(ArrayList<Pair<Integer, Byte>> resultPairs, int numOfBytesPerIndex) throws IOException {

        out.write(((Integer)numOfBytesPerIndex).byteValue());

        for (int i = 0; i < resultPairs.size(); i++) {

            Pair res = resultPairs.get(i);

            write((int) res.getKey(), numOfBytesPerIndex);
            out.write((byte) res.getValue());
        }
    }
}