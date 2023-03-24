/*    */ package com.sun.xml.rpc.processor.generator.writer;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*    */ import com.sun.xml.rpc.processor.generator.Names;
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
/*    */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiteralInterfaceSerializerWriter
/*    */   extends LiteralSequenceSerializerWriter
/*    */   implements GeneratorConstants
/*    */ {
/*    */   public LiteralInterfaceSerializerWriter(String basePackage, LiteralType type, Names names) {
/* 45 */     super(basePackage, type, names);
/* 46 */     this.serializerName = names.typeInterfaceSerializerClassName(basePackage, (AbstractType)type);
/*    */ 
/*    */ 
/*    */     
/* 50 */     this.serializerMemberName = names.getClassMemberName(this.serializerName);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\LiteralInterfaceSerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */