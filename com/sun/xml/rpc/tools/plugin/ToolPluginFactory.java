/*     */ package com.sun.xml.rpc.tools.plugin;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class ToolPluginFactory
/*     */ {
/*     */   private Map pluginMap;
/*     */   private static Logger logger;
/*     */   private static Localizer localizer;
/*     */   private static LocalizableMessageFactory messageFactory;
/*     */   public static final String NS_NAME = "http://java.sun.com/xml/ns/jax-rpc/ri/tool-plugin";
/*  64 */   private static final QName QNAME_TOOL_PLUGINS = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/tool-plugin", "toolPlugins");
/*     */   
/*  66 */   private static final QName QNAME_TOOL_PLUGIN = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/tool-plugin", "toolPlugin");
/*     */   
/*  68 */   private static final QName QNAME_EXTENSION_POINT = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/tool-plugin", "extensionPoint");
/*     */   
/*  70 */   private static final QName QNAME_EXTENSION = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/tool-plugin", "extension");
/*     */   
/*     */   private static final String ATTR_EXTEND_ID = "extendId";
/*     */   
/*     */   private static final String ATTR_EXTEND_TYPE = "type";
/*     */   
/*     */   private static final String ATTR_PLUGIN_ID = "pluginId";
/*     */   private static final String ATTR_CLASS_NAME = "class";
/*  78 */   private static ToolPluginFactory factory = new ToolPluginFactory();
/*     */   
/*     */   private class ExtensionPointTag {
/*     */     private String extendId;
/*     */     private String extendType;
/*     */     private List implementors;
/*     */     
/*     */     public ExtensionPointTag(String extendId, String extendType) {
/*  86 */       this.extendId = extendId;
/*  87 */       this.extendType = extendType;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  91 */       return this.extendId + ":" + this.extendType + ":" + this.implementors;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExtensionTag {
/*     */     private String pluginId;
/*     */     private String extendId;
/*     */     
/*     */     public ExtensionTag(String pluginId, String extendId) {
/* 100 */       this.pluginId = pluginId;
/* 101 */       this.extendId = extendId;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 105 */       return this.pluginId + ":" + this.extendId;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ToolPluginTag {
/*     */     private String pluginId;
/*     */     private String className;
/*     */     private Map extensionPointMap;
/*     */     private List extensionsList;
/*     */     
/*     */     public ToolPluginTag(String pluginId, String className) {
/* 116 */       this.pluginId = pluginId;
/* 117 */       this.className = className;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ToolPluginAll
/*     */   {
/*     */     private ToolPlugin toolPlugin;
/*     */     private Map extensionsMap;
/*     */     private ToolPluginFactory.ToolPluginTag toolPluginTag;
/*     */     
/*     */     public ToolPluginAll(ToolPluginFactory.ToolPluginTag toolPluginTag) {
/* 128 */       this.toolPluginTag = toolPluginTag;
/*     */     }
/*     */     
/*     */     public ToolPlugin getToolPlugin() {
/* 132 */       if (this.toolPlugin == null) {
/* 133 */         ClassLoader cll = Thread.currentThread().getContextClassLoader();
/*     */         
/*     */         try {
/* 136 */           Class<?> cl = cll.loadClass(this.toolPluginTag.className);
/* 137 */           this.toolPlugin = (ToolPlugin)cl.newInstance();
/* 138 */         } catch (Exception ex) {
/* 139 */           ex.printStackTrace();
/*     */         } 
/*     */       } 
/* 142 */       return this.toolPlugin;
/*     */     }
/*     */     
/*     */     public Iterator getExtensions(String extendId) {
/* 146 */       if (this.toolPluginTag.extensionPointMap == null) {
/* 147 */         return null;
/*     */       }
/* 149 */       ToolPluginFactory.ExtensionPointTag ExtensionPointTag = (ToolPluginFactory.ExtensionPointTag)this.toolPluginTag.extensionPointMap.get(extendId);
/*     */       
/* 151 */       if (ExtensionPointTag == null) {
/* 152 */         return null;
/*     */       }
/* 154 */       List allList = ExtensionPointTag.implementors;
/* 155 */       if (allList == null) {
/* 156 */         return null;
/*     */       }
/* 158 */       if (this.extensionsMap == null) {
/* 159 */         this.extensionsMap = new HashMap<Object, Object>();
/*     */       }
/* 161 */       List<ToolPlugin> implList = (List)this.extensionsMap.get(extendId);
/* 162 */       if (implList == null) {
/* 163 */         implList = new ArrayList();
/* 164 */         Iterator<ToolPluginAll> i = allList.iterator();
/* 165 */         while (i.hasNext()) {
/* 166 */           ToolPluginAll implAll = i.next();
/* 167 */           ToolPlugin toolPlugin = implAll.getToolPlugin();
/* 168 */           implList.add(toolPlugin);
/*     */         } 
/* 170 */         this.extensionsMap.put(extendId, implList);
/*     */       } 
/* 172 */       return implList.iterator();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private ToolPluginFactory() {
/* 178 */     messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.toolplugin");
/*     */     
/* 180 */     logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.toolplugin");
/*     */     
/* 182 */     localizer = new Localizer();
/* 183 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */     try {
/* 185 */       Enumeration<URL> urls = loader.getResources("META-INF/jaxrpc/ToolPlugin.xml");
/* 186 */       while (urls != null && urls.hasMoreElements()) {
/* 187 */         URL url = urls.nextElement();
/* 188 */         InputStream in = new BufferedInputStream(url.openStream());
/* 189 */         XMLReader reader = XMLReaderFactory.newInstance().createXMLReader(in);
/*     */         
/* 191 */         reader.next();
/* 192 */         QName tag = reader.getName();
/* 193 */         if (tag.equals(QNAME_TOOL_PLUGINS)) {
/* 194 */           parseToolPlugins(reader);
/*     */         }
/* 196 */         reader.nextElementContent();
/* 197 */         XMLReaderUtil.verifyReaderState(reader, 5);
/* 198 */         in.close();
/*     */       } 
/* 200 */       linkExtensionsWithExtensionPoints();
/* 201 */     } catch (Exception ex) {
/* 202 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void linkExtensionsWithExtensionPoints() {
/* 207 */     if (this.pluginMap != null) {
/* 208 */       Iterator<Map.Entry> i = this.pluginMap.entrySet().iterator();
/* 209 */       while (i.hasNext()) {
/* 210 */         Map.Entry entry = i.next();
/* 211 */         ToolPluginAll toolPluginAll = (ToolPluginAll)entry.getValue();
/* 212 */         List list = toolPluginAll.toolPluginTag.extensionsList;
/* 213 */         if (list != null) {
/* 214 */           Iterator<ExtensionTag> j = list.iterator();
/* 215 */           while (j.hasNext()) {
/* 216 */             ExtensionTag extensionTag = j.next();
/* 217 */             if (toolPluginAll.toolPluginTag.pluginId.equals(extensionTag.pluginId)) {
/*     */ 
/*     */               
/* 220 */               if (logger.isLoggable(Level.WARNING)) {
/* 221 */                 logger.warning(localizer.localize(messageFactory.getMessage("no.self.extension", extensionTag.pluginId)));
/*     */               }
/*     */ 
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 228 */             setExtensionImplRef(extensionTag.pluginId, extensionTag.extendId, toolPluginAll);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setExtensionImplRef(String pluginId, String extendId, ToolPluginAll impl) {
/* 240 */     ToolPluginAll base = (ToolPluginAll)this.pluginMap.get(pluginId);
/* 241 */     if (base != null) {
/* 242 */       ToolPluginTag toolPluginTag = base.toolPluginTag;
/* 243 */       if (toolPluginTag.extensionPointMap != null) {
/* 244 */         ExtensionPointTag tag = (ExtensionPointTag)toolPluginTag.extensionPointMap.get(extendId);
/*     */         
/* 246 */         if (tag != null) {
/* 247 */           if (tag.implementors == null) {
/* 248 */             tag.implementors = new ArrayList();
/*     */           }
/* 250 */           tag.implementors.add(impl);
/*     */         }
/* 252 */         else if (logger.isLoggable(Level.WARNING)) {
/* 253 */           logger.warning(localizer.localize(messageFactory.getMessage("unknown.extensionPoint", extendId)));
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 259 */       else if (logger.isLoggable(Level.WARNING)) {
/* 260 */         logger.warning(localizer.localize(messageFactory.getMessage("unknown.extensionPoint", extendId)));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 265 */     else if (logger.isLoggable(Level.WARNING)) {
/* 266 */       logger.warning(localizer.localize(messageFactory.getMessage("unknown.plugin", pluginId)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExtensionTag parseExtension(XMLReader reader) {
/* 274 */     Attributes attrs = reader.getAttributes();
/* 275 */     String pluginId = attrs.getValue("pluginId");
/* 276 */     String extendId = attrs.getValue("extendId");
/* 277 */     if ((pluginId == null || extendId == null) && 
/* 278 */       logger.isLoggable(Level.WARNING)) {
/* 279 */       logger.warning(localizer.localize(messageFactory.getMessage("no.pluginId.or.extendId")));
/*     */     }
/*     */ 
/*     */     
/* 283 */     reader.nextElementContent();
/* 284 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 285 */     ExtensionTag extensionTag = new ExtensionTag(pluginId, extendId);
/*     */     
/* 287 */     return extensionTag;
/*     */   }
/*     */   
/*     */   private ExtensionPointTag parseExtendPoint(XMLReader reader) {
/* 291 */     Attributes attrs = reader.getAttributes();
/* 292 */     String extendId = attrs.getValue("extendId");
/* 293 */     String extendType = attrs.getValue("type");
/* 294 */     if ((extendId == null || extendType == null) && 
/* 295 */       logger.isLoggable(Level.WARNING)) {
/* 296 */       logger.warning(localizer.localize(messageFactory.getMessage("no.extendId.or.extendType")));
/*     */     }
/*     */ 
/*     */     
/* 300 */     reader.nextElementContent();
/* 301 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 302 */     ExtensionPointTag ExtensionPointTag = new ExtensionPointTag(extendId, extendType);
/*     */     
/* 304 */     return ExtensionPointTag;
/*     */   }
/*     */   
/*     */   private void parseToolPlugins(XMLReader reader) {
/* 308 */     while (reader.nextElementContent() == 1) {
/*     */       
/* 310 */       QName tag = reader.getName();
/* 311 */       if (tag.equals(QNAME_TOOL_PLUGIN)) {
/* 312 */         parseToolPlugin(reader); continue;
/*     */       } 
/* 314 */       if (logger.isLoggable(Level.WARNING)) {
/* 315 */         logger.warning(localizer.localize(messageFactory.getMessage("unknown.tag", tag.toString())));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 320 */     XMLReaderUtil.verifyReaderState(reader, 2);
/*     */   }
/*     */   
/*     */   private void parseToolPlugin(XMLReader reader) {
/* 324 */     Attributes attrs = reader.getAttributes();
/* 325 */     String pluginId = attrs.getValue("pluginId");
/* 326 */     String className = attrs.getValue("class");
/* 327 */     ToolPluginTag toolPluginTag = new ToolPluginTag(pluginId, className);
/*     */     
/* 329 */     while (reader.nextElementContent() == 1) {
/* 330 */       QName tag = reader.getName();
/* 331 */       if (tag.equals(QNAME_EXTENSION_POINT)) {
/* 332 */         ExtensionPointTag ExtensionPointTag = parseExtendPoint(reader);
/* 333 */         if (toolPluginTag.extensionPointMap == null) {
/* 334 */           toolPluginTag.extensionPointMap = new HashMap<Object, Object>();
/*     */         }
/* 336 */         toolPluginTag.extensionPointMap.put(ExtensionPointTag.extendId, ExtensionPointTag); continue;
/*     */       } 
/* 338 */       if (tag.equals(QNAME_EXTENSION)) {
/* 339 */         ExtensionTag ExtensionTag = parseExtension(reader);
/* 340 */         if (toolPluginTag.extensionsList == null) {
/* 341 */           toolPluginTag.extensionsList = new ArrayList();
/*     */         }
/* 343 */         toolPluginTag.extensionsList.add(ExtensionTag); continue;
/*     */       } 
/* 345 */       if (logger.isLoggable(Level.WARNING)) {
/* 346 */         logger.warning(localizer.localize(messageFactory.getMessage("unknown.tag", tag.toString())));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 351 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 352 */     if (this.pluginMap == null) {
/* 353 */       this.pluginMap = new HashMap<Object, Object>();
/*     */     }
/* 355 */     this.pluginMap.put(pluginId, new ToolPluginAll(toolPluginTag));
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolPlugin getPlugin(String pluginId) {
/* 360 */     ToolPlugin toolPlugin = null;
/* 361 */     if (this.pluginMap != null) {
/* 362 */       ToolPluginAll toolPluginAll = (ToolPluginAll)this.pluginMap.get(pluginId);
/*     */       
/* 364 */       if (toolPluginAll != null) {
/* 365 */         toolPlugin = toolPluginAll.getToolPlugin();
/*     */       }
/*     */     } 
/* 368 */     return toolPlugin;
/*     */   }
/*     */   
/*     */   public Iterator getExtensions(String pluginId, String extendId) {
/* 372 */     Iterator i = null;
/* 373 */     if (this.pluginMap != null) {
/* 374 */       ToolPluginAll toolPluginAll = (ToolPluginAll)this.pluginMap.get(pluginId);
/*     */       
/* 376 */       if (toolPluginAll != null) {
/* 377 */         ToolPlugin toolPlugin = toolPluginAll.getToolPlugin();
/* 378 */         if (toolPlugin != null) {
/* 379 */           return toolPluginAll.getExtensions(extendId);
/*     */         }
/*     */       } 
/*     */     } 
/* 383 */     return i;
/*     */   }
/*     */   
/*     */   private void printAll() {
/* 387 */     System.out.println("All Plugins");
/* 388 */     System.out.println("===========");
/* 389 */     if (this.pluginMap != null) {
/* 390 */       Iterator<Map.Entry> i = this.pluginMap.entrySet().iterator();
/* 391 */       while (i.hasNext()) {
/* 392 */         Map.Entry entry = i.next();
/* 393 */         System.out.println("pluginId=" + entry.getKey());
/* 394 */         System.out.println("------------------------");
/* 395 */         ToolPluginAll toolPluginAll = (ToolPluginAll)entry.getValue();
/* 396 */         System.out.println("toolPlugin=" + toolPluginAll.toolPlugin);
/* 397 */         System.out.println("extensionPointMap=" + toolPluginAll.toolPluginTag.extensionPointMap);
/*     */         
/* 399 */         System.out.println("extensionsList=" + toolPluginAll.toolPluginTag.extensionsList);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ToolPluginFactory getInstance() {
/* 407 */     if (factory == null) {
/* 408 */       factory = new ToolPluginFactory();
/*     */     }
/* 410 */     return factory;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\plugin\ToolPluginFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */