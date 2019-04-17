package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class InstitutionAbstractSection extends AbstractSection {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;

    public InstitutionAbstractSection(String title, LocalDate startDate, LocalDate endDate, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionAbstractSection that = (InstitutionAbstractSection) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate, endDate, description);
    }

    @Override
    public String toString() {
        return "InstitutionAbstractSection{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
