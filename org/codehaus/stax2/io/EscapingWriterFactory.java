package org.codehaus.stax2.io;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public interface EscapingWriterFactory {
  Writer createEscapingWriterFor(Writer paramWriter, String paramString) throws UnsupportedEncodingException;
  
  Writer createEscapingWriterFor(OutputStream paramOutputStream, String paramString) throws UnsupportedEncodingException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\io\EscapingWriterFactory.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */