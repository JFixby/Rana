
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.file.File;

public class RedResourcesManagerSpecs {

	public File assets_folder;
	public File assets_cache_folder;
	public Set<RemoteBankSpecs> remotebanks = Collections.newSet();
	public boolean readResourcesConfigFile = true;

}
