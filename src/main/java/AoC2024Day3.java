void main() throws Exception {
    var lines = Files.readAllLines(Path.of("day3/input.txt"));
    long result = 0, result2 = 0; boolean enabled = true;
    for (String line : lines) {
        Matcher matcher = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|(do(n't)*)\\(\\)").matcher(line);
        while (matcher.find()) { switch (matcher.group()) {
            case "do()": enabled = true; break; case "don't()": enabled = false; break;
            default: long product = Long.valueOf(matcher.group(1)) * Long.valueOf(matcher.group(2));
                     result += product; if (enabled) result2 += product;
    }}}
    System.out.println(result + " " + result2);
}