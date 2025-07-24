package consoleapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MarkdownParser {

    private Date date;
    private List<String> highPriorityTasks = new ArrayList<>();
    private List<String> mediumPriorityTasks = new ArrayList<>();
    private List<String> lowPriorityTasks = new ArrayList<>();
    private List<String> specialPriorityTasks = new ArrayList<>();

    public MarkdownParser(HashMap<String, PriorityType> tasksWithPriorityMap) {
        date = new Date();

        for (String task : tasksWithPriorityMap.keySet()) {

            switch (tasksWithPriorityMap.get(task)) {
                case HIGH_PRIORITY -> highPriorityTasks.add(task);
                case MEDIUM_PRIORITY -> mediumPriorityTasks.add(task);
                case LOW_PRIORITY -> lowPriorityTasks.add(task);
                case SPECIAL_PRIORITY -> specialPriorityTasks.add(task);
            }

        }

    }

    public String parseToMarkdown() {

        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        stringBuilder.append("# ").append(simpleDateFormat.format(date)).append("\n\n");
        stringBuilder.append("# Сегодняшний ToDo List\n\n\n");

        stringBuilder.append("## High Priority Tasks\n\n");
        for (String task : highPriorityTasks) {
            stringBuilder.append("- ").append(task).append("\n");
        }

        stringBuilder.append("\n## Medium Priority Tasks\n\n");
        for (String task : mediumPriorityTasks) {
            stringBuilder.append("- ").append(task).append("\n");
        }

        stringBuilder.append("\n## Low Priority Task\n\n");
        for (String task : lowPriorityTasks) {
            stringBuilder.append("- ").append(task).append("\n");
        }

        stringBuilder.append("\n------------------------");
        stringBuilder.append("\n## SPECIAL Priority Tasks\n\n");
        for (String task : specialPriorityTasks) {
            stringBuilder.append("- ").append(task).append("\n");
        }

        return stringBuilder.toString();
    }
}
