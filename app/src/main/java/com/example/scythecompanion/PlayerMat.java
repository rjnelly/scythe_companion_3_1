package com.example.scythecompanion;

public enum PlayerMat implements Comparable<PlayerMat>{
    INDUSTRIAL("Industrial", R.drawable.playermat_industrial, R.drawable.icon_industrial),
    ENGINEERING("Engineering", R.drawable.playermat_engineering, R.drawable.icon_engineering),
    MILITANT("Militant", R.drawable.playermat_militant, R.drawable.icon_militant),
    PATRIOTIC("Patriotic", R.drawable.playermat_patriotic, R.drawable.icon_patriotic),
    INNOVATIVE("Innovative", R.drawable.playermat_innovative, R.drawable.icon_innovative),
    MECHANICAL("Mechanical", R.drawable.playermat_mechanical, R.drawable.icon_mechanical),
    AGRICULTURAL("Agricultural", R.drawable.playermat_agricultural, R.drawable.icon_agricultural),
    NONE("None", R.drawable.playermat_front, R.drawable.playermat_front_icon);

    public final String NAME;
    public final int IMAGE;
    public final int ICON;



    PlayerMat(String name, int image, int icon) {
        NAME = name;
        IMAGE = image;
        ICON = icon;
    }
}
