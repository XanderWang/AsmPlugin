package com.xander.plugin.asm

import com.android.build.api.transform.Transform
import org.gradle.api.Project

interface IPluginFactory {
  Transform createTransform(Project project)
}