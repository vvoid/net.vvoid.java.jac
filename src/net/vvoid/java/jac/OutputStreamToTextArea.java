package net.vvoid.java.jac;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;

/**
 * TDODO: clean up
 *
 * @author root
 */
public class OutputStreamToTextArea extends OutputStream {

  private final JTextArea jTextArea;
  private String encoding = "Cp1250";

  public OutputStreamToTextArea(JTextArea jTextArea) {
    this.jTextArea = jTextArea;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  @Override
  public void write(int intValue) throws IOException {

    byte[] buffer = new byte[]{(byte) intValue};

    if (intValue != 0) {
      String txt = new String(buffer, encoding);
      jTextArea.append(txt);
      jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
    }

  }

}
