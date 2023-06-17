package com.example.mvt_tracker.service.enums;

public enum Games {
    BASKETBALL("BASKETBALL"),
    HANDBALL("HANDBALL");

    private final String str;

    Games(String str) {
        this.str = str;
    }

    public boolean equals(String str) {
        return this.str.equals(str);
    }

    @Override
    public String toString() {
        return str;
    }
}
