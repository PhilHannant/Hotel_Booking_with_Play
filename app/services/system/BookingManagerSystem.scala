package services.system

import java.util.Date

import exceptions.{NoRoomsAvailableException, RoomNotAvailableException}

import scala.collection.concurrent


class BookingManagerSystem extends BookingManager  {


  val bookings = new concurrent.TrieMap[String, Booking]
  val rooms: Set[Int] = Set(101, 102, 201, 203)

  /**
    * Return true if there is no booking for the given room on the date,
    * otherwise false
    */
  override def isRoomAvailable(room: Int, date: Date): Boolean = {
    !bookings.values.toList.contains(Booking(room, date))
  }

  /**
    * Add a booking for the given guest in the given room on the given
    * date. If the room is not available, throw a suitable Exception.
    */
  override def addBooking(guest: String, room: Int, date: Date): Boolean = {
    if (bookings.isEmpty || isRoomAvailable(room, date)) {
      bookings.put(guest, Booking(room, date))
      true
    } else {
      throw RoomNotAvailableException(s"Room $room not available")
    }
  }

  def roomIsValid(room: Int): Boolean = {
    rooms.contains(room)
  }

  /**
    * Return a list of all the available room numbers for the given date
    */
  override def getAvailableRooms(date: Date): Seq[Int] = {
    val bookedRooms = bookings.values.filter(b => b.date == date).toSeq
    val result = rooms.filter(r => !bookedRooms.map(_.room).contains(r)).toSeq
    if(result.nonEmpty){
      result
    } else {
      throw NoRoomsAvailableException(s"No rooms available for $date")
    }
  }

}


object BookingManagerSystem {
  def apply(): BookingManagerSystem = new BookingManagerSystem()
}