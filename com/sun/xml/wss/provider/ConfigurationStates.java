package com.sun.xml.wss.provider;

import com.sun.enterprise.security.jauth.AuthPolicy;

public interface ConfigurationStates {
  public static final int AUTHENTICATE_RECIPIENT_ONLY = 1;
  
  public static final int AUTHENTICATE_SENDER_TOKEN_ONLY = 2;
  
  public static final int AUTHENTICATE_SENDER_SIGNATURE_ONLY = 3;
  
  public static final int AUTHENTICATE_RECIPIENT_AUTHENTICATE_SENDER_TOKEN = 4;
  
  public static final int AUTHENTICATE_SENDER_TOKEN_AUTHENTICATE_RECIPIENT = 5;
  
  public static final int AUTHENTICATE_RECIPIENT_AUTHENTICATE_SENDER_SIGNATURE = 6;
  
  public static final int AUTHENTICATE_SENDER_SIGNATURE_AUTHENTICATE_RECIPIENT = 7;
  
  public static final int EMPTY_POLICY_STATE = 8;
  
  int resolveConfigurationState(AuthPolicy paramAuthPolicy, boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\ConfigurationStates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */