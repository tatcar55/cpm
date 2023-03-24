/*      */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*      */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.HeaderFault;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.ModelException;
/*      */ import com.sun.xml.rpc.processor.model.ModelObject;
/*      */ import com.sun.xml.rpc.processor.model.Operation;
/*      */ import com.sun.xml.rpc.processor.model.Parameter;
/*      */ import com.sun.xml.rpc.processor.model.Port;
/*      */ import com.sun.xml.rpc.processor.model.Request;
/*      */ import com.sun.xml.rpc.processor.model.Response;
/*      */ import com.sun.xml.rpc.processor.model.Service;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralContentMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*      */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*      */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*      */ import com.sun.xml.rpc.processor.util.ClassNameCollector;
/*      */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.processor.util.StringUtils;
/*      */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*      */ import com.sun.xml.rpc.util.localization.Localizable;
/*      */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*      */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*      */ import com.sun.xml.rpc.wsdl.document.Binding;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingFault;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingOperation;
/*      */ import com.sun.xml.rpc.wsdl.document.Documentation;
/*      */ import com.sun.xml.rpc.wsdl.document.Fault;
/*      */ import com.sun.xml.rpc.wsdl.document.Kinds;
/*      */ import com.sun.xml.rpc.wsdl.document.Message;
/*      */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*      */ import com.sun.xml.rpc.wsdl.document.Operation;
/*      */ import com.sun.xml.rpc.wsdl.document.OperationStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.Port;
/*      */ import com.sun.xml.rpc.wsdl.document.PortType;
/*      */ import com.sun.xml.rpc.wsdl.document.Service;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*      */ import com.sun.xml.rpc.wsdl.document.mime.MIMEContent;
/*      */ import com.sun.xml.rpc.wsdl.document.mime.MIMEMultipartRelated;
/*      */ import com.sun.xml.rpc.wsdl.document.mime.MIMEPart;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPAddress;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBinding;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPFault;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPHeader;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPHeaderFault;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPOperation;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*      */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*      */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*      */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
/*      */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*      */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*      */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*      */ import com.sun.xml.rpc.wsdl.framework.NoSuchEntityException;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParseException;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParserListener;
/*      */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*      */ import com.sun.xml.rpc.wsdl.parser.SOAPEntityReferenceValidator;
/*      */ import com.sun.xml.rpc.wsdl.parser.Util;
/*      */ import com.sun.xml.rpc.wsdl.parser.WSDLParser;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.xml.namespace.QName;
/*      */ import org.w3c.dom.Element;
/*      */ import org.xml.sax.InputSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class WSDLModelerBase
/*      */   implements Modeler
/*      */ {
/*      */   private static final String OPERATION_HAS_VOID_RETURN_TYPE = "com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType";
/*      */   private static final String WSDL_DOCUMENTATION = "com.sun.xml.rpc.processor.modeler.wsdl.documentation";
/*      */   private static final String WSDL_PARAMETER_ORDER = "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";
/*      */   private static final String WSDL_RESULT_PARAMETER = "com.sun.xml.rpc.processor.modeler.wsdl.resultParameter";
/*      */   public static final String MESSAGE_HAS_MIME_MULTIPART_RELATED_BINDING = "com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding";
/*      */   protected ProcessSOAPOperationInfo info;
/*      */   private WSDLModelInfo _modelInfo;
/*      */   private Properties _options;
/*      */   private SchemaAnalyzerBase _analyzer;
/*      */   private LocalizableMessageFactory _messageFactory;
/*      */   private Set _conflictingClassNames;
/*      */   protected Map _javaExceptions;
/*      */   protected Map _faultTypeToStructureMap;
/*      */   private ProcessorEnvironment _env;
/*      */   protected JavaSimpleTypeCreator _javaTypes;
/*      */   private Map _bindingNameToPortMap;
/*      */   
/*      */   private void init() {
/*      */     soap11WSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(SOAPVersion.SOAP_11);
/*      */     soap12WSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(SOAPVersion.SOAP_12);
/*      */   }
/*      */   
/*      */   public Model buildModel() {
/*      */     try {
/*      */       this.parser = new WSDLParser();
/*      */       InputSource inputSource = new InputSource(this._modelInfo.getLocation());
/*      */       this.parser.addParserListener(new ParserListener()
/*      */           {
/*      */             public void ignoringExtension(QName name, QName parent) {
/*      */               if (parent.equals(WSDLConstants.QNAME_TYPES))
/*      */                 if (name.getLocalPart().equals("schema") && !name.getNamespaceURI().equals(""))
/*      */                   WSDLModelerBase.this.warn("wsdlmodeler.warning.ignoringUnrecognizedSchemaExtension", name.getNamespaceURI());  
/*      */             }
/*      */             
/*      */             public void doneParsingEntity(QName element, Entity entity) {}
/*      */           });
/*      */       this.useWSIBasicProfile = Boolean.valueOf(this._options.getProperty("useWSIBasicProfile")).booleanValue();
/*      */       this.unwrap = Boolean.valueOf(this._options.getProperty("unwrapDocLitWrappers", "true")).booleanValue();
/*      */       this.strictCompliance = Boolean.valueOf(this._options.getProperty("strictCompliance")).booleanValue();
/*      */       WSDLDocument document = this.parser.parse(inputSource, this.useWSIBasicProfile);
/*      */       document.validateLocally();
/*      */       this.literalOnly = (this.useWSIBasicProfile || Boolean.valueOf(this._options.getProperty("useDocumentLiteralEncoding")).booleanValue());
/*      */       this.literalOnly = (this.literalOnly || Boolean.valueOf(this._options.getProperty("useRPCLiteralEncoding")).booleanValue());
/*      */       boolean validateWSDL = Boolean.valueOf(this._options.getProperty("validationWSDL")).booleanValue();
/*      */       if (validateWSDL)
/*      */         document.validate((EntityReferenceValidator)new SOAPEntityReferenceValidator()); 
/*      */       Model model = internalBuildModel(document);
/*      */       ClassNameCollector collector = new ClassNameCollector();
/*      */       collector.process(model);
/*      */       if (collector.getConflictingClassNames().isEmpty())
/*      */         return model; 
/*      */       this._conflictingClassNames = collector.getConflictingClassNames();
/*      */       model = internalBuildModel(document);
/*      */       ClassNameCollector collector2 = new ClassNameCollector();
/*      */       collector2.process(model);
/*      */       if (collector2.getConflictingClassNames().isEmpty())
/*      */         return model; 
/*      */       StringBuffer conflictList = new StringBuffer();
/*      */       boolean first = true;
/*      */       Iterator<String> iter = collector2.getConflictingClassNames().iterator();
/*      */       while (iter.hasNext()) {
/*      */         if (!first) {
/*      */           conflictList.append(", ");
/*      */         } else {
/*      */           first = false;
/*      */         } 
/*      */         conflictList.append(iter.next());
/*      */       } 
/*      */       throw new ModelerException("wsdlmodeler.unsolvableNamingConflicts", conflictList.toString());
/*      */     } catch (ModelException e) {
/*      */       throw new ModelerException(e);
/*      */     } catch (ParseException e) {
/*      */       throw new ModelerException(e);
/*      */     } catch (ValidationException e) {
/*      */       throw new ModelerException(e);
/*      */     } finally {
/*      */       this._analyzer = null;
/*      */       this._conflictingClassNames = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private Model internalBuildModel(WSDLDocument document) {
/*      */     QName modelName = new QName(document.getDefinitions().getTargetNamespaceURI(), (document.getDefinitions().getName() == null) ? "model" : document.getDefinitions().getName());
/*      */     Model model = new Model(modelName);
/*      */     model.setProperty("com.sun.xml.rpc.processor.model.ModelerName", "com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler");
/*      */     this.theModel = model;
/*      */     this._javaTypes = new JavaSimpleTypeCreator();
/*      */     this._analyzer = getSchemaAnalyzerInstance(document, this._modelInfo, this._options, this._conflictingClassNames, this._javaTypes);
/*      */     this._javaExceptions = new HashMap<Object, Object>();
/*      */     this._faultTypeToStructureMap = new HashMap<Object, Object>();
/*      */     this._bindingNameToPortMap = new HashMap<Object, Object>();
/*      */     model.setTargetNamespaceURI(document.getDefinitions().getTargetNamespaceURI());
/*      */     setDocumentationIfPresent((ModelObject)model, document.getDefinitions().getDocumentation());
/*      */     boolean searchSchema = Boolean.valueOf(this._options.getProperty("searchSchemaForSubtypes")).booleanValue();
/*      */     if (searchSchema)
/*      */       processSearchSchemaOption(document, model); 
/*      */     boolean hasServices = document.getDefinitions().services().hasNext();
/*      */     if (hasServices) {
/*      */       Iterator<Service> iter = document.getDefinitions().services();
/*      */       while (iter.hasNext()) {
/*      */         processService(iter.next(), model, document);
/*      */         hasServices = true;
/*      */       } 
/*      */     } else {
/*      */       warn("wsdlmodeler.warning.noServiceDefinitionsFound");
/*      */     } 
/*      */     return model;
/*      */   }
/*      */   
/*      */   protected abstract SchemaAnalyzerBase getSchemaAnalyzerInstance(WSDLDocument paramWSDLDocument, WSDLModelInfo paramWSDLModelInfo, Properties paramProperties, Set paramSet, JavaSimpleTypeCreator paramJavaSimpleTypeCreator);
/*      */   
/*      */   protected SchemaAnalyzerBase getSchemaAnalyzer() {
/*      */     return this._analyzer;
/*      */   }
/*      */   
/*      */   protected void processSearchSchemaOption(WSDLDocument document, Model model) {
/*      */     Map typeMap = document.getMap(SchemaKinds.XSD_TYPE);
/*      */     int errorcount = 0;
/*      */     for (Iterator<QName> iter = typeMap.keySet().iterator(); iter.hasNext(); ) {
/*      */       QName typeName = iter.next();
/*      */       try {
/*      */         LiteralType literalType;
/*      */         AbstractType extraType = null;
/*      */         this.hSet = this.parser.getUse();
/*      */         if (this.hSet.contains("encoded") && !this.hSet.contains("literal")) {
/*      */           SOAPType sOAPType = this._analyzer.schemaTypeToSOAPType(typeName);
/*      */         } else if (!this.hSet.contains("encoded") && this.hSet.contains("literal")) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(typeName);
/*      */         } else {
/*      */           throw new ModelerException("wsdlmodler.warning.operation.use");
/*      */         } 
/*      */         if (literalType != null) {
/*      */           model.addExtraType((AbstractType)literalType);
/*      */           continue;
/*      */         } 
/*      */         errorcount++;
/*      */       } catch (ModelException e) {
/*      */         errorcount++;
/*      */       } 
/*      */     } 
/*      */     if (errorcount > 0)
/*      */       warn("wsdlmodeler.warning.searchSchema.unrecognizedTypes", Integer.toString(errorcount)); 
/*      */   }
/*      */   
/*      */   protected Documentation getDocumentationFor(Element e) {
/*      */     String s = XmlUtil.getTextForNode(e);
/*      */     if (s == null)
/*      */       return null; 
/*      */     return new Documentation(s);
/*      */   }
/*      */   
/*      */   protected void checkNotWsdlElement(Element e) {
/*      */     if (e.getNamespaceURI().equals("http://schemas.xmlsoap.org/wsdl/"))
/*      */       Util.fail("parsing.invalidWsdlElement", e.getTagName()); 
/*      */   }
/*      */   
/*      */   protected void checkNotWsdlRequired(Element e) {
/*      */     String required = XmlUtil.getAttributeNSOrNull(e, "required", "http://schemas.xmlsoap.org/wsdl/");
/*      */     if (required != null && required.equals("true"))
/*      */       Util.fail("parsing.requiredExtensibilityElement", e.getTagName(), e.getNamespaceURI()); 
/*      */   }
/*      */   
/*      */   protected String getServiceInterfaceName(QName serviceQName, Service wsdlService) {
/*      */     String serviceInterface = "";
/*      */     String javaPackageName = null;
/*      */     if (this._modelInfo.getJavaPackageName() != null && !this._modelInfo.getJavaPackageName().equals(""))
/*      */       javaPackageName = this._modelInfo.getJavaPackageName(); 
/*      */     if (javaPackageName != null)
/*      */       serviceInterface = javaPackageName + "."; 
/*      */     serviceInterface = serviceInterface + this._env.getNames().validJavaClassName(wsdlService.getName());
/*      */     return serviceInterface;
/*      */   }
/*      */   
/*      */   protected void processService(Service wsdlService, Model model, WSDLDocument document) {
/*      */     String serviceInterface = "";
/*      */     QName serviceQName = getQNameOf((GloballyKnown)wsdlService);
/*      */     serviceInterface = getServiceInterfaceName(serviceQName, wsdlService);
/*      */     if (isConflictingServiceClassName(serviceInterface))
/*      */       serviceInterface = serviceInterface + "_Service"; 
/*      */     Service service = new Service(serviceQName, new JavaInterface(serviceInterface, serviceInterface + "Impl"));
/*      */     setDocumentationIfPresent((ModelObject)service, wsdlService.getDocumentation());
/*      */     boolean hasPorts = false;
/*      */     for (Iterator<Port> iter = wsdlService.ports(); iter.hasNext(); ) {
/*      */       boolean processed = processPort(iter.next(), service, document);
/*      */       hasPorts = (hasPorts || processed);
/*      */     } 
/*      */     model.addService(service);
/*      */     if (!hasPorts)
/*      */       warn("wsdlmodeler.warning.noPortsInService", wsdlService.getName()); 
/*      */   }
/*      */   
/*      */   protected String getJavaNameOfPort(QName portQName) {
/*      */     return null;
/*      */   }
/*      */   
/*      */   protected boolean processPort(Port wsdlPort, Service service, WSDLDocument document) {
/*      */     try {
/*      */       QName portQName = getQNameOf((GloballyKnown)wsdlPort);
/*      */       Port port = new Port(portQName);
/*      */       setCurrentPort(port);
/*      */       String metaPortName = getJavaNameOfPort(portQName);
/*      */       if (metaPortName != null)
/*      */         port.setProperty("com.sun.xml.rpc.processor.model.JavaPortName", metaPortName); 
/*      */       setDocumentationIfPresent((ModelObject)port, wsdlPort.getDocumentation());
/*      */       SOAPAddress soapAddress = (SOAPAddress)getExtensionOfType((Extensible)wsdlPort, SOAPAddress.class);
/*      */       if (soapAddress == null) {
/*      */         warn("wsdlmodeler.warning.ignoringNonSOAPPort.noAddress", wsdlPort.getName());
/*      */         return false;
/*      */       } 
/*      */       port.setAddress(soapAddress.getLocation());
/*      */       Binding binding = wsdlPort.resolveBinding((AbstractDocument)document);
/*      */       QName bindingName = getQNameOf((GloballyKnown)binding);
/*      */       PortType portType = binding.resolvePortType((AbstractDocument)document);
/*      */       port.setProperty("com.sun.xml.rpc.processor.model.WSDLPortName", getQNameOf((GloballyKnown)wsdlPort));
/*      */       port.setProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName", getQNameOf((GloballyKnown)portType));
/*      */       port.setProperty("com.sun.xml.rpc.processor.model.WSDLBindingName", bindingName);
/*      */       if (this._bindingNameToPortMap.containsKey(bindingName)) {
/*      */         Port existingPort = (Port)this._bindingNameToPortMap.get(bindingName);
/*      */         port.setOperationsList(existingPort.getOperationsList());
/*      */         port.setJavaInterface(existingPort.getJavaInterface());
/*      */       } else {
/*      */         SOAPBinding soapBinding = (SOAPBinding)getExtensionOfType((Extensible)binding, SOAPBinding.class);
/*      */         if (soapBinding == null) {
/*      */           warn("wsdlmodeler.warning.ignoringNonSOAPPort", wsdlPort.getName());
/*      */           return false;
/*      */         } 
/*      */         if (soapBinding.getTransport() == null || !soapBinding.getTransport().equals(soap11WSDLConstants.getSOAPTransportHttpURI())) {
/*      */           warn("wsdlmodeler.warning.ignoringSOAPBinding.nonHTTPTransport", wsdlPort.getName());
/*      */           return false;
/*      */         } 
/*      */         boolean hasOverloadedOperations = false;
/*      */         Set<String> operationNames = new HashSet();
/*      */         for (Iterator<Operation> iter = portType.operations(); iter.hasNext(); ) {
/*      */           Operation operation = iter.next();
/*      */           if (operationNames.contains(operation.getName())) {
/*      */             hasOverloadedOperations = true;
/*      */             break;
/*      */           } 
/*      */           operationNames.add(operation.getName());
/*      */           Iterator<BindingOperation> itr = binding.operations();
/*      */           while (iter.hasNext()) {
/*      */             BindingOperation bindingOperation = itr.next();
/*      */             if (operation.getName().equals(bindingOperation.getName()))
/*      */               break; 
/*      */             if (!itr.hasNext())
/*      */               throw new ModelerException("wsdlmodeler.invalid.bindingOperation.notFound", new Object[] { operation.getName(), binding.getName() }); 
/*      */           } 
/*      */         } 
/*      */         Map<Object, Object> headers = new HashMap<Object, Object>();
/*      */         boolean hasOperations = false;
/*      */         for (Iterator<BindingOperation> iterator = binding.operations(); iterator.hasNext(); ) {
/*      */           BindingOperation bindingOperation = iterator.next();
/*      */           Operation portTypeOperation = null;
/*      */           Set<Operation> operations = portType.getOperationsNamed(bindingOperation.getName());
/*      */           if (operations.size() == 0)
/*      */             throw new ModelerException("wsdlmodeler.invalid.bindingOperation.notInPortType", new Object[] { bindingOperation.getName(), binding.getName() }); 
/*      */           if (operations.size() == 1) {
/*      */             portTypeOperation = operations.iterator().next();
/*      */           } else {
/*      */             boolean found = false;
/*      */             String expectedInputName = bindingOperation.getInput().getName();
/*      */             String expectedOutputName = bindingOperation.getOutput().getName();
/*      */             if (this.useWSIBasicProfile) {
/*      */               SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)bindingOperation, SOAPOperation.class);
/*      */               if (soapOperation.getStyle() != null && soapBinding.getStyle() != null && soapOperation.getStyle() != soapBinding.getStyle()) {
/*      */                 warn("wsdlmodeler.warning.ignoringOperation.conflictStyleInWSIMode", bindingOperation.getName());
/*      */                 continue;
/*      */               } 
/*      */             } 
/*      */             Iterator<Operation> iter2 = operations.iterator();
/*      */             while (iter2.hasNext()) {
/*      */               Operation candidateOperation = iter2.next();
/*      */               if (expectedInputName == null)
/*      */                 throw new ModelerException("wsdlmodeler.invalid.bindingOperation.missingInputName", new Object[] { bindingOperation.getName(), binding.getName() }); 
/*      */               if (expectedOutputName == null)
/*      */                 throw new ModelerException("wsdlmodeler.invalid.bindingOperation.missingOutputName", new Object[] { bindingOperation.getName(), binding.getName() }); 
/*      */               if (expectedInputName.equals(candidateOperation.getInput().getName()) && expectedOutputName.equals(candidateOperation.getOutput().getName())) {
/*      */                 if (found)
/*      */                   throw new ModelerException("wsdlmodeler.invalid.bindingOperation.multipleMatchingOperations", new Object[] { bindingOperation.getName(), binding.getName() }); 
/*      */                 found = true;
/*      */                 portTypeOperation = candidateOperation;
/*      */               } 
/*      */             } 
/*      */             if (!found)
/*      */               throw new ModelerException("wsdlmodeler.invalid.bindingOperation.notFound", new Object[] { bindingOperation.getName(), binding.getName() }); 
/*      */           } 
/*      */           this.info = new ProcessSOAPOperationInfo(port, wsdlPort, portTypeOperation, bindingOperation, soapBinding, document, hasOverloadedOperations, headers);
/*      */           Operation operation = processSOAPOperation();
/*      */           postProcessSOAPOperation(operation);
/*      */           if (operation != null) {
/*      */             port.addOperation(operation);
/*      */             hasOperations = true;
/*      */           } 
/*      */         } 
/*      */         if (!hasOperations)
/*      */           warn("wsdlmodeler.warning.noOperationsInPort", wsdlPort.getName()); 
/*      */         createJavaInterfaceForPort(port);
/*      */         this._bindingNameToPortMap.put(bindingName, port);
/*      */       } 
/*      */       port.setClientHandlerChainInfo(this._modelInfo.getClientHandlerChainInfo());
/*      */       port.setServerHandlerChainInfo(this._modelInfo.getServerHandlerChainInfo());
/*      */       String stubClassName = this._env.getNames().stubFor(port, null);
/*      */       if (isConflictingStubClassName(stubClassName))
/*      */         stubClassName = this._env.getNames().stubFor(port, getNonQualifiedNameFor(port.getName())); 
/*      */       String tieClassName = this._env.getNames().tieFor(port, this._env.getNames().getSerializerNameInfix());
/*      */       tieClassName = getInfixedName(port, tieClassName);
/*      */       port.setProperty("com.sun.xml.rpc.processor.model.StubClassName", stubClassName);
/*      */       port.setProperty("com.sun.xml.rpc.processor.model.TieClassName", tieClassName);
/*      */       service.addPort(port);
/*      */       setCurrentPort(null);
/*      */       return true;
/*      */     } catch (NoSuchEntityException e) {
/*      */       warn((Localizable)e);
/*      */       return false;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void setCurrentPort(Port port) {}
/*      */   
/*      */   private String getInfixedName(Port port, String tieClassName) {
/*      */     if (isConflictingTieClassName(tieClassName)) {
/*      */       String str = null;
/*      */       if (this._env.getNames().getSerializerNameInfix() != null) {
/*      */         str = getNonQualifiedNameFor(port.getName()) + "_" + this._env.getNames().getSerializerNameInfix();
/*      */       } else {
/*      */         str = getNonQualifiedNameFor(port.getName());
/*      */       } 
/*      */       tieClassName = this._env.getNames().tieFor(port, str);
/*      */     } 
/*      */     return tieClassName;
/*      */   }
/*      */   
/*      */   protected Operation processSOAPOperation() {
/*      */     Operation operation = new Operation(new QName(null, this.info.bindingOperation.getName()));
/*      */     setDocumentationIfPresent((ModelObject)operation, this.info.portTypeOperation.getDocumentation());
/*      */     if (this.info.portTypeOperation.getStyle() != OperationStyle.REQUEST_RESPONSE && this.info.portTypeOperation.getStyle() != OperationStyle.ONE_WAY) {
/*      */       warn("wsdlmodeler.warning.ignoringOperation.notSupportedStyle", this.info.portTypeOperation.getName());
/*      */       return null;
/*      */     } 
/*      */     SOAPStyle soapStyle = this.info.soapBinding.getStyle();
/*      */     SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)this.info.bindingOperation, SOAPOperation.class);
/*      */     if (soapOperation != null) {
/*      */       if (soapOperation.getStyle() != null)
/*      */         soapStyle = soapOperation.getStyle(); 
/*      */       if (soapOperation.getSOAPAction() != null)
/*      */         operation.setSOAPAction(soapOperation.getSOAPAction()); 
/*      */     } 
/*      */     operation.setStyle(soapStyle);
/*      */     String uniqueOperationName = getUniqueName(this.info.portTypeOperation, this.info.hasOverloadedOperations);
/*      */     if (this.info.hasOverloadedOperations)
/*      */       operation.setUniqueName(uniqueOperationName); 
/*      */     this.info.operation = operation;
/*      */     this.info.uniqueOperationName = uniqueOperationName;
/*      */     SOAPBody soapRequestBody = getSOAPRequestBody();
/*      */     if (soapRequestBody == null)
/*      */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.inputMissingSoapBody", new Object[] { this.info.bindingOperation.getName() }); 
/*      */     if (soapStyle == SOAPStyle.RPC) {
/*      */       if (soapRequestBody.isEncoded())
/*      */         return processSOAPOperationRPCEncodedStyle(); 
/*      */       return processSOAPOperationRPCLiteralStyle();
/*      */     } 
/*      */     return processSOAPOperationDocumentLiteralStyle();
/*      */   }
/*      */   
/*      */   protected void postProcessSOAPOperation(Operation operation) {}
/*      */   
/*      */   protected void setJavaOperationNameProperty(Message inputMessage) {}
/*      */   
/*      */   protected boolean useExplicitServiceContextForRpcEncoded(Message inputMessage) {
/*      */     return useExplicitServiceContext();
/*      */   }
/*      */   
/*      */   protected boolean useExplicitServiceContextForRpcLit(Message inputMessage) {
/*      */     return useExplicitServiceContext();
/*      */   }
/*      */   
/*      */   protected boolean useExplicitServiceContextForDocLit(Message inputMessage) {
/*      */     return useExplicitServiceContext();
/*      */   }
/*      */   
/*      */   private boolean useExplicitServiceContext() {
/*      */     return Boolean.valueOf(this._options.getProperty("explicitServiceContext")).booleanValue();
/*      */   }
/*      */   
/*      */   protected Operation processSOAPOperationRPCEncodedStyle() {
/*      */     RPCResponseStructureType rPCResponseStructureType;
/*      */     if (this.useWSIBasicProfile)
/*      */       warn("wsdlmodeler.warning.nonconforming.wsdl.use", this.info.portTypeOperation.getName()); 
/*      */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*      */     Request request = new Request();
/*      */     Response response = new Response();
/*      */     this.info.operation.setUse(SOAPUse.ENCODED);
/*      */     SOAPBody soapRequestBody = getSOAPRequestBody();
/*      */     SOAPBody soapResponseBody = null;
/*      */     Message outputMessage = null;
/*      */     if (isRequestResponse) {
/*      */       soapResponseBody = getSOAPResponseBody();
/*      */       outputMessage = getOutputMessage();
/*      */       if (outputMessage != null)
/*      */         response.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)outputMessage)); 
/*      */     } 
/*      */     if (soapRequestBody.isLiteral() || (!tokenListContains(soapRequestBody.getEncodingStyle(), soap11WSDLConstants.getSOAPEncodingNamespace()) && !tokenListContains(soapRequestBody.getEncodingStyle(), soap12WSDLConstants.getSOAPEncodingNamespace())) || (soapResponseBody != null && (soapResponseBody.isLiteral() || (!tokenListContains(soapResponseBody.getEncodingStyle(), soap11WSDLConstants.getSOAPEncodingNamespace()) && !tokenListContains(soapResponseBody.getEncodingStyle(), soap12WSDLConstants.getSOAPEncodingNamespace()))))) {
/*      */       warn("wsdlmodeler.warning.ignoringOperation.notEncoded", this.info.portTypeOperation.getName());
/*      */       return null;
/*      */     } 
/*      */     Message inputMessage = getInputMessage();
/*      */     if (inputMessage != null)
/*      */       request.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)inputMessage)); 
/*      */     Set inputParameterNames = new HashSet();
/*      */     Set outputParameterNames = new HashSet();
/*      */     String resultParameterName = null;
/*      */     StringBuffer result = new StringBuffer();
/*      */     setJavaOperationNameProperty(inputMessage);
/*      */     List parameterList = processParameterOrder(inputParameterNames, outputParameterNames, result);
/*      */     if (result.length() > 0)
/*      */       resultParameterName = result.toString(); 
/*      */     String requestNamespaceURI = getRequestNamespaceURI(soapRequestBody);
/*      */     String responseNamespaceURI = null;
/*      */     if (isRequestResponse)
/*      */       responseNamespaceURI = getResponseNamespaceURI(soapResponseBody); 
/*      */     String structureNamePrefix = getStructureNamePrefix();
/*      */     QName requestBodyName = new QName(requestNamespaceURI, this.info.portTypeOperation.getName());
/*      */     RPCRequestUnorderedStructureType rPCRequestUnorderedStructureType = new RPCRequestUnorderedStructureType(requestBodyName);
/*      */     JavaStructureType requestBodyJavaType = new JavaStructureType(getUniqueClassName(makePackageQualified(StringUtils.capitalize(structureNamePrefix + this._env.getNames().validExternalJavaIdentifier(this.info.uniqueOperationName)) + "_RequestStruct", requestBodyName)), false, rPCRequestUnorderedStructureType);
/*      */     rPCRequestUnorderedStructureType.setJavaType((JavaType)requestBodyJavaType);
/*      */     Block requestBodyBlock = new Block(requestBodyName, (AbstractType)rPCRequestUnorderedStructureType);
/*      */     request.addBodyBlock(requestBodyBlock);
/*      */     SOAPStructureType responseBodyType = null;
/*      */     JavaStructureType responseBodyJavaType = null;
/*      */     Block responseBodyBlock = null;
/*      */     if (isRequestResponse) {
/*      */       QName responseBodyName = new QName(responseNamespaceURI, this.info.portTypeOperation.getName() + "Response");
/*      */       rPCResponseStructureType = new RPCResponseStructureType(responseBodyName);
/*      */       responseBodyJavaType = new JavaStructureType(getUniqueClassName(makePackageQualified(StringUtils.capitalize(structureNamePrefix + this._env.getNames().validExternalJavaIdentifier(this.info.uniqueOperationName)) + "_ResponseStruct", responseBodyName)), false, rPCResponseStructureType);
/*      */       rPCResponseStructureType.setJavaType((JavaType)responseBodyJavaType);
/*      */       responseBodyBlock = new Block(responseBodyName, (AbstractType)rPCResponseStructureType);
/*      */       response.addBodyBlock(responseBodyBlock);
/*      */     } 
/*      */     if (resultParameterName == null) {
/*      */       this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType", "true");
/*      */     } else {
/*      */       MessagePart part = outputMessage.getPart(resultParameterName);
/*      */       SOAPType soapType = getSchemaTypeToSOAPType(part.getDescriptor());
/*      */       soapType = (SOAPType)verifyResultType((AbstractType)soapType, this.info.operation);
/*      */       SOAPStructureMember member = new SOAPStructureMember(new QName(null, part.getName()), soapType);
/*      */       JavaStructureMember javaMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(part.getName()), soapType.getJavaType(), member, false);
/*      */       javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */       javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */       member.setJavaStructureMember(javaMember);
/*      */       rPCResponseStructureType.add(member);
/*      */       responseBodyJavaType.add(javaMember);
/*      */       Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */       parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */       parameter.setEmbedded(true);
/*      */       parameter.setType((AbstractType)soapType);
/*      */       parameter.setBlock(responseBodyBlock);
/*      */       response.addParameter(parameter);
/*      */       this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */     } 
/*      */     List<String> definitiveParameterList = new ArrayList();
/*      */     for (Iterator<String> iter = parameterList.iterator(); iter.hasNext(); ) {
/*      */       String name = iter.next();
/*      */       boolean isInput = inputParameterNames.contains(name);
/*      */       boolean isOutput = outputParameterNames.contains(name);
/*      */       SOAPType soapType = null;
/*      */       Parameter inParameter = null;
/*      */       if (isInput && isOutput)
/*      */         if (!inputMessage.getPart(name).getDescriptor().equals(outputMessage.getPart(name).getDescriptor()))
/*      */           throw new ModelerException("wsdlmodeler.invalid.parameter.differentTypes", new Object[] { name, this.info.operation.getName().getLocalPart() });  
/*      */       if (isInput) {
/*      */         MessagePart part = inputMessage.getPart(name);
/*      */         soapType = getSchemaTypeToSOAPType(part.getDescriptor());
/*      */         soapType = (SOAPType)verifyParameterType((AbstractType)soapType, part.getName(), this.info.operation);
/*      */         SOAPStructureMember member = new SOAPStructureMember(new QName(null, part.getName()), soapType);
/*      */         JavaStructureMember javaMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(part.getName()), soapType.getJavaType(), member, false);
/*      */         javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */         javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */         member.setJavaStructureMember(javaMember);
/*      */         rPCRequestUnorderedStructureType.add(member);
/*      */         requestBodyJavaType.add(javaMember);
/*      */         inParameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */         inParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         inParameter.setEmbedded(true);
/*      */         inParameter.setType((AbstractType)soapType);
/*      */         inParameter.setBlock(requestBodyBlock);
/*      */         request.addParameter(inParameter);
/*      */         definitiveParameterList.add(inParameter.getName());
/*      */       } 
/*      */       if (isOutput) {
/*      */         MessagePart part = outputMessage.getPart(name);
/*      */         if (soapType == null) {
/*      */           soapType = getSchemaTypeToSOAPType(part.getDescriptor());
/*      */           soapType = (SOAPType)verifyParameterType((AbstractType)soapType, part.getName(), this.info.operation);
/*      */         } 
/*      */         SOAPStructureMember member = new SOAPStructureMember(new QName(null, part.getName()), soapType);
/*      */         rPCResponseStructureType.add(member);
/*      */         JavaStructureMember javaMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(part.getName()), soapType.getJavaType(), member, false);
/*      */         responseBodyJavaType.add(javaMember);
/*      */         javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */         javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */         member.setJavaStructureMember(javaMember);
/*      */         Parameter outParameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */         outParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         outParameter.setEmbedded(true);
/*      */         outParameter.setType((AbstractType)soapType);
/*      */         outParameter.setBlock(responseBodyBlock);
/*      */         if (inParameter == null) {
/*      */           definitiveParameterList.add(outParameter.getName());
/*      */         } else {
/*      */           inParameter.setLinkedParameter(outParameter);
/*      */           outParameter.setLinkedParameter(inParameter);
/*      */         } 
/*      */         response.addParameter(outParameter);
/*      */       } 
/*      */     } 
/*      */     this.info.operation.setRequest(request);
/*      */     if (isRequestResponse)
/*      */       this.info.operation.setResponse(response); 
/*      */     Set duplicateNames = getDuplicateFaultNames();
/*      */     handleEncodedSOAPFault(response, duplicateNames);
/*      */     boolean explicitServiceContext = useExplicitServiceContextForRpcEncoded(inputMessage);
/*      */     if (explicitServiceContext) {
/*      */       handleEncodedSOAPHeaders(request, response, this.info.bindingOperation.getInput().extensions(), duplicateNames, definitiveParameterList, true);
/*      */       if (isRequestResponse)
/*      */         handleEncodedSOAPHeaders(request, response, this.info.bindingOperation.getOutput().extensions(), duplicateNames, definitiveParameterList, false); 
/*      */     } 
/*      */     this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder", definitiveParameterList);
/*      */     return this.info.operation;
/*      */   }
/*      */   
/*      */   protected AbstractType verifyResultType(AbstractType type, Operation operation) {
/*      */     return type;
/*      */   }
/*      */   
/*      */   protected AbstractType verifyParameterType(AbstractType type, String partName, Operation operation) {
/*      */     return type;
/*      */   }
/*      */   
/*      */   private void handleEncodedSOAPHeaders(Request request, Response response, Iterator<Extension> iter, Set duplicateNames, List<String> definitiveParameterList, boolean processRequest) {
/*      */     while (iter.hasNext()) {
/*      */       Extension extension = iter.next();
/*      */       if (extension instanceof SOAPHeader) {
/*      */         SOAPHeader header = (SOAPHeader)extension;
/*      */         if (header.isLiteral() || !tokenListContains(header.getEncodingStyle(), soap11WSDLConstants.getSOAPEncodingNamespace())) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.notEncoded", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         Message headerMessage = findMessage(header.getMessage(), this.info);
/*      */         if (headerMessage == null) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.cant.resolve.message", new Object[] { header.getMessage(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         MessagePart part = headerMessage.getPart(header.getPart());
/*      */         if (part == null) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.notFound", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         if (processRequest) {
/*      */           if (isHeaderPartPresentInBody(getSOAPRequestBody(), getInputMessage(), header.getPart(), true)) {
/*      */             warn("wsdlmodeler.warning.ignoringHeader.partFromBody", new Object[] { header.getPart() });
/*      */             warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */             continue;
/*      */           } 
/*      */         } else if (isHeaderPartPresentInBody(getSOAPResponseBody(), getOutputMessage(), header.getPart(), false)) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.partFromBody", new Object[] { header.getPart() });
/*      */           warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         SOAPType headerType = getSchemaTypeToSOAPType(part.getDescriptor());
/*      */         String namespaceURI = header.getNamespace();
/*      */         if (namespaceURI == null)
/*      */           namespaceURI = headerType.getName().getNamespaceURI(); 
/*      */         QName headerName = new QName(namespaceURI, header.getPart());
/*      */         Block headerBlock = new Block(headerName, (AbstractType)headerType);
/*      */         headerBlock.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)headerMessage));
/*      */         AbstractType alreadySeenHeaderType = (AbstractType)this.info.headers.get(headerName);
/*      */         if (alreadySeenHeaderType != null && alreadySeenHeaderType != headerType) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.inconsistentDefinition", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */         } else {
/*      */           this.info.headers.put(headerName, headerType);
/*      */           if (processRequest) {
/*      */             request.addHeaderBlock(headerBlock);
/*      */           } else {
/*      */             response.addHeaderBlock(headerBlock);
/*      */           } 
/*      */           Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */           parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           parameter.setEmbedded(false);
/*      */           parameter.setType((AbstractType)headerType);
/*      */           parameter.setBlock(headerBlock);
/*      */           if (processRequest && definitiveParameterList != null) {
/*      */             request.addParameter(parameter);
/*      */             definitiveParameterList.add(parameter.getName());
/*      */           } else {
/*      */             if (definitiveParameterList != null) {
/*      */               Iterator<String> iterInParams = definitiveParameterList.iterator();
/*      */               while (iterInParams.hasNext()) {
/*      */                 String inParamName = iterInParams.next();
/*      */                 if (inParamName.equals(parameter.getName())) {
/*      */                   Parameter inParam = request.getParameterByName(inParamName);
/*      */                   parameter.setLinkedParameter(inParam);
/*      */                   inParam.setLinkedParameter(parameter);
/*      */                 } 
/*      */               } 
/*      */               if (!definitiveParameterList.contains(parameter.getName()))
/*      */                 definitiveParameterList.add(parameter.getName()); 
/*      */             } 
/*      */             response.addParameter(parameter);
/*      */           } 
/*      */         } 
/*      */         processHeaderFaults(header, this.info, response, duplicateNames);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleEncodedSOAPFault(Response response, Set duplicateNames) {
/*      */     for (Iterator<BindingFault> iter = this.info.bindingOperation.faults(); iter.hasNext(); ) {
/*      */       BindingFault bindingFault = iter.next();
/*      */       Fault portTypeFault = null;
/*      */       Iterator<Fault> iter2 = this.info.portTypeOperation.faults();
/*      */       while (iter2.hasNext()) {
/*      */         Fault aFault = iter2.next();
/*      */         if (aFault.getName().equals(bindingFault.getName())) {
/*      */           if (portTypeFault != null)
/*      */             throw new ModelerException("wsdlmodeler.invalid.bindingFault.notUnique", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */           portTypeFault = aFault;
/*      */         } 
/*      */       } 
/*      */       if (portTypeFault == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.notFound", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       Fault fault = new Fault(portTypeFault.getMessage().getLocalPart());
/*      */       SOAPFault soapFault = (SOAPFault)getExtensionOfType((Extensible)bindingFault, SOAPFault.class);
/*      */       if (soapFault == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.outputMissingSoapFault", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       if (soapFault.getName() != null && !soapFault.getName().equals(bindingFault.getName()))
/*      */         warn("wsdlmodeler.invalid.bindingFault.wrongSoapFaultName", new Object[] { soapFault.getName(), bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       if (soapFault.isLiteral() || !tokenListContains(soapFault.getEncodingStyle(), soap11WSDLConstants.getSOAPEncodingNamespace())) {
/*      */         warn("wsdlmodeler.warning.ignoringFault.notEncoded", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       String faultNamespaceURI = soapFault.getNamespace();
/*      */       if (faultNamespaceURI == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.missingNamespace", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       Message faultMessage = portTypeFault.resolveMessage((AbstractDocument)this.info.document);
/*      */       Iterator<MessagePart> iterator = faultMessage.parts();
/*      */       if (!iterator.hasNext())
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.emptyMessage", new Object[] { bindingFault.getName(), faultMessage.getName() }); 
/*      */       MessagePart faultPart = iterator.next();
/*      */       QName faultQName = new QName(faultNamespaceURI, faultMessage.getName());
/*      */       if (duplicateNames.contains(faultQName)) {
/*      */         warn("wsdlmodeler.duplicate.fault.part.name", new Object[] { bindingFault.getName(), this.info.portTypeOperation.getName(), faultPart.getName() });
/*      */         continue;
/*      */       } 
/*      */       if (iterator.hasNext())
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.messageHasMoreThanOnePart", new Object[] { bindingFault.getName(), faultMessage.getName() }); 
/*      */       if (faultPart.getDescriptorKind() != SchemaKinds.XSD_TYPE)
/*      */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { faultMessage.getName(), faultPart.getName() }); 
/*      */       SOAPType faultType = getSchemaTypeToSOAPType(faultPart.getDescriptor());
/*      */       QName elementName = new QName(faultNamespaceURI, faultPart.getName());
/*      */       fault.setElementName(elementName);
/*      */       fault.setJavaMemberName(elementName.getLocalPart());
/*      */       Block faultBlock = new Block(faultQName, (AbstractType)faultType);
/*      */       fault.setBlock(faultBlock);
/*      */       createParentFault(fault);
/*      */       createSubfaults(fault);
/*      */       response.addFaultBlock(faultBlock);
/*      */       this.info.operation.addFault(fault);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void createParentFault(Fault fault) {
/*      */     LiteralStructuredType literalStructuredType;
/*      */     AbstractType faultType = fault.getBlock().getType();
/*      */     AbstractType parentType = null;
/*      */     if (faultType instanceof SOAPStructureType) {
/*      */       SOAPStructureType sOAPStructureType = ((SOAPStructureType)faultType).getParentType();
/*      */     } else if (faultType instanceof LiteralStructuredType) {
/*      */       literalStructuredType = ((LiteralStructuredType)faultType).getParentType();
/*      */     } 
/*      */     if (literalStructuredType == null)
/*      */       return; 
/*      */     if (fault.getParentFault() != null)
/*      */       return; 
/*      */     Fault parentFault = new Fault(literalStructuredType.getName().getLocalPart());
/*      */     QName faultQName = new QName(fault.getBlock().getName().getNamespaceURI(), parentFault.getName());
/*      */     Block block = new Block(faultQName);
/*      */     block.setType((AbstractType)literalStructuredType);
/*      */     parentFault.setBlock(block);
/*      */     parentFault.addSubfault(fault);
/*      */     createParentFault(parentFault);
/*      */   }
/*      */   
/*      */   protected void createSubfaults(Fault fault) {
/*      */     AbstractType faultType = fault.getBlock().getType();
/*      */     Iterator<AbstractType> subtypes = null;
/*      */     if (faultType instanceof SOAPStructureType) {
/*      */       subtypes = ((SOAPStructureType)faultType).getSubtypes();
/*      */     } else if (faultType instanceof LiteralStructuredType) {
/*      */       subtypes = ((LiteralStructuredType)faultType).getSubtypes();
/*      */     } 
/*      */     if (subtypes != null)
/*      */       while (subtypes.hasNext()) {
/*      */         AbstractType subtype = subtypes.next();
/*      */         Fault subFault = new Fault(subtype.getName().getLocalPart());
/*      */         QName faultQName = new QName(fault.getBlock().getName().getNamespaceURI(), subFault.getName());
/*      */         Block block = new Block(faultQName);
/*      */         block.setType(subtype);
/*      */         subFault.setBlock(block);
/*      */         fault.addSubfault(subFault);
/*      */         createSubfaults(subFault);
/*      */       }  
/*      */   }
/*      */   
/*      */   protected SOAPBody getSOAPRequestBody() {
/*      */     SOAPBody requestBody = (SOAPBody)getAnyExtensionOfType((Extensible)this.info.bindingOperation.getInput(), SOAPBody.class);
/*      */     if (requestBody == null)
/*      */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.inputMissingSoapBody", new Object[] { this.info.bindingOperation.getName() }); 
/*      */     return requestBody;
/*      */   }
/*      */   
/*      */   protected boolean isRequestMimeMultipart() {
/*      */     for (Iterator<Extension> iter = this.info.bindingOperation.getInput().extensions(); iter.hasNext(); ) {
/*      */       Extension extension = iter.next();
/*      */       if (extension.getClass().equals(MIMEMultipartRelated.class))
/*      */         return true; 
/*      */     } 
/*      */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isResponseMimeMultipart() {
/*      */     for (Iterator<Extension> iter = this.info.bindingOperation.getOutput().extensions(); iter.hasNext(); ) {
/*      */       Extension extension = iter.next();
/*      */       if (extension.getClass().equals(MIMEMultipartRelated.class))
/*      */         return true; 
/*      */     } 
/*      */     return false;
/*      */   }
/*      */   
/*      */   protected SOAPBody getSOAPResponseBody() {
/*      */     SOAPBody responseBody = (SOAPBody)getAnyExtensionOfType((Extensible)this.info.bindingOperation.getOutput(), SOAPBody.class);
/*      */     if (responseBody == null)
/*      */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.outputMissingSoapBody", new Object[] { this.info.bindingOperation.getName() }); 
/*      */     return responseBody;
/*      */   }
/*      */   
/*      */   protected Message getOutputMessage() {
/*      */     if (this.info.portTypeOperation.getOutput() == null)
/*      */       return null; 
/*      */     return this.info.portTypeOperation.getOutput().resolveMessage((AbstractDocument)this.info.document);
/*      */   }
/*      */   
/*      */   protected Message getInputMessage() {
/*      */     return this.info.portTypeOperation.getInput().resolveMessage((AbstractDocument)this.info.document);
/*      */   }
/*      */   
/*      */   protected void setSOAPUse() {
/*      */     SOAPBody requestBody = getSOAPRequestBody();
/*      */     SOAPBody responseBody = null;
/*      */     if (this.useWSIBasicProfile && requestBody != null && !requestBody.isLiteral() && !requestBody.isEncoded()) {
/*      */       requestBody.setUse(SOAPUse.LITERAL);
/*      */     } else if (requestBody != null && requestBody.isEncoded()) {
/*      */       requestBody.setUse(SOAPUse.ENCODED);
/*      */     } 
/*      */     if (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE) {
/*      */       responseBody = getSOAPResponseBody();
/*      */       if (this.useWSIBasicProfile && responseBody != null && !responseBody.isLiteral() && !responseBody.isEncoded()) {
/*      */         responseBody.setUse(SOAPUse.LITERAL);
/*      */       } else if (responseBody != null && responseBody.isEncoded()) {
/*      */         responseBody.setUse(SOAPUse.ENCODED);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected List getMessageParts(SOAPBody body, Message message, boolean isInput) {
/*      */     String bodyParts = body.getParts();
/*      */     ArrayList<MessagePart> partsList = new ArrayList();
/*      */     List<MessagePart> parts = new ArrayList();
/*      */     List mimeParts = null;
/*      */     if (isInput) {
/*      */       mimeParts = getMimeContentParts(message, (Extensible)this.info.bindingOperation.getInput());
/*      */     } else {
/*      */       mimeParts = getMimeContentParts(message, (Extensible)this.info.bindingOperation.getOutput());
/*      */     } 
/*      */     if (bodyParts != null) {
/*      */       StringTokenizer in = new StringTokenizer(bodyParts.trim(), " ");
/*      */       while (in.hasMoreTokens()) {
/*      */         String part = in.nextToken();
/*      */         MessagePart mPart = message.getPart(part);
/*      */         if (null == mPart)
/*      */           throw new ModelerException("wsdlmodeler.error.partsNotFound", new Object[] { part, message.getName() }); 
/*      */         mPart.setBindingExtensibilityElementKind(1);
/*      */         partsList.add(mPart);
/*      */       } 
/*      */     } else {
/*      */       for (Iterator<MessagePart> iterator = message.parts(); iterator.hasNext(); ) {
/*      */         MessagePart mPart = iterator.next();
/*      */         if (!mimeParts.contains(mPart))
/*      */           mPart.setBindingExtensibilityElementKind(1); 
/*      */         partsList.add(mPart);
/*      */       } 
/*      */     } 
/*      */     for (Iterator<MessagePart> iter = message.parts(); iter.hasNext(); ) {
/*      */       MessagePart mPart = iter.next();
/*      */       if (mimeParts.contains(mPart)) {
/*      */         mPart.setBindingExtensibilityElementKind(5);
/*      */         parts.add(mPart);
/*      */         continue;
/*      */       } 
/*      */       if (partsList.contains(mPart)) {
/*      */         mPart.setBindingExtensibilityElementKind(1);
/*      */         parts.add(mPart);
/*      */       } 
/*      */     } 
/*      */     return parts;
/*      */   }
/*      */   
/*      */   private List getMimeContentParts(Message message, Extensible ext) {
/*      */     ArrayList<MessagePart> mimeContentParts = new ArrayList();
/*      */     String mimeContentPartName = null;
/*      */     Iterator<MIMEPart> mimeParts = getMimeParts(ext);
/*      */     while (mimeParts.hasNext()) {
/*      */       MessagePart part = getMimeContentPart(message, mimeParts.next());
/*      */       if (part != null)
/*      */         mimeContentParts.add(part); 
/*      */     } 
/*      */     return mimeContentParts;
/*      */   }
/*      */   
/*      */   private boolean validateMimeParts(Iterator<MIMEPart> mimeParts) {
/*      */     boolean gotRootPart = false;
/*      */     List<MIMEContent> mimeContents = new ArrayList();
/*      */     while (mimeParts.hasNext()) {
/*      */       MIMEPart mPart = mimeParts.next();
/*      */       Iterator extns = mPart.extensions();
/*      */       while (extns.hasNext()) {
/*      */         Object obj = extns.next();
/*      */         if (obj instanceof SOAPBody) {
/*      */           if (gotRootPart) {
/*      */             warn("mimemodeler.invalidMimePart.moreThanOneSOAPBody", new Object[] { this.info.operation.getName().getLocalPart() });
/*      */             return false;
/*      */           } 
/*      */           gotRootPart = true;
/*      */           continue;
/*      */         } 
/*      */         if (obj instanceof MIMEContent)
/*      */           mimeContents.add((MIMEContent)obj); 
/*      */       } 
/*      */       if (!validateMimeContentPartNames(mimeContents.iterator()))
/*      */         return false; 
/*      */       if (mPart.getName() != null)
/*      */         warn("mimemodeler.invalidMimePart.nameNotAllowed", this.info.portTypeOperation.getName()); 
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   private MessagePart getMimeContentPart(Message message, MIMEPart part) {
/*      */     String mimeContentPartName = null;
/*      */     Iterator<MIMEContent> mimeContents = getMimeContents(part).iterator();
/*      */     if (mimeContents.hasNext()) {
/*      */       mimeContentPartName = ((MIMEContent)mimeContents.next()).getPart();
/*      */       MessagePart mPart = message.getPart(mimeContentPartName);
/*      */       if (null == mPart)
/*      */         throw new ModelerException("wsdlmodeler.error.partsNotFound", new Object[] { mimeContentPartName, message.getName() }); 
/*      */       mPart.setBindingExtensibilityElementKind(5);
/*      */       return mPart;
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   private List getAlternateMimeTypes(List mimeContents) {
/*      */     List<String> mimeTypes = new ArrayList();
/*      */     String mimeType = null;
/*      */     for (Iterator<MIMEContent> iter = mimeContents.iterator(); iter.hasNext(); ) {
/*      */       MIMEContent mimeContent = iter.next();
/*      */       if (mimeType == null) {
/*      */         mimeType = getMimeContentType(mimeContent);
/*      */         mimeTypes.add(mimeType);
/*      */       } 
/*      */       String newMimeType = getMimeContentType(mimeContent);
/*      */       if (!newMimeType.equals(mimeType))
/*      */         mimeTypes.add(newMimeType); 
/*      */     } 
/*      */     return mimeTypes;
/*      */   }
/*      */   
/*      */   private boolean validateMimeContentPartNames(Iterator<MIMEContent> mimeContents) {
/*      */     while (mimeContents.hasNext()) {
/*      */       String mimeContnetPart = null;
/*      */       if (mimeContnetPart == null) {
/*      */         mimeContnetPart = getMimeContentPartName(mimeContents.next());
/*      */         if (mimeContnetPart == null) {
/*      */           warn("mimemodeler.invalidMimeContent.missingPartAttribute", new Object[] { this.info.operation.getName().getLocalPart() });
/*      */           return false;
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */       String newMimeContnetPart = getMimeContentPartName(mimeContents.next());
/*      */       if (newMimeContnetPart == null) {
/*      */         warn("mimemodeler.invalidMimeContent.missingPartAttribute", new Object[] { this.info.operation.getName().getLocalPart() });
/*      */         return false;
/*      */       } 
/*      */       if (!newMimeContnetPart.equals(mimeContnetPart)) {
/*      */         warn("mimemodeler.invalidMimeContent.differentPart");
/*      */         return false;
/*      */       } 
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   private Iterator getMimeParts(Extensible ext) {
/*      */     MIMEMultipartRelated multiPartRelated = (MIMEMultipartRelated)getAnyExtensionOfType(ext, MIMEMultipartRelated.class);
/*      */     if (multiPartRelated == null) {
/*      */       ArrayList parts = new ArrayList();
/*      */       return parts.iterator();
/*      */     } 
/*      */     return multiPartRelated.getParts();
/*      */   }
/*      */   
/*      */   private List getMimeContents(MIMEPart part) {
/*      */     ArrayList<MIMEContent> mimeContents = new ArrayList();
/*      */     Iterator<Extension> parts = part.extensions();
/*      */     while (parts.hasNext()) {
/*      */       Extension mimeContent = parts.next();
/*      */       if (mimeContent instanceof MIMEContent)
/*      */         mimeContents.add((MIMEContent)mimeContent); 
/*      */     } 
/*      */     return mimeContents;
/*      */   }
/*      */   
/*      */   private String getMimeContentPartName(MIMEContent mimeContent) {
/*      */     return mimeContent.getPart();
/*      */   }
/*      */   
/*      */   private String getMimeContentType(MIMEContent mimeContent) {
/*      */     String mimeType = mimeContent.getType();
/*      */     if (mimeType == null)
/*      */       throw new ModelerException("mimemodeler.invalidMimeContent.missingTypeAttribute", new Object[] { this.info.operation.getName().getLocalPart() }); 
/*      */     return mimeType;
/*      */   }
/*      */   
/*      */   protected List processParameterOrder(Set<String> inputParameterNames, Set<String> outputParameterNames, StringBuffer resultParameterName) {
/*      */     if (resultParameterName == null)
/*      */       resultParameterName = new StringBuffer(); 
/*      */     SOAPBody soapRequestBody = getSOAPRequestBody();
/*      */     Message inputMessage = getInputMessage();
/*      */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*      */     SOAPBody soapResponseBody = null;
/*      */     Message outputMessage = null;
/*      */     String parameterOrder = this.info.portTypeOperation.getParameterOrder();
/*      */     List<String> parameterList = null;
/*      */     boolean buildParameterList = false;
/*      */     if (parameterOrder != null && !parameterOrder.trim().equals("")) {
/*      */       parameterList = XmlUtil.parseTokenList(parameterOrder);
/*      */     } else {
/*      */       parameterList = new ArrayList();
/*      */       buildParameterList = true;
/*      */     } 
/*      */     Set<String> partNames = new HashSet();
/*      */     boolean gotOne = false;
/*      */     List inputMessageParts = getMessageParts(soapRequestBody, inputMessage, true);
/*      */     SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)this.info.bindingOperation, SOAPOperation.class);
/*      */     for (Iterator<MessagePart> iter = inputMessageParts.iterator(); iter.hasNext(); ) {
/*      */       MessagePart part = iter.next();
/*      */       if (part.getBindingExtensibilityElementKind() == 1 && !isStyleAndPartMatch(soapOperation, part))
/*      */         continue; 
/*      */       partNames.add(part.getName());
/*      */       inputParameterNames.add(part.getName());
/*      */       if (buildParameterList)
/*      */         parameterList.add(part.getName()); 
/*      */       gotOne = true;
/*      */     } 
/*      */     boolean inputIsEmpty = !gotOne;
/*      */     if (isRequestResponse) {
/*      */       outputMessage = getOutputMessage();
/*      */       soapResponseBody = getSOAPResponseBody();
/*      */       gotOne = false;
/*      */       List outputMessageParts = getMessageParts(soapResponseBody, outputMessage, false);
/*      */       Iterator<MessagePart> partsIter = outputMessageParts.iterator();
/*      */       while (partsIter.hasNext()) {
/*      */         MessagePart part = partsIter.next();
/*      */         if (part.getBindingExtensibilityElementKind() == 1 && !isStyleAndPartMatch(soapOperation, part))
/*      */           continue; 
/*      */         partNames.add(part.getName());
/*      */         if (!partsIter.hasNext() && outputParameterNames.size() == 0 && buildParameterList && !isSingleInOutPart(inputParameterNames, part)) {
/*      */           resultParameterName.append(part.getName());
/*      */         } else {
/*      */           outputParameterNames.add(part.getName());
/*      */           if (buildParameterList && !inputParameterNames.contains(part.getName()))
/*      */             parameterList.add(part.getName()); 
/*      */         } 
/*      */         gotOne = true;
/*      */       } 
/*      */     } 
/*      */     if (!buildParameterList) {
/*      */       for (Iterator<String> iterator = parameterList.iterator(); iterator.hasNext(); ) {
/*      */         String name = iterator.next();
/*      */         if (!partNames.contains(name))
/*      */           throw new ModelerException("wsdlmodeler.invalid.parameterorder.parameter", new Object[] { name, this.info.operation.getName().getLocalPart() }); 
/*      */         partNames.remove(name);
/*      */       } 
/*      */       if (partNames.size() > 1) {
/*      */         Iterator<String> partNameIterator = partNames.iterator();
/*      */         while (partNameIterator.hasNext()) {
/*      */           String name = partNameIterator.next();
/*      */           if (inputParameterNames.contains(name) && !outputParameterNames.contains(name))
/*      */             throw new ModelerException("wsdlmodeler.invalid.parameterOrder.tooManyUnmentionedParts", new Object[] { this.info.operation.getName().getLocalPart() }); 
/*      */           if (outputParameterNames.contains(name))
/*      */             parameterList.add(name); 
/*      */         } 
/*      */       } 
/*      */       if (partNames.size() == 1) {
/*      */         String partName = partNames.iterator().next();
/*      */         if (outputParameterNames.contains(partName))
/*      */           resultParameterName.append(partName); 
/*      */       } 
/*      */     } 
/*      */     return parameterList;
/*      */   }
/*      */   
/*      */   protected boolean isSingleInOutPart(Set inputParameterNames, MessagePart outputPart) {
/*      */     SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)this.info.bindingOperation, SOAPOperation.class);
/*      */     if (soapOperation != null && (soapOperation.isDocument() || this.info.soapBinding.isDocument()))
/*      */       return false; 
/*      */     Message inputMessage = getInputMessage();
/*      */     if (inputParameterNames.contains(outputPart.getName()) && inputMessage.getPart(outputPart.getName()).getDescriptor().equals(outputPart.getDescriptor()))
/*      */       return true; 
/*      */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isOperationDocumentLiteral() {
/*      */     SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)this.info.bindingOperation, SOAPOperation.class);
/*      */     if ((soapOperation != null && soapOperation.isDocument()) || this.info.soapBinding.isDocument())
/*      */       return true; 
/*      */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isStyleAndPartMatch(SOAPOperation soapOperation, MessagePart part) {
/*      */     if (soapOperation != null && soapOperation.getStyle() != null) {
/*      */       if ((soapOperation.isDocument() && part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT && this.useWSIBasicProfile) || (soapOperation.isRPC() && part.getDescriptorKind() != SchemaKinds.XSD_TYPE && this.useWSIBasicProfile))
/*      */         return false; 
/*      */     } else if ((this.info.soapBinding.isDocument() && part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT && this.useWSIBasicProfile) || (this.info.soapBinding.isRPC() && part.getDescriptorKind() != SchemaKinds.XSD_TYPE && this.useWSIBasicProfile)) {
/*      */       return false;
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected String getRequestNamespaceURI(SOAPBody body) {
/*      */     String namespaceURI = body.getNamespace();
/*      */     if (namespaceURI == null)
/*      */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.inputSoapBody.missingNamespace", new Object[] { this.info.bindingOperation.getName() }); 
/*      */     return namespaceURI;
/*      */   }
/*      */   
/*      */   protected String getResponseNamespaceURI(SOAPBody body) {
/*      */     String namespaceURI = body.getNamespace();
/*      */     if (namespaceURI == null)
/*      */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.outputSoapBody.missingNamespace", new Object[] { this.info.bindingOperation.getName() }); 
/*      */     return namespaceURI;
/*      */   }
/*      */   
/*      */   protected String getStructureNamePrefix() {
/*      */     String structureNamePrefix = null;
/*      */     QName portTypeName = (QName)this.info.modelPort.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*      */     if (portTypeName != null) {
/*      */       structureNamePrefix = getNonQualifiedNameFor(portTypeName);
/*      */     } else {
/*      */       structureNamePrefix = getNonQualifiedNameFor(this.info.modelPort.getName());
/*      */     } 
/*      */     structureNamePrefix = structureNamePrefix + "_";
/*      */     return structureNamePrefix;
/*      */   }
/*      */   
/*      */   protected Operation processSOAPOperationRPCLiteralStyle() {
/*      */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*      */     Request request = new Request();
/*      */     Response response = new Response();
/*      */     this.info.operation.setUse(SOAPUse.LITERAL);
/*      */     SOAPBody soapRequestBody = getSOAPRequestBody();
/*      */     if (soapRequestBody != null && isRequestMimeMultipart())
/*      */       request.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding", "true"); 
/*      */     SOAPBody soapResponseBody = null;
/*      */     Message outputMessage = null;
/*      */     if (isRequestResponse) {
/*      */       soapResponseBody = getSOAPResponseBody();
/*      */       outputMessage = getOutputMessage();
/*      */       if (outputMessage != null)
/*      */         response.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)outputMessage)); 
/*      */       if (soapResponseBody != null && isResponseMimeMultipart())
/*      */         response.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding", "true"); 
/*      */     } 
/*      */     setSOAPUse();
/*      */     if (!soapRequestBody.isLiteral() || (soapResponseBody != null && !soapResponseBody.isLiteral())) {
/*      */       warn("wsdlmodeler.warning.ignoringOperation.notLiteral", this.info.portTypeOperation.getName());
/*      */       return null;
/*      */     } 
/*      */     if (!validateMimeParts(getMimeParts((Extensible)this.info.bindingOperation.getInput())) || !validateMimeParts(getMimeParts((Extensible)this.info.bindingOperation.getOutput())))
/*      */       return null; 
/*      */     Message inputMessage = getInputMessage();
/*      */     setJavaOperationNameProperty(inputMessage);
/*      */     if (inputMessage != null)
/*      */       request.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)inputMessage)); 
/*      */     Set inputParameterNames = new HashSet();
/*      */     Set outputParameterNames = new HashSet();
/*      */     Set mimeContentParameterNames = new HashSet();
/*      */     String resultParameterName = null;
/*      */     StringBuffer result = new StringBuffer();
/*      */     List parameterList = processParameterOrder(inputParameterNames, outputParameterNames, result);
/*      */     if (result.length() > 0)
/*      */       resultParameterName = result.toString(); 
/*      */     String requestNamespaceURI = getRequestNamespaceURI(soapRequestBody);
/*      */     String responseNamespaceURI = null;
/*      */     if (isRequestResponse)
/*      */       responseNamespaceURI = getResponseNamespaceURI(soapResponseBody); 
/*      */     String structureNamePrefix = getStructureNamePrefix();
/*      */     QName reqBodyName = new QName(requestNamespaceURI, this.info.portTypeOperation.getName());
/*      */     LiteralSequenceType reqType = new LiteralSequenceType(reqBodyName);
/*      */     reqType.setRpcWrapper(true);
/*      */     Block reqBlock = new Block(reqBodyName, (AbstractType)reqType);
/*      */     JavaStructureType requestBodyJavaType = new JavaStructureType(getUniqueClassName(makePackageQualified(StringUtils.capitalize(structureNamePrefix + this._env.getNames().validExternalJavaIdentifier(this.info.uniqueOperationName)) + "_RequestStruct", reqBodyName)), false, reqType);
/*      */     reqType.setJavaType((JavaType)requestBodyJavaType);
/*      */     request.addBodyBlock(reqBlock);
/*      */     LiteralSequenceType resType = null;
/*      */     Block resBlock = null;
/*      */     JavaStructureType responseBodyJavaType = null;
/*      */     if (isRequestResponse) {
/*      */       QName resBodyName = new QName(responseNamespaceURI, this.info.portTypeOperation.getName() + "Response");
/*      */       resType = new LiteralSequenceType(resBodyName);
/*      */       resType.setRpcWrapper(true);
/*      */       resBlock = new Block(resBodyName, (AbstractType)resType);
/*      */       responseBodyJavaType = new JavaStructureType(getUniqueClassName(makePackageQualified(StringUtils.capitalize(structureNamePrefix + this._env.getNames().validExternalJavaIdentifier(this.info.uniqueOperationName)) + "_ResponseStruct", resBodyName)), false, resType);
/*      */       resType.setJavaType((JavaType)responseBodyJavaType);
/*      */       response.addBodyBlock(resBlock);
/*      */     } 
/*      */     if (resultParameterName == null) {
/*      */       this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType", "true");
/*      */     } else {
/*      */       MessagePart part = outputMessage.getPart(resultParameterName);
/*      */       if (isBoundToMimeContent(part)) {
/*      */         List mimeContents = getMimeContents((Extensible)this.info.bindingOperation.getOutput(), getOutputMessage(), resultParameterName);
/*      */         LiteralAttachmentType mimeModelType = getAttachmentType(mimeContents, part);
/*      */         Block block = new Block(new QName(part.getName()), (AbstractType)mimeModelType);
/*      */         response.addAttachmentBlock(block);
/*      */         Parameter outParameter = new Parameter(getEnvironment().getNames().validJavaMemberName(part.getName()));
/*      */         outParameter.setEmbedded(false);
/*      */         outParameter.setType((AbstractType)mimeModelType);
/*      */         outParameter.setBlock(block);
/*      */         outParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         response.addParameter(outParameter);
/*      */         this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", outParameter.getName());
/*      */       } else if (isBoundToSOAPBody(part)) {
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         LiteralType literalType = (LiteralType)verifyResultType((AbstractType)literalType, this.info.operation);
/*      */         LiteralElementMember member = new LiteralElementMember(new QName(null, part.getName()), literalType);
/*      */         member.setRequired(true);
/*      */         JavaStructureMember javaMember = getJavaMember(part, literalType, member);
/*      */         member.setJavaStructureMember(javaMember);
/*      */         resType.add(member);
/*      */         responseBodyJavaType.add(javaMember);
/*      */         Parameter parameter = getParameter(part, literalType, resBlock);
/*      */         response.addParameter(parameter);
/*      */         this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */       } 
/*      */     } 
/*      */     List<String> definitiveParameterList = new ArrayList();
/*      */     for (Iterator<String> iter = parameterList.iterator(); iter.hasNext(); ) {
/*      */       String name = iter.next();
/*      */       boolean isInput = inputParameterNames.contains(name);
/*      */       boolean isOutput = outputParameterNames.contains(name);
/*      */       boolean isBoundToSoapBody = isBoundToSOAPBody(inputMessage.getPart(name));
/*      */       boolean isBoundToMimeContent = isBoundToMimeContent(inputMessage.getPart(name));
/*      */       Parameter outParameter = null, inParameter = outParameter;
/*      */       if (isInput && isOutput && isBoundToSOAPBody(inputMessage.getPart(name)))
/*      */         if (!inputMessage.getPart(name).getDescriptor().equals(outputMessage.getPart(name).getDescriptor()))
/*      */           throw new ModelerException("wsdlmodeler.invalid.parameter.differentTypes", new Object[] { name, this.info.operation.getName().getLocalPart() });  
/*      */       if (isInput && isBoundToSOAPBody(inputMessage.getPart(name))) {
/*      */         MessagePart part = inputMessage.getPart(name);
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         LiteralType literalType = (LiteralType)verifyParameterType((AbstractType)literalType, part.getName(), this.info.operation);
/*      */         inParameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */         inParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         inParameter.setEmbedded(true);
/*      */         inParameter.setType((AbstractType)literalType);
/*      */         inParameter.setBlock(reqBlock);
/*      */         request.addParameter(inParameter);
/*      */         definitiveParameterList.add(inParameter.getName());
/*      */         addParameterToStructures(part, inParameter, (LiteralStructuredType)reqType, requestBodyJavaType);
/*      */       } else if (isInput && isBoundToMimeContent(inputMessage.getPart(name))) {
/*      */         MessagePart part = inputMessage.getPart(name);
/*      */         List mimeContents = getMimeContents((Extensible)this.info.bindingOperation.getInput(), getInputMessage(), name);
/*      */         LiteralAttachmentType mimeModelType = getAttachmentType(mimeContents, part);
/*      */         Block block = new Block(new QName(part.getName()), (AbstractType)mimeModelType);
/*      */         request.addAttachmentBlock(block);
/*      */         inParameter = new Parameter(getEnvironment().getNames().validJavaMemberName(part.getName()));
/*      */         inParameter.setEmbedded(false);
/*      */         inParameter.setType((AbstractType)mimeModelType);
/*      */         inParameter.setBlock(block);
/*      */         inParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         request.addParameter(inParameter);
/*      */         definitiveParameterList.add(inParameter.getName());
/*      */       } 
/*      */       if (isOutput && isBoundToSOAPBody(outputMessage.getPart(name))) {
/*      */         MessagePart part = outputMessage.getPart(name);
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         LiteralType literalType = (LiteralType)verifyParameterType((AbstractType)literalType, part.getName(), this.info.operation);
/*      */         outParameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */         outParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         outParameter.setEmbedded(true);
/*      */         outParameter.setType((AbstractType)literalType);
/*      */         outParameter.setBlock(resBlock);
/*      */         response.addParameter(outParameter);
/*      */         addParameterToStructures(part, outParameter, (LiteralStructuredType)resType, responseBodyJavaType);
/*      */         if (inParameter == null) {
/*      */           definitiveParameterList.add(outParameter.getName());
/*      */           continue;
/*      */         } 
/*      */         inParameter.setLinkedParameter(outParameter);
/*      */         outParameter.setLinkedParameter(inParameter);
/*      */         continue;
/*      */       } 
/*      */       if (isOutput && isBoundToMimeContent(outputMessage.getPart(name))) {
/*      */         MessagePart part = outputMessage.getPart(name);
/*      */         if (part != null) {
/*      */           List mimeContents = getMimeContents((Extensible)this.info.bindingOperation.getOutput(), getOutputMessage(), name);
/*      */           LiteralAttachmentType mimeModelType = getAttachmentType(mimeContents, part);
/*      */           Block block = new Block(new QName(part.getName()), (AbstractType)mimeModelType);
/*      */           response.addAttachmentBlock(block);
/*      */           outParameter = new Parameter(getEnvironment().getNames().validJavaMemberName(part.getName()));
/*      */           outParameter.setEmbedded(false);
/*      */           outParameter.setType((AbstractType)mimeModelType);
/*      */           outParameter.setBlock(block);
/*      */           outParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           response.addParameter(outParameter);
/*      */           if (inParameter == null) {
/*      */             definitiveParameterList.add(outParameter.getName());
/*      */             continue;
/*      */           } 
/*      */           Parameter inParam = request.getParameterByName(inParameter.getName());
/*      */           List inMimeTypes = ((LiteralAttachmentType)inParameter.getType()).getAlternateMIMETypes();
/*      */           List outMimeTypes = ((LiteralAttachmentType)outParameter.getType()).getAlternateMIMETypes();
/*      */           boolean sameMimeTypes = true;
/*      */           if (inMimeTypes.size() == outMimeTypes.size()) {
/*      */             Iterator<String> inTypesIter = inMimeTypes.iterator();
/*      */             Iterator<String> outTypesIter = outMimeTypes.iterator();
/*      */             while (inTypesIter.hasNext()) {
/*      */               String inTypeName = inTypesIter.next();
/*      */               String outTypeName = outTypesIter.next();
/*      */               if (!inTypeName.equals(outTypeName)) {
/*      */                 sameMimeTypes = false;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           String inMimeType = ((LiteralAttachmentType)inParam.getType()).getMIMEType();
/*      */           String outMimeType = ((LiteralAttachmentType)outParameter.getType()).getMIMEType();
/*      */           if (inParameter.getType().getName().equals(outParameter.getType().getName()) && sameMimeTypes) {
/*      */             outParameter.setLinkedParameter(inParameter);
/*      */             inParameter.setLinkedParameter(outParameter);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     this.info.operation.setRequest(request);
/*      */     if (isRequestResponse)
/*      */       this.info.operation.setResponse(response); 
/*      */     Set duplicateNames = getDuplicateFaultNames();
/*      */     handleLiteralSOAPFault(response, duplicateNames);
/*      */     boolean explicitServiceContext = useExplicitServiceContextForRpcLit(inputMessage);
/*      */     if (explicitServiceContext) {
/*      */       handleLiteralSOAPHeaders(request, response, getHeaderExtensions((Extensible)this.info.bindingOperation.getInput()), duplicateNames, definitiveParameterList, true);
/*      */       if (isRequestResponse)
/*      */         handleLiteralSOAPHeaders(request, response, getHeaderExtensions((Extensible)this.info.bindingOperation.getOutput()), duplicateNames, definitiveParameterList, false); 
/*      */     } 
/*      */     this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder", definitiveParameterList);
/*      */     return this.info.operation;
/*      */   }
/*      */   
/*      */   protected boolean isBoundToMimeContent(MessagePart part) {
/*      */     if (part != null && part.getBindingExtensibilityElementKind() == 5)
/*      */       return true; 
/*      */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isBoundToSOAPBody(MessagePart part) {
/*      */     if (part != null && part.getBindingExtensibilityElementKind() == 1)
/*      */       return true; 
/*      */     return false;
/*      */   }
/*      */   
/*      */   private Iterator getHeaderExtensions(Extensible extensible) {
/*      */     List<Object> headerList = new ArrayList();
/*      */     Iterator<Extension> bindingIter = extensible.extensions();
/*      */     while (bindingIter.hasNext()) {
/*      */       Extension extension = bindingIter.next();
/*      */       if (extension.getClass().equals(MIMEMultipartRelated.class)) {
/*      */         Iterator<Extension> parts = ((MIMEMultipartRelated)extension).getParts();
/*      */         while (parts.hasNext()) {
/*      */           Extension part = parts.next();
/*      */           if (part.getClass().equals(MIMEPart.class)) {
/*      */             boolean isRootPart = isRootPart((MIMEPart)part);
/*      */             Iterator iter = ((MIMEPart)part).extensions();
/*      */             while (iter.hasNext()) {
/*      */               Object obj = iter.next();
/*      */               if (obj instanceof SOAPHeader) {
/*      */                 if (!isRootPart) {
/*      */                   warn("mimemodeler.warning.IgnoringinvalidHeaderPart.notDeclaredInRootPart", new Object[] { this.info.bindingOperation.getName() });
/*      */                   return (new ArrayList()).iterator();
/*      */                 } 
/*      */                 headerList.add(obj);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         continue;
/*      */       } 
/*      */       if (extension instanceof SOAPHeader)
/*      */         headerList.add(extension); 
/*      */     } 
/*      */     return headerList.iterator();
/*      */   }
/*      */   
/*      */   private boolean isRootPart(MIMEPart part) {
/*      */     Iterator iter = part.extensions();
/*      */     while (iter.hasNext()) {
/*      */       if (iter.next() instanceof SOAPBody)
/*      */         return true; 
/*      */     } 
/*      */     return false;
/*      */   }
/*      */   
/*      */   protected void handleLiteralSOAPFault(Response response, Set duplicateNames) {
/*      */     for (Iterator<BindingFault> iter = this.info.bindingOperation.faults(); iter.hasNext(); ) {
/*      */       LiteralType literalType;
/*      */       QName elemName;
/*      */       BindingFault bindingFault = iter.next();
/*      */       Fault portTypeFault = null;
/*      */       Iterator<Fault> iter2 = this.info.portTypeOperation.faults();
/*      */       while (iter2.hasNext()) {
/*      */         Fault aFault = iter2.next();
/*      */         if (aFault.getName().equals(bindingFault.getName())) {
/*      */           if (portTypeFault != null)
/*      */             throw new ModelerException("wsdlmodeler.invalid.bindingFault.notUnique", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */           portTypeFault = aFault;
/*      */         } 
/*      */       } 
/*      */       if (portTypeFault == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.notFound", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       Fault fault = new Fault(portTypeFault.getMessage().getLocalPart());
/*      */       SOAPFault soapFault = (SOAPFault)getExtensionOfType((Extensible)bindingFault, SOAPFault.class);
/*      */       if (soapFault == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.outputMissingSoapFault", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       if (!soapFault.isLiteral()) {
/*      */         warn("wsdlmodeler.warning.ignoringFault.notLiteral", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       if (this.useWSIBasicProfile)
/*      */         if (soapFault.getName() == null) {
/*      */           warn("wsdlmodeler.invalid.bindingFault.noSoapFaultName", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() });
/*      */         } else if (!soapFault.getName().equals(bindingFault.getName())) {
/*      */           warn("wsdlmodeler.invalid.bindingFault.wrongSoapFaultName", new Object[] { soapFault.getName(), bindingFault.getName(), this.info.bindingOperation.getName() });
/*      */         } else if (soapFault.getNamespace() != null) {
/*      */           warn("wsdlmodeler.warning.r2716r2726", new Object[] { "soapbind:fault", soapFault.getName() });
/*      */         }  
/*      */       String faultNamespaceURI = soapFault.getNamespace();
/*      */       if (faultNamespaceURI == null)
/*      */         faultNamespaceURI = portTypeFault.getMessage().getNamespaceURI(); 
/*      */       Message faultMessage = portTypeFault.resolveMessage((AbstractDocument)this.info.document);
/*      */       Iterator<MessagePart> iterator = faultMessage.parts();
/*      */       if (!iterator.hasNext())
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.emptyMessage", new Object[] { bindingFault.getName(), faultMessage.getName() }); 
/*      */       MessagePart faultPart = iterator.next();
/*      */       QName faultQName = new QName(faultNamespaceURI, faultMessage.getName());
/*      */       if (duplicateNames.contains(faultQName)) {
/*      */         warn("wsdlmodeler.duplicate.fault.soap.name", new Object[] { bindingFault.getName(), this.info.portTypeOperation.getName(), faultPart.getName() });
/*      */         continue;
/*      */       } 
/*      */       if (iterator.hasNext())
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.messageHasMoreThanOnePart", new Object[] { bindingFault.getName(), faultMessage.getName() }); 
/*      */       if (faultPart.getDescriptorKind() != SchemaKinds.XSD_ELEMENT && this.useWSIBasicProfile)
/*      */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveElementDescriptor", new Object[] { faultMessage.getName(), faultPart.getName() }); 
/*      */       if (faultPart.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */         literalType = this._analyzer.schemaTypeToLiteralType(faultPart.getDescriptor());
/*      */       } else {
/*      */         literalType = getElementTypeToLiteralType(faultPart.getDescriptor());
/*      */       } 
/*      */       LiteralSequenceType partType = new LiteralSequenceType(faultPart.getDescriptor());
/*      */       if (this.info.soapBinding.getStyle() == SOAPStyle.RPC && !(literalType instanceof LiteralStructuredType)) {
/*      */         elemName = new QName(faultNamespaceURI, faultPart.getName());
/*      */       } else {
/*      */         elemName = faultPart.getDescriptor();
/*      */       } 
/*      */       fault.setElementName(elemName);
/*      */       fault.setJavaMemberName(faultPart.getName());
/*      */       Block faultBlock = new Block(faultQName, (AbstractType)literalType);
/*      */       fault.setBlock(faultBlock);
/*      */       createParentFault(fault);
/*      */       createSubfaults(fault);
/*      */       response.addFaultBlock(faultBlock);
/*      */       this.info.operation.addFault(fault);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleLiteralSOAPHeaders(Request request, Response response, Iterator<Extension> iter, Set duplicateNames, List<String> definitiveParameterList, boolean processRequest) {
/*      */     while (iter.hasNext()) {
/*      */       Extension extension = iter.next();
/*      */       if (extension instanceof SOAPHeader) {
/*      */         LiteralType headerType;
/*      */         SOAPHeader header = (SOAPHeader)extension;
/*      */         if (this.useWSIBasicProfile && !header.isLiteral() && !header.isEncoded())
/*      */           header.setUse(SOAPUse.LITERAL); 
/*      */         if (!header.isLiteral()) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.notLiteral", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         if (header.getNamespace() != null)
/*      */           warn("wsdlmodeler.warning.r2716r2726", new Object[] { "soapbind:header", this.info.bindingOperation.getName() }); 
/*      */         Message headerMessage = findMessage(header.getMessage(), this.info);
/*      */         if (headerMessage == null) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.cant.resolve.message", new Object[] { header.getMessage(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         MessagePart part = headerMessage.getPart(header.getPart());
/*      */         if (part == null) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.notFound", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         if (part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT && this.useWSIBasicProfile) {
/*      */           warn("wsdlmodeler.invalid.message.partMustHaveElementDescriptor", new Object[] { headerMessage.getName(), part.getName() });
/*      */           warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         if (processRequest) {
/*      */           if (isHeaderPartPresentInBody(getSOAPRequestBody(), getInputMessage(), header.getPart(), true)) {
/*      */             warn("wsdlmodeler.warning.ignoringHeader.partFromBody", new Object[] { header.getPart() });
/*      */             warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */             continue;
/*      */           } 
/*      */         } else if (isHeaderPartPresentInBody(getSOAPResponseBody(), getOutputMessage(), header.getPart(), false)) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.partFromBody", new Object[] { header.getPart() });
/*      */           warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           headerType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           headerType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         Block block = new Block(part.getDescriptor(), (AbstractType)headerType);
/*      */         block.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)headerMessage));
/*      */         AbstractType alreadySeenHeaderType = (AbstractType)this.info.headers.get(block.getName());
/*      */         if (alreadySeenHeaderType != null && alreadySeenHeaderType != headerType) {
/*      */           warn("wsdlmodeler.warning.ignoringHeader.inconsistentDefinition", new Object[] { header.getPart(), this.info.bindingOperation.getName() });
/*      */           continue;
/*      */         } 
/*      */         this.info.headers.put(block.getName(), headerType);
/*      */         if (processRequest) {
/*      */           request.addHeaderBlock(block);
/*      */         } else {
/*      */           response.addHeaderBlock(block);
/*      */         } 
/*      */         Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */         parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         parameter.setEmbedded(false);
/*      */         parameter.setType((AbstractType)headerType);
/*      */         parameter.setBlock(block);
/*      */         if (processRequest && definitiveParameterList != null) {
/*      */           request.addParameter(parameter);
/*      */           definitiveParameterList.add(parameter.getName());
/*      */         } else {
/*      */           if (definitiveParameterList != null) {
/*      */             Iterator<String> iterInParams = definitiveParameterList.iterator();
/*      */             while (iterInParams.hasNext()) {
/*      */               String inParamName = iterInParams.next();
/*      */               if (inParamName.equals(parameter.getName())) {
/*      */                 Parameter inParam = request.getParameterByName(inParamName);
/*      */                 parameter.setLinkedParameter(inParam);
/*      */                 inParam.setLinkedParameter(parameter);
/*      */               } 
/*      */             } 
/*      */             if (!definitiveParameterList.contains(parameter.getName()))
/*      */               definitiveParameterList.add(parameter.getName()); 
/*      */           } 
/*      */           response.addParameter(parameter);
/*      */         } 
/*      */         processHeaderFaults(header, this.info, response, duplicateNames);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isHeaderPartPresentInBody(SOAPBody body, Message message, String headerPart, boolean isInput) {
/*      */     Iterator<MessagePart> parts = getMessageParts(body, message, isInput).iterator();
/*      */     while (parts.hasNext()) {
/*      */       if (((MessagePart)parts.next()).getName().equals(headerPart))
/*      */         return true; 
/*      */     } 
/*      */     return false;
/*      */   }
/*      */   
/*      */   private Set getDuplicateFaultNames() {
/*      */     Set<QName> faultNames = new HashSet();
/*      */     Set<QName> duplicateNames = new HashSet();
/*      */     for (Iterator<BindingFault> iter = this.info.bindingOperation.faults(); iter.hasNext(); ) {
/*      */       BindingFault bindingFault = iter.next();
/*      */       Fault portTypeFault = null;
/*      */       Iterator<Fault> iter2 = this.info.portTypeOperation.faults();
/*      */       while (iter2.hasNext()) {
/*      */         Fault aFault = iter2.next();
/*      */         if (aFault.getName().equals(bindingFault.getName())) {
/*      */           if (portTypeFault != null)
/*      */             throw new ModelerException("wsdlmodeler.invalid.bindingFault.notUnique", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */           portTypeFault = aFault;
/*      */         } 
/*      */       } 
/*      */       if (portTypeFault == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.notFound", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       SOAPFault soapFault = (SOAPFault)getExtensionOfType((Extensible)bindingFault, SOAPFault.class);
/*      */       if (soapFault == null)
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.outputMissingSoapFault", new Object[] { bindingFault.getName(), this.info.bindingOperation.getName() }); 
/*      */       Message faultMessage = portTypeFault.resolveMessage((AbstractDocument)this.info.document);
/*      */       Iterator<MessagePart> iterator = faultMessage.parts();
/*      */       if (!iterator.hasNext())
/*      */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.emptyMessage", new Object[] { bindingFault.getName(), faultMessage.getName() }); 
/*      */       if (this.useWSIBasicProfile && soapFault.getNamespace() != null)
/*      */         warn("wsdlmodeler.warning.r2716r2726", new Object[] { "soapbind:fault", soapFault.getName() }); 
/*      */       String faultNamespaceURI = soapFault.getNamespace();
/*      */       if (faultNamespaceURI == null)
/*      */         faultNamespaceURI = portTypeFault.getMessage().getNamespaceURI(); 
/*      */       MessagePart faultPart = iterator.next();
/*      */       String faultName = getFaultName(faultPart.getName(), soapFault.getName(), bindingFault.getName(), faultMessage.getName());
/*      */       QName faultQName = new QName(faultNamespaceURI, faultName);
/*      */       if (faultNames.contains(faultQName)) {
/*      */         duplicateNames.add(faultQName);
/*      */         continue;
/*      */       } 
/*      */       faultNames.add(faultQName);
/*      */     } 
/*      */     return duplicateNames;
/*      */   }
/*      */   
/*      */   protected String getFaultName(String faultPartName, String soapFaultName, String bindFaultName, String faultMessageName) {
/*      */     return faultMessageName;
/*      */   }
/*      */   
/*      */   private Parameter getParameter(MessagePart part, LiteralType literalType, Block resBlock) {
/*      */     Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */     parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */     parameter.setEmbedded(true);
/*      */     parameter.setType((AbstractType)literalType);
/*      */     parameter.setBlock(resBlock);
/*      */     return parameter;
/*      */   }
/*      */   
/*      */   private JavaStructureMember getJavaMember(MessagePart part, LiteralType literalType, LiteralElementMember member) {
/*      */     JavaStructureMember javaMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(part.getName()), literalType.getJavaType(), member, false);
/*      */     javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */     javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */     return javaMember;
/*      */   }
/*      */   
/*      */   protected void addParameterToStructures(MessagePart part, Parameter parameter, LiteralStructuredType literalType, JavaStructureType javaType) {
/*      */     LiteralElementMember member = new LiteralElementMember(new QName(part.getName()), (LiteralType)parameter.getType());
/*      */     member.setRequired(true);
/*      */     JavaStructureMember javaMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(parameter.getName()), parameter.getType().getJavaType(), member, false);
/*      */     javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */     javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */     member.setJavaStructureMember(javaMember);
/*      */     literalType.add(member);
/*      */     javaType.add(javaMember);
/*      */   }
/*      */   
/*      */   protected boolean isUnwrappable(Message inputMessage) {
/*      */     return isUnwrappable();
/*      */   }
/*      */   
/*      */   protected boolean isUnwrappable() {
/*      */     Message inputMessage = getInputMessage();
/*      */     Message outputMessage = getOutputMessage();
/*      */     if (!this.strictCompliance) {
/*      */       if (!this.useWSIBasicProfile)
/*      */         return true; 
/*      */       if (!this.unwrap)
/*      */         return false; 
/*      */     } 
/*      */     if ((inputMessage != null && inputMessage.numParts() != 1) || (outputMessage != null && outputMessage.numParts() != 1))
/*      */       return false; 
/*      */     MessagePart inputPart = (inputMessage != null) ? inputMessage.parts().next() : null;
/*      */     MessagePart outputPart = (outputMessage != null) ? outputMessage.parts().next() : null;
/*      */     String operationName = this.info.operation.getName().getLocalPart();
/*      */     if ((inputPart != null && !inputPart.getDescriptor().getLocalPart().equals(operationName)) || (outputPart != null && !outputPart.getDescriptor().getLocalPart().startsWith(operationName)))
/*      */       return false; 
/*      */     LiteralType inputType = null;
/*      */     LiteralType outputType = null;
/*      */     if (inputPart != null)
/*      */       inputType = getElementTypeToLiteralType(inputPart.getDescriptor()); 
/*      */     if (outputPart != null)
/*      */       outputType = getElementTypeToLiteralType(outputPart.getDescriptor()); 
/*      */     if (!isWrapperType(inputType) || !isWrapperType(outputType))
/*      */       return false; 
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected boolean isWrapperType(LiteralType type) {
/*      */     if (type != null && (!(type instanceof LiteralSequenceType) || ((LiteralSequenceType)type).getAttributeMembersCount() > 0))
/*      */       return false; 
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected boolean typeHasNoWildcardElement(LiteralType type, boolean unwrappable) {
/*      */     if (!unwrappable)
/*      */       return unwrappable; 
/*      */     if (type == null || (type != null && !(type instanceof LiteralSequenceType)))
/*      */       return unwrappable; 
/*      */     LiteralSequenceType sequenceType = (LiteralSequenceType)type;
/*      */     for (Iterator<LiteralElementMember> iter2 = sequenceType.getElementMembers(); iter2.hasNext(); ) {
/*      */       LiteralElementMember element = iter2.next();
/*      */       if (element.isWildcard())
/*      */         return false; 
/*      */     } 
/*      */     return unwrappable;
/*      */   }
/*      */   
/*      */   protected void setUnwrapped(LiteralStructuredType type) {
/*      */     if (type instanceof LiteralSequenceType)
/*      */       ((LiteralSequenceType)type).setUnwrapped(true); 
/*      */   }
/*      */   
/*      */   protected Operation processSOAPOperationDocumentLiteralStyle() {
/*      */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*      */     Request request = new Request();
/*      */     Response response = new Response();
/*      */     this.info.operation.setUse(SOAPUse.LITERAL);
/*      */     SOAPBody soapRequestBody = getSOAPRequestBody();
/*      */     if (soapRequestBody != null && isRequestMimeMultipart())
/*      */       request.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding", "true"); 
/*      */     if (this.useWSIBasicProfile && soapRequestBody.getNamespace() != null)
/*      */       warn("wsdlmodeler.warning.r2716", new Object[] { "soapbind:body", this.info.bindingOperation.getName() }); 
/*      */     SOAPBody soapResponseBody = null;
/*      */     Message outputMessage = null;
/*      */     if (isRequestResponse) {
/*      */       soapResponseBody = getSOAPResponseBody();
/*      */       if (this.useWSIBasicProfile && soapResponseBody.getNamespace() != null)
/*      */         warn("wsdlmodeler.warning.r2716", new Object[] { "soapbind:body", this.info.bindingOperation.getName() }); 
/*      */       outputMessage = getOutputMessage();
/*      */       if (outputMessage != null)
/*      */         response.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)outputMessage)); 
/*      */       if (soapResponseBody != null && isResponseMimeMultipart())
/*      */         response.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding", "true"); 
/*      */     } 
/*      */     setSOAPUse();
/*      */     if (!soapRequestBody.isLiteral() || (soapResponseBody != null && !soapResponseBody.isLiteral())) {
/*      */       warn("wsdlmodeler.warning.ignoringOperation.notLiteral", this.info.portTypeOperation.getName());
/*      */       return null;
/*      */     } 
/*      */     if (!validateMimeParts(getMimeParts((Extensible)this.info.bindingOperation.getInput())) || !validateMimeParts(getMimeParts((Extensible)this.info.bindingOperation.getOutput())))
/*      */       return null; 
/*      */     if (!validateBodyParts(this.info.bindingOperation)) {
/*      */       warn("wsdlmodeler.warning.ignoringOperation.cannotHandleTypeMessagePart", this.info.portTypeOperation.getName());
/*      */       return null;
/*      */     } 
/*      */     Message inputMessage = getInputMessage();
/*      */     setJavaOperationNameProperty(inputMessage);
/*      */     if (inputMessage != null)
/*      */       request.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getQNameOf((GloballyKnown)inputMessage)); 
/*      */     Set inputParameterNames = new HashSet();
/*      */     Set outputParameterNames = new HashSet();
/*      */     Set mimeContentParameterNames = new HashSet();
/*      */     String resultParameterName = null;
/*      */     StringBuffer result = new StringBuffer();
/*      */     List parameterList = processParameterOrder(inputParameterNames, outputParameterNames, result);
/*      */     if (result.length() > 0)
/*      */       resultParameterName = result.toString(); 
/*      */     boolean unwrappable = isUnwrappable(inputMessage);
/*      */     if (resultParameterName == null) {
/*      */       this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType", "true");
/*      */     } else {
/*      */       MessagePart part = outputMessage.getPart(resultParameterName);
/*      */       if (isBoundToSOAPBody(part)) {
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         LiteralType literalType = (LiteralType)verifyParameterType((AbstractType)literalType, part.getName(), this.info.operation);
/*      */         unwrappable = typeHasNoWildcardElement(literalType, unwrappable);
/*      */         Block block = null;
/*      */         if (!this.useWSIBasicProfile && part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           block = new Block(new QName(resultParameterName), (AbstractType)literalType);
/*      */         } else {
/*      */           block = new Block(part.getDescriptor(), (AbstractType)literalType);
/*      */         } 
/*      */         response.addBodyBlock(block);
/*      */         if (literalType instanceof LiteralStructuredType) {
/*      */           int memberCount = ((LiteralStructuredType)literalType).getElementMembersCount() + ((LiteralStructuredType)literalType).getAttributeMembersCount();
/*      */           if (((LiteralStructuredType)literalType).getContentMember() != null)
/*      */             memberCount++; 
/*      */           boolean j2eeUnwrap = false;
/*      */           if (this.info.operation.getProperty("J2EE_UNWRAP") != null)
/*      */             j2eeUnwrap = true; 
/*      */           if (memberCount == 0 && unwrappable && (this.useWSIBasicProfile || this.strictCompliance || j2eeUnwrap)) {
/*      */             setUnwrapped((LiteralStructuredType)literalType);
/*      */             this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType", "true");
/*      */           } else if (memberCount == 1 && unwrappable) {
/*      */             LiteralStructuredType structuredType = (LiteralStructuredType)literalType;
/*      */             setUnwrapped(structuredType);
/*      */             JavaStructureType javaStructureType = (JavaStructureType)structuredType.getJavaType();
/*      */             Iterator<LiteralAttributeMember> iter2 = structuredType.getAttributeMembers();
/*      */             if (iter2.hasNext()) {
/*      */               LiteralAttributeMember attribute = iter2.next();
/*      */               Parameter parameter = new Parameter(attribute.getJavaStructureMember().getName());
/*      */               parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", attribute.getName().getLocalPart());
/*      */               parameter.setEmbedded(true);
/*      */               parameter.setType((AbstractType)attribute.getType());
/*      */               parameter.setBlock(block);
/*      */               response.addParameter(parameter);
/*      */               this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */             } else {
/*      */               iter2 = structuredType.getElementMembers();
/*      */               LiteralElementMember element = (LiteralElementMember)iter2.next();
/*      */               Parameter parameter = new Parameter(element.getJavaStructureMember().getName());
/*      */               parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", element.getName().getLocalPart());
/*      */               parameter.setEmbedded(true);
/*      */               if (element.isRepeated()) {
/*      */                 LiteralArrayType arrayType = new LiteralArrayType();
/*      */                 arrayType.setName(new QName("synthetic-array-type"));
/*      */                 arrayType.setElementType(element.getType());
/*      */                 JavaArrayType javaArrayType = new JavaArrayType(element.getType().getJavaType().getName() + "[]");
/*      */                 javaArrayType.setElementType(element.getType().getJavaType());
/*      */                 arrayType.setJavaType((JavaType)javaArrayType);
/*      */                 parameter.setType((AbstractType)arrayType);
/*      */               } else {
/*      */                 parameter.setType((AbstractType)element.getType());
/*      */               } 
/*      */               parameter.setBlock(block);
/*      */               response.addParameter(parameter);
/*      */               this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */             } 
/*      */           } else {
/*      */             Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */             parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */             parameter.setEmbedded(false);
/*      */             parameter.setType((AbstractType)literalType);
/*      */             parameter.setBlock(block);
/*      */             response.addParameter(parameter);
/*      */             this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */           } 
/*      */         } else {
/*      */           Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */           parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           parameter.setEmbedded(false);
/*      */           parameter.setType((AbstractType)literalType);
/*      */           parameter.setBlock(block);
/*      */           response.addParameter(parameter);
/*      */           this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */         } 
/*      */       } else if (isBoundToMimeContent(part)) {
/*      */         if (part != null) {
/*      */           List mimeContents = getMimeContents((Extensible)this.info.bindingOperation.getOutput(), getOutputMessage(), resultParameterName);
/*      */           LiteralAttachmentType mimeModelType = getAttachmentType(mimeContents, part);
/*      */           Block block = new Block(new QName(part.getName()), (AbstractType)mimeModelType);
/*      */           response.addAttachmentBlock(block);
/*      */           Parameter outMimeParameter = new Parameter(getEnvironment().getNames().validJavaMemberName(part.getName()));
/*      */           outMimeParameter.setEmbedded(false);
/*      */           outMimeParameter.setType((AbstractType)mimeModelType);
/*      */           outMimeParameter.setBlock(block);
/*      */           outMimeParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           response.addParameter(outMimeParameter);
/*      */           this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", outMimeParameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     List<String> definitiveParameterList = new ArrayList();
/*      */     for (Iterator<String> iter = parameterList.iterator(); iter.hasNext(); ) {
/*      */       String name = iter.next();
/*      */       boolean isInput = inputParameterNames.contains(name);
/*      */       boolean isOutput = outputParameterNames.contains(name);
/*      */       Parameter inMimeParameter = null;
/*      */       Parameter outMimeParameter = null;
/*      */       if (isInput && isBoundToSOAPBody(inputMessage.getPart(name))) {
/*      */         MessagePart part = inputMessage.getPart(name);
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         LiteralType literalType = (LiteralType)verifyParameterType((AbstractType)literalType, part.getName(), this.info.operation);
/*      */         unwrappable = typeHasNoWildcardElement(literalType, unwrappable);
/*      */         Block block = null;
/*      */         if (!this.useWSIBasicProfile && part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           block = new Block(new QName(name), (AbstractType)literalType);
/*      */         } else {
/*      */           block = new Block(part.getDescriptor(), (AbstractType)literalType);
/*      */         } 
/*      */         request.addBodyBlock(block);
/*      */         if (literalType instanceof LiteralSequenceType && unwrappable) {
/*      */           LiteralSequenceType sequenceType = (LiteralSequenceType)literalType;
/*      */           setUnwrapped((LiteralStructuredType)sequenceType);
/*      */           Iterator<LiteralAttributeMember> iterator = sequenceType.getAttributeMembers();
/*      */           while (iterator.hasNext()) {
/*      */             LiteralAttributeMember attribute = iterator.next();
/*      */             Parameter parameter = new Parameter(attribute.getJavaStructureMember().getName());
/*      */             parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", attribute.getName().getLocalPart());
/*      */             parameter.setEmbedded(true);
/*      */             parameter.setType((AbstractType)attribute.getType());
/*      */             parameter.setBlock(block);
/*      */             request.addParameter(parameter);
/*      */             definitiveParameterList.add(parameter.getName());
/*      */           } 
/*      */           if (sequenceType.getContentMember() != null) {
/*      */             LiteralContentMember content = sequenceType.getContentMember();
/*      */             Parameter parameter = new Parameter(content.getJavaStructureMember().getName());
/*      */             parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", parameter.getName());
/*      */             parameter.setEmbedded(true);
/*      */             parameter.setType((AbstractType)content.getType());
/*      */             parameter.setBlock(block);
/*      */             request.addParameter(parameter);
/*      */             definitiveParameterList.add(parameter.getName());
/*      */           } 
/*      */           Iterator<LiteralElementMember> iter2 = sequenceType.getElementMembers();
/*      */           while (iter2.hasNext()) {
/*      */             LiteralElementMember element = iter2.next();
/*      */             Parameter parameter = new Parameter(element.getJavaStructureMember().getName());
/*      */             parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", element.getName().getLocalPart());
/*      */             parameter.setEmbedded(true);
/*      */             if (element.isRepeated()) {
/*      */               LiteralArrayType arrayType = new LiteralArrayType();
/*      */               arrayType.setName(new QName("synthetic-array-type"));
/*      */               arrayType.setElementType(element.getType());
/*      */               JavaArrayType javaArrayType = new JavaArrayType(element.getType().getJavaType().getName() + "[]");
/*      */               javaArrayType.setElementType(element.getType().getJavaType());
/*      */               arrayType.setJavaType((JavaType)javaArrayType);
/*      */               parameter.setType((AbstractType)arrayType);
/*      */             } else {
/*      */               parameter.setType((AbstractType)element.getType());
/*      */             } 
/*      */             parameter.setBlock(block);
/*      */             request.addParameter(parameter);
/*      */             definitiveParameterList.add(parameter.getName());
/*      */           } 
/*      */         } else {
/*      */           Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */           parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           parameter.setEmbedded(false);
/*      */           parameter.setType((AbstractType)literalType);
/*      */           parameter.setBlock(block);
/*      */           request.addParameter(parameter);
/*      */           definitiveParameterList.add(parameter.getName());
/*      */         } 
/*      */       } else if (isInput && isBoundToMimeContent(inputMessage.getPart(name))) {
/*      */         MessagePart part = inputMessage.getPart(name);
/*      */         List mimeContents = getMimeContents((Extensible)this.info.bindingOperation.getInput(), getInputMessage(), name);
/*      */         LiteralAttachmentType mimeModelType = getAttachmentType(mimeContents, part);
/*      */         Block block = new Block(new QName(part.getName()), (AbstractType)mimeModelType);
/*      */         request.addAttachmentBlock(block);
/*      */         inMimeParameter = new Parameter(getEnvironment().getNames().validJavaMemberName(part.getName()));
/*      */         inMimeParameter.setEmbedded(false);
/*      */         inMimeParameter.setType((AbstractType)mimeModelType);
/*      */         inMimeParameter.setBlock(block);
/*      */         inMimeParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         request.addParameter(inMimeParameter);
/*      */         definitiveParameterList.add(inMimeParameter.getName());
/*      */       } 
/*      */       if (isOutput && isBoundToSOAPBody(outputMessage.getPart(name))) {
/*      */         MessagePart part = outputMessage.getPart(name);
/*      */         if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(part.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(part.getDescriptor());
/*      */         } 
/*      */         LiteralType literalType = (LiteralType)verifyParameterType((AbstractType)literalType, part.getName(), this.info.operation);
/*      */         Block block = null;
/*      */         if (!this.useWSIBasicProfile && part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           block = new Block(new QName(name), (AbstractType)literalType);
/*      */         } else {
/*      */           block = new Block(part.getDescriptor(), (AbstractType)literalType);
/*      */         } 
/*      */         response.addBodyBlock(block);
/*      */         if (literalType instanceof LiteralStructuredType) {
/*      */           int memberCount = ((LiteralStructuredType)literalType).getElementMembersCount() + ((LiteralStructuredType)literalType).getAttributeMembersCount();
/*      */           if (((LiteralStructuredType)literalType).getContentMember() != null)
/*      */             memberCount++; 
/*      */           if (memberCount == 0 && unwrappable && (this.useWSIBasicProfile || this.strictCompliance)) {
/*      */             setUnwrapped((LiteralStructuredType)literalType);
/*      */             continue;
/*      */           } 
/*      */           if (memberCount == 1 && unwrappable) {
/*      */             LiteralStructuredType structuredType = (LiteralStructuredType)literalType;
/*      */             setUnwrapped(structuredType);
/*      */             JavaStructureType javaStructureType = (JavaStructureType)structuredType.getJavaType();
/*      */             Iterator<LiteralAttributeMember> iter2 = structuredType.getAttributeMembers();
/*      */             if (iter2.hasNext()) {
/*      */               LiteralAttributeMember attribute = iter2.next();
/*      */               Parameter parameter3 = new Parameter(attribute.getJavaStructureMember().getName());
/*      */               parameter3.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", attribute.getName().getLocalPart());
/*      */               parameter3.setEmbedded(true);
/*      */               parameter3.setType((AbstractType)attribute.getType());
/*      */               parameter3.setBlock(block);
/*      */               response.addParameter(parameter3);
/*      */               definitiveParameterList.add(parameter3.getName());
/*      */               continue;
/*      */             } 
/*      */             iter2 = structuredType.getElementMembers();
/*      */             LiteralElementMember element = (LiteralElementMember)iter2.next();
/*      */             Parameter parameter2 = new Parameter(element.getJavaStructureMember().getName());
/*      */             parameter2.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", element.getName().getLocalPart());
/*      */             parameter2.setEmbedded(true);
/*      */             if (element.isRepeated()) {
/*      */               LiteralArrayType arrayType = new LiteralArrayType();
/*      */               arrayType.setName(new QName("synthetic-array-type"));
/*      */               arrayType.setElementType(element.getType());
/*      */               JavaArrayType javaArrayType = new JavaArrayType(element.getType().getJavaType().getName() + "[]");
/*      */               javaArrayType.setElementType(element.getType().getJavaType());
/*      */               arrayType.setJavaType((JavaType)javaArrayType);
/*      */               parameter2.setType((AbstractType)arrayType);
/*      */             } else {
/*      */               parameter2.setType((AbstractType)element.getType());
/*      */             } 
/*      */             parameter2.setBlock(block);
/*      */             response.addParameter(parameter2);
/*      */             definitiveParameterList.add(parameter2.getName());
/*      */             continue;
/*      */           } 
/*      */           Parameter parameter1 = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */           parameter1.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           parameter1.setEmbedded(false);
/*      */           parameter1.setType((AbstractType)literalType);
/*      */           parameter1.setBlock(block);
/*      */           response.addParameter(parameter1);
/*      */           definitiveParameterList.add(parameter1.getName());
/*      */           continue;
/*      */         } 
/*      */         Parameter parameter = new Parameter(this._env.getNames().validJavaMemberName(part.getName()));
/*      */         parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */         parameter.setEmbedded(false);
/*      */         parameter.setType((AbstractType)literalType);
/*      */         parameter.setBlock(block);
/*      */         response.addParameter(parameter);
/*      */         definitiveParameterList.add(parameter.getName());
/*      */         continue;
/*      */       } 
/*      */       if (isOutput && isBoundToMimeContent(outputMessage.getPart(name))) {
/*      */         MessagePart part = outputMessage.getPart(name);
/*      */         if (part != null) {
/*      */           List mimeContents = getMimeContents((Extensible)this.info.bindingOperation.getOutput(), getOutputMessage(), name);
/*      */           LiteralAttachmentType mimeModelType = getAttachmentType(mimeContents, part);
/*      */           Block block = new Block(new QName(part.getName()), (AbstractType)mimeModelType);
/*      */           response.addAttachmentBlock(block);
/*      */           outMimeParameter = new Parameter(getEnvironment().getNames().validJavaMemberName(part.getName()));
/*      */           outMimeParameter.setEmbedded(false);
/*      */           outMimeParameter.setType((AbstractType)mimeModelType);
/*      */           outMimeParameter.setBlock(block);
/*      */           outMimeParameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", part.getName());
/*      */           response.addParameter(outMimeParameter);
/*      */           if (inMimeParameter == null) {
/*      */             definitiveParameterList.add(outMimeParameter.getName());
/*      */             continue;
/*      */           } 
/*      */           List inMimeTypes = ((LiteralAttachmentType)inMimeParameter.getType()).getAlternateMIMETypes();
/*      */           List outMimeTypes = ((LiteralAttachmentType)outMimeParameter.getType()).getAlternateMIMETypes();
/*      */           boolean sameMimeTypes = true;
/*      */           if (inMimeTypes.size() == outMimeTypes.size()) {
/*      */             Iterator<String> inTypesIter = inMimeTypes.iterator();
/*      */             Iterator<String> outTypesIter = outMimeTypes.iterator();
/*      */             while (inTypesIter.hasNext()) {
/*      */               String inTypeName = inTypesIter.next();
/*      */               String outTypeName = outTypesIter.next();
/*      */               if (!inTypeName.equals(outTypeName)) {
/*      */                 sameMimeTypes = false;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           if (inMimeParameter.getType().getName().equals(outMimeParameter.getType().getName()) && sameMimeTypes) {
/*      */             outMimeParameter.setLinkedParameter(inMimeParameter);
/*      */             inMimeParameter.setLinkedParameter(outMimeParameter);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     this.info.operation.setRequest(request);
/*      */     if (isRequestResponse)
/*      */       this.info.operation.setResponse(response); 
/*      */     Set duplicateNames = getDuplicateFaultNames();
/*      */     handleLiteralSOAPFault(response, duplicateNames);
/*      */     boolean explicitServiceContext = useExplicitServiceContextForDocLit(inputMessage);
/*      */     if (explicitServiceContext) {
/*      */       handleLiteralSOAPHeaders(request, response, getHeaderExtensions((Extensible)this.info.bindingOperation.getInput()), duplicateNames, definitiveParameterList, true);
/*      */       if (isRequestResponse)
/*      */         handleLiteralSOAPHeaders(request, response, getHeaderExtensions((Extensible)this.info.bindingOperation.getOutput()), duplicateNames, definitiveParameterList, false); 
/*      */     } 
/*      */     this.info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder", definitiveParameterList);
/*      */     return this.info.operation;
/*      */   }
/*      */   
/*      */   private boolean validateBodyParts(BindingOperation operation) {
/*      */     boolean isRequestResponse = (this.info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*      */     List inputParts = getMessageParts(getSOAPRequestBody(), getInputMessage(), true);
/*      */     if (!validateStyleAndPart(operation, inputParts))
/*      */       return false; 
/*      */     if (isRequestResponse) {
/*      */       List outputParts = getMessageParts(getSOAPResponseBody(), getOutputMessage(), false);
/*      */       if (!validateStyleAndPart(operation, outputParts))
/*      */         return false; 
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   private boolean validateStyleAndPart(BindingOperation operation, List parts) {
/*      */     SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)operation, SOAPOperation.class);
/*      */     for (Iterator<MessagePart> iter = parts.iterator(); iter.hasNext(); ) {
/*      */       MessagePart part = iter.next();
/*      */       if (part.getBindingExtensibilityElementKind() == 1 && !isStyleAndPartMatch(soapOperation, part))
/*      */         return false; 
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected void processHeaderFaults(SOAPHeader header, ProcessSOAPOperationInfo info, Response response, Set duplicateNames) {
/*      */     Iterator<Extension> faults = header.faults();
/*      */     while (faults.hasNext()) {
/*      */       LiteralType literalType;
/*      */       Extension extn = faults.next();
/*      */       if (!(extn instanceof SOAPHeaderFault))
/*      */         return; 
/*      */       SOAPHeaderFault headerFault = (SOAPHeaderFault)extn;
/*      */       if (null == headerFault.getMessage())
/*      */         return; 
/*      */       if (this.useWSIBasicProfile && headerFault.getNamespace() != null)
/*      */         warn("wsdlmodeler.warning.r2716r2726", new Object[] { "soapbind:headerfault", info.bindingOperation.getName() }); 
/*      */       String faultNamespaceURI = headerFault.getNamespace();
/*      */       Message faultMessage = findMessage(headerFault.getMessage(), info);
/*      */       if (faultMessage == null) {
/*      */         warn("wsdlmodeler.warning.ignoringFault.cant.resolve.message", new Object[] { header.getMessage(), info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       MessagePart faultPart = faultMessage.getPart(headerFault.getPart());
/*      */       if (faultPart == null) {
/*      */         warn("wsdlmodeler.warning.ignoringHeaderFault.notFound", new Object[] { header.getMessage(), headerFault.getPart(), info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       QName faultQName = new QName(faultNamespaceURI, faultPart.getName());
/*      */       if (duplicateNames.contains(faultQName)) {
/*      */         warn("wsdlmodeler.duplicate.fault.part.name", new Object[] { headerFault.getMessage(), info.portTypeOperation.getName(), faultPart.getName() });
/*      */         continue;
/*      */       } 
/*      */       if (faultPart.getDescriptorKind() != SchemaKinds.XSD_TYPE && headerFault.getUse() == SOAPUse.ENCODED) {
/*      */         warn("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { faultMessage.getName(), faultPart.getName() });
/*      */         warn("wsdlmodeler.warning.ignoringHeaderFault", new Object[] { headerFault.getPart(), info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       if (faultPart.getDescriptorKind() != SchemaKinds.XSD_ELEMENT && headerFault.getUse() == SOAPUse.LITERAL) {
/*      */         warn("wsdlmodeler.invalid.message.partMustHaveElementDescriptor", new Object[] { faultMessage.getName(), faultPart.getName() });
/*      */         warn("wsdlmodeler.warning.ignoringHeaderFault", new Object[] { headerFault.getPart(), info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       if (headerFault.getUse() == SOAPUse.LITERAL) {
/*      */         if (faultPart.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */           literalType = this._analyzer.schemaTypeToLiteralType(faultPart.getDescriptor());
/*      */         } else {
/*      */           literalType = getElementTypeToLiteralType(faultPart.getDescriptor());
/*      */         } 
/*      */       } else {
/*      */         warn("wsdlmodeler.warning.ignoringHeader.notLiteral", new Object[] { header.getPart(), info.bindingOperation.getName() });
/*      */         continue;
/*      */       } 
/*      */       HeaderFault fault = new HeaderFault(faultPart.getDescriptor().toString());
/*      */       fault.setElementName(faultPart.getDescriptor());
/*      */       fault.setMessage(headerFault.getMessage());
/*      */       fault.setPart(headerFault.getPart());
/*      */       AbstractType faultType = getHeaderFaultSequenceType((AbstractType)literalType, faultPart, faultPart.getDescriptor());
/*      */       Block faultBlock = new Block(faultPart.getDescriptor(), faultType);
/*      */       fault.setBlock(faultBlock);
/*      */       createParentFault((Fault)fault);
/*      */       createSubfaults((Fault)fault);
/*      */       response.addFaultBlock(faultBlock);
/*      */       info.operation.addFault((Fault)fault);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected AbstractType getHeaderFaultSequenceType(AbstractType faultType, MessagePart faultPart, QName elemName) {
/*      */     if (faultType instanceof LiteralSimpleType) {
/*      */       LiteralSimpleType faultSimpleType = new LiteralSimpleType(faultType.getName(), (JavaSimpleType)faultType.getJavaType());
/*      */       LiteralSequenceType structureType = new LiteralSequenceType(faultPart.getDescriptor());
/*      */       JavaStructureType javaStructureType = new JavaStructureType(makePackageQualified(this._env.getNames().validJavaClassName(structureType.getName().getLocalPart()), structureType.getName()), false, structureType);
/*      */       structureType.setJavaType((JavaType)javaStructureType);
/*      */       LiteralElementMember member = new LiteralElementMember(elemName, (LiteralType)faultSimpleType);
/*      */       JavaStructureMember javaMember = new JavaStructureMember(faultPart.getName(), faultSimpleType.getJavaType(), member, false);
/*      */       javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */       javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */       member.setJavaStructureMember(javaMember);
/*      */       javaStructureType.add(javaMember);
/*      */       structureType.add(member);
/*      */       return (AbstractType)structureType;
/*      */     } 
/*      */     return faultType;
/*      */   }
/*      */   
/*      */   protected String getJavaNameOfSEI(Port port) {
/*      */     QName portTypeName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*      */     QName bindingName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*      */     String interfaceName = null;
/*      */     if (portTypeName != null) {
/*      */       interfaceName = makePackageQualified(this._env.getNames().validJavaClassName(getNonQualifiedNameFor(portTypeName)), portTypeName, false);
/*      */     } else {
/*      */       interfaceName = makePackageQualified(this._env.getNames().validJavaClassName(getNonQualifiedNameFor(port.getName())), port.getName(), false);
/*      */     } 
/*      */     return interfaceName;
/*      */   }
/*      */   
/*      */   protected void createJavaInterfaceForPort(Port port) {
/*      */     String interfaceName = getJavaNameOfSEI(port);
/*      */     if (isConflictingPortClassName(interfaceName))
/*      */       interfaceName = interfaceName + "_PortType"; 
/*      */     JavaInterface intf = new JavaInterface(interfaceName);
/*      */     Set methodNames = new HashSet();
/*      */     Set methodSignatures = new HashSet();
/*      */     for (Iterator<Operation> iter = port.getOperations(); iter.hasNext(); ) {
/*      */       Operation operation = iter.next();
/*      */       createJavaMethodForOperation(port, operation, intf, methodNames, methodSignatures);
/*      */     } 
/*      */     port.setJavaInterface(intf);
/*      */   }
/*      */   
/*      */   protected String getJavaNameForOperation(Operation operation) {
/*      */     return this._env.getNames().validJavaMemberName(operation.getName().getLocalPart());
/*      */   }
/*      */   
/*      */   private void createJavaMethodForOperation(Port port, Operation operation, JavaInterface intf, Set<String> methodNames, Set<String> methodSignatures) {
/*      */     String candidateName = getJavaNameForOperation(operation);
/*      */     JavaMethod method = new JavaMethod(candidateName);
/*      */     Request request = operation.getRequest();
/*      */     Iterator requestBodyBlocks = request.getBodyBlocks();
/*      */     Block requestBlock = requestBodyBlocks.hasNext() ? request.getBodyBlocks().next() : null;
/*      */     Response response = operation.getResponse();
/*      */     Iterator responseBodyBlocks = null;
/*      */     Block responseBlock = null;
/*      */     if (response != null) {
/*      */       responseBodyBlocks = response.getBodyBlocks();
/*      */       responseBlock = responseBodyBlocks.hasNext() ? response.getBodyBlocks().next() : null;
/*      */     } 
/*      */     String signature = candidateName;
/*      */     List parameterOrder = (List)operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder");
/*      */     if (parameterOrder == null) {
/*      */       for (Iterator<Parameter> iterator = request.getParameters(); iterator.hasNext(); ) {
/*      */         Parameter parameter = iterator.next();
/*      */         if (parameter.getJavaParameter() != null)
/*      */           throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart()); 
/*      */         JavaType parameterType = parameter.getType().getJavaType();
/*      */         JavaParameter javaParameter = new JavaParameter(this._env.getNames().validJavaMemberName(parameter.getName()), parameterType, parameter, (parameter.getLinkedParameter() != null));
/*      */         method.addParameter(javaParameter);
/*      */         parameter.setJavaParameter(javaParameter);
/*      */         signature = signature + "%" + parameterType.getName();
/*      */       } 
/*      */       boolean operationHasVoidReturnType = (operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType") != null);
/*      */       if (response != null) {
/*      */         Parameter resultParameter = null;
/*      */         Iterator<Parameter> iterator2 = response.getParameters();
/*      */         while (iterator2.hasNext()) {
/*      */           if (!operationHasVoidReturnType && resultParameter == null) {
/*      */             resultParameter = iterator2.next();
/*      */             if (resultParameter.getJavaParameter() != null)
/*      */               throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart()); 
/*      */             if (resultParameter.getLinkedParameter() != null)
/*      */               throw new ModelerException("wsdlmodeler.resultIsInOutParameter", operation.getName().getLocalPart()); 
/*      */             if (resultParameter.getBlock() != responseBlock)
/*      */               throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart()); 
/*      */             JavaType returnType = resultParameter.getType().getJavaType();
/*      */             method.setReturnType(returnType);
/*      */             continue;
/*      */           } 
/*      */           Parameter parameter = iterator2.next();
/*      */           if (parameter.getJavaParameter() != null)
/*      */             throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart()); 
/*      */           JavaParameter javaParameter = null;
/*      */           if (parameter.getLinkedParameter() != null)
/*      */             javaParameter = parameter.getLinkedParameter().getJavaParameter(); 
/*      */           JavaType parameterType = parameter.getType().getJavaType();
/*      */           parameterType.setHolder(true);
/*      */           parameterType.setHolderPresent(false);
/*      */           if (javaParameter == null)
/*      */             javaParameter = new JavaParameter(this._env.getNames().validJavaMemberName(parameter.getName()), parameterType, parameter, true); 
/*      */           parameter.setJavaParameter(javaParameter);
/*      */           if (parameter.getLinkedParameter() == null)
/*      */             method.addParameter(javaParameter); 
/*      */         } 
/*      */       } 
/*      */       if (response == null || operationHasVoidReturnType)
/*      */         method.setReturnType((JavaType)this._javaTypes.VOID_JAVATYPE); 
/*      */     } else {
/*      */       boolean operationHasVoidReturnType = (operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType") != null);
/*      */       for (Iterator<String> iterator = parameterOrder.iterator(); iterator.hasNext(); ) {
/*      */         String parameterName = iterator.next();
/*      */         Parameter requestParameter = request.getParameterByName(parameterName);
/*      */         Parameter responseParameter = (response != null) ? response.getParameterByName(parameterName) : null;
/*      */         if (requestParameter == null && responseParameter == null)
/*      */           throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart()); 
/*      */         if (requestParameter != null) {
/*      */           Parameter linkedParameter = requestParameter.getLinkedParameter();
/*      */           if (responseParameter == null || linkedParameter == null) {
/*      */             JavaType javaType = requestParameter.getType().getJavaType();
/*      */             JavaParameter javaParameter1 = new JavaParameter(this._env.getNames().validJavaMemberName(requestParameter.getName()), javaType, requestParameter, false);
/*      */             method.addParameter(javaParameter1);
/*      */             requestParameter.setJavaParameter(javaParameter1);
/*      */             signature = signature + "%" + javaType.getName();
/*      */             continue;
/*      */           } 
/*      */           if (responseParameter != linkedParameter)
/*      */             throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart()); 
/*      */           JavaType parameterType = responseParameter.getType().getJavaType();
/*      */           JavaParameter javaParameter = new JavaParameter(this._env.getNames().validJavaMemberName(responseParameter.getName()), parameterType, responseParameter, true);
/*      */           parameterType.setHolder(true);
/*      */           parameterType.setHolderPresent(false);
/*      */           requestParameter.setJavaParameter(javaParameter);
/*      */           responseParameter.setJavaParameter(javaParameter);
/*      */           method.addParameter(javaParameter);
/*      */           requestParameter.setJavaParameter(javaParameter);
/*      */           responseParameter.setJavaParameter(javaParameter);
/*      */           signature = signature + "%" + parameterType.getName();
/*      */           continue;
/*      */         } 
/*      */         if (responseParameter != null) {
/*      */           Parameter linkedParameter = responseParameter.getLinkedParameter();
/*      */           if (linkedParameter != null)
/*      */             throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart()); 
/*      */           JavaType parameterType = responseParameter.getType().getJavaType();
/*      */           parameterType.setHolder(true);
/*      */           parameterType.setHolderPresent(false);
/*      */           JavaParameter javaParameter = new JavaParameter(this._env.getNames().validJavaMemberName(responseParameter.getName()), parameterType, responseParameter, true);
/*      */           responseParameter.setJavaParameter(javaParameter);
/*      */           method.addParameter(javaParameter);
/*      */           signature = signature + "%" + parameterType.getName();
/*      */         } 
/*      */       } 
/*      */       String resultParameterName = (String)operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter");
/*      */       if (resultParameterName == null) {
/*      */         if (!operationHasVoidReturnType)
/*      */           throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart()); 
/*      */         method.setReturnType((JavaType)this._javaTypes.VOID_JAVATYPE);
/*      */       } else {
/*      */         if (operationHasVoidReturnType)
/*      */           throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart()); 
/*      */         Parameter resultParameter = response.getParameterByName(resultParameterName);
/*      */         JavaType returnType = resultParameter.getType().getJavaType();
/*      */         method.setReturnType(returnType);
/*      */       } 
/*      */     } 
/*      */     String operationName = candidateName;
/*      */     if (methodSignatures.contains(signature)) {
/*      */       operationName = makeNameUniqueInSet(candidateName, methodNames);
/*      */       method.setName(operationName);
/*      */     } 
/*      */     methodSignatures.add(signature);
/*      */     methodNames.add(method.getName());
/*      */     operation.setJavaMethod(method);
/*      */     intf.addMethod(method);
/*      */     Iterator<Fault> iter = operation.getFaults();
/*      */     while (iter != null && iter.hasNext()) {
/*      */       Fault fault = iter.next();
/*      */       createJavaException(fault, port, operationName);
/*      */     } 
/*      */     for (Iterator<Fault> iterator1 = operation.getFaults(); iterator1.hasNext(); ) {
/*      */       Fault fault = iterator1.next();
/*      */       JavaException javaException = fault.getJavaException();
/*      */       method.addException(javaException.getName());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean createJavaException(Fault fault, Port port, String operationName) {
/*      */     if (fault.getBlock().getType().isSOAPType())
/*      */       return createJavaExceptionFromSOAPType(fault, port, operationName); 
/*      */     return createJavaExceptionFromLiteralType(fault, port, operationName);
/*      */   }
/*      */   
/*      */   protected WSDLExceptionInfo getExceptionInfo(Fault fault) {
/*      */     return null;
/*      */   }
/*      */   
/*      */   protected boolean createJavaExceptionFromSOAPType(Fault fault, Port port, String operationName) {
/*      */     SOAPOrderedStructureType sOAPOrderedStructureType;
/*      */     String exceptionName = null;
/*      */     String propertyName = this._env.getNames().validJavaMemberName(fault.getName());
/*      */     SOAPType faultType = (SOAPType)fault.getBlock().getType();
/*      */     WSDLExceptionInfo exInfo = getExceptionInfo(fault);
/*      */     if (faultType instanceof SOAPStructureType) {
/*      */       if (exInfo != null) {
/*      */         exceptionName = exInfo.exceptionType;
/*      */       } else {
/*      */         exceptionName = makePackageQualified(this._env.getNames().validJavaClassName(faultType.getName().getLocalPart()), faultType.getName());
/*      */       } 
/*      */       SOAPStructureType soapStruct = (SOAPStructureType)this._faultTypeToStructureMap.get(faultType.getName());
/*      */       if (soapStruct == null) {
/*      */         sOAPOrderedStructureType = new SOAPOrderedStructureType(faultType.getName());
/*      */         SOAPStructureType temp = (SOAPStructureType)faultType;
/*      */         Iterator<SOAPStructureMember> iterator = temp.getMembers();
/*      */         while (iterator.hasNext())
/*      */           sOAPOrderedStructureType.add(iterator.next()); 
/*      */         this._faultTypeToStructureMap.put(faultType.getName(), sOAPOrderedStructureType);
/*      */       } 
/*      */     } else {
/*      */       if (exInfo != null) {
/*      */         exceptionName = exInfo.exceptionType;
/*      */       } else {
/*      */         exceptionName = makePackageQualified(this._env.getNames().validJavaClassName(fault.getName()), port.getName());
/*      */       } 
/*      */       sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(fault.getBlock().getName().getNamespaceURI(), fault.getName()));
/*      */       QName memberName = fault.getElementName();
/*      */       SOAPStructureMember soapMember = new SOAPStructureMember(memberName, faultType);
/*      */       String javaMemberName = fault.getJavaMemberName();
/*      */       if (javaMemberName == null)
/*      */         javaMemberName = memberName.getLocalPart(); 
/*      */       JavaStructureMember javaMember = new JavaStructureMember(javaMemberName, faultType.getJavaType(), soapMember);
/*      */       soapMember.setJavaStructureMember(javaMember);
/*      */       javaMember.setConstructorPos(0);
/*      */       javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */       javaMember.setInherited(soapMember.isInherited());
/*      */       soapMember.setJavaStructureMember(javaMember);
/*      */       sOAPOrderedStructureType.add(soapMember);
/*      */     } 
/*      */     if (isConflictingExceptionClassName(exceptionName))
/*      */       exceptionName = exceptionName + "_Exception"; 
/*      */     JavaException existingJavaException = (JavaException)this._javaExceptions.get(exceptionName);
/*      */     if (existingJavaException != null && existingJavaException.getName().equals(exceptionName) && (((SOAPType)existingJavaException.getOwner()).getName().equals(sOAPOrderedStructureType.getName()) || isEquivalentSOAPStructures((SOAPStructureType)sOAPOrderedStructureType, (SOAPStructureType)existingJavaException.getOwner()))) {
/*      */       if (faultType instanceof SOAPStructureType)
/*      */         fault.getBlock().setType((AbstractType)existingJavaException.getOwner()); 
/*      */       fault.setJavaException(existingJavaException);
/*      */       createRelativeJavaExceptions(fault, port, operationName);
/*      */       return false;
/*      */     } 
/*      */     JavaException javaException = new JavaException(exceptionName, false, sOAPOrderedStructureType);
/*      */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/*      */     this._javaExceptions.put(javaException.getName(), javaException);
/*      */     Iterator<SOAPStructureMember> members = sOAPOrderedStructureType.getMembers();
/*      */     SOAPStructureMember member = null;
/*      */     for (int i = 0; members.hasNext(); i++) {
/*      */       member = members.next();
/*      */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */       if (faultType instanceof SOAPStructureType && exInfo != null) {
/*      */         String memberName = member.getName().getLocalPart();
/*      */         Integer pos = (Integer)exInfo.constructorOrder.get(memberName);
/*      */         if (pos == null)
/*      */           throw new ModelerException("unable to find member " + memberName + " in jaxrpcmapping meta for exception whose wsdl message is: " + fault.getBlock().getName()); 
/*      */         javaMember.setConstructorPos(pos.intValue());
/*      */       } else {
/*      */         javaMember.setConstructorPos(i);
/*      */       } 
/*      */       javaException.add(javaMember);
/*      */     } 
/*      */     if (faultType instanceof SOAPStructureType)
/*      */       fault.getBlock().setType((AbstractType)sOAPOrderedStructureType); 
/*      */     fault.setJavaException(javaException);
/*      */     createRelativeJavaExceptions(fault, port, operationName);
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected String getLiteralJavaMemberName(Fault fault) {
/*      */     QName memberName = fault.getElementName();
/*      */     String javaMemberName = fault.getJavaMemberName();
/*      */     if (javaMemberName == null)
/*      */       javaMemberName = memberName.getLocalPart(); 
/*      */     return javaMemberName;
/*      */   }
/*      */   
/*      */   public boolean isEquivalentSOAPStructures(SOAPStructureType struct1, SOAPStructureType struct2) {
/*      */     if (struct1.getMembersCount() != struct2.getMembersCount())
/*      */       return false; 
/*      */     Iterator<SOAPStructureMember> members = struct1.getMembers();
/*      */     for (int i = 0; members.hasNext(); i++) {
/*      */       SOAPStructureMember member1 = members.next();
/*      */       JavaStructureMember javaMember1 = member1.getJavaStructureMember();
/*      */       JavaStructureMember javaMember2 = ((JavaStructureType)struct2.getJavaType()).getMemberByName(member1.getJavaStructureMember().getName());
/*      */       if (javaMember2.getConstructorPos() != i || !javaMember1.getType().equals(javaMember2.getType()))
/*      */         return false; 
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   public boolean isEquivalentLiteralStructures(LiteralStructuredType struct1, LiteralStructuredType struct2) {
/*      */     if (struct1.getElementMembersCount() != struct2.getElementMembersCount() || struct1.getAttributeMembersCount() != struct2.getAttributeMembersCount())
/*      */       return false; 
/*      */     Iterator<LiteralElementMember> members = struct1.getElementMembers();
/*      */     for (int i = 0; members.hasNext(); i++) {
/*      */       LiteralElementMember member1 = members.next();
/*      */       JavaStructureMember javaMember1 = member1.getJavaStructureMember();
/*      */       JavaStructureMember javaMember2 = ((JavaStructureType)struct2.getJavaType()).getMemberByName(member1.getJavaStructureMember().getName());
/*      */       if (javaMember2.getConstructorPos() != i || !javaMember1.getType().equals(javaMember2.getType()))
/*      */         return false; 
/*      */     } 
/*      */     members = struct1.getAttributeMembers();
/*      */     for (int j = 0; members.hasNext(); j++) {
/*      */       LiteralAttributeMember member = (LiteralAttributeMember)members.next();
/*      */       JavaStructureMember javaMember1 = member.getJavaStructureMember();
/*      */       JavaStructureMember javaMember2 = ((JavaStructureType)struct2.getJavaType()).getMemberByName(member.getJavaStructureMember().getName());
/*      */       if (javaMember2.getConstructorPos() != j || !javaMember1.getType().equals(javaMember2.getType()))
/*      */         return false; 
/*      */     } 
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected boolean createJavaExceptionFromLiteralType(Fault fault, Port port, String operationName) {
/*      */     LiteralSequenceType literalSequenceType;
/*      */     WSDLExceptionInfo exInfo = getExceptionInfo(fault);
/*      */     String exceptionName = null;
/*      */     String propertyName = this._env.getNames().validJavaMemberName(fault.getName());
/*      */     LiteralType faultType = (LiteralType)fault.getBlock().getType();
/*      */     if (faultType instanceof LiteralStructuredType) {
/*      */       if (exInfo != null) {
/*      */         exceptionName = exInfo.exceptionType;
/*      */       } else {
/*      */         exceptionName = makePackageQualified(this._env.getNames().validJavaClassName(faultType.getName().getLocalPart()), faultType.getName());
/*      */       } 
/*      */       LiteralStructuredType literalStruct = (LiteralStructuredType)this._faultTypeToStructureMap.get(faultType.getName());
/*      */       if (literalStruct == null) {
/*      */         literalSequenceType = new LiteralSequenceType(faultType.getName());
/*      */         LiteralStructuredType temp = (LiteralStructuredType)faultType;
/*      */         Iterator<LiteralElementMember> iterator = temp.getElementMembers();
/*      */         while (iterator.hasNext())
/*      */           literalSequenceType.add(iterator.next()); 
/*      */         Iterator<LiteralAttributeMember> iterator1 = temp.getAttributeMembers();
/*      */         while (iterator1.hasNext()) {
/*      */           LiteralAttributeMember attribute = iterator1.next();
/*      */           literalSequenceType.add(attribute);
/*      */         } 
/*      */         this._faultTypeToStructureMap.put(faultType.getName(), literalSequenceType);
/*      */       } 
/*      */     } else {
/*      */       if (exInfo != null) {
/*      */         exceptionName = exInfo.exceptionType;
/*      */       } else {
/*      */         exceptionName = makePackageQualified(this._env.getNames().validJavaClassName(fault.getName()), port.getName());
/*      */       } 
/*      */       literalSequenceType = new LiteralSequenceType(new QName(fault.getBlock().getName().getNamespaceURI(), fault.getName()));
/*      */       QName memberName = fault.getElementName();
/*      */       LiteralElementMember literalMember = new LiteralElementMember(memberName, faultType);
/*      */       literalMember.setNillable(faultType.isNillable());
/*      */       String javaMemberName = getLiteralJavaMemberName(fault);
/*      */       JavaStructureMember javaMember = new JavaStructureMember(javaMemberName, faultType.getJavaType(), literalMember);
/*      */       literalMember.setJavaStructureMember(javaMember);
/*      */       javaMember.setConstructorPos(0);
/*      */       javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */       javaMember.setInherited(false);
/*      */       literalMember.setJavaStructureMember(javaMember);
/*      */       literalSequenceType.add(literalMember);
/*      */     } 
/*      */     if (isConflictingExceptionClassName(exceptionName))
/*      */       exceptionName = exceptionName + "_Exception"; 
/*      */     JavaException existingJavaException = (JavaException)this._javaExceptions.get(exceptionName);
/*      */     if (existingJavaException != null && existingJavaException.getName().equals(exceptionName) && (((LiteralType)existingJavaException.getOwner()).getName().equals(literalSequenceType.getName()) || isEquivalentLiteralStructures((LiteralStructuredType)literalSequenceType, (LiteralStructuredType)existingJavaException.getOwner()))) {
/*      */       if (faultType instanceof LiteralStructuredType)
/*      */         fault.getBlock().setType((AbstractType)existingJavaException.getOwner()); 
/*      */       fault.setJavaException(existingJavaException);
/*      */       createRelativeJavaExceptions(fault, port, operationName);
/*      */       return false;
/*      */     } 
/*      */     JavaException javaException = new JavaException(exceptionName, false, literalSequenceType);
/*      */     literalSequenceType.setJavaType((JavaType)javaException);
/*      */     this._javaExceptions.put(javaException.getName(), javaException);
/*      */     int constPos = 0;
/*      */     Iterator<LiteralAttributeMember> iter2 = literalSequenceType.getAttributeMembers();
/*      */     while (iter2.hasNext()) {
/*      */       LiteralAttributeMember attribute = iter2.next();
/*      */       JavaStructureMember javaMember = attribute.getJavaStructureMember();
/*      */       javaMember.setConstructorPos(constPos++);
/*      */       javaException.add(javaMember);
/*      */     } 
/*      */     Iterator<LiteralElementMember> members = literalSequenceType.getElementMembers();
/*      */     LiteralElementMember member = null;
/*      */     for (int i = constPos; members.hasNext(); i++) {
/*      */       member = members.next();
/*      */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */       if (faultType instanceof LiteralStructuredType && exInfo != null) {
/*      */         String memberName = null;
/*      */         if (member instanceof com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember) {
/*      */           memberName = javaMember.getName();
/*      */         } else {
/*      */           memberName = member.getName().getLocalPart();
/*      */         } 
/*      */         Integer pos = (Integer)exInfo.constructorOrder.get(memberName);
/*      */         if (pos == null)
/*      */           throw new ModelerException("unable to find member " + memberName + " in jaxrpcmapping meta for exception whose wsdl message is: " + fault.getBlock().getName()); 
/*      */         javaMember.setConstructorPos(pos.intValue());
/*      */       } else {
/*      */         javaMember.setConstructorPos(i);
/*      */       } 
/*      */       javaException.add(javaMember);
/*      */     } 
/*      */     if (faultType instanceof LiteralStructuredType)
/*      */       fault.getBlock().setType((AbstractType)literalSequenceType); 
/*      */     fault.setJavaException(javaException);
/*      */     createRelativeJavaExceptions(fault, port, operationName);
/*      */     return true;
/*      */   }
/*      */   
/*      */   protected void createRelativeJavaExceptions(Fault fault, Port port, String operationName) {
/*      */     if (fault.getParentFault() != null && fault.getParentFault().getJavaException() == null) {
/*      */       createJavaException(fault.getParentFault(), port, operationName);
/*      */       fault.getParentFault().getJavaException().addSubclass((JavaStructureType)fault.getJavaException());
/*      */       if (fault.getParentFault().getJavaException().getOwner() instanceof SOAPStructureType) {
/*      */         ((SOAPStructureType)fault.getParentFault().getJavaException().getOwner()).addSubtype((SOAPStructureType)fault.getJavaException().getOwner());
/*      */       } else if (fault.getParentFault().getJavaException().getOwner() instanceof LiteralStructuredType) {
/*      */         ((LiteralStructuredType)fault.getParentFault().getJavaException().getOwner()).addSubtype((LiteralStructuredType)fault.getJavaException().getOwner());
/*      */       } 
/*      */     } 
/*      */     Iterator<Fault> subfaults = fault.getSubfaults();
/*      */     if (subfaults != null)
/*      */       while (subfaults.hasNext()) {
/*      */         Fault subfault = subfaults.next();
/*      */         if (subfault.getJavaException() == null) {
/*      */           boolean didCreateNewException = createJavaException(subfault, port, operationName);
/*      */           fault.getJavaException().addSubclass((JavaStructureType)subfault.getJavaException());
/*      */           if (fault.getJavaException().getOwner() instanceof SOAPStructureType) {
/*      */             ((SOAPStructureType)fault.getJavaException().getOwner()).addSubtype((SOAPStructureType)subfault.getJavaException().getOwner());
/*      */             continue;
/*      */           } 
/*      */           if (fault.getJavaException().getOwner() instanceof LiteralStructuredType)
/*      */             ((LiteralStructuredType)fault.getJavaException().getOwner()).addSubtype((LiteralStructuredType)subfault.getJavaException().getOwner()); 
/*      */         } 
/*      */       }  
/*      */   }
/*      */   
/*      */   private List getMimeContents(Extensible ext, Message message, String name) {
/*      */     Iterator<MIMEPart> mimeParts = getMimeParts(ext);
/*      */     String mimeContentPartName = null;
/*      */     while (mimeParts.hasNext()) {
/*      */       MIMEPart mimePart = mimeParts.next();
/*      */       List mimeContents = getMimeContents(mimePart);
/*      */       Iterator<MIMEContent> mimeIter = mimeContents.iterator();
/*      */       if (mimeIter.hasNext()) {
/*      */         MIMEContent mimeContent = mimeIter.next();
/*      */         mimeContentPartName = mimeContent.getPart();
/*      */         if (mimeContentPartName.equals(name))
/*      */           return mimeContents; 
/*      */       } 
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   private LiteralAttachmentType getAttachmentType(List mimeContents, MessagePart part) {
/*      */     JavaSimpleType type = null;
/*      */     MimeHelper mimeHelper = new MimeHelper();
/*      */     boolean useDataHandlerOnly = Boolean.valueOf(this._options.getProperty("useDataHandlerOnly")).booleanValue();
/*      */     List<String> mimeTypes = getAlternateMimeTypes(mimeContents);
/*      */     if (mimeTypes.size() > 1) {
/*      */       type = MimeHelper.javaType.DATA_HANDLER_JAVATYPE;
/*      */     } else if (mimeTypes.size() == 1) {
/*      */       String mimeType = mimeTypes.iterator().next();
/*      */       type = (JavaSimpleType)MimeHelper.mimeTypeToJavaType.get(mimeType);
/*      */       if (type == null && mimeType.startsWith("multipart/")) {
/*      */         type = (JavaSimpleType)MimeHelper.mimeTypeToJavaType.get("multipart/*");
/*      */       } else if (type == null || useDataHandlerOnly) {
/*      */         type = MimeHelper.javaType.DATA_HANDLER_JAVATYPE;
/*      */       } 
/*      */     } 
/*      */     LiteralType contentType = null;
/*      */     if (part.getDescriptorKind() == SchemaKinds.XSD_TYPE) {
/*      */       contentType = getSchemaAnalyzer().schemaTypeToLiteralType(part.getDescriptor());
/*      */     } else if (part.getDescriptorKind() == SchemaKinds.XSD_ELEMENT) {
/*      */       for (Iterator<String> mimeTypeIter = mimeTypes.iterator(); mimeTypeIter.hasNext(); ) {
/*      */         String mimeType = mimeTypeIter.next();
/*      */         if (!mimeType.equals("text/xml") && !mimeType.equals("applicatioon/xml"))
/*      */           warn("mimemodeler.elementPart.invalidElementMimeType", new Object[] { part.getName(), mimeType }); 
/*      */         contentType = getElementTypeToLiteralType(part.getDescriptor());
/*      */       } 
/*      */     } 
/*      */     if (contentType == null)
/*      */       throw new ModelerException("mimemodeler.invalidMimeContent.unknownSchemaType", new Object[] { part.getName(), part.getDescriptor() }); 
/*      */     LiteralAttachmentType mimeModelType = new LiteralAttachmentType(contentType.getName(), (JavaType)type);
/*      */     if (mimeModelType == null)
/*      */       throw new ModelerException("mimemodeler.invalidMimeContent.invalidSchemaType", new Object[] { part.getName(), part.getDescriptor() }); 
/*      */     if (mimeTypes.size() >= 1)
/*      */       mimeModelType.setMIMEType(mimeTypes.iterator().next()); 
/*      */     mimeModelType.setContentID(MimeHelper.getAttachmentUniqueID(part.getName()));
/*      */     mimeModelType.addAlternateMIMEType(getAlternateMimeTypes(mimeContents).iterator());
/*      */     return mimeModelType;
/*      */   }
/*      */   
/*      */   protected ProcessorEnvironment getEnvironment() {
/*      */     return this._env;
/*      */   }
/*      */   
/*      */   protected void warn(Localizable msg) {
/*      */     getEnvironment().warn(msg);
/*      */   }
/*      */   
/*      */   protected void warn(String key) {
/*      */     getEnvironment().warn(this._messageFactory.getMessage(key));
/*      */   }
/*      */   
/*      */   protected void warn(String key, String arg) {
/*      */     getEnvironment().warn(this._messageFactory.getMessage(key, arg));
/*      */   }
/*      */   
/*      */   protected void warn(String key, Object[] args) {
/*      */     getEnvironment().warn(this._messageFactory.getMessage(key, args));
/*      */   }
/*      */   
/*      */   protected void info(String key) {
/*      */     getEnvironment().info(this._messageFactory.getMessage(key));
/*      */   }
/*      */   
/*      */   protected void info(String key, String arg) {
/*      */     getEnvironment().info(this._messageFactory.getMessage(key, arg));
/*      */   }
/*      */   
/*      */   protected String makePackageQualified(String s, QName name) {
/*      */     return makePackageQualified(s, name, true);
/*      */   }
/*      */   
/*      */   protected String makePackageQualified(String s, QName name, boolean useNamespaceMapping) {
/*      */     String javaPackageName = null;
/*      */     if (useNamespaceMapping)
/*      */       javaPackageName = getJavaPackageName(name); 
/*      */     if (javaPackageName != null)
/*      */       return javaPackageName + "." + s; 
/*      */     if (this._modelInfo.getJavaPackageName() != null && !this._modelInfo.getJavaPackageName().equals(""))
/*      */       return this._modelInfo.getJavaPackageName() + "." + s; 
/*      */     return s;
/*      */   }
/*      */   
/*      */   protected QName makePackageQualified(QName name) {
/*      */     return makePackageQualified(name, true);
/*      */   }
/*      */   
/*      */   protected QName makePackageQualified(QName name, boolean useNamespaceMapping) {
/*      */     return new QName(name.getNamespaceURI(), makePackageQualified(name.getLocalPart(), name));
/*      */   }
/*      */   
/*      */   protected String makeNameUniqueInSet(String candidateName, Set names) {
/*      */     String baseName = candidateName;
/*      */     String name = baseName;
/*      */     for (int i = 2; names.contains(name); i++)
/*      */       name = baseName + Integer.toString(i); 
/*      */     return name;
/*      */   }
/*      */   
/*      */   protected String getUniqueName(Operation operation, boolean hasOverloadedOperations) {
/*      */     if (hasOverloadedOperations)
/*      */       return operation.getUniqueKey().replace(' ', '_'); 
/*      */     return operation.getName();
/*      */   }
/*      */   
/*      */   protected String getUniqueParameterName(Operation operation, String baseName) {
/*      */     Set<String> names = new HashSet();
/*      */     Iterator<Parameter> iterator1 = operation.getRequest().getParameters();
/*      */     while (iterator1.hasNext()) {
/*      */       Parameter p = iterator1.next();
/*      */       names.add(p.getName());
/*      */     } 
/*      */     Iterator<Parameter> iter = operation.getResponse().getParameters();
/*      */     while (iter.hasNext()) {
/*      */       Parameter p = iter.next();
/*      */       names.add(p.getName());
/*      */     } 
/*      */     String candidateName = baseName;
/*      */     while (names.contains(candidateName))
/*      */       candidateName = candidateName + "_prime"; 
/*      */     return candidateName;
/*      */   }
/*      */   
/*      */   protected String getNonQualifiedNameFor(QName name) {
/*      */     return this._env.getNames().validJavaClassName(name.getLocalPart());
/*      */   }
/*      */   
/*      */   protected static void setDocumentationIfPresent(ModelObject obj, Documentation documentation) {
/*      */     if (documentation != null && documentation.getContent() != null)
/*      */       obj.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.documentation", documentation.getContent()); 
/*      */   }
/*      */   
/*      */   protected static QName getQNameOf(GloballyKnown entity) {
/*      */     return new QName(entity.getDefining().getTargetNamespaceURI(), entity.getName());
/*      */   }
/*      */   
/*      */   protected static Extension getExtensionOfType(Extensible extensible, Class type) {
/*      */     for (Iterator<Extension> iter = extensible.extensions(); iter.hasNext(); ) {
/*      */       Extension extension = iter.next();
/*      */       if (extension.getClass().equals(type))
/*      */         return extension; 
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   protected Extension getAnyExtensionOfType(Extensible extensible, Class type) {
/*      */     if (extensible == null)
/*      */       return null; 
/*      */     for (Iterator<Extension> iter = extensible.extensions(); iter.hasNext(); ) {
/*      */       Extension extension = iter.next();
/*      */       if (extension.getClass().equals(type))
/*      */         return extension; 
/*      */       if (extension.getClass().equals(MIMEMultipartRelated.class) && (type.equals(SOAPBody.class) || type.equals(MIMEContent.class) || type.equals(MIMEPart.class))) {
/*      */         Iterator<Extension> parts = ((MIMEMultipartRelated)extension).getParts();
/*      */         while (parts.hasNext()) {
/*      */           Extension part = parts.next();
/*      */           if (part.getClass().equals(MIMEPart.class)) {
/*      */             MIMEPart mPart = (MIMEPart)part;
/*      */             Extension extn = getExtensionOfType((Extensible)part, type);
/*      */             if (extn != null)
/*      */               return extn; 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   protected static Message findMessage(QName messageName, ProcessSOAPOperationInfo info) {
/*      */     Message message = null;
/*      */     try {
/*      */       message = (Message)info.document.find(Kinds.MESSAGE, messageName);
/*      */     } catch (NoSuchEntityException e) {}
/*      */     return message;
/*      */   }
/*      */   
/*      */   protected static boolean tokenListContains(String tokenList, String target) {
/*      */     if (tokenList == null)
/*      */       return false; 
/*      */     StringTokenizer tokenizer = new StringTokenizer(tokenList, " ");
/*      */     while (tokenizer.hasMoreTokens()) {
/*      */       String s = tokenizer.nextToken();
/*      */       if (target.equals(s))
/*      */         return true; 
/*      */     } 
/*      */     return false;
/*      */   }
/*      */   
/*      */   private String getUniqueClassName(String className) {
/*      */     int cnt = 2;
/*      */     String uniqueName = className;
/*      */     while (this.reqResNames.contains(uniqueName.toLowerCase())) {
/*      */       uniqueName = className + cnt;
/*      */       cnt++;
/*      */     } 
/*      */     this.reqResNames.add(uniqueName.toLowerCase());
/*      */     return uniqueName;
/*      */   }
/*      */   
/*      */   private String getJavaPackageName(QName name) {
/*      */     String packageName = null;
/*      */     if (this._modelInfo.getNamespaceMappingRegistry() != null) {
/*      */       NamespaceMappingInfo i = this._modelInfo.getNamespaceMappingRegistry().getNamespaceMappingInfo(name);
/*      */       if (i != null)
/*      */         return i.getJavaPackageName(); 
/*      */     } 
/*      */     return packageName;
/*      */   }
/*      */   
/*      */   protected boolean isConflictingClassName(String name) {
/*      */     if (this._conflictingClassNames == null)
/*      */       return false; 
/*      */     return this._conflictingClassNames.contains(name);
/*      */   }
/*      */   
/*      */   protected boolean isConflictingServiceClassName(String name) {
/*      */     return isConflictingClassName(name);
/*      */   }
/*      */   
/*      */   protected boolean isConflictingStubClassName(String name) {
/*      */     return isConflictingClassName(name);
/*      */   }
/*      */   
/*      */   protected boolean isConflictingTieClassName(String name) {
/*      */     return isConflictingClassName(name);
/*      */   }
/*      */   
/*      */   protected boolean isConflictingPortClassName(String name) {
/*      */     return isConflictingClassName(name);
/*      */   }
/*      */   
/*      */   protected boolean isConflictingExceptionClassName(String name) {
/*      */     return isConflictingClassName(name);
/*      */   }
/*      */   
/*      */   protected LiteralType getElementTypeToLiteralType(QName elementType) {
/*      */     return this._analyzer.schemaElementTypeToLiteralType(elementType);
/*      */   }
/*      */   
/*      */   private SOAPType getSchemaTypeToSOAPType(QName elementType) {
/*      */     return this._analyzer.schemaTypeToSOAPType(elementType);
/*      */   }
/*      */   
/*      */   private static SOAPWSDLConstants soap11WSDLConstants = null;
/*      */   private static SOAPWSDLConstants soap12WSDLConstants = null;
/*      */   private boolean useWSIBasicProfile;
/*      */   private boolean literalOnly;
/*      */   private boolean unwrap;
/*      */   private boolean strictCompliance;
/*      */   private Model theModel;
/*      */   private Set reqResNames;
/*      */   private static final boolean RPCLIT_PARAM_REQUIRED = true;
/*      */   
/*      */   public WSDLModelerBase(WSDLModelInfo modelInfo, Properties options) {
/* 5571 */     this.useWSIBasicProfile = false;
/* 5572 */     this.literalOnly = false;
/* 5573 */     this.unwrap = true;
/* 5574 */     this.strictCompliance = false;
/*      */     init();
/*      */     this._modelInfo = modelInfo;
/*      */     this._options = options;
/*      */     this._messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.modeler");
/*      */     this._conflictingClassNames = null;
/*      */     this._env = (ProcessorEnvironment)modelInfo.getParent().getEnvironment();
/*      */     this.hSet = null;
/*      */     this.reqResNames = new HashSet();
/*      */   }
/*      */   
/*      */   protected WSDLParser parser;
/*      */   protected HashSet hSet;
/*      */   
/*      */   public class ProcessSOAPOperationInfo
/*      */   {
/*      */     public ProcessSOAPOperationInfo(Port modelPort, Port port, Operation portTypeOperation, BindingOperation bindingOperation, SOAPBinding soapBinding, WSDLDocument document, boolean hasOverloadedOperations, Map headers) {
/* 5591 */       this.modelPort = modelPort;
/* 5592 */       this.port = port;
/* 5593 */       this.portTypeOperation = portTypeOperation;
/* 5594 */       this.bindingOperation = bindingOperation;
/* 5595 */       this.soapBinding = soapBinding;
/* 5596 */       this.document = document;
/* 5597 */       this.hasOverloadedOperations = hasOverloadedOperations;
/* 5598 */       this.headers = headers;
/*      */     }
/*      */     
/*      */     public Port modelPort;
/*      */     public Port port;
/*      */     public Operation portTypeOperation;
/*      */     public BindingOperation bindingOperation;
/*      */     public SOAPBinding soapBinding;
/*      */     public WSDLDocument document;
/*      */     public boolean hasOverloadedOperations;
/*      */     public Map headers;
/*      */     public Operation operation;
/*      */     public String uniqueOperationName;
/*      */   }
/*      */   
/*      */   public static class WSDLExceptionInfo {
/*      */     public String exceptionType;
/*      */     public QName wsdlMessage;
/*      */     public String wsdlMessagePartName;
/*      */     public HashMap constructorOrder;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\WSDLModelerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */