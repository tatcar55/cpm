package javax.xml.ws;

import java.util.Map;
import java.util.concurrent.Future;

public interface Response<T> extends Future<T> {
  Map<String, Object> getContext();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\Response.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */