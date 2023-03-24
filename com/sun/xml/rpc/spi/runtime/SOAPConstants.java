/*    */ package com.sun.xml.rpc.spi.runtime;
/*    */ 
/*    */ import com.sun.xml.rpc.spi.tools.SOAPConstants;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SOAPConstants
/*    */   extends SOAPConstants
/*    */ {
/*    */   public static final String URI_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/* 34 */   public static final QName FAULT_CODE_SERVER = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   public static final QName FAULT_CODE_CLIENT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client");
/*    */   public static final String HTTP_SERVLET_RESPONSE = "com.sun.xml.rpc.server.http.HttpServletResponse";
/*    */   public static final String ONE_WAY_OPERATION = "com.sun.xml.rpc.server.OneWayOperation";
/*    */   public static final String CLIENT_BAD_REQUEST = "com.sun.xml.rpc.server.http.ClientBadRequest";
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\runtime\SOAPConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */