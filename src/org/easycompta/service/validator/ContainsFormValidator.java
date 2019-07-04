/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.object.Contains;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class ContainsFormValidator implements Validator{
    
    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return Contains.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Contains cont = (Contains) o;
        ValidationUtils.rejectIfEmpty(errors, "comporte", "NotEmpty.containsForm.comporte");
        cont.getComporte().forEach((item) -> {
            if(item == 0)
            {
                errors.rejectValue("comporte", "NotNull.containsForm.comporte");
            }
        });
    }
    
}
