package org.jvnet.fastinfoset.sax;

import org.xml.sax.Attributes;

public interface EncodingAlgorithmAttributes extends Attributes {
  String getAlgorithmURI(int paramInt);
  
  int getAlgorithmIndex(int paramInt);
  
  Object getAlgorithmData(int paramInt);
  
  String getAlpababet(int paramInt);
  
  boolean getToIndex(int paramInt);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\EncodingAlgorithmAttributes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */