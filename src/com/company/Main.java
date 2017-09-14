package com.company;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }


    /***
     * This method will run all of the written exercises
     */
    private void run() {
       opdracht1(1000);
       opdracht2(1000);
       opdracht3(800000, 100);
    }


    /***
     * A function that will use the getRandomNumberArray function to create an unsorted arraylist.
     * The unsorted arraylist will be sorted using the insertion sort. A timer will start when starting
     * the sorting and will finish counting when the sorting is finished. When finished, the sorted array
     * will print out + the duration in seconds
     * @param max The length that will be given to the arraylist
     */
    private void opdracht1(int max) {
        //Create a new array
        ArrayList<Integer> ints = getRandomNumberArray(max);
        //Create startTimer
        long startTime = System.currentTimeMillis();
        InsertionSort insertionSort = new InsertionSort(ints);
        Thread thread = new Thread(insertionSort);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException ex) {
        }
        //Print out sorted array and duration
        System.out.printf("Sorted array: " + ints.toString() + "\n");
        //Count the time spent
        long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        duration = duration / 1000;
        //Print out the duration of the whole process
        System.out.println("Duration: " + duration + " seconds");
    }


    /***
     * This function will create a random numbers array using the getRandomNumberArray function
     * and then split the given array into 2 and run each in a thread.
     * After the sorting, the splitted arrays will me merged using the merge function.
     * @param arraySize The length of the array
     */
    private void opdracht2(int arraySize) {
        ArrayList<Integer> listToSort = getRandomNumberArray(arraySize);
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        for (int j = 0; j < listToSort.size() / 2; j++) {
            left.add(listToSort.get(j));
        }
        for (int j = listToSort.size() / 2 ; j < listToSort.size(); j++) {
            right.add(listToSort.get(j));
        }
        InsertionSort sort1 = new InsertionSort(left);
        InsertionSort sort2 = new InsertionSort(right);
        Thread t1 = new Thread(sort1);
        Thread t2 = new Thread(sort2);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
        }
        ArrayList<Integer> combinedList = merge(left,right,listToSort);
        listToSort = combinedList;
    }
    /***
     * This function will create a random numbers array using the getRandomNumberArray function
     * and then split the given array until each thread holds an array that has a length of the threshhold.
     * Each thread will sort the array that it contains using the insertionSort function.
     * After the sorting, the splitted arrays will me merged using the merge function.
     * @param arraySize The length of the array
     * @param threshold The length of the array which each thread will contain
     */
    private void opdracht3(int arraySize, int threshold) {
        //Create an array
        ArrayList<Integer> row = getRandomNumberArray(arraySize);
        //Create startTimer
        long startTime = System.currentTimeMillis();
        InsertionSortSplitter insertionSortSplitter = new InsertionSortSplitter(row, threshold);
        Thread run = new Thread(insertionSortSplitter);
        run.start();
        try {
            run.join();
        } catch (InterruptedException ex) {
        }
        row = insertionSortSplitter.getListToSort();
        System.out.printf("Done\n");
        //Count the time spent
        long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        duration = duration / 1000;
        //Print out the duration of the whole process
        System.out.println("Duration: " + duration + " seconds");


    }

    /***
     * This function will merge the incoming arrays together. When the user runs this function, the program
     * will take the left arraylist and the right arraylist and compare them to each other until they are merged
     * in the correct order. listToSort arraylist is used to compare the left and the right arraylist with it.
     * @param left The left arraylist
     * @param right The right arraylist
     * @param listToSort The original list that will be compared to
     * @return Returns a merged array that is sorted
     */
    private ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right,ArrayList<Integer> listToSort) {
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;

        // As long as neither the left nor the right ArrayList has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ( (left.get(leftIndex).compareTo(right.get(rightIndex))) < 0) {
                listToSort.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                listToSort.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }

        ArrayList<Integer> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            // The left ArrayList has been use up...
            rest = right;
            restIndex = rightIndex;
        } else {
            // The right ArrayList has been used up...
            rest = left;
            restIndex = leftIndex;
        }

        // Copy the rest of whichever ArrayList (left or right) was not used up.
        for (int i=restIndex; i<rest.size(); i++) {
            listToSort.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
        return listToSort;
    }

    /***
     * This class provides the insertion sort for the unsorted arraylist that run through it
     * this class implements Runnable so that it is able to be run in a thread. When the function run()
     * is used, the program will run through the ararylist and compare the numbers to sort them from low
     * to high.
     */
    public class InsertionSort implements Runnable {

        private ArrayList<Integer> listToSort;

        public void run() {
            //Loop through the unsorted list
            for (Integer number : listToSort) {
                //Set the current string that we are comparing
                int current = number;
                //A counter that will start from the previous value instead of the current value
                int counter = listToSort.indexOf(number) - 1;

                //Loop throught the previous elements to check if they are smaller or larger than our current string
                while (counter != -1) {
                    //If the current string is smaller than its previouse string, then swap their values
                    if (current <= listToSort.get(counter)) {
                        //Create a temporary variable to hold the larger value
                        int temp = listToSort.get(counter);
                        //The index position of the current value
                        int currentIndex = listToSort.indexOf(number);
                        //Swap values
                        listToSort.set(counter, current);
                        listToSort.set(currentIndex, temp);
                        //Relocate the new position of our current string that we were comparing
                        current = listToSort.get(counter);
                    }
                    //Go back to the previous value
                    counter--;
                }

            }
        }

        public InsertionSort(ArrayList<Integer> listToSort) {
            this.listToSort = listToSort;
        }

        public void main(String args[]) {
            new Main().run();
        }


    }

    /***
     * This class can be used to split the incoming array into threads. When the run function is called,
     * the program will split the given array into the provided threshhold. If the length of the array is
     * 100000 and the threshhold is 100, then the array will be splitted until each thread contains an array
     * with the length of 100. After the splitting, the program starts sorting in threads using the insertionSort.
     * After finishing with the sorting, the function rus the merge method to merge the splited arrays in to a
     * complete sorted array.
     */
    public class InsertionSortSplitter implements Runnable {

        private ArrayList<Integer> listToSort;
        private int threshold;


        @Override
        public void run() {

            //Split the array if the the size is above the threshold
            if (listToSort.size() > threshold) {
                ArrayList<Integer> left = new ArrayList<>();
                ArrayList<Integer> right = new ArrayList<>();
                for (int j = 0; j < listToSort.size() / 2; j++) {
                    left.add(listToSort.get(j));
                }
                for (int j = listToSort.size() / 2 ; j < listToSort.size(); j++) {
                    right.add(listToSort.get(j));
                }
                InsertionSortSplitter sort1 = new InsertionSortSplitter(left, threshold);
                InsertionSortSplitter sort2 = new InsertionSortSplitter(right, threshold);
                Thread t1 = new Thread(sort1);
                Thread t2 = new Thread(sort2);
                t1.start();
                t2.start();
                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException ex) {
                }
                ArrayList<Integer> combinedList = merge(left,right,listToSort);
                listToSort = combinedList;

            } else {
                InsertionSort s = new InsertionSort(listToSort);
                Thread run = new Thread(s);
                run.start();
                try {
                    run.join();
                } catch (InterruptedException ex) {
                }
            }
        }

        InsertionSortSplitter(ArrayList<Integer> listToSort, int threshhold) {
            this.listToSort = listToSort;
            this.threshold = threshhold;
        }

        public ArrayList<Integer> getListToSort() {
            return listToSort;
        }

        public void main(String args[]) {
            new Main().run();
        }

    }

    /***
     * A function that will create an array with the length that is inputed with max.
     * A linkedHashSet is used to prevent dupplication
     * @param max the length of the arraylist
     * @return an arraylist filled with random,unsorted, non-dupplicate numbers
     */
    private ArrayList<Integer> getRandomNumberArray(int max) {
        ArrayList<Integer> row = new ArrayList<>();
        //Create a new random
        Random random = new Random();
        int rand;
        //Create a set that will hold the random numbers
        Set<Integer> linkedHashSet = new LinkedHashSet<>();
        //Loop max times and add the random generated numbers to our set
        for (int j = 0; j < max; j++) {
            rand = random.nextInt(max) + 1;
            boolean added = linkedHashSet.contains(rand);
            if(added){
                j--;
            }else {
                linkedHashSet.add(rand);
            }
        }
        //Add the set to the arraylist for sorting
        for (Integer integer : linkedHashSet) {
            row.add(integer);
        }
        return row;
    }
}



