package com.sun.xml.registry.common.util;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Utility {
  public static final String LOGGING_DOMAIN = "javax.enterprise.resource.webservices.registry";
  
  Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.registry.common");
  
  private static Utility instance = null;
  
  private String jaxrHome = null;
  
  public void setJAXRHome(String paramString) {
    this.jaxrHome = paramString;
  }
  
  public String getJAXRHome() {
    this.logger.finest("getJAXRHome() called");
    if (this.jaxrHome == null)
      try {
        this.jaxrHome = System.getProperty("JAXR_HOME");
        if (this.jaxrHome == null)
          throw new NullPointerException(); 
      } catch (NullPointerException nullPointerException) {
        throw new RuntimeException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("Utility:JAXR_HOME_must_be_set"), nullPointerException);
      }  
    return this.jaxrHome;
  }
  
  public String getContextRoot() {
    String str = "c:/jaxr";
    try {
      InitialContext initialContext = new InitialContext();
      str = (String)initialContext.lookup("java:comp/env/jaxr-service/contextRoot");
    } catch (NamingException namingException) {
      System.getProperty("JAXR_HOME", "c:/jaxr");
    } 
    return str;
  }
  
  public String getContextRootURLString() {
    String str = getContextRoot();
    if (!str.startsWith("http", 0) && !str.startsWith("file", 0))
      str = "file:///" + str; 
    return str;
  }
  
  public URL getURL(String paramString) throws MalformedURLException {
    null = null;
    String str = getContextRootURLString() + paramString;
    return new URL(str);
  }
  
  public static Utility getInstance() {
    if (instance == null)
      synchronized (Utility.class) {
        if (instance == null)
          instance = new Utility(); 
      }  
    return instance;
  }
  
  public static String generateUUID() {
    String str = null;
    try {
      str = InetAddress.getLocalHost() + (new UID()).toString();
    } catch (UnknownHostException unknownHostException) {
      unknownHostException.printStackTrace();
    } 
    return str;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\commo\\util\Utility.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */