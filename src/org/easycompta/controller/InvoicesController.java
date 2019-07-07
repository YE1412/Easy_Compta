/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Commande;
import org.easycompta.object.Facture;
import org.easycompta.object.Langue;
import org.easycompta.object.Personne;
import org.easycompta.service.dao.ActorsDAOManager;
import org.easycompta.service.dao.InvoicesDAOManager;
import org.easycompta.service.dao.LanguagesDAOManager;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.validator.InvoicesFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
@RequestMapping(value="/invoices")
public class InvoicesController {
    private InvoicesDAOManager invoicesManager;
    private OrdersDAOManager ordersManager;
    private LanguagesDAOManager languagesManager;
    private InvoicesFormValidator invoicesFormValidator;
    private ActorsDAOManager actorsManager;
    private ResourceBundle messages, validate;
    SessionLocaleResolver localResolver;
    Map<String, String> ordersListForSelect = new LinkedHashMap<>();
    Map<String, String> languagesListForSelect = new LinkedHashMap<>();
    Map<Integer, String> languagesListForDisplay = new LinkedHashMap<>();
    Map<Integer, Float> pricesListForDisplay = new LinkedHashMap<>();
    Map<Integer, Integer> ordersListForDisplay = new LinkedHashMap<>();
    Map<Integer, Personne> sellersListForDisplay = new LinkedHashMap<>();
//    Map<String, String> sellersListForSelect = new LinkedHashMap<>();
    Map<Integer, Personne> buyersListForDisplay = new LinkedHashMap<>();
//    WebApplicationContext ctx = null;
    //    Map<String, String> buyersListForSelect = new LinkedHashMap<>();
    @Autowired
    SessionLocaleResolver slr;
    public InvoicesController(HttpServletRequest req) {
//    	ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }
    public void setActorsManager(ActorsDAOManager actorsManager) {
        this.actorsManager = actorsManager;
    }
    
    public void setSellersListForDisplay()
    {
        List<Facture> invoicesList=invoicesManager.getAllInvoices();
        for (Facture f:invoicesList)
        {
            int idActor = 0;
            try {
            	idActor = invoicesManager.getSellerByInvoiceId(f.getId());
            }catch(Exception e) {
            	idActor = 0;
            }
            if (idActor != 0)
                sellersListForDisplay.put(f.getId(), actorsManager.getActorById(idActor));
            else
                sellersListForDisplay.put(f.getId(), new Personne());
        }
    }
    
    public void setBuyersListForDisplay()
    {
        List<Facture> invoicesList=invoicesManager.getAllInvoices();
        for (Facture f:invoicesList)
        {
        	
            int idActor = 0;
            try {
            	idActor = invoicesManager.getBuyerByInvoiceId(f.getId());
            } catch(Exception e) {
            	idActor = 0;
            }
            if (idActor != 0)
                buyersListForDisplay.put(f.getId(), actorsManager.getActorById(idActor));
            else
                buyersListForDisplay.put(f.getId(), new Personne());
        }
    }
    
    public void setLanguagesListForDisplay()
    {
        List<Facture> invoicesList=invoicesManager.getAllInvoices();
        for (Facture f:invoicesList)
        {
            languagesListForDisplay.put(f.getId(), languagesManager.getLanguageById(f.getIdLangue()).getLibelle());
        }
    }
    
    public void setOrdersListForDisplay()
    {
        List<Facture> invoicesList=invoicesManager.getAllInvoices();
        for (Facture f:invoicesList)
        {
            ordersListForDisplay.put(f.getId(), invoicesManager.getOrderByInvoiceId(f.getId()) != null ?
                                                            invoicesManager.getOrderByInvoiceId(f.getId()).getId() : 
                                                            null);
        }
    }
    
    public void setLanguagesManager(LanguagesDAOManager languagesManager) {
        this.languagesManager = languagesManager;
    }

    public void setInvoicesFormValidator(InvoicesFormValidator invoicesFormValidator) {
        this.invoicesFormValidator = invoicesFormValidator;
    }
    
    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(invoicesFormValidator);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
        dateFormat, false));
    }
    
    public void setPricesListForDisplay()
    {
        List<Commande> ordersList=ordersManager.getAllOrders();
        for (Commande c:ordersList)
        {
            pricesListForDisplay.put(c.getId(), c.getPriceTt());
        }
    }
    
    public void setLanguagesListForSelect()
    {
        List<Langue> languageList=languagesManager.getAllLanguages();
        languagesListForSelect.put("0", messages.getString("actors.select.option1"));
        for (Langue l:languageList)
        {
            languagesListForSelect.put(l.getId().toString(), l.getLibelle()+" - "+l.getNom());
        }
    }
    
    public void setOrdersListForSelect()
    {
        List<Commande> comList=ordersManager.getAllOrders();
        ordersListForSelect.put("0", messages.getString("actors.select.option1"));
        for (Commande c:comList)
        {
            ordersListForSelect.put(c.getId().toString(), "Commande n#"+c.getId());
        }
    }

    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }
    
    public void setUserManager(UserManager userManager) {
    }

    public void setInvoicesManager(InvoicesDAOManager invoicesManager) {
        this.invoicesManager = invoicesManager;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Model model){
        String now = (new Date()).toString();
        model.addAttribute("now", now);
        model.addAttribute("invoices", invoicesManager.getAllInvoices());
        model.addAttribute("invoices_form", false);
        setPricesListForDisplay();
        setOrdersListForDisplay();
        setLanguagesListForDisplay();
        setBuyersListForDisplay();
        setSellersListForDisplay();
        model.addAttribute("pricesList", pricesListForDisplay);
        model.addAttribute("ordersList", ordersListForDisplay);
        model.addAttribute("languagesList", languagesListForDisplay);
        model.addAttribute("sellersList", sellersListForDisplay);
        model.addAttribute("buyersList", buyersListForDisplay);
        return "invoices";
    }
    
    @RequestMapping(value = "/addForm", method = RequestMethod.GET)
    public String handleRequestForAddForm(HttpServletRequest req,
    		Model model){
    	messages = ResourceBundle.getBundle("messages", slr.resolveLocale(req));
    	validate = ResourceBundle.getBundle("validate", slr.resolveLocale(req));
        Date now = new Date();
        model.addAttribute("now", now.toString());
        model.addAttribute("invoices_form", true);
        model.addAttribute("invoicesForm", new Facture());
        model.addAttribute("update", false);
        model.addAttribute("add", true);
        setOrdersListForSelect();
        setLanguagesListForSelect();
        model.addAttribute("ordersList", ordersListForSelect);
        model.addAttribute("languagesList", languagesListForSelect);
        return "invoices";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String handleRequestForAddOrder(HttpServletRequest req,
    		@ModelAttribute("invoicesForm") @Validated Facture fac, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
        Date now = new Date();
        if (result.hasErrors())
        {        
            model.addAttribute("now", now.toString());
            model.addAttribute("invoices_form", true);
            model.addAttribute("invoicesForm", fac);
            model.addAttribute("update", false);
            model.addAttribute("add", true);
            setOrdersListForSelect();
            setLanguagesListForSelect();
            model.addAttribute("ordersList", ordersListForSelect);
            model.addAttribute("languagesList", languagesListForSelect);
            return "invoices";
        }
        fac.setDate(now);
//        model.addAttribute("services_form", false);
        int id = invoicesManager.insertInvoice(fac);
        if (id > 0)
        {
            Commande order=ordersManager.getOrderById(fac.getIdCommande());
            order.setIdFacture(id);
            if (ordersManager.updateOrder(order) == 1)
            {
                redirectAttributes.addFlashAttribute("msg", validate.getString("Success.invoicesForm.add"));
                redirectAttributes.addFlashAttribute("success", true);
            }
            else
            {
                redirectAttributes.addFlashAttribute("msg", validate.getString("Success.invoicesForm.add2"));
                redirectAttributes.addFlashAttribute("success", true);
            }
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.invoicesForm.add"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/invoices";
    }
    
    @RequestMapping(value = "/updateForm/{id}", method = RequestMethod.GET)
    public String handleRequestForUpdateContainsForm(@PathVariable("id") int id,
            Model model){
        String now = (new Date()).toString();
        /*Contains cont = new Contains();
        cont.setIdCommande(id);
        cont.setComporte(ordersManager.getContains(id));*/
        model.addAttribute("now", now);
        model.addAttribute("invoices_form", true);
        model.addAttribute("invoicesForm", invoicesManager.getInvoiceById(id));
        model.addAttribute("update", true);
        model.addAttribute("add", false);
        setOrdersListForSelect();
        setLanguagesListForSelect();
        model.addAttribute("ordersList", ordersListForSelect);
        model.addAttribute("languagesList", languagesListForSelect);
        return "invoices";
    }
    
    @RequestMapping(value = "/updateForm/update", method = RequestMethod.POST)
    public String handleRequestForUpdateOrder(HttpServletRequest req,
    		@ModelAttribute("invoicesForm") @Validated Facture fac, 
            BindingResult result,
            Model model,
            final RedirectAttributes redirectAttributes){
    	messages = ResourceBundle.getBundle("messages", slr.resolveLocale(req));
    	validate = ResourceBundle.getBundle("validate", slr.resolveLocale(req));
        if (result.hasErrors())
        {
            model.addAttribute("invoices_form", true);
            model.addAttribute("invoicesForm", fac);
            model.addAttribute("update", true);
            model.addAttribute("add", false);
            setOrdersListForSelect();
            setLanguagesListForSelect();
            model.addAttribute("ordersList", ordersListForSelect);
            model.addAttribute("languagesList", languagesListForSelect);
            return "invoices";
        }
        Commande c=ordersManager.getOrderById(fac.getIdCommande());
        c.setIdFacture(fac.getId());
        if(invoicesManager.updateInvoice(fac) > 0 && ordersManager.updateOrder(c) > 0)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.invoicesForm.update"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.invoicesForm.update"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/invoices";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String handleRequestForDeleteService(@PathVariable("id") int id,
            Model model,
            final RedirectAttributes redirectAttributes){
        if(invoicesManager.deleteInvoice(id) == 1)
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Success.invoicesForm.delete"));
            redirectAttributes.addFlashAttribute("success", true);
        }
        else
        {
            redirectAttributes.addFlashAttribute("msg", validate.getString("Error.invoicesForm.delete"));
            redirectAttributes.addFlashAttribute("success", false);
        }
        return "redirect:/payments";
    }
}
