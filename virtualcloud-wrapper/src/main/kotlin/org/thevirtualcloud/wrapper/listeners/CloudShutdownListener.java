/*
 * MIT License
 *
 * Copyright (c) 2022 REPLACE_WITH_NAME
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.thevirtualcloud.wrapper.listeners;

import org.thevirtualcloud.api.CloudAPI;
import org.thevirtualcloud.wrapper.CloudWrapper;
import org.thevirtualcloud.wrapper.events.CloudShutdownEvent;

/**
 * this doc was created on 07.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 */

public class CloudShutdownListener {

    public CloudShutdownListener() {
        CloudWrapper.getInstance().getCloudRemoteEventRegistry().subscribe(CloudShutdownEvent.class, event -> {
            CloudAPI.instance.getCloudConsole().write("try to shutdown the cloud");
            CloudAPI.getInstance().getCloudChannelManager().getCloudChannel().close();
            CloudAPI.getInstance().getCloudConsole().write("Close the Cloud server...");
            System.exit(-1);
        });
    }
}
