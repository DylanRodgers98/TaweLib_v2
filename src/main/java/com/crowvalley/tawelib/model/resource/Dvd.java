package com.crowvalley.tawelib.model.resource;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Dvd class for creating objects to store information about DVDs
 * to be persisted in a database.
 *
 * @author Dylan Rodgers
 */
@Entity
@Table
public class Dvd extends Resource {

    private String director;

    private String language;

    private Integer runtime;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "subtitle_language")
    private Set<String> subtitleLanguages;

    public Dvd(String title, String year, String imageUrl, String director,
               String language, Integer runtime, Set<String> subtitleLanguages) {
        super(ResourceType.DVD, title, year, imageUrl);
        this.director = director;
        this.language = language;
        this.runtime = runtime;
        this.subtitleLanguages = subtitleLanguages;
    }

    public Dvd() {
        super(ResourceType.DVD, null, null, null);
    }

    @Override
    public String toString() {
        return title + " (" + year + ")";
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

    public Set<String> getSubtitleLanguages() {
        return subtitleLanguages;
    }

    public String getSubtitleLanguageString() {
        if (subtitleLanguages == null) {
            return StringUtils.EMPTY;
        }
        List<String> subtitleLanguagesList = new ArrayList<>(subtitleLanguages);
        Collections.sort(subtitleLanguagesList);
        return String.join(", ", subtitleLanguagesList);
    }

    public void setSubtitleLanguages(Set<String> subtitleLanguages) {
        this.subtitleLanguages = subtitleLanguages;
    }

    public void addSubtitleLanguage(String subtitleLanguage) {
        if (subtitleLanguages == null) {
            subtitleLanguages = new HashSet<>();
        }
        subtitleLanguages.add(subtitleLanguage);
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
