package net.vvoid.java.jac;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JTextArea;

/**
 *
 * @author root
 */
public class OutputStreamWriterToTextArea extends OutputStreamWriter {

  JTextArea jTextArea;
  OutputStream out;

  public OutputStreamWriterToTextArea(OutputStream out, String charsetName, JTextArea jTextArea) throws UnsupportedEncodingException {
    super(out, charsetName);

    this.jTextArea = jTextArea;

  }

  public JTextArea getjTextArea() {
    return jTextArea;
  }

  public void setjTextArea(JTextArea jTextArea) {
    this.jTextArea = jTextArea;
  }

  public OutputStream getOut() {
    return out;
  }

  public void setOut(OutputStream out) {
    this.out = out;
  }

}
