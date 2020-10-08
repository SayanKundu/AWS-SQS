package com.sayan.aws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/sqs")
public class SQSController {
	

	Logger logger = LoggerFactory.getLogger(SQSController.class);

	// TEMPLATE
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	// END-POINT
	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	@GetMapping
	public void sendMessageToQueue() {
		// This message I need to send using queueMessagingTemplate
		queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload("Hello from AWS SQS Service").build());
	}
	
	@PostMapping
	public void postMessage(@RequestBody String msg) {
		queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(msg).build());
	}

	// CONSUMES THE MESSAGE
	//GIVE THE QUEUE NAME
	@SqsListener("Java_Sayan_1")
	public void loadMessageFromSQS(String message) {
		logger.info("Received from AWS SQS Queue - " + message);
	}

}
