package com.basejava.webapp.model.contact;

public class Contact {
    private ContactType _type;
    private String _data;

    public Contact(ContactType type, String data) {
        this._type = type;
        this._data = data;
    }

    public ContactType getContactType() {
        return _type;
    }

    public String getData() {
        return _data;
    }

    @Override
    public String toString() {
        return _data.isEmpty()
            ? _type.toString()
            : String.format("%s: %s", _type, _data);
    }
}
