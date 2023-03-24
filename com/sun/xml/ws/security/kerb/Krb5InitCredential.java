/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import java.util.Date;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.Config;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5InitCredential
/*     */   extends KerberosTicket
/*     */   implements Krb5CredElement
/*     */ {
/*     */   private static final long serialVersionUID = 7723415700837898232L;
/*     */   private Krb5NameElement name;
/*     */   private Credentials krb5Credentials;
/*     */   
/*     */   private Krb5InitCredential(Krb5NameElement name, byte[] asn1Encoding, KerberosPrincipal client, KerberosPrincipal server, byte[] sessionKey, int keyType, boolean[] flags, Date authTime, Date startTime, Date endTime, Date renewTill, InetAddress[] clientAddresses) throws GSSException {
/*  55 */     super(asn1Encoding, client, server, sessionKey, keyType, flags, authTime, startTime, endTime, renewTill, clientAddresses);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     this.name = name;
/*     */ 
/*     */     
/*     */     try {
/*  71 */       this.krb5Credentials = new Credentials(asn1Encoding, client.getName(), server.getName(), sessionKey, keyType, flags, authTime, startTime, endTime, renewTill, clientAddresses);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  82 */     catch (KrbException e) {
/*  83 */       throw new GSSException(13, -1, e.getMessage());
/*     */     }
/*  85 */     catch (IOException e) {
/*  86 */       throw new GSSException(13, -1, e.getMessage());
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
/*     */   private Krb5InitCredential(Krb5NameElement name, Credentials delegatedCred, byte[] asn1Encoding, KerberosPrincipal client, KerberosPrincipal server, byte[] sessionKey, int keyType, boolean[] flags, Date authTime, Date startTime, Date endTime, Date renewTill, InetAddress[] clientAddresses) throws GSSException {
/* 106 */     super(asn1Encoding, client, server, sessionKey, keyType, flags, authTime, startTime, endTime, renewTill, clientAddresses);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     this.name = name;
/*     */ 
/*     */     
/* 121 */     this.krb5Credentials = delegatedCred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5InitCredential getInstance(int caller, Krb5NameElement name, int initLifetime) throws GSSException {
/* 128 */     KerberosTicket tgt = getTgt(caller, name, initLifetime);
/* 129 */     if (tgt == null) {
/* 130 */       throw new GSSException(13, -1, "Failed to find any Kerberos tgt");
/*     */     }
/*     */     
/* 133 */     if (name == null) {
/* 134 */       String fullName = tgt.getClient().getName();
/* 135 */       name = Krb5NameElement.getInstance(fullName, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */     } 
/*     */ 
/*     */     
/* 139 */     return new Krb5InitCredential(name, tgt.getEncoded(), tgt.getClient(), tgt.getServer(), tgt.getSessionKey().getEncoded(), tgt.getSessionKeyType(), tgt.getFlags(), tgt.getAuthTime(), tgt.getStartTime(), tgt.getEndTime(), tgt.getRenewTill(), tgt.getClientAddresses());
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
/*     */   static Krb5InitCredential getInstance(Krb5NameElement name, Credentials delegatedCred) throws GSSException {
/* 157 */     EncryptionKey sessionKey = delegatedCred.getSessionKey();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     PrincipalName cPrinc = delegatedCred.getClient();
/* 165 */     PrincipalName sPrinc = delegatedCred.getServer();
/*     */     
/* 167 */     KerberosPrincipal client = null;
/* 168 */     KerberosPrincipal server = null;
/*     */     
/* 170 */     Krb5NameElement credName = null;
/*     */     
/* 172 */     if (cPrinc != null) {
/* 173 */       String fullName = cPrinc.getName();
/* 174 */       credName = Krb5NameElement.getInstance(fullName, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */       
/* 176 */       client = new KerberosPrincipal(fullName);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (sPrinc != null) {
/* 182 */       server = new KerberosPrincipal(sPrinc.getName());
/*     */     }
/*     */ 
/*     */     
/* 186 */     return new Krb5InitCredential(credName, delegatedCred, delegatedCred.getEncoded(), client, server, sessionKey.getBytes(), sessionKey.getEType(), delegatedCred.getFlags(), delegatedCred.getAuthTime(), delegatedCred.getStartTime(), delegatedCred.getEndTime(), delegatedCred.getRenewTill(), delegatedCred.getClientAddresses());
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
/*     */   public final GSSNameSpi getName() throws GSSException {
/* 209 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/* 219 */     int retVal = 0;
/* 220 */     retVal = (int)(getEndTime().getTime() - (new Date()).getTime());
/*     */ 
/*     */     
/* 223 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/* 233 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isInitiatorCredential() throws GSSException {
/* 237 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isAcceptorCredential() throws GSSException {
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Oid getMechanism() {
/* 252 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public final Provider getProvider() {
/* 256 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Credentials getKrb5Credentials() {
/* 265 */     return this.krb5Credentials;
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
/*     */   public void dispose() throws GSSException {
/*     */     try {
/* 278 */       destroy();
/* 279 */     } catch (DestroyFailedException e) {
/* 280 */       GSSException gssException = new GSSException(11, -1, "Could not destroy credentials - " + e.getMessage());
/*     */ 
/*     */       
/* 283 */       gssException.initCause(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static KerberosTicket getTgt(int caller, Krb5NameElement name, int initLifetime) throws GSSException {
/* 294 */     final String clientPrincipal, realm = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (name != null) {
/* 302 */       clientPrincipal = name.getKrb5PrincipalName().getName();
/* 303 */       realm = name.getKrb5PrincipalName().getRealmAsString();
/*     */     } else {
/* 305 */       clientPrincipal = null;
/*     */       try {
/* 307 */         Config config = Config.getInstance();
/* 308 */         realm = config.getDefaultRealm();
/* 309 */       } catch (KrbException e) {
/* 310 */         GSSException ge = new GSSException(13, -1, "Attempt to obtain INITIATE credentials failed! (" + e.getMessage() + ")");
/*     */ 
/*     */ 
/*     */         
/* 314 */         ge.initCause(e);
/* 315 */         throw ge;
/*     */       } 
/*     */     } 
/*     */     
/* 319 */     final String tgsPrincipal = new String("krbtgt/" + realm + "@" + realm);
/*     */ 
/*     */     
/* 322 */     final AccessControlContext acc = AccessController.getContext();
/*     */     
/*     */     try {
/* 325 */       final int realCaller = (caller == -1) ? 1 : caller;
/*     */ 
/*     */       
/* 328 */       return AccessController.<KerberosTicket>doPrivileged(new PrivilegedExceptionAction<KerberosTicket>()
/*     */           {
/*     */             public Object run() throws Exception {
/* 331 */               return Krb5Util.getTicket(realCaller, clientPrincipal, tgsPrincipal, acc);
/*     */             }
/*     */           });
/*     */     }
/* 335 */     catch (PrivilegedActionException e) {
/* 336 */       GSSException ge = new GSSException(13, -1, "Attempt to obtain new INITIATE credentials failed! (" + e.getMessage() + ")");
/*     */ 
/*     */ 
/*     */       
/* 340 */       ge.initCause(e.getException());
/* 341 */       throw ge;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5InitCredential.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */