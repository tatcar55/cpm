/*    */ package org.jvnet.fastinfoset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExternalVocabulary
/*    */ {
/*    */   public final String URI;
/*    */   public final Vocabulary vocabulary;
/*    */   
/*    */   public ExternalVocabulary(String URI, Vocabulary vocabulary) {
/* 40 */     if (URI == null || vocabulary == null) {
/* 41 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 44 */     this.URI = URI;
/* 45 */     this.vocabulary = vocabulary;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\ExternalVocabulary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */