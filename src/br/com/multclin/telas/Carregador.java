/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multclin.telas;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

/**
 *
 * @author hugov
 */
public class Carregador extends JDesktopPane{
   // Image img = new ImageIcon(getClass().getResource("")).getImage();
   Image img = new ImageIcon(getClass().getResource("/imagens/carregador/fundoBranco.jpg")).getImage();
    
    
    @Override
    public void paintChildren(Graphics g){
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        super.paintChildren(g);
    }
}
