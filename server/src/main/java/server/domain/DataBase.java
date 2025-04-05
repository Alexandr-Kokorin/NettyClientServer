package server.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @JsonIgnore
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

    public String getVoteDesc(String topic, String vote) {
        return topics.get(topic).votes.get(vote).description;
    }

    public Map<String, Integer> getVoteMap(String topic, String vote) {
        var answers = topics.get(topic).votes.get(vote).answers;
        Map<String, Integer> map = new HashMap<>();
        for (String ans : answers.keySet()) {
            map.put(ans, answers.get(ans).users.size());
        }
        return map;
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

    public void deleteVote(String topic, String vote) {
        topics.get(topic).votes.remove(vote);
    }


    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Topic {

        private final Map<String, Vote> votes = new ConcurrentHashMap<>();
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @NoArgsConstructor
    public static class Vote {

        private String creator;
        private String description;
        private final Map<String, Answer> answers = new ConcurrentHashMap<>();

        public Vote( String creator, String description, List<String> answers) {
            this.creator = creator;
            this.description = description;
            for (String answer : answers) {
                this.answers.put(answer, new Answer());
            }
        }
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Answer {

        private final Set<String> users = new HashSet<>();
    }
}
