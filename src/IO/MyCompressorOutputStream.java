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

    //TODO implement and document
    @Override
    public void write(int b) throws IOException {

    }

    @Override
/**
 * Compresses the given array and writes the compressed array into this output stream
 */
    public void write(byte[] b) throws IOException{

        if (b == null) {
            throw new NullPointerException();
        }
        else {

            HashMap<ArrayList<Byte>,Pair<Integer,Integer>> dictionary = new HashMap();
            //pattern, pair<index,previous index>
            //Patterns - every new pattern is being added to the dictionary
            //
            //Indexes - of the longest pattern which is contained in the new pattern. - if there is no
            //such pattern the index equals to 0
            ArrayList<Pair<Integer, Byte>> resultPairs = new ArrayList<>();
            //The final result  - pairs of the previous pattern index and the additional byte which
            //together create a new pattern
            dictionary.put(null, new Pair(0, 0)); //The first pattern (default)
            int i = 0;
            ArrayList<Byte> newPattern;
            int previousPatternIndex;
            int currentPatternIndex = 1;

            while (i < b.length) {

                previousPatternIndex = 0;
                newPattern = new ArrayList<>();
                newPattern.add(b[i]);

                while (indexOf(dictionary, newPattern) != -1) { //The pattern is in the list

                    previousPatternIndex = indexOf(dictionary, newPattern);
                    if (i < b.length - 1) { //Add the next byte to the new pattern
                        i++;
                        newPattern.add(b[i]);
                    } else {
                        //If all the bytes were inserted to the dictionary the last pattern
                        // is being added to the dictionary with a default previousPatternIndex
                        previousPatternIndex = 0;
                        break;
                    }
                }

                dictionary.put(newPattern,new Pair<>(currentPatternIndex, previousPatternIndex));
                currentPatternIndex++;
                Pair newResultPair = new Pair(previousPatternIndex, b[i]);
                resultPairs.add(newResultPair);
                i++;
            }

                for (i = 0; i < resultPairs.size(); i++) {

                    Pair res = resultPairs.get(i);
                    byte keyByte = ((Integer)res.getKey()).byteValue();
                    out.write(keyByte);
                    byte valueByte = (byte)res.getValue();
                    out.write(valueByte);
                }
        }
        return;
    }

    /**
     * checks if the given pattern exist in the given dictionary
     * @param dictionary where the pattern is being searched
     * @param pattern the checked pattern
     * @return if the pattern exists in the dictionary - returns its index in the dictionary.
     * else, returns -1
     */
    public int indexOf(HashMap<ArrayList<Byte>, Pair<Integer, Integer>> dictionary, ArrayList<Byte> pattern) {


        if (dictionary.get(pattern) == null) return -1;

        return dictionary.get(pattern).getKey();
    }

    public char[] convertToChars(Integer intToConvert, Byte byteToConvert){

        return null;
    }
}