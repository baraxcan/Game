import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class Game extends JFrame implements Runnable {
    Unit[] units = {
        new Unit(2, 0, Color.RED),
        new Unit(2, 2, Color.RED),
        new Unit(0, 2, Color.RED),
        new Unit(7, 7, Color.BLUE),
        new Unit(7, 9, Color.BLUE),
        new Unit(9, 7, Color.BLUE)
    };
    Area area = new Area();
    int currentMousePosiotionX = -1;
    int currentMousePosiotionY = -1;

    public static void main(String[] args) {
        System.out.println("START");
        Game game = new Game();
        game.area.setWall(4, 0);
        game.area.setWall(4, 1);
//        game.area.setWall(4, 2);
        game.area.setWall(4, 3);
        game.area.setWall(4, 4);
        game.area.setWall(3, 4);
//        game.area.setWall(2, 4);
        game.area.setWall(1, 4);
        game.area.setWall(0, 4);
        game.area.setWall(5, 9);
        game.area.setWall(5, 8);
//        game.area.setWall(5, 7);
        game.area.setWall(5, 6);
        game.area.setWall(5, 5);
        game.area.setWall(6, 5);
//        game.area.setWall(7, 5);
        game.area.setWall(8, 5);
        game.area.setWall(9, 5);
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
        for (int i = 0; i < units.length; ++i) {
            Unit unit = units[i];
            g.setColor(unit.getCommand());
            if (!unit.isLife()) {
                g.setColor(Color.BLACK);
            }
            g.fillOval(area.getLeft() + unit.getX() * area.getSizeX(),
                    area.getTop() + unit.getY() * area.getSizeY(), area.getSizeX(), area.getSizeY());

        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < units.length; ++i) {
            Unit unit = units[i];
            if (unit.isActive() && unit.isLife()) {
                g.drawOval(area.getLeft() + unit.getX() * area.getSizeX(),
                        area.getTop() + unit.getY() * area.getSizeY(), area.getSizeX(), area.getSizeY());
            }
        }

        for (int i = 0; i < units.length; ++i) {
            g.setColor(Color.BLACK);
            Unit unit = units[i];
            g.fill3DRect(area.getLeft() + unit.getX() * area.getSizeX(),
                    area.getTop() + unit.getY() * area.getSizeY() - 3, area.getSizeX(), 3, true);
            g.setColor((Color.GREEN));
            double size =  area.getSizeX() * unit.getLife();
            g.fill3DRect(area.getLeft() + unit.getX() * area.getSizeX(),
                    area.getTop() + unit.getY() * area.getSizeY() - 3, (area.getSizeX() * unit.getLife()) / 10, 3, true);
        }
    }

    public void paint(Graphics g) {
        this.createBufferStrategy(2);
        BufferStrategy bs = this.getBufferStrategy();
        g = (Graphics2D) bs.getDrawGraphics();
        super.paint(g);

        drawGrid(g);
        drawWall(g);
        drawClickMousePosition(g);
        drawUnits(g);

        bs.show();
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
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            for (int i = 0; i < units.length; ++i) {
                Unit unit_i = units[i];
                if (!unit_i.isLife()) {
                    continue;
                }
                for (int j = i + 1; j < units.length; ++j) {
                    Unit unit_j = units[j];
                    if (!unit_j.isLife()) {
                        continue;
                    }
                    if (unit_i.checkUnitNearby(unit_j) && unit_i.getCommand() != unit_j.getCommand()) {
                        unit_i.lifeReduction();
                        unit_j.lifeReduction();
                        System.out.println("CHECK");
                    }
                }
            }
            repaint();
        }
    }
}
