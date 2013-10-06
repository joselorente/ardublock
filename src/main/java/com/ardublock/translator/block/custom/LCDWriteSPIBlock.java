package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCDWriteSPIBlock extends TranslatorBlock
{
	public LCDWriteSPIBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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

		LCDWriteSerialBlock.setupLCDWrite(translator);
		//setupLCDWriteSerial(translator);
		
		String tempo2 = "";
		String tempo3 = "";

		TranslatorBlock tbadd1 = getRequiredTranslatorBlockAtSocket(1);		
		tempo2 = tempo2 + tbadd1.toCode();
		TranslatorBlock tbadd2 = getRequiredTranslatorBlockAtSocket(2);		
		tempo3 = tempo3 + tbadd2.toCode();
		translator.addDefinitionCommand("LiquidCrystal lcd(3, 2, 4);\n");		
		translator.addSetupCommand("lcd.begin("+tempo2+","+tempo3+");\n");
		
		ret +="lcd.setCursor( ";
		TranslatorBlock tb = getRequiredTranslatorBlockAtSocket(3);
		ret = ret + tb.toCode();
		ret = ret + ", ";
		tb = getRequiredTranslatorBlockAtSocket(4);
		ret = ret + tb.toCode();
		ret = ret + " );\n";
		tb = this.getRequiredTranslatorBlockAtSocket(5, "lcd.print( ", " );\n");
		ret = ret + tb.toCode();
		return codePrefix + ret + codeSuffix;
	}

}
