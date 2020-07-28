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
public class ReportDependentController {
    private FUNPREFController funprefController;
    
    public ReportDependentController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
    }

    Document generateData(Document document) {
        ArrayList< ArrayList<Object>> data;
        Cell cell;
        PdfFont font;
        String text;
        
        try {
            font = PdfFontFactory.createFont(FontConstants.HELVETICA);
            
            data = funprefController.getDependentController().getReportDependentData();

            document.add( new Paragraph("Lista de dependentes").setTextAlignment(TextAlignment.CENTER));
            document.add( new Paragraph(""));

            Table table = new Table(UnitValue.createPercentArray(new float[]{35,10,10,35,10})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            // header
            cell = new Cell();
            cell.add( new Paragraph( "nome do dependente" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);
            
            cell = new Cell();
            cell.add( new Paragraph( "cpf" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);          
            
            cell = new Cell();
            cell.add( new Paragraph( "fone" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);            
            
            cell = new Cell();
            cell.add( new Paragraph( "nome do beneficiario" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            table.addCell(cell);          
            
            cell = new Cell();
            cell.add( new Paragraph( "parentesco" ).setFont(font).setBold().setFontSize(6).setVerticalAlignment(VerticalAlignment.MIDDLE) );
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
            
        } catch (IOException ex) {
            Logger.getLogger(ReportDependentController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportDependentController.class.getName(), ex);
        }

        return document;
    }

    void setFunprefController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
    }
    
}
