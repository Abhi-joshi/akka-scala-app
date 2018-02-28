package com.example.akka.sample

import akka.actor.{Props, Actor}
import akka.event.Logging
import SupervisorActorThree.ReceiveMessageSupervisior

class ChildActorTaskThree extends Actor {
  import ChildActorTaskThree._
  val log = Logging.getLogger(context.system, this)
 
  def receive = {
    case childActorTaskThree: ReceiveMessageChildren => {
       log.info(s"Id ${childActorTaskThree.senderId} sent the message to its  neighbours ${childActorTaskThree.id}.");
       sender() ! ReceiveMessageSupervisior(childActorTaskThree.name, childActorTaskThree.id, childActorTaskThree.totalActors) // Sending acknowledge to sender again that a path is covered .(step -11).
    }
  }
 
}

object ChildActorTaskThree {
  def props = Props(new ChildActorTaskThree)
  case class ReceiveMessageChildren(name: String, id: Int, senderId: Int, totalActors: Int)
}