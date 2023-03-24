/*    */ package com.beust.jcommander.internal;
/*    */ 
/*    */ import com.beust.jcommander.IStringConverter;
/*    */ import com.beust.jcommander.IStringConverterFactory;
/*    */ import com.beust.jcommander.converters.BigDecimalConverter;
/*    */ import com.beust.jcommander.converters.BooleanConverter;
/*    */ import com.beust.jcommander.converters.DoubleConverter;
/*    */ import com.beust.jcommander.converters.FileConverter;
/*    */ import com.beust.jcommander.converters.FloatConverter;
/*    */ import com.beust.jcommander.converters.ISO8601DateConverter;
/*    */ import com.beust.jcommander.converters.IntegerConverter;
/*    */ import com.beust.jcommander.converters.LongConverter;
/*    */ import com.beust.jcommander.converters.PathConverter;
/*    */ import com.beust.jcommander.converters.StringConverter;
/*    */ import com.beust.jcommander.converters.URIConverter;
/*    */ import com.beust.jcommander.converters.URLConverter;
/*    */ import java.io.File;
/*    */ import java.math.BigDecimal;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultConverterFactory
/*    */   implements IStringConverterFactory
/*    */ {
/* 51 */   private static Map<Class, Class<? extends IStringConverter<?>>> m_classConverters = Maps.newHashMap(); static {
/* 52 */     m_classConverters.put(String.class, StringConverter.class);
/* 53 */     m_classConverters.put(Integer.class, IntegerConverter.class);
/* 54 */     m_classConverters.put(int.class, IntegerConverter.class);
/* 55 */     m_classConverters.put(Long.class, LongConverter.class);
/* 56 */     m_classConverters.put(long.class, LongConverter.class);
/* 57 */     m_classConverters.put(Float.class, FloatConverter.class);
/* 58 */     m_classConverters.put(float.class, FloatConverter.class);
/* 59 */     m_classConverters.put(Double.class, DoubleConverter.class);
/* 60 */     m_classConverters.put(double.class, DoubleConverter.class);
/* 61 */     m_classConverters.put(Boolean.class, BooleanConverter.class);
/* 62 */     m_classConverters.put(boolean.class, BooleanConverter.class);
/* 63 */     m_classConverters.put(File.class, FileConverter.class);
/* 64 */     m_classConverters.put(BigDecimal.class, BigDecimalConverter.class);
/* 65 */     m_classConverters.put(Date.class, ISO8601DateConverter.class);
/* 66 */     m_classConverters.put(Path.class, PathConverter.class);
/* 67 */     m_classConverters.put(URI.class, URIConverter.class);
/* 68 */     m_classConverters.put(URL.class, URLConverter.class);
/*    */   }
/*    */   
/*    */   public Class<? extends IStringConverter<?>> getConverter(Class forType) {
/* 72 */     return m_classConverters.get(forType);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\internal\DefaultConverterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */