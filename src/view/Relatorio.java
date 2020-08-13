package view;

import classes.ToDo;
import dao.ToDoDao;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.Util;

public class Relatorio extends javax.swing.JInternalFrame {

    public Relatorio() {
        initComponents();
        
        loadToDoItems("", "", "");
    }

    // Imports Globais
    private Util util = new Util();
    private Integer qtdToDo = 0;
    private ArrayList<ToDo> todoRequest = null;


    /**
     * Metodo que carrega os ToDo
     *
     * @author Flavio Ever
     * @param id primary key do todo
     * @param titulo titulo do todo
     * @param data data do todo
     * @since 10/05/2020
     */
    public void loadToDoItems(String id, String titulo, String data) {
        qtdToDo = 0;
        
        String titulos[] = {"Id", "Titulo", "Data", "V. Unitário", "Qtd", "V. Total", "Prioridade", "Status"};

        DefaultTableModel modelGrid = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Desabilita edição da jTable
                return false;
            }
        };

        todoRequest = (ArrayList<ToDo>) new ToDoDao().index(id, titulo, data);

        for (int i = 0; i < todoRequest.size(); i++) {
            modelGrid.addRow(new Object[]{
                todoRequest.get(i).getPrimaryKey(),
                todoRequest.get(i).getTitulo(),
                todoRequest.get(i).getDataPrevista(),
                todoRequest.get(i).getValorUnitario(),
                todoRequest.get(i).getQtd(),
                todoRequest.get(i).getValorTotal(),
                todoRequest.get(i).getPrioridade(),
                todoRequest.get(i).getStatus()});
            qtdToDo++;
        }

        lbQtd.setText(qtdToDo.toString());
        tbToDo.setModel(modelGrid);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoPrioridade = new javax.swing.ButtonGroup();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbToDo = new javax.swing.JTable();
        lbQtd = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDataPesq = new javax.swing.JFormattedTextField();
        lbLimpar = new javax.swing.JLabel();
        txtPesquisaId = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Cadastro de Compra");
        getContentPane().setLayout(null);

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText("ToDo(s):");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(30, 10, 70, 30);

        tbToDo.setAutoCreateRowSorter(true);
        tbToDo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Titulo", "Data", "V. Unitário", "Qtd", "V. Total", "Prioridade", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbToDo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbToDoMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbToDoMouseClicked(evt);
            }
        });
        tbToDo.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                tbToDoCaretPositionChanged(evt);
            }
        });
        jScrollPane3.setViewportView(tbToDo);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(30, 220, 670, 230);

        lbQtd.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbQtd.setText("...");
        getContentPane().add(lbQtd);
        lbQtd.setBounds(100, 10, 240, 30);

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });
        getContentPane().add(txtPesquisa);
        txtPesquisa.setBounds(200, 80, 500, 30);

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/pesquisa.png"))); // NOI18N
        jLabel6.setText("Pesquisar ID:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(30, 120, 170, 30);

        try {
            txtDataPesq.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataPesq.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDataPesqKeyReleased(evt);
            }
        });
        getContentPane().add(txtDataPesq);
        txtDataPesq.setBounds(200, 160, 90, 30);

        lbLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/clear.png"))); // NOI18N
        lbLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbLimpar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLimparMouseClicked(evt);
            }
        });
        getContentPane().add(lbLimpar);
        lbLimpar.setBounds(660, 160, 30, 30);

        txtPesquisaId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaIdActionPerformed(evt);
            }
        });
        txtPesquisaId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaIdKeyReleased(evt);
            }
        });
        getContentPane().add(txtPesquisaId);
        txtPesquisaId.setBounds(200, 120, 90, 30);

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/pesquisa.png"))); // NOI18N
        jLabel9.setText("Pesquisar Data:");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(30, 160, 170, 30);

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/pesquisa.png"))); // NOI18N
        jLabel10.setText("Pesquisar Titulo:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(30, 80, 170, 30);
        getContentPane().add(jSeparator3);
        jSeparator3.setBounds(30, 50, 670, 12);

        setBounds(0, 0, 752, 521);
    }// </editor-fold>//GEN-END:initComponents

    private void tbToDoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbToDoMouseClicked

    }//GEN-LAST:event_tbToDoMouseClicked

    private void tbToDoCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tbToDoCaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tbToDoCaretPositionChanged

    private void tbToDoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbToDoMouseReleased
    }//GEN-LAST:event_tbToDoMouseReleased

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        txtDataPesq.setText(null);
        txtPesquisaId.setText(null);
        
        loadToDoItems("", txtPesquisa.getText(), "");
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void txtDataPesqKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataPesqKeyReleased
        txtPesquisa.setText(null);
        txtPesquisaId.setText(null);

        if (util.dateIsValid(txtDataPesq.getText())) {   
            loadToDoItems("", "", txtDataPesq.getText());
        }
    }//GEN-LAST:event_txtDataPesqKeyReleased

    private void lbLimparMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLimparMouseClicked
        txtPesquisa.setText(null);
        txtDataPesq.setText(null);
        txtPesquisaId.setText(null);
        
        loadToDoItems("", "", "");
    }//GEN-LAST:event_lbLimparMouseClicked

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaIdActionPerformed

    private void txtPesquisaIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaIdKeyReleased
        txtPesquisa.setText(null);
        txtDataPesq.setText(null);
        
        loadToDoItems(txtPesquisaId.getText(), "", "");
    }//GEN-LAST:event_txtPesquisaIdKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoPrioridade;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbLimpar;
    private javax.swing.JLabel lbQtd;
    private javax.swing.JTable tbToDo;
    private javax.swing.JFormattedTextField txtDataPesq;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtPesquisaId;
    // End of variables declaration//GEN-END:variables
}
