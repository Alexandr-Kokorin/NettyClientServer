package server.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Setter
@Getter
public class DataBase {

    private Map<String, Topic> topics = new ConcurrentHashMap<>();

    public Map<String, Integer> getTopicsMap() {
        Map<String, Integer> map = new HashMap<>();
        for (String topic : topics.keySet()) {
            map.put(topic, topics.get(topic).votes.size());
        }
        return map;
    }

    public Set<String> getTopicSet(String topic) {
        return topics.get(topic).votes.keySet();
    }

    public Vote getVote(String topic, String vote) {
        return topics.get(topic).votes.get(vote);
    }

    public boolean isExistTopic(String topic) {
        return topics.containsKey(topic);
    }

    public boolean isExistVote(String topic, String vote) {
        return topics.get(topic).votes.containsKey(vote);
    }

    public boolean isExistAnswer(String topic, String vote, String answer) {
        return topics.get(topic).votes.get(vote).answers.containsKey(answer);
    }

    public boolean isVoteCreator(String topic, String vote, String creator) {
        return topics.get(topic).votes.get(vote).creator.equals(creator);
    }

    public boolean isVoter(String topic, String vote, String voter) {
        var set = topics.get(topic).votes.get(vote).answers;
        for (var answer: set.values()) {
            if (answer.users.contains(voter)) {
                return true;
            }
        }
        return false;
    }

    public void addTopic(String topic) {
        topics.put(topic, new Topic());
    }

    public void addVote(String topic, String vote, String creator, String description, List<String> answers) {
        topics.get(topic).votes.put(vote, new Vote(creator, description, answers));
    }

    public void addAnswer(String topic, String vote, String answer, String user) {
        topics.get(topic).votes.get(vote).answers.get(answer).users.add(user);
    }

//---------------------------------------------------------------------------------------------------------------

    public static class Topic {

        private final Map<String, Vote> votes = new ConcurrentHashMap<>();
    }

    public static class Vote {

        private final String creator;
        private final String description;
        private final Map<String, Answer> answers = new ConcurrentHashMap<>();

        public Vote( String creator, String description, List<String> answers) {
            this.creator = creator;
            this.description = description;
            for (String answer : answers) {
                this.answers.put(answer, new Answer());
            }
        }
    }

    public static class Answer {

        private final Set<String> users = new HashSet<>();
    }
}
