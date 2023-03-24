package com.sun.xml.ws.rx.ha;

public interface ReplicationManager<K extends java.io.Serializable, V> {
  V load(K paramK);
  
  void save(K paramK, V paramV, boolean paramBoolean);
  
  void remove(K paramK);
  
  void close();
  
  void destroy();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\ha\ReplicationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */