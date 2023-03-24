package com.sun.xml.ws.transport.tcp.connectioncache.spi.transport;

import java.io.IOException;

public interface ContactInfo<C extends Connection> {
  C createConnection() throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\transport\ContactInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */