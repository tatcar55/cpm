/*     */ package com.sun.xml.wss.impl.policy.verifier;
/*     */ 
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.PolicyViolationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.spi.PolicyVerifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptionPolicyVerifier
/*     */   implements PolicyVerifier
/*     */ {
/*     */   FilterProcessingContext context;
/*     */   
/*     */   public EncryptionPolicyVerifier(FilterProcessingContext context) {
/*  70 */     this.context = context;
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
/*     */   public void verifyPolicy(SecurityPolicy configPolicy, SecurityPolicy recvdPolicy) throws PolicyViolationException {
/*  82 */     if (PolicyTypeUtil.encryptionPolicy(configPolicy) && PolicyTypeUtil.encryptionPolicy(recvdPolicy)) {
/*  83 */       EncryptionPolicy rEP = (EncryptionPolicy)recvdPolicy;
/*  84 */       EncryptionPolicy cEP = (EncryptionPolicy)configPolicy;
/*     */       
/*  86 */       EncryptionPolicy.FeatureBinding rfBinding = (EncryptionPolicy.FeatureBinding)rEP.getFeatureBinding();
/*  87 */       EncryptionPolicy.FeatureBinding cfBinding = (EncryptionPolicy.FeatureBinding)cEP.getFeatureBinding();
/*  88 */       String rDA = rfBinding.getDataEncryptionAlgorithm();
/*  89 */       String cDA = cfBinding.getDataEncryptionAlgorithm();
/*  90 */       if (cDA != null && cDA.length() > 0 && 
/*  91 */         !cDA.equals(rDA)) {
/*  92 */         throw new PolicyViolationException("Receiver side requirement verification failed, DataEncryptionAlgorithm specified in the receiver requirements did match with DataEncryptionAlgorithm used to encrypt the message.Configured DataEncryptionAlgorithm is " + cDA + "  DataEncryptionAlgorithm used in the" + "message is " + rDA);
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkSAMLAssertionBinding(AuthenticationTokenPolicy.SAMLAssertionBinding configPolicy, AuthenticationTokenPolicy.SAMLAssertionBinding recvdPolicy) throws PolicyViolationException {
/* 134 */     boolean matched = true;
/*     */     
/* 136 */     String _cAI = configPolicy.getAuthorityIdentifier();
/* 137 */     String _rAI = recvdPolicy.getAuthorityIdentifier();
/* 138 */     if (_cAI != null && _cAI.length() > 0 && _rAI != null) {
/* 139 */       matched = _cAI.equals(_rAI);
/* 140 */       _throwError((SecurityPolicy)configPolicy, (SecurityPolicy)recvdPolicy, matched);
/*     */     } 
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
/*     */   private void checkX509CertificateBinding(AuthenticationTokenPolicy.X509CertificateBinding configPolicy, AuthenticationTokenPolicy.X509CertificateBinding recvdPolicy) throws PolicyViolationException {
/* 153 */     boolean matched = true;
/*     */     
/* 155 */     configPolicy = setReferenceType(configPolicy);
/* 156 */     String ckeyAlg = configPolicy.getKeyAlgorithm();
/* 157 */     String rkeyAlg = recvdPolicy.getKeyAlgorithm();
/* 158 */     if (ckeyAlg != null && ckeyAlg.length() > 0 && rkeyAlg.length() > 0) {
/* 159 */       matched = ckeyAlg.equals(rkeyAlg);
/*     */     }
/* 161 */     _throwError((SecurityPolicy)configPolicy, (SecurityPolicy)recvdPolicy, matched);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     String cVT = configPolicy.getValueType();
/* 172 */     String rVT = recvdPolicy.getValueType();
/*     */     
/* 174 */     if (cVT != null && cVT.length() > 0) {
/* 175 */       matched = cVT.equals(rVT);
/*     */     }
/* 177 */     _throwError((SecurityPolicy)configPolicy, (SecurityPolicy)recvdPolicy, matched);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void _throwError(SecurityPolicy configPolicy, SecurityPolicy recvdPolicy, boolean matched) throws PolicyViolationException {
/* 201 */     if (!matched)
/* 202 */       throw new PolicyViolationException("KeyType used to Encrypt the message doesnot match with  the receiver side requirements. Configured KeyType is " + configPolicy + " KeyType inferred from the message is  " + recvdPolicy); 
/*     */   }
/*     */   
/*     */   private AuthenticationTokenPolicy.X509CertificateBinding setReferenceType(AuthenticationTokenPolicy.X509CertificateBinding configPolicy) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual policyTokenWasSet : ()Z
/*     */     //   4: ifeq -> 113
/*     */     //   7: aload_1
/*     */     //   8: pop
/*     */     //   9: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER : Ljava/lang/String;
/*     */     //   12: aload_1
/*     */     //   13: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   16: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   19: ifne -> 37
/*     */     //   22: aload_1
/*     */     //   23: pop
/*     */     //   24: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER_VER2 : Ljava/lang/String;
/*     */     //   27: aload_1
/*     */     //   28: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   31: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   34: ifeq -> 113
/*     */     //   37: aload_0
/*     */     //   38: getfield context : Lcom/sun/xml/wss/impl/FilterProcessingContext;
/*     */     //   41: invokevirtual getWSSAssertion : ()Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   44: astore_2
/*     */     //   45: ldc 'Direct'
/*     */     //   47: aload_1
/*     */     //   48: invokevirtual getReferenceType : ()Ljava/lang/String;
/*     */     //   51: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   54: ifeq -> 113
/*     */     //   57: aload_2
/*     */     //   58: ifnull -> 107
/*     */     //   61: aload_2
/*     */     //   62: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   65: ldc 'MustSupportRefKeyIdentifier'
/*     */     //   67: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   72: ifeq -> 84
/*     */     //   75: aload_1
/*     */     //   76: ldc 'Identifier'
/*     */     //   78: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   81: goto -> 113
/*     */     //   84: aload_2
/*     */     //   85: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   88: ldc 'MustSupportRefThumbprint'
/*     */     //   90: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   95: ifeq -> 113
/*     */     //   98: aload_1
/*     */     //   99: ldc 'Thumbprint'
/*     */     //   101: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   104: goto -> 113
/*     */     //   107: aload_1
/*     */     //   108: ldc 'Identifier'
/*     */     //   110: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   113: aload_1
/*     */     //   114: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #216	-> 0
/*     */     //   #217	-> 7
/*     */     //   #219	-> 37
/*     */     //   #220	-> 45
/*     */     //   #221	-> 57
/*     */     //   #222	-> 61
/*     */     //   #223	-> 75
/*     */     //   #224	-> 84
/*     */     //   #225	-> 98
/*     */     //   #228	-> 107
/*     */     //   #234	-> 113
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   45	68	2	wssAssertion	Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   0	115	0	this	Lcom/sun/xml/wss/impl/policy/verifier/EncryptionPolicyVerifier;
/*     */     //   0	115	1	configPolicy	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\EncryptionPolicyVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */