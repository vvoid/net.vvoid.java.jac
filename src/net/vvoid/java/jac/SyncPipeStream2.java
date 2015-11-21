package net.vvoid.java.jac;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SyncPipeStream2 implements Runnable {

  private OutputStream outputStream;
  private InputStream inputStream;
  private Boolean active;

  public OutputStream getOutputStream() {
    return outputStream;
  }

  public void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public Boolean getActive() {
    return active;
  }

  synchronized public void setActive(Boolean active) {
    this.active = active;
  }

  public SyncPipeStream2(InputStream inputStream, OutputStream outputStream) {
    this.inputStream = inputStream;
    this.outputStream = outputStream;
    this.active = true;
  }

  @Override
  public void run() {
    try {
      final byte[] buffer = new byte[1024];
      for (int length = 0; -1 != (length = inputStream.read(buffer));) {
        if (active) {
          outputStream.write(buffer, 0, length);
        } else {
          Thread.sleep(100);
        }
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
