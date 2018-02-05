package io.newbieApps.springboot.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*SPRING SERVICE CLASSES ARE SINGLETON*/
@Service	
public class TopicService {
	@Autowired
	private TopicRepository topicRepository;
	private List<Topic> topics=new ArrayList<>(Arrays.asList(
			new Topic("spring","Spring Framewok","Spring Framework description"),
			new Topic("java","Core java","core java description"),
			new Topic("javascript","javascript","javascript description"))
			);
			
	public List<Topic> getAllTopics(){
//		return topics;
		List<Topic> topics=new ArrayList<Topic>();
		topicRepository.findAll()
		.forEach(topics::add);
		return topics;
	}

	public Topic getTopic(String id) {
		//Use of lambda expressions
//		return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
		return topicRepository.findOne(id);
	}

	public void addTopic(Topic topic) {
//		topics.add(topic);
		topicRepository.save(topic);
	}

	public void updateTopic(String id, Topic topic) {
		topicRepository.save(topic);
	}
	
	public void deleteTopic(String topicId){
//		 topics.removeIf(t -> t.getId().equals(topicId));
		topicRepository.delete(topicId);
	}
}
