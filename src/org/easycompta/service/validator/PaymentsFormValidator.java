/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.Paiement;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class PaymentsFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return Paiement.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Paiement p=(Paiement) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etat", "NotEmpty.paymentsForm.etat");
        if (p.getReste() == 0 && !p.getEtat()){
            errors.rejectValue("etat", "Incorrect.paymentsForm.etat");
        }
        if (p.getReste() != 0 && p.getEtat()){
            errors.rejectValue("etat", "IncorrectNotNull.paymentsForm.etat");
        }
    }
    
}
