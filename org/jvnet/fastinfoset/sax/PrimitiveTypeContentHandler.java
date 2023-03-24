package org.jvnet.fastinfoset.sax;

import org.xml.sax.SAXException;

public interface PrimitiveTypeContentHandler {
  void booleans(boolean[] paramArrayOfboolean, int paramInt1, int paramInt2) throws SAXException;
  
  void bytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws SAXException;
  
  void shorts(short[] paramArrayOfshort, int paramInt1, int paramInt2) throws SAXException;
  
  void ints(int[] paramArrayOfint, int paramInt1, int paramInt2) throws SAXException;
  
  void longs(long[] paramArrayOflong, int paramInt1, int paramInt2) throws SAXException;
  
  void floats(float[] paramArrayOffloat, int paramInt1, int paramInt2) throws SAXException;
  
  void doubles(double[] paramArrayOfdouble, int paramInt1, int paramInt2) throws SAXException;
  
  void uuids(long[] paramArrayOflong, int paramInt1, int paramInt2) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\PrimitiveTypeContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */