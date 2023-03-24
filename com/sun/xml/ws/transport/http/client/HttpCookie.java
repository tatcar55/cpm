/*      */ package com.sun.xml.ws.transport.http.client;
/*      */ 
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TimeZone;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class HttpCookie
/*      */   implements Cloneable
/*      */ {
/*      */   private String name;
/*      */   private String value;
/*      */   private String comment;
/*      */   private String commentURL;
/*      */   private boolean toDiscard;
/*      */   private String domain;
/*   91 */   private long maxAge = -1L;
/*      */   private String path;
/*      */   private String portlist;
/*      */   private boolean secure;
/*      */   private boolean httpOnly;
/*   96 */   private int version = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   private long whenCreated = 0L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long MAX_AGE_UNSPECIFIED = -1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  116 */   private static final String[] COOKIE_DATE_FORMATS = new String[] { "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'", "EEE',' dd MMM yyyy HH:mm:ss 'GMT'", "EEE MMM dd yyyy HH:mm:ss 'GMT'Z" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SET_COOKIE = "set-cookie:";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SET_COOKIE2 = "set-cookie2:";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String tspecials = ",;";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   HttpCookie(String name, String value) {
/*  163 */     name = name.trim();
/*  164 */     if (name.length() == 0 || !isToken(name) || isReserved(name)) {
/*  165 */       throw new IllegalArgumentException("Illegal cookie name");
/*      */     }
/*      */     
/*  168 */     this.name = name;
/*  169 */     this.value = value;
/*  170 */     this.toDiscard = false;
/*  171 */     this.secure = false;
/*      */     
/*  173 */     this.whenCreated = System.currentTimeMillis();
/*  174 */     this.portlist = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<HttpCookie> parse(String header) {
/*  196 */     int version = guessCookieVersion(header);
/*      */ 
/*      */     
/*  199 */     if (startsWithIgnoreCase(header, "set-cookie2:")) {
/*  200 */       header = header.substring("set-cookie2:".length());
/*  201 */     } else if (startsWithIgnoreCase(header, "set-cookie:")) {
/*  202 */       header = header.substring("set-cookie:".length());
/*      */     } 
/*      */ 
/*      */     
/*  206 */     List<HttpCookie> cookies = new ArrayList<HttpCookie>();
/*      */ 
/*      */ 
/*      */     
/*  210 */     if (version == 0) {
/*      */       
/*  212 */       HttpCookie cookie = parseInternal(header);
/*  213 */       cookie.setVersion(0);
/*  214 */       cookies.add(cookie);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  219 */       List<String> cookieStrings = splitMultiCookies(header);
/*  220 */       for (String cookieStr : cookieStrings) {
/*  221 */         HttpCookie cookie = parseInternal(cookieStr);
/*  222 */         cookie.setVersion(1);
/*  223 */         cookies.add(cookie);
/*      */       } 
/*      */     } 
/*      */     
/*  227 */     return cookies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasExpired() {
/*  243 */     if (this.maxAge == 0L) return true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     if (this.maxAge == -1L) return false;
/*      */     
/*  250 */     long deltaSecond = (System.currentTimeMillis() - this.whenCreated) / 1000L;
/*  251 */     if (deltaSecond > this.maxAge) {
/*  252 */       return true;
/*      */     }
/*  254 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setComment(String purpose) {
/*  272 */     this.comment = purpose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getComment() {
/*  290 */     return this.comment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommentURL(String purpose) {
/*  308 */     this.commentURL = purpose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommentURL() {
/*  326 */     return this.commentURL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDiscard(boolean discard) {
/*  340 */     this.toDiscard = discard;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDiscard() {
/*  355 */     return this.toDiscard;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPortlist(String ports) {
/*  369 */     this.portlist = ports;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPortlist() {
/*  384 */     return this.portlist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDomain(String pattern) {
/*  408 */     this.domain = (pattern == null) ? null : pattern.toLowerCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDomain() {
/*  422 */     return this.domain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxAge(long expiry) {
/*  448 */     this.maxAge = expiry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMaxAge() {
/*  469 */     return this.maxAge;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPath(String uri) {
/*  497 */     this.path = uri;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  517 */     return this.path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecure(boolean flag) {
/*  539 */     this.secure = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSecure() {
/*  558 */     return this.secure;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  574 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValue(String newValue) {
/*  600 */     this.value = newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getValue() {
/*  617 */     return this.value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getVersion() {
/*  640 */     return this.version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVersion(int v) {
/*  663 */     if (v != 0 && v != 1) {
/*  664 */       throw new IllegalArgumentException("cookie version should be 0 or 1");
/*      */     }
/*      */     
/*  667 */     this.version = v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean isHttpOnly() {
/*  680 */     return this.httpOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setHttpOnly(boolean httpOnly) {
/*  694 */     this.httpOnly = httpOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean domainMatches(String domain, String host) {
/*  747 */     if (domain == null || host == null) {
/*  748 */       return false;
/*      */     }
/*      */     
/*  751 */     boolean isLocalDomain = ".local".equalsIgnoreCase(domain);
/*  752 */     int embeddedDotInDomain = domain.indexOf('.');
/*  753 */     if (embeddedDotInDomain == 0)
/*  754 */       embeddedDotInDomain = domain.indexOf('.', 1); 
/*  755 */     if (!isLocalDomain && (embeddedDotInDomain == -1 || embeddedDotInDomain == domain.length() - 1))
/*      */     {
/*  757 */       return false;
/*      */     }
/*      */     
/*  760 */     int firstDotInHost = host.indexOf('.');
/*  761 */     if (firstDotInHost == -1 && isLocalDomain) {
/*  762 */       return true;
/*      */     }
/*  764 */     int domainLength = domain.length();
/*  765 */     int lengthDiff = host.length() - domainLength;
/*  766 */     if (lengthDiff == 0)
/*      */     {
/*  768 */       return host.equalsIgnoreCase(domain);
/*      */     }
/*  770 */     if (lengthDiff > 0) {
/*      */       
/*  772 */       String H = host.substring(0, lengthDiff);
/*  773 */       String D = host.substring(lengthDiff);
/*      */       
/*  775 */       return (H.indexOf('.') == -1 && D.equalsIgnoreCase(domain));
/*      */     } 
/*  777 */     if (lengthDiff == -1)
/*      */     {
/*  779 */       return (domain.charAt(0) == '.' && host.equalsIgnoreCase(domain.substring(1)));
/*      */     }
/*      */ 
/*      */     
/*  783 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  795 */     if (getVersion() > 0) {
/*  796 */       return toRFC2965HeaderString();
/*      */     }
/*  798 */     return toNetscapeHeaderString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  815 */     if (obj == this)
/*  816 */       return true; 
/*  817 */     if (!(obj instanceof HttpCookie))
/*  818 */       return false; 
/*  819 */     HttpCookie other = (HttpCookie)obj;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  825 */     return (equalsIgnoreCase(getName(), other.getName()) && equalsIgnoreCase(getDomain(), other.getDomain()) && equals(getPath(), other.getPath()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  845 */     int h1 = this.name.toLowerCase().hashCode();
/*  846 */     int h2 = (this.domain != null) ? this.domain.toLowerCase().hashCode() : 0;
/*  847 */     int h3 = (this.path != null) ? this.path.hashCode() : 0;
/*      */     
/*  849 */     return h1 + h2 + h3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/*  859 */       return super.clone();
/*  860 */     } catch (CloneNotSupportedException e) {
/*  861 */       throw new RuntimeException(e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isToken(String value) {
/*  885 */     int len = value.length();
/*      */     
/*  887 */     for (int i = 0; i < len; i++) {
/*  888 */       char c = value.charAt(i);
/*      */       
/*  890 */       if (c < ' ' || c >= '' || ",;".indexOf(c) != -1)
/*  891 */         return false; 
/*      */     } 
/*  893 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isReserved(String name) {
/*  903 */     if (name.equalsIgnoreCase("Comment") || name.equalsIgnoreCase("CommentURL") || name.equalsIgnoreCase("Discard") || name.equalsIgnoreCase("Domain") || name.equalsIgnoreCase("Expires") || name.equalsIgnoreCase("Max-Age") || name.equalsIgnoreCase("Path") || name.equalsIgnoreCase("Port") || name.equalsIgnoreCase("Secure") || name.equalsIgnoreCase("Version") || name.equalsIgnoreCase("HttpOnly") || name.charAt(0) == '$')
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  916 */       return true;
/*      */     }
/*      */     
/*  919 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static HttpCookie parseInternal(String header) {
/*  935 */     HttpCookie cookie = null;
/*      */ 
/*      */     
/*  938 */     StringTokenizer tokenizer = new StringTokenizer(header, ";");
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  943 */       String namevaluePair = tokenizer.nextToken();
/*  944 */       int index = namevaluePair.indexOf('=');
/*  945 */       if (index != -1) {
/*  946 */         String name = namevaluePair.substring(0, index).trim();
/*  947 */         String value = namevaluePair.substring(index + 1).trim();
/*  948 */         cookie = new HttpCookie(name, stripOffSurroundingQuote(value));
/*      */       } else {
/*      */         
/*  951 */         throw new IllegalArgumentException("Invalid cookie name-value pair");
/*      */       } 
/*  953 */     } catch (NoSuchElementException ignored) {
/*  954 */       throw new IllegalArgumentException("Empty cookie header string");
/*      */     } 
/*      */ 
/*      */     
/*  958 */     while (tokenizer.hasMoreTokens()) {
/*  959 */       String name, value, str1 = tokenizer.nextToken();
/*  960 */       int index = str1.indexOf('=');
/*      */       
/*  962 */       if (index != -1) {
/*  963 */         name = str1.substring(0, index).trim();
/*  964 */         value = str1.substring(index + 1).trim();
/*      */       } else {
/*  966 */         name = str1.trim();
/*  967 */         value = null;
/*      */       } 
/*      */ 
/*      */       
/*  971 */       assignAttribute(cookie, name, value);
/*      */     } 
/*      */     
/*  974 */     return cookie;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  985 */   static Map<String, CookieAttributeAssignor> assignors = null; private static SimpleDateFormat[] cDateFormats;
/*      */   
/*  987 */   static { assignors = new HashMap<String, CookieAttributeAssignor>();
/*  988 */     assignors.put("comment", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/*  990 */             if (cookie.getComment() == null) cookie.setComment(attrValue); 
/*      */           }
/*      */         });
/*  993 */     assignors.put("commenturl", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/*  995 */             if (cookie.getCommentURL() == null) cookie.setCommentURL(attrValue); 
/*      */           }
/*      */         });
/*  998 */     assignors.put("discard", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1000 */             cookie.setDiscard(true);
/*      */           }
/*      */         });
/* 1003 */     assignors.put("domain", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1005 */             if (cookie.getDomain() == null) cookie.setDomain(attrValue); 
/*      */           }
/*      */         });
/* 1008 */     assignors.put("max-age", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/*      */             try {
/* 1011 */               long maxage = Long.parseLong(attrValue);
/* 1012 */               if (cookie.getMaxAge() == -1L) cookie.setMaxAge(maxage); 
/* 1013 */             } catch (NumberFormatException ignored) {
/* 1014 */               throw new IllegalArgumentException("Illegal cookie max-age attribute");
/*      */             } 
/*      */           }
/*      */         });
/* 1018 */     assignors.put("path", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1020 */             if (cookie.getPath() == null) cookie.setPath(attrValue); 
/*      */           }
/*      */         });
/* 1023 */     assignors.put("port", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1025 */             if (cookie.getPortlist() == null) cookie.setPortlist((attrValue == null) ? "" : attrValue); 
/*      */           }
/*      */         });
/* 1028 */     assignors.put("secure", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1030 */             cookie.setSecure(true);
/*      */           }
/*      */         });
/* 1033 */     assignors.put("httponly", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1035 */             cookie.setHttpOnly(true);
/*      */           }
/*      */         });
/* 1038 */     assignors.put("version", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/*      */             try {
/* 1041 */               int version = Integer.parseInt(attrValue);
/* 1042 */               cookie.setVersion(version);
/* 1043 */             } catch (NumberFormatException ignored) {
/* 1044 */               throw new IllegalArgumentException("Illegal cookie version attribute");
/*      */             } 
/*      */           }
/*      */         });
/* 1048 */     assignors.put("expires", new CookieAttributeAssignor() {
/*      */           public void assign(HttpCookie cookie, String attrName, String attrValue) {
/* 1050 */             if (cookie.getMaxAge() == -1L) {
/* 1051 */               cookie.setMaxAge(cookie.expiryDate2DeltaSeconds(attrValue));
/*      */             }
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1104 */     cDateFormats = null;
/*      */     
/* 1106 */     cDateFormats = new SimpleDateFormat[COOKIE_DATE_FORMATS.length];
/* 1107 */     for (int i = 0; i < COOKIE_DATE_FORMATS.length; i++) {
/* 1108 */       cDateFormats[i] = new SimpleDateFormat(COOKIE_DATE_FORMATS[i], Locale.US);
/* 1109 */       cDateFormats[i].setTimeZone(TimeZone.getTimeZone("GMT"));
/*      */     }  }
/*      */ 
/*      */   
/*      */   private static void assignAttribute(HttpCookie cookie, String attrName, String attrValue) {
/*      */     attrValue = stripOffSurroundingQuote(attrValue);
/*      */     CookieAttributeAssignor assignor = assignors.get(attrName.toLowerCase());
/*      */     if (assignor != null)
/*      */       assignor.assign(cookie, attrName, attrValue); 
/*      */   }
/*      */   
/* 1120 */   private long expiryDate2DeltaSeconds(String dateString) { for (SimpleDateFormat df : cDateFormats) {
/*      */       try {
/* 1122 */         Date date = df.parse(dateString);
/* 1123 */         return (date.getTime() - this.whenCreated) / 1000L;
/* 1124 */       } catch (Exception e) {}
/*      */     } 
/*      */ 
/*      */     
/* 1128 */     return 0L; }
/*      */ 
/*      */   
/*      */   private String toNetscapeHeaderString() {
/*      */     StringBuilder sb = new StringBuilder();
/*      */     sb.append(getName()).append("=").append(getValue());
/*      */     return sb.toString();
/*      */   }
/*      */   
/* 1137 */   private static int guessCookieVersion(String header) { int version = 0;
/*      */     
/* 1139 */     header = header.toLowerCase();
/* 1140 */     if (header.indexOf("expires=") != -1) {
/*      */       
/* 1142 */       version = 0;
/* 1143 */     } else if (header.indexOf("version=") != -1) {
/*      */       
/* 1145 */       version = 1;
/* 1146 */     } else if (header.indexOf("max-age") != -1) {
/*      */       
/* 1148 */       version = 1;
/* 1149 */     } else if (startsWithIgnoreCase(header, "set-cookie2:")) {
/*      */       
/* 1151 */       version = 1;
/*      */     } 
/*      */     
/* 1154 */     return version; }
/*      */   private String toRFC2965HeaderString() { StringBuilder sb = new StringBuilder(); sb.append(getName()).append("=\"").append(getValue()).append('"'); if (getPath() != null)
/*      */       sb.append(";$Path=\"").append(getPath()).append('"');  if (getDomain() != null)
/*      */       sb.append(";$Domain=\"").append(getDomain()).append('"');  if (getPortlist() != null)
/* 1158 */       sb.append(";$Port=\"").append(getPortlist()).append('"');  return sb.toString(); } private static String stripOffSurroundingQuote(String str) { if (str != null && str.length() > 0 && str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"')
/*      */     {
/* 1160 */       return str.substring(1, str.length() - 1);
/*      */     }
/* 1162 */     return str; }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean equalsIgnoreCase(String s, String t) {
/* 1167 */     if (s == t) return true; 
/* 1168 */     if (s != null && t != null) {
/* 1169 */       return s.equalsIgnoreCase(t);
/*      */     }
/* 1171 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean equals(String s, String t) {
/* 1175 */     if (s == t) return true; 
/* 1176 */     if (s != null && t != null) {
/* 1177 */       return s.equals(t);
/*      */     }
/* 1179 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean startsWithIgnoreCase(String s, String start) {
/* 1183 */     if (s == null || start == null) return false;
/*      */     
/* 1185 */     if (s.length() >= start.length() && start.equalsIgnoreCase(s.substring(0, start.length())))
/*      */     {
/* 1187 */       return true;
/*      */     }
/*      */     
/* 1190 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<String> splitMultiCookies(String header) {
/* 1205 */     List<String> cookies = new ArrayList<String>();
/* 1206 */     int quoteCount = 0;
/*      */     
/*      */     int q;
/* 1209 */     for (int p = 0; p < header.length(); p++) {
/* 1210 */       char c = header.charAt(p);
/* 1211 */       if (c == '"') quoteCount++; 
/* 1212 */       if (c == ',' && quoteCount % 2 == 0) {
/* 1213 */         cookies.add(header.substring(q, p));
/* 1214 */         q = p + 1;
/*      */       } 
/*      */     } 
/*      */     
/* 1218 */     cookies.add(header.substring(q));
/*      */     
/* 1220 */     return cookies;
/*      */   }
/*      */   
/*      */   static interface CookieAttributeAssignor {
/*      */     void assign(HttpCookie param1HttpCookie, String param1String1, String param1String2);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\client\HttpCookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */