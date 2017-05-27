
package com.jfixby.rana.api.pkg.io;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class PackageDescriptor implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 940908719512054127L;

	public PackageDescriptor () {
		super();
	}

	public static final String PACKAGE_DESCRIPTOR_FILE_NAME = "package.descriptor";
	public static final String PACKAGE_CONTENT_FOLDER = "content";

	public String format;
	public String version;
	public String timestamp;
	public String root_file_name;
	public ArrayList<String> packed_assets = new ArrayList<String>();
	public ArrayList<String> package_dependencies = new ArrayList<String>();
	public LinkedHashMap<String, String> package_parameters = null;

	public long timestamp () {
		return Long.parseLong(this.timestamp);
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.format == null) ? 0 : this.format.hashCode());
		result = prime * result + ((this.package_dependencies == null) ? 0 : this.package_dependencies.hashCode());
		result = prime * result + ((this.packed_assets == null) ? 0 : this.packed_assets.hashCode());
		result = prime * result + ((this.root_file_name == null) ? 0 : this.root_file_name.hashCode());
		result = prime * result + ((this.timestamp == null) ? 0 : this.timestamp.hashCode());
		result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final PackageDescriptor other = (PackageDescriptor)obj;
		if (this.format == null) {
			if (other.format != null) {
				return false;
			}
		} else if (!this.format.equals(other.format)) {
			return false;
		}
		if (this.package_dependencies == null) {
			if (other.package_dependencies != null) {
				return false;
			}
		} else if (!this.package_dependencies.equals(other.package_dependencies)) {
			return false;
		}
		if (this.packed_assets == null) {
			if (other.packed_assets != null) {
				return false;
			}
		} else if (!this.packed_assets.equals(other.packed_assets)) {
			return false;
		}
		if (this.root_file_name == null) {
			if (other.root_file_name != null) {
				return false;
			}
		} else if (!this.root_file_name.equals(other.root_file_name)) {
			return false;
		}
		if (this.timestamp == null) {
			if (other.timestamp != null) {
				return false;
			}
		} else if (!this.timestamp.equals(other.timestamp)) {
			return false;
		}
		if (this.version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!this.version.equals(other.version)) {
			return false;
		}
		return true;
	}

}
