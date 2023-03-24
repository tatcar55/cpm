/*    */ package com.sun.xml.rpc.processor.config.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
/*    */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*    */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
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
/*    */ public class ModelFileModelInfoParser
/*    */   extends ModelInfoParser
/*    */ {
/*    */   public ModelFileModelInfoParser(ProcessorEnvironment env) {
/* 41 */     super(env);
/*    */   }
/*    */   
/*    */   public ModelInfo parse(XMLReader reader) {
/* 45 */     ModelFileModelInfo modelInfo = new ModelFileModelInfo();
/* 46 */     String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*    */     
/* 48 */     modelInfo.setLocation(location);
/* 49 */     return (ModelInfo)modelInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\ModelFileModelInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */