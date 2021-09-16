package algorithms;
public class rotationCount {
    public static int countCWRotationsHelper(int[] arr, int low, int high) {
        if (high < low) {
            return 0;
        }
 
        if (high == low)
            return low;
 
        int mid = low + (high - low)/2;

        if (mid < high && arr[mid+1] < arr[mid]) {
            return (mid+1);
        }

        if (mid > low && arr[mid] < arr[mid - 1]) {
            return mid;
        }
 
        if (arr[high] > arr[mid]) {
            return countCWRotationsHelper(arr, low, mid-1);
        }
 
        return countCWRotationsHelper(arr, mid+1, high);
    }

    public static int countCWRotations(int[] arr) {
        return countCWRotationsHelper(arr, 0, arr.length - 1);
    }

    public static int countACWRotations(int[] arr) {
        return (arr.length - countCWRotations(arr)) % arr.length;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 4, 5, 1, 2};
        System.out.println(countCWRotations(arr));
        System.out.println(countACWRotations(arr));
    }
}
