import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

public abstract class Unit {
    private int x;
    private int y;
    private int life = 10;
    private Area area = null;
    Stack<int[]> path = new Stack<>();
    private boolean isActive = false;
    public Unit(int x, int y, Area area) {
        this.x = x;
        this.y = y;
        this.area = area;
    }
    public int tx = -1;
    public int ty = -1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLife() {
        return life != 0;
    }

    public void lifeReduction() {
        if (isLife())
            --life;
    }

    public int getLife() {
        return life;
    }

    public boolean checkUnitNearby(Unit unit) {
        if (Math.abs(unit.getX() - this.x) == 1 && Math.abs(unit.getY() - this.y) == 0 ||
                Math.abs(unit.getX() - this.x) == 0 && Math.abs(unit.getY() - this.y) == 1) {
            return true;
        }
        return false;
    }

    public void move(Area area) {
        if (!isLife()) {
            return;
        }
        if (path.isEmpty()) {
            tx = -1;
            ty = -1;
            return;
        };
        int[] temp = path.pop();
        x = temp[0]; y = temp[1];
        setToCoor(tx, ty);
    }

    public abstract void setToCoor(int tx, int ty);

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Area getArea() {
        return area;
    }

    public void setPath(Stack<int[]> path) {
        this.path = path;
    }

    public void setTarget(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        setToCoor(tx, ty);
    }
}
