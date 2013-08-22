package com.ardublock.translator.block;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialPrintlnBlock extends TranslatorBlock
{
	public SerialPrintlnBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addSetupCommand("Serial.begin(9600);");
		TranslatorBlock t1 = getRequiredTranslatorBlockAtSocket(1);
		String b1 = t1.toCode();
		
		String text = "";
		
		if (b1.equals("true")) {

			text = "Serial.println( ";
			
        } else {

			text = "Serial.print( ";

		}			
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0, text , " );\n");
		
		String ret = translatorBlock.toCode();

		if (b1.equals("true")) {

			ret = ret + "Serial.println(\"\");\n";				
        } else {

    		ret = ret + "Serial.print(\"\");\n";

		}	
		
		return ret;
	}
}
