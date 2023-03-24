/*    */ package com.sun.xml.rpc.processor.generator.writer;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.generator.Names;
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LiteralSerializerWriterBase
/*    */   extends SerializerWriterBase
/*    */ {
/*    */   public LiteralSerializerWriterBase(AbstractType type, Names names) {
/* 40 */     super(type, names);
/*    */   }
/*    */   
/*    */   protected String getEncodingStyleString() {
/* 44 */     return "\"\"";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\LiteralSerializerWriterBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */