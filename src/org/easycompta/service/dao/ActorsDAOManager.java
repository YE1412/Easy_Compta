/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;
import org.easycompta.object.Personne;

/**
 *
 * @author Yannick
 */
public interface ActorsDAOManager {
    public List<Personne> getAllActors();
    public int updateActor(Personne actor);
    public List<Personne> getAllSellers();
    public List<Personne> getAllBuyers();
    public int insertActor(Personne actor);
    public Personne getActorById(int id);
    public int deleteActor(int id);
}
