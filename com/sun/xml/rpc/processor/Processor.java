/*     */ package com.sun.xml.rpc.processor;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.spi.model.Model;
/*     */ import com.sun.xml.rpc.spi.tools.Processor;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Processor
/*     */   implements Processor
/*     */ {
/*     */   private Properties _options;
/*     */   private Configuration _configuration;
/*     */   private List _actions;
/*     */   private Model _model;
/*     */   private boolean _printStackTrace;
/*     */   private ProcessorEnvironment _env;
/*     */   
/*     */   public Processor(Configuration configuration, Properties options) {
/*  48 */     this._configuration = configuration;
/*  49 */     this._options = options;
/*  50 */     this._actions = new ArrayList();
/*     */ 
/*     */     
/*  53 */     this._printStackTrace = Boolean.valueOf(this._options.getProperty("printStackTrace")).booleanValue();
/*     */     
/*  55 */     this._env = (ProcessorEnvironment)this._configuration.getEnvironment();
/*     */   }
/*     */   
/*     */   public void add(ProcessorAction action) {
/*  59 */     this._actions.add(action);
/*     */   }
/*     */   
/*     */   public Model getModel() {
/*  63 */     return (Model)this._model;
/*     */   }
/*     */   
/*     */   public void run() {
/*  67 */     runModeler();
/*  68 */     if (this._model != null) {
/*  69 */       runActions();
/*     */     }
/*     */   }
/*     */   
/*     */   public void runModeler() {
/*     */     try {
/*  75 */       ModelInfo modelInfo = (ModelInfo)this._configuration.getModelInfo();
/*  76 */       if (modelInfo == null) {
/*  77 */         throw new ProcessorException("processor.missing.model");
/*     */       }
/*     */       
/*  80 */       this._model = modelInfo.buildModel(this._options);
/*     */     }
/*  82 */     catch (JAXRPCExceptionBase e) {
/*  83 */       if (this._printStackTrace) {
/*  84 */         this._env.printStackTrace((Throwable)e);
/*     */       }
/*  86 */       this._env.error((Localizable)e);
/*  87 */     } catch (Exception e) {
/*  88 */       if (this._printStackTrace) {
/*  89 */         this._env.printStackTrace(e);
/*     */       }
/*  91 */       this._env.error((Localizable)new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void runActions() {
/*     */     try {
/*  97 */       if (this._model == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 103 */       for (Iterator<ProcessorAction> iter = this._actions.iterator(); iter.hasNext(); ) {
/* 104 */         ProcessorAction action = iter.next();
/* 105 */         action.perform(this._model, this._configuration, this._options);
/*     */       } 
/* 107 */     } catch (JAXRPCExceptionBase e) {
/* 108 */       if (this._printStackTrace) {
/* 109 */         this._env.printStackTrace((Throwable)e);
/*     */       }
/* 111 */       this._env.error((Localizable)e);
/* 112 */     } catch (Exception e) {
/* 113 */       if (this._printStackTrace) {
/* 114 */         this._env.printStackTrace(e);
/*     */       }
/* 116 */       this._env.error((Localizable)new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\Processor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */