package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import java.util.Collection;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Key;

public abstract class JAXRCommand {
  RegistryServiceImpl service;
  
  UDDIMapper mapper;
  
  BulkResponseImpl response;
  
  BulkResponseImpl content;
  
  public JAXRCommand(RegistryServiceImpl paramRegistryServiceImpl, BulkResponseImpl paramBulkResponseImpl) throws JAXRException {
    this.service = paramRegistryServiceImpl;
    this.response = paramBulkResponseImpl;
    this.mapper = new UDDIMapper(paramRegistryServiceImpl);
  }
  
  abstract void execute() throws JAXRException;
  
  static class SaveServicesCommand extends JAXRCommand {
    Collection services;
    
    public SaveServicesCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.services = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveServices(this.services));
    }
  }
  
  static class SaveServiceBindingsCommand extends JAXRCommand {
    Collection bindings;
    
    public SaveServiceBindingsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.bindings = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveServiceBindings(this.bindings));
    }
  }
  
  static class SaveOrganizationsCommand extends JAXRCommand {
    Collection organizations;
    
    public SaveOrganizationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.organizations = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveOrganizations(this.organizations));
    }
  }
  
  static class SaveObjectsCommand extends JAXRCommand {
    Collection cataloguedObjects;
    
    public SaveObjectsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.cataloguedObjects = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveObjects(this.cataloguedObjects));
    }
  }
  
  static class SaveConceptsCommand extends JAXRCommand {
    Collection concepts;
    
    public SaveConceptsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.concepts = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveConcepts(this.concepts));
    }
  }
  
  static class SaveClassificationSchemesCommand extends JAXRCommand {
    Collection schemes;
    
    public SaveClassificationSchemesCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.schemes = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveClassificationSchemes(this.schemes));
    }
  }
  
  static class SaveAssociationsCommand extends JAXRCommand {
    Collection associations;
    
    boolean replace;
    
    public SaveAssociationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection, boolean param1Boolean) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.associations = param1Collection;
      this.replace = param1Boolean;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.saveAssociations(this.associations, this.replace));
    }
  }
  
  static class GetRegistryObjectsByTypeCommand extends JAXRCommand {
    String objectType;
    
    public GetRegistryObjectsByTypeCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, String param1String) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.objectType = param1String;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.getRegistryObjects(this.objectType));
    }
  }
  
  static class GetRegistryObjectsByKeysCommand extends JAXRCommand {
    Collection objectKeys;
    
    String type;
    
    public GetRegistryObjectsByKeysCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection, String param1String) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.objectKeys = param1Collection;
      this.type = param1String;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.getRegistryObjects(this.objectKeys, this.type));
    }
  }
  
  static class FindCallerAssociationsCommand extends JAXRCommand {
    Collection findQualifiers;
    
    Boolean confirmedByCaller;
    
    Boolean confirmedByOther;
    
    Collection associationTypes;
    
    public FindCallerAssociationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection1, Boolean param1Boolean1, Boolean param1Boolean2, Collection param1Collection2) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.findQualifiers = param1Collection1;
      this.confirmedByCaller = param1Boolean1;
      this.confirmedByOther = param1Boolean2;
      this.associationTypes = param1Collection2;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findCallerAssociations(this.findQualifiers, this.confirmedByCaller, this.confirmedByOther, this.associationTypes));
    }
  }
  
  static class FindAssociationsCommand extends JAXRCommand {
    Collection findQualifiers;
    
    String sourceObjectId;
    
    String targetObjectId;
    
    Collection associationTypes;
    
    public FindAssociationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection1, String param1String1, String param1String2, Collection param1Collection2) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.findQualifiers = param1Collection1;
      this.sourceObjectId = param1String1;
      this.targetObjectId = param1String2;
      this.associationTypes = param1Collection2;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findAssociations(this.findQualifiers, this.sourceObjectId, this.targetObjectId, this.associationTypes));
    }
  }
  
  static class FindServicesCommand extends JAXRCommand {
    Key orgKey;
    
    Collection findQualifiers;
    
    Collection namePatterns;
    
    Collection classifications;
    
    Collection specifications;
    
    public FindServicesCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Key param1Key, Collection param1Collection1, Collection param1Collection2, Collection param1Collection3, Collection param1Collection4) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.orgKey = param1Key;
      this.findQualifiers = param1Collection1;
      this.namePatterns = param1Collection2;
      this.classifications = param1Collection3;
      this.specifications = param1Collection4;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findServices(this.orgKey, this.findQualifiers, this.namePatterns, this.classifications, this.specifications));
    }
  }
  
  static class FindServiceBindingsCommand extends JAXRCommand {
    Key serviceKey;
    
    Collection findQualifiers;
    
    Collection classifications;
    
    Collection specifications;
    
    public FindServiceBindingsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Key param1Key, Collection param1Collection1, Collection param1Collection2, Collection param1Collection3) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.serviceKey = param1Key;
      this.findQualifiers = param1Collection1;
      this.classifications = param1Collection2;
      this.specifications = param1Collection3;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findServiceBindings(this.serviceKey, this.findQualifiers, this.classifications, this.specifications));
    }
  }
  
  static class FindOrganizationsCommand extends JAXRCommand {
    Collection findQualifiers;
    
    Collection namePatterns;
    
    Collection classifications;
    
    Collection specifications;
    
    Collection identifiers;
    
    Collection externalLinks;
    
    public FindOrganizationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection1, Collection param1Collection2, Collection param1Collection3, Collection param1Collection4, Collection param1Collection5, Collection param1Collection6) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.findQualifiers = param1Collection1;
      this.namePatterns = param1Collection2;
      this.classifications = param1Collection3;
      this.specifications = param1Collection4;
      this.identifiers = param1Collection5;
      this.externalLinks = param1Collection6;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findOrganizations(this.findQualifiers, this.namePatterns, this.classifications, this.specifications, this.identifiers, this.externalLinks));
    }
  }
  
  static class FindConceptsCommand extends JAXRCommand {
    Collection findQualifiers;
    
    Collection namePatterns;
    
    Collection classifications;
    
    Collection identifiers;
    
    Collection externalLinks;
    
    public FindConceptsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection1, Collection param1Collection2, Collection param1Collection3, Collection param1Collection4, Collection param1Collection5) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.findQualifiers = param1Collection1;
      this.namePatterns = param1Collection2;
      this.classifications = param1Collection3;
      this.identifiers = param1Collection4;
      this.externalLinks = param1Collection5;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findConcepts(this.findQualifiers, this.namePatterns, this.classifications, this.identifiers, this.externalLinks));
    }
  }
  
  static class FindClassificationSchemesCommand extends JAXRCommand {
    Collection findQualifiers;
    
    Collection namePatterns;
    
    Collection classifications;
    
    Collection externalLinks;
    
    public FindClassificationSchemesCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection1, Collection param1Collection2, Collection param1Collection3, Collection param1Collection4) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.findQualifiers = param1Collection1;
      this.namePatterns = param1Collection2;
      this.classifications = param1Collection3;
      this.externalLinks = param1Collection4;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.findClassificationSchemes(this.findQualifiers, this.namePatterns, this.classifications, this.externalLinks));
    }
  }
  
  static class DeleteServicesCommand extends JAXRCommand {
    Collection serviceKeys;
    
    public DeleteServicesCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.serviceKeys = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteServices(this.serviceKeys));
    }
  }
  
  static class DeleteServiceBindingsCommand extends JAXRCommand {
    Collection bindingKeys;
    
    public DeleteServiceBindingsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.bindingKeys = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteServiceBindings(this.bindingKeys));
    }
  }
  
  static class DeleteOrganizationsCommand extends JAXRCommand {
    Collection organizationKeys;
    
    public DeleteOrganizationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.organizationKeys = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteOrganizations(this.organizationKeys));
    }
  }
  
  static class DeleteObjectsCommand extends JAXRCommand {
    Collection keys;
    
    String type;
    
    public DeleteObjectsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection, String param1String) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.keys = param1Collection;
      this.type = param1String;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteObjects(this.keys, this.type));
    }
  }
  
  static class DeleteConceptsCommand extends JAXRCommand {
    Collection conceptKeys;
    
    public DeleteConceptsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.conceptKeys = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteConcepts(this.conceptKeys));
    }
  }
  
  static class DeleteClassificationSchemesCommand extends JAXRCommand {
    Collection schemeKeys;
    
    public DeleteClassificationSchemesCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.schemeKeys = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteConcepts(this.schemeKeys));
    }
  }
  
  static class DeleteAssociationsCommand extends JAXRCommand {
    Collection schemeKeys;
    
    public DeleteAssociationsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl, Collection param1Collection) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
      this.schemeKeys = param1Collection;
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.deleteAssociations(this.schemeKeys));
    }
  }
  
  static class GetRegistryObjectsCommand extends JAXRCommand {
    public GetRegistryObjectsCommand(RegistryServiceImpl param1RegistryServiceImpl, BulkResponseImpl param1BulkResponseImpl) throws JAXRException {
      super(param1RegistryServiceImpl, param1BulkResponseImpl);
    }
    
    void execute() throws JAXRException {
      this.response.updateResponse(this.mapper.getRegistryObjects());
    }
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\JAXRCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */