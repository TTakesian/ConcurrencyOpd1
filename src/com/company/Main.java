package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }


    //Opdracht 1
    private void run() {
        Opdracht1();
        Opdracht2(10);
    }


    private void Opdracht1() {
        //Create a new array
        ArrayList<Integer> ints = new ArrayList<>();
        //Create a new random
        Random random = new Random();
        //Create startTimer
        long startTime = System.currentTimeMillis();
        //Sort 800000 times
        for (int i = 0; i < 800001; i++) {
            //Fill the array with random numbers 9 times
            for (int j = 0; j < 10; j++) {
                int rand = random.nextInt(9) + 1;
                ints.add(rand);
            }
            //Sort the array
            insertionSort(ints);
            //Print out sorted array and duration
            System.out.printf("Sorted array: " + ints.toString() + "\n");
            //Clear the list
            ints.clear();
        }
        //Count the time spent
        long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        duration = duration / 1000;
        //Print out the duration of the whole process
        System.out.println("Duration: " + duration + " seconds");
    }


    //Opdracht 2
    private void Opdracht2(int arraySize) {
        //Create 3 arrays
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> ints1 = new ArrayList<>();
        //Create a new random
        Random random = new Random();
        //Create startTimer
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arraySize; i++) {
            int rand = random.nextInt(9) + 1;
            ints1.add(rand);
        }
        //split the array in 2
        for (int j = 0; j < arraySize / 2; j++) {
            left.add(ints1.get(j));
        }
        for (int j = arraySize / 2 + 1; j < arraySize; j++) {
            right.add(ints1.get(j));
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


        //Sort the array
        insertionSort(ints1);


        //Print out sorted array and duration
        System.out.printf("Sorted array: " + ints1.toString() + "\n");
        //Clear the list
        ints1.clear();


        //Count the time spent
        long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        duration = duration / 1000;
        //Print out the duration of the whole process
        System.out.println("Duration: " + duration + " seconds");
    }


    //Opdracht 1
    private ArrayList<Integer> insertionSort(ArrayList<Integer> listToSort) {

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
        //Return a sorted list
        return listToSort;
    }

    public class InsertionSort implements Runnable {

        private ArrayList<Integer> listToSort;

        public void run() {
            //A counter that will help us identify, the byteValue of dupplicated numbers
            int counterLoop = 0;
            //Loop through the unsorted list
            for (Integer number : listToSort) {

                //Set the current string that we are comparing
                int current = number;

                //A counter that will start from the previous value instead of the current value
                int counter = counterLoop - 1;

                //Loop throught the previous elements to check if they are smaller or larger than our current string
                while (counter != -1) {
                    //If the current string is smaller than its previouse string, then swap their values
                    if (current <= listToSort.get(counter)) {
                        //Create a temporary variable to hold the larger value
                        int temp = listToSort.get(counter);
                        //Swap values
                        listToSort.set(counter, current);
                        listToSort.set(counterLoop, temp);
                        //Relocate the new position of our current string that we were comparing
                        current = listToSort.get(counter);
                    }
                    //Go back to the previous value
                    counter--;
                }
                //Count the byteValue position
                counterLoop++;
            }
            listToSort = listToSort;
            int a = 0;

        }

        public InsertionSort(ArrayList<Integer> listToSort) {
            this.listToSort = listToSort;
        }

        private ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right) {
            int indexLinks = 0;
            int indexRechts = 0;
            ArrayList<Integer> merge = new ArrayList<>();
            while (merge.size() < (left.size() + right.size())) {
                if (indexLinks != left.size() && indexRechts != right.size()) {
                    if (right.get(indexRechts) > left.get(indexLinks)) {
                        if (merge.size() == 0) {
                            merge.add(left.get(indexLinks));
                            indexLinks++;
                        } else {
                            merge.add(merge.size(), left.get(indexLinks));
                            indexLinks++;
                        }
                    } else if (right.get(indexRechts) < left.get(indexLinks)) {
                        if (merge.size() == 0) {
                            merge.add(right.get(indexRechts));
                            indexRechts++;
                        } else {
                            merge.add(merge.size(), right.get(indexRechts));
                            indexRechts++;
                        }
                    } else if (right.get(indexRechts) == left.get(indexLinks)) {

                        if (merge.size() == 0) {
                            merge.add(right.get(indexRechts));
                            merge.add(left.get(indexLinks));
                            indexRechts++;
                            indexLinks++;
                        } else {
                            merge.add(merge.size(), right.get(indexRechts));
                            merge.add(merge.size(), left.get(indexLinks));
                            indexRechts++;
                            indexLinks++;
                        }

                    }

                } else if (indexLinks == left.size() && indexRechts != right.size()) {
                    while (indexRechts < right.size()) {
                        merge.add(merge.size(), right.get(indexRechts));
                        indexRechts++;
                    }

                } else if (indexLinks != left.size() && indexRechts == right.size()) {
                    while (indexLinks < left.size()) {
                        merge.add(merge.size(), left.get(indexLinks));
                        indexLinks++;
                    }

                }

            }
            return merge;
        }


        public void main(String args[]) {
            new Main().run();
        }


    }
}



