package com.sayan.aws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SQSController {

	Logger logger = LoggerFactory.getLogger(SQSController.class);

	// TEMPLATE
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	// END-POINT
	@Value("${cloud.aws.end-point.uri}")
	private String endpoint;

	@GetMapping("/send/{message}")
	public void sendMessageToQueue(@PathVariable String message) {
		// This message I need to send using queueMessagingTemplate
		queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
	}

	// CONSUMES THE MESSAGE
	@SqsListener("Java_Sayan_1")
	public void loadMessageFromSQS(String message) {
		logger.info("message from SQS" + message);
	}

}
