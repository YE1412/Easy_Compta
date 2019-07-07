/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller.form;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Concerner;
import org.easycompta.object.Personne;
import org.easycompta.service.dao.ActorsDAOManager;
import org.easycompta.service.dao.InvoicesDAOManager;
import org.easycompta.service.validator.ConcernedFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Yannick
 */
@RequestMapping(value="/actors_in_invoice")
public class ActorsInInvoiceFormController {
    private ActorsDAOManager actorsManager;
    private InvoicesDAOManager invoicesManager;
    private ConcernedFormValidator concernedFormValidator;
    Map<String, String> sellersListForSelect = new LinkedHashMap<>();
    Map<String, String> buyersListForSelect = new LinkedHashMap<>();
    private ResourceBundle messages, validate;
    @Autowired
    SessionLocaleResolver slr;
    private ActorsInInvoiceFormController() {
    	
    }
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(concernedFormValidator);
    }

    public void setConcernedFormValidator(ConcernedFormValidator concernedFormValidator) {
        this.concernedFormValidator = concernedFormValidator;
    }
    
    public void setActorsValidator(ConcernedFormValidator concernedValidator) {
        this.concernedFormValidator = concernedValidator;
    }
    
    public void setUserManager(UserManager userManager) {
    }

    public void setActorsManager(ActorsDAOManager actorsManager) {
        this.actorsManager = actorsManager;
    }

    public void setInvoicesManager(InvoicesDAOManager invoicesManager) {
        this.invoicesManager = invoicesManager;
    }
    
    public void setSellersListForSelect()
    {
        List<Personne> actorsList=actorsManager.getAllSellers();
        sellersListForSelect.put("0", messages.getString("actors.select.option1"));
        for (Personne p:actorsList)
        {
            sellersListForSelect.put(p.getId().toString(), p.getNom()+" "+p.getPrenom());
        }
    }
    
    public void setBuyersListForSelect()
    {
        List<Personne> actorsList=actorsManager.getAllBuyers();
        buyersListForSelect.put("0", messages.getString("actors.select.option1"));
        for (Personne p:actorsList)
        {
            buyersListForSelect.put(p.getId().toString(), p.getNom()+" "+p.getPrenom());
        }
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateContainsForm(HttpServletRequest req,
    		@PathVariable("id") int id,
            Model model){
    	messages = ResourceBundle.getBundle("messages", slr.resolveLocale(req));
    	validate = ResourceBundle.getBundle("validate", slr.resolveLocale(req));
        String now = (new Date()).toString();
        /*Contains cont = new Contains();
        cont.setIdCommande(id);
        cont.setComporte(ordersManager.getContains(id));*/
        model.addAttribute("now", now);
        Concerner conc=new Concerner();
        conc.setIdFacture(id);
        int idClient = 0, idVendeur = 0;
        try {
        	idClient = invoicesManager.getBuyerByInvoiceId(id);
        	idVendeur = invoicesManager.getSellerByInvoiceId(id);
        }catch (Exception ex){
        	idClient = 0;
        	idVendeur = 0;
        }
        conc.setIdClient(idClient);
        conc.setIdVendeur(idVendeur);
        model.addAttribute("concernedForm", conc);
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        setSellersListForSelect();
        setBuyersListForSelect();
        model.addAttribute("sellersList", sellersListForSelect);
        model.addAttribute("buyersList", buyersListForSelect);
        return "actors_in_invoice";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateOrder(HttpServletRequest req,
    		@ModelAttribute("concernedForm") @Validated Concerner conc, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
    	messages = ResourceBundle.getBundle("messages", slr.resolveLocale(req));
    	validate = ResourceBundle.getBundle("validate", slr.resolveLocale(req));
        if (result.hasErrors())
        {
            model.addAttribute("concernedForm", conc);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            setSellersListForSelect();
            setBuyersListForSelect();
            model.addAttribute("sellersList", sellersListForSelect);
            model.addAttribute("buyersList", buyersListForSelect);
            return "actors_in_invoice";
        }
        if(invoicesManager.insertActors(conc) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.actors_in_invoiceForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.actors_in_invoiceForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/invoices";
    }
}
