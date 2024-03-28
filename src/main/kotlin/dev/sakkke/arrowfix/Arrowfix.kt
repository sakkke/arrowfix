package dev.sakkke.arrowfix

import org.bukkit.plugin.java.JavaPlugin
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory

class Arrowfix: JavaPlugin() {
    companion object {
        lateinit var instance: Arrowfix
            private set
    }

    override fun onEnable() {
        logger.info("Arrowfix enabled")

        instance = this

        val schedulerFactory = StdSchedulerFactory()
        val scheduler = schedulerFactory.scheduler
        scheduler.start()
        val arrowfixJob = JobBuilder.newJob(ArrowfixJob::class.java)
            .withIdentity("arrowfixJob", "main")
            .build()
        val hourlyTrigger = TriggerBuilder.newTrigger()
            .withIdentity("hourlyTrigger", "main")
            .startNow()
            .withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?"))
            .forJob("arrowfixJob", "main")
            .build()
        scheduler.scheduleJob(arrowfixJob, hourlyTrigger)
    }
}