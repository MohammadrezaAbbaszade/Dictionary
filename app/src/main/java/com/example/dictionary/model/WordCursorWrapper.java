package com.example.dictionary.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.dictionary.database.WordDBSchema;

import java.util.UUID;

public class WordCursorWrapper extends CursorWrapper {
    public WordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Word getWord() {
        String strUUID = getString(getColumnIndex(WordDBSchema.Word.Cols.UUID));
        String englishLanguage = getString(getColumnIndex(WordDBSchema.Word.Cols.ENGLISHNAME));
        String persianLanguage = getString(getColumnIndex(WordDBSchema.Word.Cols.PERSIANNAME));

        Word word = new Word(UUID.fromString(strUUID));
        word.setEnglishNAME(englishLanguage);
        word.setPersianNAME(persianLanguage);

        return word;

    }

}
