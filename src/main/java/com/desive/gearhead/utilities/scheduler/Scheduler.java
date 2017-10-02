/*
 * Copyright (C) 2017  GearHead
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.gearhead.utilities.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private ScheduledExecutorService SERVICE;

    public Scheduler(int threads) {
        this.SERVICE = Executors.newScheduledThreadPool(threads);
    }

    public Future<?> runTask(Runnable runner){
        return this.SERVICE.submit(runner);
    }

    public Future<?> runTaskLaterRepeat(Runnable runner, long delay, long interval, TimeUnit unit){
        return this.SERVICE.scheduleWithFixedDelay(runner, delay, interval, unit);
    }

    public void stop(){
        this.SERVICE.shutdown();
    }

    public void stopNow(){
        this.SERVICE.shutdownNow();
    }

}
