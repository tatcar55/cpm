/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.msv.grammar.Grammar;
/*     */ import com.sun.xml.bind.GrammarImpl;
/*     */ import com.sun.xml.bind.Messages;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GrammarInfoImpl
/*     */   implements GrammarInfo
/*     */ {
/*     */   private final Map rootTagMap;
/*     */   private final Class objectFactoryClass;
/*     */   private final Map defaultImplementationMap;
/*     */   private final ClassLoader classLoader;
/*     */   
/*     */   public GrammarInfoImpl(Map _rootTagMap, Map _defaultImplementationMap, Class _objectFactoryClass) {
/*  56 */     this.rootTagMap = _rootTagMap;
/*  57 */     this.defaultImplementationMap = _defaultImplementationMap;
/*  58 */     this.objectFactoryClass = _objectFactoryClass;
/*     */ 
/*     */     
/*  61 */     this.classLoader = this.objectFactoryClass.getClassLoader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Class lookupRootMap(String nsUri, String localName) {
/*  72 */     QName qn = new QName(nsUri, localName);
/*  73 */     if (this.rootTagMap.containsKey(qn)) return (Class)this.rootTagMap.get(qn);
/*     */     
/*  75 */     qn = new QName(nsUri, "*");
/*  76 */     if (this.rootTagMap.containsKey(qn)) return (Class)this.rootTagMap.get(qn);
/*     */     
/*  78 */     qn = new QName("*", "*");
/*  79 */     return (Class)this.rootTagMap.get(qn);
/*     */   }
/*     */   
/*     */   public final Class getRootElement(String namespaceUri, String localName) {
/*  83 */     Class intfCls = lookupRootMap(namespaceUri, localName);
/*  84 */     if (intfCls == null) return null; 
/*  85 */     return getDefaultImplementation(intfCls);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final UnmarshallingEventHandler createUnmarshaller(String namespaceUri, String localName, UnmarshallingContext context) {
/*  91 */     Class impl = getRootElement(namespaceUri, localName);
/*  92 */     if (impl == null) return null;
/*     */     
/*     */     try {
/*  95 */       return ((UnmarshallableObject)impl.newInstance()).createUnmarshaller(context);
/*  96 */     } catch (InstantiationException e) {
/*  97 */       throw new InstantiationError(e.toString());
/*  98 */     } catch (IllegalAccessException e) {
/*  99 */       throw new IllegalAccessError(e.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String[] getProbePoints() {
/* 104 */     List r = new ArrayList();
/* 105 */     for (Iterator itr = this.rootTagMap.keySet().iterator(); itr.hasNext(); ) {
/* 106 */       QName qn = itr.next();
/* 107 */       r.add(qn.getNamespaceURI());
/* 108 */       r.add(qn.getLocalPart());
/*     */     } 
/* 110 */     return r.<String>toArray(new String[r.size()]);
/*     */   }
/*     */   
/*     */   public final boolean recognize(String nsUri, String localName) {
/* 114 */     return (lookupRootMap(nsUri, localName) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Class getDefaultImplementation(Class javaContentInterface) {
/*     */     try {
/* 120 */       return Class.forName((String)this.defaultImplementationMap.get(javaContentInterface), true, this.classLoader);
/* 121 */     } catch (ClassNotFoundException e) {
/* 122 */       throw new NoClassDefFoundError(e.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Grammar getGrammar() throws JAXBException {
/*     */     try {
/* 132 */       InputStream is = this.objectFactoryClass.getResourceAsStream("bgm.ser");
/*     */       
/* 134 */       if (is == null) {
/*     */         
/* 136 */         String name = this.objectFactoryClass.getName();
/* 137 */         int idx = name.lastIndexOf('.');
/* 138 */         name = '/' + name.substring(0, idx + 1).replace('.', '/') + "bgm.ser";
/* 139 */         throw new JAXBException(Messages.format("GrammarInfo.NoBGM", name));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 144 */       ObjectInputStream ois = new ObjectInputStream(is);
/* 145 */       GrammarImpl g = (GrammarImpl)ois.readObject();
/* 146 */       ois.close();
/*     */       
/* 148 */       g.connect(new Grammar[] { (Grammar)g });
/*     */       
/* 150 */       return (Grammar)g;
/* 151 */     } catch (Exception e) {
/* 152 */       throw new JAXBException(Messages.format("GrammarInfo.UnableToReadBGM"), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSerializable castToXMLSerializable(Object o) {
/* 162 */     if (o instanceof XMLSerializable) {
/* 163 */       return (XMLSerializable)o;
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidatableObject castToValidatableObject(Object o) {
/* 173 */     if (o instanceof ValidatableObject) {
/* 174 */       return (ValidatableObject)o;
/*     */     }
/* 176 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\GrammarInfoImpl.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */