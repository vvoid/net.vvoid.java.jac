package net.vvoid.java.jac;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

/**
 *
 * @author root
 */
public class PrintStreamToTextArea extends PrintStream {

  JTextArea jTextArea;

//  public PrintStreamToTextArea(JTextArea jTextArea) {
//    this.jTextArea = jTextArea;
//  }
//  @Override
//  public void write(int b) throws IOException {
//    jTextArea.append(String.valueOf((char) b));
//    jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
//  }
  public PrintStreamToTextArea(OutputStream out) {
    super(out);
  }

}
