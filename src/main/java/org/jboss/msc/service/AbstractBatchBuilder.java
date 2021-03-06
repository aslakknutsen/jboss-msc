/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.msc.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract base class used for BatchBuilders
 *
 * @author John Bailey
 */
abstract class AbstractBatchBuilder implements BatchBuilder {
    private final Set<ServiceListener<Object>> listeners = new HashSet<ServiceListener<Object>>();
    private final Set<ServiceName> dependencies = new HashSet<ServiceName>();

    abstract boolean isDone();

    @Override
    public BatchBuilder addListener(ServiceListener<Object> listener) {
        if(isDone()) {
            throw alreadyInstalled();
        }
        listeners.add(listener);
        return this;
    }

    @Override
    public BatchBuilder addListener(ServiceListener<Object>... listeners) {
        if(isDone()) {
            throw alreadyInstalled();
        }
        final Set<ServiceListener<Object>> batchListeners = this.listeners;

        for(ServiceListener<Object> listener : listeners) {
            batchListeners.add(listener);
        }
        return this;
    }

    @Override
    public BatchBuilder addListener(Collection<ServiceListener<Object>> listeners) {
        if(isDone()) {
            throw alreadyInstalled();
        }
        if (listeners == null)
            throw new IllegalArgumentException("Listeners can not be null");

        final Set<ServiceListener<Object>> batchListeners = this.listeners;

        for(ServiceListener<Object> listener : listeners) {
            batchListeners.add(listener);
        }
        return this;
    }

    @Override
    public BatchBuilder addDependency(ServiceName dependency) {
        if(isDone()) {
            throw alreadyInstalled();
        }
        dependencies.add(dependency);
        return this;
    }

    @Override
    public BatchBuilder addDependency(ServiceName... dependencies) {
        if(isDone()) {
            throw alreadyInstalled();
        }
        final Set<ServiceName> batchDependencies = this.dependencies;
        for(ServiceName dependency : dependencies) {
            batchDependencies.add(dependency);
        }
        return this;
    }

    @Override
    public BatchBuilder addDependency(Collection<ServiceName> dependencies) {
        if(isDone()) {
            throw alreadyInstalled();
        }
        if(dependencies == null) throw new IllegalArgumentException("Dependencies can not be null");
        final Set<ServiceName> batchDependencies = this.dependencies;
        for(ServiceName dependency : dependencies) {
            batchDependencies.add(dependency);
        }
        return this;
    }

    static IllegalStateException alreadyInstalled() {
        return new IllegalStateException("Batch already installed");
    }

    Set<ServiceListener<Object>> getListeners() {
        return listeners;
    }

    Set<ServiceName> getDependencies() {
        return dependencies;
    }
}
