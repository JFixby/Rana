
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.rana.api.pkg.Resource;
import com.jfixby.rana.api.pkg.ResourceRebuildIndexListener;
import com.jfixby.rana.api.pkg.ResourcesGroup;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class RedBank implements ResourcesGroup {

	private final Map<String, Resource> resources = Collections.newMap();
	private final ID bankName;

	@Override
	public String toString () {
		return "ResourcesGroup[" + this.bankName + "]";
	}

	public RedBank (final ID bank_name) {
		this.bankName = bank_name;
	}

	public void addResource (final Resource resource_to_install) {
		Debug.checkNull("resource_to_install", resource_to_install);
		final String name = resource_to_install.getName();
		if (this.resources.containsKey(name)) {
			Err.reportError("Resource with this name <" + name + "> is already installed: " + this.resources.get(name));
		}
		this.resources.put(name, resource_to_install);
	}

	@Override
	public ID getName () {
		return this.bankName;
	}

	@Override
	public void printAllIndexes () {
		for (int i = 0; i < this.resources.size(); i++) {
			final Resource resouce = this.resources.getValueAt(i);
			L.d("index of ", resouce);
			resouce.printIndex();
		}
	}

	@Override
	public void rebuildAllIndexes (final ResourceRebuildIndexListener listener) {
		for (int i = 0; i < this.resources.size(); i++) {
			final Resource resouce = this.resources.getValueAt(i);
// L.d("index of ", resouce);
			resouce.rebuildIndex(listener);
		}
	}

	@Override
	public PackageSearchResult findPackages (final PackageSearchParameters search_params) {
		final RedPackageSearchResult result = new RedPackageSearchResult(search_params);
		Debug.checkNull("search_params", search_params);
		for (int i = 0; i < this.resources.size(); i++) {
			final Resource resource = this.resources.getValueAt(i);
			final PackageSearchResult result_i = resource.findPackages(search_params);
			result.add(result_i);
		}

		return result;
	}

}
