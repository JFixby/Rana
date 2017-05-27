
package com.jfixby.red.engine.core.resources;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;

public class PackerSpecs {

	private File PackageFolder;
	final Set<File> PackedFiles = Collections.newSet();
	final Set<ID> PackedAssets = Collections.newSet();
	final Set<ID> RequiredAssets = Collections.newSet();
	private String rootFileName;
	String PackageFormat;
	String Version;

	public File getPackageFolder () {
		return this.PackageFolder;
	}

	public Set<File> getPackedFiles () {
		return this.PackedFiles;
	}

	public Set<ID> getPackedAssets () {
		return this.PackedAssets;
	}

	public Set<ID> getRequiredAssets () {
		return this.RequiredAssets;
	}

	public String getRootFileName () {
		return this.rootFileName;
	}

	public void setPackageFolder (final File package_folder) {
		this.PackageFolder = package_folder;
	}

	public void addPackedFiles (final Collection<File> files) {
		this.PackedFiles.addAll(files);
	}

	public void addPackedFile (final File file) {
		this.PackedFiles.add(file);
	}

	public void setRootFileName (final String rootFileName) {
		this.rootFileName = rootFileName;
	}

	public void setPackedAssets (final Collection<ID> packed) {
		this.PackedAssets.addAll(packed);
	}

	public void setRequiredAssets (final Collection<ID> required) {
		this.RequiredAssets.addAll(required);
	}

	public String getPackageFormat () {
		return this.PackageFormat;
	}

	public void setPackageFormat (final String packageFormat) {
		this.PackageFormat = packageFormat;
	}

	public String getVersion () {
		return this.Version;
	}

	public void setVersion (final String version) {
		this.Version = version;
	}

}
