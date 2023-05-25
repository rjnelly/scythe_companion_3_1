package com.example.scythecompanion;

public interface ItemInteractionListener {
    void playerNameSelected(int playerPosition);
    void factionSelected(int playerPosition);
    void playerMatSelected(int playerPosition);

    void factionDeleted(int adapterPosition);
}
