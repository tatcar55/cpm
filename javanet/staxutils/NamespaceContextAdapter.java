/*     */ package javanet.staxutils;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceContextAdapter
/*     */   implements NamespaceContext
/*     */ {
/*     */   protected NamespaceContext namespaceCtx;
/*     */   
/*     */   public NamespaceContextAdapter() {}
/*     */   
/*     */   public NamespaceContextAdapter(NamespaceContext namespaceCtx) {
/*  60 */     this.namespaceCtx = namespaceCtx;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  66 */     if (this.namespaceCtx != null)
/*     */     {
/*  68 */       return this.namespaceCtx.getNamespaceURI(prefix);
/*     */     }
/*     */ 
/*     */     
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix(String nsURI) {
/*  80 */     if (this.namespaceCtx != null)
/*     */     {
/*  82 */       return this.namespaceCtx.getPrefix(nsURI);
/*     */     }
/*     */ 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getPrefixes(String nsURI) {
/*  94 */     if (this.namespaceCtx != null)
/*     */     {
/*  96 */       return this.namespaceCtx.getPrefixes(nsURI);
/*     */     }
/*     */ 
/*     */     
/* 100 */     return Collections.EMPTY_LIST.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\NamespaceContextAdapter.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */