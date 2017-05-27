
package com.jfixby.rana.api.pkg.io.cfg;

import java.util.ArrayList;

public class PackageManagerConfig {

	public static final String FILE_NAME = "rana-packman-config.json";

	public ArrayList<LocalAssetsFolder> local_banks = new ArrayList<LocalAssetsFolder>();
	public ArrayList<HttpAssetsFolder> remote_banks = new ArrayList<HttpAssetsFolder>();

}
