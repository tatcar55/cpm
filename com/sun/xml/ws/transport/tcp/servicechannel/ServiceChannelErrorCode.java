/*    */ package com.sun.xml.ws.transport.tcp.servicechannel;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlEnum;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlType(name = "serviceChannelErrorCode")
/*    */ @XmlEnum
/*    */ public enum ServiceChannelErrorCode
/*    */ {
/* 52 */   TOO_MANY_OPEN_SESSIONS,
/* 53 */   TOO_MANY_OPEN_CHANNELS_FOR_SESSION,
/* 54 */   UNKNOWN_ENDPOINT_ADDRESS,
/* 55 */   CONTENT_NEGOTIATION_FAILED,
/* 56 */   UNKNOWN_CHANNEL_ID;
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\ServiceChannelErrorCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */