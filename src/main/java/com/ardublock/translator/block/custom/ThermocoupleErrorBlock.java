package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
//import com.ardublock.translator.block.WireReadBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ThermocoupleErrorBlock extends TranslatorBlock
{
	public ThermocoupleErrorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		ThermocoupleReadBlock.setupThermocoupleEnvironment(translator);
		//WireReadBlock.setupWireEnvironment(translator);
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}

			ret += "__ardublockThermocoupleError() ";

		return codePrefix + ret + codeSuffix;
	}
	

	//private static final String THERMOCOUPLE_ERROR_DEFINITION = "#if ARDUINO < 100\nint SCK = 13;\nint MISO = 12;\n#endif\nint CS1 = 11;\nMAX31855 temperature(SCK, CS1, MISO);\n";
	//private static final String THERMOCOUPLE_ERROR_DEFINITION = "bool __ardublockThermocoupleError()\n{\nbool ThermocoupleError = false;\nif (temperature.readFaultCode()>0)\n{\nThermocoupleError = true;\n}\nreturn ThermocoupleError;\n}\n\n";

	/*public static void setupThermocoupleError(Translator t)
	{
		//t.addHeaderFile("MAX31855.h");
		t.addDefinitionCommand(THERMOCOUPLE_ERROR_DEFINITION);
	}*/
	
}
