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
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import funpref.controller.FUNPREFController;
import funpref.model.Beneficiary;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robson
 */
public class ReportBeneficiaryController {
    
    private final FUNPREFController funprefController;
    private final ReportController reportController;
    private final SimpleDateFormat formatDate;

    ReportBeneficiaryController(FUNPREFController funprefController, ReportController reportController) {
        this.funprefController = funprefController;
        this.reportController = reportController;
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
    }

    Document generateData(Document document, boolean printRegisterDate) {
        try {
            Cell cell;
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
            Beneficiary beneficiary = funprefController.getBeneficiaryController().getCurrentBeneficiary();
            Table table2 = new Table(UnitValue.createPercentArray(new float[]{80,20})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));
            int defaultFontSize = 9;
            int smallFontSize = 7;            
            
            cell = new Cell();
            cell.add( new Paragraph( beneficiary.getName() ).setFont(font).setFontSize(10).setVerticalAlignment(VerticalAlignment.MIDDLE) );
            cell.add( new Paragraph( reportController.printBenefitTypeText( beneficiary.getIdBenefitType() ) ).setFont(font).setFontSize(10).setVerticalAlignment(VerticalAlignment.MIDDLE) );            
            cell.setBorder(Border.NO_BORDER);
            table2.addCell(cell);

            cell = new Cell();
            cell.add( new Paragraph( "CPF" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( beneficiary.getCpf() ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
            cell.add( new Paragraph( "CONCESSÃO DO BENEFÍCIO" ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
            cell.add( new Paragraph( formatDate.format(beneficiary.getInactivationDate()) ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );

            if ( printRegisterDate && ( beneficiary.getRegisterDate() != null ) ) {
                cell.add( new Paragraph( "DATA DO RECENSEAMENTO" ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
                cell.add( new Paragraph( formatDate.format(beneficiary.getRegisterDate()) ).setFont(font).setFontSize(smallFontSize).setHorizontalAlignment(HorizontalAlignment.CENTER) );
            }

            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setBorder(Border.NO_BORDER);
            table2.setBorderTop( new SolidBorder(0.3f) );
            table2.setBorderBottom(new SolidBorder(0.3f) );
            table2.addCell(cell);


            document.add(table2.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table3 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            cell = new Cell();
            cell.add( new Paragraph( "Informações da Ativa" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "Data de admissão: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Cargo: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Tempo anterior: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.setBorder(Border.NO_BORDER);
            table3.addCell(cell);

            cell = new Cell();

            if( ( beneficiary.getIdBenefitType() != 2 ) && ( beneficiary.getIdBenefitType() != 7 ) ) {
                cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
                cell.add( new Paragraph( formatDate.format( beneficiary.getAdmissionDate() ) ).setFont(font).setFontSize(defaultFontSize) );
                cell.add( new Paragraph( beneficiary.getOffice() ).setFont(font).setFontSize(defaultFontSize) );
                cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );
            }

            cell.setBorder(Border.NO_BORDER);
            table3.addCell(cell);

            table3.setBorderTop( new SolidBorder(0.3f) );
            table3.setBorderBottom(new SolidBorder(0.3f) );

            document.add(table3.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table4 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            cell = new Cell();
            cell.add( new Paragraph( "Dados Pessoais" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "Data de nascimento: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Naturalidade: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Mãe: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Pai: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Grau de instrução: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Estado civil: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Estado físico: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.setBorder(Border.NO_BORDER);
            table4.addCell(cell);

            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( formatDate.format( beneficiary.getBirthDate() ) ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "" + funprefController.decodeCity(beneficiary.getIdCityPlaceOfBirth(),beneficiary.getIdProvincePlaceOfBirth()) +
                    "-" + funprefController.decodeProvince(beneficiary.getIdProvincePlaceOfBirth()) +
                    ", " + beneficiary.getNationality() ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( beneficiary.getMotherName() ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( beneficiary.getFatherName() ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "" + funprefController.decodeDegreeEducation(beneficiary.getIdDegreeOfEducation()) ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "" + funprefController.decodeMaritalStatus(beneficiary.getIdMaritalStatus()) ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "" + funprefController.decodeDeficiency(beneficiary.getIdDeficiency()) ).setFont(font).setFontSize(defaultFontSize) );
            cell.setBorder(Border.NO_BORDER);
            table4.addCell(cell);

            table4.setBorderTop( new SolidBorder(0.3f) );
            table4.setBorderBottom(new SolidBorder(0.3f) );

            document.add(table4.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table5 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            cell = new Cell();
            cell.add( new Paragraph( "Documentos" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "Identidade: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Título de eleitor: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.setBorder(Border.NO_BORDER);
            table5.addCell(cell);

            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( beneficiary.getRg() + " " +
            //                    funprefController.decodeRgIssuingBody(beneficiary.getIdRgIssuingBody()) +
            //                    "-" + funprefController.decodeProvince(beneficiary.getIdProvinceRg()) +
            //                    " emitido em " + ( (beneficiary.getRgEmissionDate() != null)?formatDate.format(beneficiary.getRgEmissionDate()):"") ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( "" + beneficiary.getVotersTitle() + " Zona: " + beneficiary.getElectoralZone() +
            //                    " Seção: " + beneficiary.getElectoralSection() +
            //                    " UF: " + funprefController.decodeProvince(beneficiary.getIdProvinceElectoralZone()) ).setFont(font).setFontSize(defaultFontSize)
                    ""));
            cell.setBorder(Border.NO_BORDER);
            table5.addCell(cell);            

            table5.setBorderTop( new SolidBorder(0.3f) );
            table5.setBorderBottom(new SolidBorder(0.3f) );

            document.add(table5.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table6 = new Table(UnitValue.createPercentArray(new float[]{20,80})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            cell = new Cell();
            cell.add( new Paragraph( "Contatos" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "Endereço: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "Telefone: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "e-mail: " ).setFont(font).setFontSize(defaultFontSize) );
            cell.setBorder(Border.NO_BORDER);
            table6.addCell(cell);  

            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            //            aCell.add( new Paragraph( beneficiary.getAddress() + ", " +
            //                    funprefController.decodeCity( beneficiary.getIdCityAddress(), beneficiary.getIdProvinceAddress() ) + "-" +
            //                    funprefController.decodeProvince( beneficiary.getIdProvinceAddress() ) ).setFont(font).setFontSize(defaultFontSize) );            
            //            aCell.add( new Paragraph( ((beneficiary.getPhone1() != null)?beneficiary.getPhone1():"") + " " +
            //                    ((beneficiary.getPhone2() != null)?beneficiary.getPhone2():"") ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( ((beneficiary.getEmail() != null)?beneficiary.getEmail():"") ).setFont(font).setFontSize(defaultFontSize) );
            cell.setBorder(Border.NO_BORDER);
            table6.addCell(cell);            

            table6.setBorderTop( new SolidBorder(0.3f) );
            table6.setBorderBottom(new SolidBorder(0.3f) );

            document.add(table6.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table7 = new Table(UnitValue.createPercentArray(new float[]{12,40,12,18,18})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            cell = new Cell();
            cell.add( new Paragraph( "Dependentes" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "CPF" ).setFont(font).setFontSize(smallFontSize) );

            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                cell.add( new Paragraph( beneficiary.getDependents().get(i).getCpf() ).setFont(font).setFontSize(defaultFontSize) );
            }

            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setBorder(Border.NO_BORDER);
            table7.addCell(cell);

            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "NOME" ).setFont(font).setFontSize(smallFontSize) );

            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                cell.add( new Paragraph( beneficiary.getDependents().get(i).getName()).setFont(font).setFontSize(defaultFontSize) );
            }

            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setBorder(Border.NO_BORDER);
            table7.addCell(cell);

            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "NASCIMENTO" ).setFont(font).setFontSize(smallFontSize) );

            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
                cell.add( new Paragraph( formatDate.format(beneficiary.getDependents().get(i).getBirthDate()) ).setFont(font).setFontSize(defaultFontSize) );
            }

            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setBorder(Border.NO_BORDER);
            table7.addCell(cell);

            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "PARENTESCO" ).setFont(font).setFontSize(smallFontSize) );

            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
            //                aCell.add( new Paragraph( "" + funprefController.decodeKinship(beneficiary.getDependents().get(i).getIdKinship() ) ).setFont(font).setFontSize(defaultFontSize) );
            }

            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setBorder(Border.NO_BORDER);
            table7.addCell(cell);


            cell = new Cell();
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "DEFICIÊNCIA" ).setFont(font).setFontSize(smallFontSize) );

            for( int i = 0; i < beneficiary.getDependents().size(); i++ ) {
            //                aCell.add( new Paragraph( "" + funprefController.decodeDeficiency(beneficiary.getDependents().get(i).getIdDeficiency() ) ).setFont(font).setFontSize(defaultFontSize) );
            }

            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setBorder(Border.NO_BORDER);
            table7.addCell(cell);

            table7.setBorderTop( new SolidBorder(0.3f) );
            table7.setBorderBottom(new SolidBorder(0.3f) );

            document.add(table7.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table8 = new Table(UnitValue.createPercentArray(new float[]{100})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "Declaramos para os devidos fins que as informações acima são verdadeiras," ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            cell.add( new Paragraph( "\n" ).setFont(font).setFontSize(smallFontSize) );
            table8.addCell(cell);

            document.add(table8.setHorizontalAlignment(HorizontalAlignment.CENTER));

            Table table9 = new Table(UnitValue.createPercentArray(new float[]{50,50})).useAllAvailableWidth().setFixedLayout().setWidth(UnitValue.createPercentValue(100));

            //            aCell = new Cell();
            //            aCell.setBorder(Border.NO_BORDER);
            //            aCell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( "Marcelino Xenófanes Diniz de Souza" ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( "Gerente de Previdência" ).setFont(font).setFontSize(smallFontSize) );  
            //            aCell.setTextAlignment(TextAlignment.CENTER);
            //            table9.addCell(aCell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            cell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( funprefController.getUser().getName() ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( funprefController.getUser().getOffice() ).setFont(font).setFontSize(smallFontSize) );
            cell.setTextAlignment(TextAlignment.CENTER);
            table9.addCell(cell);

            cell = new Cell();
            cell.setBorder(Border.NO_BORDER);
            //            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );
            //            aCell.add( new Paragraph( "\n" ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( "________________________________" ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( beneficiary.getName() ).setFont(font).setFontSize(defaultFontSize) );
            cell.add( new Paragraph( reportController.printBenefitTypeText( beneficiary.getIdBenefitType() ) ).setFont(font).setFontSize(smallFontSize) );
            cell.setTextAlignment(TextAlignment.CENTER);
            table9.addCell(cell);

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
        } catch (IOException ex) {
            Logger.getLogger(ReportBeneficiaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return document;
    }
    
}
