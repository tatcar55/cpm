/*     */ package javax.xml.soap;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
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
/*     */ public class MimeHeaders
/*     */ {
/*  66 */   private Vector headers = new Vector();
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
/*     */   public String[] getHeader(String name) {
/*  79 */     Vector<String> values = new Vector();
/*     */     
/*  81 */     for (int i = 0; i < this.headers.size(); i++) {
/*  82 */       MimeHeader hdr = this.headers.elementAt(i);
/*  83 */       if (hdr.getName().equalsIgnoreCase(name) && hdr.getValue() != null)
/*     */       {
/*  85 */         values.addElement(hdr.getValue());
/*     */       }
/*     */     } 
/*  88 */     if (values.size() == 0) {
/*  89 */       return null;
/*     */     }
/*  91 */     String[] r = new String[values.size()];
/*  92 */     values.copyInto((Object[])r);
/*  93 */     return r;
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
/*     */   public void setHeader(String name, String value) {
/* 114 */     boolean found = false;
/*     */     
/* 116 */     if (name == null || name.equals("")) {
/* 117 */       throw new IllegalArgumentException("Illegal MimeHeader name");
/*     */     }
/* 119 */     for (int i = 0; i < this.headers.size(); i++) {
/* 120 */       MimeHeader hdr = this.headers.elementAt(i);
/* 121 */       if (hdr.getName().equalsIgnoreCase(name)) {
/* 122 */         if (!found) {
/* 123 */           this.headers.setElementAt(new MimeHeader(hdr.getName(), value), i);
/*     */           
/* 125 */           found = true;
/*     */         } else {
/*     */           
/* 128 */           this.headers.removeElementAt(i--);
/*     */         } 
/*     */       }
/*     */     } 
/* 132 */     if (!found) {
/* 133 */       addHeader(name, value);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addHeader(String name, String value) {
/* 152 */     if (name == null || name.equals("")) {
/* 153 */       throw new IllegalArgumentException("Illegal MimeHeader name");
/*     */     }
/* 155 */     int pos = this.headers.size();
/*     */     
/* 157 */     for (int i = pos - 1; i >= 0; i--) {
/* 158 */       MimeHeader hdr = this.headers.elementAt(i);
/* 159 */       if (hdr.getName().equalsIgnoreCase(name)) {
/* 160 */         this.headers.insertElementAt(new MimeHeader(name, value), i + 1);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 165 */     this.headers.addElement(new MimeHeader(name, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHeader(String name) {
/* 176 */     for (int i = 0; i < this.headers.size(); i++) {
/* 177 */       MimeHeader hdr = this.headers.elementAt(i);
/* 178 */       if (hdr.getName().equalsIgnoreCase(name)) {
/* 179 */         this.headers.removeElementAt(i--);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllHeaders() {
/* 187 */     this.headers.removeAllElements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getAllHeaders() {
/* 198 */     return this.headers.iterator();
/*     */   }
/*     */   
/*     */   class MatchingIterator implements Iterator {
/*     */     private boolean match;
/*     */     private Iterator iterator;
/*     */     private String[] names;
/*     */     private Object nextHeader;
/*     */     
/*     */     MatchingIterator(String[] names, boolean match) {
/* 208 */       this.match = match;
/* 209 */       this.names = names;
/* 210 */       this.iterator = MimeHeaders.this.headers.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     private Object nextMatch() {
/* 215 */       label21: while (this.iterator.hasNext()) {
/* 216 */         MimeHeader hdr = this.iterator.next();
/*     */         
/* 218 */         if (this.names == null) {
/* 219 */           return this.match ? null : hdr;
/*     */         }
/* 221 */         for (int i = 0; i < this.names.length; i++) {
/* 222 */           if (hdr.getName().equalsIgnoreCase(this.names[i])) {
/* 223 */             if (this.match)
/* 224 */               return hdr;  continue label21;
/*     */           } 
/*     */         } 
/* 227 */         if (!this.match)
/* 228 */           return hdr; 
/*     */       } 
/* 230 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 235 */       if (this.nextHeader == null)
/* 236 */         this.nextHeader = nextMatch(); 
/* 237 */       return (this.nextHeader != null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object next() {
/* 243 */       if (this.nextHeader != null) {
/* 244 */         Object ret = this.nextHeader;
/* 245 */         this.nextHeader = null;
/* 246 */         return ret;
/*     */       } 
/* 248 */       if (hasNext())
/* 249 */         return this.nextHeader; 
/* 250 */       return null;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 254 */       this.iterator.remove();
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
/*     */   
/*     */   public Iterator getMatchingHeaders(String[] names) {
/* 269 */     return new MatchingIterator(names, true);
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
/*     */   public Iterator getNonMatchingHeaders(String[] names) {
/* 282 */     return new MatchingIterator(names, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javax\xml\soap\MimeHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */