/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.ReflectPermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.ReflectionException;
/*     */ import org.glassfish.gmbal.generic.DumpIgnore;
/*     */ import org.glassfish.gmbal.generic.DumpToString;
/*     */ import org.glassfish.gmbal.generic.FacetAccessor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*     */ import org.glassfish.gmbal.generic.Pair;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedAccessibleDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedFieldDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedMethodDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedType;
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
/*     */ public class AttributeDescriptor
/*     */ {
/*     */   @DumpToString
/*     */   private EvaluatedAccessibleDeclaration _decl;
/*     */   private String _id;
/*     */   private String _description;
/*     */   private AttributeType _atype;
/*     */   @DumpToString
/*     */   private EvaluatedType _type;
/*     */   private TypeConverter _tc;
/*     */   
/*     */   public enum AttributeType
/*     */   {
/*  67 */     SETTER, GETTER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @DumpIgnore
/*  78 */   private MethodMonitor mm = MethodMonitorFactory.makeStandard(getClass());
/*     */ 
/*     */   
/*  81 */   private static final Permission accessControlPermission = new ReflectPermission("suppressAccessChecks");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributeDescriptor(ManagedObjectManagerInternal mom, final EvaluatedAccessibleDeclaration decl, String id, String description, AttributeType atype, EvaluatedType type) {
/*  89 */     this._decl = AccessController.<EvaluatedAccessibleDeclaration>doPrivileged(new PrivilegedAction<EvaluatedAccessibleDeclaration>()
/*     */         {
/*     */           public EvaluatedAccessibleDeclaration run() {
/*  92 */             decl.accessible().setAccessible(true);
/*  93 */             return decl;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  98 */     this._id = id;
/*  99 */     this._description = description;
/* 100 */     this._atype = atype;
/* 101 */     this._type = type;
/* 102 */     this._tc = mom.getTypeConverter(type);
/*     */   }
/*     */   public final AccessibleObject accessible() {
/* 105 */     return this._decl.accessible();
/*     */   } public final String id() {
/* 107 */     return this._id;
/*     */   } public final String description() {
/* 109 */     return this._description;
/*     */   } public final AttributeType atype() {
/* 111 */     return this._atype;
/*     */   } public final EvaluatedType type() {
/* 113 */     return this._type;
/*     */   } public final TypeConverter tc() {
/* 115 */     return this._tc;
/*     */   }
/*     */   public boolean isApplicable(Object obj) {
/* 118 */     if (this._decl instanceof EvaluatedMethodDeclaration) {
/* 119 */       EvaluatedMethodDeclaration em = (EvaluatedMethodDeclaration)this._decl;
/* 120 */       return em.method().getDeclaringClass().isInstance(obj);
/* 121 */     }  if (this._decl instanceof EvaluatedFieldDeclaration) {
/* 122 */       EvaluatedFieldDeclaration ef = (EvaluatedFieldDeclaration)this._decl;
/* 123 */       return ef.field().getDeclaringClass().isInstance(obj);
/*     */     } 
/*     */     
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   private void checkType(AttributeType at) {
/* 130 */     if (at != this._atype) {
/* 131 */       throw Exceptions.self.excForCheckType(at);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(FacetAccessor fa, boolean debug) throws MBeanException, ReflectionException {
/* 138 */     this.mm.enter(debug, "get", new Object[] { fa });
/*     */     
/* 140 */     checkType(AttributeType.GETTER);
/*     */     
/* 142 */     Object result = null;
/*     */     
/*     */     try {
/* 145 */       if (this._decl instanceof EvaluatedMethodDeclaration) {
/* 146 */         EvaluatedMethodDeclaration em = (EvaluatedMethodDeclaration)this._decl;
/* 147 */         result = this._tc.toManagedEntity(fa.invoke(em.method(), debug, new Object[0]));
/* 148 */       } else if (this._decl instanceof EvaluatedFieldDeclaration) {
/* 149 */         EvaluatedFieldDeclaration ef = (EvaluatedFieldDeclaration)this._decl;
/* 150 */         result = this._tc.toManagedEntity(fa.get(ef.field(), debug));
/*     */       } else {
/* 152 */         Exceptions.self.unknownDeclarationType((EvaluatedDeclaration)this._decl);
/*     */       } 
/*     */     } finally {
/* 155 */       this.mm.exit(debug, result);
/*     */     } 
/*     */     
/* 158 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(FacetAccessor target, Object value, boolean debug) throws MBeanException, ReflectionException {
/* 164 */     checkType(AttributeType.SETTER);
/*     */     
/* 166 */     this.mm.enter(debug, "set", new Object[] { target, value });
/*     */     
/*     */     try {
/* 169 */       if (this._decl instanceof EvaluatedMethodDeclaration) {
/* 170 */         EvaluatedMethodDeclaration em = (EvaluatedMethodDeclaration)this._decl;
/*     */         
/* 172 */         target.invoke(em.method(), debug, new Object[] { this._tc.fromManagedEntity(value) });
/* 173 */       } else if (this._decl instanceof EvaluatedFieldDeclaration) {
/* 174 */         EvaluatedFieldDeclaration ef = (EvaluatedFieldDeclaration)this._decl;
/* 175 */         target.set(ef.field(), this._tc.fromManagedEntity(value), debug);
/*     */       } else {
/* 177 */         Exceptions.self.unknownDeclarationType((EvaluatedDeclaration)this._decl);
/*     */       } 
/*     */     } finally {
/* 180 */       this.mm.exit(debug);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean startsWithNotEquals(String str, String prefix) {
/* 190 */     return (str.startsWith(prefix) && !str.equals(prefix));
/*     */   }
/*     */   
/*     */   private static String stripPrefix(String str, String prefix) {
/* 194 */     return str.substring(prefix.length());
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
/*     */   private static String lowerInitialCharacter(String arg) {
/* 209 */     if (arg == null || arg.length() == 0) {
/* 210 */       return arg;
/*     */     }
/*     */     
/* 213 */     char initChar = Character.toLowerCase(arg.charAt(0));
/* 214 */     String rest = arg.substring(1);
/* 215 */     return initChar + rest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getDerivedId(String methodName, Pair<AttributeType, EvaluatedType> ainfo, ManagedObjectManagerInternal.AttributeDescriptorType adt) {
/* 222 */     String result = methodName;
/* 223 */     boolean needLowerCase = (adt == ManagedObjectManagerInternal.AttributeDescriptorType.COMPOSITE_DATA_ATTR);
/*     */ 
/*     */ 
/*     */     
/* 227 */     if (ainfo.first() == AttributeType.GETTER) {
/* 228 */       if (startsWithNotEquals(methodName, "get")) {
/* 229 */         result = stripPrefix(methodName, "get");
/* 230 */         if (needLowerCase) {
/* 231 */           result = lowerInitialCharacter(result);
/*     */         }
/* 233 */       } else if (((EvaluatedType)ainfo.second()).equals(EvaluatedType.EBOOLEAN) && startsWithNotEquals(methodName, "is")) {
/*     */         
/* 235 */         result = stripPrefix(methodName, "is");
/* 236 */         if (needLowerCase) {
/* 237 */           result = lowerInitialCharacter(result);
/*     */         }
/*     */       }
/*     */     
/* 241 */     } else if (startsWithNotEquals(methodName, "set")) {
/* 242 */       result = stripPrefix(methodName, "set");
/* 243 */       if (needLowerCase) {
/* 244 */         result = lowerInitialCharacter(result);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 249 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Pair<AttributeType, EvaluatedType> getTypeInfo(EvaluatedDeclaration decl) {
/* 255 */     EvaluatedType evalType = null;
/* 256 */     AttributeType atype = null;
/* 257 */     if (decl instanceof EvaluatedMethodDeclaration) {
/* 258 */       EvaluatedMethodDeclaration method = (EvaluatedMethodDeclaration)decl;
/*     */ 
/*     */       
/* 261 */       List<EvaluatedType> atypes = method.parameterTypes();
/*     */       
/* 263 */       if (method.returnType().equals(EvaluatedType.EVOID)) {
/* 264 */         if (atypes.size() != 1) {
/* 265 */           return null;
/*     */         }
/*     */         
/* 268 */         atype = AttributeType.SETTER;
/* 269 */         evalType = atypes.get(0);
/*     */       } else {
/* 271 */         if (atypes.size() != 0) {
/* 272 */           return null;
/*     */         }
/*     */         
/* 275 */         atype = AttributeType.GETTER;
/* 276 */         evalType = method.returnType();
/*     */       } 
/* 278 */     } else if (decl instanceof EvaluatedFieldDeclaration) {
/* 279 */       EvaluatedFieldDeclaration field = (EvaluatedFieldDeclaration)decl;
/*     */       
/* 281 */       evalType = field.fieldType();
/* 282 */       atype = AttributeType.GETTER;
/*     */     } else {
/* 284 */       Exceptions.self.unknownDeclarationType(decl);
/*     */     } 
/*     */     
/* 287 */     return new Pair(atype, evalType);
/*     */   }
/*     */   
/*     */   private static boolean empty(String arg) {
/* 291 */     return (arg == null || arg.length() == 0);
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
/*     */   public static AttributeDescriptor makeFromInherited(ManagedObjectManagerInternal mom, EvaluatedMethodDeclaration method, String id, String methodName, String description, ManagedObjectManagerInternal.AttributeDescriptorType adt) {
/* 303 */     if (empty(methodName) && empty(id)) {
/* 304 */       throw Exceptions.self.excForMakeFromInherited();
/*     */     }
/*     */     
/* 307 */     Pair<AttributeType, EvaluatedType> ainfo = getTypeInfo((EvaluatedDeclaration)method);
/* 308 */     if (ainfo == null) {
/* 309 */       return null;
/*     */     }
/*     */     
/* 312 */     String derivedId = getDerivedId(method.name(), ainfo, adt);
/*     */     
/* 314 */     if (empty(methodName)) {
/* 315 */       if (!derivedId.equals(id)) {
/* 316 */         return null;
/*     */       }
/* 318 */     } else if (!methodName.equals(method.name())) {
/* 319 */       return null;
/*     */     } 
/*     */     
/* 322 */     String actualId = empty(id) ? derivedId : id;
/*     */     
/* 324 */     return new AttributeDescriptor(mom, (EvaluatedAccessibleDeclaration)method, actualId, description, (AttributeType)ainfo.first(), (EvaluatedType)ainfo.second());
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
/*     */   public static AttributeDescriptor makeFromAnnotated(ManagedObjectManagerInternal mom, EvaluatedAccessibleDeclaration decl, String extId, String description, ManagedObjectManagerInternal.AttributeDescriptorType adt) {
/* 339 */     Pair<AttributeType, EvaluatedType> ainfo = getTypeInfo((EvaluatedDeclaration)decl);
/* 340 */     if (ainfo == null) {
/* 341 */       throw Exceptions.self.excForMakeFromAnnotated(decl);
/*     */     }
/*     */     
/* 344 */     String actualId = empty(extId) ? getDerivedId(decl.name(), ainfo, adt) : extId;
/*     */ 
/*     */     
/* 347 */     if (mom.isAMXAttributeName(actualId)) {
/* 348 */       throw Exceptions.self.duplicateAMXFieldName(actualId, decl.name(), decl.containingClass().name());
/*     */     }
/*     */ 
/*     */     
/* 352 */     return new AttributeDescriptor(mom, decl, actualId, description, (AttributeType)ainfo.first(), (EvaluatedType)ainfo.second());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\AttributeDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */