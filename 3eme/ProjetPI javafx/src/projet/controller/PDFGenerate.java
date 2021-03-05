/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projet.models.LigneDePanier;
import projet.models.Panier;
import projet.models.Produit;
import projet.models.Utilisateur;
import projet.service.PanierService;
import projet.service.ProduitService;

/**
 *
 * @author yassine
 */
public class PDFGenerate {
    
      
    private Panier panier;
    
    private ObservableList<LigneDePanier> observableList;
    private ProduitService service_produit = new ProduitService();
    private PanierService service_panier = new PanierService();
    
     public void generate(int id){
        
        try {
          
            Utilisateur user = WorldfriendshipController.recupererUtilisateurConnecte;
            getPanier();
            
            String file_name = "C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\Facture"+id+".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            

            Image facture = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\facture.png");
            facture.scaleToFit(200, 70);
            
            document.add(facture);
            document.add(new Paragraph("\n\n\n\n\n"));
            Image facture_a = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\facA.png");
            facture_a.scaleToFit(80, 50);
            
            Image facture_n = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\facN.png");
            facture_n.scaleToFit(80, 50);
            
            Image date = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\date.png");
            date.scaleToFit(80, 50);
            
            Image qte_img = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\qte.png");
            qte_img.scaleToFit(80, 50);
            Image produit_img = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\produit.png");
            produit_img.scaleToFit(80, 50);
            Image m_tot = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\mTot.png");
            m_tot.scaleToFit(80, 50);
            Image pun = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\pun.png");
            pun.scaleToFit(80, 50);
            
            Image total = Image.getInstance("C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\images\\total.png");
            total.scaleToFit(80, 50);
            
           
            PdfPTable table = new PdfPTable(3);
            
            table.setWidthPercentage(100);

            PdfPCell cell = new PdfPCell(facture_a);
            cell.setPadding(0);
            cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            cell.setBorder(PdfPCell.NO_BORDER); 
            table.addCell(cell);
            table.addCell(CenterCell());
                       
            table.addCell(RightCell(facture_n, String.valueOf(id)));
            
            table.addCell(LeftCell(user.getNom_Utilisateur()));
            table.addCell(CenterCell());
            
            LocalDate created = LocalDate.now();
            table.addCell(RightCell(date, String.valueOf(created)));
            
            table.addCell(LeftCell(user.getEmail()));
            table.addCell(CenterCell());
            cell = new PdfPCell();      
            cell.setBorder(PdfPCell.NO_BORDER); 
            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            table.addCell(cell);
          
            
           
            document.add(table);
            
            document.add(new Paragraph("\n\n\n\n"));

            table = new PdfPTable(4);
            table.setWidthPercentage(100);


            table.addCell(getCell(qte_img));
            table.addCell(getCell(produit_img));
            table.addCell(getCell(pun));
            table.addCell(getCell(m_tot));
            
            for (LigneDePanier ligne : observableList) {
                
                Produit produit = service_produit.getProduit(ligne.getProduit_id());

                Phrase p = new Phrase(String.valueOf(ligne.getQuantite()));
                cell = new PdfPCell(p);
                cell.setPadding(10);
                cell.setBorder(PdfPCell.NO_BORDER); 
                table.addCell(cell);
                
                p = new Phrase(produit.getLibelle());
                cell = new PdfPCell(p);
                cell.setPadding(7);
                cell.setBorder(PdfPCell.NO_BORDER); 
                table.addCell(cell);
                
                p = new Phrase(String.format("%.2f",produit.getPrix()));
                cell = new PdfPCell(p);
                cell.setPadding(7);
                cell.setBorder(PdfPCell.NO_BORDER); 
                table.addCell(cell);
                
                p = new Phrase(String.format("%.2f",produit.getPrix()*ligne.getQuantite()));
                cell = new PdfPCell(p);
                cell.setBorder(PdfPCell.NO_BORDER); 
                cell.setPadding(7);
                table.addCell(cell);
            }

            document.add(table);

            document.add(new Paragraph("\n\n\n"));
            table = new PdfPTable(3);
            table.setWidthPercentage(100);

            cell = new PdfPCell();   
            
            cell.setBorder(PdfPCell.NO_BORDER); 
            table.addCell(cell);
            table.addCell(cell);
            
            table.addCell(RightCell(total,String.format("%.2f",panier.getTotal())+" DNT"));
            document.add(table);
            
            document.close();
            
           
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CommanderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(CommanderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommanderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public PdfPCell LeftCell(String s) {
        Phrase p = new Phrase(s);
        PdfPCell cell = new PdfPCell(p);
        cell.setPadding(5);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell.setBorder(PdfPCell.NO_BORDER); 
        return cell;
    
    }
    
    public PdfPCell CenterCell() {
        PdfPCell cell = new PdfPCell();
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        cell.setBorder(PdfPCell.NO_BORDER); 
        return cell;
    
    }
    
    public PdfPCell RightCell(Image image,String s) {
        
        Phrase p = new Phrase(s);
        
        PdfPCell cell1 = new PdfPCell(p);
        cell1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell1.setBorder(PdfPCell.NO_BORDER); 
        PdfPCell cell2 = new PdfPCell(image);
        cell2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        cell2.setBorder(PdfPCell.NO_BORDER); 
        PdfPTable table = new PdfPTable(2);
                
        table.addCell(cell2);
        table.addCell(cell1);
        
        PdfPCell cell = new PdfPCell(table);

        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBorder(PdfPCell.NO_BORDER); 
        cell.setPadding(5);

        return cell;
    
    }
    
    public PdfPCell getCell(Image img) {
        PdfPCell cell = new PdfPCell(); 
        cell = new PdfPCell(img);
        cell.setPadding(10);
        cell.setBorder(PdfPCell.NO_BORDER); 

        return cell;
    
    }
    
    private void getPanier(){
        
        panier = service_panier.getPanier();
   
        observableList = FXCollections.observableArrayList(service_panier.getLignePanier(panier.getId()));
       
    }
}
