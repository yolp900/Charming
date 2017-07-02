package com.yolp900.charming.api.crafting;

public class OreDictStack {
    private String oreDictEntry;
    private int stackSize;

    public OreDictStack(String oreDictEntry, int stackSize) {
        this.oreDictEntry = oreDictEntry;
        this.stackSize = stackSize;
    }

    public String getOreDictEntry() {
        return oreDictEntry;
    }

    public int getStackSize() {
        return stackSize;
    }

    @Override
    public String toString() {
        return "oreDictEntry[Charming] of:" +  this.getStackSize() + "x" + this.getOreDictEntry();
    }
}
