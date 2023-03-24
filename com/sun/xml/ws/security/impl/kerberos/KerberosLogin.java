/*     */ package com.sun.xml.ws.security.impl.kerberos;
/*     */ 
/*     */ import com.sun.security.auth.callback.TextCallbackHandler;
/*     */ import com.sun.xml.ws.security.jgss.XWSSProvider;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Key;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.login.AccountExpiredException;
/*     */ import javax.security.auth.login.CredentialExpiredException;
/*     */ import javax.security.auth.login.FailedLoginException;
/*     */ import javax.security.auth.login.LoginContext;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import org.ietf.jgss.GSSContext;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSManager;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KerberosLogin
/*     */ {
/*  78 */   private static final String javaVersion = System.getProperty("java.version");
/*  79 */   private static final boolean isJava6Or5 = (javaVersion.startsWith("1.6") || javaVersion.startsWith("1.5"));
/*  80 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KerberosContext login(String loginModule, String servicePrincipal, boolean credDeleg) throws XWSSecurityException {
/*  89 */     KerberosContext krbContext = new KerberosContext();
/*  90 */     LoginContext lc = null;
/*     */     try {
/*  92 */       lc = new LoginContext(loginModule, new TextCallbackHandler());
/*  93 */     } catch (LoginException le) {
/*  94 */       throw new XWSSecurityException("Cannot create LoginContext. ", le);
/*  95 */     } catch (SecurityException se) {
/*  96 */       throw new XWSSecurityException("Cannot create LoginContext. ", se);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 101 */       lc.login();
/*     */     }
/* 103 */     catch (AccountExpiredException aee) {
/* 104 */       throw new XWSSecurityException("Your Kerberos account has expired.", aee);
/* 105 */     } catch (CredentialExpiredException cee) {
/* 106 */       throw new XWSSecurityException("Your credentials have expired.", cee);
/* 107 */     } catch (FailedLoginException fle) {
/* 108 */       throw new XWSSecurityException("Authentication Failed", fle);
/* 109 */     } catch (Exception e) {
/* 110 */       throw new XWSSecurityException("Unexpected Exception in Kerberos login - unable to continue", e);
/*     */     } 
/*     */     
/*     */     try {
/* 114 */       Subject loginSubject = lc.getSubject();
/* 115 */       Subject.doAsPrivileged(loginSubject, new KerberosClientSetupAction(servicePrincipal, credDeleg), (AccessControlContext)null);
/*     */ 
/*     */ 
/*     */       
/* 119 */       Set<Object> setPubCred = loginSubject.getPublicCredentials();
/* 120 */       Iterator<Object> iter1 = setPubCred.iterator();
/* 121 */       GSSContext gssContext = null;
/* 122 */       while (iter1.hasNext()) {
/* 123 */         Object pubObject = iter1.next();
/* 124 */         if (pubObject instanceof byte[]) {
/* 125 */           krbContext.setKerberosToken((byte[])pubObject); continue;
/* 126 */         }  if (pubObject instanceof GSSContext) {
/* 127 */           gssContext = (GSSContext)pubObject;
/* 128 */           krbContext.setGSSContext(gssContext);
/*     */         } 
/*     */       } 
/* 131 */       if (!isJava6Or5) {
/* 132 */         if (gssContext != null && gssContext.isEstablished()) {
/*     */ 
/*     */           
/*     */           try {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 140 */             Class<?> inquireType = Class.forName("com.sun.security.jgss.InquireType");
/* 141 */             Class<?> extendedGSSContext = Class.forName("com.sun.security.jgss.ExtendedGSSContext");
/* 142 */             Method inquireSecContext = extendedGSSContext.getMethod("inquireSecContext", new Class[] { inquireType });
/* 143 */             Key key = (Key)inquireSecContext.invoke(gssContext, new Object[] { Enum.valueOf(inquireType, "KRB5_GET_SESSION_KEY") });
/* 144 */             krbContext.setSecretKey(key.getEncoded());
/* 145 */           } catch (IllegalAccessException ex) {
/* 146 */             log.log(Level.SEVERE, (String)null, ex);
/* 147 */             throw new XWSSecurityException(ex);
/* 148 */           } catch (IllegalArgumentException ex) {
/* 149 */             log.log(Level.SEVERE, (String)null, ex);
/* 150 */             throw new XWSSecurityException(ex);
/* 151 */           } catch (InvocationTargetException ex) {
/* 152 */             log.log(Level.SEVERE, (String)null, ex);
/* 153 */             throw new XWSSecurityException(ex);
/* 154 */           } catch (NoSuchMethodException ex) {
/* 155 */             log.log(Level.SEVERE, (String)null, ex);
/* 156 */             throw new XWSSecurityException(ex);
/* 157 */           } catch (SecurityException ex) {
/* 158 */             log.log(Level.SEVERE, (String)null, ex);
/* 159 */             throw new XWSSecurityException(ex);
/* 160 */           } catch (ClassNotFoundException ex) {
/* 161 */             log.log(Level.SEVERE, (String)null, ex);
/* 162 */             throw new XWSSecurityException(ex);
/*     */           } 
/*     */         } else {
/*     */           
/* 166 */           throw new XWSSecurityException("GSSContext was null in the Login Subject");
/*     */         } 
/*     */       } else {
/* 169 */         Set<Object> setPrivCred = loginSubject.getPrivateCredentials();
/* 170 */         Iterator<Object> iter2 = setPrivCred.iterator();
/* 171 */         while (iter2.hasNext()) {
/* 172 */           Object privObject = iter2.next();
/* 173 */           if (privObject instanceof KerberosTicket) {
/* 174 */             KerberosTicket kerbTicket = (KerberosTicket)privObject;
/*     */             try {
/* 176 */               if (kerbTicket.getServer().getName().equals(gssContext.getTargName().toString())) {
/* 177 */                 SecretKey sKey = kerbTicket.getSessionKey();
/* 178 */                 byte[] secret = sKey.getEncoded();
/* 179 */                 krbContext.setSecretKey(secret);
/*     */                 break;
/*     */               } 
/* 182 */               log.log(Level.WARNING, LogStringsMessages.WSS_1650_KERBEROS_KEY_WARNING(kerbTicket.getServer().getName(), gssContext.getTargName().toString()));
/*     */ 
/*     */             
/*     */             }
/* 186 */             catch (GSSException ex) {
/* 187 */               throw new XWSSecurityException(ex);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 192 */     } catch (PrivilegedActionException pae) {
/* 193 */       throw new XWSSecurityException(pae);
/*     */     } 
/* 195 */     krbContext.setOnce(true);
/* 196 */     return krbContext;
/*     */   }
/*     */   
/*     */   public KerberosContext login(String loginModule, byte[] token) throws XWSSecurityException {
/* 200 */     KerberosContext krbContext = new KerberosContext();
/* 201 */     LoginContext lc = null;
/*     */     try {
/* 203 */       lc = new LoginContext(loginModule, new TextCallbackHandler());
/* 204 */     } catch (LoginException le) {
/* 205 */       throw new XWSSecurityException("Cannot create LoginContext. ", le);
/* 206 */     } catch (SecurityException se) {
/* 207 */       throw new XWSSecurityException("Cannot create LoginContext. ", se);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 212 */       lc.login();
/*     */     }
/* 214 */     catch (AccountExpiredException aee) {
/* 215 */       throw new XWSSecurityException("Your Kerberos account has expired.", aee);
/* 216 */     } catch (CredentialExpiredException cee) {
/* 217 */       throw new XWSSecurityException("Your credentials have expired.", cee);
/* 218 */     } catch (FailedLoginException fle) {
/* 219 */       throw new XWSSecurityException("Authentication Failed", fle);
/* 220 */     } catch (Exception e) {
/* 221 */       throw new XWSSecurityException("Unexpected Exception in Kerberos login - unable to continue", e);
/*     */     } 
/*     */     
/*     */     try {
/* 225 */       Subject loginSubject = lc.getSubject();
/* 226 */       Subject.doAsPrivileged(loginSubject, new KerberosServerSetupAction(token), (AccessControlContext)null);
/*     */ 
/*     */ 
/*     */       
/* 230 */       Set<Object> setPubCred = loginSubject.getPublicCredentials();
/* 231 */       Iterator<Object> iter1 = setPubCred.iterator();
/* 232 */       GSSContext gssContext = null;
/* 233 */       while (iter1.hasNext()) {
/* 234 */         Object pubObject = iter1.next();
/* 235 */         if (pubObject instanceof byte[]) {
/* 236 */           krbContext.setKerberosToken((byte[])pubObject); continue;
/* 237 */         }  if (pubObject instanceof GSSContext) {
/* 238 */           gssContext = (GSSContext)pubObject;
/* 239 */           krbContext.setGSSContext(gssContext);
/*     */         } 
/*     */       } 
/* 242 */       if (!isJava6Or5) {
/* 243 */         if (gssContext != null && gssContext.isEstablished()) {
/*     */ 
/*     */           
/*     */           try {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 251 */             Class<?> inquireType = Class.forName("com.sun.security.jgss.InquireType");
/* 252 */             Class<?> extendedGSSContext = Class.forName("com.sun.security.jgss.ExtendedGSSContext");
/* 253 */             Method inquireSecContext = extendedGSSContext.getMethod("inquireSecContext", new Class[] { inquireType });
/* 254 */             Key key = (Key)inquireSecContext.invoke(gssContext, new Object[] { Enum.valueOf(inquireType, "KRB5_GET_SESSION_KEY") });
/* 255 */             krbContext.setSecretKey(key.getEncoded());
/* 256 */           } catch (IllegalAccessException ex) {
/* 257 */             log.log(Level.SEVERE, (String)null, ex);
/* 258 */             throw new XWSSecurityException(ex);
/* 259 */           } catch (IllegalArgumentException ex) {
/* 260 */             log.log(Level.SEVERE, (String)null, ex);
/* 261 */             throw new XWSSecurityException(ex);
/* 262 */           } catch (InvocationTargetException ex) {
/* 263 */             log.log(Level.SEVERE, (String)null, ex);
/* 264 */             throw new XWSSecurityException(ex);
/* 265 */           } catch (NoSuchMethodException ex) {
/* 266 */             log.log(Level.SEVERE, (String)null, ex);
/* 267 */             throw new XWSSecurityException(ex);
/* 268 */           } catch (SecurityException ex) {
/* 269 */             log.log(Level.SEVERE, (String)null, ex);
/* 270 */             throw new XWSSecurityException(ex);
/* 271 */           } catch (ClassNotFoundException ex) {
/* 272 */             log.log(Level.SEVERE, (String)null, ex);
/* 273 */             throw new XWSSecurityException(ex);
/*     */           } 
/*     */         } else {
/*     */           
/* 277 */           throw new XWSSecurityException("GSSContext was null in the Login Subject");
/*     */         } 
/*     */       } else {
/* 280 */         Set<Object> setPrivCred = loginSubject.getPrivateCredentials();
/* 281 */         Iterator<Object> iter2 = setPrivCred.iterator();
/* 282 */         while (iter2.hasNext()) {
/* 283 */           Object privObject = iter2.next();
/* 284 */           if (privObject instanceof EncryptionKey) {
/* 285 */             EncryptionKey encKey = (EncryptionKey)privObject;
/* 286 */             byte[] keyBytes = encKey.getBytes();
/* 287 */             krbContext.setSecretKey(keyBytes);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 292 */     } catch (PrivilegedActionException pae) {
/* 293 */       throw new XWSSecurityException(pae);
/*     */     } 
/* 295 */     krbContext.setOnce(false);
/* 296 */     return krbContext;
/*     */   }
/*     */   
/*     */   class KerberosClientSetupAction implements PrivilegedExceptionAction {
/*     */     String server;
/*     */     boolean credentialDelegation = false;
/*     */     
/*     */     public KerberosClientSetupAction(String server, boolean credDeleg) {
/* 304 */       this.server = server;
/* 305 */       this.credentialDelegation = credDeleg;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object run() throws Exception {
/*     */       try {
/* 311 */         Oid krb5Oid = new Oid("1.2.840.113554.1.2.2");
/* 312 */         GSSManager manager = GSSManager.getInstance();
/* 313 */         GSSName serverName = manager.createName(this.server, (Oid)null);
/*     */         
/* 315 */         GSSContext context = manager.createContext(serverName, krb5Oid, null, 0);
/*     */ 
/*     */ 
/*     */         
/* 319 */         context.requestMutualAuth(false);
/* 320 */         context.requestConf(false);
/* 321 */         context.requestInteg(true);
/*     */         
/* 323 */         context.requestCredDeleg(this.credentialDelegation);
/*     */         
/* 325 */         byte[] token = new byte[0];
/* 326 */         token = context.initSecContext(token, 0, token.length);
/*     */         
/* 328 */         AccessControlContext acc = AccessController.getContext();
/* 329 */         Subject loginSubject = Subject.getSubject(acc);
/* 330 */         loginSubject.getPublicCredentials().add(context);
/* 331 */         loginSubject.getPublicCredentials().add(token);
/*     */       }
/* 333 */       catch (Exception e) {
/* 334 */         throw new PrivilegedActionException(e);
/*     */       } 
/* 336 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   class KerberosServerSetupAction
/*     */     implements PrivilegedExceptionAction
/*     */   {
/*     */     byte[] token;
/*     */     
/*     */     public KerberosServerSetupAction(byte[] token) {
/* 346 */       this.token = token;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object run() throws Exception {
/*     */       try {
/* 353 */         final GSSManager manager = GSSManager.getInstance();
/* 354 */         final Oid krb5Oid = new Oid("1.2.840.113554.1.2.2");
/* 355 */         if (KerberosLogin.isJava6Or5) {
/* 356 */           AccessController.doPrivileged(new PrivilegedAction()
/*     */               {
/*     */                 public Object run() {
/*     */                   try {
/* 360 */                     manager.addProviderAtFront((Provider)new XWSSProvider(), krb5Oid);
/* 361 */                   } catch (GSSException gsse) {
/* 362 */                     gsse.printStackTrace();
/*     */                   } 
/* 364 */                   return null;
/*     */                 }
/*     */               });
/*     */         }
/*     */         
/* 369 */         GSSContext context = manager.createContext((GSSCredential)null);
/* 370 */         byte[] outToken = context.acceptSecContext(this.token, 0, this.token.length);
/* 371 */         if (outToken == null || outToken.length != 0);
/*     */ 
/*     */         
/* 374 */         AccessControlContext acc = AccessController.getContext();
/* 375 */         Subject loginSubject = Subject.getSubject(acc);
/* 376 */         loginSubject.getPublicCredentials().add(context);
/* 377 */         loginSubject.getPublicCredentials().add(this.token);
/* 378 */       } catch (Exception e) {
/* 379 */         throw new PrivilegedActionException(e);
/*     */       } 
/* 381 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\kerberos\KerberosLogin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */