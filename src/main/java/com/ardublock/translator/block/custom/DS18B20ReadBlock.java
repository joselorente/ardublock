package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DS18B20ReadBlock extends TranslatorBlock
{
	public DS18B20ReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupDS18B20Environment(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}
		
		TranslatorBlock t1 = getRequiredTranslatorBlockAtSocket(0);
		String b1 = t1.toCode();
		//addSetupCommand("sensors.begin();\n");

		//TranslatorBlock t2 = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock t3 = this.getRequiredTranslatorBlockAtSocket(1);		

		if (t3.toCode().equals("D_2")) {
			setupDefinitionDS18B20_2(translator);
		} else if (t3.toCode().equals("D_3")) {
			setupDefinitionDS18B20_3(translator);
		} else if (t3.toCode().equals("D_4")) {
			setupDefinitionDS18B20_4(translator);
		} else if (t3.toCode().equals("D_5")) {
			setupDefinitionDS18B20_5(translator);
		} else if (t3.toCode().equals("D_6")) {
			setupDefinitionDS18B20_6(translator);
		} else if (t3.toCode().equals("D_7")) {
			setupDefinitionDS18B20_7(translator);
		} else if (t3.toCode().equals("D_8")) {
			setupDefinitionDS18B20_8(translator);
		} else if (t3.toCode().equals("D_9")) {
			setupDefinitionDS18B20_9(translator);
		} else if (t3.toCode().equals("D_10")) {
			setupDefinitionDS18B20_10(translator);
		} else if (t3.toCode().equals("D_11")) {
			setupDefinitionDS18B20_11(translator);
		}else if (t3.toCode().equals("D_12")) {
			setupDefinitionDS18B20_12(translator);
		}else {
			setupDefinitionDS18B20_13(translator);
		}
		
		
		//Switch was not used for compatibility with java 1.6
		if (b1.equals("RES_9")) {
			setupDS18B20Res9(translator);
			ret += "__ardublockDS18B20ReadData( ";
			//ret = ret + t2.toCode();
			ret = ret + " ) ";
		} else if (b1.equals("RES_10")) {
			setupDS18B20Res10(translator);
			ret += "__ardublockDS18B20ReadData( ";
			//ret = ret + t2.toCode();
			ret = ret + " ) ";		
        } else if (b1.equals("RES_11")) {
        	setupDS18B20Res11(translator);
			ret += "__ardublockDS18B20ReadData( ";
			//ret = ret + t2.toCode();
			ret = ret + " ) ";
        } else {
        	setupDS18B20Res12(translator);
			ret += "__ardublockDS18B20ReadData( ";
			//ret = ret + t2.toCode();
			ret = ret + " ) ";
		}
		return codePrefix + ret + codeSuffix;
	}
	
	private static final String DS18B20_RES_DEFINITION_09 = "sensors.begin();\nsensors.setResolution(9);\n";
	private static final String DS18B20_RES_DEFINITION_10 = "sensors.begin();\nsensors.setResolution(10);\n";
	private static final String DS18B20_RES_DEFINITION_11 = "sensors.begin();\nsensors.setResolution(11);\n";
	private static final String DS18B20_RES_DEFINITION_12 = "sensors.begin();\nsensors.setResolution(12);\n";

	private static final String DS18B20_FUNC_DEFINITION_00 = "float __ardublockDS18B20ReadData(void)\n{\nsensors.requestTemperatures();\nsensors.setWaitForConversion(false);\nsensors.requestTemperatures();\nsensors.setWaitForConversion(true);\nfloat tempo = sensors.getTempCByIndex(0);\nreturn tempo;\n}\n";
	//private static final String DS18B20_FUNC_DEFINITION_00 = "char __ardublockDS18B20ReadData(void)\n{\nsensors.requestTemperatures();\nsensors.setWaitForConversion(false);\nsensors.requestTemperatures();\nsensors.setWaitForConversion(true);\nfloat tempo = sensors.getTempCByIndex(0);\nchar data;\ndtostrf(tempo,2,2,&data);\nreturn data;\n}\n";

	
	private static final String DS18B20_PIN_DEFINITION_02 = "OneWire oneWire(2);\nDallasTemperature sensors(&oneWire);\n";	
	private static final String DS18B20_PIN_DEFINITION_03 = "OneWire oneWire(3);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_04 = "OneWire oneWire(4);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_05 = "OneWire oneWire(5);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_06 = "OneWire oneWire(6);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_07 = "OneWire oneWire(7);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_08 = "OneWire oneWire(8);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_09 = "OneWire oneWire(9);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_10 = "OneWire oneWire(10);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_11 = "OneWire oneWire(11);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_12 = "OneWire oneWire(12);\nDallasTemperature sensors(&oneWire);\n";
	private static final String DS18B20_PIN_DEFINITION_13 = "OneWire oneWire(13);\nDallasTemperature sensors(&oneWire);\n";
	
	public static void setupDS18B20Environment(Translator t)
	{
		t.addHeaderFile("OneWire.h");
		t.addHeaderFile("DallasTemperature.h");
	}

	private static void setupDefinitionDS18B20_2(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_02);
	}
	private static void setupDefinitionDS18B20_3(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_03);
	}
	private static void setupDefinitionDS18B20_4(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_04);
	}
	private static void setupDefinitionDS18B20_5(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_05);
	}
	private static void setupDefinitionDS18B20_6(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_06);
	}
	private static void setupDefinitionDS18B20_7(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_07);
	}
	private static void setupDefinitionDS18B20_8(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_08);
	}
	private static void setupDefinitionDS18B20_9(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_09);
	}
	private static void setupDefinitionDS18B20_10(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_10);
	}
	private static void setupDefinitionDS18B20_11(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_11);
	}
	private static void setupDefinitionDS18B20_12(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_12);
	}
	private static void setupDefinitionDS18B20_13(Translator tr2)
	{
		tr2.addDefinitionCommand(DS18B20_PIN_DEFINITION_13);
	}
	
	private static void setupDS18B20Res9(Translator t1)
	{
		t1.addDefinitionCommand(DS18B20_FUNC_DEFINITION_00);
		t1.addSetupCommand(DS18B20_RES_DEFINITION_09);
	}
	private static void setupDS18B20Res10(Translator t1)
	{
		t1.addDefinitionCommand(DS18B20_FUNC_DEFINITION_00);
		t1.addSetupCommand(DS18B20_RES_DEFINITION_10);
	}
	private static void setupDS18B20Res11(Translator t1)
	{
		t1.addDefinitionCommand(DS18B20_FUNC_DEFINITION_00);
		t1.addSetupCommand(DS18B20_RES_DEFINITION_11);
	}
	private static void setupDS18B20Res12(Translator t1)
	{
		t1.addDefinitionCommand(DS18B20_FUNC_DEFINITION_00);
		t1.addSetupCommand(DS18B20_RES_DEFINITION_12);
	}

}
