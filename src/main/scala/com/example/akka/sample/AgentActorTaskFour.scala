package com.example.akka.sample

import akka.actor.{Actor, ActorRef, Props}
import akka.event.Logging
import scala.util.Random
import java.time.LocalDate

class AgentActorTaskFour extends Actor {
  import AgentActorTaskFour._
  import SchedulerActorTaskFour._
  
   val log = Logging.getLogger(context.system, this)
   
   var count: Int = 0  
   
    def receive = {
    case agentTrigger: AgentTrigger => {
      log.info(s"Message received from SchedulerActor: Id: ${self.path.name} Time: ${agentTrigger.time} and Random: ${agentTrigger.random}"); // Print the message when agent actor receive the trigger from scheduler actor
      count += 1 ; // Agent sent and receive the trigger message at max 10 times
      val random = Random.nextInt(100)
      if(count < 10 ){
        sender ! SchedulerTriggerReceive(LocalDate.now(), random, self) // Sent the trigger message to scheduler (Step - 7)    
      }
       sender ! SchedulerAcknowledgement(self) //Sent the acknowledgement message to scheduler (Step - 9) 
      
    }
    
    case _ => log.info("Doing Nothing...")
    
  }
  
}

object AgentActorTaskFour {
  def props = Props(new AgentActorTaskFour)
  case class AgentTrigger(time: java.time.LocalDate, random: Int)
}