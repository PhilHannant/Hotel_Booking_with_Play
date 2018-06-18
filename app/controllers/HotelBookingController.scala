package controllers

import javax.inject.{Inject, Singleton}

import actors.BookingSystemActor
import akka.actor.{ActorSystem, Props}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class HotelBookingController @Inject() (system: ActorSystem, cc: ControllerComponents)
  extends AbstractController(cc){

  val bookingManagerActor = system.actorOf(Props[BookingSystemActor], "bookingSystemActor")

}
