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

package org.jboss.as.deployment.module;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;
import org.jboss.msc.value.Value;
import org.jboss.msc.value.Values;

/**
 * Service used to wrap a deployment module loader as a service.
 * 
 * @author John E. Bailey
 */
public class DeploymentModuleLoaderService implements Service<DeploymentModuleLoader> {
    private final Value<DeploymentModuleLoader> deploymentModuleLoaderValue;

    public DeploymentModuleLoaderService(Value<DeploymentModuleLoader> deploymentModuleLoaderValue) {
        this.deploymentModuleLoaderValue = deploymentModuleLoaderValue;
    }

    public DeploymentModuleLoaderService(DeploymentModuleLoader deploymentModuleLoader) {
        this.deploymentModuleLoaderValue = Values.immediateValue(deploymentModuleLoader);
    }

    @Override
    public void start(StartContext context) throws StartException {
    }

    @Override
    public void stop(StopContext context) {
    }

    @Override
    public DeploymentModuleLoader getValue() throws IllegalStateException {
        return deploymentModuleLoaderValue.getValue();
    }
}
