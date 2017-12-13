package com.example.chrisboynton.termproject;

import android.content.Context;

import java.util.Random;

/**
 * Created by chrisboynton on 12/6/17.
 */

//this class is used to decide which string should be used based on the importance number
    //then makes a random number and goes from there
public class GenerateText {

    private int output;

    Random randomGenerator = new Random();

    //generates a random number based on the build in methood
    private int randomInt = randomGenerator.nextInt(3);


//passes in the importance level
    public int generateText(int impValue) {


        if (impValue <= 3)
        {

            leastImportant();

        }
        if (impValue > 3 && impValue <= 7){
            Important();
        }
        if (impValue > 7)
        {
            mostImportant();
        }




        return output;
    }



//puts the random value to the string value
    private void leastImportant()
    {
        if (randomInt == 0) {
            output = R.string.importance_1_1;
        }
        if (randomInt == 1) {
            output = R.string.importance_1_2;
        }
        if(randomInt == 2) {
            output = R.string.importance_1_3;
        }
    }

    private void Important()
    {
        if (randomInt == 0) {
            output = R.string.importance_2_1;
        }
        if (randomInt == 1) {
            output = R.string.importance_2_2;
        }
        if (randomInt == 2) {

            output = R.string.importance_2_3;
        }

    }

    private void mostImportant()
    {
        if (randomInt == 0) {
            output = R.string.importance_3_1;
        }
        if(randomInt == 1) {
            output = R.string.importance_3_2;
        }
        if (randomInt == 2) {
            output = R.string.importance_3_3;
        }

    }




}
