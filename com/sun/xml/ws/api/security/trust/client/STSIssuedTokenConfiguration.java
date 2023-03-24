/*     */ package com.sun.xml.ws.api.security.trust.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class STSIssuedTokenConfiguration
/*     */   implements IssuedTokenConfiguration
/*     */ {
/*     */   public static final String PROTOCOL_10 = "http://schemas.xmlsoap.org/ws/2005/02/trust";
/*     */   public static final String PROTOCOL_13 = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
/*     */   public static final String ISSUED_TOKEN = "IssuedToken";
/*     */   public static final String APPLIES_TO = "AppliesTo";
/*     */   public static final String ACT_AS = "ActAs";
/*     */   public static final String SHARE_TOKEN = "shareToken";
/*     */   public static final String RENEW_EXPIRED_TOKEN = "renewExpiredToken";
/*     */   public static final String STS_ENDPOINT = "sts-endpoint";
/*     */   public static final String STS_MEX_ADDRESS = "sts-mex-address";
/*     */   public static final String STS_WSDL_LOCATION = "sts-wsdlLocation";
/*     */   public static final String STS_SERVICE_NAME = "sts-service-name";
/*     */   public static final String STS_PORT_NAME = "sts-port-name";
/*     */   public static final String STS_NAMESPACE = "sts-namespace";
/*     */   public static final String LIFE_TIME = "LifeTime";
/*     */   public static final String MAX_CLOCK_SKEW = "MaxClockSkew";
/*     */   protected String protocol;
/*     */   protected String stsEndpoint;
/*  75 */   protected String stsMEXAddress = null;
/*     */   
/*  77 */   protected String stsWSDLLocation = null;
/*     */   
/*  79 */   protected String stsServiceName = null;
/*     */   
/*  81 */   protected String stsPortName = null;
/*     */   
/*  83 */   protected String stsNamespace = null;
/*     */   
/*  85 */   protected SecondaryIssuedTokenParameters sisPara = null;
/*     */   
/*  87 */   private Map<String, Object> otherOptions = new HashMap<String, Object>();
/*     */ 
/*     */   
/*     */   protected STSIssuedTokenConfiguration() {}
/*     */   
/*     */   protected STSIssuedTokenConfiguration(String stsEndpoint, String stsMEXAddress) {
/*  93 */     this("http://schemas.xmlsoap.org/ws/2005/02/trust", stsEndpoint, stsMEXAddress);
/*     */   }
/*     */   protected STSIssuedTokenConfiguration(String protocol, String stsEndpoint, String stsMEXAddress) {
/*  96 */     this.protocol = protocol;
/*  97 */     this.stsEndpoint = stsEndpoint;
/*  98 */     this.stsMEXAddress = stsMEXAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   protected STSIssuedTokenConfiguration(String stsEndpoint, String stsWSDLLocation, String stsServiceName, String stsPortName, String stsNamespace) {
/* 103 */     this("http://schemas.xmlsoap.org/ws/2005/02/trust", stsEndpoint, stsWSDLLocation, stsServiceName, stsPortName, stsNamespace);
/*     */   }
/*     */ 
/*     */   
/*     */   protected STSIssuedTokenConfiguration(String protocol, String stsEndpoint, String stsWSDLLocation, String stsServiceName, String stsPortName, String stsNamespace) {
/* 108 */     this.protocol = protocol;
/* 109 */     this.stsEndpoint = stsEndpoint;
/* 110 */     this.stsWSDLLocation = stsWSDLLocation;
/* 111 */     this.stsServiceName = stsServiceName;
/* 112 */     this.stsPortName = stsPortName;
/* 113 */     this.stsNamespace = stsNamespace;
/*     */   }
/*     */   
/*     */   public String getProtocol() {
/* 117 */     return this.protocol;
/*     */   }
/*     */   
/*     */   public String getSTSEndpoint() {
/* 121 */     return this.stsEndpoint;
/*     */   }
/*     */   
/*     */   public String getSTSMEXAddress() {
/* 125 */     return this.stsMEXAddress;
/*     */   }
/*     */   
/*     */   public String getSTSWSDLLocation() {
/* 129 */     return this.stsWSDLLocation;
/*     */   }
/*     */   
/*     */   public String getSTSServiceName() {
/* 133 */     return this.stsServiceName;
/*     */   }
/*     */   
/*     */   public String getSTSPortName() {
/* 137 */     return this.stsPortName;
/*     */   }
/*     */   
/*     */   public String getSTSNamespace() {
/* 141 */     return this.stsNamespace;
/*     */   }
/*     */   
/*     */   public SecondaryIssuedTokenParameters getSecondaryIssuedTokenParameters() {
/* 145 */     return this.sisPara;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getOtherOptions() {
/* 149 */     return this.otherOptions;
/*     */   }
/*     */   
/*     */   public abstract String getTokenType();
/*     */   
/*     */   public abstract String getKeyType();
/*     */   
/*     */   public abstract long getKeySize();
/*     */   
/*     */   public abstract String getSignatureAlgorithm();
/*     */   
/*     */   public abstract String getEncryptionAlgorithm();
/*     */   
/*     */   public abstract String getCanonicalizationAlgorithm();
/*     */   
/*     */   public abstract String getKeyWrapAlgorithm();
/*     */   
/*     */   public abstract String getSignWith();
/*     */   
/*     */   public abstract String getEncryptWith();
/*     */   
/*     */   public abstract Claims getClaims();
/*     */   
/*     */   public abstract Token getOBOToken();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\client\STSIssuedTokenConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */