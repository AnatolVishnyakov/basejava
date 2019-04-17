package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListAbstractSection extends AbstractSection {
    private List<String> contents;

    public ListAbstractSection(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListAbstractSection that = (ListAbstractSection) o;
        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }
}
