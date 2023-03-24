/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.security.impl.PasswordDerivedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*     */ import com.sun.xml.ws.security.secext10.AttributedString;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.UsernameToken;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UsernameTokenDataResolver
/*     */ {
/*  71 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.filter", "com.sun.xml.wss.logging.impl.filter.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AuthenticationTokenPolicy.UsernameTokenBinding setSaltandIterationsforUsernameToken(FilterProcessingContext context, UsernameToken unToken, SignaturePolicy policy, AuthenticationTokenPolicy.UsernameTokenBinding untBinding, int firstByte) throws XWSSecurityException, UnsupportedEncodingException {
/*     */     int iterations;
/*  91 */     if (context.getiterationsForPDK() != 0) {
/*  92 */       iterations = context.getiterationsForPDK();
/*     */     } else {
/*  94 */       iterations = 1000;
/*     */     } 
/*  96 */     if (iterations < 1000) {
/*  97 */       iterations = 1000;
/*     */     }
/*  99 */     byte[] macSignature = null;
/* 100 */     PasswordDerivedKey pdk = new PasswordDerivedKey();
/*     */     
/* 102 */     String userName = unToken.getUsernameValue();
/* 103 */     if (userName == null || "".equals(userName)) {
/* 104 */       userName = context.getSecurityEnvironment().getUsername(context.getExtraneousProperties());
/*     */     }
/* 106 */     if (userName == null || "".equals(userName)) {
/* 107 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1409_INVALID_USERNAME_TOKEN());
/* 108 */       throw new XWSSecurityException("Username has not been set");
/*     */     } 
/* 110 */     unToken.setUsernameValue(userName);
/* 111 */     String password = untBinding.getPassword();
/* 112 */     if (!untBinding.hasNoPassword() && (password == null || "".equals(password))) {
/* 113 */       password = context.getSecurityEnvironment().getPassword(context.getExtraneousProperties());
/*     */     }
/* 115 */     if (!untBinding.hasNoPassword() && 
/* 116 */       password == null) {
/* 117 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1424_INVALID_USERNAME_TOKEN());
/* 118 */       throw new XWSSecurityException("Password for the username has not been set");
/*     */     } 
/*     */ 
/*     */     
/* 122 */     AttributedString as = new AttributedString();
/* 123 */     String iterate = Integer.toString(iterations);
/* 124 */     as.setValue(iterate);
/* 125 */     unToken.setIteration(as);
/*     */     
/* 127 */     byte[] salt = null;
/* 128 */     if (unToken.getSalt() == null) {
/*     */       
/* 130 */       salt = pdk.get16ByteSalt();
/* 131 */       AttributedString aString = new AttributedString();
/* 132 */       aString.setValue(Base64.encode(salt));
/* 133 */       unToken.setSalt(aString);
/*     */     } else {
/*     */       
/* 136 */       String decodeString = unToken.getSalt().getValue();
/* 137 */       String iter = unToken.getIteration().getValue();
/* 138 */       iterations = Integer.parseInt(iter);
/*     */       try {
/* 140 */         salt = Base64.decode(decodeString);
/* 141 */       } catch (Base64DecodingException ex) {
/* 142 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1426_BASE_64_DECODING_ERROR(), ex);
/* 143 */         throw new UnsupportedEncodingException("error while decoding the salt in username token");
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     salt[0] = (byte)firstByte;
/*     */     try {
/* 149 */       macSignature = pdk.generate160BitKey(password, iterations, salt);
/* 150 */     } catch (UnsupportedEncodingException ex) {
/* 151 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1425_UNSUPPORTED_ENCODING(), ex);
/* 152 */       throw new UnsupportedEncodingException("error while creating 160 bit key");
/*     */     } 
/* 154 */     untBinding.setSecretKey(macSignature);
/* 155 */     return untBinding;
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
/*     */   public static AuthenticationTokenPolicy.UsernameTokenBinding setSaltandIterationsforUsernameToken(FilterProcessingContext context, UsernameToken unToken, EncryptionPolicy policy, AuthenticationTokenPolicy.UsernameTokenBinding untBinding) throws XWSSecurityException, UnsupportedEncodingException {
/*     */     int iterations;
/* 172 */     if (context.getiterationsForPDK() != 0) {
/* 173 */       iterations = context.getiterationsForPDK();
/*     */     } else {
/* 175 */       iterations = 1000;
/*     */     } 
/* 177 */     if (iterations < 1000) {
/* 178 */       iterations = 1000;
/*     */     }
/* 180 */     byte[] keyof128bits = new byte[16];
/* 181 */     byte[] encSignature = null;
/*     */     
/* 183 */     String userName = unToken.getUsernameValue();
/* 184 */     if (userName == null || "".equals(userName)) {
/* 185 */       userName = context.getSecurityEnvironment().getUsername(context.getExtraneousProperties());
/*     */     }
/* 187 */     if (userName == null || "".equals(userName)) {
/* 188 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1409_INVALID_USERNAME_TOKEN());
/* 189 */       throw new XWSSecurityException("Username has not been set");
/*     */     } 
/* 191 */     unToken.setUsernameValue(userName);
/*     */     
/* 193 */     String password = untBinding.getPassword();
/* 194 */     if (!untBinding.hasNoPassword() && (password == null || "".equals(password))) {
/* 195 */       password = context.getSecurityEnvironment().getPassword(context.getExtraneousProperties());
/*     */     }
/* 197 */     if (!untBinding.hasNoPassword() && 
/* 198 */       password == null) {
/* 199 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1424_INVALID_USERNAME_TOKEN());
/* 200 */       throw new XWSSecurityException("Password for the username has not been set");
/*     */     } 
/*     */ 
/*     */     
/* 204 */     AttributedString as = new AttributedString();
/* 205 */     String iterate = Integer.toString(iterations);
/* 206 */     as.setValue(iterate);
/* 207 */     unToken.setIteration(as);
/* 208 */     PasswordDerivedKey pdk = new PasswordDerivedKey();
/* 209 */     byte[] salt = null;
/* 210 */     if (unToken.getSalt() == null) {
/*     */       
/* 212 */       salt = pdk.get16ByteSalt();
/* 213 */       AttributedString atbs = new AttributedString();
/* 214 */       atbs.setValue(Base64.encode(salt));
/* 215 */       unToken.setSalt(atbs);
/*     */     } else {
/*     */       
/* 218 */       String decodeString = unToken.getSalt().getValue();
/* 219 */       String iter = unToken.getIteration().getValue();
/* 220 */       iterations = Integer.parseInt(iter);
/*     */       try {
/* 222 */         salt = Base64.decode(decodeString);
/* 223 */       } catch (Base64DecodingException ex) {
/* 224 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1426_BASE_64_DECODING_ERROR(), ex);
/* 225 */         throw new UnsupportedEncodingException("error while decoding the salt in username token");
/*     */       } 
/*     */     } 
/* 228 */     salt[0] = 2;
/*     */     try {
/* 230 */       encSignature = pdk.generate160BitKey(password, iterations, salt);
/* 231 */     } catch (UnsupportedEncodingException ex) {
/* 232 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1425_UNSUPPORTED_ENCODING(), ex);
/* 233 */       throw new UnsupportedEncodingException("error while creating 128 bit key");
/*     */     } 
/* 235 */     for (int i = 0; i < 16; i++) {
/* 236 */       keyof128bits[i] = encSignature[i];
/*     */     }
/* 238 */     untBinding.setSecretKey(keyof128bits);
/* 239 */     return untBinding;
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
/*     */   public static AuthenticationTokenPolicy.UsernameTokenBinding resolveUsernameToken(FilterProcessingContext context, UsernameToken token, UsernameToken unToken, AuthenticationTokenPolicy policy) throws XWSSecurityException {
/* 255 */     AuthenticationTokenPolicy.UsernameTokenBinding userNamePolicy = (AuthenticationTokenPolicy.UsernameTokenBinding)policy.getFeatureBinding();
/*     */ 
/*     */     
/* 258 */     String userName = userNamePolicy.getUsername();
/* 259 */     String password = userNamePolicy.getPassword();
/*     */     
/* 261 */     if (userName == null || "".equals(userName)) {
/* 262 */       userName = context.getSecurityEnvironment().getUsername(context.getExtraneousProperties());
/*     */     }
/* 264 */     if (userName == null || "".equals(userName)) {
/* 265 */       log.log(Level.SEVERE, LogStringsMessages.WSS_1409_INVALID_USERNAME_TOKEN());
/* 266 */       throw new XWSSecurityException("Username has not been set");
/*     */     } 
/* 268 */     if (token != null) {
/* 269 */       token.setUsername(userName);
/*     */     } else {
/* 271 */       unToken.setUsernameValue(userName);
/*     */     } 
/* 273 */     if (!userNamePolicy.hasNoPassword() && (password == null || "".equals(password))) {
/* 274 */       password = context.getSecurityEnvironment().getPassword(context.getExtraneousProperties());
/*     */     }
/* 276 */     if (!userNamePolicy.hasNoPassword()) {
/* 277 */       if (password == null) {
/* 278 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1424_INVALID_USERNAME_TOKEN());
/* 279 */         throw new XWSSecurityException("Password for the username has not been set");
/*     */       } 
/* 281 */       if (token != null) {
/* 282 */         token.setPassword(password);
/*     */       } else {
/* 284 */         unToken.setPasswordValue(password);
/*     */       } 
/*     */     } 
/* 287 */     return userNamePolicy;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\UsernameTokenDataResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */