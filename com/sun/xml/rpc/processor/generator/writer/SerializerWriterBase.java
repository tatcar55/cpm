/*     */ package com.sun.xml.rpc.processor.generator.writer;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorConstants;
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorUtil;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.io.IOException;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public abstract class SerializerWriterBase
/*     */   implements SerializerWriter, GeneratorConstants
/*     */ {
/*     */   AbstractType type;
/*     */   Names names;
/*  51 */   SOAPVersion soapVer = SOAPVersion.SOAP_11;
/*     */ 
/*     */   
/*     */   public SerializerWriterBase(AbstractType type, Names names) {
/*  55 */     if (type.isSOAPType()) {
/*  56 */       String ver = type.getVersion();
/*  57 */       if (ver.equals(SOAPVersion.SOAP_11.toString())) {
/*  58 */         this.soapVer = SOAPVersion.SOAP_11;
/*  59 */       } else if (ver.equals(SOAPVersion.SOAP_12.toString())) {
/*  60 */         this.soapVer = SOAPVersion.SOAP_12;
/*     */       } 
/*  62 */     }  this.type = type;
/*  63 */     this.names = names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerSerializer(IndentingWriter p, boolean encodeTypes, boolean multiRefEncoding, String typeMapping) throws IOException {
/*  72 */     StringBuffer typeName = new StringBuffer(40);
/*  73 */     typeName.append("type");
/*  74 */     createSerializer(p, typeName, "serializer", encodeTypes, multiRefEncoding, typeMapping);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     writeRegisterFactories(p, typeName.toString(), "serializer", typeMapping);
/*     */   }
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
/*     */   public void initializeSerializer(IndentingWriter p, String typeName, String registry) throws IOException {
/*  94 */     String javaType = null;
/*  95 */     if (this.type.getName().equals(SchemaConstants.QNAME_TYPE_IDREF)) {
/*  96 */       javaType = "java.lang.String";
/*     */     } else {
/*  98 */       javaType = this.type.getJavaType().getName();
/*     */     } 
/* 100 */     p.pln(serializerMemberName() + " = (CombinedSerializer)registry.getSerializer(" + getEncodingStyleString() + ", " + javaType + ".class, " + typeName + ");");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializerName() {
/* 112 */     return "CombinedSerializer";
/*     */   }
/*     */   
/*     */   public String deserializerName() {
/* 116 */     return serializerName();
/*     */   }
/*     */   
/*     */   public static boolean handlesType(AbstractType type) {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   protected String getEncodingStyleString() {
/* 124 */     if (this.soapVer.equals(SOAPVersion.SOAP_12))
/* 125 */       return "SOAP12Constants.NS_SOAP_ENCODING"; 
/* 126 */     if (this.soapVer.equals(SOAPVersion.SOAP_11))
/* 127 */       return "SOAPConstants.NS_SOAP_ENCODING"; 
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   protected String getEncodingStyle() {
/* 132 */     if (this.soapVer.equals(SOAPVersion.SOAP_12))
/* 133 */       return "http://www.w3.org/2002/06/soap-encoding"; 
/* 134 */     if (this.soapVer.equals(SOAPVersion.SOAP_11))
/* 135 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 136 */     return null;
/*     */   }
/*     */   
/*     */   protected String getSOAPVersionString() {
/* 140 */     if (this.soapVer.equals(SOAPVersion.SOAP_12))
/* 141 */       return "SOAPVersion.SOAP_12"; 
/* 142 */     if (this.soapVer.equals(SOAPVersion.SOAP_11))
/* 143 */       return "SOAPVersion.SOAP_11"; 
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void declareType(IndentingWriter p, StringBuffer member, QName type, boolean isStatic, boolean isFinal) throws IOException {
/* 155 */     String qnameConstant = GeneratorUtil.getQNameConstant(type);
/* 156 */     if (qnameConstant != null) {
/* 157 */       member.delete(0, member.length());
/* 158 */       member.append(qnameConstant);
/*     */     } else {
/* 160 */       String modifier = getModifier(isStatic, isFinal);
/* 161 */       p.p(modifier + "QName " + member + " = ");
/* 162 */       GeneratorUtil.writeNewQName(p, type);
/* 163 */       p.pln(";");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeRegisterFactories(IndentingWriter p, String typeName, String memberName, String mapping) throws IOException {
/* 173 */     p.pln("registerSerializer(" + mapping + "," + this.type.getJavaType().getName() + ".class, " + typeName + ", " + memberName + ");");
/*     */   }
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
/*     */   protected String getModifier(boolean isStatic, boolean isFinal) {
/* 186 */     String modifier = "";
/* 187 */     if (isStatic) {
/* 188 */       modifier = modifier + "static ";
/*     */     }
/* 190 */     if (isFinal) {
/* 191 */       modifier = modifier + "final ";
/*     */     }
/* 193 */     return modifier;
/*     */   }
/*     */   
/*     */   protected String getPrefix(AbstractType type) {
/* 197 */     QName typeName = type.getName();
/* 198 */     String prefix = this.names.getPrefix(typeName);
/* 199 */     return prefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\SerializerWriterBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */