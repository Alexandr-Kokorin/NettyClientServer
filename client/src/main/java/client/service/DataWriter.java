package client.service;

import org.springframework.stereotype.Component;

@Component
public class DataWriter {

    public void writeTopics(String data) {
        if (data.isEmpty()) {
            System.out.println("Еще не создано ни одного раздела.");
        } else {
            var topics = data.split("\\|");
            System.out.println("Список существующих разделов - количество голосований в них:\n***");
            for (int i = 0; i < topics.length; i += 2) {
                System.out.println(topics[i] + " - " + topics[i + 1]);
            }
            System.out.println("***");
        }
    }

    public void writeTopic(String data) {
        if (data.isEmpty()) {
            System.out.println("Еще не создано ни одного голосования в данном разделе.");
        } else {
            var votes = data.split("\\|");
            System.out.println("Список доступных голосований в данном разделе:\n***");
            for (String vote : votes) {
                System.out.println(vote);
            }
            System.out.println("***");
        }
    }

    public void writeVote(String data) {
        var vote = data.split("\\|");
        System.out.println(vote[0] + ":\n***");
        for (int i = 1; i < vote.length; i += 2) {
            System.out.println(vote[i] + " - " + vote[i + 1]);
        }
        System.out.println("***");
    }
}
