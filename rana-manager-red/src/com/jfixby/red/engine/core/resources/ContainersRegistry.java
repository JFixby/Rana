
package com.jfixby.red.engine.core.resources;

import com.jfixby.rana.api.asset.SealedAssetsContainer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.err.Err;

public class ContainersRegistry {

	final Map<SealedAssetsContainer, AssetsContainerOwner> container2owner = Collections.newMap();
	final Map<AssetsContainerOwner, Set<SealedAssetsContainer>> owner2containers = Collections.newMap();

	public AssetsContainerOwner getContainerOwner (final SealedAssetsContainer c) {
		return this.container2owner.get(c);
	}

	public void unregister (final AssetsContainerOwner owner) {
		if (this.hasMoreContainers(owner)) {
			Err.reportError("ContainersRegistry is corrupted");
		}
		this.owner2containers.remove(owner);
	}

	public boolean hasMoreContainers (final AssetsContainerOwner owner) {
		final Set<SealedAssetsContainer> containers = this.owner2containers.get(owner);
		if (containers == null) {
			return false;
		}
		if (containers.size() == 0) {
			return false;
		}
		return true;
	}

	public void registerContainer (final SealedAssetsContainer container) {
		final AssetsContainerOwner owner = new AssetsContainerOwner(container);
		this.container2owner.put(container, owner);
		final Set<SealedAssetsContainer> list = Collections.newSet();
		list.add(container);
		this.owner2containers.put(owner, list);
// L.d("MISSING REG");
	}

	public void unregisterContainer (final SealedAssetsContainer c) {
// this.owner2containers.print("owner2containers");
// this.container2owner.print("container2owner");
		final AssetsContainerOwner owner = this.container2owner.get(c);
		this.container2owner.remove(c);
		final Set<SealedAssetsContainer> list = this.owner2containers.get(owner);
		list.remove(c);
		if (list.size() == 0) {
			this.owner2containers.remove(owner);
		}

// this.owner2containers.print("owner2containers");
// this.container2owner.print("container2owner");
	}

}
