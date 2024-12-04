import utils.Matrix;
import utils.Matrix.Direction;

void main() throws Exception {
    var matrix = Matrix.fromLines(Files.readAllLines(Path.of("day4/input.txt")));
    long result1 = 0, result2 = 0;
    for (int y = 1; y <= matrix.getHeight(); y++) {
        for (int x = 1; x <= matrix.getWidth(); x++) {
            if ("X".equals(matrix.get(x, y))) result1 += findXMAS(matrix, x, y);
            if ("A".equals(matrix.get(x, y))) result2 += findCrossMAS(matrix, x, y);
        }
    }
    System.out.println(result1 + " " + result2);
}

long findXMAS(Matrix matrix, int x, int y) {
    return Arrays.stream(Direction.values()).filter(direction -> { try {
        return "MAS".equals(IntStream.range(1,4)
                .mapToObj(i -> matrix.getValueInDirection(x, y, direction, i)).collect(Collectors.joining()));
    } catch (Exception e) { return false; }}).count();
}

long findCrossMAS(Matrix matrix, int x, int y) {
    try {
        String top = matrix.getTopLeft(x, y) + matrix.getTopRight(x, y);
        String bottom = matrix.getBottomLeft(x, y) + matrix.getBottomRight(x, y);
        return (top + bottom).matches("(MMSS|SSMM|MSMS|SMSM)") ? 1 : 0;
    } catch (Exception e) { return 0; }
}
