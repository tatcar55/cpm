/*    */ package com.sun.xml.rpc.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*    */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*    */ import com.sun.xml.rpc.wsdl.framework.ParserContext;
/*    */ import com.sun.xml.rpc.wsdl.framework.WriterContext;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import org.w3c.dom.Element;
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
/*    */ public abstract class ExtensionHandler
/*    */ {
/*    */   protected Map _extensionHandlers;
/*    */   
/*    */   public abstract String getNamespaceURI();
/*    */   
/*    */   public void setExtensionHandlers(Map m) {
/* 52 */     this._extensionHandlers = m;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doHandleExtension(ParserContext context, Extensible parent, Element e) {
/* 59 */     return false;
/*    */   }
/*    */   
/*    */   public void doHandleExtension(WriterContext context, Extension extension) throws IOException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\parser\ExtensionHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */