/*    */ package com.sun.xml.fastinfoset.vocab;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Vocabulary
/*    */ {
/*    */   public static final int RESTRICTED_ALPHABET = 0;
/*    */   public static final int ENCODING_ALGORITHM = 1;
/*    */   public static final int PREFIX = 2;
/*    */   public static final int NAMESPACE_NAME = 3;
/*    */   public static final int LOCAL_NAME = 4;
/*    */   public static final int OTHER_NCNAME = 5;
/*    */   public static final int OTHER_URI = 6;
/*    */   public static final int ATTRIBUTE_VALUE = 7;
/*    */   public static final int OTHER_STRING = 8;
/*    */   public static final int CHARACTER_CONTENT_CHUNK = 9;
/*    */   public static final int ELEMENT_NAME = 10;
/*    */   public static final int ATTRIBUTE_NAME = 11;
/*    */   protected boolean _hasInitialReadOnlyVocabulary;
/*    */   protected String _referencedVocabularyURI;
/*    */   
/*    */   public boolean hasInitialVocabulary() {
/* 40 */     return this._hasInitialReadOnlyVocabulary;
/*    */   }
/*    */   
/*    */   protected void setInitialReadOnlyVocabulary(boolean hasInitialReadOnlyVocabulary) {
/* 44 */     this._hasInitialReadOnlyVocabulary = hasInitialReadOnlyVocabulary;
/*    */   }
/*    */   
/*    */   public boolean hasExternalVocabulary() {
/* 48 */     return (this._referencedVocabularyURI != null);
/*    */   }
/*    */   
/*    */   public String getExternalVocabularyURI() {
/* 52 */     return this._referencedVocabularyURI;
/*    */   }
/*    */   
/*    */   protected void setExternalVocabularyURI(String referencedVocabularyURI) {
/* 56 */     this._referencedVocabularyURI = referencedVocabularyURI;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\vocab\Vocabulary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */