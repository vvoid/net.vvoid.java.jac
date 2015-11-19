package net.vvoid.java.jac;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class CommandTabjPanel extends javax.swing.JPanel {

  HistoryManager historyManager = new HistoryManager();

  RunCmdHelper cmdHelper;

  /**
   * Creates new form NewJPanel
   */
  public CommandTabjPanel() throws IOException {
    initComponents();

    historyjTable.setModel(historyManager);

    cmdHelper = new RunCmdHelper(new OutputStreamToTextArea(outputTextArea));
    cmdHelper.setWorrkingDirectory(new File(System.getProperty("user.dir", "C:\\")));

    currentDirectoryjLabel.setText(cmdHelper.getWorkingDirectory().getCanonicalPath());

  }

  public void exit() {
    try {
      historyManager.writeHistoryFile();
    } catch (IOException ex) {
      Logger.getLogger(CommandTabjPanel.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    commandTabjPanel = new javax.swing.JPanel();
    horizontaljSplitPane = new javax.swing.JSplitPane();
    verticaljSplitPane = new javax.swing.JSplitPane();
    outputjScrollPane = new javax.swing.JScrollPane();
    outputTextArea = new javax.swing.JTextArea();
    historyjScrollPane = new javax.swing.JScrollPane();
    historyjTable = new javax.swing.JTable();
    jPanel1 = new javax.swing.JPanel();
    commandjScrollPane = new javax.swing.JScrollPane();
    commandjTextField = new javax.swing.JTextField();
    currentDirectoryjLabel = new javax.swing.JLabel();

    setBackground(new java.awt.Color(255, 51, 51));
    setAutoscrolls(true);
    setEnabled(false);
    setFocusable(false);
    setOpaque(false);
    setRequestFocusEnabled(false);
    setVerifyInputWhenFocusTarget(false);
    setLayout(new java.awt.BorderLayout());

    horizontaljSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    horizontaljSplitPane.setResizeWeight(1.0);
    horizontaljSplitPane.setToolTipText("");
    horizontaljSplitPane.setContinuousLayout(true);
    horizontaljSplitPane.setDoubleBuffered(true);
    horizontaljSplitPane.setMinimumSize(new java.awt.Dimension(120, 120));
    horizontaljSplitPane.setPreferredSize(new java.awt.Dimension(0, 0));

    verticaljSplitPane.setResizeWeight(0.9);
    verticaljSplitPane.setContinuousLayout(true);
    verticaljSplitPane.setDoubleBuffered(true);
    verticaljSplitPane.setMinimumSize(new java.awt.Dimension(0, 20));
    verticaljSplitPane.setName(""); // NOI18N
    verticaljSplitPane.setPreferredSize(new java.awt.Dimension(0, 0));

    outputjScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    outputjScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    outputjScrollPane.setMinimumSize(new java.awt.Dimension(0, 0));

    outputTextArea.setEditable(false);
    outputTextArea.setColumns(20);
    outputTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
    outputTextArea.setRows(5);
    outputjScrollPane.setViewportView(outputTextArea);

    verticaljSplitPane.setLeftComponent(outputjScrollPane);

    historyjScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    historyjScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    historyjScrollPane.setMinimumSize(new java.awt.Dimension(0, 0));
    historyjScrollPane.setPreferredSize(new java.awt.Dimension(50, 50));

    historyjTable.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
    historyjTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null}
      },
      new String [] {
        "Command"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    historyjTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    historyjTable.setColumnSelectionAllowed(false);
    historyjTable.setDoubleBuffered(true);
    historyjTable.setDragEnabled(true);
    historyjTable.setFillsViewportHeight(true);
    historyjTable.setRequestFocusEnabled(false);
    historyjTable.setShowHorizontalLines(false);
    historyjTable.setShowVerticalLines(false);
    historyjScrollPane.setViewportView(historyjTable);

    verticaljSplitPane.setRightComponent(historyjScrollPane);

    horizontaljSplitPane.setTopComponent(verticaljSplitPane);

    jPanel1.setMinimumSize(new java.awt.Dimension(72, 40));
    jPanel1.setPreferredSize(new java.awt.Dimension(249, 50));
    jPanel1.setLayout(new java.awt.BorderLayout());

    commandjScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    commandjScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    commandjScrollPane.setMinimumSize(new java.awt.Dimension(0, 40));
    commandjScrollPane.setPreferredSize(new java.awt.Dimension(0, 0));

    commandjTextField.setFont(new java.awt.Font("Monospaced", 1, 14)); // NOI18N
    commandjTextField.setForeground(new java.awt.Color(0, 51, 153));
    commandjTextField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        commandjTextFieldActionPerformed(evt);
      }
    });
    commandjTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        commandjTextFieldKeyPressed(evt);
      }
    });
    commandjScrollPane.setViewportView(commandjTextField);

    jPanel1.add(commandjScrollPane, java.awt.BorderLayout.CENTER);

    currentDirectoryjLabel.setText("/home/void/jac");
    jPanel1.add(currentDirectoryjLabel, java.awt.BorderLayout.PAGE_START);

    horizontaljSplitPane.setRightComponent(jPanel1);

    javax.swing.GroupLayout commandTabjPanelLayout = new javax.swing.GroupLayout(commandTabjPanel);
    commandTabjPanel.setLayout(commandTabjPanelLayout);
    commandTabjPanelLayout.setHorizontalGroup(
      commandTabjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(commandTabjPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(horizontaljSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
        .addContainerGap())
    );
    commandTabjPanelLayout.setVerticalGroup(
      commandTabjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(commandTabjPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(horizontaljSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
        .addContainerGap())
    );

    add(commandTabjPanel, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  private void commandjTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandjTextFieldActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_commandjTextFieldActionPerformed

  private void commandjTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_commandjTextFieldKeyPressed

    switch (evt.getKeyCode()) {
      case KeyEvent.VK_UP:

        commandjTextField.setText(historyManager.up());
        try {
          historyjTable.setRowSelectionAllowed(true);
          historyjTable.setRowSelectionInterval(historyManager.getHistoryPosition(), historyManager.getHistoryPosition());
        } catch (IllegalArgumentException e) {

        }

        break;

      case KeyEvent.VK_DOWN:
        commandjTextField.setText(historyManager.down());
        try {
          historyjTable.setRowSelectionAllowed(true);
          historyjTable.setRowSelectionInterval(historyManager.getHistoryPosition(), historyManager.getHistoryPosition());
        } catch (IllegalArgumentException e) {
          historyjTable.setRowSelectionAllowed(false);
        }

        break;

      case KeyEvent.VK_TAB:
        System.out.println("" + evt.getKeyCode());
        break;

      case KeyEvent.VK_ENTER: {
        String cmd = commandjTextField.getText();

        System.out.println("cmd: '" + cmd + "'");

        if (!cmd.isEmpty()) {
          historyManager.add(cmd);
          cmdHelper.runCmd(cmd);
          commandjTextField.setText("");
          historyManager.resetPosition();
          currentDirectoryjLabel.setText(cmdHelper.getCmdWorkingDirectory());
        }
      }
      break;

      default:
//        System.out.println("" + evt.getKeyCode());
    }

  }//GEN-LAST:event_commandjTextFieldKeyPressed

  @Override
  public void setVisible(boolean aFlag) {
    super.setVisible(aFlag);
    this.commandjTextField.requestFocusInWindow();
  }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel commandTabjPanel;
  private javax.swing.JScrollPane commandjScrollPane;
  private javax.swing.JTextField commandjTextField;
  private javax.swing.JLabel currentDirectoryjLabel;
  private javax.swing.JScrollPane historyjScrollPane;
  private javax.swing.JTable historyjTable;
  private javax.swing.JSplitPane horizontaljSplitPane;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea outputTextArea;
  private javax.swing.JScrollPane outputjScrollPane;
  private javax.swing.JSplitPane verticaljSplitPane;
  // End of variables declaration//GEN-END:variables
}
