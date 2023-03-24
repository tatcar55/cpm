package com.sun.xml.ws.api.message;

import com.sun.istack.NotNull;
import java.util.Iterator;

public interface AttachmentEx extends Attachment {
  @NotNull
  Iterator<MimeHeader> getMimeHeaders();
  
  public static interface MimeHeader {
    String getName();
    
    String getValue();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\AttachmentEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */