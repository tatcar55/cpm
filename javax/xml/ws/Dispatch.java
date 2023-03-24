package javax.xml.ws;

import java.util.concurrent.Future;

public interface Dispatch<T> extends BindingProvider {
  T invoke(T paramT);
  
  Response<T> invokeAsync(T paramT);
  
  Future<?> invokeAsync(T paramT, AsyncHandler<T> paramAsyncHandler);
  
  void invokeOneWay(T paramT);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\ws\Dispatch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */