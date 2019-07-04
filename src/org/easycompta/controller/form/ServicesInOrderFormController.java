/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller.form;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Commande;
import org.easycompta.object.Contains;
import org.easycompta.object.ProduitService;
import org.easycompta.object.Tva;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.dao.ServicesDAOManager;
import org.easycompta.service.validator.ContainsFormValidator;
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
@RequestMapping(value="/services_in_order")
public class ServicesInOrderFormController {
   private OrdersDAOManager ordersManager;
   private ServicesDAOManager servicesManager;
   private ContainsFormValidator containsFormValidator;
   Map<String,String> servicesListForSelect = new LinkedHashMap<>();
   private ResourceBundle messages, validate;
   private ServicesInOrderFormController() {
	   	messages = ResourceBundle.getBundle("org/messages", Locale.getDefault());
   		validate = ResourceBundle.getBundle("org/validate", Locale.getDefault());
   }
	public void setContainsFormValidator(ContainsFormValidator containsFormValidator) {
	    this.containsFormValidator = containsFormValidator;
	}
    
    public void setUserManager(UserManager userManager) {
    }

    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }

    public void setServicesManager(ServicesDAOManager servicesManager) {
        this.servicesManager = servicesManager;
    }
    
    public void setServicesListForSelect() {
        List<ProduitService> servicesList = servicesManager.getAllServices();
        servicesListForSelect.put("0", messages.getString("actors.select.option1"));
        servicesList.forEach((item) -> {
            servicesListForSelect.put(item.getId().toString(), item.getNom());
       });
    }
    
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(containsFormValidator);
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateContainsForm(@PathVariable("id") int id,
            Model model){
        String now = (new Date()).toString();
        Contains cont = new Contains();
        cont.setIdCommande(id);
        cont.setComporte(ordersManager.getContains(id));
        model.addAttribute("now", now);
        model.addAttribute("containsForm", cont);
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        setServicesListForSelect();
        model.addAttribute("servicesList", servicesListForSelect);
        
        return "services_in_order";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateServicesInOrder(@ModelAttribute("containsForm") @Validated Contains cont, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            //model.addAttribute("containsForm", cont);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            setServicesListForSelect();
            model.addAttribute("servicesList", servicesListForSelect);
            return "services_in_order";
        }
        Commande comUpdated = ordersManager.getOrderById(cont.getIdCommande());
        comUpdated = updateOrderModel(comUpdated, cont);
        if(ordersManager.deleteContains(cont.getIdCommande()) >= 0 
                && ordersManager.insertServicesInOrder(cont) == 1 
                && ordersManager.updateOrder(comUpdated) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.services_in_orderForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.services_in_orderForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/orders";
    }
    
    private Commande updateOrderModel(Commande comUpdated, Contains cont){
        float ht_price = 0, tt_price = 0;
//        System.err.println("Contenu "+cont.getComporte());
        for(Integer item:cont.getComporte()){
//        	System.err.println("	Service "+servicesManager.getServiceById(item));
            ht_price += servicesManager.getServiceById(item).getMontantHt() *  servicesManager.getServiceById(item).getQuantite();
        }
        for(Tva item:ordersManager.getAllTva())
        {
           if(item.getId() == comUpdated.getIdTva())
           {
               tt_price = ht_price + (ht_price * item.getPourcentage());
               break;
           }
        }
        comUpdated.setPriceHt(ht_price);
        comUpdated.setPriceTt(tt_price);
//        System.err.println("Commande "+comUpdated);
        return (comUpdated);
    }
}
