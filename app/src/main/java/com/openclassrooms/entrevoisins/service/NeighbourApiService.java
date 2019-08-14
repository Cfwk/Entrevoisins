package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
    *  Add Neighbours to favorite
    * @param neighbour
    */
    void favoriteNeighbour (Neighbour neighbour);

    /**
     *  Return the boolean favorite of the neighbour
     * @param  neighbour
     */

    boolean isFavorite (Neighbour neighbour);

    /**
     * Get only favorite Neighbours
     * @return {@link List}
     */
    List<Neighbour> getFavoriteNeighbours();

}
