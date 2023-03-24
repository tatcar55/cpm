/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.helpers.FilterXMLOutputFactory;
/*     */ import javax.xml.stream.XMLEventWriter;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaxUtilsXMLOutputFactory
/*     */   extends FilterXMLOutputFactory
/*     */ {
/*     */   public static final String INDENTING = "net.java.staxutils.indenting";
/*     */   public static final String INDENT = "net.java.staxutils.indent";
/*     */   public static final String NEW_LINE = "net.java.staxutils.newLine";
/*     */   
/*     */   public StaxUtilsXMLOutputFactory() {}
/*     */   
/*     */   public StaxUtilsXMLOutputFactory(XMLOutputFactory source) {
/*  67 */     super(source);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean indenting = false;
/*  72 */   private String indent = "  ";
/*     */   
/*  74 */   private String newLine = "\n";
/*     */   protected XMLEventWriter filter(XMLEventWriter writer) {
/*     */     IndentingXMLEventWriter indentingXMLEventWriter;
/*  77 */     if (this.indenting) {
/*  78 */       IndentingXMLEventWriter indenter = new IndentingXMLEventWriter(writer);
/*  79 */       indenter.setNewLine(this.newLine);
/*  80 */       indenter.setIndent(this.indent);
/*  81 */       indentingXMLEventWriter = indenter;
/*     */     } 
/*  83 */     return (XMLEventWriter)indentingXMLEventWriter;
/*     */   }
/*     */   protected XMLStreamWriter filter(XMLStreamWriter writer) {
/*     */     IndentingXMLStreamWriter indentingXMLStreamWriter;
/*  87 */     if (this.indenting) {
/*  88 */       IndentingXMLStreamWriter indenter = new IndentingXMLStreamWriter(writer);
/*  89 */       indenter.setNewLine(this.newLine);
/*  90 */       indenter.setIndent(this.indent);
/*  91 */       indentingXMLStreamWriter = indenter;
/*     */     } 
/*  93 */     return (XMLStreamWriter)indentingXMLStreamWriter;
/*     */   }
/*     */   
/*     */   public boolean isPropertySupported(String name) {
/*  97 */     return ("net.java.staxutils.indenting".equals(name) || "net.java.staxutils.indent".equals(name) || "net.java.staxutils.newLine".equals(name) || super.isPropertySupported(name));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws IllegalArgumentException {
/* 102 */     if ("net.java.staxutils.indenting".equals(name)) {
/* 103 */       this.indenting = ((Boolean)value).booleanValue();
/* 104 */     } else if ("net.java.staxutils.indent".equals(name)) {
/* 105 */       this.indent = (String)value;
/* 106 */     } else if ("net.java.staxutils.newLine".equals(name)) {
/* 107 */       this.newLine = (String)value;
/*     */     } else {
/* 109 */       super.setProperty(name, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 114 */     if ("net.java.staxutils.indenting".equals(name))
/* 115 */       return this.indenting ? Boolean.TRUE : Boolean.FALSE; 
/* 116 */     if ("net.java.staxutils.indent".equals(name))
/* 117 */       return this.indent; 
/* 118 */     if ("net.java.staxutils.newLine".equals(name)) {
/* 119 */       return this.newLine;
/*     */     }
/* 121 */     return super.getProperty(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     return super.hashCode() + (this.indenting ? 1 : 0) + hashCode(this.indent) + hashCode(this.newLine);
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 130 */     if (!(o instanceof StaxUtilsXMLOutputFactory))
/* 131 */       return false; 
/* 132 */     StaxUtilsXMLOutputFactory that = (StaxUtilsXMLOutputFactory)o;
/* 133 */     return (super.equals(that) && this.indenting == that.indenting && equals(this.indent, that.indent) && equals(this.newLine, that.newLine));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\StaxUtilsXMLOutputFactory.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */