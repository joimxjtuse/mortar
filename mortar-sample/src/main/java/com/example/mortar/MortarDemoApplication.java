/*
 * Copyright 2013 Square Inc.
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
package com.example.mortar;

import android.app.Application;
import com.example.flow.GsonParceler;
import com.example.flow.util.FlowBundler;
import com.example.mortar.core.ApplicationModule;
import com.example.mortar.screen.ChatListScreen;
import com.google.gson.Gson;
import dagger.ObjectGraph;
import flow.Backstack;
import javax.annotation.Nullable;
import mortar.Mortar;
import mortar.MortarScope;

public class MortarDemoApplication extends Application {
  private final FlowBundler flowBundler = new FlowBundler(new GsonParceler(new Gson())) {
    @Override protected Backstack getColdStartBackstack(@Nullable Backstack restoredBackstack) {
      return restoredBackstack == null ? Backstack.single(new ChatListScreen()) : restoredBackstack;
    }
  };
  private MortarScope rootScope;

  @Override public void onCreate() {
    super.onCreate();

    rootScope =
        Mortar.createRootScope(BuildConfig.DEBUG, ObjectGraph.create(new ApplicationModule()));
  }

  public FlowBundler getFlowBundler() {
    return flowBundler;
  }

  @Override public Object getSystemService(String name) {
    if (Mortar.isScopeSystemService(name)) {
      return rootScope;
    }
    return super.getSystemService(name);
  }
}
