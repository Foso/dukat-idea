package de.jensklingenberg.dukatidea

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class DukatIdeaAction : AnAction() {
    
    override fun actionPerformed(anActionEvent: AnActionEvent) {
        val filePath = anActionEvent.dataContext.getData(CommonDataKeys.VIRTUAL_FILE).toString().replace("file://", "")
        val folderPath = filePath?.replaceAfterLast("/", "")
        showInformationNotification("Dukat is started")

        Runtime.getRuntime().exec("dukat $filePath -d $folderPath")
    }

    override fun update(e: AnActionEvent) {
        val filepath = e.dataContext.getData(CommonDataKeys.PSI_FILE)?.viewProvider?.virtualFile?.path ?: ""
        e.presentation.isVisible = filepath.contains(".d.ts")

    }

    private fun showInformationNotification(
        message: String = "",
        title: String = ""
    ) {
        Notifications.Bus.notify(
            Notification(
                "Dukat Idea",
                title,
                message,
                NotificationType.INFORMATION
            )
        )
    }
}