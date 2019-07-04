/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;

import org.easycompta.object.Commande;
import org.easycompta.object.Concerner;
import org.easycompta.object.Facture;

/**
 *
 * @author Yannick
 */
public interface InvoicesDAOManager {
    public List<Facture> getAllInvoices();
    public int insertInvoice(Facture fac);
    public int updateInvoice(Facture fac);
    public Facture getInvoiceById(int id);
    public int deleteInvoice(int id);
    public Commande getOrderByInvoiceId(int idFac);
    public int getSellerByInvoiceId(int idFac) throws Exception;
    public int getBuyerByInvoiceId(int idFac) throws Exception;
    public int insertActors(Concerner conc);
}
