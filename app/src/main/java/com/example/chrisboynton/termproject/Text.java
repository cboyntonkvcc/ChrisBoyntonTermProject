package com.example.chrisboynton.termproject;

import java.util.UUID;

/**
 * Created by chrisboynton on 11/5/17.
 */

public class Text {

    private UUID mId;
    private String mTitle;
    private String mContact;
    private String mPhone;
    private String mEmail;
    private String mAddress;
    private String mRecieve;



    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

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
