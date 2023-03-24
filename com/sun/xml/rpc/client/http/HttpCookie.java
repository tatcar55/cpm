/*     */ package com.sun.xml.rpc.client.http;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.net.URL;
/*     */ import java.util.Date;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpCookie
/*     */   implements Serializable
/*     */ {
/*  46 */   private Date expirationDate = null;
/*     */   private String nameAndValue;
/*     */   private String path;
/*     */   private String domain;
/*     */   private boolean isSecure = false;
/*     */   
/*     */   public HttpCookie(String cookieString) {
/*  53 */     parseCookieString(cookieString);
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
/*     */   public HttpCookie(Date expirationDate, String nameAndValue, String path, String domain, boolean isSecure) {
/*  66 */     this.expirationDate = expirationDate;
/*  67 */     this.nameAndValue = nameAndValue;
/*  68 */     this.path = path;
/*  69 */     this.domain = stripPort(domain);
/*  70 */     this.isSecure = isSecure;
/*     */   }
/*     */   
/*     */   public HttpCookie(URL url, String cookieString) {
/*  74 */     parseCookieString(cookieString);
/*  75 */     applyDefaults(url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyDefaults(URL url) {
/*  84 */     if (this.domain == null) {
/*  85 */       this.domain = url.getHost();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (this.path == null) {
/*  91 */       this.path = url.getFile();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       int last = this.path.lastIndexOf("/");
/*     */       
/* 100 */       if (last > -1) {
/* 101 */         this.path = this.path.substring(0, last);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String stripPort(String domainName) {
/* 108 */     int index = domainName.indexOf(':');
/*     */     
/* 110 */     if (index == -1) {
/* 111 */       return domainName;
/*     */     }
/*     */     
/* 114 */     return domainName.substring(0, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseCookieString(String cookieString) {
/* 123 */     StringTokenizer tokens = new StringTokenizer(cookieString, ";");
/*     */     
/* 125 */     if (!tokens.hasMoreTokens());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     this.nameAndValue = tokens.nextToken().trim();
/*     */     
/* 132 */     while (tokens.hasMoreTokens()) {
/* 133 */       String token = tokens.nextToken().trim();
/*     */       
/* 135 */       if (token.equalsIgnoreCase("secure")) {
/* 136 */         this.isSecure = true; continue;
/*     */       } 
/* 138 */       int equIndex = token.indexOf("=");
/*     */       
/* 140 */       if (equIndex < 0) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 146 */       String attr = token.substring(0, equIndex);
/* 147 */       String val = token.substring(equIndex + 1);
/*     */       
/* 149 */       if (attr.equalsIgnoreCase("path")) {
/* 150 */         this.path = val; continue;
/* 151 */       }  if (attr.equalsIgnoreCase("domain")) {
/* 152 */         if (val.indexOf(".") == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 159 */           this.domain = stripPort(val.substring(1)); continue;
/*     */         } 
/* 161 */         this.domain = stripPort(val); continue;
/*     */       } 
/* 163 */       if (attr.equalsIgnoreCase("expires")) {
/* 164 */         this.expirationDate = parseExpireDate(val);
/*     */       }
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
/*     */   public String getNameValue() {
/* 178 */     return this.nameAndValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 186 */     int index = this.nameAndValue.indexOf("=");
/*     */     
/* 188 */     return this.nameAndValue.substring(0, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDomain() {
/* 197 */     return this.domain;
/*     */   }
/*     */   
/*     */   public String getPath() {
/* 201 */     return this.path;
/*     */   }
/*     */   
/*     */   public Date getExpirationDate() {
/* 205 */     return this.expirationDate;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean hasExpired() {
/* 210 */     if (this.expirationDate == null) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     return (this.expirationDate.getTime() <= System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSaveable() {
/* 222 */     return (this.expirationDate != null && this.expirationDate.getTime() > System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSecure() {
/* 227 */     return this.isSecure;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Date parseExpireDate(String dateString) {
/* 233 */     RfcDateParser parser = new RfcDateParser(dateString);
/* 234 */     Date theDate = parser.getDate();
/*     */     
/* 236 */     return theDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 241 */     String result = this.nameAndValue;
/*     */     
/* 243 */     if (this.expirationDate != null) {
/* 244 */       result = result + "; expires=" + this.expirationDate;
/*     */     }
/*     */     
/* 247 */     if (this.path != null) {
/* 248 */       result = result + "; path=" + this.path;
/*     */     }
/*     */     
/* 251 */     if (this.domain != null) {
/* 252 */       result = result + "; domain=" + this.domain;
/*     */     }
/*     */     
/* 255 */     if (this.isSecure) {
/* 256 */       result = result + "; secure";
/*     */     }
/*     */     
/* 259 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\http\HttpCookie.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */