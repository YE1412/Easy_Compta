/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.Concerner;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class ConcernedFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return Concerner.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Concerner c= (Concerner) o;
        ValidationUtils.rejectIfEmpty(errors, "idClient", "NotEmpty.actors_in_invoiceForm.idClient");
        ValidationUtils.rejectIfEmpty(errors, "idVendeur", "NotEmpty.actors_in_invoiceForm.idVendeur");
        if (c.getIdVendeur() == 0)
        {
            errors.rejectValue("idVendeur", "Incorrect.actors_in_invoiceForm.idVendeur");
        }
        if (c.getIdClient() == 0)
        {
            errors.rejectValue("idClient", "Incorrect.actors_in_invoiceForm.idClient");
        }
        if (c.getIdClient() == c.getIdVendeur())
        {
            errors.rejectValue("idClient", "Diff.actors_in_invoiceForm.idClient");
        }
    }
    
}
