/*     */ package com.sun.xml.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.CharArrayArray;
/*     */ import com.sun.xml.fastinfoset.util.ContiguousCharArrayArray;
/*     */ import com.sun.xml.fastinfoset.util.PrefixArray;
/*     */ import com.sun.xml.fastinfoset.util.QualifiedNameArray;
/*     */ import com.sun.xml.fastinfoset.util.StringArray;
/*     */ import com.sun.xml.fastinfoset.vocab.ParserVocabulary;
/*     */ import java.io.File;
/*     */ import javax.xml.parsers.SAXParser;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintTable
/*     */ {
/*     */   public static void printVocabulary(ParserVocabulary vocabulary) {
/*  41 */     printArray("Attribute Name Table", vocabulary.attributeName);
/*  42 */     printArray("Attribute Value Table", vocabulary.attributeValue);
/*  43 */     printArray("Character Content Chunk Table", vocabulary.characterContentChunk);
/*  44 */     printArray("Element Name Table", vocabulary.elementName);
/*  45 */     printArray("Local Name Table", vocabulary.localName);
/*  46 */     printArray("Namespace Name Table", vocabulary.namespaceName);
/*  47 */     printArray("Other NCName Table", vocabulary.otherNCName);
/*  48 */     printArray("Other String Table", vocabulary.otherString);
/*  49 */     printArray("Other URI Table", vocabulary.otherURI);
/*  50 */     printArray("Prefix Table", vocabulary.prefix);
/*     */   }
/*     */   
/*     */   public static void printArray(String title, StringArray a) {
/*  54 */     System.out.println(title);
/*     */     
/*  56 */     for (int i = 0; i < a.getSize(); i++) {
/*  57 */       System.out.println("" + (i + 1) + ": " + a.getArray()[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printArray(String title, PrefixArray a) {
/*  62 */     System.out.println(title);
/*     */     
/*  64 */     for (int i = 0; i < a.getSize(); i++) {
/*  65 */       System.out.println("" + (i + 1) + ": " + a.getArray()[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printArray(String title, CharArrayArray a) {
/*  70 */     System.out.println(title);
/*     */     
/*  72 */     for (int i = 0; i < a.getSize(); i++) {
/*  73 */       System.out.println("" + (i + 1) + ": " + a.getArray()[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printArray(String title, ContiguousCharArrayArray a) {
/*  78 */     System.out.println(title);
/*     */     
/*  80 */     for (int i = 0; i < a.getSize(); i++) {
/*  81 */       System.out.println("" + (i + 1) + ": " + a.getString(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void printArray(String title, QualifiedNameArray a) {
/*  86 */     System.out.println(title);
/*     */     
/*  88 */     for (int i = 0; i < a.getSize(); i++) {
/*  89 */       QualifiedName name = a.getArray()[i];
/*  90 */       System.out.println("" + (name.index + 1) + ": " + "{" + name.namespaceName + "}" + name.prefix + ":" + name.localName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*     */     try {
/* 101 */       SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
/* 102 */       saxParserFactory.setNamespaceAware(true);
/*     */       
/* 104 */       SAXParser saxParser = saxParserFactory.newSAXParser();
/*     */       
/* 106 */       ParserVocabulary referencedVocabulary = new ParserVocabulary();
/*     */       
/* 108 */       VocabularyGenerator vocabularyGenerator = new VocabularyGenerator(referencedVocabulary);
/* 109 */       File f = new File(args[0]);
/* 110 */       saxParser.parse(f, vocabularyGenerator);
/*     */       
/* 112 */       printVocabulary(referencedVocabulary);
/* 113 */     } catch (Exception e) {
/* 114 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\PrintTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */