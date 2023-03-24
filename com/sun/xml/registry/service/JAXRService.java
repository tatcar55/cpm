package com.sun.xml.registry.service;

import com.sun.xml.registry.common.ConnectionFactoryImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;

public class JAXRService {
  Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.registry.service");
  
  private static JAXRService instance;
  
  public static JAXRService getInstance() {
    if (instance == null)
      instance = new JAXRService(); 
    return instance;
  }
  
  void startService() {
    try {
      ConnectionFactoryImpl connectionFactoryImpl = new ConnectionFactoryImpl();
      InitialContext initialContext = new InitialContext();
      initialContext.rebind("javax.xml.registry.ConnectionFactory", connectionFactoryImpl);
    } catch (Exception exception) {
      this.logger.log(Level.SEVERE, exception.getMessage(), exception);
    } 
  }
  
  void stopService() {
    try {
      InitialContext initialContext = new InitialContext();
      initialContext.unbind("JAXRConnectionFactory");
      initialContext.close();
    } catch (Exception exception) {
      this.logger.log(Level.SEVERE, exception.getMessage(), exception);
    } 
  }
  
  public static void main(String[] paramArrayOfString) {
    if (paramArrayOfString.length == 0)
      showUsage(); 
    JAXRService jAXRService = getInstance();
    String str = paramArrayOfString[0];
    if (str.equals("-startService")) {
      jAXRService.startService();
    } else if (str.equals("-stopService")) {
      jAXRService.stopService();
    } else {
      showUsage();
    } 
  }
  
  private static void showUsage() {
    System.err.println("Must specify -startService or -stopService");
    System.exit(-1);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\service\JAXRService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */