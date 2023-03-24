package com.sun.xml.ws.api.pipe;

public interface FiberContextSwitchInterceptor {
  <R, P> R execute(Fiber paramFiber, P paramP, Work<R, P> paramWork);
  
  public static interface Work<R, P> {
    R execute(P param1P);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\FiberContextSwitchInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */