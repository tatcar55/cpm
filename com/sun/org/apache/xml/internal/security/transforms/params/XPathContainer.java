package com.sun.org.apache.xml.internal.security.transforms.params;

import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XPathContainer extends SignatureElementProxy implements TransformParam {
  public XPathContainer(Document paramDocument) {
    super(paramDocument);
  }
  
  public void setXPath(String paramString) {
    if (this._constructionElement.getChildNodes() != null) {
      NodeList nodeList = this._constructionElement.getChildNodes();
      for (byte b = 0; b < nodeList.getLength(); b++)
        this._constructionElement.removeChild(nodeList.item(b)); 
    } 
    Text text = this._doc.createTextNode(paramString);
    this._constructionElement.appendChild(text);
  }
  
  public String getXPath() {
    return getTextFromTextChild();
  }
  
  public String getBaseLocalName() {
    return "XPath";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\transforms\params\XPathContainer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */