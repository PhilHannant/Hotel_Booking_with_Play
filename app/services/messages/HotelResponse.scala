package messages


sealed trait HotelResponse

trait IsRoomAvailableResponse extends HotelResponse
trait AddBookingResponse extends HotelResponse
trait AvailableRoomsResponse extends HotelResponse
case class AvailableRooms(availableRoomNumbers: Seq[Int]) extends AvailableRoomsResponse

case class ErrorResponse() extends HotelResponse
case class ErrorOccurred() extends HotelResponse
case class RoomAvaiable() extends IsRoomAvailableResponse
case class NoRoomAvailable() extends IsRoomAvailableResponse
case class BookingMade() extends AddBookingResponse
