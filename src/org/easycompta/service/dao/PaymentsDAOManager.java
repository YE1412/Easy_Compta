/*
s * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.dao;

import java.util.List;
import org.easycompta.object.Composed;
import org.easycompta.object.Paiement;

/**
 *
 * @author Yannick
 */
public interface PaymentsDAOManager {
    public List<Paiement> getAllPayments();
    public int insertPayment(Paiement pay);
    public int updatePayment(Paiement pay);
    public int insertOrdersInPayment(Composed comp);
    public Paiement getPaymentById(int id);
    public int deletePayment(int id);
    public int deleteComposed(int idP);
    public List<Integer> getOrdersByPaymentId(int idP);
}
