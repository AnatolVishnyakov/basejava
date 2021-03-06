package com.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

/**
 * Секции "Позиция" и "Личные качества"
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends AbstractSection {
    public static final TextSection EMPTY = new TextSection("");
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection() {
    }

    public TextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
