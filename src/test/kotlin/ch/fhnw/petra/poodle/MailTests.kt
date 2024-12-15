package ch.fhnw.petra.poodle

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.services.EmailService
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.javamail.JavaMailSender


@ExtendWith(MockitoExtension::class)
class MailTests {

    @Mock
    private val mailSender: JavaMailSender? = null

    @Test
    fun `should invite all participants`() {
        // arrange
        val event = Event(participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com"))
        val service = EmailService(mailSender!!)
        val mimeMessageMock = Mockito.mock(MimeMessage::class.java)
        `when`(mailSender.createMimeMessage()).thenReturn(mimeMessageMock)

        // act
        service.sendInvitation(event)

        // assert
        verify(mailSender, Mockito.times(3)).send(Mockito.any(MimeMessage::class.java))
    }

    @Test
    fun `should ignore empty emails when inviting`() {
        // arrange
        val event = Event(participantEmails = mutableListOf("", "", "asdf"))
        val service = EmailService(mailSender!!)
        val mimeMessageMock = Mockito.mock(MimeMessage::class.java)
        `when`(mailSender.createMimeMessage()).thenReturn(mimeMessageMock)

        // act
        service.sendInvitation(event)

        // assert
        verify(mailSender, Mockito.times(1)).send(Mockito.any(MimeMessage::class.java))
    }

}