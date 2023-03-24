package org.jvnet.fastinfoset.sax.helpers;

import org.jvnet.fastinfoset.sax.EncodingAlgorithmContentHandler;
import org.jvnet.fastinfoset.sax.PrimitiveTypeContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class FastInfosetDefaultHandler extends DefaultHandler implements LexicalHandler, EncodingAlgorithmContentHandler, PrimitiveTypeContentHandler {
  public void comment(char[] ch, int start, int length) throws SAXException {}
  
  public void startCDATA() throws SAXException {}
  
  public void endCDATA() throws SAXException {}
  
  public void startDTD(String name, String publicId, String systemId) throws SAXException {}
  
  public void endDTD() throws SAXException {}
  
  public void startEntity(String name) throws SAXException {}
  
  public void endEntity(String name) throws SAXException {}
  
  public void octets(String URI, int algorithm, byte[] b, int start, int length) throws SAXException {}
  
  public void object(String URI, int algorithm, Object o) throws SAXException {}
  
  public void booleans(boolean[] b, int start, int length) throws SAXException {}
  
  public void bytes(byte[] b, int start, int length) throws SAXException {}
  
  public void shorts(short[] s, int start, int length) throws SAXException {}
  
  public void ints(int[] i, int start, int length) throws SAXException {}
  
  public void longs(long[] l, int start, int length) throws SAXException {}
  
  public void floats(float[] f, int start, int length) throws SAXException {}
  
  public void doubles(double[] d, int start, int length) throws SAXException {}
  
  public void uuids(long[] msblsb, int start, int length) throws SAXException {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\helpers\FastInfosetDefaultHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */