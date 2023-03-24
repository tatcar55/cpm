/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class FastInfosetReflection
/*     */ {
/*     */   static Constructor fiDOMDocumentParser_new;
/*     */   static Method fiDOMDocumentParser_parse;
/*     */   static Constructor fiDOMDocumentSerializer_new;
/*     */   static Method fiDOMDocumentSerializer_serialize;
/*     */   static Method fiDOMDocumentSerializer_setOutputStream;
/*     */   static Class fiFastInfosetSource_class;
/*     */   static Constructor fiFastInfosetSource_new;
/*     */   static Method fiFastInfosetSource_getInputStream;
/*     */   static Method fiFastInfosetSource_setInputStream;
/*     */   static Constructor fiFastInfosetResult_new;
/*     */   static Method fiFastInfosetResult_getOutputStream;
/*     */   
/*     */   static {
/*     */     try {
/* 116 */       Class<?> clazz = Class.forName("com.sun.xml.fastinfoset.dom.DOMDocumentParser");
/* 117 */       fiDOMDocumentParser_new = clazz.getConstructor((Class[])null);
/* 118 */       fiDOMDocumentParser_parse = clazz.getMethod("parse", new Class[] { Document.class, InputStream.class });
/*     */ 
/*     */       
/* 121 */       clazz = Class.forName("com.sun.xml.fastinfoset.dom.DOMDocumentSerializer");
/* 122 */       fiDOMDocumentSerializer_new = clazz.getConstructor((Class[])null);
/* 123 */       fiDOMDocumentSerializer_serialize = clazz.getMethod("serialize", new Class[] { Node.class });
/*     */       
/* 125 */       fiDOMDocumentSerializer_setOutputStream = clazz.getMethod("setOutputStream", new Class[] { OutputStream.class });
/*     */ 
/*     */       
/* 128 */       fiFastInfosetSource_class = clazz = Class.forName("org.jvnet.fastinfoset.FastInfosetSource");
/* 129 */       fiFastInfosetSource_new = clazz.getConstructor(new Class[] { InputStream.class });
/*     */       
/* 131 */       fiFastInfosetSource_getInputStream = clazz.getMethod("getInputStream", (Class[])null);
/* 132 */       fiFastInfosetSource_setInputStream = clazz.getMethod("setInputStream", new Class[] { InputStream.class });
/*     */ 
/*     */       
/* 135 */       clazz = Class.forName("org.jvnet.fastinfoset.FastInfosetResult");
/* 136 */       fiFastInfosetResult_new = clazz.getConstructor(new Class[] { OutputStream.class });
/*     */       
/* 138 */       fiFastInfosetResult_getOutputStream = clazz.getMethod("getOutputStream", (Class[])null);
/*     */     }
/* 140 */     catch (Exception e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object DOMDocumentParser_new() throws Exception {
/* 148 */     if (fiDOMDocumentParser_new == null) {
/* 149 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 151 */     return fiDOMDocumentParser_new.newInstance((Object[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void DOMDocumentParser_parse(Object parser, Document d, InputStream s) throws Exception {
/* 157 */     if (fiDOMDocumentParser_parse == null) {
/* 158 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 160 */     fiDOMDocumentParser_parse.invoke(parser, new Object[] { d, s });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object DOMDocumentSerializer_new() throws Exception {
/* 166 */     if (fiDOMDocumentSerializer_new == null) {
/* 167 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 169 */     return fiDOMDocumentSerializer_new.newInstance((Object[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void DOMDocumentSerializer_serialize(Object serializer, Node node) throws Exception {
/* 175 */     if (fiDOMDocumentSerializer_serialize == null) {
/* 176 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 178 */     fiDOMDocumentSerializer_serialize.invoke(serializer, new Object[] { node });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void DOMDocumentSerializer_setOutputStream(Object serializer, OutputStream os) throws Exception {
/* 184 */     if (fiDOMDocumentSerializer_setOutputStream == null) {
/* 185 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 187 */     fiDOMDocumentSerializer_setOutputStream.invoke(serializer, new Object[] { os });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFastInfosetSource(Source source) {
/* 193 */     return source.getClass().getName().equals("org.jvnet.fastinfoset.FastInfosetSource");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Class getFastInfosetSource_class() {
/* 198 */     if (fiFastInfosetSource_class == null) {
/* 199 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/*     */     
/* 202 */     return fiFastInfosetSource_class;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Source FastInfosetSource_new(InputStream is) throws Exception {
/* 207 */     if (fiFastInfosetSource_new == null) {
/* 208 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 210 */     return fiFastInfosetSource_new.newInstance(new Object[] { is });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream FastInfosetSource_getInputStream(Source source) throws Exception {
/* 216 */     if (fiFastInfosetSource_getInputStream == null) {
/* 217 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 219 */     return (InputStream)fiFastInfosetSource_getInputStream.invoke(source, (Object[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void FastInfosetSource_setInputStream(Source source, InputStream is) throws Exception {
/* 225 */     if (fiFastInfosetSource_setInputStream == null) {
/* 226 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 228 */     fiFastInfosetSource_setInputStream.invoke(source, new Object[] { is });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFastInfosetResult(Result result) {
/* 234 */     return result.getClass().getName().equals("org.jvnet.fastinfoset.FastInfosetResult");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Result FastInfosetResult_new(OutputStream os) throws Exception {
/* 241 */     if (fiFastInfosetResult_new == null) {
/* 242 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 244 */     return fiFastInfosetResult_new.newInstance(new Object[] { os });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static OutputStream FastInfosetResult_getOutputStream(Result result) throws Exception {
/* 250 */     if (fiFastInfosetResult_getOutputStream == null) {
/* 251 */       throw new RuntimeException("Unable to locate Fast Infoset implementation");
/*     */     }
/* 253 */     return (OutputStream)fiFastInfosetResult_getOutputStream.invoke(result, (Object[])null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\FastInfosetReflection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */