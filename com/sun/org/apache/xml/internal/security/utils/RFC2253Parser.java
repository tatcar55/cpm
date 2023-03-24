package com.sun.org.apache.xml.internal.security.utils;

import java.io.IOException;
import java.io.StringReader;

public class RFC2253Parser {
  static boolean _TOXML = true;
  
  static int counter = 0;
  
  public static String rfc2253toXMLdsig(String paramString) {
    _TOXML = true;
    String str = normalize(paramString);
    return rfctoXML(str);
  }
  
  public static String xmldsigtoRFC2253(String paramString) {
    _TOXML = false;
    String str = normalize(paramString);
    return xmltoRFC(str);
  }
  
  public static String normalize(String paramString) {
    if (paramString == null || paramString.equals(""))
      return ""; 
    try {
      String str = semicolonToComma(paramString);
      StringBuffer stringBuffer = new StringBuffer();
      int i = 0;
      int j = 0;
      int k;
      int m;
      for (m = 0; (k = str.indexOf(",", m)) >= 0; m = k + 1) {
        j += countQuotes(str, m, k);
        if (k > 0 && str.charAt(k - 1) != '\\' && j % 2 != 1) {
          stringBuffer.append(parseRDN(str.substring(i, k).trim()) + ",");
          i = k + 1;
          j = 0;
        } 
      } 
      stringBuffer.append(parseRDN(trim(str.substring(i))));
      return stringBuffer.toString();
    } catch (IOException iOException) {
      return paramString;
    } 
  }
  
  static String parseRDN(String paramString) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    int k;
    int m;
    for (m = 0; (k = paramString.indexOf("+", m)) >= 0; m = k + 1) {
      j += countQuotes(paramString, m, k);
      if (k > 0 && paramString.charAt(k - 1) != '\\' && j % 2 != 1) {
        stringBuffer.append(parseATAV(trim(paramString.substring(i, k))) + "+");
        i = k + 1;
        j = 0;
      } 
    } 
    stringBuffer.append(parseATAV(trim(paramString.substring(i))));
    return stringBuffer.toString();
  }
  
  static String parseATAV(String paramString) throws IOException {
    int i = paramString.indexOf("=");
    if (i == -1 || (i > 0 && paramString.charAt(i - 1) == '\\'))
      return paramString; 
    String str1 = normalizeAT(paramString.substring(0, i));
    String str2 = null;
    if (str1.charAt(0) >= '0' && str1.charAt(0) <= '9') {
      str2 = paramString.substring(i + 1);
    } else {
      str2 = normalizeV(paramString.substring(i + 1));
    } 
    return str1 + "=" + str2;
  }
  
  static String normalizeAT(String paramString) {
    String str = paramString.toUpperCase().trim();
    if (str.startsWith("OID"))
      str = str.substring(3); 
    return str;
  }
  
  static String normalizeV(String paramString) throws IOException {
    String str = trim(paramString);
    if (str.startsWith("\"")) {
      StringBuffer stringBuffer = new StringBuffer();
      StringReader stringReader = new StringReader(str.substring(1, str.length() - 1));
      int i = 0;
      while ((i = stringReader.read()) > -1) {
        char c = (char)i;
        if (c == ',' || c == '=' || c == '+' || c == '<' || c == '>' || c == '#' || c == ';')
          stringBuffer.append('\\'); 
        stringBuffer.append(c);
      } 
      str = trim(stringBuffer.toString());
    } 
    if (_TOXML == true) {
      if (str.startsWith("#"))
        str = '\\' + str; 
    } else if (str.startsWith("\\#")) {
      str = str.substring(1);
    } 
    return str;
  }
  
  static String rfctoXML(String paramString) {
    try {
      String str = changeLess32toXML(paramString);
      return changeWStoXML(str);
    } catch (Exception exception) {
      return paramString;
    } 
  }
  
  static String xmltoRFC(String paramString) {
    try {
      String str = changeLess32toRFC(paramString);
      return changeWStoRFC(str);
    } catch (Exception exception) {
      return paramString;
    } 
  }
  
  static String changeLess32toRFC(String paramString) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    StringReader stringReader = new StringReader(paramString);
    int i = 0;
    while ((i = stringReader.read()) > -1) {
      char c = (char)i;
      if (c == '\\') {
        stringBuffer.append(c);
        char c1 = (char)stringReader.read();
        char c2 = (char)stringReader.read();
        if (((c1 >= '0' && c1 <= '9') || (c1 >= 'A' && c1 <= 'F') || (c1 >= 'a' && c1 <= 'f')) && ((c2 >= '0' && c2 <= '9') || (c2 >= 'A' && c2 <= 'F') || (c2 >= 'a' && c2 <= 'f'))) {
          char c3 = (char)Byte.parseByte("" + c1 + c2, 16);
          stringBuffer.append(c3);
          continue;
        } 
        stringBuffer.append(c1);
        stringBuffer.append(c2);
        continue;
      } 
      stringBuffer.append(c);
    } 
    return stringBuffer.toString();
  }
  
  static String changeLess32toXML(String paramString) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    StringReader stringReader = new StringReader(paramString);
    int i = 0;
    while ((i = stringReader.read()) > -1) {
      if (i < 32) {
        stringBuffer.append('\\');
        stringBuffer.append(Integer.toHexString(i));
        continue;
      } 
      stringBuffer.append((char)i);
    } 
    return stringBuffer.toString();
  }
  
  static String changeWStoXML(String paramString) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    StringReader stringReader = new StringReader(paramString);
    int i = 0;
    while ((i = stringReader.read()) > -1) {
      char c = (char)i;
      if (c == '\\') {
        char c1 = (char)stringReader.read();
        if (c1 == ' ') {
          stringBuffer.append('\\');
          String str = "20";
          stringBuffer.append(str);
          continue;
        } 
        stringBuffer.append('\\');
        stringBuffer.append(c1);
        continue;
      } 
      stringBuffer.append(c);
    } 
    return stringBuffer.toString();
  }
  
  static String changeWStoRFC(String paramString) {
    StringBuffer stringBuffer = new StringBuffer();
    int i = 0;
    int j;
    int k;
    for (k = 0; (j = paramString.indexOf("\\20", k)) >= 0; k = j + 3) {
      stringBuffer.append(trim(paramString.substring(i, j)) + "\\ ");
      i = j + 3;
    } 
    stringBuffer.append(paramString.substring(i));
    return stringBuffer.toString();
  }
  
  static String semicolonToComma(String paramString) {
    return removeWSandReplace(paramString, ";", ",");
  }
  
  static String removeWhiteSpace(String paramString1, String paramString2) {
    return removeWSandReplace(paramString1, paramString2, paramString2);
  }
  
  static String removeWSandReplace(String paramString1, String paramString2, String paramString3) {
    StringBuffer stringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    int k;
    int m;
    for (m = 0; (k = paramString1.indexOf(paramString2, m)) >= 0; m = k + 1) {
      j += countQuotes(paramString1, m, k);
      if (k > 0 && paramString1.charAt(k - 1) != '\\' && j % 2 != 1) {
        stringBuffer.append(trim(paramString1.substring(i, k)) + paramString3);
        i = k + 1;
        j = 0;
      } 
    } 
    stringBuffer.append(trim(paramString1.substring(i)));
    return stringBuffer.toString();
  }
  
  private static int countQuotes(String paramString, int paramInt1, int paramInt2) {
    byte b = 0;
    for (int i = paramInt1; i < paramInt2; i++) {
      if (paramString.charAt(i) == '"')
        b++; 
    } 
    return b;
  }
  
  static String trim(String paramString) {
    String str = paramString.trim();
    int i = paramString.indexOf(str) + str.length();
    if (paramString.length() > i && str.endsWith("\\") && !str.endsWith("\\\\") && paramString.charAt(i) == ' ')
      str = str + " "; 
    return str;
  }
  
  public static void main(String[] paramArrayOfString) throws Exception {
    testToXML("CN=\"Steve, Kille\",  O=Isode Limited, C=GB");
    testToXML("CN=Steve Kille    ,   O=Isode Limited,C=GB");
    testToXML("\\ OU=Sales+CN=J. Smith,O=Widget Inc.,C=US\\ \\ ");
    testToXML("CN=L. Eagle,O=Sue\\, Grabbit and Runn,C=GB");
    testToXML("CN=Before\\0DAfter,O=Test,C=GB");
    testToXML("CN=\"L. Eagle,O=Sue, = + < > # ;Grabbit and Runn\",C=GB");
    testToXML("1.3.6.1.4.1.1466.0=#04024869,O=Test,C=GB");
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append('L');
    stringBuffer.append('u');
    stringBuffer.append('쒍');
    stringBuffer.append('i');
    stringBuffer.append('쒇');
    String str = "SN=" + stringBuffer.toString();
    testToXML(str);
    testToRFC("CN=\"Steve, Kille\",  O=Isode Limited, C=GB");
    testToRFC("CN=Steve Kille    ,   O=Isode Limited,C=GB");
    testToRFC("\\20OU=Sales+CN=J. Smith,O=Widget Inc.,C=US\\20\\20 ");
    testToRFC("CN=L. Eagle,O=Sue\\, Grabbit and Runn,C=GB");
    testToRFC("CN=Before\\12After,O=Test,C=GB");
    testToRFC("CN=\"L. Eagle,O=Sue, = + < > # ;Grabbit and Runn\",C=GB");
    testToRFC("1.3.6.1.4.1.1466.0=\\#04024869,O=Test,C=GB");
    stringBuffer = new StringBuffer();
    stringBuffer.append('L');
    stringBuffer.append('u');
    stringBuffer.append('쒍');
    stringBuffer.append('i');
    stringBuffer.append('쒇');
    str = "SN=" + stringBuffer.toString();
    testToRFC(str);
  }
  
  static void testToXML(String paramString) {
    System.out.println("start " + counter++ + ": " + paramString);
    System.out.println("         " + rfc2253toXMLdsig(paramString));
    System.out.println("");
  }
  
  static void testToRFC(String paramString) {
    System.out.println("start " + counter++ + ": " + paramString);
    System.out.println("         " + xmldsigtoRFC2253(paramString));
    System.out.println("");
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\securit\\utils\RFC2253Parser.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */