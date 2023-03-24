package com.sun.xml.ws.api.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.Component;
import java.net.URI;

public interface BoundEndpoint extends Component {
  @NotNull
  WSEndpoint getEndpoint();
  
  @NotNull
  URI getAddress();
  
  @NotNull
  URI getAddress(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\BoundEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */