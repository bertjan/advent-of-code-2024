package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix {

    public record Position(int x, int y) {};

    public record Item(int x, int y, String value, List<Position> history) {}

    public record Offset(int x, int y) {}

    public enum Direction {
        TopLeft, Top, TopRight, Right, BottomRight, Bottom, BottomLeft, Left
    }

    public static final List<Direction> squareDirections = List.of(Direction.Top, Direction.Right, Direction.Bottom, Direction.Left);

    public Offset getOffsetFor(Direction direction) {
        int x = 0, y = 0;
        switch (direction) {
            case Top: x = 0; y = 1; break;
            case Right: x = 1; y = 0; break;
            case Bottom: x = 0; y = -1; break;
            case Left: x = -1; y = 0; break;
            default: break;
        }
        return new Offset(x, y);
    }

    public Matrix getCopy() {
        String[][] newMatrix = new String[getWidth()][getHeight()];
        for (int x = 0; x < getWidth(); x++) {
            newMatrix[x] = matrix[x].clone();
        }
        return new Matrix(newMatrix);
    }

    private String[][] matrix;

    public Matrix(int width, int height) {
        matrix = new String[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                matrix[x][y] = " ";
            }
        }
    }

    public Matrix(String[][] newMatrix) {
        this.matrix = newMatrix;
    }

    public static Matrix fromLines(List<String> lines) {
        var matrix = new Matrix(lines.get(0).length(), lines.size());
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                matrix.put(x + 1, y + 1, lines.get(y).charAt(x));
        }}
        return matrix;
    }

    public void addMatrixRow() {
        var newMatrix = new Matrix(getWidth(), getHeight() + 1);
        for (int x = 1; x <= getWidth(); x++) {
            for (int y = 1; y <= getHeight(); y++) {
                newMatrix.put(x, y, get(x, y));
        }}
        matrix = newMatrix.matrix;
    }

    public boolean isEmptyRow(int y) {
        return IntStream.range(1, getWidth() + 1).allMatch(x -> " ".equals(get(x, y)));
    }

    public void clear(int x, int y) {
        put(x, y, " ");
    }

    public void print() {
        System.out.println("\nMatrix:");
        for (int y = getHeight(); y > 0; y--) {
            var yPos = y;
            if (!isEmptyRow(y)) {
                System.out.println(IntStream.range(1, getWidth() + 1)
                        .mapToObj(x -> get(x, yPos))
                        .collect(Collectors.joining()));
    }}}


    public void move(int fromX, int fromY, int toX, int toY) {
        put(toX, toY, get(fromX, fromY));
        clear(fromX, fromY);
    }

    public void put(int x, int y, char value) {
        put(x, y, String.valueOf(value));
    }

    public void put(int x, int y, String value) {
        while (y > getHeight()) addMatrixRow();
        matrix[x - 1][y - 1] = value;
    }

    public String get(int x, int y) {
        return matrix[x - 1][y - 1];
    }

    public int getWidth() {
        return matrix.length;
    }

    public int getHeight() {
        return matrix[0].length;
    }

    public String getValueInDirection(int x, int y, Direction direction) {
        return getValueInDirection(x, y, direction, 1);
    }

    public String getValueInDirection(int x, int y, Direction direction, int steps) {
        return switch (direction) {
            case TopLeft -> get(x - steps, y + steps);
            case Top -> get(x, y + steps);
            case TopRight -> get(x + steps, y + steps);
            case Right -> get(x + steps, y);
            case BottomRight -> get(x + steps, y - steps);
            case Bottom -> get(x, y - steps);
            case BottomLeft -> get(x - steps, y - steps);
            case Left -> get(x - steps, y);
        };
    }

    public String getTopLeft(int x, int y) {
        return this.getValueInDirection(x, y, Direction.TopLeft, 1);
    }

    public String getTopRight(int x, int y) {
        return this.getValueInDirection(x, y, Direction.TopRight, 1);
    }

    public String getBottomLeft(int x, int y) {
        return this.getValueInDirection( x, y, Direction.BottomLeft, 1);
    }

    public String getBottomRight(int x, int y) {
        return this.getValueInDirection(x, y, Direction.BottomRight, 1);
    }

    public List<Position> findPositions(String content) {
        List<Position> positions = new ArrayList<>();
        for (int y = 1; y <= this.getHeight(); y++) {
            for (int x = 1; x <= this.getWidth(); x++) {
                if (content.equals(this.get(x, y))) positions.add(new Position(x,y));
            }
        }
        return positions;
    }

}
