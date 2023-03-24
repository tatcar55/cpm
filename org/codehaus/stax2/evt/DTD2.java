package org.codehaus.stax2.evt;

import javax.xml.stream.events.DTD;

public interface DTD2 extends DTD {
  String getRootName();
  
  String getSystemId();
  
  String getPublicId();
  
  String getInternalSubset();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\evt\DTD2.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */