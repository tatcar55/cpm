/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.v2.ClassFactory;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
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
/*     */ 
/*     */ public abstract class Coordinator
/*     */   implements ErrorHandler, ValidationEventHandler
/*     */ {
/*  82 */   private final HashMap<Class<? extends XmlAdapter>, XmlAdapter> adapters = new HashMap<Class<? extends XmlAdapter>, XmlAdapter>();
/*     */   
/*     */   private Object old;
/*     */   
/*     */   public final XmlAdapter putAdapter(Class<? extends XmlAdapter> c, XmlAdapter a) {
/*  87 */     if (a == null) {
/*  88 */       return this.adapters.remove(c);
/*     */     }
/*  90 */     return this.adapters.put(c, a);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object[] table;
/*     */   
/*     */   public Exception guyWhoSetTheTableToNull;
/*     */ 
/*     */   
/*     */   public final <T extends XmlAdapter> T getAdapter(Class<T> key) {
/* 100 */     XmlAdapter xmlAdapter = (XmlAdapter)key.cast(this.adapters.get(key));
/* 101 */     if (xmlAdapter == null) {
/* 102 */       xmlAdapter = (XmlAdapter)ClassFactory.create(key);
/* 103 */       putAdapter(key, xmlAdapter);
/*     */     } 
/* 105 */     return (T)xmlAdapter;
/*     */   }
/*     */   
/*     */   public <T extends XmlAdapter> boolean containsAdapter(Class<T> type) {
/* 109 */     return this.adapters.containsKey(type);
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
/*     */   protected final void setThreadAffinity() {
/* 135 */     this.table = activeTable.get();
/* 136 */     assert this.table != null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void resetThreadAffinity() {
/* 144 */     if (activeTable != null) {
/* 145 */       activeTable.remove();
/*     */     }
/* 147 */     if (debugTableNPE)
/* 148 */       this.guyWhoSetTheTableToNull = new Exception(); 
/* 149 */     this.table = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void pushCoordinator() {
/* 156 */     this.old = this.table[0];
/* 157 */     this.table[0] = this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void popCoordinator() {
/* 164 */     assert this.table[0] == this;
/* 165 */     this.table[0] = this.old;
/* 166 */     this.old = null;
/*     */   }
/*     */   
/*     */   public static Coordinator _getInstance() {
/* 170 */     return (Coordinator)((Object[])activeTable.get())[0];
/*     */   }
/*     */ 
/*     */   
/* 174 */   private static final ThreadLocal<Object[]> activeTable = new ThreadLocal<Object[]>()
/*     */     {
/*     */       public Object[] initialValue() {
/* 177 */         return new Object[1];
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean debugTableNPE;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ValidationEventLocator getLocation();
/*     */ 
/*     */ 
/*     */   
/*     */   public final void error(SAXParseException exception) throws SAXException {
/* 192 */     propagateEvent(1, exception);
/*     */   }
/*     */   
/*     */   public final void warning(SAXParseException exception) throws SAXException {
/* 196 */     propagateEvent(0, exception);
/*     */   }
/*     */   
/*     */   public final void fatalError(SAXParseException exception) throws SAXException {
/* 200 */     propagateEvent(2, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void propagateEvent(int severity, SAXParseException saxException) throws SAXException {
/* 206 */     ValidationEventImpl ve = new ValidationEventImpl(severity, saxException.getMessage(), getLocation());
/*     */ 
/*     */     
/* 209 */     Exception e = saxException.getException();
/* 210 */     if (e != null) {
/* 211 */       ve.setLinkedException(e);
/*     */     } else {
/* 213 */       ve.setLinkedException(saxException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 218 */     boolean result = handleEvent(ve);
/* 219 */     if (!result)
/*     */     {
/*     */       
/* 222 */       throw saxException;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 230 */       debugTableNPE = Boolean.getBoolean(Coordinator.class.getName() + ".debugTableNPE");
/* 231 */     } catch (SecurityException t) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\Coordinator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */