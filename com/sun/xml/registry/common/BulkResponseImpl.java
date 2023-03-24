package com.sun.xml.registry.common;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;

public class BulkResponseImpl extends JAXRResponseImpl implements BulkResponse {
  static final Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.common");
        }
      });
  
  private ArrayList collection = new ArrayList();
  
  private ArrayList exceptions;
  
  private boolean isPartial = false;
  
  public Collection getCollection() throws JAXRException {
    synchronized (this) {
      while (!isAvailable()) {
        try {
          wait();
        } catch (InterruptedException interruptedException) {}
      } 
      return (Collection)this.collection.clone();
    } 
  }
  
  public void setCollection(Collection<?> paramCollection) {
    this.collection = new ArrayList(paramCollection);
    if (this.isPartial)
      setStatus(1); 
  }
  
  public void addCollection(Collection paramCollection) {
    if (paramCollection != null) {
      Iterator iterator = paramCollection.iterator();
      while (iterator.hasNext())
        this.collection.add(iterator.next()); 
    } 
  }
  
  public Collection getExceptions() throws JAXRException {
    synchronized (this) {
      while (!isAvailable()) {
        try {
          wait();
        } catch (InterruptedException interruptedException) {}
      } 
      if (this.exceptions != null)
        return (Collection)this.exceptions.clone(); 
      return null;
    } 
  }
  
  public void addException(Collection paramCollection) {
    if (paramCollection != null && paramCollection.size() > 0) {
      setStatus(2);
      initExceptions();
      Iterator iterator = paramCollection.iterator();
      while (iterator.hasNext())
        this.exceptions.add(iterator.next()); 
    } 
  }
  
  public void setExceptions(Collection<?> paramCollection) {
    if (paramCollection != null && paramCollection.size() > 0) {
      setStatus(2);
      initExceptions();
      this.exceptions = null;
      this.exceptions = new ArrayList(paramCollection);
    } 
  }
  
  public void addException(JAXRException paramJAXRException) {
    initExceptions();
    this.exceptions.add(paramJAXRException);
    setStatus(2);
  }
  
  public boolean isPartialResponse() throws JAXRException {
    return this.isPartial;
  }
  
  public void setPartialResponse(boolean paramBoolean) throws JAXRException {
    if (this.collection.size() > 0)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("BulkResponseImpl:Cannot_set_isPartial_with_collection_already_set.")); 
    this.isPartial = paramBoolean;
  }
  
  public void setPartialResponse(String paramString) throws JAXRException {
    if (this.collection.size() > 0)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("BulkResponseImpl:Cannot_set_isPartial_with_collection_already_set.")); 
    this.isPartial = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void updateResponse(BulkResponse paramBulkResponse) throws JAXRException {
    synchronized (this) {
      setPartialResponse(paramBulkResponse.isPartialResponse());
      this.collection = new ArrayList(paramBulkResponse.getCollection());
      if (paramBulkResponse.getExceptions() != null)
        this.exceptions = new ArrayList(paramBulkResponse.getExceptions()); 
      setStatus(paramBulkResponse.getStatus());
      notify();
    } 
  }
  
  public static BulkResponse combineBulkResponses(Collection paramCollection) {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    bulkResponseImpl.setStatus(0);
    try {
      bulkResponseImpl1 = null;
      ArrayList arrayList1 = new ArrayList();
      ArrayList arrayList2 = new ArrayList();
      boolean bool = false;
      for (BulkResponseImpl bulkResponseImpl1 : paramCollection) {
        arrayList1.addAll(bulkResponseImpl1.getCollection());
        if (bulkResponseImpl1.getExceptions() != null)
          arrayList2.addAll(bulkResponseImpl1.getExceptions()); 
        if (bulkResponseImpl1.isPartialResponse() == true)
          bool = true; 
      } 
      bulkResponseImpl.setPartialResponse(bool);
      if (bool == true)
        bulkResponseImpl.setStatus(1); 
      bulkResponseImpl.setCollection(arrayList1);
      if (arrayList2.size() > 0) {
        bulkResponseImpl.setExceptions(arrayList2);
        bulkResponseImpl.setStatus(2);
      } 
    } catch (JAXRException jAXRException) {
      logger.log(Level.SEVERE, jAXRException.getMessage(), (Throwable)jAXRException);
    } 
    return bulkResponseImpl;
  }
  
  private void initExceptions() {
    if (this.exceptions == null)
      this.exceptions = new ArrayList(); 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\BulkResponseImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */