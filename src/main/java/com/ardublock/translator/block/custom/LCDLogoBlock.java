package com.ardublock.translator.block.custom;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCDLogoBlock extends TranslatorBlock
{
	public LCDLogoBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

	String tempo1 = "";
	TranslatorBlock tbadd = getRequiredTranslatorBlockAtSocket(0);		
	tempo1 = tempo1 + tbadd.toCode();
	translator.addDefinitionCommand("LiquidCrystal_I2C lcd("+tempo1+",16,2);\n");	

	setupLcdGreatbatch(translator);

	String ret = "";
	
	ret += "lcd.home();\nlcd.write(0);\nlcd.write(1);\nlcd.write(2);\n";
	ret = ret + "lcd.setCursor(0, 1);\nlcd.write(3);\nlcd.write(4);\nlcd.write(3);\n";
	return ret;
	}

	public static void setupLcdGreatbatch(Translator t)
	{
		t.addHeaderFile("Wire.h");
		t.addHeaderFile("LiquidCrystal_I2C.h");
		t.addDefinitionCommand("uint8_t car1[8] = {0x00,0x00,0x00,0x00,0x0E,0x11,0x11,0x0E};\nuint8_t car2[8] = {0x00,0x0E,0x11,0x11,0x0E,0x00,0x00,0x00};\nuint8_t car3[8] = {0x00,0x00,0x00,0x00,0x0E,0x1F,0x1F,0x0E};\nuint8_t car4[8] = {0x0E,0x11,0x11,0x0E,0x00,0x00,0x00,0x00};\nuint8_t car5[8] = {0x00,0x00,0x00,0x0E,0x11,0x11,0x0E,0x00};\n");
		t.addSetupCommand("lcd.init();\nlcd.createChar(0, car1);\nlcd.createChar(1, car2);\nlcd.createChar(2, car3);\nlcd.createChar(3, car4);\nlcd.createChar(4, car5);\n");

	}

}