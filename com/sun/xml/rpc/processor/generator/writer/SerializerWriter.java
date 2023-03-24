package com.sun.xml.rpc.processor.generator.writer;

import com.sun.xml.rpc.processor.util.IndentingWriter;
import java.io.IOException;

public interface SerializerWriter {
  void createSerializer(IndentingWriter paramIndentingWriter, StringBuffer paramStringBuffer, String paramString1, boolean paramBoolean1, boolean paramBoolean2, String paramString2) throws IOException;
  
  void registerSerializer(IndentingWriter paramIndentingWriter, boolean paramBoolean1, boolean paramBoolean2, String paramString) throws IOException;
  
  void declareSerializer(IndentingWriter paramIndentingWriter, boolean paramBoolean1, boolean paramBoolean2) throws IOException;
  
  void initializeSerializer(IndentingWriter paramIndentingWriter, String paramString1, String paramString2) throws IOException;
  
  String serializerName();
  
  String serializerMemberName();
  
  String deserializerName();
  
  String deserializerMemberName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\writer\SerializerWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */