import utils.Matrix;
import utils.Matrix.Direction;
import utils.Matrix.Position;

import static utils.Matrix.Position.offsetFor;

void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day15/input.txt"));
    int result1=0, split = lines.indexOf("");
    List<String> matrixLines = new ArrayList<>(lines.subList(0, split));
    Collections.reverse(matrixLines);
    Matrix Position = Matrix.fromLines(matrixLines);
    for (char c: String.join("", lines.subList(split + 1, lines.size())).toCharArray()) {
        Direction dir = switch (c) {
            case '^' -> Direction.Top; case '>' -> Direction.Right; case 'v' -> Direction.Bottom; case '<' -> Direction.Left;
            default -> throw new Exception();
        };
        Position pos = Position.findFirst("@"), newPos = pos.withOffset(dir);
        boolean movedBoxes = false;
        if (Position.get(newPos.x(), newPos.y()).equals("O")) {
            for (int i=0;i<Position.getHeight();i++) { try {
                    int checkX = pos.x() + i* offsetFor(dir).x(), checkY = pos.y() + i*offsetFor(dir).y();
                    String checkVal = Position.get(checkX, checkY);
                    if (".".equals(checkVal)) { Position.put(checkX, checkY, "O"); movedBoxes = true; break; }
                    else if ("#".equals(checkVal)) break;
            } catch (Exception ignored) { break; }}
        }
        if (movedBoxes || Position.get(newPos.x(), newPos.y()).equals(".")) {
            Position.put(pos.x(), pos.y(), ".");
            Position.put(newPos.x(), newPos.y(), "@");
        }
    }
    for (int y = 1; y <= Position.getHeight(); y++) for (int x = 1; x <= Position.getWidth(); x++)
        if ("O".equals(Position.get(x,y))) result1 += (x-1) + 100*(Position.getHeight()-y);
    System.out.println(result1);

    // Gave up on part 2 ...
}
