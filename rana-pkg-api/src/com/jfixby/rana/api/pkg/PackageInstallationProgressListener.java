package com.jfixby.rana.api.pkg;

import java.io.IOException;

public interface PackageInstallationProgressListener {

	public static final PackageInstallationProgressListener NULL = new PackageInstallationProgressListener() {

		@Override
		public void onBegin() {
		}

		@Override
		public void onFailed(IOException e) {
			e.printStackTrace();
		}

		@Override
		public void onProgress(float progress) {
		}

		@Override
		public void onDone() {
		}
	};

	void onBegin();

	void onFailed(IOException e);

	void onProgress(float progress);

	void onDone();

}
