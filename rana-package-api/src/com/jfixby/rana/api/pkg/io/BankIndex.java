
package com.jfixby.rana.api.pkg.io;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;

import com.jfixby.scarabei.api.io.GZipInputStream;
import com.jfixby.scarabei.api.io.GZipOutputStream;
import com.jfixby.scarabei.api.io.IO;
import com.jfixby.scarabei.api.io.InputStream;
import com.jfixby.scarabei.api.io.OutputStream;
import com.jfixby.scarabei.api.java.ByteArray;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.json.JsonString;
import com.jfixby.scarabei.api.util.JUtils;

public class BankIndex implements Serializable {

	private static final long serialVersionUID = -1093587103868366257L;
	public static final String FILE_NAME = "index.r3-bank";
	public static final String FILE_NAME_DEBUG = "index.r3-bank.json";

	public final LinkedHashMap<String, PackageDescriptor> descriptors = new LinkedHashMap<String, PackageDescriptor>();

	public void add (final String name, final PackageDescriptor descriptor) {
		this.descriptors.put(name, descriptor);
	}

	public static void serializeGZIPJava (final BankIndex index, final OutputStream os) throws IOException {
		final GZipOutputStream gzip = IO.newGZipStream(os);
		gzip.open();
		IO.serialize(index, gzip);
		gzip.close();
	}

	public static void serializeJson (final BankIndex index, final OutputStream os) throws IOException {
		final JsonString data = Json.serializeToString(index);
		final byte[] bytes = data.toString().getBytes();
		os.write(bytes);
	}

	public static BankIndex deSerializeJson (final InputStream is) throws IOException {
		final ByteArray data = is.readAll();
		final String raw_json_string = JUtils.newString(data);
		final JsonString string = Json.newJsonString(raw_json_string);
		return Json.deserializeFromString(BankIndex.class, string);
	}

	public static BankIndex deSerializeGZIPJava (final InputStream is) throws IOException {
		final GZipInputStream gzip = IO.newGZipStream(is);
		gzip.open();
		final BankIndex index = IO.deserialize(BankIndex.class, gzip);
		gzip.close();
		return index;
	}

}
