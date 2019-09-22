package com.example.dictionary.model;

import java.util.UUID;

public class Word {
    private UUID mUUID;
    private String mNAME;
    private String mDiscription;
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

    public String getDiscription() {
        return mDiscription;
    }

    public void setDiscription(String discription) {
        mDiscription = discription;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
