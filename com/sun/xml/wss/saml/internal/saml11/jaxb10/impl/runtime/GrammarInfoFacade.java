/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import com.sun.msv.grammar.Expression;
/*     */ import com.sun.msv.grammar.ExpressionPool;
/*     */ import com.sun.msv.grammar.Grammar;
/*     */ import com.sun.xml.bind.GrammarImpl;
/*     */ import com.sun.xml.bind.Messages;
/*     */ import com.sun.xml.bind.ProxyGroup;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ class GrammarInfoFacade
/*     */   implements GrammarInfo
/*     */ {
/*  28 */   private GrammarInfo[] grammarInfos = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Grammar bgm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnmarshallingEventHandler createUnmarshaller(String namespaceUri, String localName, UnmarshallingContext context) {
/*  44 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/*  45 */       UnmarshallingEventHandler ueh = this.grammarInfos[i].createUnmarshaller(namespaceUri, localName, context);
/*  46 */       if (ueh != null) {
/*  47 */         return ueh;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getRootElement(String namespaceUri, String localName) {
/*  57 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/*  58 */       Class c = this.grammarInfos[i].getRootElement(namespaceUri, localName);
/*  59 */       if (c != null) {
/*  60 */         return c;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public boolean recognize(String nsUri, String localName) {
/*  69 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/*  70 */       if (this.grammarInfos[i].recognize(nsUri, localName))
/*  71 */         return true; 
/*  72 */     }  return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getProbePoints() {
/*  82 */     ArrayList probePointList = new ArrayList();
/*     */     
/*  84 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/*  85 */       String[] points = this.grammarInfos[i].getProbePoints();
/*  86 */       for (int j = 0; j < points.length; j++) {
/*  87 */         probePointList.add(points[j]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return probePointList.<String>toArray(new String[probePointList.size()]);
/*     */   }
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
/*     */   private void detectRootElementCollisions(String[] points) throws JAXBException {
/* 105 */     for (int i = 0; i < points.length; i += 2) {
/*     */ 
/*     */       
/* 108 */       boolean elementFound = false;
/* 109 */       for (int j = this.grammarInfos.length - 1; j >= 0; j--) {
/* 110 */         if (this.grammarInfos[j].recognize(points[i], points[i + 1])) {
/* 111 */           if (!elementFound) {
/* 112 */             elementFound = true;
/*     */           } else {
/* 114 */             throw new JAXBException(Messages.format("GrammarInfoFacade.CollisionDetected", points[i], points[i + 1]));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   static GrammarInfo createGrammarInfoFacade(String contextPath, ClassLoader classLoader) throws JAXBException {
/* 131 */     String version = null;
/*     */ 
/*     */     
/* 134 */     ArrayList gis = new ArrayList();
/*     */     
/* 136 */     StringTokenizer st = new StringTokenizer(contextPath, ":;");
/*     */ 
/*     */     
/* 139 */     while (st.hasMoreTokens()) {
/* 140 */       String targetPackage = st.nextToken();
/* 141 */       String objectFactoryName = targetPackage + ".ObjectFactory";
/*     */       
/*     */       try {
/* 144 */         JAXBContext c = (JAXBContext)Class.forName(objectFactoryName, true, classLoader).newInstance();
/*     */ 
/*     */ 
/*     */         
/* 148 */         if (version == null) { version = getVersion(c); }
/*     */         
/* 150 */         else if (!version.equals(getVersion(c)))
/* 151 */         { throw new JAXBException(Messages.format("GrammarInfoFacade.IncompatibleVersion", new Object[] { version, c.getClass().getName(), getVersion(c) })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 158 */         Object grammarInfo = c.getClass().getField("grammarInfo").get(null);
/*     */ 
/*     */         
/* 161 */         grammarInfo = ProxyGroup.blindWrap(grammarInfo, GrammarInfo.class, new Class[] { GrammarInfo.class, UnmarshallingContext.class, UnmarshallingEventHandler.class, XMLSerializer.class, XMLSerializable.class, NamespaceContext2.class, ValidatableObject.class });
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
/* 173 */         gis.add(grammarInfo);
/* 174 */       } catch (ClassNotFoundException e) {
/* 175 */         throw new NoClassDefFoundError(e.getMessage());
/* 176 */       } catch (Exception e) {
/* 177 */         throw new JAXBException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     if (gis.size() == 1)
/*     */     {
/* 183 */       return (GrammarInfo)gis.get(0);
/*     */     }
/* 185 */     return new GrammarInfoFacade(gis.<GrammarInfo>toArray(new GrammarInfo[gis.size()]));
/*     */   }
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
/*     */   private static String getVersion(JAXBContext c) throws JAXBException {
/*     */     try {
/* 201 */       Class jaxbBersionClass = (Class)c.getClass().getField("version").get(null);
/* 202 */       return (String)jaxbBersionClass.getField("version").get(null);
/* 203 */     } catch (Throwable t) {
/* 204 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Class getDefaultImplementation(Class javaContentInterface) {
/* 209 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/* 210 */       Class c = this.grammarInfos[i].getDefaultImplementation(javaContentInterface);
/* 211 */       if (c != null) return c; 
/*     */     } 
/* 213 */     return null;
/*     */   }
/*     */   
/* 216 */   public GrammarInfoFacade(GrammarInfo[] items) throws JAXBException { this.bgm = null;
/*     */     this.grammarInfos = items;
/*     */     detectRootElementCollisions(getProbePoints()); } public Grammar getGrammar() throws JAXBException {
/* 219 */     if (this.bgm == null) {
/* 220 */       Grammar[] grammars = new Grammar[this.grammarInfos.length];
/*     */       
/*     */       int i;
/* 223 */       for (i = 0; i < this.grammarInfos.length; i++) {
/* 224 */         grammars[i] = this.grammarInfos[i].getGrammar();
/*     */       }
/*     */       
/* 227 */       for (i = 0; i < this.grammarInfos.length; i++) {
/* 228 */         if (grammars[i] instanceof GrammarImpl) {
/* 229 */           ((GrammarImpl)grammars[i]).connect(grammars);
/*     */         }
/*     */       } 
/* 232 */       for (i = 0; i < this.grammarInfos.length; i++) {
/* 233 */         Grammar n = grammars[i];
/* 234 */         if (this.bgm == null) { this.bgm = n; }
/* 235 */         else { this.bgm = union(this.bgm, n); }
/*     */       
/*     */       } 
/* 238 */     }  return this.bgm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Grammar union(Grammar g1, Grammar g2) {
/* 248 */     final ExpressionPool pool = g1.getPool();
/* 249 */     final Expression top = pool.createChoice(g1.getTopLevel(), g2.getTopLevel());
/*     */     
/* 251 */     return new Grammar() { private final ExpressionPool val$pool;
/*     */         public ExpressionPool getPool() {
/* 253 */           return pool;
/*     */         } private final Expression val$top; private final GrammarInfoFacade this$0;
/*     */         public Expression getTopLevel() {
/* 256 */           return top;
/*     */         } }
/*     */       ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSerializable castToXMLSerializable(Object o) {
/* 266 */     XMLSerializable result = null;
/* 267 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/* 268 */       result = this.grammarInfos[i].castToXMLSerializable(o);
/* 269 */       if (result != null) {
/* 270 */         return result;
/*     */       }
/*     */     } 
/* 273 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidatableObject castToValidatableObject(Object o) {
/* 280 */     ValidatableObject result = null;
/* 281 */     for (int i = 0; i < this.grammarInfos.length; i++) {
/* 282 */       result = this.grammarInfos[i].castToValidatableObject(o);
/* 283 */       if (result != null) {
/* 284 */         return result;
/*     */       }
/*     */     } 
/* 287 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\GrammarInfoFacade.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */