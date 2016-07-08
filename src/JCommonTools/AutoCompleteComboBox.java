package JCommonTools;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.event.*;

public class AutoCompleteComboBox <E> extends JComboBox <E>
{
	   public int caretPos=0;
	   public JTextField inputField=null;
	  
	   public AutoCompleteComboBox () 
	   {
	      super();
	      setEditor(new BasicComboBoxEditor());
	      setEditable(true);
	   }
	  
	   public void setSelectedIndex(int index) 
	   {
	      super.setSelectedIndex(index);
	  
	      inputField.setText(getItemAt(index).toString());
	      //inputField.setSelectionEnd(caretPos + tf.getText().length());
	      inputField.setSelectionEnd(caretPos + inputField.getText().length());
	      inputField.moveCaretPosition(caretPos);
	   }
	  
	   public void setEditor(ComboBoxEditor editor) {
	      super.setEditor(editor);
	      if (editor.getEditorComponent() instanceof JTextField) {
	         inputField = (JTextField) editor.getEditorComponent();
	  
	         inputField.addKeyListener(new KeyAdapter() {
	            public void keyReleased( KeyEvent ev ) {
	               char key=ev.getKeyChar();
	               if (! (Character.isLetterOrDigit(key) || Character.isSpaceChar(key) )) return;
	  
	               caretPos=inputField.getCaretPosition();
	               String text="";
	               try {
	                  text=inputField.getText(0, caretPos);
	               }
	               catch (javax.swing.text.BadLocationException e) {
	                  e.printStackTrace();
	               }
	  
	               for (int i=0; i<getItemCount(); i++) {
	                  String element = getItemAt(i).toString();
	                  if (element.startsWith(text)) {
	                     setSelectedIndex(i);
	                     return;
	                  }
	               }
	            }
	         });
	      }
	   }
}
