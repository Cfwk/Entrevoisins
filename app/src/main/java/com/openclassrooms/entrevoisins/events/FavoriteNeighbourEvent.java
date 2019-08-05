package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user add a Neighbour to favorite
 */
public class FavoriteNeighbourEvent {

    /**
     * Neighbour to favorite
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public FavoriteNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
        if (neighbour.getFavorite()!=true)
            neighbour.setFavorite(true);
        else neighbour.setFavorite(false);
    }
}


