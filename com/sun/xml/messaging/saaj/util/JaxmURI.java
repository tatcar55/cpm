/*      */ package com.sun.xml.messaging.saaj.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JaxmURI
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
/*  107 */       super(p_msg);
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
/*  126 */   private String m_scheme = null;
/*      */ 
/*      */   
/*  129 */   private String m_userinfo = null;
/*      */ 
/*      */   
/*  132 */   private String m_host = null;
/*      */ 
/*      */   
/*  135 */   private int m_port = -1;
/*      */ 
/*      */   
/*  138 */   private String m_path = null;
/*      */ 
/*      */ 
/*      */   
/*  142 */   private String m_queryString = null;
/*      */ 
/*      */   
/*  145 */   private String m_fragment = null;
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
/*      */   public JaxmURI() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JaxmURI(JaxmURI p_other) {
/*  162 */     initialize(p_other);
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
/*      */   public JaxmURI(String p_uriSpec) throws MalformedURIException {
/*  181 */     this((JaxmURI)null, p_uriSpec);
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
/*      */   public JaxmURI(JaxmURI p_base, String p_uriSpec) throws MalformedURIException {
/*  197 */     initialize(p_base, p_uriSpec);
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
/*      */   public JaxmURI(String p_scheme, String p_schemeSpecificPart) throws MalformedURIException {
/*  214 */     if (p_scheme == null || p_scheme.trim().length() == 0) {
/*  215 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme!");
/*      */     }
/*      */     
/*  218 */     if (p_schemeSpecificPart == null || p_schemeSpecificPart.trim().length() == 0)
/*      */     {
/*  220 */       throw new MalformedURIException("Cannot construct URI with null/empty scheme-specific part!");
/*      */     }
/*      */     
/*  223 */     setScheme(p_scheme);
/*  224 */     setPath(p_schemeSpecificPart);
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
/*      */   public JaxmURI(String p_scheme, String p_host, String p_path, String p_queryString, String p_fragment) throws MalformedURIException {
/*  251 */     this(p_scheme, null, p_host, -1, p_path, p_queryString, p_fragment);
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
/*      */   public JaxmURI(String p_scheme, String p_userinfo, String p_host, int p_port, String p_path, String p_queryString, String p_fragment) throws MalformedURIException {
/*  283 */     if (p_scheme == null || p_scheme.trim().length() == 0) {
/*  284 */       throw new MalformedURIException("Scheme is required!");
/*      */     }
/*      */     
/*  287 */     if (p_host == null) {
/*  288 */       if (p_userinfo != null) {
/*  289 */         throw new MalformedURIException("Userinfo may not be specified if host is not specified!");
/*      */       }
/*      */       
/*  292 */       if (p_port != -1) {
/*  293 */         throw new MalformedURIException("Port may not be specified if host is not specified!");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  298 */     if (p_path != null) {
/*  299 */       if (p_path.indexOf('?') != -1 && p_queryString != null) {
/*  300 */         throw new MalformedURIException("Query string cannot be specified in path and query string!");
/*      */       }
/*      */ 
/*      */       
/*  304 */       if (p_path.indexOf('#') != -1 && p_fragment != null) {
/*  305 */         throw new MalformedURIException("Fragment cannot be specified in both the path and fragment!");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  310 */     setScheme(p_scheme);
/*  311 */     setHost(p_host);
/*  312 */     setPort(p_port);
/*  313 */     setUserinfo(p_userinfo);
/*  314 */     setPath(p_path);
/*  315 */     setQueryString(p_queryString);
/*  316 */     setFragment(p_fragment);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize(JaxmURI p_other) {
/*  325 */     this.m_scheme = p_other.getScheme();
/*  326 */     this.m_userinfo = p_other.getUserinfo();
/*  327 */     this.m_host = p_other.getHost();
/*  328 */     this.m_port = p_other.getPort();
/*  329 */     this.m_path = p_other.getPath();
/*  330 */     this.m_queryString = p_other.getQueryString();
/*  331 */     this.m_fragment = p_other.getFragment();
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
/*      */   private void initialize(JaxmURI p_base, String p_uriSpec) throws MalformedURIException {
/*  352 */     if (p_base == null && (p_uriSpec == null || p_uriSpec.trim().length() == 0))
/*      */     {
/*  354 */       throw new MalformedURIException("Cannot initialize URI with empty parameters.");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  359 */     if (p_uriSpec == null || p_uriSpec.trim().length() == 0) {
/*  360 */       initialize(p_base);
/*      */       
/*      */       return;
/*      */     } 
/*  364 */     String uriSpec = p_uriSpec.trim();
/*  365 */     int uriSpecLen = uriSpec.length();
/*  366 */     int index = 0;
/*      */ 
/*      */ 
/*      */     
/*  370 */     int colonIdx = uriSpec.indexOf(':');
/*  371 */     int slashIdx = uriSpec.indexOf('/');
/*  372 */     if (colonIdx < 2 || (colonIdx > slashIdx && slashIdx != -1)) {
/*  373 */       int fragmentIdx = uriSpec.indexOf('#');
/*      */       
/*  375 */       if (p_base == null && fragmentIdx != 0) {
/*  376 */         throw new MalformedURIException("No scheme found in URI.");
/*      */       }
/*      */     } else {
/*      */       
/*  380 */       initializeScheme(uriSpec);
/*  381 */       index = this.m_scheme.length() + 1;
/*      */     } 
/*      */ 
/*      */     
/*  385 */     if (index + 1 < uriSpecLen && uriSpec.substring(index).startsWith("//")) {
/*      */       
/*  387 */       index += 2;
/*  388 */       int startPos = index;
/*      */ 
/*      */       
/*  391 */       char testChar = Character.MIN_VALUE;
/*  392 */       while (index < uriSpecLen) {
/*  393 */         testChar = uriSpec.charAt(index);
/*  394 */         if (testChar == '/' || testChar == '?' || testChar == '#') {
/*      */           break;
/*      */         }
/*  397 */         index++;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  402 */       if (index > startPos) {
/*  403 */         initializeAuthority(uriSpec.substring(startPos, index));
/*      */       } else {
/*      */         
/*  406 */         this.m_host = "";
/*      */       } 
/*      */     } 
/*      */     
/*  410 */     initializePath(uriSpec.substring(index));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  417 */     if (p_base != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  426 */       if (this.m_path.length() == 0 && this.m_scheme == null && this.m_host == null) {
/*      */         
/*  428 */         this.m_scheme = p_base.getScheme();
/*  429 */         this.m_userinfo = p_base.getUserinfo();
/*  430 */         this.m_host = p_base.getHost();
/*  431 */         this.m_port = p_base.getPort();
/*  432 */         this.m_path = p_base.getPath();
/*      */         
/*  434 */         if (this.m_queryString == null) {
/*  435 */           this.m_queryString = p_base.getQueryString();
/*      */         }
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  442 */       if (this.m_scheme == null) {
/*  443 */         this.m_scheme = p_base.getScheme();
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  451 */       if (this.m_host == null) {
/*  452 */         this.m_userinfo = p_base.getUserinfo();
/*  453 */         this.m_host = p_base.getHost();
/*  454 */         this.m_port = p_base.getPort();
/*      */       } else {
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  461 */       if (this.m_path.length() > 0 && this.m_path.startsWith("/")) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  468 */       String path = "";
/*  469 */       String basePath = p_base.getPath();
/*      */ 
/*      */       
/*  472 */       if (basePath != null) {
/*  473 */         int lastSlash = basePath.lastIndexOf('/');
/*  474 */         if (lastSlash != -1) {
/*  475 */           path = basePath.substring(0, lastSlash + 1);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  480 */       path = path.concat(this.m_path);
/*      */ 
/*      */       
/*  483 */       index = -1;
/*  484 */       while ((index = path.indexOf("/./")) != -1) {
/*  485 */         path = path.substring(0, index + 1).concat(path.substring(index + 3));
/*      */       }
/*      */ 
/*      */       
/*  489 */       if (path.endsWith("/.")) {
/*  490 */         path = path.substring(0, path.length() - 1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  495 */       index = 1;
/*  496 */       int segIndex = -1;
/*  497 */       String tempString = null;
/*      */       
/*  499 */       while ((index = path.indexOf("/../", index)) > 0) {
/*  500 */         tempString = path.substring(0, path.indexOf("/../"));
/*  501 */         segIndex = tempString.lastIndexOf('/');
/*  502 */         if (segIndex != -1) {
/*  503 */           if (!tempString.substring(segIndex++).equals("..")) {
/*  504 */             path = path.substring(0, segIndex).concat(path.substring(index + 4));
/*      */             continue;
/*      */           } 
/*  507 */           index += 4;
/*      */           continue;
/*      */         } 
/*  510 */         index += 4;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  515 */       if (path.endsWith("/..")) {
/*  516 */         tempString = path.substring(0, path.length() - 3);
/*  517 */         segIndex = tempString.lastIndexOf('/');
/*  518 */         if (segIndex != -1) {
/*  519 */           path = path.substring(0, segIndex + 1);
/*      */         }
/*      */       } 
/*  522 */       this.m_path = path;
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
/*      */   private void initializeScheme(String p_uriSpec) throws MalformedURIException {
/*  536 */     int uriSpecLen = p_uriSpec.length();
/*  537 */     int index = 0;
/*  538 */     String scheme = null;
/*  539 */     char testChar = Character.MIN_VALUE;
/*      */     
/*  541 */     while (index < uriSpecLen) {
/*  542 */       testChar = p_uriSpec.charAt(index);
/*  543 */       if (testChar == ':' || testChar == '/' || testChar == '?' || testChar == '#') {
/*      */         break;
/*      */       }
/*      */       
/*  547 */       index++;
/*      */     } 
/*  549 */     scheme = p_uriSpec.substring(0, index);
/*      */     
/*  551 */     if (scheme.length() == 0) {
/*  552 */       throw new MalformedURIException("No scheme found in URI.");
/*      */     }
/*      */     
/*  555 */     setScheme(scheme);
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
/*      */   private void initializeAuthority(String p_uriSpec) throws MalformedURIException {
/*  569 */     int index = 0;
/*  570 */     int start = 0;
/*  571 */     int end = p_uriSpec.length();
/*  572 */     char testChar = Character.MIN_VALUE;
/*  573 */     String userinfo = null;
/*      */ 
/*      */     
/*  576 */     if (p_uriSpec.indexOf('@', start) != -1) {
/*  577 */       while (index < end) {
/*  578 */         testChar = p_uriSpec.charAt(index);
/*  579 */         if (testChar == '@') {
/*      */           break;
/*      */         }
/*  582 */         index++;
/*      */       } 
/*  584 */       userinfo = p_uriSpec.substring(start, index);
/*  585 */       index++;
/*      */     } 
/*      */ 
/*      */     
/*  589 */     String host = null;
/*  590 */     start = index;
/*  591 */     while (index < end) {
/*  592 */       testChar = p_uriSpec.charAt(index);
/*  593 */       if (testChar == ':') {
/*      */         break;
/*      */       }
/*  596 */       index++;
/*      */     } 
/*  598 */     host = p_uriSpec.substring(start, index);
/*  599 */     int port = -1;
/*  600 */     if (host.length() > 0)
/*      */     {
/*  602 */       if (testChar == ':') {
/*      */         
/*  604 */         start = ++index;
/*  605 */         while (index < end) {
/*  606 */           index++;
/*      */         }
/*  608 */         String portStr = p_uriSpec.substring(start, index);
/*  609 */         if (portStr.length() > 0) {
/*  610 */           for (int i = 0; i < portStr.length(); i++) {
/*  611 */             if (!isDigit(portStr.charAt(i))) {
/*  612 */               throw new MalformedURIException(portStr + " is invalid. Port should only contain digits!");
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/*  618 */             port = Integer.parseInt(portStr);
/*      */           }
/*  620 */           catch (NumberFormatException nfe) {}
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  626 */     setHost(host);
/*  627 */     setPort(port);
/*  628 */     setUserinfo(userinfo);
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
/*      */   private void initializePath(String p_uriSpec) throws MalformedURIException {
/*  640 */     if (p_uriSpec == null) {
/*  641 */       throw new MalformedURIException("Cannot initialize path from null string!");
/*      */     }
/*      */ 
/*      */     
/*  645 */     int index = 0;
/*  646 */     int start = 0;
/*  647 */     int end = p_uriSpec.length();
/*  648 */     char testChar = Character.MIN_VALUE;
/*      */ 
/*      */     
/*  651 */     while (index < end) {
/*  652 */       testChar = p_uriSpec.charAt(index);
/*  653 */       if (testChar == '?' || testChar == '#') {
/*      */         break;
/*      */       }
/*      */       
/*  657 */       if (testChar == '%') {
/*  658 */         if (index + 2 >= end || !isHex(p_uriSpec.charAt(index + 1)) || !isHex(p_uriSpec.charAt(index + 2)))
/*      */         {
/*      */           
/*  661 */           throw new MalformedURIException("Path contains invalid escape sequence!");
/*      */         
/*      */         }
/*      */       }
/*  665 */       else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */         
/*  667 */         throw new MalformedURIException("Path contains invalid character: " + testChar);
/*      */       } 
/*      */       
/*  670 */       index++;
/*      */     } 
/*  672 */     this.m_path = p_uriSpec.substring(start, index);
/*      */ 
/*      */     
/*  675 */     if (testChar == '?') {
/*      */       
/*  677 */       start = ++index;
/*  678 */       while (index < end) {
/*  679 */         testChar = p_uriSpec.charAt(index);
/*  680 */         if (testChar == '#') {
/*      */           break;
/*      */         }
/*  683 */         if (testChar == '%') {
/*  684 */           if (index + 2 >= end || !isHex(p_uriSpec.charAt(index + 1)) || !isHex(p_uriSpec.charAt(index + 2)))
/*      */           {
/*      */             
/*  687 */             throw new MalformedURIException("Query string contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  691 */         else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */           
/*  693 */           throw new MalformedURIException("Query string contains invalid character:" + testChar);
/*      */         } 
/*      */         
/*  696 */         index++;
/*      */       } 
/*  698 */       this.m_queryString = p_uriSpec.substring(start, index);
/*      */     } 
/*      */ 
/*      */     
/*  702 */     if (testChar == '#') {
/*      */       
/*  704 */       start = ++index;
/*  705 */       while (index < end) {
/*  706 */         testChar = p_uriSpec.charAt(index);
/*      */         
/*  708 */         if (testChar == '%') {
/*  709 */           if (index + 2 >= end || !isHex(p_uriSpec.charAt(index + 1)) || !isHex(p_uriSpec.charAt(index + 2)))
/*      */           {
/*      */             
/*  712 */             throw new MalformedURIException("Fragment contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  716 */         else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */           
/*  718 */           throw new MalformedURIException("Fragment contains invalid character:" + testChar);
/*      */         } 
/*      */         
/*  721 */         index++;
/*      */       } 
/*  723 */       this.m_fragment = p_uriSpec.substring(start, index);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getScheme() {
/*  733 */     return this.m_scheme;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSchemeSpecificPart() {
/*  743 */     StringBuffer schemespec = new StringBuffer();
/*      */     
/*  745 */     if (this.m_userinfo != null || this.m_host != null || this.m_port != -1) {
/*  746 */       schemespec.append("//");
/*      */     }
/*      */     
/*  749 */     if (this.m_userinfo != null) {
/*  750 */       schemespec.append(this.m_userinfo);
/*  751 */       schemespec.append('@');
/*      */     } 
/*      */     
/*  754 */     if (this.m_host != null) {
/*  755 */       schemespec.append(this.m_host);
/*      */     }
/*      */     
/*  758 */     if (this.m_port != -1) {
/*  759 */       schemespec.append(':');
/*  760 */       schemespec.append(this.m_port);
/*      */     } 
/*      */     
/*  763 */     if (this.m_path != null) {
/*  764 */       schemespec.append(this.m_path);
/*      */     }
/*      */     
/*  767 */     if (this.m_queryString != null) {
/*  768 */       schemespec.append('?');
/*  769 */       schemespec.append(this.m_queryString);
/*      */     } 
/*      */     
/*  772 */     if (this.m_fragment != null) {
/*  773 */       schemespec.append('#');
/*  774 */       schemespec.append(this.m_fragment);
/*      */     } 
/*      */     
/*  777 */     return schemespec.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUserinfo() {
/*  786 */     return this.m_userinfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHost() {
/*  795 */     return this.m_host;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPort() {
/*  804 */     return this.m_port;
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
/*      */   public String getPath(boolean p_includeQueryString, boolean p_includeFragment) {
/*  823 */     StringBuffer pathString = new StringBuffer(this.m_path);
/*      */     
/*  825 */     if (p_includeQueryString && this.m_queryString != null) {
/*  826 */       pathString.append('?');
/*  827 */       pathString.append(this.m_queryString);
/*      */     } 
/*      */     
/*  830 */     if (p_includeFragment && this.m_fragment != null) {
/*  831 */       pathString.append('#');
/*  832 */       pathString.append(this.m_fragment);
/*      */     } 
/*  834 */     return pathString.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPath() {
/*  844 */     return this.m_path;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getQueryString() {
/*  855 */     return this.m_queryString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFragment() {
/*  866 */     return this.m_fragment;
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
/*      */   public void setScheme(String p_scheme) throws MalformedURIException {
/*  879 */     if (p_scheme == null) {
/*  880 */       throw new MalformedURIException("Cannot set scheme from null string!");
/*      */     }
/*      */     
/*  883 */     if (!isConformantSchemeName(p_scheme)) {
/*  884 */       throw new MalformedURIException("The scheme is not conformant.");
/*      */     }
/*      */     
/*  887 */     this.m_scheme = p_scheme.toLowerCase();
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
/*      */   public void setUserinfo(String p_userinfo) throws MalformedURIException {
/*  900 */     if (p_userinfo == null) {
/*  901 */       this.m_userinfo = null;
/*      */     } else {
/*      */       
/*  904 */       if (this.m_host == null) {
/*  905 */         throw new MalformedURIException("Userinfo cannot be set when host is null!");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  911 */       int index = 0;
/*  912 */       int end = p_userinfo.length();
/*  913 */       char testChar = Character.MIN_VALUE;
/*  914 */       while (index < end) {
/*  915 */         testChar = p_userinfo.charAt(index);
/*  916 */         if (testChar == '%') {
/*  917 */           if (index + 2 >= end || !isHex(p_userinfo.charAt(index + 1)) || !isHex(p_userinfo.charAt(index + 2)))
/*      */           {
/*      */             
/*  920 */             throw new MalformedURIException("Userinfo contains invalid escape sequence!");
/*      */           
/*      */           }
/*      */         }
/*  924 */         else if (!isUnreservedCharacter(testChar) && ";:&=+$,".indexOf(testChar) == -1) {
/*      */           
/*  926 */           throw new MalformedURIException("Userinfo contains invalid character:" + testChar);
/*      */         } 
/*      */         
/*  929 */         index++;
/*      */       } 
/*      */     } 
/*  932 */     this.m_userinfo = p_userinfo;
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
/*      */   public void setHost(String p_host) throws MalformedURIException {
/*  945 */     if (p_host == null || p_host.trim().length() == 0) {
/*  946 */       this.m_host = p_host;
/*  947 */       this.m_userinfo = null;
/*  948 */       this.m_port = -1;
/*      */     }
/*  950 */     else if (!isWellFormedAddress(p_host)) {
/*  951 */       throw new MalformedURIException("Host is not a well formed address!");
/*      */     } 
/*  953 */     this.m_host = p_host;
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
/*      */   public void setPort(int p_port) throws MalformedURIException {
/*  968 */     if (p_port >= 0 && p_port <= 65535) {
/*  969 */       if (this.m_host == null) {
/*  970 */         throw new MalformedURIException("Port cannot be set when host is null!");
/*      */       
/*      */       }
/*      */     }
/*  974 */     else if (p_port != -1) {
/*  975 */       throw new MalformedURIException("Invalid port number!");
/*      */     } 
/*  977 */     this.m_port = p_port;
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
/*      */   public void setPath(String p_path) throws MalformedURIException {
/*  995 */     if (p_path == null) {
/*  996 */       this.m_path = null;
/*  997 */       this.m_queryString = null;
/*  998 */       this.m_fragment = null;
/*      */     } else {
/*      */       
/* 1001 */       initializePath(p_path);
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
/*      */   public void appendPath(String p_addToPath) throws MalformedURIException {
/* 1020 */     if (p_addToPath == null || p_addToPath.trim().length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1024 */     if (!isURIString(p_addToPath)) {
/* 1025 */       throw new MalformedURIException("Path contains invalid character!");
/*      */     }
/*      */ 
/*      */     
/* 1029 */     if (this.m_path == null || this.m_path.trim().length() == 0) {
/* 1030 */       if (p_addToPath.startsWith("/")) {
/* 1031 */         this.m_path = p_addToPath;
/*      */       } else {
/*      */         
/* 1034 */         this.m_path = "/" + p_addToPath;
/*      */       }
/*      */     
/* 1037 */     } else if (this.m_path.endsWith("/")) {
/* 1038 */       if (p_addToPath.startsWith("/")) {
/* 1039 */         this.m_path = this.m_path.concat(p_addToPath.substring(1));
/*      */       } else {
/*      */         
/* 1042 */         this.m_path = this.m_path.concat(p_addToPath);
/*      */       }
/*      */     
/*      */     }
/* 1046 */     else if (p_addToPath.startsWith("/")) {
/* 1047 */       this.m_path = this.m_path.concat(p_addToPath);
/*      */     } else {
/*      */       
/* 1050 */       this.m_path = this.m_path.concat("/" + p_addToPath);
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
/*      */   public void setQueryString(String p_queryString) throws MalformedURIException {
/* 1067 */     if (p_queryString == null) {
/* 1068 */       this.m_queryString = null;
/*      */     } else {
/* 1070 */       if (!isGenericURI()) {
/* 1071 */         throw new MalformedURIException("Query string can only be set for a generic URI!");
/*      */       }
/*      */       
/* 1074 */       if (getPath() == null) {
/* 1075 */         throw new MalformedURIException("Query string cannot be set when path is null!");
/*      */       }
/*      */       
/* 1078 */       if (!isURIString(p_queryString)) {
/* 1079 */         throw new MalformedURIException("Query string contains invalid character!");
/*      */       }
/*      */ 
/*      */       
/* 1083 */       this.m_queryString = p_queryString;
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
/*      */   public void setFragment(String p_fragment) throws MalformedURIException {
/* 1099 */     if (p_fragment == null) {
/* 1100 */       this.m_fragment = null;
/*      */     } else {
/* 1102 */       if (!isGenericURI()) {
/* 1103 */         throw new MalformedURIException("Fragment can only be set for a generic URI!");
/*      */       }
/*      */       
/* 1106 */       if (getPath() == null) {
/* 1107 */         throw new MalformedURIException("Fragment cannot be set when path is null!");
/*      */       }
/*      */       
/* 1110 */       if (!isURIString(p_fragment)) {
/* 1111 */         throw new MalformedURIException("Fragment contains invalid character!");
/*      */       }
/*      */ 
/*      */       
/* 1115 */       this.m_fragment = p_fragment;
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
/*      */   public boolean equals(Object p_test) {
/* 1128 */     if (p_test instanceof JaxmURI) {
/* 1129 */       JaxmURI testURI = (JaxmURI)p_test;
/* 1130 */       if (((this.m_scheme == null && testURI.m_scheme == null) || (this.m_scheme != null && testURI.m_scheme != null && this.m_scheme.equals(testURI.m_scheme))) && ((this.m_userinfo == null && testURI.m_userinfo == null) || (this.m_userinfo != null && testURI.m_userinfo != null && this.m_userinfo.equals(testURI.m_userinfo))) && ((this.m_host == null && testURI.m_host == null) || (this.m_host != null && testURI.m_host != null && this.m_host.equals(testURI.m_host))) && this.m_port == testURI.m_port && ((this.m_path == null && testURI.m_path == null) || (this.m_path != null && testURI.m_path != null && this.m_path.equals(testURI.m_path))) && ((this.m_queryString == null && testURI.m_queryString == null) || (this.m_queryString != null && testURI.m_queryString != null && this.m_queryString.equals(testURI.m_queryString))) && ((this.m_fragment == null && testURI.m_fragment == null) || (this.m_fragment != null && testURI.m_fragment != null && this.m_fragment.equals(testURI.m_fragment))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1149 */         return true;
/*      */       }
/*      */     } 
/* 1152 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1157 */     return 153214;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1166 */     StringBuffer uriSpecString = new StringBuffer();
/*      */     
/* 1168 */     if (this.m_scheme != null) {
/* 1169 */       uriSpecString.append(this.m_scheme);
/* 1170 */       uriSpecString.append(':');
/*      */     } 
/* 1172 */     uriSpecString.append(getSchemeSpecificPart());
/* 1173 */     return uriSpecString.toString();
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
/*      */   public boolean isGenericURI() {
/* 1186 */     return (this.m_host != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConformantSchemeName(String p_scheme) {
/* 1197 */     if (p_scheme == null || p_scheme.trim().length() == 0) {
/* 1198 */       return false;
/*      */     }
/*      */     
/* 1201 */     if (!isAlpha(p_scheme.charAt(0))) {
/* 1202 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1206 */     for (int i = 1; i < p_scheme.length(); i++) {
/* 1207 */       char testChar = p_scheme.charAt(i);
/* 1208 */       if (!isAlphanum(testChar) && "+-.".indexOf(testChar) == -1)
/*      */       {
/* 1210 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1214 */     return true;
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
/*      */   public static boolean isWellFormedAddress(String p_address) {
/* 1229 */     if (p_address == null) {
/* 1230 */       return false;
/*      */     }
/*      */     
/* 1233 */     String address = p_address.trim();
/* 1234 */     int addrLength = address.length();
/* 1235 */     if (addrLength == 0 || addrLength > 255) {
/* 1236 */       return false;
/*      */     }
/*      */     
/* 1239 */     if (address.startsWith(".") || address.startsWith("-")) {
/* 1240 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1246 */     int index = address.lastIndexOf('.');
/* 1247 */     if (address.endsWith(".")) {
/* 1248 */       index = address.substring(0, index).lastIndexOf('.');
/*      */     }
/*      */     
/* 1251 */     if (index + 1 < addrLength && isDigit(p_address.charAt(index + 1))) {
/*      */       
/* 1253 */       int numDots = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1258 */       for (int i = 0; i < addrLength; i++) {
/* 1259 */         char testChar = address.charAt(i);
/* 1260 */         if (testChar == '.') {
/* 1261 */           if (!isDigit(address.charAt(i - 1)) || (i + 1 < addrLength && !isDigit(address.charAt(i + 1))))
/*      */           {
/* 1263 */             return false;
/*      */           }
/* 1265 */           numDots++;
/*      */         }
/* 1267 */         else if (!isDigit(testChar)) {
/* 1268 */           return false;
/*      */         } 
/*      */       } 
/* 1271 */       if (numDots != 3) {
/* 1272 */         return false;
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1280 */       for (int i = 0; i < addrLength; i++) {
/* 1281 */         char testChar = address.charAt(i);
/* 1282 */         if (testChar == '.') {
/* 1283 */           if (!isAlphanum(address.charAt(i - 1))) {
/* 1284 */             return false;
/*      */           }
/* 1286 */           if (i + 1 < addrLength && !isAlphanum(address.charAt(i + 1))) {
/* 1287 */             return false;
/*      */           }
/*      */         }
/* 1290 */         else if (!isAlphanum(testChar) && testChar != '-') {
/* 1291 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1295 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isDigit(char p_char) {
/* 1305 */     return (p_char >= '0' && p_char <= '9');
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isHex(char p_char) {
/* 1315 */     return (isDigit(p_char) || (p_char >= 'a' && p_char <= 'f') || (p_char >= 'A' && p_char <= 'F'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAlpha(char p_char) {
/* 1326 */     return ((p_char >= 'a' && p_char <= 'z') || (p_char >= 'A' && p_char <= 'Z'));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAlphanum(char p_char) {
/* 1336 */     return (isAlpha(p_char) || isDigit(p_char));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isReservedCharacter(char p_char) {
/* 1346 */     return (";/?:@&=+$,".indexOf(p_char) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isUnreservedCharacter(char p_char) {
/* 1355 */     return (isAlphanum(p_char) || "-_.!~*'() ".indexOf(p_char) != -1);
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
/*      */   private static boolean isURIString(String p_uric) {
/* 1367 */     if (p_uric == null) {
/* 1368 */       return false;
/*      */     }
/* 1370 */     int end = p_uric.length();
/* 1371 */     char testChar = Character.MIN_VALUE;
/* 1372 */     for (int i = 0; i < end; i++) {
/* 1373 */       testChar = p_uric.charAt(i);
/* 1374 */       if (testChar == '%') {
/* 1375 */         if (i + 2 >= end || !isHex(p_uric.charAt(i + 1)) || !isHex(p_uric.charAt(i + 2)))
/*      */         {
/*      */           
/* 1378 */           return false;
/*      */         }
/*      */         
/* 1381 */         i += 2;
/*      */ 
/*      */       
/*      */       }
/* 1385 */       else if (!isReservedCharacter(testChar) && !isUnreservedCharacter(testChar)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1390 */         return false;
/*      */       } 
/*      */     } 
/* 1393 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\JaxmURI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */