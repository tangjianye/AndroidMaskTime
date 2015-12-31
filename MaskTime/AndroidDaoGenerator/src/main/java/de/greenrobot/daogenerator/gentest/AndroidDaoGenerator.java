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
        Entity entity = schema.addEntity("Note");
        entity.addIdProperty();
        entity.addStringProperty("text").notNull();
        entity.addStringProperty("comment");
        entity.addDateProperty("date");
    }

    private static void addRecord(Schema schema) {
        Entity entity = schema.addEntity("Record");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("title").notNull();
        entity.addStringProperty("content");
        entity.addStringProperty("path01");
        entity.addStringProperty("path02");
        entity.addStringProperty("path03");
        entity.addLongProperty("date").notNull();
    }
}
