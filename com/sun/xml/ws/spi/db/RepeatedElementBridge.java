/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
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
/*     */ public class RepeatedElementBridge<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   XMLBridge<T> delegate;
/*     */   CollectionHandler collectionHandler;
/*     */   
/*     */   public RepeatedElementBridge(TypeInfo typeInfo, XMLBridge<T> xb) {
/*  76 */     this.delegate = xb;
/*  77 */     this.collectionHandler = create(typeInfo);
/*     */   }
/*     */   
/*     */   public CollectionHandler collectionHandler() {
/*  81 */     return this.collectionHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  86 */     return this.delegate.context();
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/*  91 */     this.delegate.marshal(object, output, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/*  96 */     this.delegate.marshal(object, output, nsContext, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/* 101 */     this.delegate.marshal(object, output);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 106 */     this.delegate.marshal(object, contentHandler, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public void marshal(T object, Result result) throws JAXBException {
/* 111 */     this.delegate.marshal(object, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 116 */     return this.delegate.unmarshal(in, au);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 121 */     return this.delegate.unmarshal(in, au);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(InputStream in) throws JAXBException {
/* 126 */     return this.delegate.unmarshal(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 131 */     return this.delegate.unmarshal(n, au);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/* 136 */     return this.delegate.getTypeInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 141 */     return this.delegate.supportOutputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   static class BaseCollectionHandler
/*     */     implements CollectionHandler
/*     */   {
/*     */     Class type;
/*     */ 
/*     */     
/*     */     BaseCollectionHandler(Class c) {
/* 152 */       this.type = c;
/*     */     } public int getSize(Object c) {
/* 154 */       return ((Collection)c).size();
/*     */     }
/*     */     public Object convert(List list) {
/*     */       try {
/* 158 */         Object o = this.type.newInstance();
/* 159 */         ((Collection)o).addAll(list);
/* 160 */         return o;
/* 161 */       } catch (Exception e) {
/* 162 */         e.printStackTrace();
/*     */         
/* 164 */         return list;
/*     */       } 
/*     */     } public Iterator iterator(Object c) {
/* 167 */       return ((Collection)c).iterator();
/*     */     } }
/*     */   
/* 170 */   static final CollectionHandler ListHandler = new BaseCollectionHandler(List.class) {
/*     */       public Object convert(List list) {
/* 172 */         return list;
/*     */       }
/*     */     };
/* 175 */   static final CollectionHandler HashSetHandler = new BaseCollectionHandler(HashSet.class) {
/*     */       public Object convert(List<?> list) {
/* 177 */         return new HashSet(list);
/*     */       }
/*     */     };
/*     */   public static CollectionHandler create(TypeInfo ti) {
/* 181 */     Class javaClass = (Class)ti.type;
/* 182 */     if (javaClass.isArray())
/* 183 */       return new ArrayHandler((Class)(ti.getItemType()).type); 
/* 184 */     if (List.class.equals(javaClass) || Collection.class.equals(javaClass))
/* 185 */       return ListHandler; 
/* 186 */     if (Set.class.equals(javaClass) || HashSet.class.equals(javaClass)) {
/* 187 */       return HashSetHandler;
/*     */     }
/* 189 */     return new BaseCollectionHandler(javaClass);
/*     */   }
/*     */   
/*     */   static class ArrayHandler implements CollectionHandler {
/*     */     Class componentClass;
/*     */     
/*     */     public ArrayHandler(Class component) {
/* 196 */       this.componentClass = component;
/*     */     }
/*     */     
/*     */     public int getSize(Object c) {
/* 200 */       return Array.getLength(c);
/*     */     }
/*     */     
/*     */     public Object convert(List list) {
/* 204 */       Object array = Array.newInstance(this.componentClass, list.size());
/* 205 */       for (int i = 0; i < list.size(); i++) {
/* 206 */         Array.set(array, i, list.get(i));
/*     */       }
/* 208 */       return array;
/*     */     }
/*     */     
/*     */     public Iterator iterator(final Object c) {
/* 212 */       return new Iterator() {
/* 213 */           int index = 0;
/*     */           
/*     */           public boolean hasNext() {
/* 216 */             if (c == null || Array.getLength(c) == 0) {
/* 217 */               return false;
/*     */             }
/* 219 */             return (this.index != Array.getLength(c));
/*     */           }
/*     */           
/*     */           public Object next() throws NoSuchElementException {
/* 223 */             Object retVal = null;
/*     */             try {
/* 225 */               retVal = Array.get(c, this.index++);
/* 226 */             } catch (ArrayIndexOutOfBoundsException ex) {
/* 227 */               throw new NoSuchElementException();
/*     */             } 
/* 229 */             return retVal;
/*     */           }
/*     */           
/*     */           public void remove() {}
/*     */         };
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface CollectionHandler {
/*     */     int getSize(Object param1Object);
/*     */     
/*     */     Iterator iterator(Object param1Object);
/*     */     
/*     */     Object convert(List param1List);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\RepeatedElementBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */