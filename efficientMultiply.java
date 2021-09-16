
public class efficientMultiply {
    private static int numLength(long n) {
        int len = 0;
        while (n > 0) {
            len++;
            n /= 10;
        }
        return len;
    }

    public static long multiply(long x, long y) {
        if (x < 10 && y < 10) {
            return x * y;
        }

        int xLength = numLength(x);
        int yLength = numLength(y);
        int maxLength = Math.max(xLength, yLength);
        Integer halfMaxLength = (maxLength / 2) + (maxLength % 2);
 
        long multiplier = (long)Math.pow(10, halfMaxLength);

        long a = x / multiplier;
        long b = x % multiplier;
        long c = y / multiplier;
        long d = y % multiplier;

        long z0 = multiply(a, c);
        long z1 = multiply(a + b, c + d);
        long z2 = multiply(b, d);

        return (long) (z0 * (long)Math.pow(10, halfMaxLength * 2) + ((z1 - z0 - z2) * (long)Math.pow(10, halfMaxLength) + z2));
    }

    public static void main(String[] args) {
        System.out.println(multiply(Long.MAX_VALUE/2,Long.MAX_VALUE/2));
    }
}