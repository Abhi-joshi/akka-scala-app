package com.example.akka.sample

import akka.actor.{ActorRef, ActorSystem, PoisonPill}

object MainTaskOneAndTaskTwo extends App{
  
  // Creating the actor system (Step - 1)
  val system: ActorSystem = ActorSystem.create("task-1-2")
  
  // Calling the main actor. Passing the random child in it (Step - 2) 
  val supervisor: ActorRef = system.actorOf(SupervisorActorTaskOneAndTaskTwo.props)
  
  // Creating the n actors (we can also  achieve it by simply passing the argument to main function and loop through it ) (Step - 3)
  supervisor ! CreateChild("1")
  supervisor ! CreateChild("2")
  supervisor ! CreateChild("3")
  
  // Printing the count of total number of hops travelled. (Step - 11)
  supervisor ! Display()

  Thread.sleep(5000)

  supervisor ! PoisonPill
}