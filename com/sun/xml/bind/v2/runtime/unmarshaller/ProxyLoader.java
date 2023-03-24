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
/*    */ 
/*    */ public abstract class ProxyLoader
/*    */   extends Loader
/*    */ {
/*    */   public ProxyLoader() {
/* 53 */     super(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 58 */     Loader loader = selectLoader(state, ea);
/* 59 */     state.loader = loader;
/* 60 */     loader.startElement(state, ea);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Loader selectLoader(UnmarshallingContext.State paramState, TagName paramTagName) throws SAXException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void leaveElement(UnmarshallingContext.State state, TagName ea) {
/* 74 */     throw new IllegalStateException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\ProxyLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */