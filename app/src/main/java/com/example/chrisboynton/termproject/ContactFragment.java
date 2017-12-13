package com.example.chrisboynton.termproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by chrisboynton on 12/1/17.
 */

public class ContactFragment extends Fragment {

    //inizialize variables
    private Button mContactInfo;
    private Button mContact;
    private Button mSendMessage;

    //different constants for the differnt buttons that reference the contact app
    private static final int REQUEST_CONTACT = 1;
    private static final int REQUEST_CONTACTS = 2;

    private Text mMessage;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessage = new Text();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contact,container,false);

        mContactInfo = (Button) view.findViewById(R.id.contact_info_button);
        mContact = (Button) view.findViewById(R.id.contact_button_contact_frag);
        mSendMessage = (Button) view.findViewById(R.id.send_message_button_contact_frag);

        mContact.setEnabled(false);



        //send the action to open the messageing app and sends with it the information from
        //the contact selected
        mSendMessage.setEnabled(false);
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getMessage());
                i.putExtra(Intent.EXTRA_SUBJECT, mMessage.getmContact());
                i.putExtra("address", mMessage.getmRecieve());
                i = Intent.createChooser(i, getString(R.string.send_message_via));
                startActivity(i);


            }
        });




        //starts the Contacts app and retrieves the data from the selected contact
        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mContactInfo = (Button) view.findViewById(R.id.contact_info_button);
        mContactInfo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivityForResult(pickContact, REQUEST_CONTACT);
                mContactInfo.setEnabled(false);
                mContact.setEnabled(true);
            }
        });

        //starts the Contacts app and retrieves the data from the selected contact
        final Intent pickContacts = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContacts, REQUEST_CONTACTS);
                mContact.setEnabled(false);
                mSendMessage.setEnabled(true);

            }
        });





        //checks for the default contacts app
        PackageManager packageManager = getActivity().getPackageManager();
        if(packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null){
            mContactInfo.setEnabled(false);
            mContact.setEnabled(false);
            mSendMessage.setEnabled(true);
        }




       return view;
    }


    //once the contact is selected and the app returns to my fragments this gets called
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CONTACT ||requestCode == REQUEST_CONTACTS && data != null) {
            Uri contactUri = data.getData();

            //used multiple cursors made more sence to me but might not be optimal
            Cursor c = null;
            Cursor emailCursor = null;
            Cursor addressCursor = null;


            String phone = "";
            String name = "";
            String email = "";
            String address = "";
            try{

                String id = contactUri.getLastPathSegment();

                 //query for the information
                c = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[] { id }, null);

                emailCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                        new String[] { id }, null);

                addressCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?", new String[] { id }, null);

                //set the name from the database
                int nameId = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                //set the phone from the data base
                int phoneId = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                //set the email
                int emailId = emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

                //set the address
                int addressId = addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS);


                //only gets called for the specific button is pressed
                if (requestCode == REQUEST_CONTACTS){
                    if (c.moveToFirst()) {
                        phone = c.getString(phoneId);
                        mMessage.setmRecieve(phone);
                        mSendMessage.setEnabled(true);
                    }
                }

                if (requestCode == REQUEST_CONTACT) {
                    if (c.moveToFirst()) {
                        phone = c.getString(phoneId);
                        name = c.getString(nameId);


                        //set the name and phone within the program
                        mMessage.setmContact(name);

                        mMessage.setmPhone(phone);
                    }


                    if (emailCursor.moveToFirst()) {
                        email = emailCursor.getString(emailId);


                        mMessage.setmEmail(email);
                    }

                    if (addressCursor.moveToFirst()) {
                        address = addressCursor.getString(addressId);
                        mMessage.setmAddress(address);

                    }

                }







            } finally {
                //closing the cursors
                c.close();
                emailCursor.close();
                addressCursor.close();
            }
        }
    }




    private String getMessage(){


        //creates the string for our message, with the fields its gathered

        String report = ("This is the contact info for\n" + mMessage.getmContact() + ", \n" + "Phone: " +
        mMessage.getmPhone() + "\n" + "Email: " + mMessage.getmEmail() +
        "\nAddress: " + mMessage.getmAddress());



        return report;


    }
}
