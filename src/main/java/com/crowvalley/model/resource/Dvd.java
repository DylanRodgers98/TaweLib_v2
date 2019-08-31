package com.crowvalley.model.resource;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dvd")
public class Dvd extends Resource {

    private String director;

    private String language;

    private Integer runtime;

    private String subLang;

    /**
     * Create a new DVD.
     * @param title The title of the DVD.
     * @param year The year of the DVD.
     * @param imageUrl The image location of the DVD.
     * @param language The language of the DVD.
     * @param runtime The runtime of the DVD.
     * @param subLang The subtitles language of the DVD.
     */
    public Dvd(String title, String year, String imageUrl,
               String director, String language, Integer runtime, String subLang){
        super(title, year, imageUrl);
        this.director = director;
        this.language = language;
        this.runtime = runtime;
        this.subLang = subLang;
    }

    public Dvd() {
        super();
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
