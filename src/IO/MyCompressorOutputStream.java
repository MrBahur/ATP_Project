package IO;

import javafx.util.Pair;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        //////////////////////cancel this - just for test//////////////////////////////////
        this.out = out;
    }

    public MyCompressorOutputStream() {
    }

    //TODO implement and document
    @Override
    public void write(int b) throws IOException {

    }

    //TODO implement and document
    @Override
    public void write(byte[] b) {

        ArrayList<Pair<ArrayList<Byte>, Integer>> dictionary = new ArrayList<>();
        ArrayList<Pair<Integer, Byte>> resultPairs = new ArrayList<>();
        dictionary.add(new Pair<>(null, 0));
        int i = 0;
        ArrayList<Byte> newPattern;
        int previousPatternIndex;

        while (i < b.length) {

            previousPatternIndex=0;
            newPattern = new ArrayList<>();
            newPattern.add(b[i]);

            while (indexOf(dictionary,newPattern) != -1) { //The pattern is in the list

                previousPatternIndex = indexOf(dictionary,newPattern);
                if (i < b.length - 1) {
                    i++;
                    newPattern.add(b[i]);
                } else {
                    break;
                }
            }

            Pair newPair = new Pair(newPattern, previousPatternIndex);
            dictionary.add(newPair);
            Pair newResultPair = new Pair(previousPatternIndex, b[i]);
            resultPairs.add(newResultPair);
            i++;
        }



        for (i = 0; i < resultPairs.size(); i++) {
            Pair res = resultPairs.get(i);
            System.out.println(res.toString());
           // System.out.println(res.getKey() + " , " + res.getValue());
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
 //   MyCompressorOutputStream test = new MyCompressorOutputStream();
//        byte[] b = {0,0,0,1,0,1,0,1,0,1,0,1,1,1,1,1};
//        test.write(b);
