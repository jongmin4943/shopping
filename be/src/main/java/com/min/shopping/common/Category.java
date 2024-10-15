package com.min.shopping.common;

public enum Category {
    TOP, OUTER, PANTS, SNEAKERS, BAG, HAT, SOCKS, ACCESSORY;

    public static int countTypes() {
        return values().length;
    }
}
