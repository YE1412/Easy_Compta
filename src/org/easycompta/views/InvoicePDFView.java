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
        SimpleDateFormat sdfr = null;
        if (idLangue == 0) {
        	sdfr = new SimpleDateFormat("dd.MM.yyyy");
        }
        else {
        	return;
        }
        Paragraph p=new Paragraph("Adresse de facturation :", headingTableFont);
        Paragraph p2=new Paragraph(buyer.getPrenom()+" "+buyer.getNom(), contentTableFont);
        Paragraph p3=new Paragraph(buyer.getNumRue()+ " " +buyer.getNomRue(), contentTableFont);
        Paragraph p4=new Paragraph("F-"+buyer.getCp()+" "+buyer.getVille(), contentTableFont);
        addEmptyLine(p4, 1);
        addEmptyLine(p4, 1);
        addEmptyLine(p4, 1);
        Paragraph p5=new Paragraph("Contenu supplémentaire :", heading_smallTableFont);
        p5.setAlignment(Element.ALIGN_CENTER);
        Paragraph p6=new Paragraph(order.getContenuAdditionnel(), contentTableFont);
        p6.setIndentationLeft(5);
        Paragraph p7=new Paragraph("Payeur :", heading_smallTableFont);
        p7.setAlignment(Element.ALIGN_CENTER);
        Paragraph p8=new Paragraph(buyer.getId().toString(), contentTableFont);
        p8.setIndentationLeft(5);
        Paragraph p9=new Paragraph("Client :", heading_smallTableFont);
        p9.setAlignment(Element.ALIGN_CENTER);
        Paragraph p10=new Paragraph(buyer.getId().toString(), contentTableFont);
        p10.setIndentationLeft(5);
        Paragraph p11=new Paragraph("Date :", heading_smallTableFont);
        p11.setAlignment(Element.ALIGN_CENTER);
        Paragraph p12=new Paragraph(sdfr.format(invoice.getDate()), contentTableFont);
        p12.setIndentationLeft(5);
        Phrase ph1=new Phrase("NÂ° ", heading_smallTableFont);
        Phrase ph2=new Phrase(order.getId().toString(), subjectFont);
        Paragraph p13=new Paragraph();
        p13.add(ph1);
        p13.add(ph2);
        
        Paragraph p15=new Paragraph("DESIGNATION", heading_smallTableFontWithoutItalic);
        addEmptyLine(p15, 1);
        Paragraph p16=new Paragraph("NUM SERVICE", heading_smallTableFontWithoutItalic);
        addEmptyLine(p16, 1);
        Paragraph p17=new Paragraph("TARIF TT", heading_smallTableFontWithoutItalic);
        addEmptyLine(p17, 1);
        Paragraph p18=new Paragraph("QUANTITE", heading_smallTableFontWithoutItalic);
        addEmptyLine(p18, 1);
        Paragraph p19=new Paragraph("PRIX NET TT", heading_smallTableFontWithoutItalic);
        addEmptyLine(p19, 1);
        Paragraph p20=new Paragraph("MONTANT TT", heading_smallTableFontWithoutItalic);
        addEmptyLine(p20, 1);
        Paragraph p21=new Paragraph("Total", heading_smallTableFontWithoutItalic);
        p21.setAlignment(Element.ALIGN_LEFT);
        Paragraph p22=new Paragraph(String.format("%.2f", order.getPriceTt()), heading_smallTableFontWithoutItalic);
        p22.setAlignment(Element.ALIGN_RIGHT);
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
        insert_services(table);
        table.addCell(cell13);
        table.addCell(cell14);
        table.setWidthPercentage(100);
        
        head_company= new Paragraph("INFOSERVICES", subFont);
        head_title= new Paragraph("FACTURE", subjectFont);
        head_title.setAlignment(Element.ALIGN_LEFT);
        head_title.setIndentationLeft(50);
        head.add(head_company);
        head.add(head_title);
        addEmptyLine(head, 1);
        addEmptyLine(head, 1);
        
        content.add(head);
        content.add(table);
        addEmptyLine(content, 1);
        insert_end_table(content);
        dcmnt.add(content);
    }
    
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    private void insert_services(PdfPTable table){
        for (ProduitService s:servicesList){
            float tu_tt = s.getMontantHt() + (s.getMontantHt() * tva.getPourcentage());
            float tt_net = tu_tt * s.getQuantite();
            Paragraph p=new Paragraph(s.getNom(), content_smallTableFontWithoutItalic);
            Paragraph p2=new Paragraph(s.getId().toString(), content_smallTableFontWithoutItalic);
            Paragraph p3=new Paragraph(String.format("%.2f", tu_tt), content_smallTableFontWithoutItalic);
            Paragraph p4=new Paragraph(String.valueOf(s.getQuantite()), content_smallTableFontWithoutItalic);
            Paragraph p5=new Paragraph(String.format("%.2f", tt_net), content_smallTableFontWithoutItalic);
            Paragraph p6=new Paragraph(String.format("%.2f", tt_net), content_smallTableFontWithoutItalic);
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
    
    private void insert_end_table(Paragraph content)
    {
        float tvaPourcent = tva.getPourcentage();
        float tva_montant = order.getPriceTt() * tvaPourcent;
        float base = order.getPriceTt() - tva_montant;
        PdfPTable table=new PdfPTable(6);
        Paragraph p=new Paragraph("TVA", heading_smallTableFontWithoutItalic);
        p.setAlignment(Element.ALIGN_CENTER);
        Paragraph p2=new Paragraph(String.format("%.2f", tvaPourcent * 100)+" %", contentTableFont);
        Paragraph p3=new Paragraph("Base TVA", heading_smallTableFontWithoutItalic);
        p3.setAlignment(Element.ALIGN_CENTER);
        Paragraph p4=new Paragraph(String.format("%.2f", base), contentTableFont);
        Paragraph p5=new Paragraph("Montant TVA", heading_smallTableFontWithoutItalic);
        p5.setAlignment(Element.ALIGN_CENTER);
        Paragraph p6=new Paragraph(String.format("%.2f", tva_montant), contentTableFont);
        Paragraph p7=new Paragraph("Total TTC", heading_smallTableFontWithoutItalic);
        p7.setAlignment(Element.ALIGN_CENTER);
        Paragraph p8=new Paragraph(String.format("%.2f", order.getPriceTt()), contentTableFont);
        
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
