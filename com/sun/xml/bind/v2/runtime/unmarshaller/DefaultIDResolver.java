/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*    */ 
/*    */ import com.sun.xml.bind.IDResolver;
/*    */ import java.util.HashMap;
/*    */ import java.util.concurrent.Callable;
/*    */ import javax.xml.bind.ValidationEventHandler;
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
/*    */ final class DefaultIDResolver
/*    */   extends IDResolver
/*    */ {
/* 59 */   private HashMap<String, Object> idmap = null;
/*    */ 
/*    */   
/*    */   public void startDocument(ValidationEventHandler eventHandler) throws SAXException {
/* 63 */     if (this.idmap != null) {
/* 64 */       this.idmap.clear();
/*    */     }
/*    */   }
/*    */   
/*    */   public void bind(String id, Object obj) {
/* 69 */     if (this.idmap == null) this.idmap = new HashMap<String, Object>(); 
/* 70 */     this.idmap.put(id, obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public Callable resolve(final String id, Class targetType) {
/* 75 */     return new Callable() {
/*    */         public Object call() throws Exception {
/* 77 */           if (DefaultIDResolver.this.idmap == null) return null; 
/* 78 */           return DefaultIDResolver.this.idmap.get(id);
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\DefaultIDResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */