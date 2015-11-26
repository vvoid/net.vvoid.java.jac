package net.vvoid.java.jac;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class SyncPipeWriter implements Runnable {

  private OutputStreamWriter outputStreamWriter;
  private InputStreamReader inputStreamReader;
  private Boolean active;

  public OutputStreamWriter getOutputStreamWriter() {
    return outputStreamWriter;
  }

  public void setOutputStreamWriter(OutputStreamWriter outputStream) {
    this.outputStreamWriter = outputStream;
  }

  public InputStreamReader getInputStreamReader() {
    return inputStreamReader;
  }

  public void setInputStreamReader(InputStreamReader inputStream) {
    this.inputStreamReader = inputStream;
  }

  public Boolean getActive() {
    return active;
  }

  synchronized public void setActive(Boolean active) {
    this.active = active;
  }

  public SyncPipeWriter(InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter) {
    this.inputStreamReader = inputStreamReader;
    this.outputStreamWriter = outputStreamWriter;
    this.active = true;
  }

  public SyncPipeWriter(InputStream inputStream, OutputStream outputStream) throws UnsupportedEncodingException {
    this.inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-15");
    this.outputStreamWriter = new OutputStreamWriter(outputStream, "ISO-8859-15");
    //this.inputStreamReader = new InputStreamReader(inputStream, "UTF-16");
    //this.outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
    this.active = true;
  }

  public SyncPipeWriter(InputStream inputStream, OutputStreamWriter outputStreamWriter) throws UnsupportedEncodingException {
    this.inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-15");
    //this.inputStreamReader = new InputStreamReader(inputStream, "UTF-16");
    this.outputStreamWriter = outputStreamWriter;
    this.active = true;
  }

  public SyncPipeWriter(InputStreamReader inputStreamReader, OutputStream outputStream) throws UnsupportedEncodingException {
    this.inputStreamReader = inputStreamReader;
    this.outputStreamWriter = new OutputStreamWriter(outputStream, "ISO-8859-15");
    //this.outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-16");
    this.active = true;
  }

  @Override
  public void run() {
    System.out.println("inputStreamReader:  " + inputStreamReader.getEncoding());
    System.out.println("outputStreamWriter: " + outputStreamWriter.getEncoding());

    try {
      final char[] buffer = new char[1024];
      //while (true) {
      for (int length = 0; -1 != (length = inputStreamReader.read(buffer));) {
        if (active) {
          outputStreamWriter.write(buffer, 0, length);
          outputStreamWriter.flush();
        } else {
          Thread.sleep(100);
        }
      }
//        Thread.sleep(100);
//      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
