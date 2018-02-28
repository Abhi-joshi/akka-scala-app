package com.example.akka.sample

import akka.actor.{Actor, Props}

case class RecordNumberOfHopsTravelled(numberOfHopsTravelled: Int, actorName: String)

class ChildActorTaskOneAndTaskTwo extends Actor {

  var count: Int = 0

  override def receive: Receive = {
    case RecordNumberOfHopsTravelled(numberOfHopsTravelled, actorName) => {
      count = numberOfHopsTravelled + 1; // Increasing the count by one in every single actor called (Step - 7)
      println(s"actor ${actorName}, message received <${System.currentTimeMillis()}>") // Printing the time in millisecond described in task2 (pdf)
      };
    case Count() => sender ! CountResponse(count) // Send the current to supervisior actor //(Step - 10)
    case _ => unhandled()
  }
}

object ChildActorTaskOneAndTaskTwo {
  def props = Props(classOf[ChildActorTaskOneAndTaskTwo])
}