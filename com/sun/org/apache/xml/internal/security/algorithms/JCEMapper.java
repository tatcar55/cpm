package com.sun.org.apache.xml.internal.security.algorithms;

import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;

public class JCEMapper {
  static Logger log = Logger.getLogger(JCEMapper.class.getName());
  
  private static Map uriToJCEName;
  
  private static Map algorithmsMap;
  
  private static String providerName = null;
  
  public static void init(Element paramElement) throws Exception {
    loadAlgorithms((Element)paramElement.getElementsByTagName("Algorithms").item(0));
  }
  
  static void loadAlgorithms(Element paramElement) {
    Element[] arrayOfElement = XMLUtils.selectNodes(paramElement.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Algorithm");
    uriToJCEName = new HashMap(arrayOfElement.length * 2);
    algorithmsMap = new HashMap(arrayOfElement.length * 2);
    for (byte b = 0; b < arrayOfElement.length; b++) {
      Element element = arrayOfElement[b];
      String str1 = element.getAttribute("URI");
      String str2 = element.getAttribute("JCEName");
      uriToJCEName.put(str1, str2);
      algorithmsMap.put(str1, new Algorithm(element));
    } 
  }
  
  static Algorithm getAlgorithmMapping(String paramString) {
    return (Algorithm)algorithmsMap.get(paramString);
  }
  
  public static String translateURItoJCEID(String paramString) {
    log.log(Level.FINE, "Request for URI " + paramString);
    return (String)uriToJCEName.get(paramString);
  }
  
  public static String getAlgorithmClassFromURI(String paramString) {
    log.log(Level.FINE, "Request for URI " + paramString);
    return ((Algorithm)algorithmsMap.get(paramString)).algorithmClass;
  }
  
  public static int getKeyLengthFromURI(String paramString) {
    return Integer.parseInt(((Algorithm)algorithmsMap.get(paramString)).keyLength);
  }
  
  public static String getJCEKeyAlgorithmFromURI(String paramString) {
    return ((Algorithm)algorithmsMap.get(paramString)).requiredKey;
  }
  
  public static String getProviderId() {
    return providerName;
  }
  
  public static void setProviderId(String paramString) {
    providerName = paramString;
  }
  
  public static class Algorithm {
    String algorithmClass;
    
    String keyLength;
    
    String requiredKey;
    
    public Algorithm(Element param1Element) {
      this.algorithmClass = param1Element.getAttribute("AlgorithmClass");
      this.keyLength = param1Element.getAttribute("KeyLength");
      this.requiredKey = param1Element.getAttribute("RequiredKey");
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\algorithms\JCEMapper.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */