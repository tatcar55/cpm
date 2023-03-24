/*     */ package com.sun.xml.rpc.processor.model;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public abstract class Message
/*     */   extends ModelObject
/*     */ {
/*     */   public void addBodyBlock(Block b) {
/*  42 */     if (this._bodyBlocks.containsKey(b.getName())) {
/*  43 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  45 */     this._bodyBlocks.put(b.getName(), b);
/*  46 */     b.setLocation(1);
/*     */   }
/*     */   
/*     */   public Iterator getBodyBlocks() {
/*  50 */     return this._bodyBlocks.values().iterator();
/*     */   }
/*     */   
/*     */   public int getBodyBlockCount() {
/*  54 */     return this._bodyBlocks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getBodyBlocksMap() {
/*  59 */     return this._bodyBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBodyBlocksMap(Map m) {
/*  64 */     this._bodyBlocks = m;
/*     */   }
/*     */   
/*     */   public boolean isBodyEmpty() {
/*  68 */     return getBodyBlocks().hasNext();
/*     */   }
/*     */   
/*     */   public boolean isBodyEncoded() {
/*  72 */     boolean isEncoded = false;
/*  73 */     for (Iterator<Block> iter = getBodyBlocks(); iter.hasNext(); ) {
/*  74 */       Block bodyBlock = iter.next();
/*  75 */       if (bodyBlock.getType().isSOAPType()) {
/*  76 */         isEncoded = true;
/*     */       }
/*     */     } 
/*  79 */     return isEncoded;
/*     */   }
/*     */   
/*     */   public void addHeaderBlock(Block b) {
/*  83 */     if (this._headerBlocks.containsKey(b.getName())) {
/*  84 */       throw new ModelException("model.uniqueness");
/*     */     }
/*  86 */     this._headerBlocks.put(b.getName(), b);
/*  87 */     b.setLocation(2);
/*     */   }
/*     */   
/*     */   public Iterator getHeaderBlocks() {
/*  91 */     return this._headerBlocks.values().iterator();
/*     */   }
/*     */   
/*     */   public int getHeaderBlockCount() {
/*  95 */     return this._headerBlocks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getHeaderBlocksMap() {
/* 100 */     return this._headerBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHeaderBlocksMap(Map m) {
/* 105 */     this._headerBlocks = m;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAttachmentBlock(Block b) {
/* 110 */     if (this._attachmentBlocks.containsKey(b.getName())) {
/* 111 */       throw new ModelException("model.uniqueness");
/*     */     }
/* 113 */     this._attachmentBlocks.put(b.getName(), b);
/* 114 */     b.setLocation(3);
/*     */   }
/*     */   
/*     */   public Iterator getAttachmentBlocks() {
/* 118 */     return this._attachmentBlocks.values().iterator();
/*     */   }
/*     */   
/*     */   public int getAttachmentBlockCount() {
/* 122 */     return this._attachmentBlocks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getAttachmentBlocksMap() {
/* 127 */     return this._attachmentBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttachmentBlocksMap(Map m) {
/* 132 */     this._attachmentBlocks = m;
/*     */   }
/*     */   
/*     */   public void addParameter(Parameter p) {
/* 136 */     if (this._parametersByName.containsKey(p.getName())) {
/* 137 */       throw new ModelException("model.uniqueness");
/*     */     }
/* 139 */     this._parameters.add(p);
/* 140 */     this._parametersByName.put(p.getName(), p);
/*     */   }
/*     */   
/*     */   public Parameter getParameterByName(String name) {
/* 144 */     if (this._parametersByName.size() != this._parameters.size()) {
/* 145 */       initializeParametersByName();
/*     */     }
/* 147 */     return (Parameter)this._parametersByName.get(name);
/*     */   }
/*     */   
/*     */   public Iterator getParameters() {
/* 151 */     return this._parameters.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public List getParametersList() {
/* 156 */     return this._parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParametersList(List l) {
/* 161 */     this._parameters = l;
/*     */   }
/*     */   
/*     */   private void initializeParametersByName() {
/* 165 */     this._parametersByName = new HashMap<Object, Object>();
/* 166 */     if (this._parameters != null) {
/* 167 */       for (Iterator<Parameter> iter = this._parameters.iterator(); iter.hasNext(); ) {
/* 168 */         Parameter param = iter.next();
/* 169 */         if (param.getName() != null && this._parametersByName.containsKey(param.getName()))
/*     */         {
/*     */           
/* 172 */           throw new ModelException("model.uniqueness");
/*     */         }
/* 174 */         this._parametersByName.put(param.getName(), param);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 179 */   private Map _attachmentBlocks = new HashMap<Object, Object>();
/* 180 */   private Map _bodyBlocks = new HashMap<Object, Object>();
/* 181 */   private Map _headerBlocks = new HashMap<Object, Object>();
/* 182 */   private List _parameters = new ArrayList();
/* 183 */   private Map _parametersByName = new HashMap<Object, Object>();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\Message.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */