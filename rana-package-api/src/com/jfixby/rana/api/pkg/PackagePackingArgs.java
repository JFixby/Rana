
package com.jfixby.rana.api.pkg;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;

public class PackagePackingArgs {

	public String packageName;
	public final Set<File> packedFiles = Collections.newSet();
	public final Set<ID> packedAssets = Collections.newSet();
	public final Set<ID> requiredAssets = Collections.newSet();
	public String rootFileName;
	public String packageFormat;
	public String version;

}
