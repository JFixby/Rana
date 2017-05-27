
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.AssetsPurgeResult;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.log.L;

public class RedAssetsPurgeResult implements AssetsPurgeResult {
	final Set<ContainerOwner> owners = Collections.newSet();

	@Override
	public void print () {
		L.d("AssetsPurgeResult");
		this.owners.print("deleted assets");
	}

	public void addOwner (final ContainerOwner owner) {
		this.owners.add(owner);
	}

}
