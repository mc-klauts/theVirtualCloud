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

package org.thevirtualcloud.api.event.impl;

import org.thevirtualcloud.api.event.CloudEvent;
import org.thevirtualcloud.api.event.EventHandler;
import org.thevirtualcloud.api.event.ICloudEventManager;
import org.thevirtualcloud.api.event.ICloudEventRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * this doc was created on 05.05.2022
 * This class belongs to the theVirtualCloud project
 *
 * @author Generix030
 *
 */

public class SimpleCloudEventManager implements ICloudEventManager {

    private final Map<String ,ICloudEventRegistry> register = new HashMap<>();

    public SimpleCloudEventManager() {
    }

    @NotNull
    public ICloudEventManager registerCloudRegistry(@NotNull ICloudEventRegistry registry) {
        this.register.put(registry.getUID().toString(), registry);
        return this;
    }

    @Override
    public<T extends CloudEvent> void callEvent(@NotNull T event) {
        for (ICloudEventRegistry registry : this.register.values()) {
            for (EventHandler<?> handler : registry.handlers(event.getClass())) {
                @SuppressWarnings("unchecked") EventHandler<T> raw = (EventHandler<T>) handler;
                raw.handle(event);
            }
        }
    }

}
