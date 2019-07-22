package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    @Override
    public void favoriteNeighbour(Neighbour neighbour) {
        if (neighbour.getFavorite()!=true)
            neighbour.setFavorite(true);
        else neighbour.setFavorite(false);
    }

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        int i=0;
        while (i<=neighbours.size()-1) {
            if (neighbours.get(i).getFavorite() != true && i <= neighbours.size()-1)
            neighbours.remove(i);
            else i++; }
        return neighbours;
    }
}

