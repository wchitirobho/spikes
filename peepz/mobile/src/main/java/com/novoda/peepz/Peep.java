package com.novoda.peepz;

import android.graphics.Bitmap;

class Peep {

    private final String id;
    private final OptionalString name;
    private final Bitmap image;
    private final OptionalTimestamp timestamp;

    Peep(String id, OptionalString name, Bitmap image, OptionalTimestamp timestamp) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.timestamp = timestamp;
    }

    public String id() {
        return id;
    }

    public OptionalString name() {
        return name;
    }

    public Bitmap image() {
        return image;
    }

    public OptionalTimestamp timestamp() {
        return timestamp;
    }

}
