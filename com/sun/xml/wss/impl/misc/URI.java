/*      */ package com.sun.xml.wss.impl.misc;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ import java.text.MessageFormat;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class URI
/*      */   implements Serializable
/*      */ {
/*      */   private static final String RESERVED_CHARACTERS = ";/?:@&=+$,";
/*      */   private static final String MARK_CHARACTERS = "-_.!~*'() ";
/*      */   private static final String SCHEME_CHARACTERS = "+-.";
/*      */   private static final String USERINFO_CHARACTERS = ";:&=+$,";
/*      */   
/*      */   public static class MalformedURIException
/*      */     extends IOException
/*      */   {
/*      */     public MalformedURIException() {}
/*      */     
/*      */     public MalformedURIException(String p_msg) {
/*  131 */       super(p_msg);
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
/*      */   
/*  155 */   private String m_scheme = null;
/*      */ 
/*      */ 
/*      */   
/*  159 */   private String m_userinfo = null;
/*      */ 
/*      */ 
/*      */   
/*  163 */   private String m_host = null;
/*      */ 
/*      */ 
/*      */   
/*  167 */   private int m_port = -1;
/*      */ 
/*      */ 
/*      */   
/*  171 */   private String m_path = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  178 */   private String m_queryString = null;
/*      */ 
/*      */ 
/*      */   
/*  182 */   private String m_fragment = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean DEBUG = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public URI(URI p_other) {
/*  200 */     initialize(p_other);
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
/*      */   public URI(String p_uriSpec) throws MalformedURIException {
/*  220 */     this((URI)null, p_uriSpec);
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
/*      */   public URI(URI p_base, String p_uriSpec) throws MalformedURIException {
/*  237 */     initialize(p_base, p_uriSpec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize(URI p_other) {
/*  248 */     this.m_scheme = p_other.getScheme();
/*  249 */     this.m_userinfo = p_other.getUserinfo();
/*  250 */     this.m_host = p_other.getHost();
/*  251 */     this.m_port = p_other.getPort();
/*  252 */     this.m_path = p_other.getPath();
/*  253 */     this.m_queryString = p_other.getQueryString();
/*  254 */     this.m_fragment = p_other.getFragment();
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
/*      */   private void initialize(URI p_base, String p_uriSpec) throws MalformedURIException {
/*  277 */     if (p_base == null && (p_uriSpec == null || p_uriSpec.trim().length() == 0))
/*      */     {
/*      */ 
/*      */       
/*  281 */       throw new MalformedURIException(createXMLMessage("Cannot initialize URI with empty parameters.", null));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  286 */     if (p_uriSpec == null || p_uriSpec.trim().length() == 0) {
/*      */       
/*  288 */       initialize(p_base);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  293 */     String uriSpec = p_uriSpec.trim();
/*  294 */     int uriSpecLen = uriSpec.length();
/*  295 */     int index = 0;
/*      */ 
/*      */     
/*  298 */     int colonIndex = uriSpec.indexOf(':');
/*  299 */     if (colonIndex < 0) {
/*      */       
/*  301 */       if (p_base == null)
/*      */       {
/*      */         
/*  304 */         throw new MalformedURIException(createXMLMessage("No scheme found in URI: ", new Object[] { uriSpec }));
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  309 */       initializeScheme(uriSpec);
/*  310 */       uriSpec = uriSpec.substring(colonIndex + 1);
/*  311 */       uriSpecLen = uriSpec.length();
/*      */     } 
/*      */ 
/*      */     
/*  315 */     if (index + 1 < uriSpecLen && uriSpec.substring(index).startsWith("//")) {
/*      */ 
/*      */       
/*  318 */       index += 2;
/*      */       
/*  320 */       int startPos = index;
/*      */ 
/*      */       
/*  323 */       char testChar = Character.MIN_VALUE;
/*      */       
/*  325 */       while (index < uriSpecLen) {
/*      */         
/*  327 */         testChar = uriSpec.charAt(index);
/*      */         
/*  329 */         if (testChar == '/' || testChar == '?' || testChar == '#') {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  334 */         index++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  339 */       if (index > startPos) {
/*      */         
/*  341 */         initializeAuthority(uriSpec.substring(startPos, index));
/*      */       }
/*      */       else {
/*      */         
/*  345 */         this.m_host = "";
/*      */       } 
/*      */     } 
/*      */     
/*  349 */     initializePath(uriSpec.substring(index));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  356 */     if (p_base != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  366 */       if (this.m_path.length() == 0 && this.m_scheme == null && this.m_host == null) {
/*      */         
/*  368 */         this.m_scheme = p_base.getScheme();
/*  369 */         this.m_userinfo = p_base.getUserinfo();
/*  370 */         this.m_host = p_base.getHost();
/*  371 */         this.m_port = p_base.getPort();
/*  372 */         this.m_path = p_base.getPath();
/*      */         
/*  374 */         if (this.m_queryString == null)
/*      */         {
/*  376 */           this.m_queryString = p_base.getQueryString();
/*      */         }
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */       
/*  384 */       if (this.m_scheme == null)
/*      */       {
/*  386 */         this.m_scheme = p_base.getScheme();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  391 */       if (this.m_host == null) {
/*      */         
/*  393 */         this.m_userinfo = p_base.getUserinfo();
/*  394 */         this.m_host = p_base.getHost();
/*  395 */         this.m_port = p_base.getPort();
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  403 */       if (this.m_path.length() > 0 && this.m_path.startsWith("/")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  410 */       String path = "";
/*  411 */       String basePath = p_base.getPath();
/*      */ 
/*      */       
/*  414 */       if (basePath != null) {
/*      */         
/*  416 */         int lastSlash = basePath.lastIndexOf('/');
/*      */         
/*  418 */         if (lastSlash != -1)
/*      */         {
/*  420 */           path = basePath.substring(0, lastSlash + 1);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  425 */       path = path.concat(this.m_path);
/*      */ 
/*      */       
/*  428 */       index = -1;
/*      */       
/*  430 */       while ((index = path.indexOf("/./")) != -1)
/*      */       {
/*  432 */         path = path.substring(0, index + 1).concat(path.substring(index + 3));
/*      */       }
/*      */ 
/*      */       
/*  436 */       if (path.endsWith("/."))
/*      */       {
/*  438 */         path = path.substring(0, path.length() - 1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  443 */       index = -1;
/*      */       
/*  445 */       int segIndex = -1;
/*  446 */       String tempString = null;
/*      */       
/*  448 */       while ((index = path.indexOf("/../")) > 0) {
/*      */         
/*  450 */         tempString = path.substring(0, path.indexOf("/../"));
/*  451 */         segIndex = tempString.lastIndexOf('/');
/*      */         
/*  453 */         if (segIndex != -1)
/*      */         {
/*  455 */           if (!tempString.substring(segIndex++).equals(".."))
/*      */           {
/*  457 */             path = path.substring(0, segIndex).concat(path.substring(index + 4));
/*      */           }
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  465 */       if (path.endsWith("/..")) {
/*      */         
/*  467 */         tempString = path.substring(0, path.length() - 3);
/*  468 */         segIndex = tempString.lastIndexOf('/');
/*      */         
/*  470 */         if (segIndex != -1)
/*      */         {
/*  472 */           path = path.substring(0, segIndex + 1);
/*      */         }
/*      */       } 
/*      */       
/*  476 */       this.m_path = path;
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
/*      */   private void initializeScheme(String p_uriSpec) throws MalformedURIException {
/*  491 */     int uriSpecLen = p_uriSpec.length();
/*  492 */     int index = 0;
/*  493 */     String scheme = null;
/*  494 */     char testChar = Character.MIN_VALUE;
/*      */     
/*  496 */     while (index < uriSpecLen) {
/*      */       
/*  498 */       testChar = p_uriSpec.charAt(index);
/*      */       
/*  500 */       if (testChar == ':' || testChar == '/' || testChar == '?' || testChar == '#') {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  506 */       index++;
/*      */     } 
/*      */     
/*  509 */     scheme = p_uriSpec.substring(0, index);
/*      */     
/*  511 */     if (scheme.length() == 0)
/*      */     {
/*      */       
/*  514 */       throw new MalformedURIException(createXMLMessage("No scheme found in URI.", null));
/*      */     }
/*      */ 
/*      */     
/*  518 */     setScheme(scheme);
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
/*      */   private void initializeAuthority(String p_uriSpec) throws MalformedURIException {
/*  534 */     int index = 0;
/*  535 */     int start = 0;
/*  536 */     int end = p_uriSpec.length();
/*  537 */     char testChar = Character.MIN_VALUE;
/*  538 */     String userinfo = null;
/*      */ 
/*      */     
/*  541 */     if (p_uriSpec.indexOf('@', start) != -1) {
/*      */       
/*  543 */       while (index < end) {
/*      */         
/*  545 */         testChar = p_uriSpec.charAt(index);
/*      */         
/*  547 */         if (testChar == '@') {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  552 */         index++;
/*      */       } 
/*      */       
/*  555 */       userinfo = p_uriSpec.substring(start, index);
/*      */       
/*  557 */       index++;
/*      */     } 
/*      */ 
/*      */     
/*  561 */     String host = null;
/*      */     
/*  563 */     start = index;
/*      */     
/*  565 */     while (index < end) {
/*      */       
/*  567 */       testChar = p_uriSpec.charAt(index);
/*      */       
/*  569 */       if (testChar == ':') {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  574 */       index++;
/*      */     } 
/*      */     
/*  577 */     host = p_uriSpec.substring(start, index);
/*      */     
/*  579 */     int port = -1;
/*      */     
/*  581 */     if (host.length() > 0)
/*      */     {
/*      */ 
/*      */       
/*  585 */       if (testChar == ':') {
/*      */ 
/*      */ 
/*      */         
/*  589 */         start = ++index;
/*      */         
/*  591 */         while (index < end)
/*      */         {
/*  593 */           index++;
/*      */         }
/*      */         
/*  596 */         String portStr = p_uriSpec.substring(start, index);
/*      */         
/*  598 */         if (portStr.length() > 0) {
/*      */           
/*  600 */           for (int i = 0; i < portStr.length(); i++) {
/*      */             
/*  602 */             if (!isDigit(portStr.charAt(i)))
/*      */             {
/*  604 */               throw new MalformedURIException(portStr + " is invalid. Port should only contain digits!");
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  611 */             port = Integer.parseInt(portStr);
/*      */           }
/*  613 */           catch (NumberFormatException nfe) {}
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  622 */     setHost(host);
/*  623 */     setPort(port);
/*  624 */     setUserinfo(userinfo);
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
/*      */   private void initializePath(String p_uriSpec) throws MalformedURIException {
/*  637 */     if (p_uriSpec == null)
/*      */     {
/*  639 */       throw new MalformedURIException("Cannot initialize path from null string!");
/*      */     }
/*      */ 
/*      */     
/*  643 */     int index = 0;
/*  644 */     int start = 0;
/*  645 */     int end = p_uriSpec.length();
/*  646 */     char testChar = Character.MIN_VALUE;
/*      */ 
/*      */     
/*  649 */     while (index < end) {
/*      */       
/*  651 */       testChar = p_uriSpec.charAt(index);
/*      */       
/*  653 */       if (testChar == '?' || testChar == '#') {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  659 */       if (testChar == '%') {
/*      */         
/*  661 */         if (index + 2 >= end || !isHex(p_uriSpec.charAt(index + 1)) || !isHex(p_uriSpec.charAt(index + 2)))
/*      */         {
/*      */ 
/*      */           
/*  665 */           throw new MalformedURIException(createXMLMessage("Path contains invalid escape sequence!", null));
/*      */         
/*      */         }
/*      */       }
/*  669 */       else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */ 
/*      */         
/*  672 */         if ('\\' != testChar)
/*      */         {
/*  674 */           throw new MalformedURIException(createXMLMessage("Path contains invalid character: ", new Object[] { String.valueOf(testChar) }));
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  679 */       index++;
/*      */     } 
/*      */     
/*  682 */     this.m_path = p_uriSpec.substring(start, index);
/*      */ 
/*      */     
/*  685 */     if (testChar == '?') {
/*      */ 
/*      */ 
/*      */       
/*  689 */       start = ++index;
/*      */       
/*  691 */       while (index < end) {
/*      */         
/*  693 */         testChar = p_uriSpec.charAt(index);
/*      */         
/*  695 */         if (testChar == '#') {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  700 */         if (testChar == '%') {
/*      */           
/*  702 */           if (index + 2 >= end || !isHex(p_uriSpec.charAt(index + 1)) || !isHex(p_uriSpec.charAt(index + 2)))
/*      */           {
/*      */             
/*  705 */             throw new MalformedURIException("Query string contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  709 */         else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */ 
/*      */           
/*  712 */           throw new MalformedURIException("Query string contains invalid character:" + testChar);
/*      */         } 
/*      */ 
/*      */         
/*  716 */         index++;
/*      */       } 
/*      */       
/*  719 */       this.m_queryString = p_uriSpec.substring(start, index);
/*      */     } 
/*      */ 
/*      */     
/*  723 */     if (testChar == '#') {
/*      */ 
/*      */ 
/*      */       
/*  727 */       start = ++index;
/*      */       
/*  729 */       while (index < end) {
/*      */         
/*  731 */         testChar = p_uriSpec.charAt(index);
/*      */         
/*  733 */         if (testChar == '%') {
/*      */           
/*  735 */           if (index + 2 >= end || !isHex(p_uriSpec.charAt(index + 1)) || !isHex(p_uriSpec.charAt(index + 2)))
/*      */           {
/*      */             
/*  738 */             throw new MalformedURIException("Fragment contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  742 */         else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */ 
/*      */           
/*  745 */           throw new MalformedURIException("Fragment contains invalid character:" + testChar);
/*      */         } 
/*      */ 
/*      */         
/*  749 */         index++;
/*      */       } 
/*      */       
/*  752 */       this.m_fragment = p_uriSpec.substring(start, index);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getScheme() {
/*  763 */     return this.m_scheme;
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
/*      */   public String getSchemeSpecificPart() {
/*  775 */     StringBuffer schemespec = new StringBuffer();
/*      */     
/*  777 */     if (this.m_userinfo != null || this.m_host != null || this.m_port != -1)
/*      */     {
/*  779 */       schemespec.append("//");
/*      */     }
/*      */     
/*  782 */     if (this.m_userinfo != null) {
/*      */       
/*  784 */       schemespec.append(this.m_userinfo);
/*  785 */       schemespec.append('@');
/*      */     } 
/*      */     
/*  788 */     if (this.m_host != null)
/*      */     {
/*  790 */       schemespec.append(this.m_host);
/*      */     }
/*      */     
/*  793 */     if (this.m_port != -1) {
/*      */       
/*  795 */       schemespec.append(':');
/*  796 */       schemespec.append(this.m_port);
/*      */     } 
/*      */     
/*  799 */     if (this.m_path != null)
/*      */     {
/*  801 */       schemespec.append(this.m_path);
/*      */     }
/*      */     
/*  804 */     if (this.m_queryString != null) {
/*      */       
/*  806 */       schemespec.append('?');
/*  807 */       schemespec.append(this.m_queryString);
/*      */     } 
/*      */     
/*  810 */     if (this.m_fragment != null) {
/*      */       
/*  812 */       schemespec.append('#');
/*  813 */       schemespec.append(this.m_fragment);
/*      */     } 
/*      */     
/*  816 */     return schemespec.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserinfo() {
/*  826 */     return this.m_userinfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHost() {
/*  836 */     return this.m_host;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  846 */     return this.m_port;
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
/*      */   public String getPath(boolean p_includeQueryString, boolean p_includeFragment) {
/*  867 */     StringBuffer pathString = new StringBuffer(this.m_path);
/*      */     
/*  869 */     if (p_includeQueryString && this.m_queryString != null) {
/*      */       
/*  871 */       pathString.append('?');
/*  872 */       pathString.append(this.m_queryString);
/*      */     } 
/*      */     
/*  875 */     if (p_includeFragment && this.m_fragment != null) {
/*      */       
/*  877 */       pathString.append('#');
/*  878 */       pathString.append(this.m_fragment);
/*      */     } 
/*      */     
/*  881 */     return pathString.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  892 */     return this.m_path;
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
/*      */   public String getQueryString() {
/*  904 */     return this.m_queryString;
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
/*      */   public String getFragment() {
/*  916 */     return this.m_fragment;
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
/*      */   public void setFragment(String p_fragment) throws MalformedURIException {
/*  933 */     if (p_fragment == null) {
/*      */       
/*  935 */       this.m_fragment = null;
/*      */     } else {
/*  937 */       if (!isGenericURI())
/*      */       {
/*  939 */         throw new MalformedURIException(createXMLMessage("Fragment can only be set for a generic URI!", null));
/*      */       }
/*      */       
/*  942 */       if (getPath() == null)
/*      */       {
/*  944 */         throw new MalformedURIException(createXMLMessage("Fragment cannot be set when path is null!", null));
/*      */       }
/*      */       
/*  947 */       if (!isURIString(p_fragment))
/*      */       {
/*  949 */         throw new MalformedURIException(createXMLMessage("Fragment contains invalid character!", null));
/*      */       }
/*      */ 
/*      */       
/*  953 */       this.m_fragment = p_fragment;
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
/*      */   public void setScheme(String p_scheme) throws MalformedURIException {
/*  969 */     if (p_scheme == null)
/*      */     {
/*      */       
/*  972 */       throw new MalformedURIException(createXMLMessage("Cannot set scheme from null string!", null));
/*      */     }
/*      */     
/*  975 */     if (!isConformantSchemeName(p_scheme))
/*      */     {
/*      */       
/*  978 */       throw new MalformedURIException(createXMLMessage("The scheme is not conformant.", null));
/*      */     }
/*      */     
/*  981 */     this.m_scheme = p_scheme.toLowerCase();
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
/*      */   public void setUserinfo(String p_userinfo) throws MalformedURIException {
/*  996 */     if (p_userinfo == null) {
/*      */       
/*  998 */       this.m_userinfo = null;
/*      */     }
/*      */     else {
/*      */       
/* 1002 */       if (this.m_host == null)
/*      */       {
/* 1004 */         throw new MalformedURIException("Userinfo cannot be set when host is null!");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1010 */       int index = 0;
/* 1011 */       int end = p_userinfo.length();
/* 1012 */       char testChar = Character.MIN_VALUE;
/*      */       
/* 1014 */       while (index < end) {
/*      */         
/* 1016 */         testChar = p_userinfo.charAt(index);
/*      */         
/* 1018 */         if (testChar == '%') {
/*      */           
/* 1020 */           if (index + 2 >= end || !isHex(p_userinfo.charAt(index + 1)) || !isHex(p_userinfo.charAt(index + 2)))
/*      */           {
/*      */             
/* 1023 */             throw new MalformedURIException("Userinfo contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/* 1027 */         else if (!isUnreservedCharacter(testChar) && ";:&=+$,".indexOf(testChar) == -1) {
/*      */ 
/*      */           
/* 1030 */           throw new MalformedURIException("Userinfo contains invalid character:" + testChar);
/*      */         } 
/*      */ 
/*      */         
/* 1034 */         index++;
/*      */       } 
/*      */     } 
/*      */     
/* 1038 */     this.m_userinfo = p_userinfo;
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
/*      */   public void setHost(String p_host) throws MalformedURIException {
/* 1053 */     if (p_host == null || p_host.trim().length() == 0) {
/*      */       
/* 1055 */       this.m_host = p_host;
/* 1056 */       this.m_userinfo = null;
/* 1057 */       this.m_port = -1;
/*      */     }
/* 1059 */     else if (!isWellFormedAddress(p_host)) {
/*      */ 
/*      */       
/* 1062 */       throw new MalformedURIException(createXMLMessage("Host is not a well formed address!", null));
/*      */     } 
/*      */     
/* 1065 */     this.m_host = p_host;
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
/*      */   public void setPort(int p_port) throws MalformedURIException {
/* 1082 */     if (p_port >= 0 && p_port <= 65535) {
/*      */       
/* 1084 */       if (this.m_host == null)
/*      */       {
/*      */         
/* 1087 */         throw new MalformedURIException(createXMLMessage("Port cannot be set when host is null!", null));
/*      */       
/*      */       }
/*      */     }
/* 1091 */     else if (p_port != -1) {
/*      */ 
/*      */       
/* 1094 */       throw new MalformedURIException(createXMLMessage("Invalid port number!", null));
/*      */     } 
/*      */     
/* 1097 */     this.m_port = p_port;
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
/*      */   public void setPath(String p_path) throws MalformedURIException {
/* 1117 */     if (p_path == null) {
/*      */       
/* 1119 */       this.m_path = null;
/* 1120 */       this.m_queryString = null;
/* 1121 */       this.m_fragment = null;
/*      */     }
/*      */     else {
/*      */       
/* 1125 */       initializePath(p_path);
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
/*      */   public void appendPath(String p_addToPath) throws MalformedURIException {
/* 1145 */     if (p_addToPath == null || p_addToPath.trim().length() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1150 */     if (!isURIString(p_addToPath))
/*      */     {
/*      */       
/* 1153 */       throw new MalformedURIException(createXMLMessage("Path contains invalid character!", new Object[] { p_addToPath }));
/*      */     }
/*      */     
/* 1156 */     if (this.m_path == null || this.m_path.trim().length() == 0) {
/*      */       
/* 1158 */       if (p_addToPath.startsWith("/"))
/*      */       {
/* 1160 */         this.m_path = p_addToPath;
/*      */       }
/*      */       else
/*      */       {
/* 1164 */         this.m_path = "/" + p_addToPath;
/*      */       }
/*      */     
/* 1167 */     } else if (this.m_path.endsWith("/")) {
/*      */       
/* 1169 */       if (p_addToPath.startsWith("/"))
/*      */       {
/* 1171 */         this.m_path = this.m_path.concat(p_addToPath.substring(1));
/*      */       }
/*      */       else
/*      */       {
/* 1175 */         this.m_path = this.m_path.concat(p_addToPath);
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1180 */     else if (p_addToPath.startsWith("/")) {
/*      */       
/* 1182 */       this.m_path = this.m_path.concat(p_addToPath);
/*      */     }
/*      */     else {
/*      */       
/* 1186 */       this.m_path = this.m_path.concat("/" + p_addToPath);
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
/*      */   public void setQueryString(String p_queryString) throws MalformedURIException {
/* 1206 */     if (p_queryString == null) {
/*      */       
/* 1208 */       this.m_queryString = null;
/*      */     } else {
/* 1210 */       if (!isGenericURI())
/*      */       {
/* 1212 */         throw new MalformedURIException("Query string can only be set for a generic URI!");
/*      */       }
/*      */       
/* 1215 */       if (getPath() == null)
/*      */       {
/* 1217 */         throw new MalformedURIException("Query string cannot be set when path is null!");
/*      */       }
/*      */       
/* 1220 */       if (!isURIString(p_queryString))
/*      */       {
/* 1222 */         throw new MalformedURIException("Query string contains invalid character!");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1227 */       this.m_queryString = p_queryString;
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
/*      */   public boolean equals(Object p_test) {
/* 1243 */     if (p_test instanceof URI) {
/*      */       
/* 1245 */       URI testURI = (URI)p_test;
/*      */       
/* 1247 */       if (((this.m_scheme == null && testURI.m_scheme == null) || (this.m_scheme != null && testURI.m_scheme != null && this.m_scheme.equals(testURI.m_scheme))) && ((this.m_userinfo == null && testURI.m_userinfo == null) || (this.m_userinfo != null && testURI.m_userinfo != null && this.m_userinfo.equals(testURI.m_userinfo))) && ((this.m_host == null && testURI.m_host == null) || (this.m_host != null && testURI.m_host != null && this.m_host.equals(testURI.m_host))) && this.m_port == testURI.m_port && ((this.m_path == null && testURI.m_path == null) || (this.m_path != null && testURI.m_path != null && this.m_path.equals(testURI.m_path))) && ((this.m_queryString == null && testURI.m_queryString == null) || (this.m_queryString != null && testURI.m_queryString != null && this.m_queryString.equals(testURI.m_queryString))) && ((this.m_fragment == null && testURI.m_fragment == null) || (this.m_fragment != null && testURI.m_fragment != null && this.m_fragment.equals(testURI.m_fragment))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1255 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1259 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1270 */     StringBuffer uriSpecString = new StringBuffer();
/*      */     
/* 1272 */     if (this.m_scheme != null) {
/*      */       
/* 1274 */       uriSpecString.append(this.m_scheme);
/* 1275 */       uriSpecString.append(':');
/*      */     } 
/*      */     
/* 1278 */     uriSpecString.append(getSchemeSpecificPart());
/*      */     
/* 1280 */     return uriSpecString.toString();
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
/*      */   public boolean isGenericURI() {
/* 1295 */     return (this.m_host != null);
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
/*      */   public static boolean isConformantSchemeName(String p_scheme) {
/* 1310 */     if (p_scheme == null || p_scheme.trim().length() == 0)
/*      */     {
/* 1312 */       return false;
/*      */     }
/*      */     
/* 1315 */     if (!isAlpha(p_scheme.charAt(0)))
/*      */     {
/* 1317 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1322 */     for (int i = 1; i < p_scheme.length(); i++) {
/*      */       
/* 1324 */       char testChar = p_scheme.charAt(i);
/*      */       
/* 1326 */       if (!isAlphanum(testChar) && "+-.".indexOf(testChar) == -1)
/*      */       {
/* 1328 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1332 */     return true;
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
/*      */   public static boolean isWellFormedAddress(String p_address) {
/* 1351 */     if (p_address == null)
/*      */     {
/* 1353 */       return false;
/*      */     }
/*      */     
/* 1356 */     String address = p_address.trim();
/* 1357 */     int addrLength = address.length();
/*      */     
/* 1359 */     if (addrLength == 0 || addrLength > 255)
/*      */     {
/* 1361 */       return false;
/*      */     }
/*      */     
/* 1364 */     if (address.startsWith(".") || address.startsWith("-"))
/*      */     {
/* 1366 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1372 */     int index = address.lastIndexOf('.');
/*      */     
/* 1374 */     if (address.endsWith("."))
/*      */     {
/* 1376 */       index = address.substring(0, index).lastIndexOf('.');
/*      */     }
/*      */     
/* 1379 */     if (index + 1 < addrLength && isDigit(p_address.charAt(index + 1))) {
/*      */ 
/*      */       
/* 1382 */       int numDots = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1387 */       for (int i = 0; i < addrLength; i++) {
/*      */         
/* 1389 */         char testChar = address.charAt(i);
/*      */         
/* 1391 */         if (testChar == '.') {
/*      */           
/* 1393 */           if (!isDigit(address.charAt(i - 1)) || (i + 1 < addrLength && !isDigit(address.charAt(i + 1))))
/*      */           {
/*      */             
/* 1396 */             return false;
/*      */           }
/*      */           
/* 1399 */           numDots++;
/*      */         }
/* 1401 */         else if (!isDigit(testChar)) {
/*      */           
/* 1403 */           return false;
/*      */         } 
/*      */       } 
/*      */       
/* 1407 */       if (numDots != 3)
/*      */       {
/* 1409 */         return false;
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1419 */       for (int i = 0; i < addrLength; i++) {
/*      */         
/* 1421 */         char testChar = address.charAt(i);
/*      */         
/* 1423 */         if (testChar == '.') {
/*      */           
/* 1425 */           if (!isAlphanum(address.charAt(i - 1)))
/*      */           {
/* 1427 */             return false;
/*      */           }
/*      */           
/* 1430 */           if (i + 1 < addrLength && !isAlphanum(address.charAt(i + 1)))
/*      */           {
/* 1432 */             return false;
/*      */           }
/*      */         }
/* 1435 */         else if (!isAlphanum(testChar) && testChar != '-') {
/*      */           
/* 1437 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1442 */     return true;
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
/*      */   private static boolean isDigit(char p_char) {
/* 1454 */     return (p_char >= '0' && p_char <= '9');
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
/*      */   private static boolean isHex(char p_char) {
/* 1467 */     return (isDigit(p_char) || (p_char >= 'a' && p_char <= 'f') || (p_char >= 'A' && p_char <= 'F'));
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
/*      */   private static boolean isAlpha(char p_char) {
/* 1480 */     return ((p_char >= 'a' && p_char <= 'z') || (p_char >= 'A' && p_char <= 'Z'));
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
/*      */   private static boolean isAlphanum(char p_char) {
/* 1493 */     return (isAlpha(p_char) || isDigit(p_char));
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
/*      */   private static boolean isReservedCharacter(char p_char) {
/* 1506 */     return (";/?:@&=+$,".indexOf(p_char) != -1);
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
/*      */   private static boolean isUnreservedCharacter(char p_char) {
/* 1518 */     return (isAlphanum(p_char) || "-_.!~*'() ".indexOf(p_char) != -1);
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
/*      */   private static boolean isURIString(String p_uric) {
/* 1533 */     if (p_uric == null)
/*      */     {
/* 1535 */       return false;
/*      */     }
/*      */     
/* 1538 */     int end = p_uric.length();
/* 1539 */     char testChar = Character.MIN_VALUE;
/*      */     
/* 1541 */     for (int i = 0; i < end; i++) {
/*      */       
/* 1543 */       testChar = p_uric.charAt(i);
/*      */       
/* 1545 */       if (testChar == '%') {
/*      */         
/* 1547 */         if (i + 2 >= end || !isHex(p_uric.charAt(i + 1)) || !isHex(p_uric.charAt(i + 2)))
/*      */         {
/*      */           
/* 1550 */           return false;
/*      */         }
/*      */ 
/*      */         
/* 1554 */         i += 2;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1560 */       else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1566 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1570 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static final String createXMLMessage(String msg, Object[] args) {
/* 1575 */     String fmsg = null;
/* 1576 */     if (args != null) {
/*      */       
/* 1578 */       int n = args.length;
/* 1579 */       for (int i = 0; i < n; i++) {
/* 1580 */         if (null == args[i])
/* 1581 */           args[i] = ""; 
/*      */       } 
/* 1583 */       fmsg = MessageFormat.format(msg, args);
/*      */     } else {
/*      */       
/* 1586 */       fmsg = msg;
/*      */     } 
/* 1588 */     return fmsg;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\URI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */