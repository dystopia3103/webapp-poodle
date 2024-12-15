package ch.fhnw.petra.poodle

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.services.EmailService
import jakarta.mail.Message
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatcher
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.javamail.JavaMailSender


@ExtendWith(MockitoExtension::class)
class MailTests {

    @Mock
    private val mailSender: JavaMailSender? = null

    @Test
    fun shouldSendInvitationMailToParticipants() {
        // arrange
        val event = Event(participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com"))
        val service = EmailService(mailSender!!)

        // act
        service.sendInvitation(event)

        // assert
        verify(mailSender, Mockito.times(3)).send(Mockito.any(MimeMessage::class.java))
    }

}