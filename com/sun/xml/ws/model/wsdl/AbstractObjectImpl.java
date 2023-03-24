/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLObject;
/*    */ import javax.xml.stream.Location;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.Locator;
/*    */ import org.xml.sax.helpers.LocatorImpl;
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
/*    */ 
/*    */ abstract class AbstractObjectImpl
/*    */   implements WSDLObject
/*    */ {
/*    */   private final int lineNumber;
/*    */   private final String systemId;
/*    */   
/*    */   AbstractObjectImpl(XMLStreamReader xsr) {
/* 61 */     Location loc = xsr.getLocation();
/* 62 */     this.lineNumber = loc.getLineNumber();
/* 63 */     this.systemId = loc.getSystemId();
/*    */   }
/*    */   
/*    */   AbstractObjectImpl(String systemId, int lineNumber) {
/* 67 */     this.systemId = systemId;
/* 68 */     this.lineNumber = lineNumber;
/*    */   }
/*    */   @NotNull
/*    */   public final Locator getLocation() {
/* 72 */     LocatorImpl loc = new LocatorImpl();
/* 73 */     loc.setSystemId(this.systemId);
/* 74 */     loc.setLineNumber(this.lineNumber);
/* 75 */     return loc;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\AbstractObjectImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */