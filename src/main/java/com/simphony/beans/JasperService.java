/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.beans;

import com.simphony.interfases.IConfigurable;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.base.JRBasePrintLine;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author Leonor
 */
@Component
public class JasperService implements IConfigurable {

    private JasperPrint jasperPrint;
    private String reportName;
    private String path = _REPORT_PATH;
    private String printerName = "";

    /**
     *
     */
    public EntityManagerFactory emf;

    @PersistenceUnit(unitName = "simphony_pu")
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void JasperService() {

    }

    public void builtReport(String type) throws JRException, ClassNotFoundException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ex = fc.getExternalContext();
        Connection connection = null;

        EntityManager em = emf.createEntityManager();
        Session ses = (Session) em.getDelegate();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();
        try {
            connection = sessionFactory.getConnectionProvider().getConnection();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }

        //JasperReport inputStream = (JasperReport) JRLoader.loadObjectFromLocation(this.path + this.reportName);
        InputStream ticketReportStream = null;
        ticketReportStream = JRLoader.getFileInputStream(this.path + this.reportName);

        if (ticketReportStream == null) {
            throw new ClassNotFoundException("Error no file in:" + this.path + this.reportName);
        }

        JasperReport saleReport = (JasperReport) JRLoader.loadObject(ticketReportStream);

        try {
            JRExporter exporter = null;
            JasperPrint jp = JasperFillManager.fillReport(saleReport, null, connection);

            HttpServletResponse response = (HttpServletResponse) ex.getResponse();
            HttpServletRequest request = (HttpServletRequest) ex.getRequest();

            if (type.trim().equals(_REPORT_PDF)) {
                exporter = new JRPdfExporter();

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            } else if (type.trim().equals(_REPORT_HTML)) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + "/image?image");
            }

            if (exporter != null) {
                exporter.exportReport();
            }
        } catch (Exception exc) {
            System.out.println("Error desconocido:" + exc.getMessage());
        }

        fc.responseComplete();
    }

    public void builtReport(Map<String, Object> parameters, String type) throws JRException, ClassNotFoundException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ex = fc.getExternalContext();
        Connection connection = null;

        EntityManager em = emf.createEntityManager();
        Session ses = (Session) em.getDelegate();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();
        try {
            connection = sessionFactory.getConnectionProvider().getConnection();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }

        InputStream reportStream = null;
        reportStream = JRLoader.getFileInputStream(this.path + this.reportName);

        if (reportStream == null) {
            throw new ClassNotFoundException("Error no file in:" + this.path + this.reportName);
        }

        JasperReport saleReport = (JasperReport) JRLoader.loadObject(reportStream);

        try {
            JRExporter exporter = null;
            JasperPrint jp = JasperFillManager.fillReport(saleReport, parameters, connection);

            HttpServletResponse response = (HttpServletResponse) ex.getResponse();
            HttpServletRequest request = (HttpServletRequest) ex.getRequest();

            if (type.trim().equals(_REPORT_PDF)) {
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            } else if (type.trim().equals(_REPORT_HTML)) {
                exporter = new JRHtmlExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + "/image?image");
            }

            if (exporter != null) {
                exporter.exportReport();
            }
        } catch (Exception exc) {
            System.out.println("Error desconocido:" + exc.getMessage());
        }

        fc.responseComplete();

    }

    public void fillTicket() throws JRException {
        long start = System.currentTimeMillis();
        this.jasperPrint = getJasperTicket();
        JRSaver.saveObject(this.jasperPrint, this.path + "PrintServiceReport.jrprint");
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
    }

    public JasperPrint getJasperTicket() throws JRException {
        //JasperPrint
        JasperPrint jasperPrintL = new JasperPrint();
        jasperPrintL.setName("Ticket");
        jasperPrintL.setPageWidth(595);
        jasperPrintL.setPageHeight(842);

        //Fonts
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Sans_Normal");
        normalStyle.setDefault(true);
        //normalStyle.setFontName("DejaVu Sans");
        normalStyle.setFontSize(8.0F);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        normalStyle.setPdfEmbedded(false);
        jasperPrintL.addStyle(normalStyle);

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("Sans_Bold");
        //boldStyle.setFontName("DejaVu Sans");
        boldStyle.setFontSize(8.0F);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        boldStyle.setPdfEmbedded(false);
        jasperPrintL.addStyle(boldStyle);

        JRDesignStyle italicStyle = new JRDesignStyle();
        italicStyle.setName("Sans_Italic");
        //italicStyle.setFontName("DejaVu Sans");
        italicStyle.setFontSize(8.0F);
        italicStyle.setItalic(true);
        italicStyle.setPdfFontName("Helvetica-Oblique");
        italicStyle.setPdfEncoding("Cp1252");
        italicStyle.setPdfEmbedded(false);
        jasperPrintL.addStyle(italicStyle);

        JRPrintPage page = new JRBasePrintPage();

        
        JRPrintText text = new JRBasePrintText(jasperPrintL.getDefaultStyleProvider());
        //text = new JRBasePrintText(jasperPrintL.getDefaultStyleProvider());
        text.setX(100);
        text.setY(30);
        text.setWidth(515);
        text.setHeight(200);
        text.setTextHeight(text.getHeight());
        text.setHorizontalAlignment(HorizontalAlignEnum.JUSTIFIED);
        text.setLineSpacingFactor(1.329241f);
        text.setLeadingOffset(-4.076172f);
        text.setStyle(normalStyle);
        text.setFontSize(8.0F);
        text.setText("ORIGEN: ");
        page.addElement(text);

        text = new JRBasePrintText(jasperPrintL.getDefaultStyleProvider());
        text.setX(100);
        text.setY(50);
        text.setWidth(515);
        text.setHeight(200);
        text.setTextHeight(text.getHeight());
        text.setHorizontalAlignment(HorizontalAlignEnum.JUSTIFIED);
        text.setLineSpacingFactor(1.329241f);
        text.setLeadingOffset(-4.076172f);
        text.setStyle(normalStyle);
        text.setFontSize(8.0F);
        text.setText("DESTINO: ");
        page.addElement(text);
        jasperPrintL.addPage(page);
        return jasperPrintL;
    }

    /**
     * Imprimimos el reporte
     *
     * @param parameters
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.lang.ClassNotFoundException
     */
    public void printTicket(Map<String, Object> parameters) throws JRException, ClassNotFoundException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ex = fc.getExternalContext();
        Connection connection = null;

        EntityManager em = emf.createEntityManager();
        Session ses = (Session) em.getDelegate();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();
        try {
            connection = sessionFactory.getConnectionProvider().getConnection();
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSizeName.ISO_A4);

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(this.printerName, null));

        InputStream reportStream = null;
        reportStream = JRLoader.getFileInputStream(this.path + _REPORT_FOR_TICKET);

        if (reportStream == null) {
            throw new ClassNotFoundException("Error no file in:" + this.path + _REPORT_FOR_TICKET);
        }

        JasperReport jp = (JasperReport) JRLoader.loadObject(reportStream);

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        this.jasperPrint = JasperFillManager.fillReport(jp, parameters, connection);

        this.jasperPrint.setPageWidth(200);
         SimpleExporterInput sei = new SimpleExporterInput(this.jasperPrint);
         exporter.setExporterInput(sei);

         SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();         
         configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
         configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
         configuration.setDisplayPageDialog(false);
         configuration.setDisplayPrintDialog(false);
         exporter.setConfiguration(configuration);
         exporter.exportReport();
        
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
    
    

}
