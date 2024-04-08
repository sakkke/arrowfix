package dev.sakkke.arrowfix

import org.bukkit.Bukkit
import org.quartz.Job
import org.quartz.JobExecutionContext

class ArrowfixJob: Job {
    override fun execute(p0: JobExecutionContext?) {
        Bukkit.getScheduler().runTask(Arrowfix.instance, Runnable {
            Arrowfix.instance.logger.info("Killing all the arrow-related entities")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=arrow]")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=snowball]")
        })
    }
}