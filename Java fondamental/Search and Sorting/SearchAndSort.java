import java.util.Arrays;
import java.util.Scanner;

public class SearchAndSort {

    // Main function: Menu-driven program for searching and sorting
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Example array
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Initial Array: " + Arrays.toString(arr));

        while (true) {
            // Display menu options
            System.out.println("\nChoose an operation:");
            System.out.println("1. Linear Search");
            System.out.println("2. Binary Search (requires sorted array)");
            System.out.println("3. Bubble Sort");
            System.out.println("4. Selection Sort");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Linear Search
                    System.out.print("Enter the element to search: ");
                    int target1 = scanner.nextInt();
                    int linearResult = linearSearch(arr, target1);
                    printSearchResult(linearResult);
                    break;

                case 2:
                    // Binary Search (ensure array is sorted first)
                    Arrays.sort(arr); // Using Java's built-in sort for convenience
                    System.out.println("Sorted Array: " + Arrays.toString(arr));
                    System.out.print("Enter the element to search: ");
                    int target2 = scanner.nextInt();
                    int binaryResult = binarySearch(arr, target2);
                    printSearchResult(binaryResult);
                    break;

                case 3:
                    // Bubble Sort
                    bubbleSort(arr);
                    System.out.println("Array after Bubble Sort: " + Arrays.toString(arr));
                    break;

                case 4:
                    // Selection Sort
                    selectionSort(arr);
                    System.out.println("Array after Selection Sort: " + Arrays.toString(arr));
                    break;

                case 5:
                    // Exit
                    System.out.println("Exiting the program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Linear Search Implementation
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // Element found at index i
            }
        }
        // Element not found
        return -1;
    }

    // Binary Search Implementation
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid; // Element found
            }
            if (arr[mid] < target) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }
        return -1; // Element not found
    }

    // Bubble Sort Implementation
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no swap occurs, the array is sorted
            if (!swapped) break;
        }
    }

    // Selection Sort Implementation
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    // Utility function to print search results
    public static void printSearchResult(int result) {
        if (result == -1) {
            System.out.println("Element not found.");
        } else {
            System.out.println("Element found at index " + result);
        }
    }
}

