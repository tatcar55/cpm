/*    */ package com.sun.xml.stream.buffer;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLStreamBufferMark
/*    */   extends XMLStreamBuffer
/*    */ {
/*    */   public XMLStreamBufferMark(Map<String, String> inscopeNamespaces, AbstractCreatorProcessor src) {
/* 77 */     if (inscopeNamespaces != null) {
/* 78 */       this._inscopeNamespaces = inscopeNamespaces;
/*    */     }
/*    */     
/* 81 */     this._structure = src._currentStructureFragment;
/* 82 */     this._structurePtr = src._structurePtr;
/*    */     
/* 84 */     this._structureStrings = src._currentStructureStringFragment;
/* 85 */     this._structureStringsPtr = src._structureStringsPtr;
/*    */     
/* 87 */     this._contentCharactersBuffer = src._currentContentCharactersBufferFragment;
/* 88 */     this._contentCharactersBufferPtr = src._contentCharactersBufferPtr;
/*    */     
/* 90 */     this._contentObjects = src._currentContentObjectFragment;
/* 91 */     this._contentObjectsPtr = src._contentObjectsPtr;
/* 92 */     this.treeCount = 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\XMLStreamBufferMark.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */