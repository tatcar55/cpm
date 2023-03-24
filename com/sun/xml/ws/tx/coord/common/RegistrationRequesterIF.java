package com.sun.xml.ws.tx.coord.common;

import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;

public interface RegistrationRequesterIF<T extends javax.xml.ws.EndpointReference, P> {
  void registerResponse(BaseRegisterResponseType<T, P> paramBaseRegisterResponseType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\RegistrationRequesterIF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */