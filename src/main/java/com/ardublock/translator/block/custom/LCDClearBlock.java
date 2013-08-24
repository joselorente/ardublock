package com.ardublock.translator.block.custom;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCDClearBlock extends TranslatorBlock
{
	public LCDClearBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		LCDLogoBlock.setupLcdGreatbatch(translator);
		
		String tempo1 = "";
		TranslatorBlock tbadd = getRequiredTranslatorBlockAtSocket(0);		
		tempo1 = tempo1 + tbadd.toCode();
		translator.addDefinitionCommand("LiquidCrystal_I2C lcd("+tempo1+",16,2);\n");
		
		String ret = "";
		
		ret += "lcd.clear();\n";
		return ret;
	}
}
