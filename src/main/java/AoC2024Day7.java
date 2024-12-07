import org.graalvm.polyglot.Context;

static Context GRAALJS = Context.newBuilder("js").build();

void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day7/input.txt"));
    long result1 = 0; long result2 = 0;
    for (String line: lines) {
        result1 += processLine(line,false);
        result2 += processLine(line,true);
    }
    println(result1 + " " + result2);
}

long processLine(String line, boolean includePipe) {
    long eqResult = Long.valueOf(line.split(":")[0]);
    List<String> numbers = Arrays.asList(line.split(": ")[1].split(" "));
    List<List> options = new ArrayList<>(List.of(List.of(numbers.getFirst())));
    numbers.subList(1, numbers.size()).forEach(num -> {
        List<String> operations = new ArrayList<>(List.of("+" + num, "*" + num));
        if (includePipe) operations.add("+\"\"+" + num);
        options.add(operations);
    });
    List<String> allCalcs = options.get(0);
    for (int i = 1; i < options.size(); i++) {
        List<String> cur = options.get(i);
        allCalcs = allCalcs.stream().flatMap(calc -> cur.stream().map(op -> "Number(" + calc + op + ")")).toList();
    }
    return allCalcs.stream().map(calc -> Double.valueOf(GRAALJS.eval("js", calc) + "").longValue())
            .filter(result -> result == eqResult).findFirst().orElse(0L);
}