/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Commande;
import org.easycompta.object.ProduitService;
import org.easycompta.object.Tva;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.dao.ServicesDAOManager;
import org.easycompta.service.validator.ServicesFormValidator;
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

@RequestMapping(value = "/services")
public class ServicesController {
    protected final Log logger = LogFactory.getLog(getClass());
    ServicesDAOManager servicesManager;
    private OrdersDAOManager ordersManager;
    ServicesFormValidator servicesFormValidator;
    private ResourceBundle validate;
    public ServicesController() {
    	validate = ResourceBundle.getBundle("org/validate", Locale.getDefault());
    }
    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }

    public void setUserManager(UserManager userManager) {
    }

    public void setServicesManager(ServicesDAOManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    public void setServicesFormValidator(ServicesFormValidator servicesFormValidator) {
        this.servicesFormValidator = servicesFormValidator;
    }
    
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
            binder.setValidator(servicesFormValidator);
    }
     
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Model model) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("services", servicesManager.getAllServices());
        model.addAttribute("services_form", false);
        //ModelAndView mv = new ModelAndView("register", "Model", model);
        //logger.info("From " +hsr.getServletPath()+ " Returning login view with " + now);
        return "services";
    }
    
    @RequestMapping(value = "/addForm", method = RequestMethod.GET)
    public String handleRequestForAddForm(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("services_form", true);
        model.addAttribute("servicesForm", new ProduitService());
        model.addAttribute("update", false);
        model.addAttribute("add", true);
        return "services";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String handleRequestForAddService(@ModelAttribute("servicesForm") @Validated ProduitService service, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("services_form", true);
            model.addAttribute("servicesForm", service);
            model.addAttribute("update", false);
            model.addAttribute("add", true);
            return "services";
        }      
//        model.addAttribute("services_form", false);
        if (servicesManager.insertService(service) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.servicesForm.add"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.servicesForm.add"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/services";
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateForm(@PathVariable("id") int id,
            Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("services_form", true);
        model.addAttribute("servicesForm", servicesManager.getServiceById(id));
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        
        return "services";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateService(@ModelAttribute("servicesForm") @Validated ProduitService service, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        if (result.hasErrors())
        {
            model.addAttribute("services_form", true);
            model.addAttribute("servicesForm", service);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            return "services";
        }
        
        //model.addAttribute("services_form", false);
        
        if(servicesManager.updateService(service) == 1)
        {
            //Mettre à jour le prix de la commande liée au service
            List<Integer> ordersToUpdate = servicesManager.getOrdersId(service.getId());
            for(int idc:ordersToUpdate){
                Commande comToUpdate=updateOrder(service, idc);
                if (ordersManager.insertOrder(comToUpdate) != 1){
                    redirectAttributes.addFlashAttribute("msg", validate.getString("Success.servicesForm.update2"));
                    redirectAttributes.addFlashAttribute("success", true);
                    break;
                }
            }
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.servicesForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.servicesForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/services";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String handleRequestForDeleteService(@PathVariable("id") int id,
            Model model,
            final RedirectAttributes redirectAttributes){
        if(servicesManager.deleteService(id) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.servicesForm.delete"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.servicesForm.delete"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/services";
    }
    
    private Commande updateOrder(ProduitService service, int orderId){
        float ht_price = 0, tt_price = 0;
        Commande order=ordersManager.getOrderById(orderId);
        for(Integer item:ordersManager.getContains(order.getId())){
            ht_price += servicesManager.getServiceById(item).getMontantHt() *  servicesManager.getServiceById(item).getQuantite();
        }
        for(Tva item:ordersManager.getAllTva())
        {
           if(item.getId() == order.getIdTva())
           {
               tt_price = ht_price + (ht_price * item.getPourcentage());
               break;
           }
        }
        order.setPriceHt(ht_price);
        order.setPriceTt(tt_price);
        return (order);
    }
}
