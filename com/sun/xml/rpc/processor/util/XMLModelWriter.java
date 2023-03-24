/*    */ package com.sun.xml.rpc.processor.util;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.ProcessorAction;
/*    */ import com.sun.xml.rpc.processor.config.Configuration;
/*    */ import com.sun.xml.rpc.processor.model.Model;
/*    */ import com.sun.xml.rpc.processor.model.exporter.ModelExporter;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.util.Properties;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XMLModelWriter
/*    */   implements ProcessorAction
/*    */ {
/*    */   private ModelExporter exporter;
/*    */   
/*    */   public XMLModelWriter(File file) throws FileNotFoundException {
/* 48 */     this.exporter = new ModelExporter(new FileOutputStream(file));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void perform(Model model, Configuration config, Properties options) {
/* 56 */     model.setSource(options.getProperty("sourceVersion"));
/*    */     
/* 58 */     this.exporter.doExport(model);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\XMLModelWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */