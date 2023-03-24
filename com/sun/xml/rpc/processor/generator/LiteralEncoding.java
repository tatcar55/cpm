/*    */ package com.sun.xml.rpc.processor.generator;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*    */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriterFactory;
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
/*    */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*    */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiteralEncoding
/*    */   implements GeneratorConstants
/*    */ {
/*    */   public static void writeStaticSerializer(IndentingWriter p, String portPackage, LiteralType type, Set<String> processedTypes, SerializerWriterFactory writerFactory, Names names) throws IOException {
/* 45 */     if (processedTypes.contains(type.getName() + ";" + type.getJavaType().getRealName())) {
/*    */       return;
/*    */     }
/* 48 */     processedTypes.add(type.getName() + ";" + type.getJavaType().getRealName());
/*    */     
/* 50 */     String qnameMember = names.getTypeQName(type.getName());
/* 51 */     if (!processedTypes.contains(type.getName() + "TYPE_QNAME")) {
/* 52 */       GeneratorUtil.writeQNameTypeDeclaration(p, type.getName(), names);
/* 53 */       processedTypes.add(type.getName() + "TYPE_QNAME");
/*    */     } 
/*    */     
/* 56 */     if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralFragmentType) {
/* 57 */       SerializerWriter writer = writerFactory.createWriter(portPackage, (AbstractType)type);
/* 58 */       String serializerClassName = writer.serializerName();
/* 59 */       String memberName = writer.serializerMemberName();
/* 60 */       p.pln("private " + serializerClassName + " " + memberName + ";");
/*    */     } else {
/*    */       
/* 63 */       SerializerWriter writer = writerFactory.createWriter(portPackage, (AbstractType)type);
/* 64 */       writer.declareSerializer(p, false, false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\LiteralEncoding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */