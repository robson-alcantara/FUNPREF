/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.view;

//import com.itextpdf.io.font.FontConstants;
//import com.itextpdf.io.image.ImageDataFactory;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.layout.element.List;
//import com.itextpdf.layout.element.ListItem;
//import com.itextpdf.layout.Document;
//import funpref.controller.ContentSearchTable;
//import funpref.controller.FUNPREFController;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.element.Image;
//import com.itextpdf.layout.element.Paragraph;
//import funpref.model.Dependent;
//import funpref.model.Beneficiary;
//import funpref.model.dao.BenefitTypeDAO;
//import funpref.model.dao.CityDAO;
//import funpref.model.dao.ContentSearchTableItensDAO;
//import funpref.model.dao.DeficiencyDAO;
//import funpref.model.dao.DegreeEducationDAO;
//import funpref.model.dao.IssuingBodyDAO;
//import funpref.model.dao.KinshipDAO;
//import funpref.model.dao.MaritalStatusDAO;
//import funpref.model.dao.ProvinceDAO;
//import funpref.model.dao.StockingOrganDAO;
//import funpref.model.dao.UserDAO;
import funpref.controller.BeneficiaryController;
import funpref.view.beneficiaryView.SearchJInternalFrame;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;

/**
 *
 * @author Robson
 */
public class FUNPREFJFrame extends javax.swing.JFrame {

    private boolean updatingSourceBeneficiary;

    public enum DependentCRUD { CREATE, READ, UPDATE, DELETE };
    public enum BeneficiaryCRUD { NEW, EDIT, READ };
    
//    private AboutJFrame aboutJFrame;
//    FUNPREFController funprefController;
    private final SimpleDateFormat formatDate;    
    
    private DependentCRUD dependentCRUDMode;    
    public BeneficiaryCRUD beneficiaryCRUDMode;
    
//    private Beneficiary currentBeneficiary;
    
    private final int currentUserID;    
    
    private SearchJInternalFrame searchJInternalFrame;
    private BeneficiaryController beneficiaryController;

    /**table
     * Creates new form FUNPREFJFrame
     */
    public FUNPREFJFrame( BeneficiaryController beneficiaryController ) {
        formatDate = new SimpleDateFormat("dd/MM/yyyy");
        this.beneficiaryController = beneficiaryController;
        initComponents();        
        initImageIcon();
        setLocationRelativeTo( null );        
        
        currentUserID = 0; //TODO: stub, add local variable in constructor
        this.beneficiaryController.setCurrentUserID(currentUserID);
    }
    
    private void initImageIcon() {        
        InputStream imageInputStream = this.getClass().getResourceAsStream("/resources/icon.png");
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(imageInputStream);
            this.setIconImage(bufferedImage);  
        } catch (IOException ex) {
            Logger.getLogger(FUNPREFJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    public JDesktopPane getJDesktopPane() {
        return jDesktopPane;
    }    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuItem5 = new javax.swing.JMenuItem();
        jLabel67 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jDesktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();

        jMenuItem5.setText("jMenuItem5");

        jLabel67.setText("jLabel67");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FUNPREF");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeWindow(evt);
            }
        });

        jButton1.setText("Ver beneficiário");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setText("Imprimir");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addContainerGap(1028, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Arquivo");

        jMenuItem3.setText("Adicionar beneficiário");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Ver beneficiário");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem6.setText("Atualizar beneficiário");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator1);

        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Opções");

        jMenuItem7.setText("Alterar Tabela do Imposto de Renda");
        jMenuItem7.setEnabled(false);
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setText("Salvar documento digital");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem11.setText("Cadastrar usuário");
        jMenuItem11.setEnabled(false);
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem16.setText("Iniciar Recenseamento Anual");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem16);

        jMenuBar1.add(jMenu3);

        jMenu6.setText("Relatórios");

        jMenuItem12.setText("Beneficiários");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuItem13.setText("Dependentes");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenuItem14.setText("Beneficiários não recadastrados");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem14);

        jMenuItem15.setText("Falecidos");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem15);

        jMenuBar1.add(jMenu6);

        jMenu4.setText("Ajuda");

        jMenuItem2.setText("Sobre");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuBar1.add(jMenu4);

        jMenu5.setEnabled(false);

        buttonGroup1.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Utilizar banco de dados");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jRadioButtonMenuItem1);

        buttonGroup1.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("Utilizar arquivo");
        jRadioButtonMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jRadioButtonMenuItem2);

        jMenuItem9.setText("Informar senha do 'admin'");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuItem10.setText("Testar senha do 'admin'");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jDesktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
//        aboutJFrame = new AboutJFrame();
//        aboutJFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void closeWindow(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeWindow
        dispose();
    }//GEN-LAST:event_closeWindow

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            try {
                searchJInternalFrame = SearchJInternalFrame.getOrderFrame(currentUserID, beneficiaryController, false);
                jDesktopPane.add(searchJInternalFrame);
                searchJInternalFrame.setLocation(jDesktopPane.getLocation().x + ( ( jDesktopPane.getWidth() - searchJInternalFrame.getWidth() ) / 2 ),
                        jDesktopPane.getLocation().y + 10);
                searchJInternalFrame.toFront();
                searchJInternalFrame.setSelected(true);
                searchJInternalFrame.setClosable(true);
                searchJInternalFrame.setVisible(true);
                searchJInternalFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } catch (Exception ex) {
                
            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
//        //if( funprefController.revalidateLogin() ) {
//        beneficiaryCRUDMode = BeneficiaryCRUD.NEW;
//        currentBeneficiary = null;
//        preFillBeneficiaryCRUDJInternalFrame();
//        clearBeneficiaryCRUDJInternalFrame();
//        setEditableBeneficiaryCRUDJInternalFrame(true);
//        jTabbedPane2.setSelectedIndex(0);                
//        updateBeneficiaryCRUDJInternalFrameTitle( currentBeneficiary );
//        beneficiaryCRUDJInternalFrame.setVisible(true);             
//        //} 
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        //generatePDFOutput();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
//        fillSearchJInternalFrame();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
//        //if( funprefController.revalidateLogin() ) {
//        beneficiaryCRUDMode = BeneficiaryCRUD.EDIT;
//        jButton3.setText("Editar");   
//        jTextField1.setText("");
//        clearTable(jTable1);
//        searchJInternalFrame.setVisible(true);
//        //}
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
//        funprefController.fillIncomeTaxJInternalFrame();
//        incomeTaxJInternalFrame.setVisible(true);
////        clearInativeCRUDJInternalFrame();
////        setEditableInativeCRUDJInternalFrame(true);
////        jTabbedPane2.setSelectedIndex(0);
////        inativeCRUDJInternalFrame.setVisible(true); 
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        
//        File sourceFile;
//        
//        try {            
//            if ( currentBeneficiary != null ) {            
//                JFileChooser chooser = new JFileChooser(new java.io.File("."));
//                chooser.setDialogTitle("Selecione o arquivo contendo o documento digitalizado");
//                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//                chooser.setMultiSelectionEnabled(false);
//
//                javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter() {
//                    public boolean accept(File f) {
//                        return f.isDirectory()
//                                || f.getName().toLowerCase().endsWith(
//                                ".pdf");
//                    }
//                    public String getDescription() {
//                        return "(*.pdf)";                    
//                    }
//                };
//
//                chooser.addChoosableFileFilter(filter);
//                chooser.setAcceptAllFileFilterUsed(false);  
//                chooser.showOpenDialog(this);            
//                sourceFile = chooser.getSelectedFile();                        
//
//                if( sourceFile != null ) {            
//                    File document = new File("./documents/" + currentBeneficiary.getOrdinance() + "/" + currentBeneficiary.getName() + ".pdf" );
//                    document.getParentFile().mkdirs();
//                    Files.move(sourceFile.toPath(), document.toPath(),StandardCopyOption.REPLACE_EXISTING);
//                    JOptionPane.showMessageDialog(rootPane, "Documento salvo", "FUNPREF", JOptionPane.INFORMATION_MESSAGE);            
//                }            
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(FUNPREFJFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        
//        UserDAO user = new UserDAO();
//        CreateUserJFrame createUserJFrame = new CreateUserJFrame();
//        
//        user = createUserJFrame.getUser();
//        
//        if( ( user != null ) && (!user.getName().isEmpty() ) ) {        
//            //funprefController.getJdbcController().connectDB();
//            funprefController.getJdbcController().createUser( user );
//        }
        
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed

//        String password;
//
//        password = JOptionPane.showInputDialog(rootPane, "Insira senha do usuário 'admin' para conferência", "Senha", JOptionPane.QUESTION_MESSAGE);
//
//        //funprefController.getJdbcController().connectDB();
//        funprefController.getJdbcController().testAdminPassword( password );

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
//
//        String password;
//
//        password = JOptionPane.showInputDialog(rootPane, "Insira senha do usuário 'admin'", "Senha", JOptionPane.QUESTION_MESSAGE);
//
//        //funprefController.getJdbcController().connectDB();
//        funprefController.getJdbcController().setAdminPassword( password );

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jRadioButtonMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMenuItem2ActionPerformed

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
//        funprefController.generateReportBeneficiary();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
//        funprefController.generateReportDependent();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
//        funprefController.generateReportBeneficiaryPending();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
//        funprefController.generateReportBeneficiaryDeceased();
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
//        int optionResult = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja iniciar um recenseamento?", "FUNPREF", JOptionPane.YES_NO_OPTION);
//        int returnValue;
//
//        if( optionResult == 0 ) {
//            
//            if( funprefController.revalidateLogin() ) {
//                
//                returnValue = funprefController.restartCadastralStatus();
//
//                if( returnValue > 0 ) {
//                    JOptionPane.showMessageDialog(rootPane, "Recenseamento reiniciado", "Informação", JOptionPane.INFORMATION_MESSAGE);                
//                }
//
//                else {                
//                    JOptionPane.showMessageDialog(rootPane, "Falha ao iniciar Recenseamento", "Erro", JOptionPane.ERROR_MESSAGE);                
//                }
//            }
//        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FUNPREFJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FUNPREFJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FUNPREFJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FUNPREFJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FUNPREFJFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JDesktopPane jDesktopPane;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables


    

}



