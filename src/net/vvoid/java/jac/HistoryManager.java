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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author root
 */
public class HistoryManager extends AbstractTableModel {

  public static final File historyFile = new File(System.getProperty("user.home"), ".jac.history");

  public final Integer maxEntries;

  List<String> commandHistory = new ArrayList<>();
  Integer historyPosition = Positions.ILLEGAL.index;

  public HistoryManager() {
    maxEntries = 5;
    init();
  }

  public HistoryManager(Integer maxEntries) throws IOException {
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

    historyPosition = Positions.CURRENT.index;

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
    if (!(commandHistory.size() < maxEntries)) {
      commandHistory.remove(0);
    }
    commandHistory.add(cmd);

    this.fireTableDataChanged();
  }

  public void del(int i) {
    commandHistory.remove(i);
  }

  public enum Positions {

    ILLEGAL(-666),
    OLDEST(0),
    CURRENT(Integer.MIN_VALUE);

    private final Integer index;

    public final Integer index() {
      return index;
    }

    Positions(Integer integer) {
      index = integer;
    }
  }

  public String up() {
    String result = "";

    if (Objects.equals(historyPosition, Positions.ILLEGAL.index)) {
      result = "";

    } else if (historyPosition == Positions.CURRENT.index) {
      historyPosition = commandHistory.size() - 1;
      result = commandHistory.get(historyPosition);

    } else if (historyPosition > (commandHistory.size() - 1)) {
      historyPosition = commandHistory.size() - 1;
      result = commandHistory.get(historyPosition);

    } else if (historyPosition <= 0) {
      historyPosition = 0;
      result = commandHistory.get(0);

    } else if (Objects.equals(historyPosition, Positions.OLDEST.index)) {
      result = commandHistory.get(0);

    } else {
      historyPosition--;
      result = commandHistory.get(historyPosition);
    }

    //System.out.println("" + commandHistory + " -> " + result + " @ " + historyPosition);
    return result;
  }

  public String down() {
    String result = "";

    if (Objects.equals(historyPosition, Positions.ILLEGAL.index)) {
      result = "";

    } else if (historyPosition == Positions.CURRENT.index) {
      result = "";

    } else if (historyPosition >= (commandHistory.size() - 1)) {
      historyPosition = Positions.CURRENT.index;
      result = "";

    } else if (historyPosition <= 0) {
      historyPosition = 1;
      result = commandHistory.get(1);

    } else if (Objects.equals(historyPosition, Positions.OLDEST.index)) {
      result = commandHistory.get(0);

    } else {
      historyPosition++;
      result = commandHistory.get(historyPosition);
    }

    //System.out.println("" + commandHistory + " -> " + result + " @ " + historyPosition);
    return result;
  }

  public void resetPosition() {
    historyPosition = Positions.CURRENT.index;
  }

  public void writeHistoryFile() throws IOException {

    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(historyFile))) {
      for (String cmd : commandHistory) {
        bufferedWriter.write(cmd);
        bufferedWriter.newLine();
      }
      bufferedWriter.flush();
    }

  }

  public Integer getHistoryPosition() {
    return historyPosition;
  }

}
