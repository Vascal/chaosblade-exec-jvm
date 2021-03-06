/*
 * Copyright 1999-2019 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.chaosblade.exec.common.model.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.chaosblade.exec.common.model.matcher.MatcherSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Changjun Xiao
 */
public abstract class BaseActionSpec implements ActionSpec {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseActionSpec.class);

    private ConcurrentHashMap<String, MatcherSpec> matcherSpecs = new ConcurrentHashMap<String, MatcherSpec>();

    private ActionExecutor actionExecutor;

    public BaseActionSpec(ActionExecutor actionExecutor) {
        this.actionExecutor = actionExecutor;
    }

    @Override
    public List<MatcherSpec> getMatchers() {
        return new ArrayList<MatcherSpec>(matcherSpecs.values());
    }

    @Override
    public Map<String, MatcherSpec> getMatcherSpecs() {
        return matcherSpecs;
    }

    @Override
    public void addMatcherDesc(MatcherSpec matcherSpec) {
        MatcherSpec oldMatcherSpec = matcherSpecs.putIfAbsent(matcherSpec.getName(), matcherSpec);
        if (oldMatcherSpec != null) {
            LOGGER.warn("{} matcher has defined", matcherSpec.getName());
        }
    }

    @Override
    public ActionExecutor getActionExecutor() {
        return actionExecutor;
    }
}
