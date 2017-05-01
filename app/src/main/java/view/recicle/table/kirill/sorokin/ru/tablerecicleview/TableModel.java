package view.recicle.table.kirill.sorokin.ru.tablerecicleview;

/**
 * Created by Kirill on 28.04.2017.
 */
public class TableModel {
    public static final int EMPTY = 0;
    public static final int HEADER = 1;
    public static final int LEFT = 2;
    public static final int BODY = 3;

    TableCell[][] columns;

    public TableModel() {
    }

    public void setColumns(TableCell[][] columns) {
        this.columns = columns;
    }

    public int size() {
        int size = 0;
        if (columns != null) {
            for (TableCell[] column : columns) {
                size += column.length;
            }
        }
        return size;
    }

    public TableCell get(int position) {
        Cell cell = getCellAtPosition(position);
        return columns[cell.getX()][cell.getY()];
    }

    public Cell getCellAtPosition(int position) {
        int x = position % columns.length;
        int y = position / columns.length;
        return new Cell(x, y);
    }

    public int getType(int position) {
        Cell cell = getCellAtPosition(position);
        if (cell.getY() == 0 && cell.getX() == 0) {
            return EMPTY;
        } else if (cell.getX() == 0) {
            return LEFT;
        } else if (cell.getY() == 0) {
            return HEADER;
        } else {
            return BODY;
        }
    }

    public int getPosition(Cell cell) {
        return cell.getY() * columns.length + cell.getX();
    }

    public int getCountRow() {
        if (columns != null && columns.length > 0) {
            return columns[0].length;
        } else {
            return 0;
        }
    }

    public int getCountColumn() {
        if (columns != null) {
            return columns.length;
        } else {
            return 0;
        }
    }
}
