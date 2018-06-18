package bookingManagerExceptions

final case class RoomNotAvailableException(errorMessage: String) extends Exception(errorMessage) {
  override def getMessage: String = errorMessage
}
