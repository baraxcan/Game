public class Area {
    private int cols = 10, rows = 10;
    private int sizeX = 50, sizeY = 50;
    private int left = 50, top = 50;

    private int[][] field;

    Area() {
        createField();
    }

    Area(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        createField();
    }

    private void createField() {
        field = new int[rows][cols];
        for (int i = 0; i < rows; ++i) {
            int[] temp = new int[cols];
            field[i] = temp;
        }
    }

    public int getCols() {
        return cols;
    }
    public int getRows() {
        return rows;
    }

    public int[][] getField() {
        int[][] fieldCopy = new int[field.length][];
        for (int i = 0; i < field.length; ++i) {
            int[] temp = new int[field[i].length];
            for (int j = 0; j < field[i].length; ++j) {
                temp[j] = field[i][j];
            }
            fieldCopy[i] = temp;
        }
        return fieldCopy;
    }

    public boolean checkWall(int x, int y) {
        return field[y][x] == 1;
    }

    public boolean checkUnit(int x, int y) {
        return field[y][x] == 2;
    }

    public void setUnit(int x, int y) {
        field[y][x] = 2;
    }

    public void setWall(int x, int y) {
        field[y][x] = 1;
    }

    public void unsetPlace(int x, int y) {
        field[y][x] = 0;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }
}
