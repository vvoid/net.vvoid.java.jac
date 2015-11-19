package net.vvoid.java.jac;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 *
 * @author root
 */
public class OutputStreamToTextArea extends OutputStream {

  JTextArea jTextArea;

  public OutputStreamToTextArea(JTextArea jTextArea) {
    this.jTextArea = jTextArea;
  }

  @Override
  public void write(int b) throws IOException {
    jTextArea.append(String.valueOf((char) b));
    jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
  }

}
