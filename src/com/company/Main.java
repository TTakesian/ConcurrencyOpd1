package com.company;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }



    //Opdracht 1
    private void run(){
        Opdracht2(10);
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
                int rand = random.nextInt(9)+1;
                ints.add(rand);
            }
            //Sort the array
            insertionSort(ints);
            //Print out sorted array and duration
            System.out.printf("Sorted array: "+ints.toString()+"\n");
            //Clear the list
            ints.clear();
        }
        //Count the time spent
        long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        duration = duration/1000;
        //Print out the duration of the whole process
        System.out.println("Duration: "+duration+" seconds");
    }


    //Opdracht 2
    private void Opdracht2(int arraySize){
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

            Insertionsort sort1 = new Insertionsort(left);
            Insertionsort sort2 = new Insertionsort(right);
            Thread t1 = new Thread(sort1);
            Thread t2 = new Thread(sort2);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            }
            catch (InterruptedException ex){
            }



            //Sort the array
            insertionSort(ints1);



        //Print out sorted array and duration
        System.out.printf("Sorted array: "+ints1.toString()+"\n");
        //Clear the list
        ints1.clear();


        //Count the time spent
        long duration = (System.currentTimeMillis() - startTime);
        //From milliseconds to seconds
        duration = duration/1000;
        //Print out the duration of the whole process
        System.out.println("Duration: "+duration+" seconds");
    }




    //Opdracht 1
    private ArrayList<Integer> insertionSort(ArrayList<Integer> listToSort) {

        //A counter that will help us identify, the byteValue of dupplicated numbers
        int counterLoop = 0;
        //Loop through the unsorted list
        for(Integer number: listToSort){

            //Set the current string that we are comparing
            int current = number;

            //A counter that will start from the previous value instead of the current value
            int counter = counterLoop-1;

            //Loop throught the previous elements to check if they are smaller or larger than our current string
            while (counter != -1) {
                //If the current string is smaller than its previouse string, then swap their values
                if(current <= listToSort.get(counter)){
                    //Create a temporary variable to hold the larger value
                    int temp = listToSort.get(counter);
                    //Swap values
                    listToSort.set(counter,current);
                    listToSort.set(counterLoop,temp);
                    //Relocate the new position of our current string that we were comparing
                    current = listToSort.get(counter);
                }
                //Go back to the previous value
                counter--;
            }
            //Count the byteValue position
            counterLoop++;

        }
        //Return a sorted list
        return listToSort;
    }

    public class Insertionsort implements Runnable {

        private ArrayList<Integer> listToSort;
        public void run() {
            //A counter that will help us identify, the byteValue of dupplicated numbers
            int counterLoop = 0;
            //Loop through the unsorted list
            for(Integer number: listToSort){

                //Set the current string that we are comparing
                int current = number;

                //A counter that will start from the previous value instead of the current value
                int counter = counterLoop-1;

                //Loop throught the previous elements to check if they are smaller or larger than our current string
                while (counter != -1) {
                    //If the current string is smaller than its previouse string, then swap their values
                    if(current <= listToSort.get(counter)){
                        //Create a temporary variable to hold the larger value
                        int temp = listToSort.get(counter);
                        //Swap values
                        listToSort.set(counter,current);
                        listToSort.set(counterLoop,temp);
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
            int a =0;

        }
        public Insertionsort(ArrayList<Integer> listToSort){
            this.listToSort = listToSort;
        }

        public ArrayList<Integer> getResult(){
            return listToSort;
        }


        public void main(String args[]) {
            new Main().run();
        }



    }
}



