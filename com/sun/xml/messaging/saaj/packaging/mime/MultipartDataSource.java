package com.sun.xml.messaging.saaj.packaging.mime;

import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import javax.activation.DataSource;

public interface MultipartDataSource extends DataSource {
  int getCount();
  
  MimeBodyPart getBodyPart(int paramInt) throws MessagingException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mime\MultipartDataSource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */