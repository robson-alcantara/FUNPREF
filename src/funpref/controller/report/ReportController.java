/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller.report;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import funpref.controller.FUNPREFController;
import funpref.controller.LogController;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author secretaria
 */
public class ReportController {
      
    private FUNPREFController funprefController;
    private Document document;
    private ReportBeneficiariesController reportBeneficiariesController;
    private ReportBeneficiariesPendingController reportBeneficiariesPendingController;
    private ReportBeneficiariesDeceasedController reportBeneficiariesDeceasedController;
    private ReportDependentController reportDependentController;
    private ReportBeneficiaryController reportBeneficiaryController;
    private String outputFilePath;
    private PdfDocument pdfDocument;

    public ReportController(FUNPREFController funprefController ) {
        this.funprefController = funprefController;        
        reportBeneficiaryController = new ReportBeneficiaryController( funprefController, this );
        reportBeneficiariesController = new ReportBeneficiariesController(funprefController);
        reportBeneficiariesPendingController = new ReportBeneficiariesPendingController(funprefController);
        reportBeneficiariesDeceasedController = new ReportBeneficiariesDeceasedController(funprefController);
        reportDependentController = new ReportDependentController(funprefController);
    }    
    
    public void generateReportBeneficiary( boolean printRegisterDate ) {
        try {        
            generateFileData();
            generateHeader();
            document = reportBeneficiaryController.generateData(document, printRegisterDate);
            document.close(); 

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportController.class.getName(), ex);
        }
    }    

    public void generateReportBeneficiaries() {
        try {        
            generateFileData(PageSize.A4.rotate());
            generateHeader();
            document = reportBeneficiariesController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportController.class.getName(), ex);
        }
    }
    
    public void generateReportBeneficiariesPendings() {
        try {        
            generateFileData(PageSize.A4.rotate());
            generateHeader();
            document = reportBeneficiariesPendingController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportController.class.getName(), ex);
        }
    }

    public void generateReportBeneficiariesDeceased() {
        try {        
            generateFileData(PageSize.A4.rotate());
            generateHeader();
            document = reportBeneficiariesDeceasedController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportController.class.getName(), ex);
        }
    }
    
    public void generateReportDependents() {
        try {        
            generateFileData(PageSize.A4.rotate());
            generateHeader();
            document = reportDependentController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportController.class.getName(), ex);
        }
    }    
    
    private void generateFileData() {        
        generateFileData(PageSize.A4);        
    }

    private void generateFileData(PageSize pageSize) {            
        try {                    
            PdfWriter writer;
            outputFilePath = "report.pdf";

            writer = new PdfWriter(outputFilePath);

            writer.setCompressionLevel(9);        
            pdfDocument = new PdfDocument(writer);                        
            pdfDocument.setDefaultPageSize(pageSize);
            document = new Document(pdfDocument);
            document.setMargins(110.0f, 25.0f, 25.0f, 25.0f);            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);        
            LogController.reportException(ReportController.class.getName(), ex);
        }       
            
    }


    private void generateHeader() {
        TableHeaderEventHandler handler = new TableHeaderEventHandler(document);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
    }

    private void generateBottom() {
        try {   
            
            document.add( new Paragraph(""));
            document.add( new Paragraph(""));            
            document.add( new Paragraph(""));
            
            int defaultFontSize = 9;
            int smallFontSize = 7;
            Table table = new Table(UnitValue.createPercentArray(new float[]{100})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));            
            Cell aCell;
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
            
            aCell = new Cell();
            aCell.setBorder(Border.NO_BORDER);
            aCell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );  
            aCell.add( new Paragraph( funprefController.getUser().getName() ).setFont(font).setFontSize(defaultFontSize) );  
            aCell.add( new Paragraph( funprefController.getUser().getOffice() ).setFont(font).setFontSize(smallFontSize) );              
            aCell.setTextAlignment(TextAlignment.CENTER);
            table.addCell(aCell);
           
            
            document.add(table.setHorizontalAlignment(HorizontalAlignment.CENTER));
            
            document.close(); 
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            LogController.reportException(ReportController.class.getName(), ex);
        }            
    }
    
    public String printBenefitTypeText(int idBenefitType) {
        String benefityType = "";
        
        if( ( idBenefitType == 1 ) || ( idBenefitType == 3 ) || ( idBenefitType == 4 ) || ( idBenefitType == 5 ) || ( idBenefitType == 6 ) ) {
            benefityType = "Servidor Inativo";
        }
        
        else if ( idBenefitType == 2 )  {
            benefityType = "Pensionista";
        }
        
        else if ( idBenefitType == 7 )  {
            benefityType = "Pensionista (temporário)";
        }        
        
        return benefityType;
    }

private static class TableHeaderEventHandler implements IEventHandler {
        private Table table;
        private float tableHeight;
        private Document doc;

        public TableHeaderEventHandler(Document doc) {
            this.doc = doc;
            initTable();

            TableRenderer renderer = (TableRenderer) table.createRendererSubTree();
            renderer.setParent(new DocumentRenderer(doc));

            // Simulate the positioning of the renderer to find out how much space the header table will occupy.
            LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4)));
            tableHeight = result.getOccupiedArea().getBBox().getHeight();
        }

        @Override
        public void handleEvent(Event currentEvent) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            float coordX = pageSize.getX() + doc.getLeftMargin() + 20;
            float coordY = pageSize.getTop() - doc.getTopMargin() + 10;
            float width = pageSize.getWidth() - doc.getRightMargin() - doc.getLeftMargin();
            float height = getTableHeight();
            Rectangle rect = new Rectangle(coordX, coordY, width, height);

            new Canvas(canvas, pdfDoc, rect)
                    .add(table)
                    .close();
        }

        public float getTableHeight() {
            return tableHeight;
        }
        
        public void initTable() {
            try {
                int defaultFontSize = 9;            ;                            
                PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);

                table = new Table(UnitValue.createPercentArray(new float[]{35,45,20})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
                Image blazon = null;
                Cell aCell;
                try {
                    blazon = new Image( ImageDataFactory.create("./src/resources/about_icon.png"));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    LogController.reportException(ReportController.class.getName(), ex);
                }

                aCell = new Cell();
                aCell.add( new Paragraph( "CENSO PREVIDENCIÁRIO" ).setFont(font).setFontSize(16) ).setVerticalAlignment(VerticalAlignment.MIDDLE);              
                aCell.setBorder(Border.NO_BORDER);
        //            cell2.setBorderTop(new SolidBorder(0));
        //            cell2.setBorderBottom(new SolidBorder(0));        
                table.addCell(aCell);            

                aCell = new Cell();
                aCell.add( new Paragraph( "FUNDO PREVIDENCIÁRIO DO MUNICÍPIO DE FLORES-PE" ).setFont(font).setFontSize(defaultFontSize) );
                aCell.add( new Paragraph( "05.699.773/0001-21" ).setFont(font).setFontSize(defaultFontSize).setFontColor(Color.GRAY) );
                aCell.setTextAlignment(TextAlignment.RIGHT);
                aCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                aCell.setBorder(Border.NO_BORDER);
        //            cell2.setBorderTop(new SolidBorder(0));
        //            cell2.setBorderBottom(new SolidBorder(0));        
                table.addCell(aCell);                        


                blazon.scale(0.5f, 0.5f);
                aCell = new Cell();
                aCell.add(blazon);
                aCell.setBorder(Border.NO_BORDER);
        //            aCell.setBorderTop(new SolidBorder(0));
        //            aCell.setBorderBottom(new SolidBorder(0));
        //            aCell.setBorderLeft(new SolidBorder(0));
                table.addCell(aCell);   
            } catch (IOException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                LogController.reportException(ReportController.class.getName(), ex);
            }            
        }
    }
    
}
