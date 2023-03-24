package com.sun.xml.ws.rx.rm.runtime.delivery;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface TransferQueue<E> extends BlockingQueue<E> {
  boolean tryTransfer(E paramE);
  
  void transfer(E paramE) throws InterruptedException;
  
  boolean tryTransfer(E paramE, long paramLong, TimeUnit paramTimeUnit) throws InterruptedException;
  
  boolean hasWaitingConsumer();
  
  int getWaitingConsumerCount();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\TransferQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */