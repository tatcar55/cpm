/*     */ package org.glassfish.gmbal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.IntrospectionException;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.JMException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanInfo;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.MalformedObjectNameException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.ReflectionException;
/*     */ import javax.management.RuntimeOperationsException;
/*     */ import javax.management.modelmbean.ModelMBeanInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AMXClient
/*     */   implements AMXMBeanInterface
/*     */ {
/*     */   private static ObjectName makeObjectName(String str) {
/*     */     try {
/*  78 */       return new ObjectName(str);
/*  79 */     } catch (MalformedObjectNameException ex) {
/*  80 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final ObjectName NULL_OBJECTNAME = makeObjectName("null:type=Null,name=Null");
/*     */   
/*     */   private MBeanServerConnection server;
/*     */   
/*     */   private ObjectName oname;
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  94 */     if (this == obj) {
/*  95 */       return true;
/*     */     }
/*     */     
/*  98 */     if (!(obj instanceof AMXClient)) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     AMXClient other = (AMXClient)obj;
/*     */     
/* 104 */     return this.oname.equals(other.oname);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     int hash = 5;
/* 110 */     hash = 47 * hash + ((this.oname != null) ? this.oname.hashCode() : 0);
/* 111 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return "AMXClient[" + this.oname + "]";
/*     */   }
/*     */   
/*     */   private <T> T fetchAttribute(String name, Class<T> type) {
/*     */     try {
/* 121 */       Object obj = this.server.getAttribute(this.oname, name);
/* 122 */       if (NULL_OBJECTNAME.equals(obj)) {
/* 123 */         return null;
/*     */       }
/* 125 */       return type.cast(obj);
/*     */     }
/* 127 */     catch (JMException exc) {
/* 128 */       throw new GmbalException("Exception in fetchAttribute", exc);
/* 129 */     } catch (IOException exc) {
/* 130 */       throw new GmbalException("Exception in fetchAttribute", exc);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AMXClient(MBeanServerConnection server, ObjectName oname) {
/* 136 */     this.server = server;
/* 137 */     this.oname = oname;
/*     */   }
/*     */   
/*     */   private AMXClient makeAMX(ObjectName on) {
/* 141 */     if (on == null) {
/* 142 */       return null;
/*     */     }
/* 144 */     return new AMXClient(this.server, on);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 148 */     return fetchAttribute("Name", String.class);
/*     */   }
/*     */   
/*     */   public Map<String, ?> getMeta() {
/*     */     try {
/* 153 */       ModelMBeanInfo mbi = (ModelMBeanInfo)this.server.getMBeanInfo(this.oname);
/* 154 */       Descriptor desc = mbi.getMBeanDescriptor();
/* 155 */       Map<String, Object> result = new HashMap<String, Object>();
/* 156 */       for (String str : desc.getFieldNames()) {
/* 157 */         result.put(str, desc.getFieldValue(str));
/*     */       }
/* 159 */       return result;
/* 160 */     } catch (MBeanException ex) {
/* 161 */       throw new GmbalException("Exception in getMeta", ex);
/* 162 */     } catch (RuntimeOperationsException ex) {
/* 163 */       throw new GmbalException("Exception in getMeta", ex);
/* 164 */     } catch (InstanceNotFoundException ex) {
/* 165 */       throw new GmbalException("Exception in getMeta", ex);
/* 166 */     } catch (IntrospectionException ex) {
/* 167 */       throw new GmbalException("Exception in getMeta", ex);
/* 168 */     } catch (ReflectionException ex) {
/* 169 */       throw new GmbalException("Exception in getMeta", ex);
/* 170 */     } catch (IOException ex) {
/* 171 */       throw new GmbalException("Exception in getMeta", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AMXClient getParent() {
/* 176 */     ObjectName res = fetchAttribute("Parent", ObjectName.class);
/* 177 */     return makeAMX(res);
/*     */   }
/*     */   
/*     */   public AMXClient[] getChildren() {
/* 181 */     ObjectName[] onames = fetchAttribute("Children", (Class)ObjectName[].class);
/*     */     
/* 183 */     return makeAMXArray(onames);
/*     */   }
/*     */   
/*     */   private AMXClient[] makeAMXArray(ObjectName[] onames) {
/* 187 */     AMXClient[] result = new AMXClient[onames.length];
/* 188 */     int ctr = 0;
/* 189 */     for (ObjectName on : onames) {
/* 190 */       result[ctr++] = makeAMX(on);
/*     */     }
/*     */     
/* 193 */     return result;
/*     */   }
/*     */   
/*     */   public Object getAttribute(String attribute) {
/*     */     try {
/* 198 */       return this.server.getAttribute(this.oname, attribute);
/* 199 */     } catch (MBeanException ex) {
/* 200 */       throw new GmbalException("Exception in getAttribute", ex);
/* 201 */     } catch (AttributeNotFoundException ex) {
/* 202 */       throw new GmbalException("Exception in getAttribute", ex);
/* 203 */     } catch (ReflectionException ex) {
/* 204 */       throw new GmbalException("Exception in getAttribute", ex);
/* 205 */     } catch (InstanceNotFoundException ex) {
/* 206 */       throw new GmbalException("Exception in getAttribute", ex);
/* 207 */     } catch (IOException ex) {
/* 208 */       throw new GmbalException("Exception in getAttribute", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, Object value) {
/* 213 */     Attribute attr = new Attribute(name, value);
/* 214 */     setAttribute(attr);
/*     */   }
/*     */   
/*     */   public void setAttribute(Attribute attribute) {
/*     */     try {
/* 219 */       this.server.setAttribute(this.oname, attribute);
/* 220 */     } catch (InstanceNotFoundException ex) {
/* 221 */       throw new GmbalException("Exception in setAttribute", ex);
/* 222 */     } catch (AttributeNotFoundException ex) {
/* 223 */       throw new GmbalException("Exception in setAttribute", ex);
/* 224 */     } catch (InvalidAttributeValueException ex) {
/* 225 */       throw new GmbalException("Exception in setAttribute", ex);
/* 226 */     } catch (MBeanException ex) {
/* 227 */       throw new GmbalException("Exception in setAttribute", ex);
/* 228 */     } catch (ReflectionException ex) {
/* 229 */       throw new GmbalException("Exception in setAttribute", ex);
/* 230 */     } catch (IOException ex) {
/* 231 */       throw new GmbalException("Exception in setAttribute", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AttributeList getAttributes(String[] attributes) {
/*     */     try {
/* 237 */       return this.server.getAttributes(this.oname, attributes);
/* 238 */     } catch (InstanceNotFoundException ex) {
/* 239 */       throw new GmbalException("Exception in getAttributes", ex);
/* 240 */     } catch (ReflectionException ex) {
/* 241 */       throw new GmbalException("Exception in getAttributes", ex);
/* 242 */     } catch (IOException ex) {
/* 243 */       throw new GmbalException("Exception in getAttributes", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AttributeList setAttributes(AttributeList attributes) {
/*     */     try {
/* 249 */       return this.server.setAttributes(this.oname, attributes);
/* 250 */     } catch (InstanceNotFoundException ex) {
/* 251 */       throw new GmbalException("Exception in setAttributes", ex);
/* 252 */     } catch (ReflectionException ex) {
/* 253 */       throw new GmbalException("Exception in setAttributes", ex);
/* 254 */     } catch (IOException ex) {
/* 255 */       throw new GmbalException("Exception in setAttributes", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
/*     */     try {
/* 262 */       return this.server.invoke(this.oname, actionName, params, signature);
/* 263 */     } catch (InstanceNotFoundException ex) {
/* 264 */       throw new GmbalException("Exception in invoke", ex);
/* 265 */     } catch (IOException ex) {
/* 266 */       throw new GmbalException("Exception in invoke", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public MBeanInfo getMBeanInfo() {
/*     */     try {
/* 272 */       return this.server.getMBeanInfo(this.oname);
/* 273 */     } catch (InstanceNotFoundException ex) {
/* 274 */       throw new GmbalException("Exception in invoke", ex);
/* 275 */     } catch (IntrospectionException ex) {
/* 276 */       throw new GmbalException("Exception in invoke", ex);
/* 277 */     } catch (ReflectionException ex) {
/* 278 */       throw new GmbalException("Exception in invoke", ex);
/* 279 */     } catch (IOException ex) {
/* 280 */       throw new GmbalException("Exception in invoke", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ObjectName objectName() {
/* 285 */     return this.oname;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\AMXClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */