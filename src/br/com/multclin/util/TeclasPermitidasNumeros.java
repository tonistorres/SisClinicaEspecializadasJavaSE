
package br.com.multclin.util;

// Contribuição:https://www.youtube.com/watch?v=0kpCsqqQuqs

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class TeclasPermitidasNumeros extends PlainDocument {
    
    @Override
    
    public void insertString(int offset, String srt,javax.swing.text.AttributeSet attr)throws BadLocationException {
    super.insertString(offset, srt.replaceAll("[^0-9]", ""),attr);
    
    }
    public void replace(int offset, String srt,javax.swing.text.AttributeSet attr)throws BadLocationException {
    super.insertString(offset, srt.replaceAll("[^0-9]", ""),attr);
    
    }
}
