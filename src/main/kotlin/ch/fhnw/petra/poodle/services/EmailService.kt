package ch.fhnw.petra.poodle.services

import ch.fhnw.petra.poodle.misc.MailTexts
import ch.fhnw.petra.poodle.misc.MailTexts.Invitation.eventLink
import ch.fhnw.petra.poodle.misc.MailTexts.Invitation.eventName
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendInvitation(participants: List<String>, eventName: String, eventLink: String) {
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

    fun sendReminder(participants: List<String>, eventName: String, eventLink: String) {
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

    fun sendMeeting(participants: List<String>, meetingName: String, meetingLink: String) {
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