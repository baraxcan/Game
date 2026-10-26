public class Queen extends Unit {
    private final int[][] pos = {
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, -1},
            {-1, 1},
            {1, 1},
            {1, -1}
    };

    public Queen(int x, int y, Area area) {
        super(x, y, area);
    }

    @Override
    public void setToCoor(int tx, int ty) {
        setPath(Algorithms.BFSforFigure(getX(), getY(), tx, ty, getArea(), pos));
    }
}
