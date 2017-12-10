package com.example.chrisboynton.termproject;

import android.app.Activity;
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
 * Created by chrisboynton on 12/5/17.
 */

public class TextFragment extends Fragment {
    private Text mText;

    private Button mMinusButton;
    private Button mPlusButton;
    private TextView mCount;
    private int mCounter = 0;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_text, container , false);

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

                if (selected == -1)
                {
                    Toast.makeText(getActivity(), R.string.not_selected, Toast.LENGTH_SHORT).show();

                }else {

                    if (selected == mFamilyRadioButton.getId()) {
                        generateText.generateText(mCounter,1);

                    }
                    if (selected == mFriendsRadioButton.getId()) {
                        generateText.generateText(mCounter,2);

                    }
                    if (selected == mSignificantOtherRadioButton.getId()) {
                        generateText.generateText(mCounter,3);

                    }
                    if (selected == mGenericRadioButton.getId()) {
                        generateText.generateText(mCounter,4);

                    }


                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, getText());
                    i.putExtra(Intent.EXTRA_SUBJECT, mText.getmContact());
                    i.putExtra("address", mText.getmPhone());
                    i = Intent.createChooser(i, getString(R.string.send_message_via));
                    startActivity(i);
                }
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mContactButton = (Button) v.findViewById(R.id.contact_button);
        mContactButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if(mText.getmContact() != null){
            mContactButton.setText(mText.getmContact());
        }


        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null){
            mContactButton.setEnabled(false);
        }



        return v;
    }



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
               c = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[] { id }, null);

                //set the name from the database
                int nameId = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                //set the email from the data base
                int phoneId = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);


                if (c.moveToFirst()) {
                    phone = c.getString(phoneId);
                   name = c.getString(nameId);

                    //set the name and email within the program
                    mText.setmContact(name);

                    mText.setmPhone(phone);



                }
                //enable and disable buttons so they can email but not select another contact
                mContactButton.setEnabled(false);
                mSendMessageButton.setEnabled(true);
                mContactName.setText(name);


            } finally {
                c.close();
            }
        }
    }


    private String getText(){



        String report = ("" + mCounter + mText.getmPhone() + mText.getmContact());



        return report;


    }


}
