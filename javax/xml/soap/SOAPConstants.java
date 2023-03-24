/*     */ package javax.xml.soap;
/*     */ 
/*     */ import javax.xml.namespace.QName;
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
/*     */ public interface SOAPConstants
/*     */ {
/*     */   public static final String DYNAMIC_SOAP_PROTOCOL = "Dynamic Protocol";
/*     */   public static final String SOAP_1_1_PROTOCOL = "SOAP 1.1 Protocol";
/*     */   public static final String SOAP_1_2_PROTOCOL = "SOAP 1.2 Protocol";
/*     */   public static final String DEFAULT_SOAP_PROTOCOL = "SOAP 1.1 Protocol";
/*     */   public static final String URI_NS_SOAP_1_1_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
/*     */   public static final String URI_NS_SOAP_1_2_ENVELOPE = "http://www.w3.org/2003/05/soap-envelope";
/*     */   public static final String URI_NS_SOAP_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
/*     */   public static final String URI_NS_SOAP_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";
/*     */   public static final String URI_NS_SOAP_1_2_ENCODING = "http://www.w3.org/2003/05/soap-encoding";
/*     */   public static final String SOAP_1_1_CONTENT_TYPE = "text/xml";
/*     */   public static final String SOAP_1_2_CONTENT_TYPE = "application/soap+xml";
/*     */   public static final String URI_SOAP_ACTOR_NEXT = "http://schemas.xmlsoap.org/soap/actor/next";
/*     */   public static final String URI_SOAP_1_2_ROLE_NEXT = "http://www.w3.org/2003/05/soap-envelope/role/next";
/*     */   public static final String URI_SOAP_1_2_ROLE_NONE = "http://www.w3.org/2003/05/soap-envelope/role/none";
/*     */   public static final String URI_SOAP_1_2_ROLE_ULTIMATE_RECEIVER = "http://www.w3.org/2003/05/soap-envelope/role/ultimateReceiver";
/*     */   public static final String SOAP_ENV_PREFIX = "env";
/* 185 */   public static final QName SOAP_VERSIONMISMATCH_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "VersionMismatch", "env");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final QName SOAP_MUSTUNDERSTAND_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "MustUnderstand", "env");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 199 */   public static final QName SOAP_DATAENCODINGUNKNOWN_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "DataEncodingUnknown", "env");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public static final QName SOAP_SENDER_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "Sender", "env");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   public static final QName SOAP_RECEIVER_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "Receiver", "env");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\SOAPConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */