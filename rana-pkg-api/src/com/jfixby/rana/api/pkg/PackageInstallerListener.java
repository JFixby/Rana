
package com.jfixby.rana.api.pkg;

import java.io.IOException;

public interface PackageInstallerListener {

	void onFailedToInstall (IOException e);

}
