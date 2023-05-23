package com.example.scythecompanion;

public enum Faction implements Comparable<Faction>{
    RUSVIET("Rusviet Union", R.drawable.faction_rusviet, R.drawable.rusviet_icon, R.color.rusviet_red),
    POLANIA("Republic of Polania", R.drawable.faction_polania, R.drawable.polania_icon, R.color.polania_white),
    CRIMEA("Crimean Khanate", R.drawable.faction_crimea, R.drawable.crimean_icon, R.color.crimea_yellow),
    NORDIC("Nordic Kingdoms", R.drawable.faction_nordic, R.drawable.nordic_icon, R.color.nordic_blue),
    SAXONY("Saxony Empire", R.drawable.faction_saxony, R.drawable.saxony_icon, R.color.saxony_black),
    TOGAWA("Togawa Shogunate", R.drawable.faction_togawa, R.drawable.togawa_icon, R.color.togawa_purple),
    ALBION("Clan Albion", R.drawable.faction_albion, R.drawable.albion_icon, R.color.albion_green),
    TESLA("Tesla", R.drawable.faction_tesla, R.drawable.tesla_icon, R.color.tesla_teal),
    FENRIS("Fenris", R.drawable.faction_fenris, R.drawable.fenris_icon, R.color.fenris_orange),
    NONE("None", R.drawable.faction_front, R.drawable.faction_front_icon, R.color.colorPrimaryDark);

    public final String NAME;
    public final int IMAGE;
    public final int ICON;
    public final int COLOR;

    Faction(String name, int image_id, int icon_id, int color_id) {
        this.NAME = name;
        IMAGE = image_id;
        ICON = icon_id;
        COLOR = color_id;
    }


}

