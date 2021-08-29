/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multclin.util;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class runCNPJ {

    public static void main(String[] args) {

        String pegaCNPJ = "01.612.326/0001-32";
        String retornaSemMask=MetodoStaticosUtil.trataMaskParaCNPJ(pegaCNPJ);
        JOptionPane.showMessageDialog(null, "Retorno Sem Mask-->> " + retornaSemMask);
        boolean resultValidacao = MetodoStaticosUtil.isCNPJ(retornaSemMask);
        JOptionPane.showMessageDialog(null, "Resultado validacao : " + resultValidacao);

    }

}
