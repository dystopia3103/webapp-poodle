package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.entities.Event
import ch.fhnw.petra.poodle.misc.MailTexts
import ch.fhnw.petra.poodle.misc.MailTexts.Invitation.eventLink
import ch.fhnw.petra.poodle.misc.MailTexts.Invitation.eventName
import ch.fhnw.petra.poodle.misc.UrlHelper
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender, private val urlHelper: UrlHelper) {

    fun sendInvitation(event: Event) {
        val participants = event.participantEmails
        val eventName = event.name
        val eventLink = invitationLink(event.link)
        participants.forEach {
            val recipient = it.trim()
            if (recipient.isNotBlank()) {
                sendEmail(
                    recipient,
                    MailTexts.Invitation.subject.eventName(eventName),
                    MailTexts.Invitation.text.eventName(eventName).eventLink(eventLink),
                )
            }
        }
    }

    fun invitationLink(link: String): String {
        return urlHelper.createUrl("/participate/" + link)
    }

    fun sendReminder(event: Event) {
        val participants = event.participantEmails
        val eventName = event.name
        val eventLink = reminderLink(event.link)
        participants.forEach {
            val recipient = it.trim()
            if (recipient.isNotBlank()) {
                sendEmail(
                    recipient,
                    MailTexts.Reminder.subject.eventName(eventName),
                    MailTexts.Reminder.text.eventName(eventName).eventLink(eventLink),
                )
            }
        }
    }

    fun reminderLink(link: String): String {
        return invitationLink(link)
    }

    fun sendMeeting(event: Event) {
        val participants =
            event.participations.filter { it.participantEmail != null && it.votes.any() }.map { it.participantEmail!! }
        val meetingName = event.name
        val meetingLink = meetingLink(event.link)
        participants.forEach {
            val recipient = it.trim()
            if (recipient.isNotBlank()) {
                sendEmail(
                    recipient,
                    MailTexts.Meeting.subject.eventName(meetingName),
                    MailTexts.Meeting.text.eventName(meetingName).eventLink(meetingLink),
                )
            }
        }
    }

    fun meetingLink(link: String): String {
        return urlHelper.createUrl("/meeting/" + link)
    }

    private fun sendEmail(recipient: String, subject: String, htmlContent: String) {
        try {
            val mimeMsg = mailSender.createMimeMessage()
            val message = MimeMessageHelper(mimeMsg, true)
            message.setFrom("poodle@gmail.com")
            message.setTo(recipient)
            message.setSubject(subject)
            message.setText(htmlContent, true)
            mailSender.send(mimeMsg)
        } catch (e: Exception) {
            // ignore
        }
    }
}