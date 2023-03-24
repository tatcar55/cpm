/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.bind.api.AccessorException;
/*    */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValuePropertyLoader
/*    */   extends Loader
/*    */ {
/*    */   private final TransducedAccessor xacc;
/*    */   
/*    */   public ValuePropertyLoader(TransducedAccessor xacc) {
/* 61 */     super(true);
/* 62 */     this.xacc = xacc;
/*    */   }
/*    */   
/*    */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/*    */     try {
/* 67 */       this.xacc.parse(state.target, text);
/* 68 */     } catch (AccessorException e) {
/* 69 */       handleGenericException((Exception)e, true);
/* 70 */     } catch (RuntimeException e) {
/* 71 */       if (state.prev != null) {
/* 72 */         if (!(state.prev.target instanceof javax.xml.bind.JAXBElement))
/*    */         {
/*    */ 
/*    */ 
/*    */           
/* 77 */           handleParseConversionException(state, e);
/*    */         }
/*    */       } else {
/* 80 */         handleParseConversionException(state, e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\ValuePropertyLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */