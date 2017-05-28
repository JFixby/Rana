
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.AssetsContainer;
import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.err.Err;

public class ContainersRegistry {

	final Map<SealedAssetsContainer, ContainerOwner> container2owner = Collections.newMap();
	final Map<ContainerOwner, Set<SealedAssetsContainer>> owner2containers = Collections.newMap();

	public ContainerOwner getContainerOwner (final SealedAssetsContainer c) {
		return this.container2owner.get(c);
	}

	public void unregister (final ContainerOwner owner) {
		if (this.hasMoreContainers(owner)) {
			Err.reportError("ContainersRegistry is corrupted");
		}
		this.owner2containers.remove(owner);
	}

	public boolean hasMoreContainers (final ContainerOwner owner) {
		final Set<SealedAssetsContainer> containers = this.owner2containers.get(owner);
		if (containers == null) {
			return false;
		}
		if (containers.size() == 0) {
			return false;
		}
		return true;
	}

	public void registerContainer (final AssetsContainer container) {

	}

}
