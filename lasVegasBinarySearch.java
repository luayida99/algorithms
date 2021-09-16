
public class lasVegasBinarySearch {
    public static int randomBinarySearch(int[] arr, int low, int high, int key) {
        if (high >= low) {
            int randomIdx = (low + (int)(Math.random() % (high - low + 1)));
            if (arr[randomIdx] == key) {
                return randomIdx;
            }
            if (arr[randomIdx] < key) {
                return randomBinarySearch(arr, randomIdx + 1, high, key);
            }
            return randomBinarySearch(arr, low, randomIdx - 1, key);
        }

        return -1;
    }

    public static int deterministicBinarySearch(int[] arr, int low, int high, int key) {
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        System.out.println(randomBinarySearch(arr, 0, arr.length - 1, 4));
        System.out.println(deterministicBinarySearch(arr, 0, arr.length, 4));
    }
}