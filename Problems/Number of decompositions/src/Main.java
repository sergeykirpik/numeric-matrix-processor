import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        List<String> result = decompose(number);
        Collections.sort(result);
        for (String d: result) {
            System.out.println(d);
        }
    }

    static List<String> decompose(int number) {
        int n1 = number - 1;
        int n2 = 1;
        List<String> result = new ArrayList<>();
        while (n1 >= n2) {
            result.add(String.format("%d %d", n1, n2));
            if (n2 > 1) {
                List<String> r = decompose(n2);
                for (String el: r) {
                    result.add(String.format("%d %s", n1, el));
                }
            }
            if (n1 > 1 && n1 != n2) {
                List<String> r = decompose(n1);
                for (String el: r) {
                    result.add(String.format("%s %d", el, n2));
                }
            }
            n1--;
            n2++;
        }
        return result;
    }
}