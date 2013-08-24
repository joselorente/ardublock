package com.ardublock.translator.block.custom;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCDBacklightBlock extends TranslatorBlock
{
	public LCDBacklightBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
		
		//TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		//String bol = translatorBlock.toCode();
		TranslatorBlock t5 = getRequiredTranslatorBlockAtSocket(1);
		String bol1 = t5.toString();
		
		if (bol1.startsWith("com.ardublock.translator.block.DigitalHigh"))
		{
			String ret = "lcd.backlight();\n";
			return ret;
		}
		else
		{
			String ret = "lcd.noBacklight();\n";
			return ret;
		}
	}
}
