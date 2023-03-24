/*      */ package com.sun.xml.ws.security.impl.policy;
/*      */ 
/*      */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.ws.api.policy.ModelGenerator;
/*      */ import com.sun.xml.ws.policy.AssertionSet;
/*      */ import com.sun.xml.ws.policy.Policy;
/*      */ import com.sun.xml.ws.policy.PolicyAssertion;
/*      */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelMarshaller;
/*      */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*      */ import com.sun.xml.ws.security.policy.AlgorithmSuiteValue;
/*      */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*      */ import com.sun.xml.wss.WSITXMLFactory;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.util.Arrays;
/*      */ import java.util.UUID;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.stream.XMLOutputFactory;
/*      */ import javax.xml.stream.XMLStreamWriter;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import org.w3c.dom.Document;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PolicyUtil
/*      */ {
/*      */   public static boolean isSecurityPolicyNS(PolicyAssertion pa, SecurityPolicyVersion spVersion) {
/*   79 */     if (spVersion.namespaceUri.equals(pa.getName().getNamespaceURI()) || "http://schemas.microsoft.com/ws/2005/07/securitypolicy".equalsIgnoreCase(pa.getName().getNamespaceURI()))
/*      */     {
/*   81 */       return true;
/*      */     }
/*   83 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSunPolicyNS(PolicyAssertion pa) {
/*   87 */     if ("http://schemas.sun.com/2006/03/wss/server".equals(pa.getName().getNamespaceURI()) || "http://schemas.sun.com/2006/03/wss/client".equals(pa.getName().getNamespaceURI()))
/*      */     {
/*   89 */       return true;
/*      */     }
/*   91 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAddressingNS(PolicyAssertion pa) {
/*   95 */     if (AddressingVersion.MEMBER.getNsUri().equals(pa.getName().getNamespaceURI())) {
/*   96 */       return true;
/*      */     }
/*   98 */     if (AddressingVersion.W3C.getNsUri().equals(pa.getName().getNamespaceURI())) {
/*   99 */       return true;
/*      */     }
/*  101 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTrustNS(PolicyAssertion pa) {
/*  105 */     if ("http://schemas.xmlsoap.org/ws/2005/02/trust".equals(pa.getName().getNamespaceURI()) || "http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(pa.getName().getNamespaceURI()))
/*      */     {
/*  107 */       return true;
/*      */     }
/*  109 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMEXNS(PolicyAssertion assertion) {
/*  113 */     if ("http://schemas.xmlsoap.org/ws/2004/09/mex".equals(assertion.getName().getNamespaceURI())) {
/*  114 */       return true;
/*      */     }
/*  116 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isUtilityNS(PolicyAssertion pa) {
/*  120 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis- 200401-wss-wssecurity-utility-1.0.xsd".equals(pa.getName().getNamespaceURI())) {
/*  121 */       return true;
/*      */     }
/*  123 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isXpathNS(PolicyAssertion pa) {
/*  127 */     if ("http://www.w3.org/TR/1999/REC-xpath-19991116".equals(pa.getName().getNamespaceURI())) {
/*  128 */       return true;
/*      */     }
/*  130 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAlgorithmAssertion(PolicyAssertion pa, SecurityPolicyVersion spVersion) {
/*  134 */     if (isSecurityPolicyNS(pa, spVersion) && 
/*  135 */       pa.getName().getLocalPart().equals("AlgorithmSuite")) {
/*  136 */       return true;
/*      */     }
/*      */     
/*  139 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isToken(PolicyAssertion pa, SecurityPolicyVersion spVersion) {
/*  143 */     if (!isSecurityPolicyNS(pa, spVersion)) {
/*  144 */       return false;
/*      */     }
/*      */     
/*  147 */     if (pa.getName().getLocalPart().equals("EncryptionToken"))
/*  148 */       return true; 
/*  149 */     if (pa.getName().getLocalPart().equals("SignatureToken"))
/*  150 */       return true; 
/*  151 */     if (pa.getName().getLocalPart().equals("InitiatorToken"))
/*  152 */       return true; 
/*  153 */     if (pa.getName().getLocalPart().equals("InitiatorSignatureToken"))
/*  154 */       return true; 
/*  155 */     if (pa.getName().getLocalPart().equals("InitiatorEncryptionToken"))
/*  156 */       return true; 
/*  157 */     if (pa.getName().getLocalPart().equals("HttpsToken"))
/*  158 */       return true; 
/*  159 */     if (pa.getName().getLocalPart().equals("IssuedToken"))
/*  160 */       return true; 
/*  161 */     if (pa.getName().getLocalPart().equals("KerberosToken"))
/*  162 */       return true; 
/*  163 */     if (pa.getName().getLocalPart().equals("ProtectionToken"))
/*  164 */       return true; 
/*  165 */     if (pa.getName().getLocalPart().equals("RecipientToken"))
/*  166 */       return true; 
/*  167 */     if (pa.getName().getLocalPart().equals("RecipientSignatureToken"))
/*  168 */       return true; 
/*  169 */     if (pa.getName().getLocalPart().equals("RecipientEncryptionToken"))
/*  170 */       return true; 
/*  171 */     if (pa.getName().getLocalPart().equals("SupportingTokens"))
/*  172 */       return true; 
/*  173 */     if (pa.getName().getLocalPart().equals("SC10SecurityContextToken"))
/*  174 */       return true; 
/*  175 */     if (pa.getName().getLocalPart().equals("SamlToken"))
/*  176 */       return true; 
/*  177 */     if (pa.getName().getLocalPart().equals("UsernameToken"))
/*  178 */       return true; 
/*  179 */     if (pa.getName().getLocalPart().equals("X509Token"))
/*  180 */       return true; 
/*  181 */     if (pa.getName().getLocalPart().equals("SecureConversationToken"))
/*  182 */       return true; 
/*  183 */     if (pa.getName().getLocalPart().equals("TransportToken"))
/*  184 */       return true; 
/*  185 */     if (pa.getName().getLocalPart().equals("RsaToken"))
/*  186 */       return true; 
/*  187 */     if (pa.getName().getLocalPart().equals("KeyValueToken")) {
/*  188 */       return true;
/*      */     }
/*  190 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isBootstrapPolicy(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  194 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  195 */       return false;
/*      */     }
/*      */     
/*  198 */     if (assertion.getName().getLocalPart().equals("BootstrapPolicy")) {
/*  199 */       return true;
/*      */     }
/*  201 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTarget(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  205 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  206 */       return false;
/*      */     }
/*      */     
/*  209 */     String name = assertion.getName().getLocalPart();
/*  210 */     if (name.equals("EncryptedParts") || name.equals("SignedParts") || name.equals("SignedElements") || name.equals("EncryptedElements"))
/*      */     {
/*      */ 
/*      */       
/*  214 */       return true;
/*      */     }
/*  216 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isXPath(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  220 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  221 */       return false;
/*      */     }
/*      */     
/*  224 */     if (assertion.getName().getLocalPart().equals("XPath")) {
/*  225 */       return true;
/*      */     }
/*  227 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isXPathFilter20(PolicyAssertion assertion) {
/*  231 */     if (!isXpathNS(assertion)) {
/*  232 */       return false;
/*      */     }
/*      */     
/*  235 */     if (assertion.getName().getLocalPart().equals("XPathFilter20")) {
/*  236 */       return true;
/*      */     }
/*  238 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequiredKey(PolicyAssertion assertion) {
/*  242 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTokenType(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  247 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  248 */       return false;
/*      */     }
/*      */     
/*  251 */     if (assertion.getName().getLocalPart().equals("WssX509V1Token10"))
/*  252 */       return true; 
/*  253 */     if (assertion.getName().getLocalPart().equals("WssX509V3Token10"))
/*  254 */       return true; 
/*  255 */     if (assertion.getName().getLocalPart().equals("WssX509Pkcs7Token10"))
/*  256 */       return true; 
/*  257 */     if (assertion.getName().getLocalPart().equals("WssX509PkiPathV1Token10"))
/*  258 */       return true; 
/*  259 */     if (assertion.getName().getLocalPart().equals("WssX509V1Token11"))
/*  260 */       return true; 
/*  261 */     if (assertion.getName().getLocalPart().equals("WssX509V3Token11"))
/*  262 */       return true; 
/*  263 */     if (assertion.getName().getLocalPart().equals("WssX509Pkcs7Token11"))
/*  264 */       return true; 
/*  265 */     if (assertion.getName().getLocalPart().equals("WssX509PkiPathV1Token11")) {
/*  266 */       return true;
/*      */     }
/*  268 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTokenReferenceType(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  273 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  274 */       return false;
/*      */     }
/*      */     
/*  277 */     if (assertion.getName().getLocalPart().equals("RequireKeyIdentifierReference"))
/*  278 */       return true; 
/*  279 */     if (assertion.getName().getLocalPart().equals("RequireThumbprintReference"))
/*  280 */       return true; 
/*  281 */     if (assertion.getName().getLocalPart().equals("RequireEmbeddedTokenReference"))
/*  282 */       return true; 
/*  283 */     if (assertion.getName().getLocalPart().equals("RequireIssuerSerialReference")) {
/*  284 */       return true;
/*      */     }
/*  286 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isUsernameTokenType(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  290 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  291 */       return false;
/*      */     }
/*      */     
/*  294 */     if (assertion.getName().getLocalPart().equals("WssUsernameToken10") || assertion.getName().getLocalPart().equals("WssUsernameToken11"))
/*      */     {
/*  296 */       return true;
/*      */     }
/*  298 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean useCreated(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  302 */     if (assertion.getName().getLocalPart().equals("Created"))
/*      */     {
/*  304 */       return true;
/*      */     }
/*  306 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean useNonce(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  310 */     if (assertion.getName().getLocalPart().equals("Nonce"))
/*      */     {
/*  312 */       return true;
/*      */     }
/*  314 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isHttpsToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  318 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  319 */       return false;
/*      */     }
/*      */     
/*  322 */     if (assertion.getName().getLocalPart().equals("HttpsToken")) {
/*  323 */       return true;
/*      */     }
/*  325 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSecurityContextToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  329 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  330 */       return false;
/*      */     }
/*      */     
/*  333 */     if (token.getName().getLocalPart().equals("SecurityContextToken")) {
/*  334 */       return true;
/*      */     }
/*  336 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSecurityContextTokenType(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  340 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  341 */       return false;
/*      */     }
/*  343 */     String localPart = token.getName().getLocalPart();
/*  344 */     if (localPart.equals("SC10SecurityContextToken")) {
/*  345 */       return true;
/*      */     }
/*  347 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKerberosToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  351 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  352 */       return false;
/*      */     }
/*      */     
/*  355 */     if (token.getName().getLocalPart().equals("KerberosToken")) {
/*  356 */       return true;
/*      */     }
/*  358 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKerberosTokenType(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  362 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  363 */       return false;
/*      */     }
/*  365 */     String localPart = token.getName().getLocalPart();
/*  366 */     if (localPart.equals("WssKerberosV5ApReqToken11"))
/*  367 */       return true; 
/*  368 */     if (localPart.equals("WssGssKerberosV5ApReqToken11")) {
/*  369 */       return true;
/*      */     }
/*  371 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKeyValueTokenType(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  375 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  376 */       return false;
/*      */     }
/*  378 */     String localPart = token.getName().getLocalPart();
/*  379 */     if (localPart.equals("RsaKeyValue") && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(spVersion.namespaceUri))
/*      */     {
/*  381 */       return true;
/*      */     }
/*  383 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRelToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  387 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  388 */       return false;
/*      */     }
/*      */     
/*  391 */     if (token.getName().getLocalPart().equals("RelToken")) {
/*  392 */       return true;
/*      */     }
/*  394 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRelTokenType(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  398 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  399 */       return false;
/*      */     }
/*  401 */     String localPart = token.getName().getLocalPart();
/*  402 */     if (localPart.equals("WssRelV10Token10"))
/*  403 */       return true; 
/*  404 */     if (localPart.equals("WssRelV10Token11"))
/*  405 */       return true; 
/*  406 */     if (localPart.equals("WssRelV20Token10"))
/*  407 */       return true; 
/*  408 */     if (localPart.equals("WssRelV20Token11")) {
/*  409 */       return true;
/*      */     }
/*  411 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isIncludeTimestamp(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  415 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  416 */       return false;
/*      */     }
/*      */     
/*  419 */     if (assertion.getName().getLocalPart().equals("IncludeTimestamp")) {
/*  420 */       return true;
/*      */     }
/*  422 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean disableTimestampSigning(PolicyAssertion assertion) {
/*  426 */     if (!isSunPolicyNS(assertion)) {
/*  427 */       return false;
/*      */     }
/*      */     
/*  430 */     if (assertion.getName().getLocalPart().equals("DisableTimestampSigning")) {
/*  431 */       return true;
/*      */     }
/*  433 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptBeforeSign(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  437 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  438 */       return false;
/*      */     }
/*      */     
/*  441 */     if (assertion.getName().getLocalPart().equals("EncryptBeforeSigning")) {
/*  442 */       return true;
/*      */     }
/*  444 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignBeforeEncrypt(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  448 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  449 */       return false;
/*      */     }
/*      */     
/*  452 */     if (assertion.getName().getLocalPart().equals("SignBeforeEncrypting")) {
/*  453 */       return true;
/*      */     }
/*  455 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isContentOnlyAssertion(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  459 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  460 */       return false;
/*      */     }
/*      */     
/*  463 */     if (assertion.getName().getLocalPart().equals("OnlySignEntireHeadersAndBody")) {
/*  464 */       return true;
/*      */     }
/*  466 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMessageLayout(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  470 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  471 */       return false;
/*      */     }
/*      */     
/*  474 */     if (assertion.getName().getLocalPart().equals("Layout")) {
/*  475 */       return true;
/*      */     }
/*  477 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptParts(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  481 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  482 */       return false;
/*      */     }
/*      */     
/*  485 */     if (assertion.getName().getLocalPart().equals("EncryptedParts")) {
/*  486 */       return true;
/*      */     }
/*  488 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptedElements(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  492 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  493 */       return false;
/*      */     }
/*      */     
/*  496 */     if (assertion.getName().getLocalPart().equals("EncryptedElements")) {
/*  497 */       return true;
/*      */     }
/*  499 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignedParts(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  503 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  504 */       return false;
/*      */     }
/*      */     
/*  507 */     if (assertion.getName().getLocalPart().equals("SignedParts")) {
/*  508 */       return true;
/*      */     }
/*  510 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignedElements(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  514 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  515 */       return false;
/*      */     }
/*      */     
/*  518 */     if (assertion.getName().getLocalPart().equals("SignedElements")) {
/*  519 */       return true;
/*      */     }
/*  521 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSignedSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  526 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  527 */       return false;
/*      */     }
/*      */     
/*  530 */     if (policyAssertion.getName().getLocalPart().equals("SignedSupportingTokens")) {
/*  531 */       return true;
/*      */     }
/*  533 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEndorsedSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  537 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  538 */       return false;
/*      */     }
/*      */     
/*  541 */     if (policyAssertion.getName().getLocalPart().equals("EndorsingSupportingTokens")) {
/*  542 */       return true;
/*      */     }
/*  544 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignedEndorsingSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  548 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  549 */       return false;
/*      */     }
/*      */     
/*  552 */     if (policyAssertion.getName().getLocalPart().equals("SignedEndorsingSupportingTokens")) {
/*  553 */       return true;
/*      */     }
/*  555 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignedEncryptedSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  559 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  560 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  564 */     if (policyAssertion.getName().getLocalPart().equals("SignedEncryptedSupportingTokens") && policyAssertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  566 */       return true;
/*      */     }
/*  568 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptedSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  572 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  573 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  577 */     if (policyAssertion.getName().getLocalPart().equals("EncryptedSupportingTokens") && policyAssertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  579 */       return true;
/*      */     }
/*  581 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEndorsingEncryptedSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  585 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  586 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  590 */     if (policyAssertion.getName().getLocalPart().equals("EndorsingEncryptedSupportingTokens") && policyAssertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  592 */       return true;
/*      */     }
/*  594 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignedEndorsingEncryptedSupportingToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  598 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  599 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  603 */     if (policyAssertion.getName().getLocalPart().equals("SignedEndorsingEncryptedSupportingTokens") && policyAssertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  605 */       return true;
/*      */     }
/*  607 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isBinding(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  613 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  614 */       return false;
/*      */     }
/*      */     
/*  617 */     String name = policyAssertion.getName().getLocalPart();
/*  618 */     if (name.equals("SymmetricBinding") || name.equals("AsymmetricBinding") || name.equals("TransportBinding"))
/*      */     {
/*      */ 
/*      */       
/*  622 */       return true;
/*      */     }
/*  624 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isUsernameToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  628 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  629 */       return false;
/*      */     }
/*      */     
/*  632 */     if (token.getName().getLocalPart().equals("UsernameToken")) {
/*  633 */       return true;
/*      */     }
/*  635 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSamlToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  639 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  640 */       return false;
/*      */     }
/*      */     
/*  643 */     if (token.getName().getLocalPart().equals("SamlToken")) {
/*  644 */       return true;
/*      */     }
/*  646 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSamlTokenType(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  650 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  651 */       return false;
/*      */     }
/*  653 */     String localPart = token.getName().getLocalPart();
/*  654 */     if (localPart.equals("WssSamlV10Token10"))
/*  655 */       return true; 
/*  656 */     if (localPart.equals("WssSamlV10Token11"))
/*  657 */       return true; 
/*  658 */     if (localPart.equals("WssSamlV11Token10"))
/*  659 */       return true; 
/*  660 */     if (localPart.equals("WssSamlV20Token11"))
/*  661 */       return true; 
/*  662 */     if (localPart.equals("WssSamlV11Token11")) {
/*  663 */       return true;
/*      */     }
/*  665 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isIssuedToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  669 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  670 */       return false;
/*      */     }
/*      */     
/*  673 */     if (token.getName().getLocalPart().equals("IssuedToken")) {
/*  674 */       return true;
/*      */     }
/*  676 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSecureConversationToken(PolicyAssertion token, SecurityPolicyVersion spVersion) {
/*  680 */     if (!isSecurityPolicyNS(token, spVersion)) {
/*  681 */       return false;
/*      */     }
/*      */     
/*  684 */     if (token.getName().getLocalPart().equals("SecureConversationToken")) {
/*  685 */       return true;
/*      */     }
/*  687 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isX509Token(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  691 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  692 */       return false;
/*      */     }
/*      */     
/*  695 */     if (policyAssertion.getName().getLocalPart().equals("X509Token")) {
/*  696 */       return true;
/*      */     }
/*  698 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKeyValueToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  702 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  703 */       return false;
/*      */     }
/*  705 */     if (policyAssertion.getName().getLocalPart().equals("KeyValueToken") && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(spVersion.namespaceUri))
/*      */     {
/*  707 */       return true;
/*      */     }
/*  709 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRsaToken(PolicyAssertion policyAssertion, SecurityPolicyVersion spVersion) {
/*  714 */     if (!isSecurityPolicyNS(policyAssertion, spVersion)) {
/*  715 */       return false;
/*      */     }
/*  717 */     if (policyAssertion.getName().getLocalPart().equals("RsaToken") && SecurityPolicyVersion.MS_SECURITYPOLICY200507.namespaceUri.equals(spVersion.namespaceUri))
/*      */     {
/*  719 */       return true;
/*      */     }
/*  721 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAsymmetricBinding(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  725 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  726 */       return false;
/*      */     }
/*      */     
/*  729 */     if (assertion.getName().getLocalPart().equals("AsymmetricBinding")) {
/*  730 */       return true;
/*      */     }
/*  732 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAsymmetricBinding(QName assertion, SecurityPolicyVersion spVersion) {
/*  736 */     if (assertion.getLocalPart().equals("AsymmetricBinding") && assertion.getNamespaceURI().equals(spVersion.namespaceUri))
/*      */     {
/*  738 */       return true;
/*      */     }
/*  740 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTransportBinding(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  744 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  745 */       return false;
/*      */     }
/*      */     
/*  748 */     if (assertion.getName().getLocalPart().equals("TransportBinding")) {
/*  749 */       return true;
/*      */     }
/*  751 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTransportBinding(QName assertion, SecurityPolicyVersion spVersion) {
/*  755 */     if (assertion.getLocalPart().equals("TransportBinding") && assertion.getNamespaceURI().equals(spVersion.namespaceUri))
/*      */     {
/*  757 */       return true;
/*      */     }
/*  759 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSymmetricBinding(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  763 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  764 */       return false;
/*      */     }
/*      */     
/*  767 */     if (assertion.getName().getLocalPart().equals("SymmetricBinding")) {
/*  768 */       return true;
/*      */     }
/*  770 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSymmetricBinding(QName assertion, SecurityPolicyVersion spVersion) {
/*  774 */     if (assertion.getLocalPart().equals("SymmetricBinding") && assertion.getNamespaceURI().equals(spVersion.namespaceUri))
/*      */     {
/*  776 */       return true;
/*      */     }
/*  778 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSupportingTokens(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  782 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  783 */       return false;
/*      */     }
/*      */     
/*  786 */     if (isSignedSupportingToken(assertion, spVersion) || isEndorsedSupportingToken(assertion, spVersion) || isSignedEndorsingSupportingToken(assertion, spVersion) || isSupportingToken(assertion, spVersion) || isSignedEncryptedSupportingToken(assertion, spVersion) || isEncryptedSupportingToken(assertion, spVersion) || isEndorsingEncryptedSupportingToken(assertion, spVersion) || isSignedEndorsingEncryptedSupportingToken(assertion, spVersion))
/*      */     {
/*      */ 
/*      */       
/*  790 */       return true;
/*      */     }
/*  792 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSupportingToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  797 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  798 */       return false;
/*      */     }
/*      */     
/*  801 */     if (assertion.getName().getLocalPart().equals("SupportingTokens")) {
/*  802 */       return true;
/*      */     }
/*  804 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSupportClientChallenge(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  809 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  810 */       return false;
/*      */     }
/*      */     
/*  813 */     if (assertion.getName().getLocalPart().equals("MustSupportClientChallenge")) {
/*  814 */       return true;
/*      */     }
/*  816 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSupportServerChallenge(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  820 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  821 */       return false;
/*      */     }
/*      */     
/*  824 */     if (assertion.getName().getLocalPart().equals("MustSupportServerChallenge")) {
/*  825 */       return true;
/*      */     }
/*  827 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isWSS10PolicyContent(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  831 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  832 */       return false;
/*      */     }
/*      */     
/*  835 */     if (assertion.getName().getLocalPart().equals("MustSupportRefKeyIdentifier"))
/*  836 */       return true; 
/*  837 */     if (assertion.getName().getLocalPart().equals("MustSupportRefIssuerSerial"))
/*  838 */       return true; 
/*  839 */     if (assertion.getName().getLocalPart().equals("RequireExternalUriReference"))
/*  840 */       return true; 
/*  841 */     if (assertion.getName().getLocalPart().equals("RequireEmbeddedTokenReference")) {
/*  842 */       return true;
/*      */     }
/*  844 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isWSS11PolicyContent(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  848 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  849 */       return false;
/*      */     }
/*      */     
/*  852 */     if (assertion.getName().getLocalPart().equals("MustSupportRefKeyIdentifier"))
/*  853 */       return true; 
/*  854 */     if (assertion.getName().getLocalPart().equals("MustSupportRefIssuerSerial"))
/*  855 */       return true; 
/*  856 */     if (assertion.getName().getLocalPart().equals("MustSupportRefThumbprint"))
/*  857 */       return true; 
/*  858 */     if (assertion.getName().getLocalPart().equals("MustSupportRefEncryptedKey"))
/*  859 */       return true; 
/*  860 */     if (assertion.getName().getLocalPart().equals("RequireSignatureConfirmation"))
/*  861 */       return true; 
/*  862 */     if (assertion.getName().getLocalPart().equals("RequireExternalUriReference"))
/*  863 */       return true; 
/*  864 */     if (assertion.getName().getLocalPart().equals("RequireEmbeddedTokenReference")) {
/*  865 */       return true;
/*      */     }
/*  867 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRequireClientCertificate(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  874 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  875 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  879 */     if (assertion.getName().getLocalPart().equals("RequireClientCertificate") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  881 */       return true;
/*      */     }
/*  883 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isHttpBasicAuthentication(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  890 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  891 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  895 */     if (assertion.getName().getLocalPart().equals("HttpBasicAuthentication") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  897 */       return true;
/*      */     }
/*  899 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isHttpDigestAuthentication(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  906 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  907 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  911 */     if (assertion.getName().getLocalPart().equals("HttpDigestAuthentication") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  913 */       return true;
/*      */     }
/*  915 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireClientEntropy(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  919 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  920 */       return false;
/*      */     }
/*      */     
/*  923 */     if (assertion.getName().getLocalPart().equals("RequireClientEntropy")) {
/*  924 */       return true;
/*      */     }
/*  926 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireServerEntropy(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  930 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  931 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  936 */     if (assertion.getName().getLocalPart().equals("RequireServerEntropy")) {
/*  937 */       return true;
/*      */     }
/*  939 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSupportIssuedTokens(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  943 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  944 */       return false;
/*      */     }
/*      */     
/*  947 */     if (assertion.getName().getLocalPart().equals("MustSupportIssuedTokens")) {
/*  948 */       return true;
/*      */     }
/*  950 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequestSecurityTokenCollection(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  954 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  955 */       return false;
/*      */     }
/*      */     
/*  958 */     if (assertion.getName().getLocalPart().equals("RequireRequestSecurityTokenCollection") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  960 */       return true;
/*      */     }
/*  962 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAppliesTo(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  966 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  967 */       return false;
/*      */     }
/*      */     
/*  970 */     if (assertion.getName().getLocalPart().equals("RequireAppliesTo") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  972 */       return true;
/*      */     }
/*  974 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isIssuer(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  978 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  979 */       return false;
/*      */     }
/*      */     
/*  982 */     if (assertion.getName().getLocalPart().equals("Issuer")) {
/*  983 */       return true;
/*      */     }
/*  985 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isIssuerName(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/*  989 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/*  990 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  994 */     if (assertion.getName().getLocalPart().equals("IssuerName") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/*  996 */       return true;
/*      */     }
/*  998 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isWSS10(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1002 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1003 */       return false;
/*      */     }
/* 1005 */     if (assertion.getName().getLocalPart().equals("Wss10")) {
/* 1006 */       return true;
/*      */     }
/* 1008 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isWSS11(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1012 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1013 */       return false;
/*      */     }
/*      */     
/* 1016 */     if (assertion.getName().getLocalPart().equals("Wss11")) {
/* 1017 */       return true;
/*      */     }
/* 1019 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTrust10(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1023 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1024 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1028 */     if (assertion.getName().getLocalPart().equals("Trust10") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri))
/*      */     {
/* 1030 */       return true;
/*      */     }
/* 1032 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTrust13(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1036 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1037 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1041 */     if (assertion.getName().getLocalPart().equals("Trust13") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1043 */       return true;
/*      */     }
/* 1045 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMustNotSendCancel(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1049 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1050 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1054 */     if (assertion.getName().getLocalPart().equals("MustNotSendCancel") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1056 */       return true;
/*      */     }
/* 1058 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMustNotSendRenew(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1062 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1063 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1067 */     if (assertion.getName().getLocalPart().equals("MustNotSendRenew") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1069 */       return true;
/*      */     }
/* 1071 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isBody(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1075 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1076 */       return false;
/*      */     }
/*      */     
/* 1079 */     if (assertion.getName().getLocalPart().equals("Body")) {
/* 1080 */       return true;
/*      */     }
/* 1082 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAttachments(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1086 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1087 */       return false;
/*      */     }
/*      */     
/* 1090 */     if (assertion.getName().getLocalPart().equals("Attachments") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1092 */       return true;
/*      */     }
/* 1094 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAttachmentCompleteTransform(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1098 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1099 */       return false;
/*      */     }
/*      */     
/* 1102 */     if (assertion.getName().getLocalPart().equals("AttachmentCompleteSignatureTransform") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1104 */       return true;
/*      */     }
/* 1106 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAttachmentContentTransform(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1110 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1111 */       return false;
/*      */     }
/*      */     
/* 1114 */     if (assertion.getName().getLocalPart().equals("ContentSignatureTransform") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1116 */       return true;
/*      */     }
/* 1118 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireDerivedKeys(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1122 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1123 */       return false;
/*      */     }
/*      */     
/* 1126 */     if ("RequireDerivedKeys".toString().equals(assertion.getName().getLocalPart().toString())) {
/* 1127 */       return true;
/*      */     }
/* 1129 */     return false;
/*      */   }
/*      */   
/*      */   public static AlgorithmSuiteValue isValidAlgorithmSuiteValue(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1133 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1134 */       return null;
/*      */     }
/*      */     
/* 1137 */     if (assertion.getName().getLocalPart().equals("Basic256"))
/* 1138 */       return AlgorithmSuiteValue.Basic256; 
/* 1139 */     if (assertion.getName().getLocalPart().equals("Basic192"))
/* 1140 */       return AlgorithmSuiteValue.Basic192; 
/* 1141 */     if (assertion.getName().getLocalPart().equals("Basic128"))
/* 1142 */       return AlgorithmSuiteValue.Basic128; 
/* 1143 */     if (assertion.getName().getLocalPart().equals("TripleDes"))
/* 1144 */       return AlgorithmSuiteValue.TripleDes; 
/* 1145 */     if (assertion.getName().getLocalPart().equals("Basic256Rsa15"))
/* 1146 */       return AlgorithmSuiteValue.Basic256Rsa15; 
/* 1147 */     if (assertion.getName().getLocalPart().equals("Basic192Rsa15"))
/* 1148 */       return AlgorithmSuiteValue.Basic192Rsa15; 
/* 1149 */     if (assertion.getName().getLocalPart().equals("Basic128Rsa15"))
/* 1150 */       return AlgorithmSuiteValue.Basic128Rsa15; 
/* 1151 */     if (assertion.getName().getLocalPart().equals("TripleDesRsa15"))
/* 1152 */       return AlgorithmSuiteValue.TripleDesRsa15; 
/* 1153 */     if (assertion.getName().getLocalPart().equals("Basic256Sha256"))
/* 1154 */       return AlgorithmSuiteValue.Basic256Sha256; 
/* 1155 */     if (assertion.getName().getLocalPart().equals("Basic192Sha256"))
/* 1156 */       return AlgorithmSuiteValue.Basic192Sha256; 
/* 1157 */     if (assertion.getName().getLocalPart().equals("Basic128Sha256"))
/* 1158 */       return AlgorithmSuiteValue.Basic128Sha256; 
/* 1159 */     if (assertion.getName().getLocalPart().equals("TripleDesSha256"))
/* 1160 */       return AlgorithmSuiteValue.TripleDesSha256; 
/* 1161 */     if (assertion.getName().getLocalPart().equals("Basic256Sha256Rsa15"))
/* 1162 */       return AlgorithmSuiteValue.Basic256Sha256Rsa15; 
/* 1163 */     if (assertion.getName().getLocalPart().equals("Basic192Sha256Rsa15"))
/* 1164 */       return AlgorithmSuiteValue.Basic192Sha256Rsa15; 
/* 1165 */     if (assertion.getName().getLocalPart().equals("Basic128Sha256Rsa15"))
/* 1166 */       return AlgorithmSuiteValue.Basic128Sha256Rsa15; 
/* 1167 */     if (assertion.getName().getLocalPart().equals("TripleDesSha256Rsa15")) {
/* 1168 */       return AlgorithmSuiteValue.TripleDesSha256Rsa15;
/*      */     }
/* 1170 */     return null;
/*      */   }
/*      */   
/*      */   public static boolean isInclusiveC14N(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1174 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1175 */       return false;
/*      */     }
/*      */     
/* 1178 */     if (assertion.getName().getLocalPart().equals("InclusiveC14N")) {
/* 1179 */       return true;
/*      */     }
/* 1181 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInclusiveC14NWithComments(PolicyAssertion assertion) {
/* 1187 */     if (!isSunPolicyNS(assertion)) {
/* 1188 */       return false;
/*      */     }
/* 1190 */     if (assertion.getName().getLocalPart().equals("InclusiveC14NWithComments")) {
/* 1191 */       return true;
/*      */     }
/* 1193 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isInclusiveC14NWithCommentsForTransforms(PolicyAssertion assertion) {
/* 1198 */     if (!isSunPolicyNS(assertion)) {
/* 1199 */       return false;
/*      */     }
/* 1201 */     if (assertion.getName().getLocalPart().equals("InclusiveC14NWithComments") && 
/* 1202 */       "true".equals(assertion.getAttributeValue(new QName("http://schemas.sun.com/2006/03/wss/server", "forTransforms")))) {
/* 1203 */       return true;
/*      */     }
/* 1205 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isInclusiveC14NWithCommentsForCm(PolicyAssertion assertion) {
/* 1210 */     if (!isSunPolicyNS(assertion)) {
/* 1211 */       return false;
/*      */     }
/* 1213 */     if (assertion.getName().getLocalPart().equals("InclusiveC14NWithComments") && 
/* 1214 */       "true".equals(assertion.getAttributeValue(new QName("http://schemas.sun.com/2006/03/wss/server", "forCm")))) {
/* 1215 */       return true;
/*      */     }
/* 1217 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isExclusiveC14NWithComments(PolicyAssertion assertion) {
/* 1221 */     if (!isSunPolicyNS(assertion)) {
/* 1222 */       return false;
/*      */     }
/* 1224 */     if (assertion.getName().getLocalPart().equals("ExclusiveC14NWithComments")) {
/* 1225 */       return true;
/*      */     }
/* 1227 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isExclusiveC14NWithCommentsForTransforms(PolicyAssertion assertion) {
/* 1231 */     if (!isSunPolicyNS(assertion)) {
/* 1232 */       return false;
/*      */     }
/* 1234 */     if (assertion.getName().getLocalPart().equals("ExclusiveC14NWithComments") && 
/* 1235 */       "true".equals(assertion.getAttributeValue(new QName("http://schemas.sun.com/2006/03/wss/server", "forTransforms")))) {
/* 1236 */       return true;
/*      */     }
/* 1238 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isExclusiveC14NWithCommentsForCm(PolicyAssertion assertion) {
/* 1242 */     if (!isSunPolicyNS(assertion)) {
/* 1243 */       return false;
/*      */     }
/* 1245 */     if (assertion.getName().getLocalPart().equals("ExclusiveC14NWithComments") && 
/* 1246 */       "true".equals(assertion.getAttributeValue(new QName("http://schemas.sun.com/2006/03/wss/server", "forCm")))) {
/* 1247 */       return true;
/*      */     }
/* 1249 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSTRTransform10(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1253 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1254 */       return false;
/*      */     }
/*      */     
/* 1257 */     if (assertion.getName().getLocalPart().equals("STRTransform10")) {
/* 1258 */       return true;
/*      */     }
/* 1260 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isInitiatorToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1264 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1265 */       return false;
/*      */     }
/*      */     
/* 1268 */     if (assertion.getName().getLocalPart().equals("InitiatorToken")) {
/* 1269 */       return true;
/*      */     }
/* 1271 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isInitiatorEncryptionToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1275 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1276 */       return false;
/*      */     }
/*      */     
/* 1279 */     if (assertion.getName().getLocalPart().equals("InitiatorEncryptionToken")) {
/* 1280 */       return true;
/*      */     }
/* 1282 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isInitiatorSignatureToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1286 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1287 */       return false;
/*      */     }
/*      */     
/* 1290 */     if (assertion.getName().getLocalPart().equals("InitiatorSignatureToken")) {
/* 1291 */       return true;
/*      */     }
/* 1293 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRecipientToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1298 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1299 */       return false;
/*      */     }
/*      */     
/* 1302 */     if (assertion.getName().getLocalPart().equals("RecipientToken")) {
/* 1303 */       return true;
/*      */     }
/* 1305 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRecipientSignatureToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1309 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1310 */       return false;
/*      */     }
/*      */     
/* 1313 */     if (assertion.getName().getLocalPart().equals("RecipientSignatureToken")) {
/* 1314 */       return true;
/*      */     }
/* 1316 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRecipientEncryptionToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1320 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1321 */       return false;
/*      */     }
/*      */     
/* 1324 */     if (assertion.getName().getLocalPart().equals("RecipientEncryptionToken")) {
/* 1325 */       return true;
/*      */     }
/* 1327 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isProtectTokens(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1332 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1333 */       return false;
/*      */     }
/*      */     
/* 1336 */     if (assertion.getName().getLocalPart().equals("ProtectTokens")) {
/* 1337 */       return true;
/*      */     }
/* 1339 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptSignature(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1343 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1344 */       return false;
/*      */     }
/*      */     
/* 1347 */     if (assertion.getName().getLocalPart().equals("EncryptSignature")) {
/* 1348 */       return true;
/*      */     }
/* 1350 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isCreated(PolicyAssertion assertion) {
/* 1354 */     if (!isUtilityNS(assertion)) {
/* 1355 */       return false;
/*      */     }
/*      */     
/* 1358 */     if (assertion.getName().getLocalPart().equals("Created")) {
/* 1359 */       return true;
/*      */     }
/* 1361 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isExpires(PolicyAssertion assertion) {
/* 1365 */     if (!isUtilityNS(assertion)) {
/* 1366 */       return false;
/*      */     }
/*      */     
/* 1369 */     if (assertion.getName().getLocalPart().equals("Expires")) {
/* 1370 */       return true;
/*      */     }
/* 1372 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignatureToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1376 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1377 */       return false;
/*      */     }
/*      */     
/* 1380 */     if (assertion.getName().getLocalPart().equals("SignatureToken")) {
/* 1381 */       return true;
/*      */     }
/*      */     
/* 1384 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptionToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1388 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1389 */       return false;
/*      */     }
/*      */     
/* 1392 */     if (assertion.getName().getLocalPart().equals("EncryptionToken")) {
/* 1393 */       return true;
/*      */     }
/* 1395 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isProtectionToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1399 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1400 */       return false;
/*      */     }
/*      */     
/* 1403 */     if (assertion.getName().getLocalPart().equals("ProtectionToken")) {
/* 1404 */       return true;
/*      */     }
/* 1406 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAddress(PolicyAssertion assertion) {
/* 1410 */     if (!isAddressingNS(assertion)) {
/* 1411 */       return false;
/*      */     }
/*      */     
/* 1414 */     if (assertion.getName().getLocalPart().equals("Address")) {
/* 1415 */       return true;
/*      */     }
/*      */     
/* 1418 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAddressingMetadata(PolicyAssertion assertion) {
/* 1422 */     if (!isAddressingNS(assertion)) {
/* 1423 */       return false;
/*      */     }
/*      */     
/* 1426 */     if (assertion.getName().getLocalPart().equals("Metadata")) {
/* 1427 */       return true;
/*      */     }
/* 1429 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMetadata(PolicyAssertion assertion) {
/* 1433 */     if (!isMEXNS(assertion)) {
/* 1434 */       return false;
/*      */     }
/*      */     
/* 1437 */     if (assertion.getName().getLocalPart().equals("Metadata")) {
/* 1438 */       return true;
/*      */     }
/*      */     
/* 1441 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMetadataSection(PolicyAssertion assertion) {
/* 1445 */     if (!isMEXNS(assertion)) {
/* 1446 */       return false;
/*      */     }
/*      */     
/* 1449 */     if (assertion.getName().getLocalPart().equals("MetadataSection")) {
/* 1450 */       return true;
/*      */     }
/*      */     
/* 1453 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isMetadataReference(PolicyAssertion assertion) {
/* 1457 */     if (!isMEXNS(assertion)) {
/* 1458 */       return false;
/*      */     }
/*      */     
/* 1461 */     if (assertion.getName().getLocalPart().equals("MetadataReference")) {
/* 1462 */       return true;
/*      */     }
/*      */     
/* 1465 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequestSecurityTokenTemplate(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1469 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1470 */       return false;
/*      */     }
/*      */     
/* 1473 */     if (assertion.getName().getLocalPart().equals("RequestSecurityTokenTemplate")) {
/* 1474 */       return true;
/*      */     }
/* 1476 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireExternalUriReference(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1480 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1481 */       return false;
/*      */     }
/*      */     
/* 1484 */     if (assertion.getName().getLocalPart().equals("RequireExternalUriReference")) {
/* 1485 */       return true;
/*      */     }
/*      */     
/* 1488 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireExternalReference(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1492 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1493 */       return false;
/*      */     }
/*      */     
/* 1496 */     if (assertion.getName().getLocalPart().equals("RequireExternalReference")) {
/* 1497 */       return true;
/*      */     }
/*      */     
/* 1500 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireInternalReference(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1504 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1505 */       return false;
/*      */     }
/*      */     
/* 1508 */     if (assertion.getName().getLocalPart().equals("RequireInternalReference")) {
/* 1509 */       return true;
/*      */     }
/*      */     
/* 1512 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEndpointReference(PolicyAssertion assertion) {
/* 1516 */     if (!isAddressingNS(assertion)) {
/* 1517 */       return false;
/*      */     }
/*      */     
/* 1520 */     if (assertion.getName().getLocalPart().equals("EndpointReference")) {
/* 1521 */       return true;
/*      */     }
/* 1523 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isLax(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1527 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1528 */       return false;
/*      */     }
/*      */     
/* 1531 */     if (assertion.getName().getLocalPart().equals("Lax")) {
/* 1532 */       return true;
/*      */     }
/* 1534 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isLaxTsFirst(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1538 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1539 */       return false;
/*      */     }
/*      */     
/* 1542 */     if (assertion.getName().getLocalPart().equals("LaxTsFirst")) {
/* 1543 */       return true;
/*      */     }
/* 1545 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isLaxTsLast(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1549 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1550 */       return false;
/*      */     }
/*      */     
/* 1553 */     if (assertion.getName().getLocalPart().equals("LaxTsLast")) {
/* 1554 */       return true;
/*      */     }
/* 1556 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isStrict(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1560 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1561 */       return false;
/*      */     }
/*      */     
/* 1564 */     if (assertion.getName().getLocalPart().equals("Strict")) {
/* 1565 */       return true;
/*      */     }
/* 1567 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKeyType(PolicyAssertion assertion) {
/* 1571 */     if (!isTrustNS(assertion)) {
/* 1572 */       return false;
/*      */     }
/*      */     
/* 1575 */     if (assertion.getName().getLocalPart().equals("KeyType")) {
/* 1576 */       return true;
/*      */     }
/*      */     
/* 1579 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKeySize(PolicyAssertion assertion) {
/* 1583 */     if (!isTrustNS(assertion)) {
/* 1584 */       return false;
/*      */     }
/*      */     
/* 1587 */     if (assertion.getName().getLocalPart().equals("KeySize")) {
/* 1588 */       return true;
/*      */     }
/*      */     
/* 1591 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isUseKey(PolicyAssertion assertion) {
/* 1595 */     if (!isTrustNS(assertion)) {
/* 1596 */       return false;
/*      */     }
/*      */     
/* 1599 */     if (assertion.getName().getLocalPart().equals("UseKey")) {
/* 1600 */       return true;
/*      */     }
/*      */     
/* 1603 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryption(PolicyAssertion assertion) {
/* 1607 */     if (!isTrustNS(assertion)) {
/* 1608 */       return false;
/*      */     }
/*      */     
/* 1611 */     if (assertion.getName().getLocalPart().equals("Encryption")) {
/* 1612 */       return true;
/*      */     }
/* 1614 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isProofEncryption(PolicyAssertion assertion) {
/* 1618 */     if (!isTrustNS(assertion)) {
/* 1619 */       return false;
/*      */     }
/*      */     
/* 1622 */     if (assertion.getName().getLocalPart().equals("ProofEncryption")) {
/* 1623 */       return true;
/*      */     }
/* 1625 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isLifeTime(PolicyAssertion assertion) {
/* 1629 */     if (!isTrustNS(assertion)) {
/* 1630 */       return false;
/*      */     }
/*      */     
/* 1633 */     if (assertion.getName().getLocalPart().equals("Lifetime")) {
/* 1634 */       return true;
/*      */     }
/* 1636 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isHeader(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1640 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1641 */       return false;
/*      */     }
/* 1643 */     if (assertion.getName().getLocalPart().equals("Header")) {
/* 1644 */       return true;
/*      */     }
/* 1646 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequireKeyIR(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1650 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1651 */       return false;
/*      */     }
/* 1653 */     if (assertion.getName().getLocalPart().equals("RequireKeyIdentifierReference")) {
/* 1654 */       return true;
/*      */     }
/* 1656 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignWith(PolicyAssertion assertion) {
/* 1660 */     if (!isTrustNS(assertion)) {
/* 1661 */       return false;
/*      */     }
/* 1663 */     if ("SignWith".equals(assertion.getName().getLocalPart())) {
/* 1664 */       return true;
/*      */     }
/* 1666 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptWith(PolicyAssertion assertion) {
/* 1670 */     if (!isTrustNS(assertion)) {
/* 1671 */       return false;
/*      */     }
/* 1673 */     if ("EncryptWith".equals(assertion.getName().getLocalPart())) {
/* 1674 */       return true;
/*      */     }
/* 1676 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequestType(PolicyAssertion assertion) {
/* 1680 */     if (!isTrustNS(assertion)) {
/* 1681 */       return false;
/*      */     }
/* 1683 */     if ("RequestType".equals(assertion.getName().getLocalPart())) {
/* 1684 */       return true;
/*      */     }
/* 1686 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSignatureAlgorithm(PolicyAssertion assertion) {
/* 1690 */     if (!isTrustNS(assertion)) {
/* 1691 */       return false;
/*      */     }
/* 1693 */     if ("SignatureAlgorithm".equals(assertion.getName().getLocalPart())) {
/* 1694 */       return true;
/*      */     }
/* 1696 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isComputedKeyAlgorithm(PolicyAssertion assertion) {
/* 1700 */     if (!isTrustNS(assertion)) {
/* 1701 */       return false;
/*      */     }
/* 1703 */     if ("ComputedKeyAlgorithm".equals(assertion.getName().getLocalPart())) {
/* 1704 */       return true;
/*      */     }
/* 1706 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isCanonicalizationAlgorithm(PolicyAssertion assertion) {
/* 1710 */     if (!isTrustNS(assertion)) {
/* 1711 */       return false;
/*      */     }
/* 1713 */     if ("CanonicalizationAlgorithm".equals(assertion.getName().getLocalPart())) {
/* 1714 */       return true;
/*      */     }
/* 1716 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEncryptionAlgorithm(PolicyAssertion assertion) {
/* 1720 */     if (!isTrustNS(assertion)) {
/* 1721 */       return false;
/*      */     }
/* 1723 */     if ("EncryptionAlgorithm".equals(assertion.getName().getLocalPart())) {
/* 1724 */       return true;
/*      */     }
/* 1726 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isAuthenticationType(PolicyAssertion assertion) {
/* 1730 */     if (!isTrustNS(assertion)) {
/* 1731 */       return false;
/*      */     }
/* 1733 */     if ("AuthenticationType".equals(assertion.getName().getLocalPart())) {
/* 1734 */       return true;
/*      */     }
/* 1736 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isKeyWrapAlgorithm(PolicyAssertion assertion) {
/* 1740 */     if (!"http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(assertion.getName().getNamespaceURI())) {
/* 1741 */       return false;
/*      */     }
/* 1743 */     if ("KeyWrapAlgorithm".equals(assertion.getName().getLocalPart())) {
/* 1744 */       return true;
/*      */     }
/* 1746 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isSC10SecurityContextToken(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1750 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1751 */       return false;
/*      */     }
/*      */     
/* 1754 */     if (assertion.getName().getLocalPart().equals("SC10SecurityContextToken")) {
/* 1755 */       return true;
/*      */     }
/* 1757 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isConfigPolicyAssertion(PolicyAssertion assertion) {
/* 1761 */     String uri = assertion.getName().getNamespaceURI();
/* 1762 */     if ("http://schemas.sun.com/ws/2006/05/sc/client".equals(uri) || "http://schemas.sun.com/ws/2006/05/trust/client".equals(uri) || "http://schemas.sun.com/ws/2006/05/sc/server".equals(uri) || "http://schemas.sun.com/ws/2006/05/trust/server".equals(uri) || "http://schemas.sun.com/2006/03/wss/client".equals(uri) || "http://schemas.sun.com/2006/03/wss/server".equals(uri))
/*      */     {
/*      */       
/* 1765 */       return true;
/*      */     }
/* 1767 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isTrustTokenType(PolicyAssertion assertion) {
/* 1771 */     if (!isTrustNS(assertion)) {
/* 1772 */       return false;
/*      */     }
/* 1774 */     if ("TokenType".equals(assertion.getName().getLocalPart())) {
/* 1775 */       return true;
/*      */     }
/* 1777 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isPortType(PolicyAssertion assertion) {
/* 1781 */     if (!isAddressingNS(assertion)) {
/* 1782 */       return false;
/*      */     }
/*      */     
/* 1785 */     if (assertion.getName().getLocalPart().equals("PortType")) {
/* 1786 */       return true;
/*      */     }
/* 1788 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isReferenceParameters(PolicyAssertion assertion) {
/* 1792 */     if (!isAddressingNS(assertion)) {
/* 1793 */       return false;
/*      */     }
/*      */     
/* 1796 */     if (assertion.getName().getLocalPart().equals("ReferenceParameters")) {
/* 1797 */       return true;
/*      */     }
/* 1799 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isReferenceProperties(PolicyAssertion assertion) {
/* 1803 */     if (!isAddressingNS(assertion)) {
/* 1804 */       return false;
/*      */     }
/*      */     
/* 1807 */     if (assertion.getName().getLocalPart().equals("ReferenceProperties")) {
/* 1808 */       return true;
/*      */     }
/* 1810 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isServiceName(PolicyAssertion assertion) {
/* 1814 */     if (!isAddressingNS(assertion)) {
/* 1815 */       return false;
/*      */     }
/*      */     
/* 1818 */     if (assertion.getName().getLocalPart().equals("ServiceName")) {
/* 1819 */       return true;
/*      */     }
/* 1821 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isRequiredElements(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1825 */     if (isSecurityPolicyNS(assertion, spVersion)) {
/* 1826 */       return false;
/*      */     }
/*      */     
/* 1829 */     if (assertion.getName().getLocalPart().equals("RequiredElements")) {
/* 1830 */       return true;
/*      */     }
/* 1832 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isClaimsElement(PolicyAssertion assertion) {
/* 1836 */     if (!isTrustNS(assertion)) {
/* 1837 */       return false;
/*      */     }
/* 1839 */     if ("Claims".equals(assertion.getName().getLocalPart())) {
/* 1840 */       return true;
/*      */     }
/* 1842 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isEntropyElement(PolicyAssertion assertion) {
/* 1846 */     if (!isTrustNS(assertion)) {
/* 1847 */       return false;
/*      */     }
/* 1849 */     if ("Entropy".equals(assertion.getName().getLocalPart())) {
/* 1850 */       return true;
/*      */     }
/* 1852 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean hasPassword(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1857 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1858 */       return false;
/*      */     }
/*      */     
/* 1861 */     if (assertion.getName().getLocalPart().equals("NoPassword")) {
/* 1862 */       return true;
/*      */     }
/* 1864 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isHashPassword(PolicyAssertion assertion, SecurityPolicyVersion spVersion) {
/* 1868 */     if (!isSecurityPolicyNS(assertion, spVersion)) {
/* 1869 */       return false;
/*      */     }
/*      */     
/* 1872 */     if (assertion.getName().getLocalPart().equals("HashPassword") && assertion.getName().getNamespaceURI().equals(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri))
/*      */     {
/* 1874 */       return true;
/*      */     }
/* 1876 */     return false;
/*      */   }
/*      */   
/*      */   public static String randomUUID() {
/* 1880 */     UUID uid = UUID.randomUUID();
/* 1881 */     String id = "uuid_" + uid.toString();
/* 1882 */     return id;
/*      */   }
/*      */   
/*      */   public static byte[] policyAssertionToBytes(PolicyAssertion token) {
/*      */     try {
/* 1887 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1888 */       XMLOutputFactory xof = XMLOutputFactory.newInstance();
/* 1889 */       XMLStreamWriter writer = xof.createXMLStreamWriter(baos);
/*      */       
/* 1891 */       AssertionSet set = AssertionSet.createAssertionSet(Arrays.asList(new PolicyAssertion[] { token }));
/* 1892 */       Policy policy = Policy.createPolicy(Arrays.asList(new AssertionSet[] { set }));
/* 1893 */       PolicySourceModel sourceModel = ModelGenerator.getGenerator().translate(policy);
/* 1894 */       PolicyModelMarshaller pm = PolicyModelMarshaller.getXmlMarshaller(true);
/* 1895 */       pm.marshal(sourceModel, writer);
/* 1896 */       writer.close();
/*      */       
/* 1898 */       return baos.toByteArray();
/* 1899 */     } catch (Exception e) {
/* 1900 */       throw new WebServiceException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static Document policyAssertionToDoc(PolicyAssertion token) {
/*      */     try {
/* 1906 */       byte[] byteArray = policyAssertionToBytes(token);
/*      */       
/* 1908 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 1909 */       dbf.setNamespaceAware(true);
/* 1910 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 1911 */       Document doc = db.parse(new ByteArrayInputStream(byteArray));
/*      */       
/* 1913 */       return doc;
/* 1914 */     } catch (Exception e) {
/* 1915 */       throw new WebServiceException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static SecurityPolicyVersion getSecurityPolicyVersion(String nsUri) {
/* 1920 */     SecurityPolicyVersion spVersion = null;
/* 1921 */     if (SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri.equals(nsUri)) {
/* 1922 */       spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/* 1923 */     } else if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(nsUri)) {
/* 1924 */       spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/* 1925 */     } else if (SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri.equals(nsUri)) {
/* 1926 */       spVersion = SecurityPolicyVersion.SECURITYPOLICY200512;
/* 1927 */     } else if (SecurityPolicyVersion.MS_SECURITYPOLICY200507.namespaceUri.equals(nsUri)) {
/* 1928 */       spVersion = SecurityPolicyVersion.MS_SECURITYPOLICY200507;
/*      */     } 
/* 1930 */     return spVersion;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\PolicyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */