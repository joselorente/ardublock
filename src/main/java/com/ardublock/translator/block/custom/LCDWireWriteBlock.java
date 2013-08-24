package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCDWireWriteBlock extends TranslatorBlock
{
	public LCDWireWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "";
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to dectect your Arduino version, using 1.0 in default\n";
		}

		LCDLogoBlock.setupLcdGreatbatch(translator);
		
		String tempo1 = "";
		TranslatorBlock tbadd = getRequiredTranslatorBlockAtSocket(0);		
		tempo1 = tempo1 + tbadd.toCode();
		translator.addDefinitionCommand("LiquidCrystal_I2C lcd("+tempo1+",16,2);\n");
		
		ret +="lcd.setCursor( ";
		TranslatorBlock tb = getRequiredTranslatorBlockAtSocket(2);
		ret = ret + tb.toCode();
		ret = ret + ", ";
		tb = getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " );\n";
		tb = this.getRequiredTranslatorBlockAtSocket(3, "lcd.print( ", " );\n");
		ret = ret + tb.toCode();
		return codePrefix + ret + codeSuffix;
	}

}
