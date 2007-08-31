/*
 * JSON-RPC-Java - a JSON-RPC to Java Bridge with dynamic invocation
 *
 * $Id: JSONRPCBridgeState.java,v 1.4 2006/03/06 12:41:33 mclark Exp $
 *
 * Copyright Metaparadigm Pte. Ltd. 2004, 2005.
 * Michael Clark <michael@metaparadigm.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.metaparadigm.jsonrpc;

import java.util.HashMap;
import java.util.HashSet;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metaparadigm.jsonrpc.serializer.Serializer;
import com.metaparadigm.jsonrpc.serializer.impl.ReferenceSerializer;

/**
 * Contains state information related to a JSONRPCBridge instance.
 * This state information includes exported classes, objects and references.
 */

public class JSONRPCBridgeState implements Serializable {
    
    private final static long serialVersionUID = 2;
    
    private final static Logger log = LoggerFactory.getLogger(JSONRPCBridgeState.class);

    // bridge this state information is associated with
    private JSONRPCBridge bridge;

    // key "exported class name", val Class
    private HashMap classMap = new HashMap();

    // key "exported instance name", val ObjectInstance
    private HashMap objectMap = new HashMap();

    // key Integer hashcode, object held as reference
    private HashMap referenceMap = null;
    
    // ReferenceSerializer if enabled
    private Serializer referenceSerializer = null;

    // key clazz, classes that should be returned as References
    private HashSet referenceSet = null;

    // key clazz, classes that should be returned as CallableReferences
    private HashSet callableReferenceSet = null;


    public JSONRPCBridgeState(JSONRPCBridge bridge) {
        this.bridge = bridge;
    }

    public HashSet getCallableReferenceSet() {
        return callableReferenceSet;
    }

    public void setCallableReferenceSet(HashSet callableReferenceSet) {
        this.callableReferenceSet = callableReferenceSet;
    }

    public HashMap getClassMap() {
        return classMap;
    }

    public void setClassMap(HashMap classMap) {
        this.classMap = classMap;
    }

    public HashMap getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(HashMap objectMap) {
        this.objectMap = objectMap;
    }

    public HashMap getReferenceMap() {
        return referenceMap;
    }

    public void setReferenceMap(HashMap referenceMap) {
        this.referenceMap = referenceMap;
    }

    public Serializer getReferenceSerializer() {
        return referenceSerializer;
    }

    public void setReferenceSerializer(Serializer referenceSerializer) {
        this.referenceSerializer = referenceSerializer;
    }

    public HashSet getReferenceSet() {
        return referenceSet;
    }

    public void setReferenceSet(HashSet referenceSet) {
        this.referenceSet = referenceSet;
    }

    public synchronized void enableReferences() throws Exception {
        if (referenceSerializer == null) {
            referenceSerializer = new ReferenceSerializer(bridge);
            bridge.registerSerializer(referenceSerializer);
            log.info("enabled references on this bridge");
        }
        if (referenceMap == null)
            referenceMap = new HashMap(); 
        if (referenceSet == null)
            referenceSet = new HashSet();
        if (callableReferenceSet == null)
            callableReferenceSet = new HashSet();
    }
}