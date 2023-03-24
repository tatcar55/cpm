package com.sun.xml.ws.tx.coord.common;

import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;

public interface RegistrationIF<T extends javax.xml.ws.EndpointReference, K, P> {
  BaseRegisterResponseType<T, P> registerOperation(BaseRegisterType<T, K> paramBaseRegisterType);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\RegistrationIF.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */