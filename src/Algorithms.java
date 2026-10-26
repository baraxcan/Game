import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Stack;

/*
    Ну кароче, в этом классе собираются основные алгоритмы.
    Все методы в нем статические. Так что можно ссылаться
    Algorithms.(название метода). Файл был создан для того
    что бы основные классы не были грамоздкими. Так же это
    сохраняет и расширяет масштабируемость.
 */
public class Algorithms {
    /*
        ну очевидно что он находит короткий путь от точки A в точку B.
        особо объяснять ненадо. Обычный BFS.
    */
    public static Stack<int[]> BFS(int startX, int startY, int targetX, int targetY, Area area) {
        Stack<int[]> path = new Stack<>();
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
        dist[startY][startX] = 0;
        q.offer(new int[]{startX, startY});
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
        if (dist[targetY][targetX] == 1000000000) {
            path.clear();
            return path;
        }

        int currX = targetX;
        int currY = targetY;
        while (currX != -1 && currY != -1) {
            path.push(new int[]{currX, currY});
            int oldTx = currX;
            currX = p[currY][currX][0];
            currY = p[currY][oldTx][1];
        }
        path.pop();
//        System.out.println("check!");
        return path;
    }
    public static Stack<int[]> BFSforFigure(int startX, int startY, int targetX, int targetY, Area area, int[][] pos) {
        Stack<int[]> path = new Stack<>();
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
        dist[startY][startX] = 0;
        q.offer(new int[]{startX, startY});
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
        if (dist[targetY][targetX] == 1000000000) {
            path.clear();
            return path;
        }

        int currX = targetX;
        int currY = targetY;
        while (currX != -1 && currY != -1) {
            path.push(new int[]{currX, currY});
            int oldTx = currX;
            currX = p[currY][currX][0];
            currY = p[currY][oldTx][1];
        }
        path.pop();
//        System.out.println("check!");
        return path;
    }
}
