package com.bugsnag.android.mazerunner.scenarios

import android.content.Context

import com.bugsnag.android.Bugsnag
import com.bugsnag.android.Configuration

/**
 * Attempts to send a handled exception to Bugsnag, when the release stage is null.
 */
internal class NullReleaseStageScenario(config: Configuration,
                                        context: Context) : Scenario(config, context) {
    init {
        config.autoCaptureSessions = false
        config.releaseStage = null
        config.notifyReleaseStages = listOf("prod")
    }

    override fun run() {
        super.run()
        Bugsnag.notify(generateException())
    }

}