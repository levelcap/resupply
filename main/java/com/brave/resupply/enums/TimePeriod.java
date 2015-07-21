package com.brave.chardb.enums;

/**
 * Created by dcohen on 2/13/15.
 */
public enum TimePeriod {
    MEDIEVAL("Medieval"),
    VICTORIAN("Victorian"),
    MODERN("Modern"),
    FUTURE("Future");

    private final String text;

    /**
     * @param text
     */
    private TimePeriod(final String text) {
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
