import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game extends JFrame implements Runnable {
    Unit[] units = {
            new Unit(1, 1),
            new Unit(2, 2),
            new Unit(0, 0)
    };
    Area area = new Area();
    int currentMousePosiotionX = -1;
    int currentMousePosiotionY = -1;

    public static void main(String[] args) {
        Game game = new Game();
        game.area.setWall(6, 7);
        game.area.setWall(6, 8);
        game.area.setWall(6, 9);
        game.area.setWall(6, 5);
        game.area.setWall(6, 4);
        game.area.setWall(6, 3);
        game.area.setWall(6, 2);
        game.area.setWall(6, 1);
        game.area.setWall(6, 0);
        game.setDefaultCloseOperation(2);
        for (int i = 0; i < game.units.length; ++i) {
            if (!game.area.checkUnit(game.units[i].getX(), game.units[i].getY())) {
                game.area.setUnit(game.units[i].getX(), game.units[i].getY());
            }
        }
        game.setBounds(100, 100, 1200, 800);
        game.setVisible(true);
        Thread thread = new Thread(game);
        thread.start();
        game.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX(), y = e.getY();
                game.updateMousePosition(x, y);

                for (int i = 0; i < game.units.length; ++i) {
                    Unit unit = game.units[i];
                    if (game.checkMouseCoordinatesIsUnit(unit)) {
                        unit.setActive(true);
                        game.repaint();
                        return;
                    }
                }
                for (int i = 0; i < game.units.length; ++i) {
                    Unit unit = game.units[i];
                    if (unit.isActive()) {
                        unit.setActive(false);
                        unit.setToCoor(game.currentMousePosiotionX, game.currentMousePosiotionY, game.area);
                        game.repaint();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i <= area.getCols(); ++i) {
            g.drawLine(area.getLeft() + i * area.getSizeX(), area.getTop(),
                    area.getTop() + i * area.getSizeX(), area.getTop() + area.getRows() * area.getSizeX());
        }
        g.setColor(Color.RED);
        for (int i = 0; i <= area.getRows(); ++i) {
            g.drawLine(area.getLeft(), area.getTop() + i * area.getSizeY(),
                    area.getLeft() + area.getCols() * area.getSizeY(), area.getTop() + i * area.getSizeY());
        }
    }
    private void drawWall(Graphics g) {
        int[][] field = area.getField();
        g.setColor(Color.GRAY);
        for (int i = 1; i <= area.getRows(); ++i) {
            for (int j = 1; j <= area.getCols(); ++j) {
                if (field[j - 1][i - 1] == 1)
                    g.fill3DRect(area.getLeft() * i,area.getTop() * j,
                            area.getSizeX(), area.getSizeY(), true);
            }
        }
    }
    private void drawClickMousePosition(Graphics g) {
        boolean f = true;
        for (int i = 0; i < units.length; ++i) {
            Unit unit = units[i];
            if (checkMouseCoordinatesIsUnit(unit)) {
                f = false;
            }
        }
        if (f) {
            g.setColor(Color.RED);
            if (currentMousePosiotionY != -1 && currentMousePosiotionX != -1) {
                g.fill3DRect(area.getLeft() + currentMousePosiotionX * area.getSizeX(),
                            area.getTop() + currentMousePosiotionY * area.getSizeY(), area.getSizeX(), area.getSizeY(), true);
            }
        }
    }
    private void drawUnits(Graphics g) {
        g.setColor(Color.YELLOW);
        for (int i = 0; i < units.length; ++i) {
            Unit unit = units[i];
            g.fillOval(area.getLeft() + unit.getX() * area.getSizeX(),
                        area.getTop() + unit.getY() * area.getSizeY(), area.getSizeX(), area.getSizeY());
        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < units.length; ++i) {
            Unit unit = units[i];
            if (unit.isActive()) {
                g.drawOval(area.getLeft() + unit.getX() * area.getSizeX(),
                            area.getTop() + unit.getY() * area.getSizeY(), area.getSizeX(), area.getSizeY());
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        drawGrid(g);
        drawWall(g);
        drawClickMousePosition(g);
        drawUnits(g);
    }

    private void updateMousePosition(int x, int y) {
        if (0 <= (x - area.getLeft()) / area.getSizeX() && (x - area.getLeft()) / area.getSizeX() < area.getCols() &&
                0 <= (y - area.getTop()) / area.getSizeY() && (y - area.getTop()) / area.getSizeY() < area.getRows()
        ) {
            currentMousePosiotionX = (x - area.getLeft()) / area.getSizeX();
            currentMousePosiotionY = (y - area.getTop()) / area.getSizeY();
        }
        else {
            currentMousePosiotionX = -1;
            currentMousePosiotionY = -1;
        }
    }
    private boolean checkMouseCoordinatesIsUnit(Unit unit) {
        return unit.getX() == currentMousePosiotionX && unit.getY() == currentMousePosiotionY;
    }

    @Override
    public void run() {
        while (true) {
            if (currentMousePosiotionY != -1 && currentMousePosiotionX != -1) {
                for (int i = 0; i < units.length; ++i) {
                    Unit unit = units[i];
                    unit.move(area);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            repaint();
        }
    }
}