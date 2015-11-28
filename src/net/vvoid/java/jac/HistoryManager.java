package net.vvoid.java.jac;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author root
 */
public class HistoryManager extends AbstractTableModel {

  public File historyFile;

  public final Integer maxEntries;

  List<String> commandHistory = new ArrayList<>();
  Integer historyPosition;

  public HistoryManager(String tabName) {
    this.historyFile = new File(Cfg.cfg.historyFilePrefix + tabName);
    maxEntries = 5000;
    init();
  }

  public HistoryManager(String tabName, Integer maxEntries) throws IOException {
    this.historyFile = new File(Cfg.cfg.historyFilePrefix + tabName);
    this.maxEntries = maxEntries;
    init();
  }

  private void init() {

    FileReader fileReader = null;

    if (historyFile.exists() && historyFile.isFile() && historyFile.canRead() && historyFile.canWrite()) {
      try {
        fileReader = new FileReader(historyFile);
      } catch (FileNotFoundException ex) {
        Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
      }

    } else {
      try {
        historyFile.createNewFile();
        fileReader = new FileReader(historyFile);
      } catch (IOException ex) {
        Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
      }

    }

    BufferedReader bufferedReader = new BufferedReader(fileReader);

    String line;
    try {
      while ((line = bufferedReader.readLine()) != null) {
        commandHistory.add(line);

        if (commandHistory.size() >= maxEntries) {
          break;
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(HistoryManager.class.getName()).log(Level.SEVERE, null, ex);
    }

    historyPosition = commandHistory.size() - 1;
  }

  @Override
  public String getColumnName(int column) {
    return "History";
  }

  @Override
  public int getRowCount() {
    return commandHistory.size();
  }

  @Override
  public int getColumnCount() {
    return 1;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    return commandHistory.get(rowIndex);
  }

  public void add(String cmd) {

    if (!commandHistory.isEmpty() && commandHistory.get(commandHistory.size() - 1).contentEquals(cmd)) {
      return;
    }

    if (commandHistory.size() >= maxEntries) {
      commandHistory.remove(0);
    }
    commandHistory.add(cmd);

    this.fireTableDataChanged();
  }

  public void del(int i) {
    commandHistory.remove(i);
  }

  public String getCurrent() {
    return commandHistory.get(historyPosition);
  }

  public String upOrDown(boolean up) {
    String result = "";
    Integer newPosition = historyPosition;
    if (up) {
      newPosition--;
    } else {
      newPosition++;
    }

    if (newPosition <= 0) {
      // underflow
      historyPosition = 0;
      result = commandHistory.get(0);

    } else if (newPosition >= (commandHistory.size() - 1)) {
      // overflow
      historyPosition = commandHistory.size() - 1;
      result = commandHistory.get(commandHistory.size() - 1);

    } else {
      historyPosition = newPosition;
      result = commandHistory.get(historyPosition);

    }

    System.out.println("" + result);
    return result;
  }

  public String up() {
    return upOrDown(true);
  }

  public String down() {
    return upOrDown(false);
  }

  public void resetPosition() {
    historyPosition = commandHistory.size() - 1;
  }

  public void writeHistoryFile() throws IOException {

    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(historyFile))) {
      for (String cmd : commandHistory) {
        bufferedWriter.write(cmd);
        bufferedWriter.newLine();
      }
      bufferedWriter.close();
    }

  }

  public void close() throws IOException {
    writeHistoryFile();
    historyFile = null;
  }

  public void rename(String tabName) {
    File oldFile = historyFile;
    File newFile = new File(Cfg.cfg.historyFilePrefix + tabName);
    oldFile.renameTo(newFile);
    this.historyFile = newFile;
  }

  public Integer getHistoryPosition() {
    return historyPosition;
  }

  public synchronized String complete(String cmd, int position, boolean backward) {
    String result = "";
    int oldHistory = historyPosition;

    String matchTo = cmd.substring(0, position);
    //int entries = commandHistory.size();

    System.out.println("" + historyPosition + " -> " + result);

    if (commandHistory.get(historyPosition).contentEquals(cmd)) {
      historyPosition = next(historyPosition, backward);
    }

    for (int i = historyPosition; i >= 0; i = next(i, backward)) {
      if (i <= 0) {
        historyPosition = oldHistory;
        result = commandHistory.get(historyPosition);
        break;
      } else if (i >= commandHistory.size()) {
        historyPosition = oldHistory;
        result = commandHistory.get(historyPosition);
        break;
      }

      String entry = commandHistory.get(i);

      if (entry.startsWith(matchTo)) {
        historyPosition = i;
        result = entry;
        break;
      }

    }
    System.out.println("" + historyPosition + " -> " + result);
    return result;
  }

  public Integer next(Integer position, boolean backward) {
    if (backward) {
      return --position;
    } else {
      return ++position;
    }
  }

}
