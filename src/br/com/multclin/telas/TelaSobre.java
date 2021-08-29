/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multclin.telas;

/**
 *
 * @author DaTorres
 */
public class TelaSobre extends javax.swing.JInternalFrame {

    /**
     * Código Mestre Chimura
     */
    private static TelaSobre instance = null;

    public static TelaSobre getInstance() {

        if (instance == null) {

            instance = new TelaSobre();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaSobre() {
        initComponents();
        personalizacaoFrontEnd();
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelSobreSistema = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelTexto = new javax.swing.JTextPane();
        lblMulticlin = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Sobre o Sistema");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        PanelSobreSistema.setBackground(new java.awt.Color(255, 255, 255));

        PanelTexto.setEditable(false);
        PanelTexto.setBorder(javax.swing.BorderFactory.createTitledBorder("Informação Sistema:"));
        PanelTexto.setText("Compact Clinical System 1.0 - É um módulo compacto de agendamento de consultas, desenvolvido em linguagem de programação java SE, com banco de dado hospedado em nuvem.\n\nBreve escopo descritivo do sistema:\n\n* Cadastro de Usuários do Sistema ;\n* Cadastro de Telefones (Celulares);\n* Cadastro de Médicos ;\n* Cadastro de Especialidades;\n* Cadastro de Pacientes  ;\n*  Agendamento;\n*  Funcionalidade de Envio de Email em (real time)\n    Observação: Essa funcionalidade é tercerizada, equivale dizer que utilizamos o servidor \n    de email do gmail, o que leva entender que a empresa(google) poderá um dia cobrar pelo\n    serviço que até o momento é gratúito.\n\n*  Nuvem utilizada HOSTGATOR(Servidor de Hospedagem);\n   Observação: Serviço terceirizado, servidor de hospedagem esse onera despesas que deverá ser\n   de responsabilidade do Contratante.\n\n* A versão do Java Utilizada é a 8 que não onera despesas porém versões superiores do Java é cobrada \n   uma licença que deverá ser comprada na empresa detentora do Java.\n\n");
        jScrollPane1.setViewportView(PanelTexto);

        lblMulticlin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Compact Clinical System 1.0");

        javax.swing.GroupLayout PanelSobreSistemaLayout = new javax.swing.GroupLayout(PanelSobreSistema);
        PanelSobreSistema.setLayout(PanelSobreSistemaLayout);
        PanelSobreSistemaLayout.setHorizontalGroup(
            PanelSobreSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
            .addGroup(PanelSobreSistemaLayout.createSequentialGroup()
                .addGroup(PanelSobreSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMulticlin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanelSobreSistemaLayout.setVerticalGroup(
            PanelSobreSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSobreSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMulticlin, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelSobreSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelSobreSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        setBounds(0, 0, 352, 460);
    }// </editor-fold>//GEN-END:initComponents
private void personalizacaoFrontEnd(){
lblMulticlin .setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/logoHannaMenor_sem_Slogam.jpeg")));

}
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        /**
         * No Evento do Formulário (closed)- A instância torna a reber valores
         * nulos
         */

        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelSobreSistema;
    private javax.swing.JTextPane PanelTexto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMulticlin;
    // End of variables declaration//GEN-END:variables
}
