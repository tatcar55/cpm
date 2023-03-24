/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenManager;
/*     */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityContextToken13;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.client.DefaultSCTokenConfiguration;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.secext10.KeyIdentifierType;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityContextTokenImpl;
/*     */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.X509SecurityToken;
/*     */ import com.sun.xml.wss.core.reference.KeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.SamlKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.logging.impl.crypto.LogStringsMessages;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.Mac;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityUtil
/*     */ {
/* 130 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.crypto", "com.sun.xml.wss.logging.impl.crypto.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey generateSymmetricKey(String algorithm) throws XWSSecurityException {
/*     */     try {
/* 141 */       String jceAlgo = JCEMapper.getJCEKeyAlgorithmFromURI(algorithm);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 147 */       KeyGenerator keyGen = KeyGenerator.getInstance(jceAlgo);
/* 148 */       int length = 0;
/* 149 */       if (jceAlgo.startsWith("DES")) {
/* 150 */         length = 168;
/*     */       } else {
/* 152 */         length = JCEMapper.getKeyLengthFromURI(algorithm);
/*     */       } 
/* 154 */       keyGen.init(length);
/*     */ 
/*     */ 
/*     */       
/* 158 */       return keyGen.generateKey();
/* 159 */     } catch (Exception e) {
/* 160 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1208_FAILEDTO_GENERATE_RANDOM_SYMMETRICKEY(e.getMessage()), new Object[] { e.getMessage() });
/*     */ 
/*     */       
/* 163 */       throw new XWSSecurityException("Unable to Generate Symmetric Key", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLengthFromAlgorithm(String algorithm) throws XWSSecurityException {
/* 174 */     if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#aes192-cbc"))
/* 175 */       return 24; 
/* 176 */     if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#aes256-cbc"))
/* 177 */       return 32; 
/* 178 */     if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#aes128-cbc"))
/* 179 */       return 16; 
/* 180 */     if (algorithm.equals("http://www.w3.org/2001/04/xmlenc#tripledes-cbc")) {
/* 181 */       return 24;
/*     */     }
/* 183 */     throw new UnsupportedOperationException("TODO: not yet implemented keyLength for" + algorithm);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String generateUUID() {
/* 188 */     Random rnd = new Random();
/* 189 */     int intRandom = rnd.nextInt();
/* 190 */     String id = "XWSSGID-" + String.valueOf(System.currentTimeMillis()) + String.valueOf(intRandom);
/* 191 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] P_SHA1(byte[] secret, byte[] seed) throws Exception {
/* 198 */     byte[] aBytes = seed;
/*     */     
/* 200 */     Mac hMac = Mac.getInstance("HMACSHA1");
/* 201 */     SecretKeySpec sKey = new SecretKeySpec(secret, "HMACSHA1");
/* 202 */     hMac.init(sKey);
/* 203 */     hMac.update(aBytes);
/* 204 */     aBytes = hMac.doFinal();
/* 205 */     hMac.reset();
/* 206 */     hMac.init(sKey);
/* 207 */     hMac.update(aBytes);
/* 208 */     hMac.update(seed);
/* 209 */     byte[] result = hMac.doFinal();
/*     */     
/* 211 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] P_SHA1(byte[] secret, byte[] seed, int requiredSize) throws NoSuchAlgorithmException, InvalidKeyException {
/* 216 */     Mac hMac = Mac.getInstance("HMACSHA1");
/* 217 */     SecretKeySpec sKey = new SecretKeySpec(secret, "HMACSHA1");
/*     */     
/* 219 */     byte[] result = new byte[requiredSize];
/* 220 */     int copied = 0;
/*     */     
/* 222 */     byte[] aBytes = seed;
/* 223 */     hMac.init(sKey);
/* 224 */     hMac.update(aBytes);
/* 225 */     aBytes = hMac.doFinal();
/*     */     
/* 227 */     int rounds = requiredSize / aBytes.length;
/* 228 */     if (requiredSize % aBytes.length != 0) {
/* 229 */       rounds++;
/*     */     }
/* 231 */     for (int i = 0; i < rounds; i++) {
/*     */       int takeBytes;
/* 233 */       hMac.reset();
/* 234 */       hMac.init(sKey);
/* 235 */       hMac.update(aBytes);
/* 236 */       hMac.update(seed);
/* 237 */       byte[] generated = hMac.doFinal();
/*     */       
/* 239 */       if (i != rounds - 1) {
/* 240 */         takeBytes = generated.length;
/*     */       } else {
/* 242 */         takeBytes = requiredSize - generated.length * i;
/* 243 */       }  System.arraycopy(generated, 0, result, copied, takeBytes);
/* 244 */       copied += takeBytes;
/* 245 */       hMac.init(sKey);
/* 246 */       hMac.update(aBytes);
/* 247 */       aBytes = hMac.doFinal();
/*     */     } 
/* 249 */     return result;
/*     */   }
/*     */   
/*     */   public static String getSecretKeyAlgorithm(String encryptionAlgo) {
/* 253 */     String encAlgo = JCEMapper.translateURItoJCEID(encryptionAlgo);
/* 254 */     if (encAlgo.startsWith("AES"))
/* 255 */       return "AES"; 
/* 256 */     if (encAlgo.startsWith("DESede"))
/* 257 */       return "DESede"; 
/* 258 */     if (encAlgo.startsWith("DES")) {
/* 259 */       return "DES";
/*     */     }
/* 261 */     return encAlgo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkIncludeTokenPolicyOpt(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.UsernameTokenBinding untBinding, String unTokenid) throws XWSSecurityException {
/*     */     try {
/* 269 */       if (!untBinding.policyTokenWasSet()) {
/*     */         return;
/*     */       }
/* 272 */       String itVersion = untBinding.getIncludeToken();
/* 273 */       if (!AuthenticationTokenPolicy.UsernameTokenBinding.INCLUDE_ALWAYS_TO_RECIPIENT.equals(itVersion)) if (!AuthenticationTokenPolicy.UsernameTokenBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2.equals(itVersion))
/*     */         {
/*     */ 
/*     */           
/* 277 */           throw new UnsupportedOperationException(untBinding.getIncludeToken() + " not supported yet as IncludeToken policy"); }  
/*     */       untBinding.setReferenceType("Direct");
/* 279 */     } catch (Exception e) {
/* 280 */       throw new XWSSecurityException(e);
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
/*     */   public static void checkIncludeTokenPolicy(FilterProcessingContext context, AuthenticationTokenPolicy.X509CertificateBinding certInfo, String x509id) throws XWSSecurityException {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual getInsertedX509Cache : ()Ljava/util/HashMap;
/*     */     //   4: astore_3
/*     */     //   5: aload_3
/*     */     //   6: aload_2
/*     */     //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   10: checkcast com/sun/xml/wss/core/X509SecurityToken
/*     */     //   13: astore #4
/*     */     //   15: aload #4
/*     */     //   17: ifnonnull -> 247
/*     */     //   20: aload_1
/*     */     //   21: invokevirtual policyTokenWasSet : ()Z
/*     */     //   24: ifne -> 28
/*     */     //   27: return
/*     */     //   28: aload_1
/*     */     //   29: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   32: astore #5
/*     */     //   34: aload_1
/*     */     //   35: pop
/*     */     //   36: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*     */     //   39: aload #5
/*     */     //   41: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   44: ifne -> 86
/*     */     //   47: aload_1
/*     */     //   48: pop
/*     */     //   49: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*     */     //   52: aload #5
/*     */     //   54: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   57: ifne -> 86
/*     */     //   60: aload_1
/*     */     //   61: pop
/*     */     //   62: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*     */     //   65: aload #5
/*     */     //   67: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   70: ifne -> 86
/*     */     //   73: aload_1
/*     */     //   74: pop
/*     */     //   75: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*     */     //   78: aload #5
/*     */     //   80: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   83: ifeq -> 95
/*     */     //   86: aload_0
/*     */     //   87: aload_1
/*     */     //   88: aload_2
/*     */     //   89: invokestatic insertCertificate : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Ljava/lang/String;)V
/*     */     //   92: goto -> 247
/*     */     //   95: aload_1
/*     */     //   96: pop
/*     */     //   97: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER : Ljava/lang/String;
/*     */     //   100: aload #5
/*     */     //   102: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   105: ifne -> 121
/*     */     //   108: aload_1
/*     */     //   109: pop
/*     */     //   110: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER_VER2 : Ljava/lang/String;
/*     */     //   113: aload #5
/*     */     //   115: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   118: ifeq -> 201
/*     */     //   121: aload_0
/*     */     //   122: invokevirtual getWSSAssertion : ()Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   125: astore #6
/*     */     //   127: ldc 'Direct'
/*     */     //   129: aload_1
/*     */     //   130: invokevirtual getReferenceType : ()Ljava/lang/String;
/*     */     //   133: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   136: ifeq -> 198
/*     */     //   139: aload #6
/*     */     //   141: ifnull -> 192
/*     */     //   144: aload #6
/*     */     //   146: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   149: ldc 'MustSupportRefKeyIdentifier'
/*     */     //   151: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   156: ifeq -> 168
/*     */     //   159: aload_1
/*     */     //   160: ldc 'Identifier'
/*     */     //   162: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   165: goto -> 198
/*     */     //   168: aload #6
/*     */     //   170: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   173: ldc 'MustSupportRefThumbprint'
/*     */     //   175: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   180: ifeq -> 198
/*     */     //   183: aload_1
/*     */     //   184: ldc 'Thumbprint'
/*     */     //   186: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   189: goto -> 198
/*     */     //   192: aload_1
/*     */     //   193: ldc 'Identifier'
/*     */     //   195: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   198: goto -> 247
/*     */     //   201: aload_1
/*     */     //   202: pop
/*     */     //   203: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ONCE : Ljava/lang/String;
/*     */     //   206: aload_1
/*     */     //   207: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   210: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   213: ifeq -> 247
/*     */     //   216: new java/lang/UnsupportedOperationException
/*     */     //   219: dup
/*     */     //   220: new java/lang/StringBuilder
/*     */     //   223: dup
/*     */     //   224: invokespecial <init> : ()V
/*     */     //   227: aload_1
/*     */     //   228: pop
/*     */     //   229: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ONCE : Ljava/lang/String;
/*     */     //   232: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   235: ldc ' not supported yet as IncludeToken policy'
/*     */     //   237: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   240: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   243: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   246: athrow
/*     */     //   247: goto -> 262
/*     */     //   250: astore #5
/*     */     //   252: new com/sun/xml/wss/XWSSecurityException
/*     */     //   255: dup
/*     */     //   256: aload #5
/*     */     //   258: invokespecial <init> : (Ljava/lang/Throwable;)V
/*     */     //   261: athrow
/*     */     //   262: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #288	-> 0
/*     */     //   #289	-> 5
/*     */     //   #293	-> 15
/*     */     //   #299	-> 20
/*     */     //   #300	-> 27
/*     */     //   #302	-> 28
/*     */     //   #303	-> 34
/*     */     //   #307	-> 86
/*     */     //   #308	-> 95
/*     */     //   #310	-> 121
/*     */     //   #311	-> 127
/*     */     //   #312	-> 139
/*     */     //   #313	-> 144
/*     */     //   #314	-> 159
/*     */     //   #315	-> 168
/*     */     //   #316	-> 183
/*     */     //   #319	-> 192
/*     */     //   #322	-> 198
/*     */     //   #323	-> 216
/*     */     //   #328	-> 247
/*     */     //   #326	-> 250
/*     */     //   #327	-> 252
/*     */     //   #329	-> 262
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   127	71	6	wssAssertion	Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   34	213	5	itVersion	Ljava/lang/String;
/*     */     //   252	10	5	e	Ljava/lang/Exception;
/*     */     //   0	263	0	context	Lcom/sun/xml/wss/impl/FilterProcessingContext;
/*     */     //   0	263	1	certInfo	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*     */     //   0	263	2	x509id	Ljava/lang/String;
/*     */     //   5	258	3	insertedX509Cache	Ljava/util/HashMap;
/*     */     //   15	248	4	x509Token	Lcom/sun/xml/wss/core/X509SecurityToken;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   15	27	250	java/lang/Exception
/*     */     //   28	247	250	java/lang/Exception
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
/*     */   public static void checkIncludeTokenPolicyOpt(JAXBFilterProcessingContext context, AuthenticationTokenPolicy.X509CertificateBinding certInfo, String x509id) throws XWSSecurityException {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokevirtual policyTokenWasSet : ()Z
/*     */     //   4: ifne -> 8
/*     */     //   7: return
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   12: astore_3
/*     */     //   13: aload_1
/*     */     //   14: pop
/*     */     //   15: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*     */     //   18: aload_3
/*     */     //   19: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   22: ifne -> 61
/*     */     //   25: aload_1
/*     */     //   26: pop
/*     */     //   27: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*     */     //   30: aload_3
/*     */     //   31: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   34: ifne -> 61
/*     */     //   37: aload_1
/*     */     //   38: pop
/*     */     //   39: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*     */     //   42: aload_3
/*     */     //   43: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   46: ifne -> 61
/*     */     //   49: aload_1
/*     */     //   50: pop
/*     */     //   51: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*     */     //   54: aload_3
/*     */     //   55: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   58: ifeq -> 70
/*     */     //   61: aload_1
/*     */     //   62: ldc 'Direct'
/*     */     //   64: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   67: goto -> 244
/*     */     //   70: aload_1
/*     */     //   71: pop
/*     */     //   72: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER : Ljava/lang/String;
/*     */     //   75: aload_3
/*     */     //   76: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   79: ifne -> 94
/*     */     //   82: aload_1
/*     */     //   83: pop
/*     */     //   84: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_NEVER_VER2 : Ljava/lang/String;
/*     */     //   87: aload_3
/*     */     //   88: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   91: ifeq -> 198
/*     */     //   94: aload_0
/*     */     //   95: invokevirtual getWSSAssertion : ()Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   98: astore #4
/*     */     //   100: ldc 'Direct'
/*     */     //   102: aload_1
/*     */     //   103: invokevirtual getReferenceType : ()Ljava/lang/String;
/*     */     //   106: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   109: ifeq -> 195
/*     */     //   112: aload #4
/*     */     //   114: ifnull -> 189
/*     */     //   117: aload #4
/*     */     //   119: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   122: ldc 'MustSupportRefIssuerSerial'
/*     */     //   124: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   129: ifeq -> 141
/*     */     //   132: aload_1
/*     */     //   133: ldc 'IssuerSerialNumber'
/*     */     //   135: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   138: goto -> 195
/*     */     //   141: aload #4
/*     */     //   143: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   146: ldc 'MustSupportRefThumbprint'
/*     */     //   148: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   153: ifeq -> 165
/*     */     //   156: aload_1
/*     */     //   157: ldc 'Thumbprint'
/*     */     //   159: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   162: goto -> 195
/*     */     //   165: aload #4
/*     */     //   167: invokevirtual getRequiredProperties : ()Ljava/util/Set;
/*     */     //   170: ldc 'MustSupportRefKeyIdentifier'
/*     */     //   172: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   177: ifeq -> 195
/*     */     //   180: aload_1
/*     */     //   181: ldc 'Identifier'
/*     */     //   183: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   186: goto -> 195
/*     */     //   189: aload_1
/*     */     //   190: ldc 'IssuerSerialNumber'
/*     */     //   192: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*     */     //   195: goto -> 244
/*     */     //   198: aload_1
/*     */     //   199: pop
/*     */     //   200: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ONCE : Ljava/lang/String;
/*     */     //   203: aload_1
/*     */     //   204: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*     */     //   207: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   210: ifeq -> 244
/*     */     //   213: new java/lang/UnsupportedOperationException
/*     */     //   216: dup
/*     */     //   217: new java/lang/StringBuilder
/*     */     //   220: dup
/*     */     //   221: invokespecial <init> : ()V
/*     */     //   224: aload_1
/*     */     //   225: pop
/*     */     //   226: getstatic com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding.INCLUDE_ONCE : Ljava/lang/String;
/*     */     //   229: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   232: ldc ' not supported yet as IncludeToken policy'
/*     */     //   234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   237: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   240: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   243: athrow
/*     */     //   244: goto -> 257
/*     */     //   247: astore_3
/*     */     //   248: new com/sun/xml/wss/XWSSecurityException
/*     */     //   251: dup
/*     */     //   252: aload_3
/*     */     //   253: invokespecial <init> : (Ljava/lang/Throwable;)V
/*     */     //   256: athrow
/*     */     //   257: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #344	-> 0
/*     */     //   #345	-> 7
/*     */     //   #347	-> 8
/*     */     //   #348	-> 13
/*     */     //   #352	-> 61
/*     */     //   #353	-> 70
/*     */     //   #355	-> 94
/*     */     //   #356	-> 100
/*     */     //   #357	-> 112
/*     */     //   #358	-> 117
/*     */     //   #359	-> 132
/*     */     //   #360	-> 141
/*     */     //   #361	-> 156
/*     */     //   #362	-> 165
/*     */     //   #363	-> 180
/*     */     //   #367	-> 189
/*     */     //   #370	-> 195
/*     */     //   #371	-> 213
/*     */     //   #376	-> 244
/*     */     //   #374	-> 247
/*     */     //   #375	-> 248
/*     */     //   #377	-> 257
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   100	95	4	wssAssertion	Lcom/sun/xml/wss/impl/WSSAssertion;
/*     */     //   13	231	3	itVersion	Ljava/lang/String;
/*     */     //   248	9	3	e	Ljava/lang/Exception;
/*     */     //   0	258	0	context	Lcom/sun/xml/ws/security/opt/impl/JAXBFilterProcessingContext;
/*     */     //   0	258	1	certInfo	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*     */     //   0	258	2	x509id	Ljava/lang/String;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	7	247	java/lang/Exception
/*     */     //   8	244	247	java/lang/Exception
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
/*     */   public static String getWsuIdOrId(Element elem) throws XWSSecurityException {
/* 380 */     NamedNodeMap nmap = elem.getAttributes();
/* 381 */     Node attr = nmap.getNamedItem("Id");
/* 382 */     if (attr == null) {
/* 383 */       attr = nmap.getNamedItem("AssertionID");
/* 384 */       if (attr == null)
/* 385 */         attr = nmap.getNamedItem("ID"); 
/* 386 */       if (attr == null) {
/* 387 */         throw new XWSSecurityException("Issued Token Element does not have a Id or AssertionId attribute");
/*     */       }
/*     */     } 
/* 390 */     return attr.getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resolveSCT(FilterProcessingContext context, SecureConversationTokenKeyBinding sctBinding) throws XWSSecurityException {
/* 398 */     String sctPolicyId = sctBinding.getUUID();
/*     */ 
/*     */     
/* 401 */     IssuedTokenContext ictx = null;
/* 402 */     String protocol = context.getWSSCVersion(context.getSecurityPolicyVersion());
/* 403 */     if (context.isClient()) {
/* 404 */       String sctId = context.getSCPolicyIDtoSctIdMap(sctPolicyId);
/* 405 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(protocol, sctId, !context.isExpired(), !context.isInboundMessage());
/* 406 */       ictx = IssuedTokenManager.getInstance().createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, null);
/*     */       try {
/* 408 */         IssuedTokenManager.getInstance().getIssuedToken(ictx);
/* 409 */       } catch (WSTrustException e) {
/* 410 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     } 
/* 413 */     if (ictx == null) {
/*     */       
/* 415 */       String sctId = "";
/* 416 */       if (context instanceof JAXBFilterProcessingContext) {
/*     */         
/* 418 */         Object sctObject = context.getExtraneousProperty("Incoming_SCT");
/*     */         
/* 420 */         if (sctObject == null) {
/* 421 */           throw new XWSSecurityException("SecureConversation Session Context not Found");
/*     */         }
/* 423 */         if (sctObject instanceof SecurityContextToken) {
/* 424 */           SecurityContextToken securityContextToken = (SecurityContextToken)sctObject;
/* 425 */           sctId = securityContextToken.getSCId();
/* 426 */         } else if (sctObject instanceof SecurityContextToken) {
/* 427 */           SecurityContextToken securityContextToken = (SecurityContextToken)sctObject;
/* 428 */           sctId = securityContextToken.getIdentifier().toString();
/*     */         } 
/*     */       } else {
/* 431 */         SecurityContextToken securityContextToken = (SecurityContextToken)context.getExtraneousProperty("Incoming_SCT");
/* 432 */         if (securityContextToken == null) {
/* 433 */           throw new XWSSecurityException("SecureConversation Session Context not Found");
/*     */         }
/* 435 */         sctId = securityContextToken.getIdentifier().toString();
/*     */       } 
/*     */       
/* 438 */       ictx = ((SessionManager)context.getExtraneousProperty("SessionManager")).getSecurityContext(sctId, !context.isExpired());
/* 439 */       URI identifier = null;
/* 440 */       String instance = null;
/* 441 */       String wsuId = null;
/*     */       
/* 443 */       SecurityContextToken sct = (SecurityContextToken)ictx.getSecurityToken();
/* 444 */       if (sct != null) {
/* 445 */         identifier = sct.getIdentifier();
/* 446 */         instance = sct.getInstance();
/* 447 */         wsuId = sct.getWsuId();
/*     */       } else {
/* 449 */         SecurityContextTokenInfo sctInfo = ictx.getSecurityContextTokenInfo();
/* 450 */         identifier = URI.create(sctInfo.getIdentifier());
/* 451 */         instance = sctInfo.getInstance();
/* 452 */         wsuId = sctInfo.getExternalId();
/*     */       } 
/*     */       
/* 455 */       ictx.setSecurityToken((Token)WSTrustElementFactory.newInstance(protocol).createSecurityContextToken(identifier, instance, wsuId));
/*     */     } 
/*     */ 
/*     */     
/* 459 */     if (ictx == null) {
/* 460 */       throw new XWSSecurityException("SecureConversation Session Context not Found");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 465 */     context.setSecureConversationContext(ictx);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resolveIssuedToken(FilterProcessingContext context, IssuedTokenKeyBinding itkb) throws XWSSecurityException {
/* 471 */     String itPolicyId = itkb.getUUID();
/*     */     
/* 473 */     IssuedTokenContext ictx = context.getIssuedTokenContext(itPolicyId);
/* 474 */     boolean clientSide = true;
/* 475 */     if (ictx == null) {
/*     */       
/* 477 */       ictx = context.getTrustCredentialHolder();
/* 478 */       clientSide = false;
/*     */     } 
/*     */     
/* 481 */     if (ictx == null) {
/* 482 */       throw new XWSSecurityException("Trust IssuedToken not Found");
/*     */     }
/* 484 */     if (ictx.getSecurityToken() instanceof GenericToken) {
/* 485 */       itkb.setRealId(((GenericToken)ictx.getSecurityToken()).getId());
/*     */     }
/*     */     
/* 488 */     context.setTrustContext(ictx);
/* 489 */     if (ictx.getProofKey() == null)
/*     */     {
/* 491 */       if (clientSide) {
/*     */         
/* 493 */         X509Certificate cert = context.getSecurityEnvironment().getDefaultCertificate(context.getExtraneousProperties());
/*     */         
/* 495 */         ictx.setRequestorCertificate(cert);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initInferredIssuedTokenContext(FilterProcessingContext wssContext, Token str, Key returnKey) throws XWSSecurityException {
/* 504 */     IssuedTokenContextImpl ictx = (IssuedTokenContextImpl)wssContext.getTrustCredentialHolder();
/* 505 */     if (ictx == null) {
/* 506 */       ictx = new IssuedTokenContextImpl();
/*     */     }
/*     */     
/* 509 */     ictx.setProofKey(returnKey.getEncoded());
/* 510 */     ictx.setUnAttachedSecurityTokenReference(str);
/* 511 */     wssContext.setTrustCredentialHolder((IssuedTokenContext)ictx);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEncryptedKey(SOAPElement elem) {
/* 516 */     if ("EncryptedKey".equals(elem.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(elem.getNamespaceURI()))
/*     */     {
/* 518 */       return true;
/*     */     }
/* 520 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBinarySecret(SOAPElement elem) {
/* 524 */     if ("BinarySecret".equals(elem.getLocalName()) && "http://schemas.xmlsoap.org/ws/2005/02/trust".equals(elem.getNamespaceURI()))
/*     */     {
/* 526 */       return true;
/*     */     }
/* 528 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SecurityContextTokenImpl locateBySCTId(FilterProcessingContext context, String sctId) throws XWSSecurityException {
/* 533 */     Hashtable contextMap = context.getIssuedTokenContextMap();
/*     */     
/* 535 */     if (contextMap == null)
/*     */     {
/*     */       
/* 538 */       return null;
/*     */     }
/*     */     
/* 541 */     Iterator<Map.Entry> it = contextMap.entrySet().iterator();
/*     */     
/* 543 */     while (it.hasNext()) {
/* 544 */       Map.Entry entry = it.next();
/* 545 */       String tokenId = (String)entry.getKey();
/* 546 */       Object token = entry.getValue();
/* 547 */       if (token instanceof IssuedTokenContext) {
/* 548 */         Object securityToken = ((IssuedTokenContext)token).getSecurityToken();
/* 549 */         if (securityToken instanceof SecurityContextToken) {
/* 550 */           SecurityContextToken ret = (SecurityContextToken)securityToken;
/* 551 */           if (sctId.equals(ret.getIdentifier().toString())) {
/* 552 */             return new SecurityContextTokenImpl(context.getSOAPMessage().getSOAPPart(), ret.getIdentifier().toString(), ret.getInstance(), ret.getWsuId(), ret.getExtElements());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 558 */     return null;
/*     */   }
/*     */   
/*     */   public static void updateSamlVsKeyCache(SecurityTokenReference str, FilterProcessingContext ctx, Key symKey) {
/* 562 */     ReferenceElement ref = ((SecurityTokenReference)str).getReference();
/* 563 */     if (ref instanceof KeyIdentifier) {
/* 564 */       String assertionId = ((KeyIdentifier)ref).getReferenceValue();
/* 565 */       if (ctx.getSamlIdVSKeyCache().get(assertionId) == null) {
/* 566 */         ctx.getSamlIdVSKeyCache().put(assertionId, symKey);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updateSamlVsKeyCache(SecurityTokenReferenceType str, FilterProcessingContext ctx, Key symKey) {
/* 572 */     List<Object> list = str.getAny();
/* 573 */     for (int i = 0; i < list.size(); i++) {
/* 574 */       Object item = list.get(i);
/* 575 */       if (item instanceof JAXBElement) {
/* 576 */         item = ((JAXBElement)item).getValue();
/*     */       }
/* 578 */       if (item instanceof KeyIdentifierType) {
/* 579 */         String assertionId = ((KeyIdentifierType)item).getValue();
/* 580 */         if (ctx.getSamlIdVSKeyCache().get(assertionId) == null) {
/* 581 */           ctx.getSamlIdVSKeyCache().put(assertionId, symKey);
/*     */         }
/* 583 */         HashMap<String, Key> sentSamlKeys = (HashMap)ctx.getExtraneousProperty("stored_saml_keys");
/* 584 */         if (sentSamlKeys != null && 
/* 585 */           sentSamlKeys.get(assertionId) == null) {
/* 586 */           sentSamlKeys.put(assertionId, symKey);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void insertCertificate(FilterProcessingContext context, AuthenticationTokenPolicy.X509CertificateBinding certInfo, String x509id) throws XWSSecurityException {
/* 596 */     HashMap<String, X509SecurityToken> insertedX509Cache = context.getInsertedX509Cache();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 608 */       String valueType = certInfo.getValueType();
/* 609 */       if (valueType == null || valueType.equals(""))
/*     */       {
/* 611 */         valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*     */       }
/* 613 */       SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/* 614 */       X509SecurityToken x509Token = new X509SecurityToken(secureMessage.getSOAPPart(), certInfo.getX509Certificate(), x509id, valueType);
/* 615 */       secureMessage.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)x509Token);
/* 616 */       insertedX509Cache.put(x509id, x509Token);
/* 617 */       certInfo.setReferenceType("Direct");
/*     */     }
/* 619 */     catch (Exception e) {
/* 620 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getDataEncryptionAlgo(JAXBFilterProcessingContext context) {
/* 626 */     WSSPolicy policy = (WSSPolicy)context.getSecurityPolicy();
/* 627 */     String tmp = "";
/* 628 */     if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy)) {
/* 629 */       EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)policy.getFeatureBinding();
/* 630 */       MLSPolicy keyBinding = ((EncryptionPolicy)policy).getKeyBinding();
/* 631 */       tmp = featureBinding.getDataEncryptionAlgorithm();
/* 632 */       if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)keyBinding) && context.getTrustContext() != null) {
/* 633 */         tmp = context.getTrustContext().getEncryptWith();
/*     */       }
/*     */     } 
/* 636 */     if ((tmp == null || "".equals(tmp)) && 
/* 637 */       context.getAlgorithmSuite() != null) {
/* 638 */       tmp = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 643 */     return tmp;
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
/*     */   public static URL loadFromContext(String configFileName, Object context) {
/* 656 */     return ReflectionUtil.<URL>invoke(context, "getResource", URL.class, new Object[] { configFileName });
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
/*     */   public static URL loadFromClasspath(String configFileName) {
/* 668 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 669 */     if (cl == null) {
/* 670 */       return ClassLoader.getSystemResource(configFileName);
/*     */     }
/* 672 */     return cl.getResource(configFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element convertSTRToElement(Object strElem, Document doc) throws XWSSecurityException {
/* 678 */     if (strElem == null || strElem instanceof Element) {
/* 679 */       return (Element)strElem;
/*     */     }
/*     */     
/* 682 */     SecurityTokenReference stRef = null;
/* 683 */     if (strElem instanceof KeyIdentifier) {
/* 684 */       KeyIdentifier keyIdStrElem = (KeyIdentifier)strElem;
/* 685 */       if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(keyIdStrElem.getValueType())) {
/* 686 */         stRef = new SecurityTokenReference(doc);
/* 687 */         SamlKeyIdentifier keyId = new SamlKeyIdentifier(doc);
/* 688 */         keyId.setReferenceValue(keyIdStrElem.getReferenceValue());
/* 689 */         keyId.setValueType(keyIdStrElem.getValueType());
/* 690 */         stRef.setReference((ReferenceElement)keyId);
/*     */       } else {
/* 692 */         throw new XWSSecurityException("Unsupported reference type encountered");
/*     */       } 
/*     */     } 
/* 695 */     return (Element)stRef;
/*     */   }
/*     */   
/*     */   public static void copySubject(final Subject to, final Subject from) {
/* 699 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 702 */             to.getPrincipals().addAll(from.getPrincipals());
/* 703 */             to.getPublicCredentials().addAll(from.getPublicCredentials());
/* 704 */             to.getPrivateCredentials().addAll(from.getPrivateCredentials());
/* 705 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static Subject getSubject(final Map context) {
/* 712 */     Subject otherPartySubject = (Subject)context.get("javax.security.auth.Subject");
/*     */     
/* 714 */     if (otherPartySubject != null) {
/* 715 */       return otherPartySubject;
/*     */     }
/* 717 */     otherPartySubject = AccessController.<Subject>doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run()
/*     */           {
/* 721 */             Subject otherPartySubj = new Subject();
/* 722 */             context.put("javax.security.auth.Subject", otherPartySubj);
/* 723 */             return otherPartySubj;
/*     */           }
/*     */         });
/*     */     
/* 727 */     return otherPartySubject;
/*     */   }
/*     */   
/*     */   public static SecurityContextToken getSCT(SecurityContextToken sct, SOAPVersion version) {
/* 731 */     if (sct instanceof SecurityContextTokenType) {
/* 732 */       return (SecurityContextToken)new SecurityContextToken13((SecurityContextTokenType)sct, version);
/*     */     }
/*     */     
/* 735 */     return (SecurityContextToken)new SecurityContextToken((SecurityContextTokenType)sct, version);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void copy(Map p1, Map p2) {
/* 740 */     if (p2 == null || p1 == null) {
/*     */       return;
/*     */     }
/* 743 */     p1.putAll(p2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object newInstance(String className, ClassLoader classLoader, String spiName) {
/*     */     try {
/*     */       Class<?> spiClass;
/* 750 */       if (classLoader == null) {
/* 751 */         spiClass = Class.forName(className);
/*     */       } else {
/* 753 */         spiClass = classLoader.loadClass(className);
/*     */       } 
/* 755 */       return spiClass.newInstance();
/* 756 */     } catch (ClassNotFoundException x) {
/* 757 */       throw new XWSSecurityRuntimeException("The " + spiName + " :" + className + " specified in META-INF/services was not found", x);
/*     */     }
/* 759 */     catch (Exception x) {
/* 760 */       throw new XWSSecurityRuntimeException("The " + spiName + " :" + className + " specified in META-INF/services could not be instantiated", x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object loadSPIClass(URL url, String spiName) {
/* 766 */     InputStream is = null;
/* 767 */     if (url == null) {
/* 768 */       return null;
/*     */     }
/*     */     try {
/* 771 */       is = url.openStream();
/* 772 */       if (is != null) {
/*     */         try {
/* 774 */           BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*     */           
/* 776 */           String factoryClassName = rd.readLine();
/* 777 */           rd.close();
/* 778 */           if (factoryClassName != null && !"".equals(factoryClassName)) {
/*     */             
/* 780 */             Object obj = newInstance(factoryClassName, Thread.currentThread().getContextClassLoader(), spiName);
/* 781 */             return obj;
/*     */           } 
/* 783 */         } catch (Exception e) {
/* 784 */           throw new XWSSecurityRuntimeException(e);
/*     */         } 
/*     */       }
/* 787 */     } catch (IOException e) {
/* 788 */       return null;
/*     */     } 
/* 790 */     return null;
/*     */   }
/*     */   
/*     */   public static long toLong(String lng) throws XWSSecurityException {
/* 794 */     if (lng == null) {
/* 795 */       return 0L;
/*     */     }
/* 797 */     Long ret = Long.valueOf(0L);
/*     */     try {
/* 799 */       ret = Long.valueOf(lng);
/* 800 */     } catch (Exception e) {
/* 801 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0719_ERROR_GETTING_LONG_VALUE());
/* 802 */       throw new XWSSecurityException(e);
/*     */     } 
/* 804 */     return ret.longValue();
/*     */   }
/*     */   public static String getKeyAlgo(String algo) {
/* 807 */     if (algo != null && algo.equals("SHA256withRSA"))
/* 808 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"; 
/* 809 */     if (algo != null && algo.equals("SHA384withRSA"))
/* 810 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha384"; 
/* 811 */     if (algo != null && algo.equals("SHA512withRSA")) {
/* 812 */       return "http://www.w3.org/2001/04/xmldsig-more#rsa-sha512";
/*     */     }
/* 814 */     return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\SecurityUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */