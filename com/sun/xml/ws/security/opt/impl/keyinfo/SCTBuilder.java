/*    */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*    */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*    */ import com.sun.xml.wss.XWSSecurityException;
/*    */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
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
/*    */ public class SCTBuilder
/*    */   extends TokenBuilder
/*    */ {
/* 61 */   private SecureConversationTokenKeyBinding sctBinding = null;
/*    */   
/*    */   public SCTBuilder(JAXBFilterProcessingContext context, SecureConversationTokenKeyBinding kb) {
/* 64 */     super(context);
/* 65 */     this.sctBinding = kb;
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
/*    */     //   12: invokestatic getDataEncryptionAlgo : (Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;)Ljava/lang/String;
/*    */     //   15: astore_2
/*    */     //   16: aload_0
/*    */     //   17: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   20: invokevirtual getUUID : ()Ljava/lang/String;
/*    */     //   23: astore_3
/*    */     //   24: aload_0
/*    */     //   25: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   28: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*    */     //   31: aload_3
/*    */     //   32: invokevirtual getChildElement : (Ljava/lang/String;)Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*    */     //   35: astore #4
/*    */     //   37: aload_0
/*    */     //   38: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   41: invokevirtual getSecureConversationContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   44: astore #5
/*    */     //   46: aload_0
/*    */     //   47: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   50: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*    */     //   53: astore #6
/*    */     //   55: aload_0
/*    */     //   56: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   59: pop
/*    */     //   60: getstatic com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*    */     //   63: aload #6
/*    */     //   65: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   68: ifne -> 119
/*    */     //   71: aload_0
/*    */     //   72: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   75: pop
/*    */     //   76: getstatic com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*    */     //   79: aload #6
/*    */     //   81: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   84: ifne -> 119
/*    */     //   87: aload_0
/*    */     //   88: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   91: pop
/*    */     //   92: getstatic com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*    */     //   95: aload #6
/*    */     //   97: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   100: ifne -> 119
/*    */     //   103: aload_0
/*    */     //   104: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   107: pop
/*    */     //   108: getstatic com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*    */     //   111: aload #6
/*    */     //   113: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   116: ifeq -> 123
/*    */     //   119: iconst_1
/*    */     //   120: goto -> 124
/*    */     //   123: iconst_0
/*    */     //   124: istore #7
/*    */     //   126: aconst_null
/*    */     //   127: astore #8
/*    */     //   129: aload #4
/*    */     //   131: ifnonnull -> 296
/*    */     //   134: aload #5
/*    */     //   136: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*    */     //   141: checkcast com/sun/xml/ws/security/SecurityContextToken
/*    */     //   144: astore #8
/*    */     //   146: aload #8
/*    */     //   148: ifnonnull -> 173
/*    */     //   151: getstatic com/sun/xml/ws/security/opt/impl/keyinfo/SCTBuilder.logger : Ljava/util/logging/Logger;
/*    */     //   154: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*    */     //   157: invokestatic WSS_1809_SCT_NOT_FOUND : ()Ljava/lang/String;
/*    */     //   160: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*    */     //   163: new com/sun/xml/wss/XWSSecurityException
/*    */     //   166: dup
/*    */     //   167: ldc 'SecureConversation Token not Found'
/*    */     //   169: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   172: athrow
/*    */     //   173: aload_0
/*    */     //   174: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   177: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*    */     //   180: aload #8
/*    */     //   182: invokeinterface getWsuId : ()Ljava/lang/String;
/*    */     //   187: invokevirtual getChildElement : (Ljava/lang/String;)Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;
/*    */     //   190: astore #4
/*    */     //   192: aload #4
/*    */     //   194: ifnonnull -> 296
/*    */     //   197: aload #8
/*    */     //   199: aload_0
/*    */     //   200: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   203: invokevirtual getSOAPVersion : ()Lcom/sun/xml/ws/api/SOAPVersion;
/*    */     //   206: invokestatic getSCT : (Lcom/sun/xml/ws/security/SecurityContextToken;Lcom/sun/xml/ws/api/SOAPVersion;)Lcom/sun/xml/ws/security/SecurityContextToken;
/*    */     //   209: astore #8
/*    */     //   211: iload #7
/*    */     //   213: ifeq -> 264
/*    */     //   216: aload_0
/*    */     //   217: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   220: invokevirtual getSecurityPolicyVersion : ()Ljava/lang/String;
/*    */     //   223: ldc 'http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702'
/*    */     //   225: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   228: ifeq -> 249
/*    */     //   231: aload_0
/*    */     //   232: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   235: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*    */     //   238: aload #8
/*    */     //   240: checkcast com/sun/xml/ws/security/opt/impl/keyinfo/SecurityContextToken13
/*    */     //   243: invokevirtual add : (Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;)V
/*    */     //   246: goto -> 264
/*    */     //   249: aload_0
/*    */     //   250: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   253: invokevirtual getSecurityHeader : ()Lcom/sun/xml/ws/security/opt/impl/outgoing/SecurityHeader;
/*    */     //   256: aload #8
/*    */     //   258: checkcast com/sun/xml/ws/security/opt/impl/keyinfo/SecurityContextToken
/*    */     //   261: invokevirtual add : (Lcom/sun/xml/ws/security/opt/api/SecurityHeaderElement;)V
/*    */     //   264: aload_0
/*    */     //   265: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   268: invokevirtual getSecurityPolicyVersion : ()Ljava/lang/String;
/*    */     //   271: ldc 'http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702'
/*    */     //   273: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   276: ifeq -> 289
/*    */     //   279: aload #8
/*    */     //   281: checkcast com/sun/xml/ws/security/opt/impl/keyinfo/SecurityContextToken13
/*    */     //   284: astore #4
/*    */     //   286: goto -> 296
/*    */     //   289: aload #8
/*    */     //   291: checkcast com/sun/xml/ws/security/opt/impl/keyinfo/SecurityContextToken
/*    */     //   294: astore #4
/*    */     //   296: aload #4
/*    */     //   298: invokeinterface getId : ()Ljava/lang/String;
/*    */     //   303: astore #9
/*    */     //   305: aload #9
/*    */     //   307: ifnonnull -> 333
/*    */     //   310: aload #4
/*    */     //   312: aload_0
/*    */     //   313: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   316: invokevirtual generateID : ()Ljava/lang/String;
/*    */     //   319: invokeinterface setId : (Ljava/lang/String;)V
/*    */     //   324: aload #4
/*    */     //   326: invokeinterface getId : ()Ljava/lang/String;
/*    */     //   331: astore #9
/*    */     //   333: aconst_null
/*    */     //   334: astore #10
/*    */     //   336: aload_0
/*    */     //   337: getfield elementFactory : Lcom/sun/xml/ws/security/opt/impl/util/WSSElementFactory;
/*    */     //   340: invokevirtual createDirectReference : ()Lcom/sun/xml/ws/security/opt/impl/reference/DirectReference;
/*    */     //   343: astore #11
/*    */     //   345: iload #7
/*    */     //   347: ifeq -> 380
/*    */     //   350: aload #11
/*    */     //   352: new java/lang/StringBuilder
/*    */     //   355: dup
/*    */     //   356: invokespecial <init> : ()V
/*    */     //   359: ldc '#'
/*    */     //   361: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   364: aload #9
/*    */     //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*    */     //   369: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   372: invokeinterface setURI : (Ljava/lang/String;)V
/*    */     //   377: goto -> 397
/*    */     //   380: aload #11
/*    */     //   382: aload #8
/*    */     //   384: invokeinterface getIdentifier : ()Ljava/net/URI;
/*    */     //   389: invokevirtual toString : ()Ljava/lang/String;
/*    */     //   392: invokeinterface setURI : (Ljava/lang/String;)V
/*    */     //   397: aload_0
/*    */     //   398: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   401: pop
/*    */     //   402: getstatic com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*    */     //   405: aload_0
/*    */     //   406: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   409: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*    */     //   412: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   415: ifeq -> 439
/*    */     //   418: aload_0
/*    */     //   419: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   422: pop
/*    */     //   423: getstatic com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*    */     //   426: aload_0
/*    */     //   427: getfield sctBinding : Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*    */     //   430: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*    */     //   433: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   436: ifne -> 475
/*    */     //   439: aload_0
/*    */     //   440: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   443: invokevirtual getSecurityPolicyVersion : ()Ljava/lang/String;
/*    */     //   446: ldc 'http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702'
/*    */     //   448: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   451: ifeq -> 466
/*    */     //   454: aload #11
/*    */     //   456: ldc 'http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct'
/*    */     //   458: invokeinterface setValueType : (Ljava/lang/String;)V
/*    */     //   463: goto -> 475
/*    */     //   466: aload #11
/*    */     //   468: ldc 'http://schemas.xmlsoap.org/ws/2005/02/sc/sct'
/*    */     //   470: invokeinterface setValueType : (Ljava/lang/String;)V
/*    */     //   475: aload #8
/*    */     //   477: invokeinterface getInstance : ()Ljava/lang/String;
/*    */     //   482: ifnull -> 527
/*    */     //   485: aload_0
/*    */     //   486: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   489: invokevirtual isExpired : ()Z
/*    */     //   492: ifne -> 527
/*    */     //   495: aload #11
/*    */     //   497: checkcast com/sun/xml/ws/security/opt/impl/reference/DirectReference
/*    */     //   500: aload_0
/*    */     //   501: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   504: aload_0
/*    */     //   505: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   508: invokevirtual getSecurityPolicyVersion : ()Ljava/lang/String;
/*    */     //   511: invokevirtual getWSSCVersion : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   514: ldc 'Instance'
/*    */     //   516: aload #8
/*    */     //   518: invokeinterface getInstance : ()Ljava/lang/String;
/*    */     //   523: invokevirtual setAttribute : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*    */     //   526: pop
/*    */     //   527: aconst_null
/*    */     //   528: astore #12
/*    */     //   530: aload #8
/*    */     //   532: invokeinterface getInstance : ()Ljava/lang/String;
/*    */     //   537: ifnull -> 612
/*    */     //   540: aload_0
/*    */     //   541: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   544: invokevirtual isExpired : ()Z
/*    */     //   547: ifeq -> 562
/*    */     //   550: aload #5
/*    */     //   552: invokeinterface getProofKey : ()[B
/*    */     //   557: astore #12
/*    */     //   559: goto -> 621
/*    */     //   562: aload #5
/*    */     //   564: invokeinterface getSecurityContextTokenInfo : ()Lcom/sun/xml/ws/security/SecurityContextTokenInfo;
/*    */     //   569: ifnull -> 600
/*    */     //   572: aload #5
/*    */     //   574: invokeinterface getSecurityContextTokenInfo : ()Lcom/sun/xml/ws/security/SecurityContextTokenInfo;
/*    */     //   579: astore #13
/*    */     //   581: aload #13
/*    */     //   583: aload #8
/*    */     //   585: invokeinterface getInstance : ()Ljava/lang/String;
/*    */     //   590: invokeinterface getInstanceSecret : (Ljava/lang/String;)[B
/*    */     //   595: astore #12
/*    */     //   597: goto -> 621
/*    */     //   600: aload #5
/*    */     //   602: invokeinterface getProofKey : ()[B
/*    */     //   607: astore #12
/*    */     //   609: goto -> 621
/*    */     //   612: aload #5
/*    */     //   614: invokeinterface getProofKey : ()[B
/*    */     //   619: astore #12
/*    */     //   621: aload_2
/*    */     //   622: invokestatic getSecretKeyAlgorithm : (Ljava/lang/String;)Ljava/lang/String;
/*    */     //   625: astore #13
/*    */     //   627: new javax/crypto/spec/SecretKeySpec
/*    */     //   630: dup
/*    */     //   631: aload #12
/*    */     //   633: aload #13
/*    */     //   635: invokespecial <init> : ([BLjava/lang/String;)V
/*    */     //   638: astore #10
/*    */     //   640: aload_0
/*    */     //   641: aload #11
/*    */     //   643: aload_0
/*    */     //   644: getfield context : Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*    */     //   647: invokevirtual generateID : ()Ljava/lang/String;
/*    */     //   650: invokevirtual buildKeyInfo : (Lcom/sun/xml/ws/security/opt/api/reference/Reference;Ljava/lang/String;)Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;
/*    */     //   653: pop
/*    */     //   654: aload_1
/*    */     //   655: aload_0
/*    */     //   656: getfield keyInfo : Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;
/*    */     //   659: invokevirtual setKeyInfo : (Lcom/sun/xml/ws/security/opt/crypto/dsig/keyinfo/KeyInfo;)V
/*    */     //   662: aload_1
/*    */     //   663: aload #10
/*    */     //   665: invokevirtual setDataProtectionKey : (Ljava/security/Key;)V
/*    */     //   668: aload_1
/*    */     //   669: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #73	-> 0
/*    */     //   #74	-> 8
/*    */     //   #75	-> 16
/*    */     //   #77	-> 24
/*    */     //   #78	-> 37
/*    */     //   #79	-> 46
/*    */     //   #80	-> 55
/*    */     //   #85	-> 126
/*    */     //   #86	-> 129
/*    */     //   #87	-> 134
/*    */     //   #88	-> 146
/*    */     //   #89	-> 151
/*    */     //   #90	-> 163
/*    */     //   #92	-> 173
/*    */     //   #93	-> 192
/*    */     //   #94	-> 197
/*    */     //   #95	-> 211
/*    */     //   #96	-> 216
/*    */     //   #97	-> 231
/*    */     //   #99	-> 249
/*    */     //   #102	-> 264
/*    */     //   #103	-> 279
/*    */     //   #105	-> 289
/*    */     //   #111	-> 296
/*    */     //   #112	-> 305
/*    */     //   #113	-> 310
/*    */     //   #114	-> 324
/*    */     //   #116	-> 333
/*    */     //   #117	-> 336
/*    */     //   #118	-> 345
/*    */     //   #119	-> 350
/*    */     //   #121	-> 380
/*    */     //   #123	-> 397
/*    */     //   #125	-> 439
/*    */     //   #126	-> 454
/*    */     //   #128	-> 466
/*    */     //   #132	-> 475
/*    */     //   #133	-> 495
/*    */     //   #136	-> 527
/*    */     //   #137	-> 530
/*    */     //   #138	-> 540
/*    */     //   #139	-> 550
/*    */     //   #141	-> 562
/*    */     //   #142	-> 572
/*    */     //   #143	-> 581
/*    */     //   #144	-> 597
/*    */     //   #145	-> 600
/*    */     //   #149	-> 612
/*    */     //   #151	-> 621
/*    */     //   #153	-> 627
/*    */     //   #154	-> 640
/*    */     //   #155	-> 654
/*    */     //   #156	-> 662
/*    */     //   #157	-> 668
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   581	16	13	sctInstanceInfo	Lcom/sun/xml/ws/security/SecurityContextTokenInfo;
/*    */     //   0	670	0	this	Lcom/sun/xml/ws/security/opt/impl/keyinfo/SCTBuilder;
/*    */     //   8	662	1	sctResult	Lcom/sun/xml/ws/security/opt/api/keyinfo/BuilderResult;
/*    */     //   16	654	2	dataEncAlgo	Ljava/lang/String;
/*    */     //   24	646	3	sctPolicyId	Ljava/lang/String;
/*    */     //   37	633	4	sct	Lcom/sun/xml/ws/security/opt/api/SecurityElement;
/*    */     //   46	624	5	ictx	Lcom/sun/xml/ws/security/IssuedTokenContext;
/*    */     //   55	615	6	sctVersion	Ljava/lang/String;
/*    */     //   126	544	7	includeToken	Z
/*    */     //   129	541	8	sct1	Lcom/sun/xml/ws/security/SecurityContextToken;
/*    */     //   305	365	9	sctWsuId	Ljava/lang/String;
/*    */     //   336	334	10	dataProtectionKey	Ljava/security/Key;
/*    */     //   345	325	11	directRef	Lcom/sun/xml/ws/security/opt/api/reference/DirectReference;
/*    */     //   530	140	12	proofKey	[B
/*    */     //   627	43	13	jceAlgo	Ljava/lang/String;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SCTBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */