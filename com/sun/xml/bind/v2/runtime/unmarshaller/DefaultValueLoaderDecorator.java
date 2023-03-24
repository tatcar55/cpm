/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*    */ 
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
/*    */ public final class DefaultValueLoaderDecorator
/*    */   extends Loader
/*    */ {
/*    */   private final Loader l;
/*    */   private final String defaultValue;
/*    */   
/*    */   public DefaultValueLoaderDecorator(Loader l, String defaultValue) {
/* 55 */     this.l = l;
/* 56 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 62 */     if (state.elementDefaultValue == null) {
/* 63 */       state.elementDefaultValue = this.defaultValue;
/*    */     }
/* 65 */     state.loader = this.l;
/* 66 */     this.l.startElement(state, ea);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\DefaultValueLoaderDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */