
package br.com.multclin.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


public class EstiloTabelaHeaderMedico implements TableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1, int row, int column) {
        JComponent jcomponent = null;
        
        if(value instanceof String ) {
            jcomponent = new JLabel((String) "   " + value);
            ((JLabel)jcomponent).setHorizontalAlignment(SwingConstants.LEFT );
            ((JLabel)jcomponent).setSize( 36, jcomponent.getWidth() );
            ((JLabel)jcomponent).setPreferredSize( new Dimension(3, jcomponent.getWidth())  );
        } 
        
        jcomponent.setEnabled(true);        
        jcomponent.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        jcomponent.setOpaque(true);
        jcomponent.setBackground( new Color(75,0,130) );//fundo coluna tabela
        jcomponent.setForeground(Color.ORANGE);//cor do texto ao fundo 
        jcomponent.setFont(new Font("Segoe UI", Font.BOLD, 10));//tipo de fornte tamanho cabeçalho
        jcomponent.setToolTipText("Colum No. "+(column+1));
        
        return jcomponent;
    }
    
}