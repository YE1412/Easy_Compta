/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.ProduitService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class ServicesFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return ProduitService.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ProduitService service = (ProduitService)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "NotEmpty.servicesForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "montantHt", "NotEmpty.servicesForm.price");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantite", "NotEmpty.servicesForm.quantity");
        if (service.getMontantHt() <= 0.0) {
            errors.rejectValue("montantHt", "ZeroValue.servicesForm.price");
        }
        if (service.getQuantite() <= 0) {
            errors.rejectValue("quantite", "ZeroValue.servicesForm.quantity");
        }
    }
    
}
