package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.infomodel.URIValidator;

public class URIValidatorImpl implements URIValidator, Serializable {
  private boolean validateURI = false;
  
  public boolean getValidateURI() {
    return this.validateURI;
  }
  
  public void setValidateURI(boolean paramBoolean) {
    this.validateURI = paramBoolean;
  }
  
  void validate(String paramString) throws InvalidRequestException {
    if (!this.validateURI)
      return; 
    URL uRL = null;
    try {
      uRL = new URL(paramString);
    } catch (MalformedURLException malformedURLException) {
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Malformed_URL_Exception:_") + paramString, malformedURLException);
    } 
    if (uRL.getProtocol().equalsIgnoreCase("http"))
      try {
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        int i = httpURLConnection.getResponseCode();
        if (i == 404)
          throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Received_response_code_") + i + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:_for_uri_") + paramString); 
        if (i < 200 || (i > 302 && i != 400))
          throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Received_response_code_") + i + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:_for_uri_") + paramString); 
      } catch (UnknownHostException unknownHostException) {
        throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Make_sure_your_proxies_are_set._Received_error:_") + unknownHostException, unknownHostException);
      } catch (Exception exception) {
        throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("URIValidatorImpl:Could_not_validate_") + paramString + ":" + exception, exception);
      }  
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\URIValidatorImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */