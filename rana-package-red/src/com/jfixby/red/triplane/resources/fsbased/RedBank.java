
package com.jfixby.red.triplane.resources.fsbased;

import java.io.IOException;

import com.jfixby.rana.api.pkg.PackageSearchParameters;
import com.jfixby.rana.api.pkg.PackageSearchResult;
import com.jfixby.rana.api.pkg.PackagesBank;
import com.jfixby.rana.api.pkg.PackagesTank;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class RedBank implements PackagesBank {

	private final Map<String, PackagesTank> resources = Collections.newMap();
	private final ID bankName;

	@Override
	public String toString () {
		return "ResourcesGroup[" + this.bankName + "]";
	}

	public RedBank (final ID bank_name) {
		this.bankName = bank_name;
	}

	public void addResource (final PackagesTank resource_to_install) {
		Debug.checkNull("resource_to_install", resource_to_install);
		final String name = resource_to_install.getShortName();
		if (this.resources.containsKey(name)) {
			Err.reportError("Resource with this name <" + name + "> is already installed: " + this.resources.get(name));
		}
		this.resources.put(name, resource_to_install);
	}

	@Override
	public PackagesTank getTank (final String string) {
		return this.resources.get(string);
	}

	@Override
	public ID getName () {
		return this.bankName;
	}

	@Override
	public void printAllIndexes () {
		for (int i = 0; i < this.resources.size(); i++) {
			final PackagesTank resouce = this.resources.getValueAt(i);
			L.d("index of ", resouce);
			resouce.printIndex();
		}
	}

	@Override
	public void rebuildAllIndexes () throws IOException {
		for (int i = 0; i < this.resources.size(); i++) {
			final PackagesTank resouce = this.resources.getValueAt(i);
// L.d("index of ", resouce);
			resouce.rebuildIndex();
		}
	}

	@Override
	public PackageSearchResult findPackages (final PackageSearchParameters search_params) {
		final RedPackageSearchResult result = new RedPackageSearchResult(search_params);
		Debug.checkNull("search_params", search_params);
		for (int i = 0; i < this.resources.size(); i++) {
			final PackagesTank resource = this.resources.getValueAt(i);
			final PackageSearchResult result_i = resource.findPackages(search_params);
			result.add(result_i);
		}

		return result;
	}

}
