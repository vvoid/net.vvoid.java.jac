package net.vvoid.java.jac;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    processBuilder = new ProcessBuilder(System.getProperty("net.vvoid.java.jac.cmdPath", "C:\\Windows\\System32\\cmd.exe"), "/U", "/K");
    //processBuilder = new ProcessBuilder(System.getProperty("net.vvoid.java.jac.cmdPath", "C:\\Windows\\System32\\cmd.exe"));
    processBuilder.inheritIO();
    processBuilder.redirectErrorStream(true);
    processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();

    //cmdOutput = new OutputStreamWriter(process.getOutputStream());
    //cmdOutput = new OutputStreamWriter(process.getOutputStream(), "Cp1250");
    cmdOutput = new OutputStreamWriter(process.getOutputStream(), "850");

    syncPipe = new SyncPipeStream(process.getInputStream(), outputStream);
    syncPipeThread = new Thread(syncPipe);
    syncPipeThread.start();

    //PrintStreamCapturer printStreamCapturer = new PrintStreamCapturer(outputTextArea, new PrintStream(outputStream, true));
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
    String dir = "";

    syncPipe.setActive(false);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(syncPipe.getInputStream()));
    try {
      cmdOutput.append("echo %CD%\n").flush();
      dir = bufferedReader.readLine();
    } catch (IOException ex) {
      Logger.getLogger(RunCmdHelper.class.getName()).log(Level.SEVERE, null, ex);
    }

    syncPipe.setActive(true);
    return dir;
  }

}
