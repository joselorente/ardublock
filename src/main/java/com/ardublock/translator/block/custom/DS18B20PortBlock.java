package com.ardublock.translator.block.custom;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class DS18B20PortBlock extends TranslatorBlock
{

	public DS18B20PortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		
	}

	public String toCode() throws SocketNullException
	{
		return codePrefix + label + codeSuffix;
	}
}