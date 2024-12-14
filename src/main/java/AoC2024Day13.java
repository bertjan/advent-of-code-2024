void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day13/input.txt"));
    long result1 = 0, result2 = 0, buttonA_X = 0, buttonA_Y = 0, buttonB_X = 0, buttonB_Y = 0;
    for (String line: lines) {
        if (line.isBlank()) continue;
        var parts = line.split(": ")[1].replaceAll("(X|Y)(\\+|=)","").split(", ");
        long partX = Long.valueOf(parts[0]), partY = Long.valueOf(parts[1]);
        if (line.startsWith("Button A:")) { buttonA_X = partX; buttonA_Y = partY; }
        else if (line.startsWith("Button B:")) { buttonB_X = partX; buttonB_Y = partY; }
        else if (line.startsWith("Prize: ")) {
            result1 += getLowestCost(partX, buttonA_Y, partY, buttonA_X, buttonB_X, buttonB_Y, 0);
            result2 += getLowestCost(partX, buttonA_Y, partY, buttonA_X, buttonB_X, buttonB_Y, 10000000000000L);
    }}
    println(result1 + " " + result2);
}

private static long getLowestCost(long prize_X, long buttonA_Y, long prize_Y, long buttonA_X, long buttonB_X, long buttonB_Y, long offset) {
    prize_X += offset; prize_Y += offset;
    long bPresses = (prize_X * buttonA_Y - prize_Y * buttonA_X) / (buttonB_X * buttonA_Y - buttonB_Y * buttonA_X);
    long aRemain = buttonA_X == 0 ? prize_Y : (prize_X - bPresses * buttonB_X);
    long aContrib = buttonA_X == 0 ? buttonA_Y : buttonA_X;
    return ((aRemain/aContrib) * buttonA_Y + bPresses * buttonB_Y == prize_Y && aRemain % aContrib == 0) ? 3 * (aRemain/aContrib) + bPresses : 0;
}

