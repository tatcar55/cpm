/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.generator.Names101;
/*     */ import com.sun.xml.rpc.processor.generator.Names103;
/*     */ import com.sun.xml.rpc.processor.generator.Names11;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreator101;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreator103;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreator11;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreatorBase;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer101;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer103;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer11;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer111;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler101;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler103;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler11;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler111;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler112;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase;
/*     */ import com.sun.xml.rpc.processor.schema.InternalSchemaBuilder101;
/*     */ import com.sun.xml.rpc.processor.schema.InternalSchemaBuilder103;
/*     */ import com.sun.xml.rpc.processor.schema.InternalSchemaBuilder11;
/*     */ import com.sun.xml.rpc.processor.schema.InternalSchemaBuilderBase;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class JAXRPCClassFactory
/*     */ {
/*  68 */   private static final JAXRPCClassFactory factory = new JAXRPCClassFactory();
/*     */   
/*  70 */   private static String classVersion = "1.1.3";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JAXRPCClassFactory newInstance() {
/*  80 */     return factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceVersion(String version) {
/*  89 */     if (version == null) {
/*  90 */       version = "1.1.3";
/*     */     }
/*  92 */     if (VersionUtil.isValidVersion(version))
/*     */     {
/*     */       
/*  95 */       classVersion = version;
/*     */     }
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
/*     */   public SchemaAnalyzerBase createSchemaAnalyzer(AbstractDocument document, ModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/*     */     SchemaAnalyzer111 schemaAnalyzer111;
/* 114 */     SchemaAnalyzerBase schemaAnalyzer = null;
/* 115 */     if (classVersion.equals("1.0.1")) {
/* 116 */       SchemaAnalyzer101 schemaAnalyzer101 = new SchemaAnalyzer101(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 122 */     else if (classVersion.equals("1.0.3")) {
/* 123 */       SchemaAnalyzer103 schemaAnalyzer103 = new SchemaAnalyzer103(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 129 */     else if (classVersion.equals("1.1")) {
/* 130 */       SchemaAnalyzer11 schemaAnalyzer11 = new SchemaAnalyzer11(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 136 */     else if (classVersion.equals("1.1.1")) {
/* 137 */       schemaAnalyzer111 = new SchemaAnalyzer111(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 143 */     else if (classVersion.equals("1.1.2")) {
/* 144 */       schemaAnalyzer111 = new SchemaAnalyzer111(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 150 */     else if (classVersion.equals("1.1.3")) {
/* 151 */       schemaAnalyzer111 = new SchemaAnalyzer111(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     return (SchemaAnalyzerBase)schemaAnalyzer111;
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
/*     */   public InternalSchemaBuilderBase createInternalSchemaBuilder(AbstractDocument document, Properties options) {
/*     */     InternalSchemaBuilder11 internalSchemaBuilder11;
/* 176 */     InternalSchemaBuilderBase internalSchemaBuilder = null;
/* 177 */     if (classVersion.equals("1.0.1")) {
/* 178 */       InternalSchemaBuilder101 internalSchemaBuilder101 = new InternalSchemaBuilder101(document, options);
/*     */     
/*     */     }
/* 181 */     else if (classVersion.equals("1.0.3")) {
/* 182 */       InternalSchemaBuilder103 internalSchemaBuilder103 = new InternalSchemaBuilder103(document, options);
/*     */     
/*     */     }
/* 185 */     else if (classVersion.equals("1.1") || classVersion.equals("1.1.1") || classVersion.equals("1.1.2") || classVersion.equals("1.1.3")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 190 */       internalSchemaBuilder11 = new InternalSchemaBuilder11(document, options);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     return (InternalSchemaBuilderBase)internalSchemaBuilder11;
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
/*     */   public WSDLModelerBase createWSDLModeler(WSDLModelInfo modelInfo, Properties options) {
/*     */     WSDLModeler112 wSDLModeler112;
/* 209 */     WSDLModelerBase wsdlModeler = null;
/* 210 */     if (classVersion.equals("1.0.1")) {
/* 211 */       WSDLModeler101 wSDLModeler101 = new WSDLModeler101(modelInfo, options);
/*     */     }
/* 213 */     else if (classVersion.equals("1.0.3")) {
/* 214 */       WSDLModeler103 wSDLModeler103 = new WSDLModeler103(modelInfo, options);
/*     */     }
/* 216 */     else if (classVersion.equals("1.1")) {
/* 217 */       WSDLModeler11 wSDLModeler11 = new WSDLModeler11(modelInfo, options);
/*     */     }
/* 219 */     else if (classVersion.equals("1.1.1")) {
/*     */       
/* 221 */       WSDLModeler111 wSDLModeler111 = new WSDLModeler111(modelInfo, options);
/*     */     }
/* 223 */     else if (classVersion.equals("1.1.2") || classVersion.equals("1.1.3")) {
/*     */ 
/*     */       
/* 226 */       wSDLModeler112 = new WSDLModeler112(modelInfo, options);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 231 */     return (WSDLModelerBase)wSDLModeler112;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Names createNames() {
/*     */     Names11 names11;
/* 240 */     Names names = null;
/* 241 */     if (classVersion.equals("1.0.1")) {
/* 242 */       Names101 names101 = new Names101();
/* 243 */     } else if (classVersion.equals("1.0.3")) {
/* 244 */       Names103 names103 = new Names103();
/* 245 */     } else if (classVersion.equals("1.1") || classVersion.equals("1.1.1") || classVersion.equals("1.1.2") || classVersion.equals("1.1.3")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 250 */       names11 = new Names11();
/*     */     } 
/*     */ 
/*     */     
/* 254 */     return (Names)names11;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase createSOAPSimpleTypeCreator() {
/*     */     SOAPSimpleTypeCreator11 sOAPSimpleTypeCreator11;
/* 263 */     SOAPSimpleTypeCreatorBase soapType = null;
/* 264 */     if (classVersion.equals("1.0.1")) {
/* 265 */       SOAPSimpleTypeCreator101 sOAPSimpleTypeCreator101 = new SOAPSimpleTypeCreator101();
/*     */     }
/* 267 */     else if (classVersion.equals("1.0.3")) {
/* 268 */       SOAPSimpleTypeCreator103 sOAPSimpleTypeCreator103 = new SOAPSimpleTypeCreator103();
/*     */     }
/* 270 */     else if (classVersion.equals("1.1") || classVersion.equals("1.1.1") || classVersion.equals("1.1.2") || classVersion.equals("1.1.3")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       sOAPSimpleTypeCreator11 = new SOAPSimpleTypeCreator11();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 280 */     return (SOAPSimpleTypeCreatorBase)sOAPSimpleTypeCreator11;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase createSOAPSimpleTypeCreator(boolean useStrictMode) {
/*     */     SOAPSimpleTypeCreator11 sOAPSimpleTypeCreator11;
/* 289 */     SOAPSimpleTypeCreatorBase soapType = null;
/* 290 */     if (classVersion.equals("1.0.1")) {
/* 291 */       SOAPSimpleTypeCreator101 sOAPSimpleTypeCreator101 = new SOAPSimpleTypeCreator101(useStrictMode);
/*     */     }
/* 293 */     else if (classVersion.equals("1.0.3")) {
/* 294 */       SOAPSimpleTypeCreator103 sOAPSimpleTypeCreator103 = new SOAPSimpleTypeCreator103(useStrictMode);
/*     */     }
/* 296 */     else if (classVersion.equals("1.1") || classVersion.equals("1.1.1") || classVersion.equals("1.1.2") || classVersion.equals("1.1.3")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       sOAPSimpleTypeCreator11 = new SOAPSimpleTypeCreator11(useStrictMode);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 306 */     return (SOAPSimpleTypeCreatorBase)sOAPSimpleTypeCreator11;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase createSOAPSimpleTypeCreator(boolean useStrictMode, SOAPVersion version) {
/*     */     SOAPSimpleTypeCreator11 sOAPSimpleTypeCreator11;
/* 317 */     SOAPSimpleTypeCreatorBase soapType = null;
/* 318 */     if (classVersion.equals("1.0.1")) {
/* 319 */       SOAPSimpleTypeCreator101 sOAPSimpleTypeCreator101 = new SOAPSimpleTypeCreator101(useStrictMode, version);
/*     */     
/*     */     }
/* 322 */     else if (classVersion.equals("1.0.3")) {
/* 323 */       SOAPSimpleTypeCreator103 sOAPSimpleTypeCreator103 = new SOAPSimpleTypeCreator103(useStrictMode, version);
/*     */     
/*     */     }
/* 326 */     else if (classVersion.equals("1.1") || classVersion.equals("1.1.1") || classVersion.equals("1.1.2") || classVersion.equals("1.1.3")) {
/*     */ 
/*     */ 
/*     */       
/* 330 */       sOAPSimpleTypeCreator11 = new SOAPSimpleTypeCreator11(useStrictMode, version);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     return (SOAPSimpleTypeCreatorBase)sOAPSimpleTypeCreator11;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 340 */     return classVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\JAXRPCClassFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */