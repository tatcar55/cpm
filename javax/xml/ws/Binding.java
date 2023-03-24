package javax.xml.ws;

import java.util.List;
import javax.xml.ws.handler.Handler;

public interface Binding {
  List<Handler> getHandlerChain();
  
  void setHandlerChain(List<Handler> paramList);
  
  String getBindingID();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\Binding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */