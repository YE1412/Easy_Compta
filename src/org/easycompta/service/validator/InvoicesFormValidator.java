/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.Facture;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class InvoicesFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return Facture.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Facture fac=(Facture) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idLangue", "NotEmpty.invoicesForm.idLangue");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idCommande", "NotEmpty.invoicesForm.idCommande");
        
        if (fac.getIdCommande() == 0){
            errors.rejectValue("idCommande", "Incorrect.invoicesForm.idCommande");
        }
        if (fac.getIdLangue() == 0){
            errors.rejectValue("idLangue", "Incorrect.invoicesForm.idLangue");
        } 
    }
    
}
