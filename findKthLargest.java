
public class findKthLargest {
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //randomised partitioning scheme
    private static int partition(int[] arr, int low, int high) {
        int randomIndex = (int) (low + (Math.random()*(high-low+1)));
	    swap(arr,high,randomIndex);
	    int pivot = arr[high];
	    int pivotIndex = low;
	    for (int i = low ; i < high ; i++) {
		    if(arr[i] <= pivot){
			    swap(arr,i,pivotIndex);
			    pivotIndex++;
		    }
	    }
	    swap(arr,pivotIndex,high);
	    return pivotIndex;
    }

    //O(n)
    public static int quickSelect(int arr[], int low, int high, int k) {
        int partitionIndex = partition(arr,low,high);
    
        if (partitionIndex == k) {
            return partitionIndex;
        } else if (partitionIndex > k) { 
            return quickSelect(arr, low, partitionIndex-1,k);
        } else {
            return quickSelect(arr, partitionIndex+1, high, k);
        }
    }

    public static int findKthLargestElement(int[] arr, int k) {
        int index = quickSelect(arr, 0, arr.length-1, arr.length - k);
        return arr[index];
    }

    public static int findKthSmallestElement(int[] arr, int k) {
        int index = quickSelect(arr, 0, arr.length-1, k - 1);
        return arr[index];
    }

    public static void main(String[] args) {
        int[] arr = new int[] {3, 1, 2, 1, 4, 5};
        System.out.println(findKthLargestElement(arr, 4));
        System.out.println(findKthSmallestElement(arr, 1));
    }
}
