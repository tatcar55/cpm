package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.util.MarshallerUtil;
import com.sun.xml.registry.common.util.Utility;
import com.sun.xml.registry.common.util.XMLUtil;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.CapabilityProfile;
import javax.xml.registry.DeclarativeQueryManager;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.LifeCycleManager;
import javax.xml.registry.RegistryService;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Encoder;

public class RegistryServiceImpl implements RegistryService {
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  private BusinessLifeCycleManager businessLCM;
  
  private BusinessQueryManager businessQM;
  
  private LifeCycleManager lcm;
  
  private ConnectionImpl connection;
  
  private BulkResponse bulkResponse;
  
  private UDDIMapper uddiMapper;
  
  private String serviceId;
  
  private UDDIObjectCache objectManager;
  
  private HashMap bulkResponses = new HashMap<Object, Object>();
  
  private XMLUtil xmlUtil;
  
  private boolean securitySet = false;
  
  private HashMap equivalentConcepts = new HashMap<Object, Object>();
  
  private String defaultPostalSchemeId;
  
  RegistryServiceImpl(ConnectionImpl paramConnectionImpl) {
    this.connection = paramConnectionImpl;
    this.serviceId = Utility.generateUUID();
    this.xmlUtil = XMLUtil.getInstance();
    this.uddiMapper = new UDDIMapper(this);
    this.objectManager = this.uddiMapper.getObjectManager();
  }
  
  public UDDIMapper getUDDIMapper() {
    if (this.uddiMapper == null)
      this.uddiMapper = new UDDIMapper(this); 
    return this.uddiMapper;
  }
  
  public String getServiceId() {
    return this.serviceId;
  }
  
  public UDDIObjectCache getObjectManager() {
    if (this.objectManager == null)
      this.objectManager = this.uddiMapper.getObjectManager(); 
    return this.objectManager;
  }
  
  public CapabilityProfile getCapabilityProfile() throws JAXRException {
    return CapabilityProfileImpl.getInstance();
  }
  
  public BusinessLifeCycleManager getBusinessLifeCycleManager() throws JAXRException {
    if (this.businessLCM == null)
      this.businessLCM = new BusinessLifeCycleManagerImpl(this); 
    return this.businessLCM;
  }
  
  public BusinessQueryManager getBusinessQueryManager() throws JAXRException {
    if (this.businessQM == null)
      this.businessQM = new BusinessQueryManagerImpl(this); 
    return this.businessQM;
  }
  
  public LifeCycleManager getLifeCycleManager() throws JAXRException {
    if (this.lcm == null)
      this.lcm = new LifeCycleManagerImpl(this); 
    return this.lcm;
  }
  
  public DeclarativeQueryManager getDeclarativeQueryManager() throws JAXRException, UnsupportedCapabilityException {
    throw new UnsupportedCapabilityException();
  }
  
  public String makeRegistrySpecificRequest(String paramString) throws JAXRException {
    boolean bool = false;
    try {
      return this.uddiMapper.makeRegistrySpecificRequest(paramString, bool);
    } catch (JAXRException jAXRException) {
      bool = true;
      return this.uddiMapper.makeRegistrySpecificRequest(paramString, bool);
    } 
  }
  
  void addConceptMapping(String paramString1, String paramString2) throws JAXRException {
    if (paramString1 != null && paramString2 != null)
      this.equivalentConcepts.put(paramString1, paramString2); 
  }
  
  HashMap getEquivalentConcepts() {
    return this.equivalentConcepts;
  }
  
  void setDefaultPostalScheme() {
    if (this.defaultPostalSchemeId == null)
      this.defaultPostalSchemeId = this.connection.getDefaultPostalAddressScheme(); 
  }
  
  public ClassificationScheme getDefaultPostalScheme() throws JAXRException {
    this.defaultPostalSchemeId = this.connection.getDefaultPostalAddressScheme();
    if (this.defaultPostalSchemeId == null)
      this.logger.finest(" defaultPostalSchemeId is null"); 
    if (this.uddiMapper == null)
      getUDDIMapper(); 
    return (this.uddiMapper != null) ? this.uddiMapper.getClassificationSchemeById(this.defaultPostalSchemeId) : null;
  }
  
  void storeBulkResponse(BulkResponse paramBulkResponse) {
    try {
      this.bulkResponses.put(paramBulkResponse.getRequestId(), paramBulkResponse);
      if (this.logger.isLoggable(Level.FINEST))
        this.logger.finest("Storing response with id: " + paramBulkResponse.getRequestId()); 
    } catch (JAXRException jAXRException) {
      this.logger.log(Level.SEVERE, jAXRException.getMessage(), (Throwable)jAXRException);
    } 
  }
  
  public BulkResponse getBulkResponse(String paramString) throws JAXRException {
    Object object = this.bulkResponses.remove(paramString);
    if (object == null)
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryServiceImpl:No_response_exists_for_specified_requestId")); 
    return (BulkResponse)object;
  }
  
  public ConnectionImpl getConnection() {
    return this.connection;
  }
  
  public SOAPBody jaxmSend(SOAPMessage paramSOAPMessage, boolean paramBoolean) throws JAXRException {
    SOAPBody sOAPBody = null;
    try {
      final String proxyHost = getConnection().getHttpProxyHost();
      final String proxyPort = getConnection().getHttpProxyPort();
      String str3 = getConnection().getProxyUserName();
      String str4 = getConnection().getProxyPassword();
      final String sslProxyHost = getConnection().getHttpsProxyHost();
      final String sslProxyPort = getConnection().getHttpsProxyPort();
      if (str1 != null && !str1.equals("") && str2 != null && !str2.equals("")) {
        AccessController.doPrivileged(new PrivilegedAction() {
              public Object run() {
                Properties properties = System.getProperties();
                properties.put("http.proxyHost", proxyHost);
                properties.put("http.proxyPort", proxyPort);
                return null;
              }
            });
        if (this.logger.isLoggable(Level.FINEST)) {
          this.logger.finest("proxy host = " + str1);
          this.logger.finest("proxy port = " + str2);
        } 
      } 
      if (str5 != null && !str5.equals("") && str6 != null && !str6.equals("")) {
        AccessController.doPrivileged(new PrivilegedAction() {
              public Object run() {
                Properties properties = System.getProperties();
                properties.put("https.proxyHost", sslProxyHost);
                properties.put("https.proxyPort", sslProxyPort);
                return null;
              }
            });
        if (this.logger.isLoggable(Level.FINEST)) {
          this.logger.finest("https proxy host = " + str5);
          this.logger.finest("https proxy port = " + str6);
        } 
      } 
      final SOAPConnectionFactory scf = AccessController.<SOAPConnectionFactory>doPrivileged(new PrivilegedAction<SOAPConnectionFactory>() {
            public Object run() {
              try {
                return SOAPConnectionFactory.newInstance();
              } catch (SOAPException sOAPException) {
                sOAPException.printStackTrace();
                return null;
              } 
            }
          });
      SOAPConnection sOAPConnection = AccessController.<SOAPConnection>doPrivileged(new PrivilegedAction<SOAPConnection>() {
            public Object run() {
              try {
                return scf.createConnection();
              } catch (SOAPException sOAPException) {
                sOAPException.printStackTrace();
                return null;
              } 
            }
          });
      if (str3 != null && str4 != null) {
        final String fProxyUserName = str3;
        final String fProxyPassword = str4;
        String str10 = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
              public Object run() {
                return "Basic " + (new BASE64Encoder()).encode((fProxyUserName + ":" + fProxyPassword).getBytes());
              }
            });
        paramSOAPMessage.getMimeHeaders().setHeader("Proxy-Authorization", str10);
      } 
      String str7 = null;
      if (paramBoolean) {
        if (!this.securitySet)
          this.securitySet = true; 
        str7 = this.connection.getLifeCycleManagerURL();
      } else {
        str7 = this.connection.getQueryManagerURL();
      } 
      paramSOAPMessage.saveChanges();
      MarshallerUtil.getInstance().log(paramSOAPMessage);
      URL uRL = new URL(str7);
      SOAPMessage sOAPMessage = sOAPConnection.call(paramSOAPMessage, uRL);
      MarshallerUtil.getInstance().log(sOAPMessage);
      sOAPBody = sOAPMessage.getSOAPBody();
      sOAPConnection.close();
    } catch (Exception exception) {
      this.logger.log(Level.FINEST, exception.getMessage(), exception);
      throw new JAXRException(exception);
    } 
    return sOAPBody;
  }
  
  public Node send(SOAPMessage paramSOAPMessage, boolean paramBoolean) throws JAXRException {
    boolean bool = getConnection().useSOAP();
    try {
      String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
            public Object run() {
              return System.getProperty("useSOAP");
            }
          });
      if (str != null && str.equalsIgnoreCase("true"))
        bool = true; 
    } catch (Throwable throwable) {
      this.logger.finest("Ignoring error checking for system useSOAP property: " + throwable);
    } 
    if (bool)
      this.logger.fine("External Soap no longer used"); 
    return jaxmSend(paramSOAPMessage, paramBoolean);
  }
  
  private void printNode(Node paramNode) {
    NodeList nodeList = paramNode.getChildNodes();
    int i = nodeList.getLength();
    for (byte b = 0; b < i; b++)
      printNode(nodeList.item(b)); 
  }
  
  public String getCurrentUser() {
    ConnectionImpl connectionImpl = getConnection();
    return (connectionImpl != null) ? connectionImpl.getCurrentUser() : null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\RegistryServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */