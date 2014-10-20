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
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author Leonor
 */
@Component
public class JasperService implements IConfigurable {

    private String reportName;
    private String path = _REPORT_PATH;

    /**
     *
     */
    public EntityManagerFactory emf;
    
    
    @PersistenceUnit(unitName="simphony_pu")
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
        Session ses = (Session)em.getDelegate();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();
        try{
            connection = sessionFactory.getConnectionProvider().getConnection();
        }catch(SQLException e){
            System.out.println("" + e.getMessage());
        }
        
        //JasperReport inputStream = (JasperReport) JRLoader.loadObjectFromLocation(this.path + this.reportName);

        InputStream ticketReportStream = null;
        ticketReportStream =JRLoader.getFileInputStream(this.path + this.reportName);   
        
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
            
            if(exporter != null){
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
        Session ses = (Session)em.getDelegate();
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory();
        try{
            connection = sessionFactory.getConnectionProvider().getConnection();
        }catch(SQLException e){
            System.out.println("" + e.getMessage());
        }
        
        InputStream reportStream = null;
        reportStream =JRLoader.getFileInputStream(this.path + this.reportName);   
        
        if (reportStream == null) {
            throw new ClassNotFoundException("Error no file in:" + this.path + this.reportName);
        }

        JasperReport saleReport = (JasperReport) JRLoader.loadObject(reportStream);
        
        
        try {
            JRExporter exporter = null;
            JasperPrint jp = JasperFillManager.fillReport(saleReport,  parameters, connection);

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
            
            if(exporter != null){
                exporter.exportReport();
            }
        } catch (Exception exc) {
            System.out.println("Error desconocido:" + exc.getMessage());
        }
        
        fc.responseComplete();
        
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
    
    
}
