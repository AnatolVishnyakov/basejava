package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class InstitutionSection extends AbstractSection {
    private final List<Institution> institutions;

    public InstitutionSection(List<Institution> institutions) {
        Objects.requireNonNull(institutions, "institutions must not be null");
        this.institutions = institutions;
    }

    public List<Institution> getInstitutions() {
        return institutions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection that = (InstitutionSection) o;
        return institutions.equals(that.institutions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(institutions);
    }

    @Override
    public String toString() {
        return institutions.toString();
    }
}
