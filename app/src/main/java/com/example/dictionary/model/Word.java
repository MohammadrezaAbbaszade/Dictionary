package com.example.dictionary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Word {
    @Id(autoincrement = true)
    private Long mUUID;
    private String mEnglishNAME;
    private String mPersianNAME;
    @Generated(hash = 1672887879)
    public Word(Long mUUID, String mEnglishNAME, String mPersianNAME) {
        this.mUUID = mUUID;
        this.mEnglishNAME = mEnglishNAME;
        this.mPersianNAME = mPersianNAME;
    }
    @Generated(hash = 3342184)
    public Word() {
    }
    public Long getMUUID() {
        return this.mUUID;
    }
    public void setMUUID(Long mUUID) {
        this.mUUID = mUUID;
    }
    public String getMEnglishNAME() {
        return this.mEnglishNAME;
    }
    public void setMEnglishNAME(String mEnglishNAME) {
        this.mEnglishNAME = mEnglishNAME;
    }
    public String getMPersianNAME() {
        return this.mPersianNAME;
    }
    public void setMPersianNAME(String mPersianNAME) {
        this.mPersianNAME = mPersianNAME;
    }

}
