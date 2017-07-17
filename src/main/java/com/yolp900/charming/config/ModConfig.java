package com.yolp900.charming.config;

import com.yolp900.charming.reference.LibConfig;
import com.yolp900.charming.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ModConfig {

    public static Configuration configuration;

    public static ConfigEntry.BooleanEntry CRAFTABLE_CONSTRUCTION_TABLE = new ConfigEntry.BooleanEntry(LibConfig.CRAFTABLE_CONSTRUCTION_TABLE, ConfigEntry.EnumCategories.Mechanics, false);
    public static ConfigEntry.FloatEntry LEVITATOR_RANGE = new ConfigEntry.FloatEntry(LibConfig.LEVITATOR_RANGE, ConfigEntry.EnumCategories.Movement, 8, 2, 16);
    public static ConfigEntry.IntegerEntry ELEVATOR_RANGE = new ConfigEntry.IntegerEntry(LibConfig.ELEVATOR_RANGE, ConfigEntry.EnumCategories.Movement, 8, 2, 16);
    public static ConfigEntry.FloatEntry ATTRACTOR_RANGE = new ConfigEntry.FloatEntry(LibConfig.ATTRACTOR_RANGE, ConfigEntry.EnumCategories.Movement, 4, 1, 6);
    public static ConfigEntry.FloatEntry IMPELLER_RANGE = new ConfigEntry.FloatEntry(LibConfig.IMPELLER_RANGE, ConfigEntry.EnumCategories.Movement, 4, 1, 6);

    public static void init(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
        }

        configuration.load();
        loadConfiguration();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private static void loadConfiguration() {
        loadConfiguration(CRAFTABLE_CONSTRUCTION_TABLE);
        loadConfiguration(LEVITATOR_RANGE);
        loadConfiguration(ELEVATOR_RANGE);
        loadConfiguration(ATTRACTOR_RANGE);
        loadConfiguration(IMPELLER_RANGE);
    }

    private static void loadConfiguration(ConfigEntry entry) {
        if (entry instanceof ConfigEntry.BooleanEntry) {
            ConfigEntry.BooleanEntry booleanEntry = (ConfigEntry.BooleanEntry) entry;
            booleanEntry.setValue(configuration.getBoolean(booleanEntry.getTitle(), booleanEntry.getCategory().getTitle(), booleanEntry.getDefaultValue(), booleanEntry.getDescription()));
        } else if (entry instanceof ConfigEntry.FloatEntry) {
            ConfigEntry.FloatEntry floatEntry = (ConfigEntry.FloatEntry) entry;
            floatEntry.setValue(configuration.getFloat(floatEntry.getTitle(), floatEntry.getCategory().getTitle(), floatEntry.getDefaultValue(), floatEntry.getMinValue(), floatEntry.getMaxValue(), floatEntry.getDescription()));
        } else if (entry instanceof ConfigEntry.IntegerEntry) {
            ConfigEntry.IntegerEntry floatEntry = (ConfigEntry.IntegerEntry) entry;
            floatEntry.setValue(configuration.getInt(floatEntry.getTitle(), floatEntry.getCategory().getTitle(), floatEntry.getDefaultValue(), floatEntry.getMinValue(), floatEntry.getMaxValue(), floatEntry.getDescription()));
        } else if (entry instanceof ConfigEntry.StringEntry) {
            ConfigEntry.StringEntry stringEntry = (ConfigEntry.StringEntry) entry;
            stringEntry.setValue(configuration.getString(stringEntry.getTitle(), stringEntry.getCategory().getTitle(), stringEntry.getDefaultValue(), stringEntry.getDescription(), stringEntry.getValidValues()));
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }

}
