/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.views;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easycompta.object.Commande;
import org.easycompta.object.Facture;
import org.easycompta.object.Personne;
import org.easycompta.object.ProduitService;
import org.easycompta.object.Tva;

/**
 *
 * @author Yannick
 */
public class InvoicePDFView extends AbstractPdfView{
    
    private List<ProduitService> servicesList = null;
    private Commande order = null;
    private int idLangue = 0;
    private Facture invoice = null;
    private Personne seller = null;
    private Personne buyer = null;
    private Tva tva = null;
    private static final Font catFont = new Font(Font.FontFamily.COURIER, 18, Font.BOLD);
    private static final Font subjectFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD | Font.ITALIC);
    private static final Font headingTableFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font heading_smallTableFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD | Font.ITALIC);
    private static final Font heading_smallTableFontWithoutItalic = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static final Font content_smallTableFontWithoutItalic = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    private static final Font contentTableFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private static final Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private static final Font subFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font smallFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    
    public void setInvoice(Facture invoice) {
        this.invoice = invoice;
    }
    
    public void setServicesList(List<ProduitService> servicesList) {
        this.servicesList = servicesList;
    }

    public void setOrder(Commande order) {
        this.order = order;
    }

    public void setIdLangue(int idLangue) {
        this.idLangue = idLangue;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    protected void buildPdfDocument(Map<String, Object> map, Document dcmnt, PdfWriter writer, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        hsr1.setHeader("Content-Disposition", "attachment; filename\"pdf_file.pdf\"");
        Map<String,Object> model = (Map<String,Object>) map.get("model");
        servicesList=(List<ProduitService>) model.get("services");
        order=(Commande) model.get("order");
        invoice=(Facture) model.get("invoice");
        seller=(Personne) model.get("seller");
        buyer=(Personne) model.get("buyer");
        tva=(Tva) model.get("tva");
        idLangue = invoice.getIdLangue();
        try
        {
            getDoc(dcmnt, idLangue);
        }catch(DocumentException e){
            e.printStackTrace();
        }
        
        
    }
    
    private void getDoc(Document dcmnt, int idLangue) throws DocumentException
    {
    	new DecimalFormat("#.##");
        Paragraph head = new Paragraph();
        Paragraph head_company = new Paragraph();
        Paragraph head_title = new Paragraph();
        Paragraph content = new Paragraph();
        PdfPTable table=new PdfPTable(7);
        Paragraph p, p2, p3, p4, p5, p6, p7, p8, p9,
        p10, p11, p12, p13, p14, p15, p16, p17, p18,
        p19, p20, p21, p22;
        Phrase ph1, ph2, ph3;
        char devise;
        Locale local;
        switch (idLangue) {
        	case 2:
        		devise = '€';
        		local = new Locale("fr");
        		break;
        	case 1:
        		devise  = '$';
        		local = new Locale("us");
        		break;
        	case 3:
        		devise = '£';
        		local = new Locale("en");
        		break;
        	default:
        		local = new Locale("en");
        		devise = '£';
        }
        SimpleDateFormat sdfr = null;
        System.err.println("idLangue : "+idLangue);
        if (idLangue == 2) {
        	sdfr = new SimpleDateFormat("dd/MM/yyyy");
        	p=new Paragraph("Adresse de facturation :", headingTableFont);
            p2=new Paragraph(buyer.getPrenom()+" "+buyer.getNom(), contentTableFont);
            p3=new Paragraph(buyer.getNumRue()+ " " +buyer.getNomRue(), contentTableFont);
            p4=new Paragraph("F-"+buyer.getCp()+" "+buyer.getVille(), contentTableFont);
        }
        else {
        	sdfr = new SimpleDateFormat("MM.dd.yyyy");
        	p=new Paragraph("Billing address :", headingTableFont);
            p2=new Paragraph(buyer.getPrenom()+" "+buyer.getNom(), contentTableFont);
            p3=new Paragraph(buyer.getNumRue()+ " " +buyer.getNomRue(), contentTableFont);
            p4=new Paragraph("F-"+buyer.getCp()+" "+buyer.getVille(), contentTableFont);
        }
        addEmptyLine(p4, 1);
        addEmptyLine(p4, 1);
        addEmptyLine(p4, 1);
        if (idLangue == 2) {
        	p5=new Paragraph("Contenu supplémentaire :", heading_smallTableFont);
            p5.setAlignment(Element.ALIGN_CENTER);
            p6=new Paragraph(order.getContenuAdditionnel(), contentTableFont);
            p6.setIndentationLeft(5);
            p7=new Paragraph("Vendeur :", heading_smallTableFont);
            p7.setAlignment(Element.ALIGN_CENTER);
            p8=new Paragraph(seller.getId().toString(), contentTableFont);
            p8.setIndentationLeft(5);
            p9=new Paragraph("Client :", heading_smallTableFont);
            p9.setAlignment(Element.ALIGN_CENTER);
            p10=new Paragraph(buyer.getId().toString(), contentTableFont);
            p10.setIndentationLeft(5);
            p11=new Paragraph("Date :", heading_smallTableFont);
            p11.setAlignment(Element.ALIGN_CENTER);
            p12=new Paragraph(sdfr.format(invoice.getDate()), contentTableFont);
            p12.setIndentationLeft(5);
        }else {
        	p5=new Paragraph("Additional content :", heading_smallTableFont);
            p5.setAlignment(Element.ALIGN_CENTER);
            p6=new Paragraph(order.getContenuAdditionnel(), contentTableFont);
            p6.setIndentationLeft(5);
            p7=new Paragraph("Seller :", heading_smallTableFont);
            p7.setAlignment(Element.ALIGN_CENTER);
            p8=new Paragraph(seller.getId().toString(), contentTableFont);
            p8.setIndentationLeft(5);
            p9=new Paragraph("Buyer :", heading_smallTableFont);
            p9.setAlignment(Element.ALIGN_CENTER);
            p10=new Paragraph(buyer.getId().toString(), contentTableFont);
            p10.setIndentationLeft(5);
            p11=new Paragraph("Date :", heading_smallTableFont);
            p11.setAlignment(Element.ALIGN_CENTER);
            p12=new Paragraph(sdfr.format(invoice.getDate()), contentTableFont);
            p12.setIndentationLeft(5);
        }
        ph1=new Phrase("N# ", heading_smallTableFont);
        ph2=new Phrase(order.getId().toString(), subjectFont);
        p13=new Paragraph();
        p13.add(ph1);
        p13.add(ph2);
        if (idLangue == 2) {
        	p15=new Paragraph("DESIGNATION", heading_smallTableFontWithoutItalic);
            addEmptyLine(p15, 1);
            p16=new Paragraph("N# SERVICE", heading_smallTableFontWithoutItalic);
            addEmptyLine(p16, 1);
            p17=new Paragraph("PRIX TT", heading_smallTableFontWithoutItalic);
            addEmptyLine(p17, 1);
            p18=new Paragraph("QUANTITE", heading_smallTableFontWithoutItalic);
            addEmptyLine(p18, 1);
            p19=new Paragraph("PRIX NET TT", heading_smallTableFontWithoutItalic);
            addEmptyLine(p19, 1);
            p20=new Paragraph("MONTANT TT", heading_smallTableFontWithoutItalic);
            addEmptyLine(p20, 1);
            p21=new Paragraph("Total", heading_smallTableFontWithoutItalic);
            p21.setAlignment(Element.ALIGN_LEFT);
            p22=new Paragraph(String.format(local, "%.2f", order.getPriceTt())+devise, heading_smallTableFontWithoutItalic);
            p22.setAlignment(Element.ALIGN_RIGHT);
        }else {
        	p15=new Paragraph("DESCRIPTION", heading_smallTableFontWithoutItalic);
            addEmptyLine(p15, 1);
            p16=new Paragraph("N# SERVICE", heading_smallTableFontWithoutItalic);
            addEmptyLine(p16, 1);
            p17=new Paragraph("PRICE INCL. TAXES", heading_smallTableFontWithoutItalic);
            addEmptyLine(p17, 1);
            p18=new Paragraph("QUANTITY", heading_smallTableFontWithoutItalic);
            addEmptyLine(p18, 1);
            p19=new Paragraph("PRICE NET INCL. TAXES", heading_smallTableFontWithoutItalic);
            addEmptyLine(p19, 1);
            p20=new Paragraph("AMOUNT INCL. TAXES", heading_smallTableFontWithoutItalic);
            addEmptyLine(p20, 1);
            p21=new Paragraph("Total", heading_smallTableFontWithoutItalic);
            p21.setAlignment(Element.ALIGN_LEFT);
            p22=new Paragraph(String.format(local, "%.2f", order.getPriceTt())+devise, heading_smallTableFontWithoutItalic);
            p22.setAlignment(Element.ALIGN_RIGHT);
        }
        PdfPCell cell=new PdfPCell();
        PdfPCell cell2=new PdfPCell();
        PdfPCell cell3=new PdfPCell();
        PdfPCell cell4=new PdfPCell();
        PdfPCell cell5=new PdfPCell();
        PdfPCell cell6=new PdfPCell();
        PdfPCell cell7=new PdfPCell();
        PdfPCell cell8=new PdfPCell();
        PdfPCell cell9=new PdfPCell();
        PdfPCell cell10=new PdfPCell();
        PdfPCell cell11=new PdfPCell();
        PdfPCell cell12=new PdfPCell();
        PdfPCell cell13=new PdfPCell();
        PdfPCell cell14=new PdfPCell();
        cell.setColspan(7);
        cell2.setColspan(2);
        cell6.setColspan(2);
        cell7.setColspan(2);
        cell.addElement(p);
        cell.addElement(p2);
        cell.addElement(p3);
        cell.addElement(p4);
        cell2.addElement(p5);
        cell2.addElement(p6);
        cell3.addElement(p7);
        cell3.addElement(p8);
        cell4.addElement(p9);
        cell4.addElement(p10);
        cell5.addElement(p11);
        cell5.addElement(p12);
        cell6.addElement(p13);
        cell7.addElement(p15);
        cell8.addElement(p16);
        cell9.addElement(p17);
        cell10.addElement(p18);
        cell11.addElement(p19);
        cell12.addElement(p20);
        cell13.setColspan(5);
        cell13.addElement(p21);
        cell13.setBorder(Rectangle.NO_BORDER);
        cell14.setColspan(2);
        cell14.addElement(p22);
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setHorizontalAlignment(Element.ALIGN_TOP);
        cell.setBorderWidth(1);
        cell2.setBorderWidth(1);
        cell3.setBorderWidth(1);
        cell4.setBorderWidth(1);
        cell5.setBorderWidth(1);
        cell6.setBorderWidth(1);
        cell7.setBorderWidth(1);
        cell8.setBorderWidth(1);
        cell9.setBorderWidth(1);
        cell10.setBorderWidth(1);
        cell11.setBorderWidth(1);
        cell12.setBorderWidth(1);
        cell14.setBorderWidth(0);
        cell14.setBorderWidthTop(1);
        /*cell14.setBorderWidthBottom(0);
        cell14.setBorderWidthLeft(0);
        cell14.setBorderWidthRight(0);*/
        table.addCell(cell);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        insert_services(table, idLangue);
        table.addCell(cell13);
        table.addCell(cell14);
        table.setWidthPercentage(100);
        if (idLangue == 2) {
        	head_company= new Paragraph("INFOSERVICES", subFont);
            head_title= new Paragraph("FACTURE", subjectFont);
        }else {
        	head_company= new Paragraph("INFOSERVICES", subFont);
            head_title= new Paragraph("INVOICE", subjectFont);
        }
        head_title.setAlignment(Element.ALIGN_LEFT);
        head_title.setIndentationLeft(50);
        head.add(head_company);
        head.add(head_title);
        addEmptyLine(head, 1);
        addEmptyLine(head, 1);
        
        content.add(head);
        content.add(table);
        addEmptyLine(content, 1);
        insert_end_table(content, idLangue);
        dcmnt.add(content);
    }
    
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    private void insert_services(PdfPTable table, int idLangue){
        for (ProduitService s:servicesList){
            float tu_tt = s.getMontantHt() + (s.getMontantHt() * tva.getPourcentage());
            float tt_net = tu_tt * s.getQuantite();
            char devise;
            Locale local;
            switch (idLangue) {
            	case 2:
            		devise = '€';
            		local = new Locale("fr");
            		break;
            	case 1:
            		devise  = '$';
            		local = new Locale("us");
            		break;
            	case 3:
            		devise = '£';
            		local = new Locale("en");
            		break;
            	default:
            		local = new Locale("en");
            		devise = '£';
            }
            Paragraph p=new Paragraph(s.getNom(), content_smallTableFontWithoutItalic);
            Paragraph p2=new Paragraph(s.getId().toString(), content_smallTableFontWithoutItalic);
            Paragraph p3=new Paragraph(String.format(local, "%.2f", tu_tt)+devise, content_smallTableFontWithoutItalic);
            Paragraph p4=new Paragraph(String.valueOf(s.getQuantite()), content_smallTableFontWithoutItalic);
            Paragraph p5=new Paragraph(String.format(local, "%.2f", tt_net)+devise, content_smallTableFontWithoutItalic);
            Paragraph p6=new Paragraph(String.format(local, "%.2f", tt_net)+devise, content_smallTableFontWithoutItalic);
            PdfPCell cell=new PdfPCell(p);
            PdfPCell cell2=new PdfPCell(p2);
            PdfPCell cell3=new PdfPCell(p3);
            PdfPCell cell4=new PdfPCell(p4);
            PdfPCell cell5=new PdfPCell(p5);
            PdfPCell cell6=new PdfPCell(p6);
            
            cell.setBorder(Rectangle.NO_BORDER);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell4.setBorder(Rectangle.NO_BORDER);
            cell5.setBorder(Rectangle.NO_BORDER);
            cell6.setBorder(Rectangle.NO_BORDER);
            cell.setBackgroundColor(GrayColor.LIGHT_GRAY);
            cell3.setBackgroundColor(GrayColor.LIGHT_GRAY);
            cell5.setBackgroundColor(GrayColor.LIGHT_GRAY);
            cell.setColspan(2);
            cell.addElement(p);
            cell2.addElement(p2);
            cell3.addElement(p3);
            cell4.addElement(p4);
            cell5.addElement(p5);
            cell6.addElement(p6);
            table.addCell(cell);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
        }
    }
    
    private void insert_end_table(Paragraph content, int idLangue)
    {
        float tvaPourcent = tva.getPourcentage();
        float tva_montant = order.getPriceTt() * tvaPourcent;
        float base = order.getPriceTt() - tva_montant;
        PdfPTable table=new PdfPTable(6);
        Paragraph p, p2, p3, p4, p5, p6, p7, p8;
        char devise;
        Locale local;
        switch (idLangue) {
        	case 2:
        		devise = '€';
        		local = new Locale("fr");
        		break;
        	case 1:
        		devise  = '$';
        		local = new Locale("us");
        		break;
        	case 3:
        		devise = '£';
        		local = new Locale("en");
        		break;
        	default:
        		local = new Locale("en");
        		devise = '£';
        }
        if (idLangue == 2) {
        	p=new Paragraph("TVA", heading_smallTableFontWithoutItalic);
            p.setAlignment(Element.ALIGN_CENTER);
            p2=new Paragraph(String.format(local, "%.2f", tvaPourcent * 100)+" %", contentTableFont);
            p3=new Paragraph("Base TVA", heading_smallTableFontWithoutItalic);
            p3.setAlignment(Element.ALIGN_CENTER);
            p4=new Paragraph(String.format(local, "%.2f", base)+devise, contentTableFont);
            p5=new Paragraph("Montant TVA", heading_smallTableFontWithoutItalic);
            p5.setAlignment(Element.ALIGN_CENTER);
            p6=new Paragraph(String.format(local, "%.2f", tva_montant)+devise, contentTableFont);
            p7=new Paragraph("Total TTC", heading_smallTableFontWithoutItalic);
            p7.setAlignment(Element.ALIGN_CENTER);
        }else {
        	p=new Paragraph("VAT", heading_smallTableFontWithoutItalic);
            p.setAlignment(Element.ALIGN_CENTER);
            p2=new Paragraph(String.format(local, "%.2f", tvaPourcent * 100)+" %", contentTableFont);
            p3=new Paragraph("Base VAT", heading_smallTableFontWithoutItalic);
            p3.setAlignment(Element.ALIGN_CENTER);
            p4=new Paragraph(String.format(local, "%.2f", base)+devise, contentTableFont);
            p5=new Paragraph("Amount VAT", heading_smallTableFontWithoutItalic);
            p5.setAlignment(Element.ALIGN_CENTER);
            p6=new Paragraph(String.format(local, "%.2f", tva_montant)+devise, contentTableFont);
            p7=new Paragraph("Total INLC. TAXES", heading_smallTableFontWithoutItalic);
            p7.setAlignment(Element.ALIGN_CENTER);
        }
        p8=new Paragraph(String.format(local, "%.2f", order.getPriceTt())+devise, contentTableFont);        
        PdfPCell cell=new PdfPCell();
        cell.setColspan(2);
        PdfPCell cell2=new PdfPCell();
        cell2.addElement(p);
        cell2.addElement(p2);
        PdfPCell cell3=new PdfPCell();
        cell3.addElement(p3);
        cell3.addElement(p4);
        PdfPCell cell4=new PdfPCell();
        cell4.addElement(p5);
        cell4.addElement(p6);
        PdfPCell cell5=new PdfPCell();
        cell5.addElement(p7);
        cell5.addElement(p8);
        cell.setBorderWidthRight(0);
        cell2.setBorderWidthLeft(0);
        cell2.setBorderWidthRight(0);
        cell3.setBorderWidthLeft(0);
        cell3.setBorderWidthRight(0);
        cell4.setBorderWidthLeft(0);
        cell4.setBorderWidthRight(0);
        cell5.setBorderWidthLeft(0);
        table.addCell(cell);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.setWidthPercentage(100);
        content.add(table);
    }
}
