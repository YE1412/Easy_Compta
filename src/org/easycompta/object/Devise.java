package org.easycompta.object;
// Generated 18 avr. 2018 22:21:51 by Hibernate Tools 4.3.1

import lombok.ToString;

/**
 * Devise generated by hbm2java
 */
@ToString(of = {"symbole"})
public class Devise  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
     private char symbole;

    public Devise() {
    }

    public Devise(char symbole) {
       this.symbole = symbole;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public char getSymbole() {
        return this.symbole;
    }
    
    public void setSymbole(char symbole) {
        this.symbole = symbole;
    }




}


