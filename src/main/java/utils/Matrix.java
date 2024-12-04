package utils;

import java.util.List;

public class Matrix {

    public enum Direction {
        TopLeft,
        Top,
        TopRight,
        Right,
        BottomRight,
        Bottom,
        BottomLeft,
        Left
    }

    private String[][] matrix;

    public Matrix(int width, int height) {
        matrix = new String[width][height];
        for (int x = 0; x < width; x++) {
            for (int y=0; y<height; y++) {
                matrix[x][y] = " ";
            }
        }
    }

    public static Matrix fromLines(List<String> lines) {
        Matrix matrix = new Matrix(lines.getFirst().length(), lines.size());
        int posY = 0;
        for (String line: lines) {
            posY++;
            for (int posX = 0; posX < line.length(); posX++) {
                matrix.put(posX + 1, posY, line.charAt(posX));
            }
        }
        return matrix;
    }

    public void addMatrixRow() {
        Matrix newMatrix = new Matrix(this.getWidth(), this.getHeight()+1);
        for (int x=1; x<this.getWidth()+1; x++) {
            for (int y=1; y<this.getHeight()+1; y++) {
                newMatrix.put(x, y, this.get(x, y));
            }
        }
        this.matrix = newMatrix.matrix;
    }

    public boolean isEmptyRow(int y) {
        boolean isEmpty = true;
        for (int x=1; x<this.getWidth()+1; x++) {
            if (!this.get(x,y).equals(" ")) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public void clear(int x, int y) {
        this.put(x, y, " ");
    }

    public void print() {
        boolean foundFirstNonEmptyRow = false;
        System.out.println("\nMatrix:");
        for (int y = this.getHeight(); y > 0; y--) {
            for (int x=1; x<this.getWidth()+1; x++) {
                if (!isEmptyRow(y)) {
                    foundFirstNonEmptyRow = true;
                }
                if (foundFirstNonEmptyRow) {
                    System.out.print("[" + this.get(x, y) + "] ");
                }
            }
            if (foundFirstNonEmptyRow) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public void move(int fromX, int fromY, int toX, int toY) {
        String fromValue = this.get(fromX, fromY);
        this.put(toX, toY, fromValue);
        this.clear(fromX, fromY);
    }

    public void put(int x, int y, char value) {
        put(x, y, "" + value);
    }

    public void put(int x, int y, String value) {
        if (y>getHeight()) {
            for (int i=0; i<y; i++) {
                this.addMatrixRow();
            }
        }
        matrix[x-1][y-1] = value;
    }

    public String get(int x, int y) {
        return matrix[x-1][y-1];
    }

    public int getInt(int x, int y) {
        return Integer.parseInt(matrix[x-1][y-1]);
    }

    public int getWidth() {
        return matrix.length;
    }

    public int getHeight() {
        return matrix[0].length;
    }

    public String getTopLeft(int x, int y, int steps) {
        return this.get(x-steps, y+steps);
    }

    public String getTop(int x, int y, int steps) {
        return this.get(x, y+steps);
    }

    public String getTopRight(int x, int y, int steps) {
        return this.get(x+steps, y+steps);
    }

    public String getRight(int x, int y, int steps) {
        return this.get(x+steps, y);
    }

    public String getBottomRight(int x, int y, int steps) {
        return this.get(x+steps, y-steps);
    }

    public String getBottom(int x, int y, int steps) {
        return this.get(x, y-steps);
    }

    public String getBottomLeft(int x, int y, int steps) {
        return this.get(x-steps, y-steps);
    }

    public String getLeft(int x, int y, int steps) {
        return this.get(x-steps, y);
    }

    public String getValueInDirection(int x, int y, Direction direction, int steps) {
        return switch (direction) {
            case TopLeft -> getTopLeft(x, y, steps);
            case Top -> getTop(x, y, steps);
            case TopRight -> getTopRight(x, y, steps);
            case Right -> getRight(x, y, steps);
            case BottomRight -> getBottomRight(x, y, steps);
            case Bottom -> getBottom(x, y, steps);
            case BottomLeft -> getBottomLeft(x, y, steps);
            case Left -> getLeft(x, y, steps);
        };
    }

}
