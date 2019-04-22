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
        int sizeOfData = b[0];
        this.rows = retrieveDataFromByteArray(b, 0);
        this.columns = retrieveDataFromByteArray(b, 1);
        this.setStartPosition(new Position(retrieveDataFromByteArray(b, 2), retrieveDataFromByteArray(b, 3)));
        this.setGoalPosition(new Position(retrieveDataFromByteArray(b, 4), retrieveDataFromByteArray(b, 5)));
        this.maze = new int[rows][columns];
        int start = 6 * sizeOfData;
        for (int i = start + 1, j = 0, k = 0; i < b.length; i++) {
            fillNextEightCells(b[i] & 0xFF, i - start - 1);
        }
    }

    private void fillNextEightCells(int data, int index) {
        for (int i = 7; i >= 0; i--) {
            int row = getRowIndex(index*8 + i);
            int col = getColsIndex(index*8 + i);
            if(row>= rows)
                return;
            maze[row][col] = data%2;
            data/=2;
        }
    }

    private int getColsIndex(int index) {
        return index%columns;
    }

    private int getRowIndex(int index) {
        return index/columns;
    }

    private int retrieveDataFromByteArray(byte[] byteArray, int index) {
        int sizeOfData = byteArray[0];
        int data = 0;
        for (int i = (index + 1) * sizeOfData; i > index * sizeOfData; i--) {
            int b = byteArray[i] & 0xFF;
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
        double numOfSquares = rows * columns;
        double numOfBitsForRows = Math.ceil(Math.log(rows + 1) / Math.log(2));
        int numOfBytesForRows = (int) Math.ceil(numOfBitsForRows / 8);
        double numOfBitsForColumns = Math.ceil(Math.log(columns + 1) / Math.log(2));
        int numOfBytesForColumns = (int) Math.ceil(numOfBitsForColumns / 8);
        int sizeOfData = Math.max(numOfBytesForRows, numOfBytesForColumns);
        byte[] byteArray = new byte[1 + 6 * sizeOfData + (int) Math.ceil(numOfSquares / 8)];
        byteArray[0] = (byte) sizeOfData;
        insertDataIntoByteArray(byteArray, rows, 0, sizeOfData);
        insertDataIntoByteArray(byteArray, columns, 1, sizeOfData);
        insertDataIntoByteArray(byteArray, getStartPosition().getRowIndex(), 2, sizeOfData);
        insertDataIntoByteArray(byteArray, getStartPosition().getColumnIndex(), 3, sizeOfData);
        insertDataIntoByteArray(byteArray, getGoalPosition().getRowIndex(), 4, sizeOfData);
        insertDataIntoByteArray(byteArray, getGoalPosition().getColumnIndex(), 5, sizeOfData);
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

    private int nextCell(int num) {
        int row = num / columns;
        int col = num % columns;
        if (row == rows) {
            return -1;
        }
        return maze[row][col];
    }

    private void insertDataIntoByteArray(byte[] byteArray, int data, int index, int sizeOfData) {
        int[] dataInBinary = new int[sizeOfData * 8];
        for (int i = dataInBinary.length - 1; i >= 0; i--) {
            dataInBinary[i] = data % 2;
            data /= 2;
        }
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
