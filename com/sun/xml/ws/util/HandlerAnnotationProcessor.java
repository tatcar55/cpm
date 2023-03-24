/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.server.AsyncProvider;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.handler.HandlerChainsModel;
/*     */ import com.sun.xml.ws.model.ReflectAnnotationReader;
/*     */ import com.sun.xml.ws.server.EndpointFactory;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.HandlerChain;
/*     */ import javax.jws.WebService;
/*     */ import javax.jws.soap.SOAPMessageHandlers;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.Service;
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
/*     */ public class HandlerAnnotationProcessor
/*     */ {
/*  89 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.util");
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
/*     */   public static HandlerAnnotationInfo buildHandlerInfo(@NotNull Class<?> clazz, QName serviceName, QName portName, WSBinding binding) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 109 */     MetadataReader metadataReader = EndpointFactory.getExternalMetadatReader(clazz, binding);
/* 110 */     if (metadataReader == null) {
/* 111 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */ 
/*     */     
/* 115 */     HandlerChain handlerChain = (HandlerChain)reflectAnnotationReader.getAnnotation(HandlerChain.class, clazz);
/* 116 */     if (handlerChain == null) {
/* 117 */       clazz = getSEI(clazz, (MetadataReader)reflectAnnotationReader);
/* 118 */       if (clazz != null)
/* 119 */         handlerChain = (HandlerChain)reflectAnnotationReader.getAnnotation(HandlerChain.class, clazz); 
/* 120 */       if (handlerChain == null) {
/* 121 */         return null;
/*     */       }
/*     */     } 
/* 124 */     if (clazz.getAnnotation(SOAPMessageHandlers.class) != null) {
/* 125 */       throw new UtilException("util.handler.cannot.combine.soapmessagehandlers", new Object[0]);
/*     */     }
/*     */     
/* 128 */     InputStream iStream = getFileAsStream(clazz, handlerChain);
/* 129 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, iStream, true);
/*     */     
/* 131 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 132 */     HandlerAnnotationInfo handlerAnnInfo = HandlerChainsModel.parseHandlerFile(reader, clazz.getClassLoader(), serviceName, portName, binding);
/*     */     
/*     */     try {
/* 135 */       reader.close();
/* 136 */       iStream.close();
/* 137 */     } catch (XMLStreamException e) {
/* 138 */       e.printStackTrace();
/* 139 */       throw new UtilException(e.getMessage(), new Object[0]);
/* 140 */     } catch (IOException e) {
/* 141 */       e.printStackTrace();
/* 142 */       throw new UtilException(e.getMessage(), new Object[0]);
/*     */     } 
/* 144 */     return handlerAnnInfo;
/*     */   }
/*     */   
/*     */   public static HandlerChainsModel buildHandlerChainsModel(Class<?> clazz) {
/* 148 */     if (clazz == null) {
/* 149 */       return null;
/*     */     }
/* 151 */     HandlerChain handlerChain = clazz.<HandlerChain>getAnnotation(HandlerChain.class);
/*     */     
/* 153 */     if (handlerChain == null)
/* 154 */       return null; 
/* 155 */     InputStream iStream = getFileAsStream(clazz, handlerChain);
/* 156 */     XMLStreamReader reader = XMLStreamReaderFactory.create(null, iStream, true);
/*     */     
/* 158 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 159 */     HandlerChainsModel handlerChainsModel = HandlerChainsModel.parseHandlerConfigFile(clazz, reader);
/*     */     try {
/* 161 */       reader.close();
/* 162 */       iStream.close();
/* 163 */     } catch (XMLStreamException e) {
/* 164 */       e.printStackTrace();
/* 165 */       throw new UtilException(e.getMessage(), new Object[0]);
/* 166 */     } catch (IOException e) {
/* 167 */       e.printStackTrace();
/* 168 */       throw new UtilException(e.getMessage(), new Object[0]);
/*     */     } 
/* 170 */     return handlerChainsModel;
/*     */   }
/*     */   
/*     */   static Class getClass(String className) {
/*     */     try {
/* 175 */       return Thread.currentThread().getContextClassLoader().loadClass(className);
/*     */     }
/* 177 */     catch (ClassNotFoundException e) {
/* 178 */       throw new UtilException("util.handler.class.not.found", new Object[] { className });
/*     */     } 
/*     */   }
/*     */   
/*     */   static Class getSEI(Class<?> clazz, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 184 */     if (metadataReader == null) {
/* 185 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 188 */     if (Provider.class.isAssignableFrom(clazz) || AsyncProvider.class.isAssignableFrom(clazz))
/*     */     {
/* 190 */       return null;
/*     */     }
/* 192 */     if (Service.class.isAssignableFrom(clazz))
/*     */     {
/* 194 */       return null;
/*     */     }
/*     */     
/* 197 */     WebService webService = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, clazz);
/* 198 */     if (webService == null) {
/* 199 */       throw new UtilException("util.handler.no.webservice.annotation", new Object[] { clazz.getCanonicalName() });
/*     */     }
/*     */     
/* 202 */     String ei = webService.endpointInterface();
/* 203 */     if (ei.length() > 0) {
/* 204 */       clazz = getClass(webService.endpointInterface());
/* 205 */       WebService ws = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, clazz);
/* 206 */       if (ws == null) {
/* 207 */         throw new UtilException("util.handler.endpoint.interface.no.webservice", new Object[] { webService.endpointInterface() });
/*     */       }
/*     */       
/* 210 */       return clazz;
/*     */     } 
/* 212 */     return null;
/*     */   }
/*     */   
/*     */   static InputStream getFileAsStream(Class clazz, HandlerChain chain) {
/* 216 */     URL url = clazz.getResource(chain.file());
/* 217 */     if (url == null) {
/* 218 */       url = Thread.currentThread().getContextClassLoader().getResource(chain.file());
/*     */     }
/*     */     
/* 221 */     if (url == null) {
/* 222 */       String tmp = clazz.getPackage().getName();
/* 223 */       tmp = tmp.replace('.', '/');
/* 224 */       tmp = tmp + "/" + chain.file();
/* 225 */       url = Thread.currentThread().getContextClassLoader().getResource(tmp);
/*     */     } 
/*     */     
/* 228 */     if (url == null) {
/* 229 */       throw new UtilException("util.failed.to.find.handlerchain.file", new Object[] { clazz.getName(), chain.file() });
/*     */     }
/*     */     
/*     */     try {
/* 233 */       return url.openStream();
/* 234 */     } catch (IOException e) {
/* 235 */       throw new UtilException("util.failed.to.parse.handlerchain.file", new Object[] { clazz.getName(), chain.file() });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\HandlerAnnotationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */