/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.core.WildcardMode;
/*    */ import javax.xml.bind.annotation.DomHandler;
/*    */ import javax.xml.transform.Result;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WildcardLoader
/*    */   extends ProxyLoader
/*    */ {
/*    */   private final DomLoader dom;
/*    */   private final WildcardMode mode;
/*    */   
/*    */   public WildcardLoader(DomHandler<?, Result> dom, WildcardMode mode) {
/* 67 */     this.dom = new DomLoader<Result>(dom);
/* 68 */     this.mode = mode;
/*    */   }
/*    */   
/*    */   protected Loader selectLoader(UnmarshallingContext.State state, TagName tag) throws SAXException {
/* 72 */     UnmarshallingContext context = state.getContext();
/*    */     
/* 74 */     if (this.mode.allowTypedObject) {
/* 75 */       Loader l = context.selectRootLoader(state, tag);
/* 76 */       if (l != null)
/* 77 */         return l; 
/*    */     } 
/* 79 */     if (this.mode.allowDom) {
/* 80 */       return this.dom;
/*    */     }
/*    */     
/* 83 */     return Discarder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\WildcardLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */