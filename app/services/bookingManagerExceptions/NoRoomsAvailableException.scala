package bookingManagerExceptions

final case class NoRoomsAvailableException(errorMessage: String) extends Exception(errorMessage) {
  override def getMessage: String = errorMessage
}
