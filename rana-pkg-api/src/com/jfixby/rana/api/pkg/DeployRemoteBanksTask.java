
package com.jfixby.rana.api.pkg;

import com.jfixby.scarabei.api.taskman.Job;
import com.jfixby.scarabei.api.taskman.TaskProgress;

public interface DeployRemoteBanksTask {

	TaskProgress getProgress ();

	Job getJob ();

}
