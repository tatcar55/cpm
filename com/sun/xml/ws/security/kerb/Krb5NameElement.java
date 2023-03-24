/*     */ package com.sun.xml.ws.security.kerb;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.Provider;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.ServiceName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Krb5NameElement
/*     */   implements GSSNameSpi
/*     */ {
/*     */   private PrincipalName krb5PrincipalName;
/*  32 */   private String gssNameStr = null;
/*  33 */   private Oid gssNameType = null;
/*     */ 
/*     */   
/*  36 */   private static String CHAR_ENCODING = "UTF-8";
/*     */ 
/*     */ 
/*     */   
/*     */   private Krb5NameElement(PrincipalName principalName, String gssNameStr, Oid gssNameType) {
/*  41 */     this.krb5PrincipalName = principalName;
/*  42 */     this.gssNameStr = gssNameStr;
/*  43 */     this.gssNameType = gssNameType;
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
/*     */   static Krb5NameElement getInstance(String gssNameStr, Oid gssNameType) throws GSSException {
/*     */     ServiceName serviceName;
/*  60 */     if (gssNameType == null) {
/*  61 */       gssNameType = Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL;
/*     */     }
/*  63 */     else if (!gssNameType.equals(GSSName.NT_USER_NAME) && !gssNameType.equals(GSSName.NT_HOSTBASED_SERVICE) && !gssNameType.equals(Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL) && !gssNameType.equals(GSSName.NT_EXPORT_NAME)) {
/*     */ 
/*     */ 
/*     */       
/*  67 */       throw new GSSException(4, -1, gssNameType.toString() + " is an unsupported nametype");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  74 */       if (gssNameType.equals(GSSName.NT_EXPORT_NAME) || gssNameType.equals(Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL))
/*     */       {
/*  76 */         PrincipalName principalName = new PrincipalName(gssNameStr, 1);
/*     */       }
/*     */       else
/*     */       {
/*  80 */         String[] components = getComponents(gssNameStr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  94 */         if (gssNameType.equals(GSSName.NT_USER_NAME)) {
/*  95 */           PrincipalName principalName = new PrincipalName(gssNameStr, 1);
/*     */         } else {
/*     */           
/*  98 */           String hostName = null;
/*  99 */           String service = components[0];
/* 100 */           if (components.length >= 2) {
/* 101 */             hostName = components[1];
/*     */           }
/* 103 */           String principal = getHostBasedInstance(service, hostName);
/* 104 */           serviceName = new ServiceName(principal, 3);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 109 */     } catch (KrbException e) {
/* 110 */       throw new GSSException(3, -1, e.getMessage());
/*     */     } 
/*     */     
/* 113 */     return new Krb5NameElement((PrincipalName)serviceName, gssNameStr, gssNameType);
/*     */   }
/*     */   
/*     */   static Krb5NameElement getInstance(PrincipalName principalName) {
/* 117 */     return new Krb5NameElement(principalName, principalName.getName(), Krb5MechFactory.NT_GSS_KRB5_PRINCIPAL);
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
/*     */   private static String[] getComponents(String gssNameStr) throws GSSException {
/*     */     String[] retVal;
/* 131 */     int separatorPos = gssNameStr.lastIndexOf('@', gssNameStr.length());
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (separatorPos > 0 && gssNameStr.charAt(separatorPos - 1) == '\\')
/*     */     {
/*     */       
/* 138 */       if (separatorPos - 2 < 0 || gssNameStr.charAt(separatorPos - 2) != '\\')
/*     */       {
/* 140 */         separatorPos = -1;
/*     */       }
/*     */     }
/* 143 */     if (separatorPos > 0) {
/* 144 */       String serviceName = gssNameStr.substring(0, separatorPos);
/* 145 */       String hostName = gssNameStr.substring(separatorPos + 1);
/* 146 */       retVal = new String[] { serviceName, hostName };
/*     */     } else {
/* 148 */       retVal = new String[] { gssNameStr };
/*     */     } 
/*     */     
/* 151 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getHostBasedInstance(String serviceName, String hostName) throws GSSException {
/* 158 */     StringBuffer temp = new StringBuffer(serviceName);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 164 */       if (hostName == null) {
/* 165 */         hostName = InetAddress.getLocalHost().getHostName();
/*     */       }
/* 167 */     } catch (UnknownHostException e) {}
/*     */ 
/*     */     
/* 170 */     hostName = hostName.toLowerCase();
/*     */     
/* 172 */     temp = temp.append('/').append(hostName);
/* 173 */     return temp.toString();
/*     */   }
/*     */   
/*     */   public final PrincipalName getKrb5PrincipalName() {
/* 177 */     return this.krb5PrincipalName;
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
/*     */   public boolean equals(GSSNameSpi other) throws GSSException {
/* 192 */     if (other == this) {
/* 193 */       return true;
/*     */     }
/* 195 */     if (other instanceof Krb5NameElement) {
/* 196 */       Krb5NameElement that = (Krb5NameElement)other;
/* 197 */       return this.krb5PrincipalName.getName().equals(that.krb5PrincipalName.getName());
/*     */     } 
/*     */     
/* 200 */     return false;
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
/*     */   public boolean equals(Object another) {
/* 215 */     if (this == another) {
/* 216 */       return true;
/*     */     }
/*     */     
/*     */     try {
/* 220 */       if (another instanceof Krb5NameElement)
/* 221 */         return equals((Krb5NameElement)another); 
/* 222 */     } catch (GSSException e) {}
/*     */ 
/*     */     
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 234 */     return 629 + this.krb5PrincipalName.getName().hashCode();
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
/*     */   public byte[] export() throws GSSException {
/* 257 */     byte[] retVal = null;
/*     */     try {
/* 259 */       retVal = this.krb5PrincipalName.getName().getBytes(CHAR_ENCODING);
/* 260 */     } catch (UnsupportedEncodingException e) {}
/*     */ 
/*     */     
/* 263 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getMechanism() {
/* 272 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
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
/*     */   public String toString() {
/* 284 */     return this.gssNameStr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getGSSNameType() {
/* 292 */     return this.gssNameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Oid getStringNameType() {
/* 303 */     return this.gssNameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnonymousName() {
/* 310 */     return this.gssNameType.equals(GSSName.NT_ANONYMOUS);
/*     */   }
/*     */   
/*     */   public Provider getProvider() {
/* 314 */     return Krb5MechFactory.PROVIDER;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\kerb\Krb5NameElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */