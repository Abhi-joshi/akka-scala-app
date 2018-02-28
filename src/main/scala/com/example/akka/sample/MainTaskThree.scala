package com.example.akka.sample

import akka.actor.ActorSystem
import SupervisorActorThree.ReceiveMessageSupervisior
 
object MainTaskThree {
  def main(args: Array[String]): Unit = {
    
    // Creating the actor system (Step - 1)
    val system = ActorSystem.create("task-3")
    
    // Creating the n actors (we can also  achieve it by simply passing the argument to main function and loop through it ) (Step - 2)
    val child_0 = system.actorOf(ChildActorTaskThree.props, "0_Child")
    val child_1 = system.actorOf(ChildActorTaskThree.props, "1_Child")
    val child_2 = system.actorOf(ChildActorTaskThree.props, "2_Child")
    val child_3 = system.actorOf(ChildActorTaskThree.props, "3_Child")
    val child_4 = system.actorOf(ChildActorTaskThree.props, "4_Child")
    val child_5 = system.actorOf(ChildActorTaskThree.props, "5_Child")
    
    // Creating the main actor which will keep the record of the path which child actor completed (Step - 3)
    val supervisor = system.actorOf(SupervisorActorThree.props, "Supervisor")
    
    // Calling the main actor. Passing the random child in it (Step - 4) 
    supervisor ! ReceiveMessageSupervisior("Social integration", 2, 6) 
    
 
    //system.terminate()
 
  }
}