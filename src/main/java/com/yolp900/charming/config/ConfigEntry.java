package com.yolp900.charming.config;

import com.yolp900.charming.reference.LibConfig;

public class ConfigEntry {
    private LibConfig.Entry entry;
    private EnumCategories category;

    public ConfigEntry(LibConfig.Entry entry, EnumCategories category) {
        this.entry = entry;
        this.category = category;
    }

    public String getTitle() {
        return entry.getTitle();
    }

    public String getDescription() {
        return entry.getDescription();
    }

    public EnumCategories getCategory() {
        return category;
    }

    public enum EnumCategories {
        GENERAL("General"), MECHANICS("Mechanics"), INTEGRATION("Integration");

        private String title;

        EnumCategories(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class BooleanEntry extends ConfigEntry {
        private boolean defaultValue;
        private boolean value;

        public BooleanEntry(LibConfig.Entry entry, EnumCategories category, boolean defaultValue) {
            super(entry, category);
            this.defaultValue = defaultValue;
        }

        public boolean getDefaultValue() {
            return defaultValue;
        }

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }

    public static class FloatEntry extends ConfigEntry {
        private float defaultValue;
        private float minValue;
        private float maxValue;
        private float value;

        public FloatEntry(LibConfig.Entry entry, EnumCategories category, float defaultValue, float minValue, float maxValue) {
            super(entry, category);
            this.defaultValue = defaultValue;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public float getDefaultValue() {
            return defaultValue;
        }

        public float getMinValue() {
            return minValue;
        }

        public float getMaxValue() {
            return maxValue;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

    public static class IntegerEntry extends ConfigEntry {
        private int defaultValue;
        private int minValue;
        private int maxValue;
        private int value;

        public IntegerEntry(LibConfig.Entry entry, EnumCategories category, int defaultValue, int minValue, int maxValue) {
            super(entry, category);
            this.defaultValue = defaultValue;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }

        public int getMinValue() {
            return minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class StringEntry extends ConfigEntry {
        private String defaultValue;
        private String[] validValues;
        private String value;

        public StringEntry(LibConfig.Entry entry, EnumCategories category, String defaultValue, String[] validValues) {
            super(entry, category);
            this.defaultValue = defaultValue;
            this.validValues = validValues;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public String[] getValidValues() {
            return validValues;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
