void main() throws Exception {
    Map<String,Long> cache = new HashMap<>();
    long result1 = 0, result2 = 0;
    var line = Files.readAllLines(Path.of("day11/input.txt")).getFirst();
    for (long curStone: Arrays.stream(line.split(" ")).mapToLong(Long::valueOf).toArray()) {
        result1 += count(curStone, 25, cache);
        result2 += count(curStone, 75, cache);
    }
    println(result1 + " " + result2);
}

private long count(long stone, int iter, Map<String,Long> cache) {
    String key = stone+" "+iter, stoneStr = ""+stone;
    if (cache.containsKey(key)) return cache.get(key);
    if (iter == 0) return 1;
    long count = (stone == 0L) ? count(1L, iter-1, cache) : stoneStr.length() % 2 == 0
            ? count(Long.parseLong(stoneStr.substring(0, stoneStr.length() / 2)), iter-1, cache) +
            count(Long.parseLong((stoneStr).substring(stoneStr.length() / 2)), iter-1, cache)
            : count(stone * 2024, iter-1, cache);
    cache.put(key, count);
    return count;
}