import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class escaper {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"), StandardCharsets.UTF_8);
        PrintWriter out = new PrintWriter("output.txt");
        String res = lines.stream().map(s -> "\"" +
                s.chars().asLongStream().mapToObj(c -> escapeChar((char) c)).collect(Collectors.joining()) +
                "\\n\""
        ).collect(Collectors.joining(" +\n"));
        out.println(res);
        out.close();
    }

    private static String escapeChar(char c) {
        String res = "";
        switch (c) {
            case 8: // backspace
            case 10: // Newline
            case 9: // Tab
            case 13: // Carriage return
            case 12: // Form feed
            case 34: // Double quote
            case 92: // Backslash
                res += "\\";
        }
        res += c;
        return res;
    }
}