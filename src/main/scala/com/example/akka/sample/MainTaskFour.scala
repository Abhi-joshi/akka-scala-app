package com.example.akka.sample

import akka.actor.ActorSystem
import akka.actor.ActorRef
import SchedulerActorTaskFour.SchedulerTriggerSend
import scala.util.Random
import java.time.LocalDate

object MainTaskFour extends App {
  
  // Creating the actor system (Step - 1)
  val system = ActorSystem("task-4")
  
  // Creating the n actors (we can also  achieve it by simply passing the argument to main function and loop through it ) (Step - 2)
  val agentActor_1 = system.actorOf(AgentActorTaskFour.props, name = "agent_1")
  val agentActor_2 = system.actorOf(AgentActorTaskFour.props, name = "agent_2")
  val agentActor_3 = system.actorOf(AgentActorTaskFour.props, name = "agent_3")
  val agentActor_4 = system.actorOf(AgentActorTaskFour.props, name = "agent_4")
  
  // Creating a list of agents  (Step - 3)
  val agentList = List(agentActor_1, agentActor_2, agentActor_3, agentActor_4)
  
    // Creating the main actor which will send the trigger to the actor agent (Step - 4)
  val schedulerActor = system.actorOf(SchedulerActorTaskFour.props, name = "scheduler")
  
  /// Calling the main actor. Passing the random child in it (Step - 5) 
  agentList.foreach((agent: ActorRef) => schedulerActor ! SchedulerTriggerSend(LocalDate.now(), Random.nextInt(100), agent))
  
  //system.terminate()
  
}