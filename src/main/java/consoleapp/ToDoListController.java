package consoleapp;

import java.io.*;
import java.util.HashMap;

public class ToDoListController {

    private final BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private HashMap<String, PriorityType> tasksWithPriorityMap = new HashMap<>();


    public static void main(String[] args) {



        ToDoListController controller = new ToDoListController();
        boolean isReadyToStart = false;

        isReadyToStart = controller.checkUserReadiness();
        if (isReadyToStart) {
            controller.interactWithUser();
            controller.createFile();
        }

    }

    private boolean checkUserReadiness() {
        String answer = "";
        System.out.println("Привет! Это твой ежедневный ToDo List! Ты готов начать?");

        while (true) {
            System.out.println("Напиши yes/no: ");

            try {
                answer = consoleReader.readLine().toLowerCase();
            } catch (IOException e) {
                answer = "";
            }

            if (answer.equals("yes") || answer.equals("да") || answer.equals("y") || answer.equals("д")) {
                return true;
            } else if (answer.equals("no") || answer.equals("нет") || answer.equals("n") || answer.equals("н")) {
                return false;
            } else {
                System.out.println("Извини, я не могу понять, что ты имеешь в виду...");
            }
        }
    }

    private void interactWithUser() {
        String PRIORITY_MASSAGE = "После задачи напиши 1/2/3 или ! (для Special задач), чтобы установить приоритет для твоей задачи.\n Например: Почистить зубы 3";
        String CHANGE_PRIORITY_MASSAGE = "Если ты хочешь изменить приоритет твоей задачи, напиши её снова с другим приоритетом.";
        String DELETE_MASSAGE = "Если ты хочешь удалить твою задачу, то напиши её снова с символом '-' в конце.\n Например: Почистить зубы -";
        String END_MASSAGE = "Если хочешь закончить список напиши 'end'.";

        System.out.println("Хорошо, давай запишем твои задачи на день.");
        System.out.println(PRIORITY_MASSAGE);
        System.out.println(CHANGE_PRIORITY_MASSAGE);
        System.out.println(DELETE_MASSAGE);
        System.out.println(END_MASSAGE);

        String task = "";
        char lastChar;
        PriorityType priority = null;


        try {
            while (!(task= consoleReader.readLine().trim()).equals("end")) {
                if (task.equals("")) { continue; }

                boolean havePriority = true;
                lastChar = task.charAt(task.length() - 1);
                String key = task.substring(0, task.length() - 1).trim();

                switch (lastChar) {
                    case '1' -> priority = PriorityType.LOW_PRIORITY;
                    case '2' -> priority = PriorityType.MEDIUM_PRIORITY;
                    case '3' -> priority = PriorityType.HIGH_PRIORITY;
                    case '!' -> priority = PriorityType.SPECIAL_PRIORITY;
                    case '-' -> {
                        if (tasksWithPriorityMap.containsKey(key)) {
                            tasksWithPriorityMap.remove(key);
                            continue;
                        } else {
                            System.out.println("У тебя нет такой задачи в списке.");
                        }
                    }
                    default -> {
                        priority = PriorityType.LOW_PRIORITY;
                        havePriority = false;

                        System.out.println("Ты не установил приоритет, так что я сделал это за тебя, установив его в 1.");
                        System.out.println(PRIORITY_MASSAGE);
                        System.out.println(CHANGE_PRIORITY_MASSAGE);
                        System.out.println(DELETE_MASSAGE);
                        System.out.println(END_MASSAGE);
                    }
                }

                if (havePriority) {
                    tasksWithPriorityMap.put(key, priority);
                } else {
                    tasksWithPriorityMap.put(task, priority);
                }
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении ввода.");
        }
    }


    public void createFile() {

        MarkdownParser markdownParser = new MarkdownParser(tasksWithPriorityMap);

        String toDoListData = markdownParser.parseToMarkdown();

        while (true) {

            System.out.println("Введи путь для сохранения твоего ToDo List с указанием названия (C:/Моя Папка/ToDoList.md): ");

            try (BufferedWriter fileOutput = new BufferedWriter(new FileWriter(consoleReader.readLine()))) {
                fileOutput.write(toDoListData);
                break;
            } catch (IOException e) {
                System.out.println("Извини, но введённый путь некорректен, попробуй ещё раз.");
            }

        }

        System.out.println("Ты успешно создал твой ToDo List!");
    }

}