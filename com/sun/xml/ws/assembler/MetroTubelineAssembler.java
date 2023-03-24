/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.ws.assembler.dev.TubelineAssemblyDecorator;
/*     */ import com.sun.xml.ws.dump.LoggingDumpTube;
/*     */ import com.sun.xml.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Level;
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
/*     */ public class MetroTubelineAssembler
/*     */   implements TubelineAssembler
/*     */ {
/*     */   private static final String COMMON_MESSAGE_DUMP_SYSTEM_PROPERTY_BASE = "com.sun.metro.soap.dump";
/*  66 */   public static final MetroConfigNameImpl JAXWS_TUBES_CONFIG_NAMES = new MetroConfigNameImpl("jaxws-tubes-default.xml", "jaxws-tubes.xml");
/*     */   
/*     */   private enum Side
/*     */   {
/*  70 */     Client("client"),
/*  71 */     Endpoint("endpoint");
/*     */     private final String name;
/*     */     
/*     */     Side(String name) {
/*  75 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  80 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MessageDumpingInfo
/*     */   {
/*     */     final boolean dumpBefore;
/*     */     final boolean dumpAfter;
/*     */     final Level logLevel;
/*     */     
/*     */     MessageDumpingInfo(boolean dumpBefore, boolean dumpAfter, Level logLevel) {
/*  91 */       this.dumpBefore = dumpBefore;
/*  92 */       this.dumpAfter = dumpAfter;
/*  93 */       this.logLevel = logLevel;
/*     */     }
/*     */   }
/*     */   
/*  97 */   private static final Logger LOGGER = Logger.getLogger(MetroTubelineAssembler.class);
/*     */   private final BindingID bindingId;
/*     */   private final TubelineAssemblyController tubelineAssemblyController;
/*     */   
/*     */   public MetroTubelineAssembler(BindingID bindingId, MetroConfigName metroConfigName) {
/* 102 */     this.bindingId = bindingId;
/* 103 */     this.tubelineAssemblyController = new TubelineAssemblyController(metroConfigName);
/*     */   }
/*     */   
/*     */   TubelineAssemblyController getTubelineAssemblyController() {
/* 107 */     return this.tubelineAssemblyController;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Tube createClient(@NotNull ClientTubeAssemblerContext jaxwsContext) {
/* 112 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 113 */       LOGGER.finer("Assembling client-side tubeline for WS endpoint: " + jaxwsContext.getAddress().getURI().toString());
/*     */     }
/*     */     
/* 116 */     DefaultClientTubelineAssemblyContext context = createClientContext(jaxwsContext);
/*     */     
/* 118 */     Collection<TubeCreator> tubeCreators = this.tubelineAssemblyController.getTubeCreators(context);
/*     */     
/* 120 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 121 */       tubeCreator.updateContext(context);
/*     */     }
/*     */     
/* 124 */     TubelineAssemblyDecorator decorator = TubelineAssemblyDecorator.composite((Iterable)ServiceFinder.find(TubelineAssemblyDecorator.class, (Component)context.getContainer()));
/*     */ 
/*     */     
/* 127 */     boolean first = true;
/* 128 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 129 */       MessageDumpingInfo msgDumpInfo = setupMessageDumping(tubeCreator.getMessageDumpPropertyBase(), Side.Client);
/*     */       
/* 131 */       Tube oldTubelineHead = context.getTubelineHead();
/* 132 */       LoggingDumpTube afterDumpTube = null;
/* 133 */       if (msgDumpInfo.dumpAfter) {
/* 134 */         afterDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.After, context.getTubelineHead());
/* 135 */         context.setTubelineHead((Tube)afterDumpTube);
/*     */       } 
/*     */       
/* 138 */       if (!context.setTubelineHead(decorator.decorateClient(tubeCreator.createTube(context), context))) {
/* 139 */         if (afterDumpTube != null) {
/* 140 */           context.setTubelineHead(oldTubelineHead);
/*     */         }
/*     */       } else {
/* 143 */         String loggedTubeName = context.getTubelineHead().getClass().getName();
/* 144 */         if (afterDumpTube != null) {
/* 145 */           afterDumpTube.setLoggedTubeName(loggedTubeName);
/*     */         }
/*     */         
/* 148 */         if (msgDumpInfo.dumpBefore) {
/* 149 */           LoggingDumpTube beforeDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.Before, context.getTubelineHead());
/* 150 */           beforeDumpTube.setLoggedTubeName(loggedTubeName);
/* 151 */           context.setTubelineHead((Tube)beforeDumpTube);
/*     */         } 
/*     */       } 
/*     */       
/* 155 */       if (first) {
/* 156 */         context.setTubelineHead(decorator.decorateClientTail(context.getTubelineHead(), context));
/* 157 */         first = false;
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     return decorator.decorateClientHead(context.getTubelineHead(), context);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Tube createServer(@NotNull ServerTubeAssemblerContext jaxwsContext) {
/* 166 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 167 */       LOGGER.finer("Assembling endpoint tubeline for WS endpoint: " + jaxwsContext.getEndpoint().getServiceName() + "::" + jaxwsContext.getEndpoint().getPortName());
/*     */     }
/*     */     
/* 170 */     DefaultServerTubelineAssemblyContext context = createServerContext(jaxwsContext);
/*     */ 
/*     */     
/* 173 */     Collection<TubeCreator> tubeCreators = this.tubelineAssemblyController.getTubeCreators(context);
/* 174 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 175 */       tubeCreator.updateContext(context);
/*     */     }
/*     */     
/* 178 */     TubelineAssemblyDecorator decorator = TubelineAssemblyDecorator.composite((Iterable)ServiceFinder.find(TubelineAssemblyDecorator.class, (Component)context.getEndpoint().getContainer()));
/*     */ 
/*     */     
/* 181 */     boolean first = true;
/* 182 */     for (TubeCreator tubeCreator : tubeCreators) {
/* 183 */       MessageDumpingInfo msgDumpInfo = setupMessageDumping(tubeCreator.getMessageDumpPropertyBase(), Side.Endpoint);
/*     */       
/* 185 */       Tube oldTubelineHead = context.getTubelineHead();
/* 186 */       LoggingDumpTube afterDumpTube = null;
/* 187 */       if (msgDumpInfo.dumpAfter) {
/* 188 */         afterDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.After, context.getTubelineHead());
/* 189 */         context.setTubelineHead((Tube)afterDumpTube);
/*     */       } 
/*     */       
/* 192 */       if (!context.setTubelineHead(decorator.decorateServer(tubeCreator.createTube(context), context))) {
/* 193 */         if (afterDumpTube != null) {
/* 194 */           context.setTubelineHead(oldTubelineHead);
/*     */         }
/*     */       } else {
/* 197 */         String loggedTubeName = context.getTubelineHead().getClass().getName();
/* 198 */         if (afterDumpTube != null) {
/* 199 */           afterDumpTube.setLoggedTubeName(loggedTubeName);
/*     */         }
/*     */         
/* 202 */         if (msgDumpInfo.dumpBefore) {
/* 203 */           LoggingDumpTube beforeDumpTube = new LoggingDumpTube(msgDumpInfo.logLevel, LoggingDumpTube.Position.Before, context.getTubelineHead());
/* 204 */           beforeDumpTube.setLoggedTubeName(loggedTubeName);
/* 205 */           context.setTubelineHead((Tube)beforeDumpTube);
/*     */         } 
/*     */       } 
/*     */       
/* 209 */       if (first) {
/* 210 */         context.setTubelineHead(decorator.decorateServerTail(context.getTubelineHead(), context));
/* 211 */         first = false;
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     return decorator.decorateServerHead(context.getTubelineHead(), context);
/*     */   }
/*     */   
/*     */   private MessageDumpingInfo setupMessageDumping(String msgDumpSystemPropertyBase, Side side) {
/* 219 */     boolean dumpBefore = false;
/* 220 */     boolean dumpAfter = false;
/* 221 */     Level logLevel = Level.INFO;
/*     */ 
/*     */     
/* 224 */     Boolean value = getBooleanValue("com.sun.metro.soap.dump");
/* 225 */     if (value != null) {
/* 226 */       dumpBefore = value.booleanValue();
/* 227 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 230 */     value = getBooleanValue("com.sun.metro.soap.dump.before");
/* 231 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 233 */     value = getBooleanValue("com.sun.metro.soap.dump.after");
/* 234 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 236 */     Level levelValue = getLevelValue("com.sun.metro.soap.dump.level");
/* 237 */     if (levelValue != null) {
/* 238 */       logLevel = levelValue;
/*     */     }
/*     */ 
/*     */     
/* 242 */     value = getBooleanValue("com.sun.metro.soap.dump." + side.toString());
/* 243 */     if (value != null) {
/* 244 */       dumpBefore = value.booleanValue();
/* 245 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 248 */     value = getBooleanValue("com.sun.metro.soap.dump." + side.toString() + ".before");
/* 249 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 251 */     value = getBooleanValue("com.sun.metro.soap.dump." + side.toString() + ".after");
/* 252 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 254 */     levelValue = getLevelValue("com.sun.metro.soap.dump." + side.toString() + ".level");
/* 255 */     if (levelValue != null) {
/* 256 */       logLevel = levelValue;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 261 */     value = getBooleanValue(msgDumpSystemPropertyBase);
/* 262 */     if (value != null) {
/* 263 */       dumpBefore = value.booleanValue();
/* 264 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 267 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".before");
/* 268 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 270 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".after");
/* 271 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 273 */     levelValue = getLevelValue(msgDumpSystemPropertyBase + ".level");
/* 274 */     if (levelValue != null) {
/* 275 */       logLevel = levelValue;
/*     */     }
/*     */ 
/*     */     
/* 279 */     msgDumpSystemPropertyBase = msgDumpSystemPropertyBase + "." + side.toString();
/*     */     
/* 281 */     value = getBooleanValue(msgDumpSystemPropertyBase);
/* 282 */     if (value != null) {
/* 283 */       dumpBefore = value.booleanValue();
/* 284 */       dumpAfter = value.booleanValue();
/*     */     } 
/*     */     
/* 287 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".before");
/* 288 */     dumpBefore = (value != null) ? value.booleanValue() : dumpBefore;
/*     */     
/* 290 */     value = getBooleanValue(msgDumpSystemPropertyBase + ".after");
/* 291 */     dumpAfter = (value != null) ? value.booleanValue() : dumpAfter;
/*     */     
/* 293 */     levelValue = getLevelValue(msgDumpSystemPropertyBase + ".level");
/* 294 */     if (levelValue != null) {
/* 295 */       logLevel = levelValue;
/*     */     }
/*     */     
/* 298 */     return new MessageDumpingInfo(dumpBefore, dumpAfter, logLevel);
/*     */   }
/*     */   
/*     */   private Boolean getBooleanValue(String propertyName) {
/* 302 */     Boolean retVal = null;
/*     */     
/* 304 */     String stringValue = System.getProperty(propertyName);
/* 305 */     if (stringValue != null) {
/* 306 */       retVal = Boolean.valueOf(stringValue);
/* 307 */       LOGGER.fine(TubelineassemblyMessages.MASM_0018_MSG_LOGGING_SYSTEM_PROPERTY_SET_TO_VALUE(propertyName, retVal));
/*     */     } 
/*     */     
/* 310 */     return retVal;
/*     */   }
/*     */   
/*     */   private Level getLevelValue(String propertyName) {
/* 314 */     Level retVal = null;
/*     */     
/* 316 */     String stringValue = System.getProperty(propertyName);
/* 317 */     if (stringValue != null) {
/*     */       
/* 319 */       LOGGER.fine(TubelineassemblyMessages.MASM_0018_MSG_LOGGING_SYSTEM_PROPERTY_SET_TO_VALUE(propertyName, stringValue));
/*     */       try {
/* 321 */         retVal = Level.parse(stringValue);
/* 322 */       } catch (IllegalArgumentException ex) {
/* 323 */         LOGGER.warning(TubelineassemblyMessages.MASM_0019_MSG_LOGGING_SYSTEM_PROPERTY_ILLEGAL_VALUE(propertyName, stringValue), ex);
/*     */       } 
/*     */     } 
/*     */     
/* 327 */     return retVal;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultServerTubelineAssemblyContext createServerContext(ServerTubeAssemblerContext jaxwsContext) {
/* 332 */     return new DefaultServerTubelineAssemblyContext(jaxwsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultClientTubelineAssemblyContext createClientContext(ClientTubeAssemblerContext jaxwsContext) {
/* 337 */     return new DefaultClientTubelineAssemblyContext(jaxwsContext);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\MetroTubelineAssembler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */