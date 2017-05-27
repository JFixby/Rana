
package com.jfixby.rana.api.pkg;

public interface ResourceRebuildIndexListener {

	static final ResourceRebuildIndexListener DEFAULT = new ResourceRebuildIndexListener() {
		@Override
		public void onError (final Throwable e) {
// Err.reportWarning(e.toString());
			e.printStackTrace();
// Err.reportError(e);
		}
	};

	void onError (Throwable e);

}
