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
/*     */ public class SecurityPolicyValidator
/*     */   implements PolicyAssertionValidator
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
/*  75 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic192Sha256Rsa15"));
/*  76 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Basic128Sha256Rsa15"));
/*  77 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "TripleDesSha256Rsa15"));
/*  78 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "InclusiveC14N"));
/*  79 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "InclusiveC14NWithComments"));
/*  80 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "ExclusiveC14NWithComments"));
/*     */ 
/*     */     
/*  83 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "STRTransform10"));
/*     */     
/*  85 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "XPathFilter20"));
/*  86 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Strict"));
/*  87 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Lax"));
/*     */     
/*  89 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "LaxTsFirst"));
/*  90 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "LaxTsLast"));
/*  91 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "IncludeTimestamp"));
/*  92 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "EncryptBeforeSigning"));
/*  93 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "EncryptSignature"));
/*     */     
/*  95 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "ProtectTokens"));
/*  96 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "OnlySignEntireHeadersAndBody"));
/*  97 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Body"));
/*     */     
/*  99 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "XPath"));
/*     */     
/* 101 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssUsernameToken10"));
/* 102 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssUsernameToken11"));
/* 103 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Issuer"));
/*     */     
/* 105 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequestSecurityTokenTemplate"));
/* 106 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireDerivedKeys"));
/* 107 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireExternalReference"));
/* 108 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireInternalReference"));
/* 109 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireKeyIdentifierReference"));
/*     */     
/* 111 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireIssuerSerialReference"));
/* 112 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireEmbeddedTokenReference"));
/* 113 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireThumbprintReference"));
/* 114 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V1Token10"));
/* 115 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V3Token10"));
/*     */     
/* 117 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509Pkcs7Token10"));
/* 118 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509PkiPathV1Token10"));
/* 119 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V1Token11"));
/* 120 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509V3Token11"));
/* 121 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509Pkcs7Token11"));
/*     */     
/* 123 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssX509PkiPathV1Token11"));
/* 124 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssKerberosV5ApReqToken11"));
/* 125 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssGssKerberosV5ApReqToken11"));
/* 126 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SC10SecurityContextToken"));
/* 127 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV10Token10"));
/*     */     
/* 129 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV11Token10"));
/* 130 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV10Token11"));
/* 131 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV11Token11"));
/* 132 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssSamlV20Token11"));
/* 133 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV10Token10"));
/*     */     
/* 135 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV20Token10"));
/* 136 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV10Token11"));
/* 137 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "WssRelV20Token11"));
/*     */     
/* 139 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SupportingTokens"));
/* 140 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SignedSupportingTokens"));
/* 141 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "EndorsingSupportingTokens"));
/* 142 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SignedEndorsingSupportingTokens"));
/* 143 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 144 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefIssuerSerial"));
/* 145 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefExternalURI"));
/* 146 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefEmbeddedToken"));
/* 147 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 148 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefIssuerSerial"));
/* 149 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefExternalURI"));
/* 150 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefEmbeddedToken"));
/* 151 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefThumbprint"));
/* 152 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportRefEncryptedKey"));
/* 153 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportClientChallenge"));
/* 154 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportServerChallenge"));
/* 155 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireClientEntropy"));
/* 156 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "RequireServerEntropy"));
/* 157 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "MustSupportIssuedTokens"));
/* 158 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "NoPassword"));
/*     */ 
/*     */     
/* 161 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "CanonicalizationAlgorithm"));
/* 162 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256"));
/* 163 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192"));
/* 164 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic128"));
/* 165 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDes"));
/* 166 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 168 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Rsa15"));
/* 169 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Rsa15"));
/* 170 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDesRsa15"));
/* 171 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Sha256"));
/* 172 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 174 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Sha256"));
/* 175 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic128Sha256"));
/* 176 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Sha256"));
/* 177 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDesSha256"));
/* 178 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic256Sha256Rsa15"));
/* 179 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic192Sha256Rsa15"));
/* 180 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Basic128Sha256Rsa15"));
/* 181 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "TripleDesSha256Rsa15"));
/* 182 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "InclusiveC14N"));
/*     */ 
/*     */     
/* 185 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "STRTransform10"));
/*     */     
/* 187 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "XPathFilter20"));
/* 188 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Strict"));
/* 189 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Lax"));
/*     */     
/* 191 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "LaxTsFirst"));
/* 192 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "LaxTsLast"));
/* 193 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "IncludeTimestamp"));
/* 194 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EncryptBeforeSigning"));
/* 195 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EncryptSignature"));
/*     */     
/* 197 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "ProtectTokens"));
/* 198 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "OnlySignEntireHeadersAndBody"));
/* 199 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Body"));
/*     */     
/* 201 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "XPath"));
/*     */     
/* 203 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssUsernameToken10"));
/* 204 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssUsernameToken11"));
/* 205 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Issuer"));
/*     */     
/* 207 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequestSecurityTokenTemplate"));
/* 208 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireDerivedKeys"));
/* 209 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireExternalReference"));
/* 210 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireInternalReference"));
/* 211 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireKeyIdentifierReference"));
/*     */     
/* 213 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireIssuerSerialReference"));
/* 214 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireEmbeddedTokenReference"));
/* 215 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireThumbprintReference"));
/* 216 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V1Token10"));
/* 217 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V3Token10"));
/*     */     
/* 219 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509Pkcs7Token10"));
/* 220 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509PkiPathV1Token10"));
/* 221 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V1Token11"));
/* 222 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509V3Token11"));
/* 223 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509Pkcs7Token11"));
/*     */     
/* 225 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssX509PkiPathV1Token11"));
/* 226 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssKerberosV5ApReqToken11"));
/* 227 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssGssKerberosV5ApReqToken11"));
/* 228 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SC10SecurityContextToken"));
/* 229 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV10Token10"));
/*     */     
/* 231 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV11Token10"));
/* 232 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV10Token11"));
/* 233 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV11Token11"));
/* 234 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssSamlV20Token11"));
/* 235 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV10Token10"));
/*     */     
/* 237 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV20Token10"));
/* 238 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV10Token11"));
/* 239 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "WssRelV20Token11"));
/*     */     
/* 241 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SupportingTokens"));
/* 242 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedSupportingTokens"));
/* 243 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EndorsingSupportingTokens"));
/* 244 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedEndorsingSupportingTokens"));
/* 245 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EncryptedSupportingTokens"));
/* 246 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedEncryptedSupportingTokens"));
/* 247 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "EndorsingEncryptedSupportingTokens"));
/* 248 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SignedEndorsingEncryptedSupportingTokens"));
/* 249 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 250 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefIssuerSerial"));
/* 251 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefExternalURI"));
/* 252 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefEmbeddedToken"));
/* 253 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 254 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefIssuerSerial"));
/* 255 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefExternalURI"));
/* 256 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefEmbeddedToken"));
/* 257 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefThumbprint"));
/* 258 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportRefEncryptedKey"));
/* 259 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportClientChallenge"));
/* 260 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportServerChallenge"));
/* 261 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireClientEntropy"));
/* 262 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireServerEntropy"));
/* 263 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "MustSupportIssuedTokens"));
/* 264 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "NoPassword"));
/* 265 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireClientCertificate"));
/* 266 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "HttpBasicAuthentication"));
/* 267 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "HttpDigestAuthentication"));
/* 268 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireRequestSecurityTokenCollection"));
/* 269 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RequireAppliesTo"));
/* 270 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "RsaKeyValue"));
/*     */ 
/*     */     
/* 273 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "CanonicalizationAlgorithm"));
/* 274 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256"));
/* 275 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192"));
/* 276 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic128"));
/* 277 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDes"));
/* 278 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 280 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Rsa15"));
/* 281 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Rsa15"));
/* 282 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDesRsa15"));
/* 283 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Sha256"));
/* 284 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Rsa15"));
/*     */     
/* 286 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Sha256"));
/* 287 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic128Sha256"));
/* 288 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Sha256"));
/* 289 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDesSha256"));
/* 290 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic256Sha256Rsa15"));
/* 291 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic192Sha256Rsa15"));
/* 292 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Basic128Sha256Rsa15"));
/* 293 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "TripleDesSha256Rsa15"));
/* 294 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "InclusiveC14N"));
/*     */     
/* 296 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "STRTransform10"));
/* 297 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "XPathFilter20"));
/* 298 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Strict"));
/* 299 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Lax"));
/*     */     
/* 301 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "LaxTsFirst"));
/* 302 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "LaxTsLast"));
/* 303 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "IncludeTimestamp"));
/* 304 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "EncryptBeforeSigning"));
/* 305 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "EncryptSignature"));
/*     */     
/* 307 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "ProtectTokens"));
/* 308 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "OnlySignEntireHeadersAndBody"));
/* 309 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Body"));
/* 310 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "XPath"));
/*     */     
/* 312 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssUsernameToken10"));
/* 313 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssUsernameToken11"));
/* 314 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "Issuer"));
/*     */     
/* 316 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequestSecurityTokenTemplate"));
/* 317 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireDerivedKeys"));
/* 318 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireExternalReference"));
/* 319 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireInternalReference"));
/* 320 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireKeyIdentifierReference"));
/*     */     
/* 322 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireIssuerSerialReference"));
/* 323 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireEmbeddedTokenReference"));
/* 324 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireThumbprintReference"));
/* 325 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V1Token10"));
/* 326 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V3Token10"));
/*     */     
/* 328 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509Pkcs7Token10"));
/* 329 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509PkiPathV1Token10"));
/* 330 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V1Token11"));
/* 331 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509V3Token11"));
/* 332 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509Pkcs7Token11"));
/*     */     
/* 334 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssX509PkiPathV1Token11"));
/* 335 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssKerberosV5ApReqToken11"));
/* 336 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssGssKerberosV5ApReqToken11"));
/* 337 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SC10SecurityContextToken"));
/* 338 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV10Token10"));
/*     */     
/* 340 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV11Token10"));
/* 341 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV10Token11"));
/* 342 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV11Token11"));
/* 343 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssSamlV20Token11"));
/* 344 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV10Token10"));
/*     */     
/* 346 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV20Token10"));
/* 347 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV10Token11"));
/* 348 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "WssRelV20Token11"));
/* 349 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SupportingTokens"));
/* 350 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SignedSupportingTokens"));
/* 351 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "EndorsingSupportingTokens"));
/* 352 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "SignedEndorsingSupportingTokens"));
/* 353 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 354 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefIssuerSerial"));
/* 355 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefExternalURI"));
/* 356 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefEmbeddedToken"));
/* 357 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefKeyIdentifier"));
/* 358 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefIssuerSerial"));
/* 359 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefExternalURI"));
/* 360 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefEmbeddedToken"));
/* 361 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefThumbprint"));
/* 362 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportRefEncryptedKey"));
/* 363 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportClientChallenge"));
/* 364 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportServerChallenge"));
/* 365 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireClientEntropy"));
/* 366 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "RequireServerEntropy"));
/* 367 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "MustSupportIssuedTokens"));
/* 368 */     supportedAssertions.add(new QName(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "NoPassword"));
/* 369 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802", "Created"));
/* 370 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802", "Nonce"));
/*     */ 
/*     */ 
/*     */     
/* 374 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityToken"));
/* 375 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestType"));
/* 376 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "TokenType"));
/* 377 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "AuthenticationType"));
/* 378 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "OnBehalfOf"));
/* 379 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeyType"));
/* 380 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeySize"));
/* 381 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignatureAlgorithm"));
/* 382 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "EncryptionAlgorithm"));
/* 383 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "CanonicalizationAlgorithm"));
/* 384 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ComputedKeyAlgorithm"));
/* 385 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Encryption"));
/* 386 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ProofEncryption"));
/* 387 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "UseKey"));
/* 388 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignWith"));
/* 389 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "EncryptWith"));
/*     */ 
/*     */     
/* 392 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityToken"));
/* 393 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestType"));
/* 394 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "TokenType"));
/* 395 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticationType"));
/* 396 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOf"));
/* 397 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType"));
/* 398 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeySize"));
/* 399 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignatureAlgorithm"));
/* 400 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptionAlgorithm"));
/* 401 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CanonicalizationAlgorithm"));
/* 402 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKeyAlgorithm"));
/* 403 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Encryption"));
/* 404 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryption"));
/* 405 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKey"));
/* 406 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignWith"));
/* 407 */     supportedAssertions.add(new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptWith"));
/*     */     
/* 409 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EnableEPRIdentity"));
/* 410 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EncSCCancel"));
/* 411 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "EncSCCancel"));
/* 412 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisableStreamingSecurity"));
/* 413 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisableStreamingSecurity"));
/* 414 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisableTimestampSigning"));
/* 415 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisableTimestampSigning"));
/* 416 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptHeaderContent"));
/* 417 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptHeaderContent"));
/* 418 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "EncryptRMLifecycleMessage"));
/* 419 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "EncryptRMLifecycleMessage"));
/* 420 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisableInclusivePrefixList"));
/* 421 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisableInclusivePrefixList"));
/* 422 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "DisablePayloadBuffering"));
/* 423 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "DisablePayloadBuffering"));
/* 424 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "AllowMissingTimestamp"));
/* 425 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "AllowMissingTimestamp"));
/* 426 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "UnsetSecurityMUValue"));
/* 427 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "UnsetSecurityMUValue"));
/*     */     
/* 429 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "KeyStore"));
/* 430 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "TrustStore"));
/*     */     
/* 432 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "KeyStore"));
/* 433 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "TrustStore"));
/*     */     
/* 435 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "SessionManagerStore"));
/*     */ 
/*     */     
/* 438 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "KerberosConfig"));
/* 439 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "KerberosConfig"));
/*     */     
/* 441 */     supportedAssertions.add(new QName("http://schemas.sun.com/ws/2006/05/sc/client", "SCClientConfiguration"));
/* 442 */     supportedAssertions.add(new QName("http://schemas.sun.com/ws/2006/05/sc/server", "SCConfiguration"));
/*     */     
/* 444 */     supportedAssertions.add(new QName("http://schemas.sun.com/ws/2006/05/trust/client", "PreconfiguredSTS"));
/* 445 */     supportedAssertions.add(new QName("http://schemas.sun.com/ws/2006/05/trust/server", "STSConfiguration"));
/*     */     
/* 447 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "CertStore"));
/* 448 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "CertStore"));
/* 449 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/client", "BSP10"));
/* 450 */     supportedAssertions.add(new QName("http://schemas.sun.com/2006/03/wss/server", "BSP10"));
/*     */ 
/*     */     
/* 453 */     supportedAssertions.add(new QName("http://schemas.xmlsoap.org/ws/2005/05/identity", "RequireFederatedIdentityProvisioning"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion policyAssertion) {
/* 461 */     String uri = policyAssertion.getName().getNamespaceURI();
/*     */     
/* 463 */     if (uri.equals("http://schemas.sun.com/2006/03/wss/server") || uri.equals("http://schemas.sun.com/ws/2006/05/trust/server")) {
/* 464 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*     */     }
/*     */     
/* 467 */     if (policyAssertion instanceof SecurityAssertionValidator) {
/* 468 */       SecurityAssertionValidator.AssertionFitness fitness = ((SecurityAssertionValidator)policyAssertion).validate(false);
/* 469 */       if (fitness == SecurityAssertionValidator.AssertionFitness.IS_VALID) {
/* 470 */         return PolicyAssertionValidator.Fitness.SUPPORTED;
/*     */       }
/* 472 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*     */     } 
/*     */ 
/*     */     
/* 476 */     if (supportedAssertions.contains(policyAssertion.getName())) {
/* 477 */       return PolicyAssertionValidator.Fitness.SUPPORTED;
/*     */     }
/* 479 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion policyAssertion) {
/* 484 */     String uri = policyAssertion.getName().getNamespaceURI();
/*     */     
/* 486 */     if (uri.equals("http://schemas.sun.com/2006/03/wss/client") || uri.equals("http://schemas.sun.com/2006/03/wss/client") || uri.equals("http://schemas.sun.com/ws/2006/05/sc/client") || uri.equals("http://schemas.sun.com/ws/2006/05/trust/client"))
/*     */     {
/* 488 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*     */     }
/*     */     
/* 491 */     if (policyAssertion instanceof SecurityAssertionValidator)
/* 492 */       return (((SecurityAssertionValidator)policyAssertion).validate(true) == SecurityAssertionValidator.AssertionFitness.IS_VALID) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNSUPPORTED; 
/* 493 */     if (supportedAssertions.contains(policyAssertion.getName())) {
/* 494 */       return PolicyAssertionValidator.Fitness.SUPPORTED;
/*     */     }
/* 496 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] declareSupportedDomains() {
/* 501 */     return new String[] { SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri, "http://schemas.xmlsoap.org/ws/2005/02/trust", "http://schemas.sun.com/2006/03/wss/client", "http://schemas.sun.com/2006/03/wss/server", "http://schemas.sun.com/ws/2006/05/sc/client" };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecurityPolicyValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */