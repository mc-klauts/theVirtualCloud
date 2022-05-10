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

package eu.thevirtualcloud.api.event.impl;

import eu.thevirtualcloud.api.event.CloudEvent;
import eu.thevirtualcloud.api.event.EventHandler;
import eu.thevirtualcloud.api.event.ICloudEventRegistry;
import eu.thevirtualcloud.api.event.impl.types.CloudAPIBootEvent;
import eu.thevirtualcloud.api.exceptions.EmptyInterfaceException;
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

public class SimpleCloudEventRegistry implements ICloudEventRegistry {

    private final Map<Class<? extends CloudEvent>, List<EventHandler<?>>> handlers = new HashMap<>();
    private final UUID uid = UUID.randomUUID();

    @Override
    public <T extends CloudEvent> int subscribe(Class<T> type, EventHandler<T> handler) {
        if (this.handlers.containsKey(type)) {
            this.handlers.get(type).add(handler);
            return this.handlers.get(type).size()-1;
        }
        List<EventHandler<? extends CloudEvent>> remote = new ArrayList<>();
        remote.add(handler);
        this.handlers.put(type, remote);
        return 0;
    }

    @Override
    public ICloudEventRegistry resubscribe(Class<? extends CloudEvent> type) {
        this.handlers.remove(type);
        return this;
    }

    @Override
    public ICloudEventRegistry clearSubscribes() {
        this.handlers.clear();
        return this;
    }

    @Override
    public ICloudEventRegistry resubscribe(Class<? extends CloudEvent> type, int index) {
        if (this.handlers.containsKey(type)) {
            this.handlers.get(type).remove(index);
        }
        return this;
    }

    @Override
    public Collection<EventHandler<?>> handlers(Class<? extends CloudEvent> type) {
        if (!this.handlers.containsKey(type))
            return Collections.emptyList();
        return this.handlers.get(type);
    }

    @Override
    public Collection<EventHandler<? extends CloudEvent>> handlers() {
        List<EventHandler<? extends CloudEvent>> cashed = new ArrayList<>();
        for (List<EventHandler<?>> value : this.handlers.values()) {
            cashed.addAll(value);
        }
        return cashed;
    }

    @Override
    public int indexOf(EventHandler<? extends CloudEvent> handler) {
        List<EventHandler<? extends CloudEvent>> list = (List<EventHandler<? extends CloudEvent>>) handlers();
        return list.indexOf(handler);
    }

    @NotNull
    @Override
    public UUID getUID() {
        return this.uid;
    }
}
