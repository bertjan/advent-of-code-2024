void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day3/input.txt"));
    long result = 0, result2 = 0; boolean enabled = true;
    for (String line : lines) {
        Matcher matcher = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\))").matcher(line);
        while (matcher.find()) { switch (matcher.group()) {
            case "do()": enabled = true; break; case "don't()": enabled = false; break;
            default: var parts = matcher.group().replaceAll("[^\\d,]", "").split(",");
                     long product = Long.valueOf(parts[0]) * Long.valueOf(parts[1]);
                     result += product; if (enabled) result2 += product;
    }}}
    System.out.println(result + " " + result2);
}