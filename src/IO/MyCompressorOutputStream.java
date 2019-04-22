package IO;

import javafx.util.Pair;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    //TODO implement and document
    @Override
    public void write(int b) throws IOException {

    }

    //TODO implement and document
    @Override
    public void write(byte[] b) {

        if (b == null) {
            throw new NullPointerException();
        }
        else {

            ArrayList<Pair<ArrayList<Byte>, Integer>> dictionary = new ArrayList<>();
            //Pairs of:
            //Patterns - every new pattern is being added to the dictionary
            //Indexes - of the longest pattern which is contained in the new pattern. - if there is no
            //such pattern the index equals to 0
            ArrayList<Pair<Integer, Byte>> resultPairs = new ArrayList<>();
            //The final result  - pairs of the previous pattern index and the additional byte which
            //together create a new pattern
            dictionary.add(new Pair<>(null, 0)); //The first pattern (default)
            int i = 0;
            ArrayList<Byte> newPattern;
            int previousPatternIndex;

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

                Pair newPair = new Pair(newPattern, previousPatternIndex);
                dictionary.add(newPair);
                Pair newResultPair = new Pair(previousPatternIndex, b[i]);
                resultPairs.add(newResultPair);
                i++;
            }

            try { //outputs the result
                for (i = 0; i < resultPairs.size(); i++) {

                    Pair res = resultPairs.get(i);
                    System.out.println(res.toString());
                    out.write(res.toString().getBytes());
                    out.write(" ".getBytes());
                }
            }
            catch(IOException e){
                e.getMessage();
            }
        }
        return;
    }

    public int indexOf(ArrayList<Pair<ArrayList<Byte>, Integer>> dictionary, ArrayList<Byte> pattern) {

        for (int i = 1; i < dictionary.size(); i++) {
            Pair p = dictionary.get(i);
            ArrayList<Byte> toCompare = (ArrayList<Byte>) p.getKey();
            if (toCompare.size() == pattern.size()) {
                for (int j = 0; j < toCompare.size(); j++) {
                    if (!(toCompare.get(j).equals(pattern.get(j)))) {
                        break;
                    }
                    if (j == toCompare.size() - 1) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}