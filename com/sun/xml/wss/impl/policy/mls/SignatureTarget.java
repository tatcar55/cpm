/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureTarget
/*     */   extends Target
/*     */   implements Cloneable
/*     */ {
/*  59 */   String _digestAlgorithm = "";
/*     */   
/*  61 */   ArrayList _transforms = new ArrayList();
/*     */   
/*     */   private boolean isOptimized = false;
/*     */   
/*  65 */   private String xpathVersion = "";
/*     */ 
/*     */   
/*     */   private boolean isITNever = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureTarget() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureTarget(Target target) {
/*  77 */     setEnforce(target.getEnforce());
/*  78 */     setType(target.getType());
/*  79 */     setValue(target.getValue());
/*  80 */     setContentOnly(target.getContentOnly());
/*  81 */     this._digestAlgorithm = "http://www.w3.org/2000/09/xmldsig#sha1";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureTarget(String digest, String transform) {
/*  90 */     this._digestAlgorithm = digest;
/*  91 */     this._transforms.add(new Transform(transform));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDigestAlgorithm() {
/*  98 */     return this._digestAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getTransforms() {
/* 105 */     return this._transforms;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void isITNever(boolean iToken) {
/* 111 */     this.isITNever = iToken;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isITNever() {
/* 117 */     return this.isITNever;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDigestAlgorithm(String digest) {
/* 125 */     if (isBSP() && digest.intern() != "http://www.w3.org/2000/09/xmldsig#sha1") {
/* 126 */       throw new RuntimeException("Does not meet BSP requirement 5420 for Digest Algorithm");
/*     */     }
/* 128 */     this._digestAlgorithm = digest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTransform(Transform transform) {
/* 137 */     String transformStr = transform.getTransform();
/* 138 */     if (isBSP() && transformStr != "http://www.w3.org/2001/10/xml-exc-c14n#" && transformStr != "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete-Transform" && transformStr != "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform" && transformStr != "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform" && transformStr != "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform" && transformStr != "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform" && transformStr != "http://www.w3.org/2002/06/xmldsig-filter2")
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       throw new RuntimeException("Does not meet BSP requirement 5423 for signature transforms");
/*     */     }
/* 148 */     this._transforms.add(transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform newSignatureTransform() {
/* 155 */     return new Transform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(SignatureTarget target) {
/* 165 */     boolean b1 = this._digestAlgorithm.equals("") ? true : this._digestAlgorithm.equals(target.getDigestAlgorithm());
/*     */     
/* 167 */     boolean b2 = this._transforms.equals(target.getTransforms());
/*     */     
/* 169 */     return (b1 && b2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 179 */     SignatureTarget target = new SignatureTarget();
/*     */     
/*     */     try {
/* 182 */       ArrayList<Object> list = target.getTransforms();
/*     */       
/* 184 */       target.setDigestAlgorithm(this._digestAlgorithm);
/* 185 */       target.setValue(getValue());
/* 186 */       target.setType(getType());
/* 187 */       target.setContentOnly(getContentOnly());
/* 188 */       target.setEnforce(getEnforce());
/*     */       
/* 190 */       Iterator<Transform> i = this._transforms.iterator();
/* 191 */       while (i.hasNext()) {
/* 192 */         Transform transform = i.next();
/* 193 */         list.add(transform.clone());
/*     */       } 
/* 195 */     } catch (Exception e) {}
/*     */     
/* 197 */     return target;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Transform
/*     */     implements Cloneable
/*     */   {
/* 205 */     String _transform = "";
/*     */ 
/*     */ 
/*     */     
/* 209 */     AlgorithmParameterSpec _algorithmParameters = null;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean disableInclusivePrefix = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Transform() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Transform(String algorithm) {
/* 223 */       this._transform = algorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AlgorithmParameterSpec getAlgorithmParameters() {
/* 231 */       return this._algorithmParameters;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAlgorithmParameters(AlgorithmParameterSpec param) {
/* 239 */       this._algorithmParameters = param;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTransform(String algorithm) {
/* 247 */       this._transform = algorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTransform() {
/* 254 */       return this._transform;
/*     */     }
/*     */     
/*     */     public boolean getDisableInclusivePrefix() {
/* 258 */       return this.disableInclusivePrefix;
/*     */     }
/*     */     
/*     */     public void setDisbaleInclusivePrefix(boolean disableInclusivePrefix) {
/* 262 */       this.disableInclusivePrefix = disableInclusivePrefix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Transform transform) {
/* 272 */       boolean b1 = this._transform.equals("") ? true : this._transform.equals(transform.getTransform());
/* 273 */       if (!b1) return false;
/*     */       
/* 275 */       boolean b2 = this._algorithmParameters.equals(transform.getAlgorithmParameters());
/* 276 */       if (!b2) return false;
/*     */       
/* 278 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 287 */       Transform transform = new Transform(this._transform);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       transform.setAlgorithmParameters(this._algorithmParameters);
/* 300 */       return transform;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isIsOptimized() {
/* 305 */     return this.isOptimized;
/*     */   }
/*     */   
/*     */   public void setIsOptimized(boolean isOptimized) {
/* 309 */     this.isOptimized = isOptimized;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setXPathVersion(String version) {
/* 314 */     this.xpathVersion = version;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getXPathVersion() {
/* 319 */     return this.xpathVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\SignatureTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */