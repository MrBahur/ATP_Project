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

    /**
     * Sets a byte[] using the information read from the input Stream
     * @param b The byte array to be set
     * @return
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException {

        Map<Integer, ArrayList<Byte>> dictionary = new HashMap();

        int numOfBytesPerIndex = 0;
        int currentIndex = 1;

        while (in.available() != 0) {

            if (currentIndex == 1) { //The first byte from the inputStream indicates the size of each index
                numOfBytesPerIndex = in.read();
            }

            Integer previousIndex = previousIndex(numOfBytesPerIndex);
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

        setBytesArray(dictionary,b,currentIndex);

        return 0;
    }

    /**
     * Calculates the value of the index using the information read from the input stream
     * @param numOfBytesPerIndex Indication of how many bytes are needed in order to get the value of the index
     * @return the value of the index
     * @throws IOException
     */
    public Integer previousIndex(Integer numOfBytesPerIndex) throws IOException{

        Integer previousIndex;
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
        return previousIndex;
    }

    /**
     * Sets the bytes Array
     * @param dictionary Where all the patterns are kept (the key indicates their turn to be added to the final byte[])
     * @param b The Array to be set
     * @param currentIndex The number of patterns that are kept in the dictionary
     *                    (which is equal to the number of bytes in b)
     */
    public void setBytesArray(Map<Integer, ArrayList<Byte>> dictionary, byte[] b, int currentIndex){

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

