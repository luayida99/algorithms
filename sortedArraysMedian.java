public class sortedArraysMedian {
    //merge sorted arrs
    public static int[] merge(int[] a1, int[] a2) {
        int[] res = new int[a1.length + a2.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < a1.length && j < a2.length) {
            if (a1[i] < a2[j]) {
                res[k] = a1[i];
                i++;
            } else {
                res[k] = a2[j];
                j++;
            }
            k++;
        }

        while (i < a1.length) {
            res[k] = a1[i];
            i++;
            k++;
        }

        while (j < a2.length) {
            res[k] = a2[j];
            j++;
            k++;
        }

        return res;
    }

    public static int findMedian(int[] a1, int[] a2, int n, int m) {
        int[] merged = merge(a1, a2);
        if ((n + m) % 2 == 1) {
            return merged[(n + m)/2];
        } else {
            return (merged[(n + m)/2] + merged[(n + m - 1)/2])/2;
        }
    }

    public static void main(String[] args) {
        int[] a1 = {1, 2, 3, 4, 18, 19, 20, 21}; 
        int[] a2 = {2, 7, 9, 10, 11, 14, 15};
        System.out.println(findMedian(a1, a2, a1.length, a2.length));
    }
}
