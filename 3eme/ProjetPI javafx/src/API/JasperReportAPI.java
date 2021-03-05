/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import projet.models.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import projet.service.ServiceEvenement;

/**
 *
 * @author solta
 */
public class JasperReportAPI {
  
    public static void jasperReportTsEvenement() {
        String path = "src\\projet\\files\\InfoTsEvenement.jrxml";
        try {
            JasperReport jr = JasperCompileManager.compileReport(path);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, ServiceEvenement.creationConnexion());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public static void jasperReportTsMesEvenement(int idUser) {
   
        String path = "src\\projet\\files\\InfoTsEvenement.jrxml";
        String sql = "select * from evenement where id_user ='" + idUser + "'";

        try {
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            net.sf.jasperreports.engine.JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, ServiceEvenement.creationConnexion());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
 public static void jasperReportEvenementSelectionner(int idEvenement) {

        String path = "src\\projet\\files\\InfoEvenement.jrxml";
        String sql = "select * from evenement where id ='" + idEvenement + "'";

        try {
            JasperDesign jd = JRXmlLoader.load(path);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            net.sf.jasperreports.engine.JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, ServiceEvenement.creationConnexion());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
