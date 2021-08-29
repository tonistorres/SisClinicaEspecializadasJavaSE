package br.com.multclin.thread;

import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.telas.TelaPrincipal;
import static br.com.multclin.telas.TelaPrincipal.barraProgresso;

import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;

import java.awt.Color;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Dã Torres Primeria coisa a fazer é extender a classe Thread do pacote
 * java lang
 */
public class MinhaThred extends Thread {

    //***********************************************//
    //AULA LOIANE GRONNER                           //
    //https://www.youtube.com/watch?v=v5l30QMKv6c  //
    //********************************************//
    private String nomeThread;
    private int tempo;

    //criar um construtor que irá receber o nome dessa Thread 
    public MinhaThred(String nomeTarefa, int tempo) {

        this.nomeThread = nomeTarefa;
        this.tempo = tempo;

        //para não ficarmos chamando o método start toda vez que criarmos uma thread 
        //colocamos ele dentro do meu construror 
        start();

    }

    //para uma Thread ser executada devemos sobrescrever o método run que tem
    // a seguinte assinatura 
    public void run() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        try {

            int contagemTestar = 8;
            for (int i = 1; i <= contagemTestar; i++) {

                //   JOptionPane.showMessageDialog(null,"valor da conexao dentro do for:"+ recebeConexao);
                if (recebeConexao == true) {
                    // JOptionPane.showMessageDialog(null, "Entrou conexao true\n valor do i -->> "+i);
                    TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setVisible(true);
                    TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setText("Conexão:[" + i + "] Valor:[" + recebeConexao + "]");

                    if (i == contagemTestar) {
                        TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setVisible(false);
                    }

                } else {
                    //         JOptionPane.showMessageDialog(null, "Entrou conexao false\n valor do i -->> "+i);
                    TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setText("Saindo do Sistema em [" + i + "]");
                //    TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setForeground(Color.WHITE);
                    //    TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setBackground(Color.YELLOW);

                    //se o número dividido por dois o resto for zero é par 
//                    if (i % 2 == 0) {
//
//                        painelAlertaCritico.setVisible(true);
//                        lblAlertaCritical.setVisible(true);
//
//                    } else {
//                        painelAlertaCritico.setVisible(false);
//                        lblAlertaCritical.setVisible(false);
//
//                    }
                    if (i == contagemTestar) {
                        new Thread() {

                            public void run() {

                                for (int i = 0; i < 101; i++) {

                                    try {

                                        sleep(25);
                                        barraProgresso.setValue(i);

                                        if (barraProgresso.getValue() <= 5) {
                                            lblVerificacao.setText("Inicializando barra de progresso");

                                            barraProgresso.setVisible(true);
                                            lblVerificacao.setVisible(true);

                                        } else if (barraProgresso.getValue() <= 15) {
                                            lblVerificacao.setText("15% Descarregado");

                                        } else if (barraProgresso.getValue() <= 25) {

                                            lblVerificacao.setText("25% Descarregado");

                                        } else if (barraProgresso.getValue() <= 35) {
                                            lblVerificacao.setText("35% Descarregado");

                                        } else if (barraProgresso.getValue() <= 45) {
                                            lblVerificacao.setText("45% Descarregado");

                                        } else if (barraProgresso.getValue() <= 55) {
                                            lblVerificacao.setText("55% Descarregado");

                                        } else if (barraProgresso.getValue() <= 65) {
                                            lblVerificacao.setText("65% Descarregado");

                                        } else if (barraProgresso.getValue() <= 75) {
                                            lblVerificacao.setText("75% Descarregado");

                                        } else if (barraProgresso.getValue() <= 85) {
                                            lblVerificacao.setText("85% Descarregado");

                                        } else if (barraProgresso.getValue() <= 95) {
                                            lblVerificacao.setText("95% Descarregado");

                                        } else {
                                            lblVerificacao.setText("Encerrado com sucesso!");
                                            System.exit(0);//sair do sistema
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                                    }

                                }
                            }
                        }.start();// iniciando a Thread
                    }
                }

                Thread.sleep(tempo);

            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();

        }

        System.out.println("Fim Execução " + "<<-->>" + nomeThread);

    }

}
