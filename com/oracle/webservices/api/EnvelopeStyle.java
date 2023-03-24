/*     */ package com.oracle.webservices.api;
/*     */ 
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import javax.xml.ws.spi.WebServiceFeatureAnnotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebServiceFeatureAnnotation(id = "", bean = EnvelopeStyleFeature.class)
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ public @interface EnvelopeStyle
/*     */ {
/*     */   Style[] style() default {Style.SOAP11};
/*     */   
/*     */   public enum Style
/*     */   {
/*  82 */     SOAP11("http://schemas.xmlsoap.org/wsdl/soap/http"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     SOAP12("http://www.w3.org/2003/05/soap/bindings/HTTP/"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     XML("http://www.w3.org/2004/08/wsdl/http");
/*     */ 
/*     */     
/*     */     public final String bindingId;
/*     */ 
/*     */ 
/*     */     
/*     */     Style(String id) {
/* 102 */       this.bindingId = id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSOAP11() {
/* 110 */       return equals(SOAP11);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSOAP12() {
/* 117 */       return equals(SOAP12);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isXML() {
/* 124 */       return equals(XML);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\EnvelopeStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */