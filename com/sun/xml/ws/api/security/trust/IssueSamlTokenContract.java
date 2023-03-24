package com.sun.xml.ws.api.security.trust;

import com.sun.xml.ws.security.IssuedTokenContext;
import com.sun.xml.ws.security.Token;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface IssueSamlTokenContract<K, V> extends WSTrustContract<K, V> {
  Token createSAMLAssertion(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Map<QName, List<String>> paramMap, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\IssueSamlTokenContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */