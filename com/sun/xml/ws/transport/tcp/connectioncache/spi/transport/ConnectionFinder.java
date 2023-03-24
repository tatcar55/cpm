package com.sun.xml.ws.transport.tcp.connectioncache.spi.transport;

import java.io.IOException;
import java.util.Collection;

public interface ConnectionFinder<C extends Connection> {
  C find(ContactInfo<C> paramContactInfo, Collection<C> paramCollection1, Collection<C> paramCollection2) throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\transport\ConnectionFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */