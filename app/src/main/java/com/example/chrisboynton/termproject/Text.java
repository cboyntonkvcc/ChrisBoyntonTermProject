package com.example.chrisboynton.termproject;

import java.util.UUID;

/**
 * Created by chrisboynton on 12/5/17.
 */

public class Text {

    private UUID mId;
    private String mTitle;
    private String mContact;
    private String mPhone;


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
}
