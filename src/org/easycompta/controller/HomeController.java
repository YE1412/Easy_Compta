/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easycompta.domain.service.UserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Yannick
 */
@RequestMapping(value = "/home")
public class HomeController {
    private UserManager userManager;
    protected final Log logger = LogFactory.getLog(getClass());

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Map<String, Object> model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String now = (new Date()).toString();
        model.put("now", now);
        model.put("users", this.userManager.getUsers());
        //ModelAndView mv = new ModelAndView("register", "Model", model);
        //logger.info("From " +hsr.getServletPath()+ " Returning login view with " + now);
        return "home";
    }
}
