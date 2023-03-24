/*    */ package org.glassfish.gmbal.impl;
/*    */ 
/*    */ import java.io.InvalidObjectException;
/*    */ import java.lang.reflect.AnnotatedElement;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.UndeclaredThrowableException;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.management.AttributeNotFoundException;
/*    */ import javax.management.JMException;
/*    */ import javax.management.MBeanException;
/*    */ import javax.management.ObjectName;
/*    */ import javax.management.openmbean.OpenType;
/*    */ import org.glassfish.gmbal.GmbalException;
/*    */ import org.glassfish.gmbal.logex.Chain;
/*    */ import org.glassfish.gmbal.logex.ExceptionWrapper;
/*    */ import org.glassfish.gmbal.logex.Log;
/*    */ import org.glassfish.gmbal.logex.LogLevel;
/*    */ import org.glassfish.gmbal.logex.Message;
/*    */ import org.glassfish.gmbal.logex.StackTrace;
/*    */ import org.glassfish.gmbal.logex.WrapperGenerator;
/*    */ import org.glassfish.gmbal.typelib.EvaluatedClassDeclaration;
/*    */ import org.glassfish.gmbal.typelib.EvaluatedDeclaration;
/*    */ import org.glassfish.gmbal.typelib.EvaluatedFieldDeclaration;
/*    */ import org.glassfish.gmbal.typelib.EvaluatedMethodDeclaration;
/*    */ import org.glassfish.gmbal.typelib.EvaluatedType;
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
/*    */ @ExceptionWrapper(idPrefix = "GMBAL", resourceBundle = "org.glassfish.gmbal.logex.LogStrings")
/*    */ public interface Exceptions
/*    */ {
/* 85 */   public static final Exceptions self = (Exceptions)WrapperGenerator.makeWrapper(Exceptions.class);
/*    */   public static final int EXCEPTIONS_PER_CLASS = 100;
/*    */   public static final int AMX_IMPL_START = 1;
/*    */   public static final int ATTRIBUTE_DESCRIPTOR_START = 101;
/*    */   public static final int DESCRIPTOR_INTROSPECTOR_START = 201;
/*    */   public static final int IMMUTABLE_DESCRIPTOR_START = 301;
/*    */   public static final int MBEAN_IMPL_START = 401;
/*    */   public static final int MBEAN_SKELETON_START = 501;
/*    */   public static final int MBEAN_TREE_START = 601;
/*    */   public static final int MANAGED_OBJECT_MANAGER_IMPL_START = 701;
/*    */   public static final int TYPE_CONVERTER_IMPL_START = 801;
/*    */   public static final int JMX_REGISTRATION_MANAGER_START = 901;
/*    */   
/*    */   @Message("Exception in getMeta")
/*    */   @Log(id = 1)
/*    */   GmbalException excForGetMeta(@Chain MBeanException paramMBeanException);
/*    */   
/*    */   @Message("Required type is {0}")
/*    */   @Log(id = 101)
/*    */   GmbalException excForCheckType(AttributeDescriptor.AttributeType paramAttributeType);
/*    */   
/*    */   @Message("methodName and id must not both be null")
/*    */   @Log(id = 102)
/*    */   IllegalArgumentException excForMakeFromInherited();
/*    */   
/*    */   @Message("{0} is not a valid attribute method")
/*    */   @Log(id = 103)
/*    */   IllegalArgumentException excForMakeFromAnnotated(EvaluatedDeclaration paramEvaluatedDeclaration);
/*    */   
/*    */   @Message("Unknown EvaluatedDeclaration type {0}")
/*    */   @Log(id = 105)
/*    */   IllegalArgumentException unknownDeclarationType(EvaluatedDeclaration paramEvaluatedDeclaration);
/*    */   
/*    */   @Message("Attribute id {0} in method {1} in class {2} is illegal becase it is a reserved Attribute id for AMX")
/*    */   @Log(id = 106)
/*    */   IllegalArgumentException duplicateAMXFieldName(String paramString1, String paramString2, String paramString3);
/*    */   
/*    */   @Message("@DescriptorFields must contain '=' : {0}")
/*    */   @Log(id = 201)
/*    */   IllegalArgumentException excForAddDescriptorFieldsToMap(String paramString);
/*    */   
/*    */   @Log(id = 202)
/*    */   @Message("Exception in addAnnotationFieldsToMap")
/*    */   UndeclaredThrowableException excForAddAnnotationFieldsToMap(@Chain Exception paramException);
/*    */   
/*    */   @Message("Inconcistent values for descriptor field {0} from annotations: {1} :: {2}")
/*    */   @Log(id = 203)
/*    */   IllegalArgumentException excForAddToMap(String paramString, Object paramObject1, Object paramObject2);
/*    */   
/*    */   @Message("Illegal type for annotation element using @DescriptorKey: {0}")
/*    */   @Log(id = 204)
/*    */   IllegalArgumentException excForAnnotationToField(String paramString);
/*    */   
/*    */   @Message("Null Map")
/*    */   @Log(id = 301)
/*    */   IllegalArgumentException nullMap();
/*    */   
/*    */   @Message("Empty or null field name")
/*    */   @Log(id = 302)
/*    */   IllegalArgumentException badFieldName();
/*    */   
/*    */   @Message("Duplicate field name: {0}")
/*    */   @Log(id = 303)
/*    */   IllegalArgumentException duplicateFieldName(String paramString);
/*    */   
/*    */   @Message("Bad names or values")
/*    */   @Log(id = 304)
/*    */   InvalidObjectException excForReadResolveImmutableDescriptor();
/*    */   
/*    */   @Message("Null array parameter")
/*    */   @Log(id = 305)
/*    */   IllegalArgumentException nullArrayParameter();
/*    */   
/*    */   @Message("Different size arrays")
/*    */   @Log(id = 306)
/*    */   IllegalArgumentException differentSizeArrays();
/*    */   
/*    */   @Message("Null fields parameter")
/*    */   @Log(id = 307)
/*    */   IllegalArgumentException nullFieldsParameter();
/*    */   
/*    */   @Message("Missing = character: {0}")
/*    */   @Log(id = 308)
/*    */   IllegalArgumentException badFieldFormat(String paramString);
/*    */   
/*    */   @Message("Inconsistent values for descriptor field {0}: {1} :: {2}")
/*    */   @Log(id = 309)
/*    */   IllegalArgumentException excForUnion(String paramString, Object paramObject1, Object paramObject2);
/*    */   
/*    */   @Message("Null argument")
/*    */   @Log(id = 310)
/*    */   IllegalArgumentException nullArgument();
/*    */   
/*    */   @Message("Descriptor is read-only")
/*    */   @Log(id = 311)
/*    */   UnsupportedOperationException unsupportedOperation();
/*    */   
/*    */   @Message("Cannot set parent to {0}: this node already has a parent")
/*    */   @Log(id = 401)
/*    */   IllegalArgumentException nodeAlreadyHasParent(MBeanImpl paramMBeanImpl);
/*    */   
/*    */   @Message("Parent object {0} only allows subtypes {1}:  cannot add child {2} of type {3}")
/*    */   @Log(id = 402)
/*    */   IllegalArgumentException invalidSubtypeOfParent(ObjectName paramObjectName1, Set<String> paramSet, ObjectName paramObjectName2, String paramString);
/*    */   
/*    */   @Message("Parent object {0} cannot contain more than one object of type {1}: cannot add child {2}")
/*    */   @Log(id = 403)
/*    */   IllegalArgumentException childMustBeSingleton(ObjectName paramObjectName1, String paramString, ObjectName paramObjectName2);
/*    */   
/*    */   @Message("tried to register MBean {0} that is already registered")
/*    */   @Log(id = 404)
/*    */   void registerMBeanRegistered(ObjectName paramObjectName);
/*    */   
/*    */   @Message("tried to unregister MBean {0} that is not registered")
/*    */   @Log(id = 405)
/*    */   void unregisterMBeanNotRegistered(ObjectName paramObjectName);
/*    */   
/*    */   @StackTrace
/*    */   @Message("registering MBean {0}")
/*    */   @Log(id = 406, level = LogLevel.INFO)
/*    */   void registeringMBean(ObjectName paramObjectName);
/*    */   
/*    */   @StackTrace
/*    */   @Message("unregistering MBean {0}")
/*    */   @Log(id = 407, level = LogLevel.INFO)
/*    */   void unregisteringMBean(ObjectName paramObjectName);
/*    */   
/*    */   @Message("Got an unexpected exception from method {0}")
/*    */   @Log(id = 408)
/*    */   void unexpectedException(String paramString, @Chain Throwable paramThrowable);
/*    */   
/*    */   @Message("At least one of getter and setter must not be null")
/*    */   @Log(id = 501)
/*    */   IllegalArgumentException notBothNull();
/*    */   
/*    */   @Message("Getter and setter type must match")
/*    */   @Log(id = 502)
/*    */   IllegalArgumentException typesMustMatch();
/*    */   
/*    */   @Message("Methods {0} and {1} are both annotated with @ObjectNameKey in class {2}")
/*    */   @Log(id = 503)
/*    */   IllegalArgumentException duplicateObjectNameKeyAttributes(EvaluatedMethodDeclaration paramEvaluatedMethodDeclaration1, EvaluatedMethodDeclaration paramEvaluatedMethodDeclaration2, String paramString);
/*    */   
/*    */   @Message("ParameterNams annotation must have the same number of arguments as the length of the method parameter list")
/*    */   @Log(id = 504)
/*    */   IllegalArgumentException parameterNamesLengthBad();
/*    */   
/*    */   @Message("Could not find attribute {0}")
/*    */   @Log(id = 505)
/*    */   AttributeNotFoundException couldNotFindAttribute(String paramString);
/*    */   
/*    */   @Message("Could not find writable attribute {0}")
/*    */   @Log(id = 506)
/*    */   AttributeNotFoundException couldNotFindWritableAttribute(String paramString);
/*    */   
/*    */   @Message("Could not find operation named {0}")
/*    */   @Log(id = 507)
/*    */   IllegalArgumentException couldNotFindOperation(String paramString);
/*    */   
/*    */   @Message("Could not find operation named {0} with signature {1}")
/*    */   @Log(id = 508)
/*    */   IllegalArgumentException couldNotFindOperationAndSignature(String paramString, List<String> paramList);
/*    */   
/*    */   @Message("Name of this ManagedObject")
/*    */   String nameOfManagedObject();
/*    */   
/*    */   @Message("Error in setting attribute {0}")
/*    */   @Log(id = 509)
/*    */   void attributeSettingError(@Chain Exception paramException, String paramString);
/*    */   
/*    */   @Message("Error in getting attribute {0}")
/*    */   @Log(id = 510)
/*    */   void attributeGettingError(@Chain Exception paramException, String paramString);
/*    */   
/*    */   @Message("OpenDataException trying to create OpenMBEanParameterInfoSupport for parameter {0} on method {1}")
/*    */   @Log(id = 511)
/*    */   IllegalStateException excInOpenParameterInfo(@Chain IllegalArgumentException paramIllegalArgumentException, String paramString, EvaluatedMethodDeclaration paramEvaluatedMethodDeclaration);
/*    */   
/*    */   @Message("Exception on invoking annotation method {0}")
/*    */   @Log(id = 512, level = LogLevel.SEVERE)
/*    */   RuntimeException annotationMethodException(Method paramMethod, @Chain Exception paramException);
/*    */   
/*    */   @Message("Root has already been set: cannot set it again")
/*    */   @Log(id = 601)
/*    */   IllegalStateException rootAlreadySet();
/*    */   
/*    */   @Message("Could not construct ObjectName for root")
/*    */   @Log(id = 602)
/*    */   IllegalArgumentException noRootObjectName(@Chain Exception paramException);
/*    */   
/*    */   @Message("Could not register root with ObjectName {0}")
/*    */   @Log(id = 603)
/*    */   IllegalArgumentException rootRegisterFail(@Chain Exception paramException, ObjectName paramObjectName);
/*    */   
/*    */   @Message("Root has not been set")
/*    */   @Log(id = 604)
/*    */   IllegalStateException rootNotSet();
/*    */   
/*    */   @Message("rootParentName {0} is invalid: missing type or name")
/*    */   @Log(id = 605)
/*    */   GmbalException invalidRootParentName(ObjectName paramObjectName);
/*    */   
/*    */   @Message("Entity {0} is not part of this EntityTree")
/*    */   @Log(id = 606)
/*    */   IllegalArgumentException notPartOfThisTree(MBeanImpl paramMBeanImpl);
/*    */   
/*    */   @Message("Parent cannot be null")
/*    */   @Log(id = 607)
/*    */   IllegalArgumentException parentCannotBeNull();
/*    */   
/*    */   @Message("Parent object {0} not found")
/*    */   @Log(id = 608)
/*    */   IllegalArgumentException parentNotFound(Object paramObject);
/*    */   
/*    */   @Message("Object {0} is already registered as {1}")
/*    */   @Log(id = 609)
/*    */   IllegalArgumentException objectAlreadyRegistered(Object paramObject, MBeanImpl paramMBeanImpl);
/*    */   
/*    */   @Message("Should not happen")
/*    */   @Log(id = 610, level = LogLevel.FINE)
/*    */   void shouldNotHappen(@Chain Exception paramException);
/*    */   
/*    */   @Message("Object {0} not found")
/*    */   @Log(id = 611)
/*    */   IllegalArgumentException objectNotFound(Object paramObject);
/*    */   
/*    */   @Message("The ObjectName of the root parent MUST contain pp key")
/*    */   @Log(id = 612)
/*    */   IllegalArgumentException ppNullInRootParent();
/*    */   
/*    */   @Message("The ObjectName of the root parent MUST contain pp key")
/*    */   @Log(id = 613)
/*    */   IllegalArgumentException typeNullInRootParent();
/*    */   
/*    */   @Message("A MalformedObjectNameException occured on {0}")
/*    */   @Log(id = 613)
/*    */   IllegalArgumentException malformedObjectName(@Chain Exception paramException, String paramString);
/*    */   
/*    */   @Message("obj argument is a String: {0} : was a call to registerAtRoot intended here?")
/*    */   @Log(id = 701)
/*    */   IllegalArgumentException objStringWrongRegisterCall(String paramString);
/*    */   
/*    */   @Message("Exception in register")
/*    */   @Log(id = 702)
/*    */   IllegalArgumentException exceptionInRegister(@Chain Exception paramException);
/*    */   
/*    */   @Message("Exception in unregister")
/*    */   @Log(id = 703)
/*    */   IllegalArgumentException exceptionInUnregister(@Chain Exception paramException);
/*    */   
/*    */   @Message("Cannot add annotation to element {0}: an Annotation of type {1} is already present")
/*    */   @Log(id = 704)
/*    */   IllegalArgumentException duplicateAnnotation(AnnotatedElement paramAnnotatedElement, String paramString);
/*    */   
/*    */   @Message("Class {0} contains both the InheritedAttribute and the InheritedAttributes annotations")
/*    */   @Log(id = 705)
/*    */   IllegalArgumentException badInheritedAttributeAnnotation(EvaluatedClassDeclaration paramEvaluatedClassDeclaration);
/*    */   
/*    */   @Message("Field {0} must be final and have an immutable type to be used as an attribute")
/*    */   @Log(id = 705)
/*    */   IllegalArgumentException illegalAttributeField(EvaluatedFieldDeclaration paramEvaluatedFieldDeclaration);
/*    */   
/*    */   @Message("No description available!")
/*    */   String noDescriptionAvailable();
/*    */   
/*    */   @Message("Method {0} cannot be called before a successful createRoot call")
/*    */   @Log(id = 706)
/*    */   IllegalStateException createRootNotCalled(String paramString);
/*    */   
/*    */   @Message("Method {0} cannot be called after a successful createRoot call")
/*    */   @Log(id = 707)
/*    */   IllegalStateException createRootCalled(String paramString);
/*    */   
/*    */   @Message("Could not construct MBean {0}")
/*    */   @Log(id = 708)
/*    */   IllegalArgumentException errorInConstructingMBean(String paramString, @Chain JMException paramJMException);
/*    */   
/*    */   @Message("Attempt made to register non-singleton object of type {1} without a name as a child of {0}")
/*    */   @Log(id = 709)
/*    */   IllegalArgumentException nonSingletonRequiresName(MBeanImpl paramMBeanImpl, String paramString);
/*    */   
/*    */   @Message("Attempt made to register singleton object of type {1} with name {2} as a child of {0}")
/*    */   @Log(id = 710)
/*    */   IllegalArgumentException singletonCannotSpecifyName(MBeanImpl paramMBeanImpl, String paramString1, String paramString2);
/*    */   
/*    */   @Message("No {0} annotation found on {1}")
/*    */   @Log(id = 711)
/*    */   IllegalArgumentException noAnnotationFound(String paramString1, String paramString2);
/*    */   
/*    */   @Message("Cannot add null annotation to {0}")
/*    */   @Log(id = 712)
/*    */   IllegalArgumentException cannotAddNullAnnotation(AnnotatedElement paramAnnotatedElement);
/*    */   
/*    */   @Message("ManagedObject annotation not found on class {0}")
/*    */   @Log(id = 713)
/*    */   IllegalArgumentException managedObjectAnnotationNotFound(String paramString);
/*    */   
/*    */   @Message("Cannot call getAnnotations on {0}")
/*    */   @Log(id = 714)
/*    */   IllegalArgumentException annotationsNotSupported(AnnotatedElement paramAnnotatedElement);
/*    */   
/*    */   @Message("Unsupported OpenType {0}")
/*    */   @Log(id = 801)
/*    */   IllegalArgumentException unsupportedOpenType(OpenType paramOpenType);
/*    */   
/*    */   @Message("{0} cannot be converted into a Java class")
/*    */   @Log(id = 802)
/*    */   IllegalArgumentException cannotConvertToJavaType(EvaluatedType paramEvaluatedType);
/*    */   
/*    */   @Message("Management entity {0} is not an ObjectName")
/*    */   @Log(id = 803)
/*    */   IllegalArgumentException entityNotObjectName(Object paramObject);
/*    */   
/*    */   @Message("Arrays of arrays not supported")
/*    */   @Log(id = 804)
/*    */   IllegalArgumentException noArrayOfArray(@Chain Exception paramException);
/*    */   
/*    */   @Message("{0} is not a String")
/*    */   @Log(id = 805)
/*    */   IllegalArgumentException notAString(Object paramObject);
/*    */   
/*    */   @Message("There is no <init>(String) constructor available to convert a String into a {0}")
/*    */   @Log(id = 806)
/*    */   UnsupportedOperationException noStringConstructor(Class paramClass);
/*    */   
/*    */   @Message("Error in converting from String to {0}")
/*    */   @Log(id = 807)
/*    */   IllegalArgumentException stringConversionError(Class paramClass, @Chain Exception paramException);
/*    */   
/*    */   @Message("Exception in makeCompositeType")
/*    */   @Log(id = 808)
/*    */   IllegalArgumentException exceptionInMakeCompositeType(@Chain Exception paramException);
/*    */   
/*    */   @Message("Exception in handleManagedData")
/*    */   @Log(id = 809)
/*    */   IllegalArgumentException exceptionInHandleManagedData(@Chain Exception paramException);
/*    */   
/*    */   @Message("Remove is not supported")
/*    */   @Log(id = 810)
/*    */   UnsupportedOperationException removeNotSupported();
/*    */   
/*    */   @Message("Recursive types are not supported: type is {0}")
/*    */   @Log(id = 811)
/*    */   UnsupportedOperationException recursiveTypesNotSupported(EvaluatedType paramEvaluatedType);
/*    */   
/*    */   @Message("OpenType exception in ArrayType construction caused by {0}")
/*    */   @Log(id = 812)
/*    */   IllegalArgumentException openTypeInArrayTypeException(OpenType paramOpenType, @Chain Exception paramException);
/*    */   
/*    */   @Message("Exception in makeMapTabularType")
/*    */   @Log(id = 813)
/*    */   IllegalArgumentException exceptionInMakeMapTabularType(@Chain Exception paramException);
/*    */   
/*    */   @Message("row type for {0}")
/*    */   String rowTypeDescription(String paramString);
/*    */   
/*    */   @Message("Key of map {0}")
/*    */   String keyFieldDescription(String paramString);
/*    */   
/*    */   @Message("Value of map {0}")
/*    */   String valueFieldDescription(String paramString);
/*    */   
/*    */   @Message("Table:{0}")
/*    */   String tableName(String paramString);
/*    */   
/*    */   @Message("Table for map {0}")
/*    */   String tableDescription(String paramString);
/*    */   
/*    */   @Message("Exception in makeMapTabularData:toManagedEntity")
/*    */   @Log(id = 814)
/*    */   IllegalArgumentException excInMakeMapTabularDataToManagedEntity(@Chain Exception paramException);
/*    */   
/*    */   @Message("{0} must have at least 1 type argument")
/*    */   @Log(id = 815)
/*    */   IllegalArgumentException paramTypeNeedsArgument(ParameterizedType paramParameterizedType);
/*    */   
/*    */   @Message("Converting from OpenType {0} to Java type {1} is not supported")
/*    */   @Log(id = 816)
/*    */   UnsupportedOperationException openToJavaNotSupported(OpenType paramOpenType, EvaluatedType paramEvaluatedType);
/*    */   
/*    */   @Message("iterator() method not found in subclass of Iterable {0}")
/*    */   @Log(id = 817)
/*    */   IllegalStateException iteratorNotFound(EvaluatedClassDeclaration paramEvaluatedClassDeclaration);
/*    */   
/*    */   @Message("next() method not found in type {0}")
/*    */   @Log(id = 818)
/*    */   IllegalStateException nextNotFound(EvaluatedClassDeclaration paramEvaluatedClassDeclaration);
/*    */   
/*    */   @Message("Could not set field {1} in CompositeData for type {0}")
/*    */   @Log(id = 819, level = LogLevel.FINE)
/*    */   void errorInConstructingOpenData(String paramString1, String paramString2, @Chain JMException paramJMException);
/*    */   
/*    */   @Message("No <init>(String) constructor available for class {0}")
/*    */   @Log(id = 820, level = LogLevel.FINE)
/*    */   void noStringConstructorAvailable(@Chain Exception paramException, String paramString);
/*    */   
/*    */   @Message("JMX exception on registration of MBean {0}")
/*    */   @Log(id = 901)
/*    */   void deferredRegistrationException(@Chain JMException paramJMException, MBeanImpl paramMBeanImpl);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\Exceptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */