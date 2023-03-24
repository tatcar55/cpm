/*    */ package com.sun.xml.ws.rx.rm.faults;
/*    */ 
/*    */ import com.sun.xml.ws.rx.rm.runtime.RmRuntimeVersion;
/*    */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Detail;
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
/*    */ public class WsrmRequiredException
/*    */   extends AbstractSoapFaultException
/*    */ {
/*    */   public WsrmRequiredException() {
/* 55 */     super("The RM Destination requires the use of WSRM.", "The RM Destination requires the use of WSRM.", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractSoapFaultException.Code getCode() {
/* 60 */     return AbstractSoapFaultException.Code.Sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public QName getSubcode(RmRuntimeVersion rv) {
/* 65 */     return rv.protocolVersion.wsrmRequiredFaultCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public Detail getDetail(RuntimeContext rc) {
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\faults\WsrmRequiredException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */