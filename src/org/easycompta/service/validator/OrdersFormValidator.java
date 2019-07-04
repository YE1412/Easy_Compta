/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.Commande;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class OrdersFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Commande.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Commande order = (Commande) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idTva", "NotEmpty.ordersForm.idTva");
        if(order.getIdTva() == -1){
            errors.rejectValue("idTva", "NotNull.ordersForm.idTva");
        }
    }
    
}
