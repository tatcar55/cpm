/*     */ package com.sun.xml.ws.xmlfilter;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.xmlfilter.localization.LocalizationMessages;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public final class XmlFilteringUtils
/*     */ {
/*     */   public static final class AttributeInfo
/*     */   {
/*     */     private final QName name;
/*     */     private final String value;
/*     */     
/*     */     AttributeInfo(QName name, String value) {
/*  60 */       this.name = name;
/*  61 */       this.value = value;
/*     */     }
/*     */     
/*     */     public QName getName() {
/*  65 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getValue() {
/*  69 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*  73 */   private static final Logger LOGGER = Logger.getLogger(XmlFilteringUtils.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefaultNamespaceURI(XMLStreamWriter writer) {
/*  83 */     return writer.getNamespaceContext().getNamespaceURI("");
/*     */   }
/*     */   
/*     */   public static QName getElementNameToWrite(Invocation invocation, String defaultNamespaceURI) { String namespaceURI, localName;
/*  87 */     checkInvocationParameter(invocation, XmlStreamWriterMethodType.WRITE_START_ELEMENT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     int argumentsCount = invocation.getArgumentsCount();
/*     */ 
/*     */ 
/*     */     
/*  98 */     switch (argumentsCount) {
/*     */       case 1:
/* 100 */         namespaceURI = defaultNamespaceURI;
/* 101 */         localName = invocation.getArgument(0).toString();
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
/* 115 */         return new QName(namespaceURI, localName);case 2: namespaceURI = invocation.getArgument(0).toString(); localName = invocation.getArgument(1).toString(); return new QName(namespaceURI, localName);case 3: localName = invocation.getArgument(1).toString(); namespaceURI = invocation.getArgument(2).toString(); return new QName(namespaceURI, localName);
/*     */     } 
/*     */     throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.XMLF_5003_UNEXPECTED_ARGUMENTS_COUNT(XmlStreamWriterMethodType.WRITE_START_ELEMENT + "(...)", Integer.valueOf(argumentsCount)))); } public static AttributeInfo getAttributeNameToWrite(Invocation invocation, String defaultNamespaceURI) {
/*     */     String namespaceURI, localName, value;
/* 119 */     checkInvocationParameter(invocation, XmlStreamWriterMethodType.WRITE_ATTRIBUTE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     int argumentsCount = invocation.getArgumentsCount();
/*     */ 
/*     */     
/* 129 */     switch (argumentsCount) {
/*     */       case 2:
/* 131 */         namespaceURI = defaultNamespaceURI;
/* 132 */         localName = invocation.getArgument(0).toString();
/* 133 */         value = invocation.getArgument(1).toString();
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
/* 149 */         return new AttributeInfo(new QName(namespaceURI, localName), value);case 3: namespaceURI = invocation.getArgument(0).toString(); localName = invocation.getArgument(1).toString(); value = invocation.getArgument(2).toString(); return new AttributeInfo(new QName(namespaceURI, localName), value);case 4: namespaceURI = invocation.getArgument(1).toString(); localName = invocation.getArgument(2).toString(); value = invocation.getArgument(3).toString(); return new AttributeInfo(new QName(namespaceURI, localName), value);
/*     */     } 
/*     */     throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.XMLF_5003_UNEXPECTED_ARGUMENTS_COUNT(XmlStreamWriterMethodType.WRITE_ATTRIBUTE + "(...)", Integer.valueOf(argumentsCount))));
/*     */   } private static void checkInvocationParameter(Invocation invocation, XmlStreamWriterMethodType expectedType) {
/* 153 */     if (invocation == null) {
/* 154 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.XMLF_5012_METHOD_PARAMETER_CANNOT_BE_NULL("Invocation parameter")));
/*     */     }
/* 156 */     if (invocation.getMethodType() != expectedType)
/* 157 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.XMLF_5013_ILLEGAL_INVOCATION_METHOD_TYPE(invocation.getMethodType(), expectedType))); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\XmlFilteringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */