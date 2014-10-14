/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simphony.beans;

import com.simphony.interfases.IConfigurable;
import java.sql.Connection;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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

/**
 *
 * @author Leonor
 */
public class JasperService implements IConfigurable {

    private String reportName;
    private String path = _REPORT_PATH;

    public void JasperService() {

    }

    public void builtReport(String type) throws JRException, ClassNotFoundException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ex = fc.getExternalContext();

        JasperReport inputStream = (JasperReport) JRLoader.loadObjectFromLocation(this.path + this.reportName);
        if (inputStream == null) {
            throw new ClassNotFoundException("Error no file in:" + this.path + this.reportName);
        }

        try {
            JRExporter exporter = null;
            Connection ds = null;
            JasperPrint jp = JasperFillManager.fillReport(inputStream, null, ds);

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
}
