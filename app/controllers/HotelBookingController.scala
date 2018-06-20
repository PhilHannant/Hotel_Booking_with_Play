package controllers

import java.text.SimpleDateFormat
import javax.inject.{Inject, Singleton}

import actors.BookingSystemActor
import akka.actor.{ActorSystem, Props}
import messages._
import play.api.mvc._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, ExecutionContext, Future, Promise}

//@Singleton
//class HotelBookingController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {
//
//
//  val bookingManagerActor = system.actorOf(Props[BookingSystemActor], "bookingSystemActor")
//  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")
//  implicit val timeout = Timeout(5 seconds)
//
//  def stuff = {
//    val isRoomAvailable = bookingManagerActor ? IsRoomAvailable(101, today)
//    val result = Await.result(isRoomAvailable, timeout.duration)
//    println(result)
//  }
//  def message = Action. {
//    getFutureMessage(1.second).map { msg => Ok(msg) }
//  }
//}


@Singleton
class HotelBookingController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {


  val bookingManagerActor = actorSystem.actorOf(Props[BookingSystemActor], "bookingSystemActor")
  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")
  implicit val timeout = Timeout(5 seconds)
  /**
    * Creates an Action that returns a plain text message after a delay
    * of 1 second.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/message`.
    */
  def message = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val isRoomAvailable = bookingManagerActor ? AddBooking("Smith", 101, today)
    val result = Await.result(isRoomAvailable, timeout.duration)
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success(result.toString)
    }(actorSystem.dispatcher) // run scheduled tasks using the actor system's dispatcher
    promise.future
  }

}