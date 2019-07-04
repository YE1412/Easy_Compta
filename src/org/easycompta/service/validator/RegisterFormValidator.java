/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.service.validator;

import org.easycompta.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author Yannick
 */
public class RegisterFormValidator implements Validator{

/*    @Autowired
    @Qualifier("emailValidator")
    EmailValidator emailValidator;*/
    
    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return User.class.equals(type);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty.registerForm.firstname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty.registerForm.lastname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty.registerForm.login");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "NotEmpty.registerForm.mail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail_confirm", "NotEmpty.registerForm.mail_confirm");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registerForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password_confirm", "NotEmpty.registerForm.password_confirm");
        
        if (!user.getPassword().equals(user.getPassword_confirm())) {
            errors.rejectValue("password_confirm", "Diff.registerForm.password_confirm");
        }
        if (!user.getMail().equals(user.getMail_confirm())) {
            errors.rejectValue("mail_confirm", "Diff.registerForm.mail_confirm");
        }
    }
    
}
