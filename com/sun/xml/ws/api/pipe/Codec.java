package com.sun.xml.ws.api.pipe;

import com.sun.xml.ws.api.message.Packet;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public interface Codec {
  String getMimeType();
  
  ContentType getStaticContentType(Packet paramPacket);
  
  ContentType encode(Packet paramPacket, OutputStream paramOutputStream) throws IOException;
  
  ContentType encode(Packet paramPacket, WritableByteChannel paramWritableByteChannel);
  
  Codec copy();
  
  void decode(InputStream paramInputStream, String paramString, Packet paramPacket) throws IOException;
  
  void decode(ReadableByteChannel paramReadableByteChannel, String paramString, Packet paramPacket);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Codec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */