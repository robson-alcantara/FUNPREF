/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.report;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import funpref.controller.FUNPREFController;
import funpref.controller.LogController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author secretaria
 */
public class ReportBeneficiariesController {
    private FUNPREFController funprefController;

    ReportBeneficiariesController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
    }

    Document generateData(Document document) {
        ArrayList< ArrayList<Object>> data = null;
        Cell cell;
        PdfFont font;
        String text;
        
        try {
            font = PdfFontFactory.createFont(FontConstants.HELVETICA);
            
            data = funprefController.getBeneficiaryController().getReportBeneficiaryData(false,false);

            document.add( new Paragraph("Lista de beneficiários").setTextAlignment(TextAlignment.CENTER));
            document.add( new Paragraph(""));

            Table table = new Table(UnitValue.createPercentArray(new float[]{5,5,10,20,26,8,8,8})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            // header
            cell = new Cell();
            cell.add( new Paragraph( "matrícula" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);
            
            cell = new Cell();
            cell.add( new Paragraph( "portaria" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);          
            
            cell = new Cell();
            cell.add( new Paragraph( "cpf" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);            
            
            cell = new Cell();
            cell.add( new Paragraph( "nome" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);          
            
            cell = new Cell();
            cell.add( new Paragraph( "endereço" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);                     
            
            cell = new Cell();
            cell.add( new Paragraph( "fone 1" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);            
            
            cell = new Cell();
            cell.add( new Paragraph( "fone 2" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);   
            
            cell = new Cell();
            cell.add( new Paragraph( "situação do censo" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);             
            

            for( int i = 0; i < data.size(); i++ ) {
                for( int j = 0; j < data.get(i).size(); j++ ) {
                    text = "";
                    cell = new Cell();
                    
                    if( data.get(i).get(j) != null ) {
                        text = data.get(i).get(j).toString();                        
                    }
                    
                    cell.add( new Paragraph( text ).setFont(font).setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );            
                    table.addCell(cell);
                }
            } 
            
            document.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));  
            
            document.add( new Paragraph(""));
            document.add( new Paragraph(""));
            
        } catch (IOException ex) {
            Logger.getLogger(ReportBeneficiariesController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportBeneficiariesController.class.getName(), ex);
        }

        
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return document;
    }

    void setFunprefController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
    }
    
}
