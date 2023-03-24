/*     */ package com.sun.xml.ws.transport.tcp.servicechannel;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.ws.api.server.PortAddressResolver;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceChannelWSDLGenerator
/*     */ {
/*     */   private static final String TCP_ENDPOINT_ADDRESS_STUB = "vnd.sun.ws.tcp://CHANGED_BY_RUNTIME";
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  73 */     QName serviceName = WSEndpoint.getDefaultServiceName(ServiceChannelWSImpl.class);
/*  74 */     QName portName = WSEndpoint.getDefaultPortName(serviceName, ServiceChannelWSImpl.class);
/*  75 */     BindingID bindingId = BindingID.parse(ServiceChannelWSImpl.class);
/*  76 */     WSBinding binding = bindingId.createBinding();
/*  77 */     Collection<SDDocumentSource> docs = new ArrayList<SDDocumentSource>(0);
/*     */     
/*  79 */     WSEndpoint<?> endpoint = WSEndpoint.create(ServiceChannelWSImpl.class, true, null, serviceName, portName, null, binding, null, docs, (URL)null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     DocumentAddressResolver resolver = new DocumentAddressResolver() {
/*     */         public String getRelativeAddressFor(SDDocument current, SDDocument referenced) {
/*  88 */           if (current.isWSDL() && referenced.isSchema() && referenced.getURL().getProtocol().equals("file")) {
/*  89 */             return referenced.getURL().getFile().substring(1);
/*     */           }
/*     */           
/*  92 */           return referenced.getURL().toExternalForm();
/*     */         }
/*     */       };
/*     */     
/*  96 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */ 
/*     */ 
/*     */     
/* 100 */     TransformerFactory tFactory = TransformerFactory.newInstance();
/*     */     
/* 102 */     Transformer transformer = tFactory.newTransformer();
/* 103 */     transformer.setOutputProperty("indent", "yes");
/*     */     
/* 105 */     for (Iterator<SDDocument> it = endpoint.getServiceDefinition().iterator(); it.hasNext(); ) {
/* 106 */       SDDocument document = it.next();
/* 107 */       baos.reset();
/*     */       
/* 109 */       document.writeTo(new PortAddressResolver() { @Nullable
/*     */             public String getAddressFor(QName serviceName, @NotNull String portName) {
/* 111 */               return "vnd.sun.ws.tcp://CHANGED_BY_RUNTIME";
/*     */             }
/*     */           },  resolver, baos);
/* 114 */       ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
/*     */       
/* 116 */       FileOutputStream fos = new FileOutputStream("./etc/" + document.getURL().getFile());
/* 117 */       Source source = new StreamSource(bais);
/* 118 */       StreamResult result = new StreamResult(fos);
/* 119 */       transformer.transform(source, result);
/* 120 */       fos.close();
/* 121 */       bais.close();
/*     */     } 
/*     */     
/* 124 */     baos.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\ServiceChannelWSDLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */