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
import org.easycompta.object.Commande;
import org.easycompta.object.Composed;
import org.easycompta.object.Paiement;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.dao.PaymentsDAOManager;
import org.easycompta.service.validator.ComposedFormValidator;
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
@RequestMapping(value="/orders_in_payment")
public class OrdersInPaymentFormController {
    private PaymentsDAOManager paymentsManager;
    private OrdersDAOManager ordersManager;
    //private ServicesDAOManager servicesManager;
    private ComposedFormValidator composedFormValidator;
    Map<String,String> ordersListForSelect = new LinkedHashMap<>();
    private ResourceBundle validate, messages;
    @Autowired
    SessionLocaleResolver slr;
    private OrdersInPaymentFormController() {
    	
    }
    private void setOrdersListForSelect(){
        List<Commande> ordersList = ordersManager.getAllOrders();
        ordersListForSelect.put("0", messages.getString("actors.select.option1"));
        ordersList.forEach((item) -> {
            ordersListForSelect.put(item.getId().toString(), item.getId().toString());
       });
    }
    
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(composedFormValidator);
    }
    public void setComposedFormValidator(ComposedFormValidator composedFormValidator) {
        this.composedFormValidator = composedFormValidator;
    }
    
    public void setUserManager(UserManager userManager) {
    }

    public void setPaymentsManager(PaymentsDAOManager paymentsManager) {
        this.paymentsManager = paymentsManager;
    }

    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }

    /*public void setServicesManager(ServicesDAOManager servicesManager) {
        this.servicesManager = servicesManager;
    }*/
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateContainsForm(HttpServletRequest req,
    		@PathVariable("id") int id,
            Model model){
    	messages = ResourceBundle.getBundle("messages", slr.resolveLocale(req));
    	validate = ResourceBundle.getBundle("validate", slr.resolveLocale(req));
        String now = (new Date()).toString();
        Composed comp = new Composed();
        comp.setIdPayment(id);
        comp.setCompose(paymentsManager.getOrdersByPaymentId(id));
        model.addAttribute("now", now);
        model.addAttribute("composedForm", comp);
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        setOrdersListForSelect();
        model.addAttribute("ordersList", ordersListForSelect);
        
        return "orders_in_payment";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateServicesInOrder(HttpServletRequest req,
    		@ModelAttribute("composedForm") @Validated Composed comp, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
    	messages = ResourceBundle.getBundle("messages", slr.resolveLocale(req));
    	validate = ResourceBundle.getBundle("validate", slr.resolveLocale(req));
        if (result.hasErrors())
        {
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            setOrdersListForSelect();
            model.addAttribute("ordersList", ordersListForSelect);
            return "orders_in_payment";
        }
        Paiement payUpdated = paymentsManager.getPaymentById(comp.getIdPayment());
        if(paymentsManager.deleteComposed(comp.getIdPayment()) >= 0 
                && paymentsManager.insertOrdersInPayment(comp) == 1 
                && paymentsManager.updatePayment(payUpdated) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.orders_in_paymentForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.orders_in_paymentForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/payments";
    }
}
