/*     */ package com.sun.xml.bind.v2.bytecode;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassTailor
/*     */ {
/*  62 */   private static final Logger logger = Util.getClassLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toVMClassName(Class c) {
/*  68 */     assert !c.isPrimitive();
/*  69 */     if (c.isArray())
/*     */     {
/*  71 */       return toVMTypeName(c); } 
/*  72 */     return c.getName().replace('.', '/');
/*     */   }
/*     */   
/*     */   public static String toVMTypeName(Class<boolean> c) {
/*  76 */     if (c.isArray())
/*     */     {
/*  78 */       return '[' + toVMTypeName(c.getComponentType());
/*     */     }
/*  80 */     if (c.isPrimitive()) {
/*  81 */       if (c == boolean.class) return "Z"; 
/*  82 */       if (c == char.class) return "C"; 
/*  83 */       if (c == byte.class) return "B"; 
/*  84 */       if (c == double.class) return "D"; 
/*  85 */       if (c == float.class) return "F"; 
/*  86 */       if (c == int.class) return "I"; 
/*  87 */       if (c == long.class) return "J"; 
/*  88 */       if (c == short.class) return "S";
/*     */       
/*  90 */       throw new IllegalArgumentException(c.getName());
/*     */     } 
/*  92 */     return 'L' + c.getName().replace('.', '/') + ';';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] tailor(Class templateClass, String newClassName, String... replacements) {
/*  98 */     String vmname = toVMClassName(templateClass);
/*  99 */     return tailor(SecureLoader.getClassClassLoader(templateClass).getResourceAsStream(vmname + ".class"), vmname, newClassName, replacements);
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
/*     */   public static byte[] tailor(InputStream image, String templateClassName, String newClassName, String... replacements) {
/* 118 */     DataInputStream in = new DataInputStream(image);
/*     */     
/*     */     try {
/* 121 */       ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
/* 122 */       DataOutputStream out = new DataOutputStream(baos);
/*     */ 
/*     */       
/* 125 */       long l = in.readLong();
/* 126 */       out.writeLong(l);
/*     */ 
/*     */       
/* 129 */       short count = in.readShort();
/* 130 */       out.writeShort(count);
/*     */ 
/*     */       
/* 133 */       for (int i = 0; i < count; i++) {
/* 134 */         String value; byte tag = in.readByte();
/* 135 */         out.writeByte(tag);
/* 136 */         switch (tag) {
/*     */           case 0:
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/* 146 */             value = in.readUTF();
/* 147 */             if (value.equals(templateClassName)) {
/* 148 */               value = newClassName;
/*     */             } else {
/* 150 */               for (int j = 0; j < replacements.length; j += 2) {
/* 151 */                 if (value.equals(replacements[j])) {
/* 152 */                   value = replacements[j + 1]; break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 156 */             out.writeUTF(value);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/*     */           case 4:
/* 162 */             out.writeInt(in.readInt());
/*     */             break;
/*     */           
/*     */           case 5:
/*     */           case 6:
/* 167 */             i++;
/* 168 */             out.writeLong(in.readLong());
/*     */             break;
/*     */           
/*     */           case 7:
/*     */           case 8:
/* 173 */             out.writeShort(in.readShort());
/*     */             break;
/*     */           
/*     */           case 9:
/*     */           case 10:
/*     */           case 11:
/*     */           case 12:
/* 180 */             out.writeInt(in.readInt());
/*     */             break;
/*     */           
/*     */           default:
/* 184 */             throw new IllegalArgumentException("Unknown constant type " + tag);
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 189 */       byte[] buf = new byte[512];
/*     */       int len;
/* 191 */       while ((len = in.read(buf)) > 0) {
/* 192 */         out.write(buf, 0, len);
/*     */       }
/* 194 */       in.close();
/* 195 */       out.close();
/*     */ 
/*     */       
/* 198 */       return baos.toByteArray();
/*     */     }
/* 200 */     catch (IOException e) {
/*     */       
/* 202 */       logger.log(Level.WARNING, "failed to tailor", e);
/* 203 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\bytecode\ClassTailor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */