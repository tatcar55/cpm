/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BindingUtils
/*     */ {
/*     */   private static List<String> SOAP11_PARAMS;
/*     */   private static List<String> SOAP12_PARAMS;
/*     */   private static List<String> MTOM11_PARAMS;
/*     */   private static List<String> MTOM12_PARAMS;
/*     */   private static NegotiatedBindingContent SOAP11_BINDING_CONTENT;
/*     */   private static NegotiatedBindingContent SOAP12_BINDING_CONTENT;
/*     */   private static NegotiatedBindingContent MTOM11_BINDING_CONTENT;
/*     */   private static NegotiatedBindingContent MTOM12_BINDING_CONTENT;
/*     */   
/*     */   static {
/*  70 */     initiate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initiate() {
/*  77 */     SOAP11_PARAMS = Arrays.asList(new String[] { "charset", "SOAPAction" });
/*  78 */     SOAP12_PARAMS = Arrays.asList(new String[] { "charset", "action" });
/*     */ 
/*     */     
/*  81 */     MTOM11_PARAMS = new ArrayList<String>(SOAP11_PARAMS);
/*  82 */     MTOM11_PARAMS.add("boundary");
/*  83 */     MTOM11_PARAMS.add("start-info");
/*  84 */     MTOM11_PARAMS.add("type");
/*     */     
/*  86 */     MTOM12_PARAMS = new ArrayList<String>(SOAP12_PARAMS);
/*  87 */     MTOM12_PARAMS.add("boundary");
/*  88 */     MTOM12_PARAMS.add("start-info");
/*  89 */     MTOM12_PARAMS.add("type");
/*     */     
/*  91 */     SOAP11_BINDING_CONTENT = new NegotiatedBindingContent(new ArrayList<String>(1), SOAP11_PARAMS);
/*     */ 
/*     */     
/*  94 */     SOAP12_BINDING_CONTENT = new NegotiatedBindingContent(new ArrayList<String>(1), SOAP12_PARAMS);
/*     */ 
/*     */     
/*  97 */     MTOM11_BINDING_CONTENT = new NegotiatedBindingContent(new ArrayList<String>(2), MTOM11_PARAMS);
/*     */ 
/*     */     
/* 100 */     MTOM12_BINDING_CONTENT = new NegotiatedBindingContent(new ArrayList<String>(2), MTOM12_PARAMS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (TCPSettings.getInstance().getEncodingMode() == TCPSettings.EncodingMode.FI_STATEFUL) {
/* 107 */       SOAP11_BINDING_CONTENT.negotiatedMimeTypes.add("application/vnd.sun.stateful.fastinfoset");
/* 108 */       SOAP12_BINDING_CONTENT.negotiatedMimeTypes.add("application/vnd.sun.stateful.soap+fastinfoset");
/*     */     } 
/*     */ 
/*     */     
/* 112 */     if (TCPSettings.getInstance().getEncodingMode() == TCPSettings.EncodingMode.FI_STATELESS) {
/* 113 */       SOAP11_BINDING_CONTENT.negotiatedMimeTypes.add("application/fastinfoset");
/* 114 */       SOAP12_BINDING_CONTENT.negotiatedMimeTypes.add("application/soap+fastinfoset");
/*     */     } 
/*     */ 
/*     */     
/* 118 */     SOAP11_BINDING_CONTENT.negotiatedMimeTypes.add("text/xml");
/* 119 */     SOAP12_BINDING_CONTENT.negotiatedMimeTypes.add("application/soap+xml");
/*     */ 
/*     */     
/* 122 */     MTOM11_BINDING_CONTENT.negotiatedMimeTypes.addAll(SOAP11_BINDING_CONTENT.negotiatedMimeTypes);
/* 123 */     MTOM11_BINDING_CONTENT.negotiatedMimeTypes.add("multipart/related");
/* 124 */     MTOM12_BINDING_CONTENT.negotiatedMimeTypes.addAll(SOAP12_BINDING_CONTENT.negotiatedMimeTypes);
/* 125 */     MTOM12_BINDING_CONTENT.negotiatedMimeTypes.add("multipart/related");
/*     */   }
/*     */   
/*     */   public static NegotiatedBindingContent getNegotiatedContentTypesAndParams(WSBinding binding) {
/* 129 */     if (binding.getSOAPVersion().equals(SOAPVersion.SOAP_11)) {
/* 130 */       if (isMTOMEnabled(binding)) {
/* 131 */         return MTOM11_BINDING_CONTENT;
/*     */       }
/* 133 */       return SOAP11_BINDING_CONTENT;
/*     */     } 
/* 135 */     if (binding.getSOAPVersion().equals(SOAPVersion.SOAP_12)) {
/* 136 */       if (isMTOMEnabled(binding)) {
/* 137 */         return MTOM12_BINDING_CONTENT;
/*     */       }
/* 139 */       return SOAP12_BINDING_CONTENT;
/*     */     } 
/*     */ 
/*     */     
/* 143 */     throw new AssertionError(MessagesMessages.WSTCP_0009_UNKNOWN_BINDING(binding));
/*     */   }
/*     */   
/*     */   private static boolean isMTOMEnabled(WSBinding binding) {
/* 147 */     return binding.isFeatureEnabled(MTOMFeature.class);
/*     */   }
/*     */   
/*     */   public static final class NegotiatedBindingContent {
/*     */     public final List<String> negotiatedMimeTypes;
/*     */     public final List<String> negotiatedParams;
/*     */     
/*     */     public NegotiatedBindingContent(List<String> negotiatedMimeTypes, List<String> negotiatedParams) {
/* 155 */       this.negotiatedMimeTypes = negotiatedMimeTypes;
/* 156 */       this.negotiatedParams = negotiatedParams;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\BindingUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */