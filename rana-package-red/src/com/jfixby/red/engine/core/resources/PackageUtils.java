
package com.jfixby.red.engine.core.resources;

import java.io.IOException;

import com.jfixby.rana.api.pkg.fs.PackageDescriptor;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.sys.Sys;

public class PackageUtils {
	public static void pack (final PackerSpecs specs) throws IOException {

		final File package_folder = specs.getPackageFolder();
		final File package_content_folder = package_folder.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		package_content_folder.makeFolder();
		package_content_folder.clearFolder();

		final String package_format = specs.getPackageFormat();
		final String version = specs.getVersion();
		final Collection<ID> provisions = specs.getPackedAssets();
		final Collection<ID> dependencies = specs.getRequiredAssets();
		final String root_file_name = specs.getRootFileName();
		producePackageDescriptor(package_folder, package_format, version, provisions, dependencies, root_file_name);

		final Collection<File> files_list = specs.getPackedFiles();
		package_content_folder.getFileSystem().copyFilesTo(files_list, package_content_folder);

	}

	static public void producePackageDescriptor (final File output_folder, final String format, final String version,
		final Collection<ID> provisions, final Collection<ID> dependencies, final String root_file_name) throws IOException {

		final PackageDescriptor descriptor = new PackageDescriptor();
		descriptor.format = format;
		descriptor.timestamp = "" + Sys.SystemTime().currentTimeMillis();
		descriptor.version = version;
		for (final ID d : provisions) {
			descriptor.packed_assets.add(d.toString());
		}
		for (final ID d : dependencies) {
			descriptor.package_dependencies.add(d.toString());
		}

		descriptor.root_file_name = root_file_name;
		final File output_file = output_folder.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
		output_file.writeData(descriptor);

		final File debug_output_file = output_folder.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME + ".json");
		debug_output_file.writeString(Json.serializeToString(descriptor).toString());

		final PackageDescriptor check = output_file.readData(PackageDescriptor.class);
		L.d("packing", Json.serializeToString(descriptor));
		L.d("cheking", Json.serializeToString(check));
		L.d("packed to", output_folder);
		Debug.checkTrue("IO fine", check.equals(descriptor));

	}
}
