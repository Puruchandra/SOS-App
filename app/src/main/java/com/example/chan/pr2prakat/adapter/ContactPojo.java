package com.example.chan.pr2prakat.adapter;

public class ContactPojo {
    private String contactNamePojo;
    private int contactNoPojo;
    private int CID;
    int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }


    public String getContactNamePojo() {
        return contactNamePojo;
    }

    public void setContactNamePojo(String contactNamePojo) {
        this.contactNamePojo = contactNamePojo;
    }

    public int getContactNoPojo() {
        return contactNoPojo;
    }

    public void setContactNoPojo(int contactNoPojo) {
        this.contactNoPojo = contactNoPojo;
    }

    @Override
    public String toString() {
        return "ContactPojo{" +
                "contactNamePojo='" + contactNamePojo + '\'' +
                ", contactNoPojo=" + contactNoPojo +
                '}';
    }
}
