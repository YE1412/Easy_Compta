/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//import orgx.servlet.http.HttpServletRequest;
//import orgx.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easycompta.domain.service.UserManager;

/**
 *
 * @author Yannick
 */
public class LoginController implements Controller {
    private UserManager userManager;
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //super.addResourceHandlers(registry); //To change body of generated methods, choose Tools | Templates.
        registry.addResourceHandler("/resources/**") 
                .addResourceLocations("WEB-INF/Resources/theme1/");
              //.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }*/
    protected final Log logger = LogFactory.getLog(getClass()); 
    @Override
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //logger.info("Returning login view");
        
        String now = (new Date()).toString();
        Map<String, Object> model = new HashMap<>();
        model.put("now", now);
        model.put("users", this.userManager.getUsers()); 
        
        ModelAndView mv = new ModelAndView("index", "Model", model);
//     logger.info("From " +hsr.getServletPath()+ " Returning login view with " + now);
        return mv;
    }  

    /**
     * @param userManager the userManager to set
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
}
