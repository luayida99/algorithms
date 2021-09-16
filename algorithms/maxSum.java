package algorithms;
public class maxSum {
    //O(nlogn)
    private static double max(double a, double b, double c) {
        return Math.max(a, Math.max(b, c));
    }

    private static double divideSumHelper(double[] arr, int low, int mid, int high) {
        double sum1 = 0;
        double leftSum = Double.NEGATIVE_INFINITY;

        for (int i = mid; i >= low; i--) {
            sum1 += arr[i];
            if (sum1 > leftSum) {
                leftSum = sum1;
            }
        }

        double sum2 = 0;
        double rightSum = Double.NEGATIVE_INFINITY;
        for (int i = mid + 1; i <= high; i++) {
            sum2 += arr[i];
            if (sum2 > rightSum) {
                rightSum = sum2;
            }
        }

        return max(leftSum, rightSum, leftSum + rightSum);
    }

    public static double divideSum(double[] arr, int low, int high) {
        if (low == high) {
            return arr[low];
        }

        int mid = (high - low)/2 + low;

        return max(divideSum(arr, low, mid), divideSum(arr, mid + 1, high), divideSumHelper(arr, low, mid, high));
    }

    //O(n)
    public static double efficientSum(double[] arr) {
        double globalMax = Double.NEGATIVE_INFINITY;
        double localMax = 0;

        for (int i = 0; i < arr.length; i++) {
            localMax = Math.max(arr[i], arr[i] + localMax);
            if (localMax > globalMax) {
                globalMax = localMax;
            }
        }

        return globalMax;
    }

    public static void main(String[] args) {
        double[] arr = new double[]{1, -5, 4, 5};
        System.out.println(divideSum(arr, 0, arr.length - 1));
        System.out.println(efficientSum(arr));
    }
}
