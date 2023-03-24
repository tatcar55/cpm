package com.sun.xml.registry.uddi;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.xml.registry.JAXRException;

public class UDDILoginModule implements LoginModule {
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  private Subject subject;
  
  private CallbackHandler callbackHandler;
  
  private Map sharedState;
  
  private Map options;
  
  private boolean debug = false;
  
  private boolean succeeded = false;
  
  private boolean commitSucceeded = false;
  
  private String username;
  
  private char[] password;
  
  private UDDIPrincipal userPrincipal;
  
  private PasswordAuthentication pa;
  
  private String authToken;
  
  private UDDIMapper mapper;
  
  public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map paramMap1, Map paramMap2) {
    this.subject = paramSubject;
    this.callbackHandler = paramCallbackHandler;
    this.sharedState = paramMap1;
    this.options = paramMap2;
    this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
  }
  
  public boolean login() throws LoginException {
    if (this.callbackHandler == null)
      throw new LoginException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDILoginModule:Error:_no_CallbackHandler_available_to_garner_authentication_information_from_the_user")); 
    Callback[] arrayOfCallback = new Callback[3];
    arrayOfCallback[0] = new NameCallback("UDDI Registry username: ");
    arrayOfCallback[1] = new PasswordCallback("UDDI Registry password: ", false);
    arrayOfCallback[2] = new UDDIMapperCallback();
    try {
      this.callbackHandler.handle(arrayOfCallback);
      this.username = ((NameCallback)arrayOfCallback[0]).getName();
      char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[1]).getPassword();
      if (arrayOfChar == null)
        arrayOfChar = new char[0]; 
      this.password = new char[arrayOfChar.length];
      System.arraycopy(arrayOfChar, 0, this.password, 0, arrayOfChar.length);
      ((PasswordCallback)arrayOfCallback[1]).clearPassword();
      this.mapper = ((UDDIMapperCallback)arrayOfCallback[2]).getUDDIMapper();
    } catch (IOException iOException) {
      LoginException loginException = new LoginException(iOException.toString());
      loginException.initCause(iOException);
      throw loginException;
    } catch (UnsupportedCallbackException unsupportedCallbackException) {
      LoginException loginException = new LoginException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDILoginModule:Error:_not_available_to_garner_authentication_information_from_the_user"));
      loginException.initCause(unsupportedCallbackException);
      throw loginException;
    } 
    if (this.debug) {
      this.logger.finest("\t\t[SampleLoginModule] user entered username: " + this.username);
      this.logger.finest("\t\t[SampleLoginModule] user entered password: ");
      for (byte b = 0; b < this.password.length; b++);
    } 
    this.pa = new PasswordAuthentication(this.username, this.password);
    HashSet<PasswordAuthentication> hashSet = new HashSet();
    hashSet.add(this.pa);
    try {
      this.authToken = this.mapper.getAuthorizationToken(hashSet);
      this.succeeded = true;
      return true;
    } catch (JAXRException jAXRException) {
      if (this.debug)
        this.logger.finest("\t\t[SampleLoginModule] authentication failed"); 
      this.succeeded = false;
      this.username = null;
      for (byte b = 0; b < this.password.length; b++)
        this.password[b] = ' '; 
      this.password = null;
      this.pa = null;
      throw new FailedLoginException("");
    } 
  }
  
  public boolean commit() throws LoginException {
    if (!this.succeeded)
      return false; 
    this.userPrincipal = new UDDIPrincipal(this.username);
    if (!this.subject.getPrincipals().contains(this.userPrincipal))
      this.subject.getPrincipals().add(this.userPrincipal); 
    if (this.debug)
      this.logger.finest("\t\t[SampleLoginModule] added SamplePrincipal to Subject"); 
    this.subject.getPrivateCredentials().add(this.pa);
    this.subject.getPrivateCredentials().add(this.authToken);
    this.username = null;
    for (byte b = 0; b < this.password.length; b++)
      this.password[b] = ' '; 
    this.password = null;
    this.commitSucceeded = true;
    return true;
  }
  
  public boolean abort() throws LoginException {
    if (!this.succeeded)
      return false; 
    if (this.succeeded == true && !this.commitSucceeded) {
      this.succeeded = false;
      this.username = null;
      if (this.password != null) {
        for (byte b = 0; b < this.password.length; b++)
          this.password[b] = ' '; 
        this.password = null;
      } 
      this.userPrincipal = null;
      this.pa = null;
    } else {
      logout();
    } 
    return true;
  }
  
  public boolean logout() throws LoginException {
    this.subject.getPrincipals().remove(this.userPrincipal);
    this.succeeded = false;
    this.succeeded = this.commitSucceeded;
    this.username = null;
    if (this.password != null) {
      for (byte b = 0; b < this.password.length; b++)
        this.password[b] = ' '; 
      this.password = null;
    } 
    this.userPrincipal = null;
    return true;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\UDDILoginModule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */