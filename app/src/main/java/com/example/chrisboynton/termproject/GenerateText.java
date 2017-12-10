package com.example.chrisboynton.termproject;

/**
 * Created by chrisboynton on 12/6/17.
 */

public class GenerateText {

    String output;


    public String generateText(int impvalue, int relation ) {

        if (impvalue <= 3)
        {

            output = "<=3";

        }
        if (impvalue > 3 || impvalue <= 7){


            output = ">=3 or <=7";
        }
        if (impvalue > 7)
        {
            output = ">7";
        }

        if(1 == relation)
        {
            //family

        }
        if(2 == relation)
        {
            //friends

        }
        if(3 == relation)
        {
            //significant

        }else
        {
            //generic

        }

        return null;
    }



}
