package org.glassfish.ha.store.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public interface ObjectInputOutputStreamFactory {
  ObjectOutputStream createObjectOutputStream(OutputStream paramOutputStream) throws IOException;
  
  ObjectInputStream createObjectInputStream(InputStream paramInputStream, ClassLoader paramClassLoader) throws IOException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\ha\store\spi\ObjectInputOutputStreamFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */