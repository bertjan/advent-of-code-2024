import utils.Matrix;
import utils.Matrix.Position;
import static utils.Parse.safeLong;

record Velocity(int x, int y) {}
record Robot(Position position, Velocity velocity) {}

void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day14/input.txt"));
    long result1 = 0, result2 = 0, seconds = 0;
    List<Robot> robots = new ArrayList<>();
    for (String line: lines) {
        var pos = line.split(" ")[0].split("=")[1].split(",");
        var vel = line.split(" ")[1].split("=")[1].split(",");
        robots.add(new Robot(
                new Position(Integer.valueOf(pos[0])+1, Integer.valueOf(pos[1])+1),
                new Velocity(Integer.valueOf(vel[0]), Integer.valueOf(vel[1]))
        ));
    }
    var matrix = new Matrix(101, 103);
    matrix.fill(".");
    robots.forEach(r -> matrix.put(r.position.x(), r.position.y(), "1"));
    while (true) {
        seconds++;
        robots.forEach(robot -> {
            int x = robot.position.x(), y = robot.position.y();
            matrix.put(x, y, safeLong(matrix.get(x, y)) - 1 > 0 ? "" + (safeLong(matrix.get(x, y)) - 1) : ".");
            int newX = (x + robot.velocity.x() - 1 + matrix.getWidth()) % matrix.getWidth() + 1;
            int newY = (y + robot.velocity.y() - 1 + matrix.getHeight()) % matrix.getHeight() + 1;
            matrix.put(newX, newY, "" + (safeLong(matrix.get(newX, newY)) + 1));
            robot.position.set(newX, newY);
        });
        if (matrix.printToLines().stream().anyMatch(mline -> mline.contains("1111111111"))) {
            result2 = seconds; break;
        }
        if (seconds == 100) {
            int midX = matrix.getWidth() / 2 + 1, midY = matrix.getHeight() / 2 + 1;
            for (int y = 1; y <= matrix.getHeight(); y++) matrix.put(midX, y, " ");
            for (int x = 1; x <= matrix.getWidth(); x++) matrix.put(x, midY, " ");
            long[] q = {0, 0, 0, 0};
            for (int y = 1; y <= matrix.getHeight(); y++) for (int x = 1; x <= matrix.getWidth(); x++) {
                var val = matrix.get(x, y);
                if (!".".equals(val) && !" ".equals(val)) q[(x > midX ? 1 : 0) + (y > midY ? 2 : 0)] += safeLong(val);
            }
            result1 = q[0] * q[1] * q[2] * q[3];
        }
    }
    System.out.println(result1 + " " + result2);
}