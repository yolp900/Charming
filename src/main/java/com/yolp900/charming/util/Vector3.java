package com.yolp900.charming.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class Vector3 {

    private final double x;
    private final double y;
    private final double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Vector3 fromBlockPos(BlockPos pos) {
        return new Vector3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector3 fromEntity(Entity entity) {
        return new Vector3(entity.posX, entity.posY, entity.posZ);
    }

    public double magnitude() {
        return Math.sqrt(selfDot());
    }

    public double selfDot() {
        return x * x + y * y + z * z;
    }

    public Vector3 add(double d) {
        return add(d, d, d);
    }

    public Vector3 add(double fx, double fy, double fz) {
        return new Vector3(x + fx, y + fy, z + fz);
    }

    public Vector3 subtract(Vector3 vec) {
        return add(-vec.getX(), -vec.getY(), -vec.getZ());
    }

    public Vector3 multiply(double d) {
        return multiply(d, d, d);
    }

    public Vector3 multiply(double fx, double fy, double fz) {
        return new Vector3(x * fx, y * fy, z * fz);
    }

    public Vector3 normalize() {
        double d = magnitude();
        if (d != 0) return multiply(1 / d);

        return this;
    }

}
