package algorithms.mazeGenerators;

public class Maze {

    private int rows;
    private int columns;
    private int[][] maze;
    private Position startPosition;
    private Position goalPosition;

    /**
     * Constructor for maze
     *
     * @param rows    the number of rows in the maze
     * @param columns the number of columns in the maze
     */
    public Maze(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        maze = new int[rows][columns];
    }

    /**
     * Constructor for maze that build a maze from non compassed byte array
     *
     * @param b the non compressed byte array
     */
    public Maze(byte[] b) {
        int sizeOfData = b[0] & 0xFF;      //we are saving the size of raw data in b[0]
        // b[i] & 0xFF stands for changing from signed byte to unsigned int
        /*
         * retrieving raw data from the byte array in this order:
         * 0. rows
         * 1. columns
         * 2. startPosition X
         * 3. startPosition Y
         * 4. goalPosition X
         * 5. goalPosition Y
         */
        this.rows = retrieveDataFromByteArray(b, 0);
        this.columns = retrieveDataFromByteArray(b, 1);
        this.setStartPosition(new Position(retrieveDataFromByteArray(b, 2), retrieveDataFromByteArray(b, 3)));
        this.setGoalPosition(new Position(retrieveDataFromByteArray(b, 4), retrieveDataFromByteArray(b, 5)));
        this.maze = new int[rows][columns];
        int start = 6 * sizeOfData + 1; // first index that shows maze cells info
        int[] arrayOfMaze = new int[8 * (b.length - start)];
        for (int i = 0; i < arrayOfMaze.length; i++) {
            arrayOfMaze[i] = -1;
        }
        int index = 0;
        for (int i = start, j = 0, k = 0; i < b.length - 1; i++) {
            int x = b[i] & 0xFF;
            index = i;
            for (int l = 7; l >= 0; l--) {
                arrayOfMaze[(i - start) * 8 + l] = x % 2;
                x /= 2;
            }
        }
        int i = 0;
        for (int j = 0; j < rows; j++) {
            for (int k = 0; k < columns; k++) {
                maze[j][k] = arrayOfMaze[i];
                i++;
            }
        }
        //counting the remaining cells to fill
        int count = 0;
        for (int j = 0; j < columns; j++) {
            if (maze[rows - 1][j] == -1) {
                count++;
            }
        }
        //filling the remaining cells
        int x = b[index + 1] & 0xFF;
        if (count != 8) {
            x /= 2;
        }
        for (int j = 0; j < count; j++) {
            maze[rows - 1][columns - 1 - j] = x % 2;
            x /= 2;
        }
    }

    /**
     * retrieve data from the byte representation of the maze
     *
     * @param byteArray the byte representation
     * @param index     the index from it you want to retrieve the data
     *                  b[0] = sizeOfData, the size in byte of the data we want to recover
     * @return the original data
     */
    private int retrieveDataFromByteArray(byte[] byteArray, int index) {
        int sizeOfData = byteArray[0];
        int data = 0;
        for (int i = (index) * sizeOfData + 1; i <= (index + 1) * sizeOfData; i++) {
            int b = byteArray[i] & 0xFF;
            // byteArray[i] & 0xFF stands for changing from signed byte to unsigned int
            data *= 256;
            data += b;
        }

        return data;
    }

    //region Getters
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int[][] getMaze() {
        return maze;
    }

    public Position getStartPosition() {
        return startPosition;
    }
    //endregion

    //region Setters
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        this.goalPosition = goalPosition;
    }
    //endregion

    /**
     * Prints the maze
     */
    public void print() {
        for (int i = 0; i < rows; i++) {
            System.out.print("[ ");
            for (int j = 0; j < columns; j++) {
                if (i == startPosition.getRowIndex() & j == startPosition.getColumnIndex()) {
                    System.out.print("S ");
                } else if (i == goalPosition.getRowIndex() & j == goalPosition.getColumnIndex()) {
                    System.out.print("E ");
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println("]");
        }
    }

    /**
     * function that represent the maze as an byte array (not compressed)
     *
     * @return the maze details, not compressed.
     */
    public byte[] toByteArray() {
        double numOfSquares = rows * columns; //calculate the amount of cells in the maze
        double numOfBitsForRows = Math.ceil(Math.log(rows + 1) / Math.log(2)); //num of *bits* to display the row amount
        int numOfBytesForRows = (int) Math.ceil(numOfBitsForRows / 8); //num of *bytes* to display the row amount
        double numOfBitsForColumns = Math.ceil(Math.log(columns + 1) / Math.log(2)); //num of *bits* to display the cols amount
        int numOfBytesForColumns = (int) Math.ceil(numOfBitsForColumns / 8); //num of *bytes* to display the cols amount
        int sizeOfData = Math.max(numOfBytesForRows, numOfBytesForColumns); //calculate sizeOfData (in bytes)
        byte[] byteArray = new byte[1 + 6 * sizeOfData + (int) Math.ceil(numOfSquares / 8)]; //creating the byte array
        byteArray[0] = (byte) sizeOfData; // saving the size of data in the first cell
        /*
         *inserting all the raw data to create the maze:
         * 0. rows
         * 1. columns
         * 2. startPosition X
         * 3. startPosition Y
         * 4. goalPosition X
         * 5. goalPosition Y
         */
        insertDataIntoByteArray(byteArray, rows, 0, sizeOfData);
        insertDataIntoByteArray(byteArray, columns, 1, sizeOfData);
        insertDataIntoByteArray(byteArray, getStartPosition().getRowIndex(), 2, sizeOfData);
        insertDataIntoByteArray(byteArray, getStartPosition().getColumnIndex(), 3, sizeOfData);
        insertDataIntoByteArray(byteArray, getGoalPosition().getRowIndex(), 4, sizeOfData);
        insertDataIntoByteArray(byteArray, getGoalPosition().getColumnIndex(), 5, sizeOfData);
        /*
         *inserting the maze itself, each 8 cells in 1 byte as int from 0 to 255
         */
        for (int i = 6 * sizeOfData, k = 0; i < byteArray.length - 1; i += 1, k += 8) {
            int toInsert = 0;
            for (int j = 0; j < 8; j++) {
                toInsert *= 2;
                int toAdd = nextCell(k + j);
                if (toAdd == -1) {
                    break;
                }
                toInsert += toAdd;
            }
            insertDataIntoByteArray(byteArray, toInsert, i, 1);
        }
        return byteArray;
    }

    /**
     * given index, returning the relevant cell in the maze
     *
     * @param index the index
     * @return the value of the cell
     */
    private int nextCell(int index) {
        int row = index / columns;
        int col = index % columns;
        if (row == rows) {
            return -1;
        }
        return maze[row][col];
    }

    /**
     * inserting data into the byte array at specific index, given specific sizeOfData
     *
     * @param byteArray  the byteArray to insert into
     * @param data       the data we want to insert into the byte array
     * @param index      the index of the data that we want to insert to the byteArray
     * @param sizeOfData the size of the data we want to insert (1 for maze cells, byteArray[0] for raw data)
     */
    private void insertDataIntoByteArray(byte[] byteArray, int data, int index, int sizeOfData) {
        int[] dataInBinary = new int[sizeOfData * 8];
        //creating binary representation for the data
        for (int i = dataInBinary.length - 1; i >= 0; i--) {
            dataInBinary[i] = data % 2;
            data /= 2;
        }
        //changing to base 256 inorder to insert the data into 1 byte
        for (int i = 0; i < sizeOfData; i++) {
            int toInsert = 0;
            for (int j = 0; j < 8; j++) {
                toInsert *= 2;
                toInsert += dataInBinary[i * 8 + j];
            }
            byteArray[1 + index * sizeOfData + i] = (byte) toInsert;
        }
    }
}
