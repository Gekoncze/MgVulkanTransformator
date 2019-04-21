package cz.mg.vulkantransformator.fixes;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;


public class SkipConditionalEntitiesFix {
    public ChainList<Text> fix(ChainList<Text> lines){
        ChainList<Text> newLines = new ChainList<>();
        int ifCount = -1;
        for(Text line : lines){
            if(line.startsWith("#if")){
                ifCount++;
            } else if(line.startsWith("#endif")){
                ifCount--;
            } else if(ifCount <= 0) {
                newLines.addLast(line);
            }
        }
        return newLines;
    }
}
