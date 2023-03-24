package com.sun.xml.rpc.spi.runtime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletDelegate {
  void init(ServletConfig paramServletConfig) throws ServletException;
  
  void destroy();
  
  void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException;
  
  void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) throws ServletException;
  
  void registerEndpointUrlPattern(RuntimeEndpointInfo paramRuntimeEndpointInfo);
  
  void setSecondDelegate(ServletSecondDelegate paramServletSecondDelegate);
  
  void setSystemHandlerDelegate(SystemHandlerDelegate paramSystemHandlerDelegate);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\ServletDelegate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */