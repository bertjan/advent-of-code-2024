import utils.Matrix;
import utils.Matrix.Direction;

void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day4/input.txt"));
    long result = 0, result2 = 0;
    Matrix matrix = Matrix.fromLines(lines);
    for (int y = 1; y < matrix.getHeight() + 1; y++) {
        for (int x = 1; x < matrix.getWidth() + 1; x++) {
            String value = matrix.get(x, y);
            if ("X".equals(value)) result += findXMAS(matrix, x, y);
            if ("A".equals(value)) result2 += findCrossMAS(matrix, x, y);
    }}
    System.out.println(result + " " + result2);
}

long findXMAS(Matrix matrix, int x, int y) {
    long result = 0;
    for (Direction direction: Direction.values()) {
        try {
            if ("M".equals(matrix.getValueInDirection(x,y,direction,1))
             && "A".equals(matrix.getValueInDirection(x,y,direction,2))
             && "S".equals(matrix.getValueInDirection(x,y,direction,3))) { result++; }
        } catch (ArrayIndexOutOfBoundsException e) {}
    }
    return result;
}

long findCrossMAS(Matrix matrix, int x, int y) {
    try {
        String top = matrix.getTopLeft(x,y,1) + matrix.getTopRight(x,y,1);
        String bottom = matrix.getBottomLeft(x,y,1) + matrix.getBottomRight(x,y,1);
        switch (top) {
            case "MM": if ("SS".equals(bottom)) { return 1; } break;
            case "SS": if ("MM".equals(bottom)) { return 1; } break;
            case "MS": if ("MS".equals(bottom)) { return 1; } break;
            case "SM": if ("SM".equals(bottom)) { return 1; } break;
        }
    } catch (Exception e) { return 0; }
    return 0;
}
