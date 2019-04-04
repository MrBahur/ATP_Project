package algorithms.mazeGenerators;

public class Position {
    private int rowIndex;
    private int columnIndex;

    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public String toString() {
        return "{" + rowIndex + "," + columnIndex + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position))
            return false;
        Position other = (Position) obj;
        return other.columnIndex == columnIndex & other.rowIndex == rowIndex;
    }
}
