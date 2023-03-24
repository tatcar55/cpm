package com.sun.xml.ws.spi.db;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

public interface BindingContext {
  public static final String DEFAULT_NAMESPACE_REMAP = "com.sun.xml.bind.defaultNamespaceRemap";
  
  public static final String TYPE_REFERENCES = "com.sun.xml.bind.typeReferences";
  
  public static final String CANONICALIZATION_SUPPORT = "com.sun.xml.bind.c14n";
  
  public static final String TREAT_EVERYTHING_NILLABLE = "com.sun.xml.bind.treatEverythingNillable";
  
  public static final String ENABLE_XOP = "com.sun.xml.bind.XOP";
  
  public static final String SUBCLASS_REPLACEMENTS = "com.sun.xml.bind.subclassReplacements";
  
  public static final String XMLACCESSORFACTORY_SUPPORT = "com.sun.xml.bind.XmlAccessorFactory";
  
  public static final String RETAIN_REFERENCE_TO_INFO = "retainReferenceToInfo";
  
  Marshaller createMarshaller() throws JAXBException;
  
  Unmarshaller createUnmarshaller() throws JAXBException;
  
  JAXBContext getJAXBContext();
  
  Object newWrapperInstace(Class<?> paramClass) throws InstantiationException, IllegalAccessException;
  
  boolean hasSwaRef();
  
  @Nullable
  QName getElementName(@NotNull Object paramObject) throws JAXBException;
  
  @Nullable
  QName getElementName(@NotNull Class paramClass) throws JAXBException;
  
  XMLBridge createBridge(@NotNull TypeInfo paramTypeInfo);
  
  XMLBridge createFragmentBridge();
  
  <B, V> PropertyAccessor<B, V> getElementPropertyAccessor(Class<B> paramClass, String paramString1, String paramString2) throws JAXBException;
  
  @NotNull
  List<String> getKnownNamespaceURIs();
  
  void generateSchema(@NotNull SchemaOutputResolver paramSchemaOutputResolver) throws IOException;
  
  QName getTypeName(@NotNull TypeInfo paramTypeInfo);
  
  @NotNull
  String getBuildId();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\BindingContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */