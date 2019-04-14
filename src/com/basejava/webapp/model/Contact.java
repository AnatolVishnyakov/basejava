package com.basejava.webapp.model;

public class Contact {
    private String contact;

    public Contact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return contact;
    }
}
