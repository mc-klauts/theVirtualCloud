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

package eu.thevirtualcloud.api.threading

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * java doc created on 08.05.2022
 * @author Alexa
 */

abstract class AbstractRunnerTask: IRunnerTask {

    companion object {
        var executor: ExecutorService = Executors.newSingleThreadExecutor()
    }

    private var delay: Int = -1
    private var period: Int = -1
    private val thread: Thread = Thread()
    private var run: Boolean = false
    private var runnerType: RunnerType = RunnerType.SYNCHRONIZED

    override fun cancel(): IRunnerTask {
        if (run) {
            this.thread.interrupt()
            run = false
        }
        return this
    }

    override fun submit(task: ITaskHandler): IRunnerTask {
        if (delay == -1 && period == -1) {
            if (this.runnerType == RunnerType.ASYNCHRONIZED)
                executor.submit { task.handle() } else
                Runnable { task.handle() }.run()
        }
        return this
    }

    override fun runner(runnerType: RunnerType): IRunnerTask {
        this.runnerType = runnerType
        return this
    }

    override fun onSubmit(delay: Int, period: Int): IRunnerTask {
        this.period = period
        this.delay = delay
        return this
    }

    override fun onSubmit(delay: Int): IRunnerTask {
        this.delay = delay
        return this
    }
}