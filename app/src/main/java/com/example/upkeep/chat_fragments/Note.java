package com.example.upkeep.chat_fragments;

public class Note {


        public static final String TABLE_NAME = "user_chat";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_FBID = "fbid";
        public static final String COLUMN_EMAIL= "email";
        public static final String COLUMN_AVATAR= "image";
        public static final String COLUMN_MOBILENUM= "mobileno";
        public static final String COLUMN_GENDER= "gender";
        public static final String COLUMN_AGE= "age";

        public static final String LAST_MESSAGE = "message";
        public static final String LAST_MESAGE_TIME = "last_message_time";
        public static final String COUNT = "unread_message_count";

        private int id;
        private String note;
        private String timestamp;


        // Create table SQL query
        public static final String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_USERNAME + " TEXT,"+
                        LAST_MESSAGE + " TEXT,"+ LAST_MESAGE_TIME + " TEXT,"+COUNT+ " TEXT,"+COLUMN_EMAIL+ " TEXT"
                        + ")";

        public Note() { }

        public Note(int id, String note, String timestamp)
        {
            this.id = id;
            this.note = note;
            this.timestamp = timestamp;
        }

        public int getId() {
            return id;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

}
