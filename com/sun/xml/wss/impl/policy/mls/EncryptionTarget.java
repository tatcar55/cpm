/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptionTarget
/*     */   extends Target
/*     */   implements Cloneable
/*     */ {
/*  59 */   String _dataEncryptionAlgorithm = "";
/*     */   
/*  61 */   ArrayList _cipherReferenceTransforms = new ArrayList();
/*     */   
/*  63 */   Element drefData = null;
/*     */ 
/*     */   
/*     */   private boolean isOptimized = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionTarget() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionTarget(Target target) {
/*  75 */     setEnforce(target.getEnforce());
/*  76 */     setType(target.getType());
/*  77 */     setValue(target.getValue());
/*  78 */     setContentOnly(target.getContentOnly());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionTarget(String algorithm) {
/*  87 */     this._dataEncryptionAlgorithm = algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionTarget(String algorithm, String transform) {
/*  97 */     this._dataEncryptionAlgorithm = algorithm;
/*  98 */     this._cipherReferenceTransforms.add(new Transform(transform));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDataEncryptionAlgorithm(String algorithm) {
/* 106 */     this._dataEncryptionAlgorithm = algorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDataEncryptionAlgorithm() {
/* 113 */     return this._dataEncryptionAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCipherReferenceTransform(String transform) {
/* 122 */     this._cipherReferenceTransforms.add(new Transform(transform));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCipherReferenceTransform(Transform transform) {
/* 131 */     this._cipherReferenceTransforms.add(transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getCipherReferenceTransforms() {
/* 138 */     return this._cipherReferenceTransforms;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform newEncryptionTransform() {
/* 145 */     return new Transform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(EncryptionTarget target) {
/* 154 */     boolean b1 = this._dataEncryptionAlgorithm.equals("") ? true : this._dataEncryptionAlgorithm.equals(target.getDataEncryptionAlgorithm());
/*     */ 
/*     */     
/* 157 */     boolean b2 = this._cipherReferenceTransforms.equals(target.getCipherReferenceTransforms());
/*     */     
/* 159 */     return (b1 && b2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 168 */     EncryptionTarget target = new EncryptionTarget();
/*     */     
/*     */     try {
/* 171 */       target.setDataEncryptionAlgorithm(this._dataEncryptionAlgorithm);
/* 172 */       target.setValue(getValue());
/* 173 */       target.setType(getType());
/* 174 */       target.setContentOnly(getContentOnly());
/* 175 */       target.setEnforce(getEnforce());
/*     */       
/* 177 */       Iterator<Transform> i = getCipherReferenceTransforms().iterator();
/* 178 */       while (i.hasNext()) {
/* 179 */         Transform transform = i.next();
/* 180 */         target.getCipherReferenceTransforms().add(transform.clone());
/*     */       } 
/* 182 */     } catch (Exception e) {}
/*     */     
/* 184 */     return target;
/*     */   }
/*     */   
/*     */   public void setElementData(Element data) {
/* 188 */     this.drefData = data;
/*     */   }
/*     */   
/*     */   public Element getElementData() {
/* 192 */     return this.drefData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Transform
/*     */     implements Cloneable
/*     */   {
/* 201 */     String _transform = "";
/*     */     
/* 203 */     AlgorithmParameterSpec _algorithmParameters = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Transform() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Transform(String algorithm) {
/* 215 */       this._transform = algorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AlgorithmParameterSpec getAlgorithmParameters() {
/* 223 */       return this._algorithmParameters;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setAlgorithmParameters(AlgorithmParameterSpec params) {
/* 231 */       this._algorithmParameters = params;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setTransform(String algorithm) {
/* 239 */       this._transform = algorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTransform() {
/* 246 */       return this._transform;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Transform transform) {
/* 255 */       boolean b1 = this._transform.equals("") ? true : this._transform.equals(transform.getTransform());
/* 256 */       if (!b1) return false;
/*     */       
/* 258 */       boolean b2 = this._algorithmParameters.equals(transform.getAlgorithmParameters());
/* 259 */       if (!b2) return false;
/*     */       
/* 261 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 269 */       Transform transform = new Transform(this._transform);
/* 270 */       transform.setAlgorithmParameters(this._algorithmParameters);
/* 271 */       return transform;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isIsOptimized() {
/* 276 */     return this.isOptimized;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\EncryptionTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */