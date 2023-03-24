/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.wss.RealmAuthenticationAdapter;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import javax.security.auth.callback.Callback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PasswordValidationCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private Request request;
/*     */   private boolean result = false;
/*     */   private PasswordValidator validator;
/*  71 */   private RealmAuthenticationAdapter authenticator = null;
/*     */   
/*     */   public PasswordValidationCallback(Request request) {
/*  74 */     this.request = request;
/*     */   }
/*     */   
/*     */   public boolean getResult() {
/*     */     try {
/*  79 */       if (this.validator != null)
/*  80 */         this.result = this.validator.validate(this.request); 
/*  81 */     } catch (ClassCastException e) {
/*  82 */       throw e;
/*  83 */     } catch (Exception e) {
/*  84 */       return false;
/*     */     } 
/*  86 */     return this.result;
/*     */   }
/*     */   
/*     */   public Request getRequest() {
/*  90 */     return this.request;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValidator(PasswordValidator validator) {
/*  98 */     this.validator = validator;
/*  99 */     if (this.validator instanceof ValidatorExtension) {
/* 100 */       ((ValidatorExtension)this.validator).setRuntimeProperties(getRuntimeProperties());
/*     */     }
/*     */   }
/*     */   
/*     */   public PasswordValidator getValidator() {
/* 105 */     return this.validator;
/*     */   }
/*     */   
/*     */   public void setRealmAuthentcationAdapter(RealmAuthenticationAdapter adapter) {
/* 109 */     this.authenticator = adapter;
/*     */   }
/*     */   
/*     */   public RealmAuthenticationAdapter getRealmAuthenticationAdapter() {
/* 113 */     return this.authenticator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Request {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PlainTextPasswordRequest
/*     */     implements Request
/*     */   {
/*     */     private String password;
/*     */ 
/*     */     
/*     */     private String userName;
/*     */ 
/*     */ 
/*     */     
/*     */     public PlainTextPasswordRequest(String userName, String password) {
/* 135 */       this.password = password;
/* 136 */       this.userName = userName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getUsername() {
/* 145 */       return this.userName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPassword() {
/* 154 */       return this.password;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DigestPasswordRequest
/*     */     implements Request
/*     */   {
/*     */     private String password;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String userName;
/*     */ 
/*     */ 
/*     */     
/*     */     private String digest;
/*     */ 
/*     */ 
/*     */     
/*     */     private String nonce;
/*     */ 
/*     */ 
/*     */     
/*     */     private String created;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DigestPasswordRequest(String userName, String digest, String nonce, String created) {
/* 188 */       this.userName = userName;
/* 189 */       this.digest = digest;
/* 190 */       this.nonce = nonce;
/* 191 */       this.created = created;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPassword(String password) {
/* 199 */       this.password = password;
/*     */     }
/*     */     
/*     */     public String getPassword() {
/* 203 */       return this.password;
/*     */     }
/*     */     
/*     */     public String getUsername() {
/* 207 */       return this.userName;
/*     */     }
/*     */     
/*     */     public String getDigest() {
/* 211 */       return this.digest;
/*     */     }
/*     */     
/*     */     public String getNonce() {
/* 215 */       return this.nonce;
/*     */     }
/*     */     
/*     */     public String getCreated() {
/* 219 */       return this.created;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DerivedKeyPasswordRequest
/*     */     implements Request
/*     */   {
/*     */     private String userName;
/*     */     
/*     */     private String password;
/*     */     private String created;
/*     */     
/*     */     public DerivedKeyPasswordRequest(String userName) {
/* 233 */       this.userName = userName;
/*     */     }
/*     */     
/*     */     public void setPassword(String password) {
/* 237 */       this.password = password;
/*     */     }
/*     */     public String getPassword() {
/* 240 */       return this.password;
/*     */     }
/*     */     public String getUsername() {
/* 243 */       return this.userName;
/*     */     }
/*     */     public void setUsername(String name) {
/* 246 */       this.userName = name;
/*     */     }
/*     */     public String getCreated() {
/* 249 */       return this.created;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface PasswordValidator
/*     */   {
/*     */     boolean validate(PasswordValidationCallback.Request param1Request) throws PasswordValidationCallback.PasswordValidationException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DigestPasswordValidator
/*     */     implements PasswordValidator
/*     */   {
/*     */     public boolean validate(PasswordValidationCallback.Request request) throws PasswordValidationCallback.PasswordValidationException {
/*     */       byte[] utf8Bytes, bytesToHash, hash;
/* 272 */       PasswordValidationCallback.DigestPasswordRequest req = (PasswordValidationCallback.DigestPasswordRequest)request;
/* 273 */       String passwd = req.getPassword();
/* 274 */       String nonce = req.getNonce();
/* 275 */       String created = req.getCreated();
/* 276 */       String passwordDigest = req.getDigest();
/*     */ 
/*     */       
/* 279 */       if (null == passwd)
/* 280 */         return false; 
/* 281 */       byte[] decodedNonce = null;
/* 282 */       if (null != nonce) {
/*     */         try {
/* 284 */           decodedNonce = Base64.decode(nonce);
/* 285 */         } catch (Base64DecodingException bde) {
/* 286 */           throw new PasswordValidationCallback.PasswordValidationException(bde);
/*     */         } 
/*     */       }
/* 289 */       String utf8String = "";
/* 290 */       if (created != null) {
/* 291 */         utf8String = utf8String + created;
/*     */       }
/* 293 */       utf8String = utf8String + passwd;
/*     */       
/*     */       try {
/* 296 */         utf8Bytes = utf8String.getBytes("utf-8");
/* 297 */       } catch (UnsupportedEncodingException uee) {
/* 298 */         throw new PasswordValidationCallback.PasswordValidationException(uee);
/*     */       } 
/*     */ 
/*     */       
/* 302 */       if (decodedNonce != null) {
/* 303 */         bytesToHash = new byte[utf8Bytes.length + decodedNonce.length]; int i;
/* 304 */         for (i = 0; i < decodedNonce.length; i++)
/* 305 */           bytesToHash[i] = decodedNonce[i]; 
/* 306 */         i = decodedNonce.length;
/* 307 */         for (; i < utf8Bytes.length + decodedNonce.length; 
/* 308 */           i++)
/* 309 */           bytesToHash[i] = utf8Bytes[i - decodedNonce.length]; 
/*     */       } else {
/* 311 */         bytesToHash = utf8Bytes;
/*     */       } 
/*     */       
/*     */       try {
/* 315 */         MessageDigest sha = MessageDigest.getInstance("SHA-1");
/* 316 */         hash = sha.digest(bytesToHash);
/* 317 */       } catch (Exception e) {
/* 318 */         throw new PasswordValidationCallback.PasswordValidationException("Password Digest could not be created" + e);
/*     */       } 
/*     */       
/* 321 */       return passwordDigest.equals(Base64.encode(hash));
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class WsitDigestPasswordValidator
/*     */     extends DigestPasswordValidator {
/*     */     public abstract void setPassword(PasswordValidationCallback.Request param1Request);
/*     */   }
/*     */   
/*     */   public static abstract class DerivedKeyPasswordValidator
/*     */     implements PasswordValidator {
/*     */     public abstract void setPassword(PasswordValidationCallback.Request param1Request);
/*     */   }
/*     */   
/*     */   public static class PasswordValidationException
/*     */     extends Exception {
/*     */     public PasswordValidationException(String message) {
/* 338 */       super(message);
/*     */     }
/*     */     
/*     */     public PasswordValidationException(String message, Throwable cause) {
/* 342 */       super(message, cause);
/*     */     }
/*     */     
/*     */     public PasswordValidationException(Throwable cause) {
/* 346 */       super(cause);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\PasswordValidationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */