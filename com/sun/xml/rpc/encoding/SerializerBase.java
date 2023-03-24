/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import javax.activation.DataHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SerializerBase
/*     */   implements CombinedSerializer, SerializerConstants
/*     */ {
/*     */   protected QName type;
/*     */   protected boolean encodeType;
/*     */   protected boolean isNullable;
/*     */   protected String encodingStyle;
/*     */   
/*     */   protected SerializerBase(QName xmlType, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  54 */     if (xmlType == null) {
/*  55 */       throw new IllegalArgumentException("xmlType parameter is not allowed to be null");
/*     */     }
/*  57 */     this.type = xmlType;
/*  58 */     this.encodeType = encodeType;
/*  59 */     this.isNullable = isNullable;
/*  60 */     this.encodingStyle = encodingStyle;
/*     */   }
/*     */   
/*     */   public QName getXmlType() {
/*  64 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean getEncodeType() {
/*  68 */     return this.encodeType;
/*     */   }
/*     */   
/*     */   public boolean isNullable() {
/*  72 */     return this.isNullable;
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/*  76 */     return this.encodingStyle;
/*     */   }
/*     */   
/*     */   public CombinedSerializer getInnermostSerializer() {
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(DataHandler dataHandler, SOAPDeserializationContext context) {
/*  86 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected QName getName(XMLReader reader) throws Exception {
/*  90 */     return reader.getName();
/*     */   }
/*     */   
/*     */   public static QName getType(XMLReader reader) throws Exception {
/*  94 */     QName type = null;
/*     */     
/*  96 */     Attributes attrs = reader.getAttributes();
/*  97 */     String typeVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/*  99 */     if (typeVal != null) {
/* 100 */       type = XMLReaderUtil.decodeQName(reader, typeVal);
/*     */     }
/*     */     
/* 103 */     return type;
/*     */   }
/*     */   
/*     */   public static boolean getNullStatus(XMLReader reader) throws Exception {
/* 107 */     boolean isNull = false;
/*     */     
/* 109 */     Attributes attrs = reader.getAttributes();
/* 110 */     String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 111 */     isNull = (nullVal != null && decodeBoolean(nullVal));
/*     */     
/* 113 */     return isNull;
/*     */   }
/*     */   
/*     */   public static boolean decodeBoolean(String str) throws Exception {
/* 117 */     return (str.equals("true") || str.equals("1"));
/*     */   }
/*     */   
/*     */   protected String getID(XMLReader reader) throws Exception {
/* 121 */     Attributes attrs = reader.getAttributes();
/* 122 */     return attrs.getValue("", "id");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void verifyName(XMLReader reader, QName expectedName) throws Exception {
/* 127 */     QName actualName = getName(reader);
/*     */     
/* 129 */     if (!actualName.equals(expectedName)) {
/* 130 */       throw new DeserializationException("soap.unexpectedElementName", new Object[] { expectedName.toString(), actualName.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void verifyType(XMLReader reader) throws Exception {
/* 137 */     if (typeIsEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 141 */     QName actualType = getType(reader);
/*     */     
/* 143 */     if (actualType != null && 
/* 144 */       !actualType.equals(this.type) && !isAcceptableType(actualType)) {
/* 145 */       throw new DeserializationException("soap.unexpectedElementType", new Object[] { this.type.toString(), actualType.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAcceptableType(QName actualType) {
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   protected void skipEmptyContent(XMLReader reader) throws Exception {
/* 157 */     reader.skipElement();
/*     */   }
/*     */   
/*     */   public String getMechanismType() {
/* 161 */     return "http://java.sun.com/jax-rpc-ri/1.0/streaming/";
/*     */   }
/*     */   
/*     */   protected boolean typeIsEmpty() {
/* 165 */     return (this.type.getNamespaceURI().equals("") && this.type.getLocalPart().equals(""));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\SerializerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */