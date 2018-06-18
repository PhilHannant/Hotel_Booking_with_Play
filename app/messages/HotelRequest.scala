package messages

import java.util.Date

sealed trait HotelRequest
case class IsRoomAvailable(room: Int, date: Date) extends HotelRequest
case class AddBooking(guest: String, room: Int, date: Date) extends HotelRequest
case class GetAvailableRooms(date: Date) extends HotelRequest