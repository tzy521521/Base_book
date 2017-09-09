package chapter30;

import chapter23.MergeSort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort1 {
  public static void main(String[] args) {
    final int SIZE = 7000000;
    int[] list1 = new int[SIZE];
    int[] list2 = new int[SIZE];
    int[] list3 = new int[SIZE];
    int[] list4 = new int[SIZE];

    for (int i = 0; i < list1.length; i++)
      list1[i] = list2[i] = list3[i] = list4[i] = (int)(Math.random() * 10000000);

    long startTime = System.currentTimeMillis();
    parallelMergeSort(list1); // Invoke parallel merge sort
    long endTime = System.currentTimeMillis();
    System.out.println("Parallel time with "
      + Runtime.getRuntime().availableProcessors() + 
      " processors is " + (endTime - startTime) + " milliseconds");

    startTime = System.currentTimeMillis();
    MergeSort.mergeSort(list2); // MergeSort is in Listing 24.5
    endTime = System.currentTimeMillis();
    System.out.println("Sequential time is " +
      (endTime - startTime) + " milliseconds");

    startTime = System.currentTimeMillis();
    Arrays.parallelSort(list3); // MergeSort is in Listing 24.5
    endTime = System.currentTimeMillis();
    System.out.println("Arrays.parallelSort time is "+ Runtime.getRuntime().availableProcessors() +
            " processors is " + (endTime - startTime) + " milliseconds");

    startTime = System.currentTimeMillis();
    Arrays.sort(list4); // MergeSort is in Listing 24.5
    endTime = System.currentTimeMillis();
    System.out.println("Arrays.sort time is " + (endTime - startTime) + " milliseconds");

  }

  public static void parallelMergeSort(int[] list) {
    RecursiveAction mainTask = new SortTask(list);
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(mainTask);
  }

  private static class SortTask extends RecursiveAction {
    private final int THRESHOLD = 500;
    private int[] list;

    SortTask(int[] list) {
      this.list = list;
    }

    @Override
    protected void compute() {
      if (list.length >1){
        int[] firstHalf = new int[list.length / 2];
        System.arraycopy(list, 0, firstHalf, 0, list.length / 2);

        // Obtain the second half
        int secondHalfLength = list.length - list.length / 2;
        int[] secondHalf = new int[secondHalfLength];
        System.arraycopy(list, list.length / 2,
                secondHalf, 0, secondHalfLength);

        // Recursively sort the two halves
        invokeAll(new SortTask(firstHalf),
                new SortTask(secondHalf));

        // Merge firstHalf with secondHalf into list
        MergeSort.merge(firstHalf, secondHalf, list);
      }
    }
  }
}
