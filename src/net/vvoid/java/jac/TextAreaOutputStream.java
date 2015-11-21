package net.vvoid.java.jac;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextArea;

/**
 * TextAreaOutputStream creates an outputstream that will output to the given textarea. Useful in setting System.out
 */
public class TextAreaOutputStream extends OutputStream {

  public static final int DEFAULT_BUFFER_SIZE = 32;

  JTextArea mText;
  byte mBuf[];
  int mLocation;

  public TextAreaOutputStream(JTextArea component) {
    this(component, DEFAULT_BUFFER_SIZE);
  }

  public TextAreaOutputStream(JTextArea component, int bufferSize) {
    mText = component;
    if (bufferSize < 1) {
      bufferSize = 1;
    }
    mBuf = new byte[bufferSize];
    mLocation = 0;
  }

  @Override
  public void write(int arg0) throws IOException {
    //System.err.println("arg = "  + (char) arg0);
    mBuf[mLocation++] = (byte) arg0;
    if (mLocation == mBuf.length) {
      flush();
    }
  }

  @Override
  public void flush() throws UnsupportedEncodingException {
    String txt = new String(mBuf, 0, mLocation, "UTF-8");
    mText.append(txt);
    mLocation = 0;
    /*
     try {
     Thread.sleep(1);
     }
     catch (Exception ex) {}
     }
     */
  }
}
