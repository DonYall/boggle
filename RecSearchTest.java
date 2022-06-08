// Main class
class RecSearchTest {
 
    // Method 1
    // Recursive binary search
    // Returns index of x if it is present
    // in arr[l..r], else return -1
    int binarySearch(String[] arr, int l, int r, String x)
    {
    	        // Restrict the boundary of right index
        // and the left index to prevent
        // overflow of indices
        if (r >= l && l <= arr.length - 1) {
 
            int mid = l + (r - l) / 2;
            int res = x.compareTo(arr[mid]);

            // If the element is present
            // at the middle itself
            if (res == 0) {
                return mid;
            }
            // If element is smaller than mid, then it can
            // only be present in left subarray
            if (res < 0 ) {
                return binarySearch(arr, l, mid - 1, x);
            }
            // Else the element can only be present
            // in right subarray
            else if (res > 0) {
            	return binarySearch(arr, mid + 1, r, x);
            }
        }
 
        // We reach here when element is not present in
        // array
        return -1;
    }
 
    // Method 2
    // Main driver method
    public static void main(String args[])
    {
        // Creating object of above class
        RecSearchTest ob = new RecSearchTest();
 
        // Custom input array
        String arr[] = {"abs","bear","cringe","dare","enough","feeling" };
 
        // Length of array
        int n = arr.length;
 
        // Custom element to be checked
        // whether present or not
        String x = "feeling";
 
        // Calling above method
        int result = ob.binarySearch(arr, 0, n - 1, x);
 
        // Element present
        if (result == -1)
 
            // Print statement
            System.out.println("Element not present");
 
        // Element not present
        else
 
            // Print statement
            System.out.println("Element found at index "
                               + result);
    }
}