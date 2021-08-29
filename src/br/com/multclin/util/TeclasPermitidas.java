
package br.com.multclin.util;

// Contribuição:https://www.youtube.com/watch?v=0kpCsqqQuqs

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class TeclasPermitidas extends PlainDocument {
    
    @Override
    
    public void insertString(int offset, String srt,javax.swing.text.AttributeSet attr)throws BadLocationException {
    super.insertString(offset, srt.replaceAll("[^a-z|^A-Z|^ |^ç|^ã]", ""),attr);
    
    }
    public void replace(int offset, String srt,javax.swing.text.AttributeSet attr)throws BadLocationException {
    super.insertString(offset, srt.replaceAll("[^a-z|^A-Z|^ |^Ç|Ã]", ""),attr);
    
    }
}
