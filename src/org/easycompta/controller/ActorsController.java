/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Personne;
import org.easycompta.service.dao.ActorsDAOManager;
import org.easycompta.service.validator.ActorsFormValidator;
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
@RequestMapping(value = "/actors")
public class ActorsController {
    private ActorsDAOManager actorsManager;
    private ActorsFormValidator actorsFormValidator;
    protected final Log logger = LogFactory.getLog(getClass());
    private ResourceBundle messages, validate;
    public ActorsController() {
    	messages = ResourceBundle.getBundle("messages");
    	validate = ResourceBundle.getBundle("validate");
    }
    public void setUserManager(UserManager userManager) {
    	
    }

    public void setActorsManager(ActorsDAOManager actorsManager) {
        this.actorsManager = actorsManager;
    }

    public void setActorsFormValidator(ActorsFormValidator actorsFormValidator) {
        this.actorsFormValidator = actorsFormValidator;
    }
    
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(actorsFormValidator);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("actors", actorsManager.getAllActors());
        model.addAttribute("actors_form", false);
        return "actors";
    }
    
    @RequestMapping(value="/addForm", method = RequestMethod.GET)
    public String handleRequestForAddForm(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("actors_form", true);
        model.addAttribute("actorsForm", new Personne());
        model.addAttribute("update", false);
        model.addAttribute("add", true);
        Map<String,String> types = new LinkedHashMap<>();
        types.put("-1", messages.getString("actors.select.option1"));
	types.put("0", messages.getString("actors.select.option2"));
	types.put("1", messages.getString("actors.select.option3"));
        model.addAttribute("typeList", types);
        return "actors";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String handleRequestForAddActor(@ModelAttribute("actorsForm") @Validated Personne actor, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("actors_form", true);
            model.addAttribute("actorsForm", actor);
            model.addAttribute("update", false);
            model.addAttribute("add", true);
            Map<String,String> types = new LinkedHashMap<>();
            types.put("-1", messages.getString("actors.select.option1"));
            types.put("0", messages.getString("actors.select.option2"));
            types.put("1", messages.getString("actors.select.option3"));
            model.addAttribute("typeList", types);
            return "actors";
        }      
//        model.addAttribute("services_form", false);
        if (actorsManager.insertActor(actor) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.actorsForm.add"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.actorsForm.add"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/actors";
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateForm(@PathVariable("id") int id,
            Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("actors_form", true);
        model.addAttribute("actorsForm", actorsManager.getActorById(id) != null ? actorsManager.getActorById(id) : new Personne());
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        Map<String,String> types = new LinkedHashMap<>();
        types.put("-1", messages.getString("actors.select.option1"));
	types.put("0", messages.getString("actors.select.option2"));
	types.put("1", messages.getString("actors.select.option3"));
        model.addAttribute("typeList", types);
        
        return "actors";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateActor(@ModelAttribute("actorsForm") @Validated Personne actor, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("actors_form", true);
            model.addAttribute("actorsForm", actor);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            Map<String,String> types = new LinkedHashMap<>();
            types.put("-1", messages.getString("actors.select.option1"));
            types.put("0", messages.getString("actors.select.option2"));
            types.put("1", messages.getString("actors.select.option3"));
            model.addAttribute("typeList", types);
            return "actors";
        }
        if(actorsManager.updateActor(actor) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.actorsForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.actorsForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/actors";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String handleRequestForDeleteActr(@PathVariable("id") int id,
            Model model,
            final RedirectAttributes redirectAttributes){
        if(actorsManager.deleteActor(id) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.actorsForm.delete"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.actorsForm.delete"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/actors";
    }
}
