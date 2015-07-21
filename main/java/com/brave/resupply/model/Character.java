package com.brave.chardb.model;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;

public class Character extends BaseDoc {
    private String group;
    private TimePeriod timePeriod;
    private Genre genre;
    
    public String getGroup() {
    	return group;
    }
    
    public void setGroup(String group) {
    	this.group = group;
    }
    
    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}