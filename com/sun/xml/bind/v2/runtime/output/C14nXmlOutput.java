/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class C14nXmlOutput
/*     */   extends UTF8XmlOutput
/*     */ {
/*     */   private StaticAttribute[] staticAttributes;
/*     */   private int len;
/*     */   private int[] nsBuf;
/*     */   private final FinalArrayList<DynamicAttribute> otherAttributes;
/*     */   private final boolean namedAttributesAreOrdered;
/*     */   
/*     */   public C14nXmlOutput(OutputStream out, Encoded[] localNames, boolean namedAttributesAreOrdered, CharacterEscapeHandler escapeHandler) {
/*  61 */     super(out, localNames, escapeHandler);
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
/*  73 */     this.staticAttributes = new StaticAttribute[8];
/*  74 */     this.len = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     this.nsBuf = new int[8];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.otherAttributes = new FinalArrayList();
/*     */     this.namedAttributesAreOrdered = namedAttributesAreOrdered;
/*     */     for (int i = 0; i < this.staticAttributes.length; i++) {
/*     */       this.staticAttributes[i] = new StaticAttribute();
/*     */     }
/*     */   }
/*     */   
/*     */   final class StaticAttribute
/*     */     implements Comparable<StaticAttribute>
/*     */   {
/*     */     Name name;
/*     */     String value;
/*     */     
/*     */     public void set(Name name, String value) {
/* 102 */       this.name = name;
/* 103 */       this.value = value;
/*     */     }
/*     */     
/*     */     void write() throws IOException {
/* 107 */       C14nXmlOutput.this.attribute(this.name, this.value);
/*     */     }
/*     */     
/*     */     C14nXmlOutput.DynamicAttribute toDynamicAttribute() {
/* 111 */       int prefix, nsUriIndex = this.name.nsUriIndex;
/*     */       
/* 113 */       if (nsUriIndex == -1) {
/* 114 */         prefix = -1;
/*     */       } else {
/* 116 */         prefix = C14nXmlOutput.this.nsUriIndex2prefixIndex[nsUriIndex];
/* 117 */       }  return new C14nXmlOutput.DynamicAttribute(prefix, this.name.localName, this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(StaticAttribute that) {
/* 122 */       return this.name.compareTo(that.name);
/*     */     }
/*     */   }
/*     */   
/*     */   final class DynamicAttribute
/*     */     implements Comparable<DynamicAttribute> {
/*     */     final int prefix;
/*     */     final String localName;
/*     */     final String value;
/*     */     
/*     */     public DynamicAttribute(int prefix, String localName, String value) {
/* 133 */       this.prefix = prefix;
/* 134 */       this.localName = localName;
/* 135 */       this.value = value;
/*     */     }
/*     */     
/*     */     private String getURI() {
/* 139 */       if (this.prefix == -1) return ""; 
/* 140 */       return C14nXmlOutput.this.nsContext.getNamespaceURI(this.prefix);
/*     */     }
/*     */     
/*     */     public int compareTo(DynamicAttribute that) {
/* 144 */       int r = getURI().compareTo(that.getURI());
/* 145 */       if (r != 0) return r; 
/* 146 */       return this.localName.compareTo(that.localName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException {
/* 152 */     if (this.staticAttributes.length == this.len) {
/*     */       
/* 154 */       int newLen = this.len * 2;
/* 155 */       StaticAttribute[] newbuf = new StaticAttribute[newLen];
/* 156 */       System.arraycopy(this.staticAttributes, 0, newbuf, 0, this.len);
/* 157 */       for (int i = this.len; i < newLen; i++)
/* 158 */         this.staticAttributes[i] = new StaticAttribute(); 
/* 159 */       this.staticAttributes = newbuf;
/*     */     } 
/*     */     
/* 162 */     this.staticAttributes[this.len++].set(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException {
/* 167 */     this.otherAttributes.add(new DynamicAttribute(prefix, localName, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException {
/* 172 */     if (this.otherAttributes.isEmpty()) {
/* 173 */       if (this.len != 0)
/*     */       {
/*     */         
/* 176 */         if (!this.namedAttributesAreOrdered) {
/* 177 */           Arrays.sort((Object[])this.staticAttributes, 0, this.len);
/*     */         }
/* 179 */         for (int i = 0; i < this.len; i++)
/* 180 */           this.staticAttributes[i].write(); 
/* 181 */         this.len = 0;
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 187 */       for (int i = 0; i < this.len; i++)
/* 188 */         this.otherAttributes.add(this.staticAttributes[i].toDynamicAttribute()); 
/* 189 */       this.len = 0;
/* 190 */       Collections.sort((List<DynamicAttribute>)this.otherAttributes);
/*     */ 
/*     */       
/* 193 */       int size = this.otherAttributes.size();
/* 194 */       for (int j = 0; j < size; j++) {
/* 195 */         DynamicAttribute a = (DynamicAttribute)this.otherAttributes.get(j);
/* 196 */         super.attribute(a.prefix, a.localName, a.value);
/*     */       } 
/* 198 */       this.otherAttributes.clear();
/*     */     } 
/* 200 */     super.endStartTag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeNsDecls(int base) throws IOException {
/* 208 */     int count = this.nsContext.getCurrent().count();
/*     */     
/* 210 */     if (count == 0) {
/*     */       return;
/*     */     }
/* 213 */     if (count > this.nsBuf.length)
/* 214 */       this.nsBuf = new int[count]; 
/*     */     int i;
/* 216 */     for (i = count - 1; i >= 0; i--) {
/* 217 */       this.nsBuf[i] = base + i;
/*     */     }
/*     */ 
/*     */     
/* 221 */     for (i = 0; i < count; i++) {
/* 222 */       for (int j = i + 1; j < count; j++) {
/* 223 */         String p = this.nsContext.getPrefix(this.nsBuf[i]);
/* 224 */         String q = this.nsContext.getPrefix(this.nsBuf[j]);
/* 225 */         if (p.compareTo(q) > 0) {
/*     */           
/* 227 */           int t = this.nsBuf[j];
/* 228 */           this.nsBuf[j] = this.nsBuf[i];
/* 229 */           this.nsBuf[i] = t;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 235 */     for (i = 0; i < count; i++)
/* 236 */       writeNsDecl(this.nsBuf[i]); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\C14nXmlOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */