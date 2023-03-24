package com.sun.xml.ws.api.pipe;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Message;
import javax.xml.stream.XMLStreamReader;

public interface StreamSOAPCodec extends Codec {
  @NotNull
  Message decode(@NotNull XMLStreamReader paramXMLStreamReader);
  
  @NotNull
  Message decode(@NotNull XMLStreamReader paramXMLStreamReader, @NotNull AttachmentSet paramAttachmentSet);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\StreamSOAPCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */