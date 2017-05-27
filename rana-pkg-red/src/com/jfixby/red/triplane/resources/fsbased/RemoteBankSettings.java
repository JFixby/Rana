
package com.jfixby.red.triplane.resources.fsbased;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Set;
import com.jfixby.scarabei.api.net.http.HttpURL;

public class RemoteBankSettings {
	public HttpURL bankURL;
	public final Set<String> tanks = Collections.newSet();

}
