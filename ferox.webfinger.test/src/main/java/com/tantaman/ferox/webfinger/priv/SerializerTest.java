package com.tantaman.ferox.webfinger.priv;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.tantaman.ferox.webfinger.Serializer;

public class SerializerTest {

	@Test
	public void testSerializeList() {
		Serializer s = new Serializer();
		
		List<String> strings = new LinkedList<>();
		
		for (int i = 0; i < 5; ++i) {
			strings.add("" + i);
		}
		
		StringBuilder b = new StringBuilder();
		s.serialize(strings, b);
		
		assertEquals("[\"0\",\"1\",\"2\",\"3\",\"4\"]", b.toString());
	}
	
	@Test
	public void testSerializeMap() {
		Serializer s = new Serializer();
		
		Map<String, String> obj = new LinkedHashMap<>();
		
		obj.put("color", "blue");
		obj.put("weight", "gold");
		obj.put("length", "10");
		
		StringBuilder b = new StringBuilder();
		s.serialize(obj, b);
		
		assertEquals("{\"color\":\"blue\",\"weight\":\"gold\",\"length\":\"10\"}", b.toString());
	}
	
	private static final String NESTED_OBJ_STRING = 
			"{\"name\":\"james\",\"son\":{\"name\":\"jayce\",\"color\":\"yellow\"},\"s\":{\"t\":{}}}";
	@Test
	public void testSerializeObject() {
		Serializer s = new Serializer();
		
		StringBuilder b = new StringBuilder();
		s.serializeObject(createNestedObject(), b);
		
		assertEquals(NESTED_OBJ_STRING, b.toString());
	}
	
	private Map<String, Object> createNestedObject() {
		Map<String, Object> obj = new LinkedHashMap<>();
		
		obj.put("name", "james");
		
		Map<String, String> subObj = new LinkedHashMap<>();
		
		subObj.put("name", "jayce");
		subObj.put("color", "yellow");
		
		obj.put("son", subObj);
		
		Map<String, Object> otherSub = new LinkedHashMap<>();
		
		otherSub.put("t", new LinkedHashMap<>());
		
		obj.put("s", otherSub);
		
		return obj;
	}
	
	@Test
	public void testSerializeObjects() {
		String expected = "[";
		List<Map<String, Object>> objects = new LinkedList<>();
		for (int i = 0; i < 5; ++i) {
			if (i != 0)
				expected += ",";
			expected += NESTED_OBJ_STRING;
			objects.add(createNestedObject());
		}
		expected += "]";
		
		Serializer s = new Serializer();
		StringBuilder b = new StringBuilder();
		s.serializeObjects(objects, b);
		
		assertEquals(expected, b.toString());
	}
}
