package com.sun.xml.ws.api.server;

import com.sun.istack.Nullable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;

@ManagedData
public interface SDDocument {
  @ManagedAttribute
  QName getRootName();
  
  @ManagedAttribute
  boolean isWSDL();
  
  @ManagedAttribute
  boolean isSchema();
  
  @ManagedAttribute
  Set<String> getImports();
  
  @ManagedAttribute
  URL getURL();
  
  void writeTo(@Nullable PortAddressResolver paramPortAddressResolver, DocumentAddressResolver paramDocumentAddressResolver, OutputStream paramOutputStream) throws IOException;
  
  void writeTo(PortAddressResolver paramPortAddressResolver, DocumentAddressResolver paramDocumentAddressResolver, XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException, IOException;
  
  public static interface WSDL extends SDDocument {
    @ManagedAttribute
    String getTargetNamespace();
    
    @ManagedAttribute
    boolean hasPortType();
    
    @ManagedAttribute
    boolean hasService();
    
    @ManagedAttribute
    Set<QName> getAllServices();
  }
  
  public static interface Schema extends SDDocument {
    @ManagedAttribute
    String getTargetNamespace();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\SDDocument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */