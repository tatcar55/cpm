/*     */ package com.sun.xml.ws.developer;
/*     */ 
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public class MemberSubmissionAddressingFeature
/*     */   extends WebServiceFeature
/*     */ {
/*     */   public static final String ID = "http://java.sun.com/xml/ns/jaxws/2004/08/addressing";
/*     */   public static final String IS_REQUIRED = "ADDRESSING_IS_REQUIRED";
/*     */   private boolean required;
/*     */   
/*     */   public MemberSubmissionAddressingFeature() {
/*  76 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberSubmissionAddressingFeature(boolean enabled) {
/*  86 */     this.enabled = enabled;
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
/*     */   public MemberSubmissionAddressingFeature(boolean enabled, boolean required) {
/*  99 */     this.enabled = enabled;
/* 100 */     this.required = required;
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
/*     */   @FeatureConstructor({"enabled", "required", "validation"})
/*     */   public MemberSubmissionAddressingFeature(boolean enabled, boolean required, MemberSubmissionAddressing.Validation validation) {
/* 116 */     this.enabled = enabled;
/* 117 */     this.required = required;
/* 118 */     this.validation = validation;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public String getID() {
/* 124 */     return "http://java.sun.com/xml/ns/jaxws/2004/08/addressing";
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean isRequired() {
/* 129 */     return this.required;
/*     */   }
/*     */   
/*     */   public void setRequired(boolean required) {
/* 133 */     this.required = required;
/*     */   }
/*     */   
/* 136 */   private MemberSubmissionAddressing.Validation validation = MemberSubmissionAddressing.Validation.LAX;
/*     */   public void setValidation(MemberSubmissionAddressing.Validation validation) {
/* 138 */     this.validation = validation;
/*     */   }
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   public MemberSubmissionAddressing.Validation getValidation() {
/* 144 */     return this.validation;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\developer\MemberSubmissionAddressingFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */