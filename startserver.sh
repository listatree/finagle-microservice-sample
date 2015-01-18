#!/bin/sh

sbt assembly
java -jar target/scala-2.11/finagle-microservice-assembly-1.0.jar