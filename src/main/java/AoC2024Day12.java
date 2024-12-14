import utils.Matrix;
import utils.Matrix.Direction;
import utils.Matrix.Item;
import utils.Matrix.Offset;

void main() throws Exception {
    var matrix = Matrix.fromLines(Files.readAllLines(Path.of("day12/input.txt")));
    long result1 = 0;
    List<Set<Item>> clusters = new ArrayList<>();
    for (int y = 1; y <= matrix.getHeight(); y++) for (int x = 1; x <= matrix.getWidth(); x++) {
        Item item = new Item(x, y, matrix.get(x, y), null);
        processItems(matrix, getCluster(clusters, item), item);
    }
    for (Set<Item> items : clusters) {
        long fences = 0;
        for (Item item: items) for (Direction dir: Matrix.squareDirections)
            try { if (!item.value().equals(matrix.getValueInDirection(item.x(), item.y(), dir))) fences++; } catch (Exception ignored) {}
        result1 += (items.size()*fences);
    }
    System.out.println(result1);
    println("1374934");

    // Gave up on part 2 ...
}

void processItems(Matrix matrix, Set<Item> itemsAround, Item item) {
    for (Direction dir: Matrix.squareDirections) try {
            String valToCheck = matrix.getValueInDirection(item.x(), item.y(), dir);
            if (item.value().equals(valToCheck)) {
                Offset offset = matrix.getOffsetFor(dir);
                Item newItem = new Item(item.x()+offset.x(), item.y()+ offset.y(), valToCheck, null);
                if (itemsAround.add(newItem)) processItems(matrix, itemsAround, newItem);
    }} catch (Exception ignored) {}
}

Set<Item> getCluster(List<Set<Item>> clusters, Item item) {
    for (Set<Item> cluster: clusters) if (cluster.contains(item)) return cluster;
    Set<Item> newCluster = new HashSet<>() {{ add(item); }};
    clusters.add(newCluster);
    return newCluster;
}