/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;
import org.easycompta.object.Commande;
import org.easycompta.object.Contains;
import org.easycompta.object.Tva;

/**
 *
 * @author Yannick
 */
public interface OrdersDAOManager {
    public List<Commande> getAllOrders();
    public int insertOrder(Commande order);
    public int updateOrder(Commande order);
    public int insertServicesInOrder(Contains cont);
    public Commande getOrderById(int id);
    public int deleteOrder(int id);
    public Commande getOrderByInvoiceId(int idInvoice);
    public List<Integer> getContains(int id);
    public List<Tva> getAllTva();
    public int deleteContains(int idCommande);
    public Tva getTvaById(int idTva);
}
