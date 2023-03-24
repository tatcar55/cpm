package com.sun.org.apache.xml.internal.security.keys.storage.implementations;

import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverSpi;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertsInFilesystemDirectoryResolver extends StorageResolverSpi {
  static Logger log = Logger.getLogger(CertsInFilesystemDirectoryResolver.class.getName());
  
  String _merlinsCertificatesDir = null;
  
  private List _certs = new ArrayList();
  
  Iterator _iterator = null;
  
  public CertsInFilesystemDirectoryResolver(String paramString) throws StorageResolverException {
    this._merlinsCertificatesDir = paramString;
    readCertsFromHarddrive();
    this._iterator = new FilesystemIterator(this._certs);
  }
  
  private void readCertsFromHarddrive() throws StorageResolverException {
    File file = new File(this._merlinsCertificatesDir);
    ArrayList arrayList = new ArrayList();
    String[] arrayOfString = file.list();
    for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
      String str = arrayOfString[b1];
      if (str.endsWith(".crt"))
        arrayList.add(arrayOfString[b1]); 
    } 
    CertificateFactory certificateFactory = null;
    try {
      certificateFactory = CertificateFactory.getInstance("X.509");
    } catch (CertificateException certificateException) {
      throw new StorageResolverException("empty", certificateException);
    } 
    if (certificateFactory == null)
      throw new StorageResolverException("empty"); 
    for (byte b2 = 0; b2 < arrayList.size(); b2++) {
      String str1 = file.getAbsolutePath() + File.separator + (String)arrayList.get(b2);
      File file1 = new File(str1);
      boolean bool = false;
      String str2 = null;
      try {
        FileInputStream fileInputStream = new FileInputStream(file1);
        X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(fileInputStream);
        fileInputStream.close();
        x509Certificate.checkValidity();
        this._certs.add(x509Certificate);
        str2 = x509Certificate.getSubjectDN().getName();
        bool = true;
      } catch (FileNotFoundException fileNotFoundException) {
        log.log(Level.FINE, "Could not add certificate from file " + str1, fileNotFoundException);
      } catch (IOException iOException) {
        log.log(Level.FINE, "Could not add certificate from file " + str1, iOException);
      } catch (CertificateNotYetValidException certificateNotYetValidException) {
        log.log(Level.FINE, "Could not add certificate from file " + str1, certificateNotYetValidException);
      } catch (CertificateExpiredException certificateExpiredException) {
        log.log(Level.FINE, "Could not add certificate from file " + str1, certificateExpiredException);
      } catch (CertificateException certificateException) {
        log.log(Level.FINE, "Could not add certificate from file " + str1, certificateException);
      } 
      if (bool)
        log.log(Level.FINE, "Added certificate: " + str2); 
    } 
  }
  
  public Iterator getIterator() {
    return this._iterator;
  }
  
  public static void main(String[] paramArrayOfString) throws Exception {
    CertsInFilesystemDirectoryResolver certsInFilesystemDirectoryResolver = new CertsInFilesystemDirectoryResolver("data/ie/baltimore/merlin-examples/merlin-xmldsig-eighteen/certs");
    Iterator iterator = certsInFilesystemDirectoryResolver.getIterator();
    while (iterator.hasNext()) {
      X509Certificate x509Certificate = iterator.next();
      byte[] arrayOfByte = XMLX509SKI.getSKIBytesFromCert(x509Certificate);
      System.out.println();
      System.out.println("Base64(SKI())=                 \"" + Base64.encode(arrayOfByte) + "\"");
      System.out.println("cert.getSerialNumber()=        \"" + x509Certificate.getSerialNumber().toString() + "\"");
      System.out.println("cert.getSubjectDN().getName()= \"" + x509Certificate.getSubjectDN().getName() + "\"");
      System.out.println("cert.getIssuerDN().getName()=  \"" + x509Certificate.getIssuerDN().getName() + "\"");
    } 
  }
  
  private static class FilesystemIterator implements Iterator {
    List _certs = null;
    
    int _i;
    
    public FilesystemIterator(List param1List) {
      this._certs = param1List;
      this._i = 0;
    }
    
    public boolean hasNext() {
      return (this._i < this._certs.size());
    }
    
    public Object next() {
      return this._certs.get(this._i++);
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Can't remove keys from KeyStore");
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\keys\storage\implementations\CertsInFilesystemDirectoryResolver.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */