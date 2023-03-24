/*     */ package com.sun.xml.ws.api.config.management.policy;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.resources.ManagementMessages;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class ManagedServiceAssertion
/*     */   extends ManagementAssertion
/*     */ {
/*  67 */   public static final QName MANAGED_SERVICE_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "ManagedService");
/*     */ 
/*     */   
/*  70 */   private static final QName COMMUNICATION_SERVER_IMPLEMENTATIONS_PARAMETER_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "CommunicationServerImplementations");
/*     */   
/*  72 */   private static final QName COMMUNICATION_SERVER_IMPLEMENTATION_PARAMETER_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "CommunicationServerImplementation");
/*     */   
/*  74 */   private static final QName CONFIGURATOR_IMPLEMENTATION_PARAMETER_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "ConfiguratorImplementation");
/*     */   
/*  76 */   private static final QName CONFIG_SAVER_IMPLEMENTATION_PARAMETER_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "ConfigSaverImplementation");
/*     */   
/*  78 */   private static final QName CONFIG_READER_IMPLEMENTATION_PARAMETER_QNAME = new QName("http://java.sun.com/xml/ns/metro/management", "ConfigReaderImplementation");
/*     */   
/*  80 */   private static final QName CLASS_NAME_ATTRIBUTE_QNAME = new QName("className");
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static final QName ENDPOINT_DISPOSE_DELAY_ATTRIBUTE_QNAME = new QName("endpointDisposeDelay");
/*     */   
/*  86 */   private static final Logger LOGGER = Logger.getLogger(ManagedServiceAssertion.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ManagedServiceAssertion getAssertion(WSEndpoint endpoint) throws WebServiceException {
/*  96 */     LOGGER.entering(new Object[] { endpoint });
/*     */ 
/*     */ 
/*     */     
/* 100 */     PolicyMap policyMap = endpoint.getPolicyMap();
/* 101 */     ManagedServiceAssertion assertion = ManagementAssertion.<ManagedServiceAssertion>getAssertion(MANAGED_SERVICE_QNAME, policyMap, endpoint.getServiceName(), endpoint.getPortName(), ManagedServiceAssertion.class);
/*     */     
/* 103 */     LOGGER.exiting(assertion);
/* 104 */     return assertion;
/*     */   }
/*     */ 
/*     */   
/*     */   public ManagedServiceAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters) throws AssertionCreationException {
/* 109 */     super(MANAGED_SERVICE_QNAME, data, assertionParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManagementEnabled() {
/* 119 */     String management = getAttributeValue(MANAGEMENT_ATTRIBUTE_QNAME);
/* 120 */     boolean result = true;
/* 121 */     if (management != null) {
/* 122 */       if (management.trim().toLowerCase().equals("on")) {
/* 123 */         result = true;
/*     */       } else {
/*     */         
/* 126 */         result = Boolean.parseBoolean(management);
/*     */       } 
/*     */     }
/* 129 */     return result;
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
/*     */   public long getEndpointDisposeDelay(long defaultDelay) throws WebServiceException {
/* 142 */     long result = defaultDelay;
/* 143 */     String delayText = getAttributeValue(ENDPOINT_DISPOSE_DELAY_ATTRIBUTE_QNAME);
/* 144 */     if (delayText != null) {
/*     */       try {
/* 146 */         result = Long.parseLong(delayText);
/* 147 */       } catch (NumberFormatException e) {
/* 148 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_1008_EXPECTED_INTEGER_DISPOSE_DELAY_VALUE(delayText), e));
/*     */       } 
/*     */     }
/*     */     
/* 152 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ImplementationRecord> getCommunicationServerImplementations() {
/* 163 */     Collection<ImplementationRecord> result = new LinkedList<ImplementationRecord>();
/* 164 */     Iterator<PolicyAssertion> parameters = getParametersIterator();
/* 165 */     while (parameters.hasNext()) {
/* 166 */       PolicyAssertion parameter = parameters.next();
/* 167 */       if (COMMUNICATION_SERVER_IMPLEMENTATIONS_PARAMETER_QNAME.equals(parameter.getName())) {
/* 168 */         Iterator<PolicyAssertion> implementations = parameter.getParametersIterator();
/* 169 */         if (!implementations.hasNext()) {
/* 170 */           throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_1005_EXPECTED_COMMUNICATION_CHILD()));
/*     */         }
/*     */         
/* 173 */         while (implementations.hasNext()) {
/* 174 */           PolicyAssertion implementation = implementations.next();
/* 175 */           if (COMMUNICATION_SERVER_IMPLEMENTATION_PARAMETER_QNAME.equals(implementation.getName())) {
/* 176 */             result.add(getImplementation(implementation));
/*     */             continue;
/*     */           } 
/* 179 */           throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(ManagementMessages.WSM_1004_EXPECTED_XML_TAG(COMMUNICATION_SERVER_IMPLEMENTATION_PARAMETER_QNAME, implementation.getName())));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImplementationRecord getConfiguratorImplementation() {
/* 196 */     return findImplementation(CONFIGURATOR_IMPLEMENTATION_PARAMETER_QNAME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImplementationRecord getConfigSaverImplementation() {
/* 206 */     return findImplementation(CONFIG_SAVER_IMPLEMENTATION_PARAMETER_QNAME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImplementationRecord getConfigReaderImplementation() {
/* 216 */     return findImplementation(CONFIG_READER_IMPLEMENTATION_PARAMETER_QNAME);
/*     */   }
/*     */   
/*     */   private ImplementationRecord findImplementation(QName implementationName) {
/* 220 */     Iterator<PolicyAssertion> parameters = getParametersIterator();
/* 221 */     while (parameters.hasNext()) {
/* 222 */       PolicyAssertion parameter = parameters.next();
/* 223 */       if (implementationName.equals(parameter.getName())) {
/* 224 */         return getImplementation(parameter);
/*     */       }
/*     */     } 
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   private ImplementationRecord getImplementation(PolicyAssertion rootParameter) {
/* 231 */     String className = rootParameter.getAttributeValue(CLASS_NAME_ATTRIBUTE_QNAME);
/* 232 */     HashMap<QName, String> parameterMap = new HashMap<QName, String>();
/* 233 */     Iterator<PolicyAssertion> implementationParameters = rootParameter.getParametersIterator();
/* 234 */     Collection<NestedParameters> nestedParameters = new LinkedList<NestedParameters>();
/* 235 */     while (implementationParameters.hasNext()) {
/* 236 */       PolicyAssertion parameterAssertion = implementationParameters.next();
/* 237 */       QName parameterName = parameterAssertion.getName();
/* 238 */       if (parameterAssertion.hasParameters()) {
/* 239 */         Map<QName, String> nestedParameterMap = new HashMap<QName, String>();
/* 240 */         Iterator<PolicyAssertion> parameters = parameterAssertion.getParametersIterator();
/* 241 */         while (parameters.hasNext()) {
/* 242 */           PolicyAssertion parameter = parameters.next();
/* 243 */           String str = parameter.getValue();
/* 244 */           if (str != null) {
/* 245 */             str = str.trim();
/*     */           }
/* 247 */           nestedParameterMap.put(parameter.getName(), str);
/*     */         } 
/* 249 */         nestedParameters.add(new NestedParameters(parameterName, nestedParameterMap));
/*     */         continue;
/*     */       } 
/* 252 */       String value = parameterAssertion.getValue();
/* 253 */       if (value != null) {
/* 254 */         value = value.trim();
/*     */       }
/* 256 */       parameterMap.put(parameterName, value);
/*     */     } 
/*     */     
/* 259 */     return new ImplementationRecord(className, parameterMap, nestedParameters);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ImplementationRecord
/*     */   {
/*     */     private final String implementation;
/*     */ 
/*     */     
/*     */     private final Map<QName, String> parameters;
/*     */     
/*     */     private final Collection<ManagedServiceAssertion.NestedParameters> nestedParameters;
/*     */ 
/*     */     
/*     */     protected ImplementationRecord(String implementation, Map<QName, String> parameters, Collection<ManagedServiceAssertion.NestedParameters> nestedParameters) {
/* 275 */       this.implementation = implementation;
/* 276 */       this.parameters = parameters;
/* 277 */       this.nestedParameters = nestedParameters;
/*     */     }
/*     */     
/*     */     public String getImplementation() {
/* 281 */       return this.implementation;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Map<QName, String> getParameters() {
/* 291 */       return this.parameters;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Collection<ManagedServiceAssertion.NestedParameters> getNestedParameters() {
/* 302 */       return this.nestedParameters;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 307 */       if (obj == null) {
/* 308 */         return false;
/*     */       }
/* 310 */       if (getClass() != obj.getClass()) {
/* 311 */         return false;
/*     */       }
/* 313 */       ImplementationRecord other = (ImplementationRecord)obj;
/* 314 */       if ((this.implementation == null) ? (other.implementation != null) : !this.implementation.equals(other.implementation))
/*     */       {
/* 316 */         return false;
/*     */       }
/* 318 */       if (this.parameters != other.parameters && (this.parameters == null || !this.parameters.equals(other.parameters))) {
/* 319 */         return false;
/*     */       }
/* 321 */       if (this.nestedParameters != other.nestedParameters && (this.nestedParameters == null || !this.nestedParameters.equals(other.nestedParameters)))
/*     */       {
/* 323 */         return false;
/*     */       }
/* 325 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 330 */       int hash = 3;
/* 331 */       hash = 53 * hash + ((this.implementation != null) ? this.implementation.hashCode() : 0);
/* 332 */       hash = 53 * hash + ((this.parameters != null) ? this.parameters.hashCode() : 0);
/* 333 */       hash = 53 * hash + ((this.nestedParameters != null) ? this.nestedParameters.hashCode() : 0);
/* 334 */       return hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 339 */       StringBuilder text = new StringBuilder("ImplementationRecord: ");
/* 340 */       text.append("implementation = \"").append(this.implementation).append("\", ");
/* 341 */       text.append("parameters = \"").append(this.parameters).append("\", ");
/* 342 */       text.append("nested parameters = \"").append(this.nestedParameters).append("\"");
/* 343 */       return text.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NestedParameters
/*     */   {
/*     */     private final QName name;
/*     */ 
/*     */     
/*     */     private final Map<QName, String> parameters;
/*     */ 
/*     */     
/*     */     private NestedParameters(QName name, Map<QName, String> parameters) {
/* 358 */       this.name = name;
/* 359 */       this.parameters = parameters;
/*     */     }
/*     */     
/*     */     public QName getName() {
/* 363 */       return this.name;
/*     */     }
/*     */     
/*     */     public Map<QName, String> getParameters() {
/* 367 */       return this.parameters;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 372 */       if (obj == null) {
/* 373 */         return false;
/*     */       }
/* 375 */       if (getClass() != obj.getClass()) {
/* 376 */         return false;
/*     */       }
/* 378 */       NestedParameters other = (NestedParameters)obj;
/* 379 */       if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
/* 380 */         return false;
/*     */       }
/* 382 */       if (this.parameters != other.parameters && (this.parameters == null || !this.parameters.equals(other.parameters))) {
/* 383 */         return false;
/*     */       }
/* 385 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 390 */       int hash = 5;
/* 391 */       hash = 59 * hash + ((this.name != null) ? this.name.hashCode() : 0);
/* 392 */       hash = 59 * hash + ((this.parameters != null) ? this.parameters.hashCode() : 0);
/* 393 */       return hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 398 */       StringBuilder text = new StringBuilder("NestedParameters: ");
/* 399 */       text.append("name = \"").append(this.name).append("\", ");
/* 400 */       text.append("parameters = \"").append(this.parameters).append("\"");
/* 401 */       return text.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\config\management\policy\ManagedServiceAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */