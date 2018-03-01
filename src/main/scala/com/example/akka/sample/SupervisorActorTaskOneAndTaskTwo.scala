package com.example.akka.sample

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.Future


case class Count()
case class CountResponse(count: Int)
case class CreateChild(name: String)
case class Display()

class SupervisorActorTaskOneAndTaskTwo extends Actor{
 
  val greeter: ActorRef = context.actorOf(ChildActorTaskOneAndTaskTwo.props)
  var numberOfHopsTravelled = 0 // Storing the number of hops travelled

  override def receive: Receive = {
    
    case CreateChild(name) =>{
            val child = context.actorOf(ChildActorTaskOneAndTaskTwo.props, name = s"$name") // Getting context of the child which actor which is to be called (Step - 4)
            child ! RecordNumberOfHopsTravelled(numberOfHopsTravelled, name) //  // Calling the child actor (Step - 5)
            increaseHopsValueByOne(child)// Increment the value of hops (Step - 7)
    }
    
    case Display() => println(s"Number Of Hops Travelled: ${numberOfHopsTravelled}");

    case _ => unhandled()
  }
  
  def increaseHopsValueByOne(child: ActorRef): Unit = {
      
      implicit val timeout = new Timeout(1 seconds)
       // ask the child actor for count
      val future = child ? Count() // Calling the child actor to get the count travelled by him (Step - 9)
      // synchronously wait for a response
      val result = Await.result(future,timeout.duration).asInstanceOf[CountResponse] // synchronously wait for a response
      numberOfHopsTravelled = result.count // Reassign the hops value to the by current count (Step - 10)
  }
  
}

object SupervisorActorTaskOneAndTaskTwo {
  def props = Props(classOf[SupervisorActorTaskOneAndTaskTwo])
}