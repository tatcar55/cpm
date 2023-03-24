/*     */ package com.sun.xml.rpc.processor.model.exporter;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderException;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactoryImpl;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.zip.GZIPInputStream;
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
/*     */ public abstract class ImporterBase
/*     */ {
/*     */   protected InputStream in;
/*     */   protected XMLReader reader;
/*     */   protected Map id2obj;
/*     */   protected Set immediateClassNames;
/*     */   protected String targetModelVersion;
/*     */   
/*     */   public ImporterBase(InputStream s) {
/* 524 */     this.targetModelVersion = ""; this.in = s; try { this.reader = (new XMLReaderFactoryImpl()).createXMLReader(new GZIPInputStream(s)); } catch (IOException e) { throw new XMLReaderException("xmlreader.ioException", new LocalizableExceptionAdapter(e)); } 
/*     */   }
/* 526 */   protected void initialize() { this.id2obj = new HashMap<Object, Object>(); this.id2obj.put(new Integer(0), null); this.immediateClassNames = new HashSet(); this.immediateClassNames.add("java.lang.Boolean"); this.immediateClassNames.add("java.lang.Integer"); this.immediateClassNames.add("java.lang.Short"); this.immediateClassNames.add("java.lang.Long"); this.immediateClassNames.add("java.lang.Float"); this.immediateClassNames.add("java.lang.Double"); this.immediateClassNames.add("java.lang.Byte"); this.immediateClassNames.add("[I"); this.immediateClassNames.add("[B"); this.immediateClassNames.add("[Ljava.lang.String;"); this.immediateClassNames.add("java.util.GregorianCalendar"); this.immediateClassNames.add("java.lang.String"); this.immediateClassNames.add("java.math.BigDecimal"); this.immediateClassNames.add("java.math.BigInteger"); this.immediateClassNames.add("java.util.ArrayList"); this.immediateClassNames.add("java.util.HashSet"); this.immediateClassNames.add("java.util.HashMap"); this.immediateClassNames.add("javax.xml.namespace.QName"); this.immediateClassNames.add("com.sun.xml.rpc.wsdl.document.soap.SOAPStyle"); this.immediateClassNames.add("com.sun.xml.rpc.wsdl.document.soap.SOAPUse"); this.immediateClassNames.add("com.sun.xml.rpc.soap.SOAPVersion"); this.immediateClassNames.add("java.net.URI"); } protected Object internalDoImport() { initialize(); this.reader.nextElementContent(); if (this.reader.getState() != 1) failInvalidSyntax(this.reader);  if (!this.reader.getName().equals(getContainerName())) failInvalidSyntax(this.reader);  checkVersion(); while (this.reader.nextElementContent() != 2) { if (this.reader.getName().equals(getDefineImmediateObjectName())) { parseDefineImmediateObject(this.reader); continue; }  if (this.reader.getName().equals(getDefineObjectName())) { parseDefineObject(this.reader); continue; }  if (this.reader.getName().equals(getPropertyName())) { parseProperty(this.reader); continue; }  failInvalidSyntax(this.reader); }  XMLReaderUtil.verifyReaderState(this.reader, 2); return this.id2obj.get(new Integer(1)); } protected void parseDefineImmediateObject(XMLReader reader) { String idAttr = getRequiredAttribute(reader, "id"); String typeAttr = getRequiredAttribute(reader, "type"); if (!this.immediateClassNames.contains(typeAttr)) failInvalidClass(reader, typeAttr);  String valueAttr = getRequiredAttribute(reader, "value"); Integer id = parseId(reader, idAttr); if (getObjectForId(id) != null) failInvalidId(reader, id);  Object obj = createImmediateObject(reader, typeAttr, valueAttr); if (obj == null) checkMinorMinorAndPatchVersion(reader);  this.id2obj.put(id, obj); verifyNoContent(reader); } protected void parseDefineObject(XMLReader reader) { String idAttr = getRequiredAttribute(reader, "id"); String typeAttr = getRequiredAttribute(reader, "type"); Integer id = parseId(reader, idAttr); if (getObjectForId(id) != null) failInvalidId(reader, id);  Object obj = createInstanceOfType(reader, typeAttr); if (obj == null) checkMinorMinorAndPatchVersion(reader);  this.id2obj.put(id, obj); verifyNoContent(reader); } protected void parseProperty(XMLReader reader) { String nameAttr = getRequiredAttribute(reader, "name"); String subjectAttr = getRequiredAttribute(reader, "subject"); String valueAttr = getRequiredAttribute(reader, "value"); Object subject = mustGetObjectForId(reader, parseId(reader, subjectAttr)); if (subject == null) failInvalidSyntax(reader);  Integer valueId = parseId(reader, valueAttr); Object value = isNullId(valueId) ? null : mustGetObjectForId(reader, valueId); try { property(reader, subject, nameAttr, value); } catch (ClassCastException e) { failInvalidProperty(reader, subject, nameAttr, value); }  verifyNoContent(reader); } protected Object createImmediateObject(XMLReader reader, String type, String value) { if (type.equals("java.lang.Integer")) { try { return Integer.valueOf(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.lang.Short")) { try { return Short.valueOf(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.lang.Long")) { try { return Long.valueOf(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.lang.Byte")) { try { return Byte.valueOf(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.lang.Float")) { try { return Float.valueOf(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.lang.Double")) { try { return Double.valueOf(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.math.BigDecimal")) { try { return new BigDecimal(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.math.BigInteger")) { try { return new BigInteger(value); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  } else { if (type.equals("java.lang.String")) return value;  if (type.equals("javax.xml.namespace.QName")) { try { return QName.valueOf(value); } catch (IllegalArgumentException e) { failInvalidLiteral(reader, type, value); }  } else { if (type.equals("java.lang.Boolean")) return Boolean.valueOf(value);  if (type.equals("com.sun.xml.rpc.wsdl.document.soap.SOAPStyle")) { if (value.equals("rpc")) return SOAPStyle.RPC;  if (value.equals("document")) return SOAPStyle.DOCUMENT;  failInvalidLiteral(reader, type, value); } else if (type.equals("com.sun.xml.rpc.wsdl.document.soap.SOAPUse")) { if (value.equals("literal")) return SOAPUse.LITERAL;  if (value.equals("encoded")) return SOAPUse.ENCODED;  failInvalidLiteral(reader, type, value); } else if (type.equals("com.sun.xml.rpc.soap.SOAPVersion")) { if (value.equals(SOAPVersion.SOAP_11.toString())) return SOAPVersion.SOAP_11;  if (value.equals(SOAPVersion.SOAP_12.toString())) return SOAPVersion.SOAP_12;  failInvalidLiteral(reader, type, value); } else { if (type.equals("[I")) { List l = XmlUtil.parseTokenList(value); int[] result = new int[l.size()]; int i = 0; for (Iterator<String> iter = l.iterator(); iter.hasNext(); i++) { String element = iter.next(); try { result[i] = Integer.parseInt(element); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  }  return result; }  if (type.equals("[B")) { List l = XmlUtil.parseTokenList(value); byte[] result = new byte[l.size()]; int i = 0; for (Iterator<String> iter = l.iterator(); iter.hasNext(); i++) { String element = iter.next(); try { result[i] = Byte.parseByte(element); } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  }  return result; }  if (type.equals("[Ljava.lang.String;")) { List l = XmlUtil.parseTokenList(value); String[] result = new String[l.size()]; int i = 0; for (Iterator<String> iter = l.iterator(); iter.hasNext(); i++) { String element = iter.next(); try { result[i] = element; } catch (NumberFormatException e) { failInvalidLiteral(reader, type, value); }  }  return result; }  if (type.equals("java.util.ArrayList")) { List l = XmlUtil.parseTokenList(value); ArrayList<Object> result = new ArrayList(); for (Iterator<String> iter = l.iterator(); iter.hasNext(); ) { String element = iter.next(); result.add(mustGetObjectForId(reader, parseId(reader, element))); }  return result; }  if (type.equals("java.util.HashSet")) { List l = XmlUtil.parseTokenList(value); HashSet<Object> result = new HashSet(); for (Iterator<String> iter = l.iterator(); iter.hasNext(); ) { String element = iter.next(); result.add(mustGetObjectForId(reader, parseId(reader, element))); }  return result; }  if (type.equals("java.util.HashMap")) { List l = XmlUtil.parseTokenList(value); HashMap<Object, Object> result = new HashMap<Object, Object>(); for (Iterator<String> iter = l.iterator(); iter.hasNext(); ) { String entryKey = iter.next(); if (!iter.hasNext()) failInvalidLiteral(reader, type, value);  String entryValue = iter.next(); result.put(mustGetObjectForId(reader, parseId(reader, entryKey)), mustGetObjectForId(reader, parseId(reader, entryValue))); }  return result; }  if (type.equals("java.net.URI")) { try { return new URI(value); } catch (URISyntaxException e) { failInvalidLiteral(reader, type, value); }  } else if (type.equals("java.util.GregorianCalendar")) { try { return XSDDateTimeCalendarEncoder.getInstance().stringToObject(value, null); } catch (Exception e) { failInvalidLiteral(reader, type, value); }  } else { failInvalidLiteral(reader, type, value); }  }  }  }  return null; } protected void verifyNoContent(XMLReader reader) { if (reader.nextElementContent() != 2) failInvalidSyntax(reader);  } protected String getRequiredAttribute(XMLReader reader, String name) { Attributes attributes = reader.getAttributes(); String value = attributes.getValue(name); if (value == null) failInvalidSyntax(reader);  return value; } protected Integer parseId(XMLReader reader, String s) { try { return Integer.valueOf(s); } catch (NumberFormatException e) { failInvalidSyntax(reader); return null; }  } protected boolean isNullId(Integer id) { return (id.intValue() == 0); } protected Object getObjectForId(Integer id) { return this.id2obj.get(id); } protected static final QName DEF_OBJ_NAME = new QName("object");
/* 527 */   protected Object mustGetObjectForId(XMLReader reader, Integer id) { Object result = getObjectForId(id); if (result == null) failInvalidId(reader, id);  return result; } protected Object createInstanceOfType(XMLReader reader, String typename) { try { Class<?> klass = Class.forName(typename); return klass.newInstance(); } catch (InstantiationException e) { checkMinorMinorAndPatchVersion(reader); failInvalidClass(reader, typename); } catch (IllegalAccessException e) { checkMinorMinorAndPatchVersion(reader); failInvalidClass(reader, typename); } catch (ClassNotFoundException e) { checkMinorMinorAndPatchVersion(reader); failInvalidClass(reader, typename); }  return null; } protected void property(XMLReader reader, Object subject, String name, Object value) { failInvalidClass(reader, subject.getClass().getName()); } protected abstract QName getContainerName(); private void checkVersion() { if (getVersion() != null) { this.targetModelVersion = getRequiredAttribute(this.reader, "version"); int[] targetVersion = VersionUtil.getCanonicalVersion(this.targetModelVersion); int[] currentVersion = VersionUtil.getCanonicalVersion(getVersion()); if (targetVersion[0] > currentVersion[0] || (targetVersion[0] == currentVersion[0] && currentVersion[1] > currentVersion[1])) failInvalidVersion(this.reader, String.valueOf(targetVersion[0]) + "." + String.valueOf(targetVersion[1]) + "." + String.valueOf(targetVersion[2]) + "." + String.valueOf(targetVersion[3]));  }  } private void checkMinorMinorAndPatchVersion(XMLReader reader) { if (getVersion() != null) { int[] targetVersion = VersionUtil.getCanonicalVersion(getTargetVersion()); int[] currentVersion = VersionUtil.getCanonicalVersion(getVersion()); if (targetVersion[2] > currentVersion[2] || (targetVersion[2] == currentVersion[2] && targetVersion[3] > currentVersion[3])) failInvalidMinorMinorOrPatchVersion(reader, String.valueOf(targetVersion[0]) + "." + String.valueOf(targetVersion[1]) + "." + String.valueOf(targetVersion[2]) + "." + String.valueOf(targetVersion[3]), getVersion());  }  } protected String getVersion() { return null; } protected String getTargetVersion() { return null; } protected QName getDefineObjectName() { return DEF_OBJ_NAME; } protected QName getDefineImmediateObjectName() { return DEF_IMM_OBJ_NAME; } protected QName getPropertyName() { return PROP_NAME; } protected static final QName DEF_IMM_OBJ_NAME = new QName("iobject");
/* 528 */   protected static final QName PROP_NAME = new QName("property");
/*     */   protected static final String ATTR_VERSION = "version";
/*     */   protected static final String ATTR_ID = "id";
/*     */   protected static final String ATTR_NAME = "name";
/*     */   protected static final String ATTR_TYPE = "type";
/*     */   protected static final String ATTR_VALUE = "value";
/*     */   protected static final String ATTR_SUBJECT = "subject";
/*     */   
/*     */   protected abstract void failInvalidSyntax(XMLReader paramXMLReader);
/*     */   
/*     */   protected abstract void failInvalidVersion(XMLReader paramXMLReader, String paramString);
/*     */   
/*     */   protected abstract void failInvalidMinorMinorOrPatchVersion(XMLReader paramXMLReader, String paramString1, String paramString2);
/*     */   
/*     */   protected abstract void failInvalidClass(XMLReader paramXMLReader, String paramString);
/*     */   
/*     */   protected abstract void failInvalidId(XMLReader paramXMLReader, Integer paramInteger);
/*     */   
/*     */   protected abstract void failInvalidLiteral(XMLReader paramXMLReader, String paramString1, String paramString2);
/*     */   
/*     */   protected abstract void failInvalidProperty(XMLReader paramXMLReader, Object paramObject1, String paramString, Object paramObject2);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\ImporterBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */