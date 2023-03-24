/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLExtension;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*    */ import java.util.ArrayList;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ import org.xml.sax.Locator;
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
/*    */ public final class WSDLMessageImpl
/*    */   extends AbstractExtensibleImpl
/*    */   implements WSDLMessage
/*    */ {
/*    */   private final QName name;
/*    */   private final ArrayList<WSDLPartImpl> parts;
/*    */   
/*    */   public WSDLMessageImpl(XMLStreamReader xsr, QName name) {
/* 61 */     super(xsr);
/* 62 */     this.name = name;
/* 63 */     this.parts = new ArrayList<WSDLPartImpl>();
/*    */   }
/*    */   
/*    */   public QName getName() {
/* 67 */     return this.name;
/*    */   }
/*    */   
/*    */   public void add(WSDLPartImpl part) {
/* 71 */     this.parts.add(part);
/*    */   }
/*    */   
/*    */   public Iterable<WSDLPartImpl> parts() {
/* 75 */     return this.parts;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLMessageImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */