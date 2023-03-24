/*    */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*    */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IssuedTokenBuilder
/*    */   extends TokenBuilder
/*    */ {
/* 72 */   private IssuedTokenKeyBinding ikb = null;
/*    */   
/*    */   public IssuedTokenBuilder(JAXBFilterProcessingContext context, IssuedTokenKeyBinding kb) {
/* 75 */     super(context);
/* 76 */     this.ikb = kb;
/*    */   }
/*    */   
/*    */   public BuilderResult process() throws XWSSecurityException {
/*    */     // Byte code:
/*    */     //   0: new com/sun/xml/ws/security/opt/api/keyinfo/BuilderResult
/*    */     //   3: dup
/*    */     //   4: invokespecial <init> : ()V
/*    */     //   7: astore_1
/*    */     //   8: aload_0
/*    */     //   9: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   12: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   15: invokeinterface getProofKey : ()[B
/*    */     //   20: astore_2
/*    */     //   21: aconst_null
/*    */     //   22: astore_3
/*    */     //   23: aconst_null
/*    */     //   24: astore #4
/*    */     //   26: aconst_null
/*    */     //   27: astore #5
/*    */     //   29: aload_2
/*    */     //   30: ifnonnull -> 141
/*    */     //   33: aload_0
/*    */     //   34: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   37: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   40: invokeinterface getProofKeyPair : ()Ljava/security/KeyPair;
/*    */     //   45: astore #6
/*    */     //   47: aload #6
/*    */     //   49: ifnonnull -> 125
/*    */     //   52: aload_0
/*    */     //   53: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   56: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   59: invokeinterface getRequestorCertificate : ()Ljava/security/cert/X509Certificate;
/*    */     //   64: astore #7
/*    */     //   66: aload #7
/*    */     //   68: ifnonnull -> 93
/*    */     //   71: getstatic com/sun/xml/ws/security/opt/impl/keyinfo/IssuedTokenBuilder.logger : Ljava/util/logging/Logger;
/*    */     //   74: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*    */     //   77: invokestatic WSS_1823_KEY_PAIR_PROOF_KEY_NULL_ISSUEDTOKEN : ()Ljava/lang/String;
/*    */     //   80: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*    */     //   83: new com/sun/xml/wss/XWSSecurityException
/*    */     //   86: dup
/*    */     //   87: ldc 'Proof Key and RSA KeyPair for Supporting token (KeyValueToken or RsaToken) are both null for Issued Token'
/*    */     //   89: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   92: athrow
/*    */     //   93: aload_0
/*    */     //   94: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   97: invokevirtual getSecurityEnvironment : ()Lcom/sun/xml/wss/SecurityEnvironment;
/*    */     //   100: aload_0
/*    */     //   101: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   104: invokevirtual getExtraneousProperties : ()Ljava/util/Map;
/*    */     //   107: aload #7
/*    */     //   109: invokeinterface getPrivateKey : (Ljava/util/Map;Ljava/security/cert/X509Certificate;)Ljava/security/PrivateKey;
/*    */     //   114: astore_3
/*    */     //   115: aload #7
/*    */     //   117: invokevirtual getPublicKey : ()Ljava/security/PublicKey;
/*    */     //   120: astore #5
/*    */     //   122: goto -> 138
/*    */     //   125: aload #6
/*    */     //   127: invokevirtual getPrivate : ()Ljava/security/PrivateKey;
/*    */     //   130: astore_3
/*    */     //   131: aload #6
/*    */     //   133: invokevirtual getPublic : ()Ljava/security/PublicKey;
/*    */     //   136: astore #5
/*    */     //   138: goto -> 184
/*    */     //   141: ldc 'AES'
/*    */     //   143: astore #6
/*    */     //   145: aload_0
/*    */     //   146: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   149: invokevirtual getAlgorithmSuite : ()Lcom/sun/xml/wss/impl/AlgorithmSuite;
/*    */     //   152: ifnull -> 170
/*    */     //   155: aload_0
/*    */     //   156: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   159: invokevirtual getAlgorithmSuite : ()Lcom/sun/xml/wss/impl/AlgorithmSuite;
/*    */     //   162: invokevirtual getEncryptionAlgorithm : ()Ljava/lang/String;
/*    */     //   165: invokestatic getSecretKeyAlgorithm : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   168: astore #6
/*    */     //   170: new javax/crypto/spec/SecretKeySpec
/*    */     //   173: dup
/*    */     //   174: aload_2
/*    */     //   175: aload #6
/*    */     //   177: invokespecial <init> : ([BLjava/lang/String;)V
/*    */     //   180: astore_3
/*    */     //   181: aload_3
/*    */     //   182: astore #5
/*    */     //   184: aconst_null
/*    */     //   185: astore #6
/*    */     //   187: aload_0
/*    */     //   188: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   191: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   194: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*    */     //   199: checkcast com/sun/xml/ws/security/trust/GenericToken
/*    */     //   202: astore #7
/*    */     //   204: aload #7
/*    */     //   206: ifnull -> 387
/*    */     //   209: aload #7
/*    */     //   211: invokevirtual getElement : ()Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*    */     //   214: astore #6
/*    */     //   216: aload #6
/*    */     //   218: ifnonnull -> 263
/*    */     //   221: aload #7
/*    */     //   223: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*    */     //   226: checkcast org/w3c/dom/Element
/*    */     //   229: astore #8
/*    */     //   231: new com/sun/xml/ws/security/opt/impl/message/GSHeaderElement
/*    */     //   234: dup
/*    */     //   235: aload #8
/*    */     //   237: invokespecial <init> : (Lorg/w3c/dom/Element;)V
/*    */     //   240: astore #6
/*    */     //   242: aload #6
/*    */     //   244: aload #7
/*    */     //   246: invokevirtual getId : ()Ljava/lang/String;
/*    */     //   249: invokeinterface setId : (Ljava/lang/String;)V
/*    */     //   254: aload_1
/*    */     //   255: aload #7
/*    */     //   257: invokevirtual getId : ()Ljava/lang/String;
/*    */     //   260: invokevirtual setDPTokenId : (Ljava/lang/String;)V
/*    */     //   263: aload #6
/*    */     //   265: invokeinterface getId : ()Ljava/lang/String;
/*    */     //   270: astore #8
/*    */     //   272: ldc ''
/*    */     //   274: aload #8
/*    */     //   276: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   279: ifeq -> 319
/*    */     //   282: ldc 'EncryptedData'
/*    */     //   284: aload #6
/*    */     //   286: invokeinterface getLocalPart : ()Ljava/lang/String;
/*    */     //   291: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   294: ifeq -> 319
/*    */     //   297: getstatic com/sun/xml/ws/security/opt/impl/keyinfo/IssuedTokenBuilder.logger : Ljava/util/logging/Logger;
/*    */     //   300: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*    */     //   303: invokestatic WSS_1808_ID_NOTSET_ENCRYPTED_ISSUEDTOKEN : ()Ljava/lang/String;
/*    */     //   306: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*    */     //   309: new com/sun/xml/wss/XWSSecurityException
/*    */     //   312: dup
/*    */     //   313: ldc 'ID attribute not set'
/*    */     //   315: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   318: athrow
/*    */     //   319: aload_0
/*    */     //   320: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   323: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*    */     //   326: aload_0
/*    */     //   327: getfield ikb : Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*    */     //   330: invokevirtual getUUID : ()Ljava/lang/String;
/*    */     //   333: aload #6
/*    */     //   335: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   338: pop
/*    */     //   339: aload_0
/*    */     //   340: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   343: ldc 'stored_saml_keys'
/*    */     //   345: invokevirtual getExtraneousProperty : (Ljava/lang/String;)Ljava/lang/Object;
/*    */     //   348: checkcast java/util/HashMap
/*    */     //   351: astore #9
/*    */     //   353: aload #9
/*    */     //   355: ifnonnull -> 367
/*    */     //   358: new java/util/HashMap
/*    */     //   361: dup
/*    */     //   362: invokespecial <init> : ()V
/*    */     //   365: astore #9
/*    */     //   367: aload #9
/*    */     //   369: aload #8
/*    */     //   371: aload_3
/*    */     //   372: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   375: pop
/*    */     //   376: aload_0
/*    */     //   377: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   380: ldc 'stored_saml_keys'
/*    */     //   382: aload #9
/*    */     //   384: invokevirtual setExtraneousProperty : (Ljava/lang/String;Ljava/lang/Object;)V
/*    */     //   387: aload_0
/*    */     //   388: getfield ikb : Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*    */     //   391: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*    */     //   394: astore #8
/*    */     //   396: aload_0
/*    */     //   397: getfield ikb : Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*    */     //   400: pop
/*    */     //   401: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*    */     //   404: aload #8
/*    */     //   406: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   409: ifne -> 460
/*    */     //   412: aload_0
/*    */     //   413: getfield ikb : Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*    */     //   416: pop
/*    */     //   417: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*    */     //   420: aload #8
/*    */     //   422: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   425: ifne -> 460
/*    */     //   428: aload_0
/*    */     //   429: getfield ikb : Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*    */     //   432: pop
/*    */     //   433: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*    */     //   436: aload #8
/*    */     //   438: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   441: ifne -> 460
/*    */     //   444: aload_0
/*    */     //   445: getfield ikb : Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*    */     //   448: pop
/*    */     //   449: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*    */     //   452: aload #8
/*    */     //   454: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   457: ifeq -> 464
/*    */     //   460: iconst_1
/*    */     //   461: goto -> 465
/*    */     //   464: iconst_0
/*    */     //   465: istore #9
/*    */     //   467: iload #9
/*    */     //   469: ifeq -> 492
/*    */     //   472: aload_0
/*    */     //   473: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   476: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   479: invokeinterface getAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*    */     //   484: checkcast com/sun/xml/ws/security/secext10/SecurityTokenReferenceType
/*    */     //   487: astore #4
/*    */     //   489: goto -> 509
/*    */     //   492: aload_0
/*    */     //   493: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   496: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   499: invokeinterface getUnAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*    */     //   504: checkcast com/sun/xml/ws/security/secext10/SecurityTokenReferenceType
/*    */     //   507: astore #4
/*    */     //   509: aload #7
/*    */     //   511: ifnull -> 551
/*    */     //   514: iload #9
/*    */     //   516: ifeq -> 551
/*    */     //   519: aload_0
/*    */     //   520: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   523: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*    */     //   526: aload #6
/*    */     //   528: invokeinterface getId : ()Ljava/lang/String;
/*    */     //   533: invokevirtual getChildElement : (Ljava/lang/String;)Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*    */     //   536: ifnonnull -> 551
/*    */     //   539: aload_0
/*    */     //   540: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   543: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*    */     //   546: aload #6
/*    */     //   548: invokevirtual add : (Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;)V
/*    */     //   551: aload_0
/*    */     //   552: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   555: invokevirtual getNamespaceContext : ()Lorg/jvnet/staxex/NamespaceContextEx;
/*    */     //   558: checkcast com/sun/xml/ws/security/opt/impl/util/NamespaceContextEx
/*    */     //   561: invokevirtual addWSS11NS : ()V
/*    */     //   564: aload_0
/*    */     //   565: new com/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo
/*    */     //   568: dup
/*    */     //   569: invokespecial <init> : ()V
/*    */     //   572: putfield keyInfo : Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;
/*    */     //   575: new com/sun/xml/ws/security/secext10/ObjectFactory
/*    */     //   578: dup
/*    */     //   579: invokespecial <init> : ()V
/*    */     //   582: aload #4
/*    */     //   584: invokevirtual createSecurityTokenReference : (Lcom/sun/xml/ws/security/secext10/SecurityTokenReferenceType;)Ljavax/xml/bind/JAXBElement;
/*    */     //   587: astore #10
/*    */     //   589: aload #10
/*    */     //   591: invokestatic singletonList : (Ljava/lang/Object;)Ljava/util/List;
/*    */     //   594: astore #11
/*    */     //   596: aload_0
/*    */     //   597: getfield keyInfo : Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;
/*    */     //   600: aload #11
/*    */     //   602: invokevirtual setContent : (Ljava/util/List;)V
/*    */     //   605: aload #4
/*    */     //   607: ifnull -> 621
/*    */     //   610: aload #4
/*    */     //   612: aload_0
/*    */     //   613: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   616: aload #5
/*    */     //   618: invokestatic updateSamlVsKeyCache : (Lcom/sun/xml/ws/security/secext10/SecurityTokenReferenceType;Lcom/sun/xml/wss/impl/FilterProcessingContext;Ljava/security/Key;)V
/*    */     //   621: aload_1
/*    */     //   622: aload_3
/*    */     //   623: invokevirtual setDataProtectionKey : (Ljava/security/Key;)V
/*    */     //   626: aload_1
/*    */     //   627: aload_0
/*    */     //   628: getfield keyInfo : Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;
/*    */     //   631: invokevirtual setKeyInfo : (Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;)V
/*    */     //   634: aload_1
/*    */     //   635: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #85	-> 0
/*    */     //   #86	-> 8
/*    */     //   #87	-> 21
/*    */     //   #88	-> 23
/*    */     //   #89	-> 26
/*    */     //   #91	-> 29
/*    */     //   #92	-> 33
/*    */     //   #93	-> 47
/*    */     //   #94	-> 52
/*    */     //   #96	-> 66
/*    */     //   #97	-> 71
/*    */     //   #98	-> 83
/*    */     //   #101	-> 93
/*    */     //   #102	-> 115
/*    */     //   #104	-> 122
/*    */     //   #105	-> 125
/*    */     //   #106	-> 131
/*    */     //   #108	-> 138
/*    */     //   #109	-> 141
/*    */     //   #110	-> 145
/*    */     //   #111	-> 155
/*    */     //   #114	-> 170
/*    */     //   #115	-> 181
/*    */     //   #119	-> 184
/*    */     //   #120	-> 187
/*    */     //   #121	-> 204
/*    */     //   #122	-> 209
/*    */     //   #123	-> 216
/*    */     //   #124	-> 221
/*    */     //   #125	-> 231
/*    */     //   #126	-> 242
/*    */     //   #127	-> 254
/*    */     //   #129	-> 263
/*    */     //   #130	-> 272
/*    */     //   #131	-> 297
/*    */     //   #132	-> 309
/*    */     //   #134	-> 319
/*    */     //   #136	-> 339
/*    */     //   #137	-> 353
/*    */     //   #138	-> 358
/*    */     //   #140	-> 367
/*    */     //   #141	-> 376
/*    */     //   #143	-> 387
/*    */     //   #144	-> 396
/*    */     //   #150	-> 467
/*    */     //   #151	-> 472
/*    */     //   #154	-> 492
/*    */     //   #158	-> 509
/*    */     //   #159	-> 519
/*    */     //   #160	-> 539
/*    */     //   #164	-> 551
/*    */     //   #165	-> 564
/*    */     //   #166	-> 575
/*    */     //   #167	-> 589
/*    */     //   #168	-> 596
/*    */     //   #169	-> 605
/*    */     //   #170	-> 610
/*    */     //   #171	-> 621
/*    */     //   #172	-> 626
/*    */     //   #173	-> 634
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   66	56	7	cert	Ljava/security/cert/X509Certificate;
/*    */     //   47	91	6	keyPair	Ljava/security/KeyPair;
/*    */     //   145	39	6	secretKeyAlg	Ljava/lang/String;
/*    */     //   231	32	8	element	Lorg/w3c/dom/Element;
/*    */     //   272	115	8	tokId	Ljava/lang/String;
/*    */     //   353	34	9	sentSamlKeys	Ljava/util/HashMap;
/*    */     //   0	636	0	this	Lcom/sun/xml/ws/security/opt/impl/keyinfo/IssuedTokenBuilder;
/*    */     //   8	628	1	itkbResult	Lcom/sun/xml/ws/security/opt/api/keyinfo/BuilderResult;
/*    */     //   21	615	2	proofKey	[B
/*    */     //   23	613	3	dataProtectionKey	Ljava/security/Key;
/*    */     //   26	610	4	str	Lcom/sun/xml/ws/security/secext10/SecurityTokenReferenceType;
/*    */     //   29	607	5	cacheKey	Ljava/security/Key;
/*    */     //   187	449	6	issuedTokenElement	Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*    */     //   204	432	7	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*    */     //   396	240	8	itType	Ljava/lang/String;
/*    */     //   467	169	9	includeToken	Z
/*    */     //   589	47	10	je	Ljavax/xml/bind/JAXBElement;
/*    */     //   596	40	11	strList	Ljava/util/List;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\IssuedTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */