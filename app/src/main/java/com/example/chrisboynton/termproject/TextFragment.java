package com.example.chrisboynton.termproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chrisboynton on 11/5/17.
 */

public class TextFragment extends Fragment {
    private Text mText;

    private Button mMinusButton;
    private Button mPlusButton;
    private TextView mCount;
    private int mCounter = 0;

    private int mMessageInt;

    private String mMessageEnding;

    private RadioGroup mRelationRadioGroup;

    private RadioButton mFamilyRadioButton;
    private RadioButton mFriendsRadioButton;
    private RadioButton mSignificantOtherRadioButton;
    private RadioButton mGenericRadioButton;



    private Button mContactButton;
    private Button mSendMessageButton;

    private TextView mContactName;



    private static final int REQUEST_CONTACT = 1;

    GenerateText generateText = new GenerateText();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mText = new Text();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_text, container , false);

        //wire up all our widgets

        mMinusButton = (Button) v.findViewById(R.id.minus_button);
        mPlusButton = (Button) v.findViewById(R.id.plus_button);
        mCount = (TextView) v.findViewById(R.id.number_textview);

        mRelationRadioGroup = (RadioGroup) v.findViewById(R.id.relation_radio_group);

        mFamilyRadioButton = (RadioButton) v.findViewById(R.id.family_radio_button);
        mFriendsRadioButton = (RadioButton) v.findViewById(R.id.friend_radio_button);
        mSignificantOtherRadioButton = (RadioButton) v.findViewById(R.id.significant_other_radio_button);
        mGenericRadioButton = (RadioButton) v.findViewById(R.id.generic_relationship_radio_button);


        mContactButton = (Button) v.findViewById(R.id.contact_button);

        mContactName = (TextView) v.findViewById(R.id.contact_textview);



        //keeps the number above 0 and will subtract if it can
        mMinusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mCounter == 0)
                {
                    Toast.makeText(getActivity(), R.string.to_low, Toast.LENGTH_SHORT).show();
                }
                else {
                    mCounter--;
                    mCount.setText("" + mCounter);
                }

            }
        });



        //adds to the counter and stops if it is about to go over 10
        mPlusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mCounter == 10)
                {
                    Toast.makeText(getActivity(), R.string.to_high, Toast.LENGTH_SHORT).show();
                }else {
                    ++mCounter;
                    mCount.setText("" + mCounter);
                }
            }
        });








        mSendMessageButton = (Button) v.findViewById(R.id.send_message_button);
        mSendMessageButton.setEnabled(false);
        mSendMessageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                int selected = mRelationRadioGroup.getCheckedRadioButtonId();

                //makes sure a radiobutton is selected before sending the message
                if (selected == -1)
                {
                    Toast.makeText(getActivity(), R.string.not_selected, Toast.LENGTH_SHORT).show();

                }else {

                    if (selected == mFamilyRadioButton.getId()) {
                        //generates the text message in the generateText class
                        //sends in the counter
                         mMessageInt = generateText.generateText(mCounter);
                        //adds the sign off of the message based on
                         mMessageEnding = getString(R.string.family_end);

                    }
                    if (selected == mFriendsRadioButton.getId()) {
                        mMessageInt = generateText.generateText(mCounter);
                        mMessageEnding = getString(R.string.friend_end);

                    }
                    if (selected == mSignificantOtherRadioButton.getId()) {
                        mMessageInt = generateText.generateText(mCounter);
                        mMessageEnding = getString(R.string.significant_end);

                    }
                    if (selected == mGenericRadioButton.getId()) {
                        mMessageInt = generateText.generateText(mCounter);
                        mMessageEnding = "";


                    }

                    //send the action to open the messageing app and sends with it the information from
                    //the contact selected
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, getTextMessage(mMessageInt));
                    i.putExtra(Intent.EXTRA_SUBJECT, mText.getmContact());
                    i.putExtra("address", mText.getmPhone());
                    i = Intent.createChooser(i, getString(R.string.send_message_via));
                    startActivity(i);
                }
            }
        });


        //starts the Contacts app and retrieves the data from the selected contact
        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mContactButton = (Button) v.findViewById(R.id.contact_button);
        mContactButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        //if there is a value then it will put it in the button so you can see who you are messageing
        if(mText.getmContact() != null){
            mContactButton.setText(mText.getmContact());
        }


        //checks for the default contact app
        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null){
            mContactButton.setEnabled(false);
        }



        return v;
    }



    //once the contact is selected and the app returns to my fragments this gets called
   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();

            Cursor c = null;
            String phone = "";
            String name = "";
            try{

                String id = contactUri.getLastPathSegment();

                // query for the information
               c = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                       null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[] { id }, null);

                //set the name from the database
                int nameId = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                //set the Phone from the data base
                int phoneId = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);


                if (c.moveToFirst()) {
                    phone = c.getString(phoneId);
                   name = c.getString(nameId);

                    //set the name and email within the program
                    mText.setmContact(name);

                    mText.setmPhone(phone);



                }
                mSendMessageButton.setEnabled(true);
                mContactName.setText(name);


            } finally {
                c.close();
            }
        }
    }


    private String getTextMessage(int message){



        //makes the message string
        String report = ("" + mText.getmContact() + ", \n" + getString(message) + "\n\n" + mMessageEnding);



        return report;


    }


}
