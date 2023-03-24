/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import com.sun.xml.ws.security.jgss.XWSSProvider;
/*     */ import java.security.Provider;
/*     */ import java.util.Vector;
/*     */ import javax.security.auth.kerberos.ServicePermission;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.GSSUtil;
/*     */ import sun.security.jgss.spi.GSSContextSpi;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.jgss.spi.MechanismFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Krb5MechFactory
/*     */   implements MechanismFactory
/*     */ {
/*  31 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*     */   
/*  33 */   static final Provider PROVIDER = (Provider)new XWSSProvider();
/*     */ 
/*     */   
/*  36 */   static final Oid GSS_KRB5_MECH_OID = createOid("1.2.840.113554.1.2.2");
/*     */ 
/*     */   
/*  39 */   static final Oid NT_GSS_KRB5_PRINCIPAL = createOid("1.2.840.113554.1.2.2.1");
/*     */ 
/*     */   
/*  42 */   private static Oid[] nameTypes = new Oid[] { GSSName.NT_USER_NAME, GSSName.NT_HOSTBASED_SERVICE, GSSName.NT_EXPORT_NAME, NT_GSS_KRB5_PRINCIPAL };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int caller;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Krb5CredElement getCredFromSubject(GSSNameSpi name, boolean initiate) throws GSSException {
/*  53 */     Vector<Krb5CredElement> creds = GSSUtil.searchSubject(name, GSS_KRB5_MECH_OID, initiate, initiate ? (Class)Krb5InitCredential.class : (Class)Krb5AcceptCredential.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     Krb5CredElement result = (creds == null || creds.isEmpty()) ? null : creds.firstElement();
/*     */ 
/*     */ 
/*     */     
/*  63 */     if (result != null) {
/*  64 */       if (initiate) {
/*  65 */         checkInitCredPermission((Krb5NameElement)result.getName());
/*     */       } else {
/*  67 */         checkAcceptCredPermission((Krb5NameElement)result.getName(), name);
/*     */       } 
/*     */     }
/*     */     
/*  71 */     return result;
/*     */   }
/*     */   
/*     */   public Krb5MechFactory(int caller) {
/*  75 */     this.caller = caller;
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(String nameStr, Oid nameType) throws GSSException {
/*  80 */     return Krb5NameElement.getInstance(nameStr, nameType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSNameSpi getNameElement(byte[] name, Oid nameType) throws GSSException {
/*  88 */     return Krb5NameElement.getInstance(new String(name), nameType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSCredentialSpi getCredentialElement(GSSNameSpi name, int initLifetime, int acceptLifetime, int usage) throws GSSException {
/*  95 */     if (name != null && !(name instanceof Krb5NameElement)) {
/*  96 */       name = Krb5NameElement.getInstance(name.toString(), name.getStringNameType());
/*     */     }
/*     */ 
/*     */     
/* 100 */     Krb5CredElement credElement = getCredFromSubject(name, (usage != 2));
/*     */ 
/*     */     
/* 103 */     if (credElement == null) {
/* 104 */       if (usage == 1 || usage == 0) {
/*     */         
/* 106 */         credElement = Krb5InitCredential.getInstance(this.caller, (Krb5NameElement)name, initLifetime);
/*     */         
/* 108 */         checkInitCredPermission((Krb5NameElement)credElement.getName());
/*     */       }
/* 110 */       else if (usage == 2) {
/* 111 */         credElement = Krb5AcceptCredential.getInstance(this.caller, (Krb5NameElement)name);
/*     */ 
/*     */         
/* 114 */         checkAcceptCredPermission((Krb5NameElement)credElement.getName(), name);
/*     */       } else {
/*     */         
/* 117 */         throw new GSSException(11, -1, "Unknown usage mode requested");
/*     */       } 
/*     */     }
/* 120 */     return credElement;
/*     */   }
/*     */   
/*     */   public static void checkInitCredPermission(Krb5NameElement name) {
/* 124 */     SecurityManager sm = System.getSecurityManager();
/* 125 */     if (sm != null) {
/* 126 */       String realm = name.getKrb5PrincipalName().getRealmAsString();
/* 127 */       String tgsPrincipal = new String("krbtgt/" + realm + '@' + realm);
/*     */       
/* 129 */       ServicePermission perm = new ServicePermission(tgsPrincipal, "initiate");
/*     */       
/*     */       try {
/* 132 */         sm.checkPermission(perm);
/* 133 */       } catch (SecurityException e) {
/* 134 */         if (DEBUG) {
/* 135 */           System.out.println("Permission to initiatekerberos init credential" + e.getMessage());
/*     */         }
/*     */         
/* 138 */         throw e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkAcceptCredPermission(Krb5NameElement name, GSSNameSpi originalName) {
/* 145 */     SecurityManager sm = System.getSecurityManager();
/* 146 */     if (sm != null) {
/* 147 */       ServicePermission perm = new ServicePermission(name.getKrb5PrincipalName().getName(), "accept");
/*     */       
/*     */       try {
/* 150 */         sm.checkPermission(perm);
/* 151 */       } catch (SecurityException e) {
/* 152 */         if (originalName == null)
/*     */         {
/* 154 */           e = new SecurityException("No permission to acquire Kerberos accept credential");
/*     */         }
/*     */ 
/*     */         
/* 158 */         throw e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(GSSNameSpi peer, GSSCredentialSpi myInitiatorCred, int lifetime) throws GSSException {
/* 166 */     if (peer != null && !(peer instanceof Krb5NameElement)) {
/* 167 */       peer = Krb5NameElement.getInstance(peer.toString(), peer.getStringNameType());
/*     */     }
/*     */ 
/*     */     
/* 171 */     if (myInitiatorCred == null) {
/* 172 */       myInitiatorCred = getCredentialElement(null, lifetime, 0, 1);
/*     */     }
/*     */     
/* 175 */     return new Krb5Context(this.caller, (Krb5NameElement)peer, (Krb5CredElement)myInitiatorCred, lifetime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(GSSCredentialSpi myAcceptorCred) throws GSSException {
/* 182 */     if (myAcceptorCred == null) {
/* 183 */       myAcceptorCred = getCredentialElement(null, 0, 2147483647, 2);
/*     */     }
/*     */     
/* 186 */     return new Krb5Context(this.caller, (Krb5CredElement)myAcceptorCred);
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSContextSpi getMechanismContext(byte[] exportedContext) throws GSSException {
/* 191 */     return new Krb5Context(this.caller, exportedContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Oid getMechanismOid() {
/* 196 */     return GSS_KRB5_MECH_OID;
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 200 */     return PROVIDER;
/*     */   }
/*     */ 
/*     */   
/*     */   public Oid[] getNameTypes() {
/* 205 */     return nameTypes;
/*     */   }
/*     */   
/*     */   private static Oid createOid(String oidStr) {
/* 209 */     Oid retVal = null;
/*     */     try {
/* 211 */       retVal = new Oid(oidStr);
/* 212 */     } catch (GSSException e) {}
/*     */ 
/*     */     
/* 215 */     return retVal;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5MechFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */