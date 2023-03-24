package com.sun.org.apache.xml.internal.security;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Init {
  static Logger log = Logger.getLogger(Init.class.getName());
  
  private static boolean _alreadyInitialized = false;
  
  public static final String CONF_NS = "http://www.xmlsecurity.org/NS/#configuration";
  
  public static final boolean isInitialized() {
    return _alreadyInitialized;
  }
  
  public static synchronized void init() {
    if (_alreadyInitialized)
      return; 
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    long l5 = 0L;
    long l6 = 0L;
    long l7 = 0L;
    long l8 = 0L;
    long l9 = 0L;
    long l10 = 0L;
    long l11 = 0L;
    long l12 = 0L;
    _alreadyInitialized = true;
    try {
      long l13 = System.currentTimeMillis();
      long l14 = System.currentTimeMillis();
      long l15 = System.currentTimeMillis();
      long l16 = System.currentTimeMillis();
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setNamespaceAware(true);
      documentBuilderFactory.setValidating(false);
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      InputStream inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction() {
            public Object run() {
              String str = System.getProperty("com.sun.org.apache.xml.internal.security.resource.config");
              return getClass().getResourceAsStream((str != null) ? str : "resource/config.xml");
            }
          });
      Document document = documentBuilder.parse(inputStream);
      long l17 = System.currentTimeMillis();
      long l18 = 0L;
      l5 = System.currentTimeMillis();
      try {
        KeyInfo.init();
      } catch (Exception exception) {
        exception.printStackTrace();
        throw exception;
      } 
      l11 = System.currentTimeMillis();
      long l19 = 0L;
      long l20 = 0L;
      long l21 = 0L;
      long l22 = 0L;
      long l23 = 0L;
      Node node1;
      for (node1 = document.getFirstChild(); node1 != null && !"Configuration".equals(node1.getLocalName()); node1 = node1.getNextSibling());
      for (Node node2 = node1.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
        if (node2 instanceof Element) {
          String str = node2.getLocalName();
          if (str.equals("ResourceBundles")) {
            l18 = System.currentTimeMillis();
            Element element = (Element)node2;
            Attr attr1 = element.getAttributeNode("defaultLanguageCode");
            Attr attr2 = element.getAttributeNode("defaultCountryCode");
            String str1 = (attr1 == null) ? null : attr1.getNodeValue();
            String str2 = (attr2 == null) ? null : attr2.getNodeValue();
            I18n.init(str1, str2);
            l1 = System.currentTimeMillis();
          } 
          if (str.equals("CanonicalizationMethods")) {
            l2 = System.currentTimeMillis();
            Canonicalizer.init();
            Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "CanonicalizationMethod");
            for (byte b = 0; b < arrayOfElement.length; b++) {
              String str1 = arrayOfElement[b].getAttributeNS(null, "URI");
              String str2 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
              try {
                Class.forName(str2);
                log.log(Level.FINE, "Canonicalizer.register(" + str1 + ", " + str2 + ")");
                Canonicalizer.register(str1, str2);
              } catch (ClassNotFoundException classNotFoundException) {
                Object[] arrayOfObject = { str1, str2 };
                log.log(Level.SEVERE, I18n.translate("algorithm.classDoesNotExist", arrayOfObject));
              } 
            } 
            l3 = System.currentTimeMillis();
          } 
          if (str.equals("TransformAlgorithms")) {
            l19 = System.currentTimeMillis();
            Transform.init();
            Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "TransformAlgorithm");
            for (byte b = 0; b < arrayOfElement.length; b++) {
              String str1 = arrayOfElement[b].getAttributeNS(null, "URI");
              String str2 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
              try {
                Class.forName(str2);
                log.log(Level.FINE, "Transform.register(" + str1 + ", " + str2 + ")");
                Transform.register(str1, str2);
              } catch (ClassNotFoundException classNotFoundException) {
                Object[] arrayOfObject = { str1, str2 };
                log.log(Level.SEVERE, I18n.translate("algorithm.classDoesNotExist", arrayOfObject));
              } catch (NoClassDefFoundError noClassDefFoundError) {
                log.log(Level.WARNING, "Not able to found dependecies for algorithm, I'm keep working.");
              } 
            } 
            l10 = System.currentTimeMillis();
          } 
          if ("JCEAlgorithmMappings".equals(str)) {
            l20 = System.currentTimeMillis();
            JCEMapper.init((Element)node2);
            l4 = System.currentTimeMillis();
          } 
          if (str.equals("SignatureAlgorithms")) {
            l21 = System.currentTimeMillis();
            SignatureAlgorithm.providerInit();
            Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "SignatureAlgorithm");
            for (byte b = 0; b < arrayOfElement.length; b++) {
              String str1 = arrayOfElement[b].getAttributeNS(null, "URI");
              String str2 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
              try {
                Class.forName(str2);
                log.log(Level.FINE, "SignatureAlgorithm.register(" + str1 + ", " + str2 + ")");
                SignatureAlgorithm.register(str1, str2);
              } catch (ClassNotFoundException classNotFoundException) {
                Object[] arrayOfObject = { str1, str2 };
                log.log(Level.SEVERE, I18n.translate("algorithm.classDoesNotExist", arrayOfObject));
              } 
            } 
            l9 = System.currentTimeMillis();
          } 
          if (str.equals("ResourceResolvers")) {
            l8 = System.currentTimeMillis();
            ResourceResolver.init();
            Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Resolver");
            for (byte b = 0; b < arrayOfElement.length; b++) {
              String str1 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
              String str2 = arrayOfElement[b].getAttributeNS(null, "DESCRIPTION");
              if (str2 != null && str2.length() > 0) {
                log.log(Level.FINE, "Register Resolver: " + str1 + ": " + str2);
              } else {
                log.log(Level.FINE, "Register Resolver: " + str1 + ": For unknown purposes");
              } 
              try {
                ResourceResolver.register(str1);
              } catch (Throwable throwable) {
                log.log(Level.WARNING, "Cannot register:" + str1 + " perhaps some needed jars are not installed", throwable);
              } 
              l22 = System.currentTimeMillis();
            } 
          } 
          if (str.equals("KeyResolver")) {
            l12 = System.currentTimeMillis();
            KeyResolver.init();
            Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "Resolver");
            for (byte b = 0; b < arrayOfElement.length; b++) {
              String str1 = arrayOfElement[b].getAttributeNS(null, "JAVACLASS");
              String str2 = arrayOfElement[b].getAttributeNS(null, "DESCRIPTION");
              if (str2 != null && str2.length() > 0) {
                log.log(Level.FINE, "Register Resolver: " + str1 + ": " + str2);
              } else {
                log.log(Level.FINE, "Register Resolver: " + str1 + ": For unknown purposes");
              } 
              KeyResolver.register(str1);
            } 
            l6 = System.currentTimeMillis();
          } 
          if (str.equals("PrefixMappings")) {
            l7 = System.currentTimeMillis();
            log.log(Level.FINE, "Now I try to bind prefixes:");
            Element[] arrayOfElement = XMLUtils.selectNodes(node2.getFirstChild(), "http://www.xmlsecurity.org/NS/#configuration", "PrefixMapping");
            for (byte b = 0; b < arrayOfElement.length; b++) {
              String str1 = arrayOfElement[b].getAttributeNS(null, "namespace");
              String str2 = arrayOfElement[b].getAttributeNS(null, "prefix");
              log.log(Level.FINE, "Now I try to bind " + str2 + " to " + str1);
              ElementProxy.setDefaultPrefix(str1, str2);
            } 
            l23 = System.currentTimeMillis();
          } 
        } 
      } 
      long l24 = System.currentTimeMillis();
      log.log(Level.FINE, "XX_init                             " + (int)(l24 - l13) + " ms");
      log.log(Level.FINE, "  XX_prng                           " + (int)(l15 - l14) + " ms");
      log.log(Level.FINE, "  XX_parsing                        " + (int)(l17 - l16) + " ms");
      log.log(Level.FINE, "  XX_configure_i18n                 " + (int)(l1 - l18) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_c14n             " + (int)(l3 - l2) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_jcemapper        " + (int)(l4 - l20) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_keyInfo          " + (int)(l11 - l5) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_keyResolver      " + (int)(l6 - l12) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_prefixes         " + (int)(l23 - l7) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_resourceresolver " + (int)(l22 - l8) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_sigalgos         " + (int)(l9 - l21) + " ms");
      log.log(Level.FINE, "  XX_configure_reg_transforms       " + (int)(l10 - l19) + " ms");
    } catch (Exception exception) {
      log.log(Level.SEVERE, "Bad: ", exception);
      exception.printStackTrace();
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\Init.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */