package com.basejava.webapp.model;

import java.util.Objects;

public class HyperLink {
    private final String name;
    private final String url;

    public HyperLink(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HyperLink hyperLink = (HyperLink) o;
        return name.equals(hyperLink.name) &&
                Objects.equals(url, hyperLink.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "HyperLink{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
