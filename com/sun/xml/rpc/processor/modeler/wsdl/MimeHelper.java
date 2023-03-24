/*     */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
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
/*     */ public class MimeHelper
/*     */ {
/*     */   protected static Map mimeTypeToJavaType;
/*     */   protected static JavaSimpleTypeCreator javaType;
/*     */   public static final String JPEG_IMAGE_MIME_TYPE = "image/jpeg";
/*     */   public static final String GIF_IMAGE_MIME_TYPE = "image/gif";
/*     */   public static final String TEXT_XML_MIME_TYPE = "text/xml";
/*     */   public static final String TEXT_HTML_MIME_TYPE = "text/html";
/*     */   public static final String TEXT_PLAIN_MIME_TYPE = "text/plain";
/*     */   public static final String APPLICATION_XML_MIME_TYPE = "application/xml";
/*     */   public static final String MULTIPART_MIME_TYPE = "multipart/*";
/*     */   
/*     */   protected static String getAttachmentUniqueID(String mimePart) {
/*  44 */     return mimePart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isMimeTypeBinary(String mimeType) {
/*  52 */     if (mimeType.equals("image/jpeg") || mimeType.equals("image/gif"))
/*     */     {
/*     */       
/*  55 */       return true; } 
/*  56 */     if (mimeType.equals("text/xml") || mimeType.equals("text/html") || mimeType.equals("text/plain") || mimeType.equals("application/xml") || mimeType.equals("multipart/*"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   protected static void initMimeTypeToJavaType() {
/*  70 */     mimeTypeToJavaType.put("image/jpeg", javaType.IMAGE_JAVATYPE);
/*     */     
/*  72 */     mimeTypeToJavaType.put("image/gif", javaType.IMAGE_JAVATYPE);
/*     */     
/*  74 */     mimeTypeToJavaType.put("text/xml", javaType.SOURCE_JAVATYPE);
/*     */     
/*  76 */     mimeTypeToJavaType.put("application/xml", javaType.SOURCE_JAVATYPE);
/*     */ 
/*     */     
/*  79 */     mimeTypeToJavaType.put("text/plain", javaType.STRING_JAVATYPE);
/*  80 */     mimeTypeToJavaType.put("multipart/*", javaType.MIME_MULTIPART_JAVATYPE);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MimeHelper() {
/* 102 */     mimeTypeToJavaType = new HashMap<Object, Object>();
/* 103 */     javaType = new JavaSimpleTypeCreator();
/* 104 */     initMimeTypeToJavaType();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\MimeHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */