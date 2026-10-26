public class Knight extends Unit {
    private final int[][] pos = {
            {-1, -2},
            {1, -2},
            {2, -1},
            {2, 1},
            {1, 2},
            {-1, 2},
            {-2, 1},
            {-2, -1}
    };

    public Knight(int x, int y, Area area) {
        super(x, y, area);
    }

    @Override
    public void setToCoor(int tx, int ty) {
        setTarget(tx, ty);
        setPath(Algorithms.BFSforFigure(getX(), getY(), tx, ty, getArea(), pos));

    }
}
