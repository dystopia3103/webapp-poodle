package ch.fhnw.petra.poodle

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.entities.Participation
import ch.fhnw.petra.poodle.entities.Vote
import ch.fhnw.petra.poodle.misc.UrlHelper
import ch.fhnw.petra.poodle.services.EmailService
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.javamail.JavaMailSender


@ExtendWith(MockitoExtension::class)
class MailTests {

    @Mock
    private val mailSender: JavaMailSender? = null

    @Mock
    private val urlHelper: UrlHelper? = null

    @BeforeEach
    fun setUp() {
        val mimeMessageMock = Mockito.mock(MimeMessage::class.java)
        lenient().`when`(mailSender!!.createMimeMessage()).thenReturn(mimeMessageMock)

        `when`(urlHelper!!.createUrl(Mockito.anyString())).thenReturn("") //don't care about url here
    }

    @Test
    fun `should invite all participants`() {
        // arrange
        val event = Event(participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com"))
        val service = EmailService(mailSender!!, urlHelper!!)

        // act
        service.sendInvitation(event)

        // assert
        verify(mailSender, times(3)).send(Mockito.any(MimeMessage::class.java))
    }

    @Test
    fun `should ignore empty emails when inviting`() {
        // arrange
        val event = Event(participantEmails = mutableListOf("", "", "asdf"))
        val service = EmailService(mailSender!!, urlHelper!!)

        // act
        service.sendInvitation(event)

        // assert
        verify(mailSender, times(1)).send(Mockito.any(MimeMessage::class.java))
    }

    @Test
    fun `should correctly notify about fixed meeting`() {
        // Arrange
        val event = Event(
            name = "Sample Event",
            link = "https://example.com/event",
            participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com"),
            participations = mutableListOf(
                Participation(participantEmail = "petra@gmail.com", votes = mutableListOf(Vote())),
                Participation(participantEmail = "pascal@gmail.com", votes = mutableListOf()), // no vote here
                Participation(participantEmail = "toby@gmail.com", votes = mutableListOf(Vote())),
                Participation(participantEmail = null, votes = mutableListOf(Vote()))
            )
        )
        val service = EmailService(mailSender!!, urlHelper!!)

        // Act
        service.sendMeeting(event)

        // Assert
        verify(mailSender, times(2)).send(Mockito.any(MimeMessage::class.java))
    }

    @Test
    fun `should create correct link for invitation`() {
        // arrange
        val event = Event(
            link = "1234",
            participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com")
        )
        val service = EmailService(mailSender!!, urlHelper!!)

        // act
        service.sendInvitation(event)

        // assert
        verify(urlHelper, times(1)).createUrl("/participate/1234")
    }

    @Test
    fun `should create correct link for reminder`() {
        // arrange
        val event = Event(
            link = "1234",
            participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com")
        )
        val service = EmailService(mailSender!!, urlHelper!!)

        // act
        service.sendReminder(event)

        // assert
        verify(urlHelper, times(1)).createUrl("/participate/1234")
    }

    @Test
    fun `should create correct link for meeting`() {
        // arrange
        val event = Event(
            link = "1234",
            participantEmails = mutableListOf("petra@gmail.com", "pascal@gmail.com", "toby@gmail.com")
        )
        val service = EmailService(mailSender!!, urlHelper!!)

        // act
        service.sendMeeting(event)

        // assert
        verify(urlHelper, times(1)).createUrl("/meeting/1234")
    }

}