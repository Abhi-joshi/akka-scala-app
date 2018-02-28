package com.example.akka.sample

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import scala.collection.mutable.Queue

class SchedulerActorTaskFour extends Actor {
  import SchedulerActorTaskFour._
  import AgentActorTaskFour._
  
   val log = Logging.getLogger(context.system, this)
   var ints = Queue[(java.time.LocalDate, Int, ActorRef)]()
   
    def receive = {
    case schedulerTriggerSend: SchedulerTriggerSend => {
      log.info("Simulation is starting..."); // Printing the start simulation message
      log.info(s"Scheduler actor is about to send the trigger to agent actor: ${schedulerTriggerSend.agent.path.name}"); // Printing the message when scheduler is ready to send the trigger to agent
      schedulerTriggerSend.agent ! AgentTrigger(schedulerTriggerSend.time, schedulerTriggerSend.random) // Message send to agent actor (Step - 6) 
    }
    
    case schedulerTriggerReceive: SchedulerTriggerReceive => {
      log.info(s"Scheduler actor received trigger from the agent actor: ${schedulerTriggerReceive.agent.path.name}"); // Receive the trigger message from the agent actor
       val message = (schedulerTriggerReceive.time, schedulerTriggerReceive.random, schedulerTriggerReceive.agent) // Storing the received message into the queue (Step-8)
       ints += (message)
    }
    
    case schedulerAcknowledgement: SchedulerAcknowledgement => {
      log.info(s"Acknowledge received from the agent actor: ${schedulerAcknowledgement.agent.path.name}"); // Received the acknowledgement message from the agent
      if(!ints.isEmpty){
         val firstMessage = ints.dequeue // Removing the first message from the queue and send the trigger message (Step-10)
          firstMessage._3 ! AgentTrigger(firstMessage._1, firstMessage._2)
      }
     
    }
    
    case _ => log.info("Doing Nothing...")
    
  }
  
}

object SchedulerActorTaskFour {
  def props = Props(new SchedulerActorTaskFour)
  case class SchedulerTriggerSend(time: java.time.LocalDate, random: Int, agent: ActorRef)
  case class SchedulerTriggerReceive(time: java.time.LocalDate, random: Int, agent: ActorRef)
  case class SchedulerAcknowledgement(agent: ActorRef)
}