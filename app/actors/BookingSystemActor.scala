package actors

import akka.actor.{Actor, ActorLogging}
import messages._
import services.system.BookingManagerSystem

import scala.util.{Failure, Success, Try}

class BookingSystemActor extends Actor with ActorLogging {

  val bookingSystem = BookingManagerSystem()


  override def preStart: Unit = {
    println("BookingSystem: Starting")
  }

  override def postStop: Unit = {
    println("BookingSystem: Shutting Down")
  }

  override def receive: Receive = {
    case IsRoomAvailable(room, date) =>
      Try(bookingSystem.isRoomAvailable(room, date))
      match {
        case Failure(e) => sender() ! NoRoomAvailable()
        case Success(s) => sender() ! NoRoomAvailable()
      }
    case AddBooking(guest, room, date) =>
      Try(bookingSystem.addBooking(guest, room, date))
      match {
        case Failure(e) => sender() ! ErrorOccurred()
        case Success(t) => sender() ! BookingMade()
      }
    case GetAvailableRooms(date) =>
      Try(bookingSystem.getAvailableRooms(date).nonEmpty)
      match {
        case Failure(e) => sender() ! ErrorOccurred()
        case Success(t) => sender() ! AvailableRooms(bookingSystem.getAvailableRooms(date))
      }
  }
}

