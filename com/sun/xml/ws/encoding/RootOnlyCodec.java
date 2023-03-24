package com.sun.xml.ws.encoding;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Codec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

public interface RootOnlyCodec extends Codec {
  void decode(@NotNull InputStream paramInputStream, @NotNull String paramString, @NotNull Packet paramPacket, @NotNull AttachmentSet paramAttachmentSet) throws IOException;
  
  void decode(@NotNull ReadableByteChannel paramReadableByteChannel, @NotNull String paramString, @NotNull Packet paramPacket, @NotNull AttachmentSet paramAttachmentSet);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\RootOnlyCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */