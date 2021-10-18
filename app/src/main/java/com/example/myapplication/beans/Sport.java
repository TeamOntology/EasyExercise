package com.example.myapplication.beans;

public class Sport {
    public enum SportType {
        INDOOR("Indoor"), OUTDOOR("Outdoor"), INDOOR_OUTDOOR("Indoor/Outdoor");

        private final String name;

        SportType(String name) {
            this.name = name;
        }

        public static SportType getType(String typeString) {
            for (SportType type : SportType.values()) {
                if (type.toString().equals(typeString)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final int id;
    private final String name;
    private final String alternativeName;
//    private final SportType type;
    private final String type;

    // TODO A change for database
//    public Sport(int id, String name, String alternativeName, SportType type) {
//        this.id = id;
//        this.name = name;
//        this.alternativeName = alternativeName;
//        this.type = type;
//    }

    public Sport(int id, String name, String alternativeName, String type) {
        this.id = id;
        this.name = name;
        this.alternativeName = alternativeName;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

//    public SportType getType() {
//        return type;
//    }

    public String getType() {
        return type;
    }
}
