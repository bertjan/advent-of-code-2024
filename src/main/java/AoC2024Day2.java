void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day2/input.txt"));
    long result = 0, result2 = 0;
    for (String line: lines) {
        var parts = line.split(" ");
        if (isValid(parts)) { result++; result2++; }
        else { result2 += IntStream.range(0, parts.length).anyMatch(i -> isValid(IntStream.range(0, parts.length)
                         .filter(j -> j != i).mapToObj(j -> parts[j]).toArray(String[]::new))) ? 1 : 0; }
    }
    System.out.println(result + " " + result2);
}

boolean isValid(String[] parts) {
    boolean inc = Long.parseLong(parts[1]) > Long.parseLong(parts[0]);
    for (int i = 1; i < parts.length; i++) {
        long cur = Long.valueOf(parts[i]), prev = Long.valueOf(parts[i-1]);
        if (Math.abs(cur-prev)<1 || Math.abs(cur-prev)>3 || (cur > prev) != inc) return false;
    }
    return true;
}