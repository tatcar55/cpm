package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.util.Utility;
import com.sun.xml.registry.common.util.VersionUtil;
import java.net.PasswordAuthentication;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.xml.registry.Connection;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.RegistryService;

public class ConnectionImpl implements Connection {
  static final String QUERY_URL_PROP = "javax.xml.registry.queryManagerURL";
  
  static final String LIFE_CYCLE_URL_PROP = "javax.xml.registry.lifeCycleManagerURL";
  
  static final String SEMANTIC_EQ_PROP = "javax.xml.registry.semanticEquivalences";
  
  static final String AUTH_METHOD_PROP = "javax.xml.registry.security.authenticationMethod";
  
  static final String MAX_ROWS = "javax.xml.registry.uddi.maxRows";
  
  static final String POSTAL_SCHEME_PROP = "javax.xml.registry.postalAddressScheme";
  
  static final String HTTP_PROXY_HOST = "com.sun.xml.registry.http.proxyHost";
  
  static final String HTTP_PROXY_PORT = "com.sun.xml.registry.http.proxyPort";
  
  static final String HTTPS_PROXY_HOST = "com.sun.xml.registry.https.proxyHost";
  
  static final String HTTPS_PROXY_PORT = "com.sun.xml.registry.https.proxyPort";
  
  static final String PROXY_USER_NAME = "com.sun.xml.registry.http.proxyUserName";
  
  static final String PROXY_PASSWORD = "com.sun.xml.registry.http.proxyPassword";
  
  static final String USE_SOAP = "com.sun.xml.registry.useSOAP";
  
  static final String USE_CACHE = "com.sun.xml.registry.useCache";
  
  static final String AUTH_TOKEN_TIMEOUT = "com.sun.xml.registry.authTokenTimeout";
  
  static final long DEFAULT_TIMEOUT = 15000L;
  
  public static final String USER_DEF_TAXONOMIES = "com.sun.xml.registry.userTaxonomyFilenames";
  
  String userDefinedTaxonomy;
  
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  String connectionId;
  
  char[] authToken;
  
  private LoginContext lc;
  
  private Subject subject;
  
  private String queryManagerURLString;
  
  private String lifeCycleManagerURLString;
  
  private String semanticEquivalences;
  
  private String authenticationMethod;
  
  private String maxRows;
  
  private String httpProxyHost;
  
  private String httpProxyPort;
  
  private String httpsProxyHost;
  
  private String httpsProxyPort;
  
  private String proxyUserName;
  
  private String proxyPassword;
  
  private String defaultPostalAddressScheme;
  
  private boolean useSOAP = false;
  
  private boolean useCache = true;
  
  private long timeout;
  
  private long timestamp;
  
  private RegistryServiceImpl service;
  
  private HashMap equivalences;
  
  private boolean synchronous = true;
  
  private Locale locale;
  
  boolean isClosed = false;
  
  Set privateCredentials;
  
  ConnectionImpl() {
    this.connectionId = Utility.generateUUID();
  }
  
  ConnectionImpl(Properties paramProperties) throws JAXRException, InvalidRequestException {
    if (this.logger.isLoggable(Level.FINEST))
      this.logger.finest("JAXR implementation version: " + VersionUtil.getJAXRCompleteVersion()); 
    this.queryManagerURLString = (String)paramProperties.get("javax.xml.registry.queryManagerURL");
    this.lifeCycleManagerURLString = (String)paramProperties.get("javax.xml.registry.lifeCycleManagerURL");
    if (this.queryManagerURLString == null)
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Missing_connection_property_") + "javax.xml.registry.queryManagerURL"); 
    if (this.lifeCycleManagerURLString == null) {
      this.logger.finest("making lifeCycleUrl to queryManagerUrl");
      this.lifeCycleManagerURLString = this.queryManagerURLString;
    } 
    this.semanticEquivalences = (String)paramProperties.get("javax.xml.registry.semanticEquivalences");
    this.authenticationMethod = (String)paramProperties.get("javax.xml.registry.security.authenticationMethod");
    this.logger.finest("authentication method=" + this.authenticationMethod);
    if (this.authenticationMethod != null && !this.authenticationMethod.equals("UDDI_GET_AUTHTOKEN"))
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_only_supports_UDDI_GET_AUTHTOKEN_authentication")); 
    this.maxRows = (String)paramProperties.get("javax.xml.registry.uddi.maxRows");
    this.httpProxyHost = (String)paramProperties.get("com.sun.xml.registry.http.proxyHost");
    this.httpProxyPort = (String)paramProperties.get("com.sun.xml.registry.http.proxyPort");
    this.httpsProxyHost = (String)paramProperties.get("com.sun.xml.registry.https.proxyHost");
    this.httpsProxyPort = (String)paramProperties.get("com.sun.xml.registry.https.proxyPort");
    this.proxyUserName = (String)paramProperties.get("com.sun.xml.registry.http.proxyUserName");
    this.proxyPassword = (String)paramProperties.get("com.sun.xml.registry.http.proxyPassword");
    if (this.httpProxyHost != null) {
      final String fHttpsPort = this.httpProxyHost;
      AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
              System.setProperty("http.proxyHost", fHttpHost);
              return null;
            }
          });
    } 
    if (this.httpProxyPort != null) {
      final String fHttpsPort = this.httpProxyPort;
      AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
              System.setProperty("http.proxyPort", fHttpPort);
              return null;
            }
          });
    } 
    if (this.httpsProxyHost != null) {
      final String fHttpsPort = this.httpsProxyHost;
      AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
              System.setProperty("https.proxyHost", fHttpsHost);
              return null;
            }
          });
    } 
    if (this.httpsProxyPort != null) {
      final String fHttpsPort = this.httpsProxyPort;
      AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
              System.setProperty("https.proxyPort", fHttpsPort);
              return null;
            }
          });
    } 
    this.userDefinedTaxonomy = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
          public Object run() {
            return System.getProperty("com.sun.xml.registry.userTaxonomyFilenames");
          }
        });
    String str1 = (String)paramProperties.get("com.sun.xml.registry.userTaxonomyFilenames");
    if (str1 != null && !str1.equals(""))
      this.userDefinedTaxonomy = str1; 
    this.defaultPostalAddressScheme = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
          public Object run() {
            return System.getProperty("javax.xml.registry.postalAddressScheme");
          }
        });
    String str2 = (String)paramProperties.get("javax.xml.registry.postalAddressScheme");
    if (str2 != null && !str2.equals(""))
      this.defaultPostalAddressScheme = str2; 
    String str3 = (String)paramProperties.get("com.sun.xml.registry.useSOAP");
    if (str3 != null && str3.equalsIgnoreCase("true"))
      this.useSOAP = true; 
    String str4 = (String)paramProperties.get("com.sun.xml.registry.useCache");
    if (str4 != null && str4.equalsIgnoreCase("false"))
      this.useCache = false; 
    String str5 = (String)paramProperties.get("com.sun.xml.registry.authTokenTimeout");
    if (str5 != null && !str5.equalsIgnoreCase("0")) {
      this.timeout = (new Long(str5)).longValue();
    } else {
      this.timeout = 15000L;
    } 
    this.subject = new Subject();
    this.service = new RegistryServiceImpl(this);
    this.connectionId = Utility.generateUUID();
  }
  
  public RegistryService getRegistryService() throws JAXRException {
    synchronized (this) {
      if (!this.isClosed) {
        if (this.service == null)
          this.service = new RegistryServiceImpl(this); 
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
      } 
      return this.service;
    } 
  }
  
  public void close() throws JAXRException {
    synchronized (this) {
      if (!this.isClosed) {
        if (this.logger.isLoggable(Level.FINEST))
          this.logger.finest("Closing UDDI connection" + this); 
        this.service = null;
        this.isClosed = true;
      } 
    } 
  }
  
  public boolean isClosed() {
    synchronized (this) {
      return this.isClosed;
    } 
  }
  
  String getQueryManagerURL() throws JAXRException {
    if (!this.isClosed)
      return this.queryManagerURLString; 
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_Closed"));
  }
  
  String getLifeCycleManagerURL() throws JAXRException {
    if (!this.isClosed)
      return this.lifeCycleManagerURLString; 
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
  }
  
  HashMap getSemanticEquivalences() {
    if (this.semanticEquivalences == null)
      return null; 
    if (this.equivalences == null) {
      if (this.logger.isLoggable(Level.FINEST))
        this.logger.finest("Parsing semantic equivalences"); 
      String str1 = " ";
      String str2 = ",";
      String str3 = "urn:";
      String str4 = "|";
      StringBuffer stringBuffer = new StringBuffer();
      this.equivalences = new HashMap<Object, Object>();
      this.logger.finest(this.semanticEquivalences);
      StringTokenizer stringTokenizer1 = new StringTokenizer(this.semanticEquivalences, str1);
      while (stringTokenizer1.hasMoreElements())
        stringBuffer.append(stringTokenizer1.nextToken()); 
      this.semanticEquivalences = stringBuffer.toString();
      this.logger.finest(this.semanticEquivalences);
      StringTokenizer stringTokenizer2 = new StringTokenizer(this.semanticEquivalences, str4);
      while (stringTokenizer2.hasMoreElements()) {
        String str5 = stringTokenizer2.nextToken();
        String str6 = str5.substring(str5.indexOf(str3) + str3.length(), str5.indexOf(str2));
        String str7 = str5.substring(str5.lastIndexOf(str3) + str3.length());
        this.logger.finest(str6 + "->" + str7);
        this.equivalences.put(str6, str7);
      } 
    } 
    return this.equivalences;
  }
  
  HashMap fetchEquivalences() {
    return this.equivalences;
  }
  
  String getDefaultPostalAddressScheme() {
    return this.defaultPostalAddressScheme;
  }
  
  String getAuthenticationMethod() {
    return this.authenticationMethod;
  }
  
  String getMaxRows() {
    return this.maxRows;
  }
  
  public boolean isSynchronous() throws JAXRException {
    synchronized (this) {
      if (!this.isClosed)
        return this.synchronous; 
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_Closed"));
    } 
  }
  
  public void setSynchronous(boolean paramBoolean) throws JAXRException {
    synchronized (this) {
      if (!this.isClosed) {
        this.synchronous = paramBoolean;
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
      } 
    } 
  }
  
  Locale getLocale() throws JAXRException {
    if (!this.isClosed)
      return (this.locale == null) ? Locale.getDefault() : this.locale; 
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
  }
  
  void setLocale(Locale paramLocale) throws JAXRException {
    if (!this.isClosed) {
      this.locale = paramLocale;
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
    } 
  }
  
  public void setCredentials(Set paramSet) throws JAXRException {
    synchronized (this) {
      this.privateCredentials = paramSet;
      if (!this.isClosed) {
        this.authToken = this.service.getUDDIMapper().getAuthorizationToken(paramSet).toCharArray();
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
      } 
    } 
  }
  
  public Set getCredentials() throws JAXRException {
    synchronized (this) {
      if (!this.isClosed)
        return this.privateCredentials; 
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
    } 
  }
  
  String getAuthToken() throws JAXRException {
    if (!this.isClosed)
      return new String(this.authToken); 
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
  }
  
  void setAuthToken(char[] paramArrayOfchar) throws JAXRException {
    if (!this.isClosed) {
      this.authToken = paramArrayOfchar;
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
    } 
  }
  
  void setAuthTokenTimestamp(long paramLong) {
    this.timestamp = paramLong;
  }
  
  long getAuthTokenTimestamp() {
    return this.timestamp;
  }
  
  Set getAuthCreds() throws JAXRException {
    if (!this.isClosed)
      return this.privateCredentials; 
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:Connection_is_closed"));
  }
  
  long getTokenTimeout() {
    return (this.timeout == 0L) ? 15000L : this.timeout;
  }
  
  private LoginContext getLoginContext() throws JAXRException {
    if (this.lc == null)
      try {
        this.lc = new LoginContext("Prototype", this.subject, new ProtoCallbackHandler(this.service.getUDDIMapper()));
      } catch (LoginException loginException) {
        throw new JAXRException(loginException);
      }  
    return this.lc;
  }
  
  void login() throws JAXRException {
    try {
      getLoginContext().login();
    } catch (LoginException loginException) {
      throw new JAXRException(loginException);
    } 
  }
  
  String getHttpProxyHost() {
    return this.httpProxyHost;
  }
  
  String getHttpProxyPort() {
    return this.httpProxyPort;
  }
  
  String getHttpsProxyHost() {
    return this.httpsProxyHost;
  }
  
  String getHttpsProxyPort() {
    return this.httpsProxyPort;
  }
  
  String getProxyUserName() {
    return this.proxyUserName;
  }
  
  String getProxyPassword() {
    return this.proxyPassword;
  }
  
  public String getUserDefinedTaxonomy() {
    return this.userDefinedTaxonomy;
  }
  
  boolean useSOAP() {
    return this.useSOAP;
  }
  
  public boolean useCache() {
    return this.useCache;
  }
  
  public String getCurrentUser() {
    if (this.privateCredentials != null && !this.privateCredentials.isEmpty())
      for (PasswordAuthentication passwordAuthentication : this.privateCredentials) {
        if (passwordAuthentication instanceof PasswordAuthentication)
          return ((PasswordAuthentication)passwordAuthentication).getUserName(); 
      }  
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\ConnectionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */