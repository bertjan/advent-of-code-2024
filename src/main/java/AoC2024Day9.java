void main() throws Exception {
    var line = Files.readString(Path.of("day9/input.txt")).trim();
    List<String> map = new ArrayList<>(); long id = 0;
    for (int i = 0; i < line.length(); i++) map.addAll(Collections.nCopies(
        Character.getNumericValue(line.charAt(i)), (i % 2 == 0) ? String.valueOf(id++) : "."));
    long result1 = checkMap(new ArrayList<>(map), false);
    long result2 = checkMap(new ArrayList<>(map), true);
    println(result1 + " " + result2);
}

private long checkMap(List<String> map, boolean multipleBlocks) {
    long checkedUntil = map.size();
    while (true) {
        int movePos = -1;
        for (int a = 0; a < checkedUntil; a++) {
            int ix = (int) checkedUntil - 1 - a;
            if (!".".equals(map.get(ix))) { movePos = ix; break; }
        }
        int startPos = movePos, endPos = movePos;
        if (multipleBlocks) for (int c = startPos; c > 0; c--)
            if (".".equals(map.get(c)) || !map.get(c).equals(map.get(startPos))) break; else endPos = c;
        var blockToMove = map.subList(endPos, startPos + 1);
        int index = Collections.indexOfSubList(map, IntStream.range(0, blockToMove.size()).mapToObj(i -> ".").toList());
        if (index != -1 && index <= endPos) for (int i = 0; i < blockToMove.size(); i++) {
            map.set(index + i, blockToMove.get(i));
            map.set(endPos + i, ".");
        }
        if ((checkedUntil = endPos) == 0) break;
    }
    return IntStream.range(0, map.size()).filter(ix -> !".".equals(map.get(ix)))
            .mapToLong(ix -> ix * Long.parseLong(map.get(ix))).sum();
}


