/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.object;

import java.util.List;

import lombok.ToString;

/**
 *
 * @author Yannick
 */
@ToString( of = {"idCommande"})
public class Contains implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idCommande;
    private List<Integer> comporte;

    public Contains() {
    }

    public Contains(int idCommande) {
        this.idCommande = idCommande;
    }

    public Contains(int idCommande, List<Integer> comporte) {
        this.idCommande = idCommande;
        this.comporte = comporte;
    }

    /**
     * @return the idCommande
     */
    public int getIdCommande() {
        return idCommande;
    }

    /**
     * @param idCommande the idCommande to set
     */
    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    /**
     * @return the comporte
     */
    public List<Integer> getComporte() {
        return comporte;
    }

    /**
     * @param comporte the comporte to set
     */
    public void setComporte(List<Integer> comporte) {
        this.comporte = comporte;
    }
    
    
}
