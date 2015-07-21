package com.brave.chardb.enums;

/**
 * Created by dcohen on 2/13/15.
 */
public enum Genre {
    FANTASY("Fantasy"),
    REALISTIC("Realistic"),
    SCIFI("Science Fiction"),
    ANIME("Anime");

    private final String text;

    /**
     * @param text
     */
    private Genre(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
