package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable{
    private int rowIndex;
    private int columnIndex;

    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    //region Getters
    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
    //endregion


    /**
     * Overriding toString for Position
     */
    @Override
    public String toString() {
        return "{" + rowIndex + "," + columnIndex + "}";
    }

    /**
     * Overriding equals for Position
     *
     * @param obj should be Position
     * @return true if obj is Position and both the positions have same coordinates
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position))
            return false;
        Position other = (Position) obj;
        return other.columnIndex == columnIndex & other.rowIndex == rowIndex;
    }
}
