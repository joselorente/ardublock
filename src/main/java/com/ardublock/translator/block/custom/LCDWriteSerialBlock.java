package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCDWriteSerialBlock extends TranslatorBlock
{
	public LCDWriteSerialBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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

		setupLCDWrite(translator);
		setupLCDWriteSerial(translator);
		
		String tempo1 = "";
		String tempo2 = "";
		TranslatorBlock tbadd = getRequiredTranslatorBlockAtSocket(1);		
		tempo1 = tempo1 + tbadd.toCode();
		TranslatorBlock tbadd1 = getRequiredTranslatorBlockAtSocket(2);		
		tempo2 = tempo2 + tbadd1.toCode();		
		translator.addSetupCommand("lcd.begin("+tempo1+","+tempo2+");\n");
		translator.addSetupCommand("Serial.begin(9600);\n");		
		
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

	public static void setupLCDWrite(Translator t)
	{
		t.addHeaderFile("Wire.h");
		t.addHeaderFile("LiquidCrystal.h");
	}
	public static void setupLCDWriteSerial(Translator t1)
	{
		t1.addHeaderFile("LiquidCrystal lcd(12, 11, 5, 4, 3, 2);");
		//t1.addSetupCommand("lcd.init();\nlcd.createChar(0, car1);\nlcd.createChar(1, car2);\nlcd.createChar(2, car3);\nlcd.createChar(3, car4);\nlcd.createChar(4, car5);\n");
	}
}
