/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public class SerializerWriterFactoryImpl
/*     */   implements SerializerWriterFactory
/*     */ {
/*     */   private Map writerMap;
/*     */   private Names names;
/*     */   
/*     */   public SerializerWriterFactoryImpl(Names names) {
/*  69 */     this.writerMap = new HashMap<Object, Object>();
/*  70 */     this.names = names;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializerWriter createWriter(String basePackage, AbstractType type) {
/*  76 */     if (type == null)
/*  77 */       return null; 
/*  78 */     SerializerWriter writer = getTypeSerializerWriter(type);
/*  79 */     if (writer == null) {
/*  80 */       if (type instanceof com.sun.xml.rpc.processor.model.soap.SOAPAnyType) {
/*  81 */         writer = new DynamicSerializerWriter((SOAPType)type, this.names);
/*  82 */       } else if (type instanceof com.sun.xml.rpc.processor.model.soap.SOAPSimpleType) {
/*  83 */         if (CollectionSerializerWriter.handlesType(type)) {
/*  84 */           writer = new CollectionSerializerWriter((SOAPType)type, this.names);
/*     */         } else {
/*     */           
/*  87 */           writer = new SimpleTypeSerializerWriter((SOAPType)type, this.names);
/*     */         }
/*     */       
/*  90 */       } else if (type instanceof SOAPStructureType) {
/*  91 */         if (((SOAPStructureType)type).getSubtypes() != null) {
/*  92 */           writer = new InterfaceSerializerWriter(basePackage, (SOAPType)type, this.names);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/*  98 */           writer = new SOAPObjectSerializerWriter(basePackage, (SOAPType)type, this.names);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 104 */       else if (type instanceof com.sun.xml.rpc.processor.model.soap.SOAPArrayType) {
/* 105 */         writer = new ArraySerializerWriter(basePackage, (SOAPType)type, this.names);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 110 */       else if (type instanceof com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType) {
/* 111 */         writer = new EnumerationSerializerWriter(basePackage, (SOAPType)type, this.names);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 116 */       else if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType) {
/* 117 */         writer = new LiteralEnumerationSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 122 */       else if (type instanceof com.sun.xml.rpc.processor.model.soap.SOAPCustomType) {
/* 123 */         writer = new CustomSerializerWriter((SOAPType)type, this.names);
/* 124 */       } else if (type instanceof LiteralFragmentType) {
/* 125 */         writer = new LiteralFragmentSerializerWriter((LiteralFragmentType)type, this.names);
/*     */ 
/*     */       
/*     */       }
/* 129 */       else if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralSimpleType) {
/* 130 */         writer = new LiteralSimpleSerializerWriter((LiteralType)type, this.names);
/*     */ 
/*     */       
/*     */       }
/* 134 */       else if (type instanceof LiteralSequenceType) {
/* 135 */         if (((LiteralSequenceType)type).getSubtypes() != null) {
/* 136 */           writer = new LiteralInterfaceSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 142 */           writer = new LiteralSequenceSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 148 */       else if (type instanceof LiteralAllType) {
/*     */         
/* 150 */         if (((LiteralAllType)type).getSubtypes() != null) {
/* 151 */           writer = new LiteralInterfaceSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 157 */           writer = new LiteralSequenceSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 163 */       else if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralArrayType) {
/*     */         
/* 165 */         writer = new LiteralSequenceSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 170 */       else if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType) {
/*     */         
/* 172 */         writer = new LiteralSequenceSerializerWriter(basePackage, (LiteralType)type, this.names);
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 177 */       else if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralListType) {
/* 178 */         writer = new LiteralSimpleSerializerWriter((LiteralType)type, this.names);
/*     */ 
/*     */       
/*     */       }
/* 182 */       else if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralIDType) {
/* 183 */         writer = new LiteralSimpleSerializerWriter((LiteralType)type, this.names);
/*     */ 
/*     */       
/*     */       }
/* 187 */       else if (type instanceof com.sun.xml.rpc.processor.model.soap.SOAPListType) {
/* 188 */         writer = new SimpleTypeSerializerWriter((SOAPType)type, this.names);
/*     */       } 
/*     */       
/* 191 */       if (writer == null) {
/* 192 */         throw new GeneratorException("generator.unsupported.type.encountered", new Object[] { type.getName().getLocalPart(), type.getName().getNamespaceURI() });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 198 */       setTypeSerializerWriter(type, writer);
/*     */     } 
/* 200 */     return writer;
/*     */   }
/*     */   
/*     */   private SerializerWriter getTypeSerializerWriter(AbstractType type) {
/* 204 */     String key = genKey(type);
/* 205 */     SerializerWriter writer = (SerializerWriter)this.writerMap.get(key);
/* 206 */     return writer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTypeSerializerWriter(AbstractType type, SerializerWriter writer) {
/* 212 */     String key = genKey(type);
/* 213 */     this.writerMap.put(key, writer);
/*     */   }
/*     */   
/*     */   protected static String genKey(AbstractType type) {
/* 217 */     String typeType, schemaType = type.getName().toString();
/* 218 */     String javaType = type.getJavaType().getRealName();
/*     */     
/* 220 */     if (type instanceof com.sun.xml.rpc.processor.model.literal.LiteralListType) {
/* 221 */       typeType = type.toString();
/*     */     } else {
/* 223 */       typeType = type.getClass().getName();
/* 224 */     }  return schemaType + ";" + javaType + ";" + typeType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\SerializerWriterFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */