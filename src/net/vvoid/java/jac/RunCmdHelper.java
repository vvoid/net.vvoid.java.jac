package net.vvoid.java.jac;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class RunCmdHelper {

  private final ProcessBuilder processBuilder;

  private final OutputStreamWriter cmdOutput;
  private final SyncPipeStream syncPipe;
  private final Thread syncPipeThread;

  private final Boolean debug = false;

  //public RunCmdHelper(OutputStream outputStream, JTextArea outputTextArea) throws IOException {
  public RunCmdHelper(OutputStream outputStream) throws IOException {

    /**
     * with this unicode setup the output encoding is Cp1250 and input encoding is Cp850! YooHoo M$...
     */
    processBuilder = new ProcessBuilder(System.getProperty("net.vvoid.java.jac.cmdPath", "C:\\Windows\\System32\\cmd.exe"), "/U", "/K");
    processBuilder.inheritIO();
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();

    cmdOutput = new OutputStreamWriter(process.getOutputStream(), "850");

    syncPipe = new SyncPipeStream(process.getInputStream(), outputStream);
    syncPipeThread = new Thread(syncPipe);
    syncPipeThread.start();

  }

  public void runCmd(String commandLine) {

    try {
      cmdOutput.append(commandLine).append("\n").flush();
    } catch (IOException ex) {
      Logger.getLogger(RunCmdHelper.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (debug) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        Logger.getLogger(RunCmdHelper.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public void setWorkingDirectory(File dir) {
    processBuilder.directory(dir);
  }

  public File getWorkingDirectory() {
    return processBuilder.directory();
  }

  public String getCmdWorkingDirectory() {
    try {
      String dir = "";

      Charset charset = Charset.forName("Cp1250");

      //syncPipe.setActive(false);

      InputStreamReader isr = new InputStreamReader(syncPipe.getInputStream(), charset);

      cmdOutput.append("echo %CD%\n").flush();
//      while (isr.ready()) {
//        System.out.print(isr.read());
//      }
//      System.out.println("");

//    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(syncPipe.getInputStream(), charset));
//    try {
//      cmdOutput.append("echo %CD%\n").flush();
//      dir = bufferedReader.readLine();
//    } catch (IOException ex) {
//      Logger.getLogger(RunCmdHelper.class.getName()).log(Level.SEVERE, null, ex);
//    }
      syncPipe.setActive(true);
      return dir;
    } catch (IOException ex) {
      Logger.getLogger(RunCmdHelper.class.getName()).log(Level.SEVERE, null, ex);
    }

    return null;
  }

}
