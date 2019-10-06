package com.example.dictionary.model;

import java.util.UUID;

public class Word {
    private UUID mUUID;
    private String mNAME;
    private String mLanguage;
    public Word() {
        this(UUID.randomUUID());
    }

    public Word(UUID uuid) {
        mUUID = uuid;
    }

    public String getNAME() {
        return mNAME;
    }

    public void setNAME(String NAME) {
        mNAME = NAME;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
