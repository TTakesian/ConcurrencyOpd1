package com.company;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }


    //Opdracht 1
    private void run() {
        // opdracht1(5000);
        //opdracht2(40);
        opdracht3(100000, 100);
    }

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
            linkedHashSet.add(rand);
        }
        //Add the set to the arraylist for sorting
        for (Integer integer : linkedHashSet) {
            row.add(integer);
        }
        return row;
    }

    //Opdracht 2
    private void opdracht2(int arraySize) {
        //Create 3 arrays
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        ArrayList<Integer> row = getRandomNumberArray(arraySize);
        //Split array
        for (int j = 0; j < row.size() / 2; j++) {
            left.add(row.get(j));
        }
        for (int j = arraySize / 2 + 1; j < row.size(); j++) {
            right.add(row.get(j));
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
        ArrayList<Integer> merged = new ArrayList<>();
        merged.addAll(left);
        merged.addAll(right);
        InsertionSort finalSort = new InsertionSort(merged);
        Thread finalThead = new Thread(finalSort);
        finalThead.run();
        try{
            finalThead.join();
        }
        catch (InterruptedException ex){
        }


        //Print out sorted array and duration
        System.out.printf("Sorted array: " + merged.toString() + "\n");
        //Clear the list
        row.clear();


        //Count the time spent
        //long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        //duration = duration / 1000;
        //Print out the duration of the whole process
        // System.out.println("Duration: " + duration + " seconds");
    }


    //Opdracht 3
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
        System.out.printf("Sorted array: " + row.toString() + "\n");

    }


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

        private ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right,ArrayList<Integer> listToSort) {
            int indexLinks = 0;
            int indexRechts = 0;
            while (listToSort.size() < (left.size() + right.size())) {
                if (indexLinks != left.size() && indexRechts != right.size()) {
                    if (right.get(indexRechts) > left.get(indexLinks)) {
                        if (listToSort.size() == 0) {
                            listToSort.add(left.get(indexLinks));
                            indexLinks++;
                        } else {
                            listToSort.add(listToSort.size(), left.get(indexLinks));
                            indexLinks++;
                        }
                    } else if (right.get(indexRechts) < left.get(indexLinks)) {
                        if (listToSort.size() == 0) {
                            listToSort.add(right.get(indexRechts));
                            indexRechts++;
                        } else {
                            listToSort.add(listToSort.size(), right.get(indexRechts));
                            indexRechts++;
                        }
                    } else if (right.get(indexRechts) == left.get(indexLinks)) {

                        if (listToSort.size() == 0) {
                            listToSort.add(right.get(indexRechts));
                            listToSort.add(left.get(indexLinks));
                            indexRechts++;
                            indexLinks++;
                        } else {
                            listToSort.add(listToSort.size(), right.get(indexRechts));
                            listToSort.add(listToSort.size(), left.get(indexLinks));
                            indexRechts++;
                            indexLinks++;
                        }

                    }

                } else if (indexLinks == left.size() && indexRechts != right.size()) {
                    while (indexRechts < right.size()) {
                        listToSort.add(listToSort.size(), right.get(indexRechts));
                        indexRechts++;
                    }

                } else if (indexLinks != left.size() && indexRechts == right.size()) {
                    while (indexLinks < left.size()) {
                        listToSort.add(listToSort.size(), left.get(indexLinks));
                        indexLinks++;
                    }

                }

            }
            return listToSort;
        }


        public void main(String args[]) {
            new Main().run();
        }


    }

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
}



