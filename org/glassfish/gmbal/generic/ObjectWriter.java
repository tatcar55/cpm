/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ObjectWriter
/*     */ {
/*     */   protected StringBuffer result;
/*     */   
/*     */   public static ObjectWriter make(boolean isIndenting, int initialLevel, int increment) {
/*  49 */     if (isIndenting) {
/*  50 */       return new IndentingObjectWriter(initialLevel, increment);
/*     */     }
/*  52 */     return new SimpleObjectWriter();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void startObject(String paramString);
/*     */   
/*     */   public abstract void startObject(Object paramObject);
/*     */   
/*     */   public abstract void startElement();
/*     */   
/*     */   public abstract void endElement();
/*     */   
/*     */   public abstract void endObject();
/*     */   
/*     */   public String toString() {
/*  67 */     return this.result.toString();
/*     */   } public void append(boolean arg) {
/*  69 */     this.result.append(arg);
/*     */   } public void append(char arg) {
/*  71 */     this.result.append(arg);
/*     */   } public void append(short arg) {
/*  73 */     this.result.append(arg);
/*     */   } public void append(int arg) {
/*  75 */     this.result.append(arg);
/*     */   } public void append(long arg) {
/*  77 */     this.result.append(arg);
/*     */   } public void append(float arg) {
/*  79 */     this.result.append(arg);
/*     */   } public void append(double arg) {
/*  81 */     this.result.append(arg);
/*     */   } public void append(String arg) {
/*  83 */     this.result.append(arg);
/*     */   } public void append(Object arg) {
/*  85 */     this.result.append(arg.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ObjectWriter() {
/*  95 */     this.result = new StringBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendObjectHeader(Object obj) {
/* 100 */     this.result.append(obj.getClass().getName());
/* 101 */     this.result.append("<");
/* 102 */     this.result.append(System.identityHashCode(obj));
/* 103 */     this.result.append(">");
/* 104 */     Class<?> compClass = obj.getClass().getComponentType();
/*     */     
/* 106 */     if (compClass != null) {
/* 107 */       this.result.append("[");
/* 108 */       if (compClass == boolean.class) {
/* 109 */         boolean[] arr = (boolean[])obj;
/* 110 */         this.result.append(arr.length);
/* 111 */         this.result.append("]");
/* 112 */       } else if (compClass == byte.class) {
/* 113 */         byte[] arr = (byte[])obj;
/* 114 */         this.result.append(arr.length);
/* 115 */         this.result.append("]");
/* 116 */       } else if (compClass == short.class) {
/* 117 */         short[] arr = (short[])obj;
/* 118 */         this.result.append(arr.length);
/* 119 */         this.result.append("]");
/* 120 */       } else if (compClass == int.class) {
/* 121 */         int[] arr = (int[])obj;
/* 122 */         this.result.append(arr.length);
/* 123 */         this.result.append("]");
/* 124 */       } else if (compClass == long.class) {
/* 125 */         long[] arr = (long[])obj;
/* 126 */         this.result.append(arr.length);
/* 127 */         this.result.append("]");
/* 128 */       } else if (compClass == char.class) {
/* 129 */         char[] arr = (char[])obj;
/* 130 */         this.result.append(arr.length);
/* 131 */         this.result.append("]");
/* 132 */       } else if (compClass == float.class) {
/* 133 */         float[] arr = (float[])obj;
/* 134 */         this.result.append(arr.length);
/* 135 */         this.result.append("]");
/* 136 */       } else if (compClass == double.class) {
/* 137 */         double[] arr = (double[])obj;
/* 138 */         this.result.append(arr.length);
/* 139 */         this.result.append("]");
/*     */       } else {
/* 141 */         Object[] arr = (Object[])obj;
/* 142 */         this.result.append(arr.length);
/* 143 */         this.result.append("]");
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     this.result.append("(");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class IndentingObjectWriter
/*     */     extends ObjectWriter
/*     */   {
/*     */     private int level;
/*     */ 
/*     */     
/*     */     private int increment;
/*     */ 
/*     */ 
/*     */     
/*     */     public IndentingObjectWriter(int initialLevel, int increment) {
/* 164 */       this.level = initialLevel;
/* 165 */       this.increment = increment;
/* 166 */       startLine();
/*     */     }
/*     */ 
/*     */     
/*     */     private void startLine() {
/* 171 */       char[] fill = new char[this.level * this.increment];
/* 172 */       Arrays.fill(fill, ' ');
/* 173 */       this.result.append(fill);
/*     */     }
/*     */     
/*     */     public void startObject(String str) {
/* 177 */       append(str);
/* 178 */       append("(");
/* 179 */       this.level++;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startObject(Object obj) {
/* 184 */       appendObjectHeader(obj);
/* 185 */       this.level++;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startElement() {
/* 190 */       this.result.append("\n");
/* 191 */       startLine();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void endElement() {}
/*     */ 
/*     */     
/*     */     public void endObject() {
/* 200 */       this.level--;
/* 201 */       this.result.append(")");
/* 202 */       this.result.append("\n");
/* 203 */       startLine();
/*     */     } }
/*     */   
/*     */   private static class SimpleObjectWriter extends ObjectWriter { private SimpleObjectWriter() {}
/*     */     
/*     */     public void startObject(String str) {
/* 209 */       append(str);
/* 210 */       append("(");
/*     */     }
/*     */ 
/*     */     
/*     */     public void startObject(Object obj) {
/* 215 */       appendObjectHeader(obj);
/* 216 */       this.result.append(" ");
/*     */     }
/*     */ 
/*     */     
/*     */     public void startElement() {
/* 221 */       this.result.append(" ");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void endElement() {}
/*     */ 
/*     */     
/*     */     public void endObject() {
/* 230 */       this.result.append(")");
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\ObjectWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */