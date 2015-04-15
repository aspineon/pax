package ca.mestevens.java.pax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import ca.mestevens.java.pax.models.PaxClass;
import ca.mestevens.java.pax.models.PaxProject;
import ca.mestevens.java.pax.models.PaxType;
import ca.mestevens.java.pax.models.PaxTypeModifier;
import ca.mestevens.java.pax.utils.PlatformTypeConverterUtil;

public class Pax {

	private PaxProject paxProject;
	
	public Pax(String apiFileLocation) throws IOException {
		byte[] encodedBytes = Files.readAllBytes(Paths.get(apiFileLocation));
		paxProject = new Gson().fromJson(new String(encodedBytes, Charset.defaultCharset()), PaxProject.class);
		getCustomClassNames();
	}
	
	private void getCustomClassNames() {
		List<PaxClass> classes = paxProject.getClasses();
		Map<String, PaxType> javaClassMap = new HashMap<String, PaxType>();
		Map<String, PaxType> objcClassMap = new HashMap<String, PaxType>();
		for(PaxClass paxClass : classes) {
			if (paxClass.getJavaMetadata() != null && paxClass.getJavaMetadata().getClassName() != null) {
				PaxType type = new PaxType();
				type.setNamespace(paxClass.getJavaMetadata().getNamespace());
				type.setClassName(paxClass.getJavaMetadata().getClassName());
				javaClassMap.put(paxClass.getClassName(), type);
			}
			if (paxClass.getObjcMetadata() != null && paxClass.getObjcMetadata().getClassName() != null) {
				PaxType type = new PaxType();
				type.setNamespace(paxClass.getObjcMetadata().getNamespace());
				type.setClassName(paxClass.getObjcMetadata().getClassName());
				type.setType(PaxTypeModifier.ID);
				objcClassMap.put(paxClass.getClassName(), type);
			}
		}
		PlatformTypeConverterUtil.javaClassMap.putAll(javaClassMap);
		PlatformTypeConverterUtil.objcClassMap.putAll(objcClassMap);
	}
	
	public void generateJavaApi() throws IOException {
		List<PaxClass> classes = paxProject.getClasses();
		for (PaxClass paxClass : classes) {
			File directory = new File(paxProject.getJavaOutputFolder() + "/" + paxClass.getJavaMetadata().getOutputFolder() + "/");
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + "/" + paxClass.getJavaMetadata().getClassName() + ".java");
			BufferedWriter output = null;
			try {
				file.createNewFile();
				output = new BufferedWriter(new FileWriter(file));
				output.write(paxClass.toJavaString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				output.close();
			}
		}
	}
	
	public void generateObjcApi() throws IOException {
		List<PaxClass> classes = paxProject.getClasses();
		for (PaxClass paxClass : classes) {
			File directory = new File(paxProject.getObjcOutputFolder() + "/" + paxClass.getObjcMetadata().getOutputFolder() + "/");
			directory.mkdirs();
			File file = new File(directory.getAbsolutePath() + "/" + paxClass.getObjcMetadata().getClassName() + ".h");
			BufferedWriter output = null;
			try {
				file.createNewFile();
				output = new BufferedWriter(new FileWriter(file));
				output.write(paxClass.toObjcString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				output.close();
			}
		}
	}
	
}