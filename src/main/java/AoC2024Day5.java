void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day5/input.txt"));
    long result = 0, result2 = 0; List<String> rules = new ArrayList<>();
    for (String line: lines) {
        if (line.contains("|")) rules.add(line);
        if (line.contains(",")) {
            var seq = Arrays.stream(line.split(",")).map(Long::valueOf).collect(Collectors.toList());
            if (isValidSeq(seq, rules)) { result += seq.get((seq.size()-1)/2); continue; }
            while (!isValidSeq(seq, rules)) { for (String rule: rules) {
                var parts = Arrays.stream(rule.split("\\|")).map(Long::valueOf).toArray(Long[]::new);
                if (seq.contains(parts[0]) && seq.contains(parts[1])) {
                    int index0 = seq.indexOf(parts[0]); int index1 = seq.indexOf(parts[1]);
                    if (index0 > index1) { seq.set(index0, parts[1]); seq.set(index1, parts[0]); }
            }}}
            result2 += seq.get((seq.size()-1)/2);
    }}
    System.out.println(result + " " + result2);
}

private boolean isValidSeq(List<Long> seq, List<String> rules) {
    for (String rule: rules) {
        var parts = Arrays.stream(rule.split("\\|")).map(Long::valueOf).toArray(Long[]::new);
        if ((seq.contains(parts[0]) && seq.contains(parts[1])) && (seq.indexOf(parts[0]) > seq.indexOf(parts[1]))) return false;
    }
    return true;
}