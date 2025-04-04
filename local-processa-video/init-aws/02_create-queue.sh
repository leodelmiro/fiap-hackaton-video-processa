#!/bin/bash
awslocal sqs create-queue --queue-name processa-video --attributes VisibilityTimeout=300
awslocal sqs create-queue --queue-name processamento-realizado
awslocal sqs create-queue --queue-name erro-processamento