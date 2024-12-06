import utils.Matrix;
import utils.Matrix.Direction;

void main() throws Exception {
    long result = 0, result2 = 0;
    var lines = new ArrayList<>(Files.readAllLines(Path.of("day6/input.txt")));
    Collections.reverse(lines);
    var cleanMatrix = Matrix.fromLines(lines);

    int startX = 0, startY = 0;
    Direction startDirection = null;
    outer: for (int y = 1; y <= cleanMatrix.getHeight(); y++) {
        for (int x = 1; x <= cleanMatrix.getWidth(); x++) {
            String ch = cleanMatrix.get(x, y);
            if (ch.matches("[\\^>v<]")) {
                startX = x; startY = y;
                startDirection = switch (ch) { case "^" -> Direction.Top; case ">" -> Direction.Right;
                    case "v" -> Direction.Bottom;case "<" -> Direction.Left; default -> null; };
                break outer;
    }}}

    for (int obsY = 1; obsY <= cleanMatrix.getHeight(); obsY++) {
        for (int obsX = 1; obsX <= cleanMatrix.getWidth(); obsX++) {
            var matrix = cleanMatrix.getCopy();
            if (obsX != startX || obsY != startY) matrix.put(obsX, obsY, "O");
            boolean visitedObstacle = false;
            Set<String> visited = new HashSet<>(), visitedStates = new HashSet<>();
            Direction dir = startDirection;
            int curX = startX, curY = startY;
            while (true) {
                if ((obsX == startX && obsY == startY)) visited.add(curX + "-" + curY);
                int newX = curX + (dir == Direction.Right ? 1 : dir == Direction.Left ? -1 : 0);
                int newY = curY + (dir == Direction.Top ? 1 : dir == Direction.Bottom ? -1 : 0);
                if (newX < 1 || newX > matrix.getWidth() || newY < 1 || newY > matrix.getHeight()) {
                    if (!visited.isEmpty()) result = visited.size(); break;
                }
                String curChar = matrix.get(newX, newY);
                if (".".equals(curChar) || "*".equals(curChar)) {
                    matrix.put(curX, curY, "*");
                    curX = newX; curY = newY;
                    if (visitedObstacle && !visitedStates.add(curX + "-" + curY + "-" + dir)) {
                        result2++; break;
                    }
                } else {
                    if ("O".equals(curChar)) visitedObstacle = true;
                    dir = switch (dir) { case Top -> Direction.Right; case Right -> Direction.Bottom;
                        case Bottom -> Direction.Left; case Left -> Direction.Top; default -> null; };
    }}}}
    println(result + " " + result2);
}





