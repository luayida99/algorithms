package algorithms;
class majorityElement {
    //O(n^2)
    public static int naiveMajority(int[] arr) {
        int majorityCount = arr.length/2;

        for (int num : arr) {
            int count = 0;
            for (int element : arr) {
                if (element == num) {
                    count++;
                }
            }
            if (count > majorityCount) {
                return num;
            }
        }

        return -1;
    }

    private static int countRange(int[] arr, int n, int low, int high) {
        int count = 0;
        for (int i = low; i <= high; i++) {
            if (arr[i] == n) {
                count++;
            }
        }
        return count;
    }

    private static int majorityHelper(int[] arr, int low, int high) {
        if (low == high) {
            return arr[low];
        }

        int mid = (high - low)/2 + low;
        int leftMax = majorityHelper(arr, low, mid);
        int rightMax = majorityHelper(arr, mid + 1, high);

        if (leftMax == rightMax) {
            return leftMax;
        }

        int leftMaxCount = countRange(arr, leftMax, low, high);
        int rightMaxCount = countRange(arr, rightMax, low, high);

        return leftMaxCount > rightMaxCount ? leftMax : rightMax;
    }

    //O(nlogn)
    public static int divideConquerMajority(int[] arr) {
        return majorityHelper(arr, 0, arr.length - 1);
    }

    //O(n)
    public static int linearMajority(int[] arr) {
        int count = 0;
        Integer majority = null;

        for (int element : arr) {
            if (count == 0) {
                majority = element;
            }

            if (element == majority) {
                count++;
            } else {
                count--;
            }
        }

        return majority;
    }
}