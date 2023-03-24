/*    */ package javanet.staxutils;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import javax.xml.namespace.NamespaceContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EmptyNamespaceContext
/*    */   implements ExtendedNamespaceContext, StaticNamespaceContext
/*    */ {
/* 52 */   public static final EmptyNamespaceContext INSTANCE = new EmptyNamespaceContext();
/*    */ 
/*    */   
/*    */   public static final NamespaceContext getInstance() {
/* 56 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNamespaceURI(String prefix) {
/* 62 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix(String nsURI) {
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator getPrefixes(String nsURI) {
/* 74 */     return Collections.EMPTY_SET.iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NamespaceContext getParent() {
/* 80 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPrefixDeclared(String prefix) {
/* 86 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator getPrefixes() {
/* 92 */     return Collections.EMPTY_LIST.iterator();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterator getDeclaredPrefixes() {
/* 98 */     return Collections.EMPTY_LIST.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\EmptyNamespaceContext.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */