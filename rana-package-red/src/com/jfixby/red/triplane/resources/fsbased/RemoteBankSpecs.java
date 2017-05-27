
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.net.http.HttpURL;

public class RemoteBankSpecs {

	private HttpURL bankURL;
	final Set<String> tanks = Collections.newSet();

	public void setBankUrl (final HttpURL bankURL) {
		this.bankURL = bankURL;
	}

	public void addTanks (final Iterable<String> tanks) {
		this.tanks.addAll(tanks);
	}

	public Iterable<String> listTanks () {
		return this.tanks;
	}

	public HttpURL getUrl () {
		return this.bankURL;
	}

}
