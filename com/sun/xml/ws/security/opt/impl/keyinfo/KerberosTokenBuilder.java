/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.OctectStreamData;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KerberosTokenBuilder
/*     */   extends TokenBuilder
/*     */ {
/*  60 */   AuthenticationTokenPolicy.KerberosTokenBinding binding = null;
/*     */ 
/*     */   
/*     */   public KerberosTokenBuilder(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.KerberosTokenBinding binding) {
/*  64 */     super(context);
/*  65 */     this.binding = binding;
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
/*     */   public BuilderResult process() throws XWSSecurityException {
/*  79 */     setIncludeTokenPolicy();
/*     */     
/*  81 */     String referenceType = this.binding.getReferenceType();
/*  82 */     if (logger.isLoggable(Level.FINEST)) {
/*  83 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1853_REFERENCETYPE_KERBEROS_TOKEN(referenceType));
/*     */     }
/*  85 */     BuilderResult result = new BuilderResult();
/*     */     
/*  87 */     if (referenceType.equals("Direct")) {
/*  88 */       BinarySecurityToken bst = createKerberosBST(this.binding, this.binding.getTokenValue());
/*  89 */       if (bst == null) {
/*  90 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1802_WRONG_TOKENINCLUSION_POLICY());
/*  91 */         throw new XWSSecurityException(LogStringsMessages.WSS_1802_WRONG_TOKENINCLUSION_POLICY());
/*     */       } 
/*  93 */       DirectReference directReference = buildDirectReference(bst.getId(), "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ");
/*  94 */       buildKeyInfo((Reference)directReference, this.binding.getSTRID());
/*  95 */     } else if (referenceType.equals("Identifier")) {
/*  96 */       BinarySecurityToken bst = createKerberosBST(this.binding, this.binding.getTokenValue());
/*  97 */       buildKeyInfoWithKIKerberos(this.binding, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1");
/*  98 */       if (this.binding.getSTRID() != null) {
/*  99 */         OctectStreamData osd = new OctectStreamData(new String(this.binding.getTokenValue()));
/* 100 */         this.context.getElementCache().put(this.binding.getSTRID(), osd);
/*     */       } 
/*     */     } else {
/* 103 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1803_UNSUPPORTED_REFERENCE_TYPE(referenceType));
/* 104 */       throw new XWSSecurityException(LogStringsMessages.WSS_1803_UNSUPPORTED_REFERENCE_TYPE(referenceType));
/*     */     } 
/* 106 */     result.setKeyInfo(this.keyInfo);
/* 107 */     return result;
/*     */   }
/*     */   
/*     */   private void setIncludeTokenPolicy() throws XWSSecurityException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   4: invokevirtual policyTokenWasSet : ()Z
/*     */     //   7: ifne -> 11
/*     */     //   10: return
/*     */     //   11: aload_0
/*     */     //   12: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   15: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   18: astore_1
/*     */     //   19: aload_0
/*     */     //   20: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   23: pop
/*     */     //   24: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*     */     //   27: aload_1
/*     */     //   28: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   31: ifne -> 79
/*     */     //   34: aload_0
/*     */     //   35: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   38: pop
/*     */     //   39: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*     */     //   42: aload_1
/*     */     //   43: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   46: ifne -> 79
/*     */     //   49: aload_0
/*     */     //   50: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   53: pop
/*     */     //   54: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*     */     //   57: aload_1
/*     */     //   58: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   61: ifne -> 79
/*     */     //   64: aload_0
/*     */     //   65: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   68: pop
/*     */     //   69: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*     */     //   72: aload_1
/*     */     //   73: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   76: ifeq -> 102
/*     */     //   79: getstatic com/sun/xml/ws/security/opt/impl/keyinfo/KerberosTokenBuilder.logger : Ljava/util/logging/Logger;
/*     */     //   82: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*     */     //   85: invokestatic WSS_1822_KERBEROS_ALWAYS_NOTALLOWED : ()Ljava/lang/String;
/*     */     //   88: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*     */     //   91: new com/sun/xml/wss/XWSSecurityException
/*     */     //   94: dup
/*     */     //   95: invokestatic WSS_1822_KERBEROS_ALWAYS_NOTALLOWED : ()Ljava/lang/String;
/*     */     //   98: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   101: athrow
/*     */     //   102: aload_0
/*     */     //   103: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   106: pop
/*     */     //   107: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_NEVER : Ljava/lang/String;
/*     */     //   110: aload_1
/*     */     //   111: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   114: ifne -> 132
/*     */     //   117: aload_0
/*     */     //   118: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   121: pop
/*     */     //   122: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_NEVER_VER2 : Ljava/lang/String;
/*     */     //   125: aload_1
/*     */     //   126: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   129: ifeq -> 144
/*     */     //   132: aload_0
/*     */     //   133: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   136: ldc 'Identifier'
/*     */     //   138: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   141: goto -> 183
/*     */     //   144: aload_0
/*     */     //   145: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   148: pop
/*     */     //   149: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_ONCE : Ljava/lang/String;
/*     */     //   152: aload_1
/*     */     //   153: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   156: ifne -> 174
/*     */     //   159: aload_0
/*     */     //   160: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   163: pop
/*     */     //   164: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding.INCLUDE_ONCE_VER2 : Ljava/lang/String;
/*     */     //   167: aload_1
/*     */     //   168: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   171: ifeq -> 183
/*     */     //   174: aload_0
/*     */     //   175: getfield binding : Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$KerberosTokenBinding;
/*     */     //   178: ldc 'Direct'
/*     */     //   180: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   183: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #116	-> 0
/*     */     //   #117	-> 10
/*     */     //   #119	-> 11
/*     */     //   #120	-> 19
/*     */     //   #126	-> 79
/*     */     //   #127	-> 91
/*     */     //   #128	-> 102
/*     */     //   #130	-> 132
/*     */     //   #131	-> 144
/*     */     //   #133	-> 174
/*     */     //   #135	-> 183
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	184	0	this	Lcom/sun/xml/ws/security/opt/impl/keyinfo/KerberosTokenBuilder;
/*     */     //   19	165	1	itVersion	Ljava/lang/String;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\KerberosTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */