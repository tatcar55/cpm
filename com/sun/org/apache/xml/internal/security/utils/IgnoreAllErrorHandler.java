package com.sun.org.apache.xml.internal.security.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class IgnoreAllErrorHandler implements ErrorHandler {
  static Logger log = Logger.getLogger(IgnoreAllErrorHandler.class.getName());
  
  static final boolean warnOnExceptions = System.getProperty("com.sun.org.apache.xml.internal.security.test.warn.on.exceptions", "false").equals("true");
  
  static final boolean throwExceptions = System.getProperty("com.sun.org.apache.xml.internal.security.test.throw.exceptions", "false").equals("true");
  
  public void warning(SAXParseException paramSAXParseException) throws SAXException {
    if (warnOnExceptions)
      log.log(Level.WARNING, "", paramSAXParseException); 
    if (throwExceptions)
      throw paramSAXParseException; 
  }
  
  public void error(SAXParseException paramSAXParseException) throws SAXException {
    if (warnOnExceptions)
      log.log(Level.SEVERE, "", paramSAXParseException); 
    if (throwExceptions)
      throw paramSAXParseException; 
  }
  
  public void fatalError(SAXParseException paramSAXParseException) throws SAXException {
    if (warnOnExceptions)
      log.log(Level.WARNING, "", paramSAXParseException); 
    if (throwExceptions)
      throw paramSAXParseException; 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\IgnoreAllErrorHandler.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */