package IO;

import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {

        Map<Integer, ArrayList<Byte>> dictionary = new HashMap();

        int numOfBytesPerIndex = 0;
        int currentIndex = 1;

        while (in.available() != 0) {

            Integer previousIndex;
            if (currentIndex == 1) {
                numOfBytesPerIndex = in.read();
            }

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
            Integer extraByte = in.read();

            ArrayList<Byte> newPattern = new ArrayList<>();

            if (previousIndex != 0) {

                ArrayList<Byte> previousPattern = dictionary.get(previousIndex);

                for (int i = 0; i < previousPattern.size(); i++) {
                    newPattern.add(previousPattern.get(i));
                }
            }
            newPattern.add(extraByte.byteValue());
            dictionary.put(currentIndex, newPattern);

            currentIndex++;
        }

        int i = 0;
        for (int j = 1; j < currentIndex; j++) {
            for (int k = 0; k < dictionary.get(j).size(); k++) {
                if (i < b.length) {
                    b[i] = dictionary.get(j).get(k);
                    i++;
                }

            }
        }

        return 0;
    }
}


//    public int intValue(Integer part1, Integer part2) {
//
//        int rest = part1;
//        int result = 0;
//
//        for (int i = 7; i >= 0; i--) {
//            if (rest >= Math.pow(2, i)) {
//                result += Math.pow(2, i);
//                rest -= Math.pow(2, i);
//            }
//        }
//
//        rest = part2;
//        for (int i = 7; i >= 0; i--) {
//            if (rest >= Math.pow(2, i)) {
//                result += Math.pow(2, i + 8);
//                rest -= Math.pow(2, i);
//            }
//        }
//        return result;
//    }

