package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class AndroidDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.peach.masktime.db");

        addNote(schema);
        addRecord(schema);
        new DaoGenerator().generateAll(schema, "app/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addRecord(Schema schema) {
        Entity note = schema.addEntity("Record");
        note.addIdProperty().autoincrement();
        note.addStringProperty("title").notNull();
        note.addStringProperty("content");
        note.addStringProperty("path01");
        note.addStringProperty("path02");
        note.addStringProperty("path03");
        note.addDateProperty("date");
    }
}
