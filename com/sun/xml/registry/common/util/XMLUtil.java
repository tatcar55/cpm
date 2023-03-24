package com.sun.xml.registry.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.UID;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class XMLUtil {
  Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.registry.common");
  
  private static XMLUtil instance = null;
  
  public static XMLUtil getInstance() {
    if (instance == null)
      synchronized (XMLUtil.class) {
        if (instance == null)
          instance = new XMLUtil(); 
      }  
    return instance;
  }
  
  public static String authToken2XXX(String paramString) {
    StringBuffer stringBuffer = new StringBuffer(150);
    StringTokenizer stringTokenizer = new StringTokenizer(paramString);
    while (stringTokenizer.hasMoreTokens()) {
      String str = stringTokenizer.nextToken();
      if (str.indexOf("userID") != -1)
        str = "userID=\"********\""; 
      if (str.indexOf("cred") != -1)
        str = "cred=\"********\""; 
      stringBuffer.append(str);
      stringBuffer.append(" ");
    } 
    return stringBuffer.toString();
  }
  
  public static String authInfo2XXX(String paramString) {
    StringBuffer stringBuffer = new StringBuffer(150);
    StringTokenizer stringTokenizer = new StringTokenizer(paramString, "<>", true);
    boolean bool = false;
    while (stringTokenizer.hasMoreTokens()) {
      String str = stringTokenizer.nextToken();
      if (!bool && str.indexOf("authInfo") != -1) {
        stringBuffer.append(str);
        str = stringTokenizer.nextToken();
        if (str.equals(">")) {
          stringBuffer.append(str);
          str = stringTokenizer.nextToken();
          str = "****************************";
        } 
        bool = true;
      } 
      stringBuffer.append(str);
    } 
    return stringBuffer.toString();
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\commo\\util\XMLUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */