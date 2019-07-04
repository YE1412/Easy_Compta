/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;
import org.easycompta.object.ProduitService;

/**
 *
 * @author Yannick
 */
public interface ServicesDAOManager {
    public List<ProduitService> getAllServices();
    public int insertService(ProduitService service);
    public int updateService(ProduitService service);
    public ProduitService getServiceById(int id);
    public int deleteService(int id);
    public List<Integer> getOrdersId(int id);
}
