/*      */ package com.sun.xml.wss.impl.policy.mls;
/*      */ 
/*      */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import java.security.cert.X509Certificate;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import org.w3c.dom.Element;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AuthenticationTokenPolicy
/*      */   extends WSSFeatureBindingExtension
/*      */ {
/*      */   public AuthenticationTokenPolicy() {
/*   86 */     setPolicyIdentifier("AuthenticationTokenPolicy");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(WSSPolicy policy) {
/*   95 */     boolean _assert = false;
/*      */     
/*      */     try {
/*   98 */       if (!PolicyTypeUtil.authenticationTokenPolicy((SecurityPolicy)policy)) {
/*   99 */         return false;
/*      */       }
/*  101 */       AuthenticationTokenPolicy aPolicy = (AuthenticationTokenPolicy)policy;
/*  102 */       _assert = ((WSSPolicy)getFeatureBinding()).equals((WSSPolicy)aPolicy.getFeatureBinding());
/*  103 */     } catch (Exception cce) {}
/*      */ 
/*      */     
/*  106 */     return _assert;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/*  115 */     return equals(policy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*  123 */     AuthenticationTokenPolicy atPolicy = new AuthenticationTokenPolicy();
/*      */     
/*      */     try {
/*  126 */       WSSPolicy fBinding = (WSSPolicy)getFeatureBinding();
/*  127 */       WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*      */       
/*  129 */       if (fBinding != null) {
/*  130 */         atPolicy.setFeatureBinding((MLSPolicy)fBinding.clone());
/*      */       }
/*  132 */       if (kBinding != null) {
/*  133 */         atPolicy.setKeyBinding((MLSPolicy)kBinding.clone());
/*      */       }
/*  135 */     } catch (Exception e) {}
/*      */ 
/*      */     
/*  138 */     return atPolicy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getType() {
/*  145 */     return "AuthenticationTokenPolicy";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class UsernameTokenBinding
/*      */     extends KeyBindingBase
/*      */   {
/*  167 */     String nonce = MessageConstants._EMPTY;
/*  168 */     String username = MessageConstants._EMPTY;
/*  169 */     String password = MessageConstants._EMPTY;
/*  170 */     String _referenceType = MessageConstants._EMPTY;
/*  171 */     UsernameToken usernametoken = null;
/*  172 */     String _valueType = MessageConstants._EMPTY;
/*      */     
/*      */     boolean useNonce = false;
/*      */     
/*      */     boolean doDigest = false;
/*      */     boolean noPasswd = false;
/*  178 */     long maxNonceAge = 0L;
/*  179 */     private String strId = null;
/*  180 */     String _keyAlgorithm = MessageConstants._EMPTY;
/*  181 */     private byte[] sKey = null;
/*  182 */     private SecretKey secretKey = null;
/*  183 */     private SecretKey Key = null;
/*      */     
/*      */     private boolean endorsing;
/*      */     
/*      */     boolean useCreated = false;
/*      */ 
/*      */     
/*      */     public UsernameTokenBinding() {
/*  191 */       setPolicyIdentifier("UsernameTokenBinding");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public UsernameTokenBinding(String username, String password, String nonce, boolean doDigest, String creationTime) {
/*  204 */       this();
/*      */       
/*  206 */       this.username = username;
/*  207 */       this.password = password;
/*  208 */       this.nonce = nonce;
/*  209 */       this.doDigest = doDigest;
/*      */     }
/*      */     
/*      */     public String getReferenceType() {
/*  213 */       return this._referenceType;
/*      */     }
/*      */ 
/*      */     
/*      */     public UsernameToken getUsernameToken() {
/*  218 */       return this.usernametoken;
/*      */     }
/*      */     
/*      */     public void isEndorsing(boolean flag) {
/*  222 */       this.endorsing = flag;
/*      */     }
/*      */     public boolean isEndorsing() {
/*  225 */       return this.endorsing;
/*      */     }
/*      */     
/*      */     public void setUsernameToken(UsernameToken token) {
/*  229 */       if (isReadOnly()) {
/*  230 */         throw new RuntimeException("Can not set UsernameToken : Policy is ReadOnly");
/*      */       }
/*  232 */       this.usernametoken = token;
/*      */     }
/*      */     
/*      */     public void setReferenceType(String referenceType) {
/*  236 */       if (isReadOnly()) {
/*  237 */         throw new RuntimeException("Can not set ReferenceType of UsernameToken : Policy is ReadOnly");
/*      */       }
/*  239 */       this._referenceType = referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MLSPolicy newTimestampFeatureBinding() throws PolicyGenerationException {
/*  249 */       if (isReadOnly()) {
/*  250 */         throw new RuntimeException("Can not create a feature binding of Timestamp type for ReadOnly " + this._policyIdentifier);
/*      */       }
/*      */       
/*  253 */       if (this._policyIdentifier != "UsernameTokenBinding" && this._policyIdentifier != "SignaturePolicy.FeatureBinding") {
/*  254 */         throw new PolicyGenerationException("Can not create a feature binding of Timestamp type for " + this._policyIdentifier);
/*      */       }
/*  256 */       this._featureBinding = new TimestampPolicy();
/*  257 */       return this._featureBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setUsername(String username) {
/*  265 */       if (isReadOnly()) {
/*  266 */         throw new RuntimeException("Can not set Username : Policy is ReadOnly");
/*      */       }
/*  268 */       this.username = username;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPassword(String password) {
/*  276 */       if (isReadOnly()) {
/*  277 */         throw new RuntimeException("Can not set Password : Policy is ReadOnly");
/*      */       }
/*  279 */       this.password = password;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setNonce(String nonce) {
/*  287 */       if (isReadOnly()) {
/*  288 */         throw new RuntimeException("Can not set Nonce : Policy is ReadOnly");
/*      */       }
/*      */       
/*  291 */       this.nonce = nonce;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setUseNonce(boolean useNonce) {
/*  300 */       if (isReadOnly()) {
/*  301 */         throw new RuntimeException("Can not set useNonce flag : Policy is ReadOnly");
/*      */       }
/*      */       
/*  304 */       this.useNonce = useNonce;
/*      */     }
/*      */     
/*      */     public void setUseCreated(boolean useCreated) {
/*  308 */       if (isReadOnly()) {
/*  309 */         throw new RuntimeException("Can not set useCreated flag : Policy is ReadOnly");
/*      */       }
/*      */       
/*  312 */       this.useCreated = useCreated;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setDigestOn(boolean doDigest) {
/*  321 */       if (isReadOnly()) {
/*  322 */         throw new RuntimeException("Can not set digest flag : Policy is ReadOnly");
/*      */       }
/*      */       
/*  325 */       this.doDigest = doDigest;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setMaxNonceAge(long nonceAge) {
/*  336 */       if (isReadOnly()) {
/*  337 */         throw new RuntimeException("Can not set maxNonceAge flag : Policy is ReadOnly");
/*      */       }
/*      */       
/*  340 */       this.maxNonceAge = nonceAge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getUsername() {
/*  348 */       return this.username;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getPassword() {
/*  356 */       return this.password;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getNonce() {
/*  364 */       return this.nonce;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getUseNonce() {
/*  372 */       return this.useNonce;
/*      */     }
/*      */     
/*      */     public boolean getUseCreated() {
/*  376 */       return this.useCreated;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getDigestOn() {
/*  383 */       return this.doDigest;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getMaxNonceAge() {
/*  390 */       return this.maxNonceAge;
/*      */     }
/*      */     
/*      */     public boolean hasNoPassword() {
/*  394 */       return this.noPasswd;
/*      */     }
/*      */     
/*      */     public void setNoPassword(boolean value) {
/*  398 */       this.noPasswd = value;
/*      */     }
/*      */     
/*      */     public void setSTRID(String id) {
/*  402 */       if (isReadOnly()) {
/*  403 */         throw new RuntimeException("Can not set STRID attribute : Policy is ReadOnly");
/*      */       }
/*      */       
/*  406 */       this.strId = id;
/*      */     }
/*      */     
/*      */     public String getSTRID() {
/*  410 */       return this.strId;
/*      */     }
/*      */     
/*      */     public void setValueType(String valueType) {
/*  414 */       if (isReadOnly()) {
/*  415 */         throw new RuntimeException("Can not set valueType of usernameToken : Policy is ReadOnly");
/*      */       }
/*  417 */       this._valueType = valueType;
/*      */     }
/*      */     
/*      */     public void setKeyAlgorithm(String keyAlgorithm) {
/*  421 */       if (isReadOnly()) {
/*  422 */         throw new RuntimeException("Can not set KeyAlgorithm : Policy is ReadOnly");
/*      */       }
/*  424 */       this._keyAlgorithm = keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getKeyAlgorithm() {
/*  430 */       return this._keyAlgorithm;
/*      */     }
/*      */     
/*      */     public void setSecretKey(SecretKey secretKey) {
/*  434 */       this.secretKey = secretKey;
/*      */     }
/*      */     
/*      */     public void setSecretKey(byte[] secretKey) {
/*  438 */       this.sKey = secretKey;
/*      */     }
/*      */     
/*      */     public SecretKey getSecretKey(String algorithm) {
/*  442 */       if (this.Key == null) {
/*  443 */         this.Key = new SecretKeySpec(this.sKey, algorithm);
/*      */       }
/*  445 */       return this.Key;
/*      */     }
/*      */     
/*      */     public SecretKey getSecretKey() {
/*  449 */       return this.secretKey;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(WSSPolicy policy) {
/*  456 */       boolean assrt = false;
/*      */       
/*      */       try {
/*  459 */         if (!PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)policy)) {
/*  460 */           return false;
/*      */         }
/*  462 */         UsernameTokenBinding utBinding = (UsernameTokenBinding)policy;
/*  463 */         assrt = (this.useNonce == utBinding.getUseNonce() && this.doDigest == utBinding.getDigestOn() && this.useCreated == utBinding.getUseCreated());
/*      */       }
/*  465 */       catch (Exception e) {}
/*      */ 
/*      */       
/*  468 */       return assrt;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equalsIgnoreTargets(WSSPolicy policy) {
/*  477 */       return equals(policy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  484 */       UsernameTokenBinding utBinding = new UsernameTokenBinding();
/*      */       try {
/*  486 */         utBinding.setUsername(this.username);
/*  487 */         utBinding.setPassword(this.password);
/*  488 */         utBinding.setNonce(this.nonce);
/*  489 */         utBinding.setUseNonce(this.useNonce);
/*  490 */         utBinding.setUseCreated(this.useCreated);
/*  491 */         utBinding.setReferenceType(this._referenceType);
/*  492 */         utBinding.setDigestOn(this.doDigest);
/*  493 */         utBinding.setUsernameToken(this.usernametoken);
/*  494 */         utBinding.setUUID(this.UUID);
/*  495 */         WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*      */         
/*  497 */         if (kBinding != null) {
/*  498 */           utBinding.setKeyBinding((MLSPolicy)kBinding.clone());
/*      */         }
/*  500 */         utBinding.isEndorsing(this.endorsing);
/*      */         
/*  502 */         utBinding.setIncludeToken(getIncludeToken());
/*  503 */         utBinding.setPolicyTokenFlag(policyTokenWasSet());
/*  504 */         utBinding.isOptional(this._isOptional);
/*  505 */       } catch (Exception e) {
/*  506 */         e.printStackTrace();
/*      */       } 
/*  508 */       return utBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getType() {
/*  515 */       return "UsernameTokenBinding";
/*      */     }
/*      */     
/*      */     public String toString() {
/*  519 */       return "UsernameTokenBinding::" + getUsername();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class X509CertificateBinding
/*      */     extends KeyBindingBase
/*      */   {
/*  537 */     String _valueType = MessageConstants._EMPTY;
/*  538 */     String _encodingType = MessageConstants._EMPTY;
/*  539 */     String _referenceType = MessageConstants._EMPTY;
/*      */ 
/*      */     
/*  542 */     X509Certificate _certificate = null;
/*  543 */     String _keyAlgorithm = MessageConstants._EMPTY;
/*  544 */     String _certificateIdentifier = "";
/*  545 */     String strId = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X509CertificateBinding() {
/*  551 */       setPolicyIdentifier("X509CertificateBinding");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X509CertificateBinding(String certificateIdentifier, String keyAlgorithm) {
/*  559 */       this();
/*  560 */       this._certificateIdentifier = certificateIdentifier;
/*  561 */       this._keyAlgorithm = keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MLSPolicy newPrivateKeyBinding() {
/*  569 */       if (isReadOnly()) {
/*  570 */         throw new RuntimeException("Can not create PrivateKeyBinding : Policy is Readonly");
/*      */       }
/*      */       
/*  573 */       this._keyBinding = new PrivateKeyBinding();
/*  574 */       return this._keyBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueType(String valueType) {
/*  582 */       if (isReadOnly()) {
/*  583 */         throw new RuntimeException("Can not set ValueType of X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/*  586 */       this._valueType = valueType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEncodingType(String encodingType) {
/*  594 */       if (isReadOnly()) {
/*  595 */         throw new RuntimeException("Can not set EncodingType of X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/*  598 */       this._encodingType = encodingType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setReferenceType(String referenceType) {
/*  606 */       if (isReadOnly()) {
/*  607 */         throw new RuntimeException("Can not set ReferenceType of X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/*  610 */       this._referenceType = referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCertificateIdentifier(String certificateIdentifier) {
/*  618 */       if (isReadOnly()) {
/*  619 */         throw new RuntimeException("Can not set X509Certificate Identifier : Policy is ReadOnly");
/*      */       }
/*      */       
/*  622 */       this._certificateIdentifier = certificateIdentifier;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setX509Certificate(X509Certificate certificate) {
/*  630 */       if (isReadOnly()) {
/*  631 */         throw new RuntimeException("Can not set X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/*  634 */       this._certificate = certificate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getValueType() {
/*  641 */       return this._valueType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getEncodingType() {
/*  648 */       return this._encodingType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getReferenceType() {
/*  655 */       return this._referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getCertificateIdentifier() {
/*  662 */       return this._certificateIdentifier;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X509Certificate getX509Certificate() {
/*  669 */       return this._certificate;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setKeyAlgorithm(String keyAlgorithm) {
/*  676 */       if (isReadOnly()) {
/*  677 */         throw new RuntimeException("Can not set KeyAlgorithm : Policy is ReadOnly");
/*      */       }
/*      */       
/*  680 */       this._keyAlgorithm = keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getKeyAlgorithm() {
/*  687 */       return this._keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSTRID(String id) {
/*  696 */       if (isReadOnly()) {
/*  697 */         throw new RuntimeException("Can not set STRID attribute : Policy is ReadOnly");
/*      */       }
/*      */       
/*  700 */       this.strId = id;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSTRID() {
/*  709 */       return this.strId;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(WSSPolicy policy) {
/*  718 */       boolean assrt = false;
/*      */       
/*      */       try {
/*  721 */         if (!PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)policy)) {
/*  722 */           return false;
/*      */         }
/*  724 */         X509CertificateBinding ctBinding = (X509CertificateBinding)policy;
/*      */         
/*  726 */         boolean b1 = this._valueType.equals("") ? true : this._valueType.equals(ctBinding.getValueType());
/*  727 */         if (!b1) {
/*  728 */           return false;
/*      */         }
/*  730 */         boolean b2 = this._encodingType.equals("") ? true : this._encodingType.equals(ctBinding.getEncodingType());
/*  731 */         if (!b2) {
/*  732 */           return false;
/*      */         }
/*  734 */         boolean b3 = this._referenceType.equals("") ? true : this._referenceType.equals(ctBinding.getReferenceType());
/*  735 */         if (!b3) {
/*  736 */           return false;
/*      */         }
/*  738 */         boolean b4 = this._keyAlgorithm.equals("") ? true : this._keyAlgorithm.equals(ctBinding.getKeyAlgorithm());
/*  739 */         if (!b4) {
/*  740 */           return false;
/*      */         }
/*  742 */         if (this.strId == null && ctBinding.getSTRID() == null) {
/*  743 */           return true;
/*      */         }
/*      */         
/*  746 */         if (this.strId != null && this.strId.equals(ctBinding.getSTRID())) {
/*  747 */           return true;
/*      */         }
/*  749 */       } catch (Exception e) {}
/*      */       
/*  751 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equalsIgnoreTargets(WSSPolicy policy) {
/*  760 */       return equals(policy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  768 */       X509CertificateBinding x509Binding = new X509CertificateBinding();
/*      */       
/*      */       try {
/*  771 */         x509Binding.setValueType(this._valueType);
/*  772 */         x509Binding.setEncodingType(this._encodingType);
/*  773 */         x509Binding.setReferenceType(this._referenceType);
/*  774 */         x509Binding.setKeyAlgorithm(this._keyAlgorithm);
/*  775 */         x509Binding.setCertificateIdentifier(this._certificateIdentifier);
/*  776 */         x509Binding.setX509Certificate(this._certificate);
/*  777 */         x509Binding.setUUID(this.UUID);
/*  778 */         x509Binding.setSTRID(this.strId);
/*      */         
/*  780 */         WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*      */         
/*  782 */         if (kBinding != null) {
/*  783 */           x509Binding.setKeyBinding((MLSPolicy)kBinding.clone());
/*      */         }
/*      */         
/*  786 */         x509Binding.setIncludeToken(getIncludeToken());
/*  787 */         x509Binding.setPolicyTokenFlag(policyTokenWasSet());
/*  788 */         x509Binding.isOptional(this._isOptional);
/*  789 */       } catch (Exception e) {}
/*      */ 
/*      */       
/*  792 */       return x509Binding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getType() {
/*  799 */       return "X509CertificateBinding";
/*      */     }
/*      */     
/*      */     public String toString() {
/*  803 */       return "X509CertificateBinding::" + getCertificateIdentifier() + "::" + this.strId + "::" + this._referenceType;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class KerberosTokenBinding
/*      */     extends KeyBindingBase
/*      */   {
/*  814 */     String _valueType = MessageConstants._EMPTY;
/*  815 */     String _encodingType = MessageConstants._EMPTY;
/*  816 */     String _referenceType = MessageConstants._EMPTY;
/*  817 */     byte[] _token = null;
/*      */     private String strId;
/*  819 */     String _keyAlgorithm = MessageConstants._EMPTY;
/*  820 */     SecretKey _secretKey = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KerberosTokenBinding() {
/*  826 */       setPolicyIdentifier("KerberosTokenBinding");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueType(String valueType) {
/*  834 */       if (isReadOnly()) {
/*  835 */         throw new RuntimeException("Can not set valueType of KerberosToken : Policy is ReadOnly");
/*      */       }
/*  837 */       this._valueType = valueType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setReferenceType(String referenceType) {
/*  845 */       if (isReadOnly()) {
/*  846 */         throw new RuntimeException("Can not set referenceType of KerberosToken : Policy is ReadOnly");
/*      */       }
/*  848 */       this._referenceType = referenceType;
/*      */     }
/*      */     
/*      */     public void setEncodingType(String encodingType) {
/*  852 */       if (isReadOnly()) {
/*  853 */         throw new RuntimeException("Can not set EncodingType of KerberosToken : Policy is ReadOnly");
/*      */       }
/*  855 */       this._encodingType = encodingType;
/*      */     }
/*      */     
/*      */     public void setTokenValue(byte[] token) {
/*  859 */       if (isReadOnly()) {
/*  860 */         throw new RuntimeException("Can not set TokenValue of KerberosToken : Policy is ReadOnly");
/*      */       }
/*  862 */       this._token = token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSTRID(String id) {
/*  871 */       if (isReadOnly()) {
/*  872 */         throw new RuntimeException("Can not set STRID attribute : Policy is ReadOnly");
/*      */       }
/*      */       
/*  875 */       this.strId = id;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSTRID() {
/*  884 */       return this.strId;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getValueType() {
/*  891 */       return this._valueType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getReferenceType() {
/*  898 */       return this._referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getEncodingType() {
/*  905 */       return this._encodingType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public byte[] getTokenValue() {
/*  912 */       return this._token;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setKeyAlgorithm(String keyAlgorithm) {
/*  919 */       if (isReadOnly()) {
/*  920 */         throw new RuntimeException("Can not set KeyAlgorithm : Policy is ReadOnly");
/*      */       }
/*      */       
/*  923 */       this._keyAlgorithm = keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getKeyAlgorithm() {
/*  930 */       return this._keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSecretKey(SecretKey secretKey) {
/*  938 */       this._secretKey = secretKey;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SecretKey getSecretKey() {
/*  945 */       return this._secretKey;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  953 */       KerberosTokenBinding ktBinding = new KerberosTokenBinding();
/*      */       
/*      */       try {
/*  956 */         ktBinding.setValueType(this._valueType);
/*  957 */         ktBinding.setEncodingType(this._encodingType);
/*  958 */         ktBinding.setTokenValue(this._token);
/*  959 */         ktBinding.setKeyAlgorithm(this._keyAlgorithm);
/*  960 */         ktBinding.setUUID(this.UUID);
/*      */         
/*  962 */         SecretKeySpec ky0 = (SecretKeySpec)this._secretKey;
/*  963 */         if (ky0 != null) {
/*  964 */           SecretKeySpec key = new SecretKeySpec(ky0.getEncoded(), ky0.getAlgorithm());
/*  965 */           ktBinding.setSecretKey(key);
/*      */         } 
/*      */         
/*  968 */         WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*  969 */         if (kBinding != null) {
/*  970 */           ktBinding.setKeyBinding((MLSPolicy)kBinding.clone());
/*      */         }
/*  972 */         ktBinding.setIncludeToken(getIncludeToken());
/*  973 */         ktBinding.setPolicyTokenFlag(policyTokenWasSet());
/*  974 */       } catch (Exception e) {}
/*      */ 
/*      */       
/*  977 */       return ktBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(WSSPolicy policy) {
/*  985 */       boolean assrt = false;
/*      */       
/*      */       try {
/*  988 */         if (!PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)policy)) {
/*  989 */           return false;
/*      */         }
/*  991 */         KerberosTokenBinding ktBinding = (KerberosTokenBinding)policy;
/*      */         
/*  993 */         boolean b1 = this._valueType.equals("") ? true : this._valueType.equals(ktBinding.getValueType());
/*  994 */         if (!b1) {
/*  995 */           return false;
/*      */         }
/*  997 */         boolean b2 = this._encodingType.equals("") ? true : this._encodingType.equals(ktBinding.getEncodingType());
/*  998 */         if (!b2) {
/*  999 */           return false;
/*      */         }
/* 1001 */         boolean b3 = this._keyAlgorithm.equals("") ? true : this._keyAlgorithm.equals(ktBinding.getKeyAlgorithm());
/* 1002 */         if (!b3) {
/* 1003 */           return false;
/*      */         }
/*      */         
/* 1006 */         return true;
/* 1007 */       } catch (Exception e) {
/*      */         
/* 1009 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 1018 */       return equals(policy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getType() {
/* 1025 */       return "KerberosTokenBinding";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setIncludeToken(String include) {
/* 1035 */       if (INCLUDE_ALWAYS.equals(include) || INCLUDE_ALWAYS_TO_RECIPIENT.equals(include)) {
/* 1036 */         throw new UnsupportedOperationException("IncludeToken policy " + include + " is not supported for Kerberos Tokens, Consider using Once");
/*      */       }
/*      */ 
/*      */       
/* 1040 */       this.includeToken = include;
/* 1041 */       this.policyToken = true;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SAMLAssertionBinding
/*      */     extends KeyBindingBase
/*      */     implements LazyKeyBinding
/*      */   {
/* 1056 */     String _type = "";
/* 1057 */     String _keyAlgorithm = "";
/* 1058 */     String _keyIdentifier = "";
/* 1059 */     String _referenceType = "";
/* 1060 */     String _authorityIdentifier = "";
/* 1061 */     String strId = null;
/* 1062 */     String assertionId = null;
/* 1063 */     String samlVersion = null;
/*      */     
/*      */     public static final String V10_ASSERTION = "SAML10Assertion";
/*      */     
/*      */     public static final String V11_ASSERTION = "SAML11Assertion";
/*      */     
/*      */     public static final String V20_ASSERTION = "SAML20Assertion";
/*      */     
/*      */     public static final String SV_ASSERTION = "SV";
/*      */     
/*      */     public static final String HOK_ASSERTION = "HOK";
/*      */     
/* 1075 */     Element _assertion = null;
/* 1076 */     Element _authorityBinding = null;
/* 1077 */     XMLStreamReader samlAssertion = null;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SAMLAssertionBinding() {
/* 1083 */       setPolicyIdentifier("SAMLAssertionBinding");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SAMLAssertionBinding(String type, String keyIdentifier, String authorityIdentifier, String referenceType) {
/* 1096 */       this();
/* 1097 */       this._type = type;
/* 1098 */       this._keyIdentifier = keyIdentifier;
/* 1099 */       this._authorityIdentifier = authorityIdentifier;
/* 1100 */       this._referenceType = referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAssertionType(String type) {
/* 1108 */       if (isReadOnly()) {
/* 1109 */         throw new RuntimeException("Can not set SAMLAssertionType : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1112 */       if ("SV".equals(type)) {
/* 1113 */         this._type = "SV";
/* 1114 */       } else if ("HOK".equals(type)) {
/* 1115 */         this._type = "HOK";
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSAMLVersion(String ver) {
/* 1122 */       if (isReadOnly()) {
/* 1123 */         throw new RuntimeException("Can not set SAMLAssertionType : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1126 */       this.samlVersion = ver;
/*      */     }
/*      */     
/*      */     public String getSAMLVersion() {
/* 1130 */       return this.samlVersion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MLSPolicy newPrivateKeyBinding() {
/* 1138 */       if (isReadOnly()) {
/* 1139 */         throw new RuntimeException("Can not create PrivateKeyBinding : Policy is Readonly");
/*      */       }
/*      */       
/* 1142 */       this._keyBinding = new PrivateKeyBinding();
/* 1143 */       return this._keyBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setKeyIdentifier(String ki) {
/* 1151 */       if (isReadOnly()) {
/* 1152 */         throw new RuntimeException("Can not set SAML KeyIdentifier : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1155 */       this._keyIdentifier = ki;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAuthorityIdentifier(String uri) {
/* 1163 */       if (isReadOnly()) {
/* 1164 */         throw new RuntimeException("Can not set SAML AuthorityIdentifier : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1167 */       this._authorityIdentifier = uri;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setReferenceType(String rtype) {
/* 1175 */       if (isReadOnly()) {
/* 1176 */         throw new RuntimeException("Can not set SAML ReferenceType : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1179 */       this._referenceType = rtype;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAuthorityBinding(Element authorityBinding) {
/* 1187 */       if (isReadOnly()) {
/* 1188 */         throw new RuntimeException("Can not set SAML AuthorityBinding : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1191 */       this._authorityBinding = authorityBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAssertion(Element assertion) {
/* 1199 */       if (isReadOnly()) {
/* 1200 */         throw new RuntimeException("Can not set SAML Assertion : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1203 */       this._assertion = assertion;
/*      */     }
/*      */     
/*      */     public void setAssertion(XMLStreamReader reader) {
/* 1207 */       this.samlAssertion = reader;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setKeyAlgorithm(String algorithm) {
/* 1215 */       if (isReadOnly()) {
/* 1216 */         throw new RuntimeException("Can not set KeyAlgorithm : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1219 */       this._keyAlgorithm = algorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getKeyAlgorithm() {
/* 1226 */       return this._keyAlgorithm;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getReferenceType() {
/* 1233 */       return this._referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAssertionType() {
/* 1240 */       return this._type;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getKeyIdentifier() {
/* 1247 */       return this._keyIdentifier;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAuthorityIdentifier() {
/* 1254 */       return this._authorityIdentifier;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getAuthorityBinding() {
/* 1261 */       return this._authorityBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Element getAssertion() {
/* 1268 */       return this._assertion;
/*      */     }
/*      */     
/*      */     public XMLStreamReader getAssertionReader() {
/* 1272 */       return this.samlAssertion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(WSSPolicy policy) {
/*      */       try {
/* 1283 */         if (!PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)policy)) {
/* 1284 */           return false;
/*      */         }
/*      */         
/* 1287 */         SAMLAssertionBinding sBinding = (SAMLAssertionBinding)policy;
/*      */ 
/*      */         
/* 1290 */         boolean b1 = this._type.equals("") ? true : this._type.equals(sBinding.getAssertionType());
/* 1291 */         if (!b1) {
/* 1292 */           return false;
/*      */         }
/* 1294 */         boolean b2 = this._authorityIdentifier.equals("") ? true : this._authorityIdentifier.equals(sBinding.getAuthorityIdentifier());
/* 1295 */         if (!b2) {
/* 1296 */           return false;
/*      */         }
/* 1298 */         boolean b3 = this._referenceType.equals("") ? true : this._referenceType.equals(sBinding.getReferenceType());
/* 1299 */         if (!b3) {
/* 1300 */           return false;
/*      */         }
/* 1302 */         boolean b6 = this._keyAlgorithm.equals("") ? true : this._keyAlgorithm.equals(sBinding.getKeyAlgorithm());
/* 1303 */         if (!b6) {
/* 1304 */           return false;
/*      */         }
/* 1306 */         boolean b7 = (this.strId == null) ? true : this.strId.equals(sBinding.getSTRID());
/* 1307 */         if (!b7) {
/* 1308 */           return false;
/*      */         }
/* 1310 */         boolean b8 = (this.assertionId == null) ? true : this.assertionId.equals(sBinding.getAssertionId());
/* 1311 */         if (!b8) {
/* 1312 */           return false;
/*      */         }
/* 1314 */       } catch (Exception e) {}
/*      */ 
/*      */       
/* 1317 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equalsIgnoreTargets(WSSPolicy binding) {
/* 1326 */       return equals(binding);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/* 1333 */       SAMLAssertionBinding samlBinding = new SAMLAssertionBinding();
/*      */       
/*      */       try {
/* 1336 */         samlBinding.setAssertionType(this._type);
/* 1337 */         samlBinding.setKeyAlgorithm(this._keyAlgorithm);
/* 1338 */         samlBinding.setKeyIdentifier(this._keyIdentifier);
/* 1339 */         samlBinding.setReferenceType(this._referenceType);
/* 1340 */         samlBinding.setAuthorityIdentifier(this._authorityIdentifier);
/* 1341 */         samlBinding.setAssertion(this._assertion);
/* 1342 */         samlBinding.setAssertion(this.samlAssertion);
/* 1343 */         samlBinding.setAuthorityBinding(this._authorityBinding);
/* 1344 */         samlBinding.setSTRID(this.strId);
/* 1345 */         samlBinding.setAssertionId(this.assertionId);
/*      */         
/* 1347 */         samlBinding.setIncludeToken(getIncludeToken());
/* 1348 */         samlBinding.setPolicyTokenFlag(policyTokenWasSet());
/* 1349 */         samlBinding.isOptional(this._isOptional);
/* 1350 */       } catch (Exception e) {}
/*      */ 
/*      */       
/* 1353 */       return samlBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getType() {
/* 1360 */       return "SAMLAssertionBinding";
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setSTRID(String id) {
/* 1369 */       if (isReadOnly()) {
/* 1370 */         throw new RuntimeException("Can not set SAML STRID : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1373 */       this.strId = id;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getSTRID() {
/* 1382 */       return this.strId;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAssertionId(String id) {
/* 1393 */       if (isReadOnly()) {
/* 1394 */         throw new RuntimeException("Can not set SAML AssertionID : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1397 */       this.assertionId = id;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getAssertionId() {
/* 1407 */       return this.assertionId;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1411 */       return "SAMLAssertionBinding::" + getReferenceType() + "::" + this._type;
/*      */     }
/*      */     
/*      */     public Element get_assertion() {
/* 1415 */       return this._assertion;
/*      */     }
/*      */     
/*      */     public String getRealId() {
/* 1419 */       return this.assertionId;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setRealId(String realId) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class KeyValueTokenBinding
/*      */     extends KeyBindingBase
/*      */   {
/* 1441 */     String _valueType = MessageConstants._EMPTY;
/* 1442 */     String _encodingType = MessageConstants._EMPTY;
/* 1443 */     String _referenceType = MessageConstants._EMPTY;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyValueTokenBinding() {
/* 1449 */       setPolicyIdentifier("RsaTokenBinding");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MLSPolicy newPrivateKeyBinding() {
/* 1457 */       if (isReadOnly()) {
/* 1458 */         throw new RuntimeException("Can not create PrivateKeyBinding : Policy is Readonly");
/*      */       }
/*      */       
/* 1461 */       this._keyBinding = new PrivateKeyBinding();
/* 1462 */       return this._keyBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueType(String valueType) {
/* 1470 */       if (isReadOnly()) {
/* 1471 */         throw new RuntimeException("Can not set ValueType of X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1474 */       this._valueType = valueType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEncodingType(String encodingType) {
/* 1482 */       if (isReadOnly()) {
/* 1483 */         throw new RuntimeException("Can not set EncodingType of X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1486 */       this._encodingType = encodingType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setReferenceType(String referenceType) {
/* 1494 */       if (isReadOnly()) {
/* 1495 */         throw new RuntimeException("Can not set ReferenceType of X509Certificate : Policy is ReadOnly");
/*      */       }
/*      */       
/* 1498 */       this._referenceType = referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getValueType() {
/* 1505 */       return this._valueType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getEncodingType() {
/* 1512 */       return this._encodingType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getReferenceType() {
/* 1519 */       return this._referenceType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(WSSPolicy policy) {
/* 1528 */       boolean assrt = false;
/*      */       
/*      */       try {
/* 1531 */         if (!PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)policy)) {
/* 1532 */           return false;
/*      */         }
/* 1534 */         KeyValueTokenBinding rsaTokenBinding = (KeyValueTokenBinding)policy;
/*      */         
/* 1536 */         boolean b1 = this._valueType.equals("") ? true : this._valueType.equals(rsaTokenBinding.getValueType());
/* 1537 */         if (!b1) {
/* 1538 */           return false;
/*      */         }
/* 1540 */         boolean b2 = this._encodingType.equals("") ? true : this._encodingType.equals(rsaTokenBinding.getEncodingType());
/* 1541 */         if (!b2) {
/* 1542 */           return false;
/*      */         }
/* 1544 */         boolean b3 = this._referenceType.equals("") ? true : this._referenceType.equals(rsaTokenBinding.getReferenceType());
/* 1545 */         if (!b3) {
/* 1546 */           return false;
/*      */         }
/* 1548 */       } catch (Exception e) {}
/*      */       
/* 1550 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 1559 */       return equals(policy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object clone() {
/* 1567 */       KeyValueTokenBinding rsaTokenBinding = new KeyValueTokenBinding();
/*      */       
/*      */       try {
/* 1570 */         rsaTokenBinding.setValueType(this._valueType);
/* 1571 */         rsaTokenBinding.setEncodingType(this._encodingType);
/* 1572 */         rsaTokenBinding.setReferenceType(this._referenceType);
/* 1573 */         rsaTokenBinding.setUUID(this.UUID);
/*      */         
/* 1575 */         WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*      */         
/* 1577 */         if (kBinding != null) {
/* 1578 */           rsaTokenBinding.setKeyBinding((MLSPolicy)kBinding.clone());
/*      */         }
/*      */         
/* 1581 */         rsaTokenBinding.setIncludeToken(getIncludeToken());
/* 1582 */         rsaTokenBinding.setPolicyTokenFlag(policyTokenWasSet());
/* 1583 */       } catch (Exception e) {}
/*      */ 
/*      */       
/* 1586 */       return rsaTokenBinding;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getType() {
/* 1594 */       return getPolicyIdentifier();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1599 */       return getPolicyIdentifier() + "::" + this._referenceType;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\AuthenticationTokenPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */