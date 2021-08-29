/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multclin.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author X552E
 */
public class executavel {

    public static void main(String[] args) {
    
        TicTac tt=new TicTac();
        ThreadTicTac tic=new ThreadTicTac("Tic", tt);
        ThreadTicTac tac=new ThreadTicTac("Tac", tt);
        
        try {
            tic.t.join();
            tac.t.join();
        } catch (InterruptedException ex) {
            ex.getMessage();
            ex.printStackTrace();
                    
        }
        
    }
}
