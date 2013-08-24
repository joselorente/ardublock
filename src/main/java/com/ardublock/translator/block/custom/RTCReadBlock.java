package com.ardublock.translator.block.custom;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RTCReadBlock extends TranslatorBlock
{
	public RTCReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupRTCEnvironment(translator);
		//setupRTCDefinition(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}
		
		TranslatorBlock t1 = getRequiredTranslatorBlockAtSocket(0);
		String b1 = t1.toCode();

		//Switch was not used for compatibility with java 1.6
		if (b1.equals("Year")) {

			ret += "__ardublockRTCYearData( )";
		} else if (b1.equals("Month")) {

			ret += "__ardublockRTCMonthData( )";
        } else if (b1.equals("Day")) {

			ret += "__ardublockRTCDayData( )";
		} else if (b1.equals("Hour")) {

			ret += "__ardublockRTCHourData( )";
        } else if (b1.equals("Minute")) {

			ret += "__ardublockRTCMinuteData( )";
		} else {

        	ret += "__ardublockRTCDOWData( )\n";
		}
		return codePrefix + ret + codeSuffix;
	}
	

	//private static final String RTC_DEFINITION_Year_00 = "uint16_t __ardublockRTCYearData(void)\n{\nDateTime now = RTC.now();\nuint16_t tempo=now.year();\nreturn tempo;\n}\n";
	//private static final String RTC_DEFINITION_Month_00 = "uint8_t __ardublockRTCMonthData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.month();\nreturn tempo;\n}\n";
	//private static final String RTC_DEFINITION_Day_00 = "uint8_t __ardublockRTCDayData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.day();\nreturn tempo;\n}\n";
	//private static final String RTC_DEFINITION_Hour_00 = "uint8_t __ardublockRTCHourData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.hour();\nreturn tempo;\n}\n";
	//private static final String RTC_DEFINITION_Minute_00 = "uint8_t __ardublockRTCMinuteData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.minute();\nreturn tempo;\n}\n";
	//private static final String RTC_DEFINITION_DOW_00 = "uint8_t __ardublockRTCDOWData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.dayOfWeek();\nreturn tempo;\n}\n";

	//private static final String DS18B20_FUNC_DEFINITION_00 = "char __ardublockDS18B20ReadData(void)\n{\nsensors.requestTemperatures();\nsensors.setWaitForConversion(false);\nsensors.requestTemperatures();\nsensors.setWaitForConversion(true);\nfloat tempo = sensors.getTempCByIndex(0);\nchar data;\ndtostrf(tempo,2,2,&data);\nreturn data;\n}\n";

	
	private static final String RTC_DEFINITION_00 = "RTC_DS1307 RTC;\nuint16_t __ardublockRTCYearData(void)\n{\nDateTime now = RTC.now();\nuint16_t tempo=now.year();\nreturn tempo;\n}\nuint8_t __ardublockRTCMonthData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.month();\nreturn tempo;\n}\nuint8_t __ardublockRTCDayData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.day();\nreturn tempo;\n}\nuint8_t __ardublockRTCHourData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.hour();\nreturn tempo;\n}\nuint8_t __ardublockRTCMinuteData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.minute();\nreturn tempo;\n}\nuint8_t __ardublockRTCDOWData(void)\n{\nDateTime now = RTC.now();\nuint8_t tempo=now.dayOfWeek();\nreturn tempo;\n}\n";	
	private static final String RTC_SETUP_00 = "Wire.begin();\nRTC.begin();\n";
	
	public static void setupRTCEnvironment(Translator t)
	{
		t.addDefinitionCommand(RTC_DEFINITION_00);
		t.addHeaderFile("Wire.h");
		t.addHeaderFile("RTClib.h");
		t.addSetupCommand(RTC_SETUP_00);
	}

}
