package com.basejava.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Institution {
    private final HyperLink homePage;
    private final String title;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;

    public Institution(HyperLink homePage, String title, LocalDate startDate, LocalDate endDate, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = homePage;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public HyperLink getHomePage() {
        return homePage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Institution that = (Institution) o;
        return homePage.equals(that.homePage) &&
                title.equals(that.title) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, title, startDate, endDate, description);
    }

    @Override
    public String toString() {
        return "Institution{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                '}';
    }
}
