package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class InstitutionSection extends Section {
    private String _title;
    private LocalDate _startDate;
    private LocalDate _endDate;
    private String _description;

    public InstitutionSection(String title, LocalDate startDate, LocalDate endDate, String description) {
        this._title = title;
        this._startDate = startDate;
        this._endDate = endDate;
        this._description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstitutionSection that = (InstitutionSection) o;
        return Objects.equals(_title, that._title) &&
                Objects.equals(_startDate, that._startDate) &&
                Objects.equals(_endDate, that._endDate) &&
                Objects.equals(_description, that._description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_title, _startDate, _endDate, _description);
    }

    @Override
    public String toString() {
        return "InstitutionSection{" +
                "_title='" + _title + '\'' +
                ", _startDate=" + _startDate +
                ", _endDate=" + _endDate +
                ", _description='" + _description + '\'' +
                '}';
    }
}
