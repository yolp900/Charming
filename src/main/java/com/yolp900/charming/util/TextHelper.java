package com.yolp900.charming.util;


import net.minecraft.util.text.translation.I18n;

public class TextHelper {

    public static String getFormattedText(String text, Object... format) {
        return I18n.translateToLocalFormatted(text, format);
    }

}
