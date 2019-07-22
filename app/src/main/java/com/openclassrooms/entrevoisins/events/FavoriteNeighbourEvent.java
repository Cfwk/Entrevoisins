package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Created by Skiti on 15/07/2019
 */
/**
 * Event fired when a user add a Neighbour to favorite
 */

class FavoriteNeighbourEvent {

    /**
     * Neighbour to favorite
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public void FavoriteNeighbourEvent(Neighbour neighbour) {
        if (neighbour.getFavorite()!=true)
            neighbour.setFavorite(true);
        else neighbour.setFavorite(false);
    }


}


