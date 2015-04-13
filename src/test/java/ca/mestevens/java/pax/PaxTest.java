package ca.mestevens.java.pax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(groups = "automated")
public class PaxTest {
	
	private Pax pax;
	private final String jsonFile = Thread.currentThread().getContextClassLoader().getResource("testJson.json").getPath().toString();
	private final String objcRequestListenerPath = Thread.currentThread().getContextClassLoader().getResource("objc/TSTHttpRequestListener.h").getPath().toString();
	private final String objcRequestProtocolPath = Thread.currentThread().getContextClassLoader().getResource("objc/TSTHttpRequestProtocol.h").getPath().toString();
	private final String objcResponseProtocolPath = Thread.currentThread().getContextClassLoader().getResource("objc/TSTHttpResponseProtocol.h").getPath().toString();
	private final String objcServiceProtocolPath = Thread.currentThread().getContextClassLoader().getResource("objc/TSTHttpServiceProtocol.h").getPath().toString();
	private final String javaRequestListenerPath = Thread.currentThread().getContextClassLoader().getResource("java/HttpRequestListener.java").getPath().toString();
	private final String javaRequestProtocolPath = Thread.currentThread().getContextClassLoader().getResource("java/HttpRequest.java").getPath().toString();
	private final String javaResponseProtocolPath = Thread.currentThread().getContextClassLoader().getResource("java/HttpResponse.java").getPath().toString();
	private final String javaServiceProtocolPath = Thread.currentThread().getContextClassLoader().getResource("java/HttpService.java").getPath().toString();

	@BeforeMethod
	public void setUp() throws IOException {
		pax = new Pax(jsonFile);
	}
	
	@AfterMethod
	public void tearDown() {
		pax = null;
	}
	
	@Test
	public void testGenerateApis() throws IOException {
		pax.generateApi();
		//Check Objc files
		File objcRequestListenerFile = new File("target/test/TSTHttpRequestListener.h");
		assertTrue(objcRequestListenerFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(objcRequestListenerFile.getAbsolutePath())), Files.readAllBytes(Paths.get(objcRequestListenerPath)));
		File objcRequestProtocolFile = new File("target/test/TSTHttpRequestProtocol.h");
		assertTrue(objcRequestProtocolFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(objcRequestProtocolFile.getAbsolutePath())), Files.readAllBytes(Paths.get(objcRequestProtocolPath)));
		File objcResponseProtocolFile = new File("target/test/TSTHttpResponseProtocol.h");
		assertTrue(objcResponseProtocolFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(objcResponseProtocolFile.getAbsolutePath())), Files.readAllBytes(Paths.get(objcResponseProtocolPath)));
		File objcServiceProtocolFile = new File("target/test/TSTHttpServiceProtocol.h");
		assertTrue(objcServiceProtocolFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(objcServiceProtocolFile.getAbsolutePath())), Files.readAllBytes(Paths.get(objcServiceProtocolPath)));
		//Check Java files
		File javaRequestListenerFile = new File("target/src/main/java/com/test/http/models/HttpRequestListener.java");
		assertTrue(javaRequestListenerFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(javaRequestListenerFile.getAbsolutePath())), Files.readAllBytes(Paths.get(javaRequestListenerPath)));
		File javaRequestProtocolFile = new File("target/src/main/java/com/test/http/models/HttpRequest.java");
		assertTrue(javaRequestProtocolFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(javaRequestProtocolFile.getAbsolutePath())), Files.readAllBytes(Paths.get(javaRequestProtocolPath)));
		File javaResponseProtocolFile = new File("target/src/main/java/com/test/http/models/HttpResponse.java");
		assertTrue(javaResponseProtocolFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(javaResponseProtocolFile.getAbsolutePath())), Files.readAllBytes(Paths.get(javaResponseProtocolPath)));
		File javaServiceProtocolFile = new File("target/src/main/java/com/test/http/services/HttpService.java");
		assertTrue(javaServiceProtocolFile.exists());
		assertEquals(Files.readAllBytes(Paths.get(javaServiceProtocolFile.getAbsolutePath())), Files.readAllBytes(Paths.get(javaServiceProtocolPath)));
	}
	
}
