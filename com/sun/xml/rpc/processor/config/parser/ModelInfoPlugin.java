package com.sun.xml.rpc.processor.config.parser;

import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
import com.sun.xml.rpc.spi.tools.ModelInfo;
import java.net.URL;
import javax.xml.namespace.QName;

public interface ModelInfoPlugin {
  QName getModelInfoName();
  
  ModelInfoParser createModelInfoParser(ProcessorEnvironment paramProcessorEnvironment);
  
  ModelInfo createModelInfo();
  
  ModelInfo createModelInfo(URL paramURL) throws Exception;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\ModelInfoPlugin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */