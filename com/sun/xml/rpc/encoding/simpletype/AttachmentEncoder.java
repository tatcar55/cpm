package com.sun.xml.rpc.encoding.simpletype;

import javax.activation.DataHandler;

public interface AttachmentEncoder {
  DataHandler objectToDataHandler(Object paramObject) throws Exception;
  
  Object dataHandlerToObject(DataHandler paramDataHandler) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\AttachmentEncoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */