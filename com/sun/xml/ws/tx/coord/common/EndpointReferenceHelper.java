/*     */ package com.sun.xml.ws.tx.coord.common;
/*     */ 
/*     */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
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
/*     */ public abstract class EndpointReferenceHelper
/*     */ {
/*     */   public static EndpointReferenceHelper newInstance(EndpointReference epr) {
/*  57 */     if (epr == null) throw new IllegalArgumentException("EndpointReference can't be null"); 
/*  58 */     if (epr instanceof MemberSubmissionEndpointReference)
/*  59 */       return new MemberSubmissionEndpointReferenceHelper((MemberSubmissionEndpointReference)epr); 
/*  60 */     if (epr instanceof W3CEndpointReference)
/*  61 */       return new W3CEndpointReferenceHelper((W3CEndpointReference)epr); 
/*  62 */     throw new IllegalArgumentException(epr.getClass() + "is not a supported EndpointReference");
/*     */   }
/*     */   
/*     */   public abstract String getAddress();
/*     */   
/*     */   public abstract Node[] getReferenceParameters();
/*     */   
/*     */   static class MemberSubmissionEndpointReferenceHelper
/*     */     extends EndpointReferenceHelper {
/*     */     MemberSubmissionEndpointReference epr;
/*     */     
/*     */     MemberSubmissionEndpointReferenceHelper(MemberSubmissionEndpointReference epr) {
/*  74 */       this.epr = epr;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAddress() {
/*  79 */       return this.epr.addr.uri;
/*     */     }
/*     */ 
/*     */     
/*     */     public Node[] getReferenceParameters() {
/*  84 */       return (Node[])this.epr.referenceParameters.elements.toArray((Object[])new org.w3c.dom.Element[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   static class W3CEndpointReferenceHelper extends EndpointReferenceHelper {
/*  89 */     private static Field address = null;
/*  90 */     private static Field referenceParameters = null;
/*  91 */     private static Class address_class = null;
/*  92 */     private static Class referenceParameters_class = null;
/*  93 */     private static Field uri = null;
/*  94 */     private static Field elements = null; W3CEndpointReference epr;
/*     */     
/*     */     static {
/*     */       try {
/*  98 */         address = W3CEndpointReference.class.getDeclaredField("address");
/*  99 */         address.setAccessible(true);
/* 100 */         referenceParameters = W3CEndpointReference.class.getDeclaredField("referenceParameters");
/* 101 */         referenceParameters.setAccessible(true);
/* 102 */         address_class = Class.forName("javax.xml.ws.wsaddressing.W3CEndpointReference$Address");
/* 103 */         referenceParameters_class = Class.forName("javax.xml.ws.wsaddressing.W3CEndpointReference$Elements");
/* 104 */         uri = address_class.getDeclaredField("uri");
/* 105 */         uri.setAccessible(true);
/* 106 */         elements = referenceParameters_class.getDeclaredField("elements");
/* 107 */         elements.setAccessible(true);
/* 108 */       } catch (Exception e) {
/* 109 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     W3CEndpointReferenceHelper(W3CEndpointReference epr) {
/* 117 */       this.epr = epr;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAddress() {
/*     */       try {
/* 123 */         return (String)uri.get(address.get(this.epr));
/* 124 */       } catch (IllegalAccessException e) {
/* 125 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Node[] getReferenceParameters() {
/*     */       try {
/* 132 */         return (Node[])((List)elements.get(referenceParameters.get(this.epr))).toArray((Object[])new org.w3c.dom.Element[0]);
/* 133 */       } catch (IllegalAccessException e) {
/* 134 */         throw new AssertionError(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\EndpointReferenceHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */