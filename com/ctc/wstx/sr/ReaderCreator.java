package com.ctc.wstx.sr;

import com.ctc.wstx.dtd.DTDId;
import com.ctc.wstx.dtd.DTDSubset;
import com.ctc.wstx.util.SymbolTable;

public interface ReaderCreator {
  DTDSubset findCachedDTD(DTDId paramDTDId);
  
  void updateSymbolTable(SymbolTable paramSymbolTable);
  
  void addCachedDTD(DTDId paramDTDId, DTDSubset paramDTDSubset);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\sr\ReaderCreator.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */