package view;

import classes.ToDo;
import dao.ToDoDao;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import util.Util;

public class Cadastro extends javax.swing.JInternalFrame {

    public Cadastro() {
        initComponents();
        productValue();

        spQtd.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                productValue();
            }
        });

        // Desabilita input de texto no spinner
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spQtd.getEditor()).getTextField();
        tf.setBackground(Color.white);
        tf.setEditable(false);

        // Carra os itens ToDo
        loadToDoItems("", "");
    }

    // Imports Globais
    private Util util = new Util();
    private ToDo gTodo = new ToDo();
    private boolean modoEdicao = false;
    private ArrayList<ToDo> todoRequest = null;

    /**
     * Metodo que atualiza os valores dos produtos
     *
     * @author Flavio Ever
     * @since 10/05/2020
     */
    public void productValue() {
        if (!txtValor.getText().equals("")) {
            Float total = Float.parseFloat(txtValor.getText()) * (Integer) spQtd.getValue();
            gTodo.setValorUnitario(Float.parseFloat(txtValor.getText()));
            gTodo.setQtd((Integer) spQtd.getValue());
            gTodo.setValorTotal(total);

            this.lbResultado.setText("R$" + gTodo.getValorTotal());
        }
    }

    /**
     * Metodo que carrega os ToDo
     *
     * @author Flavio Ever
     * @param titulo titulo do item
     * @param data data do item
     * @since 10/05/2020
     */
    public void loadToDoItems(String titulo, String data) {
        String titulos[] = {"Titulo", "Data", "V. Unitário", "Qtd", "V. Total", "Prioridade", "Status"};

        DefaultTableModel modelGrid = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Desabilita edição da jTable
                return false;
            }
        };

        todoRequest = (ArrayList<ToDo>) new ToDoDao().index("", titulo, data);

        for (int i = 0; i < todoRequest.size(); i++) {
            modelGrid.addRow(new Object[]{
                todoRequest.get(i).getTitulo(),
                todoRequest.get(i).getDataPrevista(),
                todoRequest.get(i).getValorUnitario(),
                todoRequest.get(i).getQtd(),
                todoRequest.get(i).getValorTotal(),
                todoRequest.get(i).getPrioridade(),
                todoRequest.get(i).getStatus()});

        }

        tbToDo.setModel(modelGrid);
    }

    /**
     * Metodo que consiste os inputs principais
     *
     * @author Flavio Ever
     * @return Boolean se está ok ou não
     * @since 10/05/2020
     */
    public boolean consistir() {
        // Titulo
        if (txtTitulo.getText().length() < 3) {
            JOptionPane.showMessageDialog(null, "Titulo: Quantidade mínima inválida!");
            return false;
        }
        // Data Expiracao
        if (!util.dateIsValid(txtData.getText())) {
            JOptionPane.showMessageDialog(null, "Data de expiração inválida!");
            return false;
        }
        // Data Expiracao
        if (Float.parseFloat(txtValor.getText()) < 1) {
            JOptionPane.showMessageDialog(null, "Valor unitário inválido!");
            return false;
        }

        return true;
    }

    /**
     * Metodo que limpa todos os campos necessários
     *
     * @author Flavio Ever
     * @since 10/05/2020
     */
    public void resetCampos() {
        txtTitulo.setText("");
        txtData.setText(null);
        txtValor.setText("0");
        spQtd.setValue(1);
        optNormal.setSelected(true);
        tbToDo.getSelectionModel().clearSelection();
        
        // Restaura os botões
        modoEdicao(false);
    }

    /**
     * Metodo que pega o que foi clicado na Grid e transmite nos Inputs do
     * cadastro para edição ou restaura o estado anterior do programa
     *
     * @author Flavio Ever
     * @param modo modo de edicao
     * @since 10/05/2020
     */
    public void modoEdicao(Boolean modo) {
        // Muda sentido
        modoEdicao = modo;

        // Entra com os dados nos inputs
        if (modo) {
            txtTitulo.setText(gTodo.getTitulo());
            txtData.setText(gTodo.getDataPrevista());
            txtValor.setText(gTodo.getValorUnitario().toString());
            spQtd.setValue(gTodo.getQtd());
            
            productValue(); // Monta total

            // Status
            if (gTodo.getStatus().equals("Pendente")) {
                chkStatus.setSelected(false);
            }
            if (gTodo.getStatus().equals("Finalizado")) {
                chkStatus.setSelected(true);
            }
            
            // Prioridade
            if (gTodo.getPrioridade().equals("Alta")) {
                optAlta.setSelected(true);
            }
            if (gTodo.getPrioridade().equals("Normal")) {
                optNormal.setSelected(true);
            }

            // Muda sentido botoes
            btnCadastrar.setText("Alterar");
            mnCadastrar.setText("Alterar");

            btnVoltar.setEnabled(true);
            mnVoltar.setEnabled(true);

            btnRemover.setEnabled(true);
            mnRemover.setEnabled(true);
            return;
        }

        // Falso
        btnVoltar.setEnabled(false);
        mnVoltar.setEnabled(false);

        btnRemover.setEnabled(false);
        mnRemover.setEnabled(false);

        btnCadastrar.setText("Cadastrar");
        mnCadastrar.setText("Cadastrar");
    }

    /**
     * Metodo que monta o objeto ToDo
     *
     * @author Flavio Ever
     * @return ToDo estrutura do ToDo validada
     * @since 10/05/2020
     */
    public ToDo objToDo() {
        gTodo.setTitulo(txtTitulo.getText());
        gTodo.setDataPrevista(txtData.getText());
        
        // Status
        if (chkStatus.isSelected()) {
            gTodo.setStatus("Finalizado");
        }
        if (!chkStatus.isSelected()) {
            gTodo.setStatus("Pendente");
        }
        
        // Prioridade
        if (optAlta.isSelected()) {
            gTodo.setPrioridade("Alta");
        }
        if (optNormal.isSelected()) {
            gTodo.setPrioridade("Normal");
        }

        // Obs: O restante dos itens como valor, qtd e total já estão carregados no objeto;
        return gTodo;
    }

    /**
     * Metodo que remove o ToDo
     *
     * @author Flavio Ever
     * @since 10/05/2020
     */
    public void remover() {
        ToDoDao todo = new ToDoDao();

        if (modoEdicao) {
            String rs = todo.remove(gTodo.getPrimaryKey());

            if (!rs.equals("ok")) {
                JOptionPane.showMessageDialog(null, rs);
                return;
            }

            // Carrega items
            loadToDoItems("", "");

            JOptionPane.showMessageDialog(null, "ToDo removido com sucesso!");

            // Limpa
            modoEdicao(false);
        }
    }

    /**
     * Metodo que cadastra o ToDo ou Altera o ToDo dependendo do que está
     * flegado na Grid
     *
     * @author Flavio Ever
     * @since 10/05/2020
     */
    public void cadastrar() {
        ToDoDao todo = new ToDoDao();

        if (consistir()) {
            if (!modoEdicao) {
                String rs = todo.store(objToDo());

                if (!rs.equals("ok")) {
                    JOptionPane.showMessageDialog(null, rs);
                    return;
                }

                // Carrega items
                loadToDoItems("", "");

                JOptionPane.showMessageDialog(null, "ToDo cadastrada com sucesso!");

                return;
            }

            if (modoEdicao) {
                String rs = todo.update(objToDo());

                if (!rs.equals("ok")) {
                    JOptionPane.showMessageDialog(null, rs);
                    return;
                }

                // Carrega items
                loadToDoItems("", "");

                JOptionPane.showMessageDialog(null, "ToDo atualizado com sucesso!");
            }

            // Limpa
            resetCampos();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoPrioridade = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        optAlta = new javax.swing.JRadioButton();
        optNormal = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbToDo = new javax.swing.JTable();
        txtValor = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnCadastrar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lbResultado = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        spQtd = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        chkStatus = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        mnCadastrar = new javax.swing.JMenuItem();
        mnRemover = new javax.swing.JMenuItem();
        mnVoltar = new javax.swing.JMenuItem();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Cadastro de Compra");
        getContentPane().setLayout(null);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel5.setText("Total:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(340, 110, 70, 30);

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(txtData);
        txtData.setBounds(560, 50, 140, 30);

        grupoPrioridade.add(optAlta);
        optAlta.setText("Alta");
        getContentPane().add(optAlta);
        optAlta.setBounds(500, 150, 80, 30);

        grupoPrioridade.add(optNormal);
        optNormal.setSelected(true);
        optNormal.setText("Normal");
        getContentPane().add(optNormal);
        optNormal.setBounds(420, 150, 90, 30);

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText("Titulo:");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(30, 10, 210, 30);

        tbToDo.setAutoCreateRowSorter(true);
        tbToDo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Titulo", "Data", "V. Unitário", "Qtd", "V. Total", "Prioridade", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jScrollPane3.setBounds(20, 230, 680, 110);

        txtValor.setText("0");
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorKeyReleased(evt);
            }
        });
        getContentPane().add(txtValor);
        txtValor.setBounds(60, 150, 120, 30);

        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/save.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/remove.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.setEnabled(false);
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/back.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setEnabled(false);
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(301, Short.MAX_VALUE)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(20, 350, 680, 50);

        lbResultado.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        lbResultado.setText("R$ 0");
        getContentPane().add(lbResultado);
        lbResultado.setBounds(340, 150, 90, 30);
        getContentPane().add(txtTitulo);
        txtTitulo.setBounds(20, 50, 520, 30);

        spQtd.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spQtd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                spQtdMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                spQtdMouseClicked(evt);
            }
        });
        spQtd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                spQtdKeyReleased(evt);
            }
        });
        getContentPane().add(spQtd);
        spQtd.setBounds(210, 150, 90, 30);

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jLabel13.setText("R$");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(30, 150, 30, 30);

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel14.setText("Quantidade:");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(210, 110, 130, 30);

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel10.setText("Data prevista:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(560, 10, 150, 30);

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel15.setText("Status:");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(600, 110, 120, 30);

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel12.setText("Valor Unitário:");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(30, 110, 150, 30);

        chkStatus.setText("Finalizado");
        chkStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStatusActionPerformed(evt);
            }
        });
        getContentPane().add(chkStatus);
        chkStatus.setBounds(590, 150, 120, 30);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(20, 200, 680, 12);

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel16.setText("Prioridade:");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(430, 110, 130, 30);

        jMenu2.setText("Opções");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        mnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/save.png"))); // NOI18N
        mnCadastrar.setText("Cadastrar");
        mnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCadastrarActionPerformed(evt);
            }
        });
        jMenu2.add(mnCadastrar);

        mnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/remove.png"))); // NOI18N
        mnRemover.setText("Excluir");
        mnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRemoverActionPerformed(evt);
            }
        });
        jMenu2.add(mnRemover);

        mnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/back.png"))); // NOI18N
        mnVoltar.setText("Voltar");
        mnVoltar.setEnabled(false);
        mnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnVoltarActionPerformed(evt);
            }
        });
        jMenu2.add(mnVoltar);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        setBounds(0, 0, 747, 495);
    }// </editor-fold>//GEN-END:initComponents

    private void tbToDoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbToDoMouseClicked

    }//GEN-LAST:event_tbToDoMouseClicked

    private void tbToDoCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tbToDoCaretPositionChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tbToDoCaretPositionChanged

    private void txtValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorKeyReleased
        productValue();
    }//GEN-LAST:event_txtValorKeyReleased

    private void spQtdMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spQtdMouseReleased

    }//GEN-LAST:event_spQtdMouseReleased

    private void spQtdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_spQtdMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_spQtdMouseClicked

    private void spQtdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spQtdKeyReleased
    }//GEN-LAST:event_spQtdKeyReleased

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastrar();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void tbToDoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbToDoMouseReleased
        if (tbToDo.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "Nada selecionado");
        } else {
            int index = tbToDo.getSelectionModel().getMinSelectionIndex();

            String titulo = tbToDo.getModel().getValueAt(index, 0).toString();
            String dataPrevista = tbToDo.getModel().getValueAt(index, 1).toString();
            Float valorUnitario = Float.parseFloat(tbToDo.getModel().getValueAt(index, 2).toString());
            Integer qtd = Integer.parseInt(tbToDo.getModel().getValueAt(index, 3).toString());
            Float valorTotal = Float.parseFloat(tbToDo.getModel().getValueAt(index, 4).toString());
            String prioridade = tbToDo.getModel().getValueAt(index, 5).toString();
            String status = tbToDo.getModel().getValueAt(index, 6).toString();

            // Primari key
            gTodo.setPrimaryKey(todoRequest.get(index).getPrimaryKey());

            // All
            gTodo.setTitulo(titulo);
            gTodo.setDataPrevista(dataPrevista);
            gTodo.setValorUnitario(valorUnitario);
            gTodo.setQtd(qtd);
            gTodo.setValorTotal(valorTotal);
            gTodo.setPrioridade(prioridade);
            gTodo.setStatus(status);

            modoEdicao(true);
        }
    }//GEN-LAST:event_tbToDoMouseReleased

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        modoEdicao(false);
        resetCampos();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        modoEdicao(false);
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void mnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCadastrarActionPerformed
        cadastrar();
    }//GEN-LAST:event_mnCadastrarActionPerformed

    private void mnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_mnRemoverActionPerformed

    private void mnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnVoltarActionPerformed
        modoEdicao(false);
        resetCampos();
    }//GEN-LAST:event_mnVoltarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void chkStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JCheckBox chkStatus;
    private javax.swing.ButtonGroup grupoPrioridade;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbResultado;
    private javax.swing.JMenuItem mnCadastrar;
    private javax.swing.JMenuItem mnRemover;
    private javax.swing.JMenuItem mnVoltar;
    private javax.swing.JRadioButton optAlta;
    private javax.swing.JRadioButton optNormal;
    private javax.swing.JSpinner spQtd;
    private javax.swing.JTable tbToDo;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
