import utils.Matrix;
import utils.Matrix.Direction;
import utils.Matrix.Item;
import utils.Matrix.Offset;
import utils.Matrix.Position;

void main() throws Exception {
    var matrix = Matrix.fromLines(Files.readAllLines(Path.of("day10/input.txt")));
    List<Item> startItems = new ArrayList<>(), items = new ArrayList<>();
    for (int y = 1; y <= matrix.getHeight(); y++) for (int x = 1; x <= matrix.getWidth(); x++)
        if ("0".equals(matrix.get(x, y))) startItems.add(new Item(x, y, matrix.get(x, y), new ArrayList<>()));
    findAll(items, matrix, startItems);
    long result1 = new HashSet<>(items).size(), result2 = items.size();
    println(result1 + " " + result2);
}

private List<Item> findNext(Matrix matrix, Item curPos) {
    List<Item> nextItems = new ArrayList<>();
    for (Direction dir: Matrix.squareDirections) try {
        long nextVal = Long.valueOf(matrix.getValueInDirection(curPos.x(), curPos.y(), dir));
        if (nextVal == Long.valueOf(curPos.value()) + 1) {
            curPos.history().add(new Position(curPos.x(), curPos.y()));
            Offset offset = matrix.getOffsetFor(dir);
            nextItems.add(new Item(curPos.x() + offset.x(), curPos.y() + offset.y(), ""+nextVal, curPos.history()));
    }} catch (Exception ignored) {}
    return nextItems;
}

private void findAll(List<Item> finished, Matrix matrix, List<Item> nextSteps) {
    for (Item next: nextSteps) for (Item item: findNext(matrix, next)) {
        if ("9".equals(item.value())) finished.add(item);
        findAll(finished, matrix, List.of(item));
    }
}


