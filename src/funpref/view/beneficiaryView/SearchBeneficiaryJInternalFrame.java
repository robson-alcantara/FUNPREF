/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funpref.view.beneficiaryView;

import funpref.controller.BeneficiaryController;
import funpref.controller.SearchBeneficiaryController;
import funpref.model.Beneficiary;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author robson
 */
public class SearchBeneficiaryJInternalFrame extends javax.swing.JInternalFrame {
    
    private static SearchBeneficiaryJInternalFrame searchJInternalFrame;
    //private static int currentUserID;
    private SearchBeneficiaryController searchBeneficiaryController;
    private ArrayList<Beneficiary> findedBeneficiaries;
    private boolean updatingSourceBeneficiary;

    /**
     * Creates new form SearchJInternalFrame
     */
    public SearchBeneficiaryJInternalFrame(SearchBeneficiaryController searchBeneficiaryController, boolean updatingSourceBeneficiary) {
        
        //SearchJInternalFrame.currentUserID = currentUserID;
        this.searchBeneficiaryController = searchBeneficiaryController;
        this.updatingSourceBeneficiary = updatingSourceBeneficiary;
        
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setTitle("Pesquisa de Beneficiário");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Pesquisa");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "matrícula", "nome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
        }

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Carregar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        findedBeneficiaries = searchBeneficiaryController.getFunprefController().getBeneficiaryController().findByExamplePart(-1, jTextField1.getText() );
        fillSearchJTable( findedBeneficiaries );
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
//        searchJInternalFrame.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        //        if( jTable1.getSelectedRow() >= 0 ) {
            //            if( !jTable1.getValueAt( jTable1.getSelectedRow(), 0 ).toString().isEmpty() ) {
                //                CRUDTicketJFrame newTicketJFrame = new CRUDTicketJFrame(this);
                //                newTicketJFrame.setTicketNumber( Integer.parseInt( jTable1.getValueAt( jTable1.getSelectedRow(), 0 ).toString() ) );
                //                newTicketJFrame.setUsersHashMap(usersHashMap);
                //                newTicketJFrame.setPlacesHashMap(placesHashMap);
                //                newTicketJFrame.setMode( CRUDTicketJFrame.CRUD.READ );
                //                newTicketJFrame.setTitle("Visualização");
                //                newTicketJFrame.setVisible(true);
                //                this.setEnabled(false);

                if( (jTable1.getSelectedRow() >= 0 ) && (!(jTable1.getValueAt(jTable1.getSelectedRow(),0)).toString().isEmpty() ) ) {
                    if( !updatingSourceBeneficiary ) {
//                        preFillBeneficiaryCRUDJInternalFrame();
//                        clearBeneficiaryCRUDJInternalFrame();

                        searchBeneficiaryController.getFunprefController().getBeneficiaryController().setCurrentBeneficiary( searchBeneficiaryController.getFunprefController().getBeneficiaryController().findById(findedBeneficiaries.get( jTable1.getSelectedRow() ).getId() ) );
                        searchBeneficiaryController.getFunprefController().getBeneficiaryController().fillAndShowBeneficiaryJInternalFrame();


//                        funprefController.readAndFillBeneficiaryCRUDJInternalFrame((int)jTable1.getValueAt(jTable1.getSelectedRow(),0), beneficiaryCRUDMode );
                        //jTabbedPane2.setSelectedIndex(0);
                        //clearSearchJInternalFrame();
                        //beneficiaryCRUDJInternalFrame.setVisible(true);
                    }

                    else {
//                        int beneficiaryId = (int)jTable1.getValueAt(jTable1.getSelectedRow(),0);
//                        int instituteEnrollment = funprefController.getBeneficiaryEnrollmentById( beneficiaryId );
//                        jTextField48.setText( "" + instituteEnrollment );
//                        jTextField49.setText( funprefController.getBeneficiaryNameByEnrollment( instituteEnrollment ) );
//                        updatingSourceBeneficiary = false;

                    }

                    searchJInternalFrame.dispose();
                }

                //            }
            //            else {
                //                JOptionPane.showMessageDialog(rootPane, "Selecione um inativo", "Erro", JOptionPane.ERROR_MESSAGE);
                //            }
            //        } else {
            //            JOptionPane.showMessageDialog(rootPane, "Selecione um inativo", "Erro", JOptionPane.ERROR_MESSAGE);
            //        }

    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    public static SearchBeneficiaryJInternalFrame getSearchBeneficiaryJInternalFrame(SearchBeneficiaryController searchBeneficiaryController, boolean updatingSourceBeneficiary ) {
        if (searchJInternalFrame != null) {
            searchJInternalFrame.dispose();
            searchJInternalFrame.reset();
        }
        else {
            searchJInternalFrame = new SearchBeneficiaryJInternalFrame(searchBeneficiaryController, updatingSourceBeneficiary );
        }
        return searchJInternalFrame;
    }

    private void fillSearchJTable(ArrayList<Beneficiary> beneficiaries) {
        clearTable(jTable1);
        
        for( int i = 0; i < beneficiaries.size(); i++ ) {    
            if( i <= 46 ) { // número de espaços da jTable1
                jTable1.setValueAt(beneficiaries.get(i).getRegistration(), i, 0);
                jTable1.setValueAt(beneficiaries.get(i).getName(), i, 1);
            }
        }
    }

    private void clearTable(JTable table) {
       for (int i = 0; i < table.getRowCount(); i++) {
          for(int j = 0; j < table.getColumnCount(); j++) {
              table.setValueAt("", i, j);
          }
       }
    }

    private void reset() {
        jTextField1.setText("");
        clearTable(jTable1);
    }
    
}