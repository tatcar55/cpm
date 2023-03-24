package org.jvnet.fastinfoset.sax;

import java.io.IOException;
import java.io.InputStream;
import org.jvnet.fastinfoset.FastInfosetException;
import org.jvnet.fastinfoset.FastInfosetParser;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public interface FastInfosetReader extends XMLReader, FastInfosetParser {
  public static final String ENCODING_ALGORITHM_CONTENT_HANDLER_PROPERTY = "http://jvnet.org/fastinfoset/sax/properties/encoding-algorithm-content-handler";
  
  public static final String PRIMITIVE_TYPE_CONTENT_HANDLER_PROPERTY = "http://jvnet.org/fastinfoset/sax/properties/primitive-type-content-handler";
  
  void parse(InputStream paramInputStream) throws IOException, FastInfosetException, SAXException;
  
  void setLexicalHandler(LexicalHandler paramLexicalHandler);
  
  LexicalHandler getLexicalHandler();
  
  void setDeclHandler(DeclHandler paramDeclHandler);
  
  DeclHandler getDeclHandler();
  
  void setEncodingAlgorithmContentHandler(EncodingAlgorithmContentHandler paramEncodingAlgorithmContentHandler);
  
  EncodingAlgorithmContentHandler getEncodingAlgorithmContentHandler();
  
  void setPrimitiveTypeContentHandler(PrimitiveTypeContentHandler paramPrimitiveTypeContentHandler);
  
  PrimitiveTypeContentHandler getPrimitiveTypeContentHandler();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\sax\FastInfosetReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */