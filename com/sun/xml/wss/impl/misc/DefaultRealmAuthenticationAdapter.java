/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.RealmAuthenticationAdapter;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.callback.CallerPrincipalCallback;
/*     */ import javax.security.auth.message.callback.PasswordValidationCallback;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class DefaultRealmAuthenticationAdapter
/*     */   extends RealmAuthenticationAdapter
/*     */ {
/*  84 */   private CallbackHandler gfCallbackHandler = null;
/*  85 */   private HashMap<String, String> tomcatUsersXML = null;
/*     */   
/*  87 */   private static DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*     */   
/*  89 */   private static String classname = "com.sun.enterprise.security.jmac.callback.ContainerCallbackHandler";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultRealmAuthenticationAdapter() {
/*  98 */     if (isGlassfish()) {
/*  99 */       this.gfCallbackHandler = loadGFHandler();
/* 100 */     } else if (isTomcat()) {
/* 101 */       populateTomcatUsersXML();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isGlassfish() {
/* 106 */     String val = System.getProperty("com.sun.aas.installRoot");
/* 107 */     if (val != null) {
/* 108 */       return true;
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */   private boolean isTomcat() {
/* 113 */     String val = System.getProperty("catalina.home");
/* 114 */     String val1 = System.getProperty("com.sun.aas.installRoot");
/* 115 */     if (val1 == null && val != null) {
/* 116 */       return true;
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean authenticateFromTomcatUsersXML(final Subject callerSubject, final String username, String password) throws XWSSecurityException {
/* 124 */     if (this.tomcatUsersXML != null) {
/* 125 */       String pass = this.tomcatUsersXML.get(username);
/* 126 */       if (pass == null) {
/* 127 */         return false;
/*     */       }
/* 129 */       if (pass.equals(password)) {
/*     */         
/* 131 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */             {
/*     */               public Object run() {
/* 134 */                 String x500Name = "CN=" + username;
/* 135 */                 Principal principal = new X500Principal(x500Name);
/* 136 */                 callerSubject.getPrincipals().add(principal);
/*     */ 
/*     */ 
/*     */                 
/* 140 */                 return null;
/*     */               }
/*     */             });
/* 143 */         return true;
/*     */       } 
/* 145 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 149 */     throw new XWSSecurityException("Internal Error: Username Authentication Failed: Could not Load/Locate tomcat-users.xml, Possible Cause is Application is Not Running on TOMCAT ?");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean authenticateWithGFCBH(Subject callerSubject, String username, String password) throws XWSSecurityException {
/* 155 */     if (this.gfCallbackHandler != null) {
/* 156 */       char[] pwd = (password == null) ? null : password.toCharArray();
/* 157 */       PasswordValidationCallback pvCallback = new PasswordValidationCallback(callerSubject, username, pwd);
/*     */       
/* 159 */       Callback[] callbacks = { (Callback)pvCallback };
/*     */       try {
/* 161 */         this.gfCallbackHandler.handle(callbacks);
/* 162 */       } catch (Exception e) {
/* 163 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */ 
/*     */       
/* 167 */       if (pwd != null) {
/* 168 */         pvCallback.clearPassword();
/*     */       }
/* 170 */       boolean result = pvCallback.getResult();
/* 171 */       if (result) {
/*     */         
/* 173 */         CallerPrincipalCallback pCallback = new CallerPrincipalCallback(callerSubject, username);
/* 174 */         callbacks = new Callback[] { (Callback)pCallback };
/*     */         try {
/* 176 */           this.gfCallbackHandler.handle(callbacks);
/* 177 */         } catch (Exception e) {
/* 178 */           throw new XWSSecurityException(e);
/*     */         } 
/* 180 */         return result;
/*     */       } 
/* 182 */       return result;
/*     */     } 
/*     */     
/* 185 */     throw new XWSSecurityException("Internal Error: Username Authentication Failed: Could not Locate/Load CallbackHandler: " + classname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean authenticate(Subject callerSubject, String username, String password) throws XWSSecurityException {
/* 192 */     if (isGlassfish()) {
/* 193 */       return authenticateWithGFCBH(callerSubject, username, password);
/*     */     }
/* 195 */     if (isTomcat()) {
/* 196 */       return authenticateFromTomcatUsersXML(callerSubject, username, password);
/*     */     }
/* 198 */     throw new XWSSecurityException("Error: Could not locate default username validator for the container");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean authenticate(Subject callerSubject, String username, String passwordDigest, String nonce, String created) throws XWSSecurityException {
/* 205 */     throw new XWSSecurityException("Not Yet Supported: Digest Authentication not yet supported in  DefaultRealmAuthenticationAdapter");
/*     */   }
/*     */ 
/*     */   
/*     */   protected CallbackHandler loadGFHandler() {
/* 210 */     Class<?> ret = null;
/*     */     
/*     */     try {
/* 213 */       ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */       try {
/* 215 */         if (loader != null) {
/* 216 */           ret = loader.loadClass(classname);
/*     */         }
/* 218 */       } catch (ClassNotFoundException e) {}
/*     */ 
/*     */ 
/*     */       
/* 222 */       if (ret == null) {
/*     */         
/* 224 */         loader = getClass().getClassLoader();
/* 225 */         ret = loader.loadClass(classname);
/*     */       } 
/*     */       
/* 228 */       if (ret != null) {
/* 229 */         CallbackHandler handler = (CallbackHandler)ret.newInstance();
/* 230 */         return handler;
/*     */       } 
/* 232 */     } catch (ClassNotFoundException e) {
/*     */ 
/*     */     
/*     */     }
/* 236 */     catch (InstantiationException e) {
/*     */     
/* 238 */     } catch (IllegalAccessException ex) {}
/*     */ 
/*     */ 
/*     */     
/* 242 */     return null;
/*     */   }
/*     */   
/*     */   private void populateTomcatUsersXML() {
/* 246 */     String catalinaHome = System.getProperty("catalina.home");
/*     */     
/* 248 */     String tomcat_user_xml = catalinaHome + File.separator + "conf" + File.separator + "tomcat-users.xml";
/*     */ 
/*     */     
/*     */     try {
/* 252 */       File tomcatUserXML = new File(tomcat_user_xml);
/* 253 */       if (!tomcatUserXML.exists()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 258 */       DocumentBuilder builder = dbf.newDocumentBuilder();
/* 259 */       Document doc = builder.parse(tomcatUserXML);
/* 260 */       NodeList nl = doc.getElementsByTagName("user");
/* 261 */       this.tomcatUsersXML = new HashMap<String, String>();
/*     */       
/* 263 */       for (int i = 0; i < nl.getLength(); i++) {
/* 264 */         Node n = nl.item(i);
/* 265 */         NamedNodeMap nmap = n.getAttributes();
/* 266 */         Node un = nmap.getNamedItem("username");
/* 267 */         if (un == null)
/*     */         {
/* 269 */           un = nmap.getNamedItem("name");
/*     */         }
/* 271 */         Node pn = nmap.getNamedItem("password");
/* 272 */         this.tomcatUsersXML.put(un.getNodeValue(), pn.getNodeValue());
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 277 */     catch (ParserConfigurationException ex) {
/* 278 */       throw new RuntimeException(ex);
/* 279 */     } catch (SAXException e) {
/* 280 */       throw new RuntimeException(e);
/* 281 */     } catch (IOException ie) {
/* 282 */       throw new RuntimeException(ie);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\DefaultRealmAuthenticationAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */