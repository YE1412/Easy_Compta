/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easycompta.domain.User;
import org.easycompta.domain.service.UserManager;
import org.easycompta.service.validator.RegisterFormValidator;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Yannick
 */

@RequestMapping(value = "/register")
public class RegisterController {
    private UserManager userManager;
    protected final Log logger = LogFactory.getLog(getClass());
    RegisterFormValidator registerFormValidator;
    private ResourceBundle validate;
    public RegisterController() {
    	validate = ResourceBundle.getBundle("org/validate", Locale.getDefault());
    }
//    DeviseDAOManager deviseManager;

//    public void setDeviseManager(DeviseDAOManager deviseManager) {
//        this.deviseManager = deviseManager;
//    }
    
    public void setRegisterFormValidator(RegisterFormValidator registerFormValidator) {
        this.registerFormValidator = registerFormValidator;
    }
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(registerFormValidator);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Map<String, Object> model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String now = (new Date()).toString();
        model.put("now", now);
        model.put("users", this.userManager.getUsers()); 
        model.put("userForm", new User());
        //ModelAndView mv = new ModelAndView("register", "Model", model);
        //logger.info("From " +hsr.getServletPath()+ " Returning login view with " + now);
        return "register";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleRequestForAdd(@ModelAttribute("userForm") @Validated User user,
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes
            ) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String now = (new Date()).toString();
        if (result.hasErrors()) {
            model.addAttribute("now", now);
            model.addAttribute("users", this.userManager.getUsers()); 
            model.addAttribute("userForm", user);
            return "register";
        }
        //ModelAndView mv = new ModelAndView("register", "Model", model);
        //logger.info("From " +hsr.getServletPath()+ " Returning login view with " + now);
        redirectAttributes.addFlashAttribute("msg", validate.getString("Error.registerForm.add"));
        return "redirect:register/registrationSuccess/" + user.getId();
    }
    
    @RequestMapping(value = "/registrationSuccess/{id}", method = RequestMethod.GET)
    public String handleRequestForSuccess(@PathVariable("id") int id,
            Model model
            ){
        model.addAttribute("user", userManager.getUserById(id));
//        model.addAttribute("devise", deviseManager.getAllDevise());
        return "registrationSuccess"; 
    }
}
