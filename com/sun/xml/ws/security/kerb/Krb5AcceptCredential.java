/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.security.Provider;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
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
/*     */ public class Krb5AcceptCredential
/*     */   extends KerberosKey
/*     */   implements Krb5CredElement
/*     */ {
/*     */   private static final long serialVersionUID = 7714332137352567952L;
/*     */   private Krb5NameElement name;
/*     */   private EncryptionKey[] krb5EncryptionKeys;
/*     */   
/*     */   private Krb5AcceptCredential(Krb5NameElement name, KerberosKey[] keys) {
/*  55 */     super(keys[0].getPrincipal(), keys[0].getEncoded(), keys[0].getKeyType(), keys[0].getVersionNumber());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.name = name;
/*     */     
/*  62 */     this.krb5EncryptionKeys = new EncryptionKey[keys.length];
/*  63 */     for (int i = 0; i < keys.length; i++) {
/*  64 */       this.krb5EncryptionKeys[i] = new EncryptionKey(keys[i].getEncoded(), keys[i].getKeyType(), new Integer(keys[i].getVersionNumber()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Krb5AcceptCredential getInstance(final int caller, Krb5NameElement name) throws GSSException {
/*     */     KerberosKey[] keys;
/*  73 */     final String serverPrinc = (name == null) ? null : name.getKrb5PrincipalName().getName();
/*     */     
/*  75 */     final AccessControlContext acc = AccessController.getContext();
/*     */ 
/*     */     
/*     */     try {
/*  79 */       keys = AccessController.<KerberosKey[]>doPrivileged(new PrivilegedExceptionAction<KerberosKey>()
/*     */           {
/*     */             public Object run() throws Exception {
/*  82 */               return Krb5Util.getKeys((caller == -1) ? 2 : caller, serverPrinc, acc);
/*     */             }
/*     */           });
/*     */     }
/*  86 */     catch (PrivilegedActionException e) {
/*  87 */       GSSException ge = new GSSException(13, -1, "Attempt to obtain new ACCEPT credentials failed!");
/*     */ 
/*     */       
/*  90 */       ge.initCause(e.getException());
/*  91 */       throw ge;
/*     */     } 
/*     */     
/*  94 */     if (keys == null || keys.length == 0) {
/*  95 */       throw new GSSException(13, -1, "Failed to find any Kerberos Key");
/*     */     }
/*     */     
/*  98 */     if (name == null) {
/*  99 */       String fullName = keys[0].getPrincipal().getName();
/* 100 */       name = Krb5NameElement.getInstance(fullName, Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return new Krb5AcceptCredential(name, keys);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final GSSNameSpi getName() throws GSSException {
/* 115 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInitLifetime() throws GSSException {
/* 125 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAcceptLifetime() throws GSSException {
/* 135 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public boolean isInitiatorCredential() throws GSSException {
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isAcceptorCredential() throws GSSException {
/* 143 */     return true;
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
/* 154 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public final Provider getProvider() {
/* 158 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */   
/*     */   EncryptionKey[] getKrb5EncryptionKeys() {
/* 162 */     return this.krb5EncryptionKeys;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() throws GSSException {
/*     */     try {
/* 170 */       destroy();
/* 171 */     } catch (DestroyFailedException e) {
/* 172 */       GSSException gssException = new GSSException(11, -1, "Could not destroy credentials - " + e.getMessage());
/*     */ 
/*     */       
/* 175 */       gssException.initCause(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws DestroyFailedException {
/* 184 */     if (this.krb5EncryptionKeys != null) {
/* 185 */       for (int i = 0; i < this.krb5EncryptionKeys.length; i++) {
/* 186 */         this.krb5EncryptionKeys[i].destroy();
/*     */       }
/* 188 */       this.krb5EncryptionKeys = null;
/*     */     } 
/*     */     
/* 191 */     super.destroy();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5AcceptCredential.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */