package com.basejava.webapp.model;

import java.util.*;

public class InstitutionSection extends AbstractSection {
    private final Map<String, List<Institution>> institutions = new HashMap<>();

    public InstitutionSection(Institution... institutions) {
        this(Arrays.asList(institutions));
    }

    public InstitutionSection(List<Institution> institutions) {
        Objects.requireNonNull(institutions, "institutions must not be null");
        institutions.forEach(value -> {
            String title = value.getTitle();
            List<Institution> institution = this.institutions
                    .computeIfAbsent(title, k -> new ArrayList<>());
            institution.add(value);
            this.institutions.put(title, institution);
        });
    }

    public Map<String, List<Institution>> getInstitutions() {
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
