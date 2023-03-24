package com.sun.org.apache.xml.internal.security.transforms.implementations;

import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xpath.internal.CachedXPathAPI;
import com.sun.org.apache.xpath.internal.XPathContext;
import org.w3c.dom.Node;

public class FuncHereContext extends XPathContext {
  private FuncHereContext() {}
  
  public FuncHereContext(Node paramNode) {
    super(paramNode);
  }
  
  public FuncHereContext(Node paramNode, XPathContext paramXPathContext) {
    super(paramNode);
    try {
      this.m_dtmManager = paramXPathContext.getDTMManager();
    } catch (IllegalAccessError illegalAccessError) {
      throw new IllegalAccessError(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + illegalAccessError.getMessage() + "\"");
    } 
  }
  
  public FuncHereContext(Node paramNode, CachedXPathAPI paramCachedXPathAPI) {
    super(paramNode);
    try {
      this.m_dtmManager = paramCachedXPathAPI.getXPathContext().getDTMManager();
    } catch (IllegalAccessError illegalAccessError) {
      throw new IllegalAccessError(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + illegalAccessError.getMessage() + "\"");
    } 
  }
  
  public FuncHereContext(Node paramNode, DTMManager paramDTMManager) {
    super(paramNode);
    try {
      this.m_dtmManager = paramDTMManager;
    } catch (IllegalAccessError illegalAccessError) {
      throw new IllegalAccessError(I18n.translate("endorsed.jdk1.4.0") + " Original message was \"" + illegalAccessError.getMessage() + "\"");
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\implementations\FuncHereContext.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */