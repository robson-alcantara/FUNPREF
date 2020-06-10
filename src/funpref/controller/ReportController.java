/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.controller;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.TrueTypeFont;
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
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.TableRenderer;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import funpref.model.Beneficiary;
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
    
    private final SimpleDateFormat formatDate;    
    private FUNPREFController funprefController;
    private Document document;
    private ReportBeneficiaryController reportBeneficiaryController;
    private ReportBeneficiaryPendingController reportBeneficiaryPendingController;
    private ReportBeneficiaryDeceasedController reportBeneficiaryDeceasedController;
    private ReportDependentController reportDependentController;
    private String outputFilePath;
    private PdfDocument pdfDocument;
    
    public ReportController() {
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
        reportBeneficiaryController = new ReportBeneficiaryController();
        reportDependentController = new ReportDependentController();
        reportBeneficiaryPendingController = new ReportBeneficiaryPendingController();
        reportBeneficiaryDeceasedController = new ReportBeneficiaryDeceasedController();
    }
    
    public void printBeneficiaryCensusVoucher(Beneficiary beneficiary, boolean printRegisterDate ) {
        try {        
            Document document;
            PdfDocument pdfDocument;
            PdfWriter writer;
            String outputFilePath = "report.pdf";
            
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);

            writer = new PdfWriter(outputFilePath);

            writer.setCompressionLevel(9);        
            pdfDocument = new PdfDocument(writer);            
            //pdfDocument.set
            //pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
            document = new Document(pdfDocument);
            document.setMargins(10.0f, 10.0f, 10.0f, 10.0f);
            
            int defaultFontSize = 9;
            int smallFontSize = 7;

            Table tableTop = new Table(UnitValue.createPercentArray(new float[]{35,45,20})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            Image blazon = null;
            Cell aCell;
            try {
                blazon = new Image( ImageDataFactory.create("./src/resources/about_icon.png"));
            } catch (MalformedURLException ex) {
                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            aCell = new Cell();
            aCell.add( new Paragraph( "CENSO PREVIDENCIÁRIO" ).setFont(font).setFontSize(16) ).setVerticalAlignment(VerticalAlignment.MIDDLE);              
            aCell.setBorder(Border.NO_BORDER);
//            cell2.setBorderTop(new SolidBorder(0));
//            cell2.setBorderBottom(new SolidBorder(0));        
            tableTop.addCell(aCell);            
            
            aCell = new Cell();
            aCell.add( new Paragraph( "FUNDO PREVIDENCIÁRIO DO MUNICÍPIO DE FLORES-PE" ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "05.699.773/0001-21" ).setFont(font).setFontSize(defaultFontSize).setFontColor(Color.GRAY) );
            aCell.setTextAlignment(TextAlignment.RIGHT);
            aCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            aCell.setBorder(Border.NO_BORDER);
//            cell2.setBorderTop(new SolidBorder(0));
//            cell2.setBorderBottom(new SolidBorder(0));        
            tableTop.addCell(aCell);                        
            
            
            blazon.scale(0.5f, 0.5f);
            aCell = new Cell();
            aCell.add(blazon);
            aCell.setBorder(Border.NO_BORDER);
//            aCell.setBorderTop(new SolidBorder(0));
//            aCell.setBorderBottom(new SolidBorder(0));
//            aCell.setBorderLeft(new SolidBorder(0));
            tableTop.addCell(aCell);
            

//            Cell cell3 = new Cell();
//            cell3.add("BOLETIM ESCOLAR");
//            cell3.add("Ano letivo: 2019");
//            cell3.setBorder(Border.NO_BORDER);
//            cell3.setBorderTop(new SolidBorder(0));
//            cell3.setBorderBottom(new SolidBorder(0));         
//            cell3.setBorderRight(new SolidBorder(0));
//            tableTop.addCell(cell3); 
            document.add(tableTop.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table2 = new Table(UnitValue.createPercentArray(new float[]{80,20})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            aCell = new Cell();
            aCell.add( new Paragraph( beneficiary.getName() ).setFont(font).setFontSize(10).setVerticalAlignment(VerticalAlignment.MIDDLE) );            
            aCell.add( new Paragraph( printBenefitTypeText( beneficiary.getIdBenefitType() ) ).setFont(font).setFontSize(10).setVerticalAlignment(VerticalAlignment.MIDDLE) );            
            aCell.setBorder(Border.NO_BORDER);
//            cell2.setBorderTop(new SolidBorder(0));
//            cell2.setBorderBottom(new SolidBorder(0));        
            table2.addCell(aCell);     
            
            aCell = new Cell();
            aCell.add( new Paragraph( "CPF" ).setFont(font).setFontSize(smallFontSize) );
            aCell.add( new Paragraph( beneficiary.getCpf() ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
            aCell.add( new Paragraph( "CONCESSÃO DO BENEFÍCIO" ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
            aCell.add( new Paragraph( formatDate.format(beneficiary.getInactivationDate()) ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );            
            
            if ( printRegisterDate && ( beneficiary.getRegisterDate() != null ) ) {
                aCell.add( new Paragraph( "DATA DO RECENSEAMENTO" ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
                aCell.add( new Paragraph( formatDate.format(beneficiary.getRegisterDate()) ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );                                            
            }
            
            aCell.setTextAlignment(TextAlignment.CENTER);
            aCell.setBorder(Border.NO_BORDER);
            table2.setBorderTop( new SolidBorder(0.3f) );
            table2.setBorderBottom(new SolidBorder(0.3f) );       
            table2.addCell(aCell);             
            
            
       
            document.add(table2.setHorizontalAlignment(HorizontalAlignment.CENTER));
            
            Table table3 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            aCell = new Cell();
            
            
            aCell.add( new Paragraph( "Informações da Ativa" ).setFont(font).setFontSize(smallFontSize) );
            aCell.add( new Paragraph( "Data de admissão: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "Cargo: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "Tempo anterior: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.setBorder(Border.NO_BORDER);
            table3.addCell(aCell);             
            
            aCell = new Cell();
            
            if( ( beneficiary.getIdBenefitType() != 2 ) && ( beneficiary.getIdBenefitType() != 7 ) ) {
                aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );            
                aCell.add( new Paragraph( formatDate.format( beneficiary.getAdmissionDate() ) ).setFont(font).setFontSize(defaultFontSize) );
                aCell.add( new Paragraph( beneficiary.getOffice() ).setFont(font).setFontSize(defaultFontSize) );
                aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );            
            }
            
            aCell.setBorder(Border.NO_BORDER);            
            table3.addCell(aCell); 
            
            table3.setBorderTop( new SolidBorder(0.3f) );
            table3.setBorderBottom(new SolidBorder(0.3f) );
            
            document.add(table3.setHorizontalAlignment(HorizontalAlignment.CENTER));
            
            Table table4 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            aCell = new Cell();
            aCell.add( new Paragraph( "Dados Pessoais" ).setFont(font).setFontSize(smallFontSize) );
            aCell.add( new Paragraph( "Data de nascimento: " ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( "Naturalidade: " ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( "Mãe: " ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( "Pai: " ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( "Grau de instrução: " ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( "Estado civil: " ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( "Estado físico: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.setBorder(Border.NO_BORDER);
            table4.addCell(aCell);             
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );            
            aCell.add( new Paragraph( formatDate.format( beneficiary.getBirthDate() ) ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "" + funprefController.decodeCity(beneficiary.getIdCityPlaceOfBirth(),beneficiary.getIdProvincePlaceOfBirth()) +
                    "-" + funprefController.decodeProvince(beneficiary.getIdProvincePlaceOfBirth()) +
                    ", " + beneficiary.getNationality() ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( beneficiary.getMotherName() ).setFont(font).setFontSize(defaultFontSize) ); 
            aCell.add( new Paragraph( beneficiary.getFatherName() ).setFont(font).setFontSize(defaultFontSize) ); 
            aCell.add( new Paragraph( "" + funprefController.decodeDegreeEducation(beneficiary.getIdDegreeOfEducation()) ).setFont(font).setFontSize(defaultFontSize) ); 
            aCell.add( new Paragraph( "" + funprefController.decodeMaritalStatus(beneficiary.getIdMaritalStatus()) ).setFont(font).setFontSize(defaultFontSize) ); 
            aCell.add( new Paragraph( "" + funprefController.decodeDeficiency(beneficiary.getIdDeficiency()) ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.setBorder(Border.NO_BORDER);
            table4.addCell(aCell);  
            
            table4.setBorderTop( new SolidBorder(0.3f) );
            table4.setBorderBottom(new SolidBorder(0.3f) );            
            
            document.add(table4.setHorizontalAlignment(HorizontalAlignment.CENTER));            
            
            Table table5 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            aCell = new Cell();
            aCell.add( new Paragraph( "Documentos" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "Identidade: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "Título de eleitor: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.setBorder(Border.NO_BORDER);
            table5.addCell(aCell);             
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( beneficiary.getRg() + " " +
                    funprefController.decodeRgIssuingBody(beneficiary.getIdRgIssuingBody()) +
                    "-" + funprefController.decodeProvince(beneficiary.getIdProvinceRg()) +
                    " emitido em " + ( (beneficiary.getRgEmissionDate() != null)?formatDate.format(beneficiary.getRgEmissionDate()):"") ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "" + beneficiary.getVotersTitle() + " Zona: " + beneficiary.getElectoralZone() +
                    " Seção: " + beneficiary.getElectoralSection() +
                    " UF: " + funprefController.decodeProvince(beneficiary.getIdProvinceElectoralZone()) ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.setBorder(Border.NO_BORDER);
            table5.addCell(aCell);  
            
            table5.setBorderTop( new SolidBorder(0.3f) );
            table5.setBorderBottom(new SolidBorder(0.3f) );            

            document.add(table5.setHorizontalAlignment(HorizontalAlignment.CENTER));         
            
            Table table6 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            aCell = new Cell();
            aCell.add( new Paragraph( "Contatos" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "Endereço: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "Telefone: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.add( new Paragraph( "e-mail: " ).setFont(font).setFontSize(defaultFontSize) );
            aCell.setBorder(Border.NO_BORDER);
            table6.addCell(aCell);             
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( beneficiary.getAddress() + ", " +
                    funprefController.decodeCity( beneficiary.getIdCityAddress(), beneficiary.getIdProvinceAddress() ) + "-" +
                    funprefController.decodeProvince( beneficiary.getIdProvinceAddress() ) ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( ((beneficiary.getPhone1() != null)?beneficiary.getPhone1():"") + " " +
                    ((beneficiary.getPhone2() != null)?beneficiary.getPhone2():"") ).setFont(font).setFontSize(defaultFontSize) );            
            aCell.add( new Paragraph( ((beneficiary.getEmail() != null)?beneficiary.getEmail():"") ).setFont(font).setFontSize(defaultFontSize) );                        
            aCell.setBorder(Border.NO_BORDER);
            table6.addCell(aCell); 
            
            table6.setBorderTop( new SolidBorder(0.3f) );
            table6.setBorderBottom(new SolidBorder(0.3f) );            

            document.add(table6.setHorizontalAlignment(HorizontalAlignment.CENTER));              
            
            Table table7 = new Table(UnitValue.createPercentArray(new float[]{12,40,12,18,18})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            
            aCell = new Cell();
            aCell.add( new Paragraph( "Dependentes" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "CPF" ).setFont(font).setFontSize(smallFontSize) );            
            
            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                aCell.add( new Paragraph( beneficiary.getDependents().get(i).getCpf() ).setFont(font).setFontSize(defaultFontSize) );            
            }
            
            aCell.setTextAlignment(TextAlignment.CENTER);
            aCell.setBorder(Border.NO_BORDER);
            table7.addCell(aCell);             
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "NOME" ).setFont(font).setFontSize(smallFontSize) );                        
            
            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                aCell.add( new Paragraph( beneficiary.getDependents().get(i).getName()).setFont(font).setFontSize(defaultFontSize) );
            }
            
            aCell.setTextAlignment(TextAlignment.CENTER);
            aCell.setBorder(Border.NO_BORDER);
            table7.addCell(aCell);                         
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "NASCIMENTO" ).setFont(font).setFontSize(smallFontSize) );                                    
            
            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                aCell.add( new Paragraph( formatDate.format(beneficiary.getDependents().get(i).getBirthDate()) ).setFont(font).setFontSize(defaultFontSize) );
            }
            
            aCell.setTextAlignment(TextAlignment.CENTER);
            aCell.setBorder(Border.NO_BORDER);
            table7.addCell(aCell);                                     
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "PARENTESCO" ).setFont(font).setFontSize(smallFontSize) );                                                
            
            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                aCell.add( new Paragraph( "" + funprefController.decodeKinship(beneficiary.getDependents().get(i).getIdKinship() ) ).setFont(font).setFontSize(defaultFontSize) );
            }
            
            aCell.setTextAlignment(TextAlignment.CENTER);
            aCell.setBorder(Border.NO_BORDER);
            table7.addCell(aCell); 
            
            
            aCell = new Cell();
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "DEFICIÊNCIA" ).setFont(font).setFontSize(smallFontSize) );                                                            
            
            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                aCell.add( new Paragraph( "" + funprefController.decodeDeficiency(beneficiary.getDependents().get(i).getIdDeficiency() ) ).setFont(font).setFontSize(defaultFontSize) );
            }
            
            aCell.setTextAlignment(TextAlignment.CENTER);
            aCell.setBorder(Border.NO_BORDER);
            table7.addCell(aCell);  
            
            table7.setBorderTop( new SolidBorder(0.3f) );
            table7.setBorderBottom(new SolidBorder(0.3f) );            
            
            document.add(table7.setHorizontalAlignment(HorizontalAlignment.CENTER));           
            
            Table table8 = new Table(UnitValue.createPercentArray(new float[]{100})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));            
            
            aCell = new Cell();
            aCell.setBorder(Border.NO_BORDER);
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "Declaramos para os devidos fins que as informações acima são verdadeiras," ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );  
            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );              
            table8.addCell(aCell);  
            
            document.add(table8.setHorizontalAlignment(HorizontalAlignment.CENTER));      
            
            Table table9 = new Table(UnitValue.createPercentArray(new float[]{50,50})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));            
            
//            aCell = new Cell();
//            aCell.setBorder(Border.NO_BORDER);
//            aCell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );  
//            aCell.add( new Paragraph( "Marcelino Xenófanes Diniz de Souza" ).setFont(font).setFontSize(defaultFontSize) );  
//            aCell.add( new Paragraph( "Gerente de Previdência" ).setFont(font).setFontSize(smallFontSize) );  
//            aCell.setTextAlignment(TextAlignment.CENTER);
//            table9.addCell(aCell);
            
            aCell = new Cell();
            aCell.setBorder(Border.NO_BORDER);
            aCell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );  
            aCell.add( new Paragraph( funprefController.getUser().getName() ).setFont(font).setFontSize(defaultFontSize) );  
            aCell.add( new Paragraph( funprefController.getUser().getOffice() ).setFont(font).setFontSize(smallFontSize) );              
            aCell.setTextAlignment(TextAlignment.CENTER);
            table9.addCell(aCell);
            
            aCell = new Cell();
            aCell.setBorder(Border.NO_BORDER);
//            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );  
//            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );  
            aCell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );  
            aCell.add( new Paragraph( beneficiary.getName() ).setFont(font).setFontSize(defaultFontSize) );              
            aCell.add( new Paragraph( printBenefitTypeText( beneficiary.getIdBenefitType() ) ).setFont(font).setFontSize(smallFontSize) );              
            aCell.setTextAlignment(TextAlignment.CENTER);
            table9.addCell(aCell);            
            
            document.add(table9.setHorizontalAlignment(HorizontalAlignment.CENTER));      
            
//            Table table10 = new Table(UnitValue.createPercentArray(new float[]{100})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));                        
//            
//            aCell = new Cell();
//            aCell.setBorder(Border.NO_BORDER);
//            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );  
//            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );  
//            aCell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );  
//            aCell.add( new Paragraph( beneficiary.getName() ).setFont(font).setFontSize(defaultFontSize) );              
//            aCell.add( new Paragraph( printBenefitTypeText( beneficiary.getIdBenefitType() ) ).setFont(font).setFontSize(smallFontSize) );              
//            aCell.setTextAlignment(TextAlignment.CENTER);
//            table10.addCell(aCell);
//            
//            document.add(table10.setHorizontalAlignment(HorizontalAlignment.CENTER));                  
            
            
            document.close(); 
            

            Desktop.getDesktop().open(new File(outputFilePath));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);        
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }                    
    }

    private String printBenefitTypeText(int idBenefitType) {
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

    void setController(FUNPREFController funprefController) {
        this.funprefController = funprefController;
        reportBeneficiaryController.setFunprefController(funprefController);
        reportDependentController.setFunprefController(funprefController);
        reportBeneficiaryPendingController.setFunprefController(funprefController);
        reportBeneficiaryDeceasedController.setFunprefController(funprefController);
    }

    void generateReportBeneficiary() {
        try {        
            generateFileData();
            generateHeader();
            document = reportBeneficiaryController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void generateReportBeneficiaryPending() {
        try {        
            generateFileData();
            generateHeader();
            document = reportBeneficiaryPendingController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        void generateReportBeneficiaryDeceased() {
        try {        
            generateFileData();
            generateHeader();
            document = reportBeneficiaryDeceasedController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void generateReportDependent() {
        try {        
            generateFileData();
            generateHeader();
            document = reportDependentController.generateData(document);
            generateBottom();

            Desktop.getDesktop().open(new File(outputFilePath));
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void generateFileData() {            
        try {        
            //PdfDocument pdfDocument;
            PdfWriter writer;
            outputFilePath = "report.pdf";
            


            writer = new PdfWriter(outputFilePath);

            writer.setCompressionLevel(9);        
            pdfDocument = new PdfDocument(writer);                        
            pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
            document = new Document(pdfDocument);
            document.setMargins(110.0f, 25.0f, 25.0f, 25.0f);            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);        
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }             
            
    }


    private void generateHeader() {
        try {        
//            int defaultFontSize = 9;            ;

            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);

//            Table tableTop = new Table(UnitValue.createPercentArray(new float[]{35,45,20})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
//            Image blazon = null;
//            Cell aCell;
//            try {
//                blazon = new Image( ImageDataFactory.create("./src/resources/about_icon.png"));
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            aCell = new Cell();
//            aCell.add( new Paragraph( "CENSO PREVIDENCIÁRIO" ).setFont(font).setFontSize(16) ).setVerticalAlignment(VerticalAlignment.MIDDLE);              
//            aCell.setBorder(Border.NO_BORDER);
//    //            cell2.setBorderTop(new SolidBorder(0));
//    //            cell2.setBorderBottom(new SolidBorder(0));        
//            tableTop.addCell(aCell);            
//
//            aCell = new Cell();
//            aCell.add( new Paragraph( "FUNDO PREVIDENCIÁRIO DO MUNICÍPIO DE FLORES-PE" ).setFont(font).setFontSize(defaultFontSize) );
//            aCell.add( new Paragraph( "05.699.773/0001-21" ).setFont(font).setFontSize(defaultFontSize).setFontColor(Color.GRAY) );
//            aCell.setTextAlignment(TextAlignment.RIGHT);
//            aCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
//            aCell.setBorder(Border.NO_BORDER);
//    //            cell2.setBorderTop(new SolidBorder(0));
//    //            cell2.setBorderBottom(new SolidBorder(0));        
//            tableTop.addCell(aCell);                        
//
//
//            blazon.scale(0.5f, 0.5f);
//            aCell = new Cell();
//            aCell.add(blazon);
//            aCell.setBorder(Border.NO_BORDER);
//    //            aCell.setBorderTop(new SolidBorder(0));
//    //            aCell.setBorderBottom(new SolidBorder(0));
//    //            aCell.setBorderLeft(new SolidBorder(0));
//            tableTop.addCell(aCell);
            
//            paragraph = new Paragraph();
//            paragraph.add(tableTop);
//            
//            for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
//                Rectangle pageSize = pdfDocument.getPage(i).getPageSize();
//                float x = pageSize.getWidth() / 2;
//                float y = pageSize.getTop() - 20;
//                document.showTextAligned(tableTop, x, y, i, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
//            }       

            //pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new TableHeaderEventHandler(tableTop));
            TableHeaderEventHandler handler = new TableHeaderEventHandler(document);
            pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
            
            //document.add(tableTop.setHorizontalAlignment(HorizontalAlignment.CENTER));
            
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }        

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
        }            
    }
    
//    private static class TableHeaderEventHandler implements IEventHandler {
//        private Table table;
//
//        public TableHeaderEventHandler(Table table) {
//            this.table = table;
//        }
//
//        @Override
//        public void handleEvent(Event currentEvent) {
//            PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
//            PdfDocument pdfDoc = docEvent.getDocument();
//            PdfPage page = docEvent.getPage();
//            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
//
//            new Canvas(canvas, pdfDoc, new Rectangle(36, 20, page.getPageSize().getWidth() - 72, 50))
//                    .add(table)
//                    .close();
//        }
//
//
//    }    

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
            }            
        }
    }
    
}
