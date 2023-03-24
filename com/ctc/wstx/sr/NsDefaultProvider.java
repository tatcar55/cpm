package com.ctc.wstx.sr;

import javax.xml.stream.XMLStreamException;

public interface NsDefaultProvider {
  boolean mayHaveNsDefaults(String paramString1, String paramString2);
  
  void checkNsDefaults(InputElementStack paramInputElementStack) throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\sr\NsDefaultProvider.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */