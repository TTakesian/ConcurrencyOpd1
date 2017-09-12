package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    private void run(){
        //Create a new array
        ArrayList<Integer> ints = new ArrayList<>();
        //Create a new random
        Random random = new Random();
        //Create startTimer
        long startTime = System.currentTimeMillis();
        //Sort 800000 times
        for (int i = 0; i < 1; i++) {
            ArrayList<Integer> dupplicate = new ArrayList();
            //Fill the array with random numbers 9 times
            for (int j = 0; j < 10; j++) {
                int rand = random.nextInt(9)+1;
                if(!dupplicate.contains(rand)){
                    dupplicate.add(rand);
                }else if(dupplicate.contains(rand)) {
                    boolean contains = true;
                    while (contains){
                        rand = random.nextInt(9)+1;
                        if(dupplicate.contains(rand)){
                            contains = true;
                        }else {
                            contains = false;
                        }
                    }
                }
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
        System.out.println("Duration: "+duration);
    }

    //Opdracht 1
    private ArrayList<Integer> insertionSort(ArrayList<Integer> listToSort) {

        for(Integer person: listToSort){

            //Set the current string that we are comparing
            int current = person;

            //A counter that will start from the previous value instead of the current value
            int counter = person.byteValue()-1;

            //Loop throught the previous elements to check if they are smaller or larger than our current string
            while (counter != -1) {
                //If the current string is smaller than its previouse string, then swap their values
                if(current <= listToSort.get(counter)){
                    //Create a temporary variable to hold the larger value
                    int temp = listToSort.get(counter);
                    //The index position of the current value
                    int currentIndex = person.byteValue();
                    //Swap values
                    listToSort.set(counter,current);
                    listToSort.set(currentIndex,temp);
                    //Relocate the new position of our current string that we were comparing
                    current = listToSort.get(counter);
                }
                //Go back to the previous value
                counter--;
            }
        }
        return listToSort;
    }
}

