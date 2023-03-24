/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
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
/*     */ public class StreamWriterData
/*     */   implements StreamWriterData
/*     */ {
/*  58 */   private GenericSecuredHeader gsh = null;
/*  59 */   private SecurityHeaderElement she = null;
/*  60 */   private SWDNamespaceContextEx nce = new SWDNamespaceContextEx();
/*  61 */   private HashMap<String, String> nsDecls = null;
/*  62 */   private XMLStreamBuffer xmlBuffer = null;
/*     */ 
/*     */   
/*     */   public StreamWriterData(GenericSecuredHeader gsh, HashMap<String, String> nsDecls) {
/*  66 */     this.gsh = gsh;
/*  67 */     this.nsDecls = nsDecls;
/*  68 */     addNSDecls();
/*     */   }
/*     */   
/*     */   public StreamWriterData(SecurityHeaderElement she, HashMap<String, String> nsDecls) {
/*  72 */     this.she = she;
/*  73 */     this.nsDecls = nsDecls;
/*  74 */     addNSDecls();
/*     */   }
/*     */   
/*     */   public StreamWriterData(XMLStreamBuffer buffer) {
/*  78 */     this.xmlBuffer = buffer;
/*     */   }
/*     */   
/*     */   public Object getDereferencedObject() {
/*  82 */     if (this.she != null) {
/*  83 */       return this.she;
/*     */     }
/*  85 */     return this.gsh;
/*     */   }
/*     */   
/*     */   private void addNSDecls() {
/*  89 */     Iterator<String> itr = this.nsDecls.keySet().iterator();
/*  90 */     while (itr.hasNext()) {
/*  91 */       String prefix = itr.next();
/*  92 */       String uri = this.nsDecls.get(prefix);
/*  93 */       this.nce.add(prefix, uri);
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/*  98 */     return this.nce;
/*     */   }
/*     */   
/*     */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/* 102 */     if (this.xmlBuffer != null) {
/* 103 */       this.xmlBuffer.writeToXMLStreamWriter(writer);
/* 104 */     } else if (this.gsh != null) {
/* 105 */       this.gsh.writeTo(writer);
/*     */     } else {
/* 107 */       ((SecurityElementWriter)this.she).writeTo(writer);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class SWDNamespaceContextEx
/*     */     implements NamespaceContextEx {
/* 113 */     private ArrayList<NamespaceContextEx.Binding> list = new ArrayList<NamespaceContextEx.Binding>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SWDNamespaceContextEx(boolean soap12Version) {
/* 119 */       if (soap12Version) {
/* 120 */         add("S", "http://www.w3.org/2003/05/soap-envelope");
/*     */       } else {
/* 122 */         add("S", "http://schemas.xmlsoap.org/soap/envelope/");
/*     */       } 
/*     */     }
/*     */     
/*     */     public void add(String prefix, String uri) {
/* 127 */       this.list.add(new BindingImpl(prefix, uri));
/*     */     }
/*     */     
/*     */     public Iterator<NamespaceContextEx.Binding> iterator() {
/* 131 */       return this.list.iterator();
/*     */     }
/*     */     
/*     */     public String getNamespaceURI(String prefix) {
/* 135 */       for (NamespaceContextEx.Binding binding : this.list) {
/* 136 */         if (prefix.equals(binding.getPrefix())) {
/* 137 */           return binding.getNamespaceURI();
/*     */         }
/*     */       } 
/* 140 */       return null;
/*     */     }
/*     */     
/*     */     public String getPrefix(String namespaceURI) {
/* 144 */       for (NamespaceContextEx.Binding binding : this.list) {
/* 145 */         if (namespaceURI.equals(binding.getNamespaceURI())) {
/* 146 */           return binding.getPrefix();
/*     */         }
/*     */       } 
/* 149 */       return null;
/*     */     }
/*     */     
/*     */     public Iterator getPrefixes(final String namespaceURI) {
/* 153 */       return new Iterator() {
/* 154 */           int index = 0;
/*     */           public boolean hasNext() {
/* 156 */             if (this.index++ < StreamWriterData.SWDNamespaceContextEx.this.list.size() && move()) {
/* 157 */               return true;
/*     */             }
/* 159 */             return false;
/*     */           }
/*     */           
/*     */           public Object next() {
/* 163 */             return ((NamespaceContextEx.Binding)StreamWriterData.SWDNamespaceContextEx.this.list.get(this.index)).getPrefix();
/*     */           }
/*     */           
/*     */           public void remove() {
/* 167 */             throw new UnsupportedOperationException();
/*     */           }
/*     */           
/*     */           private boolean move() {
/* 171 */             boolean found = false;
/*     */             do {
/* 173 */               if (namespaceURI.equals(((NamespaceContextEx.Binding)StreamWriterData.SWDNamespaceContextEx.this.list.get(this.index)).getNamespaceURI())) {
/* 174 */                 found = true;
/*     */                 break;
/*     */               } 
/* 177 */               this.index++;
/*     */             }
/* 179 */             while (this.index < StreamWriterData.SWDNamespaceContextEx.this.list.size());
/* 180 */             return found;
/*     */           }
/*     */         };
/*     */     }
/*     */     public SWDNamespaceContextEx() {}
/*     */     
/* 186 */     static class BindingImpl implements NamespaceContextEx.Binding { private String prefix = "";
/* 187 */       private String uri = "";
/*     */       public BindingImpl(String prefix, String uri) {
/* 189 */         this.prefix = prefix;
/* 190 */         this.uri = uri;
/* 191 */         if (this.prefix == null) {
/* 192 */           this.prefix = "";
/*     */         }
/*     */       }
/*     */       
/*     */       public String getPrefix() {
/* 197 */         return this.prefix;
/*     */       }
/*     */       
/*     */       public String getNamespaceURI() {
/* 201 */         return this.uri;
/*     */       } }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\StreamWriterData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */