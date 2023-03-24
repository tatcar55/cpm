/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricKeyBinding
/*     */   extends KeyBindingBase
/*     */ {
/*  65 */   String _keyAlgorithm = MessageConstants._EMPTY;
/*     */   
/*  67 */   String _keyIdentifier = MessageConstants._EMPTY;
/*     */   
/*  69 */   String _certAlias = MessageConstants._EMPTY;
/*     */   
/*     */   boolean _useReceivedSecret = false;
/*     */   
/*  73 */   SecretKey _secretKey = null;
/*     */ 
/*     */   
/*     */   private boolean isEKSHA1 = false;
/*     */ 
/*     */   
/*     */   public SymmetricKeyBinding() {
/*  80 */     setPolicyIdentifier("SymmetricKeyBinding");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymmetricKeyBinding(String keyIdentifier, String keyAlgorithm) {
/*  88 */     this();
/*     */     
/*  90 */     this._keyIdentifier = keyIdentifier;
/*  91 */     this._keyAlgorithm = keyAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyIdentifier(String keyIdentifier) {
/*  99 */     this._keyIdentifier = keyIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyIdentifier() {
/* 106 */     return this._keyIdentifier;
/*     */   }
/*     */   
/*     */   public void setCertAlias(String certAlias) {
/* 110 */     this._certAlias = certAlias;
/*     */   }
/*     */   
/*     */   public String getCertAlias() {
/* 114 */     return this._certAlias;
/*     */   }
/*     */   
/*     */   public void setUseReceivedSecret(boolean useReceivedSecret) {
/* 118 */     this._useReceivedSecret = useReceivedSecret;
/*     */   }
/*     */   
/*     */   public boolean getUseReceivedSecret() {
/* 122 */     return this._useReceivedSecret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyAlgorithm(String keyAlgorithm) {
/* 130 */     this._keyAlgorithm = keyAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyAlgorithm() {
/* 137 */     return this._keyAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecretKey(SecretKey secretKey) {
/* 145 */     this._secretKey = secretKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecretKey getSecretKey() {
/* 152 */     return this._secretKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy newX509CertificateKeyBinding() {
/* 163 */     if (isReadOnly()) {
/* 164 */       throw new RuntimeException("Can not create X509CertificateKeyBinding : Policy is Readonly");
/*     */     }
/* 166 */     this._keyBinding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 167 */     return this._keyBinding;
/*     */   }
/*     */   public boolean usesEKSHA1KeyBinding() {
/* 170 */     return this.isEKSHA1;
/*     */   }
/*     */   public void usesEKSHA1KeyBinding(boolean value) {
/* 173 */     this.isEKSHA1 = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/* 181 */     boolean assrt = false;
/*     */     
/*     */     try {
/* 184 */       SymmetricKeyBinding skBinding = (SymmetricKeyBinding)policy;
/*     */       
/* 186 */       boolean b1 = this._keyIdentifier.equals("") ? true : this._keyIdentifier.equals(skBinding.getKeyIdentifier());
/*     */       
/* 188 */       boolean b2 = this._keyAlgorithm.equals("") ? true : this._keyAlgorithm.equals(skBinding.getKeyAlgorithm());
/*     */       
/* 190 */       boolean b3 = this._certAlias.equals("") ? true : this._certAlias.equals(skBinding.getCertAlias());
/*     */       
/* 192 */       boolean b4 = !this._useReceivedSecret ? true : ((this._useReceivedSecret == skBinding.getUseReceivedSecret()));
/* 193 */       boolean b5 = this._keyBinding.equals(policy._keyBinding);
/* 194 */       boolean b6 = (this.isEKSHA1 == skBinding.usesEKSHA1KeyBinding());
/* 195 */       assrt = (b1 && b2 && b3 && b4 && b5 && b6);
/* 196 */     } catch (Exception e) {}
/*     */     
/* 198 */     return assrt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy binding) {
/* 207 */     return equals(binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 215 */     SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*     */     
/*     */     try {
/* 218 */       skBinding.setUUID(getUUID());
/* 219 */       skBinding.setKeyIdentifier(this._keyIdentifier);
/* 220 */       skBinding.setKeyAlgorithm(this._keyAlgorithm);
/* 221 */       skBinding.setCertAlias(this._certAlias);
/* 222 */       skBinding.setUseReceivedSecret(this._useReceivedSecret);
/* 223 */       skBinding.usesEKSHA1KeyBinding(this.isEKSHA1);
/* 224 */       SecretKeySpec ky0 = (SecretKeySpec)this._secretKey;
/* 225 */       if (ky0 != null) {
/* 226 */         SecretKeySpec key = new SecretKeySpec(ky0.getEncoded(), ky0.getAlgorithm());
/* 227 */         skBinding.setSecretKey(key);
/*     */       } 
/*     */       
/* 230 */       if (this._keyBinding != null) {
/* 231 */         if (this._keyBinding instanceof AuthenticationTokenPolicy.UsernameTokenBinding) {
/* 232 */           skBinding.setKeyBinding((AuthenticationTokenPolicy.UsernameTokenBinding)((AuthenticationTokenPolicy.UsernameTokenBinding)this._keyBinding).clone());
/*     */         }
/* 234 */         else if (this._keyBinding instanceof AuthenticationTokenPolicy.X509CertificateBinding) {
/* 235 */           skBinding.setKeyBinding((AuthenticationTokenPolicy.X509CertificateBinding)((AuthenticationTokenPolicy.X509CertificateBinding)this._keyBinding).clone());
/*     */         }
/* 237 */         else if (this._keyBinding instanceof AuthenticationTokenPolicy.KerberosTokenBinding) {
/* 238 */           skBinding.setKeyBinding((AuthenticationTokenPolicy.KerberosTokenBinding)((AuthenticationTokenPolicy.KerberosTokenBinding)this._keyBinding).clone());
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 243 */     catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 247 */     return skBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 254 */     return "SymmetricKeyBinding";
/*     */   }
/*     */   
/*     */   public String toString() {
/* 258 */     return "SymmetricKeyBinding::" + getKeyAlgorithm() + "::" + this._keyIdentifier;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\SymmetricKeyBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */