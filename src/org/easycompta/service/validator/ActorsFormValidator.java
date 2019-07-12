/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.Personne;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class ActorsFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Personne.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Personne actor = (Personne) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numCommercant", "NotEmpty.actorsForm.numCommercant");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "NotEmpty.actorsForm.nom");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenom", "NotEmpty.actorsForm.prenom");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.actorsForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tel", "NotEmpty.actorsForm.tel");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numRue", "NotEmpty.actorsForm.numRue");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomRue", "NotEmpty.actorsForm.nomRue");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cp", "NotEmpty.actorsForm.cp");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ville", "NotEmpty.actorsForm.ville");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty.actorsForm.type");
        if (actor.getTel().length() != 10) {
            errors.rejectValue("tel", "Correct.actorsForm.tel");
        }
        if (actor.getNumRue().length() > 10) {
        	errors.rejectValue("numRue", "Correct.actorsForm.numRue");
        }
        if (actor.getCp().length() > 5) {
        	errors.rejectValue("cp", "Correct.actorsForm.cp");
        }
        if (actor.getType() == -1) {
            errors.rejectValue("type", "NotEmpty.actorsForm.type");
        }
    }
    
}
