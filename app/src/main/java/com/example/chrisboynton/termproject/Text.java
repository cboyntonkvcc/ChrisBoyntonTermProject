package com.example.chrisboynton.termproject;

import java.util.UUID;

/**
 * Created by chrisboynton on 11/5/17.
 */

//Text model for our text applys getters and setters to the object

public class Text {


    private String mContact;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mRecieve;



    public String getmContact() {
        return mContact;
    }

    public void setmContact(String mContact) {
        this.mContact = mContact;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmRecieve() {
        return mRecieve;
    }

    public void setmRecieve(String mRecieve) {
        this.mRecieve = mRecieve;
    }
}
