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

/**
 * 
 */
package org.jboss.as.server.manager;

import org.jboss.as.process.ProcessManagerSlave.Handler;
import org.jboss.logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * A MessageHandler.
 * 
 * @author Brian Stansberry
 */
class MessageHandler implements Handler {

    private static final Logger log = Logger.getLogger("org.jboss.server.manager");

    private final ServerManager serverManager;
//    private final Map<String, Server> servers = new ConcurrentHashMap<String, Server>();
    
    
    MessageHandler(ServerManager serverManager) {
        if (serverManager == null) {
            throw new IllegalArgumentException("serverManager is null");
        }
        this.serverManager = serverManager;
    }

    @Override
    public void handleMessage(String sourceProcessName, byte[] message) {
        ServerMessage serverMessage = null;
        try {
            serverMessage = readServerMessage(message);
            // TODO: actually handle this....
            log.info("Received message from server " + sourceProcessName + ": " + serverMessage.getMessage());
        } catch (Throwable t) {
            log.warn("Failed to read server message", t);
        }
    }
    
    /* (non-Javadoc)
     * @see org.jboss.as.process.ProcessManagerSlave.Handler#handleMessage(java.lang.String, java.util.List)
     */
    @Override
    public void handleMessage(String sourceProcessName, List<String> message) {
        log.info("Received message from server " + sourceProcessName + ":" + message);
        // TODO: actually handle this....
    }

    @Override
    public void shutdown() {
        serverManager.stop();        
    }

    private ServerMessage readServerMessage(byte[] message) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            final ServerMessage serverMessage = (ServerMessage)objectInputStream.readObject();
            return serverMessage;
        } finally {
            if(objectInputStream != null)
                objectInputStream.close();
        }
    }
    
//    public void registerServer(String serverName, Server server) {
//        if (serverName == null) {
//            throw new IllegalArgumentException("serverName is null");
//        }
//        if (server == null) {
//            throw new IllegalArgumentException("server is null");
//        }
//        servers.put(serverName, server);
//    }
//    
//    public void unregisterServer(String serverName) {
//        if (serverName == null) {
//            throw new IllegalArgumentException("serverName is null");
//        }
//        servers.remove(serverName);
//    }

}
