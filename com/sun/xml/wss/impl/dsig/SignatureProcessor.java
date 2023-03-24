/*      */ package com.sun.xml.wss.impl.dsig;
/*      */ 
/*      */ import com.sun.xml.ws.security.DerivedKeyToken;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.SecurityContextToken;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.ReferenceElement;
/*      */ import com.sun.xml.wss.core.SecurityContextTokenImpl;
/*      */ import com.sun.xml.wss.core.SecurityHeader;
/*      */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*      */ import com.sun.xml.wss.core.SecurityTokenReference;
/*      */ import com.sun.xml.wss.core.X509SecurityToken;
/*      */ import com.sun.xml.wss.core.reference.DirectReference;
/*      */ import com.sun.xml.wss.core.reference.X509IssuerSerial;
/*      */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*      */ import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.PolicyViolationException;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.keyinfo.KeyIdentifierStrategy;
/*      */ import com.sun.xml.wss.impl.misc.Base64;
/*      */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.verifier.SignaturePolicyVerifier;
/*      */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.security.Key;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.crypto.Data;
/*      */ import javax.xml.crypto.NodeSetData;
/*      */ import javax.xml.crypto.OctetStreamData;
/*      */ import javax.xml.crypto.URIReference;
/*      */ import javax.xml.crypto.dsig.Reference;
/*      */ import javax.xml.crypto.dsig.SignedInfo;
/*      */ import javax.xml.crypto.dsig.Transform;
/*      */ import javax.xml.crypto.dsig.TransformService;
/*      */ import javax.xml.crypto.dsig.XMLSignature;
/*      */ import javax.xml.crypto.dsig.XMLSignatureException;
/*      */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*      */ import javax.xml.crypto.dsig.dom.DOMValidateContext;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*      */ import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import org.jcp.xml.dsig.internal.dom.DOMSubTreeData;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
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
/*      */ public class SignatureProcessor
/*      */ {
/*  153 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
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
/*      */   public static int sign(FilterProcessingContext context) throws XWSSecurityException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual getSecurityPolicy : ()Lcom/sun/xml/wss/impl/policy/SecurityPolicy;
/*      */     //   4: checkcast com/sun/xml/wss/impl/policy/mls/SignaturePolicy
/*      */     //   7: astore_1
/*      */     //   8: aload_0
/*      */     //   9: invokevirtual getSOAPMessage : ()Ljavax/xml/soap/SOAPMessage;
/*      */     //   12: astore_2
/*      */     //   13: aload_0
/*      */     //   14: invokevirtual getSecurableSoapMessage : ()Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*      */     //   17: astore_3
/*      */     //   18: aload_1
/*      */     //   19: invokevirtual getKeyBinding : ()Lcom/sun/xml/wss/impl/policy/MLSPolicy;
/*      */     //   22: checkcast com/sun/xml/wss/impl/policy/mls/WSSPolicy
/*      */     //   25: astore #4
/*      */     //   27: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   30: getstatic java/util/logging/Level.FINEST : Ljava/util/logging/Level;
/*      */     //   33: invokevirtual isLoggable : (Ljava/util/logging/Level;)Z
/*      */     //   36: ifeq -> 68
/*      */     //   39: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   42: getstatic java/util/logging/Level.FINEST : Ljava/util/logging/Level;
/*      */     //   45: new java/lang/StringBuilder
/*      */     //   48: dup
/*      */     //   49: invokespecial <init> : ()V
/*      */     //   52: ldc 'KeyBinding is '
/*      */     //   54: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   57: aload #4
/*      */     //   59: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*      */     //   62: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   65: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   68: aconst_null
/*      */     //   69: astore #5
/*      */     //   71: aconst_null
/*      */     //   72: astore #6
/*      */     //   74: invokestatic getInstance : ()Lcom/sun/xml/wss/impl/dsig/WSSPolicyConsumerImpl;
/*      */     //   77: astore #7
/*      */     //   79: aconst_null
/*      */     //   80: astore #8
/*      */     //   82: aload_3
/*      */     //   83: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   86: astore #9
/*      */     //   88: aload_1
/*      */     //   89: invokevirtual getFeatureBinding : ()Lcom/sun/xml/wss/impl/policy/MLSPolicy;
/*      */     //   92: checkcast com/sun/xml/wss/impl/policy/mls/SignaturePolicy$FeatureBinding
/*      */     //   95: astore #10
/*      */     //   97: aload_0
/*      */     //   98: invokevirtual getAlgorithmSuite : ()Lcom/sun/xml/wss/impl/AlgorithmSuite;
/*      */     //   101: astore #11
/*      */     //   103: ldc 'true'
/*      */     //   105: aload_0
/*      */     //   106: ldc 'EnableWSS11PolicyReceiver'
/*      */     //   108: invokevirtual getExtraneousProperty : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   111: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   114: istore #12
/*      */     //   116: ldc 'true'
/*      */     //   118: aload_0
/*      */     //   119: ldc 'EnableWSS11PolicySender'
/*      */     //   121: invokevirtual getExtraneousProperty : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   124: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   127: istore #13
/*      */     //   129: iload #13
/*      */     //   131: ifne -> 138
/*      */     //   134: iconst_1
/*      */     //   135: goto -> 139
/*      */     //   138: iconst_0
/*      */     //   139: istore #14
/*      */     //   141: iload #12
/*      */     //   143: ifeq -> 162
/*      */     //   146: iload #13
/*      */     //   148: ifeq -> 162
/*      */     //   151: aload_0
/*      */     //   152: invokestatic getEKSHA1Ref : (Lcom/sun/xml/wss/impl/FilterProcessingContext;)Ljava/lang/String;
/*      */     //   155: ifnull -> 162
/*      */     //   158: iconst_1
/*      */     //   159: goto -> 163
/*      */     //   162: iconst_0
/*      */     //   163: istore #15
/*      */     //   165: aload #4
/*      */     //   167: invokestatic usernameTokenPolicy : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   170: ifeq -> 195
/*      */     //   173: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   176: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   179: invokestatic WSS_1326_UNSUPPORTED_USERNAMETOKEN_KEYBINDING : ()Ljava/lang/String;
/*      */     //   182: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   185: new com/sun/xml/wss/XWSSecurityException
/*      */     //   188: dup
/*      */     //   189: ldc 'UsernameToken as KeyBinding for SignaturePolicy is Not Yet Supported'
/*      */     //   191: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   194: athrow
/*      */     //   195: aload #4
/*      */     //   197: invokestatic derivedTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   200: ifeq -> 995
/*      */     //   203: aload #4
/*      */     //   205: invokevirtual clone : ()Ljava/lang/Object;
/*      */     //   208: checkcast com/sun/xml/wss/impl/policy/mls/DerivedTokenKeyBinding
/*      */     //   211: astore #16
/*      */     //   213: aload #16
/*      */     //   215: invokevirtual getOriginalKeyBinding : ()Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;
/*      */     //   218: astore #17
/*      */     //   220: aconst_null
/*      */     //   221: astore #18
/*      */     //   223: aload #11
/*      */     //   225: ifnull -> 235
/*      */     //   228: aload #11
/*      */     //   230: invokevirtual getEncryptionAlgorithm : ()Ljava/lang/String;
/*      */     //   233: astore #18
/*      */     //   235: aload #18
/*      */     //   237: invokestatic getSecretKeyAlgorithm : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   240: astore #19
/*      */     //   242: lconst_0
/*      */     //   243: lstore #20
/*      */     //   245: aload #18
/*      */     //   247: invokestatic getLengthFromAlgorithm : (Ljava/lang/String;)I
/*      */     //   250: i2l
/*      */     //   251: lstore #22
/*      */     //   253: lload #22
/*      */     //   255: ldc2_w 32
/*      */     //   258: lcmp
/*      */     //   259: ifne -> 267
/*      */     //   262: ldc2_w 24
/*      */     //   265: lstore #22
/*      */     //   267: aload #17
/*      */     //   269: invokestatic x509CertificateBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   272: ifeq -> 297
/*      */     //   275: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   278: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   281: invokestatic WSS_1327_UNSUPPORTED_ASYMMETRICBINDING_DERIVEDKEY_X_509_TOKEN : ()Ljava/lang/String;
/*      */     //   284: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   287: new com/sun/xml/wss/XWSSecurityException
/*      */     //   290: dup
/*      */     //   291: ldc 'Asymmetric Binding with DerivedKeys under X509Token Policy Not Yet Supported'
/*      */     //   293: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   296: athrow
/*      */     //   297: aload #17
/*      */     //   299: invokestatic symmetricKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   302: ifeq -> 424
/*      */     //   305: aconst_null
/*      */     //   306: astore #24
/*      */     //   308: aload_0
/*      */     //   309: invokevirtual getSymmetricKeyBinding : ()Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;
/*      */     //   312: ifnull -> 326
/*      */     //   315: aload_0
/*      */     //   316: invokevirtual getSymmetricKeyBinding : ()Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;
/*      */     //   319: astore #24
/*      */     //   321: aload_0
/*      */     //   322: aconst_null
/*      */     //   323: invokevirtual setSymmetricKeyBinding : (Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;)V
/*      */     //   326: aconst_null
/*      */     //   327: astore #25
/*      */     //   329: aload_0
/*      */     //   330: invokevirtual getCurrentSecret : ()Ljavax/crypto/SecretKey;
/*      */     //   333: ifnull -> 345
/*      */     //   336: aload_0
/*      */     //   337: invokevirtual getCurrentSecret : ()Ljavax/crypto/SecretKey;
/*      */     //   340: astore #25
/*      */     //   342: goto -> 358
/*      */     //   345: aload #24
/*      */     //   347: invokevirtual getSecretKey : ()Ljavax/crypto/SecretKey;
/*      */     //   350: astore #25
/*      */     //   352: aload_0
/*      */     //   353: aload #25
/*      */     //   355: invokevirtual setCurrentSecret : (Ljava/security/Key;)V
/*      */     //   358: aload #25
/*      */     //   360: invokeinterface getEncoded : ()[B
/*      */     //   365: astore #26
/*      */     //   367: new com/sun/xml/ws/security/impl/DerivedKeyTokenImpl
/*      */     //   370: dup
/*      */     //   371: lload #20
/*      */     //   373: lload #22
/*      */     //   375: aload #26
/*      */     //   377: invokespecial <init> : (JJ[B)V
/*      */     //   380: astore #27
/*      */     //   382: aload #27
/*      */     //   384: aload #19
/*      */     //   386: invokeinterface generateSymmetricKey : (Ljava/lang/String;)Ljavax/crypto/SecretKey;
/*      */     //   391: astore #5
/*      */     //   393: iconst_1
/*      */     //   394: anewarray org/w3c/dom/Node
/*      */     //   397: astore #28
/*      */     //   399: aload_0
/*      */     //   400: aload #4
/*      */     //   402: aload #25
/*      */     //   404: aload_1
/*      */     //   405: aload #28
/*      */     //   407: aconst_null
/*      */     //   408: aload #27
/*      */     //   410: invokestatic prepareForSymmetricKeySignature : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;Ljava/security/Key;Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;[Lorg/w3c/dom/Node;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Lcom/sun/xml/ws/security/DerivedKeyToken;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   413: astore #8
/*      */     //   415: aload #28
/*      */     //   417: iconst_0
/*      */     //   418: aaload
/*      */     //   419: astore #6
/*      */     //   421: goto -> 992
/*      */     //   424: aload #17
/*      */     //   426: invokestatic issuedTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   429: ifeq -> 924
/*      */     //   432: aload_0
/*      */     //   433: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   436: invokeinterface getProofKey : ()[B
/*      */     //   441: astore #24
/*      */     //   443: aload #24
/*      */     //   445: ifnonnull -> 835
/*      */     //   448: aload_0
/*      */     //   449: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   452: invokeinterface getRequestorCertificate : ()Ljava/security/cert/X509Certificate;
/*      */     //   457: astore #25
/*      */     //   459: aload #25
/*      */     //   461: ifnonnull -> 486
/*      */     //   464: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   467: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   470: invokestatic WSS_1328_ILLEGAL_CERTIFICATE_KEY_NULL : ()Ljava/lang/String;
/*      */     //   473: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   476: new com/sun/xml/wss/XWSSecurityException
/*      */     //   479: dup
/*      */     //   480: ldc 'Requestor Certificate and Proof Key are both null for Issued Token'
/*      */     //   482: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   485: athrow
/*      */     //   486: aload_0
/*      */     //   487: invokevirtual getSecurityEnvironment : ()Lcom/sun/xml/wss/SecurityEnvironment;
/*      */     //   490: aload_0
/*      */     //   491: invokevirtual getExtraneousProperties : ()Ljava/util/Map;
/*      */     //   494: aload #25
/*      */     //   496: invokeinterface getPrivateKey : (Ljava/util/Map;Ljava/security/cert/X509Certificate;)Ljava/security/PrivateKey;
/*      */     //   501: astore #5
/*      */     //   503: aload_0
/*      */     //   504: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   507: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   512: checkcast com/sun/xml/ws/security/trust/GenericToken
/*      */     //   515: astore #26
/*      */     //   517: aload #26
/*      */     //   519: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*      */     //   522: checkcast org/w3c/dom/Element
/*      */     //   525: astore #27
/*      */     //   527: aload_3
/*      */     //   528: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   531: aload #27
/*      */     //   533: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   536: astore #28
/*      */     //   538: aload #28
/*      */     //   540: ldc 'Id'
/*      */     //   542: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   547: astore #29
/*      */     //   549: ldc ''
/*      */     //   551: aload #29
/*      */     //   553: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   556: ifeq -> 587
/*      */     //   559: ldc 'EncryptedData'
/*      */     //   561: aload #28
/*      */     //   563: invokeinterface getLocalName : ()Ljava/lang/String;
/*      */     //   568: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   571: ifeq -> 587
/*      */     //   574: aload #28
/*      */     //   576: ldc 'Id'
/*      */     //   578: aload_3
/*      */     //   579: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   582: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   587: aload_0
/*      */     //   588: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*      */     //   591: aload #4
/*      */     //   593: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   596: aload #28
/*      */     //   598: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   601: pop
/*      */     //   602: aload #17
/*      */     //   604: checkcast com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding
/*      */     //   607: astore #30
/*      */     //   609: aload #30
/*      */     //   611: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*      */     //   614: astore #31
/*      */     //   616: aload #30
/*      */     //   618: pop
/*      */     //   619: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*      */     //   622: aload #31
/*      */     //   624: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   627: ifne -> 672
/*      */     //   630: aload #30
/*      */     //   632: pop
/*      */     //   633: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*      */     //   636: aload #31
/*      */     //   638: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   641: ifne -> 672
/*      */     //   644: aload #30
/*      */     //   646: pop
/*      */     //   647: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*      */     //   650: aload #31
/*      */     //   652: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   655: ifne -> 672
/*      */     //   658: aload #30
/*      */     //   660: pop
/*      */     //   661: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*      */     //   664: aload #31
/*      */     //   666: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   669: ifeq -> 676
/*      */     //   672: iconst_1
/*      */     //   673: goto -> 677
/*      */     //   676: iconst_0
/*      */     //   677: istore #32
/*      */     //   679: aconst_null
/*      */     //   680: astore #33
/*      */     //   682: iload #32
/*      */     //   684: ifeq -> 709
/*      */     //   687: aload_0
/*      */     //   688: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   691: invokeinterface getAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   696: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   701: checkcast org/w3c/dom/Element
/*      */     //   704: astore #33
/*      */     //   706: goto -> 728
/*      */     //   709: aload_0
/*      */     //   710: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   713: invokeinterface getUnAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   718: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   723: checkcast org/w3c/dom/Element
/*      */     //   726: astore #33
/*      */     //   728: aload_3
/*      */     //   729: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   732: aload #33
/*      */     //   734: iconst_1
/*      */     //   735: invokevirtual importNode : (Lorg/w3c/dom/Node;Z)Lorg/w3c/dom/Node;
/*      */     //   738: checkcast org/w3c/dom/Element
/*      */     //   741: astore #34
/*      */     //   743: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   746: dup
/*      */     //   747: aload_3
/*      */     //   748: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   751: aload #34
/*      */     //   753: iconst_1
/*      */     //   754: invokeinterface cloneNode : (Z)Lorg/w3c/dom/Node;
/*      */     //   759: checkcast org/w3c/dom/Element
/*      */     //   762: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   765: iconst_0
/*      */     //   766: invokespecial <init> : (Ljavax/xml/soap/SOAPElement;Z)V
/*      */     //   769: astore #35
/*      */     //   771: aload #28
/*      */     //   773: ifnull -> 811
/*      */     //   776: iload #32
/*      */     //   778: ifeq -> 802
/*      */     //   781: aload_3
/*      */     //   782: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   785: aload #28
/*      */     //   787: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*      */     //   790: aload #28
/*      */     //   792: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   797: astore #6
/*      */     //   799: goto -> 805
/*      */     //   802: aconst_null
/*      */     //   803: astore #6
/*      */     //   805: aload_0
/*      */     //   806: aload #28
/*      */     //   808: invokevirtual setIssuedSAMLToken : (Lorg/w3c/dom/Element;)V
/*      */     //   811: aload #7
/*      */     //   813: aload_1
/*      */     //   814: aload #35
/*      */     //   816: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   819: astore #8
/*      */     //   821: aload #35
/*      */     //   823: aload_0
/*      */     //   824: aload #25
/*      */     //   826: invokevirtual getPublicKey : ()Ljava/security/PublicKey;
/*      */     //   829: invokestatic updateSamlVsKeyCache : (Lcom/sun/xml/ws/security/SecurityTokenReference;Lcom/sun/xml/wss/impl/FilterProcessingContext;Ljava/security/Key;)V
/*      */     //   832: goto -> 921
/*      */     //   835: new com/sun/xml/ws/security/impl/DerivedKeyTokenImpl
/*      */     //   838: dup
/*      */     //   839: lload #20
/*      */     //   841: lload #22
/*      */     //   843: aload #24
/*      */     //   845: invokespecial <init> : (JJ[B)V
/*      */     //   848: astore #25
/*      */     //   850: aload #25
/*      */     //   852: aload #19
/*      */     //   854: invokeinterface generateSymmetricKey : (Ljava/lang/String;)Ljavax/crypto/SecretKey;
/*      */     //   859: astore #5
/*      */     //   861: iconst_1
/*      */     //   862: anewarray org/w3c/dom/Node
/*      */     //   865: astore #26
/*      */     //   867: ldc 'AES'
/*      */     //   869: astore #27
/*      */     //   871: aload #11
/*      */     //   873: ifnull -> 886
/*      */     //   876: aload #11
/*      */     //   878: invokevirtual getEncryptionAlgorithm : ()Ljava/lang/String;
/*      */     //   881: invokestatic getSecretKeyAlgorithm : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   884: astore #27
/*      */     //   886: new javax/crypto/spec/SecretKeySpec
/*      */     //   889: dup
/*      */     //   890: aload #24
/*      */     //   892: aload #27
/*      */     //   894: invokespecial <init> : ([BLjava/lang/String;)V
/*      */     //   897: astore #28
/*      */     //   899: aload_0
/*      */     //   900: aload #4
/*      */     //   902: aload #28
/*      */     //   904: aload_1
/*      */     //   905: aload #26
/*      */     //   907: aconst_null
/*      */     //   908: aload #25
/*      */     //   910: invokestatic prepareForSymmetricKeySignature : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;Ljava/security/Key;Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;[Lorg/w3c/dom/Node;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Lcom/sun/xml/ws/security/DerivedKeyToken;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   913: astore #8
/*      */     //   915: aload #26
/*      */     //   917: iconst_0
/*      */     //   918: aaload
/*      */     //   919: astore #6
/*      */     //   921: goto -> 992
/*      */     //   924: aload #17
/*      */     //   926: invokestatic secureConversationTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   929: ifeq -> 992
/*      */     //   932: new com/sun/xml/ws/security/impl/DerivedKeyTokenImpl
/*      */     //   935: dup
/*      */     //   936: lload #20
/*      */     //   938: lload #22
/*      */     //   940: aload_0
/*      */     //   941: invokevirtual getSecureConversationContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   944: invokeinterface getProofKey : ()[B
/*      */     //   949: invokespecial <init> : (JJ[B)V
/*      */     //   952: astore #24
/*      */     //   954: aload #24
/*      */     //   956: aload #19
/*      */     //   958: invokeinterface generateSymmetricKey : (Ljava/lang/String;)Ljavax/crypto/SecretKey;
/*      */     //   963: astore #5
/*      */     //   965: iconst_1
/*      */     //   966: anewarray org/w3c/dom/Node
/*      */     //   969: astore #25
/*      */     //   971: aload_0
/*      */     //   972: aload #4
/*      */     //   974: aconst_null
/*      */     //   975: aload_1
/*      */     //   976: aload #25
/*      */     //   978: aconst_null
/*      */     //   979: aload #24
/*      */     //   981: invokestatic prepareForSymmetricKeySignature : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;Ljava/security/Key;Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;[Lorg/w3c/dom/Node;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Lcom/sun/xml/ws/security/DerivedKeyToken;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   984: astore #8
/*      */     //   986: aload #25
/*      */     //   988: iconst_0
/*      */     //   989: aaload
/*      */     //   990: astore #6
/*      */     //   992: goto -> 3091
/*      */     //   995: aload #4
/*      */     //   997: invokestatic issuedTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1000: ifeq -> 1468
/*      */     //   1003: iconst_1
/*      */     //   1004: anewarray org/w3c/dom/Node
/*      */     //   1007: astore #16
/*      */     //   1009: aload_0
/*      */     //   1010: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1013: invokeinterface getProofKey : ()[B
/*      */     //   1018: astore #17
/*      */     //   1020: aload #17
/*      */     //   1022: ifnonnull -> 1412
/*      */     //   1025: aload_0
/*      */     //   1026: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1029: invokeinterface getRequestorCertificate : ()Ljava/security/cert/X509Certificate;
/*      */     //   1034: astore #18
/*      */     //   1036: aload #18
/*      */     //   1038: ifnonnull -> 1063
/*      */     //   1041: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1044: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1047: invokestatic WSS_1328_ILLEGAL_CERTIFICATE_KEY_NULL : ()Ljava/lang/String;
/*      */     //   1050: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1053: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1056: dup
/*      */     //   1057: ldc 'Requestor Certificate and Proof Key are both null for Issued Token'
/*      */     //   1059: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1062: athrow
/*      */     //   1063: aload_0
/*      */     //   1064: invokevirtual getSecurityEnvironment : ()Lcom/sun/xml/wss/SecurityEnvironment;
/*      */     //   1067: aload_0
/*      */     //   1068: invokevirtual getExtraneousProperties : ()Ljava/util/Map;
/*      */     //   1071: aload #18
/*      */     //   1073: invokeinterface getPrivateKey : (Ljava/util/Map;Ljava/security/cert/X509Certificate;)Ljava/security/PrivateKey;
/*      */     //   1078: astore #5
/*      */     //   1080: aload_0
/*      */     //   1081: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1084: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   1089: checkcast com/sun/xml/ws/security/trust/GenericToken
/*      */     //   1092: astore #19
/*      */     //   1094: aload #19
/*      */     //   1096: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*      */     //   1099: checkcast org/w3c/dom/Element
/*      */     //   1102: astore #20
/*      */     //   1104: aload_3
/*      */     //   1105: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1108: aload #20
/*      */     //   1110: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   1113: astore #21
/*      */     //   1115: aload #21
/*      */     //   1117: ldc 'Id'
/*      */     //   1119: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1124: astore #22
/*      */     //   1126: ldc ''
/*      */     //   1128: aload #22
/*      */     //   1130: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1133: ifeq -> 1164
/*      */     //   1136: ldc 'EncryptedData'
/*      */     //   1138: aload #21
/*      */     //   1140: invokeinterface getLocalName : ()Ljava/lang/String;
/*      */     //   1145: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1148: ifeq -> 1164
/*      */     //   1151: aload #21
/*      */     //   1153: ldc 'Id'
/*      */     //   1155: aload_3
/*      */     //   1156: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   1159: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1164: aload_0
/*      */     //   1165: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*      */     //   1168: aload #4
/*      */     //   1170: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   1173: aload #21
/*      */     //   1175: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1178: pop
/*      */     //   1179: aload #4
/*      */     //   1181: checkcast com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding
/*      */     //   1184: astore #23
/*      */     //   1186: aload #23
/*      */     //   1188: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*      */     //   1191: astore #24
/*      */     //   1193: aload #23
/*      */     //   1195: pop
/*      */     //   1196: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*      */     //   1199: aload #24
/*      */     //   1201: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1204: ifne -> 1249
/*      */     //   1207: aload #23
/*      */     //   1209: pop
/*      */     //   1210: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*      */     //   1213: aload #24
/*      */     //   1215: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1218: ifne -> 1249
/*      */     //   1221: aload #23
/*      */     //   1223: pop
/*      */     //   1224: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*      */     //   1227: aload #24
/*      */     //   1229: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1232: ifne -> 1249
/*      */     //   1235: aload #23
/*      */     //   1237: pop
/*      */     //   1238: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*      */     //   1241: aload #24
/*      */     //   1243: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1246: ifeq -> 1253
/*      */     //   1249: iconst_1
/*      */     //   1250: goto -> 1254
/*      */     //   1253: iconst_0
/*      */     //   1254: istore #25
/*      */     //   1256: aconst_null
/*      */     //   1257: astore #26
/*      */     //   1259: iload #25
/*      */     //   1261: ifeq -> 1286
/*      */     //   1264: aload_0
/*      */     //   1265: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1268: invokeinterface getAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   1273: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   1278: checkcast org/w3c/dom/Element
/*      */     //   1281: astore #26
/*      */     //   1283: goto -> 1305
/*      */     //   1286: aload_0
/*      */     //   1287: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1290: invokeinterface getUnAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   1295: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   1300: checkcast org/w3c/dom/Element
/*      */     //   1303: astore #26
/*      */     //   1305: aload_3
/*      */     //   1306: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1309: aload #26
/*      */     //   1311: iconst_1
/*      */     //   1312: invokevirtual importNode : (Lorg/w3c/dom/Node;Z)Lorg/w3c/dom/Node;
/*      */     //   1315: checkcast org/w3c/dom/Element
/*      */     //   1318: astore #27
/*      */     //   1320: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   1323: dup
/*      */     //   1324: aload_3
/*      */     //   1325: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1328: aload #27
/*      */     //   1330: iconst_1
/*      */     //   1331: invokeinterface cloneNode : (Z)Lorg/w3c/dom/Node;
/*      */     //   1336: checkcast org/w3c/dom/Element
/*      */     //   1339: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   1342: iconst_0
/*      */     //   1343: invokespecial <init> : (Ljavax/xml/soap/SOAPElement;Z)V
/*      */     //   1346: astore #28
/*      */     //   1348: aload #21
/*      */     //   1350: ifnull -> 1388
/*      */     //   1353: iload #25
/*      */     //   1355: ifeq -> 1379
/*      */     //   1358: aload_3
/*      */     //   1359: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1362: aload #21
/*      */     //   1364: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*      */     //   1367: aload #21
/*      */     //   1369: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1374: astore #6
/*      */     //   1376: goto -> 1382
/*      */     //   1379: aconst_null
/*      */     //   1380: astore #6
/*      */     //   1382: aload_0
/*      */     //   1383: aload #21
/*      */     //   1385: invokevirtual setIssuedSAMLToken : (Lorg/w3c/dom/Element;)V
/*      */     //   1388: aload #7
/*      */     //   1390: aload_1
/*      */     //   1391: aload #28
/*      */     //   1393: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1396: astore #8
/*      */     //   1398: aload #28
/*      */     //   1400: aload_0
/*      */     //   1401: aload #18
/*      */     //   1403: invokevirtual getPublicKey : ()Ljava/security/PublicKey;
/*      */     //   1406: invokestatic updateSamlVsKeyCache : (Lcom/sun/xml/ws/security/SecurityTokenReference;Lcom/sun/xml/wss/impl/FilterProcessingContext;Ljava/security/Key;)V
/*      */     //   1409: goto -> 1465
/*      */     //   1412: ldc 'AES'
/*      */     //   1414: astore #18
/*      */     //   1416: aload #11
/*      */     //   1418: ifnull -> 1431
/*      */     //   1421: aload #11
/*      */     //   1423: invokevirtual getEncryptionAlgorithm : ()Ljava/lang/String;
/*      */     //   1426: invokestatic getSecretKeyAlgorithm : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1429: astore #18
/*      */     //   1431: new javax/crypto/spec/SecretKeySpec
/*      */     //   1434: dup
/*      */     //   1435: aload #17
/*      */     //   1437: aload #18
/*      */     //   1439: invokespecial <init> : ([BLjava/lang/String;)V
/*      */     //   1442: astore #5
/*      */     //   1444: aload_0
/*      */     //   1445: aload #4
/*      */     //   1447: aload #5
/*      */     //   1449: aload_1
/*      */     //   1450: aload #16
/*      */     //   1452: aconst_null
/*      */     //   1453: aconst_null
/*      */     //   1454: invokestatic prepareForSymmetricKeySignature : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;Ljava/security/Key;Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;[Lorg/w3c/dom/Node;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Lcom/sun/xml/ws/security/DerivedKeyToken;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1457: astore #8
/*      */     //   1459: aload #16
/*      */     //   1461: iconst_0
/*      */     //   1462: aaload
/*      */     //   1463: astore #6
/*      */     //   1465: goto -> 3091
/*      */     //   1468: aload #4
/*      */     //   1470: invokestatic secureConversationTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1473: ifeq -> 1544
/*      */     //   1476: iconst_1
/*      */     //   1477: anewarray org/w3c/dom/Node
/*      */     //   1480: astore #16
/*      */     //   1482: aload_0
/*      */     //   1483: aload #4
/*      */     //   1485: aconst_null
/*      */     //   1486: aload_1
/*      */     //   1487: aload #16
/*      */     //   1489: aconst_null
/*      */     //   1490: aconst_null
/*      */     //   1491: invokestatic prepareForSymmetricKeySignature : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;Ljava/security/Key;Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;[Lorg/w3c/dom/Node;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Lcom/sun/xml/ws/security/DerivedKeyToken;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1494: astore #8
/*      */     //   1496: ldc 'AES'
/*      */     //   1498: astore #17
/*      */     //   1500: aload #11
/*      */     //   1502: ifnull -> 1515
/*      */     //   1505: aload #11
/*      */     //   1507: invokevirtual getEncryptionAlgorithm : ()Ljava/lang/String;
/*      */     //   1510: invokestatic getSecretKeyAlgorithm : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1513: astore #17
/*      */     //   1515: new javax/crypto/spec/SecretKeySpec
/*      */     //   1518: dup
/*      */     //   1519: aload_0
/*      */     //   1520: invokevirtual getSecureConversationContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1523: invokeinterface getProofKey : ()[B
/*      */     //   1528: aload #17
/*      */     //   1530: invokespecial <init> : ([BLjava/lang/String;)V
/*      */     //   1533: astore #5
/*      */     //   1535: aload #16
/*      */     //   1537: iconst_0
/*      */     //   1538: aaload
/*      */     //   1539: astore #6
/*      */     //   1541: goto -> 3091
/*      */     //   1544: aload #4
/*      */     //   1546: invokestatic x509CertificateBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1549: ifeq -> 1626
/*      */     //   1552: aconst_null
/*      */     //   1553: astore #16
/*      */     //   1555: aload_0
/*      */     //   1556: invokevirtual getX509CertificateBinding : ()Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   1559: ifnull -> 1576
/*      */     //   1562: aload_0
/*      */     //   1563: invokevirtual getX509CertificateBinding : ()Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   1566: astore #16
/*      */     //   1568: aload_0
/*      */     //   1569: aconst_null
/*      */     //   1570: invokevirtual setX509CertificateBinding : (Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;)V
/*      */     //   1573: goto -> 1583
/*      */     //   1576: aload #4
/*      */     //   1578: checkcast com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding
/*      */     //   1581: astore #16
/*      */     //   1583: aload #16
/*      */     //   1585: invokevirtual getKeyBinding : ()Lcom/sun/xml/wss/impl/policy/MLSPolicy;
/*      */     //   1588: checkcast com/sun/xml/wss/impl/policy/mls/PrivateKeyBinding
/*      */     //   1591: astore #17
/*      */     //   1593: aload #17
/*      */     //   1595: invokevirtual getPrivateKey : ()Ljava/security/PrivateKey;
/*      */     //   1598: astore #5
/*      */     //   1600: iconst_1
/*      */     //   1601: anewarray org/w3c/dom/Node
/*      */     //   1604: astore #18
/*      */     //   1606: aload_0
/*      */     //   1607: aload_1
/*      */     //   1608: aload #16
/*      */     //   1610: aload #18
/*      */     //   1612: invokestatic handleX509Binding : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;[Lorg/w3c/dom/Node;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1615: astore #8
/*      */     //   1617: aload #18
/*      */     //   1619: iconst_0
/*      */     //   1620: aaload
/*      */     //   1621: astore #6
/*      */     //   1623: goto -> 3091
/*      */     //   1626: aload #4
/*      */     //   1628: invokestatic samlTokenPolicy : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1631: ifeq -> 2012
/*      */     //   1634: aload #4
/*      */     //   1636: checkcast com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$SAMLAssertionBinding
/*      */     //   1639: astore #16
/*      */     //   1641: aload #16
/*      */     //   1643: invokevirtual getKeyBinding : ()Lcom/sun/xml/wss/impl/policy/MLSPolicy;
/*      */     //   1646: checkcast com/sun/xml/wss/impl/policy/mls/PrivateKeyBinding
/*      */     //   1649: astore #17
/*      */     //   1651: aload #17
/*      */     //   1653: ifnonnull -> 1678
/*      */     //   1656: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1659: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1662: invokestatic WSS_1329_NULL_PRIVATEKEYBINDING_SAML_POLICY : ()Ljava/lang/String;
/*      */     //   1665: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1668: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1671: dup
/*      */     //   1672: ldc 'PrivateKey binding not set for SAML Policy by CallbackHandler'
/*      */     //   1674: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1677: athrow
/*      */     //   1678: aload #17
/*      */     //   1680: invokevirtual getPrivateKey : ()Ljava/security/PrivateKey;
/*      */     //   1683: astore #5
/*      */     //   1685: aload #5
/*      */     //   1687: ifnonnull -> 1712
/*      */     //   1690: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1693: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1696: invokestatic WSS_1330_NULL_PRIVATEKEY_SAML_POLICY : ()Ljava/lang/String;
/*      */     //   1699: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1702: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1705: dup
/*      */     //   1706: ldc 'PrivateKey null inside PrivateKeyBinding set for SAML Policy '
/*      */     //   1708: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1711: athrow
/*      */     //   1712: aload #16
/*      */     //   1714: invokevirtual getReferenceType : ()Ljava/lang/String;
/*      */     //   1717: astore #18
/*      */     //   1719: aload #18
/*      */     //   1721: ldc 'Embedded'
/*      */     //   1723: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1726: ifeq -> 1751
/*      */     //   1729: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1732: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1735: invokestatic WSS_1331_UNSUPPORTED_EMBEDDED_REFERENCE_SAML : ()Ljava/lang/String;
/*      */     //   1738: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1741: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1744: dup
/*      */     //   1745: ldc 'Embedded Reference Type for SAML Assertions not supported yet'
/*      */     //   1747: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1750: athrow
/*      */     //   1751: aload #16
/*      */     //   1753: invokevirtual getAssertionId : ()Ljava/lang/String;
/*      */     //   1756: astore #19
/*      */     //   1758: aload #16
/*      */     //   1760: invokevirtual getAssertion : ()Lorg/w3c/dom/Element;
/*      */     //   1763: astore #20
/*      */     //   1765: aload #16
/*      */     //   1767: invokevirtual getAuthorityBinding : ()Lorg/w3c/dom/Element;
/*      */     //   1770: astore #21
/*      */     //   1772: aload #19
/*      */     //   1774: ifnonnull -> 1841
/*      */     //   1777: aload #20
/*      */     //   1779: ifnonnull -> 1804
/*      */     //   1782: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1785: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1788: invokestatic WSS_1332_NULL_SAML_ASSERTION_SAML_ASSERTION_ID : ()Ljava/lang/String;
/*      */     //   1791: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1794: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1797: dup
/*      */     //   1798: ldc 'None of SAML Assertion, SAML Assertion Id information was set into  the Policy by the CallbackHandler'
/*      */     //   1800: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1803: athrow
/*      */     //   1804: aload #20
/*      */     //   1806: ldc 'ID'
/*      */     //   1808: invokeinterface getAttributeNode : (Ljava/lang/String;)Lorg/w3c/dom/Attr;
/*      */     //   1813: ifnull -> 1830
/*      */     //   1816: aload #20
/*      */     //   1818: ldc 'ID'
/*      */     //   1820: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1825: astore #19
/*      */     //   1827: goto -> 1841
/*      */     //   1830: aload #20
/*      */     //   1832: ldc 'AssertionID'
/*      */     //   1834: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1839: astore #19
/*      */     //   1841: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   1844: dup
/*      */     //   1845: aload_3
/*      */     //   1846: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1849: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   1852: astore #22
/*      */     //   1854: aload #16
/*      */     //   1856: invokevirtual getSTRID : ()Ljava/lang/String;
/*      */     //   1859: astore #23
/*      */     //   1861: aload #23
/*      */     //   1863: ifnonnull -> 1872
/*      */     //   1866: aload_3
/*      */     //   1867: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   1870: astore #23
/*      */     //   1872: aload #22
/*      */     //   1874: aload #23
/*      */     //   1876: invokevirtual setWsuId : (Ljava/lang/String;)V
/*      */     //   1879: aload #20
/*      */     //   1881: ldc 'ID'
/*      */     //   1883: invokeinterface getAttributeNode : (Ljava/lang/String;)Lorg/w3c/dom/Attr;
/*      */     //   1888: ifnull -> 1901
/*      */     //   1891: aload #22
/*      */     //   1893: ldc 'http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0'
/*      */     //   1895: invokevirtual setTokenType : (Ljava/lang/String;)V
/*      */     //   1898: goto -> 1908
/*      */     //   1901: aload #22
/*      */     //   1903: ldc 'http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1'
/*      */     //   1905: invokevirtual setTokenType : (Ljava/lang/String;)V
/*      */     //   1908: aload #21
/*      */     //   1910: ifnull -> 1924
/*      */     //   1913: aload #22
/*      */     //   1915: aload #21
/*      */     //   1917: aload_3
/*      */     //   1918: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1921: invokevirtual setSamlAuthorityBinding : (Lorg/w3c/dom/Element;Lorg/w3c/dom/Document;)V
/*      */     //   1924: aload #20
/*      */     //   1926: ifnull -> 2002
/*      */     //   1929: aload #21
/*      */     //   1931: ifnonnull -> 2002
/*      */     //   1934: new com/sun/xml/wss/core/SamlAssertionHeaderBlock
/*      */     //   1937: dup
/*      */     //   1938: aload #20
/*      */     //   1940: aload_3
/*      */     //   1941: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1944: invokespecial <init> : (Lorg/w3c/dom/Element;Lorg/w3c/dom/Document;)V
/*      */     //   1947: astore #24
/*      */     //   1949: aload_3
/*      */     //   1950: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1953: aload #24
/*      */     //   1955: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*      */     //   1958: new com/sun/xml/wss/impl/keyinfo/KeyIdentifierStrategy
/*      */     //   1961: dup
/*      */     //   1962: aload #19
/*      */     //   1964: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1967: astore #25
/*      */     //   1969: aload #25
/*      */     //   1971: aload #22
/*      */     //   1973: aload_3
/*      */     //   1974: invokevirtual insertKey : (Lcom/sun/xml/wss/core/SecurityTokenReference;Lcom/sun/xml/wss/impl/SecurableSoapMessage;)V
/*      */     //   1977: aload #7
/*      */     //   1979: aload_1
/*      */     //   1980: aload #22
/*      */     //   1982: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1985: astore #8
/*      */     //   1987: aload #24
/*      */     //   1989: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   1992: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1997: astore #6
/*      */     //   1999: goto -> 2009
/*      */     //   2002: aload #9
/*      */     //   2004: invokevirtual getNextSiblingOfTimestamp : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   2007: astore #6
/*      */     //   2009: goto -> 3091
/*      */     //   2012: aload #4
/*      */     //   2014: invokestatic symmetricKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   2017: ifeq -> 3069
/*      */     //   2020: aconst_null
/*      */     //   2021: astore #16
/*      */     //   2023: aload_0
/*      */     //   2024: invokevirtual getSymmetricKeyBinding : ()Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;
/*      */     //   2027: ifnull -> 2044
/*      */     //   2030: aload_0
/*      */     //   2031: invokevirtual getSymmetricKeyBinding : ()Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;
/*      */     //   2034: astore #16
/*      */     //   2036: aload_0
/*      */     //   2037: aconst_null
/*      */     //   2038: invokevirtual setSymmetricKeyBinding : (Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;)V
/*      */     //   2041: goto -> 2051
/*      */     //   2044: aload #4
/*      */     //   2046: checkcast com/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding
/*      */     //   2049: astore #16
/*      */     //   2051: aload #16
/*      */     //   2053: invokevirtual getKeyIdentifier : ()Ljava/lang/String;
/*      */     //   2056: getstatic com/sun/xml/wss/impl/MessageConstants._EMPTY : Ljava/lang/String;
/*      */     //   2059: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2062: ifne -> 2099
/*      */     //   2065: aload #16
/*      */     //   2067: invokevirtual getSecretKey : ()Ljavax/crypto/SecretKey;
/*      */     //   2070: astore #5
/*      */     //   2072: aload #16
/*      */     //   2074: invokevirtual getKeyIdentifier : ()Ljava/lang/String;
/*      */     //   2077: astore #17
/*      */     //   2079: aload #7
/*      */     //   2081: aload_1
/*      */     //   2082: aload #17
/*      */     //   2084: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Ljava/lang/String;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   2087: astore #8
/*      */     //   2089: aload #9
/*      */     //   2091: invokevirtual getNextSiblingOfTimestamp : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   2094: astore #6
/*      */     //   2096: goto -> 3066
/*      */     //   2099: iload #15
/*      */     //   2101: ifeq -> 2177
/*      */     //   2104: aload_0
/*      */     //   2105: invokestatic getEKSHA1Ref : (Lcom/sun/xml/wss/impl/FilterProcessingContext;)Ljava/lang/String;
/*      */     //   2108: astore #17
/*      */     //   2110: aload #16
/*      */     //   2112: invokevirtual getSecretKey : ()Ljavax/crypto/SecretKey;
/*      */     //   2115: astore #5
/*      */     //   2117: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   2120: dup
/*      */     //   2121: aload_3
/*      */     //   2122: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2125: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2128: astore #18
/*      */     //   2130: new com/sun/xml/wss/core/reference/EncryptedKeySHA1Identifier
/*      */     //   2133: dup
/*      */     //   2134: aload_3
/*      */     //   2135: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2138: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2141: astore #19
/*      */     //   2143: aload #19
/*      */     //   2145: aload #17
/*      */     //   2147: invokevirtual setReferenceValue : (Ljava/lang/String;)V
/*      */     //   2150: aload #18
/*      */     //   2152: aload #19
/*      */     //   2154: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   2157: aload #7
/*      */     //   2159: aload_1
/*      */     //   2160: aload #18
/*      */     //   2162: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   2165: astore #8
/*      */     //   2167: aload #9
/*      */     //   2169: invokevirtual getNextSiblingOfTimestamp : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   2172: astore #6
/*      */     //   2174: goto -> 3066
/*      */     //   2177: iload #13
/*      */     //   2179: ifne -> 2187
/*      */     //   2182: iload #14
/*      */     //   2184: ifeq -> 3066
/*      */     //   2187: aload #16
/*      */     //   2189: invokevirtual getSecretKey : ()Ljavax/crypto/SecretKey;
/*      */     //   2192: astore #5
/*      */     //   2194: aconst_null
/*      */     //   2195: astore #17
/*      */     //   2197: aconst_null
/*      */     //   2198: astore #18
/*      */     //   2200: aload #16
/*      */     //   2202: invokevirtual getCertAlias : ()Ljava/lang/String;
/*      */     //   2205: getstatic com/sun/xml/wss/impl/MessageConstants._EMPTY : Ljava/lang/String;
/*      */     //   2208: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2211: ifne -> 2277
/*      */     //   2214: new com/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding
/*      */     //   2217: dup
/*      */     //   2218: invokespecial <init> : ()V
/*      */     //   2221: astore #17
/*      */     //   2223: aload #17
/*      */     //   2225: invokevirtual newPrivateKeyBinding : ()Lcom/sun/xml/wss/impl/policy/MLSPolicy;
/*      */     //   2228: pop
/*      */     //   2229: aload #17
/*      */     //   2231: aload #16
/*      */     //   2233: invokevirtual getCertAlias : ()Ljava/lang/String;
/*      */     //   2236: invokevirtual setCertificateIdentifier : (Ljava/lang/String;)V
/*      */     //   2239: aload_0
/*      */     //   2240: invokevirtual getSecurityEnvironment : ()Lcom/sun/xml/wss/SecurityEnvironment;
/*      */     //   2243: aload_0
/*      */     //   2244: invokevirtual getExtraneousProperties : ()Ljava/util/Map;
/*      */     //   2247: aload #17
/*      */     //   2249: invokevirtual getCertificateIdentifier : ()Ljava/lang/String;
/*      */     //   2252: iconst_0
/*      */     //   2253: invokeinterface getCertificate : (Ljava/util/Map;Ljava/lang/String;Z)Ljava/security/cert/X509Certificate;
/*      */     //   2258: astore #18
/*      */     //   2260: aload #17
/*      */     //   2262: aload #18
/*      */     //   2264: invokevirtual setX509Certificate : (Ljava/security/cert/X509Certificate;)V
/*      */     //   2267: aload #17
/*      */     //   2269: ldc 'Direct'
/*      */     //   2271: invokevirtual setReferenceType : (Ljava/lang/String;)V
/*      */     //   2274: goto -> 2302
/*      */     //   2277: aload_0
/*      */     //   2278: invokevirtual getX509CertificateBinding : ()Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   2281: ifnull -> 2302
/*      */     //   2284: aload_0
/*      */     //   2285: invokevirtual getX509CertificateBinding : ()Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   2288: astore #17
/*      */     //   2290: aload_0
/*      */     //   2291: aconst_null
/*      */     //   2292: invokevirtual setX509CertificateBinding : (Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;)V
/*      */     //   2295: aload #17
/*      */     //   2297: invokevirtual getX509Certificate : ()Ljava/security/cert/X509Certificate;
/*      */     //   2300: astore #18
/*      */     //   2302: aload_0
/*      */     //   2303: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*      */     //   2306: astore #19
/*      */     //   2308: aload_0
/*      */     //   2309: invokevirtual getInsertedX509Cache : ()Ljava/util/HashMap;
/*      */     //   2312: astore #20
/*      */     //   2314: aload #17
/*      */     //   2316: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   2319: astore #21
/*      */     //   2321: aload #21
/*      */     //   2323: ifnull -> 2336
/*      */     //   2326: aload #21
/*      */     //   2328: ldc ''
/*      */     //   2330: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2333: ifeq -> 2342
/*      */     //   2336: aload_3
/*      */     //   2337: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   2340: astore #21
/*      */     //   2342: aload_0
/*      */     //   2343: aload #17
/*      */     //   2345: aload #21
/*      */     //   2347: invokestatic checkIncludeTokenPolicy : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Ljava/lang/String;)V
/*      */     //   2350: ldc 'http://www.w3.org/2001/04/xmlenc#rsa-1_5'
/*      */     //   2352: astore #22
/*      */     //   2354: aconst_null
/*      */     //   2355: astore #23
/*      */     //   2357: aload #11
/*      */     //   2359: ifnull -> 2369
/*      */     //   2362: aload #11
/*      */     //   2364: invokevirtual getAsymmetricKeyAlgorithm : ()Ljava/lang/String;
/*      */     //   2367: astore #23
/*      */     //   2369: aload #23
/*      */     //   2371: ifnull -> 2388
/*      */     //   2374: ldc ''
/*      */     //   2376: aload #23
/*      */     //   2378: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2381: ifne -> 2388
/*      */     //   2384: aload #23
/*      */     //   2386: astore #22
/*      */     //   2388: aload #17
/*      */     //   2390: invokevirtual getReferenceType : ()Ljava/lang/String;
/*      */     //   2393: astore #24
/*      */     //   2395: aload #24
/*      */     //   2397: ldc 'Identifier'
/*      */     //   2399: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2402: ifeq -> 2440
/*      */     //   2405: aload #17
/*      */     //   2407: invokevirtual getValueType : ()Ljava/lang/String;
/*      */     //   2410: ldc 'http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1'
/*      */     //   2412: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2415: ifeq -> 2440
/*      */     //   2418: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2421: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2424: invokestatic WSS_1333_UNSUPPORTED_KEYIDENTIFER_X_509_V_1 : ()Ljava/lang/String;
/*      */     //   2427: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   2430: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2433: dup
/*      */     //   2434: ldc 'Key Identifier strategy in X509v1 is not supported'
/*      */     //   2436: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   2439: athrow
/*      */     //   2440: aload #24
/*      */     //   2442: invokestatic getInstance : (Ljava/lang/String;)Lcom/sun/xml/wss/impl/keyinfo/KeyInfoStrategy;
/*      */     //   2445: astore #25
/*      */     //   2447: aconst_null
/*      */     //   2448: astore #26
/*      */     //   2450: aload_0
/*      */     //   2451: invokevirtual getSecurableSoapMessage : ()Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*      */     //   2454: astore_3
/*      */     //   2455: invokestatic getInstance : ()Lcom/sun/xml/wss/impl/dsig/WSSPolicyConsumerImpl;
/*      */     //   2458: astore #7
/*      */     //   2460: aconst_null
/*      */     //   2461: astore #27
/*      */     //   2463: aload #17
/*      */     //   2465: invokevirtual getX509Certificate : ()Ljava/security/cert/X509Certificate;
/*      */     //   2468: astore #18
/*      */     //   2470: aload #17
/*      */     //   2472: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   2475: astore #28
/*      */     //   2477: iconst_0
/*      */     //   2478: istore #29
/*      */     //   2480: aload #28
/*      */     //   2482: ifnull -> 2495
/*      */     //   2485: aload #28
/*      */     //   2487: ldc ''
/*      */     //   2489: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2492: ifeq -> 2501
/*      */     //   2495: aload_3
/*      */     //   2496: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   2499: astore #28
/*      */     //   2501: aload #19
/*      */     //   2503: aload #28
/*      */     //   2505: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2508: checkcast com/sun/xml/wss/core/X509SecurityToken
/*      */     //   2511: astore #27
/*      */     //   2513: aload_0
/*      */     //   2514: invokevirtual getInsertedX509Cache : ()Ljava/util/HashMap;
/*      */     //   2517: aload #28
/*      */     //   2519: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2522: checkcast com/sun/xml/wss/core/X509SecurityToken
/*      */     //   2525: astore #30
/*      */     //   2527: aload #27
/*      */     //   2529: ifnonnull -> 2590
/*      */     //   2532: aload #17
/*      */     //   2534: invokevirtual getValueType : ()Ljava/lang/String;
/*      */     //   2537: astore #31
/*      */     //   2539: aload #31
/*      */     //   2541: ifnull -> 2554
/*      */     //   2544: aload #31
/*      */     //   2546: ldc ''
/*      */     //   2548: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2551: ifeq -> 2558
/*      */     //   2554: ldc 'http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3'
/*      */     //   2556: astore #31
/*      */     //   2558: new com/sun/xml/wss/core/X509SecurityToken
/*      */     //   2561: dup
/*      */     //   2562: aload_3
/*      */     //   2563: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2566: aload #18
/*      */     //   2568: aload #28
/*      */     //   2570: aload #31
/*      */     //   2572: invokespecial <init> : (Lorg/w3c/dom/Document;Ljava/security/cert/X509Certificate;Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   2575: astore #27
/*      */     //   2577: aload #19
/*      */     //   2579: aload #28
/*      */     //   2581: aload #27
/*      */     //   2583: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2586: pop
/*      */     //   2587: goto -> 2593
/*      */     //   2590: iconst_1
/*      */     //   2591: istore #29
/*      */     //   2593: aconst_null
/*      */     //   2594: astore #31
/*      */     //   2596: aload_0
/*      */     //   2597: invokevirtual getEncryptedKeyCache : ()Ljava/util/HashMap;
/*      */     //   2600: astore #32
/*      */     //   2602: iload #29
/*      */     //   2604: ifne -> 2905
/*      */     //   2607: aload_0
/*      */     //   2608: aload #5
/*      */     //   2610: invokevirtual setCurrentSecret : (Ljava/security/Key;)V
/*      */     //   2613: aload_0
/*      */     //   2614: ldc 'SecretKey'
/*      */     //   2616: aload #5
/*      */     //   2618: invokevirtual setExtraneousProperty : (Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   2621: new com/sun/xml/wss/core/KeyInfoHeaderBlock
/*      */     //   2624: dup
/*      */     //   2625: aload_3
/*      */     //   2626: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2629: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2632: astore #26
/*      */     //   2634: aload #25
/*      */     //   2636: aload #18
/*      */     //   2638: invokevirtual setCertificate : (Ljava/security/cert/X509Certificate;)V
/*      */     //   2641: aload #25
/*      */     //   2643: aload #26
/*      */     //   2645: aload_3
/*      */     //   2646: aload #28
/*      */     //   2648: invokevirtual insertKey : (Lcom/sun/xml/wss/core/KeyInfoHeaderBlock;Lcom/sun/xml/wss/impl/SecurableSoapMessage;Ljava/lang/String;)V
/*      */     //   2651: aload #26
/*      */     //   2653: invokevirtual getKeyInfo : ()Lcom/sun/org/apache/xml/internal/security/keys/KeyInfo;
/*      */     //   2656: astore #33
/*      */     //   2658: aconst_null
/*      */     //   2659: astore #34
/*      */     //   2661: aconst_null
/*      */     //   2662: astore #35
/*      */     //   2664: aload #22
/*      */     //   2666: invokestatic getInstance : (Ljava/lang/String;)Lcom/sun/org/apache/xml/internal/security/encryption/XMLCipher;
/*      */     //   2669: astore #35
/*      */     //   2671: aload #35
/*      */     //   2673: iconst_3
/*      */     //   2674: aload #18
/*      */     //   2676: invokevirtual getPublicKey : ()Ljava/security/PublicKey;
/*      */     //   2679: invokevirtual init : (ILjava/security/Key;)V
/*      */     //   2682: aload #35
/*      */     //   2684: ifnull -> 2700
/*      */     //   2687: aload #35
/*      */     //   2689: aload_3
/*      */     //   2690: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2693: aload #5
/*      */     //   2695: invokevirtual encryptKey : (Lorg/w3c/dom/Document;Ljava/security/Key;)Lcom/sun/org/apache/xml/internal/security/encryption/EncryptedKey;
/*      */     //   2698: astore #34
/*      */     //   2700: goto -> 2727
/*      */     //   2703: astore #36
/*      */     //   2705: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2708: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2711: invokestatic WSS_1334_ERROR_CREATING_ENCRYPTEDKEY : ()Ljava/lang/String;
/*      */     //   2714: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   2717: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2720: dup
/*      */     //   2721: aload #36
/*      */     //   2723: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   2726: athrow
/*      */     //   2727: aload_3
/*      */     //   2728: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   2731: astore #31
/*      */     //   2733: aload #34
/*      */     //   2735: aload #31
/*      */     //   2737: invokeinterface setId : (Ljava/lang/String;)V
/*      */     //   2742: aload #32
/*      */     //   2744: aload #28
/*      */     //   2746: aload #31
/*      */     //   2748: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2751: pop
/*      */     //   2752: aload #34
/*      */     //   2754: aload #33
/*      */     //   2756: invokeinterface setKeyInfo : (Lcom/sun/org/apache/xml/internal/security/keys/KeyInfo;)V
/*      */     //   2761: aload #35
/*      */     //   2763: aload #34
/*      */     //   2765: invokevirtual martial : (Lcom/sun/org/apache/xml/internal/security/encryption/EncryptedKey;)Lorg/w3c/dom/Element;
/*      */     //   2768: checkcast javax/xml/soap/SOAPElement
/*      */     //   2771: astore #36
/*      */     //   2773: aload #30
/*      */     //   2775: ifnonnull -> 2790
/*      */     //   2778: aload_3
/*      */     //   2779: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   2782: aload #36
/*      */     //   2784: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*      */     //   2787: goto -> 2805
/*      */     //   2790: aload_3
/*      */     //   2791: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   2794: aload #36
/*      */     //   2796: aload #30
/*      */     //   2798: invokevirtual getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2801: invokevirtual insertBefore : (Lorg/w3c/dom/Node;Lorg/w3c/dom/Node;)Lorg/w3c/dom/Node;
/*      */     //   2804: pop
/*      */     //   2805: aload #36
/*      */     //   2807: new javax/xml/namespace/QName
/*      */     //   2810: dup
/*      */     //   2811: ldc 'http://www.w3.org/2001/04/xmlenc#'
/*      */     //   2813: ldc 'CipherData'
/*      */     //   2815: ldc 'xenc'
/*      */     //   2817: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   2820: invokeinterface getChildElements : (Ljavax/xml/namespace/QName;)Ljava/util/Iterator;
/*      */     //   2825: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   2830: checkcast org/w3c/dom/Element
/*      */     //   2833: astore #37
/*      */     //   2835: aload #37
/*      */     //   2837: ldc 'http://www.w3.org/2001/04/xmlenc#'
/*      */     //   2839: ldc 'CipherValue'
/*      */     //   2841: invokeinterface getElementsByTagNameNS : (Ljava/lang/String;Ljava/lang/String;)Lorg/w3c/dom/NodeList;
/*      */     //   2846: iconst_0
/*      */     //   2847: invokeinterface item : (I)Lorg/w3c/dom/Node;
/*      */     //   2852: invokeinterface getTextContent : ()Ljava/lang/String;
/*      */     //   2857: astore #38
/*      */     //   2859: aload #38
/*      */     //   2861: invokestatic decode : (Ljava/lang/String;)[B
/*      */     //   2864: astore #39
/*      */     //   2866: ldc 'SHA-1'
/*      */     //   2868: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
/*      */     //   2871: aload #39
/*      */     //   2873: invokevirtual digest : ([B)[B
/*      */     //   2876: astore #40
/*      */     //   2878: aload #40
/*      */     //   2880: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   2883: astore #41
/*      */     //   2885: aload_0
/*      */     //   2886: ldc 'EncryptedKeySHA1'
/*      */     //   2888: aload #41
/*      */     //   2890: invokevirtual setExtraneousProperty : (Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   2893: aload #36
/*      */     //   2895: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2900: astore #6
/*      */     //   2902: goto -> 2936
/*      */     //   2905: aload #32
/*      */     //   2907: aload #28
/*      */     //   2909: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2912: checkcast java/lang/String
/*      */     //   2915: astore #31
/*      */     //   2917: aload_0
/*      */     //   2918: invokevirtual getCurrentSecret : ()Ljavax/crypto/SecretKey;
/*      */     //   2921: astore #5
/*      */     //   2923: aload_3
/*      */     //   2924: aload #31
/*      */     //   2926: invokevirtual getElementById : (Ljava/lang/String;)Lorg/w3c/dom/Element;
/*      */     //   2929: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2934: astore #6
/*      */     //   2936: ldc 'Direct'
/*      */     //   2938: aload #24
/*      */     //   2940: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2943: ifeq -> 2970
/*      */     //   2946: aload #30
/*      */     //   2948: ifnonnull -> 2970
/*      */     //   2951: aload_3
/*      */     //   2952: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   2955: aload #27
/*      */     //   2957: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*      */     //   2960: aload #20
/*      */     //   2962: aload #28
/*      */     //   2964: aload #27
/*      */     //   2966: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2969: pop
/*      */     //   2970: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   2973: dup
/*      */     //   2974: aload_3
/*      */     //   2975: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2978: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2981: astore #33
/*      */     //   2983: new com/sun/xml/wss/core/reference/DirectReference
/*      */     //   2986: dup
/*      */     //   2987: invokespecial <init> : ()V
/*      */     //   2990: astore #34
/*      */     //   2992: aload #17
/*      */     //   2994: invokevirtual getSTRID : ()Ljava/lang/String;
/*      */     //   2997: astore #35
/*      */     //   2999: aload #35
/*      */     //   3001: ifnonnull -> 3010
/*      */     //   3004: aload_3
/*      */     //   3005: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   3008: astore #35
/*      */     //   3010: aload #33
/*      */     //   3012: aload #35
/*      */     //   3014: invokevirtual setWsuId : (Ljava/lang/String;)V
/*      */     //   3017: aload #34
/*      */     //   3019: new java/lang/StringBuilder
/*      */     //   3022: dup
/*      */     //   3023: invokespecial <init> : ()V
/*      */     //   3026: ldc '#'
/*      */     //   3028: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   3031: aload #31
/*      */     //   3033: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   3036: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   3039: invokevirtual setURI : (Ljava/lang/String;)V
/*      */     //   3042: aload #34
/*      */     //   3044: ldc 'http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey'
/*      */     //   3046: invokevirtual setValueType : (Ljava/lang/String;)V
/*      */     //   3049: aload #33
/*      */     //   3051: aload #34
/*      */     //   3053: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   3056: aload #7
/*      */     //   3058: aload_1
/*      */     //   3059: aload #33
/*      */     //   3061: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   3064: astore #8
/*      */     //   3066: goto -> 3091
/*      */     //   3069: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   3072: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   3075: invokestatic WSS_1335_UNSUPPORTED_KEYBINDING_SIGNATUREPOLICY : ()Ljava/lang/String;
/*      */     //   3078: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   3081: new com/sun/xml/wss/XWSSecurityException
/*      */     //   3084: dup
/*      */     //   3085: ldc 'Unsupported Key Binding for SignaturePolicy'
/*      */     //   3087: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   3090: athrow
/*      */     //   3091: aload #9
/*      */     //   3093: ldc 'http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd'
/*      */     //   3095: ldc 'UsernameToken'
/*      */     //   3097: invokevirtual getElementsByTagNameNS : (Ljava/lang/String;Ljava/lang/String;)Lorg/w3c/dom/NodeList;
/*      */     //   3100: astore #16
/*      */     //   3102: aload #16
/*      */     //   3104: ifnull -> 3132
/*      */     //   3107: aload #16
/*      */     //   3109: invokeinterface getLength : ()I
/*      */     //   3114: ifle -> 3132
/*      */     //   3117: aload #16
/*      */     //   3119: iconst_0
/*      */     //   3120: invokeinterface item : (I)Lorg/w3c/dom/Node;
/*      */     //   3125: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   3130: astore #6
/*      */     //   3132: aload_0
/*      */     //   3133: invokevirtual getCurrentRefList : ()Lorg/w3c/dom/Node;
/*      */     //   3136: astore #17
/*      */     //   3138: aload #17
/*      */     //   3140: ifnull -> 3152
/*      */     //   3143: aload #17
/*      */     //   3145: astore #6
/*      */     //   3147: aload_0
/*      */     //   3148: aconst_null
/*      */     //   3149: invokevirtual setCurrentReferenceList : (Lorg/w3c/dom/Node;)V
/*      */     //   3152: aload #10
/*      */     //   3154: invokevirtual isEndorsingSignature : ()Z
/*      */     //   3157: ifeq -> 3172
/*      */     //   3160: aload #9
/*      */     //   3162: invokevirtual getLastChild : ()Lorg/w3c/dom/Node;
/*      */     //   3165: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   3170: astore #6
/*      */     //   3172: invokestatic getInstance : ()Lcom/sun/xml/wss/impl/dsig/WSSPolicyConsumerImpl;
/*      */     //   3175: aload_0
/*      */     //   3176: invokevirtual constructSignedInfo : (Lcom/sun/xml/wss/impl/FilterProcessingContext;)Ljavax/xml/crypto/dsig/SignedInfo;
/*      */     //   3179: astore #18
/*      */     //   3181: aconst_null
/*      */     //   3182: astore #19
/*      */     //   3184: aload #6
/*      */     //   3186: ifnonnull -> 3208
/*      */     //   3189: new javax/xml/crypto/dsig/dom/DOMSignContext
/*      */     //   3192: dup
/*      */     //   3193: aload #5
/*      */     //   3195: aload #9
/*      */     //   3197: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   3200: invokespecial <init> : (Ljava/security/Key;Lorg/w3c/dom/Node;)V
/*      */     //   3203: astore #19
/*      */     //   3205: goto -> 3226
/*      */     //   3208: new javax/xml/crypto/dsig/dom/DOMSignContext
/*      */     //   3211: dup
/*      */     //   3212: aload #5
/*      */     //   3214: aload #9
/*      */     //   3216: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   3219: aload #6
/*      */     //   3221: invokespecial <init> : (Ljava/security/Key;Lorg/w3c/dom/Node;Lorg/w3c/dom/Node;)V
/*      */     //   3224: astore #19
/*      */     //   3226: aload #19
/*      */     //   3228: invokestatic getInstance : ()Ljavax/xml/crypto/URIDereferencer;
/*      */     //   3231: invokevirtual setURIDereferencer : (Ljavax/xml/crypto/URIDereferencer;)V
/*      */     //   3234: aload #7
/*      */     //   3236: aload #18
/*      */     //   3238: aload #8
/*      */     //   3240: aload_1
/*      */     //   3241: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   3244: invokevirtual constructSignature : (Ljavax/xml/crypto/dsig/SignedInfo;Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;Ljava/lang/String;)Ljavax/xml/crypto/dsig/XMLSignature;
/*      */     //   3247: astore #20
/*      */     //   3249: aload #19
/*      */     //   3251: ldc 'http://wss.sun.com#processingContext'
/*      */     //   3253: aload_0
/*      */     //   3254: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   3257: pop
/*      */     //   3258: aload #19
/*      */     //   3260: ldc 'http://www.w3.org/2000/09/xmldsig#'
/*      */     //   3262: ldc_w 'ds'
/*      */     //   3265: invokevirtual putNamespacePrefix : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*      */     //   3268: pop
/*      */     //   3269: aload #20
/*      */     //   3271: aload #19
/*      */     //   3273: invokeinterface sign : (Ljavax/xml/crypto/dsig/XMLSignContext;)V
/*      */     //   3278: aload_0
/*      */     //   3279: ldc_w 'SignatureConfirmation'
/*      */     //   3282: invokevirtual getExtraneousProperty : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   3285: checkcast java/util/ArrayList
/*      */     //   3288: astore #21
/*      */     //   3290: aload #21
/*      */     //   3292: ifnull -> 3318
/*      */     //   3295: aload #21
/*      */     //   3297: aload #20
/*      */     //   3299: invokeinterface getSignatureValue : ()Ljavax/xml/crypto/dsig/XMLSignature$SignatureValue;
/*      */     //   3304: invokeinterface getValue : ()[B
/*      */     //   3309: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   3312: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   3317: pop
/*      */     //   3318: goto -> 3360
/*      */     //   3321: astore_1
/*      */     //   3322: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   3325: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   3328: invokestatic WSS_1316_SIGN_FAILED : ()Ljava/lang/String;
/*      */     //   3331: aload_1
/*      */     //   3332: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   3335: aload_1
/*      */     //   3336: athrow
/*      */     //   3337: astore_1
/*      */     //   3338: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   3341: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   3344: invokestatic WSS_1316_SIGN_FAILED : ()Ljava/lang/String;
/*      */     //   3347: aload_1
/*      */     //   3348: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   3351: new com/sun/xml/wss/XWSSecurityException
/*      */     //   3354: dup
/*      */     //   3355: aload_1
/*      */     //   3356: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   3359: athrow
/*      */     //   3360: iconst_0
/*      */     //   3361: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #165	-> 0
/*      */     //   #166	-> 8
/*      */     //   #169	-> 13
/*      */     //   #170	-> 18
/*      */     //   #171	-> 27
/*      */     //   #172	-> 39
/*      */     //   #175	-> 68
/*      */     //   #176	-> 71
/*      */     //   #178	-> 74
/*      */     //   #179	-> 79
/*      */     //   #180	-> 82
/*      */     //   #182	-> 88
/*      */     //   #184	-> 97
/*      */     //   #186	-> 103
/*      */     //   #187	-> 116
/*      */     //   #188	-> 129
/*      */     //   #189	-> 141
/*      */     //   #191	-> 165
/*      */     //   #192	-> 173
/*      */     //   #193	-> 185
/*      */     //   #194	-> 195
/*      */     //   #195	-> 203
/*      */     //   #197	-> 213
/*      */     //   #199	-> 220
/*      */     //   #200	-> 223
/*      */     //   #201	-> 228
/*      */     //   #203	-> 235
/*      */     //   #206	-> 242
/*      */     //   #207	-> 245
/*      */     //   #208	-> 253
/*      */     //   #210	-> 267
/*      */     //   #212	-> 275
/*      */     //   #213	-> 287
/*      */     //   #214	-> 297
/*      */     //   #216	-> 305
/*      */     //   #217	-> 308
/*      */     //   #218	-> 315
/*      */     //   #219	-> 321
/*      */     //   #222	-> 326
/*      */     //   #223	-> 329
/*      */     //   #224	-> 336
/*      */     //   #226	-> 345
/*      */     //   #227	-> 352
/*      */     //   #229	-> 358
/*      */     //   #230	-> 367
/*      */     //   #232	-> 382
/*      */     //   #234	-> 393
/*      */     //   #235	-> 399
/*      */     //   #236	-> 415
/*      */     //   #238	-> 421
/*      */     //   #239	-> 432
/*      */     //   #240	-> 443
/*      */     //   #242	-> 448
/*      */     //   #244	-> 459
/*      */     //   #245	-> 464
/*      */     //   #246	-> 476
/*      */     //   #249	-> 486
/*      */     //   #253	-> 503
/*      */     //   #255	-> 517
/*      */     //   #256	-> 527
/*      */     //   #260	-> 538
/*      */     //   #261	-> 549
/*      */     //   #264	-> 574
/*      */     //   #266	-> 587
/*      */     //   #267	-> 602
/*      */     //   #268	-> 609
/*      */     //   #269	-> 616
/*      */     //   #274	-> 679
/*      */     //   #276	-> 682
/*      */     //   #277	-> 687
/*      */     //   #280	-> 709
/*      */     //   #284	-> 728
/*      */     //   #286	-> 743
/*      */     //   #290	-> 771
/*      */     //   #291	-> 776
/*      */     //   #292	-> 781
/*      */     //   #294	-> 790
/*      */     //   #297	-> 802
/*      */     //   #299	-> 805
/*      */     //   #301	-> 811
/*      */     //   #302	-> 821
/*      */     //   #303	-> 832
/*      */     //   #304	-> 835
/*      */     //   #305	-> 850
/*      */     //   #306	-> 861
/*      */     //   #308	-> 867
/*      */     //   #309	-> 871
/*      */     //   #310	-> 876
/*      */     //   #312	-> 886
/*      */     //   #313	-> 899
/*      */     //   #315	-> 915
/*      */     //   #317	-> 921
/*      */     //   #319	-> 932
/*      */     //   #321	-> 954
/*      */     //   #322	-> 965
/*      */     //   #323	-> 971
/*      */     //   #324	-> 986
/*      */     //   #327	-> 992
/*      */     //   #328	-> 1003
/*      */     //   #330	-> 1009
/*      */     //   #331	-> 1020
/*      */     //   #333	-> 1025
/*      */     //   #335	-> 1036
/*      */     //   #336	-> 1041
/*      */     //   #337	-> 1053
/*      */     //   #340	-> 1063
/*      */     //   #344	-> 1080
/*      */     //   #346	-> 1094
/*      */     //   #347	-> 1104
/*      */     //   #351	-> 1115
/*      */     //   #352	-> 1126
/*      */     //   #355	-> 1151
/*      */     //   #357	-> 1164
/*      */     //   #358	-> 1179
/*      */     //   #359	-> 1186
/*      */     //   #360	-> 1193
/*      */     //   #365	-> 1256
/*      */     //   #367	-> 1259
/*      */     //   #368	-> 1264
/*      */     //   #371	-> 1286
/*      */     //   #375	-> 1305
/*      */     //   #377	-> 1320
/*      */     //   #381	-> 1348
/*      */     //   #382	-> 1353
/*      */     //   #383	-> 1358
/*      */     //   #385	-> 1367
/*      */     //   #388	-> 1379
/*      */     //   #390	-> 1382
/*      */     //   #392	-> 1388
/*      */     //   #393	-> 1398
/*      */     //   #394	-> 1409
/*      */     //   #396	-> 1412
/*      */     //   #397	-> 1416
/*      */     //   #398	-> 1421
/*      */     //   #401	-> 1431
/*      */     //   #402	-> 1444
/*      */     //   #404	-> 1459
/*      */     //   #407	-> 1465
/*      */     //   #410	-> 1476
/*      */     //   #411	-> 1482
/*      */     //   #415	-> 1496
/*      */     //   #416	-> 1500
/*      */     //   #417	-> 1505
/*      */     //   #419	-> 1515
/*      */     //   #420	-> 1535
/*      */     //   #422	-> 1541
/*      */     //   #424	-> 1552
/*      */     //   #425	-> 1555
/*      */     //   #426	-> 1562
/*      */     //   #427	-> 1568
/*      */     //   #429	-> 1576
/*      */     //   #432	-> 1583
/*      */     //   #433	-> 1593
/*      */     //   #435	-> 1600
/*      */     //   #436	-> 1606
/*      */     //   #437	-> 1617
/*      */     //   #439	-> 1623
/*      */     //   #442	-> 1634
/*      */     //   #444	-> 1641
/*      */     //   #445	-> 1651
/*      */     //   #446	-> 1656
/*      */     //   #447	-> 1668
/*      */     //   #450	-> 1678
/*      */     //   #452	-> 1685
/*      */     //   #453	-> 1690
/*      */     //   #454	-> 1702
/*      */     //   #457	-> 1712
/*      */     //   #458	-> 1719
/*      */     //   #459	-> 1729
/*      */     //   #460	-> 1741
/*      */     //   #463	-> 1751
/*      */     //   #464	-> 1758
/*      */     //   #465	-> 1765
/*      */     //   #467	-> 1772
/*      */     //   #468	-> 1777
/*      */     //   #469	-> 1782
/*      */     //   #470	-> 1794
/*      */     //   #474	-> 1804
/*      */     //   #475	-> 1816
/*      */     //   #477	-> 1830
/*      */     //   #481	-> 1841
/*      */     //   #482	-> 1854
/*      */     //   #483	-> 1861
/*      */     //   #484	-> 1866
/*      */     //   #486	-> 1872
/*      */     //   #488	-> 1879
/*      */     //   #489	-> 1891
/*      */     //   #491	-> 1901
/*      */     //   #494	-> 1908
/*      */     //   #495	-> 1913
/*      */     //   #499	-> 1924
/*      */     //   #501	-> 1934
/*      */     //   #503	-> 1949
/*      */     //   #505	-> 1958
/*      */     //   #506	-> 1969
/*      */     //   #507	-> 1977
/*      */     //   #509	-> 1987
/*      */     //   #510	-> 1999
/*      */     //   #511	-> 2002
/*      */     //   #513	-> 2009
/*      */     //   #514	-> 2020
/*      */     //   #515	-> 2023
/*      */     //   #516	-> 2030
/*      */     //   #517	-> 2036
/*      */     //   #519	-> 2044
/*      */     //   #523	-> 2051
/*      */     //   #524	-> 2065
/*      */     //   #525	-> 2072
/*      */     //   #527	-> 2079
/*      */     //   #528	-> 2089
/*      */     //   #529	-> 2096
/*      */     //   #531	-> 2104
/*      */     //   #532	-> 2110
/*      */     //   #534	-> 2117
/*      */     //   #536	-> 2130
/*      */     //   #537	-> 2143
/*      */     //   #538	-> 2150
/*      */     //   #543	-> 2157
/*      */     //   #544	-> 2167
/*      */     //   #547	-> 2174
/*      */     //   #548	-> 2187
/*      */     //   #550	-> 2194
/*      */     //   #551	-> 2197
/*      */     //   #552	-> 2200
/*      */     //   #553	-> 2214
/*      */     //   #554	-> 2223
/*      */     //   #555	-> 2229
/*      */     //   #556	-> 2239
/*      */     //   #557	-> 2260
/*      */     //   #559	-> 2267
/*      */     //   #560	-> 2277
/*      */     //   #561	-> 2284
/*      */     //   #562	-> 2290
/*      */     //   #563	-> 2295
/*      */     //   #566	-> 2302
/*      */     //   #567	-> 2308
/*      */     //   #568	-> 2314
/*      */     //   #569	-> 2321
/*      */     //   #570	-> 2336
/*      */     //   #573	-> 2342
/*      */     //   #575	-> 2350
/*      */     //   #576	-> 2354
/*      */     //   #577	-> 2357
/*      */     //   #578	-> 2362
/*      */     //   #580	-> 2369
/*      */     //   #581	-> 2384
/*      */     //   #583	-> 2388
/*      */     //   #584	-> 2395
/*      */     //   #585	-> 2418
/*      */     //   #586	-> 2430
/*      */     //   #588	-> 2440
/*      */     //   #589	-> 2447
/*      */     //   #590	-> 2450
/*      */     //   #591	-> 2455
/*      */     //   #594	-> 2460
/*      */     //   #595	-> 2463
/*      */     //   #596	-> 2470
/*      */     //   #598	-> 2477
/*      */     //   #601	-> 2480
/*      */     //   #602	-> 2495
/*      */     //   #605	-> 2501
/*      */     //   #609	-> 2513
/*      */     //   #612	-> 2527
/*      */     //   #613	-> 2532
/*      */     //   #614	-> 2539
/*      */     //   #616	-> 2554
/*      */     //   #618	-> 2558
/*      */     //   #619	-> 2577
/*      */     //   #620	-> 2587
/*      */     //   #621	-> 2590
/*      */     //   #623	-> 2593
/*      */     //   #624	-> 2596
/*      */     //   #625	-> 2602
/*      */     //   #626	-> 2607
/*      */     //   #628	-> 2613
/*      */     //   #629	-> 2621
/*      */     //   #630	-> 2634
/*      */     //   #631	-> 2641
/*      */     //   #632	-> 2651
/*      */     //   #634	-> 2658
/*      */     //   #635	-> 2661
/*      */     //   #637	-> 2664
/*      */     //   #638	-> 2671
/*      */     //   #639	-> 2682
/*      */     //   #640	-> 2687
/*      */     //   #645	-> 2700
/*      */     //   #642	-> 2703
/*      */     //   #643	-> 2705
/*      */     //   #644	-> 2717
/*      */     //   #646	-> 2727
/*      */     //   #647	-> 2733
/*      */     //   #648	-> 2742
/*      */     //   #650	-> 2752
/*      */     //   #653	-> 2761
/*      */     //   #654	-> 2773
/*      */     //   #655	-> 2778
/*      */     //   #657	-> 2790
/*      */     //   #661	-> 2805
/*      */     //   #662	-> 2835
/*      */     //   #663	-> 2859
/*      */     //   #664	-> 2866
/*      */     //   #665	-> 2878
/*      */     //   #666	-> 2885
/*      */     //   #667	-> 2893
/*      */     //   #668	-> 2902
/*      */     //   #670	-> 2905
/*      */     //   #671	-> 2917
/*      */     //   #672	-> 2923
/*      */     //   #676	-> 2936
/*      */     //   #677	-> 2951
/*      */     //   #678	-> 2960
/*      */     //   #682	-> 2970
/*      */     //   #683	-> 2983
/*      */     //   #684	-> 2992
/*      */     //   #685	-> 2999
/*      */     //   #686	-> 3004
/*      */     //   #688	-> 3010
/*      */     //   #692	-> 3017
/*      */     //   #693	-> 3042
/*      */     //   #694	-> 3049
/*      */     //   #695	-> 3056
/*      */     //   #698	-> 3066
/*      */     //   #699	-> 3069
/*      */     //   #700	-> 3081
/*      */     //   #704	-> 3091
/*      */     //   #705	-> 3102
/*      */     //   #706	-> 3117
/*      */     //   #710	-> 3132
/*      */     //   #711	-> 3138
/*      */     //   #712	-> 3143
/*      */     //   #714	-> 3147
/*      */     //   #717	-> 3152
/*      */     //   #718	-> 3160
/*      */     //   #721	-> 3172
/*      */     //   #722	-> 3181
/*      */     //   #723	-> 3184
/*      */     //   #724	-> 3189
/*      */     //   #726	-> 3208
/*      */     //   #728	-> 3226
/*      */     //   #729	-> 3234
/*      */     //   #730	-> 3249
/*      */     //   #731	-> 3258
/*      */     //   #733	-> 3269
/*      */     //   #736	-> 3278
/*      */     //   #737	-> 3290
/*      */     //   #738	-> 3295
/*      */     //   #748	-> 3318
/*      */     //   #742	-> 3321
/*      */     //   #743	-> 3322
/*      */     //   #744	-> 3335
/*      */     //   #745	-> 3337
/*      */     //   #746	-> 3338
/*      */     //   #747	-> 3351
/*      */     //   #749	-> 3360
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   308	113	24	skb	Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;
/*      */     //   329	92	25	originalKey	Ljava/security/Key;
/*      */     //   367	54	26	secret	[B
/*      */     //   382	39	27	dkt	Lcom/sun/xml/ws/security/DerivedKeyToken;
/*      */     //   399	22	28	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   459	373	25	cert	Ljava/security/cert/X509Certificate;
/*      */     //   517	315	26	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*      */     //   527	305	27	elem	Lorg/w3c/dom/Element;
/*      */     //   538	294	28	tokenElem	Ljavax/xml/soap/SOAPElement;
/*      */     //   549	283	29	tokId	Ljava/lang/String;
/*      */     //   609	223	30	ikb	Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*      */     //   616	216	31	iTokenType	Ljava/lang/String;
/*      */     //   679	153	32	includeToken	Z
/*      */     //   682	150	33	strElem	Lorg/w3c/dom/Element;
/*      */     //   743	89	34	imported	Lorg/w3c/dom/Element;
/*      */     //   771	61	35	str	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   850	71	25	dkt	Lcom/sun/xml/ws/security/DerivedKeyToken;
/*      */     //   867	54	26	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   871	50	27	secretKeyAlg	Ljava/lang/String;
/*      */     //   899	22	28	originalKey	Ljava/security/Key;
/*      */     //   443	478	24	prfKey	[B
/*      */     //   954	38	24	dkt	Lcom/sun/xml/ws/security/DerivedKeyToken;
/*      */     //   971	21	25	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   213	779	16	dtk	Lcom/sun/xml/wss/impl/policy/mls/DerivedTokenKeyBinding;
/*      */     //   220	772	17	originalKeyBinding	Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;
/*      */     //   223	769	18	algorithm	Ljava/lang/String;
/*      */     //   242	750	19	jceAlgo	Ljava/lang/String;
/*      */     //   245	747	20	offset	J
/*      */     //   253	739	22	length	J
/*      */     //   1036	373	18	cert	Ljava/security/cert/X509Certificate;
/*      */     //   1094	315	19	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*      */     //   1104	305	20	elem	Lorg/w3c/dom/Element;
/*      */     //   1115	294	21	tokenElem	Ljavax/xml/soap/SOAPElement;
/*      */     //   1126	283	22	tokId	Ljava/lang/String;
/*      */     //   1186	223	23	ikb	Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*      */     //   1193	216	24	iTokenType	Ljava/lang/String;
/*      */     //   1256	153	25	includeToken	Z
/*      */     //   1259	150	26	strElem	Lorg/w3c/dom/Element;
/*      */     //   1320	89	27	imported	Lorg/w3c/dom/Element;
/*      */     //   1348	61	28	str	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   1416	49	18	secretKeyAlg	Ljava/lang/String;
/*      */     //   1009	456	16	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   1020	445	17	proofKey	[B
/*      */     //   1482	59	16	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   1500	41	17	secretKeyAlg	Ljava/lang/String;
/*      */     //   1555	68	16	certInfo	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   1593	30	17	privKBinding	Lcom/sun/xml/wss/impl/policy/mls/PrivateKeyBinding;
/*      */     //   1606	17	18	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   1949	50	24	samlHeaderblock	Lcom/sun/xml/wss/core/SamlAssertionHeaderBlock;
/*      */     //   1969	30	25	strat	Lcom/sun/xml/wss/impl/keyinfo/KeyIdentifierStrategy;
/*      */     //   1641	368	16	samlBinding	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$SAMLAssertionBinding;
/*      */     //   1651	358	17	privKBinding	Lcom/sun/xml/wss/impl/policy/mls/PrivateKeyBinding;
/*      */     //   1719	290	18	referenceType	Ljava/lang/String;
/*      */     //   1758	251	19	assertionId	Ljava/lang/String;
/*      */     //   1765	244	20	_assertion	Lorg/w3c/dom/Element;
/*      */     //   1772	237	21	_authorityBinding	Lorg/w3c/dom/Element;
/*      */     //   1854	155	22	tokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   1861	148	23	strId	Ljava/lang/String;
/*      */     //   2079	17	17	symmetricKeyName	Ljava/lang/String;
/*      */     //   2110	64	17	ekSha1Ref	Ljava/lang/String;
/*      */     //   2130	44	18	secTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   2143	31	19	refElem	Lcom/sun/xml/wss/core/reference/EncryptedKeySHA1Identifier;
/*      */     //   2539	48	31	valueType	Ljava/lang/String;
/*      */     //   2705	22	36	e	Ljava/lang/Exception;
/*      */     //   2658	244	33	apacheKeyInfo	Lcom/sun/org/apache/xml/internal/security/keys/KeyInfo;
/*      */     //   2661	241	34	encryptedKey	Lcom/sun/org/apache/xml/internal/security/encryption/EncryptedKey;
/*      */     //   2664	238	35	keyEncryptor	Lcom/sun/org/apache/xml/internal/security/encryption/XMLCipher;
/*      */     //   2773	129	36	se	Ljavax/xml/soap/SOAPElement;
/*      */     //   2835	67	37	cipherData	Lorg/w3c/dom/Element;
/*      */     //   2859	43	38	cipherValue	Ljava/lang/String;
/*      */     //   2866	36	39	decodedCipher	[B
/*      */     //   2878	24	40	ekSha1	[B
/*      */     //   2885	17	41	encEkSha1	Ljava/lang/String;
/*      */     //   2197	869	17	x509Binding	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   2200	866	18	cert	Ljava/security/cert/X509Certificate;
/*      */     //   2308	758	19	tokenCache	Ljava/util/HashMap;
/*      */     //   2314	752	20	insertedX509Cache	Ljava/util/HashMap;
/*      */     //   2321	745	21	x509id	Ljava/lang/String;
/*      */     //   2354	712	22	keyEncAlgo	Ljava/lang/String;
/*      */     //   2357	709	23	tmp	Ljava/lang/String;
/*      */     //   2395	671	24	referenceType	Ljava/lang/String;
/*      */     //   2447	619	25	strategy	Lcom/sun/xml/wss/impl/keyinfo/KeyInfoStrategy;
/*      */     //   2450	616	26	keyInfoBlock	Lcom/sun/xml/wss/core/KeyInfoHeaderBlock;
/*      */     //   2463	603	27	token	Lcom/sun/xml/wss/core/X509SecurityToken;
/*      */     //   2477	589	28	x509TokenId	Ljava/lang/String;
/*      */     //   2480	586	29	tokenInserted	Z
/*      */     //   2527	539	30	insertedx509	Lcom/sun/xml/wss/core/X509SecurityToken;
/*      */     //   2596	470	31	id	Ljava/lang/String;
/*      */     //   2602	464	32	ekCache	Ljava/util/HashMap;
/*      */     //   2983	83	33	secTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   2992	74	34	reference	Lcom/sun/xml/wss/core/reference/DirectReference;
/*      */     //   2999	67	35	strId	Ljava/lang/String;
/*      */     //   2023	1043	16	skb	Lcom/sun/xml/wss/impl/policy/mls/SymmetricKeyBinding;
/*      */     //   8	3310	1	signaturePolicy	Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;
/*      */     //   13	3305	2	soapMessage	Ljavax/xml/soap/SOAPMessage;
/*      */     //   18	3300	3	secureMessage	Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*      */     //   27	3291	4	keyBinding	Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;
/*      */     //   71	3247	5	signingKey	Ljava/security/Key;
/*      */     //   74	3244	6	nextSibling	Lorg/w3c/dom/Node;
/*      */     //   79	3239	7	dsigHelper	Lcom/sun/xml/wss/impl/dsig/WSSPolicyConsumerImpl;
/*      */     //   82	3236	8	keyInfo	Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   88	3230	9	securityHeader	Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   97	3221	10	featureBinding	Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy$FeatureBinding;
/*      */     //   103	3215	11	algSuite	Lcom/sun/xml/wss/impl/AlgorithmSuite;
/*      */     //   116	3202	12	wss11Receiver	Z
/*      */     //   129	3189	13	wss11Sender	Z
/*      */     //   141	3177	14	wss10	Z
/*      */     //   165	3153	15	sendEKSHA1	Z
/*      */     //   3102	216	16	nodeList	Lorg/w3c/dom/NodeList;
/*      */     //   3138	180	17	refList	Lorg/w3c/dom/Node;
/*      */     //   3181	137	18	signedInfo	Ljavax/xml/crypto/dsig/SignedInfo;
/*      */     //   3184	134	19	signContext	Ljavax/xml/crypto/dsig/dom/DOMSignContext;
/*      */     //   3249	69	20	signature	Ljavax/xml/crypto/dsig/XMLSignature;
/*      */     //   3290	28	21	scList	Ljava/util/List;
/*      */     //   3322	15	1	xe	Lcom/sun/xml/wss/XWSSecurityException;
/*      */     //   3338	22	1	ex	Ljava/lang/Exception;
/*      */     //   0	3362	0	context	Lcom/sun/xml/wss/impl/FilterProcessingContext;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   0	3318	3321	com/sun/xml/wss/XWSSecurityException
/*      */     //   0	3318	3337	java/lang/Exception
/*      */     //   2664	2700	2703	java/lang/Exception
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
/*      */   public static int verify(FilterProcessingContext context) throws XWSSecurityException {
/*      */     try {
/*  761 */       WSSPolicyConsumerImpl dsigUtil = WSSPolicyConsumerImpl.getInstance();
/*  762 */       SOAPElement signElement = context.getSecurableSoapMessage().findSecurityHeader().getCurrentHeaderElement();
/*  763 */       if (signElement == null || signElement.getLocalName() == null || !"Signature".equals(signElement.getLocalName())) {
/*      */         
/*  765 */         String localName = (signElement != null) ? signElement.getLocalName() : "";
/*  766 */         context.setPVE((Throwable)new PolicyViolationException("Expected Signature Element as per receiver requirements, found  " + localName));
/*      */ 
/*      */         
/*  769 */         context.isPrimaryPolicyViolation(true);
/*  770 */         return 0;
/*      */       } 
/*  772 */       DOMValidateContext validationContext = new DOMValidateContext(KeySelectorImpl.getInstance(), signElement);
/*  773 */       XMLSignatureFactory signatureFactory = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/*      */       
/*  775 */       XMLSignature signature = signatureFactory.unmarshalXMLSignature(validationContext);
/*      */ 
/*      */       
/*  778 */       List<String> scList = (ArrayList)context.getExtraneousProperty("receivedSignValues");
/*  779 */       if (scList != null) {
/*  780 */         scList.add(Base64.encode(signature.getSignatureValue().getValue()));
/*      */       }
/*      */ 
/*      */       
/*  784 */       validationContext.setURIDereferencer(DSigResolver.getInstance());
/*      */       
/*  786 */       validationContext.put("http://wss.sun.com#processingContext", context);
/*  787 */       SignaturePolicy currentMessagePolicy = null;
/*  788 */       if (context.getMode() == 0 || context.getMode() == 1) {
/*      */         
/*  790 */         currentMessagePolicy = new SignaturePolicy();
/*  791 */         context.setInferredPolicy((WSSPolicy)currentMessagePolicy);
/*  792 */       } else if (context.getMode() == 3) {
/*  793 */         currentMessagePolicy = new SignaturePolicy();
/*  794 */         context.getInferredSecurityPolicy().append((SecurityPolicy)currentMessagePolicy);
/*      */       } 
/*      */ 
/*      */       
/*  798 */       boolean coreValidity = signature.validate(validationContext);
/*  799 */       SecurityPolicy securityPolicy = context.getSecurityPolicy();
/*      */       
/*  801 */       boolean isBSP = false;
/*  802 */       if (securityPolicy != null) {
/*  803 */         if (PolicyTypeUtil.messagePolicy(securityPolicy)) {
/*  804 */           isBSP = ((MessagePolicy)securityPolicy).isBSP();
/*      */         } else {
/*  806 */           isBSP = ((WSSPolicy)securityPolicy).isBSP();
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  812 */       if (!coreValidity) {
/*      */         
/*  814 */         if (logger.isLoggable(Level.FINEST)) {
/*  815 */           logger.log(Level.FINEST, "Signature failed core validation");
/*  816 */           boolean sv = signature.getSignatureValue().validate(validationContext);
/*  817 */           logger.log(Level.FINEST, "Signature validation status: " + sv);
/*      */           
/*  819 */           Iterator<Reference> i = signature.getSignedInfo().getReferences().iterator();
/*  820 */           for (int j = 0; i.hasNext(); j++) {
/*  821 */             Reference ref = i.next();
/*  822 */             logger.log(Level.FINEST, "Reference ID " + ref.getId());
/*  823 */             logger.log(Level.FINEST, "Reference URI " + ref.getURI());
/*  824 */             boolean refValid = ref.validate(validationContext);
/*      */             
/*  826 */             logger.log(Level.FINEST, "Reference[" + j + "] validity status: " + refValid);
/*      */           } 
/*      */         } 
/*  829 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1315_SIGNATURE_VERIFICATION_FAILED());
/*  830 */         XWSSecurityException xwsse = new XWSSecurityException("Signature verification failed");
/*  831 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, "Signature verification failed ", xwsse);
/*      */       } 
/*      */       
/*  834 */       if (logger.isLoggable(Level.FINEST)) {
/*  835 */         logger.log(Level.FINE, "Signature Passed Core Validation");
/*      */       }
/*  837 */       SignedInfo signInfo = signature.getSignedInfo();
/*  838 */       if (isBSP) {
/*  839 */         Iterator<Reference> i = signInfo.getReferences().iterator();
/*  840 */         for (int j = 0; i.hasNext(); j++) {
/*  841 */           Reference reference = i.next();
/*      */           
/*  843 */           Iterator<Transform> t = reference.getTransforms().iterator();
/*  844 */           for (int index = 0; t.hasNext(); index++) {
/*  845 */             Transform transform = t.next();
/*  846 */             if ("http://www.w3.org/2000/09/xmldsig#enveloped-signature".equals(transform.getAlgorithm())) {
/*  847 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1336_ILLEGAL_ENVELOPEDSIGNATURE());
/*  848 */               throw new XWSSecurityException("Enveloped signatures not permitted by BSP");
/*      */             } 
/*  850 */             if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(transform.getAlgorithm()))
/*      */             {
/*  852 */               if (transform.getParameterSpec() != null) {
/*  853 */                 ExcC14NParameterSpec spec = (ExcC14NParameterSpec)transform.getParameterSpec();
/*  854 */                 if (spec.getPrefixList().isEmpty())
/*  855 */                   logger.log(Level.SEVERE, LogStringsMessages.WSS_1337_INVALID_EMPTYPREFIXLIST()); 
/*  856 */                 throw new XWSSecurityException("Prefix List cannot be empty: violation of BSP 5407");
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*  862 */       if (context.getMode() == 1) {
/*      */         
/*  864 */         MessagePolicy policy = (MessagePolicy)context.getSecurityPolicy();
/*  865 */         dsigUtil.constructSignaturePolicy(signInfo, policy.isBSP(), currentMessagePolicy);
/*  866 */         policy.append((SecurityPolicy)currentMessagePolicy);
/*      */       } 
/*      */       
/*  869 */       if (context.getMode() == 0) {
/*      */ 
/*      */ 
/*      */         
/*  873 */         verifyRequirements(context, signature, validationContext);
/*  874 */         SignaturePolicy policy = (SignaturePolicy)context.getSecurityPolicy();
/*  875 */         dsigUtil.constructSignaturePolicy(signInfo, policy.isBSP(), currentMessagePolicy);
/*  876 */         SignaturePolicyVerifier spv = new SignaturePolicyVerifier(context);
/*  877 */         spv.verifyPolicy((SecurityPolicy)policy, (SecurityPolicy)currentMessagePolicy);
/*      */         
/*  879 */         if (logger.isLoggable(Level.FINEST)) {
/*  880 */           logger.log(Level.FINE, "Reciever Requirements  are met");
/*      */         }
/*      */       } 
/*      */       
/*  884 */       if (context.getMode() == 3) {
/*  885 */         dsigUtil.constructSignaturePolicy(signInfo, currentMessagePolicy, context.getSecurableSoapMessage());
/*      */       }
/*      */     }
/*  888 */     catch (XWSSecurityException xwe) {
/*  889 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY());
/*  890 */       throw xwe;
/*  891 */     } catch (XMLSignatureException xse) {
/*      */       
/*  893 */       Throwable t1 = xse.getCause();
/*      */       
/*  895 */       if (t1 == null) {
/*  896 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY());
/*  897 */         throw new XWSSecurityException(xse);
/*      */       } 
/*  899 */       if (t1 instanceof javax.xml.crypto.KeySelectorException || t1 instanceof javax.xml.crypto.URIReferenceException) {
/*      */         
/*  901 */         Throwable t2 = t1.getCause();
/*      */         
/*  903 */         if (t2 != null && t2 instanceof WssSoapFaultException) {
/*  904 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY());
/*  905 */           throw (WssSoapFaultException)t2;
/*      */         } 
/*  907 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY());
/*  908 */         throw new XWSSecurityException((Exception)t1);
/*      */       } 
/*      */       
/*  911 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY());
/*  912 */       throw new XWSSecurityException(xse);
/*      */     }
/*  914 */     catch (Exception ex) {
/*  915 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY());
/*  916 */       throw new XWSSecurityException(ex);
/*      */     } finally {
/*  918 */       context.setInferredPolicy(null);
/*      */     } 
/*  920 */     return 0;
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
/*      */   public static void verifyRequirements(FilterProcessingContext context, XMLSignature signature, DOMValidateContext validationContext) throws Exception {
/*  934 */     SignaturePolicy policy = (SignaturePolicy)context.getSecurityPolicy();
/*  935 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)policy.getFeatureBinding();
/*  936 */     WSSPolicyConsumerImpl dsigUtil = WSSPolicyConsumerImpl.getInstance();
/*  937 */     ArrayList targets = featureBinding.getTargetBindings();
/*  938 */     if (targets == null || targets.size() == 0) {
/*      */       return;
/*      */     }
/*  941 */     SignedInfo signedInfo = signature.getSignedInfo();
/*  942 */     List signedReferences = signedInfo.getReferences();
/*  943 */     Iterator<Reference> sr = signedReferences.listIterator();
/*  944 */     ArrayList<DataWrapper> signedDataList = new ArrayList();
/*  945 */     ArrayList<Reference> signedReferenceList = new ArrayList();
/*  946 */     while (sr.hasNext()) {
/*  947 */       Reference reference = sr.next();
/*  948 */       Data tmpObj = getData(reference, validationContext);
/*  949 */       signedDataList.add(new DataWrapper(tmpObj));
/*      */ 
/*      */       
/*  952 */       signedReferenceList.add(reference);
/*      */     } 
/*      */     
/*  955 */     ArrayList<SignatureTarget> optionalReqList = new ArrayList();
/*  956 */     ArrayList<DataWrapper> requiredDataList = new ArrayList();
/*  957 */     ArrayList<Reference> requiredReferenceList = new ArrayList();
/*  958 */     ArrayList<DataWrapper> optionalDataList = new ArrayList();
/*  959 */     ArrayList<Reference> optionalReferenceList = new ArrayList();
/*      */ 
/*      */ 
/*      */     
/*  963 */     Iterator<SignatureTarget> targetItr = targets.iterator();
/*  964 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*  965 */     while (targetItr.hasNext()) {
/*  966 */       SignatureTarget signatureTarget = targetItr.next();
/*  967 */       boolean requiredTarget = signatureTarget.getEnforce();
/*  968 */       List<Reference> list = null;
/*      */       try {
/*  970 */         if (requiredTarget) {
/*  971 */           list = dsigUtil.generateReferenceList(Collections.singletonList(signatureTarget), secureMessage, context, true, featureBinding.isEndorsingSignature());
/*      */         } else {
/*      */           
/*  974 */           optionalReqList.add(signatureTarget);
/*      */         } 
/*  976 */       } catch (Exception ex) {
/*  977 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1302_REFLIST_ERROR(), ex);
/*  978 */         if (requiredTarget) {
/*  979 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1339_INVALID_RECEIVER_REQUIREMENTS());
/*  980 */           throw new XWSSecurityException("Receiver requirement for SignatureTarget " + signatureTarget.getValue() + " is not met");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  985 */       if (!requiredTarget) {
/*      */         continue;
/*      */       }
/*  988 */       if (list.size() <= 0) {
/*  989 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1339_INVALID_RECEIVER_REQUIREMENTS());
/*  990 */         throw new XWSSecurityException("Receiver requirement for SignatureTarget " + signatureTarget.getValue() + " is not met");
/*      */       } 
/*      */       
/*  993 */       boolean allRef = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  998 */       for (int k = 0; k < list.size(); k++) {
/*  999 */         Reference reference = list.get(k);
/* 1000 */         Data data = null;
/*      */         try {
/* 1002 */           data = getData(reference, validationContext);
/* 1003 */           if (requiredTarget && data != null) {
/* 1004 */             DataWrapper tmpObj = new DataWrapper(data);
/* 1005 */             tmpObj.setTarget(signatureTarget);
/*      */             
/* 1007 */             requiredDataList.add(tmpObj);
/* 1008 */             requiredReferenceList.add(reference);
/*      */           } 
/* 1010 */         } catch (Exception ex) {
/* 1011 */           if (requiredTarget) {
/* 1012 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1339_INVALID_RECEIVER_REQUIREMENTS());
/* 1013 */             throw new XWSSecurityException("Receiver requirement for SignatureTarget " + signatureTarget.getValue() + " is not met");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1023 */     if (optionalReqList.size() == 0 && requiredReferenceList.size() != signedReferenceList.size()) {
/* 1024 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1340_ILLEGAL_UNMATCHED_NOOF_TARGETS());
/* 1025 */       throw new XWSSecurityException("Number of Targets in the message dont match number of Targets in receiver requirements");
/*      */     } 
/*      */ 
/*      */     
/* 1029 */     if (requiredDataList.size() == 0) {
/* 1030 */       if (logger.isLoggable(Level.FINER)) {
/* 1031 */         logger.log(Level.FINER, "No mandatory receiver requirements were provided");
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/* 1036 */     for (int i = 0; i < requiredDataList.size(); i++) {
/* 1037 */       DataWrapper rData = requiredDataList.get(i);
/* 1038 */       boolean found = false;
/* 1039 */       for (int k = 0; k < signedDataList.size(); k++) {
/* 1040 */         DataWrapper sData = null;
/* 1041 */         sData = signedDataList.get(k);
/* 1042 */         if (isEqual(rData, sData, requiredReferenceList.get(i), signedReferenceList.get(k))) {
/* 1043 */           signedDataList.remove(k);
/* 1044 */           signedReferenceList.remove(k);
/* 1045 */           found = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1049 */       if (!found) {
/*      */         
/* 1051 */         String uri = rData.getTarget().getValue();
/* 1052 */         String type = rData.getTarget().getType();
/* 1053 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1341_ILLEGAL_UNMATCHED_TYPE_URI());
/* 1054 */         throw new XWSSecurityException("Receiver requirement for SignatureTarget having " + type + " type and value " + uri + " is not met");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1059 */     if (signedDataList.size() == 0) {
/* 1060 */       if (logger.isLoggable(Level.FINEST)) {
/* 1061 */         logger.log(Level.FINEST, "All receiver requirements are met");
/*      */       }
/*      */       return;
/*      */     } 
/* 1065 */     List<Reference> referenceList = null;
/*      */     
/*      */     int j;
/* 1068 */     for (j = 0; j < optionalReqList.size(); j++) {
/* 1069 */       SignatureTarget signatureTarget = optionalReqList.get(j);
/*      */       try {
/* 1071 */         referenceList = null;
/* 1072 */         referenceList = dsigUtil.generateReferenceList(Collections.singletonList(signatureTarget), secureMessage, context, true, featureBinding.isEndorsingSignature());
/* 1073 */       } catch (Exception ex) {
/* 1074 */         if (logger.isLoggable(Level.FINE)) {
/* 1075 */           logger.log(Level.FINE, "Optional Target not found in the message ", ex);
/*      */         }
/*      */       } 
/* 1078 */       if (referenceList != null && referenceList.size() > 0) {
/*      */ 
/*      */         
/* 1081 */         Reference reference = referenceList.get(0);
/* 1082 */         Data data = null;
/*      */         try {
/* 1084 */           data = getData(reference, validationContext);
/* 1085 */         } catch (Exception ex) {}
/*      */ 
/*      */         
/* 1088 */         if (data != null) {
/* 1089 */           DataWrapper tmpObj = new DataWrapper(data);
/* 1090 */           tmpObj.setTarget(signatureTarget);
/* 1091 */           optionalDataList.add(tmpObj);
/* 1092 */           optionalReferenceList.add(reference);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1096 */     for (j = 0; j < signedDataList.size(); j++) {
/* 1097 */       DataWrapper sData = signedDataList.get(j);
/* 1098 */       DataWrapper oData = null;
/* 1099 */       boolean found = false;
/*      */       
/* 1101 */       for (int k = 0; k < optionalDataList.size(); k++) {
/* 1102 */         oData = optionalDataList.get(k);
/*      */         
/* 1104 */         if (isEqual(oData, sData, optionalReferenceList.get(k), signedReferenceList.get(j))) {
/* 1105 */           optionalDataList.remove(k);
/* 1106 */           optionalReferenceList.remove(k);
/* 1107 */           found = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1112 */       if (!found) {
/* 1113 */         Reference st = signedReferenceList.get(j);
/* 1114 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1341_ILLEGAL_UNMATCHED_TYPE_URI());
/* 1115 */         throw new XWSSecurityException("SignatureTarget in the message with URI " + st.getURI() + " has not met receiver requirements");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1120 */     if (logger.isLoggable(Level.FINEST)) {
/* 1121 */       logger.log(Level.FINEST, "All receiver requirements are met");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isEqual(DataWrapper data1, DataWrapper data2, Reference ref1, Reference ref2) throws XWSSecurityException {
/* 1127 */     if (data1.isNodesetData() && data2.isNodesetData()) {
/* 1128 */       NodeSetData ns1 = (NodeSetData)data1.getData();
/* 1129 */       NodeSetData ns2 = (NodeSetData)data2.getData();
/*      */       
/* 1131 */       Node nsd1Root = null;
/* 1132 */       Node nsd2Root = null;
/*      */       
/* 1134 */       if (ns1 instanceof DOMSubTreeData) {
/* 1135 */         nsd1Root = ((DOMSubTreeData)ns1).getRoot();
/*      */       }
/*      */       
/* 1138 */       if (ns2 instanceof DOMSubTreeData) {
/* 1139 */         nsd2Root = ((DOMSubTreeData)ns2).getRoot();
/*      */       }
/*      */       
/* 1142 */       if (nsd1Root != null && nsd2Root != null && (
/* 1143 */         nsd1Root.isSameNode(nsd2Root) || nsd1Root.isEqualNode(nsd2Root))) {
/* 1144 */         return true;
/*      */       }
/*      */       
/* 1147 */       return false;
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
/* 1175 */     if (data1.isOctectData() && data2.isOctectData()) {
/* 1176 */       OctetStreamData osd1 = (OctetStreamData)data1.getData();
/* 1177 */       OctetStreamData osd2 = (OctetStreamData)data2.getData();
/* 1178 */       InputStream stream1 = osd1.getOctetStream();
/* 1179 */       InputStream stream2 = osd2.getOctetStream();
/* 1180 */       byte[] b1 = new byte[128];
/* 1181 */       byte[] b2 = new byte[128];
/*      */       while (true) {
/* 1183 */         int len1 = 0;
/* 1184 */         int len2 = 0;
/*      */         try {
/* 1186 */           len1 = stream1.read(b1);
/* 1187 */           len2 = stream2.read(b2);
/* 1188 */         } catch (IOException ioEx) {
/* 1189 */           if (logger.isLoggable(Level.FINEST)) {
/* 1190 */             logger.log(Level.FINEST, "Error occurred whilecomparing OctetStreamData objects " + ioEx.getMessage());
/*      */           }
/*      */           
/* 1193 */           return false;
/*      */         } 
/* 1195 */         if (len1 == -1 && len2 == -1) {
/*      */           break;
/*      */         }
/* 1198 */         if (len1 != len2) {
/* 1199 */           return false;
/*      */         }
/* 1201 */         for (int i = 0; i < len1; i++) {
/* 1202 */           if (b1[i] != b2[i]) {
/* 1203 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/* 1207 */       return true;
/* 1208 */     }  if (data1.isAttachmentData() && data2.isAttachmentData()) {
/* 1209 */       AttachmentData ad1 = (AttachmentData)data1.getData();
/* 1210 */       AttachmentData ad2 = (AttachmentData)data2.getData();
/* 1211 */       String uriOne = ad1.getAttachmentPart().getContentId();
/* 1212 */       String uriTwo = ad2.getAttachmentPart().getContentId();
/* 1213 */       if (uriOne != null && uriOne.equals(uriTwo)) {
/* 1214 */         return isTransformsEqual(ref1, ref2);
/*      */       }
/* 1216 */       return false;
/*      */     } 
/*      */     
/* 1219 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isTransformsEqual(Reference ref1, Reference ref2) throws XWSSecurityException {
/* 1223 */     List<Transform> tList1 = ref1.getTransforms();
/*      */     
/* 1225 */     List<Transform> tList2 = ref2.getTransforms();
/* 1226 */     if (tList1.size() != tList2.size()) {
/* 1227 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1342_ILLEGAL_UNMATCHED_TRANSFORMS());
/* 1228 */       throw new XWSSecurityException("Receiver Requirements for the transforms are not met");
/*      */     } 
/*      */     
/* 1231 */     int i = 0;
/* 1232 */     while (i < tList1.size()) {
/* 1233 */       Transform tr1 = tList1.get(i);
/* 1234 */       Transform tr2 = tList2.get(i);
/*      */       
/* 1236 */       String alg1 = tr1.getAlgorithm();
/* 1237 */       String alg2 = tr2.getAlgorithm();
/* 1238 */       i++;
/* 1239 */       if (alg1 == alg2 || (alg1 != null && alg1.equals(alg2))) {
/*      */         continue;
/*      */       }
/* 1242 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1342_ILLEGAL_UNMATCHED_TRANSFORMS());
/* 1243 */       throw new XWSSecurityException("Receiver Requirements for the transforms are not met");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1249 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Data getData(Reference reference, DOMValidateContext context) throws Exception {
/* 1254 */     final String uri = reference.getURI();
/* 1255 */     URIReference uriRef = new URIReference() {
/*      */         public String getURI() {
/* 1257 */           return uri;
/*      */         }
/*      */         
/*      */         public String getType() {
/* 1261 */           return null;
/*      */         }
/*      */       };
/* 1264 */     Data inputData = DSigResolver.getInstance().dereference(uriRef, context);
/* 1265 */     if (inputData instanceof AttachmentData) {
/* 1266 */       return inputData;
/*      */     }
/* 1268 */     List transformList = reference.getTransforms();
/* 1269 */     Iterator<Transform> itr = transformList.iterator();
/* 1270 */     while (itr.hasNext()) {
/* 1271 */       Transform transform = itr.next();
/* 1272 */       inputData = getData(transform, inputData, context);
/*      */     } 
/* 1274 */     return inputData;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Data getData(Transform transform, Data inputData, DOMValidateContext context) throws Exception {
/* 1279 */     String transformAlgo = transform.getAlgorithm();
/* 1280 */     if (transformAlgo == "http://www.w3.org/TR/1999/REC-xpath-19991116" || transformAlgo == "http://www.w3.org/2002/06/xmldsig-filter2" || transformAlgo == "http://www.w3.org/TR/1999/REC-xslt-19991116") {
/* 1281 */       TransformService transformImpl = TransformService.getInstance(transformAlgo, "DOM");
/* 1282 */       TransformParameterSpec transformParams = null;
/*      */       
/* 1284 */       transformParams = (TransformParameterSpec)transform.getParameterSpec();
/* 1285 */       transformImpl.init(transformParams);
/* 1286 */       return transformImpl.transform(inputData, context);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1291 */     return inputData;
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
/*      */   public static boolean verifySignature(Element signElement, FilterProcessingContext context) throws XWSSecurityException {
/*      */     try {
/* 1306 */       DOMValidateContext validationContext = new DOMValidateContext(KeySelectorImpl.getInstance(), signElement);
/*      */       
/* 1308 */       XMLSignatureFactory signatureFactory = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/*      */       
/* 1310 */       XMLSignature signature = signatureFactory.unmarshalXMLSignature(validationContext);
/* 1311 */       validationContext.setURIDereferencer(DSigResolver.getInstance());
/*      */       
/* 1313 */       validationContext.put("http://wss.sun.com#processingContext", context);
/* 1314 */       boolean coreValidity = signature.validate(validationContext);
/* 1315 */       if (!coreValidity)
/*      */       {
/* 1317 */         if (logger.isLoggable(Level.FINEST)) {
/* 1318 */           logger.log(Level.FINEST, "Signature failed core validation");
/* 1319 */           boolean sv = signature.getSignatureValue().validate(validationContext);
/* 1320 */           logger.log(Level.FINEST, "Signature validation status: " + sv);
/*      */           
/* 1322 */           Iterator<Reference> i = signature.getSignedInfo().getReferences().iterator();
/* 1323 */           for (int j = 0; i.hasNext(); j++) {
/* 1324 */             Reference ref = i.next();
/* 1325 */             logger.log(Level.FINEST, "Reference ID " + ref.getId());
/* 1326 */             logger.log(Level.FINEST, "Reference URI " + ref.getURI());
/* 1327 */             boolean refValid = ref.validate(validationContext);
/*      */             
/* 1329 */             logger.log(Level.FINEST, "Reference[" + j + "] validity status: " + refValid);
/*      */           } 
/*      */         } 
/*      */       }
/* 1333 */       return coreValidity;
/*      */     }
/* 1335 */     catch (Exception e) {
/* 1336 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1338_ERROR_VERIFY() + e.getMessage());
/* 1337 */       throw new XWSSecurityException(e);
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
/*      */   private static KeyInfo prepareForSymmetricKeySignature(FilterProcessingContext context, WSSPolicy keyBinding, Key originalKey, SignaturePolicy signaturePolicy, Node[] nxtSiblingContainer, AuthenticationTokenPolicy.X509CertificateBinding certInfo, DerivedKeyToken dkt) throws XWSSecurityException {
/*      */     // Byte code:
/*      */     //   0: aconst_null
/*      */     //   1: astore #7
/*      */     //   3: aconst_null
/*      */     //   4: astore #8
/*      */     //   6: ldc 'http://www.w3.org/2001/04/xmlenc#rsa-1_5'
/*      */     //   8: astore #9
/*      */     //   10: aload_0
/*      */     //   11: invokevirtual getAlgorithmSuite : ()Lcom/sun/xml/wss/impl/AlgorithmSuite;
/*      */     //   14: ifnull -> 26
/*      */     //   17: aload_0
/*      */     //   18: invokevirtual getAlgorithmSuite : ()Lcom/sun/xml/wss/impl/AlgorithmSuite;
/*      */     //   21: invokevirtual getAsymmetricKeyAlgorithm : ()Ljava/lang/String;
/*      */     //   24: astore #9
/*      */     //   26: aconst_null
/*      */     //   27: astore #10
/*      */     //   29: aconst_null
/*      */     //   30: astore #11
/*      */     //   32: aconst_null
/*      */     //   33: astore #12
/*      */     //   35: aload_0
/*      */     //   36: invokevirtual getSecurableSoapMessage : ()Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*      */     //   39: astore #13
/*      */     //   41: aload #13
/*      */     //   43: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   46: astore #14
/*      */     //   48: invokestatic getInstance : ()Lcom/sun/xml/wss/impl/dsig/WSSPolicyConsumerImpl;
/*      */     //   51: astore #15
/*      */     //   53: ldc 'true'
/*      */     //   55: aload_0
/*      */     //   56: ldc 'EnableWSS11PolicyReceiver'
/*      */     //   58: invokevirtual getExtraneousProperty : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   61: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   64: istore #16
/*      */     //   66: ldc 'true'
/*      */     //   68: aload_0
/*      */     //   69: ldc 'EnableWSS11PolicySender'
/*      */     //   71: invokevirtual getExtraneousProperty : (Ljava/lang/String;)Ljava/lang/Object;
/*      */     //   74: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   77: istore #17
/*      */     //   79: iload #16
/*      */     //   81: ifeq -> 100
/*      */     //   84: iload #17
/*      */     //   86: ifeq -> 100
/*      */     //   89: aload_0
/*      */     //   90: invokestatic getEKSHA1Ref : (Lcom/sun/xml/wss/impl/FilterProcessingContext;)Ljava/lang/String;
/*      */     //   93: ifnull -> 100
/*      */     //   96: iconst_1
/*      */     //   97: goto -> 101
/*      */     //   100: iconst_0
/*      */     //   101: istore #18
/*      */     //   103: iload #17
/*      */     //   105: ifne -> 112
/*      */     //   108: iconst_1
/*      */     //   109: goto -> 113
/*      */     //   112: iconst_0
/*      */     //   113: istore #19
/*      */     //   115: aload_1
/*      */     //   116: invokestatic derivedTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   119: ifeq -> 2228
/*      */     //   122: aload_1
/*      */     //   123: invokevirtual clone : ()Ljava/lang/Object;
/*      */     //   126: checkcast com/sun/xml/wss/impl/policy/mls/DerivedTokenKeyBinding
/*      */     //   129: astore #20
/*      */     //   131: aload #20
/*      */     //   133: invokevirtual getOriginalKeyBinding : ()Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;
/*      */     //   136: astore #21
/*      */     //   138: aload #21
/*      */     //   140: invokestatic x509CertificateBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   143: ifeq -> 168
/*      */     //   146: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   149: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   152: invokestatic WSS_1327_UNSUPPORTED_ASYMMETRICBINDING_DERIVEDKEY_X_509_TOKEN : ()Ljava/lang/String;
/*      */     //   155: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   158: new com/sun/xml/wss/XWSSecurityException
/*      */     //   161: dup
/*      */     //   162: ldc 'Asymmetric Binding with DerivedKeys under X509Token Policy Not Yet Supported'
/*      */     //   164: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   167: athrow
/*      */     //   168: aload #21
/*      */     //   170: invokestatic symmetricKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   173: ifeq -> 1295
/*      */     //   176: iload #18
/*      */     //   178: ifeq -> 389
/*      */     //   181: aload_0
/*      */     //   182: invokestatic getEKSHA1Ref : (Lcom/sun/xml/wss/impl/FilterProcessingContext;)Ljava/lang/String;
/*      */     //   185: astore #22
/*      */     //   187: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   190: dup
/*      */     //   191: aload #13
/*      */     //   193: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   196: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   199: astore #23
/*      */     //   201: new com/sun/xml/wss/core/reference/EncryptedKeySHA1Identifier
/*      */     //   204: dup
/*      */     //   205: aload #13
/*      */     //   207: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   210: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   213: astore #24
/*      */     //   215: aload #24
/*      */     //   217: aload #22
/*      */     //   219: invokevirtual setReferenceValue : (Ljava/lang/String;)V
/*      */     //   222: aload #23
/*      */     //   224: aload #24
/*      */     //   226: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   229: aload_1
/*      */     //   230: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   233: astore #25
/*      */     //   235: aload #25
/*      */     //   237: ifnonnull -> 247
/*      */     //   240: aload #13
/*      */     //   242: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   245: astore #25
/*      */     //   247: aload #6
/*      */     //   249: invokeinterface getNonce : ()[B
/*      */     //   254: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   257: astore #26
/*      */     //   259: new com/sun/xml/wss/core/DerivedKeyTokenHeaderBlock
/*      */     //   262: dup
/*      */     //   263: aload #14
/*      */     //   265: invokevirtual getOwnerDocument : ()Lorg/w3c/dom/Document;
/*      */     //   268: aload #23
/*      */     //   270: aload #26
/*      */     //   272: aload #6
/*      */     //   274: invokeinterface getOffset : ()J
/*      */     //   279: aload #6
/*      */     //   281: invokeinterface getLength : ()J
/*      */     //   286: aload #25
/*      */     //   288: invokespecial <init> : (Lorg/w3c/dom/Document;Lcom/sun/xml/wss/core/SecurityTokenReference;Ljava/lang/String;JJLjava/lang/String;)V
/*      */     //   291: astore #27
/*      */     //   293: aload #13
/*      */     //   295: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   298: aload #27
/*      */     //   300: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*      */     //   303: aload #27
/*      */     //   305: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   308: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   313: astore #7
/*      */     //   315: aload #4
/*      */     //   317: iconst_0
/*      */     //   318: aload #7
/*      */     //   320: aastore
/*      */     //   321: new com/sun/xml/wss/core/reference/DirectReference
/*      */     //   324: dup
/*      */     //   325: invokespecial <init> : ()V
/*      */     //   328: astore #28
/*      */     //   330: aload #28
/*      */     //   332: new java/lang/StringBuilder
/*      */     //   335: dup
/*      */     //   336: invokespecial <init> : ()V
/*      */     //   339: ldc '#'
/*      */     //   341: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   344: aload #25
/*      */     //   346: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   349: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   352: invokevirtual setURI : (Ljava/lang/String;)V
/*      */     //   355: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   358: dup
/*      */     //   359: aload #13
/*      */     //   361: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   364: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   367: astore #29
/*      */     //   369: aload #29
/*      */     //   371: aload #28
/*      */     //   373: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   376: aload #15
/*      */     //   378: aload_3
/*      */     //   379: aload #29
/*      */     //   381: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   384: astore #8
/*      */     //   386: aload #8
/*      */     //   388: areturn
/*      */     //   389: iload #17
/*      */     //   391: ifne -> 399
/*      */     //   394: iload #19
/*      */     //   396: ifeq -> 2225
/*      */     //   399: aconst_null
/*      */     //   400: astore #22
/*      */     //   402: aconst_null
/*      */     //   403: astore #23
/*      */     //   405: aload_0
/*      */     //   406: invokevirtual getX509CertificateBinding : ()Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   409: ifnull -> 423
/*      */     //   412: aload_0
/*      */     //   413: invokevirtual getX509CertificateBinding : ()Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   416: astore #22
/*      */     //   418: aload_0
/*      */     //   419: aconst_null
/*      */     //   420: invokevirtual setX509CertificateBinding : (Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;)V
/*      */     //   423: aload_0
/*      */     //   424: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*      */     //   427: astore #24
/*      */     //   429: aload_0
/*      */     //   430: invokevirtual getInsertedX509Cache : ()Ljava/util/HashMap;
/*      */     //   433: astore #25
/*      */     //   435: aload #22
/*      */     //   437: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   440: astore #26
/*      */     //   442: aload #26
/*      */     //   444: ifnull -> 457
/*      */     //   447: aload #26
/*      */     //   449: ldc ''
/*      */     //   451: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   454: ifeq -> 464
/*      */     //   457: aload #13
/*      */     //   459: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   462: astore #26
/*      */     //   464: aload_0
/*      */     //   465: aload #22
/*      */     //   467: aload #26
/*      */     //   469: invokestatic checkIncludeTokenPolicy : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;Ljava/lang/String;)V
/*      */     //   472: aload #22
/*      */     //   474: invokevirtual getReferenceType : ()Ljava/lang/String;
/*      */     //   477: astore #10
/*      */     //   479: aload #10
/*      */     //   481: invokestatic getInstance : (Ljava/lang/String;)Lcom/sun/xml/wss/impl/keyinfo/KeyInfoStrategy;
/*      */     //   484: astore #11
/*      */     //   486: aconst_null
/*      */     //   487: astore #27
/*      */     //   489: aload #22
/*      */     //   491: invokevirtual getX509Certificate : ()Ljava/security/cert/X509Certificate;
/*      */     //   494: astore #23
/*      */     //   496: aload #22
/*      */     //   498: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   501: astore #28
/*      */     //   503: iconst_0
/*      */     //   504: istore #29
/*      */     //   506: aload #28
/*      */     //   508: ifnull -> 521
/*      */     //   511: aload #28
/*      */     //   513: ldc ''
/*      */     //   515: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   518: ifeq -> 528
/*      */     //   521: aload #13
/*      */     //   523: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   526: astore #28
/*      */     //   528: aload_0
/*      */     //   529: invokevirtual getInsertedX509Cache : ()Ljava/util/HashMap;
/*      */     //   532: aload #28
/*      */     //   534: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   537: checkcast com/sun/xml/wss/core/X509SecurityToken
/*      */     //   540: astore #30
/*      */     //   542: aload #24
/*      */     //   544: aload #28
/*      */     //   546: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   549: checkcast com/sun/xml/wss/core/X509SecurityToken
/*      */     //   552: astore #27
/*      */     //   554: aload #27
/*      */     //   556: ifnonnull -> 645
/*      */     //   559: aload #30
/*      */     //   561: ifnull -> 581
/*      */     //   564: aload #30
/*      */     //   566: astore #27
/*      */     //   568: aload #24
/*      */     //   570: aload #28
/*      */     //   572: aload #30
/*      */     //   574: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   577: pop
/*      */     //   578: goto -> 637
/*      */     //   581: aload #22
/*      */     //   583: invokevirtual getValueType : ()Ljava/lang/String;
/*      */     //   586: astore #31
/*      */     //   588: aload #31
/*      */     //   590: ifnull -> 603
/*      */     //   593: aload #31
/*      */     //   595: ldc ''
/*      */     //   597: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   600: ifeq -> 607
/*      */     //   603: ldc 'http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3'
/*      */     //   605: astore #31
/*      */     //   607: new com/sun/xml/wss/core/X509SecurityToken
/*      */     //   610: dup
/*      */     //   611: aload #13
/*      */     //   613: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   616: aload #23
/*      */     //   618: aload #28
/*      */     //   620: aload #31
/*      */     //   622: invokespecial <init> : (Lorg/w3c/dom/Document;Ljava/security/cert/X509Certificate;Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   625: astore #27
/*      */     //   627: aload #24
/*      */     //   629: aload #28
/*      */     //   631: aload #27
/*      */     //   633: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   636: pop
/*      */     //   637: aload_0
/*      */     //   638: aload_2
/*      */     //   639: invokevirtual setCurrentSecret : (Ljava/security/Key;)V
/*      */     //   642: goto -> 648
/*      */     //   645: iconst_1
/*      */     //   646: istore #29
/*      */     //   648: aload_1
/*      */     //   649: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   652: astore #31
/*      */     //   654: aload #31
/*      */     //   656: ifnonnull -> 666
/*      */     //   659: aload #13
/*      */     //   661: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   664: astore #31
/*      */     //   666: aload #6
/*      */     //   668: invokeinterface getNonce : ()[B
/*      */     //   673: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   676: astore #32
/*      */     //   678: aload_0
/*      */     //   679: invokevirtual getEncryptedKeyCache : ()Ljava/util/HashMap;
/*      */     //   682: astore #33
/*      */     //   684: aload #33
/*      */     //   686: aload #28
/*      */     //   688: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   691: checkcast java/lang/String
/*      */     //   694: astore #34
/*      */     //   696: aconst_null
/*      */     //   697: astore #35
/*      */     //   699: aconst_null
/*      */     //   700: astore #36
/*      */     //   702: iload #29
/*      */     //   704: ifne -> 851
/*      */     //   707: aload_0
/*      */     //   708: ldc 'SecretKey'
/*      */     //   710: aload_2
/*      */     //   711: invokevirtual setExtraneousProperty : (Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   714: new com/sun/xml/wss/core/KeyInfoHeaderBlock
/*      */     //   717: dup
/*      */     //   718: aload #13
/*      */     //   720: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   723: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   726: astore #12
/*      */     //   728: aload #11
/*      */     //   730: aload #23
/*      */     //   732: invokevirtual setCertificate : (Ljava/security/cert/X509Certificate;)V
/*      */     //   735: aload #11
/*      */     //   737: aload #12
/*      */     //   739: aload #13
/*      */     //   741: aload #28
/*      */     //   743: invokevirtual insertKey : (Lcom/sun/xml/wss/core/KeyInfoHeaderBlock;Lcom/sun/xml/wss/impl/SecurableSoapMessage;Ljava/lang/String;)V
/*      */     //   746: aload #12
/*      */     //   748: invokevirtual getKeyInfo : ()Lcom/sun/org/apache/xml/internal/security/keys/KeyInfo;
/*      */     //   751: astore #37
/*      */     //   753: aload #9
/*      */     //   755: invokestatic getInstance : (Ljava/lang/String;)Lcom/sun/org/apache/xml/internal/security/encryption/XMLCipher;
/*      */     //   758: astore #36
/*      */     //   760: aload #36
/*      */     //   762: iconst_3
/*      */     //   763: aload #23
/*      */     //   765: invokevirtual getPublicKey : ()Ljava/security/PublicKey;
/*      */     //   768: invokevirtual init : (ILjava/security/Key;)V
/*      */     //   771: aload #36
/*      */     //   773: ifnull -> 789
/*      */     //   776: aload #36
/*      */     //   778: aload #13
/*      */     //   780: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   783: aload_2
/*      */     //   784: invokevirtual encryptKey : (Lorg/w3c/dom/Document;Ljava/security/Key;)Lcom/sun/org/apache/xml/internal/security/encryption/EncryptedKey;
/*      */     //   787: astore #35
/*      */     //   789: goto -> 816
/*      */     //   792: astore #38
/*      */     //   794: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   797: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   800: invokestatic WSS_1335_UNSUPPORTED_KEYBINDING_SIGNATUREPOLICY : ()Ljava/lang/String;
/*      */     //   803: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   806: new com/sun/xml/wss/XWSSecurityException
/*      */     //   809: dup
/*      */     //   810: aload #38
/*      */     //   812: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   815: athrow
/*      */     //   816: aload #13
/*      */     //   818: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   821: astore #34
/*      */     //   823: aload #33
/*      */     //   825: aload #28
/*      */     //   827: aload #34
/*      */     //   829: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   832: pop
/*      */     //   833: aload #35
/*      */     //   835: aload #34
/*      */     //   837: invokeinterface setId : (Ljava/lang/String;)V
/*      */     //   842: aload #35
/*      */     //   844: aload #37
/*      */     //   846: invokeinterface setKeyInfo : (Lcom/sun/org/apache/xml/internal/security/keys/KeyInfo;)V
/*      */     //   851: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   854: dup
/*      */     //   855: aload #13
/*      */     //   857: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   860: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   863: astore #37
/*      */     //   865: new com/sun/xml/wss/core/reference/DirectReference
/*      */     //   868: dup
/*      */     //   869: invokespecial <init> : ()V
/*      */     //   872: astore #38
/*      */     //   874: aload #38
/*      */     //   876: ldc 'http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey'
/*      */     //   878: invokevirtual setValueType : (Ljava/lang/String;)V
/*      */     //   881: aload #38
/*      */     //   883: new java/lang/StringBuilder
/*      */     //   886: dup
/*      */     //   887: invokespecial <init> : ()V
/*      */     //   890: ldc '#'
/*      */     //   892: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   895: aload #34
/*      */     //   897: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   900: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   903: invokevirtual setURI : (Ljava/lang/String;)V
/*      */     //   906: aload #37
/*      */     //   908: aload #38
/*      */     //   910: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   913: new com/sun/xml/wss/core/DerivedKeyTokenHeaderBlock
/*      */     //   916: dup
/*      */     //   917: aload #14
/*      */     //   919: invokevirtual getOwnerDocument : ()Lorg/w3c/dom/Document;
/*      */     //   922: aload #37
/*      */     //   924: aload #32
/*      */     //   926: aload #6
/*      */     //   928: invokeinterface getOffset : ()J
/*      */     //   933: aload #6
/*      */     //   935: invokeinterface getLength : ()J
/*      */     //   940: aload #31
/*      */     //   942: invokespecial <init> : (Lorg/w3c/dom/Document;Lcom/sun/xml/wss/core/SecurityTokenReference;Ljava/lang/String;JJLjava/lang/String;)V
/*      */     //   945: astore #39
/*      */     //   947: iload #29
/*      */     //   949: ifne -> 1183
/*      */     //   952: aconst_null
/*      */     //   953: astore #40
/*      */     //   955: aload #30
/*      */     //   957: ifnull -> 967
/*      */     //   960: aload #30
/*      */     //   962: invokevirtual getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   965: astore #40
/*      */     //   967: aload #40
/*      */     //   969: ifnonnull -> 985
/*      */     //   972: aload #13
/*      */     //   974: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   977: aload #39
/*      */     //   979: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*      */     //   982: goto -> 997
/*      */     //   985: aload #13
/*      */     //   987: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   990: aload #39
/*      */     //   992: aload #40
/*      */     //   994: invokevirtual insertBefore : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;Lorg/w3c/dom/Node;)V
/*      */     //   997: aload #30
/*      */     //   999: ifnull -> 1009
/*      */     //   1002: aload #30
/*      */     //   1004: invokevirtual getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1007: astore #40
/*      */     //   1009: aload #36
/*      */     //   1011: aload #35
/*      */     //   1013: invokevirtual martial : (Lcom/sun/org/apache/xml/internal/security/encryption/EncryptedKey;)Lorg/w3c/dom/Element;
/*      */     //   1016: checkcast javax/xml/soap/SOAPElement
/*      */     //   1019: astore #41
/*      */     //   1021: aload #40
/*      */     //   1023: ifnonnull -> 1039
/*      */     //   1026: aload #13
/*      */     //   1028: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1031: aload #41
/*      */     //   1033: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*      */     //   1036: goto -> 1052
/*      */     //   1039: aload #13
/*      */     //   1041: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1044: aload #41
/*      */     //   1046: aload #40
/*      */     //   1048: invokevirtual insertBefore : (Lorg/w3c/dom/Node;Lorg/w3c/dom/Node;)Lorg/w3c/dom/Node;
/*      */     //   1051: pop
/*      */     //   1052: ldc 'Direct'
/*      */     //   1054: aload #10
/*      */     //   1056: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1059: ifeq -> 1092
/*      */     //   1062: aload #25
/*      */     //   1064: aload #28
/*      */     //   1066: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1069: ifnonnull -> 1092
/*      */     //   1072: aload #13
/*      */     //   1074: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1077: aload #27
/*      */     //   1079: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*      */     //   1082: aload #25
/*      */     //   1084: aload #28
/*      */     //   1086: aload #27
/*      */     //   1088: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1091: pop
/*      */     //   1092: aload #41
/*      */     //   1094: new javax/xml/namespace/QName
/*      */     //   1097: dup
/*      */     //   1098: ldc 'http://www.w3.org/2001/04/xmlenc#'
/*      */     //   1100: ldc 'CipherData'
/*      */     //   1102: ldc 'xenc'
/*      */     //   1104: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1107: invokeinterface getChildElements : (Ljavax/xml/namespace/QName;)Ljava/util/Iterator;
/*      */     //   1112: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1117: checkcast org/w3c/dom/Element
/*      */     //   1120: astore #42
/*      */     //   1122: aload #42
/*      */     //   1124: ldc 'http://www.w3.org/2001/04/xmlenc#'
/*      */     //   1126: ldc 'CipherValue'
/*      */     //   1128: invokeinterface getElementsByTagNameNS : (Ljava/lang/String;Ljava/lang/String;)Lorg/w3c/dom/NodeList;
/*      */     //   1133: iconst_0
/*      */     //   1134: invokeinterface item : (I)Lorg/w3c/dom/Node;
/*      */     //   1139: invokeinterface getTextContent : ()Ljava/lang/String;
/*      */     //   1144: astore #43
/*      */     //   1146: aload #43
/*      */     //   1148: invokestatic decode : (Ljava/lang/String;)[B
/*      */     //   1151: astore #44
/*      */     //   1153: ldc 'SHA-1'
/*      */     //   1155: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
/*      */     //   1158: aload #44
/*      */     //   1160: invokevirtual digest : ([B)[B
/*      */     //   1163: astore #45
/*      */     //   1165: aload #45
/*      */     //   1167: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   1170: astore #46
/*      */     //   1172: aload_0
/*      */     //   1173: ldc 'EncryptedKeySHA1'
/*      */     //   1175: aload #46
/*      */     //   1177: invokevirtual setExtraneousProperty : (Ljava/lang/String;Ljava/lang/Object;)V
/*      */     //   1180: goto -> 1209
/*      */     //   1183: aload #13
/*      */     //   1185: aload #34
/*      */     //   1187: invokevirtual getElementById : (Ljava/lang/String;)Lorg/w3c/dom/Element;
/*      */     //   1190: astore #40
/*      */     //   1192: aload #13
/*      */     //   1194: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1197: aload #39
/*      */     //   1199: aload #40
/*      */     //   1201: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1206: invokevirtual insertBefore : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;Lorg/w3c/dom/Node;)V
/*      */     //   1209: new com/sun/xml/wss/core/reference/DirectReference
/*      */     //   1212: dup
/*      */     //   1213: invokespecial <init> : ()V
/*      */     //   1216: astore #40
/*      */     //   1218: aload #40
/*      */     //   1220: new java/lang/StringBuilder
/*      */     //   1223: dup
/*      */     //   1224: invokespecial <init> : ()V
/*      */     //   1227: ldc '#'
/*      */     //   1229: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1232: aload #31
/*      */     //   1234: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1237: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   1240: invokevirtual setURI : (Ljava/lang/String;)V
/*      */     //   1243: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   1246: dup
/*      */     //   1247: aload #13
/*      */     //   1249: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1252: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   1255: astore #41
/*      */     //   1257: aload #41
/*      */     //   1259: aload #40
/*      */     //   1261: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   1264: aload #15
/*      */     //   1266: aload_3
/*      */     //   1267: aload #41
/*      */     //   1269: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1272: astore #8
/*      */     //   1274: aload #39
/*      */     //   1276: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   1279: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1284: astore #7
/*      */     //   1286: aload #4
/*      */     //   1288: iconst_0
/*      */     //   1289: aload #7
/*      */     //   1291: aastore
/*      */     //   1292: aload #8
/*      */     //   1294: areturn
/*      */     //   1295: aload #21
/*      */     //   1297: invokestatic issuedTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1300: ifeq -> 1948
/*      */     //   1303: aload #21
/*      */     //   1305: checkcast com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding
/*      */     //   1308: astore #22
/*      */     //   1310: aload_0
/*      */     //   1311: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1314: astore #23
/*      */     //   1316: aload #23
/*      */     //   1318: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   1323: checkcast com/sun/xml/ws/security/trust/GenericToken
/*      */     //   1326: astore #24
/*      */     //   1328: aconst_null
/*      */     //   1329: astore #25
/*      */     //   1331: aconst_null
/*      */     //   1332: astore #26
/*      */     //   1334: aconst_null
/*      */     //   1335: astore #27
/*      */     //   1337: aload #21
/*      */     //   1339: checkcast com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding
/*      */     //   1342: astore #28
/*      */     //   1344: aload #28
/*      */     //   1346: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   1349: astore #29
/*      */     //   1351: aload_0
/*      */     //   1352: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*      */     //   1355: astore #30
/*      */     //   1357: aload #30
/*      */     //   1359: aload #29
/*      */     //   1361: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1364: astore #31
/*      */     //   1366: aconst_null
/*      */     //   1367: astore #32
/*      */     //   1369: aload #28
/*      */     //   1371: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*      */     //   1374: astore #33
/*      */     //   1376: aload #28
/*      */     //   1378: pop
/*      */     //   1379: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*      */     //   1382: aload #33
/*      */     //   1384: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1387: ifne -> 1432
/*      */     //   1390: aload #28
/*      */     //   1392: pop
/*      */     //   1393: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*      */     //   1396: aload #33
/*      */     //   1398: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1401: ifne -> 1432
/*      */     //   1404: aload #28
/*      */     //   1406: pop
/*      */     //   1407: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*      */     //   1410: aload #33
/*      */     //   1412: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1415: ifne -> 1432
/*      */     //   1418: aload #28
/*      */     //   1420: pop
/*      */     //   1421: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*      */     //   1424: aload #33
/*      */     //   1426: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1429: ifeq -> 1436
/*      */     //   1432: iconst_1
/*      */     //   1433: goto -> 1437
/*      */     //   1436: iconst_0
/*      */     //   1437: istore #34
/*      */     //   1439: iload #34
/*      */     //   1441: ifeq -> 1472
/*      */     //   1444: aload #24
/*      */     //   1446: ifnonnull -> 1472
/*      */     //   1449: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1452: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1455: invokestatic WSS_1343_NULL_ISSUED_TOKEN : ()Ljava/lang/String;
/*      */     //   1458: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1461: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1464: dup
/*      */     //   1465: ldc_w 'Issued Token to be inserted into the Message was Null'
/*      */     //   1468: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1471: athrow
/*      */     //   1472: aload #24
/*      */     //   1474: ifnull -> 1617
/*      */     //   1477: aload #24
/*      */     //   1479: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*      */     //   1482: checkcast org/w3c/dom/Element
/*      */     //   1485: astore #35
/*      */     //   1487: aload #31
/*      */     //   1489: ifnonnull -> 1567
/*      */     //   1492: aload #13
/*      */     //   1494: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1497: aload #35
/*      */     //   1499: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   1502: astore #25
/*      */     //   1504: aload #25
/*      */     //   1506: ldc 'Id'
/*      */     //   1508: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   1513: astore #36
/*      */     //   1515: ldc ''
/*      */     //   1517: aload #36
/*      */     //   1519: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1522: ifeq -> 1554
/*      */     //   1525: ldc 'EncryptedData'
/*      */     //   1527: aload #25
/*      */     //   1529: invokeinterface getLocalName : ()Ljava/lang/String;
/*      */     //   1534: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   1537: ifeq -> 1554
/*      */     //   1540: aload #25
/*      */     //   1542: ldc 'Id'
/*      */     //   1544: aload #13
/*      */     //   1546: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   1549: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   1554: aload #30
/*      */     //   1556: aload #29
/*      */     //   1558: aload #25
/*      */     //   1560: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1563: pop
/*      */     //   1564: goto -> 1617
/*      */     //   1567: aload #31
/*      */     //   1569: checkcast org/w3c/dom/Element
/*      */     //   1572: invokestatic getWsuIdOrId : (Lorg/w3c/dom/Element;)Ljava/lang/String;
/*      */     //   1575: astore #36
/*      */     //   1577: aload #13
/*      */     //   1579: aload #36
/*      */     //   1581: invokevirtual getElementById : (Ljava/lang/String;)Lorg/w3c/dom/Element;
/*      */     //   1584: checkcast javax/xml/soap/SOAPElement
/*      */     //   1587: astore #32
/*      */     //   1589: aload #32
/*      */     //   1591: ifnonnull -> 1617
/*      */     //   1594: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1597: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1600: invokestatic WSS_1344_ERROR_LOCATE_ISSUE_TOKEN_MESSAGE : ()Ljava/lang/String;
/*      */     //   1603: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1606: new com/sun/xml/wss/XWSSecurityException
/*      */     //   1609: dup
/*      */     //   1610: ldc_w 'Could not locate Issued Token in Message'
/*      */     //   1613: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1616: athrow
/*      */     //   1617: iload #34
/*      */     //   1619: ifeq -> 1642
/*      */     //   1622: aload #23
/*      */     //   1624: invokeinterface getAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   1629: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   1634: checkcast org/w3c/dom/Element
/*      */     //   1637: astore #27
/*      */     //   1639: goto -> 1659
/*      */     //   1642: aload #23
/*      */     //   1644: invokeinterface getUnAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   1649: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   1654: checkcast org/w3c/dom/Element
/*      */     //   1657: astore #27
/*      */     //   1659: aload #13
/*      */     //   1661: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1664: aload #27
/*      */     //   1666: iconst_1
/*      */     //   1667: invokevirtual importNode : (Lorg/w3c/dom/Node;Z)Lorg/w3c/dom/Node;
/*      */     //   1670: checkcast org/w3c/dom/Element
/*      */     //   1673: astore #35
/*      */     //   1675: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   1678: dup
/*      */     //   1679: aload #13
/*      */     //   1681: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1684: aload #35
/*      */     //   1686: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   1689: iconst_0
/*      */     //   1690: invokespecial <init> : (Ljavax/xml/soap/SOAPElement;Z)V
/*      */     //   1693: astore #26
/*      */     //   1695: aload_2
/*      */     //   1696: ifnull -> 1706
/*      */     //   1699: aload #26
/*      */     //   1701: aload_0
/*      */     //   1702: aload_2
/*      */     //   1703: invokestatic updateSamlVsKeyCache : (Lcom/sun/xml/ws/security/SecurityTokenReference;Lcom/sun/xml/wss/impl/FilterProcessingContext;Ljava/security/Key;)V
/*      */     //   1706: aload_1
/*      */     //   1707: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   1710: astore #36
/*      */     //   1712: aload #36
/*      */     //   1714: ifnonnull -> 1724
/*      */     //   1717: aload #13
/*      */     //   1719: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   1722: astore #36
/*      */     //   1724: new com/sun/xml/wss/core/DerivedKeyTokenHeaderBlock
/*      */     //   1727: dup
/*      */     //   1728: aload #13
/*      */     //   1730: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1733: aload #26
/*      */     //   1735: aload #6
/*      */     //   1737: invokeinterface getNonce : ()[B
/*      */     //   1742: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   1745: aload #6
/*      */     //   1747: invokeinterface getOffset : ()J
/*      */     //   1752: aload #6
/*      */     //   1754: invokeinterface getLength : ()J
/*      */     //   1759: aload #36
/*      */     //   1761: invokespecial <init> : (Lorg/w3c/dom/Document;Lcom/sun/xml/wss/core/SecurityTokenReference;Ljava/lang/String;JJLjava/lang/String;)V
/*      */     //   1764: astore #37
/*      */     //   1766: aload #32
/*      */     //   1768: ifnull -> 1795
/*      */     //   1771: aload #13
/*      */     //   1773: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1776: astore #38
/*      */     //   1778: aload #38
/*      */     //   1780: aload #37
/*      */     //   1782: aload #32
/*      */     //   1784: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1789: invokevirtual insertBefore : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;Lorg/w3c/dom/Node;)V
/*      */     //   1792: goto -> 1836
/*      */     //   1795: aload_0
/*      */     //   1796: invokevirtual getCurrentRefList : ()Lorg/w3c/dom/Node;
/*      */     //   1799: astore #38
/*      */     //   1801: aload #38
/*      */     //   1803: ifnull -> 1826
/*      */     //   1806: aload #13
/*      */     //   1808: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1811: aload #37
/*      */     //   1813: aload #38
/*      */     //   1815: invokevirtual insertBefore : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;Lorg/w3c/dom/Node;)V
/*      */     //   1818: aload_0
/*      */     //   1819: aconst_null
/*      */     //   1820: invokevirtual setCurrentReferenceList : (Lorg/w3c/dom/Node;)V
/*      */     //   1823: goto -> 1836
/*      */     //   1826: aload #13
/*      */     //   1828: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1831: aload #37
/*      */     //   1833: invokevirtual insertHeaderBlock : (Lcom/sun/xml/wss/core/SecurityHeaderBlock;)V
/*      */     //   1836: aload #25
/*      */     //   1838: ifnull -> 1862
/*      */     //   1841: iload #34
/*      */     //   1843: ifeq -> 1856
/*      */     //   1846: aload #13
/*      */     //   1848: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1851: aload #25
/*      */     //   1853: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*      */     //   1856: aload_0
/*      */     //   1857: aload #25
/*      */     //   1859: invokevirtual setIssuedSAMLToken : (Lorg/w3c/dom/Element;)V
/*      */     //   1862: new com/sun/xml/wss/core/reference/DirectReference
/*      */     //   1865: dup
/*      */     //   1866: invokespecial <init> : ()V
/*      */     //   1869: astore #38
/*      */     //   1871: aload #38
/*      */     //   1873: new java/lang/StringBuilder
/*      */     //   1876: dup
/*      */     //   1877: invokespecial <init> : ()V
/*      */     //   1880: ldc '#'
/*      */     //   1882: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1885: aload #36
/*      */     //   1887: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   1890: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   1893: invokevirtual setURI : (Ljava/lang/String;)V
/*      */     //   1896: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   1899: dup
/*      */     //   1900: aload #13
/*      */     //   1902: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   1905: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   1908: astore #39
/*      */     //   1910: aload #39
/*      */     //   1912: aload #38
/*      */     //   1914: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   1917: aload #15
/*      */     //   1919: aload_3
/*      */     //   1920: aload #39
/*      */     //   1922: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   1925: astore #8
/*      */     //   1927: aload #37
/*      */     //   1929: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   1932: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   1937: astore #7
/*      */     //   1939: aload #4
/*      */     //   1941: iconst_0
/*      */     //   1942: aload #7
/*      */     //   1944: aastore
/*      */     //   1945: aload #8
/*      */     //   1947: areturn
/*      */     //   1948: aload #21
/*      */     //   1950: invokestatic samlTokenPolicy : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1953: ifeq -> 1979
/*      */     //   1956: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   1959: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   1962: invokestatic WSS_1345_UNSUPPORTED_DERIVEDKEYS_SAML_TOKEN : ()Ljava/lang/String;
/*      */     //   1965: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   1968: new java/lang/UnsupportedOperationException
/*      */     //   1971: dup
/*      */     //   1972: ldc_w 'DerivedKeys with SAMLToken not yet supported'
/*      */     //   1975: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   1978: athrow
/*      */     //   1979: aload #21
/*      */     //   1981: invokestatic secureConversationTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   1984: ifeq -> 2225
/*      */     //   1987: aload #21
/*      */     //   1989: checkcast com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding
/*      */     //   1992: astore #22
/*      */     //   1994: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   1997: dup
/*      */     //   1998: aload #13
/*      */     //   2000: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2003: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2006: astore #23
/*      */     //   2008: aload_0
/*      */     //   2009: aload #22
/*      */     //   2011: aload #23
/*      */     //   2013: invokestatic insertSCT : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/soap/SOAPElement;
/*      */     //   2016: astore #24
/*      */     //   2018: aload_1
/*      */     //   2019: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   2022: astore #25
/*      */     //   2024: aload #25
/*      */     //   2026: ifnonnull -> 2036
/*      */     //   2029: aload #13
/*      */     //   2031: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   2034: astore #25
/*      */     //   2036: aload #6
/*      */     //   2038: invokeinterface getNonce : ()[B
/*      */     //   2043: invokestatic encode : ([B)Ljava/lang/String;
/*      */     //   2046: astore #26
/*      */     //   2048: new com/sun/xml/wss/core/DerivedKeyTokenHeaderBlock
/*      */     //   2051: dup
/*      */     //   2052: aload #14
/*      */     //   2054: invokevirtual getOwnerDocument : ()Lorg/w3c/dom/Document;
/*      */     //   2057: aload #23
/*      */     //   2059: aload #26
/*      */     //   2061: aload #6
/*      */     //   2063: invokeinterface getOffset : ()J
/*      */     //   2068: aload #6
/*      */     //   2070: invokeinterface getLength : ()J
/*      */     //   2075: aload #25
/*      */     //   2077: invokespecial <init> : (Lorg/w3c/dom/Document;Lcom/sun/xml/wss/core/SecurityTokenReference;Ljava/lang/String;JJLjava/lang/String;)V
/*      */     //   2080: astore #27
/*      */     //   2082: aload #24
/*      */     //   2084: ifnull -> 2097
/*      */     //   2087: aload #24
/*      */     //   2089: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2094: goto -> 2098
/*      */     //   2097: aconst_null
/*      */     //   2098: astore #28
/*      */     //   2100: aload #28
/*      */     //   2102: ifnonnull -> 2125
/*      */     //   2105: aload_0
/*      */     //   2106: invokevirtual getCurrentRefList : ()Lorg/w3c/dom/Node;
/*      */     //   2109: astore #29
/*      */     //   2111: aload #29
/*      */     //   2113: ifnull -> 2125
/*      */     //   2116: aload #29
/*      */     //   2118: astore #28
/*      */     //   2120: aload_0
/*      */     //   2121: aconst_null
/*      */     //   2122: invokevirtual setCurrentReferenceList : (Lorg/w3c/dom/Node;)V
/*      */     //   2125: aload #14
/*      */     //   2127: aload #27
/*      */     //   2129: invokevirtual getAsSoapElement : ()Ljavax/xml/soap/SOAPElement;
/*      */     //   2132: aload #28
/*      */     //   2134: invokevirtual insertBefore : (Lorg/w3c/dom/Node;Lorg/w3c/dom/Node;)Lorg/w3c/dom/Node;
/*      */     //   2137: checkcast javax/xml/soap/SOAPElement
/*      */     //   2140: astore #29
/*      */     //   2142: new com/sun/xml/wss/core/reference/DirectReference
/*      */     //   2145: dup
/*      */     //   2146: invokespecial <init> : ()V
/*      */     //   2149: astore #30
/*      */     //   2151: aload #30
/*      */     //   2153: new java/lang/StringBuilder
/*      */     //   2156: dup
/*      */     //   2157: invokespecial <init> : ()V
/*      */     //   2160: ldc '#'
/*      */     //   2162: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   2165: aload #25
/*      */     //   2167: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   2170: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   2173: invokevirtual setURI : (Ljava/lang/String;)V
/*      */     //   2176: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   2179: dup
/*      */     //   2180: aload #13
/*      */     //   2182: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2185: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2188: astore #31
/*      */     //   2190: aload #31
/*      */     //   2192: aload #30
/*      */     //   2194: invokevirtual setReference : (Lcom/sun/xml/wss/core/ReferenceElement;)V
/*      */     //   2197: aload #29
/*      */     //   2199: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2204: astore #7
/*      */     //   2206: aload #4
/*      */     //   2208: iconst_0
/*      */     //   2209: aload #7
/*      */     //   2211: aastore
/*      */     //   2212: aload #15
/*      */     //   2214: aload_3
/*      */     //   2215: aload #31
/*      */     //   2217: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   2220: astore #8
/*      */     //   2222: aload #8
/*      */     //   2224: areturn
/*      */     //   2225: goto -> 2828
/*      */     //   2228: aload_1
/*      */     //   2229: invokestatic issuedTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   2232: ifeq -> 2754
/*      */     //   2235: aload_0
/*      */     //   2236: invokevirtual getTrustContext : ()Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   2239: astore #20
/*      */     //   2241: aload #20
/*      */     //   2243: invokeinterface getSecurityToken : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   2248: checkcast com/sun/xml/ws/security/trust/GenericToken
/*      */     //   2251: astore #21
/*      */     //   2253: aconst_null
/*      */     //   2254: astore #22
/*      */     //   2256: aconst_null
/*      */     //   2257: astore #23
/*      */     //   2259: aconst_null
/*      */     //   2260: astore #24
/*      */     //   2262: aconst_null
/*      */     //   2263: astore #25
/*      */     //   2265: aload_1
/*      */     //   2266: checkcast com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding
/*      */     //   2269: astore #26
/*      */     //   2271: aload #26
/*      */     //   2273: invokevirtual getUUID : ()Ljava/lang/String;
/*      */     //   2276: astore #27
/*      */     //   2278: aload_0
/*      */     //   2279: invokevirtual getTokenCache : ()Ljava/util/HashMap;
/*      */     //   2282: astore #28
/*      */     //   2284: aload #28
/*      */     //   2286: aload #27
/*      */     //   2288: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2291: astore #29
/*      */     //   2293: aload #26
/*      */     //   2295: invokevirtual getIncludeToken : ()Ljava/lang/String;
/*      */     //   2298: astore #30
/*      */     //   2300: aload #26
/*      */     //   2302: pop
/*      */     //   2303: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT : Ljava/lang/String;
/*      */     //   2306: aload #30
/*      */     //   2308: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2311: ifne -> 2356
/*      */     //   2314: aload #26
/*      */     //   2316: pop
/*      */     //   2317: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS : Ljava/lang/String;
/*      */     //   2320: aload #30
/*      */     //   2322: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2325: ifne -> 2356
/*      */     //   2328: aload #26
/*      */     //   2330: pop
/*      */     //   2331: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2 : Ljava/lang/String;
/*      */     //   2334: aload #30
/*      */     //   2336: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2339: ifne -> 2356
/*      */     //   2342: aload #26
/*      */     //   2344: pop
/*      */     //   2345: getstatic com/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2 : Ljava/lang/String;
/*      */     //   2348: aload #30
/*      */     //   2350: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2353: ifeq -> 2360
/*      */     //   2356: iconst_1
/*      */     //   2357: goto -> 2361
/*      */     //   2360: iconst_0
/*      */     //   2361: istore #31
/*      */     //   2363: iload #31
/*      */     //   2365: ifeq -> 2396
/*      */     //   2368: aload #21
/*      */     //   2370: ifnonnull -> 2396
/*      */     //   2373: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2376: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2379: invokestatic WSS_1343_NULL_ISSUED_TOKEN : ()Ljava/lang/String;
/*      */     //   2382: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   2385: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2388: dup
/*      */     //   2389: ldc_w 'Issued Token to be inserted into the Message was Null'
/*      */     //   2392: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   2395: athrow
/*      */     //   2396: aload #21
/*      */     //   2398: ifnull -> 2541
/*      */     //   2401: aload #21
/*      */     //   2403: invokevirtual getTokenValue : ()Ljava/lang/Object;
/*      */     //   2406: checkcast org/w3c/dom/Element
/*      */     //   2409: astore #32
/*      */     //   2411: aload #29
/*      */     //   2413: ifnonnull -> 2491
/*      */     //   2416: aload #13
/*      */     //   2418: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2421: aload #32
/*      */     //   2423: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   2426: astore #22
/*      */     //   2428: aload #22
/*      */     //   2430: ldc 'Id'
/*      */     //   2432: invokeinterface getAttribute : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   2437: astore #33
/*      */     //   2439: ldc ''
/*      */     //   2441: aload #33
/*      */     //   2443: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2446: ifeq -> 2478
/*      */     //   2449: ldc 'EncryptedData'
/*      */     //   2451: aload #22
/*      */     //   2453: invokeinterface getLocalName : ()Ljava/lang/String;
/*      */     //   2458: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   2461: ifeq -> 2478
/*      */     //   2464: aload #22
/*      */     //   2466: ldc 'Id'
/*      */     //   2468: aload #13
/*      */     //   2470: invokevirtual generateId : ()Ljava/lang/String;
/*      */     //   2473: invokeinterface setAttribute : (Ljava/lang/String;Ljava/lang/String;)V
/*      */     //   2478: aload #28
/*      */     //   2480: aload #27
/*      */     //   2482: aload #22
/*      */     //   2484: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   2487: pop
/*      */     //   2488: goto -> 2541
/*      */     //   2491: aload #29
/*      */     //   2493: checkcast org/w3c/dom/Element
/*      */     //   2496: invokestatic getWsuIdOrId : (Lorg/w3c/dom/Element;)Ljava/lang/String;
/*      */     //   2499: astore #33
/*      */     //   2501: aload #13
/*      */     //   2503: aload #33
/*      */     //   2505: invokevirtual getElementById : (Ljava/lang/String;)Lorg/w3c/dom/Element;
/*      */     //   2508: checkcast javax/xml/soap/SOAPElement
/*      */     //   2511: astore #25
/*      */     //   2513: aload #25
/*      */     //   2515: ifnonnull -> 2541
/*      */     //   2518: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2521: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2524: invokestatic WSS_1344_ERROR_LOCATE_ISSUE_TOKEN_MESSAGE : ()Ljava/lang/String;
/*      */     //   2527: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   2530: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2533: dup
/*      */     //   2534: ldc_w 'Could not locate Issued Token in Message'
/*      */     //   2537: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   2540: athrow
/*      */     //   2541: iload #31
/*      */     //   2543: ifeq -> 2571
/*      */     //   2546: aload #20
/*      */     //   2548: invokeinterface getAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   2553: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   2558: aload #13
/*      */     //   2560: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2563: invokestatic convertSTRToElement : (Ljava/lang/Object;Lorg/w3c/dom/Document;)Lorg/w3c/dom/Element;
/*      */     //   2566: astore #24
/*      */     //   2568: goto -> 2593
/*      */     //   2571: aload #20
/*      */     //   2573: invokeinterface getUnAttachedSecurityTokenReference : ()Lcom/sun/xml/ws/security/Token;
/*      */     //   2578: invokeinterface getTokenValue : ()Ljava/lang/Object;
/*      */     //   2583: aload #13
/*      */     //   2585: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2588: invokestatic convertSTRToElement : (Ljava/lang/Object;Lorg/w3c/dom/Document;)Lorg/w3c/dom/Element;
/*      */     //   2591: astore #24
/*      */     //   2593: aload #24
/*      */     //   2595: ifnonnull -> 2621
/*      */     //   2598: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2601: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2604: invokestatic WSS_1378_UNABLETO_REFER_ISSUE_TOKEN : ()Ljava/lang/String;
/*      */     //   2607: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;)V
/*      */     //   2610: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2613: dup
/*      */     //   2614: ldc_w 'Cannot determine how to reference the Issued Token in the Message'
/*      */     //   2617: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   2620: athrow
/*      */     //   2621: aload #13
/*      */     //   2623: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2626: aload #24
/*      */     //   2628: iconst_1
/*      */     //   2629: invokevirtual importNode : (Lorg/w3c/dom/Node;Z)Lorg/w3c/dom/Node;
/*      */     //   2632: checkcast org/w3c/dom/Element
/*      */     //   2635: astore #32
/*      */     //   2637: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   2640: dup
/*      */     //   2641: aload #13
/*      */     //   2643: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2646: aload #32
/*      */     //   2648: iconst_1
/*      */     //   2649: invokeinterface cloneNode : (Z)Lorg/w3c/dom/Node;
/*      */     //   2654: checkcast org/w3c/dom/Element
/*      */     //   2657: invokestatic convertToSoapElement : (Lorg/w3c/dom/Document;Lorg/w3c/dom/Element;)Ljavax/xml/soap/SOAPElement;
/*      */     //   2660: iconst_0
/*      */     //   2661: invokespecial <init> : (Ljavax/xml/soap/SOAPElement;Z)V
/*      */     //   2664: astore #23
/*      */     //   2666: aload_2
/*      */     //   2667: ifnull -> 2677
/*      */     //   2670: aload #23
/*      */     //   2672: aload_0
/*      */     //   2673: aload_2
/*      */     //   2674: invokestatic updateSamlVsKeyCache : (Lcom/sun/xml/ws/security/SecurityTokenReference;Lcom/sun/xml/wss/impl/FilterProcessingContext;Ljava/security/Key;)V
/*      */     //   2677: aload #22
/*      */     //   2679: ifnull -> 2725
/*      */     //   2682: iload #31
/*      */     //   2684: ifeq -> 2711
/*      */     //   2687: aload #13
/*      */     //   2689: invokevirtual findOrCreateSecurityHeader : ()Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   2692: aload #22
/*      */     //   2694: invokevirtual insertHeaderBlockElement : (Ljavax/xml/soap/SOAPElement;)V
/*      */     //   2697: aload #4
/*      */     //   2699: iconst_0
/*      */     //   2700: aload #22
/*      */     //   2702: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2707: aastore
/*      */     //   2708: goto -> 2716
/*      */     //   2711: aload #4
/*      */     //   2713: iconst_0
/*      */     //   2714: aconst_null
/*      */     //   2715: aastore
/*      */     //   2716: aload_0
/*      */     //   2717: aload #22
/*      */     //   2719: invokevirtual setIssuedSAMLToken : (Lorg/w3c/dom/Element;)V
/*      */     //   2722: goto -> 2741
/*      */     //   2725: aload #25
/*      */     //   2727: ifnull -> 2741
/*      */     //   2730: aload #4
/*      */     //   2732: iconst_0
/*      */     //   2733: aload #25
/*      */     //   2735: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2740: aastore
/*      */     //   2741: aload #15
/*      */     //   2743: aload_3
/*      */     //   2744: aload #23
/*      */     //   2746: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   2749: astore #8
/*      */     //   2751: aload #8
/*      */     //   2753: areturn
/*      */     //   2754: aload_1
/*      */     //   2755: invokestatic secureConversationTokenKeyBinding : (Lcom/sun/xml/wss/impl/policy/SecurityPolicy;)Z
/*      */     //   2758: ifeq -> 2828
/*      */     //   2761: new com/sun/xml/wss/core/SecurityTokenReference
/*      */     //   2764: dup
/*      */     //   2765: aload #13
/*      */     //   2767: invokevirtual getSOAPPart : ()Ljavax/xml/soap/SOAPPart;
/*      */     //   2770: invokespecial <init> : (Lorg/w3c/dom/Document;)V
/*      */     //   2773: astore #20
/*      */     //   2775: aload_1
/*      */     //   2776: checkcast com/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding
/*      */     //   2779: astore #21
/*      */     //   2781: aload_0
/*      */     //   2782: aload #21
/*      */     //   2784: aload #20
/*      */     //   2786: invokestatic insertSCT : (Lcom/sun/xml/wss/impl/FilterProcessingContext;Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/soap/SOAPElement;
/*      */     //   2789: astore #22
/*      */     //   2791: aload #22
/*      */     //   2793: ifnull -> 2806
/*      */     //   2796: aload #22
/*      */     //   2798: invokeinterface getNextSibling : ()Lorg/w3c/dom/Node;
/*      */     //   2803: goto -> 2807
/*      */     //   2806: aconst_null
/*      */     //   2807: astore #7
/*      */     //   2809: aload #4
/*      */     //   2811: iconst_0
/*      */     //   2812: aload #7
/*      */     //   2814: aastore
/*      */     //   2815: aload #15
/*      */     //   2817: aload_3
/*      */     //   2818: aload #20
/*      */     //   2820: invokevirtual constructKeyInfo : (Lcom/sun/xml/wss/impl/policy/MLSPolicy;Lcom/sun/xml/wss/core/SecurityTokenReference;)Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   2823: astore #8
/*      */     //   2825: aload #8
/*      */     //   2827: areturn
/*      */     //   2828: goto -> 2909
/*      */     //   2831: astore #20
/*      */     //   2833: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2836: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2839: invokestatic WSS_1346_ERROR_PREPARING_SYMMETRICKEY_SIGNATURE : ()Ljava/lang/String;
/*      */     //   2842: aload #20
/*      */     //   2844: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   2847: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2850: dup
/*      */     //   2851: aload #20
/*      */     //   2853: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   2856: athrow
/*      */     //   2857: astore #20
/*      */     //   2859: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2862: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2865: invokestatic WSS_1346_ERROR_PREPARING_SYMMETRICKEY_SIGNATURE : ()Ljava/lang/String;
/*      */     //   2868: aload #20
/*      */     //   2870: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   2873: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2876: dup
/*      */     //   2877: aload #20
/*      */     //   2879: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   2882: athrow
/*      */     //   2883: astore #20
/*      */     //   2885: getstatic com/sun/xml/wss/impl/dsig/SignatureProcessor.logger : Ljava/util/logging/Logger;
/*      */     //   2888: getstatic java/util/logging/Level.SEVERE : Ljava/util/logging/Level;
/*      */     //   2891: invokestatic WSS_1346_ERROR_PREPARING_SYMMETRICKEY_SIGNATURE : ()Ljava/lang/String;
/*      */     //   2894: aload #20
/*      */     //   2896: invokevirtual log : (Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/Throwable;)V
/*      */     //   2899: new com/sun/xml/wss/XWSSecurityException
/*      */     //   2902: dup
/*      */     //   2903: aload #20
/*      */     //   2905: invokespecial <init> : (Ljava/lang/Throwable;)V
/*      */     //   2908: athrow
/*      */     //   2909: aconst_null
/*      */     //   2910: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1345	-> 0
/*      */     //   #1346	-> 3
/*      */     //   #1350	-> 6
/*      */     //   #1351	-> 10
/*      */     //   #1352	-> 17
/*      */     //   #1355	-> 26
/*      */     //   #1356	-> 29
/*      */     //   #1357	-> 32
/*      */     //   #1358	-> 35
/*      */     //   #1359	-> 41
/*      */     //   #1361	-> 48
/*      */     //   #1363	-> 53
/*      */     //   #1364	-> 66
/*      */     //   #1365	-> 79
/*      */     //   #1366	-> 103
/*      */     //   #1369	-> 115
/*      */     //   #1370	-> 122
/*      */     //   #1372	-> 131
/*      */     //   #1374	-> 138
/*      */     //   #1375	-> 146
/*      */     //   #1376	-> 158
/*      */     //   #1377	-> 168
/*      */     //   #1379	-> 176
/*      */     //   #1380	-> 181
/*      */     //   #1383	-> 187
/*      */     //   #1384	-> 201
/*      */     //   #1385	-> 215
/*      */     //   #1386	-> 222
/*      */     //   #1390	-> 229
/*      */     //   #1391	-> 235
/*      */     //   #1392	-> 240
/*      */     //   #1394	-> 247
/*      */     //   #1395	-> 259
/*      */     //   #1398	-> 293
/*      */     //   #1400	-> 303
/*      */     //   #1401	-> 315
/*      */     //   #1404	-> 321
/*      */     //   #1405	-> 330
/*      */     //   #1406	-> 355
/*      */     //   #1407	-> 369
/*      */     //   #1408	-> 376
/*      */     //   #1410	-> 386
/*      */     //   #1412	-> 389
/*      */     //   #1414	-> 399
/*      */     //   #1415	-> 402
/*      */     //   #1416	-> 405
/*      */     //   #1417	-> 412
/*      */     //   #1418	-> 418
/*      */     //   #1421	-> 423
/*      */     //   #1422	-> 429
/*      */     //   #1423	-> 435
/*      */     //   #1424	-> 442
/*      */     //   #1425	-> 457
/*      */     //   #1428	-> 464
/*      */     //   #1430	-> 472
/*      */     //   #1431	-> 479
/*      */     //   #1432	-> 486
/*      */     //   #1433	-> 489
/*      */     //   #1434	-> 496
/*      */     //   #1436	-> 503
/*      */     //   #1438	-> 506
/*      */     //   #1439	-> 521
/*      */     //   #1443	-> 528
/*      */     //   #1448	-> 542
/*      */     //   #1449	-> 554
/*      */     //   #1450	-> 559
/*      */     //   #1451	-> 564
/*      */     //   #1452	-> 568
/*      */     //   #1454	-> 581
/*      */     //   #1455	-> 588
/*      */     //   #1457	-> 603
/*      */     //   #1459	-> 607
/*      */     //   #1460	-> 627
/*      */     //   #1462	-> 637
/*      */     //   #1464	-> 645
/*      */     //   #1467	-> 648
/*      */     //   #1468	-> 654
/*      */     //   #1469	-> 659
/*      */     //   #1471	-> 666
/*      */     //   #1472	-> 678
/*      */     //   #1473	-> 684
/*      */     //   #1474	-> 696
/*      */     //   #1475	-> 699
/*      */     //   #1476	-> 702
/*      */     //   #1478	-> 707
/*      */     //   #1480	-> 714
/*      */     //   #1481	-> 728
/*      */     //   #1482	-> 735
/*      */     //   #1483	-> 746
/*      */     //   #1488	-> 753
/*      */     //   #1489	-> 760
/*      */     //   #1490	-> 771
/*      */     //   #1491	-> 776
/*      */     //   #1496	-> 789
/*      */     //   #1493	-> 792
/*      */     //   #1494	-> 794
/*      */     //   #1495	-> 806
/*      */     //   #1497	-> 816
/*      */     //   #1498	-> 823
/*      */     //   #1499	-> 833
/*      */     //   #1501	-> 842
/*      */     //   #1505	-> 851
/*      */     //   #1506	-> 865
/*      */     //   #1510	-> 874
/*      */     //   #1511	-> 881
/*      */     //   #1512	-> 906
/*      */     //   #1513	-> 913
/*      */     //   #1516	-> 947
/*      */     //   #1517	-> 952
/*      */     //   #1518	-> 955
/*      */     //   #1519	-> 960
/*      */     //   #1522	-> 967
/*      */     //   #1523	-> 972
/*      */     //   #1525	-> 985
/*      */     //   #1528	-> 997
/*      */     //   #1529	-> 1002
/*      */     //   #1533	-> 1009
/*      */     //   #1534	-> 1021
/*      */     //   #1535	-> 1026
/*      */     //   #1537	-> 1039
/*      */     //   #1540	-> 1052
/*      */     //   #1541	-> 1072
/*      */     //   #1542	-> 1082
/*      */     //   #1545	-> 1092
/*      */     //   #1546	-> 1122
/*      */     //   #1547	-> 1146
/*      */     //   #1548	-> 1153
/*      */     //   #1549	-> 1165
/*      */     //   #1550	-> 1172
/*      */     //   #1551	-> 1180
/*      */     //   #1553	-> 1183
/*      */     //   #1554	-> 1192
/*      */     //   #1558	-> 1209
/*      */     //   #1559	-> 1218
/*      */     //   #1560	-> 1243
/*      */     //   #1561	-> 1257
/*      */     //   #1562	-> 1264
/*      */     //   #1565	-> 1274
/*      */     //   #1566	-> 1286
/*      */     //   #1568	-> 1292
/*      */     //   #1571	-> 1295
/*      */     //   #1573	-> 1303
/*      */     //   #1575	-> 1310
/*      */     //   #1578	-> 1316
/*      */     //   #1579	-> 1328
/*      */     //   #1580	-> 1331
/*      */     //   #1581	-> 1334
/*      */     //   #1584	-> 1337
/*      */     //   #1586	-> 1344
/*      */     //   #1589	-> 1351
/*      */     //   #1590	-> 1357
/*      */     //   #1591	-> 1366
/*      */     //   #1592	-> 1369
/*      */     //   #1593	-> 1376
/*      */     //   #1599	-> 1439
/*      */     //   #1600	-> 1449
/*      */     //   #1601	-> 1461
/*      */     //   #1604	-> 1472
/*      */     //   #1606	-> 1477
/*      */     //   #1607	-> 1487
/*      */     //   #1609	-> 1492
/*      */     //   #1611	-> 1504
/*      */     //   #1612	-> 1515
/*      */     //   #1614	-> 1540
/*      */     //   #1616	-> 1554
/*      */     //   #1617	-> 1564
/*      */     //   #1619	-> 1567
/*      */     //   #1620	-> 1577
/*      */     //   #1621	-> 1589
/*      */     //   #1622	-> 1594
/*      */     //   #1623	-> 1606
/*      */     //   #1628	-> 1617
/*      */     //   #1629	-> 1622
/*      */     //   #1631	-> 1642
/*      */     //   #1635	-> 1659
/*      */     //   #1636	-> 1675
/*      */     //   #1638	-> 1695
/*      */     //   #1639	-> 1699
/*      */     //   #1642	-> 1706
/*      */     //   #1643	-> 1712
/*      */     //   #1644	-> 1717
/*      */     //   #1647	-> 1724
/*      */     //   #1657	-> 1766
/*      */     //   #1658	-> 1771
/*      */     //   #1659	-> 1778
/*      */     //   #1660	-> 1792
/*      */     //   #1661	-> 1795
/*      */     //   #1662	-> 1801
/*      */     //   #1663	-> 1806
/*      */     //   #1664	-> 1818
/*      */     //   #1666	-> 1826
/*      */     //   #1671	-> 1836
/*      */     //   #1672	-> 1841
/*      */     //   #1673	-> 1846
/*      */     //   #1677	-> 1856
/*      */     //   #1681	-> 1862
/*      */     //   #1682	-> 1871
/*      */     //   #1683	-> 1896
/*      */     //   #1684	-> 1910
/*      */     //   #1685	-> 1917
/*      */     //   #1688	-> 1927
/*      */     //   #1689	-> 1939
/*      */     //   #1690	-> 1945
/*      */     //   #1692	-> 1948
/*      */     //   #1693	-> 1956
/*      */     //   #1694	-> 1968
/*      */     //   #1696	-> 1979
/*      */     //   #1697	-> 1987
/*      */     //   #1699	-> 1994
/*      */     //   #1700	-> 2008
/*      */     //   #1701	-> 2018
/*      */     //   #1702	-> 2024
/*      */     //   #1703	-> 2029
/*      */     //   #1705	-> 2036
/*      */     //   #1706	-> 2048
/*      */     //   #1710	-> 2082
/*      */     //   #1712	-> 2100
/*      */     //   #1713	-> 2105
/*      */     //   #1714	-> 2111
/*      */     //   #1715	-> 2116
/*      */     //   #1716	-> 2120
/*      */     //   #1720	-> 2125
/*      */     //   #1723	-> 2142
/*      */     //   #1724	-> 2151
/*      */     //   #1725	-> 2176
/*      */     //   #1726	-> 2190
/*      */     //   #1729	-> 2197
/*      */     //   #1730	-> 2206
/*      */     //   #1732	-> 2212
/*      */     //   #1733	-> 2222
/*      */     //   #1736	-> 2225
/*      */     //   #1738	-> 2235
/*      */     //   #1739	-> 2241
/*      */     //   #1740	-> 2253
/*      */     //   #1741	-> 2256
/*      */     //   #1742	-> 2259
/*      */     //   #1743	-> 2262
/*      */     //   #1746	-> 2265
/*      */     //   #1748	-> 2271
/*      */     //   #1751	-> 2278
/*      */     //   #1752	-> 2284
/*      */     //   #1753	-> 2293
/*      */     //   #1754	-> 2300
/*      */     //   #1759	-> 2363
/*      */     //   #1760	-> 2373
/*      */     //   #1761	-> 2385
/*      */     //   #1764	-> 2396
/*      */     //   #1766	-> 2401
/*      */     //   #1767	-> 2411
/*      */     //   #1769	-> 2416
/*      */     //   #1771	-> 2428
/*      */     //   #1772	-> 2439
/*      */     //   #1774	-> 2464
/*      */     //   #1776	-> 2478
/*      */     //   #1777	-> 2488
/*      */     //   #1779	-> 2491
/*      */     //   #1780	-> 2501
/*      */     //   #1781	-> 2513
/*      */     //   #1782	-> 2518
/*      */     //   #1783	-> 2530
/*      */     //   #1788	-> 2541
/*      */     //   #1789	-> 2546
/*      */     //   #1791	-> 2571
/*      */     //   #1794	-> 2593
/*      */     //   #1795	-> 2598
/*      */     //   #1796	-> 2610
/*      */     //   #1800	-> 2621
/*      */     //   #1801	-> 2637
/*      */     //   #1804	-> 2666
/*      */     //   #1805	-> 2670
/*      */     //   #1808	-> 2677
/*      */     //   #1809	-> 2682
/*      */     //   #1811	-> 2687
/*      */     //   #1812	-> 2697
/*      */     //   #1814	-> 2711
/*      */     //   #1818	-> 2716
/*      */     //   #1819	-> 2725
/*      */     //   #1820	-> 2730
/*      */     //   #1823	-> 2741
/*      */     //   #1824	-> 2751
/*      */     //   #1826	-> 2754
/*      */     //   #1828	-> 2761
/*      */     //   #1829	-> 2775
/*      */     //   #1830	-> 2781
/*      */     //   #1833	-> 2791
/*      */     //   #1834	-> 2809
/*      */     //   #1836	-> 2815
/*      */     //   #1837	-> 2825
/*      */     //   #1849	-> 2828
/*      */     //   #1840	-> 2831
/*      */     //   #1841	-> 2833
/*      */     //   #1842	-> 2847
/*      */     //   #1843	-> 2857
/*      */     //   #1844	-> 2859
/*      */     //   #1845	-> 2873
/*      */     //   #1846	-> 2883
/*      */     //   #1847	-> 2885
/*      */     //   #1848	-> 2899
/*      */     //   #1851	-> 2909
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   187	202	22	ekSha1Ref	Ljava/lang/String;
/*      */     //   201	188	23	tokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   215	174	24	refElem	Lcom/sun/xml/wss/core/reference/EncryptedKeySHA1Identifier;
/*      */     //   235	154	25	dktId	Ljava/lang/String;
/*      */     //   259	130	26	nonce	Ljava/lang/String;
/*      */     //   293	96	27	dktHeadrBlock	Lcom/sun/xml/wss/core/DerivedKeyTokenHeaderBlock;
/*      */     //   330	59	28	reference	Lcom/sun/xml/wss/core/reference/DirectReference;
/*      */     //   369	20	29	sigTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   588	49	31	valueType	Ljava/lang/String;
/*      */     //   794	22	38	e	Ljava/lang/Exception;
/*      */     //   753	98	37	apacheKeyInfo	Lcom/sun/org/apache/xml/internal/security/keys/KeyInfo;
/*      */     //   955	225	40	nsX509	Lorg/w3c/dom/Node;
/*      */     //   1021	159	41	se	Ljavax/xml/soap/SOAPElement;
/*      */     //   1122	58	42	cipherData	Lorg/w3c/dom/Element;
/*      */     //   1146	34	43	cipherValue	Ljava/lang/String;
/*      */     //   1153	27	44	decodedCipher	[B
/*      */     //   1165	15	45	ekSha1	[B
/*      */     //   1172	8	46	encEkSha1	Ljava/lang/String;
/*      */     //   1192	17	40	ekElem	Lorg/w3c/dom/Element;
/*      */     //   402	893	22	x509Binding	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   405	890	23	cert	Ljava/security/cert/X509Certificate;
/*      */     //   429	866	24	tokenCache	Ljava/util/HashMap;
/*      */     //   435	860	25	insertedX509Cache	Ljava/util/HashMap;
/*      */     //   442	853	26	x509id	Ljava/lang/String;
/*      */     //   489	806	27	token	Lcom/sun/xml/wss/core/X509SecurityToken;
/*      */     //   503	792	28	x509TokenId	Ljava/lang/String;
/*      */     //   506	789	29	tokenInserted	Z
/*      */     //   542	753	30	insertedx509	Lcom/sun/xml/wss/core/X509SecurityToken;
/*      */     //   654	641	31	dktId	Ljava/lang/String;
/*      */     //   678	617	32	nonce	Ljava/lang/String;
/*      */     //   684	611	33	ekCache	Ljava/util/HashMap;
/*      */     //   696	599	34	ekId	Ljava/lang/String;
/*      */     //   699	596	35	encryptedKey	Lcom/sun/org/apache/xml/internal/security/encryption/EncryptedKey;
/*      */     //   702	593	36	keyEncryptor	Lcom/sun/org/apache/xml/internal/security/encryption/XMLCipher;
/*      */     //   865	430	37	tokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   874	421	38	reference	Lcom/sun/xml/wss/core/reference/DirectReference;
/*      */     //   947	348	39	dktHeadrBlock	Lcom/sun/xml/wss/core/DerivedKeyTokenHeaderBlock;
/*      */     //   1218	77	40	refSig	Lcom/sun/xml/wss/core/reference/DirectReference;
/*      */     //   1257	38	41	sigTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   1515	49	36	tokId	Ljava/lang/String;
/*      */     //   1577	40	36	wsuId	Ljava/lang/String;
/*      */     //   1487	130	35	elem	Lorg/w3c/dom/Element;
/*      */     //   1778	14	38	_secHeader	Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   1801	35	38	reflist	Lorg/w3c/dom/Node;
/*      */     //   1310	638	22	itk	Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*      */     //   1316	632	23	issuedTokenContext	Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   1328	620	24	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*      */     //   1331	617	25	tokenElem	Ljavax/xml/soap/SOAPElement;
/*      */     //   1334	614	26	str	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   1337	611	27	strElem	Lorg/w3c/dom/Element;
/*      */     //   1344	604	28	ikb	Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*      */     //   1351	597	29	ikbPolicyId	Ljava/lang/String;
/*      */     //   1357	591	30	tokCache	Ljava/util/HashMap;
/*      */     //   1366	582	31	tok	Ljava/lang/Object;
/*      */     //   1369	579	32	issuedTokenElementFromMsg	Ljavax/xml/soap/SOAPElement;
/*      */     //   1376	572	33	iTokenType	Ljava/lang/String;
/*      */     //   1439	509	34	includeIST	Z
/*      */     //   1675	273	35	imported	Lorg/w3c/dom/Element;
/*      */     //   1712	236	36	dktId	Ljava/lang/String;
/*      */     //   1766	182	37	derivedKeyTokenHeaderBlock	Lcom/sun/xml/wss/core/DerivedKeyTokenHeaderBlock;
/*      */     //   1871	77	38	refSig	Lcom/sun/xml/wss/core/reference/DirectReference;
/*      */     //   1910	38	39	sigTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   2111	14	29	reflist	Lorg/w3c/dom/Node;
/*      */     //   1994	231	22	sctBinding	Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*      */     //   2008	217	23	tokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   2018	207	24	sctElement	Ljavax/xml/soap/SOAPElement;
/*      */     //   2024	201	25	dktId	Ljava/lang/String;
/*      */     //   2048	177	26	nonce	Ljava/lang/String;
/*      */     //   2082	143	27	dktHeaderBlock	Lcom/sun/xml/wss/core/DerivedKeyTokenHeaderBlock;
/*      */     //   2100	125	28	next	Lorg/w3c/dom/Node;
/*      */     //   2142	83	29	dktElem	Ljavax/xml/soap/SOAPElement;
/*      */     //   2151	74	30	refSig	Lcom/sun/xml/wss/core/reference/DirectReference;
/*      */     //   2190	35	31	sigTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   131	2094	20	dtk	Lcom/sun/xml/wss/impl/policy/mls/DerivedTokenKeyBinding;
/*      */     //   138	2087	21	originalKeyBinding	Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;
/*      */     //   2439	49	33	tokId	Ljava/lang/String;
/*      */     //   2501	40	33	wsuId	Ljava/lang/String;
/*      */     //   2411	130	32	elem	Lorg/w3c/dom/Element;
/*      */     //   2241	513	20	issuedTokenContext	Lcom/sun/xml/ws/security/IssuedTokenContext;
/*      */     //   2253	501	21	issuedToken	Lcom/sun/xml/ws/security/trust/GenericToken;
/*      */     //   2256	498	22	tokenElem	Ljavax/xml/soap/SOAPElement;
/*      */     //   2259	495	23	str	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   2262	492	24	strElem	Lorg/w3c/dom/Element;
/*      */     //   2265	489	25	issuedTokenElementFromMsg	Ljavax/xml/soap/SOAPElement;
/*      */     //   2271	483	26	ikb	Lcom/sun/xml/wss/impl/policy/mls/IssuedTokenKeyBinding;
/*      */     //   2278	476	27	ikbPolicyId	Ljava/lang/String;
/*      */     //   2284	470	28	tokCache	Ljava/util/HashMap;
/*      */     //   2293	461	29	tok	Ljava/lang/Object;
/*      */     //   2300	454	30	iTokenType	Ljava/lang/String;
/*      */     //   2363	391	31	includeIST	Z
/*      */     //   2637	117	32	imported	Lorg/w3c/dom/Element;
/*      */     //   2775	53	20	secTokenRef	Lcom/sun/xml/wss/core/SecurityTokenReference;
/*      */     //   2781	47	21	sctBinding	Lcom/sun/xml/wss/impl/policy/mls/SecureConversationTokenKeyBinding;
/*      */     //   2791	37	22	sctElement	Ljavax/xml/soap/SOAPElement;
/*      */     //   2833	24	20	ex	Ljavax/xml/soap/SOAPException;
/*      */     //   2859	24	20	ex	Lcom/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException;
/*      */     //   2885	24	20	ex	Ljava/security/NoSuchAlgorithmException;
/*      */     //   0	2911	0	context	Lcom/sun/xml/wss/impl/FilterProcessingContext;
/*      */     //   0	2911	1	keyBinding	Lcom/sun/xml/wss/impl/policy/mls/WSSPolicy;
/*      */     //   0	2911	2	originalKey	Ljava/security/Key;
/*      */     //   0	2911	3	signaturePolicy	Lcom/sun/xml/wss/impl/policy/mls/SignaturePolicy;
/*      */     //   0	2911	4	nxtSiblingContainer	[Lorg/w3c/dom/Node;
/*      */     //   0	2911	5	certInfo	Lcom/sun/xml/wss/impl/policy/mls/AuthenticationTokenPolicy$X509CertificateBinding;
/*      */     //   0	2911	6	dkt	Lcom/sun/xml/ws/security/DerivedKeyToken;
/*      */     //   3	2908	7	nextSibling	Lorg/w3c/dom/Node;
/*      */     //   6	2905	8	keyInfo	Ljavax/xml/crypto/dsig/keyinfo/KeyInfo;
/*      */     //   10	2901	9	keyEncAlgo	Ljava/lang/String;
/*      */     //   29	2882	10	referenceType	Ljava/lang/String;
/*      */     //   32	2879	11	strategy	Lcom/sun/xml/wss/impl/keyinfo/KeyInfoStrategy;
/*      */     //   35	2876	12	keyInfoBlock	Lcom/sun/xml/wss/core/KeyInfoHeaderBlock;
/*      */     //   41	2870	13	secureMessage	Lcom/sun/xml/wss/impl/SecurableSoapMessage;
/*      */     //   48	2863	14	securityHeader	Lcom/sun/xml/wss/core/SecurityHeader;
/*      */     //   53	2858	15	dsigHelper	Lcom/sun/xml/wss/impl/dsig/WSSPolicyConsumerImpl;
/*      */     //   66	2845	16	wss11Receiver	Z
/*      */     //   79	2832	17	wss11Sender	Z
/*      */     //   103	2808	18	sendEKSHA1	Z
/*      */     //   115	2796	19	wss10	Z
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   115	388	2831	javax/xml/soap/SOAPException
/*      */     //   115	388	2857	com/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException
/*      */     //   115	388	2883	java/security/NoSuchAlgorithmException
/*      */     //   389	1294	2831	javax/xml/soap/SOAPException
/*      */     //   389	1294	2857	com/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException
/*      */     //   389	1294	2883	java/security/NoSuchAlgorithmException
/*      */     //   753	789	792	java/lang/Exception
/*      */     //   1295	1947	2831	javax/xml/soap/SOAPException
/*      */     //   1295	1947	2857	com/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException
/*      */     //   1295	1947	2883	java/security/NoSuchAlgorithmException
/*      */     //   1948	2224	2831	javax/xml/soap/SOAPException
/*      */     //   1948	2224	2857	com/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException
/*      */     //   1948	2224	2883	java/security/NoSuchAlgorithmException
/*      */     //   2225	2753	2831	javax/xml/soap/SOAPException
/*      */     //   2225	2753	2857	com/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException
/*      */     //   2225	2753	2883	java/security/NoSuchAlgorithmException
/*      */     //   2754	2827	2831	javax/xml/soap/SOAPException
/*      */     //   2754	2827	2857	com/sun/org/apache/xml/internal/security/exceptions/Base64DecodingException
/*      */     //   2754	2827	2883	java/security/NoSuchAlgorithmException
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
/*      */   public static SOAPElement insertSCT(FilterProcessingContext context, SecureConversationTokenKeyBinding sctBinding, SecurityTokenReference secTokenRef) throws XWSSecurityException {
/* 1856 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*      */     
/* 1858 */     String sctPolicyId = sctBinding.getUUID();
/*      */ 
/*      */     
/* 1861 */     HashMap<String, SecurityContextTokenImpl> tokCache = context.getTokenCache();
/* 1862 */     SecurityContextTokenImpl sct = null;
/* 1863 */     sct = (SecurityContextTokenImpl)tokCache.get(sctPolicyId);
/* 1864 */     boolean tokenInserted = false;
/* 1865 */     SOAPElement sctElement = null;
/*      */     
/* 1867 */     IssuedTokenContext ictx = context.getSecureConversationContext();
/*      */     
/* 1869 */     if (sct == null) {
/* 1870 */       SecurityContextToken sct1 = (SecurityContextToken)ictx.getSecurityToken();
/* 1871 */       if (sct1 == null) {
/* 1872 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1347_NULL_SECURE_CONVERSATION_TOKEN());
/* 1873 */         throw new XWSSecurityException("SecureConversation Token not Found");
/*      */       } 
/*      */       
/* 1876 */       sct = new SecurityContextTokenImpl(secureMessage.getSOAPPart(), sct1.getIdentifier().toString(), sct1.getInstance(), sct1.getWsuId(), sct1.getExtElements());
/*      */ 
/*      */       
/* 1879 */       tokCache.put(sctPolicyId, sct);
/*      */     } else {
/* 1881 */       tokenInserted = true;
/*      */       
/* 1883 */       sctElement = secureMessage.getElementByWsuId(sct.getWsuId());
/*      */     } 
/*      */     
/* 1886 */     String sctWsuId = sct.getWsuId();
/* 1887 */     if (sctWsuId == null) {
/* 1888 */       sct.setId(secureMessage.generateId());
/*      */     }
/* 1890 */     sctWsuId = sct.getWsuId();
/* 1891 */     String iTokenType = sctBinding.getIncludeToken();
/* 1892 */     if (!SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT.equals(iTokenType)) if (!SecureConversationTokenKeyBinding.INCLUDE_ALWAYS.equals(iTokenType)) if (!SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2.equals(iTokenType)) { if (SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_VER2.equals(iTokenType))
/*      */           
/*      */           { 
/*      */ 
/*      */             
/* 1897 */             if (!tokenInserted) {
/* 1898 */               secureMessage.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)sct);
/*      */               
/* 1900 */               sctElement = secureMessage.getElementByWsuId(sct.getWsuId());
/*      */             } 
/*      */             
/* 1903 */             DirectReference directReference1 = new DirectReference();
/*      */             
/* 1905 */             directReference1.setURI("#" + sctWsuId);
/* 1906 */             secTokenRef.setReference((ReferenceElement)directReference1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1914 */             return sctElement; }  DirectReference reference = new DirectReference(); reference.setSCTURI(sct.getIdentifier().toString(), sct.getInstance()); secTokenRef.setReference((ReferenceElement)reference); return sctElement; }    if (!tokenInserted) { secureMessage.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)sct); sctElement = secureMessage.getElementByWsuId(sct.getWsuId()); }  DirectReference directReference = new DirectReference(); directReference.setURI("#" + sctWsuId); secTokenRef.setReference((ReferenceElement)directReference); return sctElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static KeyInfo handleX509Binding(FilterProcessingContext context, SignaturePolicy signaturePolicy, AuthenticationTokenPolicy.X509CertificateBinding certInfo, Node[] nxtSiblingContainer) throws XWSSecurityException {
/* 1922 */     Node nextSibling = null;
/* 1923 */     KeyInfo keyInfo = null;
/* 1924 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/* 1925 */     SecurityHeader securityHeader = secureMessage.findOrCreateSecurityHeader();
/* 1926 */     WSSPolicyConsumerImpl dsigHelper = WSSPolicyConsumerImpl.getInstance();
/*      */     
/* 1928 */     HashMap<String, X509SecurityToken> tokenCache = context.getTokenCache();
/* 1929 */     HashMap<String, X509SecurityToken> insertedX509Cache = context.getInsertedX509Cache();
/* 1930 */     String x509id = certInfo.getUUID();
/* 1931 */     if (x509id == null || x509id.equals("")) {
/* 1932 */       x509id = secureMessage.generateId();
/*      */     }
/*      */     
/* 1935 */     SecurityUtil.checkIncludeTokenPolicy(context, certInfo, x509id);
/*      */     
/* 1937 */     String referenceType = certInfo.getReferenceType();
/* 1938 */     String strId = certInfo.getSTRID();
/* 1939 */     if (strId == null) {
/* 1940 */       strId = secureMessage.generateId();
/*      */     }
/*      */     
/*      */     try {
/* 1944 */       if (referenceType.equals("Direct")) {
/* 1945 */         DirectReference reference = new DirectReference();
/*      */         
/* 1947 */         String valueType = certInfo.getValueType();
/* 1948 */         if (valueType == null || valueType.equals("")) {
/* 1949 */           valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*      */         }
/*      */         
/* 1952 */         reference.setValueType(valueType);
/*      */ 
/*      */         
/* 1955 */         String id = certInfo.getUUID();
/* 1956 */         if (id == null || id.equals("")) {
/* 1957 */           id = secureMessage.generateId();
/*      */         }
/* 1959 */         reference.setURI("#" + id);
/* 1960 */         SecurityTokenReference secTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 1961 */         secTokenRef.setReference((ReferenceElement)reference);
/* 1962 */         secTokenRef.setWsuId(strId);
/* 1963 */         keyInfo = dsigHelper.constructKeyInfo((MLSPolicy)signaturePolicy, secTokenRef);
/* 1964 */         X509SecurityToken token = null;
/* 1965 */         token = (X509SecurityToken)tokenCache.get(id);
/* 1966 */         if (token == null) {
/* 1967 */           valueType = certInfo.getValueType();
/* 1968 */           if (valueType == null || valueType.equals(""))
/*      */           {
/* 1970 */             valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*      */           }
/* 1972 */           token = new X509SecurityToken(secureMessage.getSOAPPart(), certInfo.getX509Certificate(), id, valueType);
/* 1973 */           tokenCache.put(id, token);
/*      */         } 
/* 1975 */         if (insertedX509Cache.get(id) == null) {
/* 1976 */           secureMessage.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)token);
/* 1977 */           insertedX509Cache.put(id, token);
/*      */         } 
/* 1979 */         nextSibling = token.getAsSoapElement().getNextSibling();
/* 1980 */         nxtSiblingContainer[0] = nextSibling;
/* 1981 */         return keyInfo;
/* 1982 */       }  if (referenceType.equals("Identifier")) {
/* 1983 */         String valueType = certInfo.getValueType();
/* 1984 */         if (valueType == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1" || valueType.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1")) {
/* 1985 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1333_UNSUPPORTED_KEYIDENTIFER_X_509_V_1());
/* 1986 */           throw new XWSSecurityException("Key Identifier reference Type is not allowed for X509v1 Certificates");
/*      */         } 
/* 1988 */         KeyIdentifierStrategy keyIdentifier = new KeyIdentifierStrategy(certInfo.getCertificateIdentifier(), true);
/*      */         
/* 1990 */         keyIdentifier.setCertificate(certInfo.getX509Certificate());
/* 1991 */         SecurityTokenReference secTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 1992 */         keyIdentifier.insertKey(secTokenRef, secureMessage);
/* 1993 */         secTokenRef.setWsuId(strId);
/* 1994 */         X509SubjectKeyIdentifier re = (X509SubjectKeyIdentifier)secTokenRef.getReference();
/* 1995 */         String id = re.getReferenceValue();
/* 1996 */         tokenCache.put(id, re);
/* 1997 */         re.setCertificate(certInfo.getX509Certificate());
/* 1998 */         keyInfo = dsigHelper.constructKeyInfo((MLSPolicy)signaturePolicy, secTokenRef);
/* 1999 */         nextSibling = securityHeader.getNextSiblingOfTimestamp();
/* 2000 */         nxtSiblingContainer[0] = nextSibling;
/* 2001 */         return keyInfo;
/* 2002 */       }  if (referenceType.equals("Thumbprint")) {
/* 2003 */         String valueType = certInfo.getValueType();
/* 2004 */         if (valueType == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1" || valueType.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1")) {
/* 2005 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1348_ILLEGAL_THUMBPRINT_X_509_V_1());
/* 2006 */           throw new XWSSecurityException("Thumb reference Type is not allowed for X509v1 Certificates");
/*      */         } 
/* 2008 */         KeyIdentifierStrategy keyIdentifier = new KeyIdentifierStrategy(certInfo.getCertificateIdentifier(), true, true);
/* 2009 */         keyIdentifier.setCertificate(certInfo.getX509Certificate());
/* 2010 */         SecurityTokenReference secTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 2011 */         keyIdentifier.insertKey(secTokenRef, secureMessage);
/* 2012 */         secTokenRef.setWsuId(strId);
/* 2013 */         X509ThumbPrintIdentifier re = (X509ThumbPrintIdentifier)secTokenRef.getReference();
/* 2014 */         String id = re.getReferenceValue();
/* 2015 */         tokenCache.put(id, re);
/* 2016 */         re.setCertificate(certInfo.getX509Certificate());
/* 2017 */         keyInfo = dsigHelper.constructKeyInfo((MLSPolicy)signaturePolicy, secTokenRef);
/* 2018 */         nextSibling = securityHeader.getNextSiblingOfTimestamp();
/* 2019 */         nxtSiblingContainer[0] = nextSibling;
/* 2020 */         return keyInfo;
/* 2021 */       }  if (referenceType.equals("IssuerSerialNumber")) {
/* 2022 */         X509Certificate xCert = certInfo.getX509Certificate();
/* 2023 */         X509IssuerSerial xis = new X509IssuerSerial(secureMessage.getSOAPPart(), xCert.getIssuerDN().getName(), xCert.getSerialNumber());
/*      */         
/* 2025 */         SecurityTokenReference secTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 2026 */         secTokenRef.setReference((ReferenceElement)xis);
/* 2027 */         secTokenRef.setWsuId(strId);
/* 2028 */         xis.setCertificate(xCert);
/* 2029 */         tokenCache.put(xis.getIssuerName() + xis.getSerialNumber(), xis);
/* 2030 */         keyInfo = dsigHelper.constructKeyInfo((MLSPolicy)signaturePolicy, secTokenRef);
/* 2031 */         nextSibling = securityHeader.getNextSiblingOfTimestamp();
/* 2032 */         nxtSiblingContainer[0] = nextSibling;
/* 2033 */         return keyInfo;
/*      */       } 
/* 2035 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1308_UNSUPPORTED_REFERENCE_MECHANISM());
/* 2036 */       throw new XWSSecurityException("Reference type " + referenceType + "not supported");
/*      */     }
/* 2038 */     catch (Exception e) {
/* 2039 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1349_ERROR_HANDLING_X_509_BINDING(), e);
/* 2040 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String getEKSHA1Ref(FilterProcessingContext context) {
/* 2045 */     String ekSha1Ref = null;
/* 2046 */     ekSha1Ref = (String)context.getExtraneousProperty("EKSHA1Value");
/* 2047 */     return ekSha1Ref;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\SignatureProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */