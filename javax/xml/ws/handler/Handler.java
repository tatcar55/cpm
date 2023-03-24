package javax.xml.ws.handler;

public interface Handler<C extends MessageContext> {
  boolean handleMessage(C paramC);
  
  boolean handleFault(C paramC);
  
  void close(MessageContext paramMessageContext);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\handler\Handler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */