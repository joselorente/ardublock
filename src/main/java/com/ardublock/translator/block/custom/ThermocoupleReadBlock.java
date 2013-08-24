package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ThermocoupleReadBlock extends TranslatorBlock
{
	public ThermocoupleReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupThermocoupleEnvironment(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}
		
		TranslatorBlock t1 = getRequiredTranslatorBlockAtSocket(0);
		String b1 = t1.toCode();

		//Switch was not used for compatibility with java 1.6
		if (b1.equals("Centigrade")) {
			setupThermocoupleCentigrades(translator);
			ret += "__ardublockThermocoupleReadCelcius() ";
			
		} else if (b1.equals("Fahrenheit")) {
			setupThermocoupleFahrenheit(translator);
			ret += "__ardublockThermocoupleReadFahrenheit() ";

        } else {
        	setupThermocoupleCJC(translator);
        	ret += "__ardublockThermocoupleReadCJC() ";

		}
		return codePrefix + ret + codeSuffix;
	}
	

	//private static final String THERMOCOUPLE_DEFINITION = "#if ARDUINO < 100\nint SCK = 13;\nint MISO = 12;\n#endif\nint CS1 = 11;\nMAX31855 temperature(SCK, CS1, MISO);\n";
	//private static final String THERMOCOUPLE_DEFINITION = "";
	private static final String THERMOCOUPLE_DEFINITION_CENTIGRADES = "double __ardublockThermocoupleReadCelcius()\n{\nMAX31855 temperature(9, 7, 8);\ndouble tempTC;\ntempTC = temperature.readCelsius();\nreturn tempTC;\n}\n\nbool __ardublockThermocoupleError()\n{\nMAX31855 temperature(9, 7, 8);\nbool ThermocoupleError = false;\nif (temperature.readFaultCode()>0)\n{\nThermocoupleError = true;\n}\nreturn ThermocoupleError;\n}\n";
	private static final String THERMOCOUPLE_DEFINITION_FAHRENHEIT = "double __ardublockThermocoupleReadFahrenheit()\n{\nMAX31855 temperature(9, 7, 8);\ndouble tempTC;\ntempTC = temperature.readFahrenheit();\nreturn tempTC;\n}\n\nbool __ardublockThermocoupleError()\n{\nMAX31855 temperature(9, 7, 8);\nbool ThermocoupleError = false;\nif (temperature.readFaultCode()>0)\n{\nThermocoupleError = true;\n}\nreturn ThermocoupleError;\n}\n";
	private static final String THERMOCOUPLE_DEFINITION_CJC = "double __ardublockThermocoupleReadCJC()\n{\nMAX31855 temperature(9, 7, 8);\ndouble tempTC;\ntempTC = temperature.readFahrenheit();\nreturn tempTC;\n}\n\nbool __ardublockThermocoupleError()\n{\nMAX31855 temperature(9, 7, 8);\nbool ThermocoupleError = false;\nif (temperature.readFaultCode()>0)\n{\nThermocoupleError = true;\n}\nreturn ThermocoupleError;\n}\n";

	
	public static void setupThermocoupleEnvironment(Translator t)
	{
		t.addHeaderFile("MAX31855.h");
	}
	public static void setupThermocoupleCentigrades(Translator t1)
	{
		t1.addDefinitionCommand(THERMOCOUPLE_DEFINITION_CENTIGRADES );
	}
	public static void setupThermocoupleFahrenheit(Translator t1)
	{
		t1.addDefinitionCommand(THERMOCOUPLE_DEFINITION_FAHRENHEIT );
	}
	public static void setupThermocoupleCJC(Translator t1)
	{
		t1.addDefinitionCommand(THERMOCOUPLE_DEFINITION_CJC );
	}
}
