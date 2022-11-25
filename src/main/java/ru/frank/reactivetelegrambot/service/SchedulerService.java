package ru.frank.reactivetelegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import ru.frank.reactivetelegrambot.bot.ScrumBot;
import ru.frank.reactivetelegrambot.property.ScrumProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private static final TimeZone MOSCOW_ZONE = TimeZone.getTimeZone("Europe/Moscow");

    private final TaskScheduler executor;
    private final ScrumProperties properties;

    private Map<String, ScheduledFuture> dailyTasks = new HashMap<>();
    private Map<String, ScheduledFuture> wednesdayTasks = new HashMap<>();

    public void scheduleScrumForConfiguredGroups(ScrumBot bot) {
        properties.getGroupIds().forEach(groupId -> {
            scheduleDaily(bot, groupId);
            scheduleWednesday(bot, groupId);
        });
    }

    public synchronized void scheduleDaily(ScrumBot bot, String groupId) {
        checkTaskNotExists(groupId, dailyTasks);

        var task = executor.schedule(
                new SendMessageJob(
                        bot,
                        groupId,
                        buildMessage(
                                properties.getDaily().getMessage(),
                                properties.getDaily().getLink()
                        )
                ),
                new CronTrigger(properties.getDaily().getCronExpression(), MOSCOW_ZONE)
        );
        dailyTasks.put(groupId, task);
        System.out.println("Scheduled daily: " + groupId);
    }

    public synchronized void stopDaily(String groupId) {
        var task = dailyTasks.get(groupId);
        stopTask(task);
    }

    public synchronized void scheduleWednesday(ScrumBot bot, String groupId) {
        checkTaskNotExists(groupId, wednesdayTasks);

        var task = executor.schedule(
                new SendMessageJob(
                        bot,
                        groupId,
                        properties.getWednesday().getMessage()
                ),
                new CronTrigger(properties.getWednesday().getCronExpression(), MOSCOW_ZONE)
        );
        wednesdayTasks.put(groupId, task);
        System.out.println("Scheduled wednesday: " + groupId);
    }

    public synchronized void stopWednesday(String groupId) {
        var task = wednesdayTasks.get(groupId);
        stopTask(task);
    }

    private void checkTaskNotExists(
            String groupId,
            Map<String, ScheduledFuture> tasks
    ) {
        ScheduledFuture task = tasks.get(groupId);
        if (isTaskActive(task)) {
            throw new IllegalStateException("Task is active already");
        }
    }

    private boolean isTaskActive(ScheduledFuture task) {
        return task != null && !task.isCancelled();
    }

    private void stopTask(ScheduledFuture task) {
        if (isTaskActive(task)) {
            task.cancel(true);
            System.out.println("Task stopped");
        }
    }

    private String buildMessage(String... messages) {
        return String.join("\n", messages);
    }
}
