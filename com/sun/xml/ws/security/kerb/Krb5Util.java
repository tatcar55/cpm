/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.util.List;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.login.LoginException;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.EncryptionKey;
/*     */ import sun.security.krb5.KrbException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5Util
/*     */ {
/*  30 */   static final boolean DEBUG = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("sun.security.krb5.debug"))).booleanValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KerberosTicket getTicketFromSubjectAndTgs(int caller, String clientPrincipal, String serverPrincipal, String tgsPrincipal, AccessControlContext acc) throws LoginException, KrbException, IOException {
/*     */     boolean fromAcc;
/*  59 */     Subject accSubj = Subject.getSubject(acc);
/*  60 */     KerberosTicket ticket = (KerberosTicket)SubjectComber.find(accSubj, serverPrincipal, clientPrincipal, KerberosTicket.class);
/*     */ 
/*     */     
/*  63 */     if (ticket != null) {
/*  64 */       return ticket;
/*     */     }
/*     */     
/*  67 */     Subject loginSubj = null;
/*  68 */     if (!GSSUtil.useSubjectCredsOnly()) {
/*     */       
/*     */       try {
/*  71 */         loginSubj = GSSUtil.login(caller, GSSUtil.GSS_KRB5_MECH_OID);
/*  72 */         ticket = (KerberosTicket)SubjectComber.find(loginSubj, serverPrincipal, clientPrincipal, KerberosTicket.class);
/*     */         
/*  74 */         if (ticket != null) {
/*  75 */           return ticket;
/*     */         }
/*  77 */       } catch (LoginException e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     KerberosTicket tgt = (KerberosTicket)SubjectComber.find(accSubj, tgsPrincipal, clientPrincipal, KerberosTicket.class);
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (tgt == null && loginSubj != null) {
/*     */       
/*  93 */       tgt = (KerberosTicket)SubjectComber.find(loginSubj, tgsPrincipal, clientPrincipal, KerberosTicket.class);
/*     */       
/*  95 */       fromAcc = false;
/*     */     } else {
/*  97 */       fromAcc = true;
/*     */     } 
/*     */ 
/*     */     
/* 101 */     if (tgt != null) {
/* 102 */       Credentials tgtCreds = ticketToCreds(tgt);
/* 103 */       Credentials serviceCreds = Credentials.acquireServiceCreds(serverPrincipal, tgtCreds);
/*     */       
/* 105 */       if (serviceCreds != null) {
/* 106 */         ticket = credsToTicket(serviceCreds);
/*     */ 
/*     */         
/* 109 */         if (fromAcc && accSubj != null && !accSubj.isReadOnly()) {
/* 110 */           accSubj.getPrivateCredentials().add(ticket);
/*     */         }
/*     */       } 
/*     */     } 
/* 114 */     return ticket;
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
/*     */   static KerberosTicket getTicket(int caller, String clientPrincipal, String serverPrincipal, AccessControlContext acc) throws LoginException {
/* 129 */     Subject accSubj = Subject.getSubject(acc);
/* 130 */     KerberosTicket ticket = (KerberosTicket)SubjectComber.find(accSubj, serverPrincipal, clientPrincipal, KerberosTicket.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (ticket == null && !GSSUtil.useSubjectCredsOnly()) {
/* 136 */       Subject subject = GSSUtil.login(caller, GSSUtil.GSS_KRB5_MECH_OID);
/* 137 */       ticket = (KerberosTicket)SubjectComber.find(subject, serverPrincipal, clientPrincipal, KerberosTicket.class);
/*     */     } 
/*     */     
/* 140 */     return ticket;
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
/*     */   public static Subject getSubject(int caller, AccessControlContext acc) throws LoginException {
/* 158 */     Subject subject = Subject.getSubject(acc);
/*     */ 
/*     */     
/* 161 */     if (subject == null && !GSSUtil.useSubjectCredsOnly()) {
/* 162 */       subject = GSSUtil.login(caller, GSSUtil.GSS_KRB5_MECH_OID);
/*     */     }
/* 164 */     return subject;
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
/*     */   public static KerberosKey[] getKeys(int caller, String serverPrincipal, AccessControlContext acc) throws LoginException {
/* 180 */     Subject accSubj = Subject.getSubject(acc);
/* 181 */     List kkeys = (List)SubjectComber.findMany(accSubj, serverPrincipal, null, KerberosKey.class);
/*     */ 
/*     */     
/* 184 */     if (kkeys == null && !GSSUtil.useSubjectCredsOnly()) {
/* 185 */       Subject subject = GSSUtil.login(caller, GSSUtil.GSS_KRB5_MECH_OID);
/* 186 */       kkeys = (List)SubjectComber.findMany(subject, serverPrincipal, null, KerberosKey.class);
/*     */     } 
/*     */     
/*     */     int len;
/*     */     
/* 191 */     if (kkeys != null && (len = kkeys.size()) > 0) {
/* 192 */       KerberosKey[] keys = new KerberosKey[len];
/* 193 */       kkeys.toArray((Object[])keys);
/* 194 */       return keys;
/*     */     } 
/* 196 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static KerberosTicket credsToTicket(Credentials serviceCreds) {
/* 201 */     EncryptionKey sessionKey = serviceCreds.getSessionKey();
/* 202 */     return new KerberosTicket(serviceCreds.getEncoded(), new KerberosPrincipal(serviceCreds.getClient().getName()), new KerberosPrincipal(serviceCreds.getServer().getName()), sessionKey.getBytes(), sessionKey.getEType(), serviceCreds.getFlags(), serviceCreds.getAuthTime(), serviceCreds.getStartTime(), serviceCreds.getEndTime(), serviceCreds.getRenewTill(), serviceCreds.getClientAddresses());
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
/*     */   public static Credentials ticketToCreds(KerberosTicket kerbTicket) throws KrbException, IOException {
/* 218 */     return new Credentials(kerbTicket.getEncoded(), kerbTicket.getClient().getName(), kerbTicket.getServer().getName(), kerbTicket.getSessionKey().getEncoded(), kerbTicket.getSessionKeyType(), kerbTicket.getFlags(), kerbTicket.getAuthTime(), kerbTicket.getStartTime(), kerbTicket.getEndTime(), kerbTicket.getRenewTill(), kerbTicket.getClientAddresses());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5Util.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */