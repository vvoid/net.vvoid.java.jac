package net.vvoid.java.jac;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author root
 */
public class Cfg {

  public static Cfg cfg;

  public String javaPath = "net.vvoid.java.jac";
  public String homeDir = System.getProperty("user.home");
  public String cfgDir = homeDir;
  public String cfgFile = homeDir + "/" + ".jac.json";
  public String historyFilePrefix = homeDir + "/" + ".jac.history.";

  public List<String> tabs = new ArrayList<>();

  private static final ObjectMapper mapper = new ObjectMapper();

  public Cfg readCfg() throws IOException {

    File file = new File(cfgFile);
    if (file.exists() && file.canRead() && file.isFile()) {
      cfg = mapper.readValue(file, Cfg.class);
      if (cfg == null) {
        return Cfg.newDefaultCfg();
      }
    } else {
      return Cfg.newDefaultCfg();
    }

    return cfg;
  }

  public void saveCfg() throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(cfgFile), Cfg.cfg);
  }

  public static Cfg newDefaultCfg() throws IOException {
    Cfg newCfg = new Cfg();
    Cfg.cfg = newCfg;
    newCfg.tabs.add("0");

    newCfg.saveCfg();
    newCfg = newCfg.readCfg();
    Cfg.cfg = newCfg;
    return newCfg;
  }

}
