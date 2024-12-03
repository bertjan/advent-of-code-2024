void main() throws Exception {
    var input = Files.lines(Path.of("day1/input.txt")).map(line -> Arrays.stream(line.split("   ")).map(Long::valueOf).toList()).toList();
    var left = input.stream().map(List::getFirst).sorted().toList();
    var right = input.stream().map(List::getLast).sorted().toList();
    var result = IntStream.range(0, left.size()).mapToLong(i -> Math.abs(left.get(i) - right.get(i))).sum();
    var result2 = left.stream().mapToLong(l -> l * Collections.frequency(right, l)).sum();
    System.out.println(result + " " + result2);
}