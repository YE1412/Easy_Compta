/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.controller.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easycompta.domain.service.UserManager;
import org.easycompta.object.Commande;
import org.easycompta.object.Facture;
import org.easycompta.object.Personne;
import org.easycompta.object.ProduitService;
import org.easycompta.service.dao.ActorsDAOManager;
import org.easycompta.service.dao.InvoicesDAOManager;
import org.easycompta.service.dao.LanguagesDAOManager;
import org.easycompta.service.dao.OrdersDAOManager;
import org.easycompta.service.dao.PaymentsDAOManager;
import org.easycompta.service.dao.ServicesDAOManager;
import org.easycompta.service.dao.TvaDAOManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *
 * @author Yannick
 */
@RequestMapping(value="/invoice_in_pdf")
public class InvoicePdfViewController {
    private ServicesDAOManager servicesManager;
    private OrdersDAOManager ordersManager;
    private InvoicesDAOManager invoicesManager;
    private ActorsDAOManager actorsManager;
    private LanguagesDAOManager languagesManager;
    private TvaDAOManager tvaManager;
    Map<Integer, String> languagesListForDisplay = new LinkedHashMap<>();
    Map<Integer, Float> pricesListForDisplay = new LinkedHashMap<>();
    Map<Integer, Integer> ordersListForDisplay = new LinkedHashMap<>();
    Map<Integer, Personne> sellersListForDisplay = new LinkedHashMap<>();
    Map<Integer, Personne> buyersListForDisplay = new LinkedHashMap<>();
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
        dateFormat, false));
    }

    public void setTvaManager(TvaDAOManager tvaManager) {
        this.tvaManager = tvaManager;
    }
    
    public void setPaymentsManager(PaymentsDAOManager paymentsManager) {
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
        	}
            catch (Exception e) {
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
    
    public void setPricesListForDisplay()
    {
        List<Commande> ordersList=ordersManager.getAllOrders();
        for (Commande c:ordersList)
        {
            pricesListForDisplay.put(c.getId(), c.getPriceTt());
        }
    }

    public void setActorsManager(ActorsDAOManager actorsManager) {
        this.actorsManager = actorsManager;
    }

    public void setLanguagesManager(LanguagesDAOManager languagesManager) {
        this.languagesManager = languagesManager;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleRequestForDisplay(Model model){
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String now_file=dateFormat2.format(now);
        model.addAttribute("now_for_file", now_file);
        model.addAttribute("now", now.toString());
        model.addAttribute("invoices", invoicesManager.getAllInvoices());
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
        return "invoice_in_pdf";
    }
    
    @RequestMapping(value="/{id}/{filename}", method=RequestMethod.GET)
    protected ModelAndView handleRequestInternal(@PathVariable("id") int id,
            HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        Map<String,Object> model = new HashMap<>();
        List<ProduitService> servList=new ArrayList<>();
        List<Integer> servIdList=null;
        Commande order=invoicesManager.getOrderByInvoiceId(id);
        servIdList = ordersManager.getContains(order.getId());
        for (int ids:servIdList){
            servList.add(servicesManager.getServiceById(ids));
        }
        model.put("services", servList);
        model.put("order", order);
        model.put("invoice", invoicesManager.getInvoiceById(id));
        model.put("seller", actorsManager.getActorById(invoicesManager.getSellerByInvoiceId(id)));
        model.put("buyer", actorsManager.getActorById(invoicesManager.getBuyerByInvoiceId(id)));
        model.put("tva", tvaManager.getTvaById(order.getIdTva()));
        return new ModelAndView("invoiceSummary", "model", model);
    }

    public void setUserManager(UserManager userManager) {
    }
    
    public void setServicesManager(ServicesDAOManager servicesManager) {
        this.servicesManager = servicesManager;
    }

    public void setOrdersManager(OrdersDAOManager ordersManager) {
        this.ordersManager = ordersManager;
    }

    public void setInvoicesManager(InvoicesDAOManager invoicesManager) {
        this.invoicesManager = invoicesManager;
    }
}
