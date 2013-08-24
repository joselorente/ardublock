package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SevenSegmentWriteBlock extends TranslatorBlock
{
	public SevenSegmentWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
		SevenSegmentSetupBlock.setupSevenSegment(translator);
		translator.addDefinitionCommand("Adafruit_7segment matrix = Adafruit_7segment();");
		
		String tempo1 = "";
		TranslatorBlock tbadd = getRequiredTranslatorBlockAtSocket(0);		
		tempo1 = tempo1 + tbadd.toCode();
		translator.addSetupCommand("matrix.begin( "+tempo1+" );\n");
		
		ret = ret + "matrix.print( ";
		TranslatorBlock tb = getRequiredTranslatorBlockAtSocket(2);
		ret = ret + tb.toCode();
		ret = ret + " ,DEC );\n";
		ret = ret + "matrix.drawColon( ";
		tb = getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();		
		ret = ret + " );\nmatrix.writeDisplay();\n";
		
		return codePrefix + ret + codeSuffix;
	}
//	public static void setupSevenSegment(Translator t)
//	{
//		t.addHeaderFile("Wire.h");
//		t.addHeaderFile("Adafruit_LEDBackpack.h");
//		t.addHeaderFile("Adafruit_GFX.h");
//	}

}
