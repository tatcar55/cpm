/*    */ package com.sun.xml.rpc.wsdl.framework;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtensibilityHelper
/*    */ {
/*    */   private List _extensions;
/*    */   
/*    */   public void addExtension(Extension e) {
/* 45 */     if (this._extensions == null) {
/* 46 */       this._extensions = new ArrayList();
/*    */     }
/* 48 */     this._extensions.add(e);
/*    */   }
/*    */   
/*    */   public Iterator extensions() {
/* 52 */     if (this._extensions == null) {
/* 53 */       return new Iterator() {
/*    */           public boolean hasNext() {
/* 55 */             return false;
/*    */           }
/*    */           
/*    */           public Object next() {
/* 59 */             throw new NoSuchElementException();
/*    */           }
/*    */           
/*    */           public void remove() {
/* 63 */             throw new UnsupportedOperationException();
/*    */           }
/*    */         };
/*    */     }
/* 67 */     return this._extensions.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public void withAllSubEntitiesDo(EntityAction action) {
/* 72 */     if (this._extensions != null) {
/* 73 */       for (Iterator<Entity> iter = this._extensions.iterator(); iter.hasNext();) {
/* 74 */         action.perform(iter.next());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void accept(ExtensionVisitor visitor) throws Exception {
/* 80 */     if (this._extensions != null)
/* 81 */       for (Iterator<Extension> iter = this._extensions.iterator(); iter.hasNext();)
/* 82 */         ((Extension)iter.next()).accept(visitor);  
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\ExtensibilityHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */