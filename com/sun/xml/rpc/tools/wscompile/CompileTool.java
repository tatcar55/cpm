/*      */ package com.sun.xml.rpc.tools.wscompile;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.Processor;
/*      */ import com.sun.xml.rpc.processor.ProcessorAction;
/*      */ import com.sun.xml.rpc.processor.ProcessorNotificationListener;
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
/*      */ import com.sun.xml.rpc.processor.config.parser.ConfigurationParser;
/*      */ import com.sun.xml.rpc.processor.generator.CustomClassGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.CustomExceptionGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.EnumerationEncoderGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.EnumerationGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.FaultExceptionBuilderGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.HolderGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.InterfaceSerializerGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.LiteralObjectSerializerGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.RemoteInterfaceGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.RemoteInterfaceImplGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.SOAPFaultSerializerGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.SOAPObjectBuilderGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.SOAPObjectSerializerGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.SerializerRegistryGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.ServiceGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.ServiceInterfaceGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.ServletConfigGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.StubGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.TieGenerator;
/*      */ import com.sun.xml.rpc.processor.generator.WSDLGenerator;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.util.ClientProcessorEnvironment;
/*      */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*      */ import com.sun.xml.rpc.processor.util.ModelWriter;
/*      */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.processor.util.XMLModelWriter;
/*      */ import com.sun.xml.rpc.spi.tools.CompileTool;
/*      */ import com.sun.xml.rpc.spi.tools.CompileToolDelegate;
/*      */ import com.sun.xml.rpc.spi.tools.Configuration;
/*      */ import com.sun.xml.rpc.spi.tools.Processor;
/*      */ import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.tools.plugin.ToolPluginFactory;
/*      */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
/*      */ import com.sun.xml.rpc.util.JavaCompilerHelper;
/*      */ import com.sun.xml.rpc.util.ToolBase;
/*      */ import com.sun.xml.rpc.util.VersionUtil;
/*      */ import com.sun.xml.rpc.util.localization.Localizable;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.StringTokenizer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompileTool
/*      */   extends ToolBase
/*      */   implements ProcessorNotificationListener, CompileTool
/*      */ {
/*      */   protected Properties properties;
/*      */   protected ProcessorEnvironment environment;
/*      */   protected Configuration configuration;
/*      */   protected Processor processor;
/*      */   protected ProcessorNotificationListener listener;
/*      */   protected Map actions;
/*      */   protected CompileToolDelegate delegate;
/*      */   protected File configFile;
/*      */   protected File modelFile;
/*      */   protected File sourceDir;
/*      */   protected File destDir;
/*      */   protected File nonclassDestDir;
/*      */   protected File debugModelFile;
/*      */   protected int mode;
/*      */   protected boolean doNothing;
/*      */   protected boolean compilerDebug;
/*      */   protected boolean compilerOptimize;
/*      */   protected boolean verbose;
/*      */   protected boolean noDataBinding;
/*      */   protected boolean noEncodedTypes;
/*      */   protected boolean noMultiRefEncoding;
/*      */   protected boolean noValidation;
/*      */   protected boolean explicitServiceContext;
/*      */   protected boolean printStackTrace;
/*      */   protected boolean keepGenerated;
/*      */   
/*      */   public CompileTool(OutputStream out, String program) {
/*  101 */     super(out, program);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1175 */     this.delegate = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     this.mode = 0;
/* 1184 */     this.doNothing = false;
/* 1185 */     this.compilerDebug = false;
/* 1186 */     this.compilerOptimize = false;
/* 1187 */     this.verbose = false;
/* 1188 */     this.noDataBinding = false;
/* 1189 */     this.noEncodedTypes = false;
/* 1190 */     this.noMultiRefEncoding = false;
/* 1191 */     this.noValidation = false;
/* 1192 */     this.explicitServiceContext = false;
/* 1193 */     this.printStackTrace = false;
/* 1194 */     this.keepGenerated = false;
/* 1195 */     this.serializable = false;
/* 1196 */     this.donotOverride = false;
/* 1197 */     this.serializeInterfaces = false;
/* 1198 */     this.searchSchemaForSubtypes = false;
/* 1199 */     this.useDataHandlerOnly = false;
/* 1200 */     this.dontGenerateRPCStructures = false;
/* 1201 */     this.useDocLiteralEncoding = false;
/* 1202 */     this.useRPCLiteralEncoding = false;
/* 1203 */     this.useWSIBasicProfile = false;
/* 1204 */     this.generateOneWayMethods = false;
/* 1205 */     this.resolveIDREF = false;
/* 1206 */     this.strictCompliance = false;
/* 1207 */     this.jaxbEnumType = false;
/* 1208 */     this.unwrapDocLitWrappers = false;
/* 1209 */     this.wrapperFlagSeen = false;
/* 1210 */     this.dontGenerateWrapperClasses = false;
/* 1211 */     this.targetVersion = null;
/*      */     
/* 1213 */     this.serializerInfix = null;
/*      */     
/* 1215 */     this.userClasspath = null;
/*      */     this.listener = this;
/*      */   }
/*      */   
/*      */   protected boolean serializable;
/*      */   protected boolean donotOverride;
/*      */   protected boolean serializeInterfaces;
/*      */   protected boolean searchSchemaForSubtypes;
/*      */   protected boolean useDataHandlerOnly;
/*      */   protected boolean dontGenerateRPCStructures;
/*      */   protected boolean useDocLiteralEncoding;
/*      */   protected boolean useRPCLiteralEncoding;
/*      */   protected boolean useWSIBasicProfile;
/*      */   protected boolean generateOneWayMethods;
/*      */   protected boolean resolveIDREF;
/*      */   protected boolean strictCompliance;
/*      */   protected boolean jaxbEnumType;
/*      */   protected boolean unwrapDocLitWrappers;
/*      */   protected boolean wrapperFlagSeen;
/*      */   protected boolean dontGenerateWrapperClasses;
/*      */   protected String targetVersion;
/*      */   protected String serializerInfix;
/*      */   protected String userClasspath;
/*      */   protected static final int MODE_UNSPECIFIED = 0;
/*      */   protected static final int MODE_IMPORT = 2;
/*      */   protected static final int MODE_DEFINE = 3;
/*      */   protected static final int MODE_GEN_CLIENT = 4;
/*      */   protected static final int MODE_GEN_SERVER = 5;
/*      */   protected static final int MODE_GEN_BOTH = 6;
/*      */   
/*      */   protected void initialize() {
/*      */     super.initialize();
/*      */     this.properties = new Properties();
/*      */     this.actions = new HashMap<Object, Object>();
/*      */     this.actions.put("remote.interface.generator", new RemoteInterfaceGenerator());
/*      */     this.actions.put("remote.interface.impl.generator", new RemoteInterfaceImplGenerator());
/*      */     this.actions.put("custom.class.generator", new CustomClassGenerator());
/*      */     this.actions.put("soap.object.serializer.generator", new SOAPObjectSerializerGenerator());
/*      */     this.actions.put("interface.serializer.generator", new InterfaceSerializerGenerator());
/*      */     this.actions.put("soap.object.builder.generator", new SOAPObjectBuilderGenerator());
/*      */     this.actions.put("literal.object.serializer.generator", new LiteralObjectSerializerGenerator());
/*      */     this.actions.put("stub.generator", new StubGenerator());
/*      */     this.actions.put("tie.generator", new TieGenerator());
/*      */     this.actions.put("servlet.config.generator", new ServletConfigGenerator());
/*      */     this.actions.put("wsdl.generator", new WSDLGenerator());
/*      */     this.actions.put("holder.generator", new HolderGenerator());
/*      */     this.actions.put("service.interface.generator", new ServiceInterfaceGenerator());
/*      */     this.actions.put("service.generator", new ServiceGenerator());
/*      */     this.actions.put("serializer.registry.generator", new SerializerRegistryGenerator());
/*      */     this.actions.put("custom.exception.generator", new CustomExceptionGenerator());
/*      */     this.actions.put("soap.fault.serializer.generator", new SOAPFaultSerializerGenerator());
/*      */     this.actions.put("enumeration.generator", new EnumerationGenerator());
/*      */     this.actions.put("enumeration.encoder.generator", new EnumerationEncoderGenerator());
/*      */     this.actions.put("fault.exception.builder.generator", new FaultExceptionBuilderGenerator());
/*      */   }
/*      */   
/*      */   public ProcessorEnvironment getEnvironment() {
/*      */     return (ProcessorEnvironment)this.environment;
/*      */   }
/*      */   
/*      */   public Processor getProcessor() {
/*      */     return (Processor)this.processor;
/*      */   }
/*      */   
/*      */   public void setDelegate(CompileToolDelegate delegate) {
/*      */     this.delegate = delegate;
/*      */   }
/*      */   
/*      */   protected boolean parseArguments(String[] args) {
/*      */     String debugModelFileName = null;
/*      */     String modelFileName = null;
/*      */     for (int i = 0; i < args.length; i++) {
/*      */       if (args[i].equals("")) {
/*      */         args[i] = null;
/*      */       } else if (args[i].equals("-g")) {
/*      */         this.compilerDebug = true;
/*      */         args[i] = null;
/*      */       } else if (args[i].equals("-O")) {
/*      */         this.compilerOptimize = true;
/*      */         args[i] = null;
/*      */       } else if (args[i].equals("-verbose")) {
/*      */         this.verbose = true;
/*      */         args[i] = null;
/*      */       } else if (args[i].equals("-import")) {
/*      */         if (this.mode != 0) {
/*      */           onError(getMessage("wscompile.tooManyModesSpecified"));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         this.mode = 2;
/*      */         this.dontGenerateWrapperClasses = true;
/*      */         args[i] = null;
/*      */       } else if (args[i].equals("-define")) {
/*      */         if (this.mode != 0) {
/*      */           onError(getMessage("wscompile.tooManyModesSpecified"));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         this.mode = 3;
/*      */         args[i] = null;
/*      */       } else if (args[i].equals("-source")) {
/*      */         if (i + 1 < args.length) {
/*      */           if (this.targetVersion != null) {
/*      */             onError(getMessage("wscompile.duplicateOption", "-source"));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */           args[i] = null;
/*      */           this.targetVersion = new String(args[++i]);
/*      */           args[i] = null;
/*      */         } else {
/*      */           onError(getMessage("wscompile.missingOptionArgument", "-source"));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         if (this.targetVersion.length() == 0) {
/*      */           onError(getMessage("wscompile.invalidOption", args[i]));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         if (!VersionUtil.isValidVersion(this.targetVersion)) {
/*      */           onError(getMessage("wscompile.invalidTargetVersion", this.targetVersion));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */       } else if (args[i].startsWith("-gen")) {
/*      */         if (this.mode != 0) {
/*      */           onError(getMessage("wscompile.tooManyModesSpecified"));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         if (args[i].equals("-gen") || args[i].equals("-gen:client")) {
/*      */           this.mode = 4;
/*      */           args[i] = null;
/*      */         } else if (args[i].equals("-gen:server")) {
/*      */           this.mode = 5;
/*      */           args[i] = null;
/*      */         } else if (args[i].equals("-gen:both")) {
/*      */           this.mode = 6;
/*      */           args[i] = null;
/*      */         } else {
/*      */           onError(getMessage("wscompile.invalidOption", args[i]));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */       } else {
/*      */         if (args[i].equals("-version")) {
/*      */           report(getVersion());
/*      */           this.doNothing = true;
/*      */           args[i] = null;
/*      */           return true;
/*      */         } 
/*      */         if (args[i].equals("-keep")) {
/*      */           this.keepGenerated = true;
/*      */           args[i] = null;
/*      */         } else if (args[i].equals("-d")) {
/*      */           if (i + 1 < args.length) {
/*      */             if (this.destDir != null) {
/*      */               onError(getMessage("wscompile.duplicateOption", "-d"));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */             args[i] = null;
/*      */             this.destDir = new File(args[++i]);
/*      */             args[i] = null;
/*      */             if (!this.destDir.exists()) {
/*      */               onError(getMessage("wscompile.noSuchDirectory", this.destDir.getPath()));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */           } else {
/*      */             onError(getMessage("wscompile.missingOptionArgument", "-d"));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */         } else if (args[i].equals("-nd")) {
/*      */           if (i + 1 < args.length) {
/*      */             if (this.nonclassDestDir != null) {
/*      */               onError(getMessage("wscompile.duplicateOption", "-nd"));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */             args[i] = null;
/*      */             this.nonclassDestDir = new File(args[++i]);
/*      */             args[i] = null;
/*      */             if (!this.nonclassDestDir.exists()) {
/*      */               onError(getMessage("wscompile.noSuchDirectory", this.nonclassDestDir.getPath()));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */           } else {
/*      */             onError(getMessage("wscompile.missingOptionArgument", "-nd"));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */         } else if (args[i].equals("-s")) {
/*      */           if (i + 1 < args.length) {
/*      */             if (this.sourceDir != null) {
/*      */               onError(getMessage("wscompile.duplicateOption", "-s"));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */             args[i] = null;
/*      */             this.sourceDir = new File(args[++i]);
/*      */             args[i] = null;
/*      */             if (!this.sourceDir.exists()) {
/*      */               onError(getMessage("wscompile.noSuchDirectory", this.sourceDir.getPath()));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */           } else {
/*      */             onError(getMessage("wscompile.missingOptionArgument", "-s"));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */         } else if (args[i].equals("-model")) {
/*      */           if (i + 1 < args.length) {
/*      */             if (this.modelFile != null) {
/*      */               onError(getMessage("wscompile.duplicateOption", "-model"));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */             args[i] = null;
/*      */             modelFileName = args[++i];
/*      */             args[i] = null;
/*      */           } else {
/*      */             onError(getMessage("wscompile.missingOptionArgument", "-model"));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */         } else if (args[i].equals("-classpath") || args[i].equals("-cp")) {
/*      */           if (i + 1 < args.length) {
/*      */             if (this.userClasspath != null) {
/*      */               onError(getMessage("wscompile.duplicateOption", args[i]));
/*      */               usage();
/*      */               return false;
/*      */             } 
/*      */             args[i] = null;
/*      */             this.userClasspath = args[++i];
/*      */             args[i] = null;
/*      */           } 
/*      */         } else if (args[i].startsWith("-f:") || args[i].startsWith("-features:")) {
/*      */           String featureString = args[i].substring(args[i].startsWith("-f:") ? 3 : 10);
/*      */           StringTokenizer tokenizer = new StringTokenizer(featureString, ",");
/*      */           while (tokenizer.hasMoreTokens()) {
/*      */             String feature = tokenizer.nextToken().trim();
/*      */             if (feature.equals("datahandleronly")) {
/*      */               this.useDataHandlerOnly = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("nodatabinding")) {
/*      */               this.noDataBinding = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("noencodedtypes")) {
/*      */               this.noEncodedTypes = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("nomultirefs")) {
/*      */               this.noMultiRefEncoding = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("novalidation")) {
/*      */               this.noValidation = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("explicitcontext")) {
/*      */               this.explicitServiceContext = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("norpcstructures")) {
/*      */               this.dontGenerateRPCStructures = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.startsWith("infix=") || feature.startsWith("infix:")) {
/*      */               String value = feature.substring(6);
/*      */               if (value.length() == 0) {
/*      */                 onError(getMessage("wscompile.invalidFeatureSyntax", "infix"));
/*      */                 usage();
/*      */                 return false;
/*      */               } 
/*      */               this.serializerInfix = value;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("searchschema")) {
/*      */               this.searchSchemaForSubtypes = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("serializeinterfaces")) {
/*      */               this.serializeInterfaces = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("documentliteral")) {
/*      */               this.useDocLiteralEncoding = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("rpcliteral")) {
/*      */               this.useRPCLiteralEncoding = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("wsi")) {
/*      */               this.useWSIBasicProfile = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("useonewayoperations")) {
/*      */               this.generateOneWayMethods = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("resolveidref")) {
/*      */               this.resolveIDREF = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("strict")) {
/*      */               this.strictCompliance = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("jaxbenumtype")) {
/*      */               this.jaxbEnumType = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("unwrap")) {
/*      */               if (this.wrapperFlagSeen && !this.unwrapDocLitWrappers)
/*      */                 onError(getMessage("wscompile.bothWrapperFlags")); 
/*      */               this.wrapperFlagSeen = true;
/*      */               this.unwrapDocLitWrappers = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("donotoverride")) {
/*      */               this.donotOverride = true;
/*      */               continue;
/*      */             } 
/*      */             if (feature.equals("donotunwrap")) {
/*      */               if (this.wrapperFlagSeen && this.unwrapDocLitWrappers)
/*      */                 onError(getMessage("wscompile.bothWrapperFlags")); 
/*      */               this.wrapperFlagSeen = true;
/*      */               this.unwrapDocLitWrappers = false;
/*      */               continue;
/*      */             } 
/*      */             onError(getMessage("wscompile.unknownFeature", feature));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */           args[i] = null;
/*      */         } else if (args[i].startsWith("-httpproxy:")) {
/*      */           String value = args[i].substring(11);
/*      */           if (value.length() == 0) {
/*      */             onError(getMessage("wscompile.invalidOption", args[i]));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */           int index = value.indexOf(':');
/*      */           if (index == -1) {
/*      */             System.setProperty("proxySet", "true");
/*      */             System.setProperty("proxyHost", value);
/*      */             System.setProperty("proxyPort", "8080");
/*      */           } else {
/*      */             System.setProperty("proxySet", "true");
/*      */             System.setProperty("proxyHost", value.substring(0, index));
/*      */             System.setProperty("proxyPort", value.substring(index + 1));
/*      */           } 
/*      */           args[i] = null;
/*      */         } else if (args[i].equals("-Xprintstacktrace")) {
/*      */           this.printStackTrace = true;
/*      */           args[i] = null;
/*      */         } else if (args[i].equals("-Xserializable")) {
/*      */           this.serializable = true;
/*      */           args[i] = null;
/*      */         } else if (args[i].startsWith("-Xdebugmodel")) {
/*      */           int index = args[i].indexOf(':');
/*      */           if (index == -1) {
/*      */             onError(getMessage("wscompile.invalidOption", args[i]));
/*      */             usage();
/*      */             return false;
/*      */           } 
/*      */           debugModelFileName = args[i].substring(index + 1);
/*      */           args[i] = null;
/*      */         } else if (args[i].startsWith("-help")) {
/*      */           help();
/*      */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     if (modelFileName != null) {
/*      */       this.modelFile = new File(modelFileName);
/*      */       if (this.modelFile.isDirectory() || (this.modelFile.getParentFile() != null && !this.modelFile.getParentFile().exists())) {
/*      */         onError(getMessage("wscompile.invalidModel", this.modelFile.getPath()));
/*      */         usage();
/*      */         return false;
/*      */       } 
/*      */     } 
/*      */     if (debugModelFileName != null) {
/*      */       if (this.nonclassDestDir != null)
/*      */         debugModelFileName = this.nonclassDestDir + System.getProperty("file.separator") + debugModelFileName; 
/*      */       this.debugModelFile = new File(debugModelFileName);
/*      */       if (this.debugModelFile.isDirectory() || (this.debugModelFile.getParentFile() != null && !this.debugModelFile.getParentFile().exists())) {
/*      */         onError(getMessage("wscompile.invalidPath", this.debugModelFile.getPath()));
/*      */         usage();
/*      */         return false;
/*      */       } 
/*      */     } 
/*      */     Iterator<UsageIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.usage");
/*      */     while (iter != null && iter.hasNext()) {
/*      */       UsageIf plugin = iter.next();
/*      */       UsageIf.UsageError error = new UsageIf.UsageError();
/*      */       if (!plugin.parseArguments(args, error)) {
/*      */         onError(error.msg);
/*      */         usage();
/*      */         return false;
/*      */       } 
/*      */     } 
/*      */     for (int j = 0; j < args.length; j++) {
/*      */       if (args[j] != null) {
/*      */         if (args[j].startsWith("-")) {
/*      */           onError(getMessage("wscompile.invalidOption", args[j]));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         if (this.configFile != null) {
/*      */           onError(getMessage("wscompile.multipleConfigurationFiles", args[j]));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */         this.configFile = new File(args[j]);
/*      */         args[j] = null;
/*      */         if (!this.configFile.exists()) {
/*      */           onError(getMessage("wscompile.fileNotFound", this.configFile.getPath()));
/*      */           usage();
/*      */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     if (this.mode == 0) {
/*      */       onError(getMessage("wscompile.noModeSpecified"));
/*      */       usage();
/*      */       return false;
/*      */     } 
/*      */     if (this.mode != 2)
/*      */       this.dontGenerateRPCStructures = false; 
/*      */     if (this.configFile == null) {
/*      */       onError(getMessage("wscompile.missingConfigurationFile"));
/*      */       usage();
/*      */       return false;
/*      */     } 
/*      */     if (this.targetVersion != null)
/*      */       resetOptionsForTargetVersion(); 
/*      */     return checkForConflictingFlags();
/*      */   }
/*      */   
/*      */   protected void resetOptionsForTargetVersion() {
/*      */     ArrayList<String> optionList = null;
/*      */     if (VersionUtil.isVersion101(this.targetVersion) || VersionUtil.isVersion103(this.targetVersion)) {
/*      */       optionList = new ArrayList();
/*      */       if (this.dontGenerateWrapperClasses)
/*      */         this.dontGenerateWrapperClasses = false; 
/*      */       if (this.donotOverride)
/*      */         this.donotOverride = false; 
/*      */       if (this.serializable)
/*      */         this.serializable = false; 
/*      */       if (this.useDocLiteralEncoding) {
/*      */         this.useDocLiteralEncoding = false;
/*      */         optionList.add("documentliteral");
/*      */       } 
/*      */       if (this.useRPCLiteralEncoding) {
/*      */         this.useRPCLiteralEncoding = false;
/*      */         optionList.add("rpcliteral");
/*      */       } 
/*      */       if (this.useWSIBasicProfile) {
/*      */         this.useWSIBasicProfile = false;
/*      */         optionList.add("wsi");
/*      */       } 
/*      */       if (this.jaxbEnumType) {
/*      */         this.jaxbEnumType = false;
/*      */         optionList.add("jaxbenumType");
/*      */       } 
/*      */       if (this.resolveIDREF) {
/*      */         this.resolveIDREF = false;
/*      */         optionList.add("resolveidref");
/*      */       } 
/*      */       if (this.strictCompliance) {
/*      */         this.strictCompliance = false;
/*      */         optionList.add("strict");
/*      */       } 
/*      */       if (this.generateOneWayMethods) {
/*      */         this.generateOneWayMethods = false;
/*      */         optionList.add("useonewayoperations");
/*      */       } 
/*      */       if (VersionUtil.isVersion101(this.targetVersion) && this.dontGenerateRPCStructures) {
/*      */         this.dontGenerateRPCStructures = false;
/*      */         optionList.add("norpcstructures");
/*      */       } 
/*      */     } 
/*      */     if (optionList != null && !optionList.isEmpty()) {
/*      */       StringBuffer str = new StringBuffer();
/*      */       for (Iterator<String> iter = optionList.iterator(); iter.hasNext(); ) {
/*      */         str.append(iter.next());
/*      */         if (iter.hasNext())
/*      */           str.append(", "); 
/*      */       } 
/*      */       onWarning(getMessage("wscompile.conflictingFeature.sourceVersion", new Object[] { this.targetVersion, str.toString() }));
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean checkForConflictingFlags() {
/*      */     if (this.strictCompliance && this.resolveIDREF) {
/*      */       onError(getMessage("wscompile.conflictingFeatureRequest", new Object[] { "resolveIDREF", "strictCompliance" }));
/*      */       return false;
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   public Localizable getVersion() {
/*      */     return getMessage("wscompile.version", "JAX-RPC Standard Implementation", "1.1.3", "R2");
/*      */   }
/*      */   
/*      */   public String getVersionString() {
/*      */     return this.localizer.localize(getVersion());
/*      */   }
/*      */   
/*      */   protected void usage() {
/*      */     report(getMessage("wscompile.usage", this.program));
/*      */   }
/*      */   
/*      */   protected void help() {
/*      */     report(getMessage("wscompile.help", this.program));
/*      */     Iterator<UsageIf> i = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.usage");
/*      */     while (i != null && i.hasNext()) {
/*      */       UsageIf plugin = i.next();
/*      */       report(plugin.getOptionsUsage());
/*      */     } 
/*      */     report(getMessage("wscompile.usage.features"));
/*      */     report(getMessage("wscompile.usage.internal"));
/*      */     report(getMessage("wscompile.usage.examples"));
/*      */   }
/*      */   
/*      */   public void run() throws Exception {
/*      */     if (this.doNothing)
/*      */       return; 
/*      */     try {
/*      */       boolean genClient = (this.mode == 6 || this.mode == 4);
/*      */       beforeHook();
/*      */       this.environment = createEnvironment();
/*      */       if (this.delegate != null)
/*      */         this.configuration = (Configuration)this.delegate.createConfiguration(); 
/*      */       if (this.configuration == null)
/*      */         this.configuration = (Configuration)createConfiguration(); 
/*      */       if (this.targetVersion != null && this.configuration.getModelInfo() instanceof ModelFileModelInfo) {
/*      */         onWarning(getMessage("wscompile.warning.ignoringTargetVersionForModel", ((ModelFileModelInfo)this.configuration.getModelInfo()).getLocation(), this.targetVersion));
/*      */         this.targetVersion = null;
/*      */         this.properties.setProperty("sourceVersion", getSourceVersion());
/*      */       } 
/*      */       JAXRPCClassFactory.newInstance().setSourceVersion(this.properties.getProperty("sourceVersion"));
/*      */       setEnvironmentValues(this.environment);
/*      */       if (genClient && this.configuration.getModelInfo() instanceof com.sun.xml.rpc.processor.config.RmiModelInfo)
/*      */         onWarning(getMessage("wscompile.warning.seimode")); 
/*      */       this.processor = new Processor(this.configuration, this.properties);
/*      */       this.processor.runModeler();
/*      */       if (genClient) {
/*      */         Model model = (Model)this.processor.getModel();
/*      */         if (model != null && this.configuration.getModelInfo() instanceof ModelFileModelInfo) {
/*      */           String modelerName = (String)model.getProperty("com.sun.xml.rpc.processor.model.ModelerName");
/*      */           if (modelerName != null && modelerName.equals("com.sun.xml.rpc.processor.modeler.rmi.RmiModeler"))
/*      */             onWarning(getMessage("wscompile.warning.modelfilemode")); 
/*      */         } 
/*      */       } 
/*      */       withModelHook();
/*      */       registerProcessorActions(this.processor);
/*      */       this.processor.runActions();
/*      */       if (this.environment.getErrorCount() == 0)
/*      */         compileGeneratedClasses(); 
/*      */       afterHook();
/*      */     } finally {
/*      */       if (!this.keepGenerated)
/*      */         removeGeneratedFiles(); 
/*      */       if (this.environment != null)
/*      */         this.environment.shutdown(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean wasSuccessful() {
/*      */     return (this.environment == null || this.environment.getErrorCount() == 0);
/*      */   }
/*      */   
/*      */   protected String getGenericErrorMessage() {
/*      */     return "wscompile.error";
/*      */   }
/*      */   
/*      */   protected String getResourceBundleName() {
/*      */     return "com.sun.xml.rpc.resources.wscompile";
/*      */   }
/*      */   
/*      */   public void printStackTrace(Throwable t) {
/*      */     if (this.printStackTrace)
/*      */       if (this.environment != null) {
/*      */         this.environment.printStackTrace(t);
/*      */       } else {
/*      */         super.printStackTrace(t);
/*      */       }  
/*      */   }
/*      */   
/*      */   protected void beforeHook() {
/*      */     if (this.destDir == null)
/*      */       this.destDir = new File("."); 
/*      */     if (this.sourceDir == null)
/*      */       this.sourceDir = this.destDir; 
/*      */     if (this.nonclassDestDir == null)
/*      */       this.nonclassDestDir = this.destDir; 
/*      */     this.properties.setProperty("JAXRPC Version", getVersionString());
/*      */     this.properties.setProperty("sourceDirectory", this.sourceDir.getAbsolutePath());
/*      */     this.properties.setProperty("destinationDirectory", this.destDir.getAbsolutePath());
/*      */     this.properties.setProperty("nonclassDestinationDirectory", this.nonclassDestDir.getAbsolutePath());
/*      */     this.properties.setProperty("encodeTypes", this.noEncodedTypes ? "false" : "true");
/*      */     this.properties.setProperty("multiRefEncoding", this.noMultiRefEncoding ? "false" : "true");
/*      */     this.properties.setProperty("validationWSDL", this.noValidation ? "false" : "true");
/*      */     this.properties.setProperty("explicitServiceContext", this.explicitServiceContext ? "true" : "false");
/*      */     this.properties.setProperty("printStackTrace", this.printStackTrace ? "true" : "false");
/*      */     this.properties.setProperty("noDataBinding", this.noDataBinding ? "true" : "false");
/*      */     this.properties.setProperty("serializerInterfaces", this.serializeInterfaces ? "true" : "false");
/*      */     this.properties.setProperty("useDataHandlerOnly", this.useDataHandlerOnly ? "true" : "false");
/*      */     this.properties.setProperty("searchSchemaForSubtypes", this.searchSchemaForSubtypes ? "true" : "false");
/*      */     this.properties.setProperty("dontGenerateRPCStructures", this.dontGenerateRPCStructures ? "true" : "false");
/*      */     this.properties.setProperty("useDocumentLiteralEncoding", this.useDocLiteralEncoding ? "true" : "false");
/*      */     this.properties.setProperty("useRPCLiteralEncoding", this.useRPCLiteralEncoding ? "true" : "false");
/*      */     this.properties.setProperty("useWSIBasicProfile", this.useWSIBasicProfile ? "true" : "false");
/*      */     this.properties.setProperty("generateOneWayOperations", this.generateOneWayMethods ? "true" : "false");
/*      */     this.properties.setProperty("resolveIDREF", this.resolveIDREF ? "true" : "false");
/*      */     this.properties.setProperty("strictCompliance", this.strictCompliance ? "true" : "false");
/*      */     this.properties.setProperty("jaxbenum", this.jaxbEnumType ? "true" : "false");
/*      */     this.properties.setProperty("sourceVersion", getSourceVersion());
/*      */     this.properties.setProperty("unwrapDocLitWrappers", this.unwrapDocLitWrappers ? "true" : "false");
/*      */     this.properties.setProperty("dontGenerateWrapperClasses", (this.dontGenerateWrapperClasses && (this.strictCompliance || this.useWSIBasicProfile)) ? "true" : "false");
/*      */     this.properties.setProperty("serializable", this.serializable ? "true" : "false");
/*      */     this.properties.setProperty("donotOverride", this.donotOverride ? "true" : "false");
/*      */   }
/*      */   
/*      */   protected String getSourceVersion() {
/*      */     if (this.targetVersion == null)
/*      */       return "1.1.3"; 
/*      */     return this.targetVersion;
/*      */   }
/*      */   
/*      */   protected void withModelHook() {
/*      */     Model model = (Model)this.processor.getModel();
/*      */     Iterator<ModelIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.model");
/*      */     while (iter != null && iter.hasNext()) {
/*      */       ModelIf plugin = iter.next();
/*      */       ModelIf.ModelProperty property = new ModelIf.ModelProperty();
/*      */       plugin.updateModel(property);
/*      */       if (property.attr != null)
/*      */         model.setProperty(property.attr, property.value); 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void afterHook() {
/*      */     if (this.delegate != null)
/*      */       this.delegate.postRun(); 
/*      */   }
/*      */   
/*      */   public void removeGeneratedFiles() {
/*      */     this.environment.deleteGeneratedFiles();
/*      */   }
/*      */   
/*      */   protected ProcessorEnvironment createEnvironment() throws Exception {
/*      */     String cpath = this.userClasspath + File.pathSeparator + System.getProperty("java.class.path");
/*      */     return (ProcessorEnvironment)new ClientProcessorEnvironment(System.out, cpath, this.listener);
/*      */   }
/*      */   
/*      */   protected void setEnvironmentValues(ProcessorEnvironment env) {
/*      */     ((ClientProcessorEnvironment)env).setNames(JAXRPCClassFactory.newInstance().createNames());
/*      */     if (this.serializerInfix != null)
/*      */       env.getNames().setSerializerNameInfix(this.serializerInfix); 
/*      */     int envFlags = env.getFlags();
/*      */     envFlags |= 0x2;
/*      */     if (this.verbose)
/*      */       envFlags |= 0x1; 
/*      */     env.setFlags(envFlags);
/*      */   }
/*      */   
/*      */   protected Configuration createConfiguration() throws Exception {
/*      */     FileInputStream ins = new FileInputStream(this.configFile);
/*      */     ConfigurationParser parser = new ConfigurationParser(this.environment);
/*      */     return parser.parse(ins);
/*      */   }
/*      */   
/*      */   protected void registerProcessorActions(Processor processor) {
/*      */     if (this.modelFile != null)
/*      */       try {
/*      */         processor.add((ProcessorAction)new XMLModelWriter(this.modelFile));
/*      */       } catch (FileNotFoundException e) {
/*      */         this.environment.error(getMessage("wscompile.invalidModel", this.modelFile.getPath()));
/*      */       }  
/*      */     if (this.debugModelFile != null)
/*      */       try {
/*      */         processor.add((ProcessorAction)new ModelWriter(this.debugModelFile));
/*      */       } catch (FileNotFoundException e) {
/*      */         this.environment.error(getMessage("wscompile.invalidPath", this.debugModelFile.getPath()));
/*      */       }  
/*      */     Iterator<ProcessorActionsIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.processorAction");
/*      */     while (iter != null && iter.hasNext()) {
/*      */       ProcessorActionsIf plugin = iter.next();
/*      */       plugin.registerActions(processor);
/*      */     } 
/*      */     boolean genStub = false;
/*      */     boolean genService = false;
/*      */     boolean genServiceInterface = false;
/*      */     boolean genTie = false;
/*      */     boolean genWsdl = false;
/*      */     boolean genSerializer = false;
/*      */     boolean genInterface = false;
/*      */     boolean genInterfaceTemplate = false;
/*      */     boolean genCustomClasses = false;
/*      */     if (this.mode == 4 || this.mode == 6) {
/*      */       genStub = true;
/*      */       genService = true;
/*      */       genServiceInterface = true;
/*      */       genInterface = true;
/*      */       genCustomClasses = true;
/*      */       genSerializer = true;
/*      */     } 
/*      */     if (this.mode == 5 || this.mode == 6) {
/*      */       genTie = true;
/*      */       genInterface = true;
/*      */       genCustomClasses = true;
/*      */       genSerializer = true;
/*      */       genWsdl = true;
/*      */     } 
/*      */     if (this.mode == 2) {
/*      */       if (!(this.configuration.getModelInfo() instanceof com.sun.xml.rpc.processor.config.WSDLModelInfo))
/*      */         this.environment.error(getMessage("wscompile.importRequiresWsdlConfig")); 
/*      */       genInterface = true;
/*      */       genInterfaceTemplate = true;
/*      */       genServiceInterface = true;
/*      */       genCustomClasses = true;
/*      */     } 
/*      */     if (this.mode == 3) {
/*      */       if (!(this.configuration.getModelInfo() instanceof com.sun.xml.rpc.processor.config.RmiModelInfo))
/*      */         this.environment.error(getMessage("wscompile.defineRequiresServiceConfig")); 
/*      */       genWsdl = true;
/*      */     } 
/*      */     if (processor.getModel() != null)
/*      */       if (this.configuration.getModelInfo() instanceof com.sun.xml.rpc.processor.config.RmiModelInfo) {
/*      */         genInterface = false;
/*      */       } else if (this.configuration.getModelInfo() instanceof com.sun.xml.rpc.processor.config.WSDLModelInfo) {
/*      */         genWsdl = false;
/*      */       } else if (this.configuration.getModelInfo() instanceof com.sun.xml.rpc.processor.config.NoMetadataModelInfo) {
/*      */         genInterface = false;
/*      */         genWsdl = false;
/*      */       }  
/*      */     if (genServiceInterface)
/*      */       processor.add(getAction("service.interface.generator")); 
/*      */     if (genService)
/*      */       processor.add(getAction("service.generator")); 
/*      */     if (genInterface) {
/*      */       processor.add(getAction("remote.interface.generator"));
/*      */       processor.add(getAction("enumeration.generator"));
/*      */       processor.add(getAction("custom.exception.generator"));
/*      */       processor.add(getAction("holder.generator"));
/*      */     } 
/*      */     if (genCustomClasses)
/*      */       processor.add(getAction("custom.class.generator")); 
/*      */     if (genInterfaceTemplate)
/*      */       processor.add(getAction("remote.interface.impl.generator")); 
/*      */     if (genSerializer) {
/*      */       processor.add(getAction("enumeration.encoder.generator"));
/*      */       processor.add(getAction("interface.serializer.generator"));
/*      */       processor.add(getAction("soap.object.serializer.generator"));
/*      */       processor.add(getAction("soap.object.builder.generator"));
/*      */       processor.add(getAction("literal.object.serializer.generator"));
/*      */       processor.add(getAction("soap.fault.serializer.generator"));
/*      */       processor.add(getAction("fault.exception.builder.generator"));
/*      */     } 
/*      */     if (genStub)
/*      */       processor.add(getAction("stub.generator")); 
/*      */     if (genTie)
/*      */       processor.add(getAction("tie.generator")); 
/*      */     if (genSerializer)
/*      */       processor.add(getAction("serializer.registry.generator")); 
/*      */     if (genWsdl)
/*      */       processor.add(getAction("wsdl.generator")); 
/*      */     if (this.delegate != null)
/*      */       this.delegate.postRegisterProcessorActions(); 
/*      */   }
/*      */   
/*      */   protected String createClasspathString() {
/*      */     if (this.userClasspath == null)
/*      */       this.userClasspath = ""; 
/*      */     return this.userClasspath + File.pathSeparator + System.getProperty("java.class.path");
/*      */   }
/*      */   
/*      */   protected void compileGeneratedClasses() {
/*      */     List<String> sourceFiles = new ArrayList();
/*      */     for (Iterator<GeneratedFileInfo> iter = this.environment.getGeneratedFiles(); iter.hasNext(); ) {
/*      */       GeneratedFileInfo fileInfo = iter.next();
/*      */       File f = fileInfo.getFile();
/*      */       if (f.exists() && f.getName().endsWith(".java"))
/*      */         sourceFiles.add(f.getAbsolutePath()); 
/*      */     } 
/*      */     if (sourceFiles.size() > 0) {
/*      */       String classDir = this.destDir.getAbsolutePath();
/*      */       String classpathString = createClasspathString();
/*      */       String[] args = new String[4 + ((this.compilerDebug == true) ? 1 : 0) + ((this.compilerOptimize == true) ? 1 : 0) + sourceFiles.size()];
/*      */       args[0] = "-d";
/*      */       args[1] = classDir;
/*      */       args[2] = "-classpath";
/*      */       args[3] = classpathString;
/*      */       int baseIndex = 4;
/*      */       if (this.compilerDebug)
/*      */         args[baseIndex++] = "-g"; 
/*      */       if (this.compilerOptimize)
/*      */         args[baseIndex++] = "-O"; 
/*      */       for (int i = 0; i < sourceFiles.size(); i++)
/*      */         args[baseIndex + i] = sourceFiles.get(i); 
/*      */       JavaCompilerHelper compilerHelper = new JavaCompilerHelper(this.out);
/*      */       boolean result = compilerHelper.compile(args);
/*      */       if (!result)
/*      */         this.environment.error(getMessage("wscompile.compilationFailed")); 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected ProcessorAction getAction(String name) {
/*      */     return (ProcessorAction)this.actions.get(name);
/*      */   }
/*      */   
/*      */   public void onError(Localizable msg) {
/*      */     if (this.delegate != null)
/*      */       this.delegate.preOnError(); 
/*      */     report(getMessage("wscompile.error", this.localizer.localize(msg)));
/*      */   }
/*      */   
/*      */   public void onWarning(Localizable msg) {
/*      */     report(getMessage("wscompile.warning", this.localizer.localize(msg)));
/*      */   }
/*      */   
/*      */   public void onInfo(Localizable msg) {
/*      */     report(getMessage("wscompile.info", this.localizer.localize(msg)));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wscompile\CompileTool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */