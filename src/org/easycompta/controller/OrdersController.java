/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Commande;
import org.easycompta.object.Tva;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.dao.ServicesDAOManager;
import org.easycompta.service.validator.OrdersFormValidator;
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
@RequestMapping(value="/orders")
public class OrdersController {
   private OrdersDAOManager ordersManager;
   private ServicesDAOManager servicesManager;
   private OrdersFormValidator ordersFormValidator;
   Map<String,String> tvaListForSelect = new LinkedHashMap<>();
   Map<Integer, Tva> tvaListForDisplay = new LinkedHashMap<>();
   Map<Integer, List<Integer>> servicesListForDisplay = new LinkedHashMap<>();
   private ResourceBundle validate, messages;
    public void setServicesManager(ServicesDAOManager servicesManager) {
        this.servicesManager = servicesManager;
    	validate = ResourceBundle.getBundle("org/validate", Locale.getDefault());
    	messages = ResourceBundle.getBundle("org/messages", Locale.getDefault());
    }
   
    public void setOrdersFormValidator(OrdersFormValidator ordersFormValidator) {
        this.ordersFormValidator = ordersFormValidator;
    }

    public void setTvaListForSelect() {
        List<Tva> tvaList = ordersManager.getAllTva();
        tvaListForSelect.put("-1", messages.getString("actors.select.option1"));
        tvaList.forEach((item) -> {
            tvaListForSelect.put(item.getId().toString(), item.getLibelle()+ " " + item.getPourcentage());
       });
    }
    
    public void setServicesListForDisplay() {
        List<Commande> comList = ordersManager.getAllOrders();
        for (Commande com:comList)
        {
            servicesListForDisplay.put(com.getId(), ordersManager.getContains(com.getId()));
        }
    }
    
    public void setTvaListForDisplay() {
        List<Commande> comList = ordersManager.getAllOrders();
        for (Commande com:comList)
        {
            tvaListForDisplay.put(com.getId(), ordersManager.getTvaById(com.getIdTva()));
        }
    }
  
    public void setUserManager(UserManager userManager) {
    }

    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }
   
   //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(ordersFormValidator);
    }
   
   @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Model model){
        String now = (new Date()).toString();
        setServicesListForDisplay();
        setTvaListForDisplay();
        if (ordersManager.getAllOrders().size() > 0)
        	System.err.println("order "+ordersManager.getAllOrders().get(0));
        model.addAttribute("now", now);
        model.addAttribute("tvaList", tvaListForDisplay);
        model.addAttribute("servicesList", servicesListForDisplay);
        model.addAttribute("orders", ordersManager.getAllOrders());
        model.addAttribute("orders_form", false);
        return "orders";
    }
    
    @RequestMapping(value="/addForm", method = RequestMethod.GET)
    public String handleRequestForAddForm(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("orders_form", true);
        model.addAttribute("ordersForm", new Commande());
        model.addAttribute("update", false);
        model.addAttribute("add", true);
        setTvaListForSelect();
        model.addAttribute("tvaList", tvaListForSelect);
        return "orders";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String handleRequestForAddOrder(@ModelAttribute("ordersForm") @Validated Commande com, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("orders_form", true);
            model.addAttribute("ordersForm", com);
            model.addAttribute("update", false);
            model.addAttribute("add", true);
            setTvaListForSelect();
            model.addAttribute("tvaList", tvaListForSelect);
            return "orders";
        }      
//        model.addAttribute("services_form", false);
        if (ordersManager.insertOrder(com) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.ordersForm.add"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.ordersForm.add"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/orders";
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateForm(@PathVariable("id") int id,
            Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("orders_form", true);
        model.addAttribute("ordersForm", ordersManager.getOrderById(id));
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        setTvaListForSelect();
        model.addAttribute("tvaList", tvaListForSelect);
        
        return "orders";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateOrder(@ModelAttribute("ordersForm") @Validated Commande com, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("orders_form", true);
            model.addAttribute("ordersForm", com);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            setTvaListForSelect();
            model.addAttribute("tvaList", tvaListForSelect);
            return "orders";
        }
        float ht_price = 0, tt_price = 0;
        for(Integer item:ordersManager.getContains(com.getId())){
            ht_price += servicesManager.getServiceById(item).getMontantHt() *  servicesManager.getServiceById(item).getQuantite();
        }
        for(Tva item:ordersManager.getAllTva())
        {
           if(item.getId() == com.getIdTva())
           {
               tt_price = ht_price + (ht_price * item.getPourcentage());
               break;
           }
        }
        com.setPriceHt(ht_price);
        com.setPriceTt(tt_price);
        if(ordersManager.updateOrder(com) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.ordersForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.ordersForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/orders";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String handleRequestForDeleteService(@PathVariable("id") int id,
            Model model,
            final RedirectAttributes redirectAttributes){
        if(ordersManager.deleteOrder(id) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.ordersForm.remove"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.ordersForm.remove"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/orders";
    }
}
