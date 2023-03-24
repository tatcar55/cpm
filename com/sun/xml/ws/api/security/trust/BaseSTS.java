package com.sun.xml.ws.api.security.trust;

import javax.xml.transform.Source;
import javax.xml.ws.Provider;

public interface BaseSTS extends Provider<Source> {
  Source invoke(Source paramSource);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\BaseSTS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */