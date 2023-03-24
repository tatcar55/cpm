package com.sun.xml.rpc.soap;

import javax.xml.namespace.QName;

public interface SOAPEncodingConstants extends SOAPWSDLConstants {
  String getURIEnvelope();
  
  String getURIEncoding();
  
  String getURIHttp();
  
  QName getQNameEncodingArray();
  
  QName getQNameEncodingArraytype();
  
  QName getQNameEncodingItemtype();
  
  QName getQNameEncodingArraysize();
  
  QName getQNameEncodingBase64();
  
  QName getQNameEnvelopeEncodingStyle();
  
  QName getQNameSOAPFault();
  
  QName getFaultCodeClient();
  
  QName getFaultCodeMustUnderstand();
  
  QName getFaultCodeServer();
  
  QName getFaultCodeVersionMismatch();
  
  QName getFaultCodeDataEncodingUnknown();
  
  QName getFaultCodeProcedureNotPresent();
  
  QName getFaultCodeBadArguments();
  
  QName getQNameSOAPRpc();
  
  QName getQNameSOAPResult();
  
  QName getFaultCodeMisunderstood();
  
  SOAPVersion getSOAPVersion();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\SOAPEncodingConstants.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */