/*    */ package com.sun.xml.wss.jaxws.impl;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Constants
/*    */ {
/* 56 */   public static final String OPERATION_SCOPE = "operation-policy-scope".intern();
/* 57 */   public static final String BINDING_SCOPE = "binding-policy-scope".intern();
/* 58 */   public static final String rstSCTURI = "http://schemas.xmlsoap.org/ws/2005/02/trust/RST/SCT".intern();
/* 59 */   public static final String rstrSCTURI = "http://schemas.xmlsoap.org/ws/2005/02/trust/RSTR/SCT".intern();
/* 60 */   public static final String rstSCTURI_13NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/SCT".intern();
/* 61 */   public static final String rstrSCTURI_13NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/SCT".intern();
/* 62 */   public static final String rstTrustURI = "http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue".intern();
/* 63 */   public static final String rstrTrustURI = "http://schemas.xmlsoap.org/ws/2005/02/trust/RSTR/Issue".intern();
/* 64 */   public static final String wsaURI = "http://schemas.xmlsoap.org/ws/2004/08/addressing".intern();
/* 65 */   public static final String SC_ASSERTION = "SecureConversationAssertion".intern();
/* 66 */   public static final QName ACTION_HEADER = new QName(wsaURI, "Action");
/* 67 */   public static final QName _SecureConversationToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/07/securitypolicy", "SecureConversationToken");
/*    */   
/* 69 */   public static final String TRUST_2005_02_NAMESPACE = "http://schemas.xmlsoap.org/ws/2005/02/trust".intern();
/*    */   
/*    */   public static final String ADDRESSING_POLICY_NAMESPACE_URI = "http://schemas.xmlsoap.org/ws/2004/09/policy/addressing";
/*    */   
/*    */   public static final String XENC_NS = "http://www.w3.org/2001/04/xmlenc#";
/*    */   public static final String ENCRYPTED_DATA_LNAME = "EncryptedData";
/* 75 */   public static final QName MESSAGE_ID_HEADER = new QName(wsaURI, "MessageID");
/* 76 */   public static final List<PolicyAssertion> EMPTY_LIST = Collections.emptyList();
/*    */   
/* 78 */   public static final QName bsOperationName = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityToken");
/*    */   public static final String SUN_WSS_SECURITY_SERVER_POLICY_NS = "http://schemas.sun.com/2006/03/wss/server";
/*    */   public static final String SUN_WSS_SECURITY_CLIENT_POLICY_NS = "http://schemas.sun.com/2006/03/wss/client";
/*    */   public static final String RM_CREATE_SEQ = "http://schemas.xmlsoap.org/ws/2005/02/rm/CreateSequence";
/*    */   public static final String RM_CREATE_SEQ_RESP = "http://schemas.xmlsoap.org/ws/2005/02/rm/CreateSequenceResponse";
/*    */   public static final String RM_SEQ_ACK = "http://schemas.xmlsoap.org/ws/2005/02/rm/SequenceAcknowledgement";
/*    */   public static final String RM_TERMINATE_SEQ = "http://schemas.xmlsoap.org/ws/2005/02/rm/TerminateSequence";
/*    */   public static final String RM_LAST_MESSAGE = "http://schemas.xmlsoap.org/ws/2005/02/rm/LastMessage";
/*    */   public static final String JAXWS_21_MESSAGE = "JAXWS_2_1_MESSAGE";
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */