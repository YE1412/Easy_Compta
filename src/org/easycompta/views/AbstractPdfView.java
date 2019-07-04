/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.easycompta.views;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 *
 * @author Yannick
 */
public abstract class AbstractPdfView extends AbstractView{

    public AbstractPdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }
    
    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ByteArrayOutputStream baos = createTemporaryOutputStream();
        
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        prepareWriter(map, writer, hsr);
        buildPdfMetadata(map, document, hsr);
        
        document.open();
        buildPdfDocument(map, document, writer, hsr, hsr1);
        document.close();
        
        writeToResponse(hsr1, baos);
    }
    
    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest req) throws DocumentException
    {
        writer.setViewerPreferences(getViewerPreferences());
    }

    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }
    
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest req){
        document.addTitle("Facture PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("An Author");
        document.addCreator("An Creator");
    }
    
    protected abstract void buildPdfDocument(Map<String, Object> map,
            Document document,
            PdfWriter writer,
            HttpServletRequest req,
            HttpServletResponse rep) throws Exception;
    
}
