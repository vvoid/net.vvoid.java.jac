package net.vvoid.java.jac;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author root
 */
public class RunCmdHelper {

  private final ProcessBuilder processBuilder;

  private final OutputStreamWriter cmdOutput;

  private final SyncPipe syncPipe;
  private final Thread syncPipeThread;

  private final Boolean debug = true;

  public RunCmdHelper(OutputStream outputStream) throws IOException {

    processBuilder = new ProcessBuilder(System.getProperty("net.vvoid.java.jac.cmdPath", "C:\\Windows\\System32\\cmd.exe"));

    processBuilder.redirectErrorStream(true);
    processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectOutput(ProcessBuilder.Redirect.PIPE);
    processBuilder.redirectError(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();

    cmdOutput = new OutputStreamWriter(process.getOutputStream());

    syncPipe = new SyncPipe(process.getInputStream(), outputStream);
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

  @Deprecated
  public static void run1(File workingDirectory, String commandLine, JTextArea jTextArea) throws IOException {

    String baseCmd = "";

    if (System.getProperty("os.name").startsWith("Windows")) {
      baseCmd += "cmd /c " + "cd " + workingDirectory.getCanonicalPath() + " && ";
    } else {
      baseCmd += "cd " + workingDirectory.getCanonicalPath() + " && ";
    }

//    String[] tmp = commandLine.split(" ");
//    String cmd = tmp[0];
//    String arg = tmp[1];
//
//    ProcessBuilder processBuilder = new ProcessBuilder("C:\\Windows\\System32\\cmd.exe" , " /c ", arg);
//    processBuilder.directory(workingDirectory);
//    processBuilder.redirectErrorStream(true);
//
//    Process process = processBuilder.start();
    System.out.println("cmd: " + baseCmd + commandLine);

    Process process = Runtime.getRuntime().exec(baseCmd + commandLine);

    //new Thread(new SyncPipe(process.getInputStream(), System.out)).start();
    new Thread(new SyncPipe(process.getInputStream(), new OutputStreamToTextArea(jTextArea))).start();

  }

  @Deprecated
  public static void run2(File workingDirectory, String commandLine, JTextArea jTextArea) throws IOException {

    String baseCmd = "";

    String[] tmp = commandLine.split(" ", 2);
    String cmd = tmp[0];
    String arg = tmp[1];

    ProcessBuilder processBuilder = new ProcessBuilder("C:\\Windows\\System32\\cmd.exe", "/c", cmd, arg);
    processBuilder.directory(workingDirectory);
    processBuilder.inheritIO();
    //processBuilder.redirectErrorStream(true);

    Process process = processBuilder.start();

    System.out.println("cmd: " + baseCmd + commandLine);

//    new Thread(new SyncPipe(process.getInputStream(), System.out)).start();
    //new Thread(new SyncPipe(process.getInputStream(), new OutputStreamToTextArea(jTextArea))).start();
    try {
      Thread.sleep(1000);

    } catch (InterruptedException ex) {
      Logger.getLogger(RunCmdHelper.class
        .getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Deprecated
  public static void run3(File workingDirectory, String commandLine, JTextArea jTextArea) throws IOException {

    String baseCmd = "";

    String[] tmp = commandLine.split(" ", 2);
    String cmd = tmp[0];
    String arg = tmp[1];

    ProcessBuilder processBuilder = new ProcessBuilder("C:\\Windows\\System32\\cmd.exe");
    processBuilder.directory(workingDirectory);
    //processBuilder.inheritIO();
    //processBuilder.redirectErrorStream(true);
    processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();
    OutputStream outputStream = process.getOutputStream();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

    new Thread(new SyncPipe(process.getInputStream(), System.out)).start();
    //new Thread(new SyncPipe(process.getInputStream(), new OutputStreamToTextArea(jTextArea))).start();

    outputStreamWriter.append(commandLine).append("\n").flush();

    System.out.println("cmd: " + baseCmd + commandLine);

    try {
      Thread.sleep(1000);

    } catch (InterruptedException ex) {
      Logger.getLogger(RunCmdHelper.class
        .getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void setWorrkingDirectory(File dir) {
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
