/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityPolicySelector
/*     */ {
/*  55 */   private static final ArrayList<QName> supportedAssertions = new ArrayList<QName>();
/*     */   static {
/*  57 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "CanonicalizationAlgorithm"));
/*  58 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic256"));
/*  59 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192"));
/*  60 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic128"));
/*  61 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "TripleDes"));
/*  62 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic256Rsa15"));
/*     */     
/*  64 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192Rsa15"));
/*  65 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192Rsa15"));
/*  66 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "TripleDesRsa15"));
/*  67 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic256Sha256"));
/*  68 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic256Rsa15"));
/*     */     
/*  70 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192Sha256"));
/*  71 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic128Sha256"));
/*  72 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192Sha256"));
/*  73 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "TripleDesSha256"));
/*  74 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic256Sha256Rsa15"));
/*     */     
/*  76 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192Sha256Rsa15"));
/*  77 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic128Sha256Rsa15"));
/*  78 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "TripleDesSha256Rsa15"));
/*  79 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "InclusiveC14N"));
/*  80 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "InclusiveC14NWithComments"));
/*  81 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "ExclusiveC14NWithComments"));
/*     */ 
/*     */     
/*  84 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "STRTransform10"));
/*     */     
/*  86 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "XPathFilter20"));
/*  87 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Strict"));
/*  88 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Lax"));
/*     */     
/*  90 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "LaxTsFirst"));
/*  91 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "LaxTsLast"));
/*  92 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "IncludeTimestamp"));
/*  93 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "EncryptBeforeSigning"));
/*  94 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "EncryptSignature"));
/*     */     
/*  96 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "ProtectTokens"));
/*  97 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "OnlySignEntireHeadersAndBody"));
/*  98 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Body"));
/*     */     
/* 100 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "XPath"));
/*     */     
/* 102 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssUsernameToken10"));
/* 103 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssUsernameToken11"));
/* 104 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Issuer"));
/*     */     
/* 106 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequestSecurityTokenTemplate"));
/* 107 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireDerivedKeys"));
/* 108 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireExternalReference"));
/* 109 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireInternalReference"));
/* 110 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireKeyIdentifierReference"));
/*     */     
/* 112 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireIssuerSerialReference"));
/* 113 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireEmbeddedTokenReference"));
/* 114 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireThumbprintReference"));
/* 115 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V1Token10"));
/* 116 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V3Token10"));
/*     */     
/* 118 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509Pkcs7Token10"));
/* 119 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509PkiPathV1Token10"));
/* 120 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V1Token11"));
/* 121 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V3Token11"));
/* 122 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509Pkcs7Token11"));
/*     */     
/* 124 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509PkiPathV1Token11"));
/* 125 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssKerberosV5ApReqToken11"));
/* 126 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssGssKerberosV5ApReqToken11"));
/* 127 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SC10SecurityContextToken"));
/* 128 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV10Token10"));
/*     */     
/* 130 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV11Token10"));
/* 131 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV10Token11"));
/* 132 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV11Token11"));
/* 133 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV20Token11"));
/* 134 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV10Token10"));
/*     */     
/* 136 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV20Token10"));
/* 137 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV10Token11"));
/* 138 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV20Token11"));
/*     */     
/* 140 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SupportingTokens"));
/* 141 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SignedSupportingTokens"));
/* 142 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "EndorsingSupportingTokens"));
/* 143 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SignedEndorsingSupportingTokens"));
/* 144 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 145 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefIssuerSerial"));
/* 146 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefExternalURI"));
/* 147 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefEmbeddedToken"));
/*     */     
/* 149 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 150 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefIssuerSerial"));
/* 151 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefExternalURI"));
/* 152 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefEmbeddedToken"));
/*     */     
/* 154 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefThumbprint"));
/* 155 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefEncryptedKey"));
/* 156 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportClientChallenge"));
/* 157 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportServerChallenge"));
/*     */     
/* 159 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireClientEntropy"));
/* 160 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireServerEntropy"));
/* 161 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportIssuedTokens"));
/*     */ 
/*     */     
/* 164 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "CanonicalizationAlgorithm"));
/* 165 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256"));
/* 166 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192"));
/* 167 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic128"));
/* 168 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDes"));
/* 169 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 171 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Rsa15"));
/* 172 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Rsa15"));
/* 173 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDesRsa15"));
/* 174 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Sha256"));
/* 175 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 177 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Sha256"));
/* 178 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic128Sha256"));
/* 179 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Sha256"));
/* 180 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDesSha256"));
/* 181 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Sha256Rsa15"));
/*     */     
/* 183 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Sha256Rsa15"));
/* 184 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic128Sha256Rsa15"));
/* 185 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDesSha256Rsa15"));
/* 186 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "InclusiveC14N"));
/*     */ 
/*     */     
/* 189 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "STRTransform10"));
/*     */     
/* 191 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "XPathFilter20"));
/* 192 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Strict"));
/* 193 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Lax"));
/*     */     
/* 195 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "LaxTsFirst"));
/* 196 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "LaxTsLast"));
/* 197 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "IncludeTimestamp"));
/* 198 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EncryptBeforeSigning"));
/* 199 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EncryptSignature"));
/*     */     
/* 201 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "ProtectTokens"));
/* 202 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "OnlySignEntireHeadersAndBody"));
/* 203 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Body"));
/*     */     
/* 205 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "XPath"));
/*     */     
/* 207 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssUsernameToken10"));
/* 208 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssUsernameToken11"));
/* 209 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Issuer"));
/*     */     
/* 211 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequestSecurityTokenTemplate"));
/* 212 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireDerivedKeys"));
/* 213 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireExternalReference"));
/* 214 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireInternalReference"));
/* 215 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireKeyIdentifierReference"));
/*     */     
/* 217 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireIssuerSerialReference"));
/* 218 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireEmbeddedTokenReference"));
/* 219 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireThumbprintReference"));
/* 220 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V1Token10"));
/* 221 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V3Token10"));
/*     */     
/* 223 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509Pkcs7Token10"));
/* 224 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509PkiPathV1Token10"));
/* 225 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V1Token11"));
/* 226 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V3Token11"));
/* 227 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509Pkcs7Token11"));
/*     */     
/* 229 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509PkiPathV1Token11"));
/* 230 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssKerberosV5ApReqToken11"));
/* 231 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssGssKerberosV5ApReqToken11"));
/* 232 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SC10SecurityContextToken"));
/* 233 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV10Token10"));
/*     */     
/* 235 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV11Token10"));
/* 236 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV10Token11"));
/* 237 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV11Token11"));
/* 238 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV20Token11"));
/* 239 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV10Token10"));
/*     */     
/* 241 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV20Token10"));
/* 242 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV10Token11"));
/* 243 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV20Token11"));
/*     */     
/* 245 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SupportingTokens"));
/* 246 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedSupportingTokens"));
/* 247 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EndorsingSupportingTokens"));
/* 248 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedEndorsingSupportingTokens"));
/* 249 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EncryptedSupportingTokens"));
/* 250 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedEncryptedSupportingTokens"));
/* 251 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EndorsingEncryptedSupportingTokens"));
/* 252 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedEndorsingEncryptedSupportingTokens"));
/*     */     
/* 254 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 255 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefIssuerSerial"));
/* 256 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefExternalURI"));
/* 257 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefEmbeddedToken"));
/*     */     
/* 259 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 260 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefIssuerSerial"));
/* 261 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefExternalURI"));
/* 262 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefEmbeddedToken"));
/*     */     
/* 264 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefThumbprint"));
/* 265 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefEncryptedKey"));
/* 266 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportClientChallenge"));
/* 267 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportServerChallenge"));
/*     */     
/* 269 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireClientEntropy"));
/* 270 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireServerEntropy"));
/* 271 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportIssuedTokens"));
/* 272 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireRequestSecurityTokenCollection"));
/* 273 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireAppliesTo"));
/*     */     
/* 275 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RsaKeyValue"));
/*     */     
/* 277 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireClientCertificate"));
/* 278 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "HttpBasicAuthentication"));
/* 279 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "HttpDigestAuthentication"));
/*     */ 
/*     */ 
/*     */     
/* 283 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "CanonicalizationAlgorithm"));
/* 284 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256"));
/* 285 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192"));
/* 286 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic128"));
/* 287 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDes"));
/* 288 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 290 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Rsa15"));
/* 291 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Rsa15"));
/* 292 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDesRsa15"));
/* 293 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Sha256"));
/* 294 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 296 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Sha256"));
/* 297 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic128Sha256"));
/* 298 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Sha256"));
/* 299 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDesSha256"));
/* 300 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Sha256Rsa15"));
/*     */     
/* 302 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Sha256Rsa15"));
/* 303 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic128Sha256Rsa15"));
/* 304 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDesSha256Rsa15"));
/* 305 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "InclusiveC14N"));
/*     */     
/* 307 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "STRTransform10"));
/*     */     
/* 309 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "XPathFilter20"));
/* 310 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Strict"));
/* 311 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Lax"));
/*     */     
/* 313 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "LaxTsFirst"));
/* 314 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "LaxTsLast"));
/* 315 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "IncludeTimestamp"));
/* 316 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "EncryptBeforeSigning"));
/* 317 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "EncryptSignature"));
/*     */     
/* 319 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "ProtectTokens"));
/* 320 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "OnlySignEntireHeadersAndBody"));
/* 321 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Body"));
/*     */     
/* 323 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "XPath"));
/*     */     
/* 325 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssUsernameToken10"));
/* 326 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssUsernameToken11"));
/* 327 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Issuer"));
/*     */     
/* 329 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequestSecurityTokenTemplate"));
/* 330 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireDerivedKeys"));
/* 331 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireExternalReference"));
/* 332 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireInternalReference"));
/* 333 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireKeyIdentifierReference"));
/*     */     
/* 335 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireIssuerSerialReference"));
/* 336 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireEmbeddedTokenReference"));
/* 337 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireThumbprintReference"));
/* 338 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V1Token10"));
/* 339 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V3Token10"));
/*     */     
/* 341 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509Pkcs7Token10"));
/* 342 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509PkiPathV1Token10"));
/* 343 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V1Token11"));
/* 344 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V3Token11"));
/* 345 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509Pkcs7Token11"));
/*     */     
/* 347 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509PkiPathV1Token11"));
/* 348 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssKerberosV5ApReqToken11"));
/* 349 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssGssKerberosV5ApReqToken11"));
/* 350 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SC10SecurityContextToken"));
/* 351 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV10Token10"));
/*     */     
/* 353 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV11Token10"));
/* 354 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV10Token11"));
/* 355 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV11Token11"));
/* 356 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV20Token11"));
/* 357 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV10Token10"));
/*     */     
/* 359 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV20Token10"));
/* 360 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV10Token11"));
/* 361 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV20Token11"));
/*     */     
/* 363 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SupportingTokens"));
/* 364 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SignedSupportingTokens"));
/* 365 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "EndorsingSupportingTokens"));
/* 366 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SignedEndorsingSupportingTokens"));
/* 367 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 368 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefIssuerSerial"));
/* 369 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefExternalURI"));
/* 370 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefEmbeddedToken"));
/*     */     
/* 372 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 373 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefIssuerSerial"));
/* 374 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefExternalURI"));
/* 375 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefEmbeddedToken"));
/*     */     
/* 377 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefThumbprint"));
/* 378 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefEncryptedKey"));
/* 379 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportClientChallenge"));
/* 380 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportServerChallenge"));
/*     */     
/* 382 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireClientEntropy"));
/* 383 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireServerEntropy"));
/* 384 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportIssuedTokens"));
/* 385 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802", "Created"));
/* 386 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802", "Nonce"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 394 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityToken"));
/* 395 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestType"));
/* 396 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "TokenType"));
/* 397 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "AuthenticationType"));
/*     */     
/* 399 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "OnBehalfOf"));
/* 400 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeyType"));
/* 401 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeySize"));
/* 402 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignatureAlgorithm"));
/*     */     
/* 404 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "EncryptionAlgorithm"));
/* 405 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "CanonicalizationAlgorithm"));
/* 406 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ComputedKeyAlgorithm"));
/*     */     
/* 408 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Encryption"));
/* 409 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ProofEncryption"));
/* 410 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "UseKey"));
/* 411 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignWith"));
/* 412 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "EncryptWith"));
/*     */ 
/*     */ 
/*     */     
/* 416 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityToken"));
/* 417 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestType"));
/* 418 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "TokenType"));
/* 419 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticationType"));
/*     */     
/* 421 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOf"));
/* 422 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType"));
/* 423 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeySize"));
/* 424 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignatureAlgorithm"));
/*     */     
/* 426 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptionAlgorithm"));
/* 427 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CanonicalizationAlgorithm"));
/* 428 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKeyAlgorithm"));
/*     */     
/* 430 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Encryption"));
/* 431 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryption"));
/* 432 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKey"));
/* 433 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignWith"));
/* 434 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptWith"));
/*     */     
/* 436 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EnableEPRIdentity"));
/* 437 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EncSCCancel"));
/* 438 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "EncSCCancel"));
/* 439 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisableStreamingSecurity"));
/* 440 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisableStreamingSecurity"));
/*     */     
/* 442 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisableTimestampSigning"));
/* 443 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisableTimestampSigning"));
/*     */     
/* 445 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisableInclusivePrefixList"));
/* 446 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisableInclusivePrefixList"));
/*     */     
/* 448 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptHeaderContent"));
/* 449 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptHeaderContent"));
/*     */     
/* 451 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptRMLifecycleMessage"));
/* 452 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptRMLifecycleMessage"));
/*     */     
/* 454 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "AllowMissingTimestamp"));
/* 455 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "AllowMissingTimestamp"));
/*     */     
/* 457 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "UnsetSecurityMUValue"));
/* 458 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "UnsetSecurityMUValue"));
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
/*     */   public PolicyAssertionValidator.Fitness getFitness(PolicyAssertion policyAssertion) {
/* 473 */     if (policyAssertion instanceof SecurityAssertionValidator)
/* 474 */       return (((SecurityAssertionValidator)policyAssertion).validate(true) == SecurityAssertionValidator.AssertionFitness.IS_VALID) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNSUPPORTED; 
/* 475 */     if (supportedAssertions.contains(policyAssertion.getName())) {
/* 476 */       return PolicyAssertionValidator.Fitness.SUPPORTED;
/*     */     }
/* 478 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecurityPolicySelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */