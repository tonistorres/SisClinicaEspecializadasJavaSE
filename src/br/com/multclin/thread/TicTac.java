package br.com.multclin.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTac {

    boolean tic;

    synchronized void tic(boolean estaExecutando) {

        if (!estaExecutando) {
            tic = true;
            notify();
            return;

        }
        System.out.print("Tic");
        tic = true;

        notify();

        try {
            while (tic) {
                wait();
            }
        } catch (InterruptedException ex) {
            ex.getMessage();
            ex.printStackTrace();

        }
    }

    synchronized void tac(boolean estaExecutando) {

        if (!estaExecutando) {
            tic = false;
            notify();
            return;

        }
        System.out.println("Tac");
        tic = false;

        notify();

        try {
            while (!tic) {
                wait();
            }
        } catch (InterruptedException ex) {
            ex.getMessage();
            ex.printStackTrace();

        }
    }

}
