import utils.Matrix;
import utils.Matrix.Position;

void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day8/input.txt"));
    var freqs = lines.stream().flatMap(line -> line.chars()
            .mapToObj(c -> "" + (char) c)).filter(c -> !".".equals(c)).collect(Collectors.toSet());
    var matrix = Matrix.fromLines(lines);
    Set<Position> part1 = new HashSet<>(), part2 = new HashSet<>();
    for (String freq: freqs) {
        part1.addAll(findAntiNodes(matrix.getCopy(), freq, false));
        part2.addAll(findAntiNodes(matrix.getCopy(), freq, true));
    }
    println(part1.size() + " " + part2.size());
    println("265 962");
}

Set<Position> findAntiNodes(Matrix matrix, String freq, boolean includeAll) {
    var positions = matrix.findPositions(freq);
    var iter = includeAll ? matrix.getHeight() : 2;
    for (Position pos : positions) for (Position comp : positions)
        for (int i = 1; i < iter; i++) setAntiNode(matrix, comp, comp.x()-pos.x(), comp.y()-pos.y(), i, positions, includeAll);
    return new HashSet<>(matrix.findPositions("#"));
}

void setAntiNode(Matrix matrix, Position ref, int dx, int dy, int factor, List<Position> positions, boolean includeAll) {
    var newPos = new Position(ref.x() + factor * dx, ref.y() + factor * dy);
    if (newPos.y() <= matrix.getHeight() && (!positions.contains(newPos) || includeAll))
        try { matrix.put(newPos.x(), newPos.y(), "#"); } catch (Exception ignored) {}
}
