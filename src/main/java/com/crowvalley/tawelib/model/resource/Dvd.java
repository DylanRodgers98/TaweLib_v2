package com.crowvalley.tawelib.model.resource;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Dvd class for creating objects to store information about DVDs
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table(name = "dvd")
public class Dvd extends Resource {

    private String director;

    private String language;

    private Integer runtime;

    private String subLang;

    public Dvd(String title, String year, String imageUrl,
               String director, String language, Integer runtime, String subLang) {
        super(ResourceType.DVD, title, year, imageUrl);
        this.director = director;
        this.language = language;
        this.runtime = runtime;
        this.subLang = subLang;
    }

    public Dvd() {
        super();
    }

    @Override
    public String toString() {
        return new StringBuilder(getTitle())
                .append(" (")
                .append(getYear())
                .append(")")
                .toString();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getSubLang() {
        return subLang;
    }

    public void setSubLang(String subLang) {
        this.subLang = subLang;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
