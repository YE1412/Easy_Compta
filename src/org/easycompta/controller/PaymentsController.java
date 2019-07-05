/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Commande;
import org.easycompta.object.Paiement;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.dao.PaymentsDAOManager;
import org.easycompta.service.validator.PaymentsFormValidator;
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
@RequestMapping(value="/payments")
public class PaymentsController {
    private PaymentsDAOManager paymentsManager;
    private OrdersDAOManager ordersManager;
    private PaymentsFormValidator paymentsFormValidator;
    Map<Integer, List<Integer>> comListForDisplay = new LinkedHashMap<>();
    Map<String, String> stateListForSelect = new LinkedHashMap<>();
    Map<Integer, Float> priceTtListForDisplay = new LinkedHashMap<>();
    private ResourceBundle messages, validate;
    public PaymentsController() {
    	messages = ResourceBundle.getBundle("messages");
    	validate = ResourceBundle.getBundle("validate");
    }
    public void setTtListForDisplay() {
        List<Commande> orderList = ordersManager.getAllOrders();
        for (Commande c:orderList){
            priceTtListForDisplay.put(c.getId(), c.getPriceTt());
       }
    }

    public void setPaymentsFormValidator(PaymentsFormValidator paymentsFormValidator) {
        this.paymentsFormValidator = paymentsFormValidator;
    }
    
    
    
    public void setComListForDisplay() {
        List<Paiement> payList = paymentsManager.getAllPayments();
        for (Paiement p:payList){
            comListForDisplay.put(p.getId(), paymentsManager.getOrdersByPaymentId(p.getId()));
       }
    }
    
    public void setStateListForSelect() {
        stateListForSelect.put("true", messages.getString("payments.select.option1"));
        stateListForSelect.put("false", messages.getString("payments.select.option2"));
    }
    
    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }
    
    public void setUserManager(UserManager userManager) {
    }

    public void setPaymentsManager(PaymentsDAOManager paymentsManager) {
        this.paymentsManager = paymentsManager;
    }
    
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(paymentsFormValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("payments", paymentsManager.getAllPayments());
        model.addAttribute("payments_form", false);
        setComListForDisplay();
        model.addAttribute("ordersList", comListForDisplay);
        setTtListForDisplay();
        model.addAttribute("pricesList", priceTtListForDisplay);
        return "payments";
    }
    
    @RequestMapping(value = "/addForm", method = RequestMethod.GET)
    public String handleRequestForAddForm(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("payments_form", true);
        model.addAttribute("paymentsForm", new Paiement());
        model.addAttribute("update", false);
        model.addAttribute("add", true);
        setStateListForSelect();
        model.addAttribute("paymentStateList", stateListForSelect);
        return "payments";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String handleRequestForAddOrder(@ModelAttribute("paymentsForm") @Validated Paiement pay, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("payments_form", true);
            model.addAttribute("paymentsForm", pay);
            model.addAttribute("update", false);
            model.addAttribute("add", true);
            setStateListForSelect();
            model.addAttribute("paymentStateList", stateListForSelect);
            return "payments";
        }      
//        model.addAttribute("services_form", false);
        if (paymentsManager.insertPayment(pay) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.paymentsForm.add"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.paymentsForm.add"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/payments";
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateContainsForm(@PathVariable("id") int id,
            Model model){
        String now = (new Date()).toString();
        /*Contains cont = new Contains();
        cont.setIdCommande(id);
        cont.setComporte(ordersManager.getContains(id));*/
        model.addAttribute("now", now);
        model.addAttribute("payments_form", true);
        model.addAttribute("paymentsForm", paymentsManager.getPaymentById(id));
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        setStateListForSelect();
        model.addAttribute("paymentStateList", stateListForSelect);
        return "payments";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateOrder(@ModelAttribute("paymentsForm") @Validated Paiement pay, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("payments_form", true);
            model.addAttribute("paymentsForm", pay);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            setStateListForSelect();
            model.addAttribute("paymentStateList", stateListForSelect);
            return "payments";
        }
        if(paymentsManager.updatePayment(pay) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.paymentsForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.paymentsForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/payments";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String handleRequestForDeleteService(@PathVariable("id") int id,
            Model model,
            final RedirectAttributes redirectAttributes){
        if(paymentsManager.deletePayment(id) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.paymentsForm.remove"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.paymentsForm.remove"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/payments";
    }
}
