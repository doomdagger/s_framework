package com.hg.ecommerce.test;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hg.ecommerce.websocket.support.SocketMessage;

public class MapKeyMainTest {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException{
		
//		AreaContainer container = new AreaContainer();
//		
//		container.setName("嘿嘿");
//		
//		
//		SysSetting setting = new SysSetting();
//		
//		setting.setCreateperson(1231);
//		setting.setEdittime(new Date());
//		setting.setPropId(213111);
//		
//		Area area = new Area();
//		
//		area.setAreaName("浪费大");
//		area.setAreaRemoteId(231);
//		area.setId(UUIDGenerator.randomUUID());
//		area.setLongitude(231.2);
//		area.setRadius(200);
//		
//		
//		container.setArea(area);
//		container.setSetting(setting);
		
		StringWriter writer = new StringWriter();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(writer, new String("hello"));
		
		SocketMessage message = new SocketMessage();
		
		message.setEventName("say");
		message.setPayload(writer.toString());
		
		writer = new StringWriter();
		
		mapper.writeValue(writer, message); // where 'dst' can be File, OutputStream or Writer
		
		System.out.println(writer.toString());
		
		SocketMessage newMessage = mapper.readValue(writer.toString(), SocketMessage.class);
		
		System.out.println(newMessage.getEventName()+"\n"+newMessage.getPayload());
		
	}
}
