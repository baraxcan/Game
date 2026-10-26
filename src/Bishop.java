public class Bishop extends Unit {
    private final int[][] pos = {
            {-1, -1},
            {-1, 1},
            {1, 1},
            {1, -1}
    };

    public Bishop(int x, int y, Area area) {
        super(x, y, area);
    }

    @Override
    public void setToCoor(int tx, int ty) {
        setPath(Algorithms.BFSforFigure(getX(), getY(), tx, ty, getArea(), pos));
    }
}
