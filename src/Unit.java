import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

public class Unit {
    private int x;
    private int y;
    private int life = 10;
    Stack<int[]> path = new Stack<>();
    private boolean isActive = false;
    public Unit(int x, int y) {
        this.x = x;
        this.y = y;
    }

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
        if (path.isEmpty()) return;
        int[] temp = path.pop();
        int tx = temp[0], ty = temp[1];
        if (Math.abs(x - tx) > Math.abs(y - ty)) {
            if (x < tx) {
                if (!area.checkWall(x + 1, y) && !area.checkUnit(x + 1, y)) {
                    ++x;
                }
            }
            else if (x > tx) {
                if (!area.checkWall(x - 1, y) && !area.checkUnit(x - 1, y)) {
                    --x;
                }
            }
        }
        else {
            if (y < ty) {
                if (!area.checkWall(x, y + 1) && !area.checkUnit(x, y + 1)) {
                    ++y;
                }
            }
            else if (y > ty) {
                if (!area.checkWall(x, y - 1) && !area.checkUnit(x, y - 1)) {
                    --y;
                }
            }
        }
        lifeReduction();
    }

    public void setToCoor(int tx, int ty, Area area) {
        path = Algorithms.BFS(x, y, tx, ty, area);
        if (path.empty()) {
            System.out.println("Путь не найден");
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
