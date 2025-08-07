package boundary;

public class BarChartUtil {
    // ANSI color codes
    public static final String[] COLORS = {
        "\u001B[31m", // Red
        "\u001B[32m", // Green
        "\u001B[33m", // Yellow
        "\u001B[34m", // Blue
        "\u001B[35m", // Magenta
        "\u001B[36m", // Cyan
        "\u001B[37m"  // White
    };
    public static final String RESET = "\u001B[0m";

    // Print a horizontal bar chart
    public static void printBarChart(String[] labels, int[] values, String title) {
        System.out.println("\n" + title);
        int max = 0;
        for (int v : values) if (v > max) max = v;
        int maxLabel = 0;
        for (String label : labels) if (label.length() > maxLabel) maxLabel = label.length();
        for (int i = 0; i < labels.length; i++) {
            String color = COLORS[i % COLORS.length];
            int barLen = (max > 0) ? (values[i] * 40 / max) : 0;
            System.out.printf("%-" + maxLabel + "s | %s%s%s %d\n", labels[i], color, repeat("█", barLen), RESET, values[i]);
        }
        System.out.println();
    }

    private static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(s);
        return sb.toString();
    }
} 