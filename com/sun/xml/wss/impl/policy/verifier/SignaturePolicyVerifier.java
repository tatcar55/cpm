/*     */ package com.sun.xml.wss.impl.policy.verifier;
/*     */ 
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.PolicyViolationException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
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
/*     */ 
/*     */ public class SignaturePolicyVerifier
/*     */   implements PolicyVerifier
/*     */ {
/*     */   FilterProcessingContext context;
/*     */   
/*     */   public SignaturePolicyVerifier(FilterProcessingContext context) {
/*  71 */     this.context = context;
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
/*     */   public void verifyPolicy(SecurityPolicy configPolicy, SecurityPolicy recvdPolicy) throws PolicyViolationException {
/*  90 */     if (PolicyTypeUtil.signaturePolicy(configPolicy) && PolicyTypeUtil.signaturePolicy(recvdPolicy)) {
/*  91 */       SignaturePolicy rsignPolicy = (SignaturePolicy)recvdPolicy;
/*  92 */       SignaturePolicy csignPolicy = (SignaturePolicy)configPolicy;
/*     */       
/*  94 */       SignaturePolicy.FeatureBinding rfBinding = (SignaturePolicy.FeatureBinding)rsignPolicy.getFeatureBinding();
/*  95 */       SignaturePolicy.FeatureBinding cfBinding = (SignaturePolicy.FeatureBinding)csignPolicy.getFeatureBinding();
/*     */       
/*  97 */       String cCanonAlgo = cfBinding.getCanonicalizationAlgorithm();
/*  98 */       String rCanonAlgo = rfBinding.getCanonicalizationAlgorithm();
/*  99 */       if (cCanonAlgo == null || rCanonAlgo == null) {
/* 100 */         throw new PolicyViolationException("Either Policy configured or Policy inferred is null while verifying inferredPolicy with configuredPolicy");
/*     */       }
/*     */       
/* 103 */       if (cCanonAlgo.length() > 0 && rCanonAlgo.length() > 0 && 
/* 104 */         !rCanonAlgo.equals(cCanonAlgo)) {
/* 105 */         throw new PolicyViolationException("Receiver side requirement verification failed, canonicalization algorithm received in the message is " + rfBinding.getCanonicalizationAlgorithm() + " policy requires " + cfBinding.getCanonicalizationAlgorithm());
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
/*     */   private void checkSAMLAssertionBinding(AuthenticationTokenPolicy.SAMLAssertionBinding configPolicy, AuthenticationTokenPolicy.SAMLAssertionBinding recvdPolicy) throws PolicyViolationException {
/* 145 */     boolean matched = true;
/*     */     
/* 147 */     String _cAI = configPolicy.getAuthorityIdentifier();
/* 148 */     String _rAI = recvdPolicy.getAuthorityIdentifier();
/* 149 */     if (_cAI != null && _cAI.length() > 0 && _rAI != null) {
/* 150 */       matched = _cAI.equals(_rAI);
/* 151 */       _throwError((SecurityPolicy)configPolicy, (SecurityPolicy)recvdPolicy, matched);
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
/* 164 */     boolean matched = true;
/*     */     
/* 166 */     configPolicy = setReferenceType(configPolicy);
/* 167 */     String ckeyAlg = configPolicy.getKeyAlgorithm();
/* 168 */     String rkeyAlg = recvdPolicy.getKeyAlgorithm();
/* 169 */     if (ckeyAlg != null && ckeyAlg.length() > 0 && rkeyAlg.length() > 0) {
/* 170 */       matched = ckeyAlg.equals(rkeyAlg);
/*     */     }
/* 172 */     _throwError((SecurityPolicy)configPolicy, (SecurityPolicy)recvdPolicy, matched);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     String cVT = configPolicy.getValueType();
/* 183 */     String rVT = recvdPolicy.getValueType();
/*     */     
/* 185 */     if (cVT != null && cVT.length() > 0 && rVT.length() > 0) {
/* 186 */       matched = cVT.equals(rVT);
/*     */     }
/* 188 */     _throwError((SecurityPolicy)configPolicy, (SecurityPolicy)recvdPolicy, matched);
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
/* 212 */     if (!matched)
/* 213 */       throw new PolicyViolationException("KeyType used to sign the message doesnot match with  the receiver side requirements. Configured KeyType is " + configPolicy + " KeyType inferred from the message is  " + recvdPolicy); 
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
/*     */     //   #227	-> 0
/*     */     //   #228	-> 7
/*     */     //   #230	-> 37
/*     */     //   #231	-> 45
/*     */     //   #232	-> 57
/*     */     //   #233	-> 61
/*     */     //   #234	-> 75
/*     */     //   #235	-> 84
/*     */     //   #236	-> 98
/*     */     //   #239	-> 107
/*     */     //   #245	-> 113
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   45	68	2	wssAssertion	Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   0	115	0	this	Lcom/sun/xml/wss/impl/policy/verifier/SignaturePolicyVerifier;
/*     */     //   0	115	1	configPolicy	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\SignaturePolicyVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */