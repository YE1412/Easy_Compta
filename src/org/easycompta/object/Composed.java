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
@ToString(of = {"idPayment"})
public class Composed implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPayment;
    private List<Integer> compose;

    public Composed() {
    }

    public Composed(int idPayment) {
        this.idPayment = idPayment;
    }
    
    public Composed(int idPayment, List<Integer> compose) {
        this.idPayment = idPayment;
        this.compose = compose;
    }

    /**
     * @return the idPayment
     */
    public int getIdPayment() {
        return idPayment;
    }

    /**
     * @param idPayment the idPayment to set
     */
    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    /**
     * @return the compose
     */
    public List<Integer> getCompose() {
        return compose;
    }

    /**
     * @param compose the compose to set
     */
    public void setCompose(List<Integer> compose) {
        this.compose = compose;
    }
}
