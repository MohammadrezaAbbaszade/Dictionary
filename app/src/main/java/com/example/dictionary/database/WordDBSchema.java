package com.example.dictionary.database;

public class WordDBSchema {

    public static final String NAME = "word.db";

    public static final class Word {
        public static final String NAME = "Word";

        public static final class Cols {
            public static final String _ID = "_id";
            public static final String UUID = "uuid";
            public static final String ENGLISHNAME = "englishname";
            public static final String PERSIANNAME = "persianname";
            public static final String LANGUAGE = "language";
        }
    }
}
