package com.sun.xml.registry.common.tools;

import com.sun.xml.registry.common.tools.bindings_v3.JAXRClassificationScheme;
import com.sun.xml.registry.common.tools.bindings_v3.JAXRConcept;
import com.sun.xml.registry.common.tools.bindings_v3.Namepattern;
import com.sun.xml.registry.common.tools.bindings_v3.ObjectFactory;
import com.sun.xml.registry.common.tools.bindings_v3.PredefinedConcepts;
import com.sun.xml.registry.uddi.ConnectionImpl;
import com.sun.xml.registry.uddi.infomodel.ClassificationSchemeImpl;
import com.sun.xml.registry.uddi.infomodel.ConceptImpl;
import com.sun.xml.registry.uddi.infomodel.InternationalStringImpl;
import com.sun.xml.registry.uddi.infomodel.KeyImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JAXRConceptsManager {
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.common");
        }
      });
  
  static Locale US_LOCALE = new Locale("en", "US");
  
  static PredefinedConcepts predefines;
  
  static PredefinedConcepts naics;
  
  static PredefinedConcepts iso;
  
  static PredefinedConcepts unsp;
  
  static PredefinedConcepts user;
  
  static Collection definedSchemes;
  
  ArrayList fileList = new ArrayList();
  
  HashMap dMap = new HashMap<Object, Object>();
  
  String jaxrFile;
  
  String naicsFile;
  
  String isoFile;
  
  String predefinesDTD;
  
  HashMap idMap;
  
  ConnectionImpl connection;
  
  private static JAXRConceptsManager instance;
  
  private JAXBContext jc;
  
  private ObjectFactory objFactory;
  
  private Unmarshaller u;
  
  private static String taxonomyPath = "resources/";
  
  public static JAXRConceptsManager getInstance(ConnectionImpl paramConnectionImpl) {
    try {
      if (instance == null)
        instance = new JAXRConceptsManager(paramConnectionImpl); 
      instance.loadTaxonomies();
      definedSchemes = instance.taxonomies2TaxonomyTree();
    } catch (JAXRException jAXRException) {
      System.out.println("Failed to load taxonomies");
      jAXRException.printStackTrace();
    } 
    return instance;
  }
  
  private JAXRConceptsManager(ConnectionImpl paramConnectionImpl) {
    this.connection = paramConnectionImpl;
    this.idMap = new HashMap<Object, Object>();
    initJAXBObjectFactory();
  }
  
  private void initJAXBObjectFactory() {
    try {
      if (this.jc == null)
        this.jc = JAXBContext.newInstance("com.sun.xml.registry.common.tools.bindings_v3"); 
    } catch (JAXBException jAXBException) {
      System.out.println("Exiting unable to initial JAXB context");
    } 
    if (this.objFactory == null)
      this.objFactory = new ObjectFactory(); 
    try {
      this.u = this.jc.createUnmarshaller();
    } catch (JAXBException jAXBException) {}
  }
  
  public Collection findClassificationSchemeByName(Collection paramCollection, String paramString) throws JAXRException {
    return getClassificationSchemeByName(paramCollection, paramString);
  }
  
  public Concept findConceptByPath(String paramString) throws JAXRException {
    return getConceptsByPath2(paramString);
  }
  
  public Collection findClassificationSchemes(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    return doFindClassificationSchemes(paramCollection1, paramCollection2);
  }
  
  public Collection getChildConcepts(ClassificationScheme paramClassificationScheme) throws JAXRException {
    return (paramClassificationScheme != null) ? paramClassificationScheme.getChildrenConcepts() : null;
  }
  
  Collection stringNames2Namepatterns(Collection paramCollection) {
    ArrayList<Namepattern> arrayList = new ArrayList();
    Iterator<E> iterator = paramCollection.iterator();
    Namepattern namepattern = null;
    while (iterator.hasNext()) {
      namepattern = this.objFactory.createNamepattern();
      namepattern.setContent(iterator.next().toString());
      arrayList.add(namepattern);
    } 
    return arrayList;
  }
  
  private JAXRClassificationScheme classificationScheme2JAXRClassificationScheme(ClassificationScheme paramClassificationScheme) throws JAXRException {
    if (paramClassificationScheme != null) {
      this.logger.finest("Scheme is not null");
      String str1 = paramClassificationScheme.getKey().getId();
      String str2 = null;
      InternationalString internationalString = paramClassificationScheme.getName();
      if (internationalString != null)
        str2 = internationalString.getValue(); 
      Object object = null;
      JAXRClassificationScheme jAXRClassificationScheme = null;
      jAXRClassificationScheme = this.objFactory.createJAXRClassificationScheme();
      jAXRClassificationScheme.setId(str1);
      jAXRClassificationScheme.setName(str2);
      if (jAXRClassificationScheme != null)
        this.logger.finest("jaxrScheme is not null in cs2jrcl"); 
      return jAXRClassificationScheme;
    } 
    return null;
  }
  
  Collection jaxrClassificationSchemes2ClassificationSchemes(Collection paramCollection) throws JAXRException {
    ArrayList<ClassificationSchemeImpl> arrayList = new ArrayList();
    if (paramCollection != null) {
      Iterator<JAXRClassificationScheme> iterator = paramCollection.iterator();
      ClassificationSchemeImpl classificationSchemeImpl = null;
      while (iterator.hasNext()) {
        JAXRClassificationScheme jAXRClassificationScheme = iterator.next();
        String str1 = jAXRClassificationScheme.getId();
        String str2 = jAXRClassificationScheme.getName();
        String str3 = jAXRClassificationScheme.getDescription();
        classificationSchemeImpl = new ClassificationSchemeImpl((Key)new KeyImpl(str1));
        classificationSchemeImpl.setName((InternationalString)new InternationalStringImpl(US_LOCALE, str2));
        classificationSchemeImpl.setDescription((InternationalString)new InternationalStringImpl(US_LOCALE, str3));
        classificationSchemeImpl.setPredefined(true);
        this.idMap.put(classificationSchemeImpl.getKey().getId(), classificationSchemeImpl);
        arrayList.add(classificationSchemeImpl);
      } 
    } 
    return arrayList;
  }
  
  ClassificationScheme jaxrClassificationScheme2ClassificationScheme(JAXRClassificationScheme paramJAXRClassificationScheme) throws JAXRException {
    ClassificationSchemeImpl classificationSchemeImpl = null;
    if (paramJAXRClassificationScheme != null) {
      String str1 = paramJAXRClassificationScheme.getId();
      String str2 = paramJAXRClassificationScheme.getName();
      String str3 = paramJAXRClassificationScheme.getDescription();
      classificationSchemeImpl = new ClassificationSchemeImpl((Key)new KeyImpl(str1));
      classificationSchemeImpl.setName((InternationalString)new InternationalStringImpl(US_LOCALE, str2));
      classificationSchemeImpl.setDescription((InternationalString)new InternationalStringImpl(US_LOCALE, str3));
      classificationSchemeImpl.setPredefined(true);
      this.idMap.put(classificationSchemeImpl.getKey().getId(), classificationSchemeImpl);
    } 
    return (ClassificationScheme)classificationSchemeImpl;
  }
  
  Collection jaxrConcepts2Concepts(ClassificationScheme paramClassificationScheme, Collection paramCollection) throws JAXRException {
    ArrayList<ConceptImpl> arrayList = new ArrayList();
    this.idMap.clear();
    ClassificationSchemeImpl classificationSchemeImpl1 = null;
    ClassificationSchemeImpl classificationSchemeImpl2 = (ClassificationSchemeImpl)paramClassificationScheme;
    if (paramClassificationScheme != null) {
      classificationSchemeImpl2.setPredefined(true);
      this.idMap.put(classificationSchemeImpl2.getKey().getId(), classificationSchemeImpl2);
      if (paramCollection != null) {
        Iterator<JAXRConcept> iterator = paramCollection.iterator();
        while (iterator.hasNext()) {
          ConceptImpl conceptImpl = null;
          JAXRConcept jAXRConcept = iterator.next();
          String str1 = jAXRConcept.getId();
          String str2 = jAXRConcept.getName();
          String str3 = jAXRConcept.getCode();
          String str4 = jAXRConcept.getParent();
          conceptImpl = new ConceptImpl();
          conceptImpl.setKey((Key)new KeyImpl(str1));
          conceptImpl.setIsRetrieved(true);
          conceptImpl.setIsLoaded(true);
          conceptImpl.setName((InternationalString)new InternationalStringImpl(US_LOCALE, str2));
          conceptImpl.setValue(str3);
          conceptImpl.setPredefined(true);
          this.idMap.put(str1, conceptImpl);
          Object object = this.idMap.get(str4);
          if (object == null)
            arrayList.add(conceptImpl); 
          if (object instanceof ClassificationSchemeImpl) {
            classificationSchemeImpl1 = (ClassificationSchemeImpl)object;
            conceptImpl.setClassificationScheme((ClassificationScheme)classificationSchemeImpl1);
            classificationSchemeImpl1.addChildConcept((Concept)conceptImpl);
            continue;
          } 
          if (object instanceof ConceptImpl) {
            ConceptImpl conceptImpl1 = (ConceptImpl)object;
            conceptImpl.setParentConcept((Concept)conceptImpl);
            conceptImpl1.addChildConcept((Concept)conceptImpl);
          } 
        } 
        if (classificationSchemeImpl1 != null) {
          classificationSchemeImpl1.setChildrenLoaded(true);
          arrayList.addAll(classificationSchemeImpl1.getChildrenConcepts());
        } 
      } 
    } 
    return arrayList;
  }
  
  ClassificationScheme jaxrConcepts2Concepts2(ClassificationScheme paramClassificationScheme, Collection paramCollection) throws JAXRException {
    ArrayList arrayList = new ArrayList();
    ClassificationSchemeImpl classificationSchemeImpl = (ClassificationSchemeImpl)paramClassificationScheme;
    if (paramClassificationScheme != null) {
      classificationSchemeImpl.setPredefined(true);
      this.idMap.put(classificationSchemeImpl.getKey().getId(), classificationSchemeImpl);
      if (paramCollection != null)
        for (JAXRConcept jAXRConcept : paramCollection) {
          Collection collection;
          String str1 = jAXRConcept.getId();
          String str2 = jAXRConcept.getName();
          String str3 = jAXRConcept.getCode();
          String str4 = jAXRConcept.getParent();
          List list = jAXRConcept.getJAXRConcept();
          ConceptImpl conceptImpl = new ConceptImpl();
          conceptImpl.setKey((Key)new KeyImpl(str1));
          conceptImpl.setIsRetrieved(true);
          conceptImpl.setIsLoaded(true);
          conceptImpl.setName((InternationalString)new InternationalStringImpl(US_LOCALE, str2));
          conceptImpl.setValue(str3);
          conceptImpl.setPredefined(true);
          if (list != null && !list.isEmpty())
            collection = jaxrChildConcepts2ConceptCollection(list, conceptImpl); 
          if (collection != null)
            for (Concept concept : collection)
              classificationSchemeImpl.addChildConcept(concept);  
        }  
    } 
    return (ClassificationScheme)classificationSchemeImpl;
  }
  
  Collection jaxrChildConcepts2ConceptCollection(Collection paramCollection, ConceptImpl paramConceptImpl) throws JAXRException {
    ArrayList<ConceptImpl> arrayList = new ArrayList();
    for (JAXRConcept jAXRConcept : paramCollection) {
      String str1 = jAXRConcept.getName();
      String str2 = jAXRConcept.getParent();
      String str3 = jAXRConcept.getId();
      String str4 = jAXRConcept.getCode();
      List list = jAXRConcept.getJAXRConcept();
      ConceptImpl conceptImpl = new ConceptImpl();
      conceptImpl.setKey((Key)new KeyImpl(str3));
      conceptImpl.setIsRetrieved(true);
      conceptImpl.setIsLoaded(true);
      conceptImpl.setName((InternationalString)new InternationalStringImpl(US_LOCALE, str1));
      conceptImpl.setParentConcept((Concept)paramConceptImpl);
      conceptImpl.setValue(str4);
      if (list != null)
        Collection collection = jaxrChildConcepts2ConceptCollection(list, conceptImpl); 
      if (conceptImpl != null) {
        arrayList.add(conceptImpl);
        paramConceptImpl.addChildConcept((Concept)conceptImpl);
      } 
    } 
    return arrayList;
  }
  
  Collection keysFromJAXRObjects(Collection paramCollection) {
    ArrayList<KeyImpl> arrayList = new ArrayList();
    for (JAXRClassificationScheme jAXRClassificationScheme : paramCollection) {
      if (jAXRClassificationScheme instanceof JAXRClassificationScheme) {
        KeyImpl keyImpl1 = new KeyImpl(((JAXRClassificationScheme)jAXRClassificationScheme).getId());
        arrayList.add(keyImpl1);
        continue;
      } 
      KeyImpl keyImpl = new KeyImpl(((JAXRConcept)jAXRClassificationScheme).getParent());
      arrayList.add(keyImpl);
    } 
    return arrayList;
  }
  
  DocumentBuilder createDocumentBuilder() {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    documentBuilderFactory.setNamespaceAware(false);
    documentBuilderFactory.setValidating(false);
    try {
      return documentBuilderFactory.newDocumentBuilder();
    } catch (ParserConfigurationException parserConfigurationException) {
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException(parserConfigurationException.getMessage());
      illegalArgumentException.initCause(parserConfigurationException);
      throw illegalArgumentException;
    } 
  }
  
  Collection getAllClassificationSchemes() {
    Collection collection = this.dMap.values();
    ArrayList arrayList = new ArrayList();
    Iterator<PredefinedConcepts> iterator = collection.iterator();
    List list = null;
    while (iterator.hasNext()) {
      PredefinedConcepts predefinedConcepts = iterator.next();
      list = predefinedConcepts.getJAXRClassificationScheme();
      Iterator iterator1 = list.iterator();
    } 
    return list;
  }
  
  Collection getClassificationSchemeByName(Collection paramCollection, String paramString) throws JAXRException {
    classificationScheme = null;
    ArrayList<ClassificationScheme> arrayList = new ArrayList();
    for (ClassificationScheme classificationScheme : definedSchemes) {
      String str1 = classificationScheme.getName().getValue(US_LOCALE);
      String str2 = paramString.toUpperCase();
      if (paramString.indexOf(str1) != -1 || str1.indexOf(paramString) != -1 || str1.equalsIgnoreCase(paramString)) {
        arrayList.add(classificationScheme);
        return arrayList;
      } 
      if (str2.indexOf(str1) != -1 || str1.indexOf(str2) != -1 || str1.equalsIgnoreCase(str2)) {
        arrayList.add(classificationScheme);
        return arrayList;
      } 
      byte b = 92;
      if (paramString.indexOf("%") == -1) {
        String str = paramString + "%";
        if (matchPattern(str, str1, b)) {
          arrayList.add(classificationScheme);
          continue;
        } 
        str = "%" + paramString;
        if (matchPattern(str, str1, b)) {
          arrayList.add(classificationScheme);
          continue;
        } 
        str = "%" + paramString + "%";
        if (matchPattern(str, str1, b)) {
          arrayList.add(classificationScheme);
          continue;
        } 
        str = str2 + "%";
        if (matchPattern(str, str1, b)) {
          arrayList.add(classificationScheme);
          continue;
        } 
        str = "%" + str2;
        if (matchPattern(str, str1, b)) {
          arrayList.add(classificationScheme);
          continue;
        } 
        str = "%" + str2 + "%";
        if (matchPattern(str, str1, b))
          arrayList.add(classificationScheme); 
        continue;
      } 
      if (matchPattern(paramString, str1, b)) {
        arrayList.add(classificationScheme);
        continue;
      } 
      if (matchPattern(str2, str1, b))
        arrayList.add(classificationScheme); 
    } 
    return arrayList;
  }
  
  public ClassificationScheme getClassificationSchemeById(String paramString) throws JAXRException {
    for (ClassificationScheme classificationScheme : definedSchemes) {
      String str = classificationScheme.getKey().getId();
      if (str.equalsIgnoreCase(paramString))
        return classificationScheme; 
    } 
    return null;
  }
  
  Concept getConceptById(Collection paramCollection, String paramString) throws JAXRException {
    for (Concept concept1 : paramCollection) {
      if (concept1.getKey().getId().equalsIgnoreCase(paramString))
        return concept1; 
      Collection collection = concept1.getChildrenConcepts();
      Concept concept2 = null;
      if (collection != null)
        concept2 = getConceptById(collection, paramString); 
      if (concept2 != null)
        return concept2; 
    } 
    return null;
  }
  
  public Concept getConceptById(String paramString) throws JAXRException {
    this.logger.finest("Id is " + paramString);
    if (paramString == null)
      return null; 
    for (ClassificationScheme classificationScheme : definedSchemes) {
      Collection collection = classificationScheme.getChildrenConcepts();
      if (collection != null)
        for (Concept concept1 : collection) {
          if (concept1.getKey().getId().equalsIgnoreCase(paramString))
            return concept1; 
          Collection collection1 = concept1.getChildrenConcepts();
          Concept concept2 = null;
          if (collection1 != null)
            concept2 = getConceptById(collection1, paramString); 
          if (concept2 != null)
            return concept2; 
        }  
    } 
    return null;
  }
  
  JAXRClassificationScheme getClassificationSchemeForConcept(JAXRConcept paramJAXRConcept) {
    String str = paramJAXRConcept.getParent();
    Collection collection = getAllClassificationSchemes();
    for (JAXRClassificationScheme jAXRClassificationScheme : collection) {
      if (str.equals(jAXRClassificationScheme.getId()))
        return jAXRClassificationScheme; 
    } 
    return null;
  }
  
  boolean hasChildren(Collection paramCollection, String paramString) {
    for (JAXRConcept jAXRConcept : paramCollection) {
      String str = jAXRConcept.getParent();
      if (str.equals(paramString))
        return true; 
    } 
    return false;
  }
  
  boolean hasChildrenConcepts(JAXRClassificationScheme paramJAXRClassificationScheme) {
    List list = paramJAXRClassificationScheme.getJAXRConcept();
    return (list != null) ? ((list.size() > 0)) : false;
  }
  
  Concept getConceptsByPath2(String paramString) throws JAXRException {
    String str1 = "*";
    String str2 = "//";
    String str3 = "/";
    boolean bool = false;
    String str4 = null;
    ArrayList<String> arrayList = new ArrayList();
    if (paramString != null) {
      StringTokenizer stringTokenizer = new StringTokenizer(paramString, str3, true);
      boolean bool1 = true;
      while (stringTokenizer.hasMoreTokens()) {
        str4 = stringTokenizer.nextToken();
        arrayList.add(str4);
      } 
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("JAXRConceptsManager:Path_is_null"));
    } 
    Concept concept = null;
    byte b = 0;
    int i = arrayList.size();
    if (b < i) {
      String str = arrayList.get(b);
      b++;
      if (str.equals("/")) {
        String str5 = arrayList.get(b);
        b++;
        ClassificationScheme classificationScheme = getClassificationSchemeById(str5);
        if (classificationScheme == null)
          return null; 
        this.logger.finest("scheme.getName " + classificationScheme.getName().getValue());
        Collection collection = classificationScheme.getChildrenConcepts();
        while (b < i) {
          String str6 = arrayList.get(b);
          if (str6.equals("/") && ++b < i) {
            String str7 = arrayList.get(b);
            b++;
            if (collection != null) {
              concept = getConceptByValue(collection, str7);
              if (concept != null) {
                this.logger.finest("FirstConcept with value" + concept.getValue());
                collection = concept.getChildrenConcepts();
                if (collection == null)
                  this.logger.finest("children are null for " + concept.getValue()); 
              } 
            } 
          } 
        } 
        return concept;
      } 
    } 
    return null;
  }
  
  Concept getConceptByValue(Collection paramCollection, String paramString) throws JAXRException {
    for (Concept concept : paramCollection) {
      String str = concept.getValue();
      if (str != null && str.equalsIgnoreCase(paramString))
        return concept; 
    } 
    return null;
  }
  
  Concept findConceptByValue(RegistryObject paramRegistryObject, String paramString) throws JAXRException {
    Collection collection = null;
    if (paramRegistryObject instanceof ClassificationScheme) {
      collection = ((ClassificationScheme)paramRegistryObject).getChildrenConcepts();
    } else if (paramRegistryObject instanceof Concept) {
      collection = ((Concept)paramRegistryObject).getChildrenConcepts();
    } 
    concept = null;
    if (collection != null) {
      for (Concept concept : collection) {
        this.logger.finest("Concept in find by value" + concept.getValue());
        String str = concept.getValue();
        if (str != null && str.equalsIgnoreCase(paramString))
          return concept; 
      } 
    } else {
      this.logger.finest("Children are null");
    } 
    return null;
  }
  
  Collection getConceptsByPath(String paramString, char paramChar) throws ParserConfigurationException {
    String str1 = String.valueOf(paramChar);
    String str2 = "*";
    String str3 = "//";
    String str4 = "/";
    String str5 = str4 + str1;
    boolean bool = false;
    String str6 = null;
    ArrayList<String> arrayList = new ArrayList();
    if (paramString != null) {
      StringTokenizer stringTokenizer = new StringTokenizer(paramString, str5, true);
      boolean bool1 = true;
      while (stringTokenizer.hasMoreTokens()) {
        str6 = stringTokenizer.nextToken();
        arrayList.add(str6);
      } 
    } 
    ArrayList arrayList1 = new ArrayList();
    PredefinedConcepts predefinedConcepts = (PredefinedConcepts)this.dMap.get(naics);
    Element element1 = null;
    byte b = 0;
    Object object = null;
    str6 = arrayList.get(b);
    Element element2 = null;
    while (b <= arrayList.size()) {
      String str = null;
      if (!str6.equals(str4) || ++b >= arrayList.size())
        break; 
      str = arrayList.get(b);
      if (str.equals(str4)) {
        if (++b >= arrayList.size())
          break; 
        str6 = arrayList.get(b);
        if (str6.indexOf("uuid:") != -1) {
          element1 = findElementByUUID(str6, element1);
        } else {
          if (element2 == null)
            element2 = element1; 
          element1 = findElementByCode(str6, element2);
        } 
        if (++b >= arrayList.size())
          break; 
        str6 = arrayList.get(b);
        continue;
      } 
      if (str.equals(str2)) {
        if (str.indexOf("uuid:") != -1) {
          element1 = findElementByUUID(str, element1);
        } else {
          if (element2 == null)
            element2 = element1; 
          element1 = findElementByCode(str, element2);
        } 
        if (++b >= arrayList.size())
          break; 
        str6 = arrayList.get(b);
        continue;
      } 
      if (str.indexOf("uuid:") != -1) {
        element1 = findElementByUUID(str, element1);
      } else {
        if (element2 == null)
          element2 = element1; 
        element1 = findElementByCode(str, element2);
      } 
      if (++b >= arrayList.size())
        break; 
      str6 = arrayList.get(b);
    } 
    if (element1 != null) {
      Object object1 = null;
      String str = element1.getTagName();
      if (str.equals("JAXRConcept")) {
        if (object1 == null)
          this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("JAXRConceptsManager:jconcept_is_null")); 
        arrayList1.add(object1);
        return arrayList1;
      } 
    } 
    return null;
  }
  
  Element findElementByUUID(String paramString, Element paramElement) {
    NodeList nodeList = paramElement.getElementsByTagName("JAXRClassificationScheme");
    for (byte b = 0; b < nodeList.getLength(); b++) {
      Node node = nodeList.item(b);
      String str1 = ((Element)node).getTagName();
      String str2 = ((Element)node).getAttribute("id");
      if (str2.equals(paramString)) {
        String str = ((Element)node).getAttribute("name");
        return (Element)node;
      } 
    } 
    return null;
  }
  
  Element findElementByCode(String paramString, Element paramElement) {
    NodeList nodeList = paramElement.getElementsByTagName("JAXRConcept");
    for (byte b = 0; b < nodeList.getLength(); b++) {
      Node node = nodeList.item(b);
      String str1 = ((Element)node).getTagName();
      String str2 = ((Element)node).getAttribute("code");
      if (str2.equals(paramString)) {
        String str = ((Element)node).getAttribute("name");
        return (Element)node;
      } 
    } 
    return null;
  }
  
  boolean matchPattern(String paramString1, String paramString2, char paramChar) {
    boolean bool1 = false;
    String str1 = String.valueOf(paramChar);
    String str2 = "_%";
    String str3 = str2 + str1;
    boolean bool2 = false;
    int i = 0;
    String str4 = null;
    boolean bool3 = false;
    try {
      if (paramString2 != null) {
        StringTokenizer stringTokenizer = new StringTokenizer(paramString1, str3, true);
        ArrayList<String> arrayList = new ArrayList();
        byte b1 = 1;
        while (stringTokenizer.hasMoreTokens()) {
          str4 = stringTokenizer.nextToken();
          if (bool3)
            this.logger.finest(b1++ + " matchPattern Token=" + str4); 
          arrayList.add(str4);
        } 
        bool1 = true;
        int j = arrayList.size();
        for (byte b2 = 0; b2 < j; b2++) {
          str4 = arrayList.get(b2);
          if (str4.equals(str1) && !bool2) {
            bool2 = true;
          } else if (str4.equals("%") && !bool2) {
            if (b2 == j - 1) {
              i = paramString2.length();
            } else if (b2 != j - 1) {
              byte b = 0;
              while (++b2 < j) {
                str4 = arrayList.get(b2);
                if (str4.equals(str1) && !bool2) {
                  bool2 = true;
                } else if (!str4.equals("%") || bool2) {
                  if (str4.equals("_") && !bool2) {
                    b++;
                  } else {
                    int k = i;
                    if (b2 == j - 1) {
                      if (paramString2.endsWith(str4)) {
                        i = paramString2.length() - str4.length();
                      } else {
                        bool1 = false;
                        if (bool3)
                          this.logger.finest("no matched5 for token: '" + str4 + "'"); 
                      } 
                    } else {
                      i = paramString2.indexOf(str4, i);
                    } 
                    if (i < 0) {
                      bool1 = false;
                      if (bool3)
                        this.logger.finest("no matched1 for token: '" + str4 + "'"); 
                    } else if (i - k >= b) {
                      i += str4.length();
                      if (bool3)
                        this.logger.finest("matched1: " + paramString2.substring(0, i)); 
                    } else {
                      bool1 = false;
                      if (bool3)
                        this.logger.finest("no matched 2 for token: '" + str4 + "'"); 
                    } 
                    bool2 = false;
                    break;
                  } 
                } 
                b2++;
              } 
            } 
          } else if (str4.equals("_") && !bool2) {
            i++;
            if (bool3)
              this.logger.finest("matched2: " + paramString2.substring(0, i)); 
          } else {
            int k = str4.length();
            if (bool3)
              this.logger.finest(i + " " + k); 
            if (i + k <= paramString2.length()) {
              String str = null;
              try {
                str = paramString2.substring(i, i + k);
              } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                bool1 = false;
                break;
              } 
              if (!str.equalsIgnoreCase(str4)) {
                bool1 = false;
                if (bool3)
                  this.logger.finest("no matched3 for token: '" + str4 + "'"); 
                break;
              } 
              i += str4.length();
              if (bool3)
                this.logger.finest("matched3: " + paramString2.substring(0, i)); 
            } else {
              bool1 = false;
              if (bool3)
                this.logger.finest("no matched4 for token: '" + str4 + "'"); 
              break;
            } 
            bool2 = false;
          } 
        } 
      } 
      if (bool1 && i != paramString2.length()) {
        if (bool3)
          this.logger.finest("no match5(remainder): " + paramString2.substring(i, paramString2.length())); 
        bool1 = false;
      } 
      return bool1;
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      bool1 = false;
      return bool1;
    } finally {
      Exception exception = null;
    } 
  }
  
  Collection doFindClassificationSchemes(Collection paramCollection1, Collection paramCollection2) throws JAXRException {
    Collection collection = null;
    for (String str : paramCollection2)
      collection = getClassificationSchemeByName(paramCollection1, str); 
    return collection;
  }
  
  void loadTaxonomies() throws JAXRException {
    String str1 = taxonomyPath + "naics.xml";
    String str2 = taxonomyPath + "iso3166.xml";
    String str3 = taxonomyPath + "unspsc.xml";
    String str4 = taxonomyPath + "jaxrconcepts.xml";
    try {
      this.fileList.add(str1);
      this.fileList.add(str2);
      this.fileList.add(str3);
      this.fileList.add(str4);
      String str = this.connection.getUserDefinedTaxonomy();
      if (str != null) {
        if (this.logger.isLoggable(Level.FINEST))
          this.logger.finest("Parsing user defined taxonomy filenames"); 
        final String filename = " ";
        StringTokenizer stringTokenizer1 = new StringTokenizer(str, str5);
        StringBuffer stringBuffer = new StringBuffer();
        while (stringTokenizer1.hasMoreElements())
          stringBuffer.append(stringTokenizer1.nextToken()); 
        str = stringBuffer.toString();
        this.logger.finest(str);
        StringTokenizer stringTokenizer2 = new StringTokenizer(str, "|");
        while (stringTokenizer2.hasMoreElements()) {
          String str6 = stringTokenizer2.nextToken();
          if (!this.fileList.contains(str6)) {
            if (this.logger.isLoggable(Level.FINEST))
              this.logger.finest("Adding filename to list to load: " + str6); 
            this.fileList.add(str6);
          } 
        } 
      } 
      for (String str5 : this.fileList) {
        if (this.logger.isLoggable(Level.FINEST))
          this.logger.finest("Filename is " + str5); 
        InputStream inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
              public Object run() {
                return getClass().getResourceAsStream(filename);
              }
            });
        if (inputStream == null) {
          this.logger.finest("Could not load input stream. Try file lookup.");
          try {
            inputStream = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
                  public Object run() {
                    try {
                      return new FileInputStream(filename);
                    } catch (FileNotFoundException fileNotFoundException) {
                      fileNotFoundException.printStackTrace();
                      return null;
                    } 
                  }
                });
          } catch (Throwable throwable) {
            this.logger.log(Level.FINEST, throwable.getMessage(), throwable);
          } 
        } 
        if (inputStream == null) {
          this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("JAXRConceptsManager:Could_not_load_file:_") + str5);
          continue;
        } 
        PredefinedConcepts predefinedConcepts = (PredefinedConcepts)this.u.unmarshal(inputStream);
        List<JAXRClassificationScheme> list = predefinedConcepts.getJAXRClassificationScheme();
        if (list.size() > 0) {
          for (byte b = 0; b < list.size(); b++) {
            JAXRClassificationScheme jAXRClassificationScheme = list.get(b);
            this.dMap.put(str5 + b, jAXRClassificationScheme);
            this.logger.finest("Got Scheme Success" + str5 + " ");
          } 
          this.logger.finest("Got Scheme Success");
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("JAXRConceptsManager:Cannot_initialize:_") + exception.getMessage(), exception);
    } 
  }
  
  Collection taxonomies2TaxonomyTree() throws JAXRException {
    Collection collection = this.dMap.values();
    ArrayList<ClassificationScheme> arrayList = new ArrayList();
    for (JAXRClassificationScheme jAXRClassificationScheme : collection) {
      List list = jAXRClassificationScheme.getJAXRConcept();
      ClassificationScheme classificationScheme = jaxrClassificationScheme2ClassificationScheme(jAXRClassificationScheme);
      if (classificationScheme.getName().getValue(US_LOCALE).indexOf("unspsc") != -1) {
        classificationScheme = jaxrConcepts2Concepts2(classificationScheme, list);
      } else {
        Collection collection1 = jaxrConcepts2Concepts(classificationScheme, list);
      } 
      arrayList.add(classificationScheme);
    } 
    return arrayList;
  }
  
  void taxonomyTree2TaxonomyFile() throws JAXRException {
    if (definedSchemes != null) {
      this.logger.finest("Have DefinedSchemes");
      for (ClassificationScheme classificationScheme : definedSchemes)
        JAXRClassificationScheme jAXRClassificationScheme = scheme2JAXRSchemeTree(classificationScheme); 
    } 
  }
  
  JAXRClassificationScheme scheme2JAXRSchemeTree(ClassificationScheme paramClassificationScheme) throws JAXRException {
    if (paramClassificationScheme != null) {
      JAXRClassificationScheme jAXRClassificationScheme = classificationScheme2JAXRClassificationScheme(paramClassificationScheme);
      Collection collection1 = paramClassificationScheme.getChildrenConcepts();
      Collection collection2 = concepts2JAXRConceptsTree(jAXRClassificationScheme.getId(), collection1);
      if (collection2 != null)
        jAXRClassificationScheme.getJAXRConcept().addAll(collection2); 
      return jAXRClassificationScheme;
    } 
    return null;
  }
  
  Collection concepts2JAXRConceptsTree(String paramString, Collection paramCollection) throws JAXRException {
    ArrayList<JAXRConcept> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      for (Concept concept : paramCollection) {
        String str1 = concept.getKey().getId();
        String str2 = concept.getName().getValue();
        String str3 = null;
        try {
          str3 = concept.getValue();
        } catch (Exception exception) {
          str3 = "";
        } 
        Collection collection1 = concept.getChildrenConcepts();
        JAXRConcept jAXRConcept = null;
        jAXRConcept.setName(str2);
        jAXRConcept.setId(str1);
        jAXRConcept.setCode(str3);
        jAXRConcept.setParent(paramString);
        Collection collection2 = null;
        if (collection1 != null) {
          collection2 = concepts2JAXRConceptsTree(str1, collection1);
          if (collection2 != null && collection2.size() > 0)
            jAXRConcept.getJAXRConcept().addAll(collection2); 
        } 
        if (jAXRConcept != null)
          arrayList.add(jAXRConcept); 
      } 
    } 
    return arrayList;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\JAXRConceptsManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */