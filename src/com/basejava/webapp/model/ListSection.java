package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private List<String> _contents;

    public ListSection(List<String> contents) {
        this._contents = contents;
    }

    public List<String> getContents() {
        return _contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(_contents, that._contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_contents);
    }
}
