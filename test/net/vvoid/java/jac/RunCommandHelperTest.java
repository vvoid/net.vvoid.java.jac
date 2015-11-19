package net.vvoid.java.jac;

import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author root
 */
public class RunCommandHelperTest {

  public RunCommandHelperTest() {
  }

  @Test
  public void test() throws InterruptedException, IOException {

    JFrame jFrame = new JFrame();
    JTextArea jTextArea = new JTextArea();
    jFrame.add(jTextArea);
    jFrame.setVisible(true);

    //RunCommandHelper commandHelper = new RunCmdHelper();
    //commandHelper.run(new File("c:"), "cmd /c dir c:", jTextArea);
//    RunCmdHelper.run2(new File("c:\\"), "echo Hallo World", jTextArea);
//    RunCmdHelper.run2(new File("c:\\"), "dir \"Program Files (x86)\"", jTextArea);
//    RunCmdHelper.run2(new File("c:\\"), "dir ", jTextArea);
    //RunCmdHelper.run3(new File("c:\\"), "dir Applicaltions \"Program Files\"", jTextArea);
    
    RunCmdHelper cmdHelper = new RunCmdHelper(System.out);
    cmdHelper.runCmd("echo hello world");
    
    

  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }
}
