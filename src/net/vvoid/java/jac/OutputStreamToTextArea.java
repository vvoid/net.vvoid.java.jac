package net.vvoid.java.jac;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import javax.swing.JTextArea;

/**
 *
 * @author root
 */
public class OutputStreamToTextArea extends OutputStream {

  JTextArea jTextArea;

  private final BitSet bit8 = new BitSet(8);

  public OutputStreamToTextArea(JTextArea jTextArea) {
    this.jTextArea = jTextArea;

    bit8.flip(7);
  }

  int byteCounter = 0;

  @Override
  public void write(int intValue) throws IOException {
    byte[] buffer1 = null;
    if (intValue != 0) {
      byte b = (byte) intValue;
      System.out.println("b: " + intValue);

      Integer i = intValue;

      buffer1 = new byte[]{
        //        (byte) (intValue >> 24),
        //        (byte) (intValue >> 18),
        //(byte) (intValue >> 8),
        (byte) (intValue >> 0)
      };

      System.out.print("bin: ");
      for (byte c : buffer1) {
        System.out.format("0x%X_", c);
      }
      System.out.println("");

      System.out.println("UTF16: '" + new String(buffer1, "UTF16") + "'");
      System.out.println("UTF-8: '" + new String(buffer1, "UTF-8") + "'");
      System.out.println("Cp1250: '" + new String(buffer1, "Cp1250") + "'");
      System.out.println("Cp850: '" + new String(buffer1, "Cp850") + "'");

    }
//    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
//    byteBuffer.putInt(b);
//    
//    for (byte c : byteBuffer.array()) {
//        System.out.format("0x%X:", c );
//    }
//    System.out.println("");
//    ByteBuffer byteBuffer = ByteBuffer.wrap(buffer1);
//    Charset charset = Charset.forName("Cp1250");
//    CharsetDecoder charsetDecoder = charset.newDecoder();
//    CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
//    
//    System.out.println("convert: " + charBuffer.toString());
//    String txt = "";
//    Character c = Character.valueOf((char) b);
//    System.out.println("" + c);
//
//    BigInteger bInteger = BigInteger.valueOf(b);
//    System.out.println(""  + buffer.length);
//    InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(buffer), "Cp1250");
    //txt = inputStreamReader.
    //jTextArea.append(String.valueOf(inputStreamReader.read()));
    //jTextArea.append(String.valueOf((char) b));
    //jTextArea.append(new String(buffer, "Cp1250"));
    //jTextArea.append(new String(buffer1, "Cp1250"));
    //if (processBytes((byte) intValue) != null) {
    if (intValue != 0) {
      //String txt2 = new String(getByteArray(bytes), "UTF-8");
      String txt2 = new String(getByteArray(bytes), "Cp1250");
      txt2 = new String(buffer1, "Cp1250");
      jTextArea.append(txt2);
      System.out.println("txt2:" + "'" + txt2 + "'");
      jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
    }

    System.out.println("");
  }

  private ArrayList<Byte> bytes = new ArrayList<>();
  private int sequenceLength = 0;
  private BitSet allBits;

  public ArrayList<Byte> processBytes(Byte b) {
    BitSet bits = BitSet.valueOf(new byte[]{(byte) b});

    if (b == 0) {
      return null;
    }

    if (!bits.get(7)) {
      bytes = new ArrayList<Byte>();
      bytes.add(b);
      sequenceLength = 1;
      return bytes;
    } else {

      if (bits.get(7) && !bits.get(6)) {
        // continuation
        bytes.add(b);
        if (bytes.size() >= sequenceLength) {
          return bytes;
        }

      } else {

        if (bits.get(7) && bits.get(6) && !bits.get(5)) {
          // start of 2 byte sequnce
          bytes = new ArrayList<>();
          bytes.add(b);
          sequenceLength = 2;

        } else if (bits.get(7) && bits.get(6) && bits.get(5) && !bits.get(4)) {
          // start of 3 byte sequnce
          bytes = new ArrayList<>();
          bytes.add(b);
          sequenceLength = 3;

        } else if (bits.get(7) && bits.get(6) && bits.get(5) && bits.get(4) && !bits.get(3)) {
          // start of 4 byte sequnce
          bytes = new ArrayList<>();
          bytes.add(b);
          sequenceLength = 4;

        } else {
          throw new RuntimeException("illegal sequence");
        }
      }

    }

    return null;
  }

  private byte[] getByteArray(List<Byte> bytes) {
    byte[] buf = new byte[bytes.size()];

    for (int i = 0; i < bytes.size(); i++) {
      buf[i] = bytes.get(i);
    }

    return buf;
  }
}
