public class bitonicSearch {
    private static int ascendBinarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (arr[mid] == key) {
                return mid;
            }
            if (arr[mid] > key) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    private static int descendBinarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (arr[mid] == key) {
                return mid;
            }
            if (arr[mid] > key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private static int findBitonicPoint(int[] arr, int low, int high) {
        int mid = low + (high - low)/2;

        if (arr[mid] > arr[mid + 1] && arr[mid] > arr[mid - 1]) {
            return mid;
        } else if (arr[mid] > arr[mid + 1] && arr[mid] < arr[mid - 1]) {
            return findBitonicPoint(arr, low, mid);
        } else {
            return findBitonicPoint(arr, mid, high);
        }
    }

    public static int bitonicBinarySearch(int[] arr, int key) {
        int bitonicPoint = findBitonicPoint(arr, 0, arr.length - 1);

        if (arr[bitonicPoint] < key) {
            return -1;
        }
        if (arr[bitonicPoint] == key) {
            return bitonicPoint;
        } else {
            int t = ascendBinarySearch(arr, 0, bitonicPoint - 1, key);
            if (t != -1) {
                return t;
            }
            return descendBinarySearch(arr, bitonicPoint + 1, arr.length - 1, key);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 3, 2, -1};
        System.out.println(bitonicBinarySearch(arr, -1));
    }
}