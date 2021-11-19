@
public class Publisher {
	@Autowired
	private JmsMessagingTemplate jms;

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;

	@RequestMapping("/queue")
	public String queue(){

		for (int i = 0; i < 10 ; i++){
			jms.convertAndSend(queue, "queue"+i);
		}

		return "queue 发送成功";
	}

	@JmsListener(destination = "out.queue")
	public void consumerMsg(String msg){
		System.out.println(msg);
	}

	@RequestMapping("/topic")
	public String topic(){

		for (int i = 0; i < 10 ; i++){
			jms.convertAndSend(topic, "topic"+i);
		}

		return "topic 发送成功";
	}
}
