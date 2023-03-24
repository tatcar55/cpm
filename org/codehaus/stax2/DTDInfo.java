package org.codehaus.stax2;

import org.codehaus.stax2.validation.DTDValidationSchema;

public interface DTDInfo {
  Object getProcessedDTD();
  
  String getDTDRootName();
  
  String getDTDSystemId();
  
  String getDTDPublicId();
  
  String getDTDInternalSubset();
  
  DTDValidationSchema getProcessedDTDSchema();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\codehaus\stax2\DTDInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */