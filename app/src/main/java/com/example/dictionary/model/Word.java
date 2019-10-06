package com.example.dictionary.model;

import java.util.UUID;

public class Word {
    private UUID mUUID;
    private String mEnglishNAME;
    private String mPersianNAME;
    public Word() {
        this(UUID.randomUUID());
    }

    public Word(UUID uuid) {
        mUUID = uuid;
    }

    public String getEnglishNAME() {
        return mEnglishNAME;
    }

    public void setEnglishNAME(String englishNAME) {
        mEnglishNAME = englishNAME;
    }

    public String getPersianNAME() {
        return mPersianNAME;
    }

    public void setPersianNAME(String persianNAME) {
        mPersianNAME = persianNAME;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
