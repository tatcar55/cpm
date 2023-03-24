/*     */ package com.sun.xml.rpc.wsdl.document;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.DuplicateEntityException;
/*     */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*     */ import com.sun.xml.rpc.wsdl.framework.EntityAction;
/*     */ import com.sun.xml.rpc.wsdl.framework.ExtensibilityHelper;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class Definitions
/*     */   extends Entity
/*     */   implements Defining, Extensible
/*     */ {
/*     */   private AbstractDocument _document;
/*     */   private ExtensibilityHelper _helper;
/*     */   private Documentation _documentation;
/*     */   private String _name;
/*     */   private String _targetNsURI;
/*     */   private Types _types;
/*     */   private List _messages;
/*     */   private List _portTypes;
/*     */   private List _bindings;
/*     */   private List _services;
/*     */   private List _imports;
/*     */   private Set _importedNamespaces;
/*     */   
/*     */   public Definitions(AbstractDocument document) {
/*  54 */     this._document = document;
/*  55 */     this._bindings = new ArrayList();
/*  56 */     this._imports = new ArrayList();
/*  57 */     this._messages = new ArrayList();
/*  58 */     this._portTypes = new ArrayList();
/*  59 */     this._services = new ArrayList();
/*  60 */     this._importedNamespaces = new HashSet();
/*  61 */     this._helper = new ExtensibilityHelper();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  65 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  69 */     this._name = s;
/*     */   }
/*     */   
/*     */   public String getTargetNamespaceURI() {
/*  73 */     return this._targetNsURI;
/*     */   }
/*     */   
/*     */   public void setTargetNamespaceURI(String s) {
/*  77 */     this._targetNsURI = s;
/*     */   }
/*     */   
/*     */   public void setTypes(Types t) {
/*  81 */     this._types = t;
/*     */   }
/*     */   
/*     */   public Types getTypes() {
/*  85 */     return this._types;
/*     */   }
/*     */   
/*     */   public void add(Message m) {
/*     */     try {
/*  90 */       this._document.define((GloballyKnown)m);
/*  91 */     } catch (DuplicateEntityException e) {
/*     */       return;
/*     */     } 
/*     */     
/*  95 */     this._messages.add(m);
/*     */   }
/*     */   
/*     */   public void add(PortType p) {
/*     */     try {
/* 100 */       this._document.define((GloballyKnown)p);
/* 101 */     } catch (DuplicateEntityException e) {
/*     */       return;
/*     */     } 
/*     */     
/* 105 */     this._portTypes.add(p);
/*     */   }
/*     */   
/*     */   public void add(Binding b) {
/*     */     try {
/* 110 */       this._document.define((GloballyKnown)b);
/* 111 */     } catch (DuplicateEntityException e) {
/*     */       return;
/*     */     } 
/*     */     
/* 115 */     this._bindings.add(b);
/*     */   }
/*     */   
/*     */   public void add(Service s) {
/*     */     try {
/* 120 */       this._document.define((GloballyKnown)s);
/* 121 */     } catch (DuplicateEntityException e) {
/*     */       return;
/*     */     } 
/*     */     
/* 125 */     this._services.add(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addServiceOveride(Service s) {
/* 130 */     this._services.add(s);
/*     */   }
/*     */   
/*     */   public void add(Import i) {
/* 134 */     if (this._importedNamespaces.contains(i.getNamespace())) {
/* 135 */       throw new DuplicateEntityException(i, i.getNamespace());
/*     */     }
/*     */     
/* 138 */     this._imports.add(i);
/*     */   }
/*     */   
/*     */   public Iterator imports() {
/* 142 */     return this._imports.iterator();
/*     */   }
/*     */   
/*     */   public Iterator messages() {
/* 146 */     return this._messages.iterator();
/*     */   }
/*     */   
/*     */   public Iterator portTypes() {
/* 150 */     return this._portTypes.iterator();
/*     */   }
/*     */   
/*     */   public Iterator bindings() {
/* 154 */     return this._bindings.iterator();
/*     */   }
/*     */   
/*     */   public Iterator services() {
/* 158 */     return this._services.iterator();
/*     */   }
/*     */   
/*     */   public QName getElementName() {
/* 162 */     return WSDLConstants.QNAME_DEFINITIONS;
/*     */   }
/*     */   
/*     */   public Documentation getDocumentation() {
/* 166 */     return this._documentation;
/*     */   }
/*     */   
/*     */   public void setDocumentation(Documentation d) {
/* 170 */     this._documentation = d;
/*     */   }
/*     */   
/*     */   public void addExtension(Extension e) {
/* 174 */     this._helper.addExtension(e);
/*     */   }
/*     */   
/*     */   public Iterator extensions() {
/* 178 */     return this._helper.extensions();
/*     */   }
/*     */   
/*     */   public void withAllSubEntitiesDo(EntityAction action) {
/* 182 */     if (this._types != null) {
/* 183 */       action.perform(this._types);
/*     */     }
/* 185 */     for (Iterator<Entity> iterator4 = this._messages.iterator(); iterator4.hasNext();) {
/* 186 */       action.perform(iterator4.next());
/*     */     }
/* 188 */     for (Iterator<Entity> iterator3 = this._portTypes.iterator(); iterator3.hasNext();) {
/* 189 */       action.perform(iterator3.next());
/*     */     }
/* 191 */     for (Iterator<Entity> iterator2 = this._bindings.iterator(); iterator2.hasNext();) {
/* 192 */       action.perform(iterator2.next());
/*     */     }
/* 194 */     for (Iterator<Entity> iterator1 = this._services.iterator(); iterator1.hasNext();) {
/* 195 */       action.perform(iterator1.next());
/*     */     }
/* 197 */     for (Iterator<Entity> iter = this._imports.iterator(); iter.hasNext();) {
/* 198 */       action.perform(iter.next());
/*     */     }
/* 200 */     this._helper.withAllSubEntitiesDo(action);
/*     */   }
/*     */   
/*     */   public void accept(WSDLDocumentVisitor visitor) throws Exception {
/* 204 */     visitor.preVisit(this);
/*     */     
/* 206 */     for (Iterator<Import> iterator3 = this._imports.iterator(); iterator3.hasNext();) {
/* 207 */       ((Import)iterator3.next()).accept(visitor);
/*     */     }
/*     */     
/* 210 */     if (this._types != null) {
/* 211 */       this._types.accept(visitor);
/*     */     }
/*     */     
/* 214 */     for (Iterator<Message> iterator2 = this._messages.iterator(); iterator2.hasNext();) {
/* 215 */       ((Message)iterator2.next()).accept(visitor);
/*     */     }
/* 217 */     for (Iterator<PortType> iterator1 = this._portTypes.iterator(); iterator1.hasNext();) {
/* 218 */       ((PortType)iterator1.next()).accept(visitor);
/*     */     }
/* 220 */     for (Iterator<Binding> iterator = this._bindings.iterator(); iterator.hasNext();) {
/* 221 */       ((Binding)iterator.next()).accept(visitor);
/*     */     }
/* 223 */     for (Iterator<Service> iter = this._services.iterator(); iter.hasNext();) {
/* 224 */       ((Service)iter.next()).accept(visitor);
/*     */     }
/*     */     
/* 227 */     this._helper.accept(visitor);
/* 228 */     visitor.postVisit(this);
/*     */   }
/*     */   
/*     */   public void validateThis() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\document\Definitions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */