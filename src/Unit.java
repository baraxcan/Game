import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class Unit {
    private int x;
    private int y;
    Deque<int[]> path = new ArrayDeque<>();
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

    public void move(Area area) {
        if (path.isEmpty()) return;
        int[] temp = path.pop();
        int tx = temp[0], ty = temp[1];
        if (Math.abs(x - tx) > Math.abs(y - ty)) {
            if (x < tx) {
                if (!area.checkWall(x + 1, y) && !area.checkUnit(x + 1, y)) {
                    ++x;
                }
            }
            else if (x > ty) {
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
    }

    public void setToCoor(int tx, int ty, Area area) {
        int[][] pos = new int[][]{
                {-1, 0},
                {0, -1},
                {1, 0},
                {0, 1}
        };
        Queue<int[]> q = new ArrayDeque<>();
        int[][] field = area.getField();
        int[][][] p = new int[field.length][field[0].length][2];
        for (int[][] matrix : p) {
            for (int[] row : matrix) {
                Arrays.fill(row, -1);
            }
        }
        int[][] dist = new int[field.length][field[0].length];
        for (int[] row : dist) {
            Arrays.fill(row, 1000000000);
        }
        dist[y][x] = 0;
        q.offer(new int[]{x, y});
        while (!q.isEmpty()) {
            int[] v = q.poll();
            for (int[] a : pos) {
                if (v[0] + a[0] >= 0 && v[1] + a[1] >= 0 &&
                    v[0] + a[0] < area.getCols() && v[1] + a[1] < area.getRows()) {
                    int[] u = new int[]{v[0] + a[0], v[1] + a[1]};
                    if (dist[u[1]][u[0]] > dist[v[1]][v[0]] + 1 &&
                        !area.checkWall(u[0], u[1]) && !area.checkUnit(u[0], u[1])) {
                        p[u[1]][u[0]] = v;
                        dist[u[1]][u[0]] = dist[v[1]][v[0]] + 1;
                        q.offer(u);
                    }
                }
            }
        }
        if (dist[ty][tx] == 1000000000) {
            path.clear();
            return;
        }

        while (tx != -1 && ty != -1) {
            path.push(new int[]{tx, ty});
            int oldTx = tx;
            tx = p[ty][tx][0];
            ty = p[ty][oldTx][1];
        }
        System.out.println("check!");
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
