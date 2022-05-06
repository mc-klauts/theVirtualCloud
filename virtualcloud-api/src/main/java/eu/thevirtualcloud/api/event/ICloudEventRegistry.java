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

package eu.thevirtualcloud.api.event;

import eu.thevirtualcloud.api.CloudAPI;
import eu.thevirtualcloud.api.common.UIDDabble;
import eu.thevirtualcloud.api.event.impl.SimpleCloudEventRegistry;

import java.util.Collection;

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

@SuppressWarnings("UnusedReturnValue")
public interface ICloudEventRegistry extends UIDDabble {

    static ICloudEventRegistry insert() {
        ICloudEventRegistry registry = new SimpleCloudEventRegistry();
        CloudAPI.getInstance().getCloudEventManager().registerCloudRegistry(registry);
        return registry;
    }

    <T extends CloudEvent> int subscribe(Class<T> type, EventHandler<T> handler);

    ICloudEventRegistry resubscribe(Class<? extends CloudEvent> type);

    ICloudEventRegistry clearSubscribes();

    ICloudEventRegistry resubscribe(Class<? extends CloudEvent> type, int index);

    Collection<EventHandler<?>> handlers(Class<? extends CloudEvent> type);

    Collection<EventHandler<? extends CloudEvent>> handlers();

    int indexOf(EventHandler<? extends CloudEvent> handler);

}
