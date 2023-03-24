package com.sun.xml.rpc.processor.modeler.j2ee;

import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
import com.sun.xml.rpc.processor.model.soap.SOAPType;
import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
import javax.xml.namespace.QName;

public interface J2EESchemaAnalyzerIf {
  SOAPType getSuperSOAPMemberType(ComplexTypeDefinitionComponent paramComplexTypeDefinitionComponent, SOAPStructureType paramSOAPStructureType, ElementDeclarationComponent paramElementDeclarationComponent, QName paramQName, boolean paramBoolean);
  
  SchemaAnalyzerBase.SchemaJavaMemberInfo getSuperJavaMemberInfo(TypeDefinitionComponent paramTypeDefinitionComponent, ElementDeclarationComponent paramElementDeclarationComponent);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EESchemaAnalyzerIf.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */