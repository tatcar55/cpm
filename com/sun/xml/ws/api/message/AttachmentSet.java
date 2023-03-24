package com.sun.xml.ws.api.message;

import com.sun.istack.Nullable;

public interface AttachmentSet extends Iterable<Attachment> {
  @Nullable
  Attachment get(String paramString);
  
  boolean isEmpty();
  
  void add(Attachment paramAttachment);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\AttachmentSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */