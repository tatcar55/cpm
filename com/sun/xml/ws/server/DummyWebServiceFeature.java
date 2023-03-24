package com.sun.xml.ws.server;

import org.glassfish.gmbal.Description;
import org.glassfish.gmbal.InheritedAttribute;
import org.glassfish.gmbal.InheritedAttributes;
import org.glassfish.gmbal.ManagedData;

@ManagedData
@Description("WebServiceFeature")
@InheritedAttributes({@InheritedAttribute(methodName = "getID", description = "unique id for this feature"), @InheritedAttribute(methodName = "isEnabled", description = "true if this feature is enabled")})
interface DummyWebServiceFeature {}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\DummyWebServiceFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */