package com.yolp900.charming.reference;

public class LibConfig {

    /**
     * The Config Entries in Charming.
     */

    public static final Entry CRAFTABLE_CONSTRUCTION_TABLE = new Entry("Craftable Construction Table", "Can the construction table be crafted in a crafting table? (as well as being crafted in-world)");
    public static final Entry LEVITATOR_ELEVATOR_RANGE = new Entry("Levitator's and Elevator's Range", "How high can the levitator levitate and the elevator elevate?");

    public static class Entry {
        private String title;
        private String description;

        Entry(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

}
