/*    */ package com.sun.xml.bind.v2.runtime;
/*    */ 
/*    */ import com.sun.xml.bind.api.Bridge;
/*    */ import com.sun.xml.bind.api.JAXBRIContext;
/*    */ import java.io.IOException;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import org.xml.sax.SAXException;
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
/*    */ abstract class InternalBridge<T>
/*    */   extends Bridge<T>
/*    */ {
/*    */   protected InternalBridge(JAXBContextImpl context) {
/* 57 */     super(context);
/*    */   }
/*    */   
/*    */   public JAXBContextImpl getContext() {
/* 61 */     return this.context;
/*    */   }
/*    */   
/*    */   abstract void marshal(T paramT, XMLSerializer paramXMLSerializer) throws IOException, SAXException, XMLStreamException;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\InternalBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */