package com.sun.xml.registry.uddi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class ProtoCallbackHandler implements CallbackHandler {
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  UDDIMapper mapper;
  
  public ProtoCallbackHandler(UDDIMapper paramUDDIMapper) {
    this.mapper = paramUDDIMapper;
  }
  
  public UDDIMapper getUDDIMapper() {
    return this.mapper;
  }
  
  public void handle(Callback[] paramArrayOfCallback) throws IOException, UnsupportedCallbackException {
    for (byte b = 0; b < paramArrayOfCallback.length; b++) {
      if (paramArrayOfCallback[b] instanceof TextOutputCallback) {
        TextOutputCallback textOutputCallback = (TextOutputCallback)paramArrayOfCallback[b];
        switch (textOutputCallback.getMessageType()) {
          case 0:
            this.logger.finest(textOutputCallback.getMessage());
            break;
          case 2:
            this.logger.log(Level.SEVERE, ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:ERROR:_") + textOutputCallback.getMessage());
            break;
          case 1:
            this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:WARNING:_") + textOutputCallback.getMessage());
            break;
          default:
            throw new IOException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:Unsupported_message_type:_") + textOutputCallback.getMessageType());
        } 
      } else if (paramArrayOfCallback[b] instanceof NameCallback) {
        NameCallback nameCallback = (NameCallback)paramArrayOfCallback[b];
        System.err.print(nameCallback.getPrompt());
        System.err.flush();
        nameCallback.setName((new BufferedReader(new InputStreamReader(System.in))).readLine());
      } else if (paramArrayOfCallback[b] instanceof PasswordCallback) {
        PasswordCallback passwordCallback = (PasswordCallback)paramArrayOfCallback[b];
        System.err.print(passwordCallback.getPrompt());
        System.err.flush();
        passwordCallback.setPassword(readPassword(System.in));
      } else if (paramArrayOfCallback[b] instanceof UDDIMapperCallback) {
        UDDIMapperCallback uDDIMapperCallback = (UDDIMapperCallback)paramArrayOfCallback[b];
        uDDIMapperCallback.setUDDIMapper(this.mapper);
      } else {
        throw new UnsupportedCallbackException(paramArrayOfCallback[b], ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ProtoCallbackHandler:Unrecognized_Callback"));
      } 
    } 
  }
  
  private char[] readPassword(InputStream paramInputStream) throws IOException {
    char[] arrayOfChar1 = new char[128];
    char[] arrayOfChar2 = arrayOfChar1;
    int i = arrayOfChar2.length;
    byte b = 0;
    while (true) {
      int k;
      int j;
      switch (j = paramInputStream.read()) {
        case -1:
        case 10:
          break;
        case 13:
          k = paramInputStream.read();
          if (k != 10 && k != -1) {
            if (!(paramInputStream instanceof PushbackInputStream))
              paramInputStream = new PushbackInputStream(paramInputStream); 
            ((PushbackInputStream)paramInputStream).unread(k);
            break;
          } 
          break;
      } 
      if (--i < 0) {
        arrayOfChar2 = new char[b + 128];
        i = arrayOfChar2.length - b - 1;
        System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, b);
        Arrays.fill(arrayOfChar1, ' ');
        arrayOfChar1 = arrayOfChar2;
      } 
      arrayOfChar2[b++] = (char)j;
    } 
    if (b == 0)
      return null; 
    char[] arrayOfChar3 = new char[b];
    System.arraycopy(arrayOfChar2, 0, arrayOfChar3, 0, b);
    Arrays.fill(arrayOfChar2, ' ');
    return arrayOfChar3;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\ProtoCallbackHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */