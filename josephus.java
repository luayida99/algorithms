public class josephus {
    public static int solve(int n) {
        if (n == 1) {
            return 1;
        } else {
            return (solve(n - 1) + 1) % n + 1;
        }
    }

    public static int fastSolve(int n) {
        if (n == 1) {
            return 1;
        } else {
            if (n % 2 == 0) {
                return 2 * fastSolve(n/2) - 1;
            } else {
                return 2 * fastSolve(n/2) + 1;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solve(100));
        System.out.println(fastSolve(100));
    }

}
