/*    */ package com.sun.xml.rpc.processor.generator;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*    */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriterFactory;
/*    */ import com.sun.xml.rpc.processor.model.AbstractType;
/*    */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPEncoding
/*    */   implements GeneratorConstants
/*    */ {
/*    */   public static void writeStaticSerializer(IndentingWriter p, String portPackage, SOAPType type, Set<String> processedTypes, SerializerWriterFactory writerFactory, Names names) throws IOException {
/* 53 */     if (processedTypes.contains(type.getName() + ";" + type.getJavaType().getRealName())) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 58 */     processedTypes.add(type.getName() + ";" + type.getJavaType().getRealName());
/*    */     
/* 60 */     String qnameMember = names.getTypeQName(type.getName());
/* 61 */     if (!processedTypes.contains(type.getName() + "TYPE_QNAME")) {
/* 62 */       GeneratorUtil.writeQNameTypeDeclaration(p, type.getName(), names);
/* 63 */       processedTypes.add(type.getName() + "TYPE_QNAME");
/*    */     } 
/* 65 */     SerializerWriter writer = writerFactory.createWriter(portPackage, (AbstractType)type);
/* 66 */     writer.declareSerializer(p, false, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\SOAPEncoding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */