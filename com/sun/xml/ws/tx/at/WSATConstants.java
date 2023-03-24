/*     */ package com.sun.xml.ws.tx.at;
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
/*     */ public interface WSATConstants
/*     */ {
/*     */   public static final String SOAP_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope";
/*     */   public static final String MUST_UNDERSTAND = "mustUnderstand";
/*     */   public static final String MESSAGE_ID = "MessageID";
/*     */   public static final String WSADDRESSING_NS_URI = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
/*     */   public static final String WSA = "wsa";
/*     */   public static final String REFERENCE_PARAMETERS = "ReferenceParameters";
/*     */   public static final String TO = "To";
/*     */   public static final String ADDRESS = "Address";
/*     */   public static final String REPLY_TO = "ReplyTo";
/*     */   public static final String FROM = "From";
/*     */   public static final String FAULT_TO = "FaultTo";
/*     */   public static final String ACTION = "Action";
/*     */   public static final String WSCOOR10_NS_URI = "http://schemas.xmlsoap.org/ws/2004/10/wscoor";
/*     */   public static final String CURRENT_WSCOOR = "http://schemas.xmlsoap.org/ws/2004/10/wscoor";
/*     */   public static final String WSCOOR = "wscoor";
/*     */   public static final String COORDINATION_TYPE = "CoordinationType";
/*     */   public static final String REGISTRATION_SERVICE = "RegistrationService";
/*     */   public static final String EXPIRES = "Expires";
/*     */   public static final String IDENTIFIER = "Identifier";
/*     */   public static final String PARTICIPANT_PROTOCOL_SERVICE = "ParticipantProtocolService";
/*     */   public static final String PROTOCOL_IDENTIFIER = "ProtocolIdentifier";
/*     */   public static final String REGISTER = "Register";
/*     */   public static final String REGISTER_RESPONSE = "RegisterResponse";
/*     */   public static final String COORDINATION_CONTEXT = "CoordinationContext";
/*     */   public static final String COORDINATOR_PROTOCOL_SERVICE = "CoordinatorProtocolService";
/*  75 */   public static final QName WSCOOR_CONTEXT_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "CoordinationContext");
/*  76 */   public static final QName WSCOOR_REGISTER_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "Register");
/*     */   
/*     */   public static final String WSCOOR11_NS_URI = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06";
/*  79 */   public static final QName WSCOOR11_CONTEXT_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "CoordinationContext");
/*  80 */   public static final QName WSCOOR11_REGISTER_QNAME = new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "Register");
/*     */   
/*     */   public static final String HTTP_SCHEMAS_XMLSOAP_ORG_WS_2004_10_WSAT = "http://schemas.xmlsoap.org/ws/2004/10/wsat";
/*     */   
/*     */   public static final String WSAT10_NS_URI = "http://schemas.xmlsoap.org/ws/2004/10/wsat";
/*     */   public static final String WSAT = "wsat";
/*     */   public static final String DURABLE_2PC = "Durable2PC";
/*     */   public static final String VOLATILE_2PC = "Volatile2PC";
/*     */   public static final String PREPARE = "Prepare";
/*     */   public static final String COMMIT = "Commit";
/*     */   public static final String ROLLBACK = "Rollback";
/*     */   public static final String PREPARED = "Prepared";
/*     */   public static final String READONLY = "ReadOnly";
/*     */   public static final String COMMITTED = "Committed";
/*     */   public static final String ABORTED = "Aborted";
/*     */   public static final String REPLAY = "Replay";
/*     */   public static final String HTTP_SCHEMAS_XMLSOAP_ORG_WS_2004_10_WSAT_DURABLE_2PC = "http://schemas.xmlsoap.org/ws/2004/10/wsat/Durable2PC";
/*     */   public static final String HTTP_SCHEMAS_XMLSOAP_ORG_WS_2004_10_WSAT_VOLATILE_2PC = "http://schemas.xmlsoap.org/ws/2004/10/wsat/Volatile2PC";
/*     */   public static final String WLA_WSAT_NS_URI = "http://com.sun.xml.ws.tx.at/ws/2008/10/wsat";
/*     */   public static final String WSAT_WSAT = "wsat-wsat";
/*     */   public static final String WSAT_CONTEXT_ROOT = "__wstx-services";
/*     */   public static final String TXID = "txId";
/* 102 */   public static final QName TXID_QNAME = new QName("http://com.sun.xml.ws.tx.at/ws/2008/10/wsat", "txId", "wsat-wsat");
/*     */   public static final String BRANCHQUAL = "branchQual";
/* 104 */   public static final QName BRANCHQUAL_QNAME = new QName("http://com.sun.xml.ws.tx.at/ws/2008/10/wsat", "branchQual", "wsat-wsat");
/*     */   public static final String ROUTING = "routing";
/* 106 */   public static final QName ROUTING_QNAME = new QName("http://com.sun.xml.ws.tx.at/ws/2008/10/wsat", "routing", "wsat-wsat");
/*     */   public static final String WSAT_COORDINATORPORTTYPEPORT = "/__wstx-services/CoordinatorPortType";
/*     */   public static final String WSAT_REGISTRATIONCOORDINATORPORTTYPEPORT = "/__wstx-services/RegistrationPortTypeRPC";
/*     */   public static final String WSAT_REGISTRATIONREQUESTERPORTTYPEPORT = "/__wstx-services/RegistrationRequesterPortType";
/*     */   public static final String WSAT_PARTICIPANTPORTTYPEPORT = "/__wstx-services/ParticipantPortType";
/*     */   public static final String DEBUG_WSAT = "DebugWSAT";
/*     */   public static final String WSAT11_NS_URI = "http://docs.oasis-open.org/ws-tx/wsat/2006/06";
/*     */   public static final String WSAT11_DURABLE_2PC = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Durable2PC";
/*     */   public static final String WSAT11_VOLATILE_2PC = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Volatile2PC";
/*     */   public static final String WSAT11_REGISTRATIONCOORDINATORPORTTYPEPORT = "/__wstx-services/RegistrationPortTypeRPC11";
/*     */   public static final String WSAT11_PARTICIPANTPORTTYPEPORT = "/__wstx-services/ParticipantPortType11";
/*     */   public static final String WSAT11_COORDINATORPORTTYPEPORT = "/__wstx-services/CoordinatorPortType11";
/*     */   public static final String WSAT11_REGISTRATIONREQUESTERPORTTYPEPORT = "/__wstx-services/RegistrationRequesterPortType11";
/*     */   public static final String TXPROP_WSAT_FOREIGN_RECOVERY_CONTEXT = "com.sun.xml.ws.tx.foreignContext";
/*     */   public static final String WSAT_TRANSACTION = "wsat.transaction";
/*     */   public static final String WSAT_TRANSACTION_XID = "wsat.transaction.xid";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\WSATConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */