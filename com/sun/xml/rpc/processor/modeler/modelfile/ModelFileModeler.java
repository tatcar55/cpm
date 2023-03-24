/*    */ package com.sun.xml.rpc.processor.modeler.modelfile;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
/*    */ import com.sun.xml.rpc.processor.model.Model;
/*    */ import com.sun.xml.rpc.processor.model.ModelException;
/*    */ import com.sun.xml.rpc.processor.model.exporter.ModelImporter;
/*    */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*    */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*    */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*    */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*    */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
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
/*    */ public class ModelFileModeler
/*    */   implements Modeler
/*    */ {
/*    */   private ModelFileModelInfo _modelInfo;
/*    */   private Properties _options;
/*    */   private LocalizableMessageFactory _messageFactory;
/*    */   private ProcessorEnvironment _env;
/*    */   
/*    */   public ModelFileModeler(ModelFileModelInfo modelInfo, Properties options) {
/* 56 */     this._modelInfo = modelInfo;
/* 57 */     this._options = options;
/* 58 */     this._messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.modeler");
/*    */     
/* 60 */     this._env = (ProcessorEnvironment)modelInfo.getParent().getEnvironment();
/*    */   }
/*    */   
/*    */   public Model buildModel() {
/*    */     try {
/* 65 */       URL url = null;
/*    */       try {
/* 67 */         url = new URL(this._modelInfo.getLocation());
/* 68 */       } catch (MalformedURLException e) {
/* 69 */         url = (new File(this._modelInfo.getLocation())).toURL();
/*    */       } 
/*    */       
/* 72 */       InputStream is = url.openStream();
/* 73 */       ModelImporter im = new ModelImporter(url.openStream());
/* 74 */       Model model = im.doImport();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 80 */       if (model.getSource() != null) {
/* 81 */         this._options.setProperty("sourceVersion", model.getSource());
/*    */       }
/*    */       
/* 84 */       return model;
/* 85 */     } catch (IOException e) {
/* 86 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/* 87 */     } catch (ModelException e) {
/* 88 */       throw new ModelerException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\modelfile\ModelFileModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */