package com.example.akka.sample

import akka.actor.{ActorRef, Props, Actor}
import akka.event.Logging
import akka.actor.ActorSystem
 
class SupervisorActorThree extends Actor {
  import SupervisorActorThree._
  import ChildActorTaskThree._
  
  val log = Logging.getLogger(context.system, this)
  
  // Map to store the path an actor traversed i.e. both previous and next neighbour
  var traversalMap :Map[Int,(Int, Int)] = Map()
  
  def receive = {
    case receiveMessageSupervisior: ReceiveMessageSupervisior => {
      
      val id = receiveMessageSupervisior.id // The current id of the actor which is about to call its neighbours (ex- child actor 2{id} want to sent the message to it's neighbour 1 and 3) (Step - 5) 
      val previousNeighbourId =  if (id - 1 < 0) receiveMessageSupervisior.totalActors - 1 else id - 1 // Id of previous neighbour (Step - 6)
      val nextNeighbourId =  if (id + 1 > receiveMessageSupervisior.totalActors - 1) 0 else id + 1 // Id of next neighbour (Step - 7)
      
      val isKeyExists =  traversalMap.exists(_._1 == id) // To see if the current id is already send the message to its neighbours  (Step - 8)
      
      if( !isKeyExists ){
         traversalMap += (id -> (previousNeighbourId, nextNeighbourId)) // This is the first time where the given actor is sending the message to its neighbour. By doing so we're storing this in a map for future check.  (Step - 9)
         log.info(s"Id ${id} is about to send the message to its  neighbours ${previousNeighbourId} and ${nextNeighbourId}.");
         Thread.sleep(100) // Putting wait for  100 millisecond
         context.actorSelection("akka://task-3/user/"+ previousNeighbourId + "_Child") ! ReceiveMessageChildren(receiveMessageSupervisior.name, previousNeighbourId, id, receiveMessageSupervisior.totalActors) // Calling the previous neighbour  (Step - 10)
         Thread.sleep(100) // Putting wait for  100 millisecond
         context.actorSelection("akka://task-3/user/" + nextNeighbourId + "_Child") ! ReceiveMessageChildren(receiveMessageSupervisior.name, nextNeighbourId, id, receiveMessageSupervisior.totalActors) // Calling the next neighbour  (Step - 10)
      }
      
    }
    
    case _ => log.info("Doing Nothing...")
    
  }
 
}
 
object SupervisorActorThree {
 
  def props = Props(new SupervisorActorThree)
 
  case class ReceiveMessageSupervisior(name: String, id: Int, totalActors: Int)

 
}