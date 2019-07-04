/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;
import org.easycompta.object.Tva;

/**
 *
 * @author Yannick
 */
public interface TvaDAOManager {
    List<Tva> getAllTva();
    Tva getTvaById(int id);
}
