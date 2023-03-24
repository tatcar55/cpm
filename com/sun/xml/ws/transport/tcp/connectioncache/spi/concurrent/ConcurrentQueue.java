package com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent;

public interface ConcurrentQueue<V> {
  int size();
  
  Handle<V> offer(V paramV);
  
  V poll();
  
  public static interface Handle<V> {
    V value();
    
    boolean remove();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\concurrent\ConcurrentQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */